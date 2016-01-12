<%@ page pageEncoding="GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
<style type="text/css">

.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}

</style>
	<title>default</title>
	<link href="${mgrPath}/skinstyle/default/css/app/monitorSet.css" rel="stylesheet" type="text/css">
	<script src="${mgrPath}/app/timebargain/tradeMonitor/js/monitorAjax3.js" type="text/javascript"></script>
    <script type="text/javascript">
	//<!--
	   //��������Դ��ַ
	   var commodityID2 = "";
		function winload() 
		{
			commodityID2=document.getElementById("commodityID2").innerHTML;
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();
			htFilter.Add("orderby",orderby);
			setRequestUrl("${basePath}/timebargain/tradeMonitor/monitorWaitOrders.action?queryOrderType=waitOrders");
			var firmID = document.getElementById("firmID2").innerHTML;
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
				if(htFilter.ContainsKey("firmId"))//�������firmId��ѯ�������������÷������
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
			
			//������ֵ �����������̻�����Ʒ
			var firmId = "";
			var commodityId="";
			if (queryType == "0") {
				firmId=frm.firmId.value;
			}else if (queryType == "1") {
				commodityId=frm.comId.value;
			}
			
			//״̬
			//var status=frm.status.value;
			
			clearTimeout(timer);//��ֹͣ��ǰ������
					
			if(htFilter.ContainsKey("queryType"))//�������queryType��ѯ�������������÷������
				htFilter.SetValue("queryType",queryType);
			else 
				htFilter.Add("queryType",queryType);
			
			if(firmId!="")
			{
				if(htFilter.ContainsKey("pageIndex"))//
					htFilter.SetValue("pageIndex",1);
				else 
					htFilter.Add("pageIndex",1);
				if(htFilter.ContainsKey("firmId"))//�������firmId��ѯ�������������÷������
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
				if(htFilter.ContainsKey("commodityId"))//�������status��ѯ�������������÷������
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
				if(htFilter.ContainsKey("status"))//�������status��ѯ�������������÷������
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
        		<c:choose>
        			<c:when test="${sysStatus==0}">��ʼ�����</c:when>
        			<c:when test="${sysStatus==1}">����״̬</c:when>
        			<c:when test="${sysStatus==2}">������</c:when>
        			<c:when test="${sysStatus==3}">����������</c:when>
        			<c:when test="${sysStatus==4}">��ͣ����</c:when>
        			<c:when test="${sysStatus==5}">������</c:when>
        			<c:when test="${sysStatus==6}">�ڼ���Ϣ</c:when>
        			<c:when test="${sysStatus==7}">���׽���</c:when>
        			<c:when test="${sysStatus==8}">���Ͼ��۽�����</c:when>
        			<c:when test="${sysStatus==9}">���Ͼ��۽��׽���</c:when>
        			<c:when test="${sysStatus==10}">�������</c:when>
        		</c:choose>
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
        		<input type="text" id="firmId" name="firmId"  maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" style="width:111"/>
        	</div>
		</td>
		<td width="20%" class="yin" id="commodityID">
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText2">��Ʒ����</label>��</span>
        		<select id="comId" style="width:111">
        			<option value="">��ѡ��</option>
        			<c:forEach items="${commoditySelect}" var="result">
						<option value="${result.COMMODITYID}">${result.COMMODITYID}</option>
					</c:forEach>
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
	<label id="firmID2" style="display:none">${firmID }</label>
	<label id="commodityID2" style="display:none">${commodityID2 }</label>
   	</form>
	</body>
</html>
