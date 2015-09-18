package project;

import java.util.List;

import org.apache.lucene.document.Document;

public class QueryResult {
	private int recordCount;
	private List<Document> recordList;

	public QueryResult(int recordCount, List<Document> recordList) {
		super();
		this.recordCount = recordCount;
		this.recordList = recordList;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public List<Document> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<Document> recordList) {
		this.recordList = recordList;
	}

}
