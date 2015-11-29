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

Event::Event(Model* m, double t, int dt, string tp, string i) {
  this->model = m;
  this->time = t;
  this->discreteTime = dt;
  this->type = tp;
  this->input = i;
}

Event::~Event() {
  
}

double Event::getTime() {
  return this->time;
}

int Event::getDiscreteTime() {
  return this->discreteTime;
}

string Event::getType() {
  return this->type;
}

string Event::getInput() {
  return input;
}

Model* Event::getModel() {
  return model;
}
