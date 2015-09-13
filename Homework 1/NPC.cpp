#include <iostream>
#include <string>
#include "Creature.h"
#include "Room.h"
#include "NPC.h"
#include "PC.h"
using namespace std;  

extern PC* pc;

NPC::NPC(Room* r, string label):Creature(r, label){};

string NPC::toString() {
  return "NPC " + this->getLabel();
};

bool NPC::react(string action, Creature* c) {
  if (c->getLabel() != this->getLabel()) {
    if (action == "dirty") {
      cout << this->getLabel() << + " smiles at you." << endl;
      pc->modifyRespect(1);
    } else { // action must be clean or a creature left
      cout << this->getLabel() << + " grumbles." << endl;
      pc->modifyRespect(-1);
      if (this->getLocation()->getState() == "clean") {
        return this->leaveRoom();
      }
    }
    return true;
  } else {
    if (action == "dirty") {
      cout << this->getLabel() << + " smiles at you excitedly." << endl;
      pc->modifyRespect(3);
    } else { // action must be clean or a creature left
      cout << this->getLabel() << + " grumbles harshly." << endl;
      pc->modifyRespect(-3);
      if (this->getLocation()->getState() == "clean") {
        return this->leaveRoom();
      }
    }
    return true;
  }
}
