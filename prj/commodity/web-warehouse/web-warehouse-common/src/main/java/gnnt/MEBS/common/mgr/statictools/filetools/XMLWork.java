package gnnt.MEBS.common.mgr.statictools.filetools;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
/**
 * xml和类的转换工具
 * @author liuzx
 */
public class XMLWork {
	/**编码格式*/
	private static final String encoding="GBK";
	/**
	 * 通过xml字符串生成类
	 * @param cl 类
	 * @param xml xml串
	 * @return Object
	 */
	public static Object reader(Class<?> cl,String xml){
		Object result = null;
		try {
			result = Class.forName(cl.getName()).newInstance();
			JAXBContext context = JAXBContext.newInstance(result.getClass());
			InputStream is = null;
			try {
				is = new ByteArrayInputStream(xml.getBytes(encoding));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Unmarshaller ums = context.createUnmarshaller();
			result = ums.unmarshal(is) ;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 通过类信息生成xml字符串
	 * @param obj 类实例
	 * @return String
	 */
	public static String writer(Object obj){
		String result = null;
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(obj.getClass());
			Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_ENCODING, encoding);
			Writer writer = new StringWriter();
			ms.marshal(obj,writer);
			result = writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 通过xml路径读取xml信息
	 * @param fileURI xml路径
	 * @return String
	 */
	public static String readXMLFile(String fileURI){
		StringBuilder sb = new StringBuilder();
		BufferedReader reader=null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileURI)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(reader != null){
			try{
				String line = reader.readLine();
				while (line != null) {
					sb.append(line.trim());
					//sb.append("\n");
					line = reader.readLine();
				}
				return sb.toString();
			}catch(Exception e){
				return "";
			}
		}
		return "";
	}
}
