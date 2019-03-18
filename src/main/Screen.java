package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Screen extends JPanel {

	//ArrayList<? extends Drawable> drawlist;
	ArrayList<Drawable> drawlist;

	private static final long serialVersionUID = -8146038385089830195L;
	Graphics buffergraphics;
	Image osi;
	Dimension dim;

	public void init(Dimension rootSize) {
		dim = rootSize;
		osi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		buffergraphics = osi.getGraphics();
		drawlist = new ArrayList<Drawable>();
	}

	public void paint(Graphics g) {
		buffergraphics.clearRect(0, 0, dim.width, dim.width);
		buffergraphics.setColor(Color.red);
		// buffergraphics.drawString("Bad Double-buffered", 10, 10);
		
		ArrayList<Drawable> deadlist = new ArrayList<Drawable>();//do this somewhere else. or is this only pos where o(x) is kept
		
		for (Drawable d : drawlist) {
			if (d.isVisible()) {
				d.draw(buffergraphics);
			} else {
				deadlist.add(d);
			}
		}
		drawlist.removeAll(deadlist);
		deadlist.clear();
		
		g.drawImage(osi, 0, 0, null);
	}

	// O(x)
	public void updatedrawlist(ArrayList<Drawable> newDrawableList) {
		drawlist.clear();
		drawlist = newDrawableList;
	}
	public void addDrawable(Drawable newDrawable) {
		drawlist.add(newDrawable);
	}
	/*<t extends Drawable> void addDrawable( t newDrawable) {
		drawlist.add(newDrawable);
	}*/
}
