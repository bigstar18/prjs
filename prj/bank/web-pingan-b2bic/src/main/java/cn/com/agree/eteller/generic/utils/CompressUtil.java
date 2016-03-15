package cn.com.agree.eteller.generic.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarException;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.transaction.NotSupportedException;

public class CompressUtil {
	public static final String ALGORITHM_ZIP = "zip";
	public static final String ALGORITHM_JAR = "jar";
	private OutputStream out;
	private ZipOutputStream compressOut;
	private InputStream in;
	private ZipInputStream compressIn;
	private ZipFile compressFile;
	private File sourceFile;
	private String algorithm;

	public CompressUtil(File packFile) throws NotSupportedException {
		this(packFile, "zip");
	}

	public CompressUtil(File packFile, String algorithm) throws NotSupportedException {
		this.sourceFile = packFile;
		this.algorithm = algorithm;
		if ((!"zip".equals(algorithm)) && (!"jar".equals(algorithm))) {
			throw new NotSupportedException("不支持该种压缩算法：" + algorithm);
		}
	}

	public void packFilesByList(String destDir, List<String> packList, String packDir) throws FileNotFoundException, IOException {
		List<String> compressFileList = new ArrayList();
		try {
			if ("zip".equals(this.algorithm)) {
				this.compressOut = new ZipOutputStream(new FileOutputStream(new File(destDir)));
			} else if ("jar".equals(this.algorithm)) {
				this.compressOut = new JarOutputStream(new FileOutputStream(new File(destDir)));
			}
			for (String packFile : packList) {
				packFile = packFile.replace("\\", "/");
				try {
					File jarEntryFile = null;
					if ((packDir != null) && (packFile.endsWith("/*"))) {
						jarEntryFile = new File(packDir + "/" + packFile.substring(0, packFile.length() - 2));
						readSourceFile(jarEntryFile, packFile.substring(0, packFile.length() - 2), true, compressFileList);

						this.in.close();
						continue;
					}
					jarEntryFile = new File(packDir + "/" + packFile);

					this.in = new FileInputStream(jarEntryFile);

					ZipEntry compressEntry = null;
					if ("zip".equals(this.algorithm)) {
						compressEntry = new ZipEntry(packFile);
					} else if ("jar".equals(this.algorithm)) {
						compressEntry = new JarEntry(packFile);
					}
					this.compressOut.putNextEntry(compressEntry);

					byte[] buf = new byte[10240];
					int length;
					while ((length = this.in.read(buf)) != -1) {
						this.compressOut.write(buf, 0, length);
					}
					if (jarEntryFile.isFile()) {
						compressFileList.add(packFile);
					}
				} finally {
					this.in.close();
				}
				this.in.close();
			}
		} finally {
			close();
		}
	}

	public List<String> packFiles(String destDir, boolean includeRootDir) throws IOException {
		List<String> compressFileList = new ArrayList();
		try {
			if ("zip".equals(this.algorithm)) {
				this.compressOut = new ZipOutputStream(new FileOutputStream(new File(destDir)));
			} else if ("jar".equals(this.algorithm)) {
				this.compressOut = new JarOutputStream(new FileOutputStream(new File(destDir)));
			}
			readSourceFile(this.sourceFile, this.sourceFile.getName(), includeRootDir, compressFileList);
		} finally {
			close();
		}
		return compressFileList;
	}

	private void readSourceFile(File file, String fileName, boolean includeRootDir, List<String> compressFileList) throws IOException {
		fileName = fileName.replace("\\", "/");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File temp : files) {
				readSourceFile(temp, fileName + File.separator + temp.getName(), includeRootDir, compressFileList);
			}
		} else {
			try {
				this.in = new FileInputStream(file);
				if ((!includeRootDir) && ((fileName.contains("/")) || (fileName.contains("\\")))) {
					int index = fileName.indexOf("/");
					index = index == -1 ? fileName.indexOf("\\") : index;
					fileName = fileName.substring(index + 1);
				}
				System.out.println("压缩的文件名：" + fileName);

				ZipEntry compressEntry = null;
				if ("zip".equals(this.algorithm)) {
					compressEntry = new ZipEntry(fileName);
				} else if ("jar".equals(this.algorithm)) {
					compressEntry = new JarEntry(fileName);
				}
				this.compressOut.putNextEntry(compressEntry);

				byte[] buf = new byte[10240];
				int length;
				while ((length = this.in.read(buf)) != -1) {
					this.compressOut.write(buf, 0, length);
				}
				compressFileList.add(fileName);
			} finally {
				this.in.close();
			}
		}
	}

	public List<String> unZipFile(String directory) throws JarException, IOException {
		List<String> unZipFileList = new ArrayList();
		if ("zip".equals(this.algorithm)) {
			this.compressFile = new ZipFile(this.sourceFile);
			this.compressIn = new ZipInputStream(new FileInputStream(this.sourceFile));
		} else if ("jar".equals(this.algorithm)) {
			this.compressFile = new JarFile(this.sourceFile);
			this.compressIn = new JarInputStream(new FileInputStream(this.sourceFile));
		}
		ZipEntry entry = null;
		try {
			while ((entry = "jar".equals(this.algorithm) ? ((JarInputStream) this.compressIn).getNextJarEntry()
					: "zip".equals(this.algorithm) ? this.compressIn.getNextEntry() : null) != null) {
				System.out.println("解压文件：" + entry.getName());

				File f = new File(directory + File.separator + entry.getName());
				if (entry.isDirectory()) {
					f.mkdirs();
				} else {
					if (!f.getParentFile().exists()) {
						f.getParentFile().mkdirs();
					}
					f.createNewFile();
					this.in = this.compressFile.getInputStream(entry);
					this.out = new FileOutputStream(f);

					byte[] buf = new byte[10240];
					int len;
					while ((len = this.in.read(buf)) != -1) {
						this.out.write(buf, 0, len);
					}
					this.in.close();
					this.out.close();
				}
				if (!entry.isDirectory()) {
					unZipFileList.add(entry.getName());
				}
			}
		} finally {
			close();
		}
		return unZipFileList;
	}

	public void close() throws IOException {
		if (this.in != null) {
			this.in.close();
		}
		if (this.out != null) {
			this.out.close();
		}
		if (this.compressIn != null) {
			this.compressIn.close();
		}
		if (this.compressOut != null) {
			this.compressOut.close();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.print("请选择你的操作：\n1、压缩文件\n2、解压缩文件\n请选择你的操作：");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String selection = br.readLine();
		switch (Integer.parseInt(selection)) {
		case 1:
			System.out.print("请输入你要压缩的文件路径：");
			br = new BufferedReader(new InputStreamReader(System.in));
			String compressFile = br.readLine();

			File f = new File(compressFile);
			List<String> fileList = new CompressUtil(f).packFiles(f.getParentFile().getAbsolutePath() + File.separator + "test.zip", true);
			System.out.println("压缩完成！");
			System.out.println("压缩的文件列表：" + fileList);
			break;
		case 2:
			System.out.println("请输入你要解压的压缩文件的路径：");
			br = new BufferedReader(new InputStreamReader(System.in));
			compressFile = br.readLine();

			f = new File(compressFile);
			new CompressUtil(f).unZipFile(f.getParentFile().getAbsolutePath());
			System.out.println("解压完成！");
		}
		br.close();
	}
}
