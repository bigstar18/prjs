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
					Ʒ����Ϣ
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr height="30">
						<td align="right" width="40%">
							Ʒ�ֱ�ţ�
						</td>
						<td align="left" width="60%">
							<input class="text" name="breedId" value="" style="width: 150px;"
								reqfv="required;Ʒ�ִ���" onkeypress="onlyNumberAndCharInput()" maxlength="8">
							&nbsp;
							<font color="red">*</font>&nbsp;��Ʒ�ֱ��һ��¼�벻���޸ģ�
						</td>
					</tr>
					<tr height="30">
						<td align="right" width="40%">
							Ʒ�����ƣ�
						</td>
						<td align="left" width="60%">
							<input class="text" name="breedName" value=""
								style="width: 150px;" reqfv="required;Ʒ������" onkeypress="onlyNumberAndCharInput()" maxlength="16">
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
								${result.propertyName }����
							</td>
						</tr>
						<tr>
							<td align="right" width="40%">
								��&nbsp;&nbsp;&nbsp;&nbsp;�ƣ�
							</td>
							<td align="left" width="60%">
								<input name="${result.propertyKey }" type="text" value="" class="text"
									style="width: 150px;" reqfv="required;����" onkeypress="onlyNumberAndCharInput()" maxlength="32"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr height="30">
							<td colspan="2">
								<div align="center">
									<button class="smlbtn" type="button" onclick="add('${result.propertyKey }');">
										�����
									</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="smlbtn" type="button" onclick="del('${result.propertyKey }');">
										ɾ����
									</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						</tr>
					</table>
				</c:forEach>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" id="qualitytb">
			  <tr height="30">
				<td align="center"colspan="2">Ʒ������ָ������</td>
			  </tr>
			  <tr>
			  	<td align="right" width="40%">��&nbsp;&nbsp;&nbsp;&nbsp;�ƣ�</td>
                <td align="left" width="60%">
                	<input name="quality" type="text" value="" class="text" style="width: 150px;" reqfv="required;����" onkeyup="value=value.replace(/^[0-9].*$/,'')"  onkeypress="onlyNumberAndCharInput()" maxlength="32">&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			  <tr height="30">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onclick="addQuality();">�����</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <button class="smlbtn" type="button" onclick="delQuality();"> ɾ����</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
											��ע��1���ʼ��ʱ�򽫻��õ�������ָ���������Ϊ��Ҫ�ʼ����Ŀ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>

									</tr>
									<tr>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2������ָ��һ�����潫����ɾ����ֻ���޸�����ָ������ƺ������µ�����ָ�ꡣ
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
									ȷ��
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="smlbtn" type="button" onclick="freturn()">
									����
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
		
				case 0:newCell.innerHTML="��&nbsp;&nbsp;&nbsp;&nbsp;��:";newCell.align="right";break;
				case 1:newCell.innerHTML="<input name=\""+propertyKey+"\" type=\"text\" value=\"\" class=\"text\" style=\"width: 150px;\" reqfv=\"required;����\" >&nbsp;<font color=\"red\">*</font>";break;
				
			}
		}
	}
	function del(propertyKey){
		var table=document.getElementById(propertyKey+"tb");
		if(table.rows.length-3>0){
			table.deleteRow(table.rows.length-2);
		}else{
			alert("������дһ����");
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
				case 0:newCell.innerHTML="��&nbsp;&nbsp;&nbsp;&nbsp;��:";newCell.align="right"; break;
				case 1:newCell.innerHTML="<input name=\"quality\" type=\"text\" value=\"\" class=\"text\" style=\"width: 150px;\" reqfv=\"required;����\" >&nbsp;<font color=\"red\">*</font>";break;
			}
		}
	}
	function delQuality(){
		var table=document.getElementById("qualitytb");
		if(table.rows.length-3>0){
			table.deleteRow(table.rows.length-2);
		}else{
			alert("������дһ����");
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
				if(confirm("ȷ��ִ�д˲�����")){
					frm.submit();
				}
			
		} 
		else
		{
			alert("Ʒ�ֱ�ű���������,���Ҳ�����0��ͷ!");
			frm.breedId.value="";
			frm.breedId.focus();
		}
	}
	function freturn(){
		frm.action = "<%=basePath%>breedController.entity?funcflg=getBreedList";
		frm.submit();
	}
	//���������ֺ���ĸ
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
