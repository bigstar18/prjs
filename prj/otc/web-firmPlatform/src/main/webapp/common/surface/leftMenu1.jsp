<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>

<script type="text/javascript" src="<%=serverPath %>/public/jslib/xtree.js"></script>

<link rel="stylesheet" type="text/css" media="all" href="${skinPath}/xtree.css" />
<link rel="stylesheet" href="${skinPath}/style.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${skinPath}/common1.css"/>

<html>
<head>
<script type="text/javascript">
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
		<div id="div_entry" align="left" style="position: absolute; top: 0px; left: 0px; z-index: 20; width: 185px; height: 21px; overflow: hidden; cursor: hand;" onclick="collaspe();" curstatus="hide">
			<img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_01.gif" width="10" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_bg.gif" width="72" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_03.gif" width="21" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_bg.gif" width="72" height="21"/><img id="img_drwPuller" src="${skinPath}/images1/drawer_PullDown_02.gif" width="10" height="21"/>
		</div>
		<!-- 绘制做菜单顶部结束 -->
		<!-- 绘制做菜单开始 -->
		<div id="div_Container" align="left" style="position: absolute; z-index: 11; left: 0px; display: none; width: 185px; height: 0px; overflow: hidden;">		 
		<!-- 图层数 -->
		  			<div style="background-color: #FFF6F4; border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
					<div id="div_drw_Panel" align="left" curDrwNo="0" class="div_drwPanel2">&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/06.gif" width="15" height="13" hspace="8" />&nbsp;查看</div>
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
										M01 = new WebFXTree("<img src='../public/menuicon/25_25.gif'> 报表","");
									</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("交易客户报表,../public/menuicon/28_28.gif","<%=basePath %>/report/customer/customerReportQuery.action?LOGINID=${LOGINID}&username=${username}");
												M01.add(M0101);
											</script>
									<script type="text/javascript">
									document.write(M01);
									</script>
									<script type="text/javascript">
										var M01;
										var M0101;
										M01 = new WebFXTree("<img src='../public/menuicon/25_25.gif'> 公告","");
									</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("有效公告,../public/menuicon/28_28.gif","<%=basePath %>/tradeManage/lookAnnouncement/list.action");
												M01.add(M0101);
											</script>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("过期公告,../public/menuicon/28_28.gif","<%=basePath %>/tradeManage/lookHisAnnouncement/list.action");
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