<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/headInc.jsp"%>
<html>
	<head>
		<title></title>
    <script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	</head>

	<body onload="init();">
		<form name="frm_query" method="post"
			action="<%=errorLoginLogControllerPath%>getErrorLoginLogList">
			<input type="hidden" name="orderField" value="${orderFiled}">
			<input type="hidden" name="orderDesc" value="${orderType}">
			<input type="hidden" name="pageSize"
				value="<c:out value="${pageInfo.pageSize}"/>">
			<input type="hidden" id="pageNo" name="pageNo">
			<fieldset width="95%">
				<legend>
					�쳣��¼��ѯ
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							�����̴��룺&nbsp;
						</td>
						<td align="left">
							<input id="traderId" name="_traderId[like]"
								value='${oldParams["traderId[like]"]}' type=text class="text"
								style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
						</td>
						<td align="left">
							<button type="button" class="smlbtn" onclick="doQuery();">
								��ѯ
							</button>
							&nbsp;
							<button type="button" onclick="resetForm();" class="smlbtn">
								����
							</button>
							&nbsp;
						</td>
					</tr>
				</table>
			</fieldset>
			<%@ include file="errorLoginLogTable.jsp"%>
			<table id="tablebutton" border="0" cellspacing="0" cellpadding="0"
				width="100%">
				<tr>
					<td align="right">
						<input class="mdlbtn" type="button" value="�ָ���¼" onclick="backLogin()">&nbsp;
						<input class="mdlbtn" type="button" value="ȫ���ָ���¼" onclick="backLoginAll()">&nbsp;
					</td>
					<td>&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</table>

		</form>
	</body>
</html>

<script type="text/javascript">
	function backLogin(){
		
		match(frm_query,tableList,'delCheck');
	}
	function backLoginAll(){
		if(isSelNothing(tableList,'delCheck') == -1)
		{
			alert ( "û�п��Բ��������ݣ�" );
			return;
		}
		if(confirm("��ȷʵҪ����ѡ��������"))
		{
		  frm_query.action="<%=errorLoginLogControllerPath%>delete&manyOrAll=all";
		  frm_query.submit();
		}
	}
	
	function fmod(vid){
	    var actionPath='<%=errorLoginLogControllerPath%>getErrorLoginLogMod&tradeId='+vid;
		var returnValue =openDialog(actionPath,"_blank",500,350);
		if(returnValue){
		frm_query.submit();
		}
	}
	
	  function init(){
	    var msg1='${msg}';
	if(msg1!=""){
		alert(msg1);
	}
			  changeOrder(sign);  
		}
	
	function doQuery(){
	frm_query.submit();
	}
	function resetForm(){
		frm_query.traderId.value = "";
		frm_query.submit();
	}
	
	function match(frm_query,tableList,checkName)
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
		// ����Ҫ�ָ���ʱ��Ÿı�·��  add by yangpei 2011-10-18
		frm_query.action="<%=errorLoginLogControllerPath%>delete&manyOrAll=many";
		frm_query.submit();
		}
		else
		{
		return false;
		}
	}
//ȷ��һ������е��б�ѡ�еļ�¼
//tblObj:������
//childName:checkbox��ID
function isSelNothing( tblObj , chkId )
{
	var collCheck = tblObj.children[1].all.namedItem(chkId);
    if(!collCheck)
        return -1;
    if(collCheck.checked)
	{
		if(collCheck.checked == true)
		 return false
		else
		  return true
	}
	if( collCheck.length < 1 )
	{
		return -1;
	}
	var noSelect = true;
	if(collCheck.checked == true)
	{
			noSelect = false;
	}
	else
	{
		for(var i=0;i < collCheck.length;i++ )
		{
			if( collCheck[i].checked == true )
			{
				noSelect = false;			
			}
		
		}
	}
	return noSelect;	
}

function alertMsg(){
	var msg1='${msg}';
	if(msg1!=""){
		alert(msg1);
	}
	
}
</script>