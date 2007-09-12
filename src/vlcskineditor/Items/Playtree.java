/*****************************************************************************
 * Playtree.java
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

package vlcskineditor.Items;

import vlcskineditor.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Playtree item
 * @author Daniel Dreibrodt
 */
public class Playtree extends Item implements ActionListener{
  
  public final int WIDTH_DEFAULT = 0;
  public final int HEIGHT_DEFAULT = 0;
  public final String VAR_DEFAULT = "playlist";    
  public final String BGIMAGE_DEFAULT = "none";
  public final String FGCOLOR_DEFAULT = "#000000";
  public final String PLAYCOLOR_DEFAULT = "#FF0000";
  public final String SELCOLOR_DEFAULT = "#0000FF";
  public final String BGCOLOR1_DEFAULT = "#FFFFFF";
  public final String BGCOLOR2_DEFAULT = "#FFFFFF";
  public final boolean FLAT_DEFAULT = false;
  public final String ITEMIMAGE_DEFAULT = "none";
  public final String OPENIMAGE_DEFAULT = "none";
  public final String CLOSEDIMAGE_DEFAULT = "none";
  
  
  public int width = WIDTH_DEFAULT;
  public int height = HEIGHT_DEFAULT;
  public String font;
  public String var = VAR_DEFAULT;
  public String bgimage = BGIMAGE_DEFAULT;
  public String fgcolor = FGCOLOR_DEFAULT;
  public String playcolor = PLAYCOLOR_DEFAULT;
  public String selcolor = SELCOLOR_DEFAULT;
  public String bgcolor1 = BGCOLOR1_DEFAULT;
  public String bgcolor2 = BGCOLOR2_DEFAULT;
  public boolean flat = FLAT_DEFAULT;
  public String itemimage = ITEMIMAGE_DEFAULT;
  public String openimage = OPENIMAGE_DEFAULT;
  public String closedimage = CLOSEDIMAGE_DEFAULT;
  
  Slider slider = null;
  
  JFrame frame = null;
  JTextField id_tf, x_tf, y_tf, help_tf, visible_tf, width_tf, height_tf;
  JTextField font_tf, bgimage_tf, itemimage_tf, openimage_tf, closedimage_tf;
  JTextField fgcolor_tf, playcolor_tf, selcolor_tf, bgcolor1_tf, bgcolor2_tf;
  JComboBox lefttop_cb, rightbottom_cb, xkeepratio_cb, ykeepratio_cb, flat_cb;
  JButton visible_btn, bgcolor1_btn, bgcolor2_btn, fgcolor_btn, playcolor_btn, selcolor_btn, slider_btn, ok_btn, help_btn;
  
  /** Creates a new instance of Playtree */
  public Playtree(String xmlcode, Skin s_) {
    type = "Playtree";
    s = s_;
    String[] xmllines = xmlcode.split("\n");
    if(xmllines[0].startsWith("<Playlist")) flat=true;
    font = XML.getValue(xmllines[0],"font");
    if(xmllines[0].indexOf("width=\"")!=-1) width = XML.getIntValue(xmllines[0],"width");
    if(xmllines[0].indexOf("height=\"")!=-1) height = XML.getIntValue(xmllines[0],"height");
    if(xmllines[0].indexOf("var=\"")!=-1) var = XML.getValue(xmllines[0],"var");
    if(xmllines[0].indexOf("bgimage=\"")!=-1) bgimage = XML.getValue(xmllines[0],"bgimage");
    if(xmllines[0].indexOf("openimage=\"")!=-1) openimage = XML.getValue(xmllines[0],"openimage");
    if(xmllines[0].indexOf("closedimage=\"")!=-1) closedimage = XML.getValue(xmllines[0],"closedimage");
    if(xmllines[0].indexOf("itemimage=\"")!=-1) itemimage = XML.getValue(xmllines[0],"itemimage");
    if(xmllines[0].indexOf("fgcolor=\"")!=-1) fgcolor = XML.getValue(xmllines[0],"fgcolor");
    if(xmllines[0].indexOf("playcolor=\"")!=-1) playcolor = XML.getValue(xmllines[0],"playcolor");
    if(xmllines[0].indexOf("selcolor=\"")!=-1) selcolor = XML.getValue(xmllines[0],"selcolor");
    if(xmllines[0].indexOf("bgcolor1=\"")!=-1) bgcolor1 = XML.getValue(xmllines[0],"bgcolor1");
    if(xmllines[0].indexOf("bgcolor2=\"")!=-1) bgcolor2 = XML.getValue(xmllines[0],"bgcolor2");
    if(xmllines[0].indexOf("flat=\"")!=-1) flat = XML.getBoolValue(xmllines[0],"flat");
    if(xmllines[0].indexOf("x=\"")!=-1) x = XML.getIntValue(xmllines[0],"x");
    if(xmllines[0].indexOf("y=\"")!=-1) y = XML.getIntValue(xmllines[0],"y");
    if(xmllines[0].indexOf("id=\"")!=-1) id = XML.getValue(xmllines[0],"id");
    else id = "Unnamed playtree #"+s.getNewId();
    if(xmllines[0].indexOf("lefttop=\"")!=-1) lefttop = XML.getValue(xmllines[0],"lefttop");
    if(xmllines[0].indexOf("rightbottom=\"")!=-1) rightbottom = XML.getValue(xmllines[0],"rightbottom");
    if(xmllines[0].indexOf("xkeepratio=\"")!=-1) xkeepratio = XML.getBoolValue(xmllines[0],"xkeepratio");
    if(xmllines[0].indexOf("ykeepratio=\"")!=-1) xkeepratio = XML.getBoolValue(xmllines[0],"ykeepratio");
    if(xmlcode.indexOf(" visible=\"")!=-1) visible = XML.getValue(xmlcode,"visible");
    
    int i=1;
    if(xmllines.length>1) {
      if(xmllines[i].startsWith("<Slider")) {
        if(xmllines[i].indexOf("/>")!=-1) {
          slider = new Slider(xmllines[i],s,true);
        }
        else {
          String itemcode = xmllines[i];
          i++;
          while(!xmllines[i].startsWith("</Slider>")) {          
            itemcode += "\n"+xmllines[i];
            i++;
          }
          itemcode += "\n"+xmllines[i];
          slider = new Slider(itemcode,s,true);
        }                
      }
    }
  }
  public Playtree(Skin s_) {
    s = s_;
    font = "defaultfont";
    id = "Unnamed playtree #"+s.getNewId();
    slider = new Slider(s,true);
    showOptions();
    s.updateItems();
    s.expandItem(id);
  }
  public void update()  {
    id = id_tf.getText();
    x = Integer.parseInt(x_tf.getText());
    y = Integer.parseInt(y_tf.getText());
    lefttop = lefttop_cb.getSelectedItem().toString();
    rightbottom = rightbottom_cb.getSelectedItem().toString();
    xkeepratio = Boolean.parseBoolean(xkeepratio_cb.getSelectedItem().toString());
    ykeepratio = Boolean.parseBoolean(ykeepratio_cb.getSelectedItem().toString());
    visible = visible_tf.getText();
    help = help_tf.getText();
    
    width = Integer.parseInt(width_tf.getText());
    height = Integer.parseInt(height_tf.getText());
    font = font_tf.getText();
    bgimage = bgimage_tf.getText();
    itemimage = itemimage_tf.getText();
    openimage = openimage_tf.getText();
    closedimage = closedimage_tf.getText();
    fgcolor = fgcolor_tf.getText();
    selcolor = selcolor_tf.getText();
    playcolor = playcolor_tf.getText();
    bgcolor1 = bgcolor1_tf.getText();
    bgcolor2 = bgcolor2_tf.getText();
    flat = (Boolean)flat_cb.getSelectedItem();
    
    s.updateItems();
    s.expandItem(id);
  }
  public void showOptions() {
    if(frame==null) {
      frame = new JFrame("Playtree settings");
      frame.setResizable(false);
      frame.setLayout(new FlowLayout());
      frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);
      JLabel id_l = new JLabel("ID*:");
      id_tf = new JTextField();      
      JLabel x_l = new JLabel("X:");
      x_tf = new JTextField();      
      x_tf.setDocument(new NumbersOnlyDocument());
      JLabel y_l = new JLabel("Y:");
      y_tf = new JTextField();      
      y_tf.setDocument(new NumbersOnlyDocument());
      String[] align_values = {"lefttop", "leftbottom", "righttop", "rightbottom"};
      JLabel lefttop_l = new JLabel("Lefttop:");
      lefttop_cb = new JComboBox(align_values);
      lefttop_cb.setToolTipText("Indicate to which corner of the Layout the top-left-hand corner of this item is attached, in case of resizing.");
      JLabel rightbottom_l = new JLabel("Rightbottom:");
      rightbottom_cb = new JComboBox(align_values);
      rightbottom_cb.setToolTipText("Indicate to which corner of the Layout the bottom-right-hand corner of this item is attached, in case of resizing.");
      Object[] bool_values = { true, false };
      JLabel xkeepratio_l = new JLabel("Keep X Ratio:");
      xkeepratio_cb = new JComboBox(bool_values);
      xkeepratio_cb.setToolTipText("When set to true, the behaviour of the horizontal resizing is changed. For example, if initially the space to the left of the control is twice as big as the one to its right, this will stay the same during any horizontal resizing. The width of the control stays constant.");
      JLabel ykeepratio_l = new JLabel("Keep Y Ratio:");
      ykeepratio_cb = new JComboBox(bool_values);
      ykeepratio_cb.setToolTipText("When set to true, the behaviour of the vertical resizing is changed. For example, if initially the space to the top of the control is twice as big as the one to its bottom, this will stay the same during any vertical resizing. The height of the control stays constant.");
      JLabel visible_l = new JLabel("Visibility:");
      visible_tf = new JTextField();
      visible_btn = new JButton("",s.m.help_icon);
      visible_btn.addActionListener(this);      
      JLabel help_l = new JLabel("Help Text:");
      help_tf = new JTextField();
      help_tf.setToolTipText("Help text for the current control. The variable '$H' will be expanded to this value when the mouse hovers the current control.");
      
      JLabel width_l = new JLabel("Width:");
      width_tf = new JTextField();
      width_tf.setDocument(new NumbersOnlyDocument(false));
      width_tf.setToolTipText("Width of the playlist, in pixels. If playlist items are wider, the end of the name will be replaced with '...'");
      JLabel height_l = new JLabel("Height:");
      height_tf = new JTextField();
      height_tf.setDocument(new NumbersOnlyDocument(false));
      JLabel font_l = new JLabel("Font*:");
      font_tf = new JTextField();
      JLabel bgimage_l = new JLabel("BG Image:");
      bgimage_tf = new JTextField();
      bgimage_tf.setToolTipText("ID of a Bitmap, used as the background image. When no bitmap is specified, the background will be filled using the bgcolor1 and bgcolor2 attributes.");
      JLabel bgcolor1_l = new JLabel("BG color #1:");
      bgcolor1_tf = new JTextField();
      bgcolor1_tf.setToolTipText("Background color for odd playlist items. This attribute is ignored if the bgimage one is used.");
      bgcolor1_btn = new JButton("Choose...");
      bgcolor1_btn.addActionListener(this);
      JLabel bgcolor2_l = new JLabel("BG color #2:");
      bgcolor2_tf = new JTextField();
      bgcolor2_tf.setToolTipText("Background color for even playlist items. This attribute is ignored if the bgimage one is used.");
      bgcolor2_btn = new JButton("Choose...");
      bgcolor2_btn.addActionListener(this);      
      JLabel selcolor_l = new JLabel("Selection color:");
      selcolor_tf = new JTextField();
      selcolor_btn = new JButton("Choose...");
      selcolor_btn.addActionListener(this);
      JLabel fgcolor_l = new JLabel("Text color:");
      fgcolor_tf = new JTextField();
      fgcolor_btn = new JButton("Choose...");
      fgcolor_btn.addActionListener(this);
      JLabel playcolor_l = new JLabel("Now playing text color:");
      playcolor_tf = new JTextField();
      playcolor_btn = new JButton("Choose...");
      playcolor_btn.addActionListener(this);
      JLabel flat_l = new JLabel("Flat playlist:");      
      flat_cb = new JComboBox(bool_values);
      flat_cb.setToolTipText("Boolean to indicate whether the playlist should use the tree structure or be completely \"flat\" (only show the leafs of the tree).");
      JLabel itemimage_l = new JLabel("Item icon:");
      itemimage_tf = new JTextField();
      JLabel openimage_l = new JLabel("Open folder icon:");
      openimage_tf = new JTextField();
      JLabel closedimage_l = new JLabel("Closed folder icon:");
      closedimage_tf = new JTextField();
      slider_btn = new JButton("Edit Playlist's slider (scrollbar)");
      slider_btn.addActionListener(this);
      ok_btn = new JButton("OK");
      ok_btn.addActionListener(this);
      help_btn = new JButton("Help");
      help_btn.addActionListener(this);
      
      JPanel general = new JPanel(null);
      general.add(id_l);
      general.add(id_tf);
      id_l.setBounds(5,15,75,24);
      id_tf.setBounds(85,15,150,24);
      general.add(x_l);
      general.add(x_tf);
      x_l.setBounds(5,45,75,24);
      x_tf.setBounds(85,45,150,24);
      general.add(y_l);
      general.add(y_tf);
      y_l.setBounds(5,75,75,24);
      y_tf.setBounds(85,75,75,24);      
      general.add(lefttop_l);
      general.add(lefttop_cb);
      lefttop_l.setBounds(5,105,75,24);
      lefttop_cb.setBounds(85,105,150,24);
      general.add(rightbottom_l);
      general.add(rightbottom_cb);
      rightbottom_l.setBounds(5,135,75,24);
      rightbottom_cb.setBounds(85,135,150,24);
      general.add(xkeepratio_l);
      general.add(xkeepratio_cb);
      xkeepratio_l.setBounds(5,165,75,24);
      xkeepratio_cb.setBounds(85,165,150,24);
      general.add(ykeepratio_l);
      general.add(ykeepratio_cb);
      ykeepratio_l.setBounds(5,195,75,24);
      ykeepratio_cb.setBounds(85,195,150,24);
      general.add(visible_l);
      general.add(visible_tf);
      general.add(visible_btn);
      visible_l.setBounds(5,225,75,24);
      visible_tf.setBounds(85,225,120,24);
      visible_btn.setBounds(210,225,24,24);
      general.add(help_l);
      general.add(help_tf);
      help_l.setBounds(5,255,75,24);
      help_tf.setBounds(85,255,150,24);      
      general.add(width_l);
      general.add(width_tf);
      width_l.setBounds(5,285,75,24);
      width_tf.setBounds(85,285,150,24);
      general.add(height_l);
      general.add(height_tf);
      height_l.setBounds(5,315,75,24);
      height_tf.setBounds(85,315,150,24);
      general.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), "General Attributes"));
      general.setMinimumSize(new Dimension(240,345));
      general.setPreferredSize(new Dimension(240,345));
      general.setMaximumSize(new Dimension(240,345));
      frame.add(general);
      
      JPanel ptp = new JPanel(null);
      ptp.add(font_l);
      ptp.add(font_tf);
      font_l.setBounds(5,15,75,24);
      font_tf.setBounds(85,15,150,24);
      ptp.add(bgimage_l);
      ptp.add(bgimage_tf);
      bgimage_l.setBounds(5,45,75,24);
      bgimage_tf.setBounds(85,45,150,24);
      ptp.add(bgcolor1_l);
      ptp.add(bgcolor1_tf);
      ptp.add(bgcolor1_btn);
      bgcolor1_l.setBounds(5,75,75,24);
      bgcolor1_tf.setBounds(85,75,75,24);
      bgcolor1_btn.setBounds(165,75,70,24);
      ptp.add(bgcolor2_l);
      ptp.add(bgcolor2_tf);
      ptp.add(bgcolor2_btn);
      bgcolor2_l.setBounds(5,105,75,24);
      bgcolor2_tf.setBounds(85,105,75,24);
      bgcolor2_btn.setBounds(165,105,70,24);
      ptp.add(selcolor_l);
      ptp.add(selcolor_tf);
      ptp.add(selcolor_btn);
      selcolor_l.setBounds(5,135,75,24);
      selcolor_tf.setBounds(85,135,75,24);
      selcolor_btn.setBounds(165,135,70,24);
      ptp.add(fgcolor_l);
      ptp.add(fgcolor_tf);
      ptp.add(fgcolor_btn);
      fgcolor_l.setBounds(5,165,75,24);
      fgcolor_tf.setBounds(85,165,75,24);
      fgcolor_btn.setBounds(165,165,70,24);      
      ptp.add(playcolor_l);
      ptp.add(playcolor_tf);
      ptp.add(playcolor_btn);
      playcolor_l.setBounds(5,195,75,24);
      playcolor_tf.setBounds(85,195,75,24);
      playcolor_btn.setBounds(165,195,70,24);
      ptp.add(flat_l);
      ptp.add(flat_cb);
      flat_l.setBounds(5,225,75,24);
      flat_cb.setBounds(85,225,150,24);
      ptp.add(itemimage_l);
      ptp.add(itemimage_tf);
      itemimage_l.setBounds(5,255,75,24);
      itemimage_tf.setBounds(85,255,150,24);
      ptp.add(openimage_l);
      ptp.add(openimage_tf);
      openimage_l.setBounds(5,285,75,24);
      openimage_tf.setBounds(85,285,150,24);
      ptp.add(closedimage_l);
      ptp.add(closedimage_tf);
      closedimage_l.setBounds(5,315,75,24);
      closedimage_tf.setBounds(85,315,150,24);      
      ptp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), "Playtree Attributes"));
      ptp.setMinimumSize(new Dimension(240,3455));
      ptp.setPreferredSize(new Dimension(240,345));
      ptp.setMaximumSize(new Dimension(240,345));
      frame.add(ptp);
      
      frame.add(slider_btn);
      slider_btn.setPreferredSize(new Dimension(490,24));
      slider_btn.setMinimumSize(new Dimension(490,24));
      slider_btn.setMaximumSize(new Dimension(490,24));
      
      frame.add(ok_btn);
      frame.add(help_btn);      
      frame.add(new JLabel("* required attribute"));
      
      frame.setMinimumSize(new Dimension(500,470));
      frame.setPreferredSize(new Dimension(500,470));
      frame.setMaximumSize(new Dimension(500,470));
      
      frame.pack();
    }
    id_tf.setText(id);    
    x_tf.setText(String.valueOf(x));
    y_tf.setText(String.valueOf(y));
    lefttop_cb.setSelectedItem(lefttop);
    rightbottom_cb.setSelectedItem(rightbottom);
    xkeepratio_cb.setSelectedItem(xkeepratio);
    ykeepratio_cb.setSelectedItem(ykeepratio);
    visible_tf.setText(visible);
    help_tf.setText(help);
    
    width_tf.setText(String.valueOf(width));
    height_tf.setText(String.valueOf(height));
    font_tf.setText(font);
    bgimage_tf.setText(bgimage);
    fgcolor_tf.setText(fgcolor);
    selcolor_tf.setText(selcolor);
    playcolor_tf.setText(playcolor);
    bgcolor1_tf.setText(bgcolor1);
    bgcolor2_tf.setText(bgcolor2);
    flat_cb.setSelectedItem(flat);
    openimage_tf.setText(openimage);
    closedimage_tf.setText(closedimage);
    itemimage_tf.setText(itemimage);
    
    frame.setVisible(true);
  }
  public void actionPerformed(ActionEvent e) {
    if(e.getSource().equals(ok_btn)) {
      if(id_tf.getText().equals("")) {
        JOptionPane.showMessageDialog(frame,"Please enter a valid ID!","ID not valid",JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      else if(!id_tf.getText().equals(id)) {
        if(s.idExists(id_tf.getText())) {
          JOptionPane.showMessageDialog(frame,"The ID \""+id_tf.getText()+"\" already exists, please choose another one.","ID not valid",JOptionPane.INFORMATION_MESSAGE);
          return;
        }
      }
      if(!bgimage_tf.getText().equals("none")&&s.getResource(bgimage_tf.getText())==null) {        
        JOptionPane.showMessageDialog(frame,"Please choose an existing background image!","Bgimage does not exist",JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      if(!itemimage_tf.getText().equals("none")&&s.getResource(itemimage_tf.getText())==null) {        
        JOptionPane.showMessageDialog(frame,"Please choose an existing item icon!","Itemimage does not exist",JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      if(!openimage_tf.getText().equals("none")&&s.getResource(openimage_tf.getText())==null) {        
        JOptionPane.showMessageDialog(frame,"Please choose an existing open folder icon!","Openimage does not exist",JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      if(!closedimage_tf.getText().equals("none")&&s.getResource(closedimage_tf.getText())==null) {        
        JOptionPane.showMessageDialog(frame,"Please choose an existing closed folder icon!","Closedimage does not exist",JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      if(!font_tf.getText().equals("defaultfont")&&s.getResource(font_tf.getText())==null) {        
        JOptionPane.showMessageDialog(frame,"Please choose an existing font!","Font does not exist",JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      update();
      frame.setVisible(false);           
    }
    else if (e.getSource().equals(bgcolor1_btn)) {
      Color color = JColorChooser.showDialog(frame,"Choose Color",Color.decode(bgcolor1_tf.getText()));    
      if (color != null) {
        String hex = "#";
        if(color.getRed()<16) hex+="0";
        hex+=Integer.toHexString(color.getRed()).toUpperCase();
        if(color.getGreen()<16) hex+="0";
        hex+=Integer.toHexString(color.getGreen()).toUpperCase();
        if(color.getBlue()<16) hex+="0";
        hex+=Integer.toHexString(color.getBlue()).toUpperCase();
        bgcolor1_tf.setText(hex);
      }
    }
    else if (e.getSource().equals(bgcolor2_btn)) {
      Color color = JColorChooser.showDialog(frame,"Choose Color",Color.decode(bgcolor2_tf.getText()));    
      if (color != null) {
        String hex = "#";
        if(color.getRed()<16) hex+="0";
        hex+=Integer.toHexString(color.getRed()).toUpperCase();
        if(color.getGreen()<16) hex+="0";
        hex+=Integer.toHexString(color.getGreen()).toUpperCase();
        if(color.getBlue()<16) hex+="0";
        hex+=Integer.toHexString(color.getBlue()).toUpperCase();
        bgcolor2_tf.setText(hex);
      }
    }
    else if (e.getSource().equals(fgcolor_btn)) {
      Color color = JColorChooser.showDialog(frame,"Choose Color",Color.decode(fgcolor_tf.getText()));    
      if (color != null) {
        String hex = "#";
        if(color.getRed()<16) hex+="0";
        hex+=Integer.toHexString(color.getRed()).toUpperCase();
        if(color.getGreen()<16) hex+="0";
        hex+=Integer.toHexString(color.getGreen()).toUpperCase();
        if(color.getBlue()<16) hex+="0";
        hex+=Integer.toHexString(color.getBlue()).toUpperCase();
        fgcolor_tf.setText(hex);
      }
    }
    else if (e.getSource().equals(selcolor_btn)) {
      Color color = JColorChooser.showDialog(frame,"Choose Color",Color.decode(selcolor_tf.getText()));    
      if (color != null) {
        String hex = "#";
        if(color.getRed()<16) hex+="0";
        hex+=Integer.toHexString(color.getRed()).toUpperCase();
        if(color.getGreen()<16) hex+="0";
        hex+=Integer.toHexString(color.getGreen()).toUpperCase();
        if(color.getBlue()<16) hex+="0";
        hex+=Integer.toHexString(color.getBlue()).toUpperCase();
        selcolor_tf.setText(hex);
      }
    }
    else if (e.getSource().equals(playcolor_btn)) {
      Color color = JColorChooser.showDialog(frame,"Choose Color",Color.decode(playcolor_tf.getText()));    
      if (color != null) {
        String hex = "#";
        if(color.getRed()<16) hex+="0";
        hex+=Integer.toHexString(color.getRed()).toUpperCase();
        if(color.getGreen()<16) hex+="0";
        hex+=Integer.toHexString(color.getGreen()).toUpperCase();
        if(color.getBlue()<16) hex+="0";
        hex+=Integer.toHexString(color.getBlue()).toUpperCase();
        playcolor_tf.setText(hex);
      }
    }
    else if(e.getSource().equals(slider_btn)){
      //frame.setEnabled(false);
      slider.showOptions();
    }
    else if(e.getSource().equals(help_btn)) {
      Desktop desktop;
      if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
              desktop.browse(new java.net.URI("http://www.videolan.org/vlc/skins2-create.html#Playtree"));
            }
            catch (Exception ex) {
              JOptionPane.showMessageDialog(null,ex.toString(),ex.getMessage(),JOptionPane.ERROR_MESSAGE);    
            }
      }
      else JOptionPane.showMessageDialog(null,"Could not launch Browser","Go to the following URL manually:\nhttp://www.videolan.org/vlc/skins2-create.html",JOptionPane.WARNING_MESSAGE);
    }
    else if(e.getSource().equals(visible_btn)) {
      Desktop desktop;
      if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
              desktop.browse(new java.net.URI("http://www.videolan.org/vlc/skins2-create.html#boolexpr"));
            }
            catch (Exception ex) {
              JOptionPane.showMessageDialog(null,ex.toString(),ex.getMessage(),JOptionPane.ERROR_MESSAGE);    
            }
      }
      else {
        JOptionPane.showMessageDialog(null,"Could not launch Browser","Go to the following URL manually:\nhttp://www.videolan.org/vlc/skins2-create.html",JOptionPane.WARNING_MESSAGE);    
      }
    }
  }
  public String returnCode() {
    String code = "<Playtree";
    if (id!=ID_DEFAULT) code+=" id=\""+id+"\"";
    code+=" font=\""+font+"\"";
    if(bgcolor1!=BGCOLOR1_DEFAULT) code+=" bgcolor1=\""+bgcolor1+"\"";
    if(bgcolor2!=BGCOLOR2_DEFAULT) code+=" bgcolor2=\""+bgcolor2+"\"";
    if(fgcolor!=FGCOLOR_DEFAULT) code+=" fgcolor=\""+fgcolor+"\"";
    if(selcolor!=SELCOLOR_DEFAULT) code+=" selcolor=\""+selcolor+"\"";
    if(playcolor!=PLAYCOLOR_DEFAULT) code+=" playcolor=\""+playcolor+"\"";
    if(bgimage!=BGIMAGE_DEFAULT) code+=" bgimage=\""+bgimage+"\"";
    if(itemimage!=ITEMIMAGE_DEFAULT) code+=" itemimage=\""+itemimage+"\"";
    if(openimage!=OPENIMAGE_DEFAULT) code+=" openimage=\""+openimage+"\"";
    if(closedimage!=CLOSEDIMAGE_DEFAULT) code+=" closedimage=\""+closedimage+"\"";
    if(var!=VAR_DEFAULT) code+=" var=\""+var+"\"";
    if(flat!=FLAT_DEFAULT) code+=" flat=\""+String.valueOf(flat)+"\"";
    if (x!=X_DEFAULT) code+=" x=\""+String.valueOf(x)+"\"";
    if (y!=Y_DEFAULT) code+=" y=\""+String.valueOf(y)+"\"";
    if (width!=WIDTH_DEFAULT) code+=" width=\""+String.valueOf(width)+"\"";
    if (height!=HEIGHT_DEFAULT) code+=" height=\""+String.valueOf(height)+"\"";
    if (lefttop!=LEFTTOP_DEFAULT) code+=" lefttop=\""+lefttop+"\"";
    if (rightbottom!=RIGHTBOTTOM_DEFAULT) code+=" rightbottom=\""+rightbottom+"\"";
    if (xkeepratio!=XKEEPRATIO_DEFAULT) code+=" xkeepratio=\""+String.valueOf(xkeepratio)+"\"";
    if (ykeepratio!=YKEEPRATIO_DEFAULT) code+=" ykeepratio=\""+String.valueOf(ykeepratio)+"\"";
    if (help!=HELP_DEFAULT) code+=" help=\""+help+"\"";
    if (visible!=VISIBLE_DEFAULT) code+=" visible=\""+visible+"\"";
    code+=">\n";    
    code+=slider.returnCode();
    code+="\n</Playtree>";
    return code;
  }
  public void draw(Graphics2D g) {
    draw(g,0,0);
  }
  public void draw(Graphics2D g, int x_, int y_) {
    if(s.gvars.parseBoolean(visible)==false) return;
    Font f = s.getFont(font);
    g.setFont(f);
    FontMetrics fm = g.getFontMetrics();
    if(!bgimage.equals("none")) {
      g.drawImage(s.getBitmapImage(bgimage),x+x_,y+y_,null);
    }
    else {      
      g.setColor(Color.decode(bgcolor1));
      g.fillRect(x+x_,y+y_,width,height);
      for(int i=fm.getHeight();i<height;i=i+fm.getHeight()*2) {
        g.setColor(Color.decode(bgcolor2));
        g.fillRect(x,y+y_+i,width,fm.getHeight());
      }
    }
    int liney = y+y_;    
    BufferedImage cfi = s.getBitmapImage(closedimage);
    BufferedImage ofi = s.getBitmapImage(openimage);
    BufferedImage iti = s.getBitmapImage(itemimage);
    int lineheight = fm.getHeight();    
    int cfi_offset=0,ofi_offset=0,iti_offset=0;
    if(cfi!=null) {
      if(cfi.getHeight()>lineheight) lineheight=cfi.getHeight();
       cfi_offset = (lineheight-cfi.getHeight())/2;
    }
    if(ofi!=null) {
      if(ofi.getHeight()>lineheight) lineheight=ofi.getHeight();
      ofi_offset = (lineheight-ofi.getHeight())/2;
    }
    if(iti!=null) {
      if(iti.getHeight()>lineheight) lineheight=iti.getHeight();
      iti_offset = (lineheight-iti.getHeight())/2; 
    }
    int text_offset = (lineheight-fm.getAscent())/2;      
    
    g.setColor(Color.decode(fgcolor));
    if(cfi!=null && !flat) {        
      g.drawImage(cfi,x+x_,liney+cfi_offset,null);
      liney+=lineheight;
      g.drawString("Closed folder",x+x_+cfi.getWidth()+2,liney-text_offset);          
    }
    if(ofi!=null && !flat) {
      g.drawImage(ofi,x+x_,liney+ofi_offset,null);
      liney+=lineheight;
      g.drawString("Open folder",x+x_+ofi.getWidth()+2,liney-text_offset);          
    }
    if(ofi!=null && iti!=null && !flat) {
      g.drawImage(iti,x+x_+ofi.getWidth()+2,liney+iti_offset,null);
      liney+=lineheight;
      g.drawString("Normal item",x+x_+ofi.getWidth()+iti.getWidth()+4,liney-text_offset);
    }
    else if(iti!=null) {
      g.drawImage(iti,x+x_,liney+iti_offset,null);
      liney+=fm.getHeight();
      g.drawString("Normal item",x+x_+iti.getWidth()+4,liney-text_offset);
    }
    else {
      liney+=fm.getHeight();
      g.drawString("Normal item",x+x_,liney-text_offset);
    }
    g.setColor(Color.decode(playcolor));
    if(ofi!=null && iti!=null && !flat) {
      g.drawImage(iti,x+x_+ofi.getWidth()+2,liney+iti_offset,null);
      liney+=lineheight;
      g.drawString("Playing item",x+x_+ofi.getWidth()+iti.getWidth()+4,liney-text_offset);
    }
    else if(iti!=null) {
      g.drawImage(iti,x+x_,liney+iti_offset,null);
      liney+=lineheight;
      g.drawString("Playing item",x+x_+iti.getWidth()+2,liney-text_offset);
    }
    else {
      liney+=lineheight;
      g.drawString("Playing item",x+x_,liney-text_offset);
    }
    g.setColor(Color.decode(selcolor));
    g.fillRect(x+x_,liney,width,lineheight);
    g.setColor(Color.decode(fgcolor));
    if(ofi!=null && iti!=null && !flat) {
      g.drawImage(iti,x+x_+ofi.getWidth()+2,liney+iti_offset,null);
      liney+=lineheight;
      g.drawString("Selected item",x+x_+ofi.getWidth()+iti.getWidth()+4,liney-text_offset);
    }
    else if(iti!=null) {
      g.drawImage(iti,x+x_,liney+iti_offset,null);
      liney+=lineheight;
      g.drawString("Selected item",x+x_+iti.getWidth()+2,liney-text_offset);
    }
    else {
      liney+=lineheight;
      g.drawString("Selected item",x+x_,liney-text_offset);
    }
    
    slider.draw(g);
    
    if(selected) {
      g.setColor(Color.RED);
      g.drawRect(x+x_,y+y_,width,height);
    }
  }
  public boolean contains (int x_, int y_) {
    return (x_>=x+offsetx && x_<=x+width+offsetx && y_>=y+offsety && y_<=y+height+offsety);
  }
  public DefaultMutableTreeNode getTreeNode() {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode("Playtree: "+id); 
    node.add(slider.getTreeNode());
    return node;
  }
  public Item getItem(String id_) {
    if(id.equals(id_)) return this;
    else return slider.getItem(id_);
  }
  public Item getParentOf(String id_) {
   if(slider!=null) {
     if(slider.id.equals(id_)) return this;
     else return slider.getParentOf(id_);
   }
   else return null;
  }
}
