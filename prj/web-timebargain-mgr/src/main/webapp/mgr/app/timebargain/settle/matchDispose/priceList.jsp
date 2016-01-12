<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>价格详情</title>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		    <div class="div_cxtj">
				    <div class="div_cxtjL"></div>
					<div class="div_cxtjC">
					   价格
					</div>
					<div class="div_cxtjR"></div>
				  </div>
				   <div style="clear: both;"></div>
				  <div>
				    <table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
			   		<%List list=(List)request.getAttribute("priceList");
				  for(int i=0;i<list.size();i++){
				  %>
				   <tr><td><%=((Map)list.get(i)).get("PRICE") %></td></tr> 
				   <%} %>
			  </table>	
			  </div>
	      </td>
	    </tr>
      </table>
       
    </div>
  </body>
</html>
