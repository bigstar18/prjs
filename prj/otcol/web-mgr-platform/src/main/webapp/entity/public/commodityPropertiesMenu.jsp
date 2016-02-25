<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<!--用户身份判断-->

<%
try
{
	
	int idx = toInt(request.getParameter("idx"));	
	%>

<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="4">
        <tr>
          <td height="18" width="10%"><div align="center"></div></td>
		  <td width="10%" class="<%=idx==1?"fdh_td1":"fdh_td"%>"><div align="center"><a href="marketConfig.jsp" class="dh">市场配置</a></div></td>          
			<!-- <td width="15%" class="<%=idx==3?"fdh_td1":"fdh_td"%>"><div align="center"><a href="storageList.jsp" class="dh">席位库存管理</a></div></td> -->
          <!-- <td width="10%" class="<%=idx==3?"fdh_td1":"fdh_td"%>"><div align="center"><a href="commodityParameterList.jsp" class="dh">商品参数</a></div></td> -->
		  <td width="10%" class="<%=idx==4?"fdh_td1":"fdh_td"%>"><div align="center"><a href="TradeTimeList.jsp" class="dh">交易节管理</a></div></td>
		  <!-- <td width="15%" class="<%=idx==5?"fdh_td1":"fdh_td"%>"><div align="center"><a href="capitalList.jsp" class="dh">席位在途资金管理</a></div></td> -->
          <td width="10%" class="<%=idx==6?"fdh_td1":"fdh_td"%>"><div align="center"><a href="marketManage.jsp" class="dh">交易管理</a></div></td>
          <td width="10%" class="<%=idx==8?"fdh_td1":"fdh_td"%>"><div align="center"><a href="broadcastList.jsp" class="dh">广播消息管理</a></div></td>
          <td width="10%"><div align="center"></div></td>
          <td width="10%"><div align="center"></div></td>
        </tr>
      </table>

	<%
}
catch(Exception e)
{
	System.out.println(e.toString());
	errOpt();
}
%>