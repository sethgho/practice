package com.sethgholson.ctec151.menu;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class MenuUtils {

  private MenuUtils() {
    //No instances allowed
  }

  static String ask(InputStream input, PrintStream output, String question) {
    Scanner scanner = new Scanner(input);
    System.out.println(question);
    return scanner.nextLine();
  }

  static int askInt(InputStream input, PrintStream output, String question) {
    Integer result = null;
    while (result == null) {
      Scanner scanner = new Scanner(input);
      output.println(question);
      String answer = scanner.nextLine();
      try {
        result = Integer.parseInt(answer);
      } catch (Exception ignored) {
        output.println("Please enter a numerical value.");
      }
    }
    return result;
  }

  public static void error(PrintStream out, String error) {
    out.println(error);
  }

  static int askOptions(InputStream input, PrintStream output, String question, String... options) {
    Integer result = null;
    while (result == null) {
      Scanner scanner = new Scanner(input);
      output.println(question);
      for (int i = 0; i < options.length; i++) {
        output.println(String.format("%d. %s", i + 1, options[i]));
      }
      String answer = scanner.nextLine();
      try {
        int selection = Integer.parseInt(answer);
        if (selection > options.length || selection < 0) {
          output.println("Invalid option selected. Try again.");
        } else {
          result = selection;
        }
      } catch (Exception ignored) {
        output.println("Please enter a numerical value.");
      }
    }
    return result;
  }
}
