<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>������Ȩ��</title>
    <script type="text/javascript">
      // ���ؽ������б�
	  function goback() {
		 var backUrl = "/timebargain/firmSet/tradePrivilege/tradePrivilege.action";
		 //��ȡ������תURL
	     var url = "${basePath}"+backUrl;

	     document.location.href = url;
	  }
		
	  // ��ϸ��Ϣ��ת
	  function viewById(id){
			//��ȡ����Ȩ�޵� URL
			var updateUrl = "/timebargain/firmSet/tradePrivilege/viewFirmPrivilege.action";
			//��ȡ������תURL
			var url = "${basePath}"+updateUrl;
			//�� URL ��Ӳ���
			url += "?entity.ID="+id;
			//�����޸�ҳ��
			if(showDialog(url, "", 600, 450)){
				//����޸ĳɹ�����ˢ���б�
				ECSideUtil.reload("ec");
			};
	  }

	  //�����Ϣ��ת
	  function addForward(){
			//��ȡ����Ȩ�޵� URL
			var addUrl=document.getElementById('add').action;
			//��ȡ������תURL
			var url = "${basePath}"+addUrl;
			//�� URL ��Ӳ���
			url += "?typeID=" + '${firmID}';
			//�������ҳ��
			if(showDialog(url, "", 600, 450)){
				//�����ӳɹ�����ˢ���б�
				ECSideUtil.reload("ec");
			}
	  }
	  
	  //����ɾ����Ϣ
	  function deletePrivilege(){
			//��ȡ����Ȩ�޵� URL
			var deleteUrl = document.getElementById('delete').action;
			//��ȡ������תURL
			var url = "${basePath}"+deleteUrl;
			//ִ��ɾ������
			updateRMIEcside(ec.ids,url);
	  }

    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
	       
		    <div class="div_gn">
			  <rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/addFirmPrivilegeForward.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <rightButton:rightButton name="ɾ��" onclick="deletePrivilege();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/deleteFirmPrivilege.action" id="delete"></rightButton:rightButton>
			</div>      
		          
            <div class="div_list"> 
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="firmPrivilege"
							  action="${basePath}/timebargain/firmSet/tradePrivilege/listFirmPrivilege.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="firmPrivilege.xls" csvFileName="firmPrivilege.csv"
							  showPrint="true" listWidth="100%" title="������ ${firmID}����Ȩ��ά��"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${firmPrivilege.ID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
					    <ec:column property="KIND" title="Ʒ��/��Ʒ" width="10%" style="text-align:center;">
					       <c:if test="${firmPrivilege.KIND == 1}">Ʒ��</c:if>
						   <c:if test="${firmPrivilege.KIND == 2}">��Ʒ</c:if>	
						</ec:column>
						<ec:column property="KINDID" title="����Ʒ��/��Ʒ����" width="20%" style="text-align:center;" >
						 <%--  <a href="#" class="blank_a" onclick="viewById('<c:out value="${firmPrivilege.ID}"/>')">
						    <font color="#880000">${firmPrivilege.KINDID }</font>
						  </a>--%>
						  <rightHyperlink:rightHyperlink href="#" className="blank_a" text="<font color='#880000'>${firmPrivilege.KINDID}</font>"  onclick="viewById('${firmPrivilege.ID}');" action="/timebargain/firmSet/tradePrivilege/viewFirmPrivilege.action" ></rightHyperlink:rightHyperlink>

						</ec:column>
						<ec:column property="kindName" title="����Ʒ��/��Ʒ" width="20%" style="text-align:center;" sortable="false">
						    <c:if test="${firmPrivilege.KIND == 1}">
						     <font color='#880000'>${firmPrivilege.BREEDNAME}</font>
						    </c:if>
						    <c:if test="${firmPrivilege.KIND == 2}">
						     <font color='#880000'>${firmPrivilege.COMMODITYNAME}</font>
						    </c:if>	
						</ec:column>
						<ec:column property="PRIVILEGECODE_B" title="��Ȩ��" width="20%" style="text-align:center;" editTemplate="ecs_privilegeCode_B" mappingItem="FIRMPRIVILEGE_B" />
						<ec:column property="PRIVILEGECODE_S" title="����Ȩ��" width="20%" style="text-align:center;" editTemplate="ecs_privilegeCode_S" mappingItem="FIRMPRIVILEGE_S" />	
					  </ec:row>
					  
					  <ec:extend >
  			           <%--  <a href="#" onclick="goback()">���ؽ������б�</a>--%>
  			            <rightHyperlink:rightHyperlink href="#" text="���ؽ������б�" onclick="goback()" action="/timebargain/firmSet/tradePrivilege/tradePrivilege.action" />
  		              </ec:extend>
					</ec:table>
				  </td>
				</tr>
			  </table>
		
            </div>		
	      </td>
	    </tr>
      </table>
      
      <!-- �༭��Ȩ������ģ�� -->
	  <textarea id="ecs_privilegeCode_B" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="privilegeCode_B" >
			<ec:options items="FIRMPRIVILEGE_B" />
		</select>
	  </textarea>
	  <!-- �༭����Ȩ������ģ�� -->
	  <textarea id="ecs_privilegeCode_S" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="privilegeCode_S" >
			<ec:options items="FIRMPRIVILEGE_S" />
		</select>
	  </textarea>		
      
    </div>
  </body>
</html>
