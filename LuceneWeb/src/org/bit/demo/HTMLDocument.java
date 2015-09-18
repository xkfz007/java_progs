package org.bit.demo;

import java.io.*;
import org.apache.lucene.document.*;
import org.apache.lucene.demo.html.HTMLParser;

public class HTMLDocument {

	private HTMLDocument() {
	}

	public static Document Document(File f) throws IOException,
			InterruptedException {

		Document doc = new Document();
		doc.add(Field.Keyword("url", f.getPath()));
		doc.add(Field.Keyword("modified", DateField.timeToString(f
				.lastModified())));
		HTMLParser parser = new HTMLParser(f);
		doc.add(Field.Text("contents", parser.getReader()));
		doc.add(Field.UnIndexed("summary", parser.getSummary()));
		doc.add(Field.Text("title", parser.getTitle()));
		return doc;
	}

}

