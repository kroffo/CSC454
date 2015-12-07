
#include <iostream>
#ifndef model_included
#define model_included
using namespace std;

class Model {
public:
  Model();
  virtual void externalTransition(string inputs [], double timeOfInput) = 0;
  virtual void internalTransition() = 0;
  virtual void confluentTransition(string inputs [], double timeOfInput) = 0;
  virtual double timeAdvance() = 0;
  virtual string output() = 0;
};

#endif
