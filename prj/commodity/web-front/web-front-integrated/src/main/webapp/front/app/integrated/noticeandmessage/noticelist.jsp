<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>公告列表页面</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/integrated/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
			jQuery(document).ready(function(){
				//设置表单验证
				$("#frm").validationEngine("attach");
				$("#view2").click(function(){
					if ($("#frm").validationEngine('validate')) {
						$("#frm").submit();
					}
				});
				$("#view").click(function(){
					if ($("#frm").validationEngine('validate')) {
						frm.submit();
					}
				});
			});

			function checkBeginDate(){
				var beginTime=document.getElementById("beginTime").value;
				var endTime=document.getElementById("endTime").value;
				if(!Util.CompareDate(beginTime,endTime)){
						return "*开始日期不能大于结束日期";
				}else{
					jQuery('#endTime').validationEngine('hide')
				}
			}
			
			function checkEndDate(){
					var beginTime=document.getElementById("beginTime").value;
					var endTime=document.getElementById("endTime").value;
					if(!Util.CompareDate(beginTime,endTime)){
							return "*结束日期不能小于开始日期";
					}else{
						jQuery('#beginTime').validationEngine('hide')
					}
				}
			function noticeDetail(id){
				var url = "${frontPath}/app/integrated/noticeandmessage/noticeDetails.action?noticeId=" + id;
				showDialog(url,"",650,450);
			}
		</script>
	</head>
	<body onload="getFocusID('title');">
<!-------------------------Body Begin------------------------->
<div class="main">
	<jsp:include page="/front/frame/current.jsp"></jsp:include>
	<div class="warning">
		<div class="title font_orange_14b">温馨提示 :</div>
		<div class="content">在此展示所有面向您的公告信息。</div>
	</div>
<div class="main_copy">
	<form id="frm" action="${frontPath}/app/integrated/noticeandmessage/noticeList.action" method="post">
		<div class="sort">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" width="250">
						<label>开始日期：
							<input id="beginTime" class="Wdate validate[custom[date],past[${today}],funcCall[checkBeginDate]] datepicker" name="${GNNT_}primary.createTime[>=][date]" value="${oldParams['primary.createTime[>=][date]']}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
						</label>
					</td>
					<td>
						<label>结束日期：
							<input id="endTime"  class="Wdate validate[custom[date],past[${today}],funcCall[checkEndDate]] datepicker" name="${GNNT_}primary.createTime[<=][datetime]" value="${oldParams['primary.createTime[<=][datetime]']}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
						</label>
					</td>
					<td>
						<label>公告标题：
							<input id="title" maxLength="<fmt:message key="title_q" bundle="${proplength}" />" name="${GNNT_}primary.title[allLike]" value="${oldParams['primary.title[allLike]']}" class="validate[maxSize[32]]"/>
						</label>
					</td>
					<td width="110" height="30" align="center" valign="middle">
						<a href="#" id="view"><img src="${skinPath }/image/memberadmin/searchbt1.gif" width="93" height="23" border="0" /> </a>
					</td>
				</tr>
			</table>
		</div>
		<div class="content">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="tb_bg">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content" summary="进行中的交易">
								<tr class="font_b tr_bg">
									<td width="20%" align="center"><div class="ordercol" id="noticeId">公告编号</div></td>
									<td width="20%" align="center">公告标题</td>
									<td width="20%" align="center"><div class="ordercol" id="createTime">发布时间</div></td>
									<td width="20%" align="center">公告内容</td>
									<td width="20%" align="center">详情</td>
								</tr>
								<c:forEach var="notice" items="${pageInfo.result}">
								<tr>
									<td width="20%" align="center">${notice.noticeId}</td>
									<td class="maxsize" lang="25" align="center">${notice.title}</td>
									<td width="20%" align="center"><fmt:formatDate value="${notice.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
									<td class="maxsize" lang="40" align="center">${notice.content}</td>
									<td width="20%" align="center"><a href="#" onclick="noticeDetail(${notice.noticeId})">详情</a></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<jsp:include page="/front/frame/pageinfo.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div></div>
<!-------------------------Body End------------------------->
	</body>
</html>