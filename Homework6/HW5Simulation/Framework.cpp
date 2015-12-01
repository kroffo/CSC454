#include <iostream>
#include <string>
#include <sstream>
#include "Model.h"
#include "Framework.h"
using namespace std;

Framework::Framework(Model* m) {
  this->model = m;
}

void Framework::run(string **inputs, double times[], int numberOfInputs, int numberOfInputsPerInput) {
  double untilTime = model->timeAdvance();
  double currentTime = 0.0;
  for (int i = 0; i < numberOfInputs; i++) {
    string* input = inputs[i]; // the array of inputs for the next time of input!
    double waitTime = times[i];
    while (waitTime > 0) {
      if (untilTime < waitTime && untilTime > 0) {
        currentTime = untilTime;
        string output = model->output();
        if (output.compare("NULLSTRING") != 0) {
          cout << currentTime << " - Output: " << output << endl;
        }
        model->internalTransition();
        untilTime = model->timeAdvance();
      } else if (waitTime < untilTime || untilTime < 0) {
        currentTime = waitTime;
	ostringstream strs;
	strs << currentTime;
	string currentTimeString = strs.str();
        string outString = currentTimeString + " - Input: ";
        for (int j = 0; j < numberOfInputsPerInput; j++) {
          outString.append(input[j] + " ");
        }
        cout << outString << endl;
        model->externalTransition(input, currentTime);
        waitTime = -1;
        if (untilTime < 0) {
          untilTime = model->timeAdvance();
        }
      } else {
        currentTime = waitTime;
	ostringstream strs;
	strs << currentTime;
	string currentTimeString = strs.str();
        string outString = currentTimeString;
        string output = model->output();
        if (output.compare("NULLSTRING") != 0) {
          outString.append(" - Output: " + output);
        }
        outString.append(" - Input: ");
        for (int j = 0; j < numberOfInputsPerInput; j++) {
          outString.append(input[j] + " ");
        }
        cout << outString << endl;
        model->confluentTransition(input, currentTime);
        waitTime = -1;
        untilTime = model->timeAdvance();
      }
    }
  }
}
