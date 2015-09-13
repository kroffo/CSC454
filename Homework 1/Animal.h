#ifndef animal_included
#define animal_included
#include "Creature.h"
#include "Room.h"
using namespace std;

class Animal : public Creature {
private:

public:
  Animal(Room* r, string label);

  string toString();

  bool react(string action, Creature* c);
};

#endif
