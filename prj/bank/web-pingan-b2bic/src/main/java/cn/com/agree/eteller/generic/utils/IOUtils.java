package cn.com.agree.eteller.generic.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;

public class IOUtils {

	public static boolean convertEncoding(File file, String srcEncode, String destEncode, final String ext) throws IOException {
		String newName = new String(file.getName().getBytes(srcEncode), destEncode);
		file.renameTo(new File((new StringBuilder(String.valueOf(file.getParentFile().getCanonicalPath()))).append("/").append(newName).toString()));
		if (file.isFile()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), srcEncode));
			StringBuilder content = new StringBuilder();
			String buf;
			while ((buf = br.readLine()) != null)
				content.append((new StringBuilder(String.valueOf(buf))).append(System.getProperty("line.separator")).toString());
			br.close();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)), destEncode));
			pw.print(content.toString());
			pw.close();
		} else {
			File list[] = (File[]) null;
			if (ext != null)
				list = file.listFiles(new FileFilter() {

					public boolean accept(File pathname) {
						return pathname.getName().endsWith(ext) || pathname.isDirectory();
					}

				});
			else
				list = file.listFiles();
			File afile[];
			int j = (afile = list).length;
			for (int i = 0; i < j; i++) {
				File f = afile[i];
				convertEncoding(new File((new StringBuilder(String.valueOf(file.getCanonicalPath()))).append("/").append(f.getName()).toString()),
						srcEncode, destEncode, ext);
			}

		}
		return true;
	}

	public static boolean convertEncoding(File file, String srcEncode, String destEncode) throws IOException {
		return convertEncoding(file, srcEncode, destEncode, null);
	}

	public static Object deepClone(Object obj) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(buf);
		oos.writeObject(obj);

		Object newObj = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(buf.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		newObj = ois.readObject();

		buf.close();
		oos.close();
		bais.close();
		ois.close();

		return newObj;
	}

	public static void split(String file, int size) throws IOException {
		InputStream in = null;
		OutputStream out = null;

		in = new BufferedInputStream(new FileInputStream(file));
		DecimalFormat fmt = new DecimalFormat("000");

		int index = 0;
		int length = 0;
		out = new BufferedOutputStream(new FileOutputStream(file + "." + fmt.format(index++)));
		int data;
		while ((data = in.read()) != -1) {
			out.write(data);
			length++;
			if (length == size) {
				out.close();
				length = 0;
				out = new BufferedOutputStream(new FileOutputStream(file + "." + fmt.format(index++)));
			}
		}
		in.close();
		out.close();
	}

	public static void join(String file) throws IOException {
		InputStream in = null;
		OutputStream out = null;

		DecimalFormat fmt = new DecimalFormat("000");
		int index = 1;
		String realName = file.substring(0, file.lastIndexOf("."));
		in = new BufferedInputStream(new FileInputStream(realName + "." + fmt.format(index++)));
		out = new BufferedOutputStream(new FileOutputStream(realName));
		for (;;) {
			byte[] data = new byte[4096];
			int length;
			while ((length = in.read(data)) != -1) {
				out.write(data, 0, length);
			}
			in.close();
			File next = new File(realName + "." + fmt.format(index++));
			if (!next.exists()) {
				break;
			}
			in = new BufferedInputStream(new FileInputStream(next));
		}
		in.close();
		out.close();
	}

	public static void copy(String src, String dest) throws IOException {
		File srcFile = new File(src);
		if (srcFile.isDirectory()) {
			if (srcFile.getParentFile().equals(new File(dest))) {
				throw new RuntimeException("源路径与目标路径重复");
			}
			File[] list = srcFile.listFiles();
			for (File f : list) {
				copy(f.getCanonicalPath(), dest + (f.isDirectory() ? "/" + f.getParentFile().getName() : "") + "/" + f.getName());
			}
		} else {
			File destFile = new File(dest);
			if (!destFile.exists()) {
				if (!destFile.getName().equals(srcFile.getName())) {
					destFile.mkdirs();
					destFile = new File(dest + "/" + srcFile.getName());
					destFile.createNewFile();
				} else {
					if (!destFile.getParentFile().exists()) {
						destFile.getParentFile().mkdirs();
					}
					destFile.createNewFile();
				}
			} else {
				destFile = new File(dest + "/" + srcFile.getName());
				destFile.createNewFile();
			}
			InputStream in = new FileInputStream(srcFile);
			Object out = new FileOutputStream(destFile);

			byte[] buf = new byte[10240];
			int length;
			while ((length = in.read(buf)) != -1) {
				((OutputStream) out).write(buf, 0, length);
			}
			in.close();
			((OutputStream) out).close();
		}
	}

	public static void copy(File src, File dest) throws IOException {
		copy(src.getCanonicalFile(), dest.getCanonicalFile());
	}

	public static String printHex(String file) {
		StringBuilder sb = new StringBuilder();
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] buf = new byte[1024];

			byte[] b = new byte[0];
			int length;
			while ((length = in.read(buf)) != -1) {
				b = Arrays.copyOf(b, b.length + length);
				System.arraycopy(buf, 0, b, b.length - length, length);
			}
			sb.append(printHex(b));

		} catch (Exception e) {
			e.printStackTrace();
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void printText(String file, String encoding) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			String str = null;
			while ((str = br.readLine()) != null) {
				System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("读取文件出现异常");
		}
	}

	public static String printHex(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			if ((i != 0) && (i % 16 == 0)) {
				sb.append(System.getProperty("line.separator"));
			}
			String hex = Integer.toHexString(b[i]);
			if (b[i] <= 15) {
				hex = hex + "0" + hex;
			}
			sb.append(hex.substring(hex.length() - 2) + " ");
		}
		return sb.toString();
	}

	public static String readAll(File file, String encoding) {
		String line = null;
		BufferedReader br = null;
		StringBuilder content = new StringBuilder();
		String lineSeparator = System.getProperty("line.separator");
		try {
			if (encoding != null) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			} else {
				br = new BufferedReader(new FileReader(file));
			}
			while ((line = br.readLine()) != null) {
				content.append(line).append(lineSeparator);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}

	public static String readAll(File file) {
		return readAll(file, null);
	}

	public static void main(String[] args) throws IOException {
		File file = new File(
				"C:\\Users\\Administrator\\Documents\\Workspaces\\MyEclipse 9\\eteller_new\\WebRoot\\css\\jquery-ui-1.8.21.Cupertino\\js\\jquery.ui.datepicker-zh-CN.js");
		convertEncoding(file, "GBK", "UTF-8");
	}
}
