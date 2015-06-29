package com.github.heqiao2010;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class PicturePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6995242827835172676L;
	private Image image;

	public PicturePanel() {
	}

	public void setPath(String picurl) {
		byte[] ret = HttpHelper.sendGetRetByte(picurl);
		System.out.println("image:" + picurl);
		ImageIcon icon = new ImageIcon(ret);
		this.image = icon.getImage();
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 画布上显示图片
		g.drawImage(image, 0, 0, 400, 300, this);
	}
	
	
}
