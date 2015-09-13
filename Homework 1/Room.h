#ifndef Room_included
#define Room_included
#include "Creature.h"
using namespace std;

class Creature;

class Room {
 private:
  string label;
  int state;
  Room* north;
  Room* south;
  Room* east;
  Room* west;
  Creature* creatures[10];

 public:
  Room(string label, int state);
  void initNeighbors(Room* neighbors[4]);
  void addCreature(Creature* c);
  void removeCreature(Creature* c);
  bool hasCreature(Creature* c);
  bool isFull();
  void equilibriumState();
  string getState();
  string getLabel();
  void modifyState(string action, Creature* c);
  void creatureLeft();
  Room* getNorth();
  Room* getSouth();
  Room* getEast();
  Room* getWest();
  string toString();
};

#endif
