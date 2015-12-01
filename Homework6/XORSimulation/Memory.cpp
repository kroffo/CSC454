#include <iostream>
#include <string>
#include "Model.h"
#include "Memory.h"
using namespace std;

Memory::Memory(int v1, int v2) {
  value1 = v1;
  value2 = v2;
}

string Memory::output() {
  return to_string(value2);
}

double Memory::timeAdvance() {
  return 1; //Never transitions unless it takes input.
}

void Memory::externalTransition(string inputs[], double timeOfInput) {
  value2 = value1;
  value1 = stoi(inputs[0]);
}

void Memory::internalTransition() {
  //Do nothing
}

void Memory::confluentTransition(string inputs[], double timeOfInput) {
  value2 = value1;
  value1 = stoi(inputs[0]);
}
