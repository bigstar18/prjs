<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<%
   String nowDate = Tools.fmtDate(new Date());
   
%>
<html>
  <head>  
    <title>�����ձ�����ҳ</title>
    <script type="text/javascript">
      function init(){
		if(frm.bdate.value == null || frm.bdate.value == ""){
			frm.bdate.value = '<%=nowDate%>';
		}
		chiframesrc(frm.bdate.value);
	  } 
      function doquery(){
			var date = document.getElementById("bdate").value;
			chiframesrc(date);
      }
      function chiframesrc(date){
    	   if(date){
				var url1 = "${basePath}/finance/financialQuery/listDebitBalanceDayReport.action?bdate="+date;
				document.getElementById("debitListFrame").src=url1;
				var url2 = "${basePath}/finance/financialQuery/listCreditBalanceDayReport.action?bdate="+date;
				document.getElementById("creditListFrame").src=url2;
			}
       }
      function requery(){
    	  frm.bdate.value = '<%=nowDate%>';
    	  chiframesrc(frm.bdate.value);
      }
    </script>
  </head>
  <body onload="init()">
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
		   <div class="div_cx">
			  <form name="frm" action="${basePath}/finance/financialQuery/balanceDayReport.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table3_style" border="0" cellspacing="0" cellpadding="0">
						  <td class="table3_td_1" align="right">
											�������ڣ�
											<input type="text" class="wdate" id="bdate"  style="width: 106px"
												name="${GNNT_}primary.bdate[=][date]"			
												value="${oldParams['primary.bdate[=][date]']}"					
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							</td>
				        
						    <td class="table3_td_anniu" align="left">
						      <button class="btn_sec" id="view" onclick="doquery()">��ѯ</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onclick="requery();">����</button>
						    </td>
					      </tr>
					    </table>
				      </div>
				    </td>
			      </tr>
			    </table>
		      </form>
		     
            </div>

	      </td>
	    </tr>
      </table>
       <iframe id="debitListFrame" name="debitListFrame" src="" frameborder="NO" border="0" framespacing="0" width="100%" height="290"></iframe><br/>
	   <iframe id="creditListFrame" name="creditListFrame" src="" frameborder="NO" border="0" framespacing="0" width="100%" height="290"></iframe>
    </div>
   
  </body>
</html>
