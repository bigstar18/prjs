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
					<b>����Ĭ���ر��Ա</b>
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td colspan="2" align="center" style="font-size: 14;color: red">ѡ��������ر��Ա</td>
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
											����
										</button>
									</td>

									<td width="50%" align="center">
										<button onClick="window.close()">
											�� ��
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
				alert('������ѡ��һ��Ĭ�ϵ��ر��Ա');
				return false;
			}else if(length>1){
				alert('��ѡ��һ��Ĭ�ϵ��ر��Ա');
				return false;
			}
			frm.action="${basePath}/account/memberRelation/update.action?memberId="+id;
			frm.submit();
		}	
	</script>
	<%@ include file="/public/footInc.jsp"%>