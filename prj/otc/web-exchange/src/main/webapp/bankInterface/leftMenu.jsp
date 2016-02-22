<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=GBK">
		<link rel="stylesheet" href="./skin/default/css/style.css" type="text/css"/>
		<title>银行接口管理后台系统</title>
		<script language="javascript" src="./public/jstools/common.js"></script>
		<script language="javascript" src="public/jstools/frameCtrl.js"></script>
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

					objSwitch.src = "./skin/default/images/drawer_PullUp.gif";
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

					objSwitch.src = "./skin/default/images/drawer_PullDown.gif";
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
		</script>
	</head>

	<body class="leftframe" onload="start();">
		<div id="div_BackGround" style="width: 100%; height: 100%; overflow: hidden; background-image: url(./skin/default/images/left_BackGround.gif); padding-top: 45px; padding-right: 6px;">
			<table style="width: 93%; height: 100%;" border="0" cellspacing="0" cellpadding="0" align="right">
				<tr height="100%">
					<td>
					</td>
				</tr>
				<tr height="25">
					<td>
        				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
        					<tr>
        						<td align="center" valign="middle"><!-- 用户： --></td>
        					</tr>
        				</table>
					</td>
				</tr>
			</table>
		</div>
		<div id="div_Drawer" style="position: absolute; top: 0px; left: 0px; z-index: 10;">
			<div id="div_entry" align="left" style="position: absolute; top: 0px; left: 0px; z-index: 20; width: 135px; height: 21px; overflow: hidden; cursor: hand;" onclick="collaspe();" curstatus="hide">
				<img id="img_drwPuller" src="./skin/default/images/drawer_PullDown.gif" width="135" height="21" alt="主功能入口" />
			</div>
			<div id="div_Container" align="left" style="position: absolute; z-index: 11; left: 0px; display: none; width: 135px; height: 0px; overflow: hidden;">
				<div style="background-image: url(./skin/default/images/drawer_BackGround.gif); border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="0" onclick="drwSwitch();">后 台 管 理</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display: inline;">
    					<span class="drwItem"
	    							onclick="window.open('moneyInfo/list.jsp','mainFrame')">
    						<img src="./skin/default/ico/actualize_plan.png" width="30" height="30" />
    						<br>
    						资金流水
    					</span>
						<%
						//CapitalProcessor cp = new CapitalProcessor();
						if(!(Tool.getConfig("AutoAudit")!=null && Tool.getConfig("AutoAudit").equalsIgnoreCase("True")))
						{
						%>
    					<span class="drwItem"
	    							onclick="window.open('moneyInfo/OutMoneyAudit.jsp','mainFrame')">
    						<img src="./skin/default/ico/actualize_plan.png" width="30" height="30" />
    						<br>
    						出金审核
    					</span>
						<%}%>
						<span class="drwItem"
	    							onclick="window.open('bank/list.jsp','mainFrame')">
    						<img src="./skin/default/ico/actualize_plan.png" width="30" height="30" />
    						<br>
    						银行管理
    					</span>
						<span class="drwItem"
	    							onclick="window.open('firm/list.jsp','mainFrame')";>
    						<img src="./skin/default/ico/actualize_plan.png" width="30" height="30" />
    						<br>
    						交易商管理
    					</span>
						<span class="drwItem"
	    							onclick="window.open('compareInfo/result.jsp','mainFrame')">
    						<img src="./skin/default/ico/actualize_plan.png" width="30" height="30" />
    						<br>
    						银行对账信息
    					</span>

    				</div>
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="1" onclick="drwSwitch();">系 统 配 置</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display: none;">

    					<span class="drwItem" onclick="window.open('dictionary/list.jsp','mainFrame')">
    						<img src="./skin/default/ico/Announcement.png" width="30" height="30" />
    						<br>
    						系统字典
    					</span>
    				</div>
				</div>
        		<div align="left" style="width: 135px; height: 14px;">
        			<img id="img_drwPuller" src="./skin/default/images/drawer_End.gif" width="135" height="14" />
        		</div>
			</div>
		</div>
	<span id="span_curstatus" curNo="0" style="display:none;"></span>
	</body>
</html>