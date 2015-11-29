#include <iostream>
#include <queue>
#include "TestModel.h"
#include "Event.h"
#include "NetworkModel.h"
using namespace std;


// priority_queue stores copies, and returns copies. The copies are destroyed when popped.
// Apparently "top"ing an empty priority_queue returns the previously last element. Be sure to check the size every time.
int main() {
  // TestModel* t = new TestModel();
  // Event* e1 = new Event(t,2.0,0,"I'm an event!");
  // priority_queue<Event, vector<Event>, compareEvents>* pqueue = new priority_queue<Event, vector<Event>, compareEvents>();
  // pqueue->push(*new Event(t,2.0,0,"I'm an event on a queue!"));
  // pqueue->push(*new Event(t,1.0,0,"I'm a different event on a queue!"));
  // cout << pqueue->size() << endl;
  // pqueue->pop();
  // pqueue->pop();
  // pqueue->push(*e1);
  // pqueue->pop();
  // cout << pqueue->empty() << endl;
  // Event e2 = pqueue->top();
  // delete pqueue;
  // cout << pqueue->empty();
  // Event* e3 = &e2;
  // cout << e3->getType() << endl;

  NetworkModel* nm = new NetworkModel(2);
  TestModel* t1 = new TestModel();
  TestModel* t2 = new TestModel();
  nm->addModel(t1);
  nm->addModel(t2);
  int inputs [] = {-2};
  nm->setInput(t1,inputs);
  inputs[0] = -1;
  nm->setInput(t2,inputs);
  nm->addOutputModel(t1);
  nm->addOutputModel(t2);
  string input[] = {"test_input", "test_input"};
  nm->printPqueue();
  nm->externalTransition(input, 0.0);
  nm->internalTransition();
  // nm->printPqueue();
  // nm->externalTransition(input, 0.0);
  // nm->printPqueue();
  // nm->externalTransition(input, 0.0);
  // nm->printPqueue();
  cout << "Hey wouldja look at that!" << endl;
}
