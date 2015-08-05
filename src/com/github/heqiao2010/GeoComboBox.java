package com.github.heqiao2010;

import java.awt.TextField;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * 一个调用百度API搜索的组合框
 * @author heqiaoa
 *
 */
public class GeoComboBox extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1642702729115910117L;
	private AutoCompleter completer = null;
	private PicturePanel imagePanel = null; //显示图片的Panel
	private JTextField locationTextFiled; //显示经纬度的TextFiled

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

	public GeoComboBox() {
		super();
		addCompleter();
	}

	public GeoComboBox(ComboBoxModel cm) {
		super(cm);
		addCompleter();
	}

	public GeoComboBox(Object[] items) {
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

	public JTextField getLocationTextFiled() {
		return locationTextFiled;
	}

	public void setLocationTextFiled(JTextField locationTextFiled) {
		this.locationTextFiled = locationTextFiled;
	}
//	public static void main(String[] args) {
//		DefaultComboBoxModel model = new DefaultComboBoxModel();
//		GeoComboBox cmb = new GeoComboBox(model);
//		cmb.setBounds(5, 5, 370, 20);
//		JFrame frame = new JFrame();
//		//BoxLayout layout=new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS); 
//		PicturePanel imagePanel = new PicturePanel();
//		cmb.setImagePanel(imagePanel);
//		String url = "http://api.map.baidu.com/staticimage?center=116.403874,39.914888&width=400&height=300&zoom=11";
//		try {
//			imagePanel.setPath(url);
//		} catch (BusinessException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		//frame.setLayout(layout);
//		frame.getContentPane().add(cmb);
//		frame.getContentPane().add(imagePanel);
//		frame.setSize(400, 300);
//		frame.setVisible(true);
//		frame.addWindowListener(new WindowAdapter() // 为了关闭窗口
//		{
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
//	}
}