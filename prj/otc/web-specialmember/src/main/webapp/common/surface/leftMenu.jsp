<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/basecommon.jsp"%>

<script type="text/javascript" src="<%=serverPath %>/public/jslib/xtree.js"></script>
<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/noticePrompt.js'></script>
<html>
<head>
<script type="text/javascript">
var oldNoticeId = -1;
var oldTradeNo = 0;
var oldSystemStatus = "";
var promptTime = 60;
//弹出提示框
var MSG1;
function CLASS_MSN_MESSAGE(id,width,height,caption,title,message,target,action){ 
 this.id = id; 
 this.title = title; 
 this.caption= caption; 
 this.message= message; 
 this.target = target; 
 this.action = action; 
 this.width = width?width:200; 
 this.height = height?height:120; 
 this.timeout= 150; 
 this.speed = 20; 
 this.step = 1; 
 this.right = screen.width -1; 
 this.bottom = screen.height; 
 this.left = this.right - this.width; 
 this.top = this.bottom - this.height; 
 this.timer = 0; 
 this.pause = false;
 this.close = false;
 this.autoHide = true;
}
CLASS_MSN_MESSAGE.prototype.hide = function(){ 
 if(this.onunload()){
 var offset = this.height>this.bottom-this.top?this.height:this.bottom-this.top; 
 var me = this;
 if(this.timer>0){ 
 window.clearInterval(me.timer); 
 }
 var fun = function(){ 
 if(me.pause==false||me.close){
 var x = me.left; 
 var y = 0; 
 var width = me.width; 
 var height = 0; 
 if(me.offset>0){ 
 height = me.offset; 
 } 
 y = me.bottom - height; 
 if(y>=me.bottom){ 
 window.clearInterval(me.timer); 
 me.Pop.hide(); 
 } else { 
 me.offset = me.offset - me.step; 
 } 
 me.Pop.show(x,y,width,height); 
 } 
 }
 this.timer = window.setInterval(fun,this.speed) 
 } 
} 
CLASS_MSN_MESSAGE.prototype.onunload = function() { 
 return true; 
} 
CLASS_MSN_MESSAGE.prototype.oncommand = function(){ 
 //this.hide(); 
//window.open("http://www.baidu.com");
}
CLASS_MSN_MESSAGE.prototype.show = function(){
 var oPopup = window.createPopup(); 
 this.Pop = oPopup; 
 var w = this.width; 
 var h = this.height; 
 var str = "<DIV style='BORDER-RIGHT: #455690 1px solid; BORDER-TOP: #a6b4cf 1px solid; Z-INDEX: 99999; LEFT: 0px; BORDER-LEFT: #a6b4cf 1px solid; WIDTH: " + w + "px; BORDER-BOTTOM: #455690 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: " + h + "px; BACKGROUND-COLOR: #c9d3f3'>" 
 str += "<TABLE style='BORDER-TOP: #ffffff 1px solid; BORDER-LEFT: #ffffff 1px solid' cellSpacing=0 cellPadding=0 width='100%' bgColor=#cfdef4 border=0>" 
 str += "<TR>" 
 str += "<TD style='FONT-SIZE: 12px;COLOR: #0f2c8c' width=30 height=24></TD>" 
 str += "<TD style='PADDING-LEFT: 4px; FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #1f336b; PADDING-TOP: 4px' vAlign=center width='100%'>" + this.caption + "</TD>" 
 str += "<TD style='PADDING-RIGHT: 2px; PADDING-TOP: 2px' vAlign=center align=right width=19>" 
 str += "<SPAN title=关闭 style='FONT-WEIGHT: bold; FONT-SIZE: 12px; CURSOR: hand; COLOR: red; MARGIN-RIGHT: 4px' id='btSysClose' >×</SPAN></TD>" 
 str += "</TR>" 
 str += "<TR>" 
 str += "<TD style='PADDING-RIGHT: 1px;PADDING-BOTTOM: 1px' colSpan=3 height=" + (h-28) + ">" 
 str += "<DIV style='BORDER-RIGHT: #b9c9ef 1px solid; PADDING-RIGHT: 8px; BORDER-TOP: #728eb8 1px solid; PADDING-LEFT: 8px; FONT-SIZE: 12px; PADDING-BOTTOM: 8px; BORDER-LEFT: #728eb8 1px solid; WIDTH: 100%; COLOR: #1f336b; PADDING-TOP: 8px; BORDER-BOTTOM: #b9c9ef 1px solid; HEIGHT: 100%'>" + this.title + "<BR><BR>" 
 str += "<DIV style='WORD-BREAK: break-all' align=left><FONT id=btCommand color=#ff0000>" + this.message + "</FONT></DIV>" 
 str += "</DIV>" 
 str += "</TD>" 
 str += "</TR>" 
 str += "</TABLE>" 
 str += "</DIV>" 
 oPopup.document.body.innerHTML = str; 
 this.offset = 0; 
 var me = this;
 oPopup.document.body.onmouseover = function(){me.pause=true;}
 oPopup.document.body.onmouseout = function(){me.pause=false;}
 var fun = function(){ 
 var x = me.left; 
 var y = 0; 
 var width = me.width; 
 var height = me.height;
 if(me.offset>me.height){ 
 height = me.height; 
 } else { 
 height = me.offset; 
 }
 y = me.bottom - me.offset; 
 if(y<=me.top){ 
 me.timeout--; 
 if(me.timeout==0){ 
 window.clearInterval(me.timer); 
 if(me.autoHide){
 me.hide(); 
 }
 } 
 } else { 
 me.offset = me.offset + me.step; 
 } 
 me.Pop.show(x,y,width,height);
 }
 this.timer = window.setInterval(fun,this.speed)
 var btClose = oPopup.document.getElementById("btSysClose");
 btClose.onclick = function(){ 
 me.close = true;
 me.hide(); 
 }
 var btCommand = oPopup.document.getElementById("btCommand"); 
 btCommand.onclick = function(){ 
 me.oncommand(); 
 } 
 var ommand = oPopup.document.getElementById("ommand"); 
 //ommand.onclick = function(){
 //me.hide(); 
//window.open(ommand.href);
 //} 
}
CLASS_MSN_MESSAGE.prototype.speed = function(s){ 
 var t = 20; 
 try { 
 t = praseInt(s); 
 } catch(e){} 
 this.speed = t; 
}
CLASS_MSN_MESSAGE.prototype.step = function(s){ 
 var t = 1; 
 try { 
 t = praseInt(s); 
 } catch(e){} 
 this.step = t; 
} 
CLASS_MSN_MESSAGE.prototype.rect = function(left,right,top,bottom){ 
 try { 
 this.left = left !=null?left:this.right-this.width; 
 this.right = right !=null?right:this.left +this.width; 
 this.bottom = bottom!=null?(bottom>screen.height?screen.height:bottom):screen.height; 
 this.top = top !=null?top:this.bottom - this.height; 
 } catch(e){} 
} 
            function addCookie(objName,objValue,objHours){//添加cookie
			 var str = objName + "=" + escape(objValue);
			 if(objHours > 0){//为0时不设定过期时间，浏览器关闭时cookie自动消失
			 var date = new Date();
			 var ms = objHours*3600*1000;
			 date.setTime(date.getTime() + ms);
			 str += "; expires=" + date.toGMTString();
			 }
			 document.cookie = str;
			 }
             function getCookie(objName){//获取指定名称的cookie的值
			   var arrStr = document.cookie.split("; ");
				 for(var i = 0;i < arrStr.length;i ++){
				 var temp = arrStr[i].split("=");
				 if(temp[0] == objName) return unescape(temp[1]);
				 }
			 }
			var screenAvailHeight = window.screen.availHeight;
			function regInput(str){  
			      reg=/^[a-z]*$/;
			      return reg.test(str)
		      }
	      
      		function goto(pathRrl,node,parentNode){
      		  var sessionId='${LOGINIDS}';
      		  addCookie(sessionId+"pathRrl",pathRrl,0);
      		  addCookie(sessionId+"node",node,0);
      		  addCookie(sessionId+"parentNode",parentNode,0);
      		 
      		  var urlArray=pathRrl.split("?");
	          if(urlArray.length==1){
					pathRrl=pathRrl+'?AUsessionId='+sessionId;
				}else if(urlArray.length==2){
					pathRrl=pathRrl+'&AUsessionId='+sessionId;
				}
			    //parent.workspace.mainFrame.location.href=pathRrl;
			    parent.workspace.mainFrame.window.location.replace(pathRrl);
				var title=parentNode + " >> " + node;
				parent.workspace.topFrame1.document.getElementById("loc").innerHTML=title;
				var title=parent.document.title;
				var titleArray=title.split("-");
				if(titleArray.length==1){
					title=node+"-"+title;
				}else if(titleArray.length==2){
					title=node+"-"+titleArray[1];
				}
				parent.document.title=title;
			}
			
			function noUse(){
			}
			
			function defDrawerHegiht()
			{
			    //当窗口很小时,菜单不能完全显示,下面时让窗口最大化
			    //top.moveTo(0,0);
				//top.resizeTo(screen.availWidth,screen.availHeight);
				var objBackGround = document.getElementById( "div_BackGround" );
				var objContainer = document.getElementById( "div_Container" );
				var bottomPosition = objBackGround.getBoundingClientRect().bottom;
				div_Container.style.height = ( bottomPosition - 21  );
				
				var objDrwPanel = document.all.namedItem( "div_drw_Panel" );
				var objDrwContent = document.all.namedItem( "div_drw_Content" );
				for ( var i = 0; i < objDrwContent.length; i++ )
				{   
					objDrwContent[i].style.height = ( bottomPosition - 21  - 14 - ( 26 * objDrwPanel.length ) );
				}
				var topPosition = objBackGround.getBoundingClientRect().top + 21;
				objContainer.style.position = "absolute";
				objContainer.style.display = "inline";
				objContainer.style.top = topPosition - parseInt( div_Container.style.height.substr( 0, ( div_Container.style.height.length - 2 ) ) ) + 14;
				objContainer.style.left = "0px";
				objContainer.showTop = topPosition;
				objContainer.hideTop = topPosition - parseInt( div_Container.style.height.substr( 0, ( div_Container.style.height.length - 2 ) ) ) + 14;
				
			}
			
			function collaspe( action )
			{
				var obj = document.getElementById( "div_entry" );
				var objContainer = document.getElementById( "div_Container" );
				var objSwitch = document.getElementById( "img_drwPuller" );
				
				if ( obj.curstatus == "hide" || action =="show" )
				{
					objContainer.style.display = "inline";

					objContainer.style.pixelTop += 50;
					if ( objContainer.style.pixelTop >= objContainer.showTop )
					{
						objContainer.style.pixelTop = objContainer.showTop;
						obj.curstatus = "show";
					}
					else
						window.setTimeout ("collaspe( 'show' );", 10);

					//objSwitch.src = "images1/drawer_PullUp.gif";
					objSwitch.src = "${skinPath}/images1/drawer_PullDown_01.gif";
				}
				else
				{
					objContainer.style.pixelTop -= 50;
					if (objContainer.style.pixelTop <= objContainer.hideTop) 
					{
						objContainer.style.pixelTop = objContainer.hideTop;
						obj.curstatus = "hide";
					}
					else
						window.setTimeout ("collaspe( 'hide' );", 10);

					//objSwitch.src = "images1/drawer_PullDown.gif";
					objSwitch.src = "${skinPath}/images1/drawer_PullDown_01.gif";
					//alert ( objContainer.hideTop );
				}
				
			}
			
			function drwSwitch()
			{
				var objDrwContent = document.all.namedItem( "div_drw_Content" );
				var objMem = document.getElementById("span_curstatus");

				var curNo = event.srcElement.curDrwNo;
            	var lastNo = objMem.curNo;
            	if ( lastNo != curNo )
            	{
            		objDrwContent[lastNo].style.display = "none";
            		objDrwContent[curNo].style.display = "inline";
            		objMem.curNo = curNo;
            	}
            	else
            		return;
			}
			function Map() {
				var myArrays = new Array();
				//添加键值，如果键重复则替换值
				this.put = function(key, value) {
					var v = this.get(key);
					if (v == null) {
						var len = myArrays.length;
						myArrays[len] = {Key: key, Value: value};
					} else {
						for (var i = 0; i < myArrays.length; i++) {
							if (myArrays[i].Key == key) {
								myArrays[i].Value = value;
							}
						}
					}
				}
				//根据键获取值
				this.get = function(key) {
					for (var i = 0; i < myArrays.length; i++) {
						if (myArrays[i].Key == key) {
							return myArrays[i].Value;
						}
					}
					return null;
				}
			}
			function start()
			{
				defDrawerHegiht();
				//correctPNG();
				collaspe("show");
				var sessionId='${LOGINIDS}';
				if(typeof(getCookie(sessionId+"pathRrl"))!= "undefined"){
					var pathRrl=getCookie(sessionId+"pathRrl");
					var node=getCookie(sessionId+"node");
					var parentNode=getCookie(sessionId+"parentNode");
					goto(pathRrl,node,parentNode);
				}
				noticePrompt.promptFun(-1, function(map){
					oldNoticeId = map['maxNoticeId'];
					oldSystemStatus = map['systemStatus'];
					oldTradeNo = map['maxTradeNo'];
				});
				window.setTimeout("showTime()",1000);
			}
			function showTime() {
				var setTime = 0;
				var newSystemStatus = oldSystemStatus;
			    noticePrompt.promptFun(oldTradeNo,  function(map){
			    	var systemStatus = map['systemStatus'];
			    	if(oldSystemStatus != "" && systemStatus != oldSystemStatus){
			    		oldSystemStatus = systemStatus;
			    		alertMsg("系统状态提示", "系统状态由：" + newSystemStatus +  "变成："+oldSystemStatus);
			    	}
			    	var tradeSize = map['tradeSize'];
			    	var newMaxTradeNo = map['maxTradeNo'];
			    	if(oldTradeNo != 0 && tradeSize > 0) {
			    		setTime += 5000;
				    	//var trade = tradeList[tradeList.length - 1];
				    	
				    	window.setTimeout(function() {alertMsg("成交提示", "新增" + tradeSize + "笔成交");oldTradeNo = newMaxTradeNo;},setTime);
			    		setTime += 5000;
			    	}
			    	var maxNoticeId = map['maxNoticeId'];
					if(oldNoticeId != -1 && oldNoticeId != 0 && maxNoticeId > oldNoticeId){
						oldNoticeId = maxNoticeId;
						window.setTimeout(function() {alertMsg("公告提示", "您有新消息，请注意查看！")},setTime);
					}
				    window.setTimeout("showTime()",promptTime*1000 + setTime);
				});
			}
			function alertMsg(titles,msg){
				 if(MSG1 != null ){
					MSG1.hide();
					MSG1 == null;
				 }
				 MSG1 = new CLASS_MSN_MESSAGE("aa",200,120,titles,"",msg); 
				 MSG1.rect(null,null,null,screen.height-50); 
				 MSG1.speed = 10; 
				 MSG1.step = 5; 
				 MSG1.show();
			}
			function chgFont()
			{
				var chgF = document.all("chgF");
				var pic = document.all("pic");
				if(chgF != null)
				{
					for(var i=0;i<chgF.length;i++)
					{
						if(event.srcElement == chgF[i]||event.srcElement == pic[i])
						{
							chgF[i].className = "drwItemBold";
						}
						else
						{
							chgF[i].className = "drwItem";
						}
					}
				}	
			}
</script>
<style>
	div.div_drwPanel2
	{
		width: 183px;
		height: 26px;
		overflow: hidden;
		background-image: url(${skinPath}/images1/205_01.jpg);
		background-position: bottom center;
		background-repeat: no-repeat;
		filter:Alpha(opacity=100);
		cursor: hand;
		padding-top: 7px;
		font-size: 14px;
	    font-weight: bold;
	    color: #FFFFFF;
	}
	div.div_drwContent2
	{
		width: 183px;
		height: 600px;
		height: window.screen.availHeight;
		overflow-x: hidden;
		overflow-y: auto;
	}
    .a{
      font-weight:bold;
    }
    .b{
      font-weight:normal;
     }
	.webfx-tree-item1 {
		font-size: 13px;
		line-height: 25px;
		color:#26548B
	}
</style>
</head>
<body class="leftframe" onload="start();"  onselectstart="return false" >
	<div id="div_BackGround" style="width: 100%; height: 100%; overflow: hidden; background-color: #FFF6F4; padding-top: 45px; padding-right: 6px;">
		<table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0" align="right">
			<tr height="100%"><td></td></tr>
			<tr height="25">
				<td>
        		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
        			<tr><td align="center" valign="middle"></td></tr>
        		</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="div_Drawer" style="position: absolute; top: 0px; left: 0px; z-index: 10;">
		<div id="div_entry" align="left" style="position: absolute; top: 0px; left: 0px; z-index: 20; width: 185px; height: 21px; overflow: hidden; cursor: hand;" onclick="collaspe();" curstatus="hide">
			<img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_01.gif" width="10" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_bg.gif" width="72" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_03.gif" width="21" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_bg.gif" width="72" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_02.gif" width="10" height="21"/>
		</div>
		<div id="div_Container" align="left" style="position: absolute; z-index: 11; left: 0px; display: none; width: 185px; height: 0px; overflow: hidden;">		 
		<c:set var="pictureLayerNumber" value="-1"/><c:choose><c:when test="${skinName == default }"><div style="background-color: #FFF6F4; border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);"></c:when><c:when test="${skinName == gray }"><div style="background-color: #eeeeee; border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);"></c:when>
	  		</c:choose><c:set var="menuSet" value="${allMenu.menuSet }"/><c:forEach items="${menuSet }" var="moduleMenu"><c:set var="hasRight" value="false"/><c:forEach items="${rightMap }" var="right"><c:if test="${right.value.id == moduleMenu.id }"><c:set var="hasRight" value="true"/><c:set var="pictureLayerNumber" value="${pictureLayerNumber+1 }"/></c:if></c:forEach><c:if test="${hasRight }">
			<div id="div_drw_Panel" align="left" curDrwNo="${pictureLayerNumber }" onclick="drwSwitch();" class="div_drwPanel2">&nbsp;&nbsp;
					  <img src="<%=skinPath%>/cssimg/06.gif" width="15" height="13" hspace="8" curDrwNo="${pictureLayerNumber }"/>&nbsp;${moduleMenu.name }</div>
					<c:choose><c:when test="${pictureLayerNumber==0 }"><div id="div_drw_Content" align="center" class="div_drwContent2" style="display: inline;"></c:when><c:otherwise><div id="div_drw_Content" align="center" class="div_drwContent2" style="display: none;"></c:otherwise></c:choose>
					<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="200" dwcopytype="CopyTableRow">
					<TR>
					<TD WIDTH="96%" NOWRAP="true" VALIGN="TOP">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						  <td valign="top">
							<div id="tree" style="position: relative;left: 0;padding: 10px 10px 10px 5px;"><c:set var="menuSetBelongeModule" value="${moduleMenu.menuSet }"/><c:forEach items="${menuSetBelongeModule }" var="firstLevelMenu"><c:set var="hasRightOfModule" value="false"/><c:forEach items="${rightMap }" var="right"><c:if test="${right.value.id == firstLevelMenu.id }"><c:set var="hasRightOfModule" value="true"/></c:if></c:forEach><c:if test="${hasRightOfModule }"><c:set var="currentMenuIoc" value="${skinPath}/images2/gif_01.gif"/><c:if test="${not empty firstLevelMenu.icon }"><c:set var="currentMenuIoc" value="${menuPicPath }${firstLevelMenu.icon }"/></c:if>
									<script type="text/javascript">
										var M01;
										var M0101;
										M01 = new WebFXTree("<img src='${currentMenuIoc }'> ${firstLevelMenu.name }","");
									</script><c:set var="menuSetBelongeModuleMenu" value="${firstLevelMenu.menuSet }"/><c:forEach items="${menuSetBelongeModuleMenu }" var="secondLevelMenu"><c:set var="hasRightOfModuleMenu" value="false"/><c:forEach items="${rightMap }" var="right"><c:if test="${right.value.id == secondLevelMenu.id }"><c:set var="hasRightOfModuleMenu" value="true"/></c:if></c:forEach><c:if test="${hasRightOfModuleMenu }"><c:set var="currentMenuIoc" value="${skinPath}/images2/gif_07.gif"/><c:if test="${not empty secondLevelMenu.icon }"><c:set var="currentMenuIoc" value="${menuPicPath }${secondLevelMenu.icon }"/></c:if><c:set var="currentMenuUrl" value="${projectPath }${secondLevelMenu.url }"/>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("${secondLevelMenu.name },${currentMenuIoc }","${currentMenuUrl}");
												M01.add(M0101);
											</script></c:if></c:forEach>
									<script type="text/javascript">
									document.write(M01);
									</script></c:if></c:forEach>
							</div>
						 </td>
						</tr>
					  </table>
					</TD>
				  </TR>
				</TABLE>
				</div></c:if></c:forEach>
			</div>
		<div style="background-image: url(${skinPath}/images1/drawer_BackGround.gif); border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
       		<div align="left" style="width: 185px; height: 14px;">
       			<img id="img_drwPuller" src="${skinPath}/images1/drawer_End_01.gif" width="9" height="14"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_End_bg.gif" width="166" height="14"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_End_02.gif" width="9" height="14"/>
       		</div>
	    </div>					
		</div><c:if test="${pictureLayerNumber==-1 }">
	        <div>
	           	<div id="div_drw_Content" align="center" class="div_drwContent" style="display:none;"></div>
				<div id="div_drw_Panel" align="center" class="div_drwContent" style="display:none;"></div>
			</div></c:if>
		</div>
	  <span id="span_curstatus" curNo="0" style="display:none;"></span>
	</body>
</html>