package project;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;

public class CacheList {
	private int cache_Size = 5;

	private int cache_StartPage = 1;

	private String QueryWord = "";

	private int PageSize = 10;

	private int PageCount;

	public CacheList(String QueryWord, int pageCout, int cache_StartPage) {
		this.QueryWord = QueryWord;
		this.PageCount = pageCout;
		this.cache_StartPage = cache_StartPage;
	}

	private List CacheDoc;

	public String getQueryWord() {
		return QueryWord;
	}

	public void setQueryWord(String queryWord) {
		QueryWord = queryWord;
	}

	public int getCache_StartPage() {
		return cache_StartPage;
	}

	public void setCache_StartPage(int cache_StartPage) {
		this.cache_StartPage = cache_StartPage;
	}

	public int getCache_Size() {
		return cache_Size;
	}

	public void setCache_Size(int cache_Size) {
		this.cache_Size = cache_Size;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public int getPageCount() {
		return PageCount;
	}

	public void setPageCount(int pageCount) {
		PageCount = pageCount;
	}

	// 添加缓存
	public void AddCache(Document doc) {
		CacheDoc.add(doc);
	}

	// flushCache
	public void flushCache() {
		CacheDoc.clear();
	}

	// 判断QueryWord
	public boolean CompareQueryWord(String word) {
		if (this.QueryWord.equals(word)) {
			return true;
		} else {
			return false;
		}
	}

	// 判断指定位置是否在缓存中
	public boolean InCache(int pageIndex, String queryword) {
		if (pageIndex >= cache_StartPage && pageIndex < (cache_StartPage + cache_Size) && this.CompareQueryWord(queryword)) {
			return true;
		}
		this.setQueryWord(queryword);
		return false;
	}

	// 读取指定页码的缓存
	public List ReadCache(int pageIndex) {
		int startIndex = (pageIndex - 1) % cache_Size * PageSize;
		if (startIndex > CacheDoc.size()) {
			startIndex = 0;
		}
		int endIndex = startIndex + PageSize;
		if (endIndex > CacheDoc.size()) {
			endIndex = CacheDoc.size();
		}
		if (endIndex >= startIndex) {
			return CacheDoc.subList(startIndex, endIndex);
		} else {
			return new ArrayList();
		}
	}
}