<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>

<script type="text/javascript" src="<%=serverPath %>/public/jslib/xtree.js"></script>

<link rel="stylesheet" type="text/css" media="all" href="<%=skinPath%>/xtree.css" />
<link rel="stylesheet" href="<%=skinPath%>/style.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=skinPath%>/common1.css"/>

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
				div_Container.style.height = ( bottomPosition - 21 - 25 );
				
				var objDrwPanel = document.all.namedItem( "div_drw_Panel" );
				var objDrwContent = document.all.namedItem( "div_drw_Content" );
				for ( var i = 0; i < objDrwContent.length; i++ )
				{   
					objDrwContent[i].style.height = ( bottomPosition - 21 - 25 - 14 - ( 26 * objDrwPanel.length ) );
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
					objSwitch.src = "<%=skinPath%>/images1/drawer_PullDown_01.gif";
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
					objSwitch.src = "<%=skinPath%>/images1/drawer_PullDown_01.gif";
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
		background-image: url(<%=skinPath%>/images1/205_01.jpg);
		background-position: bottom center;
		background-repeat: no-repeat;
		filter:Alpha(opacity=100);
		cursor: hand;
		font-size: 10pt;
		padding-top: 7px;
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
<body class="leftframe" onload="start();"  onselectstart="return false">
	<div id="div_BackGround" style="width: 100%; height: 100%; overflow: hidden; background-image: url(<%=skinPath%>/images1/205_02.jpg); padding-top: 45px; padding-right: 6px;">
		<table style="width: 93%; height: 100%;" border="0" cellspacing="0" cellpadding="0" align="right">
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
			<img id="img_drwPuller" src="<%=skinPath%>/images1/drawer_PullDown_01.gif" width="10" height="21"/><img id="img_drwPuller" src="<%=skinPath%>/images1/drawer_PullDown_bg.gif" width="72" height="21"/><img id="img_drwPuller" src="<%=skinPath%>/images1/drawer_PullDown_03.gif" width="21" height="21"/><img id="img_drwPuller" src="<%=skinPath%>/images1/drawer_PullDown_bg.gif" width="72" height="21"/><img id="img_drwPuller" src="<%=skinPath%>/images1/drawer_PullDown_02.gif" width="10" height="21"/>
		</div>
		<!-- 绘制做菜单顶部结束 -->
		<!-- 绘制做菜单开始 -->
		<div id="div_Container" align="left" style="position: absolute; z-index: 11; left: 0px; display: none; width: 185px; height: 0px; overflow: hidden;">		 
		<c:set var="pictureLayerNumber" value="-1"/><!-- 图层数 -->
			<c:choose>
		  		<c:when test="${skinName == default }">
		  			<div style="background-color: #def2fb; border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
		  		</c:when>
		  		<c:when test="${skinName == gray }">
		  			<div style="background-color: #eeeeee; border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
		  		</c:when>
	  		</c:choose>
	  		<!-- 展示菜单：规则：（所有菜单中公用的以及）登录用户权限内的菜单 -->
			
			<c:set var="menuSet" value="${allMenu.menuSet }"/>
			<c:forEach items="${menuSet }" var="moduleMenu">
			    
				<!-- 判断是否有该模块权限 -->
				<c:set var="hasRight" value="false"/>
				<c:forEach items="${rightMap }" var="right">
					<c:if test="${right.value.id == moduleMenu.id }">
						<c:set var="hasRight" value="true"/>
						<c:set var="pictureLayerNumber" value="${pictureLayerNumber+1 }"/>
					</c:if>
				</c:forEach>
				<!-- 有该模块权限则展示 -->
				<c:if test="${hasRight }">
					<!-- 展示模块名称 -->
					<div id="div_drw_Panel" align="center" curDrwNo="${pictureLayerNumber }" onclick="drwSwitch();" class="div_drwPanel2">${moduleMenu.name }</div>
					<c:choose>
						<c:when test="${pictureLayerNumber==0 }">
							<div id="div_drw_Content" align="center" class="div_drwContent2" style="display: inline;">
						</c:when>
						<c:otherwise>
							<div id="div_drw_Content" align="center" class="div_drwContent2" style="display: none;">
						</c:otherwise>
					</c:choose>
					
					<!-- 展示模块下一级菜单 -->
					<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="200" dwcopytype="CopyTableRow">
					<TR>
					<TD WIDTH="96%" NOWRAP="true" VALIGN="TOP">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						  <td valign="top">
							<div id="tree" style="position: relative;left: 0;padding: 10px;">
							
							<c:set var="menuSetBelongeModule" value="${moduleMenu.menuSet }"/>
							<c:forEach items="${menuSetBelongeModule }" var="firstLevelMenu">
								
								<!-- 判断是否有该模块下一级菜单权限 -->
								<c:set var="hasRightOfModule" value="false"/>
								<c:forEach items="${rightMap }" var="right">
									<c:if test="${right.value.id == firstLevelMenu.id }">
										<c:set var="hasRightOfModule" value="true"/>
									</c:if>
								</c:forEach>
								<!-- 有该模块下一级菜单权限则展示 -->
								<c:if test="${hasRightOfModule }">
								
									<!-- 设置菜单图片 -->
									<c:set var="currentMenuIoc" value="<%=skinPath+"/images2/gif_01.gif" %>"/>
									<c:if test="${not empty firstLevelMenu.icon }">
										<c:set var="currentMenuIoc" value="${menuPicPath }${firstLevelMenu.icon }"/>
									</c:if>
									<script type="text/javascript">
										var M01;
										var M0101;
										M01 = new WebFXTree("<img src='${currentMenuIoc }'> ${firstLevelMenu.name }","");
									</script>
									
									<!-- 展示模块下二级菜单 -->
									<c:set var="menuSetBelongeModuleMenu" value="${firstLevelMenu.menuSet }"/>
									<c:forEach items="${menuSetBelongeModuleMenu }" var="secondLevelMenu">
										
										<!-- 判断是否有该模块下二级菜单权限 -->
										<c:set var="hasRightOfModuleMenu" value="false"/>
										<c:forEach items="${rightMap }" var="right">
											<c:if test="${right.value.id == secondLevelMenu.id }">
												<c:set var="hasRightOfModuleMenu" value="true"/>
											</c:if>
										</c:forEach>
										<!-- 有该模块下二级菜单权限则展示 -->
										<c:if test="${hasRightOfModuleMenu }">
										
											<!-- 设置菜单图片 -->
											<c:set var="currentMenuIoc" value="<%=skinPath+"/images2/gif_07.gif" %>"/>
											<c:if test="${not empty secondLevelMenu.icon }">
												<c:set var="currentMenuIoc" value="${menuPicPath }${secondLevelMenu.icon }"/>
											</c:if>
											<c:set var="currentMenuUrl" value="${projectPath }${secondLevelMenu.url }"/>
											<script type="text/javascript">
												M0101 = new WebFXTreeItem("${secondLevelMenu.name },${currentMenuIoc }","${currentMenuUrl}");
												M01.add(M0101);
											</script>
										</c:if>
									</c:forEach>
									
									<script type="text/javascript">
									document.write(M01);
									</script>
								</c:if>
							</c:forEach>
							</div>
						 </td>
						</tr>
					  </table>
					</TD>
				  </TR>
				</TABLE>
				</div>	
				</c:if>
			</c:forEach>
			</div>
		<!-- 绘制做菜单结束 -->
		<!-- 绘制做菜单底部开始 -->
		<div style="background-image: url(<%=skinPath%>/images1/drawer_BackGround.gif); border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
       		<div align="left" style="width: 185px; height: 14px;">
       			<img id="img_drwPuller" src="<%=skinPath%>/images1/drawer_End_01.gif" width="9" height="14"/><img id="img_drwPuller" src="<%=skinPath%>/images1/drawer_End_bg.gif" width="166" height="14"/><img id="img_drwPuller" src="<%=skinPath%>/images1/drawer_End_02.gif" width="9" height="14"/>
       		</div>
	    </div>					
		</div>
        <c:if test="${pictureLayerNumber==-1 }">
	        <div>
	           	<div id="div_drw_Content" align="center" class="div_drwContent" style="display:none;"></div>
				<div id="div_drw_Panel" align="center" class="div_drwContent" style="display:none;"></div>
			</div>
        </c:if>
		<!-- 绘制做菜单底部结束 -->
		</div>
	  <span id="span_curstatus" curNo="0" style="display:none;"></span>
	</body>
</html>