<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=publicPath%>jstools/calendar.htc">
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>tradeInfoController.zcjs?funcflg=list" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>��ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">
							��������&nbsp;
					</td>
					<td align="left">
						<select id="businessdirection" name="_businessdirection[=]" class="normal" style="width: 80px">
						  <option value="" >ȫ��</option>
						  <option value="B" >��</option>
						  <option value="S" >��</option>
						 </select>
						 <script>
							 frm.businessdirection.value = "<c:out value='${oldParams["businessdirection[=]"]}'/>";
						 </script>
					</td>
					<td align="right">
							״̬&nbsp;
					</td>
					<td align="left">
							<select id="status" name="_status[=]" class="normal" style="width: 80px">
							  <option value="" >ȫ��</option>
							  <option value=1 >δ�ɽ�</option>
							  <option value=2 >�ѳɽ�</option>
							  <option value=3 >����</option>
							  <option value=4 >�ϳ�ԭ��</option>
							  <option value=5 >ϵͳ����</option>
							 </select>
							 <script>
								 frm.status.value = "<c:out value='${oldParams["status[=]"]}'/>";
							  </script>
					</td>
					<td align="right">
							�Ƿ����ֵ�&nbsp;
					</td>
					<td align="left">
						<select id="isregstock" name="_isregstock[=]" class="normal" style="width: 80px">
						  <option value="" >ȫ��</option>
						  <option value="Y" >��</option>
						  <option value="N" >��</option>
						 </select>
						  <script>
							 frm.isregstock.value = "<c:out value='${oldParams["isregstock[=]"]}'/>";
						  </script>
					</td>
					<td align="right">�ҵ�ʱ��&nbsp;</td>
					<td align="left"><MEBS:calendar eltID="zSDate" eltName="_goodsorderdate[>=][date]" eltValue="<c:out value='${oldParams["goodsorderdate[>=][date]"]}'/>" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/" />
						&nbsp;��&nbsp;
						<MEBS:calendar eltID="zEDate" eltName="_goodsorderdate[<=][date]" eltValue="<c:out value='${oldParams["goodsorderdate[<=][date]"]}'/>" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/" />
					</td>
					

				 <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">����</button>	
				</td>
				</tr>
			</table>
		</fieldset>
	  <%@ include file="goodsOrderTable.jsp"%>
	  <table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
			  <button class="lgrbtn" type="button" onclick="add();">���</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <button class="lgrbtn" type="button" onclick="del();">ɾ��</button>
			</div></td>
        </tr>
    </table>
	
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function initBody(returnValue){
		var msg1='${msg}';
	if(msg1!=""){
		alert(msg1);
		}
	
		changeOrder(sign);
		
	}
	function fmod(vid){
		frm.action = "<%=basePath%>/tradeInfoController.zcjs?funcflg=view&id="+vid;
		frm.submit();
	}
	function resetForm(){
		frm.zSDate.value = "";
		frm.zEDate.value = "";
		frm.businessdirection.value = "";
		frm.isregstock.value = "";
		
		frm.submit();
	}
	function add(){
		frm.action="<%=basePath%>/tradeInfoController.zcjs?funcflg=addForward";
		frm.submit();
	}
	 function del(){
		frm.action="<%=basePath%>/tradeInfoController.zcjs?funcflg=delForward";
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
		frm.submit();
		//return true;
		}
		else
		{
		return false;
		}
	}
	
	

</SCRIPT>