package irui;

 import java.awt.BorderLayout;  
 import java.awt.Container;  
  import java.io.IOException;  
  import javax.swing.JEditorPane;  
  import javax.swing.JFrame;  
  import javax.swing.JScrollPane;  
  import javax.swing.event.HyperlinkEvent;  
  import javax.swing.event.HyperlinkListener;  
  import javax.swing.text.html.HTMLDocument;  
  import javax.swing.text.html.HTMLFrameHyperlinkEvent;  
  public class HTMLView extends JFrame implements HyperlinkListener  
  {  
     public HTMLView() throws Exception  
     {  
        setSize(640, 480);      setTitle("隔叶黄莺:The Blog of Unmi");  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        JEditorPane editorPane = new JEditorPane();  
          
        //放到滚动窗格中才能滚动查看所有内容  
        JScrollPane scrollPane = new JScrollPane(editorPane);  
        //设置是显示网页 html 文件,可选项  
        //editorPane.setContentType("text/html");  
        //设置成只读，如果是可编辑，你会看到显示的样子也是不一样的，true显示界面  
        editorPane.setEditable(false);  
        //要能响应网页中的链接，则必须加上超链监听器  
        editorPane.addHyperlinkListener(this);  
        String path = "http://www.baidu.com";  
        try  
        {  
           editorPane.setPage(path);  
        }  
        catch (IOException e)  
        {  
           System.out.println("读取页面 " + path + " 出错. " + e.getMessage());  
        }  
        Container container = getContentPane();  
          
        //让editorPane总是填满整个窗体  
        container.add(scrollPane, BorderLayout.CENTER);  
     }  
     //超链监听器，处理对超级链接的点击事件，但对按钮的点击还捕获不到  
     public void hyperlinkUpdate(HyperlinkEvent e)  
     {  
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)  
        {  
           JEditorPane pane = (JEditorPane) e.getSource();  
           if (e instanceof HTMLFrameHyperlinkEvent)  
           {  
              HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;  
              HTMLDocument doc = (HTMLDocument) pane.getDocument();  
              doc.processHTMLFrameHyperlinkEvent(evt);  
           }  
           else  
           {  
              try  
              {  
                 pane.setPage(e.getURL());  
              }  
              catch (Throwable t)  
              {  
                 t.printStackTrace();  
              }  
           }  
        }  
     }  
       
     public static void main(String[] args) throws Exception  
     {  
        JFrame frame = new HTMLView();  
        frame.setVisible(true);  
     }  
  }  