<%@ page pageEncoding="GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    $("#refreshTime").focus();
}
function save_onclick()
{
	if (confirm("您确定要提交吗？"))
    {	
    	if ($("#refreshTime").attr("value") == "") 
    	{
    		alert("刷新时间不能为空！");
    		$("#refreshTime").focus();
    		return false;
    	}
    	else if($("#refreshTime").attr("value")<3)
    	{
    		alert("刷新时间不能小于3！");
    		$("#refreshTime").focus();
    		return false;
    	}
    	if ($("#pageSize").attr("value") == "") 
    	{
    		alert("每页显示的记录数不能为空！");
    		$("#pageSize").focus();
    		return false;
    	}	    
	    var updateDataUrl = $("#save").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	}
}
</script>
	</head>
	<body onLoad="window_onload()" >
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<form id="frm" action="" method="POST" >
						<fieldset class="pickList">
							<legend class="common">
								<b>
									风险监控参数
								</b>
							</legend>

							<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="2">
								<!-- 基本信息 -->
								<tr class="common">
									<td colspan="4">
										<span id="paraminfo">
											<table cellSpacing="0" cellPadding="0" width="300" border="0" align="center" >
												<tr>
													<td align="right" width="150">
														监控刷新时间：
													</td>
													<td align="left" width="150">
														<input type="text" id="refreshTime"  name="refreshTime" maxlength="5" style="ime-mode:disabled" value="${refreshTime }"  size="10" />
													</td>
												</tr>
												<tr>
													<td align="right" width="150">
														风险监控每页显示记录数：
													</td>
													<td align="left" width="150">
														<input type="text" id="pageSize" name="pageSize" maxlength="5" style="ime-mode:disabled" value="${pageSize }" size="10" />
													</td>
												</tr>
											</table>
										</span>
									</td>
								</tr>

								<tr>
									<td colspan="4" height="3">
									</td>
								</tr>
								<tr>
									<td colspan="4" align="center">
										<rightButton:rightButton name="提交" onclick="save_onclick()" className="" action="${basePath}/timebargain/tradeMonitor/monitorParameterSave.action" id="save"></rightButton:rightButton>
									</td>
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
