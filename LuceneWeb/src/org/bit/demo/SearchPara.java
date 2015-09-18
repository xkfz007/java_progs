package org.bit.demo;

public class SearchPara {
	private int startindex;

	private int maxresults;

	private int thispage;

	private int page;

	public SearchPara() {
	}

	public SearchPara(int startindex, int maxresults, int thispage, int page) {
		this.startindex = startindex;
		this.maxresults = maxresults;
		this.thispage = thispage;
		this.page = page;
	}

	public int getStartindex() {
		return startindex;
	}

	public int getMaxresults() {
		return maxresults;
	}

	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}

	public void setMaxresults(int maxresults) {
		this.maxresults = maxresults;
	}

	public int getThispage() {
		return thispage;
	}

	public void setThispage(int thispage) {
		this.thispage = thispage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}