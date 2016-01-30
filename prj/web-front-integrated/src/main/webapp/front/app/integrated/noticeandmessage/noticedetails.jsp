<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>公告详情页面</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<!-------------------------Body Begin------------------------->
		<div class="main">
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示:
				</div>
				<div class="content">当前公告编号【${notice.noticeId}】。此页面展示公告详情。</div>
				
			</div>
			<div class="form margin_10b">
			<div class="column1">
				<h1>公告详情:</h1>
			</div>
			<table cellspacing="0" cellpadding="0" class="content" width="90%">
				<tr valign="bottom">
					<th align="right" scope="row" width="15%">公告标题：</th>
					<td align="left">${notice.title }&nbsp;</td>
				</tr>
				<tr>
					<th scope="row" align="right"> 公告内容：</th>
					<td align="left">
						<textarea rows="6" cols="97" readonly="readonly" style="color:#737373; width:75%;"
								name="notice.content">${notice.content }</textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" align="right"> 发布时间：</th>
					<td align="left">
							<fmt:formatDate value="${notice.createTime}"
								pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
			</table>
			<div class="page text_center">
				<button class="btbg"  onClick= window.close(); >
								关闭
				</button>
			</div>
			</div>
		</div>
		<!-------------------------Body End------------------------->
	</body>
</html>