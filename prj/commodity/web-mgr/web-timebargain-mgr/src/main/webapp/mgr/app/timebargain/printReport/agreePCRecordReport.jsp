<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title>Э�齻�ռ�¼�� </title>
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
	String startClearDate = request.getParameter("startClearDate");
	String endClearDate    = request.getParameter("endClearDate");
	
	String filter = " 1=1 ";
	if(chcekNull(startClearDate)){
		filter += " and t.cleardate>=to_date('"+startClearDate +"','yyyy-MM-dd')";
	}
	if(chcekNull(endClearDate)){
		filter += " and t.cleardate<=to_date('"+endClearDate +"','yyyy-MM-dd')";
	}
	//query data
	String sql = " select to_char(t.cleardate,'yyyy-MM-dd') cleardate,t.commodityid commodityid,t.bs_flag bs_flag, "+
				" t.firmid firmid,t.customerid customerid,t.quantity quantity,t.holdprice holdprice, "+
				" t.price price,t.Close_PL Close_PL from t_h_trade t where "+filter+
				" and t.ordertype=2 and t.tradetype=6 order by to_char(t.cleardate,'yyyy-MM-dd'),t.commodityid,t.bs_flag,t.firmid";
	
		DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	    List list=dao.queryBySQL(sql);    
    	%>    
	<br><center class="reportHead">Э�齻�ռ�¼��</center><br><br>
	<table align="center" width="600px">
	<tr>
		<td class="reportRight" colspan="9">��ʼ����:<%=startClearDate %>&nbsp;&nbsp;��������:<%=endClearDate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">Э�齻������</td>
	<td class="td_reportMdHead">��Ʒ����</td>
	<td class="td_reportMdHead">��/����</td>
	<td class="td_reportMdHead">�����̴���</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">����</td>
	<td class="td_reportMdHead">������</td>
	<td class="td_reportMdHead">ת�ü�</td>
	<td class="td_reportRdHead">ƽ��ӯ��</td>
	</tr>	
    	<%
	   	BigDecimal sumQuantity = new BigDecimal(0);//С��
	   	
	   	BigDecimal countQuantity = new BigDecimal(0);//�ܼ�
		
		String strMark = null;//���
		String mark = null;
		int num = 0;
		int size = list.size()-1;
    for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a);
    	
    	String bs_flag = innerMap.get("bs_flag").toString();
    	String relBS = "";
    	if ("1".equals(bs_flag)) {
    		relBS = "��";
    	}else if ("2".equals(bs_flag)) {
    		relBS = "��";
    	}
    	
    	num = a;
    	mark = turnToStr(innerMap.get("cleardate"))+turnToStr(innerMap.get("commodityid"))+turnToNum(innerMap.get("bs_flag"));
    	if(!mark.equals(strMark)){
    		if(strMark != null){
    		%>	
				<tr>
					<td class="td_reportMd" colspan="3">&nbsp;</td>
					<td class="td_reportMd"><b>�ϼ�:</b></td>
					<td class="td_reportMd">&nbsp;</td>
					<td class="td_reportMd1"><b><%=sumQuantity %></b></td>
					<td class="td_reportRd" colspan="3">&nbsp;</td>
				</tr>
    		<%
	    		sumQuantity = new BigDecimal(0);
    		}
   			strMark = mark;
    	}
		%>	
	<tr>
	    <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("cleardate")) %></td>
	    <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	    <td class="td_reportMd">&nbsp;<%=relBS %></td>
	    <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	    <td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	    <td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>	
	    <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("holdprice")) %></td>		
	    <td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("price")) %></td>
	    <td class="td_reportRd1">&nbsp;<%=turnToNum2(innerMap.get("Close_PL")) %></td>
	</tr>
	<%   		
   		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("quantity")));   		
   		countQuantity = countQuantity.add(turnToNum(innerMap.get("quantity")));
	}
	if(size == num){
	%>	
	<tr>
		<td class="td_reportMd" colspan="3">&nbsp;</td>
		<td class="td_reportMd"><b>�ϼ�:</b></td>
		<td class="td_reportMd">&nbsp;</td>
		<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
		<td class="td_reportRd" colspan="3">&nbsp;</td>
	</tr>
	<%
	}
	if(list.size()>0){
	%>	
	<tr>
	<td class="td_reportMd" colspan="5"><b>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countQuantity %></b></td>
	<td class="td_reportRd1" colspan="3">&nbsp;</td>
	</tr>
	<%	
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="9" align="left">
			�޷���������Ϣ��
		</td>
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