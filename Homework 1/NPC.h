#ifndef NPC_included
#define NPC_included
#include "Creature.h"
#include "Room.h"
using namespace std;

class NPC : public Creature {
private:

public:
  NPC(Room* r, string label);

  string toString();

  bool react(string action, Creature* c);
};

#endif
