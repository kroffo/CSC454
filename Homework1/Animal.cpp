#include <iostream>
#include <string>
#include "Creature.h"
#include "Room.h"
#include "Animal.h"
#include "PC.h"
using namespace std;  

extern PC* pc;

Animal::Animal(Room* r, string label):Creature(r, label){};

string Animal::toString() {
  return "Animal " + this->getLabel();
};

bool Animal::react(string action, Creature* c) {
  if (c->getLabel() != this->getLabel()) {
    if (action == "clean") {
      cout << this->getLabel() << + " licks your face." << endl;
      pc->modifyRespect(1);
    } else { // action must be dirty or a creature left
      cout << this->getLabel() << + " growls at you." << endl;
      pc->modifyRespect(-1);
      if (this->getLocation()->getState() == "dirty") {
        return this->leaveRoom();
      }
    }
    return true;
  } else {
    if (action == "clean") {
      cout << this->getLabel() << + " licks your face vigorously." << endl;
      pc->modifyRespect(3);
    } else { // action must be dirty or a creature left
      cout << this->getLabel() << + " growls at you intensely." << endl;
      pc->modifyRespect(-3);
      if (this->getLocation()->getState() == "dirty") {
        return this->leaveRoom();
      }
    }
    return true;
  }
}
