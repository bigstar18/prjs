<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<base target="_self">
<head>
	<title>������ɫ</title>
	<meta http-equiv="Pragma" content="no-cache">
</head>
<body style="overflow-y: hidden"
	onload="CheckSelectAll('ck','checkAll')">
	<c:set var="length" value="0"></c:set>
	<div
		style="position: absolute; top: 2; z-index: 1; overflow: auto; height: 360px;overflow-x: hidden;">
		<table border="0" width="100%" align="center">
			<tr>
				<td align="center">
					<form name="frm"
						action="${basePath}/user/updateRelatedRight.action?entity.userId=${user.userId}"
						method="post">
						<table width="90%">
							<tr height="1px">
								<td>
									<div class="warning">
										<div class="content">
											��ܰ��ʾ :����Ա��${user.name}��������ɫ
										</div>
									</div>
								</td>
							</tr>
						</table>
						<table id="tb" border="0" cellspacing="0" cellpadding="0"
							width="90%"
							style="word-break: break-all; table-layout: fixed; border-right: 1px solid #d9d9d9; border-top: 1px solid #d9d9d9; border-bottom: 1px solid #d9d9d9;">
							<div class="div_cxtj">
								<div class="div_cxtjL"></div>
								<div class="div_cxtjC">
									��������Ա��ɫ
								</div>
								<div class="div_cxtjR"></div>
							</div>
							<tr height="25" align=center>
								<td width="27%" class="panel_tHead_MB" align=center>
									<input class=checkbox_1 type="checkbox" id="checkAll"
										onclick="relatedSelect(tb,'ck')">
									������ɫ
								</td>
								<td width="27%" class="panel_tHead_MB" align=center>
									��ɫ����
								</td>
								<td width="46%" class="panel_tHead_MB" align=center id="titTd">
									��ɫ����
								</td>
							</tr>
							<c:forEach items="${roleList }" var="role">
								<c:set var="length" value="${length+1}"></c:set>
								<tr align=center>
									<td class="tab_td_border" align=center>
										<c:set var="flag" value="false"></c:set>
											<c:forEach items="${user.roleSet}" var="roleSe">
												<c:if test="${roleSe.id==role.id}">
													<c:set var="flag" value="true"></c:set>
												</c:if>
											</c:forEach>
										<input name="ck" type="checkbox" id="ck_${role.id}"
											value="${role.id }" <c:if test="${flag=='true'}">checked=true</c:if>
											onclick="relatedSelectOne('${role.id }',true)"/>
									</td>
									<td class="tab_td_border" align=left style="font-size: 12px;">
										${role.name }&nbsp;
									</td>
									<td class="tab_td_border" align=left style="font-size: 12px;"
										id="showTd_${length}">
										${role.description }&nbsp;
									</td>
								</tr>
							</c:forEach>

						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
	<div class="tab_pad">
		<table border="0" cellspacing="0" cellpadding="0" width="100%"
			align="center">
			<tr height="35">
				<td align="right" id="tdId">
					<rightButton:rightButton name="����" onclick="relatedRight()"
						className="btn_sec" action="/user/updateRelatedRight.action"
						id="related"></rightButton:rightButton>
					&nbsp;&nbsp;&nbsp;
					<button class="btn_sec" onClick="window.close()">
						�ر�
					</button>
					&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</div>
</body>
<SCRIPT LANGUAGE="JavaScript">
var userId='${user.userId}';

for(var i=1;i<=${length};i++){
	var showTds=document.getElementById('showTd_'+i);
	var titTd=document.getElementById('titTd');
	//tableTds.className='tab_td_borderightleft';
	showTds.style.width='60%';
	titTd.style.width='60%';
}
var reg =/^\w*DEFAULT_+\w*$/; 
var id='${user.type}';
var regSuper=/^\w*_SUPER_+\w*$/;
if(id.match(regSuper)!=null){
	var checkAll=document.getElementById("checkAll");
	checkAll.checked=true;
	var cks=document.getElementsByName('ck');
	for(var i=0;i<cks.length;i++){
		cks[i].checked=true;
	}
}
if(id.match(reg)!=null||userId=='${sessionScope.CurrentUser.userId}'){
	var tdId=document.getElementById("tdId");
	tdId.style.display="none";
	var checkAll=document.getElementById("checkAll");
	checkAll.disabled='disabled';
	var cks=document.getElementsByName('ck');
	for(var i=0;i<cks.length;i++){
		cks[i].disabled='disabled';
	}
}
if(${sessionScope.IsSuperAdmin}){
	for(var i=1;i<=${length};i++){
	var showTdTds=document.getElementById('showTd_'+i);
	var titTd=document.getElementById('titTd');
	showTdTds.style.width='40%';
	titTd.style.width='40%';
	}
}
	//����Ա����Ȩ��
	function relatedRight(){
		if(userConfirm()){
			sign = true;
		  }else{
			return false;
		  }
	//��ֹ�ظ��ύ
	if(sign){
			frm.submit();
		}
	}
//��һ������еĶ�ѡ��ִ��ȫѡ/ȫ��ѡ�Ĳ���
//tblObj:������
//childName:checkbox�����ID
function relatedSelect( tblObj , childName )
{
  var objCheck = event.srcElement;
  var collCheck;
  
  if ( tblObj != null )
  	var collCheck = document.getElementsByName(childName);
  else
  	var collCheck = document.getElementsByName(childName);
  	
  if(collCheck)
  {
	  if(!collCheck.length)
	  	collCheck.checked = (objCheck.checked==true)? true:false;
	  else
	  {
	  	for(var i=0;i < collCheck.length;i++ )
		{
			collCheck[i].checked = (objCheck.checked==true)? true:false;
		}
	  }  
  }
  else
  {
  	objCheck.disabled = true;
  }
}

//��һ������еĶ�ѡ��ִ��ȫѡ/ȫ��ѡ�Ĳ���
//tblObj:������
//childName:checkbox�����ID
function relatedSelectOne( cellId , flag )
{	
	var checkAll=document.getElementById("checkAll");
			
	var checkSrc=document.getElementById("ck_" + cellId);
	if(flag)
	{		
		if(!checkSrc.checked)
		{
			checkAll.checked = false;
		}
		else
		{
			CheckSelectAll("ck","checkAll");
		}
	}
		
}

function CheckSelectAll(name,taget)
{	
	var header=document.getElementById(taget);
	var checkAll=document.getElementsByName(name);
	var flag = true;
	for(var i=0;i < checkAll.length;i++ )
	{
		if(!checkAll[i].checked)
		{
			flag = false;
			break;
		}
	}
	if(flag)
	{
		header.checked = true;
	}
}
 function userConfirm(){
   if(affirm("��ȷʵҪ������Щ������"))
   { 
     return true;
   }else{
     return false;
   }
 }
</SCRIPT>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>