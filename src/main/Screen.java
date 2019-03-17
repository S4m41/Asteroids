package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel{
	
	ArrayList<Drawable> drawlist;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8146038385089830195L;
	 Graphics buffergraphics;
     Image osi;
     Dimension dim;

     public void init(Dimension rootSize) {
         dim = rootSize;
         osi = new BufferedImage(500, 500,BufferedImage.TYPE_INT_ARGB);
         buffergraphics = osi.getGraphics();
         drawlist = new ArrayList<Drawable>();
     }
     
     public void paint(Graphics g) {
         buffergraphics.clearRect(0, 0, dim.width, dim.width);
         buffergraphics.setColor(Color.red);
         buffergraphics.drawString("Bad Double-buffered", 10, 10);
         paintstuff(buffergraphics);
         g.drawImage(osi, 0, 0, null);
     }
     
     //O(x)
     private void paintstuff(Graphics buffergraphics) {
    	 for(Drawable d: drawlist) {
    		 d.draw(buffergraphics);
    	 }
    	 drawlist.clear();
    	 //drawlist.dispose();
         //throw new UnsupportedOperationException("Must be overridden"); 
     }
     //O(x)
     public void updatedrawlist(ArrayList<Drawable> newDrawable) {
    	 drawlist=newDrawable;
     }
}
