<%@ page contentType="text/html;charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<base target="_self">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ include file="../../globalDef.jsp"%>
<style type='text/css'>	
</style>
	<c:if test="${not empty param.add}">
  <c:catch var="exceError">
  	<%

  	 String contest1 = request.getParameter("contest");

     String id1=request.getParameter("id");

     TradeDAO dao = TradeDAOFactory.getDAO();
	 int result=0;
	 try
	{
     result=dao.bargainMod(id1,contest1);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
     if(result==1)
     {
     %>
       <SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("��Ӻ�ͬ�ı��ɹ�");
			window.returnValue="1";
				window.close();
			//-->
			</SCRIPT>
			<%
     }
    else
    	{
    	%>
    	 	<SCRIPT LANGUAGE="JavaScript">
			  <!--
			   alert("���ʧ�ܣ�����������!");
			   window.returnValue="1";
					window.close();
			  //-->
			  </SCRIPT>
    	<%
    	}
  	%>
  </c:catch>
<c:if test="${not empty exceError}">
     <%
	       //�쳣����
	       String exceError=pageContext.getAttribute("exceError").toString();
		   log(request,exceError);
		   hintError(out);
	 %>
</c:if>
</c:if>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>��ͬ�ı����</title>
<script type="text/javascript" src="FCKeditor/fckeditor.js"></script>
</head>

<body>
<form id="frm" name="frm" method="post">
<table width="100%" border="0">
	<%
	String id=request.getParameter("id");
	String name="";
	String validFlag="";
	String tradePartition="";
	String contractContent="";
	%>
	<%
  //�����������ݶ���
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  int cnt = 0;
  try{
    Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
    StringBuffer sql=new StringBuffer("select * from v_contracttemplet where id="+id+"");
    ps = conn.prepareStatement(sql.toString());
	  rs=ps.executeQuery();
%>
<%
	  if(rs.next()){
	  name=rs.getString("name");
	  Clob clob;
	  clob=rs.getClob("templet");
    if(clob!=null){
      Reader is=clob.getCharacterStream();
      int c=0;
      char[] buffer=new char[100];
      StringBuffer str=null;
      while ((c=is.read(buffer))!=-1) {
        str=new StringBuffer();
        str.append(buffer,0,c);
        //out.print(str.toString());
        contractContent=contractContent+str.toString();
        
      }
      }
%>
    <%}
      rs.close();
      ps.close();
      }catch(Exception e){
        e.printStackTrace();
      }finally{
        if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
	      try{
          conn.close();
        }catch (Exception e){}
         conn = null;
	     }%>
	<input type="hidden" name="id" value="<%=id%>">
	<tr>
	              <td align="left">
                   ��� ��<%=name%>
                   
                   
                </td>
</tr>
<tr>
	<td>
		&nbsp;
	</td>
</tr>
<tr>
	<td>
		��ǩ˵����
	</td>
</tr>
<tr>
	<td>
		<table border="1" bordercolor='#175768' style='border-collapse:collapse;' cellspacing="0" cellpadding="0" width="100%">
		<tr height="25">
			<td align=center>��ͬ��:</td><td align=center>t_contractid</td>
			<td align=center>��ĺ�:</td><td align=center>t_code</td>
			<td align=center>�򷽽����̴���:</td><td align=center>buy_no</td>
			<td align=center>���������̴���:</td><td align=center>sell_no</td>
			
		</tr>
		<tr height="25">
			<td align=center>��Ʒ����:</td><td align=center>t_type</td>
			<td align=center>��Ʒ������λ:</td><td align=center>units</td>
			<td align=center>�Ӽ۷���:</td><td align=center>str1k</td>
			<td align=center>ʵ�ʴ洢���:</td><td align=center>str3_0</td>
			
		</tr>
		<tr height="25">
			<td align=center>��Ʒ��������*:</td><td align=center>str3_(*-1)</td>
			<td align=center>�������:</td><td align=center>str12</td>
			<td align=center>����:</td><td align=center>str13</td>
			<td align=center>����:</td><td align=center>str14</td>
		</tr>
		<tr height="25">
			<td align=center>�����ص�:</td><td align=center>str15</td>
			<td align=center>���վ����:</td><td align=center>str16</td>
			<td align=center>��������ϵ��:</td><td align=center>b_fee</td>
			<td align=center>����������ϵ��:</td><td align=center>s_fee</td>
		</tr>
		<tr height="25">
			<td align=center colspan=2>�򷽱�֤��ϵ��:</td><td align=center colspan=2>b_security</td>
			<td align=center colspan=2>������֤��ϵ��:</td><td align=center colspan=2>s_security</td>
		</tr>
		<tr height="25">
			<td align=center>�򷽱�֤��ϵ��:</td><td align=center>b_security</td>
			<td align=center>������֤��ϵ��:</td><td align=center>s_security</td>
			<td align=center>������:</td><td align=center>buy_name</td>
			<td align=center>��������:</td><td align=center>sell_name</td>
		</tr>
		<tr height="25">
			<td align=center>�򷽵�ַ:</td><td align=center>buy_address</td>
			<td align=center>������ַ:</td><td align=center>sell_address</td>
			<td align=center>�򷽵绰:</td><td align=center>buy_phone</td>
			<td align=center>�����绰:</td><td align=center>sell_phone</td>
		</tr>
		<tr height="25">
			<td align=center>�򷽿�����:</td><td align=center>buy_bank</td>
			<td align=center>����������:</td><td align=center>sell_bank</td>
			<td align=center>�������ʺ�:</td><td align=center>buy_account</td>
			<td align=center>���������ʺ�:</td><td align=center>sell_account</td>
		</tr>
		<tr height="25">
			<td align=center>����������:</td><td align=center>buy_postid</td>
			<td align=center>������������:</td><td align=center>sell_postid</td>
			<td align=center>�򷽴���:</td><td align=center>buy_tdfax</td>
			<td align=center>��������:</td><td align=center>sell_tdfax</td>
		</tr>
		<tr height="25">
			<td align=center>���ֻ�:</td><td align=center>buy_tdmobile</td>
			<td align=center>�����ֻ�:</td><td align=center>sell_tdmobile</td>
			<td align=center>�򷽵�������:</td><td align=center>buy_traderemail</td>
			<td align=center>������������:</td><td align=center>sell_traderemail</td>
		</tr>
		<tr height="25">
			<td align=center>�ɽ�����:</td><td align=center>t_amount</td>
			<td align=center>�ɽ��۸�:</td><td align=center>t_price</td>
			<td align=center>�ɽ����:</td><td align=center>t_money</td>
			<td align=center>����д:</td><td align=center>t_bmoney</td>
		</tr>
		<tr height="25">
			<td align=center colspan=2>�򷽽��״�������:</td><td align=center colspan=2>buy_tradername</td>
			<td align=center colspan=2>�������״�������:</td><td align=center colspan=2>sell_tradername</td>
		</tr>
	</table>
	</td>
</tr>
<tr>
	<td>
		&nbsp;
	</td>
</tr>


<tr>
    <td height="25">
      <textarea name="contest" id="contest" style="width:60%; height:200px;"><%=contractContent%></textarea>
<script type="text/javascript">
var oFCKeditor = new FCKeditor( 'contest' ) ;
//oFCKeditor.BasePath = 'FCKeditor/' ;
oFCKeditor.ToolbarSet = 'Default' ;
oFCKeditor.Width = '100%' ;
oFCKeditor.Height = '400' ;
oFCKeditor.Value = '' ;
oFCKeditor.ReplaceTextarea(); 
//oFCKeditor.Create() ;
</script>
    </td>
</tr>
<tr>
	<td align="center">
		<input type="hidden" name="add">
		<input type="button" class="btn" value="����"  onclick="return frmChk()"/>&nbsp;&nbsp;
		<input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">
	</td>
</tr>
</table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--

function frmChk()
{
	   if(userConfirm()){
		  //alert(frm.amount.value);
		  //return false;
		  //return true;
		  frm.add.value="add";
		  frm.submit();
	   }else{
	      return false;
	   }
}
//-->
</SCRIPT>

