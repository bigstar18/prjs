 //仅输入数字和.
function onlyDoubleInput()
{
   if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
   {
	    event.returnValue=false;
   }
} 

//仅输入数字
function onlyNumberInput() {
	if (event.keyCode>=48 && event.keyCode<=57) {
		event.returnValue=true;
	} else {
		event.returnValue=false;
	}
}

// 只读
function setReadOnly(obj)
{
  obj.readOnly = true;
  obj.style.backgroundColor = "#C0C0C0";
}

// 读写
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

//控制不能输入空格
function notSpace()
{
  if(event.keyCode == 32)
  {
    event.returnValue=false;
  }
}