// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi

package gnnt.MEBS.common.broker.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.common.broker.model.translate.GetNameByField;
import gnnt.MEBS.common.broker.statictools.ApplicationContextInit;
import gnnt.MEBS.common.broker.statictools.Serialize;

public abstract class StandardModel implements Cloneable, Serializable {
	public class PrimaryInfo {

		private String key;
		private Serializable value;

		public String getKey() {
			return key;
		}

		public void setKey(String s) {
			key = s;
		}

		public Serializable getValue() {
			return value;
		}

		public void setValue(Serializable serializable) {
			value = serializable;
		}

		public PrimaryInfo(String s, Serializable serializable) {
			key = s;
			value = serializable;
		}
	}

	private final transient Log logger = LogFactory.getLog(StandardModel.class);
	private static final long serialVersionUID = 0xd5e9be1649ae48f7L;

	public StandardModel() {
	}

	public StandardModel clone() {
		try {
			return (StandardModel) super.clone();
		} catch (CloneNotSupportedException clonenotsupportedexception) {
			System.out.println("Cloning not allowed.");
			clonenotsupportedexception.printStackTrace();
			return this;
		}
	}

	public String serialize() {
		return Serialize.serializeToXml(this);
	}

	public String translate() {
		String s = "";
		GetNameByField getnamebyfield = (GetNameByField) ApplicationContextInit.getBean("getNameByField");
		Field afield[] = getClass().getDeclaredFields();
		for (int i = 0; i < afield.length; i++) {
			if (Modifier.isFinal(afield[i].getModifiers()) || Modifier.isTransient(afield[i].getModifiers()))
				continue;
			if (!afield[i].isAccessible())
				afield[i].setAccessible(true);
			String s1 = getnamebyfield.getName(afield[i]);
			Object obj = null;
			try {
				obj = afield[i].get(this);
			} catch (Exception exception) {
				logger.warn(exception.getMessage());
				exception.printStackTrace();
			}
			if (obj == null)
				continue;
			if (obj instanceof StandardModel) {
				String s2 = ((StandardModel) obj).translate();
				if (s2 != null && s2.length() > 0)
					s = (new StringBuilder()).append(s).append("子对象").append(s1).append("添加内容[").append(s2).append("];").toString();
				continue;
			}
			if (obj instanceof Collection) {
				if (((Collection) obj).size() <= 0)
					continue;
				s = (new StringBuilder()).append(s).append(s1).append(":[").toString();
				Object aobj[] = ((Collection) obj).toArray();
				for (int j = 0; j < aobj.length; j++) {
					if (j > 0)
						s = (new StringBuilder()).append(s).append(";").toString();
					Object obj1 = aobj[j];
					if (obj1 instanceof StandardModel)
						s = (new StringBuilder()).append(s).append(((StandardModel) obj1).translate()).toString();
					else
						s = (new StringBuilder()).append(s).append(obj1.toString()).toString();
				}

				s = (new StringBuilder()).append(s).append("];").toString();
			} else {
				s = (new StringBuilder()).append(s).append(s1).append(":").append(obj.toString()).append(";").toString();
			}
		}

		return s;
	}

	public String compareTranslate(StandardModel standardmodel) {
		String s = "";
		GetNameByField getnamebyfield = (GetNameByField) ApplicationContextInit.getBean("getNameByField");
		Field afield[] = getClass().getDeclaredFields();
		PrimaryInfo primaryinfo = fetchPKey();
		if (primaryinfo != null && primaryinfo.getKey() != null) {
			Field field = null;
			Field afield1[] = afield;
			int j = afield1.length;
			int k = 0;
			do {
				if (k >= j)
					break;
				Field field1 = afield1[k];
				if (primaryinfo.getKey().equals(field1.getName())) {
					field = field1;
					break;
				}
				k++;
			} while (true);
			if (field != null) {
				String s1 = getnamebyfield.getName(field);
				s = (new StringBuilder()).append(s).append(s1).append("为 ").append(primaryinfo.getValue()).append(";").toString();
			}
		}
		for (int i = 0; i < afield.length; i++) {
			if (Modifier.isFinal(afield[i].getModifiers()) || Modifier.isTransient(afield[i].getModifiers()))
				continue;
			if (!afield[i].isAccessible())
				afield[i].setAccessible(true);
			String s2 = getnamebyfield.getName(afield[i]);
			Object obj = null;
			Object obj1 = null;
			try {
				obj = afield[i].get(this);
				obj1 = afield[i].get(standardmodel);
			} catch (Exception exception) {
				logger.warn(exception.getMessage());
				exception.printStackTrace();
			}
			if (obj == null)
				obj = "";
			if (obj1 == null)
				continue;
			if (obj instanceof StandardModel) {
				StandardModel standardmodel1 = (StandardModel) obj;
				StandardModel standardmodel2 = (StandardModel) obj1;
				String s3 = standardmodel1.compareTranslate(standardmodel2);
				if (s3 != null && s3.length() > 0)
					s = (new StringBuilder()).append(s).append("子对象").append(s2).append("修改内容[").append(s3).append("];").toString();
				continue;
			}
			if (!(obj instanceof Collection) && !obj.equals(obj1))
				s = (new StringBuilder()).append(s).append(s2).append("从").append(obj.toString()).append("修改为").append(obj1.toString()).append(";")
						.toString();
		}

		return s;
	}

	public abstract PrimaryInfo fetchPKey();

}
