<!-- %
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
String path = ((new File(application.getRealPath(request.getRequestURI()))).getParentFile()).getParentFile().getParentFile().getParentFile().getParent();
if(loginID1 == null)
{
	  //----------------------------------
	  /*String ip_all = request.getParameter("ip_all");
	  String id_all = request.getParameter("id_all");
	  String overtime_all = request.getParameter("overtime_all");

	  if(ip_all!=null&&id_all!=null&&overtime_all!=null)
	  {
		  String k2_url = path+ "/pri2.key";
		  String k3_url = path+ "/pub.key";
		  File f2 = new File(k2_url);
		  File f3 = new File(k3_url);
		  
		  VerifySignature k1 = new VerifySignature(Util.hex2byte(ip_all),f3, f2, "RSA");
		  VerifySignature k2 = new VerifySignature(Util.hex2byte(id_all),f3, f2, "RSA");

		  System.out.println("ip is --- "+new String(k1.getEnMyinfo()));
		  System.out.println("id is --- "+new String(k2.getEnMyinfo()));
		  System.out.println("overtime is --- "+overtime_all);
	  }*/
	  //----------------------------------

	  return;
}
% -->