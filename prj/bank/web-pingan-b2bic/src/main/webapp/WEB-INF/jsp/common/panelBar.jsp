<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="panelBar">
	<div class="pages">
		<span>每页</span>
		<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
			<c:forEach begin="10" end="50" step="10" varStatus="s">
				<option value="${s.index}" ${page.perPageRecords eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
			</c:forEach>
		</select>
		<span style="margin-left: 8px;">总共: ${page.allRecords}</span>
	</div>
	
	<div class="pagination" targetType="navTab" totalCount="${page.allRecords}" numPerPage="${page.perPageRecords}" pageNumShown="${page.visiblePage}" currentPage="${page.curPage}"></div>
</div>