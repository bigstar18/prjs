function ecsideDialog(url, args, width, height){
	if (!width) width = 600;
	if (!height) height = 400;
	if(checkie()){
		height=height+50;
	}
	if (!args) args='';
	var urlArray=url.split("?");
	if(urlArray.length==1){
		url=url+'?t='+Math.random();
	}else if(urlArray.length==2){
		url=url+'&t='+Math.random();
	}
	var result=window.showModalDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; status=yes;scroll=yes;help=no;");
	if(result>0)
	{
	   ec.submit();	
	}
}
function dialog(url, args, width, height){
	if (!width) width = 600;
	if (!height) height = 400;
	if(checkie()){
		height=height+50;
	}
	if (!args) args='';
	var au='111111';
	if(typeof(AUsessionId)!= "undefined"){
		au=AUsessionId;
	}
	var urlArray=url.split("?");
	if(urlArray.length==1){
		url=url+'?AUsessionId='+au+'&d='+new Date();
	}else if(urlArray.length==2){
		url=url+'&AUsessionId='+au+'&d='+new Date();
	}
	var result=window.showModalDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; status=yes;scroll=yes;help=no;");
	return result;
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
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(collCheck))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("��ȷ��Ҫ����ѡ��������"))
	{
		ec.action=url;
		ec.submit();
	}
}
function downOnline(collCheck,url)
{
	if(isSelNothing(collCheck) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(collCheck))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("��ȷ��Ҫǿ�ƽ���������"))
	{
		ec.action=url;
		ec.submit();
	}
}
function updateRMIEcside(collCheck,url)
{
	if(isSelNothing(collCheck) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(collCheck))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("��ȷ��Ҫ����ѡ��������"))
	{
		ec.action=url;
		ec.submit();
	}
}
function checkie(){
         if(navigator.userAgent.indexOf("MSIE")>0){ 
            if(navigator.userAgent.indexOf("MSIE 6.0")>0){ 
              return true;
           } 
          }
        }
