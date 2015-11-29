#include <iostream>
#ifndef Press_included
#define Press_included
#include "Model.h"
using namespace std;

class Press : public Model {
 private:
  int parts;
  double elapsed;
  double startTime;
  
 public:
  Press();
  void externalTransition(string inputs [], double timeOfInput);
  void internalTransition();
  void confluentTransition(string inputs [], double timeOfInput);
  double timeAdvance();
  string output();
};

#endif
