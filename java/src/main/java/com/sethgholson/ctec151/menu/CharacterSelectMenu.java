package com.sethgholson.ctec151.menu;

import com.sethgholson.ctec151.character.GameCharacter;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public class CharacterSelectMenu {
  private final InputStream input;
  private final PrintStream output;

  public CharacterSelectMenu(InputStream input, PrintStream output) {
    this.input = input;
    this.output = output;
  }

  public GameCharacter askToSelectCharacter(List<GameCharacter> characters) {
    GameCharacter result = null;
    while(result == null) {
      StringBuilder question = new StringBuilder("Which character would you like to select?\n");
      for (int i = 0; i < characters.size(); i++) {
        question.append(String.format("%d. %s\n", i + 1, characters.get(i).toString()));
      }

      int pick = MenuUtils.askInt(input, output, question.toString());
      if(pick > characters.size() || pick <= 0) {
        MenuUtils.error(output, "Invalid selection. Try again.");
      } else {
        result = characters.get(pick - 1);
      }
    }

    return result;
  }
}
