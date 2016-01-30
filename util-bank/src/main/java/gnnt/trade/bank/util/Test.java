package gnnt.trade.bank.util;
import java.sql.SQLException;
public class Test {
	/**
	 * @param args
	 */
//	public static void main(String[] args) 
//	{
//		CapitalProcessor processor = new CapitalProcessor();
//		System.out.println("入金：" + processor.inMoneyMarket("01", "1008", "", "123",500, "BUFF")); 
//		System.out.println("入金：" + processor.inMoneyMarket("01", "xjt1", "", "111111",1, "BUFF")); 
//		System.out.println("入金：" + processor.inMoneyMarket("01", "1008", "", "123",500, "BUFF")); 
//		System.out.println("入金：" + processor.inMoneyMarket("01", "1008", "", "123",500, "BUFF")); 
//	}
	public static void main(String args[]){
		getFlote(new String[]{"600009","600010","600011"});
	}
	/**
	   * 取得交易商浮动盈亏数据
	   * @param firmIDs 交易商代码集
	   * @param conn 数据库连接
	   * @return Vector<TholdPositionValue>
	   * @throws SQLException
	   */
	  public static void getFlote(String[] firmIDs){
		  String sql = null;
		  String filter = "";
		  if(firmIDs != null && firmIDs.length > 0){
			  String firms = "";
			  for(String firmID : firmIDs){
				  if(firmID != null && firmID.trim().length()>0){
					  firms +="'"+firmID.trim()+"',";
				  }
			  }
			  if(firms != null && firms.trim().length()>0){
				  filter += " and firmid in ("+firms.substring(0, firms.length()-1)+") ";
			  }
		  }
			  sql = "select sum(floatingloss) floatingloss,firmID from t_holdposition where 1=1 " + filter +" group by firmid order by firmid";
			  
			  System.out.println(sql);
	  }
}
