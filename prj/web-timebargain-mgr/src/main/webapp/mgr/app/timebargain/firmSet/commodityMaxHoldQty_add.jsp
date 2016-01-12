<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>添加特殊商品订货量</title>
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	  <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	  <script src="${basePath}/mgr/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	  <script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
	  <script type="text/javascript">
		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			// 添加按钮注册点击事件
			$("#add").click(function(){
				// 验证信息
				if(jQuery("#frm").validationEngine('validate')){
					
					var vaild = affirm("您确定要操作吗？");
					if(vaild){
						// 添加信息URL
						var addUrl = $(this).attr("action");
						// 全 URL 路径
						var url = "${basePath}" + addUrl;
						$("#frm").attr("action",url);
						$("#frm").submit();
					}
				}
			});
		});

		// 根据商品ID, 获取商品数据
		function getCommodity(id){
			var oldAjaxAsync = $.ajaxSettings.async;
			var url = "${basePath}/ajaxcheck/firmSet/getCommodityJson.action?commodityID=" + id;
			$.ajaxSettings.async = false;
			$.getJSON(url,null,function call(result){
				
				document.getElementById("maxHoldQty").value = result[32];
				document.getElementById("cleanMaxHoldQty").value = result[33];

			});
			$.ajaxSettings.async = oldAjaxAsync;

		}
	  </script>
		
    <script type="text/javascript">
      function setReadOnly(obj)
	  {
	    obj.readOnly = true;
	    obj.style.backgroundColor = "#C0C0C0";
	  }
	  function setReadWrite(obj)
	  {
	    obj.readOnly = false;
	    obj.style.backgroundColor = "white";
	  }

	  //仅输入数字
	  function onlyNumberInput() {
	  	if (event.keyCode>=48 && event.keyCode<=57) {
	  		event.returnValue=true;
	  	} else {
	  		event.returnValue=false;
	  	}
	  }
	  
	  //仅输入数字和.
	  function onlyDoubleInput()
	  {
	    if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
	    {
	      event.returnValue=false;
	    }
	  }

	  // 改变 最大订货量
      function changeMaxHoldQty(id){

     	if (id == "1") {
     		document.getElementById('maxHoldQty').value = "";
     		setReadWrite(document.getElementById('maxHoldQty'));
     	}
     	if (id == "2") {
     		document.getElementById('maxHoldQty').value = "-1";
     		setReadOnly(document.getElementById('maxHoldQty'));
     	}
      }

      // 改变 最大净订货量
      function changeCleanMaxHoldQty(id){

     	if (id == "1") {
     		document.getElementById('cleanMaxHoldQty').value = "";  		
     		setReadWrite(document.getElementById('cleanMaxHoldQty'));
     	}
     	if (id == "2") {
     		document.getElementById('cleanMaxHoldQty').value = "-1";
     		setReadOnly(document.getElementById('cleanMaxHoldQty'));
     	}
      }

      function on_change(){
    	  if($("#maxHoldQty").val() != "-1"){
    		  document.getElementById('type4').checked = true;		
    		  setReadWrite(document.getElementById('maxHoldQty'));
    	  }else{
    		  document.getElementById('type3').checked = true;		
    		  setReadOnly(document.getElementById('maxHoldQty'));
    	  }
    	  
    	  if($("#cleanMaxHoldQty").val() != "-1"){
    		  document.getElementById('type6').checked = true;		
    		  setReadWrite(document.getElementById('cleanMaxHoldQty'));
    	  }else{
    		  document.getElementById('type5').checked = true;		
    		  setReadOnly(document.getElementById('cleanMaxHoldQty'));
    	  }
      }

      function window_onLoad(){
    	  changeMaxHoldQty(2);
    	  changeCleanMaxHoldQty(2);
      }	  
    </script>
  </head>
  <body onload="window_onLoad()">
	<form id="frm" name="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden" >

	  <div class="div_cx">
		<table border="0" width="100%" align="center">
		  <tr>
		    <td>
			  <div class="warning">
			    <div class="content">
			               温馨提示 :特殊订货量添加
				</div>
			  </div>
			</td>
		  </tr>
		  <tr>
		    <td>
			  <table border="0" width="100%" align="center">
			   
			    <tr>
				  <td>
				    <div class="div_cxtj">
					  <div class="div_cxtjL"></div>
					  <div class="div_cxtjC">
						添加特殊订货量
					  </div>
					  <div class="div_cxtjR"></div>
					</div>
					<div style="clear: both;"></div>
				    <div>
				    
					 <table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="table2_style">
						<tr>
						  <td align="right">交易商代码：</td>
						  <td>
						    <input type="text" id="firmID" name="entity.firmID" 
								   class="validate[required,maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />],custom[onlyLetterNumber],ajax[mouseCheckFirmByFirmId]] input_text "/>
							<span class="required">*</span>
						  </td>
						  <td>
							<div class="onfocus">不能为空！</div>
						  </td>						
						</tr>	
						<tr>
						  <td align="right">商品代码：</td>
						  <td>
						    <select id="commodityID" name="entity.commodityID" class="validate[required]" onchange="getCommodity(this.value),on_change()">
                   			  <option value="">请选择</option>
                   			  <c:forEach items="${commodityList}" var="map" >
								<option value="${map['COMMODITYID']}">${map['COMMODITYID']}</option>
							  </c:forEach>
                			</select>
							<span class="required">*</span>
						  </td>
						  <td>
							<div class="onfocus">不能为空！</div>
						  </td>						
						</tr>	
						<tr>
                          <td align="right">
                                                                                最大双边订货量：
                            <input type="radio" id="type3" name="type1" onclick="changeMaxHoldQty(2)" checked="checked"/>不限制
                            <input type="radio" id="type4" name="type1" onclick="changeMaxHoldQty(1)" />限制
                          </td>
                          <td>
                            <input type="text" id="maxHoldQty" name="entity.maxHoldQty" class="validate[required,maxSize[10]] input_text " onkeypress="onlyNumberInput()" />
                            <span class="required">*</span>
                          </td>
                          <td>
							<div class="onfocus">不能为空！</div>
						  </td>	
						</tr>		
						<tr>
                          <td align="right">
                                                                                最大净订货量：
                            <input type="radio" id="type5" name="type2" onclick="changeCleanMaxHoldQty(2)"  checked="checked"/>不限制
                            <input type="radio" id="type6" name="type2" onclick="changeCleanMaxHoldQty(1)"  />限制
                          </td>
                          <td>
                            <input type="text" id="cleanMaxHoldQty" name="entity.cleanMaxHoldQty"  class="validate[required,maxSize[10]] input_text " onkeypress="onlyNumberInput()" />
                            <span class="required">*</span>
                          </td>
                          <td>
							<div class="onfocus">不能为空！</div>
						  </td>	
						</tr>						
					  </table>
					  
				   	</div>
				  </td>
			    </tr>
				
				
		</table>
	  </div>
	  <div class="tab_pad">
	    <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  <tr>
		    <td align="center">
			  <rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/timebargain/firmSet/commoditySpecial/addMaxHoldQty.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			 <button class="btn_sec" onClick="window.close();">关闭</button>
		    </td>
		  </tr>
	    </table>
	  </div>
    </form>
  </body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>