package gnnt.MEBS.common.mgr.webframe.securitycheck;

import gnnt.MEBS.common.mgr.common.ActiveUserManager;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;

/**
 * 检查用户信息
 * 
 * @author xuejt
 * 
 */
public class UrlCheckUserInfo implements IUrlCheck {

public UrlCheckResult check(String url, User user) {
		try {
			ISLogonResultVO result = ActiveUserManager.isLogon(user.getUserId(), user.getSessionId(), user.getModuleID(),user.getLogonType());
			if(result.getResult() == -1){
				//用户信息为空
				if("-1301".equals(result.getRecode().trim())){
					return UrlCheckResult.USERISNULL;
				}
				//AU超时
				else if("-1302".equals(result.getRecode().trim())){
					return UrlCheckResult.AUOVERTIME;
				}
				//AU判断用户被踢
				else if("-1303".equals(result.getRecode().trim())){
					return UrlCheckResult.AUUSERKICK;
				}else{
					return UrlCheckResult.USERISNULL;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return UrlCheckResult.AUOVERTIME;
		}
		
		return UrlCheckResult.SUCCESS;
	}
}