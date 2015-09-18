package org.bit.demo;

import java.io.File;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;

public class DeleteCollection {

	public static void delete(IndexReader reader, String delurl) {
		File file = new File(delurl);
		delete(reader, file);
	}

	private static void delete(IndexReader reader, File file) {
		if (file.isDirectory()) {
			String[] files = file.list();
			for (int i = 0; i < files.length; i++)
				delete(reader, new File(file, files[i]));
		} else {
			System.out.println("deleting " + file);
			System.out.println(file.getPath());
			try {
				System.out.println(reader
						.delete(new Term("url", file.getPath())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}