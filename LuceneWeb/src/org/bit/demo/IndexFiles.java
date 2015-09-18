package org.bit.demo;

import java.io.*;
import java.util.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.index.*;
import org.apache.struts.upload.*;

public class IndexFiles {
	public static String beginIndex(String url, boolean create,
			String serverurl, String collectionname) {
		try {
			Date start = new Date();
			String imgurl = null;

			if (!new File(Configuration.INDEX_STORE_PATH).exists())
				new File(Configuration.INDEX_STORE_PATH).mkdirs();

			IndexWriter writer = new IndexWriter(
					Configuration.INDEX_STORE_PATH, new StandardAnalyzer(),
					create);
			writer.maxFieldLength = 1000000;

			indexCollection(writer, url, new File(url), serverurl,
					collectionname);

			writer.optimize();
			writer.close();

			Date end = new Date();

			System.out.print(end.getTime() - start.getTime());
			System.out.println(" total milliseconds");

		} catch (Exception e) {
			System.out.println(" caught a " + e.getClass()
					+ "\n with message: " + e.getMessage());
		}
		return null;
	}

	private static void indexCollection(IndexWriter writer, String url,
			File file, String serverurl, String collectionname)
			throws Exception {
		if (file.isDirectory()) {
			String[] files = file.list();
			for (int i = 0; i < files.length; i++)
				indexCollection(writer, url, new File(file, files[i]),
						serverurl, collectionname);
		} else {
			System.out.println("adding " + file);
			indexFile(writer, url, file, checkFileType(file), serverurl,
					collectionname);
		}
	}

	private static void indexFile(IndexWriter writer, String url, File file,
			int type, String serverurl, String collectionname) throws Exception {
		File f = null;
		switch (type) {
		case 0:
			f = uploadFile(file, serverurl, url, collectionname);
			writer.addDocument(FileDocument.Document(f));
			break;
		case 1:
			f = uploadFile(file, serverurl, url, collectionname);
			writer.addDocument(HTMLDocument.Document(f));
			break;
		case 2:
			break;
		case -1:
			f = uploadFile(file, serverurl, url, collectionname);
			System.out.println("File type not supported..");
		default:
			break;
		}
	}

	private static int checkFileType(File file) {
		if (file.getPath().endsWith(".html") || file.getPath().endsWith(".htm"))
			return 1;
		else if (file.getPath().endsWith(".txt"))
			return 0;
		else
			return -1;
	}

	private static File uploadFile(File file, String serverurl, String url,
			String collectionname) {
		String absolutePath = file.getAbsolutePath();
		int urllength = url.length();
		String storepath = absolutePath.substring(urllength);

		StringBuffer storeabsolutepath = new StringBuffer();
		storeabsolutepath.append(serverurl).append("/collection").append(
				"/" + collectionname).append("/" + storepath);

		String s = storeabsolutepath.toString();

		FormFile df = new DiskFile(file.getAbsolutePath());
		try {
			String path = serverurl + file.getName();
			path = new File(s).getParent();
			if (null != path) {
				if (!new File(path).exists()) {
					new File(path).mkdirs();
				}
			}

			InputStream stream = df.getInputStream();
			OutputStream bos = new FileOutputStream(s);

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
			stream.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return new File(s);
	}
}