<%@ page contentType="text/html;charset=GBK"%>
<%@ page import = "java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%
	//2010-01-08
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	ResultSet rsTime = null;
	String ti = null;
	try{
        Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/hqForYT");
		conn = ds.getConnection();
		stmt = conn.createStatement();
		String CurrentTime = "select max(t.createtime) as time from t_quotation t";
		rsTime = stmt.executeQuery(CurrentTime);
		if(rsTime.next()){
			Timestamp tf = rsTime.getTimestamp("time");
			if(tf != null){
				ti = tf.toString();
			}
		}
		rsTime.close();
		rsTime = null;

//		String CurrentData = "select * from Commodity a inner join CurrentData b on (a.Commodity_ID = b.Code) where a.status='N' order by b.code";
		String CurrentData = "select distinct a.CommodityID,	 "
   +"    a.yesterbalanceprice,									"
    +   "b.name,													 "
    +"   a.HighPrice,												 "
     +"  a.LowPrice,												 "
      +" a.CurPrice,												 "
       +"a.CreateTime,											 "
       +"b.MinPriceMove,											 "
       +"a.spread,												 "
       +"nvl(LastClose.ClosePrice, 0) ClosePrice,											 "
       +" a.openprice,												 "
        +"a.curamount,												 "
        +"a.Reservecount,											 "
        +"a.Price,													 "
        +"c.CURPRICE_B,											 "
        +"c.CURPRICE_S        										 "
   +"from T_Quotation a, T_Commodity b,(select Commodityid,max(CURPRICE_B) CURPRICE_B, max(CURPRICE_S) CURPRICE_S from t_c_quotation_rt group by commodityid) c,	 "
    +"(select commodityid, closeprice from t_quotation_h h where h.cleardate = (select max(cleardate) from t_quotation_h)) LastClose "
  +"where b.Status = 1												 "
    +" and a.CommodityID = b.CommodityID and a.commodityid= c.commodityid and a.commodityid = LastClose.commodityId(+)";
		//String CurrentData = "select * from CurrentData where Code <> 'SYS' order by code";
		rs=stmt.executeQuery(CurrentData);

		DecimalFormat df = new DecimalFormat("0.00");
		String CurrentDiff = "";
		String CurPrice = "";
		while(rs.next()){
			CurPrice = df.format(rs.getDouble("CurPrice"));
			if(CurPrice.equals("0.00")){
				CurrentDiff = "0.00";
			}else{
				CurrentDiff = df.format(rs.getBigDecimal("CurPrice").doubleValue() - rs.getBigDecimal("YesterBalancePrice").doubleValue());
			}
			out.print(rs.getString("COMMODITYID")+",");			//商品代码
			out.print(rs.getString("name")+",");			 //	 商品名
//			out.print(df.format(rs.getDouble("YesterBalancePrice"))+",");
			out.print(df.format(rs.getDouble("closeprice"))+",");	//昨收盘价
			out.print(df.format(rs.getDouble("OpenPrice"))+",");	//今开市价
			out.print(df.format(rs.getDouble("HighPrice"))+",");	//最高价
			out.print(df.format(rs.getDouble("LowPrice"))+",");		//最低价
			out.print(CurPrice+",");								//最新价
			out.print(rs.getInt("CurAmount")+",");				  //现量(最新价所对应的成交量)
			out.print(rs.getInt("ReserveCount")+",");			  //行情订货量
	//		out.print(df.format(rs.getDouble("BalancePrice"))+",");
	//		out.print(df.format(rs.getDouble("TotalMoney"))+",");
	//		out.print(rs.getInt("TotalAmount")+",");
			out.print(CurrentDiff+",");							   //涨跌
			out.print(df.format(rs.getDouble("YESTERBALANCEPRICE"))+",");			//结算价

			out.print(df.format(rs.getDouble("CURPRICE_B"))+",");	//买价  (买1)
			out.print(df.format(rs.getDouble("CURPRICE_S"))+",");	//卖价	(卖1)
			out.print(1+",");	//点差
			out.print(1+",");	//涨跌幅


//			out.print(rs.getInt("BuyAmount1")+",");
//			out.print(rs.getInt("SellAmount1")+",");
//			out.print(df.format(rs.getDouble("BuyPrice2"))+",");
//			out.print(df.format(rs.getDouble("SellPrice2"))+",");
//			out.print(rs.getInt("BuyAmount2")+",");
//			out.print(rs.getInt("SellAmount2")+",");
//			out.print(df.format(rs.getDouble("BuyPrice3"))+",");
//			out.print(df.format(rs.getDouble("SellPrice3"))+",");
//			out.print(rs.getInt("BuyAmount3")+",");
//			out.print(rs.getInt("SellAmount3")+",");
//			out.print(df.format(rs.getDouble("BuyPrice4"))+",");
//			out.print(df.format(rs.getDouble("SellPrice4"))+",");
//			out.print(rs.getInt("BuyAmount4")+",");
//			out.print(rs.getInt("SellAmount4")+",");
////			out.print(df.format(rs.getDouble("BuyPrice5"))+",");
	//		out.print(df.format(rs.getDouble("SellPrice5"))+",");
	//		out.print(rs.getInt("BuyAmount5")+",");
	//		out.print(rs.getInt("SellAmount5"));
			out.print("\n");
		}
		rs.close();
		rs = null;
		stmt.close();
		stmt = null;
		conn.close();
		conn = null;

		if(ti != null){
			out.print(ti.substring(0,19));
		}
	}catch(Exception e){
			e.printStackTrace();
	}finally{
		try{
			if(rsTime != null){
				rsTime.close();
				rsTime = null;
			}
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(stmt != null){
				stmt.close();
				stmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
%>