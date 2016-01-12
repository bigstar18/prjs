<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>���׿ͻ��б�</title>
    <script type="text/javascript">
      // ���ؽ������б�
	  function goback() {
		 var backUrl = "/timebargain/firmSet/tradePrivilege/tradePrivilege.action";
		 //��ȡ������תURL
	     var url = "${basePath}"+backUrl;

	     document.location.href = url;
	  }

	  // �޸�״̬
	  function updateStatus(){
			var status = document.getElementById("sta").value;
			var updateStatusUrl = "/timebargain/firmSet/tradePrivilege/updateStatusCustomer.action";
			//��ȡ������תURL
			var url = "${basePath}"+updateStatusUrl+"?status="+status;
			
			updateRMIEcside(ec.ids,url);
	  }

	   //�����Ϣ��ת
	   function addForward(){
			//��ȡ����Ȩ�޵� URL
			var addUrl=document.getElementById('add').action;
			//��ȡ������תURL
			var url = "${basePath}"+addUrl;
			//�� URL ��Ӳ���
			 url += "?firmID=" + "${firmID}";
			//�������ҳ��
			if(showDialog(url, "", 600, 400)){
				 //�����ӳɹ�����ˢ���б�
			     ECSideUtil.reload("ec");
			}
		}
		
		//����ɾ����Ϣ
		function deleteCustomer(){
			//��ȡ����Ȩ�޵� URL
			var delateUrl = document.getElementById('delete').action;
			//��ȡ������תURL
			var url = "${basePath}"+delateUrl;
			//ִ��ɾ������
			updateRMIEcside(ec.ids,url);
		}

	    // ��ת���׿ͻ�Ȩ��
		function updateForwardPrivilege(id) {
				//��ȡ����Ȩ�޵� URL
				var updateUrl = "/timebargain/firmSet/tradePrivilege/listCustomerPrivilege.action";
				//��ȡ������תURL
				var url = "${basePath}"+updateUrl;
				//�� URL ��Ӳ���
				url += "?customerID="+id + "&firmID=" + '${firmID}';

				document.location.href = url;
		 }

    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		    
		    <div class="div_gn">
			  <rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/addCustomerForward.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <rightButton:rightButton name="ɾ��" onclick="deleteCustomer();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/deleteCustomer.action?autoInc=false" id="delete"></rightButton:rightButton>
			</div>     
		      
            <div class="div_list"> 
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="customer"
							  action="${basePath}/timebargain/firmSet/tradePrivilege/listCustomer.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="customer.xls" csvFileName="customer.csv"
							  showPrint="true" listWidth="100%" title="������ ���룺${firmID}"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
				        <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${customer.customerID}" width="5%" viewsAllowed="html" />
				         <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="customerID" title="��������" width="20%" style="text-align:center;" />
						
						<ec:column property="status" title="״̬" width="20%" style="text-align:center;" >
						  ${firm_statusMap[customer.status] }
						</ec:column>
						<ec:column property="createTime" title="����ʱ��" width="20%" style="text-align:center;">
						  <fmt:formatDate value="${customer.createTime }" pattern="yyyy-MM-dd HH:mm:SS" />
						</ec:column>
						<ec:column property="prop" title="��������Ȩ��" width="10%" style="text-align:center;" sortable="false"  tipTitle="�鿴Ȩ��" >
						  <%-- <a href="#" onclick="updateForwardPrivilege('<c:out value="${customer.customerID}"/>')"><img src="<c:url value="${skinPath}/image/app/timebargain/wheel.gif"/>"/></a>--%>
						  <rightHyperlink:rightHyperlink text="<img src='${skinPath}/image/app/timebargain/wheel.gif'/>" href="#" onclick="updateForwardPrivilege('${customer.customerID}')" action="/timebargain/firmSet/tradePrivilege/listCustomerPrivilege"/>
						</ec:column>
					  </ec:row>
					  
					  <ec:extend >
  			           <%--  <a href="#" onclick="goback()">���ؽ������б�</a>--%>
  			            <rightHyperlink:rightHyperlink href="#" text="���ؽ������б�" onclick="goback()" action="/timebargain/firmSet/tradePrivilege/tradePrivilege.action" />
  		              </ec:extend>
					</ec:table>
				  </td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
		          <td>
			        <select id="sta">
			          <c:forEach items="${firm_statusMap}" var="map">
					    <option value="${map.key}">${map.value}</option>
				      </c:forEach>		
			        </select>
			        <input type="button" class="anniu_btn" value="�޸�״̬"  onclick="updateStatus()">
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
