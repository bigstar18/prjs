<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.collections.map.ListOrderedMap" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManage" %>
<jsp:directive.page import="java.math.BigDecimal"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<base target="_self">
<%
	//配对信息
	String matchID= (String)request.getParameter("matchID");
	String rsID= (String)request.getParameter("rsID");
	Map matchMsg = SettleManage.getSettle(matchID);
	request.setAttribute("matchMsg",matchMsg);
	long quantity = ((BigDecimal)matchMsg.get("QUANTITY")).longValue();
	String commodityId = (String)matchMsg.get("COMMODITYID");
	String firmid = (String)matchMsg.get("FIRMID_S");
	//可选的仓单信息
	String filterSql = " where firmid = '"+firmid+"' and type!=2 and breedid=(select breedid from t_commodity where commodityid='"+commodityId+"') and (weight - frozenweight)>="+quantity+"*(select ContractFactor from t_commodity where commodityid='"+commodityId+"') ";
	List list = SettleManage.getRegStockIdList(filterSql);
	if(list==null||list.size()==0){
		filterSql=" where firmid = '"+firmid+"' and type!=2 and breedid=(select breedid from T_SettleCommodity where commodityid='"+commodityId+"') and (weight - frozenweight)>="+quantity+"*(select ContractFactor from T_SettleCommodity where commodityid='"+commodityId+"') ";
		list = SettleManage.getRegStockIdList(filterSql);
	}
	ListOrderedMap map = null;
	// 遍历注册仓单，是否已存在当前交收配对对应的仓单
	boolean result = true;
	for (int i = 0; i < list.size(); i++) {
		map = (ListOrderedMap)list.get(i);
		if (map.get("REGSTOCKID").equals(rsID)){
			result = false;
			break;
		}
	}
	if(result){
	  	list.add(matchMsg);
	}
	request.setAttribute("list",list);
	
	if("save".equals(request.getParameter("opt"))){
		String restockId = request.getParameter("restockId");
		String matchId = request.getParameter("matchId");
		
		try{
			if(SettleManage.getSettleMatchRegStockId(matchId)) {
				SettleManage.updateSettleMatch(matchId,restockId);
				%>
				<script>
					alert("补录成功！");
					window.returnValue=0;
					window.close();
				</script>
				<%
			} else {
				SettleManage.updateSettleMatchRegStock(matchId,restockId);
			}
			%>
			<script>
				alert("仓单修改成功！");
				window.returnValue=0;
				window.close();
			</script>
			<%
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("补录失败,操作异常:"+e.getMessage());
			%>
			<script>
				alert("补录失败,操作异常！");
				window.returnValue=0;
				window.close();
			</script>
			<%
		}
	}
%>
<html>
<head>
	<title>补录仓单</title>
</head>
<body>
	<form name="frm" method="post" action="">
	<input type="hidden" name="matchId" value="${matchMsg.MATCHID }">
	<input type="hidden" name="quantity" value="${matchMsg.QUANTITY }">
	<br>
	<fieldset width="100%">
		<legend class="common"><b>补录仓单</b></legend>
		<BR>
		<span>
			<table class="common" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
				<!-- commodity -->
				<tr class="common">
					<td align="right" width="50%" height="30">配对编号：</td>
					<td align="left" width="50%" height="30">${matchMsg.MATCHID }</td>
				</tr>
				<!-- amount -->
				<tr class="common">
					<td align="right" width="50%" height="30">交收数量：</td>
					<td align="left" width="50%" height="30">${matchMsg.QUANTITY }</td>
				</tr>
				<!-- commodity -->
				<tr class="common">
					<td align="right" width="50%" height="30">商品代码：</td>
					<td align="left" width="50%" height="30">${matchMsg.COMMODITYID }</td>
				</tr>
				<!-- buyer -->
				<tr class="common">
					<td align="right" width="50%" height="30">买方代码：</td>
					<td align="left" width="50%" height="30">${matchMsg.FIRMID_B }</td>
				</tr>
				<!-- seller -->
				<tr class="common">
					<td align="right" width="50%" height="30">卖方代码：</td>
					<td align="left" width="50%" height="30">${matchMsg.FIRMID_S }</td>
					<td align="left" width="50%"> </td>
				</tr>
				<!-- s_restock -->
				<tr class="common">
					<td align="right" width="50%" height="30">仓单编号：</td>
					<td align="left" width="50%">
						<select name="restockId">
							<option value="">请选择</option>
							<c:forEach items="${list }" var="regStock">
								<option value="${regStock.REGSTOCKID }">${regStock.REGSTOCKID }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr><td colspan="2">&nbsp;</td></tr>
				<!-- submit -->
				<tr class="common">
					<td colspan="2" align="center" height="30">
						<input type="button" name="subbut" class="button" onclick="submitMSG();" value="提交" class="mdlbtn">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" onclick="window.close();" value="关闭" class="mdlbtn">
						<input type="hidden" name="opt">
				</tr>
			</table><br>
		</span>
		</fieldset>
	</form>
</body>
</html>
<script type="text/javascript">

	<%
		if(rsID!=null&&!"".equals(rsID)){
	%>
			frm.restockId.value= <%=rsID%>;
	<%
		}
	%>
    
	function submitMSG()
	{
		var restockId = frm.restockId.value;
		if(restockId == "")
		{
			alert("请选择仓单编号！");
		}
		else
		{
			confirm("确认提交？")
			{
				frm.opt.value = "save";
				frm.submit();
			}
		}
	}
	
	
	function firstRequest(v)
	{
		request.onreadystatechange = fisrtResponse;
		request.open("post","getData.jsp?"+v,false);
		request.send();
		request.abort();
		firstCopyValues(buyList,sellerList);
	}
	function secondRequest(v)
	{
		request.onreadystatechange = secondResponse;
		request.open("post","checkData.jsp?"+v,false);
		request.send();
		request.abort();
		
		//secondCopyValues(responseResult);
	}
	function thirdRequest(v)
	{
		request.onreadystatechange = thirdResponse;
		request.open("post","getRegStocks.jsp?"+v,false);
		request.send();
		request.abort();
		
		//secondCopyValues(responseResult);
	}
	function fisrtResponse()
	{
		// only if req shows "loaded"
		if (request.readyState == 4) {
		   // only if "OK"
		   //if (request.status == 200) {
			var result = request.responseText;
			var bs = result.split("[]");
			buyList = bs[0];			
			sellerList = bs[1];
			//}	
		}
	}
	function secondResponse()
	{
		// only if req shows "loaded"
		if (request.readyState == 4) {
		   // only if "OK"
		   //if (request.status == 200) {
			var responseResult = request.responseText;
			var sign = trim(responseResult);
				//-1检查通过 可以进行提交录入，大于等于0不通过，返回值是实际可交收的数量
				if(sign == -1){
					//alert("交收配对成功,可以录入！");
				}else if(sign >= 0){
					alert("您提交的数量大于最大交收数量["+sign+"],请重新录入数据！");
					document.getElementById("div3").innerHTML="<font color='red'>最大交收数量["+sign+"]</font>";
					frm.Quantity.value="";
					frm.Quantity.focus();
				}
			//}	
		}
	}
	function thirdResponse()
	{
		var sign = "";
		// only if req shows "loaded"
		if (request.readyState == 4) {
		   // only if "OK"
		   //if (request.status == 200) {
			var responseResult = request.responseText;
			sign = trim(responseResult);
		}
		var regstocks = sign.split(";");
		var restockmsg="<select name='restockid' id='restockid' onChange='select(4);'><option>请选择</option>";
		for(var a=0;a<regstocks.length;a++)
		{
			if(regstocks[a]!="")
			{
				restockmsg = restockmsg + "<option value='"+regstocks[a]+"'>"+regstocks[a]+"</option>";
			}
		}
		restockmsg = restockmsg + "</select>";
		document.getElementById("div4").innerHTML=restockmsg;
	}
	function firstCopyValues(bl,sl)
	{
		var blist = bl.split(";");
		var slist = sl.split(";");
		var buymsg="<select name='buyerid' id='buyerid' onChange='select(1);'><option>请选择</option>";
		var sellmsg="<select name='sellerid' id='sellerid' onChange='select(2);'><option>请选择</option>";
		for(var b = 0 ; b < blist.length ; b ++)
		{
			if(blist[b]!=""){
				buymsg = buymsg+"<option value='"+blist[b]+"'>"+blist[b]+"</option>";
			}
		}
		for(var s = 0 ; s < slist.length ; s ++)
		{
			if(slist[s]!=""){
				sellmsg = sellmsg+"<option value='"+slist[s]+"'>"+slist[s]+"</option>";
			}
		}
		buymsg = buymsg+"</select>";
		sellmsg = sellmsg+"</select>";
		document.getElementById("div1").innerHTML=buymsg;
		document.getElementById("div2").innerHTML=sellmsg;
	}
	
	function select(v)
	{
		var selectValue ;
			switch (v)
			{
				case 1:
					selectValue = document.getElementById("buyerid").value;
					frm.FirmID_B.value = selectValue;
					frm.FirmID_B.readOnly = true;
					break;
				case 2:
					selectValue = document.getElementById("sellerid").value;
					var commodityid = frm.CommodityID.value;
					var filter = "commodityid="+commodityid+"&FirmID_S="+selectValue;
					thirdRequest(filter);
					frm.FirmID_S.value = selectValue;
					frm.FirmID_S.readOnly = true;
					break;
				case 3:
					selectValue = document.getElementById("handleResult").value;
					var strValue = "";
					if(selectValue == 1){
						strValue="履约";
					}else if(selectValue == 2){
						strValue="买方违约";
					}else if(selectValue == 3){
						strValue="卖方违约";
					}else if(selectValue == 4){
						strValue="双方违约";
					}
					frm.result.value = strValue;
					frm.result.readOnly = true;
					break;
				case 4:
					selectValue = document.getElementById("restockid").value;
					frm.regsto.value = selectValue;
					frm.regsto.readOnly = true;
					break;	
			}
	}
	//清除前后空格
	function trim ( str ) {
		regExp = /\S/
		
		if ( regExp.test(str) == false )
			return "";
		
		regExp = /(^\s*)(.*\S)(\s*$)/
		regExp.exec(str);
		
		return RegExp.$2;
	}
</script>