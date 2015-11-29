#include <iostream>
#include <queue>
#include <string>
#include "Model.h"
#include "Event.h"
using namespace std;

Event::Event() {}

Event::Event(Model* m, double t, int dt, string tp) {
  this->model = m;
  this->time = t;
  this->discreteTime = dt;
  this->type = tp;
}

Event::Event(Model* m, double t, int dt, string tp, string* i, int nInputs) {
  this->model = m;
  this->time = t;
  this->discreteTime = dt;
  this->type = tp;
  this->input = i;
  this->numberOfInputs = nInputs;
}

Event::~Event() {
  
}

double Event::getTime() {
  return time;
}

int Event::getDiscreteTime() {
  return discreteTime;
}

string Event::getType() {
  return type;
}

string* Event::getInput() {
  return input;
}

int Event::getNumberOfInputs() {
  return numberOfInputs;
}

Model* Event::getModel() {
  return model;
}
