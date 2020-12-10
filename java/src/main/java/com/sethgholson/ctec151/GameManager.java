package com.sethgholson.ctec151;

import com.sethgholson.ctec151.character.GameCharacter;
import com.sethgholson.ctec151.menu.ActionMenu;
import com.sethgholson.ctec151.menu.CharacterSelectMenu;
import com.sethgholson.ctec151.menu.CreateMenu;
import com.sethgholson.ctec151.menu.LoadMenu;
import com.sethgholson.ctec151.menu.MainMenu;
import com.sethgholson.ctec151.menu.MenuUtils;
import com.sethgholson.ctec151.menu.SaveMenu;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class GameManager {
  private boolean isGameOn = true;

  private final InputStream input;
  private final PrintStream output;
  private String selectedCharacterId;
  private HashMap<String, GameCharacter> characters;

  private final SaveManager saveManager;

  public GameManager(InputStream in, PrintStream out) {
    this.input = in;
    this.output = out;
    characters = new HashMap<>();
    this.saveManager = new SaveManager();
  }

  public void start() {
    while (isGameOn) {
      presentMainMenu();
    }
    output.println("Thanks for playing!");
  }

  private void presentMainMenu() {
    MainMenu menu = new MainMenu();
    StringBuilder greeting = new StringBuilder("Welcome to the manager! ");
    if (getSelectedCharacter() != null) {
      greeting.append(String.format("You have %s selected. ", getSelectedCharacter()));
    } else {
      greeting.append("You have no character selected. ");
    }
    MainMenu.Option selectedItem = menu.presentMenu(input, output, greeting.toString());
    switch (selectedItem) {
      case CREATE:
        presentCreateMenu();
        break;
      case SELECT:
        presentSelectMenu();
        break;
      case LOAD:
        presentLoadMenu();
        break;
      case SAVE:
        presentSaveMenu();
        break;
      case ACTION:
        presentActionMenu();
        break;
      case EXIT:
        exit();
        break;
    }
  }

  private void presentActionMenu() {
    ActionMenu actionMenu = new ActionMenu(input, output, getSelectedCharacter());
    actionMenu.presentActions();
  }

  private void presentSaveMenu() {
    boolean didSave = false;
    while (!didSave) {
      SaveMenu saveMenu = new SaveMenu(input, output);
      String filename = saveMenu.askFileName();
      try {
        saveToFile(filename);
        didSave = true;
      } catch (IOException e) {
        MenuUtils.error(output, "Whoops! Failed to save. Try another file name.");
      }
    }
  }

  private void saveToFile(String filename) throws IOException {
    saveManager.saveToFile(filename, characters.values());
  }

  private void presentLoadMenu() {
    LoadMenu loadMenu = new LoadMenu(input, output);
    try {
      File file = loadMenu.askFileName();
      loadFromFile(file);
    } catch (IOException ex) {
      MenuUtils.error(output, "Uh oh! Something went wrong. Try another file?");
    }
  }

  private void loadFromFile(File file) throws IOException {
    if (file == null) return;
    Collection<GameCharacter> characters = saveManager.loadFromFile(file);
    this.characters.clear();
    if (characters != null && characters.size() > 0) {
      for (GameCharacter character : characters) {
        this.characters.put(character.id, character);
        selectCharacter(character.id);
      }
    }
  }

  private void presentSelectMenu() {
    if (characters.isEmpty()) {
      MenuUtils.error(output, "⚠️ No characters to choose from. Try creating one!");
    } else {
      CharacterSelectMenu selectMenu = new CharacterSelectMenu(input, output);
      GameCharacter selectedCharacter =
          selectMenu.askToSelectCharacter(new ArrayList<>(characters.values()));
      selectCharacter(selectedCharacter.id);
    }
  }

  private void presentCreateMenu() {
    CreateMenu createMenu = new CreateMenu(input, output);
    GameCharacter createdCharacter = createMenu.presentCreationMenu();
    output.println(
        String.format(
            "\uD83C\uDF82 Congratulations! \uD83E\uDD73\nYou've created %s, a Level 1 %s. \n%s\n",
            createdCharacter.name,
            createdCharacter.archetype.name, createdCharacter.toAttributeString()));
    this.characters.put(createdCharacter.id, createdCharacter);
    selectCharacter(createdCharacter.id);
  }

  private void selectCharacter(String id) {
    selectedCharacterId = id;
  }

  public GameCharacter getSelectedCharacter() {
    return characters.get(selectedCharacterId);
  }

  private void exit() {
    isGameOn = false;
  }
}
