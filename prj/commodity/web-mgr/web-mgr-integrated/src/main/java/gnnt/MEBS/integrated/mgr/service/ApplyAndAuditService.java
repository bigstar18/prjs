package gnnt.MEBS.integrated.mgr.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.common.mgr.model.Apply;
import gnnt.MEBS.common.mgr.model.Audit;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Serialize;

@Service("applyAndAuditService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class ApplyAndAuditService extends StandardService {
	public void rejectApply(Apply paramApply, String paramString) {
		paramApply.setStatus(Integer.valueOf(2));
		paramApply.setModTime(new Date());
		getDao().update(paramApply);
		Audit localAudit = new Audit();
		localAudit.setApply(paramApply);
		localAudit.setModTime(new Date());
		localAudit.setStatus(Integer.valueOf(2));
		localAudit.setAuditUser(paramString);
		getDao().add(localAudit);
	}

	public void passApply(Apply paramApply, String paramString)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchFieldException {
		paramApply.setStatus(Integer.valueOf(1));
		paramApply.setModTime(new Date());
		getDao().update(paramApply);
		Audit localAudit = new Audit();
		localAudit.setApply(paramApply);
		localAudit.setModTime(new Date());
		localAudit.setStatus(Integer.valueOf(1));
		localAudit.setAuditUser(paramString);
		getDao().add(localAudit);
		if (paramApply.getOperateType().equals("add")) {
			getDao().add(Serialize.deSerializeFromXml(paramApply.getContent()));
		} else if (paramApply.getOperateType().equals("update")) {
			getDao().update(Serialize.deSerializeFromXml(paramApply.getContent()));
		} else if ((paramApply.getOperateType().equals("delete")) || (paramApply.getOperateType().equals("deleteCollection"))) {
			String[] arrayOfString = paramApply.getContent().split(",");
			StandardModel localStandardModel1 = (StandardModel) Class.forName(paramApply.getEntityClass()).newInstance();
			Class localClass = localStandardModel1.getClass().getDeclaredField(localStandardModel1.fetchPKey().getKey()).getType();
			Object[] localObject1;
			int i;
			if (localClass.equals(Long.class)) {
				localObject1 = new Long[arrayOfString.length];
				for (i = 0; i < arrayOfString.length; i++) {
					localObject1[i] = Long.valueOf(arrayOfString[i]);
				}
			} else if (localClass.equals(Integer.class)) {
				localObject1 = new Integer[arrayOfString.length];
				for (i = 0; i < arrayOfString.length; i++) {
					localObject1[i] = Integer.valueOf(arrayOfString[i]);
				}
			} else {
				localObject1 = arrayOfString;
			}
			if (paramApply.getOperateType().equals("delete")) {
				getDao().deleteBYBulk(localStandardModel1, (Object[]) localObject1);
			} else {
				LinkedList localLinkedList = new LinkedList();
				for (Object localObject3 : localObject1) {
					StandardModel localStandardModel2 = (StandardModel) localStandardModel1.getClass().newInstance();
					String str = localStandardModel2.fetchPKey().getKey();
					Field localField = localStandardModel2.getClass().getDeclaredField(str);
					if (!localField.isAccessible()) {
						localField.setAccessible(true);
					}
					localField.set(localStandardModel2, localObject3);
					localStandardModel2 = getDao().get(localStandardModel2);
					localLinkedList.add(localStandardModel2);
				}
				getDao().deleteBYBulk(localLinkedList);
			}
		}
	}
}
