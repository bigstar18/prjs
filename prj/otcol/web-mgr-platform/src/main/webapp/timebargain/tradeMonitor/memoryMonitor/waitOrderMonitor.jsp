<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.webapp.action.AgencyRMIBean"%>
<%@ page import="gnnt.MEBS.timebargain.server.model.SystemStatus"%>
<%
	
		LookupManager lookupMgr = (LookupManager)SysData.getBean("lookupManager");
		
		request.setAttribute("commoditySelect", lookupMgr
				.getSelectLabelValueByTableDistinctCommodityID("t_orders", "commodityID",
						"commodityID"," order by commodityID "));
	String firmID = request.getParameter("firmID");
	String commodityID2 = request.getParameter("commodityID2");
	AgencyRMIBean agencyRMIBean =new AgencyRMIBean(request); 
	SystemStatus systemStatus = agencyRMIBean.getSystemStatus();
	int sysStatus = systemStatus.getStatus();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
-->
</style>
	<title>default</title>
	<link href="<c:url value="/timebargain/styles/maintest.css"/>" rel="stylesheet" type="text/css">
	<script language="javascript" src="<c:url value='/timebargain/scripts/monitorAjax3.js'/>"></script>
    <script type="text/javascript">
	<!--
	   //��������Դ��ַ
	   var commodityID2 = '<%=commodityID2%>';
		function winload() 
		{
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();
			htFilter.Add("orderby",orderby);
			setRequestUrl("<c:url value='/timebargain/tradeMonitor/tradeMonitor.do?funcflg=memoryMonitor&queryOrderType=waitOrders'/>");
			var firmID = '<%=firmID%>';
			if (firmID != "null" && firmID != "") {
				sel('0');
				frm.firmId.value = firmID;
				query();
			}
			if (commodityID2 != "null" && commodityID2 != "") {
				sel('1');
				document.getElementsByName("queryType")[1].checked = true;
				frm.comId.value = commodityID2;
			}
			
		}	
		//��ȡϵͳ״̬
		
		
		
		function sel(value)
		{
			if(value==0)
			{
				//document.getElementById("queryTypeText").innerHTML="�����̴���";
				document.getElementById("firmID").className = 'xian';
				document.getElementById("commodityID").className = 'yin';
			}
			else
			{
				//document.getElementById("queryTypeText").innerHTML="��Ʒ����";
				document.getElementById("firmID").className = 'yin';
				document.getElementById("commodityID").className = 'xian';
			}
		}
		
	/*	function query()
		{
			var firmId="";
			if (commodityID2 != "null" && commodityID2 != "") { 
				frm.firmId.value = commodityID2;
			} 
			firmId = frm.firmId.value;
			clearTimeout(timer);//��ֹͣ��ǰ������
						
			if(firmId!="")
			{
				if(htFilter.ContainsKey("firmId"))//�������firmId��ѯ�������������÷�������
					htFilter.SetValue("firmId",firmId);
				else 
					htFilter.Add("firmId",firmId);
			}
			else
			{
				htFilter.Remove("firmId");
			}	
			//��������
			sendRequest();	
		}*/
		
		function query()
		{
			//��ѯ����0���������̲�ѯ 1������Ʒ��ѯ
			var queryType="0";	
			var aa = document.getElementsByName("queryType");
    		for (var i=0; i<aa.length; i++)
    		{
     			if(aa[i].checked)
    			   queryType=aa[i].value;
			}
			//����queryType
			if (commodityID2 != "null" && commodityID2 != "") {
				
				queryType="1";
			}
			
			//������ֵ �������������̻�����Ʒ
			var firmId = "";
			var commodityId="";
			if (queryType == "0") {
				firmId=frm.firmId.value;
			}else if (queryType == "1") {
				commodityId=frm.comId.value;
			}
			
			//״̬
			var status=frm.status.value;
			
			clearTimeout(timer);//��ֹͣ��ǰ������
					
			if(htFilter.ContainsKey("queryType"))//�������queryType��ѯ�������������÷�������
				htFilter.SetValue("queryType",queryType);
			else 
				htFilter.Add("queryType",queryType);
			
			if(firmId!="")
			{
				if(htFilter.ContainsKey("pageIndex"))//
					htFilter.SetValue("pageIndex",1);
				else 
					htFilter.Add("pageIndex",1);
				if(htFilter.ContainsKey("firmId"))//�������firmId��ѯ�������������÷�������
					htFilter.SetValue("firmId",firmId);
				else 
					htFilter.Add("firmId",firmId);
			}
			else
			{
				htFilter.Remove("firmId");
			}
			
			if(commodityId!="")
			{
				if(htFilter.ContainsKey("commodityId"))//�������status��ѯ�������������÷�������
					htFilter.SetValue("commodityId",commodityId);
				else 
					htFilter.Add("commodityId",commodityId);
			}
			else
			{
				htFilter.Remove("commodityId");
			}	
			
			if(status!="")
			{
				if(htFilter.ContainsKey("status"))//�������status��ѯ�������������÷�������
					htFilter.SetValue("status",status);
				else 
					htFilter.Add("status",status);
			}
			else
			{
				htFilter.Remove("status");
			}	
			//��������
			sendRequest();	
		}
		
	// -->
	</script>
  </head>
  <body bgcolor="#000000" onLoad="winload()">
  <form name="frm" METHOD=POST ACTION="">
  <table width="100%" height="5%"  border="0" cellpadding="0" cellspacing="0">
       <tr> 
       <td class="xian" width="20%">
        	<div align="left">
        		<span class="pagetype">��ǰ����ϵͳ״̬��<label id="sysStatus">
        		<%if (sysStatus==0) {%>��ʼ�����<%} %>
        		<%if (sysStatus==1) {%>����״̬<%} %>
        		<%if (sysStatus==2) {%>������<%} %>
        		<%if (sysStatus==3) {%>����������<%} %>
        		<%if (sysStatus==4) {%>��ͣ����<%} %>
        		<%if (sysStatus==5) {%>������<%} %>
        		<%if (sysStatus==6) {%>�ڼ���Ϣ<%} %>
        		<%if (sysStatus==7) {%>���׽���<%} %>
        		<%if (sysStatus==8) {%>���Ͼ��۽�����<%} %>
        		<%if (sysStatus==9) {%>���Ͼ��۽��׽���<%} %>
        		<%if (sysStatus==10) {%>�������<%} %>
        		</label>
        		</span>
        	</div>
		</td>
      	<td width="34%" >
      		<div align="right">
      			<span class="pagetype"><INPUT TYPE="radio" name="queryType"  value="0" onclick="sel('0')" checked="checked"/>�������̲�ѯ 
				<INPUT TYPE="radio" name="queryType"  value="1" onclick="sel('1')"/>����Ʒ��ѯ </span>
			</div>
      	</td>
        <td width="20%" class="xian" id="firmID">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText">�����̴���</label>��</span>
        		<input type="text" id="firmId" name="firmId"  maxlength="16" style="width:111"/>
        	</div>
		</td>
		<td width="20%" class="yin" id="commodityID">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText2">��Ʒ����</label>��</span>
        		<select id="comId" style="width:111">
        			<%
        				String commodityID = "";
						List list = (List)request.getAttribute("commoditySelect");
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								LabelValue lv = (LabelValue)list.get(i);
								if (lv != null) {
									commodityID = lv.getValue();
					%>
					<option value="<%=commodityID%>"><%=commodityID%></option>
					<%
								}
							}
							
						}
        			%>
        		</select>
        	</div>
		</td><!-- 
		<td width="20%"><div align="RIGHT"><span class="pagetype">ί�е�״̬��</span>
			<select id="status" name="_a.Status[=]" style="width:111">
				                            <option value=""></option>
					                        <option value="1">��ί��</option>
					                        <option value="2">���ֳɽ�</option>
					                        <option value="3">ȫ���ɽ�</option>
					                        <option value="5">ȫ������</option>
					                        <option value="6">���ֳɽ��󳷵�</option>
			    </select>	
			</div>
		</td> -->
		<td width="6%"><div align="RIGHT"><input type="button" value="��ѯ" onclick="query()"></div></td>
  </tr>
</table>
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHead onclick=dtquery()>
	   <tr>
	    <td width="3%" class="hq_bt" align="center" ><div align="center"><span id=netStatus></span></div></td>
	    <td width="11%" class="hq_bt" align="center" abbr="CommodityID" style="cursor:hand">��Ʒ����</td>
	    <td width="10%" class="hq_bt" align="center" abbr="FirmID" style="cursor:hand">�����̴���</td>
	    <td width="9%" class="hq_bt" align="center" abbr="BS_Flag" style="cursor:hand">��/��</td>
	    <td width="10%" class="hq_bt" align="center" abbr="OrderType" style="cursor:hand">����/ת��</td>
	    <td width="10%" class="hq_bt" align="center" abbr="Price" style="cursor:hand">ί�м�</td>
	    <td width="9%" class="hq_bt" align="center" abbr="Quantity" style="cursor:hand">ί����</td>
	    <td width="8%" class="hq_bt" align="center" abbr="A_OrderNo" style="cursor:hand">ί�кš�</td>
	    <td width="11%" class="hq_bt" align="center" abbr="OrderTime" style="cursor:hand">�뵥ʱ��</td>     
	    <td width="8%" class="hq_bt" align="center" abbr="Status" style="cursor:hand">ί�е�״̬</td>
	  </tr>
	  </table>
	 
	  <table width="100%"  height="81%" cellpadding="0" cellspacing="0"  bgcolor="#000000"  id=tbFrame>
	  		<tr valign="top">
	  			<td>
	  				<table width="100%"  valign="top" cellpadding="0" cellspacing="0"  id=tb>  		
	  				</table>
	  			</td>
	  		</tr>
	  </table>
	  <table width="100%" height="3%" border="0" cellspacing="0" cellpadding="4" class="pagetype">
		  <tr>
		    <td align="right">
		         ��ǰ:<label id="pageIndex"></label>/<label id="totalPage"></label>ҳ&nbsp;��<label id="totalCount"></label>��
				<label id="firstPage">��һҳ</label>
				<label id="prePage">��һҳ</label>
				<label id="nextPage">��һҳ</label>
				<label id="lastPage">��ĩҳ</label>
				</td>
		  </tr>
	 </table>
	<label id="orderType" style="display:none">DESC</label>
	<label id="orderName" style="display:none">A_OrderNo</label>
	<!-- ���� �����������Ƶ�ǰѡ���� ����ʹ������ű�ʶ����������������ʹ��";"�ָ�-->
	<label id="PrimaryKey" style="display:none">7;</label>
   	</form>
	</body>
</html>