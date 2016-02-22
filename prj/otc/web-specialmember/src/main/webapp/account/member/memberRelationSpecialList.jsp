<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>

	<body leftmargin="0" topmargin="0">
	<form action="" name="frm" method="post"  targetType="hidden">
			<fieldset>
				<legend>
					<b>关联默认特别会员</b>
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td colspan="2" align="center" style="font-size: 14;color: red">选择关联的特别会员</td>
						</tr>
						<tr>
							<td>&nbsp;&nbsp;</td></tr>
						<c:forEach items="${specialMemberList}" var="result">
						<tr>
								<td align="right" width="50%">
								<input type="checkbox" name="checkIds"
												<c:if test="${compMember.sm_firmId==result.id}"> checked="checked"</c:if>
								  id="checkSpecialMemberId" value="${result.id }"> 
						&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="left" width="50%">
									${result.name }
								</td>
						</tr>	
									
					</c:forEach>
				</table>
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
								<tr>
									<td width="50%" align="center">
										<button value="ContactSpecialMember" onClick="contactSpecialMember('${memberId}')">
											关联
										</button>
									</td>

									<td width="50%" align="center">
										<button onClick="window.close()">
											返 回
										</button>
									</td>
								</tr>
							</table>
			</fieldset>
			</form>
		</body>
	</html>
	<script type="text/javascript">
		function contactSpecialMember(id){
			var checkBox=document.getElementsByName("checkIds");
			var length=0;
			for(var i=0;i < checkBox.length;i++ )
			{
				if( checkBox[i].checked == true )
					{
					length+=1;			
				}
			}
			if(length==0){
				alert('请至少选择一个默认的特别会员');
				return false;
			}else if(length>1){
				alert('请选择一个默认的特别会员');
				return false;
			}
			frm.action="${basePath}/account/memberRelation/update.action?memberId="+id;
			frm.submit();
		}	
	</script>
	<%@ include file="/public/footInc.jsp"%>