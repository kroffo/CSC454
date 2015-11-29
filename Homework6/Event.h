
#include <iostream>
#ifndef event_included
#define event_included
#include "Model.h"
using namespace std;

class Event {
 private:
  Model* model;
  double time;
  int discreteTime;
  string type;
  string input;

 public:
  Event(Model* m, double t, int dt, string tp);
  Event(Model* m, double t, int dt, string tp, string i);
  double getTime();
  int getDiscreteTime();
  string getType();
  string getInput();
  Model* getModel();
};

#endif
