package irui;

/*
 �����WindowsӦ�ó������棬�������IE�ĵ�ַ������������ComboBox���ı�������ʱ��
 ���������б����Զ��г���ƥ�����Ŀ�����ҽ���ƥ�����Ŀ��ʾ��������С�  
 ��Java���и�JComboBox�࣬������ʵ������ѡ���������ѡ��
 ����������û���ṩ�Զ����Һ���ɹ��ܡ��������ھ���   ����װ������࣬ʹ�������Զ����Һ���ɹ��ܡ�  
 
 ��װ˼·���£�  
 1.�ȼ̳�һ��JComboBox�࣬����setEditableΪtrue.   �����Ļ����û��ſ�����combobox���������֡�  
 2.����֪��combobox���������һ��JTextField,   ����ͨ��comboBox.getEditor().getEditorComponent()ȡ������ı���  
 3.Ϊ����ı������һ��KeyListener.  
 4.���û����ı����а���ʱ����ⷢkeyReleased�¼�������������¼���д��Ҫ��ʵ���Զ����Һ���ɵĴ��롣  
 ˼�������ô�򵥣����Զ����ҵ��㷨���κ�һ���Ա�̲�İ�����˶�����д�����������г������ĳ�����룺  
 */

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class JAutoCompleteComboBox extends JComboBox {
	private AutoCompleter completer;

	public JAutoCompleteComboBox() {
		super();
		addCompleter();
	}

	public JAutoCompleteComboBox(ComboBoxModel cm) {
		super(cm);
		addCompleter();
	}

	public JAutoCompleteComboBox(Object[] items) {
		super(items);
		addCompleter();
	}

	public JAutoCompleteComboBox(List v) {
		super((Vector) v);
		addCompleter();
	}

	private void addCompleter() {
		setEditable(true);
		completer = new AutoCompleter(this);
	}

	public void autoComplete(String str) {
		this.completer.autoComplete(str, str.length());
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
		JFrame frame = new JFrame();
		Object[] items = new Object[] { "abc ", "aab ", "aba ", "hpp ", "pp ", "hlp " };
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		JComboBox cmb = new JAutoCompleteComboBox(model);
		model.addElement("abc ");
		model.addElement("aab ");
		model.addElement("aba ");
		model.addElement("hpp ");
		model.addElement("pp ");
		model.addElement("hlp ");
		frame.getContentPane().add(cmb);
		frame.setSize(400, 80);
		frame.setVisible(true);
	}
}