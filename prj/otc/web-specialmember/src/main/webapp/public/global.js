//-------------------------------图片切换--------------------------------------//
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-------------------------------图片切换--------------------------------------//


/* 改变对象的样式类 */
function swapClass(obj, newStyle) {
    obj.className = newStyle;
}

function isUndefined(value) {
    var undef;
    return value == undef;
}

/* Function for showing and hiding elements that use 'display:none' to hide */
function toggleDisplay(targetId)
{
    if (document.getElementById) {
        target = document.getElementById(targetId);
    	if (target.style.display == "none"){
    		target.style.display = "";
    	} else {
    		target.style.display = "none";
    	}
    }
}

// toggle visibility
function toggleVisibility(targetId) {
    if (document.getElementById) {
        target = document.getElementById(targetId);
    	if (target.style.visibility == "hidden"){
    		target.style.visibility = "visible";
    	} else {
    		target.style.visibility = "hidden";
    	}
    }
}

function checkAll(theForm) { // check all the checkboxes in the list
  for (var i=0;i<theForm.elements.length;i++) {
    var e = theForm.elements[i];
		var eName = e.name;
    	if (eName != 'allbox' &&
            (e.type.indexOf("checkbox") == 0)) {
        	e.checked = theForm.allbox.checked;
		}
	}
}

/* Function to clear a form of all it's values */
function clearForm(formName) {
	for(var i = 0; i < formName.length; i++)
	{
            if (formName[i].type == "text" || formName[i].type == "password")
            {
                formName[i].value = "";
            }
            else if (formName[i].type == "checkbox" || formName[i].type == "radio")
            {
                formName[i].checked = false;
            }
            else if (formName[i].type == "select-one")
            {
                for(var j = 0; j < formName[i].length ; j++)
                {
				    formName[i].options[j].selected=false;
			    }
                formName[i].options[0].selected=true;
            }
	}
/*
	for (var i = 0; i < frmObj.length; i++) {
        var element = frmObj.elements[i];
		if(element.type.indexOf("text") == 0 ||
				element.type.indexOf("password") == 0) {
					element.value="";
		} else if (element.type.indexOf("radio") == 0) {
			element.checked=false;
		} else if (element.type.indexOf("checkbox") == 0) {
			element.checked = false;
		} else if (element.type.indexOf("select") == 0) {
			for(var j = 0; j < element.length ; j++) {
				element.options[j].selected=false;
			}
            element.options[0].selected=true;
		}
	}
*/	
}

/* Function to get a form's values in a string */
function getFormAsString(frmObj) {
    var query = "";
	for (var i = 0; i < frmObj.length; i++) {
        var element = frmObj.elements[i];
        if (element.type.indexOf("checkbox") == 0 ||
            element.type.indexOf("radio") == 0) {
            if (element.checked) {
                query += element.name + '=' + escape(element.value) + "&";
            }
		} else if (element.type.indexOf("select") == 0) {
			for (var j = 0; j < element.length ; j++) {
				if (element.options[j].selected) {
                    query += element.name + '=' + escape(element.value) + "&";
                }
			}
        } else {
            query += element.name + '='
                  + escape(element.value) + "&";
        }
    }
    return query;
}

/* Function to hide form elements that show through
   the search form when it is visible */
function toggleForm(frmObj, iState) // 1 visible, 0 hidden
{
	for(var i = 0; i < frmObj.length; i++) {
		if (frmObj.elements[i].type.indexOf("select") == 0 || frmObj.elements[i].type.indexOf("checkbox") == 0) {
            frmObj.elements[i].style.visibility = iState ? "visible" : "hidden";
		}
	}
}

/* Helper function for re-ordering options in a select */
function opt(txt,val,sel) {
    this.txt=txt;
    this.val=val;
    this.sel=sel;
}

/* Function for re-ordering <option>'s in a <select> */
function move(list,to) {
    var total=list.options.length;
    index = list.selectedIndex;
    if (index == -1) return false;
    if (to == +1 && index == total-1) return false;
    if (to == -1 && index == 0) return false;
    to = index+to;
    var opts = new Array();
    for (i=0; i<total; i++) {
        opts[i]=new opt(list.options[i].text,list.options[i].value,list.options[i].selected);
    }
    tempOpt = opts[to];
    opts[to] = opts[index];
    opts[index] = tempOpt
    list.options.length=0; // clear

    for (i=0;i<opts.length;i++) {
        list.options[i] = new Option(opts[i].txt,opts[i].val);
        list.options[i].selected = opts[i].sel;
    }

    list.focus();
}

/*  This function is to select all options in a multi-valued <select> */
function selectAll(elementId) {
    var element = document.getElementById(elementId);
	len = element.length;
	if (len != 0) {
		for (i = 0; i < len; i++) {
			element.options[i].selected = true;
		}
	}
}

/* This function is used to select a checkbox by passing
 * in the checkbox id
 */
function toggleChoice(elementId) {
    var element = document.getElementById(elementId);
    if (element.checked) {
        element.checked = false;
    } else {
        element.checked = true;
    }
}

/* This function is used to select a radio button by passing
 * in the radio button id and index you want to select
 */
function toggleRadio(elementId, index) {
    var element = document.getElementsByName(elementId)[index];
    element.checked = true;
}


/* This function is used to open a pop-up window */
function openWindow(url, winTitle, winParams) {
	winName = window.open(url, winTitle, winParams);
    winName.focus();
}


/* This function is to open search results in a pop-up window */
function openSearch(url, winTitle) {
    var screenWidth = parseInt(screen.availWidth);
    var screenHeight = parseInt(screen.availHeight);

    var winParams = "width=" + screenWidth + ",height=" + screenHeight;
        winParams += ",left=0,top=0,toolbar,scrollbars,resizable,status=yes";

    openWindow(url, winTitle, winParams);
}

/* This function is used to set cookies */
function setCookie(name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}

/* This function is used to get cookies */
function getCookie(name) {
	var prefix = name + "="
	var start = document.cookie.indexOf(prefix)

	if (start==-1) {
		return null;
	}

	var end = document.cookie.indexOf(";", start+prefix.length)
	if (end==-1) {
		end=document.cookie.length;
	}

	var value=document.cookie.substring(start+prefix.length, end)
	return unescape(value);
}

/* This function is used to delete cookies */
function deleteCookie(name,path,domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}

// This function is for stripping leading and trailing spaces
function trim(str) {
    if (str != null) {
        var i;
        for (i=0; i<str.length; i++) {
            if (str.charAt(i)!=" ") {
                str=str.substring(i,str.length);
                break;
            }
        }

        for (i=str.length-1; i>=0; i--) {
            if (str.charAt(i)!=" ") {
                str=str.substring(0,i+1);
                break;
            }
        }

        if (str.charAt(0)==" ") {
            return "";
        } else {
            return str;
        }
    }
}

// This function is used by the login screen to validate user/pass
// are entered.
function validateRequired(form) {
    var bValid = true;
    var focusField = null;
    var i = 0;
    var fields = new Array();
    oRequired = new required();

    for (x in oRequired) {
        if ((form[oRequired[x][0]].type == 'text' || form[oRequired[x][0]].type == 'textarea' || form[oRequired[x][0]].type == 'select-one' || form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password') && form[oRequired[x][0]].value == '') {
           if (i == 0)
              focusField = form[oRequired[x][0]];

           fields[i++] = oRequired[x][1];

           bValid = false;
        }
    }

    if (fields.length > 0) {
       focusField.focus();
       alert(fields.join('\n'));
    }

    return bValid;
}

// This function is a generic function to create form elements
function createFormElement(element, type, name, id, value, parent) {
    var e = document.createElement(element);
    e.setAttribute("name", name);
    e.setAttribute("type", type);
    e.setAttribute("id", id);
    e.setAttribute("value", value);
    parent.appendChild(e);
}

function confirmDelete(obj) {
    var msg = "您确定要删除 " + obj + "吗？";
    ans = confirm(msg);
    if (ans) {
        return true;
    } else {
        return false;
    }
}

function highlightTableRows(tableId) {
    var previousClass = null;
    var table = document.getElementById(tableId);
    var tbody = table.getElementsByTagName("tbody")[0];
    if (tbody == null) {
        var rows = table.getElementsByTagName("tr");
    } else {
        var rows = tbody.getElementsByTagName("tr");
    }
    // add event handlers so rows light up and are clickable
    for (i=0; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className+=' over' };
        rows[i].onmouseout = function() { this.className=previousClass };
        rows[i].onclick = function() {
            var cell = this.getElementsByTagName("td")[0];
            var link = cell.getElementsByTagName("a")[0];
            location.href = link.getAttribute("href");
            this.style.cursor="wait";
        }
    }
}

function highlightFormElements() {
    // add input box highlighting
    addFocusHandlers(document.getElementsByTagName("input"));
    addFocusHandlers(document.getElementsByTagName("textarea"));
}

function blur2set(oldblur) {
    oldblur();
	if(!this.readOnly){this.className='';}
}

function addFocusHandlers(elements) {
    for (i=0; i < elements.length; i++) {
        if (elements[i].type != "button" && elements[i].type != "submit" && elements[i].type != "image" &&
            elements[i].type != "reset" && elements[i].type != "checkbox" && elements[i].type != "radio")
        {
            elements[i].onfocus=function() {if(!this.readOnly){this.className='focus';};if(!isNaN(this.value)){this.select()}};
            elements[i].onclick=function() {if(!isNaN(this.value)){this.select()}};
            elements[i].onblur=function() {if(!this.readOnly){this.className=''}};
        }
    }
}

function radio(clicked){
    var form = clicked.form;
    var checkboxes = form.elements[clicked.name];
    if (!clicked.checked || !checkboxes.length) {
        clicked.parentNode.parentNode.className="";
        return false;
    }

    for (i=0; i<checkboxes.length; i++) {
        if (checkboxes[i] != clicked) {
            checkboxes[i].checked=false;
            checkboxes[i].parentNode.parentNode.className="";
        }
    }

    // highlight the row
    clicked.parentNode.parentNode.className="over";
}

window.onload = function() {
    //highlightFormElements();
     /* Commented out b/c causes stack trace with Canoo WebTest
       http://lists.canoo.com/pipermail/webtest/2005q3/004493.html
    if ($('successMessages'))
        new Effect.Highlight('successMessages', {startcolor: '#ffff00', endcolor: '#ffffcc'});
    if ($('errorMessages'))
        new Effect.Highlight('errorMessages', {startcolor: '#ffff00', endcolor: '#ffffcc'});
    */
}

// Show the document's title on the status bar
//window.defaultStatus=document.title;

function delMode(formName)
{
	for(var i = 0; i < formName.length; i++)
	{
            if (formName[i].type == "text" || formName[i].type == "password")
            {
                formName[i].readOnly = true;
                formName[i].style.backgroundColor = "#C0C0C0";
            }
            if (formName[i].type == "checkbox" || formName[i].type == "select-one" || formName[i].type == "radio")
            {
              if (formName[i].type == "select-one")
              {
                formName[i].style.backgroundColor = "#C0C0C0";
              }
               formName[i].disabled = true;
            }
	}
}

/**
 * X—金额值
 * Y—精度值（0，1，2，3）
 * 0-保留小数到元
 * 1-保留小数到角
 * 2-保留小数到分
 * 3-见分进元
 */
function MYROUND(x, y)
{
	switch(y)
	{
		case 0:
			return Math.round(x);
		case 1:
			return Math.round(x * 10)/10;
		case 2:
			return Math.round(x * 100)/100;
		case 3:
			return Math.ceil(x);
	}
}
/**
*Descripts:将数值按精度转化成字符串
*Params:
* str==>需要转化的数值
* dec==>小数精度
*/
function formatFloat(str,dec)
{
	var intPos = str.indexOf(".");
	var arr;
	var i;
	if(intPos<0)
	{
		intLen = 0;
		str += (dec>0)?".":"";
	}
	else
	{
		arr = str.split(".");
		intLen = arr[1].length;
	}
	if(intLen <= dec)
	{
	  for(i=intLen;i<dec;i++)
	  {
		str += "0";
	  }
	}
	else
	{
      str = arr[0] + "." + arr[1].substr(0,dec);
	}
	return str;
}
//仅输入数字，-，.
function numberPass()
{
  if (event.keyCode<45 || event.keyCode>57 || event.keyCode == 47)
  {
    event.returnValue=false;
  }
}
//仅输入汉字的控制
function onlyChineseCharInput()
{
  if (event.keyCode>=20 || event.keyCode<=128)
  {
    event.returnValue=false;
  }
}
//仅输入数字和.
function onlyNumberInput()
{
  if (event.keyCode<45 || event.keyCode>57 || event.keyCode == 47)
  {
    event.returnValue=false;
  }
}
//仅输入数字和字母
function onlyNumberAndCharInput()
{
  if ((event.keyCode>=48 && event.keyCode<=57) || (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122))
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}
function moveSelected(from,to) {

  newTo = new Array();
  newFrom = new Array();

  for(i=from.options.length - 1; i >= 0; i--) {
    if (from.options[i].selected) {
      newTo[newTo.length] = cloneOption(from.options[i]);
    }

    else
    {
      newFrom[newFrom.length] = cloneOption(from.options[i]);
    }
  }
  for(i=to.options.length - 1; i >= 0; i--) {
    newTo[newTo.length] = cloneOption(to.options[i]);

    newTo[newTo.length-1].selected = false;
  }

  to.options.length = 0;
  from.options.length = 0;

  for(i=newTo.length - 1; i >=0 ; i--) {
    to.options[to.options.length] = newTo[i];
  }

  for(i=newFrom.length - 1; i >=0 ; i--) {
    from.options[from.options.length] = newFrom[i];
  }

  selectionChanged(to,from);
}

function cloneOption(option) {
  var out = new Option(option.text,option.value);
  out.selected = option.selected;
  out.defaultSelected = option.defaultSelected;
  return out;
}

function selectionChanged(selectedElement,unselectedElement) {
  for(i=0; i<unselectedElement.options.length; i++) {
    unselectedElement.options[i].selected=false;
  }
}
function moveSelectedAll(from ,to){
  newTo = new Array();
  newFrom = new Array();

  for(i=from.options.length - 1; i >= 0; i--) {
      newTo[newTo.length] = cloneOption(from.options[i]);
  }
  for(i=to.options.length - 1; i >= 0; i--) {
    newTo[newTo.length] = cloneOption(to.options[i]);

    newTo[newTo.length-1].selected = false;
  }

  to.options.length = 0;
  from.options.length = 0;

  for(i=newTo.length - 1; i >=0 ; i--) {
    to.options[to.options.length] = newTo[i];
  }
}

function contains(arr, str)
{
	if (arr.length <= 0)
	{
		return false;
	}
	for(i = 0; i < arr.length; i++)
	{
		if (arr[i] == str)
		{
			return true;
		}
	}
	return false;
}
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
function setDisabled(obj)
{
  obj.disabled = true;
  obj.style.backgroundColor = "#C0C0C0";
}
function setEnabled(obj)
{
  obj.disabled = false;
  obj.style.backgroundColor = "white";
}
function keyEnter(iKeyCode)
{
  var srcElement = window.event.srcElement;
  /*
  if(srcElement.name == "save" || iKeyCode==-1)
  {
    document.forms[0].submit();
    return true;
  }
  */
  if(iKeyCode != 13)
  {
    return false;
  }
  if ((srcElement.tagName == "INPUT" && srcElement.type != "button" &&
       srcElement.type != "submit" && srcElement.type != "reset") ||
       srcElement.tagName == "SELECT")
  {
    var i = 0;
    while (srcElement != srcElement.form.elements[i])
    {
      i++;
    } 
    if(i == srcElement.form.elements.length-1)
    {
      return false;
    }    
    while (srcElement.form.elements[i+1].tagName == "FIELDSET")
    {
      i++;
    } 
    if(i == srcElement.form.elements.length-1)
    {
      return false;
    } 
    while (srcElement.form.elements[i+1].disabled || srcElement.form.elements[i+1].readOnly || srcElement.form.elements[i+1].type == "hidden")
    {
      i++;
    } 

    if(i == srcElement.form.elements.length-1)
    {
      return false;
    }
    
    while (srcElement.form.elements[i+1].tagName == "FIELDSET")
    {
      i++;
    } 
    if(i == srcElement.form.elements.length-1)
    {
      return false;
    }  
        
    if(srcElement.form.elements[i+1] != null)
    {
      srcElement.form.elements[i+1].focus();
    }
  }
  return false;
}

//----  start cxc -----------------------------------------------
function enableDelMode(formName)
{
	for(var i = 0; i < formName.length; i++)
	{
            if (formName[i].type == "checkbox" || formName[i].type == "select-one" || formName[i].type == "radio")
            {
              if (formName[i].type == "select-one")
              {
                formName[i].style.backgroundColor = "white";
              }
               formName[i].disabled = false;
            }
	}
}
//clear options
function clearOptions(select)
{
	var len = select.options.length;
	for(i=0; i<len; i++)
	{
		select.options.remove(0);
	}
}
//add option
function addSelectOption(select,mc,bm)
{
	select.options.add(new Option(mc,bm));
}
/*
功能：在某个字符串中某个位置开始循环插入需要到达总长度还差的某个字符个数
@param: str：被插入的字符串
        startpos：插入开始位置
        len：插入后字符串的总长度
        chars：插入的字符    
*/
function strtran(str,startpos,len,chars)
{
	if (str.length == 0)
	{
	  return str;
	}
	if (startpos > len)
	{
	  return str;
	}
	if (str.length > len)
	{
	  return str.substr(0,len);
	}	
	else
	{
	  var s = "";
	  var i;
	  for (i = 0; i < len-str.length; i++)
	  {
	    s += chars;
	  }
	  return str.substr(0,startpos) + s + str.substr(startpos,str.length);
	}
}
//在字符串str中找某个字符
function returnPos(str,chars)
{
    var ret = -1;
    for(var i=0;i<str.length;i++)
    {
      if(str.substr(i,1) == chars)
      {
        ret = i;
        break;
      }
    }
    return ret;
}

//禁止右键的脚本
//from www.jx165.com
function nocontextmenu(){
event.cancelBubble = true
event.returnValue = false;
return false;}
function norightclick(e){
if (window.Event){
if (e.which == 2 || e.which == 3)
return false;}
else
if (event.button == 2 || event.button == 3){
alert("欢迎使用！");
event.cancelBubble = true
event.returnValue = false;
return false;}
}
//禁止右键
///document.oncontextmenu = nocontextmenu;  // for IE5+
///document.onmousedown = norightclick;  // for all others

/**
*Descripts:格式化控件精度
*Params:
* ctl==>需要转化的控件
* dec==>小数精度
*/
function formatControlPrecision(ctl,dec)
{
  ctl.value = formatFloat(ctl.value,dec);
}
/**
*Descripts:格式化控件2位精度
*Params:
* ctl==>需要转化的控件
*/
function formatControl2Precision(ctl)
{
  ctl.value = formatFloat(ctl.value,2);
}

/**
*Descripts:对日期输入8位的自动格式化成yyyy-MM-dd形式
*Params:
* ctl==>需要转化的控件
*/
function formatDateInput(ctl)
{
  var v = trim(ctl.value);
  if(v.length == 8)
  {
    ctl.value = v.substr(0,4) + "-" + v.substr(4,2) + "-" + v.substr(6,2);
  }  
}
//批量操作
function batch_do(entityName, action)
{
    if (confirm("您确定要" + entityName + "吗？"))
    {
        if (!atleaseOneCheck())
        {
            alert('请至少选择一' + entityName + '！');
            return;
        }
        var form = document.forms.ec;
        form.action = action + '&autoInc=false';
        form.submit();
    }
}
//checkbox中至少有一项被选中
function atleaseOneCheck()
{
    var items = document.getElementsByName('itemlist');
    if (items.length > 0) {
        for (var i = 0; i < items.length; i++)
        {
            if (items[i].checked == true)
            {
                return true;
            }
        }
    } else {
        if (items.checked == true) {
            return true;
        }
    }
    return false;
}
// ------ end cxc -------------------------------------------------
