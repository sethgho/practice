package com.sethgholson.ctec151;

public class CharacterManagerApp {

  public static void main(String[] args) {
    GameManager game = new GameManager(System.in, System.out);
    game.start();
  }
}
