<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>�ر��Ա��Ϣ</title>
	</head>

	<body leftmargin="0" topmargin="0" class="st_body" style="overflow-y:hidden">
		<form action="${basePath}/account/specialMemberInfo/update.action" name="frm" method="post" targetType="hidden">
		<div style="overflow:auto;height:450px;">
			<table border="0" width="95%" align="center" >
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;�ر��Ա��Ϣ</div>
						<%@include file="commonSpecialMemberTable.jsp" %>
					</td>
				</tr>
			</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="100%" align="center">
				<tr>
					<td align="center">
						<button  class="btn_sec" onClick="updatSpecialMember()" id="update">����</button>
					</td>

					<td align="center">
						<button  class="btn_sec" onClick="window.close()">�ر�</button>
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
	
	function checkSpecialMemberId(userID){
			return true;
		}
	function updatSpecialMember(){
		if(!myblur("all")){
			return false;
		}
		var vaild = affirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}
	function password(userID){
		return true;
	}
	function changeUserId(userID){
	}
	function passwordcompare(userID){
		return true;
	}
</script>
<%@ include file="/public/footInc.jsp"%>