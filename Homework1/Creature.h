#ifndef creature_included
#define creature_included
#include "Room.h"
using namespace std;

class Room;

class Creature {
 private:
  Room *location;
  string label;  
  
 public:
  Creature(Room* r, string label);
  Room* getLocation();
  void changeLocation(Room* room);
  string getLabel();
  bool leaveRoom();
  void clean();
  void dirty();
  virtual string toString() = 0;
  virtual bool react(string state, Creature* c) = 0;
};

#endif
