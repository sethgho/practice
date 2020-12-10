package com.sethgholson.ctec151;

public class ActionResult {
  public final int roll;
  public final int modifier;
  public final int total;

  public ActionResult(int roll, int mitigation, int totalDamage) {
    this.roll = roll;
    this.modifier = mitigation;
    this.total = totalDamage;
  }
}
