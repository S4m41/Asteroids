package main;

import java.awt.Graphics;

public abstract interface Drawable
{
  public abstract void draw(Graphics g);
  public abstract boolean isVisible();//bad name. use for scheduling removal
}