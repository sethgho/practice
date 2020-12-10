package com.sethgholson.ctec151.character;

public enum Archetype {
  RANGER("Ranger"),
  WIZARD("Wizard"),
  WARRIOR("Warrior"),
  THIEF("Thief");

  public final String name;

  Archetype(String name) {
    this.name = name;
  }
}
