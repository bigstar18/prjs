<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
<html>
<body>
 <div id="haeder" style="height: 65px;">
 <form name=frm id=frm>
			<input type="hidden" name="obj.id" value="">
   <div class="logo"> <div class="anniu_kuang">
    <div class="anniu_01Copy"><a href="#" onclick="logout();"><img src="<%=skinPath%>/cssimg/03.gif" width="91" height="25" border="0" style="-moz-opacity:0.5; filter:alpha(opacity=80);cursor:hand;" onmouseover="this.style.MozOpacity=1;
this.filters.alpha.opacity=100" onmouseout="this.style.MozOpacity=0.5;
this.filters.alpha.opacity=80"></a></div>
    <div class="anniu_01"><a href="#" onclick="changePwd('${CURRENUSERID}');"><img src="<%=skinPath%>/cssimg/02.gif" width="91" height="25" border="0" style="-moz-opacity:0.5; filter:alpha(opacity=80);cursor:hand;" onmouseover="this.style.MozOpacity=1;
this.filters.alpha.opacity=100" onmouseout="this.style.MozOpacity=0.5;
this.filters.alpha.opacity=80"></a></div>
   </div>
    <div class="anniu_kuangCopy">
      <div class="user_name">µÇÂ¼ÕËºÅ:${CURRENUSERID }&nbsp;&nbsp;&nbsp;&nbsp;µÇÂ¼Ãû³Æ:${CURRENUSERNAME }</div>
    </div>
  </div></form>
 </div>
 </body>
</html>
<script language="javascript">
	function logout()
	{
		var vaild = window.confirm("ÄúÈ·¶¨ÒªÍË³öÂð£¿");
		if(vaild==true){
		    parent.window.location="<%=basePath %>/userLogOut/commonUserLogout.action";
	    }else{
           return false;
	    }
    }
	function changePwd(id)
	{
		var url = "<%=basePath %>/slbj/self/passwordSelf.action?obj.userId="+id;
		var value=dialog(url, window, 400, 350);
		//if(value==1111)
			//window.location.reload();
	}
	function versionInfo()
	{
		var url = "<%=surfacePath %>/version.jsp?d="+Date();
		ecsideDialog(url,null,385,270);
	}
	function shinStyle(id){
		var url = "<%=basePath %>/user/commonUserModStyle.action?obj.userId="+id;
		var result = ecsideDialog(url,null,400,250);
		if(result)
		{		
			parent.window.location = "<%=surfacePath %>/index.jsp";
		}
	}
</script>