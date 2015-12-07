#include <iostream>
#include <string>
#include "Model.h"
#include "Cell.h"
using namespace std;

Cell::Cell(string s, string l) {
  state = s;
  label = l;
}

string Cell::output() {
  return label + ":" + state;
}

double Cell::timeAdvance() {
  return 1;
}

void Cell::externalTransition(string input[], double time) {
  string index0 = input[0].substr(0,input[0].find(":"));
  string index1 = input[1].substr(0,input[1].find(":"));
  string alpha = "";
  string omega = "";
  if (index0 < index1 || index0.length() < index1.length()) { 
    alpha = input[0].substr(input[0].find(":")+1);
    omega = input[1].substr(input[1].find(":")+1);
  } else {
    alpha = input[1].substr(input[1].find(":")+1);
    omega = input[0].substr(input[0].find(":")+1);
  }
  if ((label.compare("0") == 0) || (label.compare("30") == 0)) {
    string tmp = alpha;
    alpha = omega;
    omega = tmp;
  }
  string info = alpha + state + omega;
  if (info.compare("000") == 0) {
    state = "0";
  } else if (info.compare("001") == 0) {
    state = "1";
  } else if (info.compare("010") == 0) {
    state = "1";
  } else if (info.compare("100") == 0) {
    state = "0";
  } else if (info.compare("011") == 0) {
    state = "1";
  } else if (info.compare("101") == 0) {
    state = "1";
  } else if (info.compare("110") == 0) {
    state = "1";
  } else if (info.compare("111") == 0) {
    state = "0";
  }
}

void Cell::internalTransition() {
  //Do nothing
}

void Cell::confluentTransition(string input[], double time) {
  string index0 = input[0].substr(0,input[0].find(":"));
  string index1 = input[1].substr(0,input[1].find(":"));
  string alpha = "";
  string omega = "";
  if (index0 < index1 || index0.length() < index1.length()) {
    alpha = input[0].substr(input[0].find(":")+1);
    omega = input[1].substr(input[1].find(":")+1);
  } else {
    alpha = input[1].substr(input[1].find(":")+1);
    omega = input[0].substr(input[0].find(":")+1);
  }
  if ((label.compare("0") == 0) || (label.compare("30") == 0)) {
    string tmp = alpha;
    alpha = omega;
    omega = tmp;
  }
  string info = alpha + state + omega;
  if (info.compare("000") == 0) {
    state = "0";
  } else if (info.compare("001") == 0) {
    state = "1";
  } else if (info.compare("010") == 0) {
    state = "1";
  } else if (info.compare("100") == 0) {
    state = "0";
  } else if (info.compare("011") == 0) {
    state = "1";
  } else if (info.compare("101") == 0) {
    state = "1";
  } else if (info.compare("110") == 0) {
    state = "1";
  } else if (info.compare("111") == 0) {
    state = "0";
  }
}
