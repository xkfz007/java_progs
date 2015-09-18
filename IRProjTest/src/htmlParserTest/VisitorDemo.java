package htmlParserTest;



import org.htmlparser.Parser;
import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.visitors.NodeVisitor;



public class VisitorDemo extends NodeVisitor{
	//��¼Remark Node����
	private int remark_node_count;
	//��¼Text Node����
	private int tag_node_count;
	//��¼Tag Node����
	private int text_node_count;
	
	public void visitRemarkNode(Remark remark) {
		System.out.println("���ڷ��ʵ� "+(++remark_node_count)+" ��Remark Node ");
	}

	public void visitStringNode(Text text) {
		System.out.println("���ڷ��ʵ� "+(++tag_node_count)+" ��Text Node ");
	}

	public void visitTag(Tag tag) {
		System.out.println("���ڷ��ʵ� "+(++text_node_count)+" ��Tag Node ");
	}

	public static void main(String[] args) {
		try{
                        //��ʽһ��
			String urlStr = "F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\dff.htm";
		//	String urlStr = "F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\htmlparser.html";
			Parser parser = HtmlParserUtils.getParserWithUrlStr(urlStr, "utf-8");
			
			NodeVisitor visitor = new VisitorDemo ();
	                parser.visitAllNodesWith (visitor);
	                
                        
                        System.out.println("=========================================");
                        //��ʽ�������ã���
                        parser.reset();
                        NodeVisitor visitor2 = new NodeVisitor() {
	        	        public void visitTag(Tag tag) {
	        		        System.out.println("���ڷ��ʵ�tag��" + tag.getTagName() +
	        		        		"  ||  Class is :"+ tag.getClass()
	        		        		+"|| Text:"+tag.getText());
	        		        
	        	        }
	                };
	                parser.visitAllNodesWith(visitor2);


		}catch(Exception e){
			e.printStackTrace();
		}
	}
}