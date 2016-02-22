<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.rmi.Naming"%>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%@ page import="java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="gnnt.MEBS.settlement.model.*"%>
<html>
	<head>

		<title>
		</title>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
%>
	</head>
	<body>
<%	
String firmID = (String)session.getAttribute("FIRMID");
String CommodityID = request.getParameter("Commodity");
String bs_flag = request.getParameter("bs_flag");
String quantity = request.getParameter("quantity");
boolean flag=true;
int result = 0;
float CurPrice = 0 ;
float settleFee = 0;
String mFirm="";

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/mgr");
		conn = ds.getConnection();
	    conn.setAutoCommit(false);
	    
		stmt = conn.createStatement();
		String commoditySql = "select * from t_quotation t ,T_Commodity c where   t.commodityid=c.commodityid and t.COMMODITYID = '"+CommodityID+"'";
		rs = stmt.executeQuery(commoditySql);
		float contractfactor = 0;
		while (rs.next()) {
			CurPrice = rs.getFloat("CurPrice");
			contractfactor = rs.getFloat("contractfactor");
			break;
		}
		//交割费 0 
		settleFee = Integer.parseInt(quantity)*contractfactor*(CurPrice*1.2f); //当前价格 + 交割费 + 出/入库费 + 加工费
		
		//firmID="101000000000001";
		System.out.println("firmID:"+firmID);
		String mFirmSql = "select m.memberno from m_customerinfo m where m.customerno='"+firmID+"'";
		rs = stmt.executeQuery(mFirmSql); 
		while (rs.next()) {
			mFirm = rs.getString("memberNo");
			break;
		}
		System.out.println("mFirm:"+mFirm);
		String	insertSettleSql = "insert into t_ApplySettle(ID,COMMODITYID,FIRMID,PRICE,QUANTITY,UPDATETIME,BS_FLAG,STATUS,settleFee,m_firmID) " +
		" values(SEQ_T_APPLYSETTLE.Nextval,'"
			+ CommodityID +"','"
			+ firmID +"',"
			+ CurPrice +","
			+ quantity +","
			+" sysdate ,"
			+ bs_flag +","
			+ "0,"+settleFee+",'"
			+ mFirm +"')";
		System.out.println("插入交收表");
		stmt.executeUpdate(insertSettleSql);
		
        conn.commit();

	} catch(Exception e) {
		e.printStackTrace();
		flag=false;
		   try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
	}finally {
			closeStatement(rs, stmt);
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}



	//String computerIP = request.getRemoteAddr();

	String funId=null;
	String str="";
	if(flag==true){
	%>
	<fieldset width="95%">
			<legend>交割申报</legend>
			<table border="0" cellspacing="0" cellpadding="0" style="width: 580px;" height="35" class="Noprint">
				<tr height="35">
				
					<td align="right" id="pawdname">申报成功<br>
						<table>
							<tr>
								<td>交收商品:</td>
								<td><%=CommodityID %></td>
							</tr>
							<tr>
								<td>交收数量:</td>
								<td><%=quantity %>&nbsp;手</td>
							</tr>
							<tr>
								<td>交收价格:</td>
								<td><%=CurPrice*1.2f %>&nbsp;元/千克</td>
							</tr>
							<tr>
								<td colspan="2">交割费用共计&nbsp;<%=settleFee %>&nbsp;&nbsp;元</td>
							</tr>
						</table>
					</td>
				</tr>
				
					<tr height="35">
					
				<tr height="35">
					<td align="right" colspan=2>
						<input id="sub_btn" type="button" class="smlbtn" value="返回" onclick="back()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td></td>
				</tr>
					<td></td>
				</tr>
			</table>
	<%
	}else{
	%>
		<table border="0" cellspacing="0" cellpadding="0" style="width: 580px;" height="35" class="Noprint">
				<tr height="35">
				
					<td align="right" id="pawdname">
					<b>操作异常！ </b>
					</td>
				</tr>
				<tr height="35">
					<td align="right" colspan=2>
						<input id="sub_btn" type="button" class="smlbtn" value="返回" onclick="back()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td></td>
				</tr>
					<td><br><br></td>
				</tr>
			</table>
	<% 
	}	
	 %>
</fieldset>
</body>	 
</html>
<SCRIPT LANGUAGE="JavaScript">

function back(){
	window.location.href = "applySettle.jsp" ;
}
</SCRIPT>
<script type="text/javascript">
//禁止右键菜单
if (window.Event) 
  document.captureEvents(Event.MOUSEUP); 
function nocontextmenu() 
{
 event.cancelBubble = true
 event.returnValue = false;
 return false;
}
 
function norightclick(e) 
{
 if (window.Event) 
 {
  if (e.which == 2 || e.which == 3)
   return false;
 }
 else
  if (event.button == 2 || event.button == 3)
  {
   event.cancelBubble = true
   event.returnValue = false;
   return false;
  }
}
document.oncontextmenu = nocontextmenu;  // for IE5+
document.onmousedown = norightclick;  // for all others
</script>

