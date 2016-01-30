
<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table border="0" width="80%" align="center">
	<tr height="30"></tr>
	<tr>
		<td>
			<div class="div_cxtj">
				<div class="div_cxtjL"></div>
				<div class="div_cxtjC">
					基本信息
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<div class="div_tj">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<tr height="20">
						<td align="right">
							公告标题：
						</td>
						<td>
							${entity.title }
						</td>
						<td align="left" height="40" colspan="3">
							<div id="id_vTip" class=""></div>
						</td>
					</tr>
					</tr>
					<tr>
						<tr>
							<td align="right">
								公告内容：
							</td>
							<td>
								<textarea rows="6" cols="40" readonly="readonly" name="entity.content">${entity.content }</textarea>
							</td>
							<td align="left" height="40" colspan="3">
								<div id="name_vTip" class=""></div>
							</td>
						</tr>
						<tr>
							<td align="right">
								管理员：
							</td>
							<td>
								${entity.userId}
							</td>
							<td align="left" height="40">
								<div id="fullName_vTip" class=""></div>
							</td>
						</tr>
						<tr>
							<td align="right">
								创建时间：
							</td>
							<td>
								<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>								
							</td>
							<td align="left" height="40">
								<div id="fullName_vTip" class=""></div>
							</td>
						</tr>
				</table>
			</div>
		</td>
	</tr>
</table>