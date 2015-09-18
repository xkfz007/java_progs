package org.bit.demo;

import org.bit.demo.FileOperation;
import java.io.File;

public class DocCollection {
	private String name;

	private String url;

	private String imgurl;

	private long filenum;

	private boolean isIndexed;

	public DocCollection() {
	}

	public DocCollection(String name, String url, boolean isIndexed) {
		this.isIndexed = isIndexed;
		this.name = name;
		this.url = url;
		this.filenum = FileOperation.countFileNum(new File(url));
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public long getfileNum() {
		return filenum;
	}

	public boolean isIndexed() {
		return isIndexed;
	}

	public void setToIndexed() {
		this.isIndexed = true;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
}