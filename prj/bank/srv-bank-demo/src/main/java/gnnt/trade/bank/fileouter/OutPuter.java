package gnnt.trade.bank.fileouter;
import gnnt.trade.bank.util.Tool;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
/**
 * 向页面输出文件类
 */
public class OutPuter {
	public OutPuter(){
	}
	/**
	 * 导出查询出的信息记录
	 * @param title 表头信息
	 * @param types 信息类型(key 为字段名,value 为字段类型)
	 * @param rs
	 * @param os
	 * @throws SQLException
	 * @throws IOException
	 */
	public void outputMsg(Vector<String> title,LinkedHashMap<String,Integer> types,ResultSet rs,OutputStream os)throws SQLException,IOException{
		StringBuilder sb = new StringBuilder();
		String str = ",";
		if(title != null && !title.isEmpty()){
			for(String top : title){
				sb.append(top+str);
			}
		}
		output(sb.toString(),os);//输出表头信息
		if(types==null || types.isEmpty() || rs == null){
			return;
		}
		Vector<String> names = getKeys(types);
		try{
			while(rs.next()){
				sb = new StringBuilder();
				for(String name:names){
					sb.append(getValue(name,types.get(name),rs)+str);
				}
				output(sb.toString(),os);//输出表值信息
			}
		}catch(SQLException e){
			throw e;
		}
	}
	/**
	 * 获取查询出的信息
	 * @param name 信息名
	 * @param type 值类型(4 long,8 double,91 date,93 timestamp,else String)
	 * @param rs 信息结果集
	 * @return String 格式化后的信息值
	 * @throws SQLException
	 */
	private String getValue(String name,int type,ResultSet rs)throws SQLException{
		String result = null;
		try{
			if(type==Types.INTEGER){
				result = rs.getLong(name)+" ";
			}else if(type==Types.DOUBLE){
				result = Tool.fmtDouble2(rs.getDouble(name))+" ";
			}else if(type==Types.DATE){
				result = Tool.fmtDate(rs.getDate(name))+"\t";
			}else if(type==Types.TIMESTAMP){
				result = Tool.fmtTime(rs.getTimestamp(name))+"\t";
			}else{
				result = rs.getString(name)+"\t";
			}
		}catch(SQLException e){
			System.out.println("出问题的 name:"+name);
			throw e;
		}
		//result = Tool.delNull(result)+"\t";
		result.replaceAll(",", "，");
		if(result.trim().length()<=0){
			result = "-";
		}
		return result;
	}
	/**
	 * 获取传入 Map 的所有 key 
	 * @param map 要遍历的 Map
	 * @return Vector<String> key集合
	 */
	private Vector<String> getKeys(Map<String,Integer> map){
		Vector<String> result = new Vector<String>();
		if(map == null || map.isEmpty()){
			return result;
		}
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			result.add(keys.next());
		}
		return result;
	}
	/**
	 * 向页面输出信息
	 * @param msg 要输出的信息
	 * @param os 输出流
	 * @throws IOException
	 */
	private void output(String msg,OutputStream os)throws IOException{
		if(os == null){
			return;
		}
		if(msg == null){
			return;
		}
		try{
			msg += "\n";
			os.write(msg.getBytes());
		}catch(IOException e){
			throw e;
		}
	}
}
