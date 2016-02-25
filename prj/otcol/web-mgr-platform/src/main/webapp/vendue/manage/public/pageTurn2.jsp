<%@ page contentType="text/html;charset=GBK" %>
<SCRIPT LANGUAGE="JavaScript">
<!--
function pgTurn(val)
{
	switch(val)
	{
		case 1:
			frm.pageIndex.value = 1;
		    frm.submit();
		    break;
		case 2:
			frm.pageIndex.value -= 1;
		    frm.submit();
		    break;
		case 3:
			frm.pageIndex.value= parseInt(frm.pageIndex.value) + 1;
		    frm.submit();
		    break;
		case 4:
			frm.pageIndex.value = "<c:out value='${pageCnt}'/>";
		    frm.submit();
		    break;
	}
}

function keyChk()
{
	//if(event.keyCode == 13)
	//{
		var idx = frm.pageJumpIdx.value;
		if(!isNaN(idx) && idx <= <c:out value="${pageCnt}"/> && idx >= 1)
		{			
			frm.pageIndex.value = idx;
			//return true;
			frm.submit();
		}
		else
		{
			alert("请输入1-"+"<c:out value='${pageCnt}'/>"+"之间的整数！");
			return false;
		}
	//}
}

function keyChkSetPageSize()
{
	if(event.keyCode == 13)
	{
		var idx = frm.pageSize.value;
		if(!isNaN(idx) && idx>0)
		{
			frm.pageIndex.value = 1;			
			frm.pageSize.value = idx;
			frm.submit();
			//return true;
		}
		else
		{
			alert("请输入大于0的数!");
			return false;
		}
	}
}
//-->
</SCRIPT>