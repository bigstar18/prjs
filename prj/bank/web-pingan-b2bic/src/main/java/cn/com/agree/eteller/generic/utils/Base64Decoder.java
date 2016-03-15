package cn.com.agree.eteller.generic.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Base64Decoder extends FilterInputStream {
	private static final char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
	private static final int[] ints = new int[''];
	private int charCount;
	private int carryOver;

	static {
		for (int i = 0; i < 64; i++) {
			ints[chars[i]] = i;
		}
	}

	public Base64Decoder(InputStream in) {
		super(in);
	}

	public int read() throws IOException {
		int x = 0;
		do {
			x = this.in.read();
			if (x == -1) {
				return -1;
			}
		} while (

		Character.isWhitespace((char) x));
		this.charCount += 1;
		if (x == 61) {
			return -1;
		}
		x = ints[x];

		int mode = (this.charCount - 1) % 4;
		if (mode == 0) {
			this.carryOver = (x & 0x3F);
			return read();
		}
		if (mode == 1) {
			int decoded = (this.carryOver << 2) + (x >> 4) & 0xFF;
			this.carryOver = (x & 0xF);
			return decoded;
		}
		if (mode == 2) {
			int decoded = (this.carryOver << 4) + (x >> 2) & 0xFF;
			this.carryOver = (x & 0x3);
			return decoded;
		}
		if (mode == 3) {
			int decoded = (this.carryOver << 6) + x & 0xFF;
			return decoded;
		}
		return -1;
	}

	public int read(byte[] b, int off, int len) throws IOException {
		int i = 0;
		for (; i < len; i++) {
			int x = read();
			if ((x == -1) && (i == 0)) {
				return -1;
			}
			if (x == -1) {
				break;
			}
			b[(off + i)] = ((byte) x);
		}
		return i;
	}

	public static String decode(String encoded) {
		byte[] bytes = (byte[]) null;
		try {
			bytes = encoded.getBytes("8859_1");
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
		Base64Decoder in = new Base64Decoder(new ByteArrayInputStream(bytes));

		ByteArrayOutputStream out = new ByteArrayOutputStream((int) (bytes.length * 0.67D));
		try {
			byte[] buf = new byte[4096];
			int bytesRead;
			while ((bytesRead = in.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
			out.close();

			return out.toString("8859_1");
		} catch (IOException ignored) {
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(decode("YWdyZWUxMjM="));
		System.out.println(decode("ZGIyaW5zdDI="));
		System.out.println(decode("emh1ODhqaWU="));
	}
}
