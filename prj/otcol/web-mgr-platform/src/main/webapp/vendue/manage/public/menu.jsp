<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>

<!--用户权限判断-->
<%/*
<fun:acl userid="loginID" subject="10"/>*/%>
<table width="100%"  border="0" cellpadding="4" cellspacing="4">
<%
   String allPartition=delNull(request.getParameter("allPartition"));
   //String userPar=session.getAttribute("USERPAR").toString();
   String idx=delNull(request.getParameter("idx"));
   StringBuffer sql=null;
   DBBean bean=null;
   ResultSet rs=null;
   String filter=null;
   int len=0;
   int rowLen=7;
   int c=0;
   try{
	   bean=new DBBean(JNDI);
       filter=" and validflag=1";
	   sql=new StringBuffer();
	   sql.append("select count(partitionid) n from v_syspartition where 1=1 "+filter+"");
	   rs=bean.executeQuery(sql.toString());
	   if(rs.next()){
	       len=rs.getInt("n");
	   }
       sql=new StringBuffer();
	   sql.append("select partitionid,description from v_syspartition where 1=1 "+filter+" order by partitionid asc");
       rs=bean.executeQuery(sql.toString());
	   while(rs.next()){
		   String partitionId=rs.getString("partitionid");
		   String description=rs.getString("description");
		   if(c==0||c%rowLen==0){
%>
           <tr>
<%
           }	
%>
		  <td width="14%" class="<%=idx.equals(partitionId)?"top_b2":"top_b1"%>" align="center"><div align="center"><a href="javascript:clickMenu('<%=partitionId%>');" class="but_t"><%=description%>板块</a></div></td>
          <!--如果登录用户是所有市场,则显示总计-->
		  <%
			      if(c==len-1){
					  c=c+1;
					  if(c==0||c%rowLen==0){
		  %>
		              <tr>
		  <%
		              }	  
		  %>
		         <td width="14%" class="<%=idx.equals(allPartition)?"top_b2":"top_b1"%>" align="center"><div align="center"><a href="javascript:clickMenu('0');" class="but_t">总计</a></div></td>
		  <%
				      if(c==0||c%rowLen==0){
		  %>
		              </tr>
		  <%
			         }
		          }  
			  if(c==len-1||c==len){
				  if(rowLen-1-c%rowLen>0){
		  %>
		          <td width="<%=(rowLen-1-c%rowLen)*14%>%" align="center" colspan="<%=rowLen-1-c%rowLen%>">&nbsp;</td>
		  <%
			      }
		      }
			  c=c+1;
			  if(c==0||rowLen==0){
		      }
	   }
   }catch(Exception e){
       e.printStackTrace();
   }finally{
       if(rs!=null)rs.close();
	   if(bean!=null)bean.close();
   }				   
%>
<input type="hidden" name="partitionID" value="${param.partitionID}">
<input type="hidden" name="idx" value="${param.idx}">
</table>
	