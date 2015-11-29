#include <iostream>
#ifndef Drill_included
#define Drill_included
#include "Model.h"
using namespace std;

class Drill : public Model {
 private:
  int parts;
  double elapsed;
  double startTime;
  
 public:
  Drill();
  void externalTransition(string inputs [], double timeOfInput);
  void internalTransition();
  void confluentTransition(string inputs [], double timeOfInput);
  double timeAdvance();
  string output();
};

#endif
