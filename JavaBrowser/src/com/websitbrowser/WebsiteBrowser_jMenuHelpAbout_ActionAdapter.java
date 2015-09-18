package com.websitbrowser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WebsiteBrowser_jMenuHelpAbout_ActionAdapter implements ActionListener {
    WebsiteBrowser adaptee;

    WebsiteBrowser_jMenuHelpAbout_ActionAdapter(WebsiteBrowser adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
       // adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
    }
}