 //���������ֺ�.
function onlyDoubleInput()
{
   if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
   {
	    event.returnValue=false;
   }
} 

//����������
function onlyNumberInput() {
	if (event.keyCode>=48 && event.keyCode<=57) {
		event.returnValue=true;
	} else {
		event.returnValue=false;
	}
}

// ֻ��
function setReadOnly(obj)
{
  obj.readOnly = true;
  obj.style.backgroundColor = "#C0C0C0";
}

// ��д
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

//���Ʋ�������ո�
function notSpace()
{
  if(event.keyCode == 32)
  {
    event.returnValue=false;
  }
}