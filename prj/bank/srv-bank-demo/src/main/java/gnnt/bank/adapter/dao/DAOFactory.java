package gnnt.bank.adapter.dao;
/**
 * 适配器dao工厂
 * @author Administrator
 *
 */
public class DAOFactory {
	/**
	 * 适配器DAO对象
	 */
	private static AdapterDAO _adapterDAO;
	/**
	 * 获取适配器DAO对象
	 */
	public static AdapterDAO getDAO(){
		if(_adapterDAO == null){
			synchronized(DAOFactory.class){
				if(_adapterDAO == null){
					_adapterDAO = new AdapterDAOImpl();
				}
			}
		}
		return _adapterDAO;
	}
}
