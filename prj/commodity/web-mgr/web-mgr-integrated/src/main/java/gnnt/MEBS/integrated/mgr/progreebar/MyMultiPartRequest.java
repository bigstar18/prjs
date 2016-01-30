package gnnt.MEBS.integrated.mgr.progreebar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.dispatcher.multipart.MultiPartRequest;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;

public class MyMultiPartRequest implements MultiPartRequest {
	static final Logger LOG = LoggerFactory.getLogger(MultiPartRequest.class);
	public static final String FileSizeLimitExceededException = "ExceededError";
	protected Map<String, List<FileItem>> files = new HashMap();
	protected Map<String, List<String>> params = new HashMap();
	protected List<String> errors = new ArrayList();
	protected long maxSize;
	protected long fileSizeMax = 1024 * Tools.strToInt(ApplicationContextInit.getConfig("fileSizeMax"), 30);

	@Inject("struts.multipart.maxSize")
	public void setMaxSize(String paramString) {
		this.maxSize = Long.parseLong(paramString);
	}

	public void parse(HttpServletRequest paramHttpServletRequest, String paramString) throws IOException {
		try {
			processUpload(paramHttpServletRequest, paramString);
		} catch (FileUploadException localFileUploadException) {
			LOG.warn("Unable to parse request", localFileUploadException, new String[0]);
			this.errors.add(localFileUploadException.getMessage());
		}
	}

	private void processUpload(HttpServletRequest paramHttpServletRequest, String paramString)
			throws FileUploadException, UnsupportedEncodingException {
		Iterator localIterator = parseRequest(paramHttpServletRequest, paramString).iterator();
		while (localIterator.hasNext()) {
			FileItem localFileItem = (FileItem) localIterator.next();
			if (LOG.isDebugEnabled()) {
				LOG.debug("Found item " + localFileItem.getFieldName(), new String[0]);
			}
			if (localFileItem.isFormField()) {
				processNormalFormField(localFileItem, paramHttpServletRequest.getCharacterEncoding());
			} else {
				processFileField(localFileItem);
			}
		}
	}

	private void processFileField(FileItem paramFileItem) {
		LOG.debug("Item is a file upload", new String[0]);
		if ((paramFileItem.getName() == null) || (paramFileItem.getName().trim().length() < 1)) {
			LOG.debug("No file has been uploaded for the field: " + paramFileItem.getFieldName(), new String[0]);
			return;
		}
		List localObject;
		if (this.files.get(paramFileItem.getFieldName()) != null) {
			localObject = (List) this.files.get(paramFileItem.getFieldName());
		} else {
			localObject = new ArrayList();
		}
		((List) localObject).add(paramFileItem);
		this.files.put(paramFileItem.getFieldName(), localObject);
	}

	private void processNormalFormField(FileItem paramFileItem, String paramString) throws UnsupportedEncodingException {
		LOG.debug("Item is a normal form field", new String[0]);
		List localObject;
		if (this.params.get(paramFileItem.getFieldName()) != null) {
			localObject = (List) this.params.get(paramFileItem.getFieldName());
		} else {
			localObject = new ArrayList();
		}
		if (paramString != null) {
			((List) localObject).add(paramFileItem.getString(paramString));
		} else {
			((List) localObject).add(paramFileItem.getString());
		}
		this.params.put(paramFileItem.getFieldName(), localObject);
	}

	private List<FileItem> parseRequest(HttpServletRequest paramHttpServletRequest, String paramString) throws FileUploadException {
		DiskFileItemFactory localDiskFileItemFactory = createDiskFileItemFactory(paramString);
		ServletFileUpload localServletFileUpload = new ServletFileUpload(localDiskFileItemFactory);
		localServletFileUpload.setSizeMax(this.maxSize);
		localServletFileUpload.setFileSizeMax(this.fileSizeMax);
		FileUploadListener localFileUploadListener = new FileUploadListener(paramHttpServletRequest);
		RequestContext localRequestContext = createRequestContext(paramHttpServletRequest);
		localServletFileUpload.setProgressListener(localFileUploadListener);
		try {
			return localServletFileUpload.parseRequest(localRequestContext);
		} catch (FileUploadBase.FileSizeLimitExceededException localFileSizeLimitExceededException) {
			paramHttpServletRequest.setAttribute("ExceededError",
					"上传的文件超过设定的大小限制，单个文件不能超过" + Integer.parseInt(new StringBuilder().append(this.fileSizeMax / 1024L).append("").toString()) + "kb");
			return new ArrayList();
		} catch (FileUploadBase.SizeLimitExceededException localSizeLimitExceededException) {
			try {
				InputStream localInputStream = localRequestContext.getInputStream();
				String str = null;
				if (str == null) {
					str = localRequestContext.getCharacterEncoding();
				}
				byte[] arrayOfByte = new byte['က'];
				while (localInputStream.read(arrayOfByte) != -1) {
				}
				localInputStream.close();
			} catch (IOException localIOException) {
				localIOException.printStackTrace();
			} finally {
				paramHttpServletRequest.setAttribute("ExceededError",
						"图片累计大小不能超过" + Integer.parseInt(new StringBuilder().append(this.maxSize / 1024L).append("").toString()) + "kb");
			}
		}
		return new ArrayList();
	}

	private DiskFileItemFactory createDiskFileItemFactory(String paramString) {
		DiskFileItemFactory localDiskFileItemFactory = new DiskFileItemFactory();
		localDiskFileItemFactory.setSizeThreshold(0);
		if (paramString != null) {
			localDiskFileItemFactory.setRepository(new File(paramString));
		}
		return localDiskFileItemFactory;
	}

	public Enumeration<String> getFileParameterNames() {
		return Collections.enumeration(this.files.keySet());
	}

	public String[] getContentType(String paramString) {
		List localList = (List) this.files.get(paramString);
		if (localList == null) {
			return null;
		}
		ArrayList localArrayList = new ArrayList(localList.size());
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			FileItem localFileItem = (FileItem) localIterator.next();
			localArrayList.add(localFileItem.getContentType());
		}
		return (String[]) localArrayList.toArray(new String[localArrayList.size()]);
	}

	public File[] getFile(String paramString) {
		List localList = (List) this.files.get(paramString);
		if (localList == null) {
			return null;
		}
		ArrayList localArrayList = new ArrayList(localList.size());
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			FileItem localFileItem = (FileItem) localIterator.next();
			File localFile = ((DiskFileItem) localFileItem).getStoreLocation();
			if ((localFileItem.isInMemory()) && (localFile != null) && (!localFile.exists())) {
				try {
					localFile.createNewFile();
				} catch (IOException localIOException) {
					if (LOG.isErrorEnabled()) {
						LOG.error("Cannot write uploaded empty file to disk: " + localFile.getAbsolutePath(), localIOException, new String[0]);
					}
				}
			}
			localArrayList.add(localFile);
		}
		return (File[]) localArrayList.toArray(new File[localArrayList.size()]);
	}

	public String[] getFileNames(String paramString) {
		List localList = (List) this.files.get(paramString);
		if (localList == null) {
			return null;
		}
		ArrayList localArrayList = new ArrayList(localList.size());
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			FileItem localFileItem = (FileItem) localIterator.next();
			localArrayList.add(getCanonicalName(localFileItem.getName()));
		}
		return (String[]) localArrayList.toArray(new String[localArrayList.size()]);
	}

	public String[] getFilesystemName(String paramString) {
		List localList = (List) this.files.get(paramString);
		if (localList == null) {
			return null;
		}
		ArrayList localArrayList = new ArrayList(localList.size());
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			FileItem localFileItem = (FileItem) localIterator.next();
			localArrayList.add(((DiskFileItem) localFileItem).getStoreLocation().getName());
		}
		return (String[]) localArrayList.toArray(new String[localArrayList.size()]);
	}

	public String getParameter(String paramString) {
		List localList = (List) this.params.get(paramString);
		if ((localList != null) && (localList.size() > 0)) {
			return (String) localList.get(0);
		}
		return null;
	}

	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(this.params.keySet());
	}

	public String[] getParameterValues(String paramString) {
		List localList = (List) this.params.get(paramString);
		if ((localList != null) && (localList.size() > 0)) {
			return (String[]) localList.toArray(new String[localList.size()]);
		}
		return null;
	}

	public List<String> getErrors() {
		return this.errors;
	}

	private String getCanonicalName(String paramString) {
		int i = paramString.lastIndexOf("/");
		int j = paramString.lastIndexOf("\\");
		if ((i != -1) && (i > j)) {
			paramString = paramString.substring(i + 1, paramString.length());
		} else if ((j != -1) && (j >= i)) {
			paramString = paramString.substring(j + 1, paramString.length());
		}
		return paramString;
	}

	private RequestContext createRequestContext(final HttpServletRequest paramHttpServletRequest) {
		return new RequestContext() {
			public String getCharacterEncoding() {
				return paramHttpServletRequest.getCharacterEncoding();
			}

			public String getContentType() {
				return paramHttpServletRequest.getContentType();
			}

			public int getContentLength() {
				return paramHttpServletRequest.getContentLength();
			}

			public InputStream getInputStream() throws IOException {
				ServletInputStream localServletInputStream = paramHttpServletRequest.getInputStream();
				if (localServletInputStream == null) {
					throw new IOException("Missing content in the request");
				}
				return paramHttpServletRequest.getInputStream();
			}
		};
	}

	public void cleanUp() {
	}
}
