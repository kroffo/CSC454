x#include <iostream>
#include <string>
#include "Creature.h"
#include "Room.h"
#include "PC.h"
using namespace std;  

extern PC* pc;

Room::Room(string label, int state) {
  this->label = label;
  this->state = state;
  Room::creatures = {NULL};
};

Room::~Room() {
  for (int i = 0; i < 10; i++) {
    if (creatures[i] != NULL) {
      delete creatures[i];
      creatures[i] = NULL;
    }
  }
}

void Room::initNeighbors(Room* neighbors[4]) {
  north = neighbors[0];
  south = neighbors[1];
  east = neighbors[2];
  west = neighbors[3];
}
  
void Room::addCreature(Creature* c) {
  for (int i = 0; i < 10; i++) {
    if (creatures[i] == NULL) {
      creatures[i] = c;
      break;
    }
  }
}

void Room::removeCreature(Creature* c) {
  for (int i = 0; i < 10; i++) {
    if (creatures[i] != NULL) {
      if (creatures[i]->getLabel() == c->getLabel()) {
        creatures[i] = NULL;
        break;
      }
    }
  }
}

bool Room::hasCreature(Creature* c) {
  for (int i = 0; i < 10; i++) {
    if (creatures[i] != NULL) {
      if (creatures[i]->getLabel() == c->getLabel()) {
        return true;
      }
    }
  }
  return false;
}

bool Room::isFull() {
  for (int i = 0; i < 10; i++) {
    if (creatures[i] == NULL) {
      return false;
    }
  }
  return true;
}

string Room::getState() {
  if (state == 0) {
    return "clean";
  } else if (state == 2) {
    return "dirty";
  } // state must be half-dirty if neither of the above are true
  return "half-dirty";
};

string Room::getLabel() {
  return label;
}

void Room::creatureLeft() {
  for (int i = 0; i < 10; i++) {
    if (creatures[i] != NULL) {
      creatures[i]->react("bad", pc);
    }
  }
}

void Room::equilibriumState() {
  this->state = 1;
}

void Room::modifyState(string action, Creature* c) {
  if (action == "clean") {
    state = state - 1;
  } else { //action is "dirty"
    state = state + 1;
  }
  int respectChange = 0;
  for (int i = 0; i < 10; i++) {
    if (creatures[i] != NULL) {
      if (!creatures[i]->react(action, c)) { //This condition returns false when the creature tries to leave the room but can't, and true otherwise
        cout << "There is no where for " << creatures[i]->getLabel() << " to go!" << endl << creatures[i]->getLabel() << " burrows out of the house!\n" << endl;
        cout << "The other creatures in the room are angered!" << endl;
	if (creatures[i]->getLabel() == c->getLabel()) {
	  c = pc;
	}
        delete creatures[i];
        creatures[i] = NULL;
        this->creatureLeft();
      }
    }
  }
}

Room* Room::getNorth() {
  return north;
};
  
Room* Room::getSouth() {
  return south;
};
  
Room* Room:: getEast() {
  return east;
};
  
Room* Room::getWest() {
  return west;
};

string Room::toString() {
  string retstr = "\nRoom: " + label + "\nState: " + getState() + "\nNeighbors:";
  if (north != NULL) {
    retstr = retstr + "\n\tNorth: " + north->getLabel();
  }
  if (south != NULL) {
    retstr = retstr + "\n\tSouth: " + south->getLabel();
  }
  if (east != NULL) {
    retstr = retstr + "\n\tEast: " + east->getLabel();
  }
  if (west != NULL) {
    retstr = retstr + "\n\tWest: " + west->getLabel();
  }
  retstr = retstr + "\nCreatures:";
  for (int i = 0; i < 10; i = i + 1) {
    if (creatures[i] != NULL) {
      retstr = retstr + "\n\t" + creatures[i]->toString();
    }
  }
  return retstr + "\n";
}
