package com.github.heqiao2010;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class MyComboBox extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1642702729115910117L;
	private AutoCompleter completer = null;
	private PicturePanel imagePanel = null;

	public PicturePanel getImagePanel() {
		return imagePanel;
	}

	public void setImagePanel(PicturePanel imagePanel) {
		this.imagePanel = imagePanel;
	}

	public AutoCompleter getCompleter() {
		return completer;
	}

	public void setCompleter(AutoCompleter completer) {
		this.completer = completer;
	}

	public MyComboBox() {
		super();
		addCompleter();
	}

	public MyComboBox(ComboBoxModel cm) {
		super(cm);
		addCompleter();
	}

	public MyComboBox(Object[] items) {
		super(items);
		addCompleter();
	}

	private void addCompleter() {
		setEditable(true);
		setCompleter(new AutoCompleter(this));
	}

	public String getText() {
		return ((JTextField) getEditor().getEditorComponent()).getText();
	}

	public void setText(String text) {
		((JTextField) getEditor().getEditorComponent()).setText(text);
	}

	public boolean containsItem(String itemString) {
		for (int i = 0; i < this.getModel().getSize(); i++) {
			String _item = " " + this.getModel().getElementAt(i);
			if (_item.equals(itemString))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		JComboBox cmb = new MyComboBox(model);
		cmb.setBounds(0, 0, 400, 20);
		JFrame frame = new JFrame();
		//BoxLayout layout=new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS); 
		PicturePanel imagePanel = new PicturePanel();
		String url = "http://api.map.baidu.com/staticimage?center=116.403874,39.914888&width=400&height=300&zoom=11";
		imagePanel.setPath(url);
		
		//frame.setLayout(layout);
		frame.getContentPane().add(cmb);
		frame.getContentPane().add(imagePanel);
		frame.setSize(400, 300);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() // 为了关闭窗口
		{
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}