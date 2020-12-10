package com.sethgholson.ctec151.menu;

import com.sethgholson.ctec151.character.Archetype;
import com.sethgholson.ctec151.character.GameCharacter;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateMenu {
  private final InputStream in;
  private final PrintStream out;

  public CreateMenu(InputStream input, PrintStream output) {
    this.in = input;
    this.out = output;
  }

  public GameCharacter presentCreationMenu() {
    GameCharacter.Builder builder = new GameCharacter.Builder();
    Archetype archetype = askArchetype();
    builder.archetype(archetype);
    builder.name(askName(archetype.name));
    askAttributes(builder);
    return builder.build();
  }

  private Archetype askArchetype() {
    Archetype result = null;
    while (result == null) {
      Archetype[] options = {
          Archetype.RANGER,
          Archetype.WIZARD,
          Archetype.WARRIOR,
          Archetype.THIEF
      };

      String[] optionNames = new String[options.length];
      for (int i = 0; i < options.length; i++) {
        optionNames[i] = options[i].name;
      }

      int answer = MenuUtils.askOptions(in, out, "What type of character would you like to create?",
          optionNames);
      result = options[answer - 1];
    }
    return result;
  }

  private String askName(String archetypeName) {
    String name = null;
    while (name == null) {
      String answer =
          MenuUtils.ask(in, out, String.format("What will this %s be called?", archetypeName));
      if (answer.trim().length() > 0) {
        name = answer;
      }
    }
    return name;
  }

  private void askAttributes(GameCharacter.Builder builder) {
    List<GameCharacter.Attribute> attributes =
        new ArrayList<>(GameCharacter.Attribute.values().length);
    for (GameCharacter.Attribute attribute : GameCharacter.Attribute.values()) {
      attributes.add(attribute);
    }
    Random rand = new Random();
    while (attributes.size() > 0) {
      int roll = rand.nextInt(50);
      GameCharacter.Attribute selectedAttribute = askAttribute(roll, attributes);
      attributes.remove(selectedAttribute);
      builder.setAttribute(selectedAttribute, roll);
    }
  }

  private GameCharacter.Attribute askAttribute(int roll, List<GameCharacter.Attribute> options) {
    GameCharacter.Attribute result = null;
    while (result == null) {
      if (options.size() == 1) {
        result = options.get(0);
        out.println(String.format(
            "You've rolled a %d. It has been assigned to the remaining attribute, %s. \n",
            roll, result.description));
      } else {
        StringBuilder sb = new StringBuilder(
            String.format(
                "You've rolled a %d. Which attribute would you like to assign this to? \n",
                roll));
        for (int i = 0; i < options.size(); i++) {
          sb.append(String.format("%d. %s \n", i + 1, options.get(i).description));
        }
        int selection = MenuUtils.askInt(in, out, sb.toString());
        if (selection > 0 && selection < options.size() + 1) {
          result = options.get(selection - 1);
        } else {
          MenuUtils.error(out, "Invalid number selected. Try again.");
        }
      }
    }
    return result;
  }
}
