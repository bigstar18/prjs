<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title>���շ���Ʒ�ɽ���ͳ�Ʊ�</title>
</head>
<body>
	<table align="center" width="600px" border="0">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
					<tr>
					<td align="right">
						<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ">
		     		 
						</div>
					</td>
					</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
				<table align="center" width="600px">
					<tr>
						<td>
					  </td>
					 </tr>
				</table>
			</td>
			</tr>
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
						
 <%
	String cleardate = request.getParameter("cleardate");
	String commodityid = request.getParameter("commodityID");
	
	String filter = " 1=1 ";
	if(chcekNull(cleardate)){
		filter += " and t.cleardate=to_date('"+cleardate +"','yyyy-MM-dd')";
	}
	if(chcekNull(commodityid)){
		filter += " and t.commodityid='"+commodityid+"'";
	}
	String sql = " select t.firmid firmid,c.name name,sum(case when t.ordertype=1 and t.bs_flag=1 then Quantity else 0 end) buyAgree, "+
				" sum(case when t.ordertype=1 and t.bs_flag=2 then Quantity else 0 end) sellAgree, "+
				" sum(case when t.ordertype=2 and t.bs_flag=1 then Quantity else 0 end) buyTransfer, "+
				" sum(case when t.ordertype=2 and t.bs_flag=2 then Quantity else 0 end) sellTransfer, "+
				" sum(case when t.ordertype=2 and t.bs_flag=1 and t.tradetype=3 then Quantity else 0 end) insteadBuyTransfer, "+
				" sum(case when t.ordertype=2 and t.bs_flag=2 and t.tradetype=3 then Quantity else 0 end) insteadSellTransfer, "+
				" sum(Quantity) allQuantity from t_h_trade t,t_h_commodity c where  "+filter+" and t.commodityid=c.commodityid and t.cleardate=c.cleardate group by t.firmid,c.name order by t.firmid,c.name";
		DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);     
    	
	  String mark = null;
	    %>
	<br><center class="reportHead">���շ���Ʒ�ɽ���ͳ�Ʊ�</center><br><br>
	<%
	if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">�޷���������Ϣ��</font></b></div>
    <%
    }
    for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a);
    	
	    if(mark == null){
		%>
	<table align="center" width="600px" border="0">
	<tr>		
		<td class="reportLeft">��Ʒ����:<%=commodityid %>&nbsp;��Ʒ����:<%=turnToStr(innerMap.get("name")) %></td>
		<td class="reportRight">����:<%=cleardate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">�����̴���</td>
	<td class="td_reportMdHead">�������</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">���ת��</td>
	<td class="td_reportMdHead">����ת��</td>
	<td class="td_reportMdHead">��Ϊ���ת��</td>
	<td class="td_reportMdHead">��Ϊ����ת��</td>
	<td class="td_reportRdHead">����</td>
	</tr>
	<%
    	mark = turnToStr(innerMap.get("firmid")) ;
	}
	%>
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("buyAgree")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sellAgree")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("buyTransfer")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("sellTransfer")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("insteadBuyTransfer")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("insteadSellTransfer")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("allQuantity")) %></td>
	</tr>
	<%
	}
	%>
	</table>
					  </td>
					 </tr>
					 <tr><td></td></tr>
				</table>
				</div>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
						<tr>
						<td align="right">
						<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
						     <input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ"> 
						</div>
						</td>
						</tr>
				</table>
			</td>	
		</tr>
	</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>