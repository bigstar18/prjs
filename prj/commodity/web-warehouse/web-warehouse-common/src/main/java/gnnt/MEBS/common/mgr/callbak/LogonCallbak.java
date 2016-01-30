
package gnnt.MEBS.common.mgr.callbak;

import gnnt.MEBS.common.mgr.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * <P>类说明：各个系统登录成功后加载自己信息的回调方法类
 * <br/>
 * <br/>
 * 本类为了各个系统登录成功后可以加载自己的私有数据使用
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2013-5-23下午05:33:09|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public interface LogonCallbak {
	/**
	 * 
	 * 登录成功后执行的方法<br/>
	 * <li>各个系统登录后可自定义实现类来加载自己的信息</li>
	 * <li>本类使用方法为：
	 * 	<ul>
	 * 		<li>1：建立自己的实现类，实现本接口</li>
	 * 		<li>2：在 spring.xml 配置文件中配置自己的实现类，id 号为 "logonCallbak"</li>
	 * 	</lu>
	 * </li>
	 * <br/><br/>
	 * @param user 登录用户信息
	 * @param request HttpServletRequest
	 */
	public void logonSuccessCallbak(User user,HttpServletRequest request);
}

