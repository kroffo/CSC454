#include <iostream>
#include <queue>
#include "Press.h"
#include "Drill.h"
#include "Event.h"
#include "NetworkModel.h"
#include "Framework.h"
using namespace std;

int main() {
  NetworkModel* nm = new NetworkModel(2,1);
  Model* p = new Press();
  Model* d = new Drill();
  nm->addModel(p);
  nm->addModel(d);
  
  int* inputs = new int[1];
  inputs[0] = -1;
  nm->setInput(p,inputs,1);
  inputs[0] = 0;
  nm->setInput(d,inputs,1);
  
  nm->addOutputModel(d);

  delete [] inputs;

  string **input;
  input = new string *[8];
  for (int i = 0; i < 8; i++) {
    input[i] = new string[1];
  }
  input[0][0] = "ball ball";
  input[1][0] = "ball";
  input[2][0] = "ball";
  input[3][0] = "ball ball ball";
  input[4][0] = "ball";
  input[5][0] = "ball ball";
  input[6][0] = "ball";
  input[7][0] = "ball";

  double times[] = {1.3, 2.3, 3.4, 4.6, 6.5, 7.8, 25.9, 29.7};
  Framework* framework = new Framework(nm);
  framework->run(input, times, 8, 1);
  
  for (int i = 0; i < 8; i++) {
    delete [] input[i];
  }
  delete [] input;
  //  delete [] times;
  delete nm;
  delete p;
  delete d;
  delete framework;
}
