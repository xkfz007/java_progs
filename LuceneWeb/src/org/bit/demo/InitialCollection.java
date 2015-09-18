package org.bit.demo;

import java.util.ArrayList;
import org.bit.demo.DocCollection;

public class InitialCollection {
	private ArrayList collectionList;

	private boolean everIndexed;

	public InitialCollection() {
		collectionList = new ArrayList();
		this.everIndexed = false;
	}

	public void set(int pos, DocCollection d) {
		collectionList.set(pos, d);
	}

	public void add(DocCollection d) {
		collectionList.add(d);
	}

	public boolean isEmpty() {
		return collectionList.isEmpty();
	}

	public int size() {
		return collectionList.size();
	}

	public DocCollection get(int i) {
		return (DocCollection) collectionList.get(i);
	}

	public int indexedNum() {
		int num = 0;
		for (int i = 0; i < collectionList.size(); i++) {
			DocCollection d = (DocCollection) collectionList.get(i);
			if (d.isIndexed())
				num++;
		}
		return num;
	}

	public void delete(int index) {
		collectionList.remove(index);
	}

	public void everIndexed() {
		this.everIndexed = true;
	}

	public boolean getEverIndexed() {
		return this.everIndexed;
	}
}