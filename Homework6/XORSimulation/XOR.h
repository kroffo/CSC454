#include <iostream>
#ifndef xor_included
#define xor_included
#include "Model.h"
using namespace std;

class XOR : public Model {
 private:
  int value1;
  int value2;
  bool firstTaken;
  string label;
  bool xOR(bool a, bool b);
  
 public:
  XOR(int v1, int v2, string a);
  void externalTransition(string inputs [], double timeOfInput);
  void internalTransition();
  void confluentTransition(string inputs [], double timeOfInput);
  double timeAdvance();
  string output();
};

#endif
