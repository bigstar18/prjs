<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�鿴����</title>
	</head>
	<body style="overflow-y: hidden">
		<form name="frm" action="" method="post" targetType="hidden">
			<div>
				<table border="0" width="500" align="center">
					<tr height="70"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43"
									height="40" align="absmiddle" />
								&nbsp;&nbsp;�鿴����
							</div>
							<table border="0" cellspacing="0" cellpadding="10" width="100%"
								align="center" class="st_bor" style="table-layout: fixed;word-break:break-all;">
								<tr>
									<td align="right" width="20%">
										����Ա��
									</td>
									<td>
										${obj.author}
									</td>
								</tr>
								<tr>
									<td align="right">
										����������
									</td>
									<td width="350">
										${obj.publishOrganization}
									</td>
								</tr>
								<tr>
									<td align="right">
										���⣺
									</td>
									<td>
										<textarea rows="2" cols="50" id="title"
											name="obj.title" readonly="readonly">${obj.title}</textarea>
									</td>
								</tr>
								<tr>
									<td align="right">
										����ʱ�䣺
									</td>
									<td>
										${datefn:formatdate(obj.sendTime)}
									</td>
								</tr>
								<tr>
									<td align="right">
										ʧЧʱ�䣺
									</td>
									<td>
										${datefn:formatdate(obj.expiryTime)}
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										��������:
									</td>
									<td>
										<textarea rows="11" cols="50" id="content"
											name="content"  readonly="readonly">${obj.content}</textarea>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center" style="padding-top: 30px;">
				<tr>
					<td align="center">
						<button class="btn_sec" onclick="window.close()">
							�ر�
						</button>
					</td>
				</tr>
			</table>
		</form>
	</body>

</html>
<script type="text/javascript">
function getAll(str){
	var result = new Array();
	var elements = document.all;
	var n=0;
	for(var i=0;i<elements.length;i++){
		var element = elements(i);
		if(element.name != null &&typeof(element.name)=="string"&& element.name.substr(0,str.length)==str){
			result[n++]=element;
		}
	}
	return result;
}

	var elements = getAll("obj.");
	RemoveControl(elements);
	
function RemoveControl(elements)
 {
  var arrObj = new Array();
  
  var count = elements.length;
  for(var i=0;i<count;i++)
  {
   if(elements[i] == undefined)
    continue;
    
   var obj = document.createElement('label');
   switch(elements[i].type)
   {
    case "text" : 
      obj.style.width=elements[i].style.width;
      obj.className="word_warpbreak";
      obj.setAttribute("innerHTML",elements[i].value);
      break;
    case "textarea" :
      obj.style.width=elements[i].style.width;
      obj.className="word_warpbreak";
      obj.setAttribute("innerHTML",elements[i].innerHTML);
      elements[i].innerHTML = '';
      break;
    case "select-one" :
      for(var j=0;j<elements[i].length;j++)
      {
       if(elements[i][j].selected)
       {
        obj.style.width=elements[i].style.width;
        obj.className="word_warpbreak";
        obj.setAttribute("innerHTML",elements[i][j].text);
        break;
       }
      }
      elements[i].options.length = 0;
      break;      
   }
   //elements[i].parentNode.appendChild(obj);
   elements[i].parentNode.insertBefore(obj,elements[i]);
   arrObj[arrObj.length] = elements[i];
  }
  
  //ɾ����ԭ�ؼ�
  for(var i=0;i<arrObj.length;i++)
  {
   arrObj[i].removeNode();
  }
 }
</script>
<%@ include file="/public/footInc.jsp"%>

