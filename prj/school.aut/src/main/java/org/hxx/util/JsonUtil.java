package org.hxx.util;

import org.hxx.app.model.UserPkPic;

public class JsonUtil {
	public static String UserPkPicToJson(UserPkPic pk){
		String objson = "{uid:'"+pk.getUid()+"',headPic:'"+ pk.getHeadPic() +"',nick:'"+pk.getNick()+"',pics:";
		if(pk.getPicdate().size()>=2){
			for(int i=0;i<pk.getPicurl().size()-1;i++){
				objson+="[{pic:'"+pk.getPicurl().get(i)+"'},";
			}
			objson+="{pic:'"+pk.getPicurl().get(pk.getPicurl().size()-1)+"'}],dates:";
			for(int i=0;i<pk.getPicdate().size()-1;i++){
				objson+="[{date:'"+pk.getPicdate().get(i)+"'},";
			}
			objson+="{date:'"+pk.getPicdate().get(pk.getPicdate().size()-1)+"'}]}";
		}else if(pk.getPicdate().size()==1){
			objson+="[{pic:'"+pk.getPicurl().get(0)+"'}],dates:";
			objson+="[{date:'"+pk.getPicdate().get(0)+"'}]}";
		}
		return objson;
	}
}
