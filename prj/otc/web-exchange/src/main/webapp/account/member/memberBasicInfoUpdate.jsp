<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>会员信息</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form action="${basePath}/account/memberInfo/update.action" name="frm" method="post" targetType="hidden">
		<input type="hidden" name="obj.memberType" id="memberTypeHidden" value="${obj.memberType}">
		<div>
			<table border="0" width="100%" align="center" >
				<tr>
					<td>
					   <div class="st_title">&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;会员信息</div>
						<%@include file="commonMemberTable.jsp" %>
					</td>
				</tr>
			</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" width="100%" cellpadding="0" border="0" align="center" >
						<tr>
						<td align="center">
							<rightButton:rightButton name="保存" onclick="updateMember()" className="btn_sec" id="update"></rightButton:rightButton>
						</td>
						<td align="center">
							<button  class="btn_sec" onClick="window.close()">关闭</button>
						</td>
					</tr>
						</table>
						</div>
		</form>
	</body>
</html>
<script type="text/javascript">
	document.getElementById('id').readOnly=true;
	document.getElementById('id').style.background="#bebebe";
	document.getElementById('memberType').disabled='disabled';
if('${obj.status}'!='C'){
	document.getElementById("memberName").readOnly=true;
	var papersType=document.getElementById("papersType").value;
	document.getElementById("papersType").disabled = "disabled";
	document.getElementById("papersName").readOnly=true;
	var collFrm = document.all.tags("form");
    if ( collFrm )
    {
        for( var i = 0 ; i < collFrm.length ; i++ )
        {
            var e= document.createElement("input");
	        e.setAttribute("type","hidden");
	        e.setAttribute("id","papersType");
	        e.setAttribute("name",'obj.papersType');
	        e.setAttribute("value",papersType);
	        collFrm[i].appendChild(e);
        }
        
    }
}
	function checkMemberId(){
		return true;
	}
	function updateMember(){
		if(!myblur("all"))
			{
			return false;
			}
		var papersType=document.getElementById("papersType").value;
		var id=document.getElementById("papersName").value;
		var memberNo=document.getElementById('id').value;
		checkAction.existMemberPapers(id,papersType,memberNo,function(isExist){
			if(isExist){
				alert('证件号码已存在，请重新添加');
				document.getElementById("papersName").value="";
				document.getElementById("papersName").focus();
				return false;
				
			}else{
				var vaild = affirm("您确定要操作吗？");
				if(vaild==true){
			    frm.submit();
		   		 }else{
		          return false;
		    	}
			}	
		});
	}
	function changeUserId(userID){
		checkMemberId(userID);
	}
	function password(userId){
		return true;
	}
	function passwordcompare(userID, compareuserID) {
		return true;
	}
</script>
<%@ include file="/public/footInc.jsp"%>