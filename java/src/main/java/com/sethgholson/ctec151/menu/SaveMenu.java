package com.sethgholson.ctec151.menu;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

public class SaveMenu {
  private final InputStream input;
  private final PrintStream output;

  public SaveMenu(InputStream input, PrintStream output) {
    this.input = input;
    this.output = output;
  }

  public String askFileName() {
    String saveFileRawInput = MenuUtils.ask(input, output, "Provide a name for this save:");
    return saveFileRawInput.trim().replace(File.pathSeparator, "");
  }
}
