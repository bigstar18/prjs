<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/basecommon.jsp"%>

<script type="text/javascript" src="<%=serverPath %>/public/jslib/xtree.js"></script>
<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/noticePrompt.js'></script>
<link rel="stylesheet" type="text/css" media="all" href="${skinPath}/xtree.css" />
<link rel="stylesheet" href="${skinPath}/style.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${skinPath}/common1.css"/>

<html>
<head>
<script type="text/javascript">
var oldNoticeId = 0;
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
		var screenAvailHeight = window.screen.availHeight;
		function regInput(str){  
		      reg=/^[a-z]*$/;
		      return reg.test(str)
	      }
	      
      		function goto(pathRrl,node,parentNode){
			  parent.workspace.mainFrame.location.href=pathRrl;
				var title=parentNode + " >> " + node;
				parent.workspace.topFrame1.document.getElementById("loc").innerHTML=title;
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
			
			function start()
			{
				defDrawerHegiht();
				//correctPNG();
				collaspe("show");
				noticePrompt.getMaxNoticeId(function(maxNoticeId){
					oldNoticeId = maxNoticeId;
				});
				window.setTimeout("validate()",1000);
				//aldit();
			}
			
			function validate() {
				//alert(oldNoticeId);
	            noticePrompt.getMaxNoticeId(function(maxNoticeId){
				//alert(maxNoticeId);
					if(maxNoticeId > oldNoticeId){
						oldNoticeId = maxNoticeId;
						alertMsg("公告提示", "您有新消息，请注意查看！");
					}
					window.setTimeout("validate()",promptTime*1000);
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
		height: 400px;
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
		<!-- 绘制做菜单顶部开始 -->
		<div id="div_entry" align="left" style="position: absolute; top: 0px; left: 0px; z-index: 20; width: 185px; height: 21px; overflow: hidden; cursor: hand;" curstatus="hide">
			<img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_01.gif" width="10" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_bg.gif" width="72" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_03.gif" width="21" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_bg.gif" width="72" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_02.gif" width="10" height="21"/>
		</div>
		<!-- 绘制做菜单顶部结束 -->
		<!-- 绘制做菜单开始 -->
		<div id="div_Container" align="left" style="position: absolute; z-index: 11; left: 0px; display: none; width: 185px; height: 0px; overflow: hidden;">		 
		<!-- 图层数 -->
		  			<div style="background-color: #FFF6F4; border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
					<div id="div_drw_Panel" align="left" curDrwNo="0" class="div_drwPanel2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看</div>
							<div id="div_drw_Content" align="center" class="div_drwContent2" style="display: inline;">
					<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="200" dwcopytype="CopyTableRow">
					<TR>
					<TD WIDTH="96%" NOWRAP="true" VALIGN="TOP">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						  <td valign="top">
							<div id="tree" style="position: relative;left: 0;padding: 10px;">
									<script type="text/javascript">
										var M01;
										var M0101;
										M01 = new WebFXTree("<img src='../public/menuicon/25_25.gif'> 公告","");
									</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("有效公告,../public/menuicon/28_28.gif","<%=basePath %>/tradeManage/lookAnnouncement/list.action?LOGINID=${LOGINID}&username=${username}");
												M01.add(M0101);
											</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("过期公告,../public/menuicon/28_28.gif","<%=basePath %>/tradeManage/lookHisAnnouncement/list.action?LOGINID=${LOGINID}&username=${username}");
												M01.add(M0101);
											</script>
									<script type="text/javascript">
									document.write(M01);
									</script>
									

									<script type="text/javascript">
										var M01;
										var M0101;
										M01 = new WebFXTree("<img src='../public/menuicon/25_25.gif'> 业务报表","");
									</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("客户资金状况表,../public/menuicon/33_33.gif","<%=basePath%>/report/customerFunds/customerFundsReportQuery.action?noQuery=true");
												M01.add(M0101);
											</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("客户持仓汇总表,../public/menuicon/33_33.gif","<%=basePath%>/report/customerHoldSummary/customerHoldSummaryReportQuery.action?noQuery=true");
												M01.add(M0101);
											</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("客户成交汇总表,../public/menuicon/33_33.gif","<%=basePath%>/report/customerTrade/customerTradeReportQuery.action?noQuery=true");
												M01.add(M0101);
											</script>
									<script type="text/javascript">
									document.write(M01);
									</script>
									<script type="text/javascript">
										var M01;
										var M0101;
										M01 = new WebFXTree("<img src='../public/menuicon/25_25.gif'> 客户查询","");
									</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("客户持仓查询,../public/menuicon/37_37.gif","<%=basePath%>/query/queryCustomerHoldSearch/list.action?noQuery=true");
												M01.add(M0101);
											</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("客户成交查询,../public/menuicon/37_37.gif","<%=basePath%>/query/queryCustomerTransactionSearch/list.action?noQuery=true");
												M01.add(M0101);
											</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("客户委托单查询,../public/menuicon/37_37.gif","<%=basePath%>/query/queryCustomerLimitPriceSearch/list.action?noQuery=true");
												M01.add(M0101);
											</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("客户资金流水查询,../public/menuicon/37_37.gif","<%=basePath%>/query/queryCustomerFundFlowSearch/list.action?noQuery=true");
												M01.add(M0101);
											</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("客户资金查询,../public/menuicon/37_37.gif","<%=basePath%>/query/queryCustomerFundSearch/list.action?noQuery=true");
												M01.add(M0101);
											</script>
									<script type="text/javascript">
									document.write(M01);
									</script>

							</div>
						 </td>
						</tr>
					  </table>
					</TD>
				  </TR>
				</TABLE>
				</div>	
				<!-- 有该模块权限则展示 -->
					<!-- 展示模块名称 -->
					<div id="div_drw_Panel" align="left" curDrwNo="1" class="div_drwPanel2">&nbsp;&nbsp;</div>
					<div id="div_drw_Content" align="center" class="div_drwContent2" style="display: none;">
					<!-- 展示模块下一级菜单 -->
					<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="200" dwcopytype="CopyTableRow">
						<TR>
						
					  </TR>
					</TABLE>
					</div>	
			</div>
		<!-- 绘制做菜单结束 -->
		<!-- 绘制做菜单底部开始 -->
		<div style="background-image: url(../skinstyle/default/common/commoncss/images1/drawer_BackGround.gif); border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
       		<div align="left" style="width: 185px; height: 14px;">
       			<img id="img_drwPuller" src="../skinstyle/default/common/commoncss/images1/drawer_End_01.gif" width="9" height="14"/><img id="img_drwPuller" src="../skinstyle/default/common/commoncss/images1/drawer_End_bg.gif" width="166" height="14"/><img id="img_drwPuller" src="../skinstyle/default/common/commoncss/images1/drawer_End_02.gif" width="9" height="14"/>
       		</div>
	    </div>					
		</div>
        
		<!-- 绘制做菜单底部结束 -->
		</div>
	  <span id="span_curstatus" curNo="0" style="display:none;"></span>
	</body>
</html>