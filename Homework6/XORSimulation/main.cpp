#include <iostream>
#include <queue>
#include "Memory.h"
#include "XOR.h"
#include "Event.h"
#include "NetworkModel.h"
#include "Framework.h"
using namespace std;

int main() {
  Model* x1 = new XOR(0,0);
  Model* x2 = new XOR(0,0);
  Model* memory = new Memory(0,0);
  NetworkModel* nm = new NetworkModel(3,2);
  nm->addModel(x1);
  nm->addModel(x2);
  nm->addModel(memory);
  
  int* inputs = new int[2];
  inputs[0] = -1;
  inputs[1] = -2;
  nm->setInput(x1,inputs,2);

  inputs[0] = nm->getIndex(x1);
  inputs[1] = nm->getIndex(memory);
  nm->setInput(x2,inputs,2);
  delete [] inputs;
  
  int* inputs2 = new int[1];
  inputs2[0] = nm->getIndex(x2);
  nm->setInput(memory,inputs2,1);
  delete [] inputs2;
  
  nm->addOutputModel(x2);

  int numberOfInputs = 50;
  string **input;
  double times[numberOfInputs];
  input = new string *[numberOfInputs];
  for (int i = 0; i < numberOfInputs; i++) {
    input[i] = new string[2];
    input[i][0] = "1";
    input[i][1] = "0";
    times[i] = i + 1;
  }
  
  Framework* framework = new Framework(nm);
  framework->run(input, times, numberOfInputs, 2);
  for (int i = 0; i < numberOfInputs; i++) {
    delete [] input[i];
  }
  delete [] input;
  delete framework;
  delete x1;
  delete x2;
  delete memory;
  delete nm;
}
