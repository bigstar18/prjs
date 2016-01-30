<%@ page contentType="text/html;charset=GBK"%>
<div class="div_gn" align="right">
	<rightButton:rightButton name="添加属性" onclick="addProperty();" className="anniu_btn" action="/category/commodity/addForwardProperty.action?categoryId=${categoryId }" id="add"></rightButton:rightButton>
	&nbsp;&nbsp;
	<rightButton:rightButton name="删除属性" onclick="deleteProperty();" className="anniu_btn" action="/category/commodity/deleteProperty.action" id="delete"></rightButton:rightButton>
</div>
<div>
<form action="" method="post" id="frm1" targetType="hidden">
<input type="hidden" name="categoryId" value="${categoryId }">
	<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="product_table">
		<thead>
		<tr align="center">
			<td class="table_title"  width="2%" align="center">&nbsp;</td>
			<td class="table_title"  width="19%" align="center">属性名称</td>
			<td class="table_title" width="10%" align="center">值字典</td>
			<td class="table_title" width="13%" align="center">匹配仓单</td>
			<td class="table_title" width="10%" align="center">检索</td>
			<td  class="table_title" width="10%" align="center">排序号</td>
			<td  class="table_title" width="10%" align="center">必填项</td>
			<td  class="table_title" width="13%" align="center">输入值类型</td>
			<td  class="table_title" width="13%" align="center">属性类型</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${propSet }" var="property">
		<tr align="center">
			<td><input type="checkbox" id="ids" name="ids" value="${property.propertyId }"/></td>
			<td align="left">
				<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/category/commodity/updateForwardProperty.action" id="detail" text="${property.propertyName }" onclick="update('${property.propertyId}');"/>
			</td>
			<td align="center">
				<c:if test="${property.hasValueDict=='Y'}">
					是
				</c:if>
				<c:if test="${property.hasValueDict=='N'}">
					否
				</c:if>
			</td>
			<td align="center">
				<c:if test="${property.stockCheck=='Y'}">
					检查
				</c:if>
				<c:if test="${property.stockCheck=='N'}">
					不检查
				</c:if>
				<c:if test="${property.stockCheck=='M'}">
					范围检查
				</c:if>&nbsp;
			</td>
			<td align="center">
				<c:if test="${property.searchable=='Y'}">
					是
				</c:if>
				<c:if test="${property.searchable=='N'}">
					否
				</c:if>
			</td>
			<td align="center">${property.sortNo }</td>
			<td align="center">
				<c:if test="${property.isNecessary=='Y'}">
					是
				</c:if>
				<c:if test="${property.isNecessary=='N'}">
					否
				</c:if>
			</td>
			<td align="center">
				${categoryPropTypeMap[property.fieldType] }
			</td>
			<td align="center">
				${empty property.propertyType.name ? '其它属性' : property.propertyType.name}
			</td>
		</tr>
		</c:forEach>
		</tbody>
		
	</table></form>
</div>
<br/>
<script type="text/javascript">
	function addProperty(){
	var a=document.getElementById('add').action;
		var url = "${basePath}"+a;
		if(showDialog(url, "", 580, 400)){
			frm1.action="${basePath}/category/commodity/updateForwardCategory.action?entity.categoryId=${categoryId}";
			frm1.submit();
		};
	}
	function update(id){
		var a=document.getElementById("detail").action;
		var url = "${basePath}"+a+"?entity.propertyId="+id;
		if(showDialog(url, "", 580, 400)){
			frm1.action="${basePath}/category/commodity/updateForwardCategory.action?entity.categoryId=${categoryId}";
			frm1.submit();
		};
	}
	function deleteProperty(){
	var a=document.getElementById('delete').action;
	var ids=document.getElementsByName("ids");
	var idsString="";
	for(var i=0;i<ids.length;i++)
	{
	if(ids[i].checked==true){
		idsString+=ids[i].value+",";
		}
	}
		idsString=idsString.substring(0,idsString.length-1);
		var url = "${basePath}"+a+"?ids="+idsString;
		if(checkDelete(ids, url))
		{
			frm1.action=url;
			frm1.submit();
		};
	}

</script>
