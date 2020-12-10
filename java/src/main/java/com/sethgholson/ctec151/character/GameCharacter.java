package com.sethgholson.ctec151.character;

import com.sethgholson.ctec151.ActionResult;
import java.util.Random;
import java.util.UUID;

public class GameCharacter {

  public final String id;
  public final String name;
  public final Archetype archetype;
  public final int attackDamage;
  public final int physicalDefense;
  public final int magicalDefense;
  public final int accuracy;

  private GameCharacter(Builder builder) {
    super();
    this.id = builder.id;
    this.name = builder.name;
    this.archetype = builder.archetype;
    this.attackDamage = builder.attackDamage;
    this.physicalDefense = builder.physicalDefense;
    this.magicalDefense = builder.magicalDefense;
    this.accuracy = builder.accuracy;
  }

  public ActionResult rollAttack() {
    int roll = new Random().nextInt(10);
    return new ActionResult(roll, attackDamage, roll * attackDamage);
  }

  public ActionResult rollDamage() {
    int roll = new Random().nextInt(10);
    return new ActionResult(roll, physicalDefense, Math.max(0, roll - physicalDefense));
  }

  @Override public String toString() {
    return String.format("%s (%s)", name, archetype.name);
  }

  public String toAttributeString() {
    return Attribute.ATTACK_DAMAGE.description + " = " + attackDamage + "\n" +
        Attribute.PHYSICAL_DEFENSE.description + " = " + physicalDefense + "\n" +
        Attribute.MAGICAL_DEFENSE.description + " = " + magicalDefense + "\n" +
        Attribute.ACCURACY.description + " = " + accuracy;
  }

  public enum Attribute {
    ATTACK_DAMAGE("Attack Damage"),
    PHYSICAL_DEFENSE("Physical Defense"),
    MAGICAL_DEFENSE("Magical Defense"),
    ACCURACY("Accuracy");

    public final String description;

    Attribute(String description) {
      this.description = description;
    }
  }

  public static class Builder {
    private String id = UUID.randomUUID().toString();
    private String name;
    private Archetype archetype;
    private int attackDamage;
    private int physicalDefense;
    private int magicalDefense;
    private int accuracy;

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder archetype(Archetype archetype) {
      this.archetype = archetype;
      return this;
    }

    public Builder attackDamage(int attackDamage) {
      this.attackDamage = attackDamage;
      return this;
    }

    public Builder physicalDefense(int physicalDefense) {
      this.physicalDefense = physicalDefense;
      return this;
    }

    public Builder magicalDefense(int magicalDefense) {
      this.magicalDefense = magicalDefense;
      return this;
    }

    public Builder accuracy(int accuracy) {
      this.accuracy = accuracy;
      return this;
    }

    public Builder fromPrototype(GameCharacter prototype) {
      id = prototype.id;
      name = prototype.name;
      archetype = prototype.archetype;
      attackDamage = prototype.attackDamage;
      physicalDefense = prototype.physicalDefense;
      magicalDefense = prototype.magicalDefense;
      accuracy = prototype.accuracy;
      return this;
    }

    public GameCharacter build() {
      return new GameCharacter(this);
    }

    public void setAttribute(Attribute selectedAttribute, int value) {
      switch (selectedAttribute) {
        case ATTACK_DAMAGE:
          attackDamage(value);
          break;
        case PHYSICAL_DEFENSE:
          physicalDefense(value);
          break;
        case MAGICAL_DEFENSE:
          magicalDefense(value);
          break;
        case ACCURACY:
          accuracy(value);
          break;
      }
    }
  }
}

