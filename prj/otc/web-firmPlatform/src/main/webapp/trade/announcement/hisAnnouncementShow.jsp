<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>查看公告</title>
	</head>
	<body style="overflow-y: hidden; background-color: #F5F5F5">
		<form name="frm" action="" method="post" targetType="hidden">
			<div>
				<table border="0" width="520" align="center">
					<tr height="70"></tr>
					<tr>
						<td>
							<table border="0" cellspacing="0" cellpadding="10" width="100%"
								align="center" style="table-layout: fixed;word-break:break-all;">
								<tr>
									<td align="right" height="25">
										&nbsp;
									</td>
									<td width="420">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right" height="25">
										发布机构：
									</td>
									<td width="420">
										${obj.publishOrganization}
									</td>
								</tr>
								<tr>
									<td align="right" height="25">
										发布时间：
									</td>
									<td>
										${datefn:formatdate(obj.sendTime)}
									</td>
								</tr>
								<tr>
									<td align="right" height="25">
										失效时间：
									</td>
									<td>
										${datefn:formatdate(obj.expiryTime)}
									</td>
								</tr>
								<tr>
									<td align="right" height="25">
										主题：
									</td>
									<td>
										<textarea rows="2" cols="50" id="title"
											name="obj.title" readonly="readonly">${obj.title}</textarea>
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										公告内容：
									</td>
									<td>
										<textarea rows="11" cols="50" id="content"
											name="content" readonly="readonly">${obj.content}</textarea>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					align="center" style="padding-top: 30px;">
					<tr>
						<td align="center">
							<button class="btn_anniu" onclick="window.close()">
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
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
  
  //删除表单原控件
  for(var i=0;i<arrObj.length;i++)
  {
   arrObj[i].removeNode();
  }
 }
</script>
<%@ include file="/public/footInc.jsp"%>

