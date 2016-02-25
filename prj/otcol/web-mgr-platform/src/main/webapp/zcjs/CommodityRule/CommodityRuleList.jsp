<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm"  method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
			<fieldset width="95%">
				<legend>
					��Ʒ�����ѯ
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							��Ʒ�������&nbsp;
						</td>
						<td align="left">
							<input id="commodityRuleId" name="_commodityRuleId[like]"
								value="<c:out value='${oldParams["commodityRuleId[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="right">
							Ʒ�ֱ��&nbsp;
						</td>
						<td align="left">
							<input id="breedId" name="_breedId[like]"
								value="<c:out value='${oldParams["breedId[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="right">
							����������&nbsp;
						</td>
						<td align="left">
							<input id="commodityRuleFirmId" name="_commodityRuleFirmId[like]"
								value="<c:out value='${oldParams["commodityRuleFirmId[like]"]}'/>"
								type=text class="text" style="width: 100px " onkeypress="notSpace()">
						</td>
						<td align="left" colspan="2">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="doQueryNew();">
								��ѯ
							</button>
							&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="resetForm();">
								����
							</button>
						</td>
					</tr>
				</table>
			</fieldset>
	  <%@ include file="CommodityRuleTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td align="center"><div >
			  <button class="lgrbtn" type="button" onclick="add();">���</button>&nbsp;&nbsp;&nbsp;
			  <button class="lgrbtn" type="button" onclick="deleted();">ɾ��</button>
			</div></td>
        </tr>
    </table>
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function fmod(commodityRuleId){
		frm.action = "<%=basePath%>commodityRuleController.zcjs?funcflg=updateForward&commodityRuleId="+commodityRuleId;
		frm.submit();
	}
	function doQueryNew(){
		var commodityRuleId=document.getElementById('commodityRuleId').value;
		var breedId=document.getElementById('breedId').value;
		if(!checkNumber(commodityRuleId)){
			document.getElementById('commodityRuleId').value="";
			document.getElementById('commodityRuleId').focus();
			alert('��Ʒ������ű���Ϊ����');
		}else{
			if(!checkNumber(breedId)){
				document.getElementById('breedId').value="";
				document.getElementById('breedId').focus();
				alert('Ʒ��Id����Ϊ����');
			}else{
				doQuery();
			}
		}
	}
	function resetForm(){
		frm.commodityRuleId.value="";
		frm.breedId.value="";
		frm.commodityRuleFirmId.value="";
		frm.submit();
	}
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
	function add(){
		frm.action="<%=basePath%>commodityRuleController.zcjs?funcflg=addForward";
		frm.submit();
	}
	 function deleted(){
		
		deleteRec(frm,tableList,'delCheck');
	}
	
	function deleteRec(frm_delete,tableList,checkName)
	{
		if(isSelNothing(tableList,checkName) == -1)
		{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
		}
		if(isSelNothing(tableList,checkName))
		{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
		}
		if(confirm("��ȷʵҪ����ѡ��������"))
		{
		frm.action="<%=basePath%>commodityRuleController.zcjs?funcflg=delete";
		frm.submit();
		}
		else
		{
		return false;
		}
	}
	
	

</SCRIPT>