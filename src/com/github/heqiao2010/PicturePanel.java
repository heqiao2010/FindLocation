package com.github.heqiao2010;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;

/**
 * 展示图片的Panel
 * @author heqiaoa
 *
 */
class PicturePanel extends ImagePanel {

	private static final long serialVersionUID = 6995242827835172676L;
	// 默认圆的直径的像素
	private int ovalSize = 0;
	// 画图颜色
	private Color color = new Color(0, 0, 0);// 黑滴
	// 画笔样式
	private BasicStroke stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND);

	private Image img = null;

	public PicturePanel(MouseListener lis) {
		this.addMouseListener(lis);
	}

	/**
	 * 在图片更新时，重绘
	 * @param picurl
	 * @throws BusinessException
	 */
	public void setPath(String picurl) throws BusinessException {
		byte[] ret = HttpHelper.sendGetRetByte(picurl);
		Logger.info("image:" + picurl);
		ImageIcon icon = new ImageIcon(ret);
		setImg(icon.getImage());
		//重绘
		repaint(); 
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	/**
	 * 在图片中心画一个直径为ovalSize的圈
	 */
	@Override
	public void paintComponent(Graphics g) {
		if(null == g){
			return ;
		}
		super.paintComponent(g);
		Dimension size = getSize();
		if (img != null) {
			g.drawImage(img, 0, 0, size.width, size.height, this);
			// 转换为 Graphics2D
			Graphics2D g2d = (Graphics2D) g;
			// 图形抗锯齿
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// 设置绘图颜色
			g2d.setColor(color);
			// 设置画笔样式
			g2d.setStroke(stroke);
			//画圈，圈直径小于5不绘画
			if (ovalSize > 5 /*&& size.width > ovalSize && size.height > ovalSize*/) {
				g.drawOval((size.width - ovalSize) / 2,
						(size.height - ovalSize) / 2, ovalSize, ovalSize);
			}
		}
	}

	public int getOvalSize() {
		return ovalSize;
	}
	
	/**
	 * 设置圆的直径时，重绘
	 * @param ovalsize
	 */
	public void setOvalSize(int ovalsize) {
		ovalSize = ovalsize;
		//重绘
		paintComponent(this.getGraphics());
	}
}

