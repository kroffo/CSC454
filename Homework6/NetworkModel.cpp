#include <iostream>
#include <queue>
#include <string>
#include "Model.h"
#include "NetworkModel.h"
using namespace std;

//priority_queue<Event, vector<Event>, compareEvents> NetworkModel::priority_queue<Event, vector<Event>, compareEvents> pqueue;
//int NetworkModel::int numberOfModels;
//int NetworkModel::int numberOfInputs;

NetworkModel::NetworkModel(int numModels, int numInputs):Model() {
  cout << "Hello world!" << endl;
}

void NetworkModel::externalTransition(string inputs [], double timeInput) {}

void NetworkModel::internalTransition() {}

void NetworkModel::confluentTransition(string inputs [], double timeOfInput) {}

double NetworkModel::timeAdvance() { return 4.0; }

string NetworkModel::output() { return "Hello World!"; }
