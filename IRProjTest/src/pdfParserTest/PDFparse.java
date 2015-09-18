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
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * ʹ�� pdfbox ����pdf �ĵ���Ϣ
 * @author longhuiping
 *
 */
public class PDFparse {

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
			
			System.out.println( "����:" + info.getTrapped() );
			
		//	System.out.println( "����ʱ��:" + dateFormat( info.getCreationDate() ));
		//	System.out.println( "�޸�ʱ��:" + dateFormat( info.getModificationDate()));
		
			
			//��ȡ������Ϣ
			PDFTextStripper pts = new PDFTextStripper();
			String content = pts.getText( document );
			System.out.println( "����:" + content );
			
			
		/*	*//** �ĵ�ҳ����Ϣ **//*
			PDDocumentCatalog cata = document.getDocumentCatalog();
			List pages = cata.getAllPages();
			int count = 1;
			for( int i = 0; i < pages.size(); i++ )
			{
				PDPage page = ( PDPage ) pages.get( i );
				if( null != page )
				{
					PDResources res = page.findResources();
					
					//��ȡҳ��ͼƬ��Ϣ
					Map imgs = res.getImages();
					if( null != imgs )
					{
						Set keySet = imgs.keySet();
						Iterator it = keySet.iterator();
						while( it.hasNext() )
						{
							Object obj =  it.next();
							PDXObjectImage img = ( PDXObjectImage ) imgs.get( obj );
							img.write2file( imgSavePath + count );
							count++;
						}
					}
				}
			}*/
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
	
	public static void main( String [] args ) throws Exception{
		//String path1="F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\01-2D_PCA.pdf";
	//	String path2="F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\01-2D_PCAImage\\";
		
		//P217-SIGIR1971.pdf
		String path1="E:\\hfz\\�ִ���Ϣ����\\TopConferences\\SIGIR\\PDF\\SIGIR2007\\P755-SIGIR2007.pdf";
		String path2=null;//"F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\P217-SIGIR1971\\";
		
		
		
		pdfParse(path1,path2);
	}
}
