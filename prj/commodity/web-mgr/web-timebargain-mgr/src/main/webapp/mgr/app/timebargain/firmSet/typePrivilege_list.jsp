<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>��������Ϣ</title>
    <script type="text/javascript">
      //ִ�в�ѯ�б�
	  function dolistquery() {
		frm.submit();
	  }
    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/firmSet/tradePrivilege/listTypePrivilege.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0">
						  <tr>
						     <td  >
							         ������:&nbsp;
								<label>
								  <input type="text" class="input_text" id="typeID" name="${GNNT_}primary.typeID[like]" value="${oldParams['primary.typeID[like]']}" />
							    </label>
							  </td>
							 <td >
							         Ʒ��/��Ʒ����:&nbsp;
								<label>
								  <input type="text" class="input_text" id="kindID" name="${GNNT_}primary.kindID[like]" value="${oldParams['primary.kindID[like]']}" />
							    </label>
							  </td>

						    <td>
						      <button class="btn_sec" id="view" onclick="dolistquery()">��ѯ</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onclick="myReset();">����</button>
						    </td>
					      </tr>
					    </table>
				      </div>
				    </td>
			      </tr>
			    </table>
		      </form>
            </div>
            
            <div class="div_list">
           
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="typePrivilege"
							  action="${basePath}/timebargain/firmSet/tradePrivilege/listTypePrivilege.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="typePrivilege.xls" csvFileName="typePrivilege.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="typeID" title="������" width="10%" style="text-align:center;" />
						<ec:column property="type" title="����������" width="10%" style="text-align:center;">
					      ${tradePrivilege_typeMap[typePrivilege.type] }
						</ec:column>
						<ec:column property="kind" title="Ʒ��/��Ʒ" width="10%" style="text-align:center;">
					      ${tradePrivilege_kindMap[typePrivilege.kind] }
						</ec:column>
						<ec:column property="kindID" title="Ʒ��/��Ʒ����" width="10%" style="text-align:center;" />
						<ec:column property="privilegeCode_B" title="��Ȩ��" width="10%" style="text-align:center;">
					      ${privilegeCode_BMap[typePrivilege.privilegeCode_B] }
						</ec:column>
						<ec:column property="privilegeCode_S" title="����Ȩ��" width="10%" style="text-align:center;">
					      ${privilegeCode_SMap[typePrivilege.privilegeCode_S] }
						</ec:column>		
					  </ec:row>
					</ec:table>
				  </td>
				</tr>
			  </table>
            </div>
    					
	      </td>
	    </tr>
      </table>
    </div>
  </body>
</html>
