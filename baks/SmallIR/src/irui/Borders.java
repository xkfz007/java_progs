package irui;

  import java.awt.BorderLayout;  
  import java.awt.Color;  
  import java.awt.Container;  
  import java.awt.GridLayout;  
    
  import javax.swing.JApplet;  
  import javax.swing.JFrame;  
  import javax.swing.JLabel;  
  import javax.swing.JPanel;  
  import javax.swing.border.BevelBorder;  
  import javax.swing.border.Border;  
  import javax.swing.border.CompoundBorder;  
  import javax.swing.border.EtchedBorder;  
  import javax.swing.border.LineBorder;  
  import javax.swing.border.MatteBorder;  
  import javax.swing.border.SoftBevelBorder;  
  import javax.swing.border.TitledBorder;  
    
  public class Borders extends JApplet {  
    static JPanel showBorder(Border b) {  
      JPanel jp = new JPanel();  
      jp.setLayout(new BorderLayout());  
      String nm = b.getClass().toString();  
      nm = nm.substring(nm.lastIndexOf('.') + 1);  
      jp.add(new JLabel(nm, JLabel.CENTER), BorderLayout.CENTER);  
      jp.setBorder(b);  
      return jp;  
    }  
    
    public void init() {  
      Container cp = getContentPane();  
      cp.setLayout(new GridLayout(2, 4));  
      cp.add(showBorder(new TitledBorder("Title")));  
      cp.add(showBorder(new EtchedBorder(EtchedBorder.LOWERED)));  
     // cp.add(showBorder(new LineBorder(Color.BLUE)));  
    //  cp.add(showBorder(new MatteBorder(5, 5, 30, 30, Color.GREEN)));  
   //   cp.add(showBorder(new BevelBorder(BevelBorder.RAISED)));  
   //   cp.add(showBorder(new SoftBevelBorder(BevelBorder.LOWERED)));  
   //   cp.add(showBorder(new CompoundBorder(new EtchedBorder(),  
    //      new LineBorder(Color.RED))));  
    }  
    
    public static void main(String[] args) {  
      run(new Borders(), 500, 300);  
    }  
    
    public static void run(JApplet applet, int width, int height) {  
      JFrame frame = new JFrame();  
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
      frame.getContentPane().add(applet);  
      frame.setSize(width, height);  
      applet.init();  
      applet.start();  
      frame.setVisible(true);  
    }  
  } 