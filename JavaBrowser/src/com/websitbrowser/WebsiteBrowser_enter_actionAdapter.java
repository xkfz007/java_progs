package com.websitbrowser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WebsiteBrowser_enter_actionAdapter implements ActionListener {
    private WebsiteBrowser adaptee;
    WebsiteBrowser_enter_actionAdapter(WebsiteBrowser adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.enter_actionPerformed(e);
    }
}
