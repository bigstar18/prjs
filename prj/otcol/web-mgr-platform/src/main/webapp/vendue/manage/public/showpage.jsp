<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<table width="98%" height="20"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
          <td height="20" align=right class="td_list2">
		  ��ǰ:<%=pageIndex%>/<%=pageCnt%>ҳ&nbsp;<%=PAGESIZE%>��/ҳ&nbsp;��<%=totalCnt%>��
				<%
			        if(pageIndex == 1){
			    %>
					   ��ҳ
				<%
		            }else if(pageIndex > 1){
				%>
					   <span style="cursor:hand;text-decoration:underline" onClick="javascript:pgTurn(<%=pageIndex-1%>)">��ҳ</span>
				<%
					}

				    if(pageIndex < pageCnt){
			    %>
					   <span style="cursor:hand;text-decoration:underline" onClick="javascript:pgTurn(<%=pageIndex+1%>)">��ҳ</span>
				<%
		            }else if(pageIndex == pageCnt || pageCnt == 0){
				%>
					   ��ҳ
				<%
					}
				    
					if(pageIndex>1){
				%>
						<span style="cursor:hand;text-decoration:underline" onclick="javascript:pgTurn(1)">��ҳ</span>
				<%
					}else{
				%>
						��ҳ
				<%
					}
				  if(pageIndex<pageCnt){
				%>
						 <span style="cursor:hand;text-decoration:underline" onclick="javascript:pgTurn(<%=pageCnt%>)">βҳ</span>
				<%
					}else{
				%>
						βҳ
				<%
					}
		        %>
				  ��<input name="jpIdx" type="text" size=2 >ҳ&nbsp;<input name="go" type="submit" class="k"  value="GO" onClick="return gotoPage('<%=pageCnt%>')">
		  </td>
          </tr>
		  <input type=hidden name=pageIndex value="<%=pageIndex%>">
</table>
<SCRIPT LANGUAGE="JavaScript">
<!--

function pgTurn(v){
	frm.pageIndex.value = v;
	frm.submit();
	
}
function gotoPage(allpage)
{
	page = frm.jpIdx.value;
	if(isNaN(page))
	{
		alert("��תҳ����ʽ����");
		frm.go.focus();
		return false;
	}
	if(parseInt(page)>parseInt(allpage))
	{
		alert("��תҳ���ܴ�����ҳ��");
		frm.go.focus();
		return false;
	}
	if(parseInt(page)<=0)
	{
		alert("��תҳ���������");
		frm.go.focus();
		return false;
	}
	frm.pageIndex.value = page;
	return true;
}

//-->
</SCRIPT>