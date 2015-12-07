#include <iostream>
#ifndef Cell_included
#define Cell_included
#include "Model.h"
using namespace std;

class Cell : public Model {
 private:
  string state;
  string label;
  
 public:
  Cell(string s, string l);
  void externalTransition(string inputs [], double timeOfInput);
  void internalTransition();
  void confluentTransition(string inputs [], double timeOfInput);
  double timeAdvance();
  string output();
  string getLabel();
};

#endif
