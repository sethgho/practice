package com.sethgholson.ctec151.menu;

import com.sethgholson.ctec151.ActionResult;
import com.sethgholson.ctec151.character.GameCharacter;
import java.io.InputStream;
import java.io.PrintStream;

public class ActionMenu {
  private final InputStream input;
  private final PrintStream output;
  private final GameCharacter character;

  public ActionMenu(InputStream input, PrintStream output, GameCharacter selectedCharacter) {
    this.input = input;
    this.output = output;
    this.character = selectedCharacter;
  }

  public void presentActions() {
    boolean exit = false;
    while (!exit) {
      int choice =
          MenuUtils.askOptions(input, output, "What would you like to do?", "roll for attack",
              "roll for damage",
              "roll for physical save",
              "roll for magical save",
              "go back to main menu");

      switch (choice) {
        case 1: //attack
          reportActionResults(character.rollAttack(), "*");
          break;
        case 2: //damage
          reportActionResults(character.rollDamage(), "-");
          break;
        case 3: // physical save
          reportActionResults(character.rollDamage(), "-");
          break;
        case 4: //magical save
          reportActionResults(character.rollDamage(), "-");
          break;
        case 5:
          exit = true;
          break;
      }
    }
  }

  private void reportActionResults(ActionResult result, String operator) {
    output.println(
        String.format("%s rolls a %d %s %d for a total of %d", character.name, result.roll,
            operator, result.modifier, result.total));
  }
}
