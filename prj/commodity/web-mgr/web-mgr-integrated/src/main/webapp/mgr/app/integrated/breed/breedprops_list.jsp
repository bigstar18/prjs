<%@ page contentType="text/html;charset=GBK"%>
<c:if test="${fn:length(propsMap)!=0 }">
<div class="div_gn" align="right">
	<rightButton:rightButton name="Ìí¼ÓÊôÐÔ" onclick="addBreedProps();" className="anniu_btn" action="/category/breed/addForwardProps.action?breedId=${breedId }" id="add"></rightButton:rightButton>
	&nbsp;&nbsp;
	<rightButton:rightButton name="É¾³ýÊôÐÔ" onclick="deleteBreedProps();" className="anniu_btn" action="/category/breed/deleteProps.action" id="delete"></rightButton:rightButton>
</div>
</c:if>
<div class="div_table_gn" style="width: 97%;"><br/>
	<c:forEach items="${propsMap }" var="map">
		<div class="div_cxtj">
			<div class="div_cxtjL"></div>
			<div class="div_cxtjC">
				${map.key.name}
			</div>
			<div class="div_cxtjR"></div>
		</div>
		<div style="clear: both;"></div>
		<div class="div_tj">
			<table border="0" cellspacing="0" cellpadding="10" width="100%" height="100px" align="center" class="table2_style">
				<c:forEach items="${map.value}" var="mapv">
				<tr>
					<td align="center" class="table_title" height="28px" width="20%">${mapv.key }</td>
					<td width="*">
					<ul style="width: 100%;">
					<c:forEach items="${mapv.value }" var="list">
						<li style="width: 25%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText));">
							<input type="checkbox" id="ids1" name="ids1" value="${list.breed.breedId };${list.categoryProperty.propertyId };${list.propertyValue };${list.sortNo }"/>
							<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/category/breed/updateForwardProps.action" id="detail" text="${list.propertyValue }" onclick="update('${list.categoryProperty.propertyId}','${list.breed.breedId }','${list.propertyValue }','${list.sortNo }');"/>&nbsp;&nbsp;
						</li>
					</c:forEach>
					</ul>
					&nbsp;
					</td>
				</tr>
				</c:forEach>
			</table>
		</div><br/>
	</c:forEach>
</div></form>
<br/>
<script type="text/javascript">
	function addBreedProps(){
	var a=document.getElementById('add').action;
		var url = "${basePath}"+a;
		if(showDialog(url, "", 600, 300)){
			frm.action="${basePath}/category/breed/updateForwardBreed.action";
			frm.submit();
		};
	}
	function update(id1,id2,id3,id4){
		var a=document.getElementById("detail").action;
		var url = "${basePath}"+a+"?entity.categoryProperty.propertyId="+id1+"&entity.breed.breedId="+id2+"&entity.propertyValue="+encodeURI(encodeURI(id3))+"&entity.sortNo="+id4;
		if(showDialog(url, "", 600, 300)){
			frm.action="${basePath}/category/breed/updateForwardBreed.action";
			frm.submit();
		};
	}
	function deleteBreedProps(){
	var a=document.getElementById('delete').action;
	var ids=document.getElementsByName("ids1");
	var idsString="";
	for(var i=0;i<ids.length;i++)
	{
	if(ids[i].checked==true){
		idsString+=ids[i].value+",";
		}
	}
		idsString=idsString.substring(0,idsString.length-1);
		document.getElementById("ids").value=idsString;
		var url = "${basePath}"+a;
		if(checkDelete(ids, url))
		{
			document.getElementById('frm').action=url;
			document.getElementById('frm').submit();
		};
	}

</script>
