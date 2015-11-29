#include <iostream>
#include <string>
#ifndef framework_included
#define framework_included
#include "Model.h"
using namespace std;

class Framework {
 private:
  Model* model;
  
 public:
  Framework(Model* m);
  void run(string **input, double times[], int numberOfInputs, int numberOfInputsPerInput);
};

#endif
