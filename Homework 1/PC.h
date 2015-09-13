#ifndef PC_included
#define PC_included
#include "Creature.h"
#include "Room.h"
using namespace std;

class PC : public Creature {
private:
  int respect;
  
public:
  PC(Room* r, string label);
  string toString();
  bool react(string action, Creature* c);
  int getRespect();
  void modifyRespect(int change);
};

#endif
