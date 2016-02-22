<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%@ include file="ajax.jsp"%>
<%@ page import="java.text.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="gnnt.MEBS.timebargain.tradeweb.model.*"%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">	
	<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
	
	<link rel="stylesheet" href="css/button.css" type="text/css"/>
	<link rel="stylesheet" href="css/print.css" type="text/css"/>
	<link rel="stylesheet" href="css/report.css" type="text/css"/>
    <title>出入金操作</title>
	<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	//交易时间记录集
	ResultSet commodity = null;
	//交易时间字符串
	String strTradeTime = "";
	List<Commodity> CommodityList = new ArrayList<Commodity>();
	try {
		
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/mgr");
		conn = ds.getConnection();

		stmt = conn.createStatement();
		String commoditySql = "select * from t_commodity t where t.tradestatus ='N'";
		rs = stmt.executeQuery(commoditySql);

		while (rs.next()) {
			Commodity Commodity = new Commodity();
			Commodity.setCommodityID(rs.getString("CommodityID"));
			Commodity.setName(rs.getString("name"));
			CommodityList.add(Commodity);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			if (commodity != null) {
				commodity.close();
				commodity = null;
			}
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
<script language="JavaScript">
	function doSubmit() {
		var str = "确认交割？";
		var txtValue=frm.quantity.value;
		if(!txtValue.match(/^[0-9]*[1-9][0-9]*$/)){
			alert("请输入正确数量");
			return;
		}
		if(frm.Commodity.value==-1){
			alert("当前无可申请交割商品");
			return ;
		}
		if(confirm(str)){
			document.getElementById("sub_btn").disabled = 'disabled';
			frm.submit();
		}
	}
	function changeCommodity(){
		if(frm.Commodity.value != -1){
			//if(frm.Commodity.value =='Ag'){
			var commodity = frm.Commodity.value;
			if(commodity.indexOf("AG")>0 || commodity.indexOf("Ag")>0 || commodity.indexOf("ag")>0){
			document.all.quantity.options.length = 0; 
				for(var i=1;i<11;i++){
					var varItem = new Option(i*15, i*15);  
			   		document.all.quantity.options.add(varItem);   
			   		document.getElementById("unit").innerHTML = '公斤';
				}
			}else{
				document.all.quantity.options.length = 0; 
				for(var i=1;i<11;i++){
					var varItem = new Option(i*10, i*10);  
			   		document.all.quantity.options.add(varItem);   
			   		document.getElementById("unit").innerHTML = '吨';
				}
			}
			getCommodityHold(frm.Commodity.value,frm.bs_flag.value,'<%=(String)session.getAttribute("FIRMID")%>');
		}else{
			document.all.quantity.options.length = 0; 
		    document.getElementById("unit").innerHTML = '';
		}
	}
		
</script>

  </head>
  <body oncontextmenu="return false">
  	<form id="frm" action="SettleAction.jsp" method="post"  >
		<fieldset width="95%">
			<legend>交割申报</legend>
			<table border="0" cellspacing="0" cellpadding="0" style="width: 580px;" height="35">
				<tr height="35">
					<td name="CommodityID" align="right">选择合约:&nbsp;</td>
					<td align="left">
						<select  name="Commodity" class="normal" style="width: 150px" onChange="changeCommodity()">
							<OPTION value="-1">请选择</OPTION>
						<% 
							if(CommodityList != null && CommodityList.size()>0){
								for(int i=0;i<CommodityList.size();i++) {
									Commodity Commodity=(Commodity)CommodityList.get(i);
								%>
									<option value="<%=Commodity.getCommodityID()%>"><%=Commodity.getName()%></option>
								<%
								}
							}
							%>
						</select>
					</td>
				</tr>
						<tr height="35">
					<td align="left">
			
			<input  name="bs_flag"  type="hidden"  value="1" maxlength=6 style="width: 100px" class="text">

					</td>
				</tr>
				<tr height="35">
					<td align="right" id="pawdname">交割数量:&nbsp;</td>
				<td align="left">
			<select  name="quantity" id='quantity' class="normal" style="width: 150px" >
							<OPTION value="-1">请选择</OPTION>
					
						</select>
				<span id='unit'> </span>
					</td>
					
					<td></td>
				</tr>
			
				<!-- 
				<tr height="35">
					<td align="right">交割价:&nbsp;</td>
					<td align="left" >
						<input  name="money2" value="" type=text  class="text" maxlength="13" style="width: 100px" onblur="checkNum()">
						<input  name="money" value="" type=hidden >
					</td>
					<td>（单位：元）</td>
				</tr>
				 -->
				<!-- 
				<tr height="35">
					<td align="right" id="pawdname">marketpwd:&nbsp;</td>
				<td align="left">
						<input  name="password" value="" type="password" maxlength=6 style="width: 100px" class="text">
					</td>
					<td></td>
				</tr>
				 -->
				<tr height="35">
					<td align="right" colspan=2>
						<input id="sub_btn" type="button" class="smlbtn" value="确认" onClick="doSubmit();">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="smlbtn" value="重置" onClick="frm.reset();">
					</td>
					<td></td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset width='95%' id="fie" style=";">
				<legend>交收详情</legend>
				<table border="0" cellspacing="0" cellpadding="0" style="width: 580px;" height="35">
					<tr height="35" id="tr01">
						<td width="20%" align="right">						<td   id="holdqty" name="holdqty"></td>
						&nbsp;</td>
						<td align="left"><p>
						
						<br></p>
						</td>
					</tr>
					<tr height="35" id="tr01">
						<td width="20%" align="right">						<td   id="holdqty1" name="holdqty1"></td>
						&nbsp;</td>
						<td align="left"><p>
						
						<br></p>
						</td>
					</tr>
						<tr height="35" id="tr01">
						<td width="20%" align="left"><font color='red' size =2px>注：交割价格可能会随着行情而变动</font> 					</td>
						&nbsp;</td>
						<p>
						
						
					</tr>
					<!--
					<tr height="35" id="tr05">
						<td width="20%" align="right">汇款账号&nbsp;</td>
						<td align="left">
							<input  name="OutAccount" id="OutAccountID" value="" type=text  class="text"style="width: 100px" onblur="check(this)">
						</td>
					</tr>
					-->
				</table>
				</fieldset>
			<!-- 
	
		<fieldset width='95%'>
			<legend>市场资金信息</legend>
			<table id="tableList" border="0" cellspacing="0" cellpadding="0" style="width: 650px;">
			<tHead>
			<tr  height="20">
				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center" colspan="3">交易账号</td>
				<td class="panel_tHead_MB" align="center">期初权益</td>
				<td class="panel_tHead_MB" align="center">当日出入金</td>
				<td class="panel_tHead_MB" align="center">当前可出金额</td>
				<td class="panel_tHead_MB_last" align="center">可用保证金</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
			</tHead>
		 </td>
				<td class="underLine" align="center">2</td>
				<td class="underLine" align="center">3</td>
				<td class="underLine" align="right">4&nbsp;</td>
				<td class="underLine" align="right">5&nbsp;</td>
				<td class="underLine" align="right">6&nbsp;</td>
				<td class="underLine_last" align="right" TITLE="在途资金是因与银行通信异常未收到立即回执时，导致资金处于在途状态。在途资金会在每天日终时与银行核对，确定转账状态。您暂时无法使用在途资金。">
				h&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="left" colspan="3">合计：</td>
				<td class="underLine" align="right">2&nbsp;</td>
				<td class="underLine" align="right">3&nbsp;</td>
				<td class="underLine" align="right">&nbsp;</td>
				<td class="underLine_last" align="right">5&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			</tBody>
			<tFoot>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="7"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
			</table>
		</fieldset>
		 -->
	</form>	
  </body>
</html>
