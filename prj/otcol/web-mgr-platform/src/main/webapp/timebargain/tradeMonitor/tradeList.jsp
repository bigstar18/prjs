<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*" %>
<%
	
		LookupManager lookupMgr = (LookupManager)SysData.getBean("lookupManager");
		
		request.setAttribute("commoditySelect", lookupMgr
				.getSelectLabelValueByTableDistinctCommodityID("t_orders", "commodityID",
						"commodityID"," order by commodityID "));
	String firmID = request.getParameter("firmID");
	String commodityID2 = request.getParameter("commodityID2");
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
	<script language="javascript" src="<c:url value='/timebargain/scripts/monitorAjax.js'/>"></script>
    <script type="text/javascript">
	<!--
	   //��������Դ��ַ
	   var commodityID2 = '<%=commodityID2%>';
		function winload() 
		{
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();
			htFilter.Add("orderby",orderby);
			setRequestUrl("<c:url value='/timebargain/tradeMonitor/tradeMonitor.do?funcflg=listTradeList'/>");
			var firmID = '<%=firmID%>';
			
			if (firmID != "null" && firmID != "") {
				sel('0');
				frm.parameter.value = firmID;
				query();
			}
			
			if (commodityID2 != "null" && commodityID2 != "") {
				sel('1');
				document.getElementsByName("queryType")[1].checked = true;
				frm.parameter2.value = commodityID2;
			}
		}	
		
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
			//������ֵ �����������̻�����Ʒ
			var parameter = "";
			if (queryType == "0") {
				parameter=frm.parameter.value;
			}else if (queryType == "1") {
				parameter=frm.parameter2.value;
			}
			
			clearTimeout(timer);//��ֹͣ��ǰ������
					
			if(htFilter.ContainsKey("queryType"))//�������queryType��ѯ�������������÷������
				htFilter.SetValue("queryType",queryType);
			else 
				htFilter.Add("queryType",queryType);
			
			if(parameter!="")
			{
				if(htFilter.ContainsKey("parameter"))//�������parameter��ѯ�������������÷������
					htFilter.SetValue("parameter",parameter);
				else 
					htFilter.Add("parameter",parameter);
			}
			else
			{
				htFilter.Remove("parameter");
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
      	<td width="54%">
      		<div align="right">
      			<span class="pagetype"><INPUT TYPE="radio" name="queryType"  value="0" onclick="sel('0')" checked="checked"/>�������̲�ѯ 
				<INPUT TYPE="radio" name="queryType"  value="1" onclick="sel('1')"/>����Ʒ��ѯ </span>
			</div>
      	</td>
        <td width="20%" class="xian" id="firmID">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText">�����̴���</label>��</span>
        		<input type="text" id="parameter"  maxlength="16" style="width:111" onkeypress="onlyNumberAndCharInput()"/>
        	</div>
		</td>
		<td width="20%" class="yin" id="commodityID">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText2">��Ʒ����</label>��</span>
        		<select id="parameter2" style="width:111">
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
		</td>
		<td width="6%"><div align="RIGHT"><input type="button" value="��ѯ" onclick="query()"></div></td>
  </tr>
</table>
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHead onclick=dtquery()>
	   <tr>
	    <td width="3%" class="hq_bt" align="center" ><div align="center"><span id=netStatus></span></div></td>
	    <td width="8%" class="hq_bt" align="center" abbr="TradeTime" style="cursor:hand">�ɽ�ʱ��</td>
	    <td width="12%" class="hq_bt" align="center" abbr="a_tradeno" style="cursor:hand">�ɽ��š�</td>
	    <td width="8%" class="hq_bt" align="center" abbr="CustomerID" style="cursor:hand">��������</td>
	    <td width="8%" class="hq_bt" align="center" abbr="CommodityID" style="cursor:hand">������Ʒ</td>
	    <td width="8%" class="hq_bt" align="center" abbr="BS_Flag" style="cursor:hand">��/��</td>
	    <td width="8%" class="hq_bt" align="center" abbr="OrderType" style="cursor:hand">����/ת��</td>
	    <td width="8%" class="hq_bt" align="center" abbr="Quantity" style="cursor:hand">�ɽ���</td>
	    <td width="8%" class="hq_bt" align="center" abbr="Price" style="cursor:hand">�ɽ���</td>     
	    <td width="8%" class="hq_bt" align="center" abbr="oppfirmId" style="cursor:hand">�ɽ��Է�</td>
	    <td width="8%" class="hq_bt" align="center" abbr="ordertime" style="cursor:hand">ί��ʱ��</td>
	    <td width="8%" class="hq_bt" align="center" abbr="A_OrderNo" style="cursor:hand">ί�к�</td>
	    <td width="8%" class="hq_bt" align="center" abbr="orderPrice" style="cursor:hand">ί�м�</td>
	  </tr>
	  </table>
	 
	  <table width="100%"  height="80%" cellpadding="0" cellspacing="0"  bgcolor="#000000"  id=tbFrame>
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
	<label id="orderName" style="display:none">a_tradeno</label>
	<!-- ���� �����������Ƶ�ǰѡ���� ����ʹ������ű�ʶ����������������ʹ��";"�ָ�-->
	<label id="PrimaryKey" style="display:none">2;</label>
   	</form>
	</body>
</html>
