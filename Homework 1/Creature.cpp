#include <iostream>
#include <string>
#include "Creature.h"
#include "Room.h"
using namespace std;

Creature::Creature(Room* r, string label) {
  location = r;
  this->label = label;
};
  
Room* Creature::getLocation() {
  return location;  
};

void Creature::changeLocation(Room* r) {
  location = r;
}

string Creature::getLabel() {
  return label;
};

void Creature::clean() {
  location->modifyState("clean", this);
}

void Creature::dirty() {
  location->modifyState("dirty", this);
}

// First I must seperate the classes into their own files and use the .h convention. Then, I must
// Do logic for creatures leaving rooms. Return the value respect is affected by this action.
bool Creature::leaveRoom() {
  Room* oldRoom = location;
  if (location->getNorth() != NULL) {
    if (!location->getNorth()->isFull()) {
      oldRoom->getNorth()->addCreature(this);
      this->changeLocation(oldRoom->getNorth());
      oldRoom->removeCreature(this);
      cout << label << " leaves to the north.\n" << endl;
      return true;
    }
  }
  if (location->getSouth() != NULL) {
    if (!location->getSouth()->isFull()) {
      oldRoom->getSouth()->addCreature(this);
      this->changeLocation(oldRoom->getSouth());
      oldRoom->removeCreature(this);
      cout << label << " leaves to the south.\n" << endl;
      return true;
    }
  }
  if (location->getEast() != NULL) {
    if (!location->getEast()->isFull()) {
      oldRoom->getEast()->addCreature(this);
      this->changeLocation(oldRoom->getEast());
      oldRoom->removeCreature(this);
      cout << label << " leaves to the east.\n" << endl;
      return true;
    }  
  }
  if (location->getWest() != NULL) {
    if (!location->getWest()->isFull()) {
      oldRoom->getWest()->addCreature(this);
      this->changeLocation(oldRoom->getWest());
      oldRoom->removeCreature(this);
      cout << label << " leaves to the west.\n" << endl;
      return true;
    }
  } //If not returned by now, then there is
   // nowhere to go, and the creature must burrow out.
  return false;
}
