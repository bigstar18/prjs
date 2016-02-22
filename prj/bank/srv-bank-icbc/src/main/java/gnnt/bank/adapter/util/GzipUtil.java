package gnnt.bank.adapter.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {
	public static void upZip(String srcPath, String desPath) throws FileNotFoundException, IOException {
		String inFilename = srcPath;
		GZIPInputStream in = new GZIPInputStream(new FileInputStream(inFilename));

		String outFilename = desPath;
		OutputStream out = new FileOutputStream(outFilename);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			// int len;
			out.write(buf, 0, len);
		}

		in.close();
		out.close();
	}

	public static void zipFile(String source, String target) {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		GZIPOutputStream gzout = null;
		byte[] buf = new byte[1024];
		try {
			try {
				fin = new FileInputStream(source);
				fout = new FileOutputStream(target);
				gzout = new GZIPOutputStream(fout);
				int num;
				while ((num = fin.read(buf)) != -1) {
					// int num;
					gzout.write(buf, 0, num);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (gzout != null) {
					gzout.close();
				}
				if (fout != null) {
					fout.close();
				}
				if (fin != null)
					fin.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			upZip("D:\\icbc-sdsg\\adapter\\dayData\\B_DTL01_20090831.gz", "D:\\icbc-sdsg\\adapter\\dayData\\B_DTL01_20090831");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		zipFile("D:\\tz\\F_STL03_20140321", "D:\\tz\\F_STL03_20140321.gz");
	}
}