#include <iostream>
#include <queue>
#include "Memory.h"
#include "XOR.h"
#include "Event.h"
#include "NetworkModel.h"
#include "Framework.h"
using namespace std;

int main() {
  Model* x1 = new XOR(0,0,"x2");
  Model* x2 = new XOR(0,0,"x1");
  Model* memory = new Memory(0,0);
  NetworkModel* inner = new NetworkModel(2,3);
  NetworkModel* outer = new NetworkModel(2,2);
  inner->addModel(x1);
  inner->addModel(x2);
  outer->addModel(memory);
  outer->addModel(inner);
  
  int* inputs = new int[2];
  inputs[0] = -2;
  inputs[1] = -3;
  inner->setInput(x1,inputs,2);

  inputs[0] = inner->getIndex(x1);
  inputs[1] = -1;
  inner->setInput(x2,inputs,2);
  delete inputs;
  
  int* inputs2 = new int[1];
  inputs2[0] = outer->getIndex(inner);
  outer->setInput(memory,inputs2,1);
  delete inputs2;

  int* inputs3 = new int[3];
  inputs3[0] = -1;
  inputs3[1] = -2;
  inputs3[2] = outer->getIndex(memory);
  outer->setInput(inner,inputs3,3);
  delete inputs3;
  
  inner->addOutputModel(x2);
  outer->addOutputModel(inner);

  int numberOfInputs = 6;
  string **input;
  double times[numberOfInputs];
  input = new string *[numberOfInputs];
  for (int i = 0; i < numberOfInputs; i++) {
    input[i] = new string[2];
    input[i][0] = "1";
    input[i][1] = "0";
    times[i] = i + 1;
  }
  
  Framework* framework = new Framework(outer);
  framework->run(input, times, 2, 2);
  
}
