package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;


public class PdfParse {
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	static int flag = 0;//全局的指针变量
	static Analyzer analyzer=new StandardAnalyzer();
	static String indexPath="E:\\hfz\\现代信息检索\\大作业\\程序\\IRProjTest\\sigirIndexes2";
	/**
	 * 解析pdf文档信息
	 * 
	 * @param pdfPath
	 *            pdf文档路径
	 * @throws Exception
	 */
	public static void pdfParse(String pdfPath)
			throws Exception {
		InputStream input = null;
		File pdfFile = new File(pdfPath);
		PDDocument document = null;
		try {
			input = new FileInputStream(pdfFile);
			// 加载 pdf 文档
			document = PDDocument.load(input);

			/** 文档属性信息 **/
			PDDocumentInformation info = document.getDocumentInformation();			
			// 获取内容信息
			PDFTextStripper pts = new PDFTextStripper();
			String content =(pts.getText(document)) ;			
			int length = content.length();
//			System.out.println("内容:" + content);
			String title,abstrack,realContent;
			title=getTile(content);//获取文档的标题
			abstrack=getAbstract(content);//获取文章的摘要
			realContent=getContent(content);//获取文章的主要内容
//			createIndex(title,abstrack,realContent);
			flag=0;
			/** 文档页面信息 **/
			/*
			 * PDDocumentCatalog cata = document.getDocumentCatalog(); List
			 * pages = cata.getAllPages(); int count = 1; for( int i = 0; i <
			 * pages.size(); i++ ) { PDPage page = ( PDPage ) pages.get( i );
			 * if( null != page ) { PDResources res = page.findResources();
			 * 
			 * //获取页面图片信息 Map imgs = res.getImages(); if( null != imgs ) { Set
			 * keySet = imgs.keySet(); Iterator it = keySet.iterator(); while(
			 * it.hasNext() ) { Object obj = it.next(); PDXObjectImage img = (
			 * PDXObjectImage ) imgs.get( obj ); img.write2file( imgSavePath +
			 * count ); count++; } } } }
			 */
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != input)
				input.close();
			if (null != document)
				document.close();
		}

	}

	/*
	 * 取文件标题的方法 基于的正则表达式为：While not matching (Author infomation)and (lineNum<=3)
	 * add to TILE
	 */
	public static String getTile(String content) {
		// System.out.println("长度 ");
		int count1 = 0;
		int count2 = 0;
		int lineNum = 0;
		String temTile1, temTitle2, title = null;
		boolean bool = false;
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				char temp = content.charAt(flag);
				while(temp==' '||temp=='\n'||temp=='\r')
				{
					flag++;
					// System.out.println(flag);
					temp = content.charAt(flag);
				}
				count1=flag;
	//			System.out.println(temp);
				while (temp != '\n') {
					flag++;
					// System.out.println(flag);
					temp = content.charAt(flag);
				}
				title = content.substring(count1, flag - 1);
				title = title + " ";
//				System.out.println("herre"+title);
				flag++;
				count1 = flag;
			} else {

				char temp = content.charAt(flag);
				while (temp != '\n') {
					flag++;
					// System.out.println(flag);
					temp = content.charAt(flag);
					// System.out.println("here");
				}
				temTile1 = content.substring(count1, flag);
				flag++;
				count2 = flag;
				// System.out.println(temTile1);
				char dtemp = content.charAt(flag);
				// System.out.println(dtemp);
				while (dtemp != '\n') {
					flag++;
					// System.out.println(flag);
					dtemp = content.charAt(flag);
					// System.out.println("长度2");

				}
				temTitle2 = content.substring(count2, flag + 1);
				// System.out.println(temTitle2);
				// System.out.println("hehheheh");
				// System.out.println(temTitle2.trim());
				flag++;
				if (isInstituteInfo(temTitle2) || isAbstractInfo(temTitle2)
						|| isAddressInfo(temTitle2) || isEmailInfo(temTitle2)) {
					bool = true;
				}
				if (!bool) {
					title = title + temTile1;
					flag = count2;
					// System.out.println(content.charAt(flag));
				} else {
					flag = count1;
					// System.out.println(content.charAt(flag));
				}
			}

		}
		System.out.println("标题:" + title);
		return title;
	}

	/*
	 * 取作者姓名的方法 基于的正则表达式为：((Creatorname)*(creatorname))RT (creatorAfflition)RT
	 * 很复杂暂时没有实现
	 */
	
	
	/*
	 * 取摘要的方法：
	 * 	摘要信息跟在作者信息后面。判断摘要信息的根据如下：
		A. 'Abstract'+Description
		假如没有: 这种情况，则利用下面的B规则：
		B.Overview+Description		
		摘要往往只包含一段或者两段的内容，判断摘要抽取结束
		可以采用如下办法：
		如果有一行，只包含回车和换行符，那么摘要就抽取到
		此为止。
		如果遇到一行中由关键字Keywords、Categories and Subject Descriptors、General Terms、
		Digital. INTRODUCTION
	 */
	public static String getAbstract(String content)
	{
		int maxlength=0;
		boolean bool=false;
		int count = flag;
		int num=0;
		int numAthor=0;
		int startPoint;
		char testChar = content.charAt(flag);
		maxlength++;
		String abstractString;
		boolean endFlag=true;
//		System.out.println("here:   "+testChar);
		String testTerm = null;
		String testLine = null;
		while(testChar==' '||testChar=='\n'||testChar=='\r')
		{
			bool=true;
			flag++;					
			testChar = content.charAt(flag);
			maxlength++;
		}
		if(bool)
		{
			count=flag;
			bool=false;
		}
		while (testChar != ' ' && testChar != '\n') {
			flag++;
			maxlength++;
			testChar = content.charAt(flag);
			
		}
		testTerm = content.substring(count, flag);
//		System.out.println("here:   "+testTerm);
		flag++;
		maxlength++;
		count=flag;
		testChar = content.charAt(flag);		
		while (!testTerm.trim().equalsIgnoreCase("ABSTRACT"))
		{
			while(testChar==' '||testChar=='\n'||testChar=='\r')
			{
				bool=true;
				flag++;	
				maxlength++;
				
				if(flag>=content.length())
					break;
				testChar = content.charAt(flag);
			}
			if(flag>=content.length())
				break;
			if(bool)
			{
				count=flag;
				bool=false;
			}
			while (testChar != ' ' && testChar != '\n') {
				flag++;
				maxlength++;
				testChar = content.charAt(flag);
//				System.out.println("22222:   "+(int)testChar);
			}
			testTerm = content.substring(count, flag);
//			System.out.println("here1:   "+testTerm);
//			flag++;
//			count=flag;
//			testChar = content.charAt(flag);
//			System.out.println("11111:   "+(int)testChar);
		}
//		System.out.println("11111:   "+testChar);
		startPoint=count;
//		System.out.println("11111:   "+content.charAt(startPoint));
		while(endFlag)
//		for(int i=0;i<22;i++)
		{
//			System.out.println("----------:"+content.length()+"vs"+flag);	
			if(flag>=content.length())
				break;
			flag++;
			count=flag;
			if(flag>=content.length())
				break;
			testChar=content.charAt(flag);
			while(testChar==' '||testChar=='\n'||testChar=='\r')
			{
				bool=true;
				flag++;	
				if(flag>=content.length())
					break;
				testChar = content.charAt(flag);
			}
			if(flag>=content.length())
				break;
			if(bool)
			{
				count=flag;
				bool=false;
			}
			while (testChar != '\n') {
				flag++;
				if(flag>=content.length())
					break;
				testChar = content.charAt(flag);
			}
			if(flag>=content.length())
				break;
			testLine=content.substring(count, flag);
//			System.out.println("11111----------:   "+testLine);
			endFlag=isAbstractEnd(testLine);
		}
		abstractString=content.substring(startPoint, count);
		System.out.println("-----------------摘要:"+abstractString);
		flag=count;
		return abstractString;
	}
	/*
	 * 获得文档的内容
	 * 方法：取完摘要以后的内容全部取做内容
	 */
	
	
	
	public static String getContent(String content)
	{
//		System.out.println("*************:"+content.charAt(flag));
//		System.out.println("*************:"+content.charAt(++flag));
//		System.out.println("*************:"+content.charAt(++flag));
		int count = 0;
		char testChar;
		boolean endFlag=true;
		boolean bool=false;
		String testLine;
		String contentLine;
		int startPoint=flag--;
		while(endFlag)
//			for(int i=0;i<22;i++)
			{
//				System.out.println("----------:"+content.length()+"vs"+flag);	
				if(flag>=content.length())
					break;
				flag++;
				count=flag;
				if(flag>=content.length())
					break;
				testChar=content.charAt(flag);
				while(testChar==' '||testChar=='\n'||testChar=='\r')
				{
					bool=true;
					flag++;	
					if(flag>=content.length())
						break;
					testChar = content.charAt(flag);
				}
				if(flag>=content.length())
					break;
				if(bool)
				{
					count=flag;
					bool=false;
				}
				while (testChar != '\n') {
					flag++;
					if(flag>=content.length())
						break;
					testChar = content.charAt(flag);
				}
				if(flag>=content.length())
					break;
				testLine=content.substring(count, flag);
//				System.out.println("11111----------:   "+testLine);
				endFlag=isContentEnd(testLine);
			}
		contentLine=content.substring(startPoint, count);
//		String realContent=content.substring(flag, content.length());
		System.out.println("*************内容:"+contentLine);
		return contentLine;
	}
	public static boolean isContentEnd(String testLine)
	{
		boolean bool=true;
		int index=0;
		int startPoint=index;
		String cstring;
		char testChar;
		int length=testLine.length();
		testChar=testLine.charAt(index);
		if(!Character.isDigit(testChar))
		{
			return bool;
		}
		else
		{
			index++;
			startPoint=index;
			testChar=testLine.charAt(index);
			while(testChar==' '||testChar=='.')
			{
				index++;
				startPoint=index;
				if(index>=length)
					break;
				
				testChar=testLine.charAt(index);
			}
			while(testChar!='\n')
			{
				index++;		
				if(index>=length)
					break;
				testChar=testLine.charAt(index);
			}
			cstring=testLine.substring(startPoint, index);
//			System.out.println("++++++++++++++++++++++++++++++++:"+cstring);
//			System.out.println("++++++++++++++++++++++++++++++++:"+cstring.trim().equalsIgnoreCase("REFERENCES"));			
			if(cstring.trim().equalsIgnoreCase("REFERENCES"))
				bool=false;
		}
		return bool;
	}
	public static void getAuthorName(String content) {
		boolean bool1 = false;
		boolean bool2 = false;
		int count = flag;
		int num=0;
		int numAthor=0;
		char testChar = content.charAt(flag);
		// System.out.println("here:   "+testChar);
		String testTerm = null;
		String testLine = null;
		while(testChar==' '||testChar=='\n'||testChar=='\r')
		{
			flag++;					
			testChar = content.charAt(flag);
		}
		count=flag;
		while (testChar != ' ' && testChar != '\n') {
			flag++;
			testChar = content.charAt(flag);
		}
		testTerm = content.substring(count, flag);
		flag++;
		testChar = content.charAt(flag);
		while (!testTerm.trim().equalsIgnoreCase("Keywords")
				&& !testTerm.trim().equalsIgnoreCase("Categories")
				&& !testTerm.trim().equalsIgnoreCase("General")
				&& !testTerm.trim().equalsIgnoreCase("INTRODUCTION")
				&& !testTerm.trim().equalsIgnoreCase("ABSTRACT"))
		// for(int i=7;i>0;i--)
		{
			if(num==0)
			{
				
			}
			/*	*/
				while (testChar != '\n') 
				{
				// System.out.println("here1111:   "+(int)testChar);
					flag++;
					testChar = content.charAt(flag);			
				}
				if (count != flag) 
				{
				testLine = content.substring(count, flag);				
				}
				System.out.println("there:   " + testLine);
				flag++;
				count = flag;
				testChar = content.charAt(flag);
			// System.out.println("here:   "+(int)testChar);
			/*
			 * if(i==2) {
			 * 
			 * System.out.println("33333:   "+(int)testChar); int kk=flag+1;
			 * char testChar1=content.charAt(kk);
			 * System.out.println("11111111:   "+(int)testChar1); int
			 * kk1=flag+2; char testChar11=content.charAt(kk1);
			 * System.out.println("11111111:   "+(int)testChar11); int
			 * kk11=flag+3; char testChar111=content.charAt(kk11);
			 * System.out.println("11111111:   "+(int)testChar111); }
			 */
			while (testChar != ' ') {
				flag++;
				testChar = content.charAt(flag);
				// if(i==2)
				// System.out.println("here444:   "+(int)testChar);
			}
			if (count != flag) {
				testTerm = content.substring(count, flag);
				flag++;
			} else {
				// System.out.println("i'm here!!!:");
				flag++;
				flag++;
				count = flag;
				testChar = content.charAt(flag);//				
			}
//			System.out.println("here:   " + testTerm);
			// testChar = content.charAt(flag);
		}
		// while()
	}

	/*
	 * 判定是否是Abstract的结束
	 */
	
	public static boolean isAbstractEnd(String testLine)
	{
		boolean bool=true;
		int startPoint=0;
		int endPoint=0;
		char checkChar;
		String checkTerm;
//		Keywords、
//		Categories and Subject Descriptors、
//		Categories and Subject Descriptors、
//		Digital. INTRODUCTION
//		System.out.println("1111:"+testLine);
		String[] check1={"Keywords","Keywords:"};
		String[] check2={"Categories and Subject Descriptors","Categories & Subject Descriptors"};
		String[] check3={"General Terms","General Terms:"};
		String[] check4={"Keywords","Keywords:"};
		String[] check5={"1. INTRODUCTION","1 INTRODUCTION"};
	//	int length=check1[0].length();
	/*	System.out.println("2222:"+length);
		for(int i=0;i<length;i++)
		{
			System.out.println("---------:"+check1[0].charAt(i));
		}*/
		checkChar=testLine.charAt(endPoint);
//		System.out.println("2222:---------"+testLine);
		while (checkChar != ':' && checkChar != '\n'&&checkChar!='\r')
		{
			endPoint++;
//			System.out.println("4444:"+endPoint);
			checkChar = testLine.charAt(endPoint);
//			System.out.println("4444:"+checkChar);
//			System.out.println("22222:   "+(int)testChar);
		}
		checkTerm = testLine.substring(startPoint, endPoint);
		if(checkTerm.trim().equalsIgnoreCase(check1[0])||checkTerm.trim().equalsIgnoreCase(check2[0])||checkTerm.trim().equalsIgnoreCase(check2[1])||
				checkTerm.trim().equalsIgnoreCase(check3[0])||checkTerm.trim().equalsIgnoreCase(check4[0])||
				checkTerm.trim().equalsIgnoreCase(check5[0])||checkTerm.trim().equalsIgnoreCase(check5[1]))
		{
//			System.out.println("3333:-----------------"+checkTerm);
			bool=false;
		}
//		System.out.println("2222:---------"+bool);
		return bool;
	}
	/*
	 * 判定是否是作者的单位信息
	 */
	public static boolean isInstituteInfo(String tempIns) {
		boolean bool = false;
		String[] check = { "Department", "Microsoft", "Laboratory",
				"University", "Research", "Studies", "Center", "Dept.",
				"Science", "School", "IBM", "Computer", "Federal", "Technical",
				"Inc.", "Institue" };
		int posStart = 0;
		int posEnd = 0;
		char temp = tempIns.charAt(posStart);
		String term = null;
		while (temp != '\n') {
			while (temp != ' ' && temp != '\n')// temp!='\n'
			{
				posStart++;
				// System.out.println("1:"+temp);
				// System.out.println("1:"+posStart);
				temp = tempIns.charAt(posStart);
				// System.out.println("2:"+posStart);
			}
			// System.out.println("here");
			term = tempIns.substring(posEnd, posStart);
			// System.out.println("here");
			for (int i = 0; i < 16; i++) {
				if (term.trim().equalsIgnoreCase(check[i])) {
					bool = true;
					// System.out.println("isInstituteInfo::here");
					break;
				}
			}
			if (temp != '\n')
				posStart++;
			posEnd = posStart;
			temp = tempIns.charAt(posStart);
			// System.out.println("here!!");
		}

		return bool;
	}

	/*
	 * 判定是是否是作者的地址信息
	 */
	public static boolean isAddressInfo(String tempAddr) {
		boolean bool = false;
		String[] check = { "Asia", "Road", "China", "Germany", "Slovenia",
				"Israel", "U.S.A.", "United Kingdom", "USA", "Australia",
				"Singapore" };
		int posStart = 0;
		int posEnd = 0;
		char temp = tempAddr.charAt(posStart);
		String term = null;
		while (temp != '\n') {
			while (temp != ' ' && temp != '\n')// temp!='\n'
			{
				posStart++;
				// System.out.println("1:"+posStart);
				temp = tempAddr.charAt(posStart);
				// System.out.println("2:"+posStart);
			}
			term = tempAddr.substring(posEnd, posStart);
			// System.out.println(term);
			for (int i = 0; i < 11; i++) {
				if (term.trim().equalsIgnoreCase(check[i])) {
					bool = true;
					// System.out.println("isAddressInfo::here");
					break;
				}
			}
			if (temp != '\n')
				posStart++;
			posEnd = posStart;
			temp = tempAddr.charAt(posStart);
			// System.out.println("here!!");
		}
		return bool;
	}

	/*
	 * 判定是否是邮箱地址
	 */
	public static boolean isEmailInfo(String tempEmail) {
		boolean bool = false;
		int startPos = 0;
		while (tempEmail.charAt(startPos) != '\n'
				&& tempEmail.charAt(startPos) != ' ') {
			if (tempEmail.charAt(startPos) == '@') {
				bool = true;
				// System.out.println("isEmailInfo::here");
				break;
			}
			startPos++;
		}
		return bool;
	}

	/*
	 * 判定是否开始摘要的内容
	 */
	public static boolean isAbstractInfo(String tempAbstract) {
		boolean bool = false;
		return bool;
	}

	/**
	 * 获取格式化后的时间信息
	 * 
	 * @param dar
	 *            时间信息
	 * @return
	 * @throws Exception
	 */
	public static String dateFormat(Calendar calendar) throws Exception {
		if (null == calendar)
			return null;
		String date = null;
		try {
			String pattern = DATE_FORMAT;
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.format(calendar.getTime());
		} catch (Exception e) {
			throw e;
		}
		return date == null ? "" : date;
	}
/*	public static void createIndex(String title,String abstrack,String content) throws Exception
	{
//		String indexPath="D:\\Eclipse\\LunceDemo\\indexDoc";
		string2DocUtils obj = new string2DocUtils();
//		Analyzer analyzer=new StandardAnalyzer();
		Document document=obj.string2Document(title, abstrack, content);
		IndexWriter indexWriter=new IndexWriter(indexPath, analyzer,true , MaxFieldLength.LIMITED);
		indexWriter.addDocument(document);
		indexWriter.close();
	}
	public static void search(String searchString)throws Exception
	{
		
//		String queryString="";
		String[] feilds={"Title"};
		QueryParser queryParser=new MultiFieldQueryParser(feilds, analyzer);		
		org.apache.lucene.search.Query query=queryParser.parse(searchString);
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		Filter filter=null;
		TopDocs topDocs = indexSearcher.search(query, filter, 1000);
//		Filter filter = null;
//		TopDocs topDocs = indexSearcher.search(query, filter, 10000);
		System.out.println("总共有【" + topDocs.totalHits + "】条匹配结果");

//		IndexSearcher indexSearcher=new in
	}*/
	public static void main(String[] args) throws Exception 
	{
		String path = "";
		String path1 = "E:\\hfz\\现代信息检索\\TopConferences\\SIGIR\\pdf\\SIGIR";
		// search("Risky Business: Modeling and Exploiting Uncertainty in Information Retrieval");
		int[] No = new int[31];
		for (int j = 0; j < 31; j++) {
			No[j] = 1979 + j;
			
		if(No[j]>2006)
		{
			path = path1 + Integer.toString(No[j]);
			System.out.println("-----------" + path);
			File fileDir = new File(path);
			File[] htmlFiles = fileDir.listFiles();
			// System.out.println("-----------"+htmlFiles.length);
			for (int i = 0; i < htmlFiles.length; i++) {
				String name=htmlFiles[i].getName();
			//	&&name.startsWith("P")
				if (htmlFiles[i].isFile()
						&& name.endsWith(".pdf")&&name.startsWith("P")) 
				{
					// String name=htmlFiles[i].getName();
					String fpath = htmlFiles[i].getAbsolutePath();
					// System.out.println(htmlFiles[i].getAbsolutePath());
					System.out.println("===========第" + i
							+ "篇文章信息：=============");
					System.out.println(fpath);
					pdfParse(fpath);// 程序的入口，每次只处理一篇文档；如果需要可以修改此函数，使其返回文档的标题、摘要、内容；该函数共执行31*n次；

				}
			}
		}
		}

//		String path11 = "D:\\Eclipse\\LunceDemo\\dataSource\\SIGIR2008\\P115-SIGIR2008.pdf";
//		pdfParse(path11);
	}

}
