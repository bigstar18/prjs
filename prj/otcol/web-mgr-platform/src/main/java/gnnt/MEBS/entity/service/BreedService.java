package gnnt.MEBS.entity.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.entity.dao.BreedDao;
import gnnt.MEBS.entity.dao.BreedParametersDao;
import gnnt.MEBS.entity.dao.BreedPropertyDao;
import gnnt.MEBS.entity.dao.BreedQualityDao;
import gnnt.MEBS.entity.model.Breed;
import gnnt.MEBS.entity.model.BreedParameters;
import gnnt.MEBS.entity.model.BreedProperty;
import gnnt.MEBS.entity.model.BreedQuality;
import gnnt.MEBS.entity.model.Commodity;
import gnnt.MEBS.entity.model.CommodityExpansion;
import gnnt.MEBS.entity.model.innerObejct.KeyValue;

@Service("e_breedService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class BreedService {
	@Autowired
	@Qualifier("e_breedDao")
	private BreedDao breedDao;
	@Autowired
	@Qualifier("e_breedParametersDao")
	private BreedParametersDao breedparameterDao;
	@Autowired
	@Qualifier("e_breedQualityDao")
	private BreedQualityDao breedQualityDao;
	@Autowired
	@Qualifier("e_breedPropertyDao")
	private BreedPropertyDao breedPropertyDao;

	public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		if (paramQueryConditions == null) {
			paramQueryConditions = new QueryConditions();
		}
		paramQueryConditions.addCondition("Status", "=", Integer.valueOf(0));
		return this.breedDao.getTableList(paramQueryConditions, paramPageInfo);
	}

	public List<BreedProperty> getBreedPropertyList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		return this.breedPropertyDao.getObjectList(null, null);
	}

	public Breed getBreedById(String paramString) {
		Breed localBreed = this.breedDao.getObject(paramString);
		QueryConditions localQueryConditions1 = new QueryConditions();
		localQueryConditions1.addCondition("breedId", "=", paramString);
		List localList1 = this.breedPropertyDao.getObjectList(null, null);
		ArrayList localArrayList = null;
		if ((localList1 != null) && (localList1.size() > 0)) {
			localArrayList = new ArrayList();
			Iterator localObject1 = localList1.iterator();
			while (((Iterator) localObject1).hasNext()) {
				BreedProperty localObject2 = (BreedProperty) ((Iterator) localObject1).next();
				KeyValue localKeyValue = new KeyValue();
				localKeyValue.setKey(localObject2);
				QueryConditions localQueryConditions2 = new QueryConditions();
				localQueryConditions2.addCondition("breedId", "=", paramString);
				localQueryConditions2.addCondition("PropertyKey", "=", ((BreedProperty) localObject2).getPropertyKey());
				PageInfo localPageInfo = new PageInfo(1, 10000, "No", false);
				List localList2 = this.breedparameterDao.getObjectList(localQueryConditions2, localPageInfo);
				localKeyValue.setValue(localList2);
				localArrayList.add(localKeyValue);
			}
		}
		Object localObject1 = new PageInfo(1, 10000, "No", false);
		Object localObject2 = this.breedQualityDao.getObjectList(localQueryConditions1, (PageInfo) localObject1);
		localBreed.addParametersList(localArrayList);
		localBreed.addQualityList((List) localObject2);
		return localBreed;
	}

	public int addBreed(Breed paramBreed) {
		int i = 0;
		List localList1 = null;
		QueryConditions localQueryConditions1 = new QueryConditions();
		localQueryConditions1.addCondition("breedId", "=", paramBreed.getBreedId());
		localList1 = this.breedDao.getObjectList(localQueryConditions1, null);
		if (localList1.size() != 0) {
			i = -1;
		}
		QueryConditions localQueryConditions2 = new QueryConditions();
		localQueryConditions2.addCondition("breedName", "=", paramBreed.getBreedName());
		localList1 = this.breedDao.getObjectList(localQueryConditions2, null);
		if (localList1.size() != 0) {
			i = -2;
		}
		if (i == 0) {
			this.breedDao.add(paramBreed);
			Commodity localCommodity = new Commodity();
			localCommodity.setId(paramBreed.getBreedId());
			localCommodity.setName(paramBreed.getBreedName());
			localCommodity.setAbility(0);
			localCommodity.setCreatetime(new Date());
			localCommodity.setModifyDate(new Date());
			this.breedDao.addCommodity(localCommodity);
			List localList2 = paramBreed.getQualityList();
			Object localObject1 = localList2.iterator();
			Object localObject3;
			while (((Iterator) localObject1).hasNext()) {
				BreedQuality localObject2 = (BreedQuality) ((Iterator) localObject1).next();
				this.breedQualityDao.add((BreedQuality) localObject2);
				localObject3 = new CommodityExpansion();
				((CommodityExpansion) localObject3).setCommodityId(paramBreed.getBreedId());
				((CommodityExpansion) localObject3).setKind("Quality");
				((CommodityExpansion) localObject3).setName(((BreedQuality) localObject2).getQualityName());
				((CommodityExpansion) localObject3).setNo(((BreedQuality) localObject2).getNo());
				this.breedDao.addCommodityExpansion((CommodityExpansion) localObject3);
			}
			localObject1 = paramBreed.getParametersList();
			Object localObject2 = ((List) localObject1).iterator();
			while (((Iterator) localObject2).hasNext()) {
				localObject3 = (KeyValue) ((Iterator) localObject2).next();
				List localList3 = (List) ((KeyValue) localObject3).getValue();
				Iterator localIterator = localList3.iterator();
				while (localIterator.hasNext()) {
					BreedParameters localBreedParameters = (BreedParameters) localIterator.next();
					this.breedparameterDao.add(localBreedParameters);
					CommodityExpansion localCommodityExpansion = new CommodityExpansion();
					localCommodityExpansion.setCommodityId(paramBreed.getBreedId());
					localCommodityExpansion.setKind(localBreedParameters.getPropertyKey());
					localCommodityExpansion.setName(localBreedParameters.getName());
					localCommodityExpansion.setNo(localBreedParameters.getNo());
					this.breedDao.addCommodityExpansion(localCommodityExpansion);
				}
			}
		}
		return i;
	}

	public int updateBreed(Breed paramBreed) {
		int i = 0;
		List localList1 = null;
		QueryConditions localQueryConditions1 = new QueryConditions();
		localQueryConditions1.addCondition("breedId", "=", paramBreed.getBreedId());
		QueryConditions localQueryConditions2 = new QueryConditions();
		localQueryConditions2.addCondition("breedName", "=", paramBreed.getBreedName());
		localQueryConditions2.addCondition("breedId", "<>", paramBreed.getBreedId());
		localList1 = this.breedDao.getObjectList(localQueryConditions2, null);
		if (localList1.size() != 0) {
			i = -1;
		}
		if (i == 0) {
			this.breedDao.update(paramBreed);
			QueryConditions localQueryConditions3 = new QueryConditions();
			Commodity localCommodity = this.breedDao.getCommodity(paramBreed.getBreedId());
			localCommodity.setModifyDate(new Date());
			localCommodity.setName(paramBreed.getBreedName());
			this.breedDao.updateCommodity(localCommodity);
			List localList2 = paramBreed.getQualityList();
			this.breedDao.delCommodityExpansiones(paramBreed.getBreedId());
			this.breedQualityDao.delete(localQueryConditions1);
			Object localObject1 = localList2.iterator();
			Object localObject3;
			while (((Iterator) localObject1).hasNext()) {
				BreedQuality localObject2 = (BreedQuality) ((Iterator) localObject1).next();
				localObject3 = new CommodityExpansion();
				((CommodityExpansion) localObject3).setCommodityId(paramBreed.getBreedId());
				((CommodityExpansion) localObject3).setKind("Quality");
				((CommodityExpansion) localObject3).setName(((BreedQuality) localObject2).getQualityName());
				((CommodityExpansion) localObject3).setNo(((BreedQuality) localObject2).getNo());
				this.breedDao.addCommodityExpansion((CommodityExpansion) localObject3);
				this.breedQualityDao.add((BreedQuality) localObject2);
			}
			localObject1 = paramBreed.getParametersList();
			System.out.println("parametersList===" + ((List) localObject1).size());
			Object localObject2 = ((List) localObject1).iterator();
			while (((Iterator) localObject2).hasNext()) {
				localObject3 = (KeyValue) ((Iterator) localObject2).next();
				List localList3 = (List) ((KeyValue) localObject3).getValue();
				BreedProperty localBreedProperty = (BreedProperty) ((KeyValue) localObject3).getKey();
				QueryConditions localQueryConditions4 = new QueryConditions();
				localQueryConditions4.addCondition("breedId", "=", paramBreed.getBreedId());
				localQueryConditions4.addCondition("PropertyKey", "=", localBreedProperty.getPropertyKey());
				this.breedparameterDao.delete(localQueryConditions4);
				Iterator localIterator = localList3.iterator();
				while (localIterator.hasNext()) {
					BreedParameters localBreedParameters = (BreedParameters) localIterator.next();
					this.breedparameterDao.add(localBreedParameters);
					CommodityExpansion localCommodityExpansion = new CommodityExpansion();
					localCommodityExpansion.setCommodityId(paramBreed.getBreedId());
					localCommodityExpansion.setKind(localBreedParameters.getPropertyKey());
					localCommodityExpansion.setName(localBreedParameters.getName());
					localCommodityExpansion.setNo(localBreedParameters.getNo());
					this.breedDao.addCommodityExpansion(localCommodityExpansion);
				}
			}
		}
		return i;
	}

	public int checkBreedById(String paramString) {
		int i = 0;
		int j = this.breedDao.getTBreed(paramString);
		if (j > 0) {
			i = 1;
		}
		int k = this.breedDao.getZBreed(paramString);
		if (k > 0) {
			i = 2;
		}
		int m = this.breedDao.getWBreed(paramString);
		if (m > 0) {
			i = 3;
		}
		return i;
	}

	public void deleteBreedById(List<Integer> paramList) {
		Iterator localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			Integer localInteger = (Integer) localIterator.next();
			QueryConditions localQueryConditions = new QueryConditions();
			localQueryConditions.addCondition("breedId", "=", localInteger);
			this.breedDao.delete(localQueryConditions);
			this.breedparameterDao.delete(localQueryConditions);
			this.breedQualityDao.delete(localQueryConditions);
			this.breedDao.delCommodity(localInteger.toString());
			this.breedDao.delCommodityExpansiones(localInteger.toString());
		}
	}

	public void synch(String[] paramArrayOfString) {
		for (String str : paramArrayOfString) {
			Breed localBreed = this.breedDao.getObject(str);
			this.breedDao.synchTBreedName(localBreed);
			this.breedDao.synchZBreedName(localBreed);
		}
	}
}
