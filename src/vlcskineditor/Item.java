/*****************************************************************************
 * Item.java
 *****************************************************************************
 * Copyright (C) 2007 Daniel Dreibrodt
 *
 * This file is part of VLC Skin Editor
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/

package vlcskineditor;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Abstract superclass representing layout elements
 * @author Daniel Dreibrodt
 */
public abstract class Item {
  /** Represents the skin to which an item belongs */
  public Skin s;  
  
  /* Common attributes */
  
  public final String ID_DEFAULT = "Unnamed";
  public final String VISIBLE_DEFAULT = "true";
  public final int X_DEFAULT = 0;
  public final int Y_DEFAULT = 0;
  public final String LEFTTOP_DEFAULT = "lefttop";
  public final String RIGHTBOTTOM_DEFAULT = "lefttop";
  public final boolean XKEEPRATIO_DEFAULT = false;
  public final boolean YKEEPRATIO_DEFAULT = false;
  public final String HELP_DEFAULT = "";
  
  public String id = ID_DEFAULT;
  public String visible = VISIBLE_DEFAULT;
  public int x = X_DEFAULT;
  public int y = Y_DEFAULT;
  public String lefttop = LEFTTOP_DEFAULT;
  public String rightbottom = RIGHTBOTTOM_DEFAULT;
  public boolean xkeepratio = XKEEPRATIO_DEFAULT;
  public boolean ykeepratio = YKEEPRATIO_DEFAULT;
  public String help = HELP_DEFAULT;
  public String type = "undefined";
  
  public boolean selected = false;
  public boolean hovered = false;
  public boolean clicked = false;
  
  public int offsetx = 0;
  public int offsety = 0;
  
  /** Creates a new instance of Item */
  public Item() {
  }
  /** Show a dialog to modify the items's parameters */
  public abstract void showOptions();  
  /** Creates the XML code representing the item */
  public abstract String returnCode();
  /** Draws the item to a graphics context */
  public abstract void draw(Graphics2D g);
   /** Draws the item to a graphics context with the offset x,y */
  public abstract void draw(Graphics2D g, int x, int y);
  /** Creates a DefaultMutableTreeNode to be displayed in the items tree */
  public abstract DefaultMutableTreeNode getTreeNode(); 
  /** If the given id is that of this item or one of the item's contained items the fitting item will be returned, else null **/
  public Item getItem(String id_) {
    if(id.equals(id_)) return this;
    else return null;
  }
  /** If an item contains a subitem of the given id the list containing the subitem is returned **/
  public java.util.List<Item> getParentListOf(String id_) {
    return null;
  }
  /** If an item contains a subitem of the given id the itemis returned **/
  public Item getParentOf(String id_) {
    return null;
  }
  /** Sets whether the item is selected in the tree or not **/
  public void setSelected(boolean s) {
    selected = s;
  }
  /** Sets whether the item is hovered by the mouse in the tree or not **/
  public void setHover(boolean h) {
    hovered = h;
  }
  /** Sets whether the item is clicked in the tree or not **/
  public void setClicked(boolean c) {
    clicked = c;
  }
  /** Checks whether the given coordinate is inside the item **/
  public boolean contains(int x_, int y_) {
    return (x==x_ && y==y_);   
  }
  /** Set offset **/
  public void setOffset(int x_, int y_) {
    offsetx=x_;
    offsety=y_;
  }
  
}
