package com.sethgholson.ctec151.menu;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.PrintStream;

public class LoadMenu {
  private final InputStream input;
  private final PrintStream output;

  public LoadMenu(InputStream input, PrintStream output) {
    this.input = input;
    this.output = output;
  }

  public File askFileName() {
    File result = null;
    File[] files = new File("./").listFiles(new FilenameFilter() {
      @Override public boolean accept(File dir, String name) {
        return name.endsWith(".json");
      }
    });
    if (files == null || files.length == 0) {
      output.println("⚠️ No available saves were found.");
    } else {
      for (int i = 0; i < files.length; i++) {
        output.println(String.format("%d. %s", i + 1, files[i].getName()));
      }
      int selection = MenuUtils.askInt(input, output, "Which save file would you like to load?");
      result = files[selection - 1];
    }
    return result;
  }
}
