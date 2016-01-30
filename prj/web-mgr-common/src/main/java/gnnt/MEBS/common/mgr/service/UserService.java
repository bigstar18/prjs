package gnnt.MEBS.common.mgr.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import gnnt.MEBS.common.mgr.model.User;

/**
 * 权限检查Service
 * 
 * @author xuejt
 * 
 */
@Service("com_userService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false,rollbackFor=Exception.class)
public class UserService extends StandardService {

	/**
	 * 获取用户信息 加载权限角色信息
	 * 
	 * @param userID
	 *            用户代码
	 * @return 用户信息
	 */
	public User getUserByID(String userID) {
		return this.getUserByID(userID, true, true);
	}

	/**
	 * 获取用户信息
	 * <p>
	 * 为了提高效率所以权限和角色采用延迟加载；选用延迟加载当跳出session事务后将不能获取权限和角色；
	 * 因为延迟加载的意思是使用时从数据库查询,但是当跳出service时session已经关闭，所以会抛出异常；
	 * 
	 * @param userID
	 *            用户代码
	 * @param loadRight
	 *            是否加载用户权限信息
	 * @param loadRole
	 *            是否加载用户角色信息
	 * @return 用户信息
	 */
	public User getUserByID(String userID, boolean loadRight, boolean loadRole) {
		logger.debug("enter getUserByID");
		User entity = new User();
		entity.setUserId(userID);
		User user = (User) super.get(entity);
		if(user!=null){
		// 获取权限集合size 会从数据库中查询出结果到对象
		if (loadRight)
			user.getRightSet().size();
		// 获取角色集合size 会从数据库中查询出结果到对象
		if (loadRole)
			user.getRoleSet().size();
		}
		return user;
	}
	
	
}
