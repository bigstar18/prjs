<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=publicPath%>jstools/calendar.htc">
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>commodityParameterController.zcjs?funcflg=list" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
				<legend>
					��Ʒ������ѯ
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							��Ʒ��������&nbsp;
						</td>
						<td align="left">
							<input id="parameterName" name="_parameterName[like]"
								value="<c:out value='${oldParams["parameterName[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="right">
							Ʒ������&nbsp;
						</td>
						<td align="left">
							<input id="breedName" name="_breedName[like]"
								value="<c:out value='${oldParams["breedName[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
						</td>
						<td align="left" colspan="2">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="smlbtn" onclick="doQuery();">
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
	  <%@ include file="commodityParameterTable.jsp"%>
	<table border="0" cellspacing="0" cellpadding="0" width="80%">
		<tr height="35">
			<td>
				<div align="center">
					<button class="lgrbtn" type="button" onclick="dealForbid();">
						״̬��ֹ
					</button>
					&nbsp;&nbsp;&nbsp;
					<button class="lgrbtn" type="button" onclick="dealNormal();">
						״̬����
					</button>
				</div>
			</td>
		</tr>
	</table> 
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function resetForm(){
		frm.parameterName.value = "";
		frm.breedName.value="";
		frm.submit();
	}
//���ͣ�type��1 ������2��ֹ��3 ɾ�� 
	function dealForbid(){
       frm.action="<%=basePath%>commodityParameterController.zcjs?funcflg=mod&type="+2;
		modRec(frm,tableList,'delCheck');
	}	
	function dealNormal(){
       frm.action="<%=basePath%>commodityParameterController.zcjs?funcflg=mod&type="+1;
		modRec(frm,tableList,'delCheck');
	}	
	function modRec(frm,tableList,checkName)
	{
		if(isSelNothing(tableList,checkName) == -1)
		{
			alert ( "û�п��Բ��������ݣ�" );
			frm.action="<%=basePath%>commodityParameterController.zcjs?funcflg=list";
			return false;
		}
		if(isSelNothing(tableList,checkName))
		{
			alert ( "��ѡ����Ҫ���������ݣ�" );
			frm.action="<%=basePath%>commodityParameterController.zcjs?funcflg=list";
			return false;
		}
		if(confirm("��ȷʵҪ����ѡ��������"))
		{
			frm.submit();
		//return true;
		}
		else
		{
		return false;
		}
	}
	
	

</SCRIPT>