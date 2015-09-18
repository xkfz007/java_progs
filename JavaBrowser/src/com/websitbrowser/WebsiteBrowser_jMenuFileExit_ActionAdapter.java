package com.websitbrowser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WebsiteBrowser_jMenuFileExit_ActionAdapter implements ActionListener {
    WebsiteBrowser adaptee;

    WebsiteBrowser_jMenuFileExit_ActionAdapter(WebsiteBrowser adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}
