package com.websitbrowser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.Dimension;

public class WebsiteBrowser extends JFrame {
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JToolBar jToolBar = new JToolBar();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    JButton backButton = new JButton("后退(B)");
    JButton forwardButton = new JButton("前进(F)");
    JButton stopButton = new JButton("停止(P)");
    JButton refershButton = new JButton("刷新(R)");
    JButton searchButton = new JButton("搜索(S)");
    ImageIcon image1 = new ImageIcon(com.websitbrowser.WebsiteBrowser.class.
                                     getResource("openFile.png"));
    ImageIcon image2 = new ImageIcon(com.websitbrowser.WebsiteBrowser.class.
                                     getResource("closeFile.png"));
    ImageIcon image3 = new ImageIcon(com.websitbrowser.WebsiteBrowser.class.
                                     getResource("help.png"));
    JLabel statusBar = new JLabel();
    JButton enter = new JButton("输入");
    JEditorPane contentEditorPane = new JEditorPane();
    JScrollPane jScrollPane1 = new JScrollPane();
    JComboBox addressComboBox = new JComboBox();
    java.util.Stack stack=new Stack();
    JMenuItem jMenuItem1 = new JMenuItem();
    JMenu jMenu1 = new JMenu();
    JMenuItem jMenuItem3 = new JMenuItem();
    JMenuItem jMenuItem4 = new JMenuItem();
    JMenu jMenu2 = new JMenu();
    JMenuItem jMenuItem2 = new JMenuItem();
    JMenuItem jMenuItem5 = new JMenuItem();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    public WebsiteBrowser() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        setSize(new Dimension(700, 500));
        setTitle("WebsiteBrowser v1.0");
        statusBar.setText(" 就绪");
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new
                                        WebsiteBrowser_jMenuFileExit_ActionAdapter(this));
        jMenuHelp.setText("Help");
        jMenuHelpAbout.setText("About");
        jMenuHelpAbout.addActionListener(new
                                         WebsiteBrowser_jMenuHelpAbout_ActionAdapter(this));
//        addressComboBox.setText("http://127.0.0.1/");
        contentEditorPane.setEditable(false);
        contentEditorPane.setText("jEditorPane1");
        contentEditorPane.setContentType("text/html");
        contentEditorPane.addHyperlinkListener(new
                                               WebsiteBrowser_contentEditorPane_hyperlinkAdapter(this));
        enter.addActionListener(new WebsiteBrowser_enter_actionAdapter(this));
        addressComboBox.setEditable(true);
        backButton.addActionListener(new WebsiteBrowser_backButton_actionAdapter(this));
        backButton.setEnabled(false);
        backButton.setMaximumSize(new Dimension(29, 27));
        jMenuItem1.setText("打开(O)...");
        jMenu1.setText("查看(V)");
        jMenuItem3.setText("停止");
        jMenuItem4.setText("刷新");
        jMenu2.setText("转到");
        jMenuItem2.setText("后退");
        jMenuItem5.setText("前进");
        jTabbedPane1.setTabPlacement(JTabbedPane.BOTTOM);
        //backButton.setMinimumSize(new Dimension(29, 27));
        jMenuBar1.add(jMenuFile);
        jMenuBar1.add(jMenu1);
        jMenuFile.add(jMenuFileExit);
        jMenuFile.add(jMenuItem1);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        setJMenuBar(jMenuBar1);
        jButton1.setIcon(image1);
        jButton1.setToolTipText("Open File");
        jButton2.setIcon(image2);
        jButton2.setToolTipText("Close File");
        jButton3.setIcon(image3);
        jButton3.setToolTipText("Help");
        jToolBar.add(jButton1);
        jToolBar.add(jButton2);
        jToolBar.add(jButton3);
        backButton.setMnemonic('B');
        jToolBar.add(backButton);
        forwardButton.setMnemonic('F');
        jToolBar.add(forwardButton);
        stopButton.setMnemonic('P');
        jToolBar.add(stopButton);
        refershButton.setMnemonic('R');
        jToolBar.add(refershButton);
        searchButton.setMnemonic('S');
        jToolBar.add(searchButton);
        jToolBar.add(        addressComboBox);
        jToolBar.add(enter);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(jToolBar, java.awt.BorderLayout.NORTH);
        contentPane.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(contentEditorPane);
        jMenu1.add(jMenu2);
        jMenu1.add(jMenuItem3);
        jMenu1.add(jMenuItem4);
        jMenu2.add(jMenuItem2);
        jMenu2.add(jMenuItem5);
        jTabbedPane1.add(jScrollPane1, "jScrollPane1");
    }

    /**
     * File | Exit action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Help | About action performed.
     *
     * @param actionEvent ActionEvent
     */
//    void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
//        WebsiteBrowser_AboutBox dlg = new WebsiteBrowser_AboutBox(this);
//        Dimension dlgSize = dlg.getPreferredSize();
//        Dimension frmSize = getSize();
//        Point loc = getLocation();
//        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
//                        (frmSize.height - dlgSize.height) / 2 + loc.y);
//        dlg.setModal(true);
//        dlg.pack();
//        dlg.setVisible(true);
//    }

    public void enter_actionPerformed(ActionEvent e) {
        try {
            stack.push(contentEditorPane.getPage());
            backButton.setEnabled(true);
            String url=addressComboBox.getSelectedItem().toString();
            if(!url.toUpperCase().startsWith("HTTP"))
             {   url="http://"+url;
                 addressComboBox.setSelectedItem(url);
             }
            contentEditorPane.setPage(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void contentEditorPane_hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                if(contentEditorPane.getPage()!=null)
                System.out.println(contentEditorPane.getPage().toString());
                stack.push(contentEditorPane.getPage());
                addressComboBox.setSelectedItem(e.getURL());
                backButton.setEnabled(true);
                contentEditorPane.setPage(e.getURL());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void backButton_actionPerformed(ActionEvent e) {
        if(stack.size()>1)
        {
           // stack.pop();
            java.net.URL url = (java.net.URL) stack.peek();
            System.out.println(url.toString());
             addressComboBox.setSelectedItem(url);
             backButton.setEnabled(stack.size()>1);
            try {
                contentEditorPane.setPage(url);
            } catch (IOException ex) {
            }
        }
    }
}










