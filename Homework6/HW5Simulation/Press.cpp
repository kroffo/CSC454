#include <iostream>
#include <string>
#include "Model.h"
#include "Press.h"
using namespace std;

Press::Press() {
  parts = 0;
  startTime = 0;
  elapsed = 0;
}

string Press::output() {
  return "disk";
}

double Press::timeAdvance() {
  if (parts > 0) {
    return 1.0 - elapsed;
  } else {
    return -1.0;
  }
}

void Press::externalTransition(string input[], double time) {
  parts += sizeof(input)/sizeof(*input);
  if (startTime == 0 && parts == 0) {
    startTime = time;
  } else if (parts > 1) {
    elapsed = time - startTime;
  } else {
    elapsed = 0.0;
  }
}

void Press::internalTransition() {
  parts--;
  if (parts > 0) {
    startTime += 1.0;
    elapsed = 0.0;
  }
}

void Press::confluentTransition(string input[], double time) {
  parts += (sizeof(input)/sizeof(*input) - 1);
  startTime = time;
  elapsed = 0.0;
}
