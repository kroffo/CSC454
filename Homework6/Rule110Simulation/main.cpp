#include <iostream>
#include <queue>
#include <sstream>
#include "Cell.h"
#include "Event.h"
#include "NetworkModel.h"
#include "Framework.h"
using namespace std;

int main() {
  int numberOfCells = 31;
  NetworkModel* nm = new NetworkModel(numberOfCells,1);
  Cell* cells[numberOfCells];

  for (int i = 0; i < numberOfCells; i++) {
    ostringstream strs;
    strs << i;
    if (i != 15) {
      cells[i] = new Cell("0",strs.str());
    } else {
      cells[i] = new Cell("1",strs.str());
    }
    nm->addModel(cells[i]);
    nm->addOutputModel(cells[i]);
  }
  
  int* inputs = new int[2];
  inputs[0] = numberOfCells-1;
  inputs[1] = 1;
  nm->setInput(cells[0],inputs,2);

  inputs[0] = 0;
  inputs[1] = numberOfCells-2;
  nm->setInput(cells[numberOfCells-1],inputs,2);

  for (int i = 1; i < numberOfCells-1; i++) {
    inputs[0] = i-1;
    inputs[1] = i+1;
    nm->setInput(cells[i],inputs,2);
  }
  delete [] inputs;

  int numberOfInputs = 1000;
  string **input;
  double times[numberOfInputs];
  input = new string *[numberOfInputs];
  for (int i = 0; i < numberOfInputs; i++) {
    input[i] = new string[1];
    input[i][0] = "";
    times[i] = i + 1;
  }

  Framework* framework = new Framework(nm);
  framework->run(input, times, numberOfInputs, 1);
  for (int i = 0; i < numberOfInputs; i++) {
    delete [] input[i];
  }
  delete [] input;
  delete framework;
  for (int i = 0; i < numberOfCells; i++) {
    delete cells[i];
  }
  delete nm;
}
