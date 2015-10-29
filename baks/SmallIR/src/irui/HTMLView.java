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
        setSize(640, 480);      setTitle("��Ҷ��ݺ:The Blog of Unmi");  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        JEditorPane editorPane = new JEditorPane();  
          
        //�ŵ����������в��ܹ����鿴��������  
        JScrollPane scrollPane = new JScrollPane(editorPane);  
        //��������ʾ��ҳ html �ļ�,��ѡ��  
        //editorPane.setContentType("text/html");  
        //���ó�ֻ��������ǿɱ༭����ῴ����ʾ������Ҳ�ǲ�һ���ģ�true��ʾ����  
        editorPane.setEditable(false);  
        //Ҫ����Ӧ��ҳ�е����ӣ��������ϳ���������  
        editorPane.addHyperlinkListener(this);  
        String path = "http://www.baidu.com";  
        try  
        {  
           editorPane.setPage(path);  
        }  
        catch (IOException e)  
        {  
           System.out.println("��ȡҳ�� " + path + " ����. " + e.getMessage());  
        }  
        Container container = getContentPane();  
          
        //��editorPane����������������  
        container.add(scrollPane, BorderLayout.CENTER);  
     }  
     //����������������Գ������ӵĵ���¼������԰�ť�ĵ�������񲻵�  
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