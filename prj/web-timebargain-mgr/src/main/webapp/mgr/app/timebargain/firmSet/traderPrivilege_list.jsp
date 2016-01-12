<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>����ԱȨ��</title>
    <script type="text/javascript">
      // ���ؽ���Ա�б�
	  function goback() {
		 var backUrl = "/timebargain/firmSet/tradePrivilege/listTrader.action";
		 //��ȡ������תURL
	     var url = "${basePath}"+backUrl;
	     //�� URL ��Ӳ���
		 url += "?firmID=" + '${firmID}';

	     document.location.href = url;
	  }

	  // ��ϸ��Ϣ��ת
	  function viewById(id){
			//��ȡ����Ȩ�޵� URL
			var updateUrl = "/timebargain/firmSet/tradePrivilege/viewTraderPrivilege.action";
			//��ȡ������תURL
			var url = "${basePath}"+updateUrl;
			//�� URL ��Ӳ���
			url += "?entity.ID="+id;
			//�����޸�ҳ��
			if(showDialog(url, "", 600, 350)){
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
			url += "?typeID=" + '${traderID}';
			//����ҳ��
			if(showDialog(url, "", 600, 350)){
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
			  <rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/addTraderPrivilegeForward.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <rightButton:rightButton name="ɾ��" onclick="deletePrivilege();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/deleteTraderPrivilege.action" id="delete"></rightButton:rightButton>
			</div>      
	 
            <div class="div_list">    
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="traderPrivilege"
							  action="${basePath}/timebargain/firmSet/tradePrivilege/listTraderPrivilege.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="traderPrivilege.xls" csvFileName="traderPrivilege.csv"
							  showPrint="true" listWidth="100%" title="����Ա ${traderID}����Ȩ��ά��"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${traderPrivilege.ID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="KINDID" title="����Ʒ�ִ���" width="20%" style="text-align:center;" >
						  <%-- <a href="#" class="blank_a" onclick="viewById('<c:out value="${traderPrivilege.ID}"/>')">
						    <font color="#880000">${traderPrivilege.BREEDNAME }</font>
						  </a>--%>
						  <rightHyperlink:rightHyperlink href="#" text="<font color='#880000'>${traderPrivilege.KINDID}</font>" className="blank_a" onclick="viewById('${traderPrivilege.ID}');" action="/timebargain/firmSet/tradePrivilege/viewTraderPrivilege.action" />
						</ec:column>
					    <ec:column property="kindName" title="����Ʒ��" width="20%" style="text-align:center;" sortable="false">   
						     <font color='#880000'>${traderPrivilege.BREEDNAME}</font>					  
						</ec:column>
						<ec:column property="PRIVILEGECODE_B" title="��Ȩ��" width="20%" style="text-align:center;" editTemplate="ecs_privilegeCode_B" mappingItem="FIRMPRIVILEGE_B" />
						<ec:column property="PRIVILEGECODE_S" title="����Ȩ��" width="20%" style="text-align:center;" editTemplate="ecs_privilegeCode_S" mappingItem="FIRMPRIVILEGE_S" />	
					  </ec:row>
					  
					  <ec:extend >
  		         	    <%-- <a href="#" onclick="goback()">���ؽ���Ա�б�</a>--%>
  		         	    <rightHyperlink:rightHyperlink href="#" text="���ؽ���Ա�б�" onclick="goback()" action="/timebargain/firmSet/tradePrivilege/listTrader.action" />
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
