package irui;

import java.io.IOException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;

public class FuzzyTest {
	public static void main(String[] args) throws CorruptIndexException, LockObtainFailedException, IOException {
		RAMDirectory ram = new RAMDirectory();
		IndexWriter writer = new IndexWriter(ram,new SimpleAnalyzer(),true);
		Document doc1 = new Document();
		doc1.add(new Field("content","爱的敬意",Field.Store.YES,Field.Index.TOKENIZED));
		Document doc2 = new Document();
		doc2.add(new Field("content","爱的致意",Field.Store.YES,Field.Index.TOKENIZED));
		writer.addDocument(doc1);
		writer.addDocument(doc2);
		writer.flush();
		writer.close();
		IndexSearcher searcher = new IndexSearcher(ram);
		Term term = new Term("content","爱的敬意");
		FuzzyQuery fuzzy = null;
		fuzzy = new FuzzyQuery(term);
		Hits hits =	searcher.search(fuzzy);
		System.out.println(hits.length());
		System.out.println(hits);
	}

}
