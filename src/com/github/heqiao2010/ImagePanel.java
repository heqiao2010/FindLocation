package com.github.heqiao2010;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;


public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image img = null;

	public void setImg(Image img) {
		this.img = img;
	}

	public ImagePanel() {
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = getSize();

		if (img != null) {
			g.drawImage(img, 0, 0, size.width, size.height, this);
		}

	}
}
