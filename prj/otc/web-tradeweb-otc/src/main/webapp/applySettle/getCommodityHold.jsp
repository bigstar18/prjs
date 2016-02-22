<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.rmi.Naming"%>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%@ page import="java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="gnnt.MEBS.timebargain.settle.model.*"%>
<%
	//����ҳ���ǿ��ˢ��
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	String result = "";
	String result2 = "";
	String CommodityID=request.getParameter("CommodityID");
	String firmID=request.getParameter("firmID");
	System.out.println("------------�����̣�"+firmID);
	String bs_flag=request.getParameter("bs_flag");
	
	Context ctx = new InitialContext();
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	float CurPrice = 0 ;
	float  settleFee = 0;

	try{
	
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/mgr");
		conn = ds.getConnection();

		stmt = conn.createStatement();
		String commoditySql = "select * from t_quotation t ,T_Commodity c where  t.commodityid=c.commodityid and t.COMMODITYID = '"+CommodityID+"'";
		rs = stmt.executeQuery(commoditySql);
		float contractfactor = 0;
		while (rs.next()) {
			CurPrice = rs.getFloat("CurPrice");
			contractfactor = rs.getFloat("contractfactor");
			break;
		}
		settleFee = contractfactor*CurPrice*1.3f; 
		System.out.println("settleFee:"+settleFee);
		result2 = "ÿ�ֽ�����Ϊ:"+Math.ceil(settleFee);
		System.out.println("result2"+result2);
		if(CurPrice == 0 ){
			result = "����ƷĿǰ�������뽻��";
		}else{
			result = CurPrice+"";
		}
	}catch(Exception e){
		e.printStackTrace();
	} finally {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
%>
<%=result%>_<%=result2%>_<%=result%>
