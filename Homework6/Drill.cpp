#include <iostream>
#include <string>
#include "Model.h"
#include "Drill.h"
using namespace std;

Drill::Drill() {
  parts = 0;
  startTime = 0;
  elapsed = 0;
}

string Drill::output() {
  return "washer";
}

double Drill::timeAdvance() {
  if (parts > 0) {
    return 2.0 - elapsed;
  } else {
    return -1.0;
  }
}

void Drill::externalTransition(string input[], double time) {
  parts += sizeof(input)/sizeof(*input);
  if (startTime == 0) {
    startTime = time;
  } else if (parts > 1) {
    elapsed = time - startTime;
  } else {
    elapsed = 0.0;
  }
}

void Drill::internalTransition() {
  parts--;
  if (parts > 0) {
    startTime += 2.0;
    elapsed = 0.0;
  }
}

void Drill::confluentTransition(string input[], double time) {
  parts += (sizeof(input)/sizeof(*input) - 1);
  startTime = time;
  elapsed = 0.0;
}
