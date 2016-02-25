<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.collections.map.ListOrderedMap" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManage" %>
<jsp:directive.page import="java.math.BigDecimal"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<base target="_self">
<%
	//�����Ϣ
	String matchID= (String)request.getParameter("matchID");
	String rsID= (String)request.getParameter("rsID");
	Map matchMsg = SettleManage.getSettle(matchID);
	request.setAttribute("matchMsg",matchMsg);
	long quantity = ((BigDecimal)matchMsg.get("QUANTITY")).longValue();
	String commodityId = (String)matchMsg.get("COMMODITYID");
	String firmid = (String)matchMsg.get("FIRMID_S");
	//��ѡ�Ĳֵ���Ϣ
	String filterSql = " where firmid = '"+firmid+"' and type!=2 and breedid=(select breedid from t_commodity where commodityid='"+commodityId+"') and (weight - frozenweight)>="+quantity+"*(select ContractFactor from t_commodity where commodityid='"+commodityId+"') ";
	List list = SettleManage.getRegStockIdList(filterSql);
	if(list==null||list.size()==0){
		filterSql=" where firmid = '"+firmid+"' and type!=2 and breedid=(select breedid from T_SettleCommodity where commodityid='"+commodityId+"') and (weight - frozenweight)>="+quantity+"*(select ContractFactor from T_SettleCommodity where commodityid='"+commodityId+"') ";
		list = SettleManage.getRegStockIdList(filterSql);
	}
	ListOrderedMap map = null;
	// ����ע��ֵ����Ƿ��Ѵ��ڵ�ǰ������Զ�Ӧ�Ĳֵ�
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
					alert("��¼�ɹ���");
					window.returnValue=0;
					window.close();
				</script>
				<%
			} else {
				SettleManage.updateSettleMatchRegStock(matchId,restockId);
			}
			%>
			<script>
				alert("�ֵ��޸ĳɹ���");
				window.returnValue=0;
				window.close();
			</script>
			<%
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("��¼ʧ��,�����쳣:"+e.getMessage());
			%>
			<script>
				alert("��¼ʧ��,�����쳣��");
				window.returnValue=0;
				window.close();
			</script>
			<%
		}
	}
%>
<html>
<head>
	<title>��¼�ֵ�</title>
</head>
<body>
	<form name="frm" method="post" action="">
	<input type="hidden" name="matchId" value="${matchMsg.MATCHID }">
	<input type="hidden" name="quantity" value="${matchMsg.QUANTITY }">
	<br>
	<fieldset width="100%">
		<legend class="common"><b>��¼�ֵ�</b></legend>
		<BR>
		<span>
			<table class="common" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
				<!-- commodity -->
				<tr class="common">
					<td align="right" width="50%" height="30">��Ա�ţ�</td>
					<td align="left" width="50%" height="30">${matchMsg.MATCHID }</td>
				</tr>
				<!-- amount -->
				<tr class="common">
					<td align="right" width="50%" height="30">����������</td>
					<td align="left" width="50%" height="30">${matchMsg.QUANTITY }</td>
				</tr>
				<!-- commodity -->
				<tr class="common">
					<td align="right" width="50%" height="30">��Ʒ���룺</td>
					<td align="left" width="50%" height="30">${matchMsg.COMMODITYID }</td>
				</tr>
				<!-- buyer -->
				<tr class="common">
					<td align="right" width="50%" height="30">�򷽴��룺</td>
					<td align="left" width="50%" height="30">${matchMsg.FIRMID_B }</td>
				</tr>
				<!-- seller -->
				<tr class="common">
					<td align="right" width="50%" height="30">�������룺</td>
					<td align="left" width="50%" height="30">${matchMsg.FIRMID_S }</td>
					<td align="left" width="50%"> </td>
				</tr>
				<!-- s_restock -->
				<tr class="common">
					<td align="right" width="50%" height="30">�ֵ���ţ�</td>
					<td align="left" width="50%">
						<select name="restockId">
							<option value="">��ѡ��</option>
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
						<input type="button" name="subbut" class="button" onclick="submitMSG();" value="�ύ" class="mdlbtn">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" onclick="window.close();" value="�ر�" class="mdlbtn">
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
			alert("��ѡ��ֵ���ţ�");
		}
		else
		{
			confirm("ȷ���ύ��")
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
				//-1���ͨ�� ���Խ����ύ¼�룬���ڵ���0��ͨ��������ֵ��ʵ�ʿɽ��յ�����
				if(sign == -1){
					//alert("������Գɹ�,����¼�룡");
				}else if(sign >= 0){
					alert("���ύ�������������������["+sign+"],������¼�����ݣ�");
					document.getElementById("div3").innerHTML="<font color='red'>���������["+sign+"]</font>";
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
		var restockmsg="<select name='restockid' id='restockid' onChange='select(4);'><option>��ѡ��</option>";
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
		var buymsg="<select name='buyerid' id='buyerid' onChange='select(1);'><option>��ѡ��</option>";
		var sellmsg="<select name='sellerid' id='sellerid' onChange='select(2);'><option>��ѡ��</option>";
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
						strValue="��Լ";
					}else if(selectValue == 2){
						strValue="��ΥԼ";
					}else if(selectValue == 3){
						strValue="����ΥԼ";
					}else if(selectValue == 4){
						strValue="˫��ΥԼ";
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
	//���ǰ��ո�
	function trim ( str ) {
		regExp = /\S/
		
		if ( regExp.test(str) == false )
			return "";
		
		regExp = /(^\s*)(.*\S)(\s*$)/
		regExp.exec(str);
		
		return RegExp.$2;
	}
</script>