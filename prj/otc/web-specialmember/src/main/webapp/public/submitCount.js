

//$(function(){
//	$('form').submit(function() {
//  if(++submit>0){
//    	alert(submit);
//    	return false;    
//    }else{
//      return true;	
//    }
//});
//});
function affirm(msg){
	if(checkSubmitCount()){
		var confirmMark= window.confirm(msg);
			if(!confirmMark){
				submitCount--;
			}
			return confirmMark;
	}else{
		return false;
	}
}

 var submitCount=0;    
function checkSubmitCount(){
    if(++submitCount>1){
    	return false;    
    }else{
      return true;	
    }
        
}

function clearSubmitCount(){
	submitCount=0;
}
