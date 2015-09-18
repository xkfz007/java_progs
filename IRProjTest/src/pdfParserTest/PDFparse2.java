package pdfParserTest;



import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;

/**
 * ʹ�� pdfbox ����pdf �ĵ���Ϣ
 * @author longhuiping
 *
 */
public class PDFparse2 {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * ����pdf�ĵ���Ϣ
	 * @param pdfPath	pdf�ĵ�·��
	 * @throws Exception
	 */
	public static void pdfParse( String pdfPath, String imgSavePath ) throws Exception
	{
		InputStream input = null;
		File pdfFile = new File( pdfPath );
		PDDocument document = null;
		try{
			input = new FileInputStream( pdfFile );
			//���� pdf �ĵ�
			document = PDDocument.load( input );
			
			/** �ĵ�������Ϣ **/
			PDDocumentInformation info = document.getDocumentInformation();
			System.out.println( "����:" + info.getTitle() );
			System.out.println( "����:" + info.getSubject() );
			System.out.println( "����:" + info.getAuthor() );
			System.out.println( "�ؼ���:" + info.getKeywords() );
			
			System.out.println( "Ӧ�ó���:" + info.getCreator() );
			System.out.println( "pdf ��������:" + info.getProducer() );
			
		//	System.out.println( "����:" + info.getTrapped() );
			
		//	System.out.println( "����ʱ��:" + dateFormat( info.getCreationDate() ));
		//	System.out.println( "�޸�ʱ��:" + dateFormat( info.getModificationDate()));
		
			
			//��ȡ������Ϣ
			PDFTextStripper pts = new PDFTextStripper();
			String content = pts.getText( document );
			
			//System.out.println( "����:" + content );
			getContent(content);
			//Matrix mat=pts.getTextLineMatrix();
			//System.out.println(mat);
			
//			/** �ĵ�ҳ����Ϣ **/
//			PDDocumentCatalog cata = document.getDocumentCatalog();
//			List pages = cata.getAllPages();
//			int count = 1;
//			for( int i = 0; i < pages.size(); i++ )
//			{
//				PDPage page = ( PDPage ) pages.get( i );
//				if( null != page )
//				{
//					PDResources res = page.findResources();
//					
//					//��ȡҳ��ͼƬ��Ϣ
//					Map imgs = res.getImages();
//					if( null != imgs )
//					{
//						Set keySet = imgs.keySet();
//						Iterator it = keySet.iterator();
//						while( it.hasNext() )
//						{
//							Object obj =  it.next();
//							PDXObjectImage img = ( PDXObjectImage ) imgs.get( obj );
//							img.write2file( imgSavePath + count );
//							count++;
//						}
//					}
//				}
//			}
		}catch( Exception e)
		{
			throw e;
		}finally{
			if( null != input )
				input.close();
			if( null != document )
				document.close();
		}
	}
	
	/**
	 * ��ȡ��ʽ�����ʱ����Ϣ
	 * @param dar	ʱ����Ϣ
	 * @return
	 * @throws Exception
	 */
	public static String dateFormat( Calendar calendar ) throws Exception
	{
		if( null == calendar )
			return null;
		String date = null;
		try{
			String pattern = DATE_FORMAT;
			SimpleDateFormat format = new SimpleDateFormat( pattern );
			date = format.format( calendar.getTime() );
		}catch( Exception e )
		{
			throw e;
		}
		return date == null ? "" : date;
	}
	public static void getContent(String fullcontent)
	{
		int introPoint=fullcontent.toLowerCase().indexOf("introduction");
		
		int refPoint=fullcontent.toLowerCase().indexOf("references");
		if(introPoint!=-1&&refPoint!=-1)
		{
		System.out.println(introPoint);
		System.out.println(refPoint);
		String content=fullcontent.substring(introPoint, refPoint);
		System.out.println( "����:" + content);
		}
	}
	
	@Test
	public void strTest()
	{
		String str="Let me begin by saying how honored I am to receive the Gerard"; 
int a1=str.indexOf("am");
int a2=str.indexOf("receive");
System.out.println(a1);
System.out.println(a2);
	}
	public static void main( String [] args ) throws Exception{
		//String path1="F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\01-2D_PCA.pdf";
	//	String path2="F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\01-2D_PCAImage\\";
	//	String path1="F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\P217-SIGIR1971.pdf";
		
		//String path1="F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\P379-SIGIR2009.pdf";
		
		//String path1="F:\\hfz\\�ִ���Ϣ����\\TopConferences\\SIGIR\\PDF\\SIGIR1971";
		String path1="e:\\hfz\\�ִ���Ϣ����\\TopConferences\\SIGIR\\PDF\\SIGIR2009";

		
		File directory = new File(path1);
	    File[] files = directory.listFiles();
		String path2=null;//"F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\P217-SIGIR1971\\";
		int n=files.length;
		
		for(int i=0;i<n;i++)
		{
		String name=files[i].getName();
		if(name.endsWith(".pdf")&&name.startsWith("P"))
		{
				System.out.println("======No."+i+"=========" + name
						+ "===================");
				System.out.println(files[i].getAbsolutePath());
				pdfParse(files[i].getAbsolutePath(), null);
		}
		}
		
		//pdfParse(path1,path2);
	}
}
