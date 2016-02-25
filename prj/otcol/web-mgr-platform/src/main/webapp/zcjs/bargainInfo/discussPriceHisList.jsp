<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>  	
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>bargainInfoController.zcjs?funcflg=getH_DisList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>��ʷ��������ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">��۱��&nbsp;</td>
					<td align="left">
						<input id="DiscussPriceId" name="_DiscussPriceId[like]"
								value="<c:out value='${oldParams["DiscussPriceId[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
					</td>
					<td align="right">���������̴���&nbsp;</td>
					<td align="left">
						<input id="FollowFirmId" name="_FollowFirmId[like]" value="<c:out value='${oldParams["FollowFirmId[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="notSpace()">
					</td>
					<td align="right">����&nbsp;</td>
					<td align="left">
						<input id="quantity" name="_quantity[like]"
								value="<c:out value='${oldParams["quantity[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
					</td>
					<td align="right">��������&nbsp;</td>
					<td align="left">
						<c:set value='${oldParams["businessDirection[like]"]}' var="businessDirection"></c:set>
						<select name="_businessDirection[like]" id="businessDirection">
									<option value="">
										ȫ��
									</option>
									<option value="��"
									<c:if test="${businessDirection=='��' }">selected</c:if>>
										��
									</option>
									<option value="��"
									<c:if test="${businessDirection=='��' }">selected</c:if>>
										��
									</option>
						</select>					
				 <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery1();">��ѯ</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="reset1()">����</button>	
				</td>
				</tr>
			</table>
	</fieldset>		
		
	  <%@ include file="discussPriceHisTable.jsp"%>
	
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function doQuery1(){
		var DiscussPriceId=document.getElementById('DiscussPriceId').value;
		var quantity=document.getElementById('quantity').value;
		if(!checkNumber(DiscussPriceId)){
			document.getElementById('DiscussPriceId').value="";
			document.getElementById('DiscussPriceId').focus();
			alert('���ID����Ϊ����');
		}else{
			if(!checkNumber(quantity)){
				document.getElementById('quantity').value="";
				document.getElementById('quantity').focus();
				alert('��������Ϊ����');
			}else{
				doQuery();
			}
		}
	}
	function reset1(){
		document.getElementById('DiscussPriceId').value="";
		document.getElementById('quantity').value="";
		document.getElementById('businessDirection').value="";
		document.getElementById('FollowFirmId').value="";
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
  	function fmod(DiscussPriceId){
  	 openDialog("<%=basePath%>/bargainInfoController.zcjs?funcflg=getH_DisParameter&DiscussPriceId="+DiscussPriceId,"_blank",470,450);
		
	}
</SCRIPT>