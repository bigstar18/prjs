<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self"/>
		<title>����Ȩ��</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<style>
		.cls
		{
			display:none;
		}
		.opn
		{
			display:;
		}
		</style>
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});
			});
		</script>
	</head>
<body>
	<form id="frm" method="post" >
		<div class="div_cx">
			<table border="0" width="100%" align="center">	
				<tr>
					<td>
						<table border="0" width="100%" align="center">
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">
											���ü�����Ȩ��
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
										<div>
								          <table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
										       
										     <tr>
												<td align="left">														
													�����̱�ţ�${brokerId}
												</td>																								 																		 							
												<input type="hidden" id="brokerId" name="brokerId" value="${brokerId}" />								
										     </tr>
									         <tr height="35">
												<td align="left" style="font-size: 12px">
												<!-- ģ��Ȩ�� -->
												<c:forEach items="${HaveRightMenu.childRightSet }" var="moduleRight">
													<div id=div_ck class=opn>&nbsp;&nbsp;
													&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="hidden" name="ck" onclick="selectCks('${moduleRight.id }');setParents('${moduleRight.parentId }');" value="${moduleRight.id }">
											        <c:set var="parentMenuMark" value="false"/>
											        <c:forEach items="${rightList }" var="map">
											        	<c:if test="${map.ID==moduleRight.id}">
											        		<c:set var="parentMenuMark" value="true"/>
											        	</c:if>
											        </c:forEach>
											        <c:choose>
														<c:when test="${parentMenuMark }">
															<span style="font-weight: bold;font-size: 14;" onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
														</c:when>
														<c:otherwise>
															<span style="font-weight: bold;font-size: 14;" onclick="clkDiv('${moduleRight.id }')">${moduleRight.name }</span>
														</c:otherwise>
													</c:choose>
													<input type=hidden name="pid" value="${moduleRight.parentId }">
													<input type="hidden" name="div_st" value="1"><br></div>
													<!-- һ���˵�Ȩ�� -->
													<c:forEach items="${moduleRight.childRightSet }" var="menuRight">
														<div id=div_ck class=opn>&nbsp;&nbsp;
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														 <c:set var="parentMenuMark1" value="false"/>
												        <c:forEach items="${rightList }" var="map">
												        	<c:if test="${map.ID==menuRight.id}">
												        		<c:set var="parentMenuMark1" value="true"/>
												        	</c:if>
												        </c:forEach>
												        <c:choose>
															<c:when test="${parentMenuMark1 }">
																<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${menuRight.id}');setParents('${menuRight.parentId }');" value="${menuRight.id }">
																<span style="font-weight: bold;" onclick="clkDiv('${menuRight.id }')">${menuRight.name }</span>
															</c:when>
															<c:otherwise>
																<input type="checkbox" name="ck" onclick="selectCks('${menuRight.id }');setParents('${menuRight.parentId }');" value="${menuRight.id }">
																<span style="font-weight: bold" onclick="clkDiv('${menuRight.id }')">${menuRight.name }</span>
															</c:otherwise>
														</c:choose>
														<input type=hidden name="pid" value="${menuRight.parentId }">
														<input type="hidden" name="div_st" value="1"><br></div>
													
														<!-- �����˵�Ȩ�� -->
														<c:forEach items="${menuRight.childRightSet }" var="childMenuRight">
															<c:set var="roleDispatchChildMenuMark" value="false"/>
															<c:forEach items="${rightList }" var="map">
																<c:if test="${ map.ID==childMenuRight.id }">
																	<c:set var="roleDispatchChildMenuMark" value="true"/>
																</c:if>
															</c:forEach>
															<div id=div_ck class=opn>&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<c:choose>
																<c:when test="${roleDispatchChildMenuMark }">
																	<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${childMenuRight.id }');setParents('${childMenuRight.parentId }');" value="${childMenuRight.id }">
																	<span style="" onclick="clkDiv('${childMenuRight.id }')">${childMenuRight.name }</span>
																</c:when>
																<c:otherwise>
																	<input type="checkbox"  name="ck" onclick="selectCks('${childMenuRight.id }');setParents('${childMenuRight.parentId }');" value="${childMenuRight.id }">
																	<span onclick="clkDiv('${childMenuRight.id }')">${childMenuRight.name }</span>
																</c:otherwise>
															</c:choose>
															
															<input type=hidden name="pid" value="${childMenuRight.parentId }">
															<input type="hidden" name="div_st" value="1"><br></div>
															<!-- �����˵�Ȩ�� -->
															<c:forEach items="${childMenuRight.childRightSet }" var="tChildMenuRight">
																<c:set var="roleDispatchChildMenuMark1" value="false"/>
																<c:forEach items="${rightList }" var="map">
																	<c:if test="${ map.ID==tChildMenuRight.id }">
																		<c:set var="roleDispatchChildMenuMark1" value="true"/>
																	</c:if>
																</c:forEach>
																<div id=div_ck class=opn>&nbsp;&nbsp;
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<c:choose>
																	<c:when test="${roleDispatchChildMenuMark1 }">
																		<input type="checkbox"  name="ck" checked="checked" onclick="selectCks('${tChildMenuRight.id }');setParents('${tChildMenuRight.parentId }');" value="${tChildMenuRight.id }">
																		<span style="" onclick="clkDiv('${tChildMenuRight.id }')">${tChildMenuRight.name }</span>
																	</c:when>
																	<c:otherwise>
																		<input type="checkbox"  name="ck" onclick="selectCks('${tChildMenuRight.id }');setParents('${tChildMenuRight.parentId }');" value="${tChildMenuRight.id }">
																		<span onclick="clkDiv('${tChildMenuRight.id }')">${tChildMenuRight.name }</span>
																	</c:otherwise>
																</c:choose>
																
																<input type=hidden name="pid" value="${tChildMenuRight.parentId }">
																<input type="hidden" name="div_st" value="1"><br></div>
															</c:forEach>
														</c:forEach>
													</c:forEach>
												</c:forEach>
				                            </td>
				                         </tr>
							          </table>
								   </div>
								</td>
							</tr>
					   </table>
				  </td>
			   </tr>
		  </table>
	</div>
		<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
			<tr>
				<td align="center">
					<rightButton:rightButton name="�ύ" onclick="" className="btn_sec" action="/broker/brokerManagement/updateBrokerRight.action" id="update"></rightButton:rightButton>
					&nbsp;&nbsp;
					<button class="btn_sec" onClick="window.close();">����</button>
				</td>
			</tr>
		</table>
	</form>
</body>
<SCRIPT LANGUAGE="JavaScript">

var cks = document.all("ck");//��¼����Ȩ��id
var pids = document.all("pid");//��¼��Ȩ��id
var div_cks = document.all("div_ck");//��ʾ��Ȩ�޵� ��
var div_sts = document.all("div_st");

/*�����ѡ�򴥷��¼�*/
function selectCks(id)
{

	if(isLastNode(id))
	{
		return;
	}
	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			cks[i].checked = event.srcElement.checked;
			selectCks(cks[i].value);
		}
	}
}
/*�������  ������ر����������ڵĲ� */
function clkDiv(id)
{
	for(var i =0;i < cks.length;i++)
	{
		if(cks[i].value == id)
		{
			if(div_sts[i].value == 0)
			{
				div_sts[i].value = 1;
				
				if(isRootNode(id))
				{
					openDiv1(id);
				}
				else
				{
					openDiv(id);
				}		
				
				break;
			}
			else
			{
				div_sts[i].value = 0;
				clsDiv(id);
				break;
			}			
		}
	}
}
/*�򿪲� */
function openDiv(id)
{
	if(isLastNode(id))
	{
		return;
	}

	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			div_cks[i].className = "opn";
			div_sts[i].value = 1;
			openDiv(cks[i].value);
		}
	}
}

function openDiv1(id)
{		
	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			div_cks[i].className = "opn";
			div_sts[i].value = 0;
		}
	}
}

function clsDiv(id)
{
	if(isLastNode(id))
	{
		return;
	}
	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			div_cks[i].className = "cls";
			div_sts[i].value = 0;
			clsDiv(cks[i].value);
		}
	}
}

function setParents(parentid)
{
	
	
	for(var i =0;i < cks.length;i++)
	{
		if(cks[i].value == parentid)
		{
			if(event.srcElement.checked) cks[i].checked = event.srcElement.checked;
			setParents(pids[i].value);
		}
	}
	if(isRootNode(parentid))
	{
		return;
	}
}

function isRootNode(id)
{
	for(var i =0;i < cks.length;i++)
	{
		if(cks[i].value == id && pids[i].value == -1)
		{			
			return true;
		}
	}
	return false;
}
//isLastNode ���ڲ鿴�Ƿ�����ײ�Ȩ�ޣ��Ƿ���true���񷵻�false
function isLastNode(id)
{
	for(var i =0;i < cks.length;i++)
	{
		if(pids[i].value == id)
		{
			return false;
		}
	}
	return true;
}

//-->
</SCRIPT>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>