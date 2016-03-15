package cn.com.agree.eteller.generic.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class FileUtil {
	private static ConfigUtil conf = new ConfigUtil("config/file_natp.properties");

	public static File saveFile(File src, String dest) throws Exception {
		return saveFile(new FileInputStream(src), dest);
	}

	public static File saveFile(InputStream in, String dest) throws Exception {
		OutputStream out = null;
		File destFile = new File(dest);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		try {
			out = new BufferedOutputStream(new FileOutputStream(destFile));
			byte[] buf = new byte[10240];
			int length;
			while ((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return destFile;
	}

	public static boolean deleteFile(String filePath) {
		File f = new File(filePath);
		if (f.exists()) {
			return f.delete();
		}
		return false;
	}

	public static void downloadFile(File file, String fileName, HttpServletResponse response) {
		if (!file.exists()) {
			return;
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO8859-1"));
			response.addHeader("content-length", Long.toString(file.length()));

			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] b = new byte[10240];
			int length = 0;
			while ((length = bis.read(b)) != -1) {
				bos.write(b, 0, length);
			}
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getNatpFileConfig(String key) {
		return conf.getString(key);
	}
}
