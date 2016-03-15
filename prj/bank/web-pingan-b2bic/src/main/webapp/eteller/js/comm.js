/**
 * Eteller公共JS函数库
 * 注意：公共库中的函数请不要随便更改，以免对其他调用的交易产生影响
 * 
 * @author 卢一贺
 * @since 2.0 2013-01-26
 */

$(function() {
	
	/**
	 * <select name="" 
	 * 		class="commontypeSelect" 
	 * 		refSelect="appid_select,appname_select" 
	 * 		refCt="appidList,appnameList" 
	 * 		refForm="query" 
	 * 		refUrl="appinfoSelectList"></select>
	 */
	$(".commontypeSelect").live("change", function() {
		var refSelect = $(this).attr("refSelect");
		var refCt = $(this).attr("refCt");
		var refForm = $(this).attr("refForm");
		var refUrl = $(this).attr("refUrl");
		var refCallback = $(this).attr("refCallback");
		var exceptSelector = $(this).attr("exceptSelector");
		
		var rs = refSelect.split(/\s*,\s*/);
		refForm = refForm ? $(this).parents("#" + refForm) : $(this).parents("form:first");
		var params = refForm ? serializeParams(refForm, null, exceptSelector) : serializeParams(refForm, null, exceptSelector);
		if(refSelect && refUrl) {
			if(refCt) {
				var rc = refCt.split(/\s*,\s*/);
				changeSelectListByIdArray(refUrl, params, rs, rc, undefined, undefined, undefined, refForm, refCallback);
			}
		}
	});
	
	/**
	 * <select name="" class="beansSelect" refSelect="appid_select,appname_select" refBeans="appidList,appnameList" refKey="id" refValue="name" refForm="query" refUrl="appinfoSelectList" refCallback="cb();"></select>
	 */
	$(".beansSelect").live("change", function() {
		var refSelect = $(this).attr("refSelect");
		var refBeans = $(this).attr("refBeans");
		var refKey = $(this).attr("refKey");
		var refValue = $(this).attr("refValue");
		var refForm = $(this).attr("refForm");
		var refUrl = $(this).attr("refUrl");
		var refCallback = $(this).attr("refCallback");
		var exceptSelector = $(this).attr("exceptSelector");
		
		var rs = refSelect.split(/\s*,\s*/);
		refForm = refForm ? $(this).parents("#" + refForm) : $(this).parents("form:first");
		var params = refForm ? serializeParams(refForm, null, exceptSelector) : serializeParams(refForm, null, exceptSelector);
		if(refSelect && refUrl) {
			if(refBeans) {
				var rb = refBeans.split(/\s*,\s*/);
				changeSelectListByBeans(refUrl, params, rs, rb, refKey, refValue, undefined, undefined, refForm, undefined, refCallback);
			}
		}
	});
	
	//导出文件
	$(".exportFile").live("click", function(event) {
		$this = $(this);
		var title = $this.attr("title");
		var exportUrl = $this.attr("exportUrl");
		var downloadUrl = $this.attr("downloadUrl");
		
		alertMsg.confirm(title, {
			okCall: function(){
				var params = $this.parent(".pageContent").siblings("#pagerForm").serializeArray();
				
				var ajaxbg = $("#background,#progressBar");
			    ajaxbg.show();
				
			    $.post(exportUrl, params, function(data) {
			    	ajaxbg.hide();
					if(data.statusCode != "0000") {
						alertMsg.error(data.message);
					} else {
						alertMsg.correct(data.message + "<br>请允许弹出窗口进行下载");
						open(downloadUrl + "?rand=" + Math.random());
					}
				});
			}
		});
	});
	
	//当表单内的域的值有变化时才允许提交，否则不允许
	$(".changeSubmitForm :input, .changeSubmitForm textarea").live("keypress", function(event) {
		$changeSubmitForm = $(this).parents(".changeSubmitForm:first");
		$submitBtn = $changeSubmitForm.find(":submit").attr("disabled", false);
	});
	$(".changeSubmitForm select, .changeSubmitForm :input").live("change", function() {
		$changeSubmitForm = $(this).parents(".changeSubmitForm:first");
		$submitBtn = $changeSubmitForm.find(":submit").attr("disabled", false);
	}); 
});

/**
 * 直接跳转至/WEB-INF下的指定页面
 * @param page 跳转页面的页面文件名称
 */
function linkPage(page) {
	request("linkPage?pagePath=" + page);
}

/**
 * 将消息窗口设置到中间位置
 * @param $dialog 要显示到中央的窗口的JQuery对象
 */
function setDialogCenter($dialog) {
	var screenWidth = getViewportWidth();
	var screenHeight = getViewportHeight();
	var width = parseInt($dialog.css("width"));
	var height = parseInt($dialog.css("height"));
	var left = screenWidth/2 - width/2;
	var top = screenHeight/2 - height/2;
	$dialog.css("top", top + "px");
	$dialog.css("left", left + "px");
}

/**
 * 将表格中选中的行中带有name属性的单元格(td)序列化为json数组
 * @param $selectedCheckBox	表格中选中的行(tr)中的checkbox的Jquery对象
 * @returns
 */
function serializeSelectedTdArray($selectedCheckBox) {
	params = [];
	var $selectedNames = $selectedCheckBox.parent().siblings("td[name]");
	$selectedNames.each(function() {
		params.push({name : $(this).attr("name"), value : $.trim($(this).text())});
	});
	return params;
}

/**
 * 根据给定select标签的ID数组和返回的CommonType的集合的属性名称列表commTypeNames，
 * 从给定的数据集合data中自动寻找匹配的CommonType选择框列表数据，并替换旧的选择框列表
 * 注意：selectIdList中的各个元素的顺序应与commTypeNames各个元素相对应
 * @param selectIdList select标签的ID数组
 * @param commTypeNames 返回的CommonType的集合的属性名称列表
 * @param data 请求服务器返回的JSON对象
 * @param callback 回调函数，在每加载完一个select都会调用一次，并且回调函数的第一个参数可获得当前遍历到的select的JQuery对象
 * @param headVal 固定添加一个选择框列表头选项
 * @param headName 固定添加一个选择框列表头选项的值
 */
function changeSelectListByIdArray(url, params, selectIdList, commTypeNames, headVal, headName, callback, $parentBox, finalCallback) {
	//$("#progressBar").show();
	$.post(url, params, function(data) {
		//$("#progressBar").hide();
		$(selectIdList).each(function(i) {
			var $select = $parentBox ? $parentBox.find("#" + this) : $("#" + this);
			var oldVal = $select.val();
			$select.children("option").remove();
			if(headVal != undefined && headName != undefined) {
				$select.append($("<option value='" + headVal + "'>" + headName + "</option>"));
			}
			var curCtName = commTypeNames[i];
			if(/'\w+'/.test(curCtName)) {
				curCtName = curCtName.substring(1, curCtName.length - 1);
			}
			var ctList = data[curCtName];
			if(curCtName == "root") {
				ctList = data;
			}
			$(ctList).each(function() {
				if(!this.id && !this.value) {
					return;
				}
				var $option = $("<option value='" + this.id + "'>" + this.value + "</option>");
				if(this.id == oldVal && this.id) {
					$option[0].selected = true;
				}
				$select.append($option);
			});
			if(callback) {
				callback($select);
			}
		});
		if(finalCallback) {
			if(typeof finalCallback == "string") {
				eval(finalCallback);
			} else {
				finalCallback();
			}
		}
	});
}

/**
 * 类似changeSelectListByIdArray方法
 * @param url
 * @param params
 * @param selectIdList
 * @param beanList
 * @param listKey
 * @param listValue
 * @param headVal
 * @param headName
 * @param $parentBox
 * @param callback
 * @param finalCallback
 */
function changeSelectListByBeans(url, params, selectIdList, beanList, listKey, listValue, headVal, headName, $parentBox, callback, finalCallback) {
	//$("#progressBar").show();
	$.post(url, params, function(data) {
		//$("#progressBar").hide();
		$(selectIdList).each(function(i) {
			var $select = $parentBox ? $parentBox.find("#" + this) : $("#" + this);
			var oldVal = $select.val();
			$select.children("option").remove();
			if(headVal != undefined && headName != undefined) {
				$select.append($("<option value='" + headVal + "'>" + headName + "</option>"));
			}
			var bl = data[beanList[i]];
			if(beanList[i] == "root") {
				bl = data;
			}
			$(bl).each(function() {
				var curId = eval("this." + listKey);
				var curValue = eval("this." + listValue);
				if(!curId && !curValue) {
					return;
				}
				var $option = $("<option value='" + curId + "'>" + curValue + "</option>");
				if(curId == oldVal && curId) {
					$option[0].selected = true;
				}
				$select.append($option);
			});
			if(callback) {
				callback($select);
			}
		});
		if(finalCallback) {
			if(typeof finalCallback == "string") {
				eval(finalCallback);
			} else {
				finalCallback();
			}
		}
	});
}

/**
 * 根据给定select标签的ID数组和返回的CommonType的集合的属性名称列表commTypeNames，
 * 从给定的数据集合data中自动寻找匹配的CommonType选择框列表数据，并替换旧的选择框列表
 * 注意：selectIdList中的各个元素的顺序应与commTypeNames各个元素相对应
 * @param selectIdList select标签的ID数组
 * @param commTypeNames 返回的CommonType的集合的属性名称列表
 * @param data 请求服务器返回的JSON对象
 * @param callback 回调函数，在每加载完一个select都会调用一次，并且回调函数的第一个参数可获得当前遍历到的select的JQuery对象
 * @param headVal 固定添加一个选择框列表头选项
 * @param headName 固定添加一个选择框列表头选项的值
 */
function changeSelectListByIdArray_old(selectIdList, commTypeNames, data, callback, headVal, headName) {
	$(selectIdList).each(function(i) {
		var $select = $("#" + this);
		var oldVal = $select.val();
		$select.children("option").remove();
		if(headVal != undefined && headName != undefined) {
			$select.append($("<option value='" + headVal + "'>" + headName + "</option>"));
		}
		
		var curCtName = commTypeNames[i];
		if(/'\w+'/.test(curCtName)) {
			curCtName = curCtName.substring(1, curCtName.length - 1);
		}
		var ctList = data[curCtName];
		if(curCtName == "root") {
			ctList = data;
		}
		
		$(ctList).each(function() {
			if(!this.id && !this.value) {
				return;
			}
			var $option = $("<option value='" + this.id + "'>" + this.value + "</option>");
			if(this.id == oldVal && this.id) {
				$option[0].selected = true;
			}
			$select.append($option);
		});
		if(callback) {
			callback($select);
		}
	});
}

/**
 * 打开并显示报表页面窗口
 */
function openReportView() {
	open("ReportView?reportId=" + Math.random());
}

/**
 * 将某一JQuery对象下的所有带有“name”属性的元素的value值序列化成表单参数
 * @param $element JQuery对象
 * @param emptyValue 如果emptyValue为true则如果带有“name”属性的元素的value值为false时，value值为emptyValue参数的值，默认emptyValue为空字符串，如果为false则不序列化该$element
 * @param exceptSelector 排除不进行序列化的选择表达式
 * @returns {Array} 表单参数
 */
function serializeParams($element, emptyValue, exceptSelector) {
	var $fields;
	if(exceptSelector) {
		$fields = $element.find(":text, :password, :file, :hidden, select, :checkbox:checked, :radio:checked, textarea").not(exceptSelector);
	} else {
		$fields = $element.find(":text, :password, :file, :hidden, select, :checkbox:checked, :radio:checked, textarea");
	}
	
	var params = [];

	if($fields.length > 0) {
		$fields.each(function() {
			var val = $(this).val();
			if(!val) {
				if(emptyValue == undefined) {
					params.push({name : $(this).attr("name"), value : ""});
				} else if(emptyValue) {
					params.push({name : $(this).attr("name"), value : emptyValue});
				} 
			} else {
				params.push({name : $(this).attr("name"), value : val});
			}
		});
	}
	return params;
}

/**
 * 限制文本输入框的输入长度
 * @param $input 要限制的文本框的JQuery对象
 * @param length 要限制的输入长度
 */
function limitTextInputLength($input, length) {
	$input.keypress(function() {
		var val = this.value;
		if(val.length >= length) {
			$(this).val(val.substring(0, length - 1));
		}
	});
}

/**
 * 在列表checkbox中只能选择一条记录的提示信息
 * @param $cb 要进行验证的checkbox表单的JQuery对象
 * @param noSelectedMsg 当没有选择任何一条记录时显示的提示信息
 * @param multiSelectedMsg 当选择多于一条记录时显示的提示信息
 * @returns {Boolean}
 */
function checkboxUniqueInfo($cb, noSelectedMsg, multiSelectedMsg) {
	if( !($cb.length > 0) ) {
		if(!noSelectedMsg) {
			noSelectedMsg = "请选择一条记录";
		}
		alertMsg.info(noSelectedMsg);
		return false;
	}
	if($cb.length > 1) {
		if(!multiSelectedMsg) {
			multiSelectedMsg = "请只选择一条记录";
		}
		alertMsg.info(multiSelectedMsg);
		return false;
	}
	return true;
}

/**
 * 通用验证方法V2 -- by lyh
 * 一旦发现验证不通过立即跳出并返回false
 * 
 * 示例：
 * var valid = commonValidate({
 * 	//元素id : {//规则}
 * 	"username" : {
 * 		required : true, 
 * 		requiredMsg : "用户名不能为空", 
 * 		pattern : /\d+/,
 * 		patternMsg : "用户名必须为数字",
 * 		minLength : 6,
 * 		minLengthMsg : "用户名最小为6位",
 * 		maxLength : 10,
 * 		maxLengthMsg : "用户名最大为10位",
 * 		lengthMsg : "用户名非法长度"
 * 		msg : "非法用户名"
 * 	},
 *  "xxx" : {
 *  	... : ...
 *  }
 * });
 * 
 * @param rule 规则
 * @returns {Boolean} 当所有验证通过则返回true，否则返回false
 */
function commonValidate(rule) {
	var valid = true;
	$.each(rule, function(rName, rValue) {
		var $e = $("#" + rName);
		
		//空值验证
		if((rValue.required || rValue.required == undefined) && !$e.val()) {
			if(rValue.errorCss || rValue.errorCss == undefined) {
				$e.addClass("ui-state-error");
			}
			if(rValue.requiredMsg) {
				alertMsg.info(rValue.requiredMsg);
			} else {
				alertMsg.info(rValue.msg);
			}
			return valid = false;
		}
		
		//正则验证
		if($e.val() && rValue.pattern) {
			var reg;
			if(rValue.pattern instanceof RegExp) {
				reg = rValue.pattern;
			} else if(typeof rValue.pattern == "string") {
				reg = new RegExp(rValue.pattern);
			}
			if(!reg.test($e.val())) {
				if(rValue.errorCss || rValue.errorCss == undefined) {
					$e.addClass("ui-state-error");
				}
				if(rValue.patternMsg) {
					alertMsg.info(rValue.patternMsg);
				} else {
					alertMsg.info(rValue.msg);
				}
				return valid = false;
			}
		}
		
		//最小长度验证
		if(rValue.minLength) {
			if($e.val().length < rValue.minLength) {
				if(rValue.errorCss || rValue.errorCss == undefined) {
					$e.addClass("ui-state-error");
				}
				if(rValue.minLengthMsg) {
					alertMsg.info(rValue.minLengthMsg);
				} else if(rValue.lengthMsg) {
					alertMsg.info(rValue.lengthMsg);
				} else {
					alertMsg.info(rValue.msg);
				}
				return valid = false;
			}
		}
		
		//最大长度验证
		if(rValue.maxLength) {
			if($e.val().length > rValue.maxLength) {
				if(rValue.errorCss || rValue.errorCss == undefined) {
					$e.addClass("ui-state-error");
				}
				if(rValue.maxLengthMsg) {
					alertMsg.info(rValue.maxLengthMsg);
				} else if(rValue.lengthMsg) {
					alertMsg.info(rValue.lengthMsg);
				} else {
					alertMsg.info(rValue.msg);
				}
				return valid = false;
			}
		}
		
		$e.removeClass("ui-state-error");
	});
	return valid;
}

/**
 * 按照指定的路径提交表单
 * @param $form
 * @param url
 */
function submitForm($form, url) {
	var action = $form.attr("action");
	if(url) {
		$form.attr("action", url);
	}
	$form.submit();
	if(url) {
		$form.attr("action", action);
	}
}