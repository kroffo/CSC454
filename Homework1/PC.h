#include <iostream>
#include <string>
#include "Creature.h"
#include "Room.h"
#include "PC.h"
using namespace std;  
  
PC::PC(Room* r, string label):Creature(r, label){  
  respect = 40;
};


string PC::toString() {
  return "PC";
};

int PC::getRespect() {
  return respect;
};

void PC::modifyRespect(int change) {
  respect = respect + change;
};

bool PC::react(string action, Creature* c) {
  return true;
}
