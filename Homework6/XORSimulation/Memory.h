#include <iostream>
#ifndef memory_included
#define memory_included
#include "Model.h"
using namespace std;

class Memory : public Model {
 private:
  int value1;
  int value2;
  
 public:
  Memory();
  void externalTransition(string inputs [], double timeOfInput);
  void internalTransition();
  void confluentTransition(string inputs [], double timeOfInput);
  double timeAdvance();
  string output();
};

#endif
