<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.MEBS.base.util.Arith"%>
<%@ include file="../../../public/session.jsp"%>
<html>
  <head>
    <title>�鿴���ռ��б�</title>
  </head>
  <body>
  <base target="_self" /><!--��仰�ǳ���Ҫ��ֻҪ���ϲ��ܱ�֤�ڵ��������е��÷���˴���������ٵ���һ���´���-->
	<fieldset width="95%">
			<legend>�鿴���ռ��б�</legend>
		
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" abbr="matchID" align="center">��Ա��</td>
				<td class="panel_tHead_MB" abbr="settleID" align="center">�ֲֵ���</td>
				<td class="panel_tHead_MB" abbr="quantity" align="center">����</td>
				<td class="panel_tHead_MB" abbr="price" align="center">�۸�</td>
				
				<td class="panel_tHead_RB"></td>
			</tr>
		</tHead>
		<tBody>		
		<% 
			double  totalMoney=0;
			double totalqty=0;
			List list=(List)request.getAttribute("list");
			for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
				totalMoney=Double.parseDouble(map.get("quantity").toString())*Double.parseDouble(map.get("price").toString())+totalMoney;
				totalqty=totalqty+Double.parseDouble(map.get("quantity").toString());
			}
			%>
		<c:forEach items="${list}" var="result">
			  		<tr height="22" onclick="selectTr();">
			  			<td class="panel_tBody_LB">&nbsp;</td>
			  			<td class="underLine" align="center"><c:out value="${result.matchID}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.settleID}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.quantity}"/>&nbsp;</td>
						<td class="underLine" align="center"><c:out value="${result.price}"/>&nbsp;</td>
			  			<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
	 </c:forEach>
	  	</tBody>
	  	<%
	  	double actualWeight=0;
	  	if(totalqty>0){
	  		actualWeight=Arith.format(Arith.div(totalMoney,totalqty), 5);
	  	}else{
	  		actualWeight=0;
	  	}
	  	
	  	 %>
	  	<tr><td class="panel_tBody_LB">&nbsp;</td>
	  	<td class="underLine" align="center" colspan="3">��Ȩƽ���ۣ�&nbsp;</td>
	  	<td class="underLine" align="center" ><%=actualWeight %></td>
	  	<td class="panel_tBody_RB">&nbsp;</td></tr>
	  		<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="4">
			
				</td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="30">
            <td align="center"><div >
			  &nbsp;&nbsp;&nbsp;<button class="lgrbtn" type="button" onclick="doReturn();">�ر�</button>
			</div></td>
			
        </tr>
    </table>
	</fieldset>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function doReturn(){
		window.close();
	}
</Script>	