package com.websitbrowser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WebsiteBrowser_backButton_actionAdapter implements ActionListener {
    private WebsiteBrowser adaptee;
    WebsiteBrowser_backButton_actionAdapter(WebsiteBrowser adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.backButton_actionPerformed(e);
    }
}
