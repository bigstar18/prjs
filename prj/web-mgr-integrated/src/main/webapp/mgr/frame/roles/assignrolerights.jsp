<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
 		<style>
 			.current{font-size:12px; color: orange; text-decoration: none;font-weight:bold;border: dashed 1px white;}
 			a:link{text-decoration: none;font-size:12px;color: #146aa5;font-weight:bold;border: dashed 1px white;}
 			a:visited{text-decoration: none;font-size:12px;border: dashed 1px white;}
 			a:hover{text-decoration: none;color: #146aa5;font-weight:bold;font-size:12px;border: dashed 1px white;}
            a:active {font-size:12px; color: orange; text-decoration: none;font-weight:bold;border: dashed 1px white;}
            .c1{ width:360px; height:auto;}
			.c1 .word{ width:320px; height:22px; line-height:22px; font-size:12px; font-weight:bold; color:#146aa5; float:left; background-color:#f7fcff; padding-left:5px;}
			.c1 .btn{ float:left; height:22px; width:35px; background-color:#f7fcff;cursor: pointer;}
			.c1 .more{ width:320px; padding-left:5px; height:auto; line-height:22px; font-size:12px; font-weight:bold; color:#146aa5; border-bottom:1px solid #c6e1f4; border-right:1px solid #c6e1f4; background:url(images/ico.gif) #f7fcff bottom right; background-repeat:no-repeat;}
			.textdiv{width:auto; float:left;white-space:nowrap;font-size:12px}
        </style>
		<title>���ý�ɫȨ��</title>
		<meta http-equiv="Pragma" content="no-cache">
		<link rel="stylesheet" href="${skinPath}/css/app/integrated/role.css" type="text/css"/>
		<script src="${publicPath }/js/jquery-1.6.min.js"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				var count=0;
				$(".more").hide();
				$(".btn").click(function(){
					$(".more").toggle("fast");
				  	count++;
					if(count %2 ==0){
					$(".btn").html('<img src="<%=skinPath%>/image/app/integrated_mgr/role/btn_1.gif" width="26" height="18" />');
					}else{
					$(".btn").html('<img src="<%=skinPath%>/image/app/integrated_mgr/role/btn_2.gif" width="26" height="18" />');
					}
				});
				
				$("#tradeModuleDiv label").click(function() {
					// ��ȡ��ǰ����İ�ť
					var $newList = $(this);
					// ��ȡ��Ҫ��ʾ��divID
					var listID = "div"+$newList.attr("rel")+"mo";
					if(document.getElementById(listID)){//���ģ���Ѿ�����
						// ��ȡ��ǰ������ʾ��divID
						var curList = "div"+$("#tradeModuleDiv label.current").attr("rel")+"mo";
						// ����ܹ��ҵ�������ʾ��div(��ֹ����)������
						if (curList && listID != curList) {
							// ȥ������ѡ��� current ��ʽ
							$("#tradeModuleDiv label.current").removeClass("current");
							// ������ǰѡ�е�div
				            $("#"+curList).fadeOut(300, function() {
								// չʾ��ǰ��div
								$("#"+listID).fadeIn();
								//���õ�ǰչʾ div ��ʽ
								$newList.addClass("current");
							});
						}
					}else{
						addModule($newList);
					}
					return false;
				});
				$("#defmo").click();
			});
			function addModuleShow($newList){
				var curList = "div"+$(document).find("#tradeModuleDiv label.current").attr("rel")+"mo";
				var listID = "div"+$newList.attr("rel")+"mo";
				if(document.getElementById(curList)){
					$("#tradeModuleDiv label").removeClass("current");
					$("#"+curList).fadeOut(0, function() {
						$("#"+listID).fadeIn();
						$newList.addClass("current");
					});
				}else{
					$("#"+listID).fadeIn();
					$newList.addClass("current");
				}
			}
			//�¼���ģ��
			function addModule($newList){
				var moduleId = $newList.attr("rel");
				var url = '${basePath}/role/updateRoleModuleRight.action?roleId=${role.id}&moduleId='+moduleId;
				url += "&t="+Math.random();
				var frame = document.getElementById("hiddenframe");
				frame.src=url;
				if (!/*@cc_on!@*/0) {//��IE�����
					frame.onload = function(){
						var w = frame.contentWindow.document;
						var html = w.getElementById("modulebase").innerHTML;
						$("#fmodulediv").append(html);
						addModuleShow($newList);
					};
				} else {//IE�����
					frame.onreadystatechange = function(){
						if (frame.readyState == "complete"){
							var w = frame.contentWindow.document;
							var html = w.getElementById("modulebase").innerHTML;
							$("#fmodulediv").append(html);
							addModuleShow($newList);
						}
					};
				}
			}
			//�����˵��ϲ�չ������¼�
			function rightdivClick(rightId){
				var chdiv = $("#ch"+rightId+"div");
				var html = "";
				if(chdiv.is(":visible")){
					html="<img src='<%=skinPath%>/image/app/integrated_mgr/role/add.gif'/>";
				}else{
					html="<img src='<%=skinPath%>/image/app/integrated_mgr/role/del.gif'/>";
				}
				$("#lab"+rightId).html(html);
				chdiv.toggle("fast");
			}
			//ѭ��ѡ�и���ѡ��
			function checkedfather(checked,fatherId){
				if(checked){
					var father = $("#"+fatherId);
					if(father){
						//�����ѡ���Ѿ�ѡ�У������
						if(father.attr('checked')==true){
						}else{
							//ѡ�и�ѡ��
							father.attr('checked',true);
							var alt = father.attr('alt');
							if(alt){
								checkedfather(true,alt);
							}
						}
					}
				}
			}
			//�����Ӹ�ѡ��ѡ��
			function uncheckchild(checked,divid){
				if(checked){//������˵�ѡ���ˣ���ѡ�����µ��Ӳ˵�
					$("#"+divid+" input").attr('checked',true);
				}else{//������˵�����ѡ�У����������Ӳ˵�ѡ��
					$("#"+divid+" input").attr('checked',false);
				}
			}
			//ִ�б���Ȩ�޲���
			function frmChk(){
				var sign=false;
				if(confirm("ȷ�ϱ���?")){
						sign=true;
				}
				if(sign){
					var a=document.getElementById('update').action;
					frm.action="<%=basePath %>"+a;
					frm.submit();
				}
			}
		</script>
	</head>
	<% int imgcont=0; %>
	<body style="overflow-y:hidden">
		<iframe id="hiddenframe" name="hiddenframe" widht="0" height="0" style="display:none;"></iframe>
		<form name="frm" method="post" targetType="hidden">
			<input type="hidden" id="roleId" name="roleId" value="${role.id}"/>
			<!-- ͷ����ܰ��ʾ -->
			<div class="warning"><div class="content">
			��ܰ��ʾ�����ý�ɫ&nbsp;${role.name}&nbsp;Ȩ��<br/>
			<!-- ����ģ�鰴ť -->
			<div id="tradeModuleDiv" style="position:absolute;z-index:10;" class="c1">
				<c:set var="defaultmo" value="true"/>
				<c:set var="modulevienum" value="0"/>
				<div class="word">
					<c:forEach var="module" items="${tradeModuleMap}" begin="0" end="3">
						<c:if test="${fn:length(rightMap[module.key])>0}">
						<div class="textdiv">
							<a href="#">
								<label 
									<c:if test="${defaultmo}">
										<c:set var="defaultmo" value="false"/>id="defmo"
									</c:if> 
										rel="${module.key}">&nbsp;${module.value.shortName}
								</label>
								<c:set var="modulevienum" value="${modulevienum+1}"/>
							</a>
							</div>
							</c:if>
					</c:forEach>
				</div>
				 <div class="btn">
					 <c:if test="${fn:length(tradeModuleMap) >4}">
					 	<img src="<%=skinPath%>/image/app/integrated_mgr/role/btn_1.gif" width="26" height="18" />
					 </c:if>
				 </div>
			<div class="more">
			<c:forEach var="module" items="${tradeModuleMap}" begin="4">
			<c:if test="${fn:length(rightMap[module.key])>0}">
			<div class="textdiv">
				<a href="#"><label <c:if test="${defaultmo}"><c:set var="defaultmo" value="false"/>id="defmo"</c:if> rel="${module.key}">&nbsp;${module.value.shortName}</label>
				<c:set var="modulevienum" value="${modulevienum+1}"/></a>
			</c:if>
			</div>
			</c:forEach>
			</div>
			</div>
			</div></div>
			<!-- ʵ�ʲ������� -->
			<div  class="tab_pad" style="overflow-x:hidden;position:absolute;top:80;z-index:1;overflow:auto;height:480px;width: 100%;">
				<!-- Ȩ�� div �ܿ� -->
				<div id="rightDiv" style="margin-right: 20;">
					<!-- ����ģ�鸸 div -->
					<div class="fmodulediv2" style="margin-left: 20;">
					<div id="fmodulediv" class="fmodulediv">
					</div>
					</div>
				</div>
			</div>
			<div class="cleard"></div>
			<div class="tab_pad" align="right">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<rightButton:rightButton name="�޸�" onclick="frmChk();" className="btn_sec" action="/role/updateRoleRight.action" id="update"></rightButton:rightButton>
				&nbsp;&nbsp;
				<button class="btn_sec" onClick=window.close();>�ر�</button>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>