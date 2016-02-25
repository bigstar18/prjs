<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>

<html>
	<head>
		<title></title>
	</head>

	<body>
		<form id="formNew" name="frm" method="POST"
			action="<%=basePath%>breedController.entity?funcflg=breedAdd">
			<fieldset width="100%">
				<legend>
					品种信息
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr height="30">
						<td align="right" width="40%">
							品种编号：
						</td>
						<td align="left" width="60%">
							<input class="text" name="breedId" value="" style="width: 150px;"
								reqfv="required;品种代码" onkeypress="onlyNumberAndCharInput()" maxlength="8">
							&nbsp;
							<font color="red">*</font>&nbsp;（品种编号一经录入不能修改）
						</td>
					</tr>
					<tr height="30">
						<td align="right" width="40%">
							品种名称：
						</td>
						<td align="left" width="60%">
							<input class="text" name="breedName" value=""
								style="width: 150px;" reqfv="required;品种名称" onkeypress="onlyNumberAndCharInput()" maxlength="16">
							&nbsp;
							<font color="red">*</font>
						</td>
					</tr>
				</table>
				<c:forEach items="${propertyList}" var="result">
					<table border="0" cellspacing="0" cellpadding="0" width="100%"
						id="${result.propertyKey}tb">
						<tr height="30">
							<td align="center" colspan="2">
								${result.propertyName }设置
							</td>
						</tr>
						<tr>
							<td align="right" width="40%">
								名&nbsp;&nbsp;&nbsp;&nbsp;称：
							</td>
							<td align="left" width="60%">
								<input name="${result.propertyKey }" type="text" value="" class="text"
									style="width: 150px;" reqfv="required;名称" onkeypress="onlyNumberAndCharInput()" maxlength="32"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr height="30">
							<td colspan="2">
								<div align="center">
									<button class="smlbtn" type="button" onclick="add('${result.propertyKey }');">
										添加项
									</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="smlbtn" type="button" onclick="del('${result.propertyKey }');">
										删除项
									</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						</tr>
					</table>
				</c:forEach>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" id="qualitytb">
			  <tr height="30">
				<td align="center"colspan="2">品种质量指标设置</td>
			  </tr>
			  <tr>
			  	<td align="right" width="40%">名&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
                <td align="left" width="60%">
                	<input name="quality" type="text" value="" class="text" style="width: 150px;" reqfv="required;名称" onkeyup="value=value.replace(/^[0-9].*$/,'')"  onkeypress="onlyNumberAndCharInput()" maxlength="32">&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			  <tr height="30">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onclick="addQuality();">添加项</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <button class="smlbtn" type="button" onclick="delQuality();"> 删除项</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </div></td>
              </tr>
			</table>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr height="30">
						<td colspan="2">
							<div align="center">
								<table border=0>
									<tr>
										<td>
											附注：1：质检的时候将会用到此质量指标设置项，作为需要质检的项目。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>

									</tr>
									<tr>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2：质量指标一经保存将不可删除，只能修改质量指标的名称和增加新的质量指标。
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr height="30">
						<td colspan="2">
							<div align="center">
								<button class="smlbtn" type="button" onclick="doSubmit();">
									确定
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="smlbtn" type="button" onclick="freturn()">
									返回
								</button>
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
<script>
	function add(propertyKey){
		var table=document.getElementById(propertyKey+'tb');
		var newRow=table.insertRow(table.rows.length-1);
		for(var i=0;i<2;i++){
			var newCell=newRow.insertCell(i);	
			switch(i){
		
				case 0:newCell.innerHTML="名&nbsp;&nbsp;&nbsp;&nbsp;称:";newCell.align="right";break;
				case 1:newCell.innerHTML="<input name=\""+propertyKey+"\" type=\"text\" value=\"\" class=\"text\" style=\"width: 150px;\" reqfv=\"required;名称\" >&nbsp;<font color=\"red\">*</font>";break;
				
			}
		}
	}
	function del(propertyKey){
		var table=document.getElementById(propertyKey+"tb");
		if(table.rows.length-3>0){
			table.deleteRow(table.rows.length-2);
		}else{
			alert("必须填写一个！");
			return false;
		}
	}

	//add & del brand
	function addQuality(){
		var table=document.getElementById("qualitytb");
		var newRow=table.insertRow(table.rows.length-1);
		for(var i=0;i<2;i++){
			var newCell=newRow.insertCell(i);	
			switch(i){
				case 0:newCell.innerHTML="名&nbsp;&nbsp;&nbsp;&nbsp;称:";newCell.align="right"; break;
				case 1:newCell.innerHTML="<input name=\"quality\" type=\"text\" value=\"\" class=\"text\" style=\"width: 150px;\" reqfv=\"required;名称\" >&nbsp;<font color=\"red\">*</font>";break;
			}
		}
	}
	function delQuality(){
		var table=document.getElementById("qualitytb");
		if(table.rows.length-3>0){
			table.deleteRow(table.rows.length-2);
		}else{
			alert("必须填写一个！");
			return false;
		}
	}

	function doSubmit()
	{
		var str = /^[1-9]*[1-9][0-9]*$/;
		var id = frm.breedId.value;
		if(!isNaN(id) && str.test(id))
		{ 
			
				if(!checkValue("formNew"))
					return;
				if(confirm("确定执行此操作？")){
					frm.submit();
				}
			
		} 
		else
		{
			alert("品种编号必须是数字,并且不能以0开头!");
			frm.breedId.value="";
			frm.breedId.focus();
		}
	}
	function freturn(){
		frm.action = "<%=basePath%>breedController.entity?funcflg=getBreedList";
		frm.submit();
	}
	//仅输入数字和字母
function onlyNumberAndCharInput()
{
  if ((event.keyCode>=48 && event.keyCode<=57) || 

(event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && 

event.keyCode<=122))
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}
</script>
