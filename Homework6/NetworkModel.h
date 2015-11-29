#include <iostream>
#include <queue>
#ifndef networkModel_included
#define networkModel_included
#include "Model.h"
#include "Event.h"
using namespace std;

class Model;

class compareEvents {
 public:
  bool operator()(Event e1, Event e2) {
    if (e1.getTime() < e2.getTime()) return false;
    if (e1.getTime() == e2.getTime() && e1.getDiscreteTime() < e2.getDiscreteTime()) return false;
    return true;
  }
};


class NetworkModel : public Model {
 private:
  priority_queue<Event, vector<Event>, compareEvents>* pqueue;
  // ADD THE PRIORITY QUEUE, MAKE THE EVENTS CLASS, WRITE THESE METHODS. THEN WRITE THE FRAMEWORK, CREATE THE MAIN AND WE ARE DONE!!!! 
  int numberOfModels = 100;
  int numberOfInputs;
  int numberOfInputsForModel[100];
  Model* models[100];
  int inputs[100][100];
  bool outputIndex[100];
  bool isOutputModel(Model* m);
  void removeTransitionOnQueue(Model* m);
  Model* getTransitioningModels(); // Return an array
  int numberOfCurrentEvents();
  bool eventsDoNotContainModel(Event* events[], Model& m);
  bool validInputForModel(Model* m, string inputs []);
  
  

 public:
  NetworkModel(int numInputs);
  ~NetworkModel();
  bool validInput(string inputs[]);
  bool addModel(Model* m);
  int getIndex(Model* m);
  bool setInput(Model* m, int sourceIndices []);
  bool addOutputModel(Model* m); 
  void externalTransition(string networkInputs[], double timeOfInput);
  void internalTransition();
  void confluentTransition(string networkInputs [], double timeOfInput);
  double timeAdvance();
  string output();
  void printPqueue();
};

#endif
