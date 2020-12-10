package com.sethgholson.ctec151.menu;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class MainMenu {
  public enum Option {
    CREATE(1, "create a new character"),
    SELECT(2, "select an existing character"),
    LOAD(3, "load a character from file"),
    SAVE(4, "save a character to file"),
    ACTION(5, "perform an action"),
    EXIT(6, "exit the app");

    private final int id;
    private final String description;

    Option(int id, String description) {
      this.id = id;
      this.description = description;
    }

    public static Option valueOf(int id) {
      for (Option o : Option.values()) {
        if (o.id == id) {
          return o;
        }
      }
      return null;
    }
  }

  private static String generateMenuMessage() {
    StringBuilder sb = new StringBuilder("Select from the following options: \n");
    for (int i = 0; i < Option.values().length; i++) {
      Option o = Option.values()[i];
      sb.append(String.format(" %d. %s\n", o.id, o.description));
    }
    return sb.toString();
  }

  public Option presentMenu(InputStream in, PrintStream out, String greeting) {
    Scanner scanner = new Scanner(in);
    Option selectedOption = null;
    while (selectedOption == null) {
      out.println(greeting);
      out.println(generateMenuMessage());
      String input = scanner.nextLine();
      try {
        selectedOption = Option.valueOf(Integer.parseInt(input));
      } catch (Exception ignored) {
      }
    }
    return selectedOption;
  }
}
