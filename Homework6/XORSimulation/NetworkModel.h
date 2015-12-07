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
  int numberOfModels;
  int numberOfInputs;
  int* numberOfInputsForModel; //****[]
  Model** models; //****[][]
  int** inputs; //****[][]
  bool* outputIndex; //****[]
  bool isOutputModel(Model* m);
  void removeTransitionOnQueue(Model* m);
  Model* getTransitioningModels(); // Return an array
  int numberOfCurrentEvents();
  bool transitionsDoNotContainModel(Event events[], Model* m, int numberOfEvents);
  bool validInputForModel(Model* m, string inputs []);
  
  

 public:
  NetworkModel(int numberOfModels, int numInputs);
  ~NetworkModel();
  bool validInput(string inputs[]);
  bool addModel(Model* m);
  int getIndex(Model* m);
  bool setInput(Model* m, int sourceIndices [], int numberOfSources);
  bool addOutputModel(Model* m); 
  void externalTransition(string networkInputs[], double timeOfInput);
  void internalTransition();
  void confluentTransition(string networkInputs [], double timeOfInput);
  double timeAdvance();
  string output();
};

#endif
