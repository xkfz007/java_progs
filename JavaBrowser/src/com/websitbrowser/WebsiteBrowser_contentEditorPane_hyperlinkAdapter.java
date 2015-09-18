package com.websitbrowser;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

class WebsiteBrowser_contentEditorPane_hyperlinkAdapter implements HyperlinkListener {
    private WebsiteBrowser adaptee;
    WebsiteBrowser_contentEditorPane_hyperlinkAdapter(WebsiteBrowser adaptee) {
        this.adaptee = adaptee;
    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        adaptee.contentEditorPane_hyperlinkUpdate(e);
    }
}