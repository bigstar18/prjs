<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>  
    <title>特殊商品设置框架页</title>
  </head>
  
 <frameset rows="35,*" border="0" >    
    <frame name="TopFrame" src="<c:url value="/mgr/app/timebargain/firmSet/commoditySpecial_head.jsp"/>" scrolling="auto" application="yes"> 
    <frame name="ListFrame" src="" application="yes">
  </frameset>
</html>
