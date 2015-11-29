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

bool NetworkModel::setInput(Model* m, int sourceIndices []) {
  int modelIndex = this->getIndex(m);
  int numberOfSources = (sizeof(sourceIndices)/sizeof(*sourceIndices));
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
  string output = "";
  for (int i = 0; i < this->numberOfModels; i++) {
    if (outputIndex[i]) {
      if (this->models[i] != NULL) {
        output.append(this->models[i]->output() + " ");
      }
    }
  }
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
  Event* events[currentCount];
  for (int i = 0; i < currentCount; i++) {
    Event e = pqueue->top();
    events[i] = &e;
    cout << events[i] << endl;
    pqueue->pop();
  }
  for (int i = 0; i < currentCount; i++) {
    cout << "Event " << i << ": " << events[i] << endl;
  }
}

void NetworkModel::confluentTransition(string networkInputs [], double timeOfInput) {
  this->externalTransition(networkInputs, timeOfInput);
  this->internalTransition();
}

void NetworkModel::printPqueue() {
  cout << endl << endl;
  int size = pqueue->size();
  for ( int i = 0; i < size; i++ ) {
    Event e = pqueue->top();
    pqueue->pop();
    cout << e.getTime() << endl;
  }
  cout << endl << endl;
}
