#include <iostream>
#include <string>
#include "Model.h"
#include "Framework.h"
using namespace std;

Framework::Framework(Model* m) {
  this->model = m;
}

void Framework::run(string **inputs, double times[], int numberOfInputs, int numberOfInputsPerInput) {
  cout << numberOfInputs << endl;
  double untilTime = model->timeAdvance();
  double currentTime = 0.0;
  for (int i = 0; i < numberOfInputs; i++) {
    string* input = inputs[i]; // the array of inputs for the next time of input!
    double waitTime = times[i];
    while (waitTime > 0) {
      if (untilTime < waitTime && untilTime > 0) {
        currentTime = untilTime;
        string output = model->output();
        if (output != "NULL79_*s") {
          cout << currentTime << " - Output: " << output << endl;
        }
        model->internalTransition();
        untilTime = model->timeAdvance();
      } else if (waitTime < untilTime || untilTime < 0) {
        currentTime = waitTime;
        string outString = to_string(currentTime) + " - Input: ";
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
        string outString = to_string(currentTime);
        string output = model->output();
        if (output != "NULL79_*s") {
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