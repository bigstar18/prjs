package gnnt.MEBS.common.front.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.common.front.model.translate.GetNameByField;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
import gnnt.MEBS.common.front.statictools.Serialize;
import gnnt.MEBS.common.front.statictools.Tools;

public abstract class StandardModel implements Cloneable, Serializable {
	private static final long serialVersionUID = -3032683871122601737L;
	private final transient Log logger = LogFactory.getLog(StandardModel.class);

	public StandardModel clone() {
		try {
			return (StandardModel) super.clone();
		} catch (CloneNotSupportedException localCloneNotSupportedException) {
			System.out.println("Cloning not allowed.");
			localCloneNotSupportedException.printStackTrace();
		}
		return this;
	}

	public String serialize() {
		return Serialize.serializeToXml(this);
	}

	public String translate() {
		String str1 = "";
		GetNameByField localGetNameByField = (GetNameByField) ApplicationContextInit.getBean("getNameByField");
		Field[] arrayOfField = getClass().getDeclaredFields();
		for (int i = 0; i < arrayOfField.length; i++) {
			if ((!Modifier.isFinal(arrayOfField[i].getModifiers())) && (!Modifier.isTransient(arrayOfField[i].getModifiers()))) {
				if (!arrayOfField[i].isAccessible()) {
					arrayOfField[i].setAccessible(true);
				}
				String str2 = localGetNameByField.getName(arrayOfField[i]);
				Object localObject1 = null;
				try {
					localObject1 = arrayOfField[i].get(this);
				} catch (Exception localException) {
					this.logger.warn(Tools.getExceptionTrace(localException));
				}
				if (localObject1 != null) {
					if ((localObject1 instanceof StandardModel)) {
						String localObject2 = ((StandardModel) localObject1).translate();
						if ((localObject2 != null) && (((String) localObject2).length() > 0)) {
							str1 = str1 + "子对象" + str2 + "的内容[" + (String) localObject2 + "];";
						}
					} else {
						Object localObject3;
						Object localObject5;
						if ((localObject1 instanceof Collection)) {
							if (((Collection) localObject1).size() > 0) {
								str1 = str1 + str2 + ":[";
								Object[] localObject2 = ((Collection) localObject1).toArray();
								localObject3 = "";
								for (int j = 0; j < localObject2.length; j++) {
									if (((String) localObject3).length() > 0) {
										localObject3 = (String) localObject3 + ";";
									}
									localObject5 = localObject2[j];
									if ((localObject5 instanceof StandardModel)) {
										localObject3 = (String) localObject3 + "";
									} else {
										localObject3 = (String) localObject3 + localObject5.toString();
									}
								}
								if (((String) localObject3).length() <= 0) {
									localObject3 = (String) localObject3 + "子对象数据可能较长，不做记录";
								}
								str1 = str1 + (String) localObject3 + "];";
							}
						} else if ((localObject1 instanceof Map)) {
							if (((Map) localObject1).size() > 0) {
								str1 = str1 + str2 + ":[";
								String localObject2 = "";
								localObject3 = ((Map) localObject1).keySet().iterator();
								while (((Iterator) localObject3).hasNext()) {
									Object localObject4 = ((Iterator) localObject3).next();
									if (((String) localObject2).length() > 0) {
										localObject2 = (String) localObject2 + ";";
									}
									localObject5 = ((Map) localObject1).get(localObject4);
									if (!(localObject5 instanceof StandardModel)) {
										localObject2 = (String) localObject2 + localObject4 + ":" + localObject5;
									}
								}
								if (((String) localObject2).length() <= 0) {
									localObject2 = (String) localObject2 + "子对象数据可能较长，不做记录";
								}
								str1 = str1 + (String) localObject2 + "];";
							}
						} else {
							str1 = str1 + str2 + ":" + localObject1.toString() + ";";
						}
					}
				}
			}
		}
		return str1;
	}

	public String compareTranslate(StandardModel paramStandardModel) {
		String str1 = "";
		GetNameByField localGetNameByField = (GetNameByField) ApplicationContextInit.getBean("getNameByField");
		Field[] arrayOfField = getClass().getDeclaredFields();
		PrimaryInfo localPrimaryInfo = fetchPKey();
		if ((localPrimaryInfo != null) && (localPrimaryInfo.getKey() != null)) {
			Object localObject1 = null;
			for (Object localObject5 : arrayOfField) {
				if (localPrimaryInfo.getKey().equals(((Field) localObject5).getName())) {
					localObject1 = localObject5;
					break;
				}
			}
			if (localObject1 != null) {
				String name = localGetNameByField.getName((Field) localObject1);
				str1 = str1 + (String) name + "为 " + localPrimaryInfo.getValue() + ";";
			}
		}
		for (int i = 0; i < arrayOfField.length; i++) {
			if ((!Modifier.isFinal(arrayOfField[i].getModifiers())) && (!Modifier.isTransient(arrayOfField[i].getModifiers()))) {
				if (!arrayOfField[i].isAccessible()) {
					arrayOfField[i].setAccessible(true);
				}
				String name = localGetNameByField.getName(arrayOfField[i]);
				Object localObject3 = null;
				Object localObject4 = null;
				try {
					localObject3 = arrayOfField[i].get(this);
					localObject4 = arrayOfField[i].get(paramStandardModel);
				} catch (Exception localException) {
					this.logger.warn(Tools.getExceptionTrace(localException));
				}
				if ((localObject3 != null) || (localObject4 != null)) {
					if (localObject3 == null) {
						localObject3 = "";
					}
					if (localObject4 != null) {
						if ((localObject3 instanceof StandardModel)) {
							StandardModel localStandardModel1 = (StandardModel) localObject3;
							StandardModel localStandardModel2 = (StandardModel) localObject4;
							String str2 = localStandardModel1.compareTranslate(localStandardModel2);
							if ((str2 != null) && (str2.length() > 0)) {
								str1 = str1 + "子对象" + name + "修改内容[" + str2 + "];";
							}
						} else
							if ((!(localObject3 instanceof Collection)) && (!(localObject3 instanceof Map)) && (!localObject3.equals(localObject4))) {
							str1 = str1 + (String) name + "从" + localObject3.toString() + "修改为" + localObject4.toString() + ";";
						}
					}
				}
			}
		}
		return str1;
	}

	public abstract PrimaryInfo fetchPKey();

	public class PrimaryInfo {
		private String key;
		private Serializable value;

		public PrimaryInfo(String paramString, Serializable paramSerializable) {
			this.key = paramString;
			this.value = paramSerializable;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(String paramString) {
			this.key = paramString;
		}

		public Serializable getValue() {
			return this.value;
		}

		public void setValue(Serializable paramSerializable) {
			this.value = paramSerializable;
		}
	}
}
