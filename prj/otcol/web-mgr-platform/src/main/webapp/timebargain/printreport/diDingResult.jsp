<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%
	//get query condition and handle 
	String startCommodityID = request.getParameter("startCommodityID");
	String endCommodityID = request.getParameter("endCommodityID");
	String breedId =request.getParameter("breedId");
	String filter = " 1=1 ";
	if(chcekNull(startCommodityID)){
		if(!startCommodityID.equals("null")){
			filter += " and t.commodityid>='"+startCommodityID +"'";
		}else{
			startCommodityID = null;
		}
	}
	if(chcekNull(endCommodityID)){
		if(!endCommodityID.equals("null")){
			filter += " and t.commodityid<='"+endCommodityID +"'";
		}else{
			endCommodityID = null;
		}
	}
	if(chcekNull(breedId)){
		if(!breedId.equals("null"))
			filter += " and t.commodityId in(select commodityId from t_commodity where breedId='"+breedId+"')";
	}
	filter += " order by t.firmid";
	//query data
	//String sql = " select t.firmid firmid,to_char(t.cleardate,'yyyy-MM-dd') cleardate,t.customerid customerid, "+
	//			" t.commodityid commodityid,v.quantity quantity,t.holdqty holdqty "+
	//			" from t_h_customerholdsum t,T_ValidBill v where "+filter+
	//			" and t.customerid=v.customerid_s and t.commodityid=v.commodityid and t.bs_flag=2 and v.billtype=1 "+
	//			" and v.status=1 and t.cleardate=trunc(v.createtime) order by t.firmid,to_char(t.cleardate,'yyyy-MM-dd'),t.commodityid";
	String sql = "select t.firmid,to_char(t.cleardate,'yyyy-MM-dd') cleardate,t.customerid,t.commodityid,(t.HoldQty + t.GageQty) HoldQty,t.gageqty quantity "+
				"from t_h_customerholdsum t where t.BS_Flag=2 and t.GageQty > 0 and"+filter;
		//System.out.println(sql);
		DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	    List list=dao.queryBySQL(sql);    
    	%>    		   
	<br><center class="reportHead">�ֶ���¼��</center><br><br>
	<table align="center" width="600px" border="0">
	<tr>
		<td class="reportLeft" colspan="6"><c:if test="<%=startCommodityID !=null%>">��ʼ��Ʒ:&nbsp;<%=startCommodityID %></c:if>&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="<%=endCommodityID !=null%>">������Ʒ:&nbsp;<%=endCommodityID %></c:if></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px">
	<tr>
	<td class="td_reportMdHead">�����̴���</td>
	<td class="td_reportMdHead">�ֶ���������</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportMdHead">��Ʒ����</td>
	<td class="td_reportMdHead">��������</td>
	<td class="td_reportRdHead">�ֶ�����</td>
	</tr>	
	<%--
    	<%
	   	//BigDecimal sumQuantity = new BigDecimal(0);//С��
	   	//BigDecimal sumHoldqty = new BigDecimal(0);
	   	
	   	//BigDecimal countQuantity = new BigDecimal(0);//�ܼ�
	   	//BigDecimal countHoldqty = new BigDecimal(0);
		
		String mark = null;//���
		int num = 0;
		int size = list.size()-1; --%>
    <% for(int a = 0 ; a < list.size() ; a ++){
    	Map innerMap = (Map)list.get(a);
    	//num = a;
    	//if(!turnToStr(innerMap.get("firmid")).equals(mark)){
    		//if(mark != null){
    		%>
    		<%--
		    <tr>	
				<td class="td_reportMd" colspan="4"><b>С��</b></td>
				<td class="td_reportMd1"><b><%=sumQuantity %></b></td>
				<td class="td_reportRd1"><b>&nbsp;</b></td>
			</tr>  --%>
    		<%
    		  //countQuantity = countQuantity.add(sumQuantity);
   		    //countHoldqty = countHoldqty.add(sumHoldqty);
	    		//sumQuantity = new BigDecimal(0);
		   		//sumHoldqty = new BigDecimal(0);
    		//}
   			//mark = turnToStr(innerMap.get("firmid"));
    	//}%>
	<tr>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("firmid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("cleardate")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("HoldQty")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>
	</tr>
	<%-- 
	<%   		
   		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("HoldQty")));
   		sumHoldqty = sumHoldqty.add(turnToNum(innerMap.get("quantity")));
	}
	if(size == num){
	%>	
	<tr>	
		<td class="td_reportMd" colspan="4"><b>С��</b></td>
		<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
		<td class="td_reportRd1"><b>&nbsp;</b></td>
	</tr>
	<%
	        countQuantity = countQuantity.add(sumQuantity);
   		    countHoldqty = countHoldqty.add(sumHoldqty);
	}%>
	--%>
	<%}
	if(list.size()>0){
	%>	
	<%-- 
	<tr>
	<td class="td_reportMd" colspan="4"><b>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</b></td>
	<td class="td_reportMd1"><b>&nbsp;<%=countQuantity %></b></td>
	<td class="td_reportRd1"><b>&nbsp;</b></td>
	</tr> --%>
	<%
	}else{
	%>
	<tr>
		<td class="td_reportRd" colspan="6">
			�޷���������Ϣ��
		</td>
	</tr>
	<%
	}
	%>
</table>