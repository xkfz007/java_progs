package org.bit.demo;

import java.io.*;

public class FileOperation {
	public static long countFileNum(File file) {
		long num = 0;
		if (file.isDirectory()) {
			String[] files = file.list();
			for (int i = 0; i < files.length; i++)
				num = num + countFileNum(new File(file, files[i]));
		} else {
			num = num + 1;
		}
		return num;
	}

	public static String getSummary(File f) {
		StringBuffer s = new StringBuffer();
		long length = f.length();
		int summaryLength = 0;

		try {
			FileInputStream fs = new FileInputStream(f);
			DataInputStream in = new DataInputStream(fs);

			if (length < 200)
				summaryLength = (int) length;
			else
				summaryLength = 200;

			for (int i = 0; i < summaryLength; i++) {
				char aa = in.readChar();
				s = s.append(aa);
			}
			in.close();
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (EOFException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		s.append("...");
		String p = s.toString();
		try {
			p = new String(p.getBytes("UTF-16"), "GB2312");
		} catch (Exception e) {
		}
		return p.trim();
	}

	public static String getLinkUrl(String url, String realpath) {
		int length = realpath.length();
		String link = url.substring(length);
		return link;
	}
}

