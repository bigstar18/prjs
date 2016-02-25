<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>登录</title>
<style type="text/css">
<!--
body {
	background-color: #6b7ea9;
	text-align: center;
	vertical-align: middle;
	margin-top: 100px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
}
.login_td {
	background-color: #CFD9F1;
	height: 298px;
	width: 490px;
	background-image: url(pic/bg.jpg);
	background-repeat: no-repeat;
	border: 1px dashed #FFFFFF;
	background-attachment: fixed;
	background-position: center bottom;
	margin: 0px;
	text-align: center;
	vertical-align: top;
}
.login_bt {
	font-size: 12px;
	font-weight: normal;
	color: #FFFFFF;
	text-decoration: none;
	background-color: #6B7EA9;
	padding-top: 0px;
	padding-right: 5px;
	padding-bottom: 0px;
	padding-left: 5px;
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #414F70;
	border-left-color: #414F70;
	text-align: center;
}
.login_k {
	background-color: #FFFFFF;
	border: 1px dashed #6B7EA9;
	height: 18px;
	width: 140px;
}
.login_btn {
	font-size: 12px;
	font-weight: normal;
	color: #FFFFFF;
	text-decoration: none;
	background-color: #6B7EA9;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #6B7EA9;
	border-right-color: #3D4A69;
	border-bottom-color: #3D4A69;
	border-left-color: #6B7EA9;
	padding-top: 2px;
	padding-right: 2px;
	padding-bottom: 0px;
	padding-left: 2px;
}
-->
</style>
</head>

<%
String uid = request.getParameter("uid");
if(uid == null) uid = "";

if(request.getParameter("Submit") != null)
{
			String randNumSys = (String)session.getAttribute("RANDOMICITYNUM");
			String randNumInput = request.getParameter("randNumInput");
			String password = request.getParameter("password");
			if(!(randNumInput != null && !randNumInput.trim().equals("") && randNumInput.trim().equals(randNumSys)))
			{
				%>
				<SCRIPT LANGUAGE="JavaScript">
					<!--
					alert("验证码错误！");
					//-->
					</SCRIPT>	
				<%
			}
			else
			{
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				int i = 0;
				try{
					 BankDAO dao = BankDAOFactory.getDAO();
					 conn = dao.getConnection();
						
						String sqlStr = "select count(*) from manager where userid='"+uid+"' and password='"+password+"'";
						
						ps = conn.prepareStatement(sqlStr);
						rs = ps.executeQuery();				
						while(rs.next()){
							i = rs.getInt(1);
						}		

						rs.close();
						rs = null;
						ps.close();
						ps = null;	
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
					try
					{
					  conn.close();
					}
					catch (Exception e) 
					{    	
					}
					conn = null;
				}

				if(i > 0)
				{
					session.setAttribute("uid",uid);
					%>
					<SCRIPT LANGUAGE="JavaScript">
						<!--
						window.location = "index.jsp";
						//-->
						</SCRIPT>	
					<%
					return;
				}
				else
				{
					%>
					<SCRIPT LANGUAGE="JavaScript">
						<!--
						alert("用户名或密码错误！");
						//-->
						</SCRIPT>	
					<%
				}

			}			
						
}

%>

<body>
<form name="frm" method="post" action="">
<table width="492"  border="0"  cellpadding="0" cellspacing="0">
    <tr>
      <td><div align="center"><!--img src="pic/m_name.jpg" width="307" height="56" /--></div></td>
    </tr>
      <tr>
        <td width="506" class="login_td">
        <table width="100%" height="88" border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td height="88">         
          <div align="center"><b>欢 迎 登 录 银 行 接 口 管 理 系 统</b><!--img src="pic/bt.jpg" width="268" height="31" /--></div>
          </td>
          </tr>
          </table>
		  <table width="55%" border="0" cellpadding="0" cellspacing="10">
            <tr>
              <td width="33%" class="login_bt">用户名</td>
              <td width="67%"><input name="uid" type="text" class="login_k" value='<%=uid%>'></td>
            </tr>
            <tr>
              <td class="login_bt">密&nbsp;&nbsp;码</td>
              <td><input name="password" type="password" class="login_k"></td>
            </tr>
			<tr>
              <td class="login_bt">验证码</td>
              <td><input name=randNumInput type="text" class="login_k">&nbsp;<img src="image.jsp" align="absmiddle"></td>
            </tr>
            <tr>              
              <td height="52">&nbsp;</td>
              <td><label>
			      <input type="submit" name="Submit" onclick="return frmChk()" class="login_btn" value="提交">&nbsp;&nbsp;
				  <input type="reset" name="Submit2"  class="login_btn" value="重置">
			  </label>
			  </td>
            </tr>
        </table>
		</td>
        </tr>
    </table>
	<input type=hidden name="kcode">
    </form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{
	if(frm.uid.value == "")
	{
		alert("请填写用户名！");
		frm.uid.focus();
		return false;
	}
	else if(frm.password.value == "")
	{
		alert("请填写密码！");
		frm.password.focus();
		return false;
	}
	else if(frm.randNumInput.value == "")
	{
		alert("请填写验证码！");
		frm.randNumInput.focus();
		return false;
	}	
}
frm.uid.focus();
//-->
</SCRIPT>