<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<!--�û�����ж�-->

<%
try
{
	
	int idx = toInt(request.getParameter("idx"));	
	%>

<table width="100%" height="28"  border="0" cellpadding="0" cellspacing="4">
        <tr>
          <td height="18" width="10%"><div align="center"></div></td>
		  <td width="10%" class="<%=idx==1?"fdh_td1":"fdh_td"%>"><div align="center"><a href="marketConfig.jsp" class="dh">�г�����</a></div></td>          
			<!-- <td width="15%" class="<%=idx==3?"fdh_td1":"fdh_td"%>"><div align="center"><a href="storageList.jsp" class="dh">ϯλ������</a></div></td> -->
          <!-- <td width="10%" class="<%=idx==3?"fdh_td1":"fdh_td"%>"><div align="center"><a href="commodityParameterList.jsp" class="dh">��Ʒ����</a></div></td> -->
		  <td width="10%" class="<%=idx==4?"fdh_td1":"fdh_td"%>"><div align="center"><a href="TradeTimeList.jsp" class="dh">���׽ڹ���</a></div></td>
		  <!-- <td width="15%" class="<%=idx==5?"fdh_td1":"fdh_td"%>"><div align="center"><a href="capitalList.jsp" class="dh">ϯλ��;�ʽ����</a></div></td> -->
          <td width="10%" class="<%=idx==6?"fdh_td1":"fdh_td"%>"><div align="center"><a href="marketManage.jsp" class="dh">���׹���</a></div></td>
          <td width="10%" class="<%=idx==8?"fdh_td1":"fdh_td"%>"><div align="center"><a href="broadcastList.jsp" class="dh">�㲥��Ϣ����</a></div></td>
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