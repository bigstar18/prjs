<%@ page contentType="text/html;charset=GBK" %>

<html>
	<head>
		<%@ include file="public/headInc.jsp" %>
		<title>财务系统</title>
		<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
		<script language="javascript" src="<%=basePath%>/public/jstools/frameCtrl.js"></script>
		<script language="javascript">
			function defDrawerHegiht() 
			{
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

					objSwitch.src = "<%=skinPath%>/images/drawer_PullUp.gif";
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

					objSwitch.src = "<%=skinPath%>/images/drawer_PullDown.gif";
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
				correctPNG();
				collaspe("show");
			}
		</script>
	</head>

	<body class="leftframe" onload="start();">
		<div id="div_BackGround" style="width: 100%; height: 100%; overflow: hidden; background-image: url(<%=skinPath%>/images/left_BackGround.gif); padding-top: 45px; padding-right: 6px;">
			<table style="width: 93%; height: 100%;" border="0" cellspacing="0" cellpadding="0" align="right">
				<tr height="100%">
					<td>
					</td>
				</tr>
				<tr height="25">
					<td>
        				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
        					<tr>
        						<td align="center" valign="middle">用户：<c:out value="${logonUser.userName}" default="anonymous"/></td>
        					</tr>
        				</table>
					</td>
				</tr>
			</table>
		</div>
		<div id="div_Drawer" style="position: absolute; top: 0px; left: 0px; z-index: 10;">
			<div id="div_entry" align="left" style="position: absolute; top: 0px; left: 0px; z-index: 20; width: 135px; height: 21px; overflow: hidden; cursor: hand;" onclick="collaspe();" curstatus="hide">
				<img id="img_drwPuller" src="<%=skinPath%>/images/drawer_PullDown.gif" width="135" height="21" alt="主功能入口" />
			</div>
			<div id="div_Container" align="left" style="position: absolute; z-index: 11; left: 0px; display: none; width: 135px; height: 0px; overflow: hidden;">
				<div style="background-image: url(<%=skinPath%>/images/drawer_BackGround.gif); border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="0" onclick="drwSwitch();">凭 证 管 理</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display: inline;">
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleInput)}'>
	    							onclick="gotoUrl('<%=basePath%>/voucherList.spr?_flag=edit&_voucherStatus[%3D]=editing&_inputUser[%3D]=<c:out value="${logonUser.userId}"/>')";
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/actualize_plan.png" width="30" height="30" />
    						<br>
    						录入凭证
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleConfirm)}'>
	    							onclick="gotoUrl('<%=basePath%>/voucherList.spr?_flag=confirm&_voucherStatus[%3D]=editing')";
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/work_projet.png" width="30" height="30" />
    						<br>
    						凭证确认
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleAudit)}'>
	    							onclick="gotoUrl('<%=basePath%>/voucherList.spr?_flag=audit&_voucherStatus[%3D]=auditing')";
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/work_examine.png" width="30" height="30" />
    						<br>
    						财务审核
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null}'>
	    							onclick="gotoUrl('<%=basePath%>/voucherList.spr')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/charge_select.png" width="30" height="30" />
    						<br>
    						凭证查询
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleAudit)}'>
	    							onclick="gotoUrl('<%=basePath%>/voucher/accountVoucher.jsp');" 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/due_emp.png" width="30" height="30"/>
    						<br>
    						凭证结算
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null}'>
	    							onclick="gotoUrl('<%=basePath%>/voucher/listVoucherBase.jsp')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/ass_stat.png" width="30" height="30" />
    						<br>
    						已入账凭证查询
    					</span>
    				</div>
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="1" onclick="drwSwitch();">账 务 查 询</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display: none;">
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleQuery)}'>
	    							onclick="gotoUrl('<%=basePath%>/reportFirmBalance.spr');"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/organise_fabric.png" width="30" height="30" />
    						<br>
    						商户余额对比
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleQuery)}'>
	    							onclick="gotoUrl('<%=basePath%>/report/queryAccountBook.jsp')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/outbox.png" width="30" height="30" />
    						<br>
    						账簿查询
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleQuery)}'>
	    							onclick="gotoUrl('<%=basePath%>/report/queryDailyBalance.jsp')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/ass_stat.png" width="30" height="30" />
    						<br>
    						结算查询
    					</span>
    				</div>
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="2" onclick="drwSwitch();">账 务 报 表</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display: none;">
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleQuery)}'>
	    							onclick="gotoUrl('<%=basePath%>/report/reportDailyBalance.jsp')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/ass_stat.png" width="30" height="30" />
    						<br>
    						结算日报表
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleQuery)}'>
	    							onclick="gotoUrl('<%=basePath%>/report/queryLedger.jsp')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/repertory.png" width="30" height="30" />
    						<br>
    						分类账
    					</span>
    					
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleQuery)}'>
	    							onclick="gotoUrl('<%=basePath%>/report/queryClientLedger.jsp')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/Announcement.png" width="30" height="30" />
    						<br>
    						客户总账单
    					</span>
    				</div>
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="3" onclick="drwSwitch();">数 据 维 护</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display:none;">
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleInput||roleAudit)}'>
	    							onclick="gotoUrl('<%=basePath%>/voucherSummaryList.spr')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/lose.png" width="30" height="30" />
    						<br>
    						摘要维护
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleInput||roleAudit)}'>
	    							onclick="gotoUrl('<%=basePath%>/accountList.spr?_flag=edit')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/sort_setting.png" width="30" height="30" />
    						<br>
    						科目维护
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleInput||roleAudit)}'>
	    							onclick="gotoUrl('<%=basePath%>/accountList.spr?_flag=firm')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/flow_setting.png" width="30" height="30" />
    						<br>
    						同步科目
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleAudit)}'>
	    							onclick="gotoUrl('<%=basePath%>/report/redoBalance.jsp')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/gift_2.png" width="30" height="30" />
    						<br>
    						重做结算
    					</span>
    					<span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&roleAdmin}'>
	    							onclick="gotoUrl('<%=basePath%>/userList.spr')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=skinPath%>/ico/application.png" width="30" height="30" />
    						<br>
    						用户管理
    					</span>
    				</div>
				</div>
        		<div align="left" style="width: 135px; height: 14px;">
        			<img id="img_drwPuller" src="<%=skinPath%>/images/drawer_End.gif" width="135" height="14" />
        		</div>
			</div>
		</div>
	<span id="span_curstatus" curNo="0" style="display:none;"></span>
	</body>
</html>