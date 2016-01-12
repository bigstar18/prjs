<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>添加特殊商品交收手续费</title>
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
						var url = "${basePath}"+addUrl;
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
				
				document.getElementById("settleFeeAlgr").value = result[43];
				if(document.getElementById("settleFeeAlgr").value == '1'){
					document.getElementById("settleFeeRate_B").value = result[44] * 100;
					document.getElementById("settleFeeRate_S").value = result[45] * 100;
				}else{
					document.getElementById("settleFeeRate_B").value = result[44];
					document.getElementById("settleFeeRate_S").value = result[45];
				}
				

			});
			$.ajaxSettings.async = oldAjaxAsync;

		}
	  </script>
	
    <script type="text/javascript">

      //仅输入数字和.
	  function onlyDoubleInput()
	  {
	    if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
	    {
	      event.returnValue=false;
	    }
	  } 

      function on_change(){
    		
    		if ($("#settleFeeAlgr").val() == "1") {
    		
    			$("#settleFeeRate_BPercent").show();
    			$("#settleFeeRate_SPercent").show();
    			
    		}else {
    			$("#settleFeeRate_BPercent").hide();
    			$("#settleFeeRate_SPercent").hide();
    			
    		}
    	}

    	 function window_load(){
    	   	   
    		 on_change();
    	 }
    </script>
  </head>
  <body onload="window_load()">
	<form id="frm" name="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden" >

	  <div class="div_cx">
		<table border="0" width="100%" align="center">
		  <tr>
		    <td>
			  <div class="warning">
			    <div class="content">
			               温馨提示 :添加特殊交收手续费
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
						基本信息
					  </div>
					  <div class="div_cxtjR"></div>
					</div>
					<div style="clear: both;"></div>
				    <div>
					  <table border="0" cellspacing="0" cellpadding="10" align="center" class="table2_style">
					    <tr>
		          		  <td align="right">交易商代码：</td>   
						  <td>
							<input type="text" id="firmID" name="entity.firmID" 
								   class="validate[required,maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}'/>],custom[onlyLetterNumber],ajax[mouseCheckFirmByFirmId]] input_text " />
							<span class="required">*</span>
						  </td>
						  
            			  <td align="right">商品代码：</td>   		   
            			  <td>
							<select id="commodityID" name="entity.commodityID" class="validate[required]" style="width:80" onchange="getCommodity(this.value),on_change()">
                   			  <option value="">请选择</option>
                   			  <c:forEach items="${commodityList}" var="map" >
								<option value="${map['COMMODITYID']}">${map['COMMODITYID']}</option>
							  </c:forEach>
                			</select>
							<span class="required">*</span>
						  </td>
							           
						</tr>
					  </table>
				   	</div>
				  </td>
			    </tr>
				
				<tr>  
				  <td>
				  
				    <div class="div_cxtj">
					  <div class="div_cxtjL"></div>
					  <div class="div_cxtjC">
						手续费
					  </div>
					  <div class="div_cxtjR"></div>
					</div>
					<div style="clear: both;"></div>
				    <div>
			      
					  <table border="0" cellspacing="0" cellpadding="0"  class="table2_style" align="center">
					    <tr>
			              <td>
			              
                            <table cellSpacing="0" cellPadding="10" border="0" align="center" class="common"> 
								<tr > 		            
		            			  <td colspan="2">			
		            				<span >交收手续费算法：
		            				  <select id="settleFeeAlgr" name="entity.settleFeeAlgr" onchange="on_change()" class="validate[required]">
										<option value="">请选择</option>
					                    <c:forEach items="${algrMap}" var="map" >
									       <option value="${map.key}">${map.value}</option>
									    </c:forEach>
			   						  </select> 
			   						  <span class="required">*</span>  
									</span>
											
								  </td>	
								</tr>  
								<tr>          		            
		            			  <td align="right" >交收买：
		            			    <input id="settleFeeRate_B" name="entity.settleFeeRate_B" value="${entity.settleFeeRate_B }"
		            					   onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text"  />
			  						<span id="settleFeeRate_BPercent">%</span><span class="required">*</span>
			  					  </td>
			  					  <td align="right" >交收卖：
			  					     <input id="settleFeeRate_S" name="entity.settleFeeRate_S" value="${entity.settleFeeRate_S }"
			  							    onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text"  />
			  						 <span id="settleFeeRate_SPercent">%</span><span class="required">*</span>  
			  					  </td>
		    					</tr>	
		    											
	                       </table>  
					
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
			  <rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/timebargain/firmSet/commoditySpecial/addSettleFee.action" id="add"></rightButton:rightButton>
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