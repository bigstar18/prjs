<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.List" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManage" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<base target="_self">
<html>
<head>
<%	//�״η�����ʾ��Ʒ����
		List cmdList=SettleManage.getCommoditysNew();
    pageContext.setAttribute("cmdList", cmdList);
    //¼��
    String opt = request.getParameter("opt");
    if( opt!= null && "create".equals(opt))
    {
		//��������
		String CommodityID = request.getParameter("CommodityID");
		String FirmID_B = request.getParameter("FirmID_B");
		String FirmID_S = request.getParameter("FirmID_S");
		int result = Integer.parseInt(request.getParameter("handleResult"));
		long Quantity = Long.parseLong(request.getParameter("Quantity"));
		int returnResult = SettleManage.createSettle(CommodityID,FirmID_B,FirmID_S,Quantity,result);
		if(returnResult==1){
		%>
		<script type="text/javascript">
			alert("���������Ϣ¼��ɹ���");
			window.returnValue="-1";
			window.close();
		</script>
		<%
		}else if(returnResult==-1) {
		  long checkResult=SettleManage.checkInsertSettle(CommodityID,FirmID_B,FirmID_S,Quantity);
		%>
		<script type="text/javascript">
			alert("¼�����ݲ����Ͻ���������\nʵ�ʿɽ��յ�����Ϊ��" + <%=checkResult%> );
		</script>
		<%
		}else if(returnResult==-2){
		%>
		<script type="text/javascript">
			alert("���������쳣��");
		</script>
		<%
		}
    }
%>
</head>
<body>
	<form name="frm" method="post" action="">
	<br>
	<fieldset width="100%">
		<legend class="common"><b>¼��</b></legend>
		<BR>
		<span>
			<table class="common" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
				<!-- commodity -->
				<tr class="common">
					<td align="right" width="50%" height="30">��Ʒ���룺<input type="text" name="CommodityID" readonly="readonly">&nbsp;&nbsp;</td>
					<td align="left" width="50%">
						<select name="cmdid" onchange="selectObj('cmdid')">
						<option>��ѡ��</option>
						<c:forEach items="${cmdList}" var="result">
							 <option value="${result.commodityid}">${result.commodityid}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<!-- buyer -->
				<tr>
					<td class="common" align="right" width="50%" height="30">�򷽴��룺<input type="text" name="FirmID_B" readonly="readonly">&nbsp;&nbsp;</td>
					<td align="left" width="50%">
						<!-- <select name="buyerid" onblur="selectObj('buyerid')"> -->
						<div id="div1">
							<select name="buyerid">
								<option>��ѡ��</option>
							</select>
						</div>						
					</td>
				</tr>
				<!-- seller -->
				<tr>
					<td class="common" align="right" width="50%" height="30">�������룺<input type="text" name="FirmID_S" readonly="readonly">&nbsp;&nbsp;</td>
					<td align="left" width="50%">						
						<!-- <select name="sellerid" onblur="selectObj('sellerid')"> -->
						<div id="div2">
							<select name="sellerid">
								<option>��ѡ��</option>
							</select>
						</div>						
					</td>
				</tr>
				<!-- ִ�н�� -->
				<tr class="common">
					<td align="right" width="50%" height="30">���ս����<input type="text" name="result" readonly="readonly">&nbsp;&nbsp;</td>
					<td align="left" width="50%">
						<select name="handleResult" id="handleResult" onchange="select(3);">
						<option value="0">��ѡ��</option>
						<option value="1">��Լ</option>
						<option value="2">��ΥԼ</option>
						<option value="3">����ΥԼ</option>
						<option value="4">˫��ΥԼ</option>
						</select>
					</td>
				</tr>
				<!-- amount -->
				<tr class="common">
					<td align="right" width="50%" height="30">����������<input type="text" name="Quantity" onkeyup="this.value=this.value.replace(/\D/g,'')">&nbsp;&nbsp;
					</td>
					<td align="left" width="50%"><div id="div3">&nbsp;<font color="red">*</font></div></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<!-- submit -->
				<tr class="common">
					<td colspan="2" align="center" height="30">
						<input type="button" name="subbut" class="button" onclick="submitMSG();" value="�ύ" class="mdlbtn">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" onclick="window.close();" value="����" class="mdlbtn">
						<input type="hidden" name="opt">
				</tr>
			</table><br>
		</span>
		</fieldset>
	</form>
</body>
</html>
<script type="text/javascript">

	function submitMSG()
	{
		var CommodityID = frm.CommodityID.value;
		var FirmID_B = frm.FirmID_B.value;
		var FirmID_S = frm.FirmID_S.value;
		var Quantity = frm.Quantity.value;
		var result = frm.handleResult.value;
		var resultMSG = "";
		if( result == 1 )
		{
			resultMSG = "��Լ";	
		}
		else if( result == 2 )
		{
			resultMSG = "��ΥԼ";	
		}
		else if( result == 3 )
		{
			resultMSG = "����ΥԼ";	
		}
		else if( result == 4 )
		{
			resultMSG = "˫��ΥԼ";	
		}
		if(CommodityID!=""&&FirmID_B!=""&&FirmID_S!=""&&Quantity!=""&&result!=""){
		
		     if(Quantity.search("^[1-9]*[1-9][0-9]*$")!=0 || Quantity  <= 0)
             {
                  alert("��ȷ�Ͻ�������Ϊ��������");
                  return false;
             }else{
		    
			   //alert("��ʼ¼��******************");
			   var dismark = false;
			   if(confirm("Ҫ�ύ������Ϊ��\n��Ʒ��"+CommodityID+
								"\n�򷽣�"+FirmID_B+"��������"+FirmID_S+
								"\n���ս����"+resultMSG+"������������"+Quantity+
								"\nȷ��Ҫ�ύô��"))
			   {
				  frm.subbut.disabled=true;
				  dismark = true;
			   }
			   if(dismark){
				  frm.opt.value="create";
				  frm.submit();				
			   }
			}
		}else{
			alert("����������ݲ�����,������¼�룡");
			return false;
		}		
	}
	
	var request = new ActiveXObject("Microsoft.XMLHTTP");
	var buyList;// = new Array(); 
	var sellerList;// = new Array();
	var responseResult;
	function selectObj(v)
	{
		var filter ;
		//ѡ������Ʒ���븳ֵ
		var objValue1 = frm.cmdid.value;
		frm.CommodityID.value = objValue1;
		filter = "CommodityID="+objValue1;				
			
		if(v == "cmdid")
		{//(��һ��ʹ��AJAX)
			//alert("��ʼ����*************firstRequest");
			frm.FirmID_B.value="";
			frm.FirmID_S.value="";
			frm.Quantity.value="";
			frm.result.value="";
			document.getElementById("div3").innerHTML="&nbsp;<font color='red'>*</font>";
			firstRequest(filter);
		}
		else if(v == "Quantity")
		{
			//alert("��ʼ����*************secondRequest");
			//��д��������֤(�ڶ���ʹ��AJAX)
				var objValue2 = frm.FirmID_B.value;
				var objValue3 = frm.FirmID_S.value;
				var objValue4 = trim(frm.Quantity.value);
				var objValue5	= frm.result.value;
				var resultStutas  = true;
				if(objValue5 == ""){
					resultStutas  = false;
					alert("��ѡ���յ�ִ�н����");
				}
				//objValue4.search("^[0-9]*[1-9][0-9]*$")!=0
				if(objValue4.search("^[1-9]*[1-9][0-9]*$")!=0 || objValue4 <= 0){
					resultStutas  = false;
					alert("��ȷ�Ͻ�������Ϊ��������");
				}
				if(resultStutas){
					filter = filter+"&FirmID_B="+objValue2+"&FirmID_S="+objValue3+"&Quantity="+objValue4;
					secondRequest(filter);								
				}
			}
	}
	
	
	function firstRequest(v)
	{
		request.onreadystatechange = fisrtResponse;
		request.open("post","getData.jsp?"+v,false);
		request.send();
		request.abort();
		firstCopyValues(buyList,sellerList);
	}
	function secondRequest(v)
	{
		request.onreadystatechange = secondResponse;
		request.open("post","checkData.jsp?"+v,false);
		request.send();
		request.abort();
		
		//secondCopyValues(responseResult);
	}
	function fisrtResponse()
	{
		// only if req shows "loaded"
		if (request.readyState == 4) {
		   // only if "OK"
		   //if (request.status == 200) {
			var result = request.responseText;
			var bs = result.split("[]");
			buyList = bs[0];			
			sellerList = bs[1];
			//}	
		}
	}
	function secondResponse()
	{
		// only if req shows "loaded"
		if (request.readyState == 4) {
		   // only if "OK"
		   //if (request.status == 200) {
			var responseResult = request.responseText;
			var sign = trim(responseResult);
				//-1���ͨ�� ���Խ����ύ¼�룬���ڵ���0��ͨ��������ֵ��ʵ�ʿɽ��յ�����
				if(sign == -1){
					//alert("������Գɹ�,����¼�룡");
				}else if(sign >= 0){
					alert("���ύ�������������������["+sign+"],������¼�����ݣ�");
					document.getElementById("div3").innerHTML="<font color='red'>���������["+sign+"]</font>";
					frm.Quantity.value="";
					frm.Quantity.focus();
				}
			//}	
		}
	}
	function firstCopyValues(bl,sl)
	{
		var blist = bl.split(";");
		var slist = sl.split(";");
		var buymsg="<select name='buyerid' id='buyerid' onChange='select(1);'><option>��ѡ��</option>";
		var sellmsg="<select name='sellerid' id='sellerid' onChange='select(2);'><option>��ѡ��</option>";
		for(var b = 0 ; b < blist.length ; b ++)
		{
			if(blist[b]!=""){
				buymsg = buymsg+"<option value='"+blist[b]+"'>"+blist[b]+"</option>";
			}
		}
		for(var s = 0 ; s < slist.length ; s ++)
		{
			if(slist[s]!=""){
				sellmsg = sellmsg+"<option value='"+slist[s]+"'>"+slist[s]+"</option>";
			}
		}
		buymsg = buymsg+"</select>";
		sellmsg = sellmsg+"</select>";
		document.getElementById("div1").innerHTML=buymsg;
		document.getElementById("div2").innerHTML=sellmsg;
	}
	
	function select(v)
	{
		var selectValue ;
			switch (v)
			{
				case 1:
					selectValue = document.getElementById("buyerid").value;
					frm.FirmID_B.value = selectValue;
					frm.FirmID_B.readOnly = true;
					break;
				case 2:
					selectValue = document.getElementById("sellerid").value;
					frm.FirmID_S.value = selectValue;
					frm.FirmID_S.readOnly = true;
					break;
				case 3:
					selectValue = document.getElementById("handleResult").value;
					var strValue = "";
					if(selectValue == 1){
						strValue="��Լ";
					}else if(selectValue == 2){
						strValue="��ΥԼ";
					}else if(selectValue == 3){
						strValue="����ΥԼ";
					}else if(selectValue == 4){
						strValue="˫��ΥԼ";
					}
					frm.result.value = strValue;
					frm.result.readOnly = true;
					break;	
			}
	}
	//���ǰ��ո�
	function trim ( str ) {
		regExp = /\S/
		
		if ( regExp.test(str) == false )
			return "";
		
		regExp = /(^\s*)(.*\S)(\s*$)/
		regExp.exec(str);
		
		return RegExp.$2;
	}
</script>
