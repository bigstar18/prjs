package gnnt.MEBS.common.mgr.webframe.securitycheck;

/**
 * 检查url是否有访问权限结果 枚举类
 * 
 * @author Administrator
 * 
 */
public enum UrlCheckResult {
	/**
	 * 成功 有权限
	 */
	SUCCESS(0),
	/**
	 * 不需要检查的URL
	 */
	NEEDLESSCHECK(0),
	/**
	 * 用户信息为空
	 */
	USERISNULL(-1),
	/**
	 *AU超时
	 */
	AUOVERTIME(-2),
	/**
	 *AU判断用户被踢
	 */
	AUUSERKICK(-3),
	/**
	 * 用户没有权限
	 */
	NOPURVIEW(-4),
	/**
	 * 不需要检查权限的URL
	 */
	NEEDLESSCHECKRIGHT(-5);

	private final int value;

	/**
	 * 返回枚举值对应的数字
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	private UrlCheckResult(int value) {
		this.value = value;
	}
	
	public static void main(String[] args){
		System.out.println(UrlCheckResult.NOPURVIEW);
	}
}
