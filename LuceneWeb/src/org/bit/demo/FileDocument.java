package org.bit.demo;

import java.io.File;
import java.io.Reader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateField;

public class FileDocument {

	private FileDocument() {
	}

	public static Document Document(File f) throws FileNotFoundException {

		Document doc = new Document();
		doc.add(Field.Keyword("url", f.getPath()));
		doc.add(Field.Keyword("modified", DateField.timeToString(f
				.lastModified())));

		FileInputStream is = new FileInputStream(f);
		Reader reader = new BufferedReader(new InputStreamReader(is));
		doc.add(Field.Text("contents", reader));
		doc.add(Field.Keyword("summary", FileOperation.getSummary(f)));
		doc.add(Field.Text("title", f.getName()));
		return doc;
	}

}

