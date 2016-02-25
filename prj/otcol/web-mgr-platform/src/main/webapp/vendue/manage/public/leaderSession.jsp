<!--%
long lid = -1000;
try
{
	lid = ((Long)session.getAttribute("LOGINIDS")).longValue();
}
catch(Exception se)
{
	
}
ActiveUserManager au = new ActiveUserManager(SPACE,EXPIRETIME,MODE);
String loginID1 = au.getUserID(lid);
if(loginID1 == null)
{
	  sendRedirect(request.getContextPath()+"/vendue/manage/leadership/login.jsp",out);
	  return;
}
% -->