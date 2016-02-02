package gnnt.trade.bank.xmlwork;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
/**生成xml文件*/
public class AddXml {
	/**编码格式*/
	private String charset="GB2312";
	/**文件路径*/
	private String url;
	/**内存文件流*/
	private Document doc;
	public static void main(String args[]){
		try{
			Map<String,String> map = new HashMap<String,String>();
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("null", " ");
			map.put("name", "lzx");
			map.put("sex", "man");
			map.put("height", "179cm");
			map.put("weight", "85kg");
			AddXml xml = new AddXml("mywork.xml","GNNT");
			List<Integer> father= new ArrayList<Integer>();
			father.add(2);
			xml.mkXML("gb2312");
			xml.setChildElements("gnnt.piple.words", map);
			xml.setChildElements("gnnt.piple.words", map);
			xml.setChildElements("gnnt.piple.words", map);
			xml.setChildElements("gnnt.piple.words", map);
			xml.setChildElements(father, map);
			xml.setChildElements(father, map2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**构造方法 url：文件路径 rootElement：根目录节点名称*/
	public AddXml(String url,String rootElement){
			this.doc=new DocumentImpl();
			this.url=url;
			this.setRoot(rootElement);
	}
	/**构造方法 url：文件路径 rootElement：根目录节点名称*/
	public AddXml(String url,String rootElement,String charset){
			this.doc=new DocumentImpl();
			this.url=url;
			this.setRoot(rootElement);
			try{
				this.mkXML(charset);
			}catch(IOException e){
				e.printStackTrace();
			}
	}
	/**生成XML文件 charset：编码格式，默认为 gb2312*/
	public void mkXML(String charset)throws IOException{
		if(charset!=null && charset.trim().length()>0){
			this.charset=charset.trim();
		}
		try{
			OutputFormat outputFormat = new OutputFormat("XML",this.charset,true);
			FileWriter fileWriter = new FileWriter(new File(url));
			XMLSerializer xmlSerializer = new XMLSerializer(fileWriter,outputFormat);
			xmlSerializer.asDOMSerializer();
			xmlSerializer.serialize(doc.getDocumentElement());
		}catch(IOException e){
			e.printStackTrace();
			throw e;
		}
	}
	/**向指定节点下追加数据*/
	public void setChildElement(String father,String key,String value)throws Exception{
		Element root = doc.getDocumentElement();
		if(father==null || father.trim().length()<=0){
			father = root.getNodeName();
		}
		String[] strs = father.split("\\.");
		Element finalElement = this.getElement(key, value);
		for(int i = strs.length-1;i>0;i--){
			finalElement = this.getElement(strs[i], finalElement);
		}
		if(!strs[0].equalsIgnoreCase(root.getNodeName())){
			finalElement = this.getElement(strs[0], finalElement);
		}
		//root.normalize();
		root.appendChild(finalElement);
		this.writeInFile();
	}
	/**向根节点下追加多个数据*/
	public void setChildElements(String father,Map<String,String> map)throws Exception{
		Element root = doc.getDocumentElement();
		if(father==null || father.trim().length()<=0){
			father = root.getNodeName();
		}
		String[] strs = father.split("\\.");
		String[][] elements = this.chickMap(map);
		if(elements!=null){
			if(strs.length>1){
				Element finalElement = doc.createElement(strs[strs.length-1]);
				for(int i = 0 ; i < elements.length ; i++){//取最后的叶子节点
					Element pageElement = this.getElement(elements[i][0], elements[i][1]);
					finalElement.appendChild(pageElement);
				}
				for(int i = strs.length-2 ; i >0 ; i--){
					finalElement = this.getElement(strs[i], finalElement);
				}
				if(!strs[0].equalsIgnoreCase(root.getNodeName())){
					finalElement = this.getElement(strs[0], finalElement);
				}
				root.normalize();
				root.appendChild(finalElement);
			}else if(strs.length==1){
				if(!strs[0].equalsIgnoreCase(root.getNodeName())){
					Element finalElement = doc.createElement(strs[0]);
					for(int i = 0 ; i < elements.length ; i++){//取最后的叶子节点
						Element pageElement = this.getElement(elements[i][0], elements[i][1]);
						finalElement.appendChild(pageElement);
					}
					root.normalize();
					root.appendChild(finalElement);
				} else {
					for(int i = 0 ; i < elements.length ; i++){//取最后的叶子节点
						Element pageElement = this.getElement(elements[i][0], elements[i][1]);
						root.normalize();
						root.appendChild(pageElement);
					}
				}
			}
			this.writeInFile();
		}
	}
	/**向指定节点中追加多个叶子记录*/
	public void setChildElements(List<Integer> father,Map<String,String> map)throws Exception{
		Element root = this.getFarthor(father);
		String[][] elements = this.chickMap(map);
		if(elements!=null){
			for(int i = 0 ; i < elements.length ; i++){//取最后的叶子节点
				Element pageElement = this.getElement(elements[i][0], elements[i][1]);
				root.appendChild(pageElement);
			}
			this.writeInFile();
		}
	}
	/**向指定节点中追加一个目录*/
	public Element setChildElements(String father,String array ,Map<String,String> map)throws Exception{
		Element root = this.getFarthor(father);
		String[][] elements = this.chickMap(map);
		Element ele = doc.createElement(array);
		if(elements!=null){
			for(int i = 0 ; i < elements.length ; i++){//取最后的叶子节点
				Element pageElement = this.getElement(elements[i][0], elements[i][1]);
				ele.appendChild(pageElement);
			}
		}
		root.appendChild(ele);
		this.writeInFile();
		return ele;
	}
	/**向给定目录追加信息*/
	public Element setChildElements(Element element,String array,Map<String,String> map)throws Exception{
		String[][] elements = this.chickMap(map);
		Element ele = doc.createElement(array);
		if(elements!=null){
			for(int i = 0 ; i < elements.length ; i++){//取最后的叶子节点
				Element pageElement = this.getElement(elements[i][0], elements[i][1]);
				ele.appendChild(pageElement);
			}
		}
		element.appendChild(ele);
		this.writeInFile();
		return ele;
	}
	/**提交向xml中写的数据*/
	public void writeInFile()throws TransformerConfigurationException,TransformerException{
		try{
			TransformerFactory tFactory=TransformerFactory.newInstance();
			Transformer   transformer=tFactory.newTransformer();
			//设置输出的encoding为改变gb2312
			transformer.setOutputProperty("encoding",this.charset);
			DOMSource   source=   new   DOMSource(doc);
			StreamResult   result   =   new   StreamResult(this.url);
			transformer.transform(source,result);
		}catch(TransformerConfigurationException   e){
			e.printStackTrace();
			throw e;
		}catch(TransformerException e){
			e.printStackTrace();
			throw e;
		}
	}
	/**为xml文件创建根节点*/
	private void setRoot(String rootElement){
		Element root = doc.createElement(rootElement);
		doc.appendChild(root);
	}
	/**获取非叶子节点*/
	private Element getElement(String key,Element value){
		if(key==null || key.trim().length()<=0){
			key = "Error";
		}
		Element result = doc.createElement(key);
		if(value==null){
			Text text = doc.createTextNode("Error");
			result.appendChild(text);
		} else {
			result.appendChild(value);
		}
		return result;
	}
	/**获取叶子节点*/
	private Element getElement(String key,String value){
		if(key==null || key.trim().length()<=0){
			key = "Error";
		}
		if(value==null){
			value = "";
		}
		Element result = doc.createElement(key);
		Text text = doc.createTextNode(value);
		result.appendChild(text);
		return result;
	}
	/**遍历Map获取二维数组*/
	private String[][] chickMap(Map<String,String> myMap){
		String[][] result = null;
		if(myMap==null){
			return new String[2][];
		}
		Set<String> set = myMap.keySet();
		int num = set.size();
		result = new String[num][2];
		int i = 0;
		for(Iterator<String> it = set.iterator();it.hasNext();){
			String key = it.next();
			String value =  myMap.get(key);
			result[i][0]=key;
			result[i++][1]=value;
		}
		return result;
	}
	/**获取要填加子目录的父目录*/
	private Element getFarthor(List<Integer> father)throws Exception{
		Element result = doc.getDocumentElement();
		if(father==null || father.size()<=0){
			return result;
		}
		try{
			for(int i = 0 ; i < father.size();i++){
				System.out.print(i);
				NodeList nl=result.getChildNodes();
				int n = father.get(i);
				if(n<1){
					n=1;
				}
				System.out.print(" "+n+" "+nl.getLength());
				if(nl.getLength()<=0 || nl.getLength()<n-1){
					return result;
				}
				result = (Element)nl.item(n-1);
				System.out.println();
			}
		}catch(Exception e){
			System.out.println();
			e.printStackTrace();
		}
		return result;
	}
	/**获取父目录*/
	private Element getFarthor(String father)throws Exception{
		Element result = doc.getDocumentElement();
		if(father==null || father.trim().length()<=0){
			return result;
		}
		String[] strs = father.split("\\.");
		for(int i = 0 ; i < strs.length ; i++){
			NodeList list = result.getChildNodes();
			int j=0;
			boolean flag = true;
			while(flag && list.getLength()>j){
				Element ele = (Element)list.item(j++);
				if(ele.getNodeName().equalsIgnoreCase(strs[i])){
					flag = false;
					result = ele;
				}
			}
		}
		return result;
	}
}
