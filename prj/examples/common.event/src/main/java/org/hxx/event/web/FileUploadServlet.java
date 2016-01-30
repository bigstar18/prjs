package org.hxx.event.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.hxx.aut.dao.IUserPicDao;
import org.hxx.aut.dao.UserPicDao;
import org.hxx.aut.model.UserPic;
import org.hxx.event.util.PicCompressUtil;
import org.hxx.util.DBUtil;

/**
 * Servlet implementation class FileUploadServlet
 */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserPicDao userPicDao = new UserPicDao();
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileUploadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		InputStream in = null;
		OutputStream out = null;
		String picId="";
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件需要上传到的路径
		String path = "./upload";
		String compressPath = "./zipUpload";
		try {
			//如果文件夹不存在，创建该文件夹
			File file = new File(path);
			if(!file.exists()){
				file.mkdir();
			}
			factory.setRepository(file);
			// 设置缓存的大小,当上传文件的容量超过该缓存时,直接放到暂时存储室
			factory.setSizeThreshold(1024 * 1024);
	
			// 高水平的API文件上传处理
			ServletFileUpload upload = new ServletFileUpload(factory);

			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>)upload.parseRequest(request);

			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的 表单信息是普通的文本信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					String value = item.getString();
					request.setAttribute(name, value);
				}
				// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
				else {
					/**
					 * 以下三步,主要获取上传文件的名字
					 */
					//获取用户手机号
					String phoneNum = (String) request.getSession().getAttribute("phone");
					//获取活动号
					String eventId = (String)request.getSession().getAttribute("eventId");
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String filename = value.substring(start + 1);
					Date currentTime = new Date();
					String dateString = formatter.format(currentTime);
					filename = phoneNum + "_" + dateString + "_" + filename ;
					request.setAttribute(name, filename);
					File saveFile = new File(path,filename);
					out = new FileOutputStream(saveFile);
					in = item.getInputStream();
					int length = 0;
					byte[] buf = new byte[1024];

//					System.out.println("获取上传文件的总共的容量：" + item.getSize());
					// in.read(buf) 每次读到的数据存放在 buf 数组中
					while ((length = in.read(buf)) != -1) {
						// 在 buf 数组中 取出数据 写到 （输出流）磁盘上
						out.write(buf, 0, length);
					}
					//对当前图片进行压缩
					PicCompressUtil.compressPic(saveFile, compressPath, filename, 160, 160, true);
					//将用户的手机号，原始图片和压缩图片路径存入数据库
					UserPic userPic = new UserPic();
					userPic.setPhoneNum(phoneNum);
					userPic.setOriginPicPath(path+"/"+filename);
					userPic.setCompressPicPath(compressPath+"/"+filename);
					int count = userPicDao.check(phoneNum);
					if(count == 2){
						userPicDao.setEventStatus(phoneNum, eventId);
					}
					if(count < 3){
						userPicDao.add(userPic);
					}
				}
			}
			picId = (String)request.getAttribute("id");
			response.getWriter().write("{\"jsonrpc\" : \"2.0\", \"result\" : null, \"id\" : \""+picId+"\"}");
		}catch (Throwable e) {
			response.getWriter().write("{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to upload picture.\"}, \"id\" : \""+picId+"\"}");
		}finally{
			in.close();
			out.close();
		}
	}

}
