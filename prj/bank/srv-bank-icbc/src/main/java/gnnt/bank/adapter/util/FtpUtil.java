package gnnt.bank.adapter.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class FtpUtil {
	private FtpClient ftpClient = null;

	public void connectServer(String server, String user, String password, String path) throws IOException {
		this.ftpClient = new FtpClient();
		this.ftpClient.openServer(server);
		this.ftpClient.login(user, password);
		if (path.length() != 0) {
			this.ftpClient.cd(path);
		}
		this.ftpClient.binary();
	}

	public long upload(String filename, String newname) throws Exception {
		long result = 0L;
		TelnetOutputStream os = null;
		FileInputStream is = null;
		try {
			File file_in = new File(filename);
			if (!file_in.exists()) {
				return -1L;
			}
			os = this.ftpClient.put(newname);
			result = file_in.length();
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		if (is != null) {
			is.close();
		}
		if (os != null) {
			os.close();
		}
		return result;
	}

	public long upload(String filename) throws Exception {
		String newname = "";
		if (filename.indexOf("/") > -1) {
			newname = filename.substring(filename.lastIndexOf("/") + 1);
		} else {
			newname = filename;
		}
		return upload(filename, newname);
	}

	public long download(String filename, String newfilename) throws Exception {
		long result = 0L;
		TelnetInputStream is = null;
		FileOutputStream os = null;
		try {
			is = this.ftpClient.get(filename);
			File outfile = new File(newfilename);
			os = new FileOutputStream(outfile);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
				result += c;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return result;
	}

	public List getFileList(String path) {
		List list = new ArrayList();
		try {
			DataInputStream dis = new DataInputStream(this.ftpClient.nameList(path));
			String filename = "";
			while ((filename = dis.readLine()) != null) {
				list.add(filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void closeServer() throws IOException {
		try {
			if (this.ftpClient != null) {
				this.ftpClient.closeServer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		FtpUtil ftp = new FtpUtil();
		try {
			ftp.connectServer("172.16.2.1", "gnnt/lixg", "Aaaa1234", "");

			List list = ftp.getFileList(".");
			for (int i = 0; i < list.size(); i++) {
				String filename = (String) list.get(i);
				System.out.println(filename);
				ftp.download(filename, "E:/" + filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ftp.closeServer();
		}
	}
}
