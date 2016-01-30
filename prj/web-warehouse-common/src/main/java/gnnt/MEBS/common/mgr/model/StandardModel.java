package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.translate.GetNameByField;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Serialize;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 所有model类的父类
 * 
 * 继承Cloneable接口以方便直接调用clone方法 不用每个model都继承Cloneable接口
 * 
 * 如果在没有实现 Cloneable 接口的实例上调用 Object 的 clone 方法，则会导致抛出
 * CloneNotSupportedException 异常
 * 
 * @author xuejt
 * 
 */
public abstract class StandardModel implements java.lang.Cloneable,
		Serializable {

	private transient final Log logger = LogFactory.getLog(StandardModel.class);

	private static final long serialVersionUID = -3032683871122601737L;

	/**
	 * 返回当前对象的克隆对象
	 */
	public StandardModel clone() {
		try {
			// call clone in Object.
			return (StandardModel) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Cloning not allowed.");
			e.printStackTrace();
			return this;
		}
	}

	/**
	 * 序列化对象为xml字符串
	 * 
	 * @return
	 */
	public String serialize() {
		return Serialize.serializeToXml(this);
	}

	/**
	 * 翻译成字符串
	 * 
	 * @return 翻译后结果
	 */
	@SuppressWarnings("unchecked")
	public String translate() {
		String result = "";
		// 从spring容器中获取
		GetNameByField getNameByField = (GetNameByField) ApplicationContextInit
				.getBean("getNameByField");

		// 对象对应的字段数组
		Field fields[] = this.getClass().getDeclaredFields();
		// 循环对象字段数组
		for (int i = 0; i < fields.length; i++) {
			// 如果是Final修饰符修饰则不管
			if (Modifier.isFinal(fields[i].getModifiers())) {
				continue;
			}

			// 如果是Transient修饰符修饰则不管
			if (Modifier.isTransient(fields[i].getModifiers())) {
				continue;
			}

			// 如果源字段声明类型为private则不能获取值 必须setAccessible(true)后方可获取
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}

			String name = getNameByField.getName(fields[i]);
			Object value = null;
			try {
				value = fields[i].get(this);
			} catch (Exception e) {
				logger.warn(e.getMessage());
				e.printStackTrace();
			}
			if (value == null) {
				continue;
			}
			if (value instanceof StandardModel) {
				String subResult = ((StandardModel) value).translate();
				if (subResult != null && subResult.length() > 0) {
					result += "子对象" + name + "的内容[" + subResult + "];";
				}
			} else if(value instanceof Collection){
				if(((Collection) value).size()>0){//如果数组长度不为0
					result += name + ":[";
					Object[] os = ((Collection)value).toArray();
					String strs = "";
					for(int j=0;j<os.length;j++){
						if(strs.length()>0){
							strs += ";";
						}
						Object o = os[j];
						if (o instanceof StandardModel) {
							strs += "";//((StandardModel)o).translate();
						}else{
							strs += o.toString();
						}
					}
					if(strs.length()<=0){
						strs += "子对象数据可能较长，不做记录";
					}
					result += strs+"];";
				}
			} else if (value instanceof Map){
				if(((Map)value).size()>0){
					result += name + ":[";
					String strs = "";
					for(Object key : ((Map)value).keySet()){
						if(strs.length()>0){
							strs += ";";
						}
						Object o = ((Map)value).get(key);
						if(!(o instanceof StandardModel)){
							strs += key+":"+o;
						}
					}
					if(strs.length()<=0){
						strs += "子对象数据可能较长，不做记录";
					}
					result += strs+"];";
				}
			} else {
				result += name + ":" + value.toString() + ";";
			}
		}

		return result;
	}

	/**
	 * 比较后翻译成字符串
	 * 
	 * @param comparedModel
	 *            被比较的model
	 * @return 翻译后结果
	 */
	@SuppressWarnings("unchecked")
	public String compareTranslate(StandardModel comparedModel) {
		String result = "";
		// 从spring容器中获取
		GetNameByField getNameByField = (GetNameByField) ApplicationContextInit
				.getBean("getNameByField");

		// 对象对应的字段数组
		Field fields[] = this.getClass().getDeclaredFields();

		//记录要修改信息的 ID 号
		PrimaryInfo pinfo = fetchPKey();
		if(pinfo != null && pinfo.getKey() != null){
			Field key = null;
			for(Field f : fields){
				if(pinfo.getKey().equals(f.getName())){
					key = f;
					break;
				}
			}
			if(key != null){
				String name = getNameByField.getName(key);
				result += name + "为 " + pinfo.getValue() + ";";
			}
		}
		
		// 循环对象字段数组
		for (int i = 0; i < fields.length; i++) {
			// 如果是Final修饰符修饰则不管
			if (Modifier.isFinal(fields[i].getModifiers())) {
				continue;
			}

			// 如果是Transient修饰符修饰则不管
			if (Modifier.isTransient(fields[i].getModifiers())) {
				continue;
			}

			// 如果源字段声明类型为private则不能获取值 必须setAccessible(true)后方可获取
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}
			String name = getNameByField.getName(fields[i]);
			Object oldValue = null;
			Object newValue = null;
			try {
				oldValue = fields[i].get(this);
				newValue = fields[i].get(comparedModel);
			} catch (Exception e) {
				logger.warn(e.getMessage());
				e.printStackTrace();
			}

			if (oldValue == null) {
				oldValue = "";
			}

			if (newValue == null) {
				continue;
			}

			if (oldValue instanceof StandardModel) {
				StandardModel oldModel = (StandardModel) oldValue;
				StandardModel newModel = (StandardModel) newValue;
				String subResult = oldModel.compareTranslate(newModel);
				if (subResult != null && subResult.length() > 0) {
					result += "子对象" + name + "修改内容[" + subResult + "];";
				}
			} else if(oldValue instanceof Collection){
				
			} else if(oldValue instanceof Map){
				
			} else {
				if (!oldValue.equals(newValue)) {
					result += name + "从" + oldValue.toString() + "修改为"
							+ newValue.toString() + ";";
				}
			}
		}

		return result;
	}

	/**
	 * 返回model的主键信息
	 * 
	 * @return 主键信息 包括主键名称和主键值
	 */
	public abstract PrimaryInfo fetchPKey();

	/**
	 * 业务对象 主键信息
	 * 
	 * @author xuejt
	 * 
	 */
	public class PrimaryInfo {

		private String key;

		private Serializable value;

		/**
		 * 业务对象 对应的主键信息
		 * 
		 * @param key
		 *            主键名称
		 * @param value
		 *            主键值
		 */
		public PrimaryInfo(String key, Serializable value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * 主键名称
		 */
		public String getKey() {
			return key;
		}

		/**
		 * 主键名称
		 */
		public void setKey(String key) {
			this.key = key;
		}

		/**
		 * 主键值
		 */
		public Serializable getValue() {
			return value;
		}

		/**
		 * 主键值
		 */
		public void setValue(Serializable value) {
			this.value = value;
		}

	}
}
