package gnnt.MEBS.common.mgr.beanforajax;

import gnnt.MEBS.common.mgr.statictools.Tools;

import org.springframework.stereotype.Controller;

/**
 * 用于 AJAX 查找信息的类
 * @author liuzx
 */
@Controller("com_ajaxQuery")
public class AjaxQuery extends BaseAjax{
	/**
	 * 查询系统时间，格式化成 yyyy-MM-dd HH:mm:ss 格式，封装成  json 串返回页面
	 */
	public String getSystemTimeJson(){
		try {
			jsonValidateReturn = this.createJSONArray(Tools.fmtTime(this.getService().getSysDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
