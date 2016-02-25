<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<table width="98%" height="20"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
          <td height="20" align=right class="td_list2">
		  当前:<%=pageIndex%>/<%=pageCnt%>页&nbsp;<%=PAGESIZE%>条/页&nbsp;共<%=totalCnt%>条
				<%
			        if(pageIndex == 1){
			    %>
					   上页
				<%
		            }else if(pageIndex > 1){
				%>
					   <span style="cursor:hand;text-decoration:underline" onClick="javascript:pgTurn(<%=pageIndex-1%>)">上页</span>
				<%
					}

				    if(pageIndex < pageCnt){
			    %>
					   <span style="cursor:hand;text-decoration:underline" onClick="javascript:pgTurn(<%=pageIndex+1%>)">下页</span>
				<%
		            }else if(pageIndex == pageCnt || pageCnt == 0){
				%>
					   下页
				<%
					}
				    
					if(pageIndex>1){
				%>
						<span style="cursor:hand;text-decoration:underline" onclick="javascript:pgTurn(1)">首页</span>
				<%
					}else{
				%>
						首页
				<%
					}
				  if(pageIndex<pageCnt){
				%>
						 <span style="cursor:hand;text-decoration:underline" onclick="javascript:pgTurn(<%=pageCnt%>)">尾页</span>
				<%
					}else{
				%>
						尾页
				<%
					}
		        %>
				  到<input name="jpIdx" type="text" size=2 >页&nbsp;<input name="go" type="submit" class="k"  value="GO" onClick="return gotoPage('<%=pageCnt%>')">
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
		alert("跳转页数格式错误");
		frm.go.focus();
		return false;
	}
	if(parseInt(page)>parseInt(allpage))
	{
		alert("跳转页不能大于总页数");
		frm.go.focus();
		return false;
	}
	if(parseInt(page)<=0)
	{
		alert("跳转页必需大于零");
		frm.go.focus();
		return false;
	}
	frm.pageIndex.value = page;
	return true;
}

//-->
</SCRIPT>