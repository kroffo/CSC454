#include <iostream>
#include "Model.h"
#include "XOR.h"
using namespace std;

XOR::XOR(int v1, int v2, string a) {
  value1 = v1;
  value2 = v2;
  firstTaken = false;
  label = a;
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
}

void XOR::internalTransition() {
  cout << "internal" << endl;
  //Do nothing
}

void XOR::confluentTransition(string inputs [], double timeOfInput) {
  if (sizeof(inputs)/sizeof(*inputs) < 2) {
    if (firstTaken) {
      value2 = stoi(inputs[0]);
      firstTaken = false;
    } else {
    cout << label << " " << inputs[0] << endl;
      value1 = stoi(inputs[0]);
      firstTaken = true;
    }
  } else {
    value1 = stoi(inputs[0]);
    value2 = stoi(inputs[1]);
  }
}

double XOR::timeAdvance() {
  return 1;
}