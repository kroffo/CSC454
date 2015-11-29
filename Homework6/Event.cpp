#include <iostream>
#include <queue>
#include <string>
#include "Model.h"
#include "Event.h"
using namespace std;

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
