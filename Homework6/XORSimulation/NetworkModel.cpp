#include <iostream>
#include <queue>
#include <string>
#include <sstream>
#include <vector>
#include <iterator>
#include "Model.h"
#include "NetworkModel.h"
#include "Event.h"
using namespace std;

//priority_queue<Event, vector<Event>, compareEvents> NetworkModel::priority_queue<Event, vector<Event>, compareEvents> pqueue;
//int NetworkModel::int numberOfModels;
//int NetworkModel::int numberOfInputs;

NetworkModel::NetworkModel(int numModels, int numInputs):Model() {
  this->numberOfModels = numModels;
  this->numberOfInputs = numInputs;
  models = new Model*[numModels];
  numberOfInputsForModel = new int[numModels];
  inputs = new int*[numModels];
  outputIndex = new bool[numModels];
  for (int i = 0; i < this->numberOfModels; i++) {
     this->models[i] = NULL;
     this->outputIndex[i] = false;
   }
  pqueue = new priority_queue<Event, vector<Event>, compareEvents>();
}

NetworkModel::~NetworkModel() {
  delete pqueue;
  delete models;
  delete outputIndex;
  delete numberOfInputsForModel;
  // for (int i = 0; i < this->numberOfModels; i++) {
  //   delete inputs[i];
  // }
  delete inputs;
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
    inputs[modelIndex] = new int[numberOfSources];
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
  string output = "";
  priority_queue<Event, vector<Event>, compareEvents>* pqueue2 = new priority_queue<Event, vector<Event>, compareEvents>();
  for (int i = 0; i < currentlyTransitioning; i++) {
    Event e = pqueue->top();
    pqueue->pop();
    pqueue2->push(e);
    if (e.getType().compare("internal") == 0) {
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
  if (output.compare("") == 0) return "NULLSTRING";
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
    string input[100000]; //an input token of over 100,000 inputs will break this.
    int index = 0;
    bool inputFound = false;
    for (int j = 0; j < this->numberOfInputsForModel[i]; j++) {
      if (inputs[i][j] < 0) {
        string thisInput = networkInputs[(-1*inputs[i][j])-1];
        istringstream buf(thisInput);
        istream_iterator<string> beg(buf), end;
        vector<string> tokens(beg,end);
        for (auto& s: tokens) {
          input[index++] = s;
        }
        inputFound = true;
      }
    }
    string inputs[index];
    for (int j = 0; j < index; j++) {
      inputs[j] = input[j];
    }
    if (index > 0) {
      Event* e = new Event(this->models[i], timeInput, 0, "input", inputs, index);
      pqueue->push(*e);
      delete e;
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
  cout << "internal called." << endl; 
  int currentCount = this->numberOfCurrentEvents();
  Event* events = new Event[currentCount];
  for (int i = 0; i < currentCount; i++) {;
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
    cout << "Processing event " << k << " " << event.getType() << endl;
    
    if (event.getType().compare("internal") == 0) {
      int count = 0;
      for (int i = 0; i < currentCount; i++) {
        for (int j = 0; j < numberOfInputsForModel[getIndex(model)]; j++) {
          if (getIndex(events[i].getModel()) == inputs[getIndex(model)][j]) {
            count++;
          }
        }
      }
      string inputFromModels[count];
      count = 0;
      for (int i = 0; i < currentCount; i++) {
        for (int j = 0; j < numberOfInputsForModel[getIndex(model)]; j++) {
          if (getIndex(events[i].getModel()) == inputs[getIndex(model)][j]) {
            inputFromModels[count++] = outputs[i];
          }
        }
      }
      string inputFromEvents[10000];
      int count2 = 0;
      for (int i = 0; i < currentCount; i++) {
        if (events[i].getModel() == event.getModel() && events[i].getType().compare("input") == 0) {
          int nInputs = events[i].getNumberOfInputs();
          string* inputsFromEvent = events[i].getInput();
          for (int j = 0; j < nInputs; j++) {
            inputFromEvents[count2++] = inputsFromEvent[j];
          }
        }
      }
      if (count + count2 > 0) { 
        string input[count + count2];
        for (int i = 0; i < count; i++) {
          input[i] = inputFromModels[i];
        }
        for (int i = 0; i < count2; i++) {
          input[count + i] = inputFromEvents[i];
        }
        model->confluentTransition(input, time);
      } else {
        model->internalTransition();
      }

      for (int i = 0; i < numberOfModels; i++) {
        if (eventsDoNotContainModel(events,models[i],currentCount)) {
          for (int j = 0; j < numberOfInputsForModel[i]; j++) {
            if (inputs[i][j] == getIndex(model)) {
              string* outputHere = new string[1];
              outputHere[0] = outputs[k];
              Event* newEvent = new Event(models[i], time, event.getDiscreteTime() + 1, "input", outputHere, 1);
              pqueue->push(*newEvent);
              delete newEvent;
            }
          }
        }
      }

      removeTransitionOnQueue(model);
      double time3 = model->timeAdvance();
      if (time3 > -1) {
        Event* newEvent = new Event(model, time + time3, 0, "internal");
        pqueue->push(*newEvent);
        delete newEvent;
      }
    } else if (event.getType().compare("input") == 0) {
      bool transitioning = false;
      for (int i = 0; i < currentCount; i++) {
        if (events[i].getModel() == model && events[i].getType().compare("internal") == 0) {
          transitioning = true;
        }
      }
      if (!transitioning) {
        string* inputs = event.getInput();
        model->externalTransition(inputs, time);
        removeTransitionOnQueue(model);
        double time2 = model->timeAdvance();
        if (time2 > -1) {
          Event* newEvent = new Event(model, time + time2, 0, "internal");
          pqueue->push(*newEvent);
          delete newEvent;
        }
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
  Event e = pqueue->top();
  this->externalTransition(networkInputs, timeOfInput);
  this->internalTransition();
}
