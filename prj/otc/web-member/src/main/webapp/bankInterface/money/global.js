<!--
var constJanuary = "一月";
var constFebrary = "二月";
var constMarch = "三月";
var constApril = "四月";
var constMay = "五月";
var constJune = "六月";
var constJuly = "七月";
var constAugust = "八月";
var constSeptember = "九月";
var constOctober= "十月";
var constNovember = "十一月";
var constDecember = "十二月";

var constMonday = "一";
var constTuesday = "二";
var constWednesday = "三";
var constThursday = "四";
var constFriday = "五";
var constSaturday = "六";
var constSunday = "日";

var constLastYear = "上一年";
var constNextYear = "下一年";
var constLastMonth = "上个月";
var constNextMonth = "下个月";
var constToday = "今天";
//-->
//-----------------------
// xForm v2.0
// Developer: bao yilei
// Nov, 2001
//-----------------------

var _activeElement=null;
var _activeEditor=null;
var _activeTable=null;
var _dropdown_window=null;

var _app_id="", _page_id="";

var _document_loading=false;
var _stored_element=null;
var _array_dataset=new Array();
var _tabpage_list=new Array();

var _skip_activeChanged=false;

<!-- 禁止右键功能 -->
/*document.oncontextmenu = function(){return false;}*/


function getIEVersion(){
	var index=window.clientInformation.userAgent.indexOf("MSIE");
	if (index<0){
		return "";
	}
	else{
		return window.clientInformation.userAgent.substring(index+5, index+8);
	}
}

function getRowByCell(cell){
	return cell.parentElement;
}

function getTableByCell(cell){
	var tbody=getRowByCell(cell).parentElement;
	if (tbody) return tbody.parentElement;
}

function getTableByRow(row){
	var tbody=row.parentElement;
	if (tbody) return tbody.parentElement;
}

function getElementEventName(element, eventName){
	var result="";
	if (element.attrib!="dockeditor")
		result=element.id+"_"+eventName;
	else{
		var holder=element.editorHolder;
		if (holder) result=holder.id+"_"+eventName;
	}
	return result;
}

function isUserEventDefined(function_name){
	if (function_name=="") return false;
	var result;
	eval("result=(typeof("+function_name+")!=\"undefined\");");
	return result;
}

function fireUserEvent(function_name, param){
	var result;
	var paramstr="";
	for(i=0; i<param.length; i++){
		if (i==0)
		 	paramstr="param["+i+"]";
		 else
		 	paramstr=paramstr+",param["+i+"]";
	}

	if (isUserEventDefined(function_name))
		eval("result="+function_name+"("+paramstr+");");
	return result;
}

function processActiveElementChanged(activeElement){

	function isChildofTable(obj) {
		var result=null;
		var tmpObj;

		if (obj.getAttribute("attrib")=="dockeditor")
			tmpObj=obj.editorHolder;
		else
			tmpObj=obj;

		if (tmpObj.getAttribute("attrib")=="tablecell") result=getTableByCell(tmpObj);
		return result;
	}

	function set_activeEditor(editor){
		if (_activeEditor!=editor){
			if (_activeEditor){
				if (needUpdateEditor){
					if (_activeEditor.window==window)
						updateEditorInput(_activeEditor);
					else
						_activeEditor.window.updateEditorInput(_activeEditor);
				}
				if (typeof(hideDropDownBtn)!="undefined") hideDropDownBtn();
				
				switch (_activeEditor.getAttribute("attrib")){
					case "editor":{
						_activeEditor.className="editor";
						break;
					}
					case "dockeditor":{
						hideDockEditor(_activeEditor);
						break;
					}
				}
				_activeEditor.use_keyField=false;
				refreshElementValue(_activeEditor);
			}

			if (editor && !editor.readOnly){
				var field=getElementField(editor);

				if (editor.getAttribute("attrib")=="editor"){
					editor.className="active_editor";
					if (field){
						editor.dataType=field.dataType;
						editor.editorType=field.editorType;
					}
				}

				if (field) editor.maxLength=(field.size>0)?field.size:2147483647;
				if (editor.getAttribute("dataType")=="date" || editor.getAttribute("dataType")=="datetime")
					editor.dropDown_mode="date";

				editor.contentEditable=(!isTrue(editor.getAttribute("dropDown_fixed")));

				editor.use_keyField=true;
				refreshElementValue(editor);

				if (!isTrue(editor.getAttribute("dropDown_fixed")) && !compareText(editor.type, "checkbox")) editor.select();
				if (typeof(showDropDownBtn)!="undefined"){
					showDropDownBtn(editor);
					if (isTrue(editor.getAttribute("autoDropDown"))){ showDropDownBox(editor);}
				}
			}

			_activeEditor=editor;
		}
	}

	function processElementBlur(){
		var doblur=(activeElement!=_activeEditor);

		if (_activeElement){
			if (typeof(_dropdown_btn)!="undefined" && _dropdown_btn){
				doblur=doblur && (_activeElement!=_dropdown_btn) &&
					(activeElement!=_dropdown_btn);
			}

			if (typeof(_dropdown_box)!="undefined" && _dropdown_box){
				var editor=_dropdown_box.editor;
				doblur=doblur && (activeElement!=editor) &&
					(!isChild(activeElement, _dropdown_box));
			}

			if (doblur){
				if (_activeEditor && _activeEditor.dropDown_visible){
					if (typeof(hideDropDownBox)!="undefined") hideDropDownBox();
					hideStatusLabel(window);
				}				
				set_activeEditor(null);
			}
		}
		else{
			doblur=false;
		}

		if (activeElement==document.body && _skip_activeChanged){
			_skip_activeChanged=false;
			return;
		}

		if ((doblur || !_activeEditor)){
			var activeTable=isChildofTable(activeElement);
			if (_activeTable!=activeTable){
				if (_activeTable){
					_activeTable.focused=false;

					var row=_activeTable.activeRow;
					if (row) refreshTableRowStyle(row);

					var eventName=getElementEventName(_activeTable, "onBlur");
					fireUserEvent(eventName, [_activeTable]);
				}

				_activeTable=activeTable;

				if (_activeTable){
					_activeTable.focused=true;

					var row=_activeTable.activeRow;
					if (row) refreshTableRowStyle(row);

					var eventName=getElementEventName(_activeTable, "onFocus");
					fireUserEvent(eventName, [_activeTable]);
				}
			}
		}
	}

	try{
		if (window.closed) return;
		if (activeElement==_activeElement) return;

		if (activeElement){
			processElementBlur();

			switch (activeElement.getAttribute("attrib")){
				case "tablecell":{
					var row=getRowByCell(activeElement);
					var table=getTableByRow(row);
					var dataset=getElementDataset(activeElement);

					table._activeCell=activeElement;
					table._activeCellIndex=activeElement.cellIndex;
					if (row.record){
						if (dataset.window==window)
							_dataset_setRecord(dataset, row.record);
						else
							dataset.window._dataset_setRecord(dataset, row.record);
					}
					setActiveTableCell(row, activeElement.cellIndex);
					break;
				}
				case "editor":;
				case "dockeditor":{
					set_activeEditor(activeElement);
					break;
				}
			}
		}
		_activeElement=activeElement;
	}
	catch(e){
		processException(e);
	}
}

function _document_onpropertychange() {
	if (event.propertyName=="activeElement")
		processActiveElementChanged(document.activeElement);
}

function _document_onkeydown(){
	switch (event.keyCode){
		case 123:{
			if (_app_id && _page_id){
				showModalDialog(xForm_Library_path+"tool/property.asp?app_id="+_app_id+"&page_id="+_page_id+"&modal_mode=1", document,
						"dialogHeight: 440px; dialogWidth: 600px; center: Yes; help: No; resizable: Yes; status: No");
			}
			break;
		}
	}

}

function _document_oncontextmenu(){
	event.returnValue=(!disableSystemContextMenu);
	if (typeof(_array_menu)=="undefined") return;
	for(var i=0; i<_array_menu.length; i++){
		var strHolders=_array_menu[i].popupHolders;
		if (getValidStr(strHolders)!=""){
			var arrayHolder=strHolders.split(",");
			for(var j=0; j<arrayHolder.length; j++){
				if (arrayHolder[j]=="") continue;
				var needPopup;
				eval("needPopup=isChild(event.srcElement,"+arrayHolder[j]+")");
				if (needPopup){
					showPopupMenu(_array_menu[i]);
					event.returnValue=false;
					return;
				}
			}
		}
	}
}

function _control_onkeydown() {

	function getCell(element){
		if (element.getAttribute("attrib")=="tablecell")
			return element;
		else if (element.in_table)
			return element.editorHolder;
	}

	function processTab(element){
		var obj=null;
		if (element.in_table){
			obj=element.editorHolder;
		}
		else{
			obj=element;
		}
		if (!obj) return;
		if (event.shiftKey)
			obj=getPriorTabElement(obj);
		else
			obj=getNextTabElement(obj);

		if (obj){
			try{
				obj.focus();
				event.returnValue=false;
			}
			catch(e){
				event.returnValue=true;
			}
		}
	}

	element=event.srcElement;
	if (isDropdownBoxVisible()){
		if (_dropdown_window) _dropdown_window.processDropDownKeyDown(event.keyCode);
		event.returnValue=true;
	}
	else{
		var rowindex, colindex;
		switch (event.keyCode) {
			//Tab
			case 9:{
				processTab(element);
				break;
			}
			//Enter
			case 13:{
				if (!compareText(element.tagName, "textarea") || event.shiftKey || event.ctrlKey || event.altKey){
					var cell=getCell(element);
					if (cell && !event.shiftKey){
						var row=getRowByCell(cell);
						var table=getTableByRow(row);
						var maxIndex=checkTableCellIndex(table, 9999, 9999);
						if (row.rowIndex==maxIndex[0] && cell.cellIndex==maxIndex[1] && !isTrue(table.getAttribute("readOnly"))){
							var dataset=getElementDataset(element);
							dataset.insertRecord("end");
							dataset.modified=false;
							setActiveTableCell(table.activeRow, 0);
						}
						else
							processTab(element);
					}
				}
				break;
			}
			//ESC
			case 27:{
				if (!element.modified){
					var dataset=getElementDataset(element);
					if (!dataset || dataset.state=="none") break;

					var cell=getCell(element);
					if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
						if (isTrue(getTableByCell(cell).getAttribute("confirmCancel"))){
							if (confirm(constDatasetConfirmCancel)){
								dataset.cancelRecord();
							}
						}
						else{
							dataset.cancelRecord();
						}
					}
				}
				else{
					setElementValue(element, element.old_value);
				}
				event.returnValue=false;
				break;
			}
			//Left
			case 37:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var table=getTableByCell(cell);
						var rowIndex=getRowByCell(cell).rowIndex;
						var cellIndex=cell.cellIndex;
						cellIndex--;
						setFocusTableCell(table, rowIndex, cellIndex);
						event.returnValue=false;
					}
				}
				break;
			}
			//Up
			case 38:{
				var cell=getCell(element);
				if (cell){
					var dataset=getElementDataset(element);
					if (dataset){
						dataset.movePrev();
						event.returnValue=false;
					}
				}
				else{
					switch (element.getAttribute("dropDown_mode")){
						case "dataset":{
							var tempDataset=element.getAttribute("dropDown_dataset");
							if (typeof(tempDataset)=="string") tempDataset=getDatasetByID(tempDataset);
							if (tempDataset) tempDataset.movePrev();
							processDropDownSelected(element, tempDataset.record, true);
							event.returnValue=false;
							break;
						}
					}
				}
				break;
			}
			//Right
			case 39:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var table=getTableByCell(cell);
						var rowIndex=getRowByCell(cell).rowIndex;
						var cellIndex=cell.cellIndex;
						cellIndex++;
						setFocusTableCell(table, rowIndex, cellIndex);
						event.returnValue=false;
					}
				}
				break;
			}
			//Down
			case 40:{
				if (event.altKey){
					showDropDownBox(element);
				}
				else{
					var cell=getCell(element);
					if (cell){
						var table=getTableByCell(cell);
						var dataset=getElementDataset(element);
						if (dataset){
							dataset.moveNext();
							if (dataset.eof && !isTrue(table.getAttribute("readOnly")) && !isTrue(dataset.readOnly)){
								dataset.insertRecord("end");
								dataset.modified=false;
							}
							event.returnValue=false;
						}
					}
					else{
						switch (element.getAttribute("dropDown_mode")){
							case "dataset":{
								var tempDataset=element.getAttribute("dropDown_dataset");
								if (typeof(tempDataset)=="string") tempDataset=getDatasetByID(tempDataset);
								if (tempDataset) tempDataset.moveNext();
								processDropDownSelected(element, tempDataset.record, true);
								event.returnValue=false;
								break;
							}
						}
					}
				}
				break;
			}
			//Insert
			case 45:{
				var cell=getCell(element);
				if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
					var dataset=getElementDataset(element);
					if (!isTrue(dataset.readOnly)){
						dataset.insertRecord("before");
						dataset.modified=false;
					}
				}
				break;
			}
			//Delete
			case 46:{
				var cell=getCell(element);
				if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
					if (event.ctrlKey){
						if (isTrue(getTableByCell(cell).getAttribute("confirmDelete"))){
							var dataset=getElementDataset(element);
							if (!isTrue(dataset.readOnly) && confirm(constDatasetConfirmDelete)){
								dataset.deleteRecord();
							}
						}
						else{
							var dataset=getElementDataset(element);
							dataset.deleteRecord();
						}
						event.returnValue=false;
					}
				}
				break;
			}
			//F2
			case 113:;
			//F7
			case 118:{
				showDropDownBox(element);
				break;
			}
		}
	}
}

function getAbsPosition(obj, offsetObj){
	var _offsetObj=(offsetObj)?offsetObj:document.body;
	var x=obj.offsetLeft;
	var y=obj.offsetTop;
	var tmpObj=obj.offsetParent;

	while ((tmpObj!=_offsetObj) && tmpObj){
		x+=tmpObj.offsetLeft+tmpObj.clientLeft-tmpObj.scrollLeft;
		y+=tmpObj.offsetTop+tmpObj.clientTop-tmpObj.scrollTop;
		tmpObj=tmpObj.offsetParent;
	}
	return ([x, y]);
}

function isChild(obj, parentObj) {
	var tmpObj=obj;
	var result=false;
	if (parentObj) {
		while (tmpObj) {
			if (tmpObj==parentObj){
				result=true;
				break;
			}
			tmpObj=tmpObj.parentElement;
		}
	}
	return result;
}

function disableDocument(window){
	if (typeof(_over_label)=="undefined"){
		document.body.insertAdjacentHTML("beforeEnd", "<div id=_over_label language=javascript"+
			" style=\"position: absolute; background-color: black; left:0; top:0; z-index: 9999; filter:alpha(opacity=30)\"></div>");
	}

	document.body._documentDisabled=true;
	_over_label.style.width=document.body.clientWidth + document.body.scrollLeft;
	_over_label.style.height=document.body.clientHeight + document.body.scrollTop;
	_over_label.style.visibility="visible";
}

function enableDocument(window){
	_over_label.style.visibility="hidden";
	document.body._documentDisabled=false;
}

function isDocumentEnable(){
	return (!isTrue(document.body._documentDisabled));
}

function initElementDataset(element){
	var dataset=element.getAttribute("dataset");
	if (dataset) setElementDataset(element, dataset);
}

function initElement(element){
	var initChildren=true;
	var _attrib=element.getAttribute("attrib");
	if (_attrib){
		switch (_attrib){
			case "fieldlabel":{
				if (!element.className) element.className=_attrib;

				var dataset;
				var _dataset=element.getAttribute("dataset");
				if (typeof(_dataset)=="string"){
					dataset=getDatasetByID(_dataset);
				}
				else{
					dataset=_dataset;
				}
				element.dataset=dataset;
				refreshElementValue(element);
				break;
			}
			case "columnheader":{
				if (!element.className) element.className=_attrib;
				element.noWrap=true;
				element.onclick=_table_head_onclick;
				element.onmouseover=_table_head_onmouseover;
				element.onmouseout=_table_head_onmouseout;
				refreshElementValue(element);
				break;
			}
			case "columnfooter":{
				if (!element.className) element.className=_attrib;
				refreshElementValue(element);
				break;
			}
			case "datalabel":{
				if (!element.className) element.className=_attrib;
				initElementDataset(element);
				break;
			}
			case "editor":;
			case "dockeditor":{
				if (!element.className) element.className=_attrib;
				if (getValidStr(element.getAttribute("dropdown_cached"))=="" && getIEVersion()>"5.0")
					element.dropdown_cached=true;

				initElementDataset(element);
				with (element){
					if (tagName.toLowerCase()=="input" && compareText(type, "checkbox")){
						style.borderColor="window";
						onclick=_checkbox_onclick;
					}

					language="javascript";
					onkeydown=_control_onkeydown;
					onkeypress=_editor_onkeypress;
					onpropertychange=_editor_onpropertychange;
				}
				break;
			}
			case "datatable":{
				if (isTrue(element.getAttribute("isDropDownTable"))){
					if (!element.className) element.className="dropdowntable";
				}
				else{
					if (!element.className) element.className="datatable";
				}

				initElementDataset(element);
				initDataTable(element, !isTrue(element.getAttribute("skipRebuild")));
				element.onkeydown=_control_onkeydown;
				initChildren=false;
				break;
			}
			case "tablecell":{
				if (!element.className) element.className=_attrib;
				break;
			}
			case "datapilot":{
				if (!element.className) element.className=_attrib;
				initElementDataset(element);
				initDataPilot(element);
				initChildren=false;
				break;
			}
			case "button":{
				if (!element.className) element.className=_attrib;

				element.hideFocus=true;
				setButtonDown(element, element.getAttribute("down"))
				element.onmousedown=_button_onmousedown;
				element.onmouseover=_button_onmouseover;
				element.onmouseout=_button_onmouseout;
				break;
			}
			case "tree":{
				if (!element.className) element.className=_attrib;
				initTree(element);
				initChildren=false;
				break;
			}
			case "tabpage":{
				if (!element.className) element.className=_attrib;
				initTabPage(element);
				initChildren=false;
				break;
			}
			case "datascrollbar":{
				if (!element.className) element.className=_attrib;
				initElementDataset(element);
				element.onlosecapture=_scrollbar_change;
				break;
			}
			default:{
				if (element.className &&_attrib) element.className=_attrib;
				break;
			}
		}

		element.window=window;
		fireUserEvent("document_onInitElement", [element, _attrib]);
	}
	return initChildren;
}

function initElements(element){
	if (compareText(element.getAttribute("attrib"), "tabpage")){
		_tabpage_list[_tabpage_list.length]=element;
	}
	else{
		if (!initElement(element)) return;
	}

	for (var i=0; i<element.children.length; i++){
		initElements(element.children[i]);
	}
}

function uninitElement(element){
	var _attrib=element.getAttribute("attrib");
	switch (_attrib){
		case "datalabel":;
		case "editor":;
		case "dockeditor":;
		case "datatable":;
		case "tablecell":;
		case "datapilot":;
		case "datascrollbar":{
			if (typeof(setElementDataset)!="undefined") setElementDataset(element, null);
			break;
		}
	}
}

function uninitElements(element){
	for(var i=0; i<_array_dataset.length; i++){
		var dataset=_array_dataset[i];
		if (dataset.window==window) dataset.setMasterDataset(null);
	}

	if (!element) element=document.body;

	for (var i=0; i<element.children.length; i++){
		uninitElements(element.children[i]);
	}
	uninitElement(element);
}

function _window_onunload() {
	uninitElements(document.body);
}

function initTabPages(){
	for (var i=0; i<_tabpage_list.length; i++){
		initElement(_tabpage_list[i]);
	}
}

function initDocument(){
	if (getIEVersion()<"5.0"){
		alert(constErrUnsupportBrowser);
		return;
	}

	_document_loading=true;
	try{
		with (document){
			for(var i=0; i<_array_dataset.length; i++){
				initDataset(_array_dataset[i]);
			}

			if (typeof(_setElementsProperties)!="undefined") _setElementsProperties();
			initElements(body);

			for(var i=0; i<_array_dataset.length; i++){
				var dataset=_array_dataset[i];
				if (dataset.masterDataset && dataset.masterLinks){
					dataset.setMasterDataset(dataset.masterDataset, dataset.masterLinks, dataset.detailSql);
				}
				dataset.refreshControls();
			}

			setTimeout("initTabPages()", 0);

			language="javascript";
			onpropertychange=_document_onpropertychange;
			onkeydown=_document_onkeydown;
			//oncontextmenu=_document_oncontextmenu;
		}
		if (!window.onunload) window.onunload=_window_onunload;

		if (typeof(sizeDockEditor)!="undefined") setInterval("adjustControlsSize();", 300);
		
		processActiveElementChanged(document.activeElement);
	}
	finally{
		_document_loading=false;
	}
}

var _ad_box=null;
var _ad_interval=50;
var _ad_count=_ad_interval;

function adjustControlsSize(){
	function showAD(){
		_ad_count++;
		if (!_ad_box || _ad_count>_ad_interval){
			if (_ad_box) _ad_box.removeNode(true);
			_ad_box=document.createElement("<A target='_blank' href='#' style='color: blue; font-size: 9pt; position: absolute'></A>");
			_ad_box.innerText="";
			document.body.appendChild(_ad_box);
			
			_ad_count=0;
			_ad_interval=45+Math.random()*10;
		}
		_ad_box.style.left=document.body.clientWidth+document.body.scrollLeft-_ad_box.offsetWidth-6;	
		_ad_box.style.top=document.body.clientHeight+document.body.scrollTop-_ad_box.offsetHeight-3;
	}
	
	if (typeof(sizeDockEditor)!="undefined"){
		sizeDockEditor();
		if (typeof(sizeDropDownBtn)!="undefined" && _activeEditor) sizeDropDownBtn(_activeEditor);
		if (typeof(sizeDropDownBox)!="undefined") sizeDropDownBox();
	}
	
	showAD();
}

function getPriorTabElement(obj){
	var i=obj.sourceIndex-1;
	var elementCount=document.all.length
	var tmpObj, result=null;
	while (i>=0){
		tmpObj=document.all[i];
		if ((tmpObj!=obj && tmpObj.isTextEdit && tmpObj.tabIndex!=-1 && !tmpObj.disabled && !tmpObj.readOnly)
			|| tmpObj.getAttribute("attrib")=="tablecell"){
			result=document.all[i];
			break;
		}
		i--;
	}
	return result;
}

function getNextTabElement(obj){
	var i=obj.sourceIndex+1;
	var elementCount=document.all.length
	var tmpObj, result=null;
	while (i<elementCount){
		tmpObj=document.all[i];
		if ((tmpObj!=obj && tmpObj.isTextEdit && tmpObj.tabIndex!=-1 && !tmpObj.disabled && !tmpObj.readOnly)
			|| tmpObj.getAttribute("attrib")=="tablecell"){
			result=document.all[i];
			break;
		}
		i++;
	}
	return result;
}

function getElementDataset(element){
	switch (element.getAttribute("attrib")){
		case "tablecell":{
			var table=getTableByCell(element);
			if (table){
				return table.getAttribute("dataset");
			}
			break;
		}
		case "tablerow":{
			var table=getTableByRow(element);
			if (table){
				return table.getAttribute("dataset");
			}
			break;
		}
		case "dockeditor":{
			var holder=element.editorHolder;
			if (holder){
				return getElementDataset(holder);
			}
			break;
		}
		default:{
			return element.getAttribute("dataset");
			break;
		}
	}
}

function getElementField(element){
	var dataset=getElementDataset(element);
	if (!dataset) return;
	return dataset.getField(element.getAttribute("dataField"));
}

function getElementValue(element){
	var eventName=getElementEventName(element, "onGetValue");
	if (isUserEventDefined(eventName)){
		var event_result=fireUserEvent(eventName, [element, value]);
		return event_result;
	}

	switch (element.getAttribute("attrib")){
		case "editor":;
		default:{
			return element.value;
			break;
		}
	}
}

function setElementValue(element, value){

	function getEditorValue(element, value){
		if (compareText(element.getAttribute("dropDown_mode"), "staticlist")
			&& isTrue(element.getAttribute("dropDown_mapValue"))){
			element.keyValue=value;

			var result="";
			var items=getDropDownItems(element);
			if (items){
				var item=items.find(["value"], [value]);
				if (item) result=item.getFieldText("name");
			}
			return result;
		}
		else
			return getValidStr(value);
	}

	switch (element.getAttribute("attrib")){
		case "fieldlabel":;
		case "columnfooter":;
		case "columnheader":{
			var eventName=getElementEventName(element, "onSetValue");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}
			element.innerHTML=value;
			break;
		}

		case "datalabel":{
			var eventName=getElementEventName(element, "onSetValue");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}
			element.innerText=value;
			break;
		}

		case "editor":;
		case "dockeditor":{
			var eventName=getElementEventName(element, "onSetValue");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}

			switch (element.type.toLowerCase()){
				case "checkbox":{
					element.checked=isTrue(value);
					break;
				}
				default:{
					element.value=getEditorValue(element, value);
					break;
				}
			}
			break;
		}

		case "tablecell":{
			var eventName=getElementEventName(element, "onSetValue");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}

			var tmpHTML;
			switch (element.getAttribute("editorType")){
				case "checkbox":{
					if (isTrue(value)){
						tmpHTML="<font face=Marlett size=2>a</font>";
					}
					else{
						tmpHTML="<font face=Webdings size=1 color=silver>c</font>";
					}
					element.innerHTML=tmpHTML;
					break;
				}
				default:{
					tmpHTML=getEditorValue(element, value);
					if (tmpHTML=="") tmpHTML=" ";
					element.innerText=tmpHTML;
				}
			}
			break;
		}

		case "treenode":{
			var node=element.node;
			var canceled=false;
			var eventName=getElementEventName(getTableByCell(element), "onSetValue");
			if (isUserEventDefined(eventName)){
				canceled=(!fireUserEvent(eventName, [element, value]));
			}
			if (!canceled) element.innerHTML=value;

			var tmpHTML="";
			if (node.imageUrl){
				if (node.hasChild && node.expanded && node.expandImageUrl)
					tmpHTML="<img src=\""+node.expandImageUrl+"\" style=\"margin-right: 4px\">";
				else
					tmpHTML="<img src=\""+node.imageUrl+"\" style=\"margin-right: 4px\">";
				element.insertAdjacentHTML("afterBegin", tmpHTML);
			}

			var record=node.data;
			var button
			if (node.hasChild){
				var button_img=(node.expanded)?"collapse.gif":"expand.gif";
				button=document.createElement("<img id=_button_expand hideFocus=true src=\""+xForm_Library_path+"image/"+button_img+"\" width=11px height=11px"+
					" language=javascript onclick=\"return _tree_expendclick(this);\" style=\"cursor: hand; margin-top: 3px; margin-left: 4px; margin-right: 4px\">");

				button.treenode=element;
				element.insertAdjacentElement("afterBegin", button);
			}
			else{
				element.insertAdjacentHTML("afterBegin", "<img id=_button_expand hideFocus=true src=\""+xForm_Library_path+"image/NoChild.gif\" width=11px height=11px"+
					" style=\"cursor: hand; margin-top: 3px; margin-left: 4px; margin-right: 4px\">");
			}

			tmpHTML="";
			element.button=button;
			for(var i=1; i<node.level; i++){
				tmpHTML+="????"
			}
			element.insertAdjacentHTML("afterBegin", tmpHTML);
			break;
		}

		case "datascrollbar":{
			element.Value=value;
			element.old_value=element.Value;
			break;
		}

		default:{
			element.value=value;
		}
	}
}

function refreshElementValue(element){
	var dataset;
	var _attrib=element.getAttribute("attrib");

	switch (_attrib){
		case "fieldlabel":{
			var label=element.getAttribute("dataField");
			var field=getElementField(element);
			if (field){
				label=field.label;
				if (isTrue(field.notNull) && !isTrue(field.readOnly) && !isTrue(field.dataset.readOnly)){
						label="<font color=red>*</font>"+label;
				}
			}
			setElementValue(element, label);
			break;
		}

		case "columnheader":;
		case "columnfooter":{
			var label=getValidStr(element.getAttribute("label"));
			var field=getElementField(element);
			if (label==""){
				if (field){
					label=field.label;
					if (isTrue(field.notNull) && !isTrue(field.readOnly) && !isTrue(field.dataset.readOnly)){
							label="<font color=red>*</font>"+label;
					}
				}
				else{
					label=getValidStr(element.getAttribute("dataField"));
				}
			}
			setElementValue(element, label);
			break;
		}

		case "tablecell":{
			var row=getRowByCell(element);
			var record=row.record;
			var dataField=element.getAttribute("dataField");
			if (dataField=="select") break;

			if (record)
				setElementValue(element, record.getFieldText(dataField));
			else
				setElementValue(element, "");
			break;
		}

		case "treenode":{
			var node=element.node;

			if (node)
				setElementValue(element, node.label);
			else
				setElementValue(element, "");
			break;
		}

		case "datascrollbar":{
			dataset=getElementDataset(element);

			if (dataset){
				if (dataset.bof){
					setElementValue(element, element.Min);
				}
				else if (dataset.eof){
					setElementValue(element, element.Max);
				}
				else{
					setElementValue(element, element.Max/2);
				}
				element.old_value=getElementValue(element);
			}
			break;
		}

		default:{
			dataset=getElementDataset(element);

			var value="";
			if (dataset){
				var fieldName;
				if (element.use_keyField && element.getAttribute("keyField")){
					fieldName=element.getAttribute("keyField");
					if (fieldName && dataset.getField(fieldName))
						value=dataset.getFieldText(fieldName);
					else if (element.keyValue)
						value=element.keyValue;
				}
				else{
					fieldName=element.getAttribute("dataField");
					if (fieldName) value=dataset.getFieldText(fieldName);
				}

				setElementValue(element, value);
			}			
			element.old_value=getElementValue(element);
			element.modified=false;
			break;
		}
	}
}

function refreshButtonColor(button){
	if (isTrue(button.getAttribute("down"))){
		button.className="button_down";
		button.style.backgroundColor="#fff1da";
	}
	else{
		button.className="button";
		button.style.backgroundColor="#d4d0c8";
	}
}

function setButtonDown(button, down){
	button.down=isTrue(down);
	refreshButtonColor(button);
}

function _button_onmousedown(){
	var button=event.srcElement;
	var menu=button.getAttribute("menu");

	if (typeof(menu)=="string" && menu!=""){
		eval("menu="+menu);
		button.menu=menu;
	}

	if (menu){
		showPopupMenu(menu, button);
	}
}

function _button_onmouseover(){
	try{
		var button=event.srcElement;
		if (button.disabled) return;
		button.style.backgroundColor="white";

		if (_menu_frame){
			var old_button=_menu_frame.button;
			if (old_button){
				var menu=button.getAttribute("menu");

				if (typeof(menu)=="string"){
					eval("menu="+menu);
					button.menu=menu;
				}

				if (button==old_button){
					clearTimeout(_menu_frame.timeout_id);
				}
				else if (menu){
					showPopupMenu(menu, button);
					button.focus();
				}
			}
		}
	}
	catch(e){
		//do nothing
	}
}

function _button_onmouseout(){
	try{
		var button=event.srcElement;
		if (button.disabled) return;
		refreshButtonColor(button);

		if (button.menu_opened) hidePopupMenu();
	}
	catch(e){
		//do nothing
	}
}

function _scrollbar_change(){
	var scrollbar=event.srcElement;
	var dataset=scrollbar.getAttribute("dataset");
	var oldValue=scrollbar.old_value;

	if (scrollbar.Value!=oldValue){
		dataset.move(scrollbar.Value-oldValue);
		refreshElementValue(scrollbar);
	}
}

function getDropDownItems(editor){
	var items=editor._dropDown_items;
	if (!items){
		initDropDownItems(editor);
		items=editor._dropDown_items;
	}
	return items;
}

function setDropDownItems(editor, items){
	editor.dropDown_items=items;
	editor._dropDown_items=null;
}

function _initDropDownItems(itemsStr, mapValue){
	if (!itemsStr) return null;
	var splitStr=";";
	var arrayItem=createDataset();
	arrayItem.id="_dropDown_items";
	arrayItem.readOnly=true;

	if (mapValue){
		var field;
		field=arrayItem.addField("name");
		field=arrayItem.addField("value");
		field.visible=false;

		var tmp=itemsStr.split(splitStr);
		var index;
		for (var i=0; i<tmp.length; i++ ){
			index=tmp[i].indexOf("=");
			record=new Array();
			record[0]=getDecodeStr(tmp[i].substr(0, index));
			record[1]=getDecodeStr(tmp[i].substr(index+1));
			pArray_insert(arrayItem, "end", null, record);
		}

	}
	else{
		arrayItem.addField("value");

		var tmp=itemsStr.split(splitStr);
		for (var i=0; i<tmp.length; i++ ){
			record=new Array();
			record[0]=getDecodeStr(tmp[i]);
			pArray_insert(arrayItem, "end", null, record);
		}
	}
	return arrayItem;
}

function initDropDownItems(editor){
	var dropDown_items=editor.getAttribute("dropDown_items");
	if (!dropDown_items) return;
	var items=_initDropDownItems(dropDown_items, isTrue(editor.getAttribute("dropDown_mapValue")));
	if (!items) return;
	initDataset(items);
	editor._dropDown_items=items;
}

function isDropdownBoxVisible(){
	if (typeof(_dropdown_box)!="undefined" && _dropdown_box)
		return (_dropdown_box.style.visibility=="visible")
	else
		return false;
}

function getStatusLabel(text){
	if (typeof(_status_label)=="undefined"){
		document.body.insertAdjacentHTML("beforeEnd", "<DIV id=_status_label nowrap style=\"position: absolute; visibility: hidden;"+
			" padding-left: 16px; padding-right: 16px; height: 22px; font-size: 9pt; background-color: #e0f0ff; border: 1 solid silver; padding-top:3; z-index: 10000\"></DIV>");
	}
	_status_label.innerHTML=text;
}

function showStatusLabel(parent_window, text, center){
	parent_window.getStatusLabel(text);
	parent_window._status_label.style.visibility="visible";
	if (center){
		parent_window._status_label.style.posLeft=(document.body.clientWidth - _status_label.offsetWidth) / 2;
		parent_window._status_label.style.posTop=(document.body.clientHeight - _status_label.offsetHeight) / 2;
		parent_window.document.onmousemove=null;
	}
	else{		
		parent_window._document_onmousemove();
		parent_window.document.onmousemove=parent_window._document_onmousemove;
	}
}

function hideStatusLabel(parent_window){
	if (!parent_window.closed && parent_window._status_label){
		parent_window.document.onmousemove=null;
		parent_window._status_label.style.visibility="hidden";
	}
}

function _document_onmousemove(){
	locateStatusLabel(event.x + document.body.scrollLeft+1, event.y + document.body.scrollTop+1);
}

function locateStatusLabel(x, y){
	if (x==0 && y==0) return;

	var posX=document.body.clientWidth + document.body.scrollLeft - _status_label.offsetWidth;
	var posY=document.body.clientHeight + document.body.scrollTop - _status_label.offsetHeight;
	posX=(x<posX)?x:posX;
	posY=(y<posY)?y:posY;

	_status_label.style.posLeft=posX + 1;
	_status_label.style.posTop=posY + 1;
}

function initDataPilot(dataPilot){
	if (!dataPilot.getAttribute("pageSize")){
		var dataset=getElementDataset(dataPilot);
		if (dataset) dataPilot.pageSize=dataset.pageSize;
	}
	var pageSize=dataPilot.getAttribute("pageSize");

	for(i=0; i<dataPilot.children.length; i++){
		dataPilot.children[i].removeNode(true);
	}

	var buttons_str=getValidStr(dataPilot.getAttribute("buttons"));
	if (buttons_str=="" || compareText(buttons_str, "default"))
		buttons_str="movefirst,prevpage,moveprev,movenext,nextpage,movelast,appendrecord,deleterecord,cancelrecord,updaterecord";
	else if (compareText(buttons_str, "readonly"))
		buttons_str="movefirst,prevpage,moveprev,movenext,nextpage,movelast";
	buttons_str=buttons_str.toLowerCase();
	var buttons=buttons_str.split(",");

	var row=dataPilot.insertRow();
	row.align="center";
	for(i=0; i<buttons.length; i++){
		btn=document.createElement("<input type=button class=button hideFocus=true style=\"height: 22px\">");

		btn.tabIndex=-1;
		btn.onmouseover=_button_onmouseover;
		btn.onmouseout=_button_onmouseout;
		btn.onclick=_datapilot_onclick;

		btn.dataset=dataPilot.getAttribute("dataset");
		btn.buttonType=buttons[i];
		btn.datapiolt=dataPilot;

		switch(buttons[i]){
			case "movefirst":{
				btn.style.fontFamily="Webdings";
				btn.value="9";
				btn.title=constDatasetMoveFirst;
				btn.style.width=30;
				break;
			}
			case "prevpage":{
				btn.style.fontFamily="Webdings";
				btn.value="7";
				btn.title=constDatasetPrevPage;
				btn.style.width=30;
				break;
			}
			case "moveprev":{
				btn.style.fontFamily="Webdings";
				btn.value="3";
				btn.title=constDatasetMovePrev;
				btn.style.width=30;
				break;
			}
			case "movenext":{
				btn.style.fontFamily="Webdings";
				btn.value="4";
				btn.title=constDatasetMoveNext;
				btn.style.width=30;
				break;
			}
			case "nextpage":{
				btn.style.fontFamily="Webdings";
				btn.value="8";
				btn.title=constDatasetNextPage;
				btn.style.width=30;
				break;
			}
			case "movelast":{
				btn.style.fontFamily="Webdings";
				btn.value=":";
				btn.title=constDatasetMoveLast;
				btn.style.width=30;
				break;
			}
			case "insertrecord":{
				btn.value=constBtnInsertRecord;
				btn.title=constDatasetInsertRecord;
				btn.style.width=45;
				break;
			}
			case "appendrecord":{
				btn.value=constBtnAppendRecord;
				btn.title=constDatasetAppendRecord;
				btn.style.width=45;
				break;
			}
			case "deleterecord":{
				btn.value=constBtnDeleteRecord;
				btn.title=constDatasetDeleteRecord;
				btn.style.width=45;
				break;
			}
			case "editrecord":{
				btn.value=constBtnEditRecord;
				btn.title=constDatasetEditRecord;
				btn.style.width=45;
				break;
			}
			case "cancelrecord":{
				btn.value=constBtnCancelRecord;;
				btn.title=constDatasetCancelRecord;
				btn.style.width=45;
				break;
			}
			case "updaterecord":{
				btn.value=constBtnUpdateRecord;
				btn.title=constDatasetUpdateRecord;
				btn.style.width=45;
				break;
			}
		}
		btn.id=dataPilot.id+"_"+btn.buttonType;
		row.insertCell().appendChild(btn);
	}

	refreshDataPilot(dataPilot);
}

function setDataPilotButtons(dataPilot, buttons){
	dataPilot.buttons=buttons;
	initDataPilot(dataPilot);
}

function refreshDataPilot(dataPilot){

	function refreshButton(btn, enable){
		btn.disabled=!enable;
		if (enable){
		}
		else{
			btn.style.backgroundColor="#d4d0c8";
		}
	}

	var dataset=getElementDataset(dataPilot);

	var row=dataPilot.rows[0];
	for(var i=0; i<row.cells.length; i++){
		var btn=row.cells[i].children[0];
		switch(btn.buttonType){
			case "movefirst":;
			case "moveprev":{
				refreshButton(btn, (dataset && !dataset.bof));
				break;
			}
			case "prevpage":{
				refreshButton(btn, (dataset && dataset.record && dataset.record.pageIndex>1));
				break;
			}
			case "movenext":;
			case "movelast":{
				refreshButton(btn, (dataset && !dataset.eof));
				break;
			}
			case "nextpage":{
				refreshButton(btn, (dataset && dataset.record && dataset.record.pageIndex<dataset.totalPage));
				break;
			}
			case "insertrecord":;
			case "appendrecord":{
				refreshButton(btn, (dataset && !dataset.readOnly));
				break;
			}
			case "editrecord":{
				refreshButton(btn, (dataset && !(dataset.bof && dataset.eof) && !dataset.readOnly));
				break;
			}
			case "deleterecord":{
				refreshButton(btn, (dataset && !(dataset.bof && dataset.eof) && !dataset.readOnly));
				break;
			}
			case "cancelrecord":;
			case "updaterecord":{
				refreshButton(btn, (dataset && (dataset.state=="insert" || dataset.state=="modify") && !dataset.readOnly));
				break;
			}
		}

		fireUserEvent(getElementEventName(dataPilot, "onRefreshButton"), [dataPilot, btn, btn.buttonType, dataset]);
	}
}

function _datapilot_onclick(){
	if (event.srcElement.disabled) return;
	var datapiolt=event.srcElement.datapiolt;
	var dataset=getElementDataset(datapiolt);

	var eventName=getElementEventName(datapiolt, "onButtonClick");
	var event_result=fireUserEvent(eventName, [datapiolt, event.srcElement, event.srcElement.buttonType, dataset]);
	if (event_result) return;

	var pageSize=datapiolt.getAttribute("pageSize");

	switch(event.srcElement.buttonType){
		case "movefirst":{
			dataset.moveFirst();
			break;
		}
		case "prevpage":{
			var pageIndex=(dataset.record)?dataset.record.pageIndex-1:1;
			dataset.moveToPage(pageIndex);
			break;
		}
		case "moveprev":{
			dataset.movePrev();
			break;
		}
		case "movenext":{
			dataset.moveNext();
			break;
		}
		case "nextpage":{
			var pageIndex=(dataset.record)?dataset.record.pageIndex+1:1;
			dataset.moveToPage(pageIndex);
			break;
		}
		case "movelast":{
			dataset.moveLast();
			break;
		}
		case "insertrecord":{
			dataset.insertRecord("before");
			break;
		}
		case "appendrecord":{
			dataset.insertRecord("end");
			break;
		}
		case "editrecord":{
			dataset_setState(dataset, "modify");
			break;
		}
		case "deleterecord":{
			if (isTrue(datapiolt.getAttribute("confirmDelete"))){
				if (confirm(constDatasetDeleteRecord)) dataset.deleteRecord();
			}
			else
				dataset.deleteRecord();
			break;
		}
		case "cancelrecord":{
			if (isTrue(datapiolt.getAttribute("confirmCancel"))){
				if (confirm(constDatasetCancelRecord)) dataset.cancelRecord();
			}
			else
				dataset.cancelRecord();
			break;
		}
		case "updaterecord":{
			dataset.updateRecord();
			break;
		}
	}
}

function initTabPage(tabpage){
	var tabItems=tabpage.getAttribute("tabItems");
	if (!tabItems) return;
	var tabs=tabItems.split(";");

	for(i=0; i<tabpage.children.length; i++){
		tabpage.children[i].removeNode(true);
	}
	var row=tabpage.insertRow();
	var cell=row.insertCell();
	cell.firstcell=true;
	cell.innerHTML="<img src=\""+xForm_Library_path+"image/start_tab.gif\" width=8px height=22px>";

	var label, tabcode, index;
	for(i=0; i<tabs.length; i++){
		index=tabs[i].indexOf("=");
		if (index>=0){
			label=tabs[i].substr(0, index);
			tabcode=tabs[i].substr(index+1);
		}
		else{
			label=tabs[i];
			tabcode=tabs[i];
		}

		cell=row.insertCell();
		cell.tab_index=i;
		cell.tab_code=tabcode;
		cell.style.backgroundColor="#e5e5e5";
		cell.style.borderTop="gray 1px solid";

		btn=document.createElement("<button hideFocus=true style=\"border-style: none; font-size: 9pt; cursor: hand\"></button>");
		btn.value=label;
		btn.tabIndex=-1;
		btn.style.height=15;
		btn.style.backgroundColor="#e5e5e5";
		btn.onclick=_tabpage_onclick;
		btn.onmouseover=_tabpage_onmouseover;
		btn.onmouseout=_tabpage_onmouseout;
		btn.tab=cell;
		cell.appendChild(btn);

		cell=row.insertCell();
		if (i!=tabs.length-1){
			cell.innerHTML="<img src=\""+xForm_Library_path+"image/tab.gif\" width=15px height=22px>";
		}
		else{
			cell.lastcell=true;
			cell.innerHTML="<img src=\""+xForm_Library_path+"image/end_tab.gif\" width=15px height=22px>";
		}

		eval("if (typeof("+tabpage.id+"_"+tabcode+")!=\"undefined\"){ "+
			tabpage.id+"_"+tabcode+".style.visibility=\"hidden\";"+
			tabpage.id+"_"+tabcode+".style.position=\"absolute\";}");
	}
	cell=row.insertCell();
	cell.width="100%";

	setActiveTabIndex(tabpage, 0);
}

function setTabs(tabpage, tabs){
	tabpage.tabs=tabs;
	initTabPage(tabpage);
}

function _setActiveTab(cell){
	try{
		var row=getRowByCell(cell);
		var tabpage=getTableByRow(row);
		var selectCell=tabpage.selectTab;

		if (selectCell==cell) return;
		var oldCode=(selectCell)?selectCell.tab_code:"";
		var newCode=cell.tab_code;

		var eventName=getElementEventName(tabpage, "beforeTabChange");
		var event_result=fireUserEvent(eventName, [tabpage, oldCode, newCode]);
		if (event_result) throw event_result;

		if (selectCell){
			var prevCell=row.cells[selectCell.cellIndex-1];
			var nextCell=row.cells[selectCell.cellIndex+1];

			selectCell.style.backgroundColor="#e5e5e5";
			selectCell.firstChild.style.backgroundColor="#e5e5e5";
			selectCell.firstChild.style.fontWeight="";

			if (prevCell.firstcell)
				prevCell.firstChild.src=xForm_Library_path+"image/start_tab.gif";
			else
				prevCell.firstChild.src=xForm_Library_path+"image/tab.gif";

			if (nextCell.lastcell)
				nextCell.firstChild.src=xForm_Library_path+"image/end_tab.gif";
			else
				nextCell.firstChild.src=xForm_Library_path+"image/tab.gif";
			eval("if (typeof("+tabpage.id+"_"+oldCode+")!=\"undefined\") "+tabpage.id+"_"+oldCode+".style.visibility=\"hidden\"");
		}

		var prevCell=row.cells[cell.cellIndex-1];
		var nextCell=row.cells[cell.cellIndex+1];

		cell.style.backgroundColor="#ffebcd";
		cell.firstChild.style.backgroundColor="#ffebcd";
		cell.firstChild.style.fontWeight="bold";

		if (prevCell.firstcell)
			prevCell.firstChild.src=xForm_Library_path+"image/active_start_tab.gif";
		else
			prevCell.firstChild.src=xForm_Library_path+"image/active_tab1.gif";

		if (nextCell.lastcell)
			nextCell.firstChild.src=xForm_Library_path+"image/active_end_tab.gif";
		else
			nextCell.firstChild.src=xForm_Library_path+"image/active_tab2.gif";
		eval("if (typeof("+tabpage.id+"_"+newCode+")!=\"undefined\") "+tabpage.id+"_"+newCode+".style.visibility=\"\"");

		tabpage.selectTab=cell;
		tabpage.selectCode=cell.tab_code;
		tabpage.selectIndex=cell.tab_index;

		var eventName=getElementEventName(tabpage, "afterTabChange");
		fireUserEvent(eventName, [tabpage, oldCode, newCode]);
	}
	catch(e){
		processException(e);
	}
}

function setActiveTab(table, tabcode){
	if (!tabcode) return;
	for(var i=0; i<table.cells.length; i++){
		if (table.cells[i].tab_code==tabcode){
			_setActiveTab(table.cells[i]);
			break;
		}
	}
}

function setActiveTabIndex(table, index){
	for(var i=0; i<table.cells.length; i++){
		if (table.cells[i].tab_index==index){
			_setActiveTab(table.cells[i]);
			break;
		}
	}
}

function _tabpage_onclick(){
	_setActiveTab(event.srcElement.tab);
}

function _tabpage_onmouseover(){
	event.srcElement.style.color="blue";
	event.srcElement.style.textDecorationUnderline=true;
}

function _tabpage_onmouseout(){
	event.srcElement.style.color="black";
	event.srcElement.style.textDecorationUnderline=false;
}

//-----------------------
// xForm v2.0
// Developer: bao yilei
// Nov, 2001
//-----------------------

function _execSQL(SQL, connection, pageSize, absolutePage){
	try{
		if (SQL){
			var _url=xForm_Library_path+"getdataset.asp?SQL="+escape(SQL)+"&conn_id="+escape(connection);
			if (!(pageSize>0)) pageSize=9999;
			_url+="&pageSize="+pageSize;
			if (absolutePage)
				_url+="&absolutePage="+absolutePage;
			else
				_url+="&absolutePage=1";

			var XMLDoc=new ActiveXObject("MSXML.DOMDocument");
			XMLDoc.async=false;
			XMLDoc.load(_url);

			var XMLRoot=XMLDoc.documentElement;
			if (isTrue(XMLRoot.selectSingleNode("SUCCESS").text)){
				var result=new Object();
				result.field_str=getValidStr(XMLRoot.selectSingleNode("FIELD").text);
				result.record_str=getValidStr(XMLRoot.selectSingleNode("RECORD").text);
				delete XMLDoc;
				return result;
			}
			else{
				var error_text=XMLRoot.selectSingleNode("ERRORTEXT").text;
				delete XMLDoc;
				throw constErrDownLoadFailed+"\n"+constErrDescription+":"+error_text;
			}
		}
	}
	catch(e){
		processException(e);
	}
}

function execSQL(SQL, connection, pageSize, absolutePage){
	var result=_execSQL(SQL, connection, pageSize, absolutePage);
	if (result){
		if (result.field_str!=""){
			var dataset=createDataset("", result.field_str, result.record_str);
			dataset.pageSize=pageSize;
			initDataset(dataset);
			delete result;
			return dataset;
		}
		else{
			return true;
		}
	}
	else
		return false;
}

function createParameters(){
	var parameters=new Array();
	parameters.setValue=parameters_setValue;
	parameters.getValue=parameters_getValue;
	return parameters;
}

function parameters_setValue(name, value){
	var count=this.length;
	var founded=false;
	for (var i=0; i<count; i++){
		if (compareText(this[i].name, name)){
			founded=true;
			break;
		}
	}
	if (!founded){
		i=count;
		this[i]=new Object();

	}
	this[i].name=name;
	this[i].value=value;
}

function parameters_getValue(name){
	var count=this.length;
	var founded=false;
	for (var i=0; i<count; i++){
		if (compareText(this[i].name, name)){
			return this[i].value;
			break;
		}
	}
}

function execStoredProcedure(commandText, connection, parameters, pageSize, absolutePage){
	var dataset=null;
	try{
		if (commandText){
			var param_str="";
			for(var i=0; i<parameters.length; i++){
				param_str += "?m_names="+parameters[i].name+"?m_values="+parameters[i].value;
			}

			var _url=xForm_Library_path+"execStoredProc.asp?commandText="+escape(commandText)+"&conn_id="+escape(connection)+param_str;
			if (!(pageSize>0)) pageSize=9999;
			_url+="&pageSize="+pageSize;
			if (absolutePage)
				_url+="&absolutePage="+absolutePage;
			else
				_url+="&absolutePage=1";

			var XMLDoc=new ActiveXObject("MSXML.DOMDocument");
			XMLDoc.async=false;
			XMLDoc.load(_url);

			var XMLRoot=XMLDoc.documentElement;
			if (isTrue(XMLRoot.selectSingleNode("SUCCESS").text)){
				var paramsNode=XMLRoot.selectSingleNode("PARAMS");
				var param_names=paramsNode.selectSingleNode("NAME").text;
				var param_values=paramsNode.selectSingleNode("VALUE").text;
				if (param_names){
					param_names=param_names.split(",");
					param_values=param_values.split(",");
					for(var i=0; i<param_names.length; i++){
						if (parameters[i]) delete parameters[i];
						parameters[i]=new Object();
						parameters[i].name=param_names[i];
						parameters[i].value=getDecodeStr(param_values[i]);
					}
				}

				if (XMLRoot.selectSingleNode("DATASET")){
					var datasetNode=XMLRoot.selectSingleNode("DATASET");
					var field_str=datasetNode.selectSingleNode("FIELD").text;
					var record_str=datasetNode.selectSingleNode("RECORD").text;
					if (field_str){
						dataset=createDataset("", field_str, record_str);
						dataset.pageSize=pageSize;
						initDataset(dataset);
					}
				}
				delete XMLDoc;
			}
			else{
				delete XMLDoc;
				throw constErrDownLoadFailed+"\n"+constErrDescription+":"+XMLRoot.selectSingleNode("errortext").text;
			}
		}
		return dataset;
	}
	catch(e){
		processException(e);
	}
}

function getDatasetByID(ID){
	for(var i=0; i<_array_dataset.length; i++){
		if (_array_dataset[i].id==ID) return _array_dataset[i];
	}

	var result;
	eval("if (typeof("+ID+")!=\"undefined\") result="+ID+";");
	return result;
}

function setElementDataset(element, dataset){
	var _dataset;
	if (typeof(dataset)=="string"){
		_dataset=getDatasetByID(dataset);
	}
	else{
		_dataset=dataset;
	}
	var old_dataset=element.getAttribute("dataset");

	if (old_dataset){
		var array=old_dataset.editors;
		if (array) pArray_ex_delete(array, element);
	}

	if (_dataset){
		var array=_dataset.editors;
		if (!array){
			array=new pArray();
			_dataset.editors=array;
		}

		pArray_ex_insert(array, element);
	}
	element.dataset=_dataset;
}

function _dataset_getField(fields, fieldName){
	var field=null;
	if (typeof(fieldName)=="number"){
		field=fields[fieldName];
	}
	else if (typeof(fieldName)=="string"){
		var fieldIndex=fields["_index_"+fieldName.toLowerCase()];
		if (!isNaN(fieldIndex)) field=fields[fieldIndex];
	}	
	return field;	
}

function dataset_getField(fieldName){
	var dataset=this;
	return _dataset_getField(dataset.fields, fieldName);
}

function appendFromDataString(dataset, recordStr, init){
	if (!recordStr) return;
	var records=recordStr.split(";");
	for(var i=0; i<records.length; i++){
		var record=records[i].split(",");
		for(var j=0; j<record.length; j++){
			record[j]=getDecodeStr(record[j]);
		}
		pArray_insert(dataset, "end", null, record);
		if (init) initRecord(record, dataset);
	}
}

function transferToDataString(dataset){
	var result="";
	var i=0;
	var record=dataset.getFirstRecord();
	while (record){
		if (i!=0) result+=";";
		for(var j=0; j<dataset.fields.fieldCount; j++){
			if (j!=0) result+=",";
			result+=getEncodeStr(record.getFieldText(j));
		}
		record=record.getNextRecord();
		i++;
	}
	return result;
}

function createDataset(ID, fieldStr, recordStr){
	var dataset=new pArray();

	dataset.fields=new Array();
	dataset.fields.fieldCount=0;
	dataset.addField=dataset_addField;
	dataset.sourceType="custom";
	dataset.pageSie=9999;
	dataset.totalPage=1;
	dataset.absolutePage=1;

	if (ID){
		dataset.id=ID;
		_array_dataset[_array_dataset.length]=dataset;
	}

	if (fieldStr){
		var fields=fieldStr.split(",");
		for(var i=0; i<fields.length; i++){
			dataset.addField(fields[i]);
		}
	}

	appendFromDataString(dataset, recordStr);

	return dataset;
}

function dataset_addField(name, dataType){
	var dataset=this;
	try{
		if (getValidStr(name)=="")
			throw constErrEmptyFieldName;

		if (dataset.prepared)
			throw constErrAddDataField;

		name=name.toLowerCase();
		var field=new Object;
		var i=dataset.fields.length;
		dataset.fields["_index_"+name]=i;
		dataset.fields[i]=field;
		dataset.fields.fieldCount++;
		field.index=i;
		field.dataset=dataset;
		field.fields=dataset.fields;
		field.name=name;
		field.label=name;
		field.fieldName=name;
		field.visible=true;
		field.dataType=dataType;

		switch (dataType){
			case "int":;
			case "float":{
				field.editorType="text";
				field.align="right";
				field.vAlign="top";
				break;
			}

			case "longtext":{
				field.editorType="textarea";
				field.align="left";
				field.vAlign="top";
				break;
			}

			case "bool":{
				field.editorType="checkbox";
				field.align="middle";
				field.vAlign="middle";
				break;
			}

			case "date":{
				field.editorType="text";
				field.align="left";
				field.vAlign="top";
				field.size=10;
				break;
			}

			case "datetime":{
				field.editorType="text";
				field.align="left";
				field.vAlign="top";
				field.size=19;
				break;
			}

			case "time":{
				field.editorType="text";
				field.align="left";
				field.vAlign="top";
				field.size=8;
				break;
			}

			default:{
				field.editorType="text";
				field.align="left";
				field.vAlign="top";
				break;
			}
		}

		return field;
	}
	catch(e){
		processException(e);
	}
}

function initFieldArray(dataset, fields){
	var fieldCount=fields.fieldCount;
	fields.dataset=dataset;

	for(var i=0; i<fieldCount; i++){
		if (dataset.id){
			if (fields[i].id && typeof(_element_property)!="undefined"){
				var root=_element_property[fields[i].id];
				if (root){
					var property_count=root.length;
					for(var j=0; j<property_count; j++)
						eval("fields[i]."+root[j].property+"=getDecodeStr(root[j].value)");
				}
			}
		}

		fields[fieldCount+i]=new Object;
		fields[fieldCount+i].name="_cur_"+fields[i].name;
		fields["_index__cur_"+fields[i].name]=fieldCount+i;
		fields[fieldCount*2+i]=new Object;
		fields[fieldCount*2+i].name="_old_"+fields[i].name;
		fields["_index__old_"+fields[i].name]=fieldCount*2+i;

		fields[i].readOnly=isTrue(fields[i].readOnly);
		fireDatasetEvent(dataset, "onInitField", [dataset, fields[i]]);
	}
}

function initRecord(record, dataset, skipSaveOld){
	record.dataset=dataset;
	record.fields=dataset.fields;
	record.recordState="none";
	record.pageIndex=1;
	record.visible=true;

	record.saveOldValue=dataset._saveOldValue;
	record.getFieldValue=dataset._getFieldValue;
	record.getFieldText=dataset._getFieldText;
	record.setFieldValue=dataset._setFieldValue;
	record.getPrevRecord=dataset._getPrevRecord;
	record.getNextRecord=dataset._getNextRecord;

	if (!skipSaveOld) record.saveOldValue();
}

function initDataset(dataset){
	if (dataset.prepared) return;

	dataset.disableControlCount=1;
	dataset.disableEventCount=1;
	try{
		if (dataset.id && typeof(_element_property)!="undefined"){
			var root=_element_property[dataset.id];
			if (root){
				var property_count=root.length;
				for(var i=0; i<property_count; i++)
					eval("dataset."+root[i].property+"=getDecodeStr(root[i].value)");
			}
		}

		dataset.window=window;

		dataset.bof=true;
		dataset.eof=true;
		dataset.state="none";
		dataset.readOnly=isTrue(dataset.readOnly);
		dataset.sortFields="";
		dataset.loadedPage=new Array();
		if (dataset.absolutePage>0) dataset.loadedPage[dataset.absolutePage-1]=true;

		dataset._saveOldValue=record_saveOldValue;
		dataset._getFieldValue=record_getFieldValue;
		dataset._getFieldText=record_getFieldText;
		dataset._setFieldValue=record_setFieldValue;
		dataset._getPrevRecord=record_getPrevRecord;
		dataset._getNextRecord=record_getNextRecord;

		dataset.getField=dataset_getField;
		dataset.getFieldValue=dataset_getFieldValue;
		dataset.getFieldText=dataset_getFieldText;
		dataset.setFieldValue=dataset_setFieldValue;
		dataset.disableControl=dataset_disableControl;
		dataset.enableControl=dataset_enableControl;
		dataset.disableEvent=dataset_disableEvent;
		dataset.enableEvent=dataset_enableEvent;
		dataset.refreshControls=dataset_refreshControls;
		dataset.setRecord=dataset_setRecord;
		dataset.setReadOnly=dataset_setReadOnly;
		dataset.setFieldReadOnly=dataset_setFieldReadOnly;
		dataset.getFirstRecord=dataset_getFirstRecord;
		dataset.getLastRecord=dataset_getLastRecord;
		dataset.move=dataset_move;
		dataset.movePrev=dataset_movePrev;
		dataset.moveNext=dataset_moveNext;
		dataset.moveFirst=dataset_moveFirst;
		dataset.moveLast=dataset_moveLast;
		dataset.find=dataset_find;
		dataset.locate=dataset_locate;
		dataset.updateRecord=dataset_updateRecord;
		dataset.cancelRecord=dataset_cancelRecord;
		dataset.insertRecord=dataset_insertRecord;
		dataset.deleteRecord=dataset_deleteRecord;
		dataset.copyRecord=dataset_copyRecord;
		dataset.loadpage=dataset_loadpage;
		dataset.loadDetail=dataset_loadDetail;
		dataset.isPageLoaded=dataset_isPageLoaded;
		dataset.moveToPage=dataset_moveToPage;
		dataset.setMasterDataset=dataset_setMasterDataset;
		dataset.flushData=dataset_flushData;
		dataset.clearData=dataset_clearData;
		dataset.passData=dataset_passData;
		dataset.sort=dataset_sort;

		fireDatasetEvent(dataset, "onInitDataset", [dataset]);
		dataset.setReadOnly(isTrue(dataset.readOnly));
		initFieldArray(dataset, dataset.fields);
		var record=dataset.firstUnit;
		while (record){
			initRecord(record, dataset);
			record=record.nextUnit;
		}

		dataset.moveFirst();
		dataset.prepared=true;
	}
	finally{
		dataset.disableControlCount=0;
		dataset.disableEventCount=0;
	}
}

function _dataset_setMasterDataset(dataset, masterDataset, linkString, detailSql){
	if (dataset.masterDataset){
		var array=dataset.masterDataset.detailDatasets;
		if (array) pArray_ex_delete(array, dataset);
	}

	if (typeof(masterDataset)=="string") masterDataset=getDatasetByID(masterDataset);
	dataset.masterDataset=masterDataset;
	dataset.detailSql=detailSql;
	if (masterDataset){
		var array=masterDataset.detailDatasets;
		if (!array){
			array=new pArray();
			masterDataset.detailDatasets=array;
		}
		pArray_ex_insert(array, dataset);

		var links=linkString.split(";");
		var field, fieldName;
		dataset.masterLinks=new Array();
		for(var i=0; i<links.length; i++){
			index=links[i].indexOf("=");
			dataset.masterLinks[i]=new Object();

			if (index>=0){
				fieldName=links[i].substr(0, index);
			}
			else{
				fieldName=links[i];
			}
			field=masterDataset.getField(fieldName);

			if (field){
				dataset.masterLinks[i].master_field=field.name;
				dataset.masterLinks[i].master_index=field.index;
			}
			else
				throw constErrCantFindMasterField.replace("%s", fieldName);

			if (index>=0){
				fieldName=links[i].substr(index+1);
			}
			else{
				fieldName=links[i];
			}
			field=dataset.getField(fieldName);

			if (field){
				dataset.masterLinks[i].detail_field=field.name;
				dataset.masterLinks[i].detail_index=field.index;
			}
			else{alert(dataset.id)
				throw constErrCantFindDetailField.replace("%s", fieldName);
			}
		}
		delete links;

		delete dataset.loaded_detail;
		dataset.loaded_detail=new Array;
		masterDataset.loadDetail();
	}
}

function dataset_setMasterDataset(masterDataset, linkString, detailSql){
	var dataset=this;
	try{
		_dataset_setMasterDataset(dataset, masterDataset, linkString, detailSql);
	}
	catch (e){
		processException(e);
	}
}

function _dataset_loadDetail(dataset){
	if (!dataset.record) return;

	if (dataset.detailDatasets){
		var unit=dataset.detailDatasets.firstUnit;
		while (unit && unit.data){
			try{
				var detail_dataset=unit.data;

				var keycode="";
				for(var i=0; i<detail_dataset.masterLinks.length; i++){
					keycode+=dataset.record[detail_dataset.masterLinks[i].master_index];
				}

				var keycode_founded=false;
				for(var i=0; i<detail_dataset.loaded_detail.length; i++){
					if (detail_dataset.loaded_detail[i]==keycode){
						keycode_founded=true;
						break;
					}
				}

				if (!keycode_founded){
					var dataset_inserted=false;
					var event_result=fireDatasetEvent(detail_dataset, "beforeLoadDetail", [detail_dataset, dataset]);
					if (event_result) throw event_result;
					if (detail_dataset.detailSql){
						var SQL=detail_dataset.detailSql;
						for(var i=0; i<detail_dataset.masterLinks.length; i++){
							var re="["+detail_dataset.masterLinks[i].master_field+"]";
							SQL=SQL.replace(re, dataset.record[detail_dataset.masterLinks[i].master_index]);
						}

						var result=_execSQL(SQL, detail_dataset.connection);
						if (result)
							appendFromDataString(detail_dataset, result.record_str, true);
						delete result;
					}

					detail_dataset.loaded_detail[detail_dataset.loaded_detail.length]=keycode;
				}
				detail_dataset.refreshControls();
				detail_dataset.moveFirst();
				unit=unit.nextUnit;
			}
			catch (e){
				processException(e);
			}
		}
	}
}

function dataset_loadDetail(){
	var dataset=this;
	try{
		_dataset_loadDetail(dataset);
	}
	catch (e){
		processException(e);
	}
}

function dataset_isPageLoaded(pageIndex){
	var dataset=this;
	return dataset.loadedPage[pageIndex-1];
}


function _dataset_loadpage(dataset, pageIndex){
	if (pageIndex<1 || pageIndex>dataset.totalPage || dataset.isPageLoaded(pageIndex)) return;
	if (dataset.masterDataset) throw constErrLoadPageOnDetailDataset;
	if (dataset.sortFields) throw constErrLoadPageAfterSort;

	var result=_execSQL(dataset.sql, dataset.connection, dataset.pageSize, pageIndex);
	if (result && result.record_str){
		var tmpArray=new pArray();
		appendFromDataString(tmpArray, result.record_str);
		var record=tmpArray.lastUnit;
		while (record){
			initRecord(record, dataset);
			record.pageIndex=pageIndex;
			record=record.prevUnit;
		}

		var inserted=false;
		var record=dataset.lastUnit;
		while (record){
			if (record.pageIndex<pageIndex){
				pArray_insertArray(dataset, "after", record, tmpArray);
				inserted=true;
				break;
			}
			record=record.prevUnit;
		}
		if (!inserted) pArray_insertArray(dataset, "begin", null, tmpArray);
		delete tmpArray;

		dataset.loadedPage[pageIndex-1]=true;
		dataset.refreshControls();
	}
	delete result;
}

function dataset_loadpage(pageIndex){
	try{
		var dataset=this;
		_dataset_loadpage(dataset, pageIndex);
	}
	catch (e){
		processException(e);
	}
}

function _dataset_clearData(dataset){
	dataset.disableControl();
	try{
		if (dataset.loaded_detail) delete dataset.loaded_detail;
		if (dataset.loadedPage) delete dataset.loadedPage;
		dataset.loadedPage=new Array();
		if (dataset.absolutePage>0) dataset.loadedPage[dataset.absolutePage-1]=true;
		pArray_clear(dataset);
		dataset.moveFirst();
	}
	finally{
		dataset.enableControl();
		dataset.refreshControls();
	}
}

function dataset_clearData(){
	try{
		var dataset=this;
		_dataset_clearData(dataset);
	}
	catch (e){
		processException(e);
	}
}

function _dataset_passData(to_dataset, mode, record, from_dataset){
	pArray_insertArray(to_dataset, mode, record, from_dataset);
}

function dataset_passData(mode, record, from_dataset){
	var dataset=this;
	pArray_insertArray(dataset, mode, record, from_dataset);
}

function freeDataset(dataset){
	if (dataset.detailDatasets) pArray_clear(dataset.detailDatasets);
	if (dataset.editors) pArray_clear(dataset.editors);
	delete dataset.masterLinks;
	pArray_clear(dataset.fields);
	dataset.clearData();
	delete dataset;
}

function _dataset_flushData(dataset, SQL, connection, pageSize, absolutePage){
	dataset.disableControl();
	try{
		if (!SQL) SQL=dataset.sql;
		if (!connection) connection=dataset.connection;
		if (typeof(pageSize)=="undefined") pageSize=dataset.pageSize;
		if (typeof(absolutePage)=="undefined") absolutePage=dataset.absolutePage;

		dataset.clearData();

		dataset.sql=SQL;
		dataset.connection=connection;
		dataset.pageSize=pageSize;
		dataset.absolutePage=absolutePage;
		var result=_execSQL(SQL, connection, pageSize, absolutePage);
		if (result){
			appendFromDataString(dataset, result.record_str, true);
			dataset.moveFirst();
		}
		delete result;
	}
	finally{
		dataset.enableControl();
		dataset.refreshControls();
	}
}

function dataset_flushData(SQL, connection, pageSize, absolutePage){
	try{
		var dataset=this;
		_dataset_flushData(dataset, SQL, connection, pageSize, absolutePage)
	}
	catch (e){
		processException(e);
	}
}

function dataset_moveToPage(pageIndex){
	try{
		var dataset=this;
		if (!dataset.isPageLoaded(pageIndex)) _dataset_loadpage(dataset, pageIndex);

		var record=dataset.getFirstRecord();
		while (record){
			if (record.pageIndex>=pageIndex){
				_dataset_setRecord(dataset, record);
				break;
			}
			record=record.getNextRecord();
		}
	}
	catch (e){
		processException(e);
	}
}

function record_saveOldValue(){
	var record=this;

	var fieldCount=record.fields.fieldCount;
	for(var i=0; i<fieldCount; i++){
		record[fieldCount+i]=record[i];
		record[fieldCount*2+i]=record[i];
	}
}

function _dataset_sort(dataset, fields){

	function quickSort(_array, _fields, _low, _high){

		function compareRecord(record, _mid_data){
			if (_fields.length>0){
				var value1, value2;
				for (var i=0; i<_fields.length; i++){
					if (_field[i].ascend){
						value1=1;
						value2=-1;
					}
					else{
						value1=-1;
						value2=1;
					}

					if (record.getFieldValue(_fields[i].index)>_mid_data[i]){
						return value1;
					}
					else if (record.getFieldValue(_fields[i].index)<_mid_data[i]){
						return value2;
					}
				}
			}
			else{
				if (record.recordno>_mid_data[0]){
					return 1;
				}
				else if (record.recordno<_mid_data[0]){
					return -1;
				}
			}
			return 0;
		}

		var low=_low;
		var high=_high;
		var mid=getInt((low+high)/2);
		var mid_data=new Array();

		if (_fields.length>0){
			for (var i=0; i<_fields.length; i++)
				mid_data[i]=_array[mid].getFieldValue(_fields[i].index);
		}
		else{
			mid_data[0]=_array[mid].recordno;
		}

		do {
			while (compareRecord(_array[low], mid_data)<0) low++;
			while (compareRecord(_array[high], mid_data)>0) high--;

			if (low<=high){
				var tmp=_array[low];
				_array[low]=_array[high];
				_array[high]=tmp;

				low++;
				high--;
			}
		}while (low<=high)

		if (high>_low) quickSort(_array, _fields, _low, high);
		if (_high>low) quickSort(_array, _fields, low, _high);
	}

	var _field=new Array();
	if (fields){
		var fields_array=fields.split(",");
		for (var i=0; i<fields_array.length; i++){
			_field[i]=new Object();
			_field[i].ascend=true;

			var firstchar=fields_array[i].substring(0, 1);
			var fieldName;
			if (firstchar=="+" || firstchar=="-"){
				if (firstchar=="-") _field[i].ascend=false;
				fieldName=fields_array[i].substring(1, fields_array[i].length);
			}
			else{
				fieldName=fields_array[i];
			}

			for (var j=0; j<dataset.fields.fieldCount; j++){
				if (compareText(fieldName, dataset.fields[j].name)){
					_field[i].index=j;
					break;
				}
			}
		}
	}

	function customSort(_array, _low, _high){

		function compareRecord(record1, record2){
			var event_name=getElementEventName(dataset, "onCompareRecord");
			if (isUserEventDefined(event_name)){
				return fireUserEvent(event_name, [record1.dataset, record1, record2]);
			}
		}

		var low=_low;
		var high=_high;
		var mid_record=_array[getInt((low+high)/2)];

		do {
			while (compareRecord(_array[low], mid_record)<0) low++;
			while (compareRecord(_array[high], mid_record)>0) high--;

			if (low<=high){
				var tmp=_array[low];
				_array[low]=_array[high];
				_array[high]=tmp;

				low++;
				high--;
			}
		}while (low<=high)

		if (high>_low) customSort(_array, _low, high);
		if (_high>low) customSort(_array, low, _high);
	}

	var _field=new Array();
	if (fields){
		if (fields!="#custom"){
			var fields_array=fields.split(",");
			for (var i=0; i<fields_array.length; i++){
				_field[i]=new Object();
				_field[i].ascend=true;

				var firstchar=fields_array[i].substring(0, 1);
				var fieldName;
				if (firstchar=="+" || firstchar=="-"){
					if (firstchar=="-") _field[i].ascend=false;
					fieldName=fields_array[i].substring(1, fields_array[i].length);
				}
				else{
					fieldName=fields_array[i];
				}

				for (var j=0; j<dataset.fields.fieldCount; j++){
					if (compareText(fieldName, dataset.fields[j].name)){
						_field[i].index=j;
						break;
					}
				}
			}
		}
	}

	if (!dataset.firstUnit) return;

	var tmp_array=new Array();
	try{
		var record=dataset.firstUnit;
		var i=0;
		while (record){
			tmp_array[i++]=record;
			if (!dataset.sortFields) record.recordno=i;
			record=record.nextUnit;
		}

		dataset.sortFields=fields;
		if (fields!="#custom"){
			quickSort(tmp_array, _field, 0, tmp_array.length-1);
		}
		else{
			customSort(tmp_array, 0, tmp_array.length-1);
		}

		dataset.firstUnit=null;
		dataset.lastUnit=null;
		for (var i=0; i<tmp_array.length; i++){
			pArray_insert(dataset, "end", null, tmp_array[i]);
		}

		dataset.refreshControls();
	}
	finally{
		delete tmp_array;
		for (var i=0; i<_field.length; i++) delete _field[i];
		delete _field;
	}
}

function dataset_sort(fields){
	try{
		var dataset=this;
		_dataset_sort(dataset, fields);
	}
	catch (e){
		processException(e);
	}
}

function dataset_setReadOnly(readOnly){
	var dataset=this;
	dataset.readOnly=readOnly;

	_broadcastDatasetMsg(_notifyDatasetStateChanged, dataset);
}

function dataset_setFieldReadOnly(fieldName, readOnly){
	var dataset=this;
	var field=dataset.getField(fieldName);
	if (field){
		field.readOnly=readOnly;
		_broadcastFieldMsg(_notifyFieldStateChanged, dataset, dataset.record, field);
	}
}

function fireDatasetEvent(dataset, eventName, param){
	if (dataset.disableEventCount>0) return;
	var result;
	result=fireUserEvent(getElementEventName(dataset, eventName), param);
	return result;
}

function dataset_isRecordValid(record){
	if (!record)
		return false;
	else{
		var result=(record.recordState!="delete" && record.recordState!="discard" && record.visible);
		var dataset=record.dataset;
		var masterDataset=dataset.masterDataset;
		if (result){
			if (masterDataset){
				if (!masterDataset.record) return false;

				for(var i=0; i<dataset.masterLinks.length; i++){
					if (masterDataset.record[dataset.masterLinks[i].master_index]!=
						record[dataset.masterLinks[i].detail_index]){
							result=false;
							break;
					}
				}
			}

			if (dataset.filtered && !(record==dataset.record && dataset.state!="none")){
				var event_name=getElementEventName(dataset, "onFilterRecord");
				if (isUserEventDefined(event_name)){
					if (!fireUserEvent(event_name, [dataset, record])) result=false;
				}
			}
		}
		return result;
	}
}

function dataset_setBofnEof(dataset, BofValue, EofValue){
	if (dataset.bof!=BofValue || dataset.eof!=EofValue){
		dataset.bof=BofValue;
		dataset.eof=EofValue;
		_broadcastDatasetMsg(_notifyDatasetStateChanged, dataset, dataset.record);
	}
}

function _do_dataset_setRecord(dataset, record){
	if (dataset.record!=record){
		if (dataset.record){
			_dataset_updateRecord(dataset);
		}
		
		if (dataset.detailDatasets){
			var detailDataset=dataset.detailDatasets.firstUnit;
			while (detailDataset){
				_dataset_updateRecord(detailDataset);
				detailDataset=detailDataset.nextUnit;
			}
		}

		var event_result=fireDatasetEvent(dataset, "beforeScroll", [dataset]);
		if (event_result) throw event_result;

		dataset.record=record;
		dataset.modified=false;

		if (dataset.disableControlCount<1) dataset.loadDetail();

		fireDatasetEvent(dataset, "afterScroll", [dataset]);
		_broadcastDatasetMsg(_notifyDatasetCursorChanged, dataset, record);
	}
}

function _dataset_setRecord(dataset, record){
	_do_dataset_setRecord(dataset, record);
	if (record){
		dataset_setBofnEof(dataset, false, false);
		dataset_setBofnEof(dataset, false, false);
	}
}

function dataset_setRecord(record){
	try{
		_dataset_setRecord(this, record);
	}
	catch(e){
		processException(e);
	}
}

function validateDatasetCursor(dataset){
	var down_found=false, up_found=false;

	var record=dataset.record;
	while (record){
		if (dataset_isRecordValid(record)){
			_do_dataset_setRecord(dataset, record);
			up_found=true;
			break;
		}
		record=_record_getPrevRecord(record);
	}

	var record=dataset.record;
	while (record){
		if (dataset_isRecordValid(record)){
			_do_dataset_setRecord(dataset, record);
			down_found=true;
			break;
		}
		record=_record_getNextRecord(record);
	}

	if (!up_found && !down_found)
		_do_dataset_setRecord(dataset, null);

	dataset_setBofnEof(dataset, (!up_found), (!down_found));
}

function dataset_setState(dataset, state){
	dataset.state=state;

	_broadcastDatasetMsg(_notifyDatasetStateChanged, dataset, dataset.record);
	fireDatasetEvent(dataset, "onStateChanged", [dataset]);
}

function record_getFieldValue(fieldName){
	var record=this;
	var fields=record.fields;
	var fieldIndex=-1;
	var result;
	if (fields){
		if (typeof(fieldName)=="number"){
			fieldIndex=fieldName;
		}
		else if (typeof(fieldName)=="string"){
			fieldIndex=fields["_index_"+fieldName.toLowerCase()];
		}

		if (!isNaN(fieldIndex) && fieldIndex!=-1){
			result=getTypedValue(record[fieldIndex], fields[fieldIndex].dataType);
		}
	}
	return result;
}

function dataset_getFieldValue(fieldName){
	var dataset=this;
	if (dataset.record)
		return dataset.record.getFieldValue(fieldName);
	else
		return "";
}

function record_getFieldText(fieldName){
	var record=this, field, result="";
	var value=record.getFieldValue(fieldName);	
	var field=record.dataset.getField(fieldName);

	switch (typeof(value)){
		case "number":{
			if (!isNaN(value)){
				if (field.dataType=="float")
					return result=formatFloat(value, 2);
				else
					return result=value+"";
			}
			break;
		}
		case "object":{
			if (!isNaN(value)){
				if (field){
					result=formatDateTime(value, field.dataType);
				}
			}
			break;
		}
		default:{
			result=getValidStr(value);
			break;
		}
	}
	return result;
}

function dataset_getFieldText(fieldName){
	var dataset=this;
	if (dataset.record)
		return dataset.record.getFieldText(fieldName);
	else
		return "";
}

function _record_setFieldValue(record, fieldName, value){
	var dataset=record.dataset;
	var fields=record.fields;
	var fieldIndex=-1;

	if (fields){
		if (typeof(fieldName)=="number"){
			fieldIndex=fieldName;
		}
		else if (typeof(fieldName)=="string"){
			fieldIndex=fields["_index_"+fieldName.toLowerCase()];
		}

		if (!isNaN(fieldIndex) && fieldIndex!=-1){
			var event_result=fireDatasetEvent(dataset, "beforeChange", [dataset, fields[fieldIndex], value]);
			if (event_result) throw event_result;

			switch (fields[fieldIndex].dataType){
				case "float":{
					record[fieldIndex]=parseFloat(value);
					break;
				}
				case "int":{
					if (typeof(value)=="number")
						record[fieldIndex]=Math.round(value);
					else
						record[fieldIndex]=Math.round(parseFloat(value));

					break;
				}
				case "date":;
				case "datetime":{		
					if (typeof(value)=="object")
						record[fieldIndex]=value;
					else
						record[fieldIndex]=new Date(value);
					break;
				}
				case "time":{
					if (typeof(value)=="object")
						record[fieldIndex]=value;
					else
						record[fieldIndex]=new Date("1900-01-01 "+value);
					break;
				}
				case "bool":{
					record[fieldIndex]=isTrue(value);
					break;
				}
				default:{
					record[fieldIndex]=getValidStr(value);
					break;
				}
			}
			dataset.modified=true;

			fireDatasetEvent(dataset, "afterChange", [dataset, fields[fieldIndex]]);

			if (dataset.state=="none") dataset_setState(dataset, "modify");
			_broadcastFieldMsg(_notifyFieldDataChanged, dataset, record, fields[fieldIndex]);
		}
	}
}

function record_setFieldValue(fieldName, value){
	try{
		_record_setFieldValue(this, fieldName, value);
	}
	catch(e){
		processException(e);
	}
}

function _record_getPrevRecord(record){
	var _record=record;
	while (_record){
		_record=_record.prevUnit;
		if (dataset_isRecordValid(_record)) return _record;
	}
}

function record_getPrevRecord(){
	return _record_getPrevRecord(this);
}

function _record_getNextRecord(record){
	var _record=record;
	while (_record){
		_record=_record.nextUnit;
		if (dataset_isRecordValid(_record)) return _record;
	}
}

function record_getNextRecord(){
	return _record_getNextRecord(this);
}

function dataset_setFieldValue(fieldName, value){
	try{
		var dataset=this;
		if (dataset.record)
			dataset.record.setFieldValue(fieldName, value);
		else
			throw constErrNoCurrentRecord;
	}
	catch(e){
		processException(e);
	}
}

function dataset_refreshCursor(dataset){
	_broadcastDatasetMsg(_notifyDatasetCursorChanged, dataset, dataset.record);
}

function dataset_disableControl(){
	var dataset=this;
	dataset.disableControlCount=dataset.disableControlCount+1;
}

function dataset_enableControl(){
	var dataset=this;
	dataset.disableControlCount=(dataset.disableControlCount>0)?dataset.disableControlCount-1:0;
	dataset_refreshCursor(dataset);

}

function dataset_disableEvent(){
	var dataset=this;
	dataset.disableEventCount=dataset.disableEventCount+1;
}

function dataset_enableEvent(){
	var dataset=this;
	dataset.disableEventCount=(dataset.disableEventCount>0)?dataset.disableEventCount-1:0;
}

function dataset_refreshControls(){
	var dataset=this;
	_broadcastDatasetMsg(_notifyDatasetRefresh, dataset);
}

function _dataset_move(dataset, count){
	var _record=dataset.record;
	if (!_record) _record=dataset.getFirstRecord();
	if (!_record) return;
	var record=_record;

	if (count>0){
		var old_pageIndex=record.pageIndex
		var eof=false;
		for(var i=0; i<count; i++){
			var pageIndex=0;

			_record=record.getNextRecord();
			if (!_record || (_record && _record.pageIndex!=old_pageIndex)){
				if (old_pageIndex<dataset.totalPage){
					if (!dataset.isPageLoaded(old_pageIndex+1)){
						if ((i+dataset.pageSize<count) && (old_pageIndex+1<dataset.totalPage)){
							i+=dataset.pageSize-1;
							_record=record;
						}
						else{
							_dataset_loadpage(dataset, old_pageIndex+1);
							_record=record.getNextRecord();
						}
					}
				}
				old_pageIndex++;
			}

			if (_record){
				record=_record;
			}
			else{
				eof=true;
				break;
			}
		}
		dataset_setBofnEof(dataset, (!dataset_isRecordValid(dataset.record)), eof);
	}
	else{
		var old_pageIndex=record.pageIndex
		var bof=false;
		for(var i=count; i<0; i++){
			var pageIndex=0;

			_record=record.getPrevRecord();
			if (!_record || (_record && _record.pageIndex!=old_pageIndex)){
				if (old_pageIndex>1){
					if (!dataset.isPageLoaded(old_pageIndex-1)){
						if ((i+dataset.pageSize<0) && (old_pageIndex>1)){
							i+=dataset.pageSize-1;
							_record=record;
						}
						else{
							_dataset_loadpage(dataset, old_pageIndex-1);
							_record=record.getPrevRecord();
						}
					}
				}
				old_pageIndex--;
			}

			if (_record){
				record=_record;
			}
			else{
				bof=true;
				break;
			}
		}
		dataset_setBofnEof(dataset, bof, (!dataset_isRecordValid(dataset.record)));
	}

	if (record) _do_dataset_setRecord(dataset, record);
}

function dataset_move(count){
	var dataset=this;
	try{
		_dataset_move(dataset, count);
	}
	catch(e){
		processException(e);
	}
}

function dataset_movePrev(){
	var dataset=this;
	try{
		_dataset_move(dataset, -1);
	}
	catch(e){
		processException(e);
	}
}

function dataset_moveNext(){
	var dataset=this;
	try{
		_dataset_move(dataset, 1);
	}
	catch(e){
		processException(e);
	}
}

function _dataset_getFirstRecord(dataset){
	var record=dataset.firstUnit;
	if (record && !dataset_isRecordValid(record)) record=record.getNextRecord();
	return record;
}

function dataset_getFirstRecord(){
	return _dataset_getFirstRecord(this);
}

function dataset_moveFirst(){
	var dataset=this;

	try{
		if (!dataset.isPageLoaded(1)) _dataset_loadpage(dataset, 1);
		_do_dataset_setRecord(dataset, dataset.getFirstRecord());
		dataset_setBofnEof(dataset, true, (!dataset_isRecordValid(dataset.record)));
	}
	catch(e){
		processException(e);
	}
}

function _dataset_getLastRecord(dataset){
	var record=dataset.lastUnit;
	if (!dataset_isRecordValid(record) && record) record=record.getPrevRecord();
	return record;
}

function dataset_getLastRecord(){
	return _dataset_getLastRecord(this);
}

function dataset_moveLast(){
	var dataset=this;

	try{
		if (!dataset.isPageLoaded(dataset.totalPage)) _dataset_loadpage(dataset, dataset.totalPage);
		_do_dataset_setRecord(dataset, dataset.getLastRecord());
		dataset_setBofnEof(dataset, (!dataset_isRecordValid(dataset.record)), true);
	}
	catch(e){
		processException(e);
	}
}

function dataset_find(fieldNames, values, startRecord){

	function isMatching(fieldNames, values, record){
		var result=true;
		for (var j=0; j<fieldNames.length && j<values.length; j++){
			if (!compareText(record.getFieldText(fieldNames[j]), values[j])){
				result=false;
				break;
			}
		}
		return result;
	}

	if (!fieldNames || !values) return false;

	var dataset=this;
	if (!dataset.record) return;
	if (isMatching(fieldNames, values, dataset.record)) return dataset.record;

	var record=(startRecord)?startRecord:dataset.getFirstRecord();
	while (record){
		if (isMatching(fieldNames, values, record)) return record;
		record=record.getNextRecord();
	}
}

function dataset_locate(fieldName, value, startRecord){

	function isMatching(fieldName, value, record){
		var tmpValue=record.getFieldText(fieldName);
		return (tmpValue && compareText(tmpValue.substr(0, len), value));
	}

	if (!value) return false;

	var dataset=this;
	if (!dataset.record) return;
	if (isMatching(fieldName, value, dataset.record)) return dataset.record;

	var len=value.length;
	var record=(startRecord)?startRecord:dataset.getFirstRecord();
	while (record){
		if (isMatching(fieldName, value, record)) return record;
		record=record.getNextRecord();
	}
}

function _dataset_insertRecord(dataset, mode){
	_dataset_updateRecord(dataset);

	var event_result=fireDatasetEvent(dataset, "beforeInsert", [dataset, mode]);
	if (event_result) throw event_result;

	var pageIndex=(dataset.record)?dataset.record.pageIndex:1;

	var newRecord=new Array();
	pArray_insert(dataset, mode, dataset.record, newRecord);
	initRecord(newRecord, dataset);

	switch (mode){
		case "begin":{
			newRecord.pageIndex=1;
			break;
		}
		case "end":{
			newRecord.pageIndex=dataset.totalPage;
			break;
		}
		default:{
			newRecord.pageIndex=pageIndex;
			break;
		}
	}

	newRecord.recordState="new";
	newRecord.recordno=9999;

	var masterDataset=dataset.masterDataset;
	if (masterDataset && masterDataset.record){
		for(var i=0; i<dataset.masterLinks.length; i++){
			newRecord[dataset.masterLinks[i].detail_index]=masterDataset.record[dataset.masterLinks[i].master_index];
		}
	}

	var fieldCount=dataset.fields.fieldCount;
	for (var i=0; i<fieldCount; i++){
		var defaultValue=getValidStr(dataset.fields[i].defaultValue);
		if (defaultValue!=""){
			if (defaultValue.length>8 && defaultValue.substr(0, 8)=="[script]"){
				newRecord[i]=eval("newRecord[i]="+defaultValue.substr(8));}
			else
				newRecord[i]=defaultValue;
		}
	}

	dataset_setState(dataset, "insert");
	_broadcastDatasetMsg(_notifyDatasetInsert, dataset, dataset.record, [mode, newRecord]);
	_dataset_setRecord(dataset, newRecord);

	fireDatasetEvent(dataset, "afterInsert", [dataset, mode]);
	dataset.modified=true;
}

function dataset_insertRecord(mode){
	try{
		_dataset_insertRecord(this, mode);
	}
	catch(e){
		processException(e);
	}
}

function _dataset_deleteRecord(dataset){
	if (!dataset.record) return;

	needUpdateEditor=false;
	try{
		if (dataset.record.recordState=="new" || dataset.record.recordState=="insert"){
			var event_result=fireDatasetEvent(dataset, "beforeDelete", [dataset]);
			if (event_result) throw event_result;

			dataset.record.recordState="discard";
		}
		else{
			var event_result=fireDatasetEvent(dataset, "beforeDelete", [dataset]);
			if (event_result) throw event_result;

			dataset.record.recordState="delete";
		}

		dataset.modified=false;

		fireDatasetEvent(dataset, "afterDelete", [dataset]);
		dataset_setState(dataset, "none");

		_broadcastDatasetMsg(_notifyDatasetDelete, dataset, dataset.record);
		validateDatasetCursor(dataset);
	}
	finally{
		needUpdateEditor=true;
	}
}

function dataset_deleteRecord(){
	try{
		_dataset_deleteRecord(this);
	}
	catch(e){
		processException(e);
	}
}

function _dataset_updateRecord(dataset){
	if (!dataset.record) return;
	if (!dataset_isRecordValid(dataset.record)) return;

	_broadcastDatasetMsg(_notifyDatasetBeforeUpdate, dataset, dataset.record);

	if (dataset.modified){
		var fieldCount=dataset.fields.fieldCount;
		for (var i=0; i<fieldCount; i++){
			if (!isTrue(dataset.fields[i].readOnly) && isTrue(dataset.fields[i].notNull) &&
				dataset.getFieldText(i)==""){
				throw constErrFieldValueRequired.replace("%s", dataset.fields[i].label);
			}
		}

		var event_result=fireDatasetEvent(dataset, "beforeUpdate", [dataset]);
		if (event_result) throw event_result;

		switch (dataset.record.recordState){
			case "none":{
				dataset.record.recordState="modify";
				break;
			}
			case "new":{
				dataset.record.recordState="insert";
				break;
			}
		}

		for (var i=0; i<fieldCount; i++){
			dataset.record[fieldCount+i]=dataset.record[i];
		}
		dataset.modified=false;

		fireDatasetEvent(dataset, "afterUpdate", [dataset]);
		dataset_setState(dataset, "none");
	}
	else{
		if (dataset.record.recordState=="new"){
			dataset.record.recordState="discard";
			dataset_setState(dataset, "none");
			_broadcastDatasetMsg(_notifyDatasetDelete, dataset, dataset.record);
			validateDatasetCursor(dataset);
		}
	}
}

function dataset_updateRecord(){
	try{
		_dataset_updateRecord(this);
		return true;
	}
	catch(e){
		processException(e);
		return false;
	}
}

function _dataset_cancelRecord(dataset){
	if (!dataset.record) return;

	needUpdateEditor=false;
	try{
		if (dataset.record.recordState=="new"){
			var event_result=fireDatasetEvent(dataset, "beforeCancel", [dataset]);
			if (event_result) throw event_result;

			dataset.record.recordState="discard";

			fireDatasetEvent(dataset, "afterCancel", [dataset]);

			dataset_setState(dataset, "none");
			_broadcastDatasetMsg(_notifyDatasetDelete, dataset, dataset.record);
			validateDatasetCursor(dataset);
		}
		else if (dataset.modified){
			var event_result=fireDatasetEvent(dataset, "beforeCancel", [dataset]);
			if (event_result) throw event_result;

			var fieldCount=dataset.fields.fieldCount;
			for (var i=0; i<fieldCount; i++){
				dataset.record[i]=dataset.record[fieldCount+i];
			}
			dataset.modified=false;

			fireDatasetEvent(dataset, "afterCancel", [dataset]);

			dataset_setState(dataset, "none");
			_broadcastDatasetMsg(_notifyDatasetRefreshRecord, dataset, dataset.record);
		}
	}
	finally{
		needUpdateEditor=true;
	}
}

function dataset_cancelRecord(){
	try{
		_dataset_cancelRecord(this);
	}
	catch(e){
		processException(e);
	}
}

function _dataset_copyRecord(dataset, record){
	for(var i=0; i<dataset.fields.fieldCount; i++){
		var fieldName=dataset.fields[i].name;
		var value=record.getFieldValue(fieldName);
		if (typeof(value)!="undefined") dataset.setFieldValue(fieldName, value);
	}
}

function dataset_copyRecord(record){
	var dataset=this;
	_dataset_copyRecord(dataset, record);
}

function _broadcastDatasetMsg(proc, dataset, record, reserved){
	if (dataset.disableControlCount>0) return;
	var pArray=dataset.editors;
	if (pArray){
		var unit=pArray.firstUnit;
		while (unit && unit.data){
			proc(unit.data, dataset, record, reserved);
			unit=unit.nextUnit;
		}
	}
}

function _broadcastFieldMsg(proc, dataset, record, field, reserved){
	if (dataset.disableControlCount>0) return;
	var pArray=dataset.editors;
	if (pArray){
		var unit=pArray.firstUnit;
		while (unit && unit.data){
			proc(unit.data, dataset, record, field, reserved);
			unit=unit.nextUnit;
		}
	}
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/

function _notifyDatasetCursorChanged(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("attrib")){
		case "datatable":{
			if (!record) break;

			var pageSize=element.getAttribute("pageSize");
			if (element.tBodies[0].rows.length>=pageSize){
				var needRefresh=true;
				var firstRecord=_window.getTableFirstRecord(element);
				var lastRecord=_window.getTableLastRecord(element);

				var _record=firstRecord;
				while (_record){
					if (_record==record){
						needRefresh=false;
						break;
					}

					if (_record==lastRecord) break;
					_record=_record.nextUnit;
				}

				if (needRefresh && firstRecord && lastRecord){
					if (record==firstRecord.getPrevRecord()){
						_window.deleteTableRecord(element.tBodies[0].rows[element.tBodies[0].rows.length-1]);
						_window.insertTableRecord(element, "begin", null, record);
						needRefresh=false;
					}
					else if (record==lastRecord.getNextRecord()){
						_window.deleteTableRecord(element.tBodies[0].rows[0]);
						_window.insertTableRecord(element, "end", null, record);
						needRefresh=false;
					}
				}

				if (needRefresh){
					var counter=pageSize;
					var tmpRecord=record;

					for(var i=0; i<counter; i++){
						tmpRecord=tmpRecord.getNextRecord();
						if (!tmpRecord) break;
					}

					var startRecord=record;
					tmpRecord=record;
					counter=pageSize-i-1;
					for(var i=0; i<counter; i++){
						tmpRecord=tmpRecord.getPrevRecord();
						if (tmpRecord)
							startRecord=tmpRecord;
						else
							break;
					}

					_window.refreshTableData(element, startRecord);
				}
			}

			var row=_window.getTableRowByRecord(element, record);
			if (row){
				_window.setActiveTableRow(row);
			}
			break;
		}
		case "datalabel":{
			_window.refreshElementValue(element);
			break;
		}
		case "editor":;
		case "dockeditor":{
			_window.refreshElementValue(element);
			element.isUserInput=false;
			break;
		}
		case "datapilot":{
			_window.refreshDataPilot(element);
			break;
		}
	}
}

function _notifyDatasetBeforeUpdate(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("attrib")){
		case "dockeditor":{
			_window.updateEditorInput(element);
			break;
		}
	}
}

function _notifyDatasetStateChanged(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("attrib")){
		case "editor":;
		case "dockeditor":{
			var readOnly=dataset.readOnly;
			var field=_window.getElementField(element);
			if (field) readOnly=(readOnly || field.readOnly);
			setEditorReadonly(element, (readOnly || element.readOnly));
			break;
		}
		case "datapilot":{
			_window.refreshDataPilot(element);
			break;
		}
		case "datascrollbar":{
			_window.refreshElementValue(element);
			break;
		}
		case "datatable":{
			if (element.activeRow) _window.refreshTableRowIndicate(element.activeRow);
			break;
		}
	}
}

function _notifyDatasetInsert(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("attrib")){
		case "datatable":{
			var row;
			if (record) row=_window.getTableRowByRecord(element, record);

			_window.insertTableRecord(element, reserved[0], row, reserved[1]);
			if (element.tBodies[0].rows.length>element.getAttribute("pageSize")){
				var lastRecord=_window.getTableLastRecord(element);
				if (lastRecord!=reserved[1]){
					_window.deleteTableRecord(element.tBodies[0].rows[element.tBodies[0].rows.length-1]);
				}
				else{
					_window.deleteTableRecord(element.tBodies[0].rows[0]);
				}
			}
			break;
		}
	}
}

function _notifyDatasetDelete(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("attrib")){
		case "datatable":{
			if (record){
				var row=_window.getTableRowByRecord(element, record);
				if (row){
					if (element.tBodies[0].rows.length<=element.getAttribute("pageSize")){
						var firstRecord=_window.getTableFirstRecord(element);
						var lastRecord=_window.getTableLastRecord(element);
						if (firstRecord){
							var _record=lastRecord.getNextRecord();
							if (_record){
								_window.insertTableRecord(element, "end", row, _record);
							}
							else{
								var _record=firstRecord.getPrevRecord();
								if (_record) _window.insertTableRecord(element, "begin", row, _record);
							}
						}
					}

					_window.deleteTableRecord(row);
				}
			}
			break;
		}
	}
}

function _notifyDatasetRefreshRecord(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("attrib")){
		case "datatable":{
			if (record){
				var row=_window.getTableRowByRecord(element, record);
				if (row) _window.refreshTableRecord(row);
			}
			break;
		}
		case "datalabel":;
		case "editor":;
		case "dockeditor":{
			_window.refreshElementValue(element);
			element.isUserInput=false;
			break;
		}
	}

	if (typeof(_window.sizeDockEditor)!="undefined") _window.sizeDockEditor();
}

function _notifyDatasetRefresh(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("attrib")){
		case "datatable":{
			_window.refreshTableData(element);
			break;
		}
		case "datalabel":;
		case "editor":;
		case "dockeditor":{
			_window.refreshElementValue(element);
			element.isUserInput=false;
			break;
		}
		case "datapilot":{
			_window.refreshDataPilot(element);
			break;
		}
	}
	_notifyDatasetStateChanged(element, dataset, record, reserved);

	if (typeof(_window.sizeDockEditor)!="undefined") _window.sizeDockEditor();
}

function _notifyFieldDataChanged(element, dataset, record, field, reserved){
	var _window=element.window;
	switch (element.getAttribute("attrib")){
		case "datatable":{
			var row=_window.getTableRowByRecord(element, record);
			for(var i=0; i<row.cells.length; i++){
				var cell=row.cells[i];
				if (compareText(cell.getAttribute("dataField"), field.name)){
					_window.refreshElementValue(cell);
				}
			}
			break;
		}
		case "editor":;
		case "dockeditor":{
			if (compareText(element.getAttribute("dataField"), field.name) || compareText(element.getAttribute("keyField"), field.name)){
				_window.refreshElementValue(element);
				element.isUserInput=false;
			}
			break;
		}
		case "datalabel":{
			if (compareText(element.getAttribute("dataField"), field.name)){
				_window.refreshElementValue(element);
			}
			break;
		}
	}

	if (typeof(_window.sizeDockEditor)!="undefined") _window.sizeDockEditor();
}

function _notifyFieldStateChanged(element, dataset, record, field, reserved){

	switch (element.getAttribute("attrib")){
		case "editor":;
		case "dockeditor":{
			if (compareText(element.getAttribute("dataField"), field.name)){
				var readOnly=dataset.readOnly;
				if (field) readOnly=(readOnly || field.readOnly);
				setEditorReadonly(element, (readOnly || element.readOnly));
			}
			break;
		}
	}
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/

function getFieldString(record, fieldName){
	var result="";
	var index=fieldName.indexOf("=");

	if (index>=0){
		result=fieldName.substr(index+1);
		fieldName=fieldName.substr(0, index);
	}
	else{
		var value=record.getFieldValue(fieldName);

		switch (typeof(value)){
			case "number":{
				if (!isNaN(value)) result=value;
				break;
			}
			case "boolean":{
				result=(value)?"1":"0";
				break;
			}
			case "object":{
				if (!isNaN(value)) result=formatDateTime(value, "datetime");
				break;
			}
			default:{
				result=getValidStr(value);
				break;
			}
		}
	}

	result=getEncodeStr(result);
	return result;
}

function resetRecordState(record){
	record.saveOldValue();
	if (record.recordState=="delete")
		record.recordState="discard";
	else
		record.recordState="none";
}

function resetDatasetState(dataset){
	var record=dataset.firstUnit;
	while (record){
		resetRecordState(record);
		record=record.nextUnit;
	}
}

function getKeyFieldArray(dataset){
	var fieldSplit=",";
	if (dataset.keyFields){
		return dataset.keyFields.split(fieldSplit);
	}
	else
		return new Array();
}

function getUpdateFieldArray(dataset){
	var fieldSplit=",";
	if (dataset.updateFields)
		return dataset.updateFields.split(fieldSplit);
	else{
		var result=new Array;
		var count=0;
		for(var i=0; i<dataset.fields.fieldCount; i++){
			if (getValidStr(dataset.fields[i].tableName)=="" || compareText(dataset.fields[i].tableName, dataset.tableName)){
				result[count]=dataset.fields[i].name;
				count++;
			}
		}

		return result;
	}
}

function getRecordUpdateCode(record, keyFieldArray, updateFieldArray, mode){
	try{
		var fieldSplit=",";
		var updateString="";
		var result=new Object();

		fireDatasetEvent(record.dataset, "onGetUpdateString", [record.dataset, record, result]);
		if (!result.updateString){
			if (result.mode) mode=result.mode;
			switch (mode){
				case "delete":{
					if (keyFieldArray.length==0) throw constErrKeyFieldRequired;

					for(var i=0; i<keyFieldArray.length; i++){
						updateString += ((i==0)?"":fieldSplit) + getFieldString(record, "_old_"+keyFieldArray[i]);
					}
					break;
				}
				case "modify":{
					if (keyFieldArray.length==0) throw constErrKeyFieldRequired;
					if (updateFieldArray.length==0) throw constErrUpdateFieldRequired;

					for(var i=0; i<updateFieldArray.length; i++){
						updateString += ((i==0)?"":fieldSplit) + getFieldString(record, updateFieldArray[i]);
					}
					for(var i=0; i<keyFieldArray.length; i++){
						updateString += fieldSplit + getFieldString(record, "_old_"+keyFieldArray[i]);
					}
					break;
				}
				case "insert":{
					if (updateFieldArray.length==0) throw constErrUpdateFieldRequired;

					for(var i=0; i<updateFieldArray.length; i++){
						updateString += ((i==0)?"":fieldSplit) + getFieldString(record, updateFieldArray[i]);
					}
					break;
				}
			}
			result.updateString=updateString;
		}
		if (!result.mode) result.mode=mode;

		return result;
	}
	catch(e){
		processException(e);
	}
}

function composeUpdateString(tableName, keyFieldArray, updateFieldArray, deletestring, updatestring, insertstring){

	function getfieldName(str){
		var index=str.indexOf("=");

		if (index>=0){
			return str.substr(0, index);
		}
		else{
			return str;
		}
	}

	if (trimStr(deletestring)=="" && trimStr(updatestring)=="" && trimStr(insertstring)=="") return "";

	var fieldSplit=",";
	var recordSplit=";";

	for(var i=0; i<keyFieldArray.length; i++)
		keyFieldArray[i]=getEncodeStr(getfieldName(keyFieldArray[i]));

	for(var i=0; i<updateFieldArray.length; i++)
		updateFieldArray[i]=getEncodeStr(getfieldName(updateFieldArray[i]));

	var result="T"+getEncodeStr(tableName);
	result+="@K"+keyFieldArray.join(fieldSplit);
	result+="@F"+updateFieldArray.join(fieldSplit);
	if (trimStr(deletestring)!="") result+="@D"+deletestring;
	if (trimStr(updatestring)!="") result+="@U"+updatestring;
	if (trimStr(insertstring)!="") result+="@I"+insertstring;
	return result;
}

function composeUpdateSQL(SqlArray){
	var recordSplit=";";

	for(var i=0; i<SqlArray.length; i++)
		SqlArray[i]=getEncodeStr(SqlArray[i]);

	return SqlArray.join(recordSplit);
}

function _getRecordUpdateString(record, mode){
	var deleteString="", modifyString="", insertString="", tmpResult;
	var keyFieldArray=getKeyFieldArray(record.dataset);
	var updateFieldArray=getUpdateFieldArray(record.dataset);
	tmpResult=getRecordUpdateCode(record, keyFieldArray, updateFieldArray, (mode)?mode:record.recordState);
	switch (tmpResult.mode){
		case "delete":{
			deleteString=tmpResult.updateString;
			break;
		}
		case "modify":{
			modifyString=tmpResult.updateString;
			break;
		}
		case "insert":{
			insertString=tmpResult.updateString;
			break;
		}
	}
	delete tmpResult;

	return composeUpdateString(record.dataset.tableName,
		keyFieldArray,
		updateFieldArray,
		deleteString,
		modifyString,
		insertString);
}

function getRecordUpdateString(record, mode){
	try{
		return _getRecordUpdateString(record, mode);
	}
	catch(e){
		processException(e);
		return "";
	}
}

function _getUpdateString(datasetArray){

	function doGetUpdateString(dataset){
		var fieldSplit=",";
		var recordSplit=";";
		var keyFieldArray=getKeyFieldArray(dataset);
		var updateFieldArray=getUpdateFieldArray(dataset);
		var deleteString="", modifyString="", insertString="", tmpResult;
		var dCount=0, mCount=0, iCount=0;

		var record=dataset.firstUnit;
		while (record){
			tmpResult=getRecordUpdateCode(record, keyFieldArray, updateFieldArray, record.recordState);
			switch (tmpResult.mode){
				case "delete":{
					deleteString+=((dCount==0)?"":recordSplit)+tmpResult.updateString;
					dCount++;
					break;
				}
				case "modify":{
					modifyString+=((mCount==0)?"":recordSplit)+tmpResult.updateString;
					mCount++;
					break;
				}
				case "insert":{
					insertString+=((iCount==0)?"":recordSplit)+tmpResult.updateString;
					iCount++;
					break;
				}
			}
			delete tmpResult;

			record=record.nextUnit;
		}

		return composeUpdateString(dataset.tableName,
			keyFieldArray,
			updateFieldArray,
			deleteString,
			modifyString,
			insertString);
	}

	for(var i=0; i<datasetArray.length; i++){
		_dataset_updateRecord(datasetArray[i]);
	}

	var result="";
	for(var i=0; i<datasetArray.length; i++){
		var tmp=doGetUpdateString(datasetArray[i]);
		if (tmp) result += ((result=="")?"":"&") + tmp;
	}
	return result;
}

function getUpdateString(datasetArray){
	try{
		return _getUpdateString(datasetArray);
	}
	catch(e){
		processException(e);
		return "";
	}
}

function _submitUpdateString(connectionString, updateString){
	if (trimStr(updateString)=="") return;
	if (showSubmitCommand) alert("UpdateString:\n"+updateString+".");
	var data=new Object();
	data.connectionString=connectionString;
	data.updateString=updateString;
	data=showModalDialog(xForm_Library_path+"SaveDataFrame.asp", data,
		"dialogHeight: 80px; dialogWidth: 220px; center: Yes; help: No; resizable: No; status: No");
	if (!data.result) throw new Error(data.error_num, data.error_text);
	return true;
}

function submitUpdateString(connectionString, updateString){
	try{
		_submitUpdateString(connectionString, updateString);
		return true;
	}
	catch(e){
		processException(getDBErrorDescription(e));
		return false;
	}
}

function submitUpdate(datasetArray){
	try{
		var updateString=_getUpdateString(datasetArray);
		try{
			if (_submitUpdateString(datasetArray[0].connection, updateString)){
				for(var i=0; i<datasetArray.length; i++) resetDatasetState(datasetArray[i]);

			}
			return true;
		}
		catch(e){
			processException(getDBErrorDescription(e));
			return false;
		}
	}
	catch(e){
		processException(e);
		return false;
	}
}

function submitRecord(record, mode){
	try{
		var updateString=_getRecordUpdateString(record, mode);
		try{
			if (_submitUpdateString(record.dataset.connection, updateString)){
				resetRecordState(record);
			}
			return true;
		}
		catch(e){
			processException(getDBErrorDescription(e));
			return false;
		}
	}
	catch(e){
		processException(e);
		return false;
	}
}

function getDBErrorDescription(errorObj){
	error_number=errorObj.number & 0xFFFF;
	var err_type;
	switch (error_number){		
		case 3421:{
			err_type=constErrDataType;
			break;
		}
		case 3604:{
			err_type=constErrKeyViolence;
			break;
		}
		default:{
			err_type=constErrUnknown;
			break;
		}
	}
	return constErrUpdateFailed+"\n"+
		constErrType+":"+err_type+" ("+error_number+")\n"+
		constErrDescription+":"+errorObj.description;
}

//-----------------------
// xForm v2.0
// Developer: bao yilei
// Nov, 2001
//-----------------------

var _dropdown_parentwindow=null;
var _dropdown_parentbox=null;
var _dropdown_box=null;
var _dropdown_table=null;
var _dropdown_frame=null;
var _dropdown_dataset=null;
var _date_dropdown_box=null;

var _calendarControl=null;
var _tmp_dataset_date=null;

function initDropDownBox(dropDown_mode){
	_document_loading=true;

	switch (dropDown_mode){
		case "data":{
			_dropdown_div.onkeydown=_dropdown_onkeydown;
		}

		case "custom":{
			_dropdown_parentwindow=window.parent;
			_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
			_dropdown_parentwindow._dropdown_window=window;
			if (!_dropdown_parentbox || _dropdown_parentbox.style.visibility=="hidden") return;

			var editor=_dropdown_parentbox.editor;
			if (dropDown_mode=="data") _dropdown_div.style.width=editor.offsetWidth;

			_dropdown_parentwindow.sizeDropDownBox();

			with (_dropdown_parentwindow._dropdown_frame){
				width="100%";
				if (filters.blendTrans.status!=2) {
					if (getIEVersion()<"5.5"){
						style.visibility="visible";
					}
					else{
						filters.blendTrans.apply();
						style.visibility="visible";
						filters.blendTrans.play();
					}
				}
			}

			hideStatusLabel(_dropdown_parentwindow);
			break;
		}

		case "date":{
			_dropdown_parentwindow=window;
			_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
			_dropdown_parentwindow._dropdown_window=window;
			sizeDropDownBox();
			if (_dropdown_parentbox.filters.blendTrans.status!=2 && !(getIEVersion()<"5.5"))
				_dropdown_parentbox.filters.blendTrans.play();
			break;
		}

		default:{
			_dropdown_parentwindow=window;
			_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
			_dropdown_parentwindow._dropdown_window=window;
			_dropdown_dataset=getElementDataset(_dropdown_table);
			sizeDropDownBox();
			if (_dropdown_parentbox.filters.blendTrans.status!=2 && !(getIEVersion()<"5.5"))
				_dropdown_parentbox.filters.blendTrans.play();
			break;
		}
	}

	_dropdown_parentbox.prepared=true;
	var editor=_dropdown_parentbox.editor;
	if (editor) dropDownLocate();
	_document_loading=false;
}

function sizeDropDownBox(){
	function _sizeDropDownBox(new_width, new_height){
		with (_dropdown_box){
			var editor=_dropdown_box.editor;
			var maxHeight=parseInt(editor.getAttribute("dropdown_height"));
			if (isNaN(maxHeight) || maxHeight<20) maxHeight=220;

			var pos=getAbsPosition(editor, document.body);
			if (editor.getAttribute("attrib")=="dockeditor")
				var _posLeft=pos[0]+2;
			else
				var _posLeft=pos[0]+1;
			var _posTop=pos[1]+editor.offsetHeight+1;

			if (new_height>maxHeight &&
				!(editor.getAttribute("dropdown_mode")=="data" && getInt(editor.getAttribute("dropDown_pageSize"))>0)){
				new_height=maxHeight;
				new_width+=16;
				if (!(getIEVersion()<"5.5"))
					style.overflowY="scroll";
				else
					style.overflowY="visible";
			}
			else{
				style.overflowY="hidden";
			}
			style.posHeight=new_height;
			if (new_width>style.posWidth) style.posWidth=new_width;

			var document_width=document.body.clientWidth + document.body.scrollLeft;
			var document_height=document.body.clientHeight + document.body.scrollTop;
			if (_posLeft+new_width>document_width && document_width>new_width) _posLeft=document_width-new_width;
			if (_posTop+new_height>document_height && pos[1]>new_height) _posTop=pos[1]-new_height-1;
			style.posLeft=_posLeft;
			style.posTop=_posTop;
		}
	}

	if (!isDropdownBoxVisible()) return;

	try{
		var _width, _height;
		switch (_dropdown_box.getAttribute("dropDown_mode")){
			case "data":;
			case "custom":{
				with (_dropdown_frame){
					_height=_dropdown_window._dropdown_div.offsetHeight;
					style.posHeight=_height;
					if (_dropdown_window._dropdown_div.offsetWidth>_dropdown_box.clientWidth){
						_width=_dropdown_window._dropdown_div.offsetWidth;
						style.posWidth=_width;
					}
				}
				break;
			}

			case "date":{
				_width=CalendarTable.offsetWidth;
				_height=CalendarTable.offsetHeight;
				break;
			}

			default:{
				_width=_dropdown_table.offsetWidth;
				_height=_dropdown_table.offsetHeight;
				break;
			}
		}
		_sizeDropDownBox(_width, _height);
	}
	catch(e){
		//do nothing
	}
}

function canDropDown(editor){
	return (editor.getAttribute("dropDown_mode") &&
		!compareText(editor.type, "checkbox"));
}

function getDropDownCacheSaver(editor){
	if (editor.getAttribute("attrib")=="editor"){
		return editor;
	}
	else{
		var table=getTableByCell(editor.editorHolder);
		if (!table[editor.editorHolder.id]) table[editor.editorHolder.id]=new Object();
		return table[editor.editorHolder.id];
	}
}

function getDropDownBox(editor){
	var needCreate=true;
	var dropdown_saver=getDropDownCacheSaver(editor);

	if (editor.getAttribute("dropDown_mode")=="date"){
		needCreate=false;
		_dropdown_box=_date_dropdown_box;
	}
	else if (isTrue(editor.getAttribute("dropDown_cached"))){
		_dropdown_box=dropdown_saver.cached_dropdownbox;
		switch (editor.getAttribute("dropDown_mode")){
			case "staticlist":{
				needCreate=(dropdown_saver.cached_dropdown_items!=editor.getAttribute("dropdown_items"));
				break;
			}
			case "dataset":{
				needCreate=(dropdown_saver.cached_dropdown_dataset!=editor.getAttribute("dropdown_dataset"));
				break;
			}
			case "data":{
				needCreate=(dropdown_saver.cached_dropdown_connection!=editor.getAttribute("dropdown_connection") ||
					dropdown_saver.cached_dropdown_sql!=editor.getAttribute("dropdown_sql"));
				break;
			}
			case "custom":{
				needCreate=(dropdown_saver.cached_dropdown_url!=editor.getAttribute("dropdown_url"));
				break;
			}
		}
	}

	if (needCreate || !_dropdown_box){
		_dropdown_box=document.createElement("<DIV style=\"overflow-X: hidden; position: absolute; visibility: hidden; filter: blendTrans(duration=0.3)\"></DIV>");
		document.body.appendChild(_dropdown_box);
	}
}

function getDropDownBtn(){
	if  (typeof(_dropdown_btn)=="undefined"){
		obj=document.createElement("<INPUT id=_dropdown_btn type=button tabindex=-1 value=6 hidefocus=true"+
			" style=\"position: absolute; visibility: hidden; border: #333333 1px solid; font-family: Marlett; font-size: 10px; cursor: hand; z-index: 9999\""+
			" LANGUAGE=javascript onmousedown=\"return _dropdown_btn_onmousedown(this)\" onfocus=\"return _dropdown_btn_onfocus(this)\" "+
			" onmouseover=\"return _button_onmouseover()\" onmouseout=\"return _button_onmouseout()\">");
		document.body.appendChild(obj);
		return obj
	}
	else{
		return _dropdown_btn;
	}
}

function showDropDownBox(_editor){
	try{
		if (!canDropDown(_editor)) return;
		if (!isDropdownBoxVisible()){
			var eventName=getElementEventName(_editor, "beforeDropDown");
			var event_result=fireUserEvent(eventName, [_editor]);
			if (event_result) throw event_result;

			getDropDownBox(_editor);
			_dropdown_box.prepared=false;
			if (_dropdown_box.filters.blendTrans.status==2) return;

			var dataset=getElementDataset(_editor);
			if (dataset){
				if (!dataset.record) dataset.insertRecord();
			}

			with (_dropdown_box){
				style.overflowY="hidden";
				setAttribute("editor", _editor);

				var dropDown_mode=_editor.getAttribute("dropDown_mode");
				setAttribute("dropDown_mode", dropDown_mode);

				switch (dropDown_mode){
					case "data":;
					case "custom":{
						style.visibility="visible";
						if (_editor.offsetWidth>128)
							style.width=editor.offsetWidth
						else
							style.width=128;
						break;
					}

					default:{
						if (filters.blendTrans.status!=2) {
							if (!(getIEVersion()<"5.5")) filters.blendTrans.apply();
							style.visibility="visible";
						}
						break;
					}
				}

				if (!_dropdown_box.cached){
					switch (dropDown_mode){
						case "data":{
							showStatusLabel(window, constDownLoadingData);
							var sql=_editor.getAttribute("dropDown_sql");
							var connection=editor.getAttribute("dropDown_connection");
							if (!connection && dataset) connection=dataset.connection;
							if (sql && connection){
								_dropdown_box.innerHTML="<IFRAME id=_dropdown_frame height=0 frameborder=0 marginheight=0 marginwidth=0 scrolling=no"+
									" src=\""+xForm_Library_path+"dropdowndata.asp?sql="+escape(sql)+"&connection="+escape(connection)+"&tableName="+_editor.getAttribute("dropDown_tableName")+"&pageSize="+getInt(editor.getAttribute("dropdown_pageSize"))+
									"&fields="+getValidStr(_editor.getAttribute("dropDown_fields"))+"\""+
									" style=\"position:absolute; visibility:hidden; left:0; top:0; border-style: none; filter: blendTrans(duration=0.3)\"></IFRAME>";
								_dropdown_frame=_dropdown_box.firstChild;
							}
							break;
						}

						case "custom":{
							showStatusLabel(window, constDownLoadingData);
							_dropdown_box.innerHTML="<IFRAME id=_dropdown_frame height=0 frameborder=0 marginheight=0 marginwidth=0 scrolling=no"+
								" src=\""+_editor.getAttribute("dropDown_url")+"\""+
								" scrolling=no style=\"overflow: hidden; position:absolute; visibility:hidden; left:0; top:0; border-style: none; filter: blendTrans(duration=0.3)\"></IFRAME>";
							_dropdown_frame=_dropdown_box.firstChild;
							break;
						}

						case "date":{
							createCalendar(_dropdown_box);
							initDropDownBox(dropDown_mode);
							_dropdown_box.onkeydown=_calendar_onkeydown;
							break;
						}

						default:{
							style.width=_editor.offsetWidth;
							createStaticListTable(_dropdown_box);
							_dropdown_table.onkeydown=_dropdown_onkeydown;

							if (dropDown_mode=="staticlist")
								_dataset=getDropDownItems(editor);
							else{
								_dataset=editor.getAttribute("dropDown_dataset");
								if (typeof(_dataset)=="string") _dataset=getDatasetByID(_dataset);
							}

							if (_dataset){
								setElementDataset(_dropdown_table, _dataset);
								_dropdown_table.fields=editor.getAttribute("dropDown_fields");
								initElements(_dropdown_table);
								refreshTableData(_dropdown_table);
							}
							initDropDownBox(dropDown_mode);
							break;
						}
					}
				}
				else{
					var dropdown_saver=getDropDownCacheSaver(_editor);
					switch (dropDown_mode){
						case "data":;
						case "custom":{
							var _window=dropdown_saver.cached_dropdown_window;
							_window.initDropDownBox(dropDown_mode);
							break;
						}

						default:{
							if (getIEVersion()<"5.5"){
								for (var i=0; i<_dropdown_box.children.length; i++){
									_dropdown_box.children[i].style.visibility="visible";
								}
							}
							_dropdown_table=dropdown_saver.cached_dropdown_table;
							initDropDownBox(dropDown_mode);
							break;
						}
					}
				}
			}
			_editor.dropDown_visible=true;
			if  (typeof(_dropdown_btn)!="undefined") _dropdown_btn.value="5";
		}
	}
	catch(e){
		processException(e);
	}
}

function hideDropDownBox(){
	if (!_dropdown_box) return;
	if (isDropdownBoxVisible()){
		_skip_activeChanged=true;
		var editor=_dropdown_box.editor;
		if (_dropdown_box.prepared && (isTrue(editor.getAttribute("dropDown_cached")) ||
			_dropdown_box.getAttribute("dropDown_mode")=="date")){
			var dropdown_saver=getDropDownCacheSaver(editor);

			dropdown_saver.cached_dropdownbox=_dropdown_box;
			_dropdown_box.cached=true;
			switch (_dropdown_box.getAttribute("dropDown_mode")){
				case "staticlist":{
					dropdown_saver.cached_dropdown_items=editor.getAttribute("dropdown_items");
					dropdown_saver.cached_dropdown_table=_dropdown_table;
					break;
				}
				case "dataset":{
					dropdown_saver.cached_dropdown_dataset=editor.getAttribute("dropdown_dataset");
					dropdown_saver.cached_dropdown_table=_dropdown_table;
					break;
				}
				case "data":{
					dropdown_saver.cached_dropdown_connection=editor.getAttribute("dropdown_connection");
					dropdown_saver.cached_dropdown_sql=editor.getAttribute("dropdown_sql");
					dropdown_saver.cached_dropdown_window=_dropdown_window;
					break;
				}
				case "custom":{
					dropdown_saver.cached_dropdown_url=editor.getAttribute("dropdown_url");
					dropdown_saver.cached_dropdown_window=_dropdown_window;
					break;
				}
				case "date":{
					_date_dropdown_box=_dropdown_box;
					break;
				}
			}

			if (getIEVersion()<"5.5"){
				for (var i=0; i<_dropdown_box.children.length; i++){
					_dropdown_box.children[i].style.visibility="hidden"
				}
			}
			_dropdown_box.style.visibility="hidden";
			_dropdown_window=null;
		}
		else{
			_dropdown_box.editor=null;
			switch (_dropdown_box.getAttribute("dropDown_mode")){
				case "staticlist":
				case "dataset":{
					setElementDataset(_dropdown_table, null);
					break;
				}
				case "data":;
				case "custom":{
					if (typeof(_dropdown_frame)!="undefined"){
						_dropdown_frame.style.visibility="hidden";
						_dropdown_frame.removeNode(true);
					}
					break;
				}
			}
			editor.cached_dropdownbox=null;
			_dropdown_window=null;

			if (getIEVersion()<"5.5"){
				for (var i=0; i<_dropdown_box.children.length; i++){
					_dropdown_box.children[i].style.visibility="hidden"
				}
			}
			_dropdown_box.style.visibility="hidden";
			_dropdown_box.removeNode(true);
			_dropdown_box=null;
		}

		editor.dropDown_visible=false;
		if  (typeof(_dropdown_btn)!="undefined") _dropdown_btn.value="6";
	}
}

function isDropDownBtnVisible(){
	if  (typeof(_dropdown_btn)!="undefined")
		return (_dropdown_btn.style.visibility=="visible")
	else
		return false;
}

function sizeDropDownBtn(_editor){
	if (!isDropDownBtnVisible()) return;
	with (_dropdown_btn){
		var pos=getAbsPosition(_editor);

		style.height=_editor.offsetHeight;
		style.width=16;
		style.posLeft=pos[0]+_editor.offsetWidth-offsetWidth;
		style.posTop=pos[1];
	}
}

function showDropDownBtn(_editor){
	if (!canDropDown(_editor)) return;
	getDropDownBtn();
	if (typeof(_dropdown_btn)=="undefined") return;

	with (_dropdown_btn){
		if (!isDropDownBtnVisible()){
			setAttribute("editor", _editor);
			style.visibility="visible";
			sizeDropDownBtn(_editor);

			var oldWidth=_editor.offsetWidth;
			_editor.style.borderRightWidth=16;
			_editor.style.width=oldWidth;
		}
	}
}

function hideDropDownBtn(){
	if  (typeof(_dropdown_btn)=="undefined") return;

	if (isDropDownBtnVisible()){
		var _editor=_dropdown_btn.editor;
		if (_editor){
			var oldWidth=_editor.offsetWidth;
			_editor.style.borderRightWidth=1;
			_editor.style.width=oldWidth;
		}
		_dropdown_btn.style.visibility="hidden";
		_dropdown_btn.editor=null;
	}
}

function _dropdown_btn_onmousedown(button){
	var obj=button.editor;
	if (!isDropdownBoxVisible()){
		if (obj) showDropDownBox(obj);
	}
	else
		hideDropDownBox();
}

function _dropdown_btn_onfocus(button){
	var obj=button.editor;
	if (obj) obj.focus();
}

function createStaticListTable(parent_element){
	_dropdown_table=document.createElement("<table attrib=datatable isDropDownTable=true readOnly=true width=100% "+
		" border=1 bordercolor=silver cellspacing=0 cellpadding=2 rules=all></table>");

	if (parent_element)
		parent_element.appendChild(_dropdown_table);
	else
		document.body.appendChild(_dropdown_table);
}

function dropDownLocate(){
	var editor=_dropdown_parentbox.editor;
	switch (editor.getAttribute("dropDown_mode")){
		case "date":{
			var _date=new Date(editor.value);
			if (!isNaN(_date)) setCalendarDate(_date);
			break;
		}
		default:{
			if (_dropdown_dataset){
				var fieldName;

				if (editor.getAttribute("dropDown_mode")=="staticlist"){
					fieldName=(editor.getAttribute("dropDown_mapValue"))?"name":"value";
				}
				else{
					if (editor.use_keyField && editor.getAttribute("keyField")){
						fieldName=editor.getAttribute("dropDown_keyField");
						if (!fieldName) fieldName=editor.getAttribute("keyField");
					}
					else{
						fieldName=editor.getAttribute("dropDown_dataField");
						if (!fieldName) fieldName=editor.getAttribute("dataField");
					}
				}

				var value=editor.value;
				var record=_dropdown_dataset.locate(fieldName, value);
				if (record) _dropdown_dataset.setRecord(record);
			}
			break;
		}
	}
}

function hideDropDown() {
	var editor=_dropdown_parentbox.editor;
	_dropdown_parentwindow.hideDropDownBox();
	editor.focus();
}

function _standard_dropdown_keyDown(keycode){
	switch(keycode){
		//PageUp
		case 33:{
			var pageIndex=(_dropdown_dataset.record)?_dropdown_dataset.record.pageIndex-1:1;
			_dropdown_dataset.moveToPage(pageIndex);
			break;
		}
		//PageDown
		case 34:{
			var pageIndex=(_dropdown_dataset.record)?_dropdown_dataset.record.pageIndex+1:1;
			_dropdown_dataset.moveToPage(pageIndex);
			break;
		}
		//Up
		case 38:{
			if (_dropdown_dataset){
				_dropdown_dataset.movePrev();
			}
			break;
		}
		//Down
		case 40:{
			if (_dropdown_dataset){
				_dropdown_dataset.moveNext();
			}
			break;
		}
	}
}

function processDropDownKeyDown(keycode) {
	switch(keycode){
		//Enter
		case 13:{
			dropDownSelected();
			break;
		}
		//ESC
		case 27:{
			hideDropDown();
			break;
		}
		//F2
		case 113:{
			hideDropDown();
			break;
		}
		//F7
		case 118:{
			hideDropDown();
			break;
		}
		default:{
			var editor=_dropdown_parentbox.editor;
			switch (editor.getAttribute("dropDown_mode")){
				case "staticlist":
				case "dataset":
				case "data":{
					_standard_dropdown_keyDown(keycode);
					break;
				}
				case "date":{
					_calendar_onkeydown();
					break;
				}
				default:{
					if (typeof(dropDown_onKeyDown)!="undefined") dropDown_onKeyDown(keycode);
					break;
				}
			}
		}
	}
}

function dropDownSelected(){
	var record;
	var editor=_dropdown_parentbox.editor;
	switch (editor.getAttribute("dropDown_mode")){
		case "staticlist":
		case "dataset":
		case "data":{
			if (_dropdown_dataset) record=_dropdown_dataset.record;
			break;
		}
		case "date":{
			_tmp_dataset_date=createDataset("_tmp_dataset_date");
			_tmp_dataset_date.addField("value");
			initDataset(_tmp_dataset_date);
			_tmp_dataset_date.insertRecord();
			_tmp_dataset_date.setFieldValue("value", new Date(_calendarControl.year, _calendarControl.month, _calendarControl.day));
			_tmp_dataset_date.updateRecord();
			record=_tmp_dataset_date.record;
			break;
		}
		default:{
			if (typeof(dropDown_onGetRecord)!="undefined") record=dropDown_onGetRecord();
			break;
		}
	}

	if (record){
		_dropdown_parentwindow.processDropDownSelected(_dropdown_parentbox.editor, record, false);
		hideDropDown();
	}
	if (_tmp_dataset_date) freeDataset(_tmp_dataset_date);
}

function _dropdown_onkeydown(){
	processDropDownKeyDown(event.keyCode);
}

function _dropdown_onclick(){
	dropDownSelected();
	event.cancelBubble=true;
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/

var _calendar_months, _calendar_days;

function createCalendar(parent_element){

	function calendar(){
	 	var today=new Date()
	 	this.todayDay=today.getDate();
		this.todayMonth=today.getMonth();
		this.todayYear=today.getFullYear();
	 	this.activeCellIndex=0;
	}

	_calendar_months=new Array(constJanuary, constFebrary, constMarch, constApril, constMay, constJune, constJuly, constAugust, constSeptember, constOctober, constNovember, constDecember);
	_calendar_days=new Array(constSunday, constMonday, constTuesday, constWednesday, constThursday, constFriday, constSaturday);
	_calendarControl=new calendar();

	var tmpHTML="";
	tmpHTML+="<TABLE id=\"CalendarTable\" border=1 bordercolor=silver rule=all width=260px cellspacing=0 cellpadding=2>";

	tmpHTML+="<TR class=calendar_title valign=top>";
	tmpHTML+="<TD>";
	tmpHTML+="<TABLE WIDTH=100% CELLSPACING=0 CELLPADDING=0 style=\"FONT-SIZE: 9pt; FONT-WEIGHT: bold\">";
	tmpHTML+="<TR>";
	tmpHTML+="<TD WIDTH=20% align=left>";
	tmpHTML+="<INPUT type=button attrib=button value=3 title=\""+constLastMonth+"\" style=\"FONT-SIZE:8;FONT-FAMILY: webdings\" onclick=\"changeCalendarDate(_calendarControl.preYear,_calendarControl.preMonth)\">";
	tmpHTML+="</TD>";
	tmpHTML+="<TD WIDTH=25% align=right id=\"monthValue\" nowrap>";
	tmpHTML+="</TD>";
	tmpHTML+="<TD WIDTH=10% id=\"yearValue\" nowrap>";
	tmpHTML+="</TD>";
	tmpHTML+="<TD WIDTH=25 align=center>";
	tmpHTML+="<TABLE CELLSPACING=0 CELLPADDING=0>";
	tmpHTML+="<TR>";
	tmpHTML+="<TD valign=bottom>";
	tmpHTML+="<INPUT type=button attrib=button value=5 title=\""+constLastYear+"\" style=\"HEIGHT: 10;FONT-SIZE:7;FONT-FAMILY: webdings\" onclick=\"changeCalendarDate(_calendarControl.year-1,_calendarControl.month)\">";
	tmpHTML+="</TD>";
	tmpHTML+="</TR>";
	tmpHTML+="<TR>";
	tmpHTML+="<TD valign=top>";
	tmpHTML+="<INPUT type=button attrib=button value=6 title=\""+constNextYear+"\" style=\"HEIGHT: 10;FONT-SIZE: 7;FONT-FAMILY: webdings\" onclick=\"changeCalendarDate(_calendarControl.year+1,_calendarControl.month)\">";
	tmpHTML+="</TD>";
	tmpHTML+="</TR>";
	tmpHTML+="</TABLE>";
	tmpHTML+="</TD>";
	tmpHTML+="<TD WIDTH=20% align=right>";
	tmpHTML+="<INPUT type=button attrib=button value=4 title=\""+constNextMonth+"\" style=\"FONT-SIZE: 8;FONT-FAMILY: webdings\" onclick=\"changeCalendarDate(_calendarControl.nextYear,_calendarControl.nextMonth)\">";
	tmpHTML+="</TD>";
	tmpHTML+="</TR>";
	tmpHTML+="</TABLE>";
	tmpHTML+="</TD>";
	tmpHTML+="</TR>";
	tmpHTML+="<TR class=calendar_week>";
	tmpHTML+="<TD>";
	tmpHTML+="<TABLE WIDTH=100% HEIGHT=5% CELLSPACING=0 CELLPADDING=0 id=\"calendarWeek\" style=\"FONT-SIZE: 9pt\">";
	tmpHTML+="<TR>";
	for (var i=0;i<=6;i++){
		tmpHTML+="<TD width=14% align=center>"+_calendar_days[i]+"</TD>";
	}
	tmpHTML+="</TR>";
	tmpHTML+="</TABLE>";
	tmpHTML+="</TD>";
	tmpHTML+="</TR>";

	tmpHTML+="<TR class=calendar_data>";
	tmpHTML+="<TD>";
	tmpHTML+="<TABLE HEIGHT=30% id=\"calendarData\" HEIGHT=100% WIDTH=100% CELLSPACING=0 CELLPADDING=0 style=\"PADDING-TOP: 0px; FONT-SIZE: 9pt; CURSOR: hand\"";
	tmpHTML+="onclick=\"_calendar_cell_onclick(event.srcElement)\">";

	for(var i=0;i<=5;i++){
		tmpHTML+="<TR HEIGHT=10%>";
		for(var j=0;j<=6;j++){
			tmpHTML+="<TD align=center></TD>";
		}
		tmpHTML+="</TR>";
	}
	tmpHTML+="</TABLE>";
	tmpHTML+="</TD>";
	tmpHTML+="</TR>";

	tmpHTML+="<TR class=calendar_footer>";
	tmpHTML+="<TD align=right>";
	tmpHTML+="<INPUT attrib=button type=button id=\"button_today\" value=\""+constToday+" "+_calendarControl.todayYear+"-"+((_calendarControl.todayMonth+1)>9?(_calendarControl.todayMonth+1):("0"+(_calendarControl.todayMonth+1)))+"-"+(_calendarControl.todayDay>9?(_calendarControl.todayDay):("0"+(_calendarControl.todayDay)))+"\" onclick=\"_calendar_today_onclick()\" ";
	tmpHTML+="style=\"height: 16\"";
	tmpHTML+="</TD>";
	tmpHTML+="</TR>";
	tmpHTML+="</TABLE>";
	if (parent_element)
		parent_element.innerHTML=tmpHTML;
	else
		document.body.innerHTML=tmpHTML;

	initElements(CalendarTable);
	changeCalendarDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay)
}

function setCalendarDate(date){
	changeCalendarDate(date.getFullYear(),date.getMonth(),date.getDate());
}

function changeCalendarDate(year, month, day){
	if (_calendarControl.year==year && _calendarControl.month==month && (!day || _calendarControl.day==day)) return;

	if (_calendarControl.year!=year || _calendarControl.month!=month){
		_calendarControl.year=year;
		_calendarControl.month=month;

		if (month==0){
			 _calendarControl.preMonth=11
			 _calendarControl.preYear=_calendarControl.year-1
		}else{
			 _calendarControl.preMonth=_calendarControl.month-1
			 _calendarControl.preYear=_calendarControl.year
		}
		if (month==11){
			_calendarControl.nextMonth=0
			_calendarControl.nextYear=_calendarControl.year+1
		}else{
			_calendarControl.nextMonth=_calendarControl.month+1
			_calendarControl.nextYear=_calendarControl.year

		}
		_calendarControl.startday=(new Date(year,month,1)).getDay()
		if (_calendarControl.startday==0) _calendarControl.startday=7
		var curNumdays=getNumberOfDays(_calendarControl.month,_calendarControl.year)
		var preNumdays=getNumberOfDays(_calendarControl.preMonth,_calendarControl.preYear)
		var nextNumdays=getNumberOfDays(_calendarControl.nextMonth,_calendarControl.nextYear)
		var startDate=preNumdays-_calendarControl.startday+1
		var endDate=42-curNumdays-_calendarControl.startday

		monthValue.innerText=_calendar_months[_calendarControl.month]+", "
		yearValue.innerText=_calendarControl.year

		var datenum=0
		for (var i=startDate;i<=preNumdays;i++){
			calendarData.cells[datenum].monthAttribute="pre";
			calendarData.cells[datenum].style.color="gray";
			calendarData.cells[datenum].innerText=i;
			datenum++;
		}
		for (var i=1;i<=curNumdays;i++){
			calendarData.cells[datenum].monthAttribute="cur";
			calendarData.cells[datenum].style.color="black";
			calendarData.cells[datenum].innerText=i;
			datenum++;
		}
		for (var i=1;i<=endDate;i++){
			calendarData.cells[datenum].monthAttribute="next";
			calendarData.cells[datenum].style.color="gray";
			calendarData.cells[datenum].innerText=i;
			datenum++;
		}
	}

	if (day) _calendarControl.day=day;
	setCalendarActiveCell(calendarData.cells[_calendarControl.day+_calendarControl.startday-1]);
}

function setCalendarActiveCell(cell){

	function setActiveCell(cellIndex){
		calendarData.cells[_calendarControl.activeCellIndex].style.backgroundColor="";
		calendarData.cells[cellIndex].style.backgroundColor="#ffebcd";
		_calendarControl.activeCellIndex=cellIndex;
		activeCell=_calendarControl.activeCellIndex%7
		activeRow=Math.floor(_calendarControl.activeCellIndex/7)
	}

	if (cell.tagName.toLowerCase()!="td") return;
	var _activeCellIndex=cell.parentElement.rowIndex*7+cell.cellIndex;

	with(_calendarControl){
		if (activeCellIndex==_activeCellIndex) return;

		var monthAttribute=cell.monthAttribute;
		switch (monthAttribute){
			case "pre":{
				changeCalendarDate(preYear,preMonth,getNumberOfDays(preMonth,preYear)-startday+_activeCellIndex+1);
				setActiveCell(startday+day-1);
				break
			}
			case "cur":{
				changeCalendarDate(year,month,_activeCellIndex-startday+1);
				setActiveCell(_activeCellIndex);
				break
			}
			case "next":{
				changeCalendarDate(nextYear,nextMonth,_activeCellIndex-getNumberOfDays(month,year)-startday+1);
				setActiveCell(startday+day-1);
				break
			}
		}
	}
}

function _calendar_cell_onclick(cell){
	setCalendarActiveCell(cell)
	dropDownSelected()
}

function _calendar_onkeydown(){
	switch(event.keyCode){
		case 33:{//PgUp
			if (event.ctrlKey){
				changeCalendarDate(_calendarControl.year-1,_calendarControl.month)
			}else{
				changeCalendarDate(_calendarControl.preYear,_calendarControl.preMonth)
			}
			break
		}
		case 34:{//PgDn
			if (event.ctrlKey){
				 changeCalendarDate(_calendarControl.year+1,_calendarControl.month)
			}else{
				 changeCalendarDate(_calendarControl.nextYear,_calendarControl.nextMonth)
			}
			break
		}
		case 35:{//End
		    	var index=getNumberOfDays(_calendarControl.month,_calendarControl.year) +_calendarControl.startday-1
			setCalendarActiveCell(calendarData.cells[index])
			break
		}
		case 36:{//Home
			setCalendarActiveCell(calendarData.cells[_calendarControl.startday])
			break
		}
		case 37:{//<--
			var index=_calendarControl.activeCellIndex-1;
			if (index<0) index=0;
			setCalendarActiveCell(calendarData.cells[index])
			break
		}
		case 38:{//上箭头
			if (_calendarControl.activeCellIndex<7){
				var day=getNumberOfDays(_calendarControl.preMonth,_calendarControl.preYear)+_calendarControl.day-7;
				setCalendarDate(new Date(_calendarControl.preYear, _calendarControl.preMonth, day));
			}
			else{
				var index=_calendarControl.activeCellIndex-7;
				setCalendarActiveCell(calendarData.cells[index]);
			}
			break
		}
		case 39:{//-->
			var index=_calendarControl.activeCellIndex+1;
			if (index>=calendarData.cells.length) index=calendarData.cells.length-1;
			setCalendarActiveCell(calendarData.cells[index])
			break
		}
		case 40:{//下箭头
			if (_calendarControl.activeCellIndex>34){
				var day=7-(getNumberOfDays(_calendarControl.month,_calendarControl.year)-_calendarControl.day);
				setCalendarDate(new Date(_calendarControl.nextYear, _calendarControl.nextMonth, day));
			}
			else{
				var index=_calendarControl.activeCellIndex+7;
				setCalendarActiveCell(calendarData.cells[index]);
			}
			break
		}
	}
}

function _calendar_today_onclick(){
	changeCalendarDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay)
	var index=_calendarControl.todayDay+_calendarControl.startday-1;
	setCalendarActiveCell(calendarData.cells[index]);
	dropDownSelected();
}

function getNumberOfDays(month,year){
	var numDays=new Array(31,28,31,30,31,30,31,31,30,31,30,31)
	n=numDays[month]
	if (month==1 && (year%4==0 && year%100!=0 || year%400==0)) n++
	return n
}

//-----------------------
// xForm v2.0
// Developer: bao yilei
// Nov, 2001
//-----------------------

var needUpdateEditor=true;

function setEditorReadonly(editor, _readOnly){
	with (editor){
		if (_readOnly){
			editor.readOnly=true;
			style.color="dimgray";
			style.backgroundColor="whitesmoke";
		}
		else{
			editor.readOnly=false;
			style.color="black";
			style.backgroundColor="";
		}
	}
}

function checkFieldEditable(editor, dataset){
	var editable=true;
	if (dataset){
		var field=getElementField(editor);
		if (field){
			editable=!(isTrue(dataset.readOnly) || isTrue(field.readOnly));
		}
		else
			editable=true;
	}

	return editable;
}

function processDropDownSelected(editor, record, fireBySystem){
	var eventName=getElementEventName(editor, "onDropDownSelect");
	needAbort=(isUserEventDefined(eventName) && !fireUserEvent(eventName, [editor, record, fireBySystem]));

	if (needAbort) return;

	if (record){
		switch (editor.getAttribute("dropDown_mode")){
			case "staticlist":{
				setElementValue(editor, record.getFieldValue("value"));
				break;
			}
			case "date":{
				setElementValue(editor,
					formatDateTime(new Date(record.getFieldValue("value")), editor.getAttribute("dataType")));
				break;
			}
			default:{
				var dataset=getElementDataset(editor);
				var dataField=editor.getAttribute("dataField");
				var keyField=editor.getAttribute("keyField");
				var dropDown_dataField=editor.getAttribute("dropDown_dataField");
				var dropDown_keyField=editor.getAttribute("dropDown_keyField");
				if (!dropDown_dataField) dropDown_dataField=dataField;
				if (!dropDown_keyField) dropDown_keyField=keyField;

				if (dataset){
					if (dropDown_keyField){
						if (fireBySystem)
							editor.keyValue=editor.value;
						else
							editor.keyValue=record.getFieldValue(dropDown_keyField);

						if (dataset.getField(keyField)){
							dataset.setFieldValue(keyField, editor.keyValue);
						}
						else{
							editor.value=editor.keyValue;
						}
					}
					dataset.setFieldValue(dataField, record.getFieldValue(dropDown_dataField));
				}
				else{
					editor.keyValue=editor.value;
					editor.value=record.getFieldValue(dataField);
				}
			}
		}
	}
	else{
		switch (editor.getAttribute("dropDown_mode")){
			case "data":;
			case "custom":{
				setElementValue(editor, "");
				var dataset=getElementDataset(editor);
				dataset.setFieldValue(editor.getAttribute("dataField"), "");
				dataset.setFieldValue(editor.getAttribute("keyField"), "");
			}
		}
	}

	editor.dropDown_selectedValue=editor.value;
}

function validEditorInput(editor){
	if (!editor.value || (compareText(editor.getAttribute("dropDown_mode"), "staticlist") && isTrue(editor.getAttribute("dropDown_mapValue")))) return;

	switch (editor.getAttribute("dataType")){
		case "int":{
			if (isNaN(parseInt(editor.value)))
				throw constErrTypeInt.replace("%s", editor.value);
			break;
		}
		case "float":{
			if (isNaN(parseFloat(editor.value)))
				throw constErrTypeNumber.replace("%s", editor.value);
			break;
		}
		case "date":{
			var _date=new Date(editor.value);
			if (isNaN(_date))
				throw constErrTypeDate.replace("%s", editor.value);
			else{
				editor.value=formatDateTime(_date, "date");
			}
			break;
		}
		case "datetime":{
			var _date=new Date(editor.value);
			if (isNaN(_date))
				throw constErrTypeDateTime.replace("%s", editor.value);
			else{
				editor.value=formatDateTime(_date, "datetime");
			}
			break;
		}
		case "time":{
			var _date=new Date("1900-01-01 "+editor.value);
			if (isNaN(_date))
				throw constErrTypeTime.replace("%s", editor.value);
			else{
				editor.value=formatDateTime(_date, "time");
			}
			break;
		}
	}
}

function updateEditorInput(editor){
	try{
		if (window.closed) return;
		if (editor.modified){
			validEditorInput(editor);

			var dataset=getElementDataset(editor);
			var editorValue=getElementValue(editor);
			var dataField=editor.getAttribute("dataField");
			var keyField=editor.getAttribute("keyField");

			var eventName=getElementEventName(editor, "onUpdate");
			var event_result=fireUserEvent(eventName, [editor]);
			if (event_result) throw event_result;

			if (editor.dropDown_selectedValue!=editor.value
				&& !isTrue(editor.getAttribute("dropDown_fixed"))){
				if (editor.value!=""){
					var notInList=false;
					switch (editor.getAttribute("dropDown_mode")){
						case "staticlist":{
							var items=getDropDownItems(editor);
							if (items){
								notInList=(items.find(["value"], [editorValue])==null);
							}
							break;
						}
						case "dataset":{
							var tmp_dataset=editor.getAttribute("dropDown_dataset");
							if (tmp_dataset){
								if (typeof(tmp_dataset)=="string") tmp_dataset=getDatasetByID(tmp_dataset);
								if (dataset){
									var keyField=editor.getAttribute("keyField");
									if (!keyField) keyField=editor.getAttribute("dataField");
									if (keyField){
										var record=tmp_dataset.find([keyField], [editor.value]);
										notInList=(record==null);
										if (!notInList)	processDropDownSelected(editor, record, true);
									}
								}
							}
							break;
						}
						case "data":;
						case "custom":{
							notInList=true;
							var retrieveSql=editor.getAttribute("retrieveSql");
							if (retrieveSql){
								retrieveSql=retrieveSql.replace("[value]", editor.value);
								var connection=editor.getAttribute("dropDown_connection");
								if (!connection && dataset) connection=dataset.connection;

								if (retrieveSql && connection){
									var tmp_dataset=execSQL(retrieveSql, connection);
									if (tmp_dataset){
										if (tmp_dataset.record){
											processDropDownSelected(editor, tmp_dataset.record, true);
											notInList=false;
											return;
										}
									}
								}
							}
							break;
						}
					}

					if (notInList && isTrue(editor.getAttribute("dropDown_restrict"))){
						throw constErrOutOfDropDownList;
					}
				}
				else{
					switch (editor.getAttribute("dropDown_mode")){
						case "data":;
						case "custom":{
							processDropDownSelected(editor, null, true);
							break;
						}
					}
				}
			}

			editor.dropDown_selectedValue=editor.value;

			if (dataset && dataset.record){
				if (dataset.window==window){
					if (keyField){
						_record_setFieldValue(dataset.record, keyField, trimStr(editorValue));
					}
					else{
						_record_setFieldValue(dataset.record, dataField, trimStr(editorValue));
					}
				}
				else{
					if (keyField){
						dataset.window._record_setFieldValue(dataset.record, keyField, trimStr(editorValue));
					}
					else{
						dataset.window._record_setFieldValue(dataset.record, dataField, trimStr(editorValue));
					}
				}
			}
		}
	}
	catch(e){
		processException(e);
		refreshElementValue(editor);
		editor.focus();
		throw "abort";
	}
}

function processEditorValueChanged(editor){
	var dataset=getElementDataset(editor);
	if (dataset){
		var value=editor.value;
		if (!dataset.record && editor.value!=""){
			dataset.insertRecord("end");
			if (dataset.state=="insert") editor.value=value;
		}
	}

	editor.modified=(getElementValue(editor)!=editor.old_value);

	if (editor.dropDown_visible && _dropdown_window)
		_dropdown_window.dropDownLocate();
}

function _editor_onpropertychange() {
	if (event.propertyName=="value"){
		var editor=event.srcElement;
		if (_activeEditor==editor) processEditorValueChanged(editor);
	}
}


function _checkbox_onclick() {
	processEditorValueChanged(event.srcElement);
}

function _editor_onkeypress() {
	if (event.srcElement.readOnly || !event.srcElement.contentEditable){
		event.returnValue=false;
		return;
	}

	var result=true;
	switch (event.srcElement.getAttribute("dataType")){
		case "number":{
			result=(event.keyCode>=48 && event.keyCode<=57);
			break;
		}
		case "int":{
			result=(event.keyCode == 45 || (event.keyCode>=48 && event.keyCode<=57));
			break;
		}
		case "float":{
			result=(event.keyCode == 45 || event.keyCode == 46 || (event.keyCode>=48 && event.keyCode<=57));
			break;
		}
		case "date":{
			result=(event.keyCode == 47 || (event.keyCode>=48 && event.keyCode<=57));
			break;
		}
		case "datetime":{
			result=(event.keyCode == 47 || event.keyCode == 58 || event.keyCode == 32 || (event.keyCode>=48 && event.keyCode<=57));
			break;
		}
		case "time":{
			result=(event.keyCode == 58 || (event.keyCode>=48 && event.keyCode<=57));
			break;
		}
	}
	event.returnValue=result;
}

function sizeDockEditor(editor) {
	var _editor=(editor)?editor:_activeEditor;
	if (!_editor) return;

	var holder=_editor.editorHolder;
	if (!holder) return;

	var pos=getAbsPosition(holder);

	with (_editor){
		if (!compareText(type, "checkbox")){
			style.posLeft=pos[0]-1;
			style.posTop=pos[1]-1;
			style.width=holder.offsetWidth+1;
			style.height=holder.offsetHeight+1;
		}
		else{
			style.posLeft=pos[0];
			style.posTop=pos[1];
			style.width=holder.clientWidth;
			style.height=holder.clientHeight;

			if (offsetWidth>18){
				style.borderLeftWidth=(offsetWidth-18)/2;
				style.borderRightWidth=(offsetWidth-18)/2;
			}
		}
	}
}

function showDockEditor(holder){
	try{
		if (isTrue(holder.getAttribute("readOnly"))) throw "abort";
		if (!checkFieldEditable(holder, getElementDataset(holder))) throw "abort";

		var eventName=getElementEventName(holder, "beforeLoadEditor");
		var event_result=fireUserEvent(eventName, [holder]);
		if (event_result) throw event_result;

		editor=getDockEditor(holder);
		if (editor.editorHolder==holder) return

		with (editor){
			if (style.visiblity!="visible"){
				editor.editorHolder=holder;
				editor.dataType=holder.getAttribute("dataType");
				editor.editorType=holder.getAttribute("editorType");
				editor.dataField=holder.getAttribute("dataField");
				editor.keyField=holder.getAttribute("keyField");
				editor.autoDropDown=holder.getAttribute("autoDropDown");
				editor.dropDown_mode=holder.getAttribute("dropDown_mode");
				editor.dropDown_items=holder.getAttribute("dropDown_items");
				editor._dropDown_items=holder._dropDown_items;
				editor.dropDown_dataset=holder.getAttribute("dropDown_dataset");
				editor.dropDown_dataField=holder.getAttribute("dropDown_dataField");
				editor.dropDown_keyField=holder.getAttribute("dropDown_keyField");
				editor.dropDown_fields=holder.getAttribute("dropDown_fields");
				editor.dropDown_fixed=holder.getAttribute("dropDown_fixed");
				editor.dropDown_cached=holder.getAttribute("dropDown_cached");
				editor.dropDown_restrict=holder.getAttribute("dropDown_restrict");
				editor.dropDown_mapValue=holder.getAttribute("dropDown_mapValue");
				editor.dropDown_url=holder.getAttribute("dropDown_url");
				editor.dropDown_sql=holder.getAttribute("dropDown_sql");
				editor.dropDown_connection=holder.getAttribute("dropDown_connection");
				editor.dropDown_tableName=holder.getAttribute("dropDown_tableName");
				editor.dropdown_pageSize=holder.getAttribute("dropdown_pageSize");
				editor.dropdown_height=holder.getAttribute("dropdown_height");
				editor.retrieveSql=holder.getAttribute("retrieveSql");

				var _dataset=getElementDataset(holder);
				if (compareText(holder.tagName, "td")){
					var table=getTableByCell(holder);
					if (table) table.editor=editor;
					editor.in_table=true;
				}
				else
					editor.in_table=false;

				setElementDataset(editor, _dataset);

				sizeDockEditor(editor);
				style.visibility="visible";
			}

			editor.focus();
		}
	}
	catch(e){
		processException(e)
	}
}

function hideDockEditor(editor){
	if (editor.style.visibility=="visible"){
		_skip_activeChanged=true;
		editor.style.visibility="hidden";
		setElementDataset(editor, null);

		var holder=editor.editorHolder;
		if (holder){
			if (compareText(holder.tagName, "td")){
				var table=getTableByCell(holder);
				if (table) table.editor=null;
			}
			editor.editorHolder=null;
		}
	}
}

function getDockEditor(holder){
	var result=null;
	var editorType=holder.getAttribute("editorType");

	switch (editorType){
		case "textarea":{
			if  (typeof(_table_textarea)=="undefined"){
				result=document.createElement("<TEXTAREA id=_table_textarea attrib=dockeditor tabindex=-1"+
					" style=\"position: absolute; visibility: hidden\"></TEXTAREA>");
				initElement(result);
				document.body.appendChild(result);
			}
			else{
				result=_table_textarea;
			}
			break;
		}
		case "checkbox":{
			if  (typeof(_table_checkbox)=="undefined"){
				result=document.createElement("<INPUT id=_table_checkbox type=checkbox hidefocus=false attrib=dockeditor tabindex=-1"+
					" style=\"position: absolute; visibility: hidden; background-color: window;\">");
				initElement(result);
				document.body.appendChild(result);
			}
			else{
				result=_table_checkbox;
			}
			break;
		}
		default:{
			if  (typeof(_table_texteditor)=="undefined"){
				result=document.createElement("<INPUT id=_table_texteditor attrib=dockeditor tabindex=-1"+
					" style=\"position: absolute; visibility: hidden\">");
				initElement(result);
				document.body.appendChild(result);

			}
			else{
				result=_table_texteditor;
			}
			break;
		}
	}

	return result;
}

function setFocusTableCell(table, rowIndex, cellIndex){
	_rowIndex=rowIndex;
	_cellIndex=cellIndex;
	if (_rowIndex==-1) _rowIndex=table.activeRowIndex;
	if (_cellIndex==-1) _cellIndex=table.activeCellIndex;
	var index=checkTableCellIndex(table, _rowIndex, _cellIndex);
	table.rows[index[0]].cells[index[1]].focus();
}

function isEmptyRow(row){
	function getTableRowState(row){
		var record=row.record;
		if (record)
			return record.recordstate
		else
			return "";
	}

	return (getTableRowState(row)=="new" && !getTableRowModified(row));
}

//-----------------------
// xForm v2.0
// Developer: bao yilei
// Nov, 2001
//-----------------------

var _menu_frame=null;
var _array_menu=new Array();

function prepareMenu(menu, menuItems){

	function getMenu(menu){
		_menu_frame=document.createElement("<table width=100 border=1 bordercolor=silver cellspacing=0 cellpadding=3 rules=all"+
			" style=\"position:absolute; visibility:hidden; border:1 solid gray; border-collapse:collapse; font-size:9pt; cursor:hand; filter: blendTrans(duration=0.5); z-index: 10000\">"+
			"</table>");
		document.body.appendChild(_menu_frame);
		_menu_frame.onmouseover=_menu_onmouseenter;
		_menu_frame.onmouseout=_menu_onmouseout;
		_menu_frame.onmouseup=_menu_onmouseup;

		var row=_menu_frame.insertRow();
		row.attrib="menuitem";
		var cell=row.insertCell();
		cell.noWrap=true;
		cell.style.paddingLeft="12px";
		cell.style.paddingRight="12px";
		_menu_frame.repeatrow=row.cloneNode(true);
	}

	if (!menuItems) return;

	getMenu(menu);
	var item=menuItems.firstUnit;
	var row, cell;
	var count=0;
	while (item){
		if (count>(_menu_frame.rows.length-1)){
			row=_menu_frame.repeatrow.cloneNode(true);
			_menu_frame.tBodies[0].insertAdjacentElement("beforeEnd", row);
		}
		else{
			row=_menu_frame.rows[count];
		}

		if (row.rowIndex % 2)
			row.bgColor="#f0f9ff";
		else
			row.bgColor="#e0f0ff";

		fireUserEvent(getElementEventName(menu, "onUpdateMenuItem"), [menu, item]);

		cell=row.cells[0];
		cell.innerHTML=item.label;
		row.item=item;
		if (getIEVersion()<"5.5"){
			cell.style.color=(item.disabled)?"gray":"";
		}
		else{
			cell.disabled=item.disabled;
		}

		count++;
		item=item.nextUnit;
	}

	_menu_frame.menu=menu;
	_menu_frame.menuItems=menuItems;
}

function isPopupMenuVisible(){
	return (_menu_frame && _menu_frame.style.visibility=="visible");
}

function showPopupMenu(menu, button){
	var useFilter=true;
	if (_menu_frame){
		clearTimeout(_menu_frame.timeout_id);
		if (_menu_frame.style.visibility=="visible"){
			_hidePopupMenu();
			useFilter=false;
		}
	}
	menuItems=menu.menuItems;
	menu.popup=(!button);
	prepareMenu(menu, menuItems);

	if (button){
		var pos=getAbsPosition(button, document.body);

		if (pos[0]+_menu_frame.offsetWidth>document.body.clientWidth-2)
			_menu_frame.style.posLeft=pos[0]+button.offsetWidth-_menu_frame.offsetWidth-2;
		else
			_menu_frame.style.posLeft=pos[0];

		if (pos[1]+button.offsetHeight+_menu_frame.offsetHeight>document.body.clientHeight-2)
			_menu_frame.style.posTop=pos[1]-_menu_frame.offsetHeight-2;
		else
			_menu_frame.style.posTop=pos[1]+button.offsetHeight+1;

		_menu_frame.button=button;
		button.menu_opened=true;
	}
	else{
		var tmp_left, tmp_top;
		if (event.x+_menu_frame.offsetWidth>document.body.clientWidth-2)
			tmp_left=event.x-_menu_frame.offsetWidth+-5;
		else
			tmp_left=event.x-5;

		if (event.y+_menu_frame.offsetHeight>document.body.clientHeight-2)
			tmp_top=event.y-_menu_frame.offsetHeight+-5;
		else
			tmp_top=event.y-5;

		_menu_frame.style.posLeft=tmp_left+document.body.scrollLeft;
		_menu_frame.style.posTop=tmp_top+document.body.scrollTop;
	}

	if (_menu_frame.filters.blendTrans.status!=2){
		if (getIEVersion()<"5.5" || !useFilter){
			_menu_frame.style.visibility="visible";
		}
		else{
			_menu_frame.filters.blendTrans.apply();
			_menu_frame.style.visibility="visible";
			_menu_frame.filters.blendTrans.play();
		}
	}
}

function _hidePopupMenu(){
	if (!isPopupMenuVisible()) return;

	var button=_menu_frame.button;
	if (button){
		button.menu_opened=false;
		_menu_frame.button=null;
	}

	_menu_frame.removeNode(true);
	_menu_frame.style.visibility="hidden";
	_menu_frame=null;
}

function hidePopupMenu(){
	_menu_frame.timeout_id=setTimeout("_hidePopupMenu()" ,300);
}

function _menu_onmouseenter() {
	var menu=_menu_frame.menu;
	var element=event.srcElement.parentElement;
	var item=element.item;

	if (element.getAttribute("attrib")=="menuitem" && item && !item.disabled){
			element.bgColor="#ffebcd";
	}

	clearTimeout(_menu_frame.timeout_id);
}

function _menu_onmouseout() {
	var menu=_menu_frame.menu;
	var element=event.srcElement.parentElement;

	if (element.getAttribute("attrib")=="menuitem"){
		if (element.rowIndex % 2)
			element.bgColor="#f0f9ff";
		else
			element.bgColor="#e0f0ff";
	}

	if (!isChild(event.toElement, _menu_frame)) hidePopupMenu();
}

function _menu_onmouseup() {
	if (_menu_frame.filters.blendTrans.status==2) return;

	if (event.button!=2){
		var menu=_menu_frame.menu;
		var element=event.srcElement.parentElement;
		var item=element.item;

		if (item && (!item.disabled) && element){
			_hidePopupMenu();
			if (element.getAttribute("attrib")=="menuitem"){
				fireUserEvent(getElementEventName(menu, "onItemClick"), [menu, item]);
			}
			return;
		}
	}
	_hidePopupMenu();
}



//-----------------------
// xForm v2.0
// Developer: bao yilei
// Nov, 2001
//-----------------------

function getRecordByCell(cell){
	return getRowByCell(cell).record;
}

function getTableRowByRecord(table, record){
	var result=null;
	for(var i=0; i<table.tBodies[0].rows.length; i++){
		var row=table.tBodies[0].rows[i];
		if (row.record==record){
			result=row;
			break;
		}
	}
	return result;
}

function refreshTableRowData(row){
	for(var i=0; i<row.cells.length; i++){
		refreshElementValue(row.cells[i]);
	}
}

function getTableRowStyle(row){
	var table=getTableByRow(row);
	if (row.rowIndex % 2)
		return "tr1";
	else
		return "tr2";
}

function refreshTableRowStyle(row){
	var table=getTableByRow(row);
	if (row==table.activeRow){
		if (table.focused)
			row.className="current_tr";
		else if (!isTrue(table.getAttribute("hideSelection")))
			row.className="active_tr";
		else
			row.className=getTableRowStyle(row);
	}
	else{
		row.className=getTableRowStyle(row);
	}
}

function refreshTableRowIndicate(row){
	var table=getTableByRow(row);
	if (!isTrue(table.getAttribute("showIndicate"))) return;

	var cell=row.firstChild;
	if (table.activeRow==row){
		var record=row.record;
		if (record && (record.dataset.state=="insert" || record.dataset.state=="modify"))
			cell.innerHTML="<label style=\"font-size: 10pt; color: red; margin-left: 1px; margin-right: 1px\">*</label>";
		else
			cell.innerHTML="<label style=\"font-family: Webdings; color: #555555; font-size: 7pt\">4</label>";
		cell.bgColor="";
	}
	else{
		cell.innerHTML="";
		cell.bgColor="#e0e0e0";
	}
}

function resetDataTableStyle(table, startIndex){
	var row;
	var maxIndex=checkTableCellIndex(table, 9999, 9999);
	for(var i=startIndex; i<=maxIndex[0]; i++){
		row=table.rows[i];
		refreshTableRowStyle(row);
	}
}

function initDataTable(table, resetColumns){

	function setElementAttribute(element, attr, value){
		if (getValidStr(element.getAttribute(attr))=="") element.setAttribute(attr, value);
	}

	table.activeRow=null;
	table.activeRowIndex=null;
	table._activeCellIndex=null;
	table.activeCellIndex=null;
	table._activeCell=null;
	table._activeCellIndex=null;
	if (isTrue(table.isDropDownTable)) table.onclick=_dropdown_onclick;
	
	var dataset=getElementDataset(table);

	if (resetColumns && dataset){
		var arrayField;
		var fields=table.getAttribute("fields");
		if (fields){
			arrayField=fields.split(",");
		}
		else{
			arrayField=new Array();
			for(var i=0; i<dataset.fields.fieldCount; i++){
				if (dataset.fields[i].visible) arrayField[arrayField.length]=dataset.fields[i].name.toLowerCase();
			}
		}

		for (var i=table.children.length-1; i>=0; i--) table.children[i].removeNode(true);
		var row, cell;
		if (isTrue(table.getAttribute("hasHeader"))){
			row=table.createTHead().insertRow();
			for(var i=0; i<arrayField.length; i++){
				cell=row.insertCell();
				cell.dataField=arrayField[i];
			}
		}

		row=table.insertRow();
		for(var i=0; i<arrayField.length; i++){
			cell=row.insertCell();
			cell.dataField=arrayField[i];
		}

		if (isTrue(table.getAttribute("hasFooter"))){
			row=table.createTFoot().insertRow();
			for(var i=0; i<arrayField.length; i++){
				cell=row.insertCell();
				cell.dataField=arrayField[i];
			}
		}
		delete arrayField;
	}
	
	var tHeadRow, tBodyRow, tFootRow;
	if (table.tHead && table.tHead.firstChild) tHeadRow=table.tHead.firstChild;	
	if (table.tFoot && table.tFoot.firstChild) tFootRow=table.tFoot.firstChild;
	tBodyRow=table.tBodies[0].firstChild;

	if (tBodyRow.cells.length==0){
		cell=tBodyRow.insertCell();
		if (tHeadRow) cell=tHeadRow.insertCell();
		if (tFootRow) cell=tFootRow.insertCell();
	}

	if (isTrue(table.getAttribute("showIndicate"))){
		table.minCellIndex=1;
		if (!tBodyRow.firstChild || (tBodyRow.firstChild && !tBodyRow.firstChild.isIndicate)){
			cell=tBodyRow.insertCell(0);
			cell.width="9px";
			cell.align="center";
			cell.bgColor="#e0e0e0";
			cell.isIndicate=true;

			if (tHeadRow){
				cell=tHeadRow.insertCell(0);
				cell.align="center";
				cell.bgColor="#e0e0e0";
			}

			if (tFootRow)
			{
				cell=tFootRow.insertCell(0);
				cell.align="center";
				cell.bgColor="#e0e0e0";
			}
		}
	}
	else{
		table.minCellIndex=0;
	}

	for(var i=table.minCellIndex; i<tBodyRow.children.length; i++){
		var cell=tBodyRow.children[i];
		var dataField=cell.getAttribute("dataField");
		if (dataField) dataField=dataField.toLowerCase();

		var field=null;
		if (dataset) field=dataset.getField(dataField);

		cell.id=table.id+"_"+dataField;
		cell.attrib="tablecell";
		if (isTrue(table.getAttribute("isDropDownTable"))) cell.noWrap=true;
		if (getValidStr(cell.getAttribute("dropdown_cached"))=="")
			cell.dropdown_cached=true;

		if (dataField=="select"){
			cell.width="16";
			cell.align="center";
			cell.vAlign="center";
			cell.innerHTML="<input type=checkbox onclick=\"return _table_checkbox_onclick();\" style=\"height:16\">";
			cell.readOnly=true;
		}
		else{
			if (field){
				setElementAttribute(cell, "readOnly", field.readOnly);
				setElementAttribute(cell, "dataType", field.dataType);
				setElementAttribute(cell, "align", field.align);
				setElementAttribute(cell, "vAlign", field.vAlign);
			}
			else{
				setElementAttribute(cell, "readOnly", true);
			}
		}

		if (getValidStr(cell.getAttribute("editorType"))==""){
			switch (cell.getAttribute("dataType")){
				case "date":{
					cell.editorType="date";
					break;
				}
				case "longtext":{
					cell.editorType="textarea";
					break;
				}

				case "bool":{
					cell.editorType="checkbox";
					break;
				}
			}
		}

		fireUserEvent(getElementEventName(table, "onInitCell"), [table, cell, field]);
		initElement(cell);

		if (tHeadRow){
			var cell=tHeadRow.children[i];
			cell.id=table.id+"_header_"+dataField;
			cell.attrib="columnheader";

			if (dataField=="select"){
				if (!cell.getAttribute("label")) cell.label="<font face=Marlett size=2>a</font>";
			}
			else if (field){
				cell.dataset=dataset;
			}
			initElement(cell);

		}

		if (tFootRow){
			var cell=tFootRow.children[i];
			cell.id=table.id+"_footer_"+dataField;
			cell.attrib="columnfooter";

			if (dataField=="select"){
				if (!cell.getAttribute("label")) cell.label="<font face=Marlett size=2>a</font>";
			}
			else if (field){
				cell.dataset=dataset;
			}
			initElement(cell);
		}
	}
	tBodyRow.attrib="tablerow";
	table.repeatRow=tBodyRow.cloneNode(true);

	table.selectedRecords=new pArray();
	if (getInt(table.getAttribute("pageSize"))==0) table.pageSize=99999;
}

function resetDataTable(table){
	initDataTable(table, true);
	refreshTableData(table);
}

function refreshTableData(table, startRecord){
	var dataset=getElementDataset(table);
	if (!dataset) return;

	var count=0, pageSize=table.getAttribute("pageSize");

	var _record=(startRecord)?startRecord:dataset.getFirstRecord();
	var count=0;
	while (_record && count<pageSize){
		if (count>(table.tBodies[0].rows.length-1)) insertTableRow(table, "end");
		row=table.tBodies[0].rows[count];
		refreshTableRowStyle(row);
		row.attrib="tablerow";
		row.record=_record;

		for (var j=0; j<row.cells.length; j++){
			cell=row.cells[j];
			refreshElementValue(cell);
		}
		count++;
		_record=_record.getNextRecord();
	}

	for(var i=table.tBodies[0].rows.length-1; i>=count; i--){
		var tmpRow=table.tBodies[0].rows[i];
		if (table.tBodies[0].rows.length!=1)
			deleteTableRow(tmpRow);
		else{
			tmpRow.record=null;
			for (var j=0; j<tmpRow.cells.length; j++){
				var cell=tmpRow.cells[j];
				if (cell.getAttribute("attrib")=="tablecell") refreshElementValue(cell);
			}
		}

	}

	var row=getTableRowByRecord(table, dataset.record);
	if (row) setActiveTableRow(row);
}

function getTableFirstRecord(table){
	if (table.tBodies[0].rows.length>0)
		return table.tBodies[0].rows[0].record;
	else
		return null;
}

function getTableLastRecord(table){
	var rowLength=table.tBodies[0].rows.length;
	if (rowLength>0)
		return table.tBodies[0].rows[rowLength-1].record;
	else
		return null;
}

function checkTableCellIndex(table, rowIndex, cellIndex){
	var r_rowIndex=rowIndex;
	var r_cellIndex=cellIndex;
	var minRowIndex=(table.tHead)?table.tHead.rows.length:0;
	minRowIndex=(minRowIndex<0)?0:minRowIndex;
	var maxRowIndex=(table.tBodies[0])?(minRowIndex+table.tBodies[0].rows.length-1):-1;
	var minCellIndex=table.minCellIndex;
	var maxCellIndex=table.tBodies[0].rows[0].cells.length-1;

	if ((!r_cellIndex)||(r_cellIndex<minCellIndex)) r_cellIndex=minCellIndex
	else if (r_cellIndex>maxCellIndex) r_cellIndex=maxCellIndex;
	if ((!r_rowIndex)||(r_rowIndex<minRowIndex)) r_rowIndex=minRowIndex
	else if (r_rowIndex>maxRowIndex) r_rowIndex=maxRowIndex;

	return ([r_rowIndex, r_cellIndex]);
}

function setActiveTableRow(row){
	var table=getTableByRow(row);
	var oldrow=table.activeRow;

	table.activeRow=row;
	table.activeRowIndex=row.rowIndex;

	if (oldrow){
		refreshTableRowStyle(oldrow);
		refreshTableRowIndicate(oldrow);
	}
	refreshTableRowStyle(row);
	refreshTableRowIndicate(row);

	var cellIndex=table._activeCellIndex;
	if (!cellIndex) cellIndex=table.activeCellIndex;

	setActiveTableCell(row, cellIndex);
	table._activeCell=null;
	table._activeCellIndex=null;
}

function setActiveTableCell(row, cellIndex){
	var table=getTableByRow(row);
	var index=checkTableCellIndex(table, row.rowIndex, cellIndex);
	cell=row.cells[index[1]];
	var oldcell=table.activeCell;

	if (oldcell!=cell && table.focused){
		if (_activeEditor && _activeEditor.getAttribute("attrib")=="dockeditor"){
			hideDockEditor(_activeEditor);
		}
	}

	var table_holder=table.parentElement;
	if (table_holder.scrollWidth>table_holder.offsetWidth || table_holder.scrollHeight>table_holder.offsetHeight){
		var pos=getAbsPosition(cell, table_holder);

		if (pos[0]<table_holder.scrollLeft)
			table_holder.scrollLeft=pos[0];
		else if ((pos[0]+cell.offsetWidth)>(table_holder.scrollLeft+table_holder.offsetWidth))
			table_holder.scrollLeft=pos[0]+cell.offsetWidth-table_holder.offsetWidth;

		if (pos[1]<table_holder.scrollTop)
			table_holder.scrollTop=pos[1];
		else if ((pos[1]+cell.offsetHeight)>(table_holder.scrollTop+table_holder.offsetHeight))
			table_holder.scrollTop=pos[1]+cell.offsetHeight-table_holder.offsetHeight;
	}

	if (table.focused){
		if (!isTrue(table.getAttribute("readOnly")) && isTrue(table.getAttribute("editable")) && cell.getAttribute("dataField")){
			_stored_element=cell;
			setTimeout("showDockEditor(_stored_element);", 0);
		}
	}

	table.activeCell=cell;
	table.activeCellIndex=cell.cellIndex;
	return true;
}

function deleteTableRow(row) {
	var table=getTableByRow(row);
	with (table){
		if (table.activeRow==row){
			setAttribute("activeRow", null);
			setAttribute("activeCell", null);
		}
		var rowIndex=row.rowIndex;
		row.removeNode(true);
		if (!_document_loading) resetDataTableStyle(table, rowIndex);
	}
}

function insertTableRow(table, mode, row, empty) {
	if (!row) row=table.tBodies[0].rows[0];

	var newRow=table.repeatRow.cloneNode(!empty);
	switch (mode){
		case "begin":{
			table.tBodies[0].insertAdjacentElement("afterBegin", newRow);
			break;
		}
		case "before":{
			row.insertAdjacentElement("beforeBegin", newRow);
			break;
		}
		case "after":{
			row.insertAdjacentElement("afterEnd", newRow);
			break;
		}
		default:{
			table.tBodies[0].insertAdjacentElement("beforeEnd", newRow);
			break;
		}
	}

	if (!_document_loading) resetDataTableStyle(table, newRow.rowIndex);
	return newRow;
}

function refreshTableRecord(row){
	refreshTableRowData(row);
}

function deleteTableRecord(row) {
	var table=getTableByRow(row);
	var editor=table.editor;
	if (editor) hideDockEditor(editor);

	if (table.tBodies[0].rows.length>1){
		var nextRow=table.tBodies[0].rows[row.rowIndex+1];
		deleteTableRow(row);
		if (nextRow) refreshTableRowData(nextRow);
	}
	else{
		row.record=null;
		for (var i=0; i<row.cells.length; i++){
			refreshElementValue(row.cells[i]);
		}
	}
}

function insertTableRecord(table, mode, row, record) {
	if (!row) row=table.tBodies[0].rows[0];

	var newRow;
	if (!row.getAttribute("record")){
		newRow=row;
	}
	else{
		newRow=insertTableRow(table, mode, row);
	}
	newRow.record=record;
	for (var i=0; i<newRow.cells.length; i++){
		refreshElementValue(newRow.cells[i]);
	}
	return newRow;
}

function selectRecord(table, record){
	var selectedRecords=table.getAttribute("selectedRecords");
	pArray_ex_insert(selectedRecords, record);
}

function unselectRecord(table, record){
	var selectedRecords=table.getAttribute("selectedRecords");
	pArray_ex_delete(selectedRecords, record);
}

function clearSelectedRecords(table){
	var selectedRecords=table.getAttribute("selectedRecords");
	pArray_clear(selectedRecords);
}

function _table_head_onmouseover(){
	var cell=this;
	if (cell.getAttribute("canClick") || cell.getAttribute("canSort")) cell.bgColor="white";
}

function _table_head_onmouseout(){
	var cell=this;
	if (cell.getAttribute("canClick") || cell.getAttribute("canSort")) cell.bgColor="";
}

function _table_head_onclick(){
	var cell=this;
	var table=getTableByCell(cell);

	if (cell.getAttribute("canClick")){
		fireUserEvent(getElementEventName(table, "onHeaderClick"), [table, cell]);
	}

	if (cell.getAttribute("canSort")){
		var dataset=getElementDataset(table);
		if (dataset){
			var sortfield;
			if (!event.altKey){
				sortfield=cell.getAttribute("dataField");
				var ascend=true;
				if (compareText(dataset.sortFields.substr(0, sortfield.length), sortfield)){
					sortfield="-"+sortfield;
					ascend=false;
				}
				showStatusLabel(window, "<FONT face=Marlett><B>"+((ascend)?"5":"6")+"</B></FONT>");
			}
			else{
				sortfield="";
				showStatusLabel(window, constCancelSort);
			}

			_stored_element=dataset;
			setTimeout("_stored_element.sort(\""+sortfield+"\")", 100);
			setTimeout("hideStatusLabel(window)",  500);
		}
	}
}

function _table_checkbox_onclick(){
	var row=getRowByCell(event.srcElement.parentElement);
	var record=row.getAttribute("record");
	if (!record) event.returnValue=false;

	if (event.srcElement.checked)
		selectRecord(getTableByRow(row), record);
	else
		unselectRecord(getTableByRow(row), record);
}

//-----------------------
// xForm v2.0
// Developer: bao yilei  
// Nov, 2001
//-----------------------

function processException(e){
	switch (typeof(e)){
		case "string":{
			if (e!="abort"){
				if (e)
					alert(e);
				else
					alert(constErrUnknown);
			}
			break;
		}

		case "object":{
			alert(e.description+"\n"+constErrType+":"+(e.number & 0xFFFF));
			break;
		}
	}
}

function trimStr(str){
	str=getValidStr(str);
	if (!str) return "";
	for(var i=str.length-1; i>=0; i--){
		if (str.charCodeAt(i, 10)!=32) break;
	}
	return str.substr(0, i+1);
}

function getValidStr(str) {
	str+="";
	if (str=="undefined" || str=="null")
		return "";
	else
		return str;
}

function encode(strIn)
{
	var intLen=strIn.length;
	var strOut="";
	var strTemp;
	var strFlag="";

	for(var i=0;i<intLen;i++)
	{
		strTemp=strIn.charCodeAt(i);
		if (strTemp>255)
		{
			if (strFlag=="")
			{
				strFlag="#";
				strOut=strOut+"[#"+strTemp.toString(16);
			}
			else if (strFlag=="~")
			{
				strFlag="#";
				strOut=strOut+"#"+strTemp.toString(16);
			}
			else if (strFlag=="#")
			{
				strOut=strOut+strTemp.toString(16);
			}
		}
		else
		{
			if (strTemp < 48 || (strTemp > 57 && strTemp < 65) || (strTemp > 90 && strTemp < 97) || strTemp > 122)
			{
				var tmp=strTemp.toString(16);
				if (tmp.length==1) tmp = "0"+tmp;
				if (strFlag=="")
				{
					strFlag="~";
					strOut=strOut+"[~"+tmp;
				}
				else if (strFlag=="#")
				{
					strFlag="~";
					strOut=strOut+"~"+tmp;
				}
				else if (strFlag=="~")
				{
					strOut=strOut+tmp;
				}
			}
			else
			{
				if (strFlag=="#" || strFlag=="~")
				{
					strFlag="";
					strOut=strOut+"]"+strIn.charAt(i);
				}
				else
				{
					strOut=strOut+strIn.charAt(i);
				}
			}
		}
	}
	return (strOut);
}

function decode(strIn)
{
	var intLen=strIn.length;
	var strOut="";
	var strTemp;
	var strFlag="";

	for(var i=0;i<intLen;i++)
	{
		strTemp=strIn.charAt(i);
		if (strTemp=="[")
		{
			i++;
			strTemp=strIn.charAt(i);
		}
		if (strTemp=="]")
		{
			strFlag="";
			continue;
		}
		if (strTemp=="~")
		{
			i++;
			strFlag="~";
		}
		if (strTemp=="#")
		{
			i++;
			strFlag="#";
		}
		switch (strFlag)
		{
			case "~":{
				strTemp=strIn.substring(i,i+2);
				strTemp=parseInt(strTemp,16);
				if (strTemp==218)
					strTemp=String.fromCharCode(13, 10);
				else
					strTemp=String.fromCharCode(strTemp);
				strOut=strOut+strTemp;
				i++;
				break;
			}
			case "#":{
				strTemp=strIn.substring(i,i+4);
				strTemp=parseInt(strTemp,16);
				i+=3;
				strTemp=String.fromCharCode(strTemp);
				strOut=strOut+strTemp;
				break;
			}
			case "":{
				strOut=strOut+strTemp;
				break;
			}
		}

	}
	return (strOut);
}

function getEncodeStr(str) {
	return encode(getValidStr(str));
}

function getDecodeStr(str) {
	return ((str)?decode(getValidStr(str)):"");
}

function compareText(str1, str2){
	str1=getValidStr(str1);
	str2=getValidStr(str2);
	if (str1==str2) return true;
	if (str1=="" || str2=="") return false;
	return (str1.toLowerCase()==str2.toLowerCase());
}

function isTrue(value){
	return (value==true || (typeof(value)=="number" && value!=0) ||
		compareText(value, "true") || compareText(value, "T") ||
		compareText(value, "yes") || compareText(value, "on"));
}

function getStringValue(value){
	if (typeof(value)=="string" || typeof(value)=="object")
		return "\""+getValidStr(value)+"\"";
	else if (typeof(value)=="date")
		return "\""+(new Date(value))+"\"";
	else if (getValidStr(value)=="")
		return "\"\"";
	else
		return value;
}

function getInt(value){
	var result=parseInt(value);
	if (isNaN(result)) result=0;
	return result;
}

function getFloat(value){
	var result=parseFloat(value);
	if (isNaN(result)) result=0;
	return result;
}

function formatFloat(value, decimalLength){
	var text=getValidStr(Math.round(getFloat(value)*Math.pow(10, decimalLength)));
	var len=text.length;
	return text.substr(0, len-decimalLength)+"."+text.substr(len-decimalLength, decimalLength);
}

function formatDateTime(date, mode){
	function getDateString(date){
		return date.getFullYear()+"-"+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+"-"+(date.getDate()>9?date.getDate():("0"+date.getDate()));
	}
	
	if (typeof(date)=="object" && !isNaN(date)){
		switch (mode){
			case "date":{
				return getDateString(date);
				break;
			}
			default:{
				return getDateString(date);
				break;
			}
		}
	}
	else
		return "";
}

function getTypedValue(value, dataType){
	var result="";
	switch (dataType){
		case "float":{
			result=parseFloat(value);
			break;
		}
		case "int":{
			result=Math.round(parseFloat(value));
			break;
		}
		case "date":;
		case "datetime":;
		case "time":{
			result=new Date(value);
			break;
		}
		case "bool":{
			result=isTrue(value);
			break;
		}
		default:{
			result=getValidStr(value);
			break;
		}
	}
	return result;
}

function pArray(){
	this.firstUnit=null;
	this.lastUnit=null;
	this.length=0;
}

function pArray_clear(pArray){
	var unit=pArray.firstUnit;
	var _unit;
	while (unit){
		_unit=unit;
		unit=unit.nextUnit;
		if (_unit.data) delete _unit.data;
		delete _unit;
	}
	pArray.firstUnit=null;
	pArray.lastUnit=null;
	pArray.length=0;
}

function pArray_insert(pArray, mode, unit, newUnit){
	var u1, u2;
	switch (mode){
		case "begin":{
			u1=null;
			u2=pArray.firstUnit;
			break;
		}
		case "before":{
			u1=(unit)?unit.prevUnit:null;
			u2=unit;
			break;
		}
		case "after":{
			u1=unit;
			u2=(unit)?unit.nextUnit:null;
			break;
		}
		default:{
			u1=pArray.lastUnit;
			u2=null;
			break;
		}
	}

	newUnit.prevUnit=u1;
	newUnit.nextUnit=u2;
	if (u1) u1.nextUnit=newUnit; else pArray.firstUnit=newUnit;
	if (u2) u2.prevUnit=newUnit; else pArray.lastUnit=newUnit;
	pArray.length++;
}

function pArray_insertArray(pArray, mode, unit, subArray){
	if (!subArray || !subArray.firstUnit) return;

	var u1, u2;
	switch (mode){
		case "begin":{
			u1=null;
			u2=pArray.firstUnit;
			break;
		}
		case "before":{
			u1=(unit)?unit.prevUnit:null;
			u2=unit;
			break;
		}
		case "after":{
			u1=unit;
			u2=(unit)?unit.nextUnit:null;
			break;
		}
		default:{
			u1=pArray.lastUnit;
			u2=null;
			break;
		}
	}

	subArray.firstUnit.prevUnit=u1;
	subArray.lastUnit.nextUnit=u2;
	if (u1) u1.nextUnit=subArray.firstUnit; else pArray.firstUnit=subArray.firstUnit;
	if (u2) u2.prevUnit=subArray.lastUnit; else pArray.lastUnit=subArray.lastUnit;
	pArray.length+=subArray.length;
}

function pArray_delete(pArray, unit){
	var u1, u2;
	u1=unit.prevUnit;
	u2=unit.nextUnit;
	if (u1) u1.nextUnit=u2; else pArray.firstUnit=u2;
	if (u2) u2.prevUnit=u1; else pArray.lastUnit=u1;
	delete unit;
	pArray.length--;
}

function pArray_ex_insert(pArray, data){
	var found=false;
	var _unit=pArray.firstUnit;
	while (_unit){
		if (_unit.data==data){
			found=true;
			break;
		}
		_unit=_unit.nextUnit;
	}

	var newUnit=new Object();
	newUnit.data=data;
	if (!found) pArray_insert(pArray, "end", null, newUnit);
}

function pArray_ex_delete(pArray, data){
	var _unit=pArray.firstUnit;
	while (_unit){
		if (_unit.data==data){
			pArray_delete(pArray, _unit);
			break;
		}
		_unit=_unit.nextUnit;
	}
}

function setClientProperty(name, value){
	Response.Write("<INPUT TYPE=hidden id=\"_client_property_"+name+"\" value=\""+getEncodeStr(value)+"\">\n");
}