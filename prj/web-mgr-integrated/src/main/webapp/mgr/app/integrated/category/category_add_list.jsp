<%@ page contentType="text/html;charset=GBK"%>

	<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="product_table">
		<thead>
		<tr align="center">
			<td class="table_title"  width="25%" align="center">分类代码</td>
			<td class="table_title" width="25%" align="center">分类名称</td>
			<td class="table_title" width="25%" align="center">排序号</td>
			<td  class="table_title" width="25%" align="center">说明</td>
		</tr>
		</thead> 
		<tbody>
		<c:forEach items="${categoryList }" var="ca">
		<tr align="center">
			<td align="center">${ca.categoryId }</td>
			<td align="left">${ca.categoryName }</td>
			<td align="center">${ca.sortNo }</td>
			<td align="left" class="maxsize">&nbsp;${ca.note }</td>
		</tr>
		</c:forEach>
		</tbody>
		
	</table>
<script type="text/javascript">
$("td.maxsize").each(function(){
	var length=$(this).attr("lang");
	if(!length || !parseInt(length) || length<3){
		length=15;
	}
	if($(this).children("a").size() > 0||$(this).children("aa").size() > 0){
		var a = $(this).children("a").first();
		if($(this).children("a").size() == 0){
			a = $(this).children("aa").first();
		}
		if(a.text().length>(length)){
			$(this).attr("title",a.text());
			a.text(a.text().substring(0,length-2)+"...");
		}
	}else{
		if($(this).text().length>length){
			$(this).attr("title",$(this).text());
			$(this).text($(this).text().substring(0,length-2)+"...");
		}
	}
	
});
</script>