package gnnt.trade.bank.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class FileReader {
	private String fileUrl;
	private String fileName;
	private String url;

	public FileReader(String url) {
		url = url.replaceAll("\\\\", "/");
		this.url = url;
		this.fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
		this.fileUrl = url.substring(0, url.indexOf(this.fileName));
	}

	public FileReader(String fileUrl, String fileName) {
		fileUrl = fileUrl.replaceAll("\\\\", "/");
		this.fileUrl = fileUrl;
		this.fileName = fileName;
		this.url = (fileUrl + "/" + fileName);
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getUrl() {
		return this.url;
	}

	public boolean writeToFile(String dataLine, boolean isAppendMode, boolean isNewLine) throws FileNotFoundException, IOException {
		DataOutputStream dos = null;
		if (isNewLine) {
			dataLine = dataLine + "\n";
		}
		try {
			if (isAppendMode) {
				dos = new DataOutputStream(new FileOutputStream(this.url, true));
			} else {
				dos = new DataOutputStream(new FileOutputStream(new File(this.url)));
			}
			dos.write(dataLine.getBytes());
		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (dos != null) {
				dos.close();
				dos = null;
			}
		}
		return true;
	}

	public boolean touchFile(String dataLine) {
		boolean flag = false;
		try {
			if (isFileExists()) {
				flag = writeToFile(dataLine, true, true);
			} else {
				flag = writeToFile(dataLine, true, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String readFromFile(int n) throws FileNotFoundException, IOException, Exception {
		int j = 1;
		if (n > 0) {
			j = n;
		}
		int rowcol = 0;
		String DataLine = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.url))));
			for (int i = 0; i < j; i++) {
				DataLine = br.readLine();
				if (DataLine != null) {
					rowcol++;
				} else {
					DataLine = null;
					throw new Exception("文件>" + (this.fileName == null ? this.url : this.fileName) + "<只有>" + rowcol + "<行,不存在您要的第>" + n + "<行");
				}
			}
		} catch (FileNotFoundException ex) {
			System.out.println("没有找到>" + this.url + "<文件");
			throw ex;
		} catch (IOException ex) {
			System.out.println("读取>" + this.url + "<文件数据流异常");
			throw ex;
		} catch (Exception ex) {
			System.out.println(ex);
			throw ex;
		} finally {
			if (br != null) {
				br.close();
				br = null;
			}
		}
		return DataLine;
	}

	public boolean isFileExists() {
		boolean flag = false;
		File file = new File(this.url);
		if (file.exists()) {
			flag = true;
		}
		return flag;
	}

	public boolean deleteFile() {
		File file = new File(this.url);
		if (isFileExists()) {
			return file.delete();
		}
		return true;
	}

	public Vector<String> fileToVector() {
		Vector<String> v = new Vector();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.url)), "UTF-8"));
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				v.addElement(inputLine.trim());
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			try {
				if (br != null) {
					br.close();
					br = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			try {
				if (br != null) {
					br.close();
					br = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return v;
	}

	public void vectorToFile(Vector<String> v) {
		for (int i = 0; i < v.size(); i++) {
			try {
				writeToFile((String) v.elementAt(i), true, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void copyUniqueElements() {
		Vector<String> v = fileToVector();
		v = removeDuplicates(v, true);
		for (int i = 0; i < v.size(); i++) {
			try {
				writeToFile((String) v.elementAt(i), false, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Vector<String> removeDuplicates(Vector<String> vector, boolean ignoreCase) {
		int i = 0;
		int j = 0;
		boolean duplicates = false;
		Vector<String> v = new Vector();
		for (i = 0; i < vector.size(); i++) {
			duplicates = false;
			for (j = i + 1; j < vector.size(); j++) {
				if (ignoreCase) {
					if (((String) vector.elementAt(i)).toString().trim().equals(((String) vector.elementAt(j)).toString().trim())) {
						duplicates = true;
						break;
					}
				} else if (((String) vector.elementAt(i)).toString().trim().equalsIgnoreCase(((String) vector.elementAt(j)).toString().trim())) {
					duplicates = true;
					break;
				}
			}
			if (!duplicates) {
				v.addElement(((String) vector.elementAt(i)).toString().trim());
			}
		}
		return v;
	}

	public boolean mkdir() {
		boolean flag = false;
		if ((this.fileUrl == null) || (this.fileUrl.trim().length() == 0)) {
			return true;
		}
		File f = new File(this.fileUrl);
		try {
			flag = f.exists();
			if (flag) {
				System.out.println("文件夹" + this.fileUrl + "已存在");
			} else {
				flag = f.mkdirs();
				if (flag) {
					System.out.println("创建文件夹" + this.fileUrl + "成功");
				} else {
					System.out.println("创建文件夹" + this.fileUrl + "失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public Vector<String> getFiles() {
		Vector<String> result = new Vector();
		String files = this.fileUrl;
		System.out.println(files);
		File file = new File(files);
		String[] strs = file.list();
		for (String str : strs) {
			result.add(str);
		}
		return result;
	}

	public String help() {
		String str = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("FileReader(String url)构造方法，参数url为文件路径" + str);
		sb.append("FileReader(String fileUrl,String fileName) 构造方法，fileUrl文件的路径，fileName文件名" + str);
		sb.append("writeToFile(String dataLine,boolean isAppendMode, boolean isNewLine) 向文件中追加信息 dataLine 添加的信息，isAppendMode 是追加还是替换 ");
		sb.append("touchFile(String dataLine) 创建文件，如果文件已经存在，则向文件中追加 dataLine" + str);
		sb.append("readFromFile(int n) 读取文件的第 n 行" + str);
		sb.append("isFileExists() 判断文件是否存在" + str);
		sb.append("fileToVector() 读取文件中的信息" + str);
		sb.append("vectorToFile(Vector<String> v) 将信息添加到文件中" + str);
		sb.append("copyUniqueElements() 将文件去掉重复行" + str);
		sb.append("mkdir() 创建文件的路径" + str);
		return sb.toString();
	}

	public static void main(String[] args) {
		FileReader fr = new FileReader("lzx/fds/wer.oui\\q");
		fr.getFiles();
	}
}
