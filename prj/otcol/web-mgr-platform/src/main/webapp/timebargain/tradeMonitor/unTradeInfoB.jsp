<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>default</title>
	<link href="<c:url value="/timebargain/styles/maintest.css"/>" rel="stylesheet" type="text/css">
	<script language="javascript" src="<c:url value='/timebargain/scripts/monitorAjax.js'/>"></script>
    <script type="text/javascript">
	<!--
	   //��������Դ��ַ
		function winload() 
		{
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			htFilter.Clear();
			htFilter.Add("orderby",orderby);
			setRequestUrl("<c:url value='/timebargain/tradeMonitor/tradeMonitor.do?funcflg=listUnTradeInfoB'/>");
		}
		
		function query(parameter)
		{
			clearTimeout(timer);//��ֹͣ��ǰ������
						
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
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHeadB>
	    <tr>
	    	<td class="hq_bt" align="center"><div align="center">��</div></td>
	    </tr>
	  </table>
     <table width="100%"  height="5%" align="center" cellpadding="0" cellspacing="0"   style="border-collapse:collapse;" id=tbHead onclick=dtquery()>
	   <tr>
	    <td width="3%" class="hq_bt" align="center" ><div align="center"><span id=netStatus></span></div></td>
	    <td width="16%" class="hq_bt" align="center" abbr="CustomerID" style="cursor:hand">��������</td>
	    <td width="16%" class="hq_bt" align="center" abbr="OrderType" style="cursor:hand">����/ת��</td>
	    <td width="16%" class="hq_bt" align="center" abbr="Price" style="cursor:hand">ί�мۡ�</td>
	    <td width="16%" class="hq_bt" align="center" abbr="SurplusQuantity" style="cursor:hand">ʣ����</td>
	    <td width="16%" class="hq_bt" align="center" abbr="A_OrderNo" style="cursor:hand">ί�к�</td>
	    <td width="16%" class="hq_bt" align="center" abbr="Ordertime" style="cursor:hand">�뵥ʱ��</td>
	  </tr>
	  </table>
	 
	  <table width="100%"  height="84%" cellpadding="0" cellspacing="0"  bgcolor="#000000"  id=tbFrame>
	  		<tr valign="top">
	  			<td>
	  				<table width="100%"  valign="top" cellpadding="0" cellspacing="0" id=tb>  		
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
	<label id="orderType" style="display:none">DESC,Ordertime ASC</label>
	<label id="orderName" style="display:none">Price</label>
	<!-- ���� �����������Ƶ�ǰѡ���� ����ʹ������ű�ʶ����������������ʹ��";"�ָ�-->
	<label id="PrimaryKey" style="display:none">5;</label>
   	</form>
	</body>
</html>
