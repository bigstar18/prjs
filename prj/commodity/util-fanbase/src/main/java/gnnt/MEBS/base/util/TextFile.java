package gnnt.MEBS.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class TextFile extends ArrayList {
	public static String read(String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		while ((s = in.readLine()) != null) {
			sb.append(s);
			sb.append("\n");
		}
		in.close();
		return sb.toString();
	}

	public static void write(String fileName, String text) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		out.print(text);
		out.close();
	}

	public TextFile(String fileName) throws IOException {
		super(Arrays.asList(read(fileName).split("\n")));
	}

	public TextFile() {
	}

	public void write(String fileName) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		for (int i = 0; i < size(); i++) {
			out.println(get(i));
		}
		out.close();
	}

	public String toText() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			sb.append((String) get(i));
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		TextFile text = new TextFile("C:\\Tomcat5.0\\LICENSE");
		System.out.print(text.toText());
	}
}
