<%@ page contentType="text/html;charset=GBK" %>
<div>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" id="PageCtrl_Table" style="font-size:9pt;">
	<tr>
		<td id="PageCtrl_pageInfo" align="left" valign="bottom">&nbsp;�� <c:out value="${pageInfo.pageNo}"/>/<c:out value="${pageInfo.totalPages}"/> ҳ</td>
		<td id="PageCtrl_recordInfo" align="center">&nbsp;�� <c:out value="${pageInfo.totalRecords}"/> ��&nbsp;
		ÿҳ<input type="text" style="text-align:center; border-left-style:none; border-right-style:none; border-top-style:none; border-bottom: solid 1px #333333; font-family:Verdana; font-size: 9pt; background-color:transparent;" size="3" id="PageCtrl_pageSize" value="<c:out value="${pageInfo.pageSize}"/>">��</td>
		<td id="PageCtrl_pageJump" align="center">ǰ����<input type="text" style="text-align:center; border-left-style:none; border-right-style:none; border-top-style:none; border-bottom: solid 1px #333333; font-family:Verdana; font-size: 9pt; background-color:transparent;" size="2" id="PageCtrl_pageGoto">ҳ</td>
		<td id="PageCtrl_pageChange"align="right" style="color:black; font-family:Webdings; font-size-12px;">
			<span title="��ҳ" id="PageCtrl_pageFirst" style="cursor:hand" onclick="gotoPage(1)">9</span>
			<span title="ǰһҳ" id="PageCtrl_pagePrev" style="cursor:hand" onclick="gotoPrevPage()">7</span>
			<span title="��һҳ" id="PageCtrl_pageNext" style="cursor:hand" onclick="gotoNextPage()">8</span>
			<span title="ĩҳ" id="PageCtrl_pageLast" style="cursor:hand" onclick="gotoPage(<c:out value="${pageInfo.totalPages}"/>)">:</span>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</div>
<script>
	var vPageNo = <c:out value="${pageInfo.pageNo}" default="0"/>;
	var vPageSize = <c:out value="${pageInfo.pageSize}" default="0"/>;
	var vTotalPages = <c:out value="${pageInfo.totalPages}" default="0"/>;
	var vTotalRecords = <c:out value="${pageInfo.totalRecords}" default="0"/>;
	function gotoPrevPage(){
		if(vPageNo>1)
			return gotoPage(vPageNo - 1);
		else 
			alert("�ѵ���һҳ��")
	}
	
	function gotoNextPage(){
		if(vPageNo < vTotalPages)    <!--С����ҳ��-->
			return gotoPage(vPageNo + 1);
		else 
			alert("�ѵ����һҳ��")
	}
	
	function gotoPage(toPage){
		if(toPage == vPageNo){
			alert("��ҳ�ɷ���");
			return;
		}
		if(frm_query){
			if(frm_query.pageNo){
				frm_query.pageNo.value = toPage;
				frm_query.submit();
			} else
				alert("û�ҵ�pageNo�ı�������ϵ������Ա��");
		}	else
			alert("û�ҵ�frm_query������ϵ������Ա��");
	}
	
	function changePageSize(pageSize){
		if(frm_query){
			if(frm_query.pageSize){
				frm_query.pageSize.value = pageSize;
				frm_query.pageNo.value = 1;
				frm_query.submit();
			} else
				alert("û�ҵ�pageSize�ı�������ϵ������Ա��");
		}	else
			alert("û�ҵ�frm_query������ϵ������Ա��");
	}
	PageCtrl_Table.tBodies[0].onkeydown = pageCtrlKeyDown;
	
	function pageCtrlKeyDown ()
	{
		if ( event.keyCode == 13 )		// �س���
		{
			var tagEvent = event.srcElement;
			
			if(tagEvent.id == "PageCtrl_pageGoto"){
				var pn = tagEvent.value;
				if(isNaN(pn) || pn < 1 || pn > vTotalPages)
					alert("������ 1 -- " + vTotalPages + " ֮������֣�");
				else
					gotoPage(pn);
			} else if(tagEvent.id == "PageCtrl_pageSize") {
				var ps = tagEvent.value;
				if(isNaN(ps) || ps < 1 || ps > vTotalRecords)
					alert("������ 1 -- " + vTotalRecords + " ֮������֣�");
				else 
					changePageSize(ps);
			}
		}
	}
</script>