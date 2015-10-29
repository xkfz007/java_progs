package project;

import irui.JAutoCompleteComboBox;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.GridBagLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class LuceneIRUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanelTop = null;

	private JComboBox jComboBox = null;

	private JTextField jTextField = null;

	private JButton jButton = null;

	private JPanel jPanel = null;

	private JButton jButton1 = null;

	private JScrollPane jScrollPane = null;

	private JTextArea jTextArea = null;

	private JComboBox jComboBox1 = null;

	/**
	 * This is the default constructor
	 */
	public LuceneIRUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(648, 543);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanelTop(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJScrollPane(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelTop	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTop() {
		if (jPanelTop == null) {
			jPanelTop = new JPanel();
			jPanelTop.setLayout(null);
			jPanelTop.setBounds(new Rectangle(32, 76, 542, 42));
			jPanelTop.add(getJComboBox(), null);
			jPanelTop.add(getJTextField(), null);
			jPanelTop.add(getJButton(), null);
		}
		return jPanelTop;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(27, 7, 88, 29));
			String[] items={"标题","作者","作者单位","摘要","内容"};
			for(int i=0;i<items.length;i++)
			jComboBox.addItem(items[i]);
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(121, 7, 200, 28));
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(340, 5, 95, 31));
			jButton.setText("检索");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				
				String queryString=jTextField.getText();
				String[] fields={"title","author","workplace","abstract"};
				if(!queryString.equals(""))
				{
				int  nfield=jComboBox.getSelectedIndex();
					//fields[0]=(String)jComboBox.getSelectedItem();
				System.out.println(fields);
				CreateIndexes searchIndex=new CreateIndexes();
					String result=searchIndex.searchQuery(queryString, fields[nfield]);
					System.out.println(result);
					jTextArea.setText("<html><body>"+result+"</body></html>");
				}
				else
					System.out.println("检索内容不能为空");
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(27, 11, 538, 53));
			jPanel.add(getJButton1(), null);
			jPanel.add(getJComboBox1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(26, 7, 105, 34));
			jButton1.setText("建立索引");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				//	System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				HIMLIndex createIndex=new HIMLIndex();
				createIndex.createIndex();
				
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(38, 130, 539, 363));
			jScrollPane.setViewportView(getJTextArea());
			jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}
	
	
/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			 DefaultComboBoxModel   model   =   new   DefaultComboBoxModel();
			jComboBox1 = new   JAutoCompleteComboBox(model);// new JComboBox();
			jComboBox1.setBounds(new Rectangle(170, 8, 214, 36));
			jComboBox1.setEditable(true);
		}
		return jComboBox1;
	}

public static void main(String args[])
{
	LuceneIRUI luir=new LuceneIRUI();
	luir.setVisible(true);
	
}

}  //  @jve:decl-index=0:visual-constraint="10,10"
