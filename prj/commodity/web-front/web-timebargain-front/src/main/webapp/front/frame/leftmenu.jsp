<%@ page trimDirectiveWhitespaces="true" contentType="text/html;charset=GBK"%>
<%@ include file="/front/public/includefiles/taglib.jsp" %>
<%@ include file="/front/public/includefiles/path.jsp" %>
<%
	request.setAttribute("modelContextMap",Global.modelContextMap);
	request.setAttribute("selfModuleID",Global.getSelfModuleID());
%>
<script>
	var jastOpenMenu="";<%/* ��¼���һ�ε����˵���ID�� */%>
	function selectMenu(menuID){
		if(menuID){
			$("#"+menuID).click();
		}else if(jastOpenMenu){
			$("#"+jastOpenMenu).click();
		}
	}
	jQuery(document).ready(function(){
		$(".menu_1").click(function(){<%/* һ���˵�����¼� */%>
			if($(this).attr("class")=='menu_1'){
				$(this).attr("class","menu_2");
			}else{
				$(this).attr("class","menu_1");
			}
			$("#"+$(this).attr("id")+"Div").toggle("fast");
			return false;
		});
		$(".menu_3").click(function(){<%/* �����˵�����¼� */%>
			jastOpenMenu=$(this).attr("id");
			mymenu.close("openUpdateMenuDiv");
			$(this).parent().show();<%/* �򿪱������˵��ĸ��˵� */%>
			$(".menu_4").attr("class","menu_3");<%/*����ǰѡ�еĶ����˵���ʽ��ԭ*/%>
			$(this).attr("class","menu_4");<%/*�޸ĵ�ǰ��������˵�����ʽ*/%>
			$("#mainfrm").attr("action",$(this).attr("action"));
			$("#mainfrm").submit();
			return false;
		});
		$("#openUpdateMenuDiv").click(function(){<%/* �������ҵĲ˵�div */%>
			var html = $("#updateMenuDiv").html();
			mymenu.show($(this).attr("id"), html);
			return false;
		});
		$(".menu_1")[0].click();<%/* �����׸�һ���˵��� */%>
	});
</script>
<form id="mainfrm" name="mainfrm" target="main" method="post"></form>
<%/* չʾ��Ϣ */%>
<div class="left_titlebor<c:if test="${empty modelContextMap[selfModuleID]['homepageAction']}">2</c:if>" style="height: 100%;width:100%;z-index:10;">
	<div class="title">��������</div>
	<!-- <div class="titler">
	<c:if test="${not empty modelContextMap[selfModuleID]['welcomePage']}">
	<a href="${basePath}${modelContextMap[selfModuleID]['welcomePage']}" target="_top">������ҳ</a>
	</c:if>
	</div> -->
	<!-- <div class="main">
	<c:if test="${not empty modelContextMap[selfModuleID]['homepageAction']}">
	<a href="${basePath}${modelContextMap[selfModuleID]['homepageAction']}}" target="main">ƽ̨��ҳ</a>
	</c:if>
	</div> -->
	<div class="main"><a id="openUpdateMenuDiv" href="#">�ҵĲ˵�</a></div>
	<div class="clear"></div>
	<%/* �ҵĲ˵���Ϣչʾ */%>
	<c:if test="${not empty mymenu}">
	<div class="menu_1" id="mymenu">�ҵĲ˵�</div>
	<div id="mymenuDiv" style="display: none">
		<c:forEach var="menu" items="${mymenu}">
		<div id="f${menu.value.id}" class="menu_3" action="${basePath}${menu.value.visiturl}">${menu.value.name}</div>
		</c:forEach>
	</div>
	</c:if>
	<%/* ����ѭ���˵�չʾ */%>
	<c:forEach var="mainMenu" items="${HaveRightMenu.childMenuSet}">
	<c:if test="${not empty mainMenu.childMenuSet}">
	<div class="menu_1" id="${mainMenu.id}">${mainMenu.name}</div>
	<div id="${mainMenu.id}Div" style="display: none">
		<c:forEach var="secondMenu" items="${mainMenu.childMenuSet}">
		<div class="menu_3" id="${secondMenu.name}" action="${basePath}${secondMenu.url}">${secondMenu.name}</div>
		</c:forEach>
	</div>
	</c:if>
	</c:forEach>
	<a href="mailto: <%=Global.getMarketInfoMap().get("marketEmail") %>"><div class="left_ad"></div></a>
</div>
<%/* ���ز���Ϣ */%>
<div style="display: none;">
	<iframe name="hiddenframe" width=0 height=0 application='yes'></iframe>
	<div id="updateMenuDiv">
		<form id="changeMymenuFrm" action="${basePath}/menu/changemymenu.action" target="hiddenframe" method="post">
			<table width="600" border="0" cellspacing="10" cellpadding="0" bgcolor="#FFF">
				<tr>
					<td height="30" colspan="5">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="3" height="30" class="font_14B borderB">���ó��ò˵�</td>
								<td class="borderB">&nbsp;</td>
							</tr>
							<tr>
								<td height="30" colspan="4" bgcolor="#F2F2F2">
									&nbsp;&nbsp;&nbsp;&nbsp;�ʵ�չʾ���Ĳ˵����������Ĳ�������
								</td>
							</tr>
							<tr valign="top">
						<c:set var="status" value="0"></c:set>
						<c:forEach var="mainMenu" items="${HaveRightMenu.childMenuSet}">
							<c:if test="${status != 0 && status%4==0}">
							</tr><tr valign="top">
							</c:if>
								<c:set var="status" value="${status+1}"></c:set>
								<td width="25%" height="30" valign="top">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td>
												<strong>${mainMenu.name}</strong>
											</td>
										</tr>
										<c:forEach var="childMenu" items="${mainMenu.childMenuSet}">
											<tr>
												<td>
													<c:set var="menuid" value="${childMenu.id}"></c:set>
													<c:if test="${not empty mymenu[childMenu.id]}">
														<input type="checkbox" checked="checked" name="mymenu"
															value="${childMenu.id}" />${childMenu.name}
									</c:if>
													<c:if test="${empty mymenu[childMenu.id]}">
														<input type="checkbox" name="mymenu"
															value="${childMenu.id}" />${childMenu.name}
									</c:if>
												</td>
											</tr>
										</c:forEach>
									</table>
								</td>
								</c:forEach>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="borderT">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="submit" id="buttonMenu" value="�ύ" class="button"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset"  value="����" class="button"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>