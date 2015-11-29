#include <iostream>
#include "Model.h"
#include "TestModel.h"
using namespace std;

TestModel::TestModel() {
  this->time = 2;
}

void TestModel::externalTransition(string inputs [], double timeOfInput) {
  if (inputs[0] == "test_input") {
    cout << "BIG SUCCESS!!!" << endl;
    time++;
  }
  if (inputs[0] == "input_test") {
    cout << "SUCCESS BIG!!!" << endl;
    time += 2;
  }
}

void TestModel::internalTransition() {

}

void TestModel::confluentTransition(string inputs [], double timeOfInput) {

}

double TestModel::timeAdvance() {
  return time;
}

string TestModel::output() {
  return "Hello World!";
}
