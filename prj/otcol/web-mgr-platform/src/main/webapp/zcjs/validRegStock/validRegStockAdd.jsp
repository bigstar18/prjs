<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../public/session.jsp"%>
<html>
	<head>
		<title></title>
		<script type="text/javascript">
			function keyDown() 
			{ 
				var keycode=event.keyCode; 
				var keyChar=String.fromCharCode(keycode); 
				if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!=''&& event.srcElement.type!='textarea') 
				{ 
					event.keyCode=9; 
				} 
			}; 
		document.onkeydown=keyDown;
		</script>
		
	</head>
	<body>
		<form name="frm"
			action="<%=basePath%>regStockController.zcjs?funcflg=viewForwardRegStock"
			method="post">
			<fieldset width="95%">
				<legend>
					��Ч�ֵ����
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="80%">
					<tr>
						<td align="right">
							�����̴��룺
						</td>
						<td align="left">
							<input type="text" name="id" id="id" onblur="oneAjax()"/>
						</td>
					</tr>
					<tr height="50">
						<td align="right">
							�ֵ��ţ�
						</td>
						<td>
							<span id="span">
						</td>
					</tr>
					<tr>
						<td align="right" width="40%">

							<button type="button" class="smlbtn" onclick="doQuery1()">
								��һ��&nbsp;&nbsp;
							</button>
						</td>

						<td>
							<button type="button" class="smlbtn" onclick="resetForm();">
								����
							</button>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	
	function resetForm(){
	
		frm.action="<%=basePath%>regStockController.zcjs?funcflg=getvalidRegStockList";
		frm.submit();
	}
	var request = new ActiveXObject("Microsoft.XMLHTTP");


	function oneAjax(){
		var v = frm.id.value;
		request.onreadystatechange = stateChanged;
		request.open("post","<%=basePath%>regStockController.zcjs?funcflg=getRegStockId&firmId="+v,false);
		request.send();
		request.abort();
	}
	function stateChanged() 
	{ 
  		if (request.readyState==4)
  		{ 
 		 var result = request.responseText;
 		 document.getElementById("span").innerHTML=result;
 	 	}
	}
	function doQuery1(){
		if(document.getElementById("regStockId")!=null){
			if(document.getElementById("regStockId").value!=null&&document.getElementById("regStockId").value!=""){
				frm.submit();	
			}else{
				alert("��ѡ�����ֵ���");
			}
		}else{
			alert("�ֵ�����Ч,�����뽻���̴��������ѡ��ֵ��ţ�");
			return false;
		}
	}

</SCRIPT>