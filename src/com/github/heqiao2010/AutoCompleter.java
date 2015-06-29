package com.github.heqiao2010;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.google.gson.JsonElement;

/**
 * 生成下拉列表
 * 
 */

class AutoCompleter implements KeyListener, ItemListener {

	private JComboBox owner = null;
	private JTextField editor = null;
	private ComboBoxModel model = null;
	private static String imageStaticUrl = "http://api.map.baidu.com/staticimage"; 
	private static String center = "116.403874,39.914888";
	private static String width = "400";
	private static String height = "300";
	private static String zoom = "11";

	public AutoCompleter(JComboBox comboBox) {
		owner = comboBox;
		editor = (JTextField) comboBox.getEditor().getEditorComponent();
		editor.addKeyListener(this);
		model = comboBox.getModel();
		owner.addItemListener(this);
	}

	private String buildImageUrl(ResultVO vo) {
		center = vo.getLocation().getLat() + "," + vo.getLocation().getLng();
		
		return imageStaticUrl + "?center=" + center + "&width=" + width
				+ "&height=" + height + "&zoom=" + zoom;
	}
	
	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		char ch = e.getKeyChar();
		//只有按下回车时才调用地图API
		if (ch == KeyEvent.CHAR_UNDEFINED || ch != '\n')
			return;
		int caretPosition = editor.getCaretPosition();
		String queryCode = editor.getText();
		if (queryCode.length() == 0)
			return;
		getPositionInfo(queryCode, caretPosition);
	}

	/**
	 * 自动完成。根据输入的内容，在列表中找到相似的项目.
	 */
	protected void getPositionInfo(String queryCode, int caretPosition) {
		if(StringUtils.isEmpty(queryCode)){
			return;
		}
		Object[] ret = sendHttpGet(queryCode.substring(0, caretPosition));
		if (owner != null) {
			model = new DefaultComboBoxModel(ret);
			owner.setModel(model);
		}
		if (ret.length > 0) {
			editor.setCaretPosition(caretPosition);
			if (owner != null) {
				try {
					owner.showPopup();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 找到相似的项目, 并且将之排列到数组的最前面。
	 * 
	 * @param str
	 * @return 返回所有项目的列表。
	 */
	protected Object[] sendHttpGet(String str) {
		PlaceSuggession.parameters.put("region", "全国");
		PlaceSuggession.parameters.put("query", str);
		String param = PlaceSuggession.getParameterStr(PlaceSuggession.parameters);
		String returnStr = HttpHelper.sendGet(PlaceSuggession.SUGGESTION_URL, param);
		JsonElement[] locations = PlaceSuggession.parseJsonStr(returnStr);
		return PlaceSuggession.parse2ResultVOs(locations);
	}

	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			int caretPosition = editor.getCaretPosition();
			if (caretPosition != -1) {
				try {
					editor.moveCaretPosition(caretPosition);
					ResultVO vo = (ResultVO) model.getSelectedItem();
					((MyComboBox)owner).getImagePanel().setPath(buildImageUrl(vo));
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
