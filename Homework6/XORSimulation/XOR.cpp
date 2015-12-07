#include <iostream>
#include "Model.h"
#include "XOR.h"
using namespace std;

XOR::XOR(int v1, int v2) {
  value1 = v1;
  value2 = v2;
  firstTaken = false;
}

string XOR::output() {
  bool v1 = (value1 == 1);
  bool v2 = (value2 == 1);
  if (xOR(v1, v2)) {
    return "1";
  }
  return "0";
}

bool XOR::xOR(bool a, bool b) {
  return ((a || b) && !(a && b));
}

void XOR::externalTransition(string inputs [], double timeOfInput) {
    value1 = stoi(inputs[0]);
    value2 = stoi(inputs[1]);
    firstTaken = false;
    //    delete [] inputs;
}

void XOR::internalTransition() {
  //Do nothing
}

void XOR::confluentTransition(string inputs [], double timeOfInput) {
    value1 = stoi(inputs[0]);
    value2 = stoi(inputs[1]);
    firstTaken = false;
    //    delete [] inputs;
}

double XOR::timeAdvance() {
  return 1;
}
