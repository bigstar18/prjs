<%@ page contentType="text/html;charset=GBK" %>

<html>
	<head>
		<%@ include file="public/headInc.jsp" %>
		<title>����ϵͳ</title>
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

					objSwitch.src = "<%=basePath + "/skin/" + userSkinName%>/images/drawer_PullUp.gif";
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

					objSwitch.src = "<%=basePath + "/skin/" + userSkinName%>/images/drawer_PullDown.gif";
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
		<div id="div_BackGround" style="width: 100%; height: 100%; overflow: hidden; background-image: url(<%=basePath + "/skin/" + userSkinName%>/images/left_BackGround.gif); padding-top: 45px; padding-right: 6px;">
			<table style="width: 93%; height: 100%;" border="0" cellspacing="0" cellpadding="0" align="right">
				<tr height="100%">
					<td>
					</td>
				</tr>
				<tr height="25">
					<td>
        				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
        					<tr>
        						<td align="center" valign="middle">�û���<c:out value="${logonUser.userName}" default="anonymous"/></td>
        					</tr>
        				</table>
					</td>
				</tr>
			</table>
		</div>
		<div id="div_Drawer" style="position: absolute; top: 0px; left: 0px; z-index: 10;">
			<div id="div_entry" align="left" style="position: absolute; top: 0px; left: 0px; z-index: 20; width: 135px; height: 21px; overflow: hidden; cursor: hand;" onclick="collaspe();" curstatus="hide">
				<img id="img_drwPuller" src="<%=basePath + "/skin/" + userSkinName%>/images/drawer_PullDown.gif" width="135" height="21" alt="���������" />
			</div>
			<div id="div_Container" align="left" style="position: absolute; z-index: 11; left: 0px; display: none; width: 135px; height: 0px; overflow: hidden;">
				<div style="background-image: url(<%=basePath + "/skin/" + userSkinName%>/images/drawer_BackGround.gif); border-left: 1px solid #94989A; border-right: 1px solid #94989A; filter:Alpha(opacity=90);">
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="0" onclick="drwSwitch();">ƾ ֤ �� ��</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display: inline;">
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/voucherList.spr?_flag=edit&_status[%3D]=editing')";		
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/actualize_plan.png" width="30" height="30" />
    						<br>
    						¼��ƾ֤
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/voucher/createVoucherFast.jsp')";		
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/actualize_plan.png" width="30" height="30" />
    						<br>
    						���¼��
    					</span>
    					<span class="drwItem"
	    					onclick="gotoUrl('<%=basePath%>/voucherList.spr?_flag=confirm&_status[%3D]=editing')";
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/work_projet.png" width="30" height="30" />
    						<br>
    						ƾ֤ȷ��
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/voucherList.spr?_flag=audit&_status[%3D]=auditing')";
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/work_examine.png" width="30" height="30" />
    						<br>
    						�������
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/voucherList.spr')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/charge_select.png" width="30" height="30" />
    						<br>
    						ƾ֤��ѯ
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/voucher/accountVoucher.jsp');" 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/due_emp.png" width="30" height="30"/>
    						<br>
    						ƾ֤����
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/voucher/listVoucherBase.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/ass_stat.png" width="30" height="30" />
    						<br>
    						������ƾ֤��ѯ
    					</span>
    				</div>
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="1" onclick="drwSwitch();">�� �� �� ѯ</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display: none;">
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/report/queryAccountBook.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/outbox.png" width="30" height="30" />
    						<br>
    						�˲���ѯ
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/report/queryDailyBalance.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/ass_stat.png" width="30" height="30" />
    						<br>
    						�����ѯ
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/report/reportDailyBalance.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/ass_stat.png" width="30" height="30" />
    						<br>
    						�����ձ���
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/report/queryLedger.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/repertory.png" width="30" height="30" />
    						<br>
    						������
    					</span>
    				</div>
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="2" onclick="drwSwitch();">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display: none;">
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/tradeuserList.spr')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/application.png" width="30" height="30" />
    						<br>
    						�ͻ�����
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/reportFirmBalance.spr');"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/organise_fabric.png" width="30" height="30" />
    						<br>
    						�ͻ���ǰ�ʽ����
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/report/queryClientLedger.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/Announcement.png" width="30" height="30" />
    						<br>
    						�ͻ����˵�
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/report/queryClientLedgerAll.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/Announcement.png" width="30" height="30" />
    						<br>
    						�ͻ����˺ϼ�
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/report/queryFundflow.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/Announcement.png" width="30" height="30" />
    						<br>
    						�ͻ��ʽ���ˮ
    					</span>
    					<span class="drwItem"
	    				onclick="gotoUrl('<%=basePath%>/report/queryAccountLedger.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/Announcement.png" width="30" height="30" />
    						<br>
    						��Ŀ����
    					</span>
    				</div>
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="3" onclick="drwSwitch();">�� �� ά ��</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display:none;">
    					<span class="drwItem"
	    							onclick="gotoUrl('<%=basePath%>/voucherSummaryList.spr')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/lose.png" width="30" height="30" />
    						<br>
    						ժҪά��
    					</span>
    					<span class="drwItem"
	    							onclick="gotoUrl('<%=basePath%>/accountList.spr?_flag=edit')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/sort_setting.png" width="30" height="30" />
    						<br>
    						��Ŀά��
    					</span>
    					<span class="drwItem"
	    							onclick="gotoUrl('<%=basePath%>/report/queryLog.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/flow_setting.png" width="30" height="30" />
    						<br>
    						�鿴��־
    					</span>
    					<span class="drwItem"
	    							onclick="gotoUrl('<%=basePath%>/voucherChannelList.spr')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/flow_setting.png" width="30" height="30" />
    						<br>
    						����ͨ��
    					</span>
    					
    					<!-- <span class="drwItem"
	    							onclick="gotoUrl('<%=basePath%>/accountList.spr?_flag=firm')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/flow_setting.png" width="30" height="30" />
    						<br>
    						ͬ����Ŀ
    					</span> -->
    					<!-- <span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&(roleAudit)}'>
	    							onclick="gotoUrl('<%=basePath%>/report/redoBalance.jsp')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/gift_2.png" width="30" height="30" />
    						<br>
    						��������
    					</span> -->
    					
    					<!-- <span class="drwItem"
	    					<c:choose>
	    						<c:when test='${logonUser!=null&&roleAdmin}'>
	    							onclick="gotoUrl('<%=basePath%>/userList.spr')"; 
	    						</c:when>
	    						<c:otherwise>
	    							style="color:gray;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)"
	    						</c:otherwise>
	     					</c:choose>
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/application.png" width="30" height="30" />
    						<br>
    						�û�����
    					</span> -->
    				</div>
    				<div id="div_drw_Panel" align="center" class="div_drwPanel">
    					<span class="drwPanel" curDrwNo="4" onclick="drwSwitch();">�� �� �� �� ��</span>
    				</div>
    				<div id="div_drw_Content" align="center" class="div_drwContent" style="display:none;">
    				    <span class="drwItem"
	    							onclick="gotoUrl('<%=basePath.replaceAll("finance","member")%>/systemList.spr')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/flow_setting.png" width="30" height="30" />
    						<br>
    						ϵͳ����
    					</span>
    				    <span class="drwItem"
	    							onclick="gotoUrl('<%=basePath.replaceAll("finance","member")%>/firmZoneList.spr')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/lose.png" width="30" height="30" />
    						<br>
    						���������趨
    					</span>
    					<span class="drwItem"
	    							onclick="gotoUrl('<%=basePath.replaceAll("finance","member")%>/firmIndustryList.spr')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/lose.png" width="30" height="30" />
    						<br>
    						��ҵ�����趨
    					</span>
    					<span class="drwItem"
	    							onclick="gotoUrl('<%=basePath.replaceAll("finance","member")%>/firmList.spr')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/lose.png" width="30" height="30" />
    						<br>
    						�������б�
    					</span>
    					<span class="drwItem"
	    							onclick="gotoUrl('<%=basePath.replaceAll("finance","member")%>/firmTraderList.spr')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/lose.png" width="30" height="30" />
    						<br>
    						����Ա�б�
    					</span>
    					<span class="drwItem"
	    							onclick="gotoUrl('<%=basePath.replaceAll("finance","timebargain")%>/printreport/bargainQuery.jsp')"; 
    					 >
    						<img src="<%=basePath + "/skin/" + userSkinName%>/ico/lose.png" width="30" height="30" />
    						<br>
    						����Ա�б�
    					</span>
    				</div>
				</div>
        		<div align="left" style="width: 135px; height: 14px;">
        			<img id="img_drwPuller" src="<%=basePath + "/skin/" + userSkinName%>/images/drawer_End.gif" width="135" height="14" />
        		</div>
			</div>
		</div>
	<span id="span_curstatus" curNo="0" style="display:none;"></span>
	</body>
</html>