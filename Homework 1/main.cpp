#include <iostream>
#include <string>
#include "Creature.h"
#include "Room.h"
#include "Animal.h"
#include "NPC.h"
#include "PC.h"
using namespace std;

extern PC* pc = NULL;

// class Creature {
// private:
//   Room *location;
//   string label;
// public:
//   Creature(Room* r, int label) {
//     location = r;
//     this->label = to_string(label);
//   };
  
//   Room* getLocation() {
//     return location;  
//   };

//   void changeLocation(Room* r) {
//     location = r;
//   }

//   string getLabel() {
//     return label;
//   };

//   // First I must seperate the classes into their own files and use the .h convention. Then, I must
//   // Do logic for creatures leaving rooms. Return the value respect is affected by this action.
//   int leaveRoom() { return 0;
//     if (location->getNorth() != NULL) {
      
//     } else if (location->getEast() != NULL) {
      
//     } else if (location->getSouth() != NULL) {

//     } else if (location->getWest() != NULL) {

//     } else { // Nowhere to go, creature must burrow out.
      
//     }
    
//     return -1;
//   }

//   virtual string toString() = 0;
//   virtual int react(string action) = 0;
// };


// class Room {
// private:
//   int label;
//   int state;
//   Room* north;
//   Room* south;
//   Room* east;
//   Room* west;
//   Creature* creatures[10] = {NULL};
  
// public:
//   Room(int label, int state) {
//     this->label = label;
//     this->state = state;
//   };

//   void initNeighbors(Room* neighbors[4]) {
//     north = neighbors[0];
//     south = neighbors[1];
//     east = neighbors[2];
//     west = neighbors[3];
//   }
  
//   void addCreature(Creature* c) {
//     for (int i = 0; i < 10; i++) {
//       if (creatures[i] == NULL) {
//         creatures[i] = c;
//         break;
//       }
//     }
//   }

//   void removeCreature(Creature* c) {
//     for (int i = 0; i < 10; i++) {
//       if (creatures[i]->getLabel() == c->getLabel()) {
//         creatures[i] = NULL;
//         break;
//       }
//     }
//   }
  
//   bool isFull() {
//     for (int i = 0; i < 10; i++) {
//       if (creatures[i] == NULL) {
//         return false;
//       }
//     }
//     return true;
//   }
  
//   string getState() {
//     if (state == 0) {
//       return "clean";
//     } else if (state == 2) {
//       return "dirty";
//     } // state must be half-dirty if neither of the above are true
//     return "half-dirty";
//   };

//   int getLabel() {
//     return label;
//   }

//   int modifyState(string action) {
//     if (action == "clean") {
//       state = state - 1;
//     } else { //action is "dirty"
//       state = state + 1;
//     }
//     int respectChange = 0;
//     for (int i = 0; i < 10; i++) {
//       if (creatures[i] != NULL) {
//         respectChange = respectChange + creatures[i]->react(action);
//       }
//     }
//     return respectChange;
//   }

//   Room* getNorth() {
//     return north;
//   };
  
//   Room* getSouth() {
//     return south;
//   };
  
//   Room* getEast() {
//     return east;
//   };
  
//   Room* getWest() {
//     return west;
//   };

//   string toString() {
//     string retstr = "\nRoom: " + to_string(label) + "\nState: " + getState() + "\nNeighbors:";
//     if (north->getLabel() != -1) {
//       retstr = retstr + "\n\tNorth: " + to_string(north->getLabel());
//     }
//     if (south->getLabel() != -1) {
//       retstr = retstr + "\n\tSouth: " + to_string(south->getLabel());
//     }
//     if (east->getLabel() != -1) {
//       retstr = retstr + "\n\tEast: " + to_string(east->getLabel());
//     }
//     if (west->getLabel() != -1) {
//       retstr = retstr + "\n\tWest: " + to_string(west->getLabel());
//     }
//     retstr = retstr + "\nCreatures:";
//     for (int i = 0; i < 10; i = i + 1) {
//       if (creatures[i] != NULL) {
//         retstr = retstr + "\n\t" + creatures[i]->toString();
//       }
//     }
//     return retstr + "\n";
//   };
// };



// class Animal : public Creature {
// private:

// public:
//   Animal(Room* r, int label):Creature(r, label){};

//   string toString() {
//     return "Animal " + this->getLabel();
//   };

//   int react(string action) {
//     if (action == "clean") {
//       cout << this->getLabel() << + " licks your face." << endl;
//       return 1;
//     } else { // action must be dirty
//       cout << this->getLabel() << + " growls at you." << endl;
//       if (this->getLocation()->getState() == "dirty") {
//         return this->leaveRoom();
//       }
//       return -1;
//     }
//   }
// };

// class NPC : public Creature {
// private:
  
// public:
//   NPC(Room* r, int label):Creature(r, label){};
  
//   string toString() {
//     return "NPC " + this->getLabel();
//   };

//   int react(string action) {
//     if (action == "dirty") {
//       cout << this->getLabel() << + " smiles at you." << endl;
//       return 1;
//     } else { // action must be clean
//       cout << this->getLabel() << + " grumbles." << endl;
//       if (this->getLocation()->getState() == "clean") {
//         return this->leaveRoom();
//       }
//       return -1;
//     }
//   }
// };

// class PC: public Creature {
// private:
//   int respect = 40;
  
// public:
//   PC(Room* r, int label):Creature(r, label){};
  
//   string toString() {
//     return "PC";
//   };

//   int getRespect() {
//     return respect;
//   };

//   void modifyRespect(int change) {
//     respect = respect + change;
//   };

//   int react(string action) {
//     return 0;
//   }
// };

void printHelpMessage() {
  cout << endl << "Your goal is to win the game by earning the respect of the creatures in the world you have created. You must get a respect of 80 or more to win."
       << endl << "Animals prefer cleanliness, whilst NPCs prefer their surroundings to be dirty.\n"
       << endl << "Commands: "
       << endl << "\tlook:\t\t\tSee the state, neighbors and creatures of the room."
       << endl << "\tclean:\t\t\tClean the room."
       << endl << "\tdirty:\t\t\tDirty the room."
       << endl << "\t[direction]:\t\tMove to the [direction] neighbor (\"north\", \"south\", \"east\" or \"west\")"
       << endl << "\t[number]:[command]:\tForce the Creature labeled [number] to execute [command] (\"clean\", \"dirty\" or [direction])"
       << endl << "\thelp:\t\t\tView this help message again."
       << endl << endl;
}

int main() {
  
  // Read in the number of rooms with error checking
  string numRooms = "0";
  bool roomsEntered = false;
  cout << "How many rooms? (Maximum of 100)" << endl;
  bool isNumber = false;
  while (!roomsEntered || !isNumber) {
    isNumber = true;
    cin >> numRooms;
    for (int i = 0; i < numRooms.length(); i++) {
      if (!isdigit(numRooms[i])) {
        isNumber = false;
        break;
      }
    }
    if (isNumber) {
    // try {
      if (stoi(numRooms) > 0 && stoi(numRooms) < 101) {
        roomsEntered = true;
      } else {
        cout << "Please enter a valid number.\nHow many rooms? (Maximum of 100)" << endl;
      }
    } else {
      // } catch(invalid_argument exc) {
      cout << "Please enter a valid number.\nHow many rooms? (Maximum of 100)" << endl;
    };
  }; 
  
  //Set up and populate the rooms array with error checking
  Room* rooms [100] = {NULL};
  int neighbors[stoi(numRooms)][4];
  cout << "\nPlease enter 5 numbers describing the room.\nState: 0, 1 or 2 for clean, half-drty or dirty respectively\nNorth South East and West: the number corresponding to the neighbor in that direction, or -1 for no neighbor\nThe format of each input line should be\n State North South East West\n" << endl;
  for (int i = 0; i < stoi(numRooms); i = i + 1) {
    string sstate, sn, ss, se, sw;
    bool infoEntered = false;
    cout << "Enter \"State North South East West\" for room " << i << ":" << endl;
    bool allNumbers = false;
    while (!infoEntered || !allNumbers) {
      allNumbers = true;
      cin >> sstate >> sn >> ss >> se >> sw;
      string neighbs[5] = {sstate, sn, ss, se, sw};
      for (int j = 0; j < 5; j++) {
        for (int k = 0; k < neighbs[j].length(); k++) {
          if (!isdigit(neighbs[j][k])) {
            if (k == 0) {
              if (!(neighbs[j][k] == '-' && neighbs[j].length() > 1)) { // If the first character is '-' then the number may be negative, so don't say it's not a number yet.
                allNumbers = false;
                break;
              } 
            } else {
              allNumbers = false;
              break;
            }
          }
        }
      }
      if (allNumbers) {
        int state = stoi(sstate);
        int n = stoi(sn);
        int s = stoi(ss);
        int e = stoi(se);
        int w = stoi(sw);
        if ( !(state < 0 || state > 2 ||
               n < -1 || n >= stoi(numRooms) ||
               s < -1 || s >= stoi(numRooms) ||
               e < -1 || e >= stoi(numRooms) ||
               w < -1 || w >= stoi(numRooms)) ) {
          infoEntered = true;
        } else {
          cout << "Input incorrectly formatted.\nEnter \"State North South East West\" for room " << i << ":" << endl;
        };
      } else {
        cout << "Input incorrectly formatted.\nEnter \"State North South East West\" for room " << i << ":" << endl;
      };
    }
    rooms[i] = new Room(to_string((long long int)i), stoi(sstate));
    neighbors[i][0] = stoi(sn);
    neighbors[i][1] = stoi(ss);
    neighbors[i][2] = stoi(se);
    neighbors[i][3] = stoi(sw);
  };
  for (int i = 0; i < stoi(numRooms); i++) {
    Room* neighs[4] = {NULL};// {rooms[neighbors[i][0]], rooms[neighbors[i][1]], rooms[neighbors[i][2]], rooms[neighbors[i][3]]};
    for (int j = 0; j < 4; j++) {
      if (neighbors[i][j] != -1) {
        neighs[j] = rooms[neighbors[i][j]];
      }
    }
    rooms[i]->initNeighbors(neighs);
  }

  // Read in the number of creatures with error checking
  string numCreatures = "0";
  int maxCreatures = 100;
  if (stoi(numRooms) < 10) {
    maxCreatures = stoi(numRooms)*10;
  };
  bool creaturesEntered = false;
  isNumber = false;
  cout << "How many creatures? (Maximum of " << maxCreatures << ")" << endl;
  while (!creaturesEntered || !isNumber) {
    isNumber = true;
    cin >> numCreatures;
    for (int i = 0; i < numCreatures.length(); i++) {
      if (!isdigit(numCreatures[i])) {
        isNumber = false;
        break;
      }
    }
    if (isNumber) {
      if (!(stoi(numCreatures) < 1 || stoi(numCreatures) > maxCreatures)) {
        creaturesEntered = true;
      } else {
        cout << "Please enter a positive number <" << maxCreatures << ".\nHow many creatures? (Maximum of " << maxCreatures << ")" << endl;
      };
    } else {
      cout << "Please enter a positive number.\nHow many creatures? (Maximum of " << maxCreatures << ")" << endl;
    }
  };

  //Create the creature array with error checking
  Creature* creatures [100] = {NULL};
  cout << "\nPlease enter 2 numbers describing the creature.\ncreatureType: 0 for PC, 1 for animal and 2 for NPC.\nLocation: The room number the creature is in.\nThe format of each input line should be\n Type Location\n" << endl;
  for (int i = 0; i < stoi(numCreatures); i++) {
    string stype, sroom;
    bool infoEntered = false;
    bool allNumbers = false;
    cout << "Enter \"Type Location\" for creature " << i << ":" << endl;
    while (!infoEntered || !allNumbers) {
      allNumbers = true;
      cin >> stype >> sroom;
      string creatInfo[2] = {stype, sroom};
      for (int j = 0; j < 2; j++) {
        for (int k = 0; k < creatInfo[j].length(); k++) {
          if (!isdigit(creatInfo[j][k])) {
            allNumbers = false;
            break;
          }
        }
      }
      if (allNumbers) {
        int type = stoi(stype);
        int room = stoi(sroom);
        if (!( type < 0 || type > 2 || room < 0 || room >= stoi(numRooms))) {
          if (rooms[room]->isFull()) {
            cout << "Room " << room << " is already full.\nEnter \"Type Location\" for creature " << i << ":" << endl;
          } else {
            infoEntered = true;
          }
        } else {
          cout << "Input incorrectly formatted.\nEnter \"Type Location\" for creature " << i << ":" << endl;
        };
      } else {
        cout << "Input incorrectly formatted.\nEnter \"Type Location\" for creature " << i << ":" << endl;
      }
    }
    if (stoi(stype) == 0) {
      // creatures[i] = PC::createPC(rooms[stoi(sroom)], i);
      creatures[i] = new PC(rooms[stoi(sroom)], to_string((long long int)i));
      pc = (PC*)creatures[i];
    } else if (stoi(stype) == 1) {
      creatures[i] = new Animal(rooms[stoi(sroom)], to_string((long long int)i));
    } else {
      creatures[i] = new NPC(rooms[stoi(sroom)], to_string((long long int)i));
    }
    rooms[stoi(sroom)]->addCreature(creatures[i]);
  };
  
  printHelpMessage();
  string command;
  while (command != "exit" && pc->getRespect()<80 && pc->getRespect()>0) {
    cout << "Enter a command:" << endl;
    cin >> command;
    if (command == "look") {
      cout << pc->getLocation()->toString() << endl;
    } else if (command == "north") {
      if (pc->getLocation()->getNorth() != NULL) {
        Room* newRoom = rooms[stoi(pc->getLocation()->getNorth()->getLabel())];
        if (!newRoom->isFull()) {
          Room* oldRoom = pc->getLocation();
          pc->changeLocation(newRoom);
          newRoom->addCreature(pc);
          oldRoom->removeCreature(pc);
          cout << "\nYou move to the north.\n" << endl << newRoom->toString() << endl;
        } else {
          cout << "\nThat room is already full.\n" << endl;
        }
      } else {
        cout << "There is no room to the north.\n" << endl;
      }
    } else if (command == "south") {
      if (pc->getLocation()->getSouth() != NULL) {
        Room* newRoom = rooms[stoi(pc->getLocation()->getSouth()->getLabel())];
        if (!newRoom->isFull()) {
          Room* oldRoom = pc->getLocation();
          pc->changeLocation(newRoom);
          newRoom->addCreature(pc);
          oldRoom->removeCreature(pc);
          cout << "\nYou move to the south.\n" << endl << newRoom->toString() << endl;
        } else {
          cout << "\nThat room is already full.\n" << endl;
        }   
      } else {
        cout << "There is no room to the south.\n" << endl;
      }
    } else if (command == "east") {
      if (pc->getLocation()->getEast() != NULL) {
        Room* newRoom = rooms[stoi(pc->getLocation()->getEast()->getLabel())];
        if (!newRoom->isFull()) {
          Room* oldRoom = pc->getLocation();
          pc->changeLocation(newRoom);
          newRoom->addCreature(pc);
          oldRoom->removeCreature(pc);
          cout << "\nYou move to the east.\n" << endl << newRoom->toString() << endl;
        } else {
          cout << "\nThat room is already full.\n" << endl;
        }
      } else {
        cout << "There is no room to the east.\n" << endl;
      }
    } else if (command == "west") {
      if (pc->getLocation()->getWest() != NULL) {
        Room* newRoom = rooms[stoi(pc->getLocation()->getWest()->getLabel())];
        if (!newRoom->isFull()) {
          Room* oldRoom = pc->getLocation();
          pc->changeLocation(newRoom);
          newRoom->addCreature(pc);
          oldRoom->removeCreature(pc);
          cout << "\nYou move to the west.\n" << endl << newRoom->toString() << endl;
        } else {
          cout << "\nThat room is already full.\n" << endl;
        }
      } else {
        cout << "There is no room to the west.\n" << endl;
      }
    } else if (command == "clean") {
      if (pc->getLocation()->getState() != "clean") {
        cout << "\nYou clean the room.\n" << endl;
        pc->getLocation()->modifyState("clean", pc);
        cout << "PC respect is now " << pc->getRespect() << endl << endl;
      } else {
        cout << "\nThe room is already clean.\n" << endl;
      }
    } else if (command == "dirty") {
      if (pc->getLocation()->getState() != "dirty") {
        cout << "\nYou dirty the room.\n" << endl;
        pc->getLocation()->modifyState("dirty", pc);
        cout << "PC respect is now " << pc->getRespect() << endl << endl;
      } else {
        cout << "\nThe room is already dirty.\n" << endl;
      }
    } else if (command == "help") {
      printHelpMessage();
    } else if (command.find(":") < 100000) {
      string sindex = command.substr(0,command.find(":"));
      int index = -1;
      bool validIndex = true;
      for (int j = 0; j < sindex.length(); j++) {
        if (!isdigit(sindex[j])) {
          validIndex = false;
          break;
        }
      };
      if (validIndex) {
        index = stoi(sindex);
        if (pc->getLocation()->hasCreature(creatures[index])) {
          string creatCommand = command.substr(command.find(":")+1);
          if (creatCommand == "clean") {
            if (pc->getLocation()->getState() != "clean") {
              creatures[index]->clean();
              cout << "PC respect is now " << pc->getRespect() << endl << endl;
            } else {
              cout << "\nThe room is already clean.\n" << endl;
            }
          } else if (creatCommand == "dirty") {
            if (pc->getLocation()->getState() != "dirty") {
              creatures[index]->dirty();
              cout << "PC respect is now " << pc->getRespect() << endl << endl;
            } else {
              cout << "\nThe room is already dirty.\n" << endl;
            }
          } else if (creatCommand == "north") {
            if (pc->getLocation()->getNorth() != NULL) {
              Room* newRoom = rooms[stoi(creatures[index]->getLocation()->getNorth()->getLabel())];
              if (!newRoom->isFull()) {
                Room* oldRoom = creatures[index]->getLocation();
                creatures[index]->changeLocation(newRoom);
                newRoom->addCreature(creatures[index]);
                oldRoom->removeCreature(creatures[index]);
                cout << creatures[index]->getLabel() << " moves to the north.\n" << endl;
              } else {
                cout << "That room is already full.\n" << endl;
              }
            } else {
              cout << "There is no room to the north.\n" << endl;
            }
          } else if (creatCommand == "south") {
            if (pc->getLocation()->getSouth() != NULL) {
              Room* newRoom = rooms[stoi(creatures[index]->getLocation()->getSouth()->getLabel())];
              if (!newRoom->isFull()) {
                Room* oldRoom = creatures[index]->getLocation();
                creatures[index]->changeLocation(newRoom);
                newRoom->addCreature(creatures[index]);
                oldRoom->removeCreature(creatures[index]);
                cout << creatures[index]->getLabel() << " moves to the south.\n" << endl;
              } else {
                cout << "That room is already full.\n" << endl;
              }
            } else {
              cout << "There is no room to the south.\n" << endl;
            }
          } else if (creatCommand == "east") {
            if (pc->getLocation()->getEast() != NULL) {
              Room* newRoom = rooms[stoi(creatures[index]->getLocation()->getEast()->getLabel())];
              if (!newRoom->isFull()) {
                Room* oldRoom = creatures[index]->getLocation();
                creatures[index]->changeLocation(newRoom);
                newRoom->addCreature(creatures[index]);
                oldRoom->removeCreature(creatures[index]);
                cout << creatures[index]->getLabel() << " moves to the east.\n" << endl;
              } else {
                cout << "That room is already full.\n" << endl;
              }
            } else {
              cout << "There is no room to the east.\n" << endl;
            }
          } else if (creatCommand == "west") {
            if (pc->getLocation()->getWest() != NULL) {
              Room* newRoom = rooms[stoi(creatures[index]->getLocation()->getWest()->getLabel())];
              if (!newRoom->isFull()) {
                Room* oldRoom = creatures[index]->getLocation();
                creatures[index]->changeLocation(newRoom);
                newRoom->addCreature(creatures[index]);
                oldRoom->removeCreature(creatures[index]);
                cout << creatures[index]->getLabel() << " moves to the west.\n" << endl;
              } else {
                cout << "That room is already full.\n" << endl;
              }
            } else {
              cout << "There is no room to the west.\n" << endl;
            }
          } else {
            cout << "Not a valid command.\n" << endl;
          }
        } else {
          cout << "Not a valid creature." << endl;
        }
        } else {
        cout << "Not a valid creature." << endl;
      }
    } else if (command != "exit") {
      cout << "\nUnknown command." << endl; 
    }
  }

  if (pc->getRespect() < 0) {
    cout << "Sorry, but it appears that you are no longer respected and have lost the game. Better luck next time!" << endl;
  } else if (pc->getRespect() > 80) {
    cout << "Congratulations! You are widely respected throughout the realm; you have won the game!" << endl;
  } else { // exit command entered
    cout << "Thank you for playing! Come back soon!" << endl;
  }
  
  for (int i = 0; i < stoi(numRooms); i++) {
    if (rooms[i] != NULL) {
      delete rooms[i];
    }
  }

  for (int i = 0; i < stoi(numCreatures); i++) {
    if (creatures[i] != NULL) {
      delete creatures[i];
    }
  }
  
  return 0;
}


