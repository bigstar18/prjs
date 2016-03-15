// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name: ConfigManager.java

package cn.com.pingan.b2bic.web.impl;

import cn.com.agree.server.component.config.IPasswordReader;
import cn.com.pingan.b2bic.web.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;
import org.dom4j.io.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class ConfigManager implements IConfigManager {
	static class Log4jDTDResolver implements EntityResolver {

		private String basePath;

		public InputSource resolveEntity(String publicId, String systemId) {
			if (systemId.endsWith("log4j.dtd"))
				try {
					File f = new File(basePath, "log4j.dtd");
					InputStream ins = new FileInputStream(f);
					return new InputSource(ins);
				} catch (FileNotFoundException _ex) {
					return null;
				}
			else
				return null;
		}

		public Log4jDTDResolver(String basePath) {
			this.basePath = basePath;
		}
	}

	private static final Log logger = LogFactory.getLog(ConfigManager.class);
	private static final String CORPFILE = "cfgcorp.xml";
	private static final String PATH_BANK_IN = "services/service[@id='bankIn']";
	private static final String PATH_BANK_OUT = "servers/server[@id='bankOut']";
	private static final String PATH_BANK_FTP = "ftpServers/server[@id='bankFtp']";
	private static final String BANKFILE = "cfgbank.xml";
	private static final String PATH_CORP_IN = "services/service[@id='corpIn']";
	private static final String PATH_CORP_OUT = "servers/server[@id='corpOut']";
	private static final String PATH_CORP_FTP = "ftpServers/server[@id='corpFtp']";
	private static final String SIGNFILE = "cfgsign.xml";
	private static final String LOGFILE = "log4j.xml";
	private static final String BAKFILESUFF = ".bl";
	private static final String TMPFILESUFF = ".tmp";
	private static final int DEFAULT_TIMEOUT = 60000;
	private static final int DEFAULT_MAXPOOLSIZE = 100;
	private static final int DEFAULT_PORT_SFTP = 22;
	private static final int DEFAULT_PORT_FTP = 21;
	private static final String DEFAULT_PROTOCOL = "tcp";
	private static final int DEFAULT_DOWNMODE = 0;
	private String basePath;
	private static final String DEFAULT_TRAN_CODE = "000000";
	private IPasswordReader pwdReader;

	public ConfigManager() {
		basePath = "configuration";
	}

	private String readPwdField(String pwd) {
		if (pwdReader == null)
			return pwd;
		else
			return pwdReader.read(pwd);
	}

	private String writePwdField(String pwd) {
		if (pwdReader == null)
			return pwd;
		else
			return pwdReader.write(pwd);
	}

	public void load(BankVo bankvo) throws Exception {
		String cfgFile = "cfgbank.xml";
		File f = new File(basePath, cfgFile);
		checkFile(f);
		Document doc = readDoc(f);
		Element root = doc.getRootElement();
		Element eIn = (Element) root.selectSingleNode("services/service[@id='bankIn']");
		if (eIn == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：bankIn配置项缺失").toString());
		ServerInVo invo = readServerInVo(eIn);
		bankvo.setBankIn(invo);
		Element eOut = (Element) root.selectSingleNode("servers/server[@id='bankOut']");
		if (eOut == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：bankOut配置项缺失").toString());
		ServerOutVo outVo = readServerOutVo(eOut);
		bankvo.setBankOut(outVo);
		Element eFtp = (Element) root.selectSingleNode("ftpServers/server[@id='bankFtp']");
		if (eFtp == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：bankFtp配置项缺失").toString());
		FtpVo ftpVo = readFtpVo(eFtp);
		if (ftpVo.getPort() <= 0)
			ftpVo.setPort(22);
		bankvo.setBankFtp(ftpVo);
	}

	public void save(BankVo bankvo) throws Exception {
		String cfgFile = "cfgbank.xml";
		File f = new File(basePath, cfgFile);
		Document doc = readDoc(f);
		Element root = doc.getRootElement();
		Element eIn = (Element) root.selectSingleNode("services/service[@id='bankIn']");
		if (eIn == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：bankIn配置项缺失").toString());
		updateServerInVo(eIn, bankvo.getBankIn());
		Element eOut = (Element) root.selectSingleNode("servers/server[@id='bankOut']");
		if (eOut == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：bankOut配置项缺失").toString());
		updateServerOutVo(eOut, bankvo.getBankOut());
		Element eFtp = (Element) root.selectSingleNode("ftpServers/server[@id='bankFtp']");
		if (eFtp == null) {
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：bankFtp配置项缺失").toString());
		} else {
			updateFtpVo(eFtp, bankvo.getBankFtp());
			writeDoc(doc, f);
			return;
		}
	}

	public void load(CorpVo corpvo) throws Exception {
		String cfgFile = "cfgcorp.xml";
		File f = new File(basePath, cfgFile);
		checkFile(f);
		Document doc = readDoc(f);
		Element root = doc.getRootElement();
		Element eIn = (Element) root.selectSingleNode("services/service[@id='corpIn']");
		if (eIn == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：corpIn配置项缺失").toString());
		ServerInVo invo = readServerInVo(eIn);
		corpvo.setCorpIn(invo);
		Element eOut = (Element) root.selectSingleNode("servers/server[@id='corpOut']");
		if (eOut == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：corpOut配置项缺失").toString());
		ServerOutVo outVo = readServerOutVo(eOut);
		corpvo.setCorpOut(outVo);
		Element eFtp = (Element) root.selectSingleNode("ftpServers/server[@id='corpFtp']");
		if (eFtp == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：corpFtp配置项缺失").toString());
		FtpVo ftpVo = readFtpVo(eFtp);
		if (ftpVo.getPort() <= 0)
			ftpVo.setPort(21);
		corpvo.setCorpFtp(ftpVo);
		corpvo.setFileNotify(getBoolValue(root, "notify", true));
		corpvo.setUseFtp(getBoolValue(root, "useftp", true));
		corpvo.setDownMode(getIntValue(root, "downMode", 0));
		corpvo.setNormalFormat(getBoolValue(root, "normalFormat", true));
		corpvo.setDataVersion(getStringValueO(root, "dataVersion", ""));
		corpvo.setCorpCode(getStringValueO(root, "corpCode", ""));
		corpvo.setTranCode(getStringValueO(root, "tranCode", "000000"));
	}

	public void save(CorpVo corpvo) throws Exception {
		String cfgFile = "cfgcorp.xml";
		File f = new File(basePath, cfgFile);
		Document doc = readDoc(f);
		Element root = doc.getRootElement();
		Element eIn = (Element) root.selectSingleNode("services/service[@id='corpIn']");
		if (eIn == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：corpIn配置项缺失").toString());
		updateServerInVo(eIn, corpvo.getCorpIn());
		Element eOut = (Element) root.selectSingleNode("servers/server[@id='corpOut']");
		if (eOut == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：corpOut配置项缺失").toString());
		updateServerOutVo(eOut, corpvo.getCorpOut());
		Element eFtp = (Element) root.selectSingleNode("ftpServers/server[@id='corpFtp']");
		if (eFtp == null) {
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：corpFtp配置项缺失").toString());
		} else {
			updateFtpVo(eFtp, corpvo.getCorpFtp());
			updateValue(root, "notify", (new StringBuilder()).append(corpvo.isFileNotify()).toString());
			updateValue(root, "useftp", (new StringBuilder()).append(corpvo.isUseFtp()).toString());
			updateValue(root, "downMode", (new StringBuilder()).append(corpvo.getDownMode()).toString());
			updateValue(root, "normalFormat", (new StringBuilder()).append(corpvo.isNormalFormat()).toString());
			updateValue(root, "dataVersion", (new StringBuilder()).append(corpvo.getDataVersion()).toString());
			updateValue(root, "corpCode", (new StringBuilder()).append(corpvo.getCorpCode()).toString());
			updateValue(root, "tranCode", (new StringBuilder()).append(corpvo.getTranCode()).toString());
			writeDoc(doc, f);
			return;
		}
	}

	public void load(SignVo signvo) throws Exception {
		String cfgFile = "cfgsign.xml";
		File f = new File(basePath, cfgFile);
		checkFile(f);
		Document doc = readDoc(f);
		Element root = doc.getRootElement();
		Element eSignopt = (Element) root.selectSingleNode("signopt");
		if (eSignopt == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：signopt配置项缺失").toString());
		signvo.setSignMode(getStringValueM(eSignopt, "signMode", "signMode项缺失"));
		signvo.setCertDn(getStringValueO(eSignopt, "certDn", ""));
		Element eTradeCode = (Element) root.selectSingleNode("signTradeCodes");
		if (eTradeCode == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：signTradeCodes配置项缺失").toString());
		String codes = getStringValueM(eTradeCode, "up", "up项缺失");
		String arr[] = codes.split(";");
		List lst = new ArrayList();
		String as[] = arr;
		int i = 0;
		for (int j = as.length; i < j; i++) {
			String code = as[i];
			if (code.trim().length() != 0)
				lst.add(code.trim());
		}

		signvo.setSignTradeCode(lst);
	}

	public void save(SignVo signvo) throws Exception {
		String cfgFile = "cfgsign.xml";
		File f = new File(basePath, cfgFile);
		Document doc = readDoc(f);
		Element root = doc.getRootElement();
		Element eSignopt = (Element) root.selectSingleNode("signopt");
		if (eSignopt == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：signopt配置项缺失").toString());
		updateValue(eSignopt, "signMode", signvo.getSignMode());
		updateValue(eSignopt, "certDn", signvo.getCertDn());
		Element eTradeCode = (Element) root.selectSingleNode("signTradeCodes");
		if (eTradeCode == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：signTradeCodes配置项缺失").toString());
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (Iterator iterator = signvo.getSignTradeCode().iterator(); iterator.hasNext();) {
			String code = (String) iterator.next();
			sb.append(code).append(";");
			if (++i > 10) {
				i = 0;
				sb.append("\n");
			}
		}

		updateValue(eTradeCode, "up", sb.toString());
		writeDoc(doc, f);
	}

	public void load(LogLvLVo logvo) throws Exception {
		String cfgFile = "log4j.xml";
		File f = new File(basePath, cfgFile);
		checkFile(f);
		Document doc = readDoc(f, new Log4jDTDResolver(basePath));
		Element root = doc.getRootElement();
		Node nagree = root.selectSingleNode("logger[@name='cn.com.agree']/level/@value");
		if (nagree == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：cn.com.agree项缺失").toString());
		String agreeLvL = nagree.getText();
		Node npingan = root.selectSingleNode("logger[@name='cn.com.pingan']/level/@value");
		if (npingan == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：cn.com.Pingan项缺失").toString());
		String pingLvL = npingan.getText();
		if (!agreeLvL.equalsIgnoreCase(pingLvL))
			logger.warn("配置文件日志级别不一致，可能手工修改");
		logvo.setSysLevel(agreeLvL);
		writeDoc(doc, f);
	}

	public void save(LogLvLVo logvo) throws Exception {
		String cfgFile = "log4j.xml";
		File f = new File(basePath, cfgFile);
		Document doc = readDoc(f, new Log4jDTDResolver(basePath));
		Element root = doc.getRootElement();
		Node nagree = root.selectSingleNode("logger[@name='cn.com.agree']/level/@value");
		if (nagree == null)
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：cn.com.agree项缺失").toString());
		nagree.setText(logvo.getSysLevel());
		Node npingan = root.selectSingleNode("logger[@name='cn.com.pingan']/level/@value");
		if (npingan == null) {
			throw new Exception((new StringBuilder("配置文件")).append(cfgFile).append("错误：cn.com.Pingan项缺失").toString());
		} else {
			npingan.setText(logvo.getSysLevel());
			writeDoc(doc, f);
			return;
		}
	}

	public void commCheck(String host, int port) throws Exception {
		Socket sock = null;
		try {
			InetSocketAddress address = new InetSocketAddress(host, port);
			sock = new Socket();
			sock.connect(address);
		} catch (NoRouteToHostException _ex) {
			throw new Exception("通讯失败。可能的原因:主机地址不正确、网络未连接");
		} catch (ConnectException _ex) {
			throw new Exception("通讯失败。可能的原因：端口不正确、服务方进程未启动");
		} catch (SocketTimeoutException _ex) {
			throw new Exception("通讯失败。可能的原因：主机地址不正确、网络异常");
		} catch (IOException e) {
			throw new Exception((new StringBuilder("通讯失败。原因:")).append(e.getMessage()).toString());
		} finally {
			if (sock != null)
				try {
					sock.close();
				} catch (Exception _ex) {
				}
			 
 		}

	}

	private Document readDoc(File f) throws FileNotFoundException, DocumentException {
		return readDoc(f, null);
	}

	private Document readDoc(File f, EntityResolver resolver)
        throws FileNotFoundException, DocumentException
    {
	    SAXReader reader = new SAXReader();
	    if (resolver != null) {
	      reader.setEntityResolver(resolver);
	    }
	    Document doc = null;
	    InputStream ins = null;
	    try
	    {
	      ins = new FileInputStream(f);
	      doc = reader.read(ins);
	    }
	    finally
	    {
	      if (ins != null) {
	        try
	        {
	          ins.close();
	        }
	        catch (Exception e)
	        {
	          logger.error(e);
	        }
	      }
	    }
	    return doc;}

	private void writeDoc(Document doc, File fout)
        throws Exception
    {
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    if (doc.getXMLEncoding() != null) {
	      format.setEncoding(doc.getXMLEncoding());
	    }
	    OutputStream outs = null;
	    
	    File ftmp = new File(fout.getParent(), fout.getName() + ".tmp");
	    try
	    {
	      outs = new BufferedOutputStream(new FileOutputStream(ftmp));
	      XMLWriter writer = new XMLWriter(outs, format);
	      writer.write(doc);
	      writer.flush();
	    }
	    catch (Exception e)
	    {
	      logger.error("临时文件写入异常." + e);
	      throw e;
	    }
	    finally
	    {
	      if (outs != null) {
	        try
	        {
	          outs.close();
	        }
	        catch (Exception e)
	        {
	          logger.error(e);
	        }
	      }
	    }
	    File fbak = new File(fout.getParent(), fout.getName() + ".bl");
	    if ((fbak.exists()) && 
	      (!fbak.delete())) {
	      logger.warn("删除旧备份文件失败:" + fbak.getName());
	    }
	    if (!fout.renameTo(fbak))
	    {
	      logger.warn("备份配置文件失败:" + fout.getName() + " -> " + fbak.getName());
	      return;
	    }
	    if (!ftmp.renameTo(fout))
	    {
	      boolean ok = fbak.renameTo(fout);
	      logger.error("保存文件失败:" + ftmp.getName() + " -> " + fout.getName());
	      if (!ok)
	      {
	        logger.error("恢复文件失败:" + fbak.getName() + " -> " + 
	          fout.getName());
	        throw new Exception("更新配置文件异常，配置文件可能损坏!");
	      }
	      throw new Exception("更新配置文件失败");
	    }
	  }

	private ServerInVo readServerInVo(Element element) throws Exception {
		ServerInVo vo = new ServerInVo();
		Node node = element.selectSingleNode("protocol");
		if (node != null)
			vo.setProtocol(node.getText());
		else
			vo.setProtocol("tcp");
		vo.setPort(getIntValue(element, "port", "port项缺失"));
		vo.setMaximumPoolSize(getIntValue(element, "maximumPoolSize", 100));
		vo.setSoTimeout(getIntValue(element, "soTimeout", 60000));
		return vo;
	}

	private void updateServerInVo(Element element, ServerInVo vo) {
		if (vo.getProtocol() != null && vo.getProtocol().length() > 0)
			updateValue(element, "protocol", vo.getProtocol());
		updateValue(element, "port", (new StringBuilder()).append(vo.getPort()).toString());
		if (vo.getSoTimeout() >= 0)
			updateValue(element, "soTimeout", (new StringBuilder()).append(vo.getSoTimeout()).toString());
		if (vo.getMaximumPoolSize() > 0)
			updateValue(element, "maximumPoolSize", (new StringBuilder()).append(vo.getMaximumPoolSize()).toString());
	}

	private ServerOutVo readServerOutVo(Element element) throws Exception {
		ServerOutVo vo = new ServerOutVo();
		Node node = element.selectSingleNode("protocol");
		if (node != null)
			vo.setProtocol(node.getText());
		else
			vo.setProtocol("tcp");
		vo.setIps(getStringValueM(element, "ips", "ips项缺失"));
		vo.setPorts(getStringValueM(element, "ports", "ports项缺失"));
		vo.setTimeout(getIntValue(element, "timeout", 60000));
		if ("http".equalsIgnoreCase(vo.getProtocol()) || "https".equalsIgnoreCase(vo.getProtocol())) {
			node = element.selectSingleNode("http/url");
			if (node != null)
				vo.setUrl(node.getText());
		}
		return vo;
	}

	private void updateServerOutVo(Element element, ServerOutVo vo) {
		if (vo.getProtocol() != null && vo.getProtocol().length() > 0)
			updateValue(element, "protocol", vo.getProtocol());
		updateValue(element, "ips", vo.getIps());
		updateValue(element, "ports", vo.getPorts());
		if (vo.getTimeout() >= 0)
			updateValue(element, "timeout", (new StringBuilder()).append(vo.getTimeout()).toString());
		if (vo.getUrl() != null && ("http".equalsIgnoreCase(vo.getProtocol()) || "https".equalsIgnoreCase(vo.getProtocol()))) {
			Element e2 = updateValue(element, "http", null);
			updateValue(e2, "url", vo.getUrl());
		}
	}

	private FtpVo readFtpVo(Element element) throws Exception {
		FtpVo vo = new FtpVo();
		vo.setHostname(getStringValueM(element, "hostname", "ftp元素hostname未设置。"));
		vo.setPort(getIntValue(element, "port", -1));
		vo.setFtpname(getStringValueM(element, "ftpname", "ftp元素ftpname未设置。"));
		String pwdpwd = getStringValueM(element, "ftppwd", "ftp元素ftppwd未设置。");
		String pwd = "NULL";
		try {
			pwd = readPwdField(pwdpwd);
		} catch (Exception _ex) {
			logger.warn((new StringBuilder("FTP密码值[")).append(pwdpwd).append("]错误，将置默认值NULL").toString());
		}
		vo.setFtppwd(pwd);
		vo.setSoTimeout(getIntValue(element, "soTimeout", 60000));
		vo.setDefaultDir(getStringValueM(element, "defaultDir", ""));
		return vo;
	}

	private void updateFtpVo(Element element, FtpVo vo) throws Exception {
		updateValue(element, "hostname", vo.getHostname());
		updateValue(element, "port", (new StringBuilder()).append(vo.getPort()).toString());
		updateValue(element, "ftpname", vo.getFtpname());
		updateValue(element, "ftppwd", writePwdField(vo.getFtppwd()));
		if (vo.getSoTimeout() >= 0)
			updateValue(element, "soTimeout", (new StringBuilder()).append(vo.getSoTimeout()).toString());
		if (vo.getDefaultDir() != null)
			updateValue(element, "defaultDir", vo.getDefaultDir());
	}

	private void checkNull(Node node, String msg) throws Exception {
		if (node == null || node.getText().length() == 0)
			throw new Exception(msg);
		else
			return;
	}

	private int getIntValue(Element element, String ename, String errMsg) throws Exception {
		Node node = element.selectSingleNode(ename);
		checkNull(node, errMsg);
		return Integer.parseInt(node.getText());
	}

	private int getIntValue(Element element, String ename, int defVal) throws Exception {
		Node node = element.selectSingleNode(ename);
		if (node != null && node.getText().length() != 0)
			return Integer.parseInt(node.getText());
		else
			return defVal;
	}

	private String getStringValueM(Element element, String ename, String errMsg) throws Exception {
		Node node = element.selectSingleNode(ename);
		checkNull(node, errMsg);
		return node.getText();
	}

	private String getStringValueO(Element element, String ename, String defVal) {
		Node node = element.selectSingleNode(ename);
		if (node == null)
			return defVal;
		else
			return node.getText();
	}

	private boolean getBoolValue(Element element, String ename, boolean defVal) {
		Node node = element.selectSingleNode(ename);
		if (node != null && node.getText().length() != 0)
			return Boolean.parseBoolean(node.getText());
		else
			return defVal;
	}

	private Element updateValue(Element element, String ename, String value) {
		Element node = (Element) element.selectSingleNode(ename);
		if (node == null)
			node = element.addElement(ename);
		if (value != null)
			node.setText(value);
		return node;
	}

	private void checkFile(File f) {
		if (!f.exists())
			throw new RuntimeException((new StringBuilder("配置文件不存在:")).append(f.getName()).toString());
		else
			return;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public IPasswordReader getPwdReader() {
		return pwdReader;
	}

	public void setPwdReader(IPasswordReader pwdReader) {
		this.pwdReader = pwdReader;
	}

}
