function ecsideDialog(url, args, width, height){
	if (!width) width = 600;
	if (!height) height = 400;
	if (!args) args='';
	var urlArray=url.split("?");
	if(urlArray.length==1){
		url=url+'?d='+new Date();
	}else if(urlArray.length==2){
		url=url+'&d='+new Date();
	}
	var result=window.showModalDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; status=yes;scroll=yes;help=no;");
	if(result>0)
	{
	   ec.submit();	
	}
}
function isSelNothing( collCheck )
{
    if(!collCheck)
        return -1;
    if(collCheck.checked)
	{
		if(collCheck.checked == true)
		 return false
		else
		  return true
	}
	if( collCheck.length < 1 )
	{
		return -1;
	}
	var noSelect = true;
	if(collCheck.checked == true)
	{
			noSelect = false;
	}
	else
	{
		for(var i=0;i < collCheck.length;i++ )
		{
			if( collCheck[i].checked == true )
			{
				noSelect = false;			
			}
		
		}
	}
	   return noSelect;	
}
function deleteEcside(collCheck,url)
{
	if(isSelNothing(collCheck) == -1)
	{
		alert ( "没有可以操作的数据！" );
		return false;
	}
	if(isSelNothing(collCheck))
	{
		alert ( "请选择需要操作的数据！" );
		return false;
	}
	if(confirm("你确定要删除吗？"))
	{
		ec.action=url;
		ec.submit();
	}
	
	
}
