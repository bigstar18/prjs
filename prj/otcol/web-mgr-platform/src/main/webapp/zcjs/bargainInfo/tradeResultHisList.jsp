<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
  	<IMPORT namespace="MEBS" implementation="<%=publicPath%>jstools/calendar.htc">
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>bargainInfoController.zcjs?funcflg=getH_TradeResultList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>��ʷ�ɽ������ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">��ͬ�ţ�&nbsp;</td>
					<td align="left">
						<input id="tradeno" name="_tradeno[like]"
								value="<c:out value='${oldParams["tradeno[like]"]}'/>"
								type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>		
					<td align="right">���������̴���&nbsp;</td>
					<td align="left">
						<input id="FirmId_S" name="_FirmId_S[like]" value="<c:out value='${oldParams["FirmId_S[like]"]}'/>" type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>	
					<td align="right">�򷽽����̴���&nbsp;</td>
					<td align="left">
						<input id="FirmId_B" name="_FirmId_B[like]" value="<c:out value='${oldParams["FirmId_B[like]"]}'/>" type=text class="text" style="width: 100px"  onkeypress="notSpace()">
					</td>	
				 <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery1();">��ѯ</button>&nbsp;&nbsp;
						<button type=button class="smlbtn" onclick="reset1();">����</button>	
				</td>
				</tr>
			</table>
	</fieldset>		
		
	  <%@ include file="tradeResultHisTable.jsp"%>
	
	</form>
	
  </body xmlns:MEBS>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function doQuery1(){
		var tradeno=document.getElementById('tradeno').value;
		if(!checkNumber(tradeno)){
			document.getElementById('tradeno').value="";
			document.getElementById('tradeno').focus();
			alert('��ͬ�ű���Ϊ����');
			return false;
		}
		doQuery();
	}
	function reset1(){
		document.getElementById('tradeno').value="";
		document.getElementById('FirmId_S').value="";
		document.getElementById('FirmId_B').value="";
		document.getElementById('tradeno').focus();
		frm.submit();
	}
	//�ж�input�Ƿ�Ϊ����
function checkNumber(input){
		if(input==""){
			return true;
		}else{
			return checkRate(input);
		}
	}
function checkRate(input)    
	{    
  		var re = /^[0-9]{1,20}$/;   //�ж��ַ����Ƿ�Ϊ����
  		if (!re.test(input)){
  			return false;
  		}else{
  			return true;
  		}
  	}
  	function fmod(TradeNo){
  	 openDialog("<%=basePath%>/bargainInfoController.zcjs?funcflg=getHisCommodityParameter&TradeNo="+TradeNo,"_blank",470,450);
		
	}
	 function showContractHis(contractid)
	 {
		var returnValue = openDialog("<%=basePath%>bargainInfoController.zcjs?funcflg=getH_TradeResult&contractid="+contractid,"_blank",750,900);
		if(returnValue)
		frm.submit();
	}
</SCRIPT>