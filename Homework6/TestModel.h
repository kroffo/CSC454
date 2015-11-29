#include <iostream>
#ifndef TestModel_included
#define TestModel_included
#include "Model.h"
using namespace std;

class TestModel : public Model {
 private:
  int time;
  
 public:
  TestModel();
  void externalTransition(string inputs [], double timeOfInput);
  void internalTransition();
  void confluentTransition(string inputs [], double timeOfInput);
  double timeAdvance();
  string output();
};

#endif
