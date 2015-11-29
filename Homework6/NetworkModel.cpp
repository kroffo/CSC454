#include <iostream>
#include <queue>
#include <string>
#include "Model.h"
#include "NetworkModel.h"
#include "Event.h"
#include "TestModel.h"
using namespace std;

//priority_queue<Event, vector<Event>, compareEvents> NetworkModel::priority_queue<Event, vector<Event>, compareEvents> pqueue;
//int NetworkModel::int numberOfModels;
//int NetworkModel::int numberOfInputs;

NetworkModel::NetworkModel(int numInputs):Model() {
  this->numberOfInputs = numInputs;
  for (int i = 0; i < this->numberOfModels; i++) {
     this->models[i] = NULL;
     this->outputIndex[i] = false;
     for (int j = 0; j < 100; j++) {
       this->inputs[i][j] = -1;
     }
   }
  pqueue = new priority_queue<Event, vector<Event>, compareEvents>();
}

NetworkModel::~NetworkModel() {
  delete pqueue;
}

bool NetworkModel::validInput(string inputs[]) {
  return true;
}

bool NetworkModel::addModel(Model* m) {
  for (int i = 0; i < this->numberOfModels; i++) {
    if (this->models[i] == NULL) {
      this->models[i] = m;
      double t = models[i]->timeAdvance();
      if (t > -1) {
        Event* e = new Event(m, t, 0, "internal");
        pqueue->push(*e);
        delete e;
      }
      return true;
    }
  }
  return false;
}

int NetworkModel::getIndex(Model* m) {
  for (int i = 0; i < this->numberOfModels; i++) {
    if (this->models[i] == m) {
      return i;
    }
  }
  return -1;
}

bool NetworkModel::setInput(Model* m, int sourceIndices [], int numberOfSources) {
  int modelIndex = this->getIndex(m);
  if (modelIndex > -1) {
    for (int i = 0; i < numberOfSources; i++) {
      int index = sourceIndices[i];
      if (index >= 0) {
        if (this->models[index] == NULL) return false;
      } else if (index * -1 > this->numberOfInputs) {
        return false;
      }
    }
    for (int i = 0; i < numberOfSources; i++) {
      this->inputs[modelIndex][i] = sourceIndices[i];
    }
    this->numberOfInputsForModel[modelIndex] = numberOfSources;
    return true;
  }
  return false;
}

bool NetworkModel::addOutputModel(Model* m) {
  if (m != NULL) {
    int index = this->getIndex(m);
    if (index > -1) {
      this->outputIndex[index] = true;
      return outputIndex[index];
    }
  }
  return false;
}

bool NetworkModel::isOutputModel(Model* m) {
  if (m != NULL) {
    int index = this->getIndex(m);
    if (index > -1) {
      return outputIndex[index];
    }
  }
  return false;
}

string NetworkModel::output() {
  int currentlyTransitioning = numberOfCurrentEvents();
  if (currentlyTransitioning == 0) return "NULL79_*s";
  string output = "";
  priority_queue<Event, vector<Event>, compareEvents>* pqueue2 = new priority_queue<Event, vector<Event>, compareEvents>();
  for (int i = 0; i < currentlyTransitioning; i++) {
    Event e = pqueue->top();
    pqueue->pop();
    pqueue2->push(e);
    if (e.getType() == "internal") {
      Model* m = e.getModel();
      if (outputIndex[getIndex(m)]){
        output.append(m->output() + " ");
      }
    }
  }
  for (int i = 0; i < currentlyTransitioning; i++) {
    Event e = pqueue2->top();
    pqueue2->pop();
    pqueue->push(e);
  }
  delete pqueue2;
  return output;
}

//returns -1 to signify infinity otherwise the next time of an event.
double NetworkModel::timeAdvance() {
  if (pqueue->size() == 0) return -1;
  Event e = pqueue->top();
  return e.getTime();
}

//removes the all transition events on the given model from the queue
void NetworkModel::removeTransitionOnQueue(Model* m) {
  priority_queue<Event, vector<Event>, compareEvents>* pqueue2 = new priority_queue<Event, vector<Event>, compareEvents>();
  int numEvents = pqueue->size();
  for (int i = 0; i < numEvents; i++) {
    Event e = pqueue->top();
    pqueue->pop();
    if (e.getModel() != m) {
      pqueue2->push(e);
    }
  }
  numEvents = pqueue2->size();
  for (int i = 0; i < numEvents; i++) {
    Event e = pqueue2->top();
    pqueue->push(e);
  }
  delete pqueue2;
}

void NetworkModel::externalTransition(string networkInputs [], double timeInput) {
  for (int i = 0; i < this->numberOfModels; i++) {
    string input[numberOfInputsForModel[i]];
    int index = 0;
    bool inputFound = false;
    for (int j = 0; j < this->numberOfInputsForModel[i]; j++) {
      if (inputs[i][j] < 0) {
        input[index++] = networkInputs[(-1*inputs[i][j])-1];
        inputFound = true;
      }
    }
    if (inputFound) {
      this->models[i]->externalTransition(input, timeInput);
      this->removeTransitionOnQueue(this->models[i]);
      double time = this->models[i]->timeAdvance();
      if (time > -1) {
        Event* e = new Event(this->models[i], timeInput + time, 0, "internal");
        pqueue->push(*e);
      }
    }
  }
}

// returns how many events on the queue happen at the current time.
int NetworkModel::numberOfCurrentEvents() {
  priority_queue<Event, vector<Event>, compareEvents>* pqueue2 = new priority_queue<Event, vector<Event>, compareEvents>();
  Event currentEvent = pqueue->top();
  int count = 1;
  pqueue->pop();
  pqueue2->push(currentEvent);
  double currentTime = currentEvent.getTime();
  int currentDiscreteTime = currentEvent.getDiscreteTime();
  int size = pqueue->size();
  for (int i = 0; i < size; i++) {
    Event e = pqueue->top();
    if (e.getTime() == currentTime && e.getDiscreteTime() == currentDiscreteTime) {
      pqueue->pop();
      pqueue2->push(e);
      count++;
    } else {
      break;
    }
  }
  for (int i = 0; i < count; i++) {
    Event e = pqueue2->top();
    pqueue->push(e);
    pqueue2->pop();
  }
  return count;
}

void NetworkModel::internalTransition() {
  int currentCount = this->numberOfCurrentEvents();
  Event* events = new Event[currentCount];
  for (int i = 0; i < currentCount; i++) {
    Event e = pqueue->top();
    events[i] = e;
    pqueue->pop();
  }
  string outputs[currentCount];
  for (int i = 0; i < currentCount; i++) {
    outputs[i] = events[i].getModel()->output();
  }
  for (int k = 0; k < currentCount; k++) {
    Event event = events[k];
    double time = event.getTime();
    Model* model = event.getModel();
    string output = model->output();

    if (event.getType() == "internal") {
      int count = 0;
      for (int i = 0; i < currentCount; i++) {
        for (int j = 0; j < numberOfInputsForModel[getIndex(model)]; j++) {
          if (getIndex(events[i].getModel()) == inputs[getIndex(model)][j]) {
            count++;
          }
        }
      }
      string input[count];
      count = 0;
      for (int i = 0; i < currentCount; i++) {
        for (int j = 0; j < numberOfInputsForModel[getIndex(model)]; j++) {
          if (getIndex(events[i].getModel()) == inputs[getIndex(model)][j]) {
            input[count++] = outputs[i];
          }
        }
      }
      if (count > 0) {
        model->confluentTransition(input, time);
      } else {
        model->internalTransition();
      }

      for (int i = 0; i < numberOfModels; i++) {
        if (eventsDoNotContainModel(events,models[i],currentCount)) {
          for (int j = 0; j < numberOfInputsForModel[i]; j++) {
            if (inputs[i][j] == getIndex(model)) {
              pqueue->push(*new Event(models[i], time, event.getDiscreteTime() + 1, "input", outputs[k]));
            }
          }
        }
      }

      removeTransitionOnQueue(model);
      double time3 = model->timeAdvance();
      if (time3 > -1) {
        pqueue->push(*new Event(model, time + time3, 0, "internal"));
      }
    } else if (event.getType() == "input") {
      string inputs[] = {event.getInput()};
      model->externalTransition(inputs, time);
      removeTransitionOnQueue(model);
      double time2 = model->timeAdvance();
      if (time2 > -1) {
        pqueue->push(*new Event(model, time + time2, 0, "internal"));
      }
    }
  }
  delete [] events;
}

bool NetworkModel::eventsDoNotContainModel(Event events[], Model* m, int numberOfEvents) {
  for (int i = 0; i < numberOfEvents; i++) {
    Model* model = events[i].getModel();
    if (model == m) {
      return false;
    }
  }
  return true;
}

void NetworkModel::confluentTransition(string networkInputs [], double timeOfInput) {
  this->externalTransition(networkInputs, timeOfInput);
  this->internalTransition();
}
