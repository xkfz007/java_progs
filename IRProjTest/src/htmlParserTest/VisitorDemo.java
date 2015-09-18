package htmlParserTest;



import org.htmlparser.Parser;
import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.visitors.NodeVisitor;



public class VisitorDemo extends NodeVisitor{
	//记录Remark Node数量
	private int remark_node_count;
	//记录Text Node数量
	private int tag_node_count;
	//记录Tag Node数量
	private int text_node_count;
	
	public void visitRemarkNode(Remark remark) {
		System.out.println("正在访问第 "+(++remark_node_count)+" 个Remark Node ");
	}

	public void visitStringNode(Text text) {
		System.out.println("正在访问第 "+(++tag_node_count)+" 个Text Node ");
	}

	public void visitTag(Tag tag) {
		System.out.println("正在访问第 "+(++text_node_count)+" 个Tag Node ");
	}

	public static void main(String[] args) {
		try{
                        //方式一：
			String urlStr = "F:\\hfz\\现代信息检索\\大作业\\测试\\papers\\dff.htm";
		//	String urlStr = "F:\\hfz\\现代信息检索\\大作业\\测试\\papers\\htmlparser.html";
			Parser parser = HtmlParserUtils.getParserWithUrlStr(urlStr, "utf-8");
			
			NodeVisitor visitor = new VisitorDemo ();
	                parser.visitAllNodesWith (visitor);
	                
                        
                        System.out.println("=========================================");
                        //方式二（常用）：
                        parser.reset();
                        NodeVisitor visitor2 = new NodeVisitor() {
	        	        public void visitTag(Tag tag) {
	        		        System.out.println("正在访问的tag：" + tag.getTagName() +
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