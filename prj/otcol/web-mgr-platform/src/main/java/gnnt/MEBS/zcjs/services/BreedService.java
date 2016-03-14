package gnnt.MEBS.zcjs.services;

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
import gnnt.MEBS.zcjs.dao.BreedDao;
import gnnt.MEBS.zcjs.dao.CommodityParameterDao;
import gnnt.MEBS.zcjs.dao.CommodityPropertyDao;
import gnnt.MEBS.zcjs.dao.EntityBreedDao;
import gnnt.MEBS.zcjs.dao.QualityDao;
import gnnt.MEBS.zcjs.model.Breed;
import gnnt.MEBS.zcjs.model.CommodityParameter;
import gnnt.MEBS.zcjs.model.CommodityProperty;
import gnnt.MEBS.zcjs.model.EntityBreed;
import gnnt.MEBS.zcjs.model.Quality;

@Service("z_breedService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class BreedService {
	@Autowired
	@Qualifier("z_breedDao")
	private BreedDao breedDao;
	@Autowired
	@Qualifier("z_qualityDao")
	private QualityDao qualityDao;
	@Autowired
	@Qualifier("z_entityBreedDao")
	private EntityBreedDao entityBreedDao;
	@Autowired
	@Qualifier("z_commodityParameterDao")
	private CommodityParameterDao commodityParameterDao;
	@Autowired
	@Qualifier("z_commodityPropertyDao")
	private CommodityPropertyDao commodityPropertyDao;

	public List<Map<String, Object>> entityBreedList() {
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("Status", "!=", Integer.valueOf(-1));
		return this.entityBreedDao.getTableList(localQueryConditions, null);
	}

	public List<Map<String, Object>> breedTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		return this.breedDao.getTableList(paramQueryConditions, paramPageInfo);
	}

	public void breedInsert(Breed paramBreed) {
		EntityBreed localEntityBreed = this.entityBreedDao.getObject(Long.valueOf(paramBreed.getBreedId()).toString());
		paramBreed.setBreedName(localEntityBreed.getBreedName());
		List localList = this.commodityPropertyDao.getObjectList(null, null);
		Object localObject3;
		Object localObject4;
		if ((localList != null) && (localList.size() > 0)) {
			Iterator localObject1 = localList.iterator();
			while (((Iterator) localObject1).hasNext()) {
				CommodityProperty localObject2 = (CommodityProperty) ((Iterator) localObject1).next();
				localObject3 = new QueryConditions();
				((QueryConditions) localObject3).addCondition("BreedId", "=", Long.valueOf(paramBreed.getBreedId()).toString());
				((QueryConditions) localObject3).addCondition("PropertyKey", "=", ((CommodityProperty) localObject2).getKey());
				localObject4 = this.entityBreedDao.getEntityParametersTableList((QueryConditions) localObject3, null);
				if ((localObject4 != null) && (((List) localObject4).size() > 0)) {
					for (int i = 0; i < ((List) localObject4).size(); i++) {
						Map localMap = (Map) ((List) localObject4).get(i);
						CommodityParameter localCommodityParameter = new CommodityParameter();
						localCommodityParameter.setBreedId(paramBreed.getBreedId());
						localCommodityParameter.setCommodityPropertyId(((CommodityProperty) localObject2).getPropertyId());
						localCommodityParameter.setParameterDescription(" ");
						localCommodityParameter.setParameterId(this.commodityParameterDao.getId());
						localCommodityParameter.setParameterName((String) localMap.get("name"));
						localCommodityParameter.setParameterStatus(1);
						this.commodityParameterDao.add(localCommodityParameter);
					}
				}
			}
		}
		Object localObject1 = new QueryConditions();
		((QueryConditions) localObject1).addCondition("BreedId", "=", Long.valueOf(paramBreed.getBreedId()));
		Object localObject2 = this.entityBreedDao.getEntityQualityTableList((QueryConditions) localObject1, null);
		if ((localObject2 != null) && (((List) localObject2).size() > 0)) {
			localObject3 = ((List) localObject2).iterator();
			while (((Iterator) localObject3).hasNext()) {
				localObject4 = (Map) ((Iterator) localObject3).next();
				Quality localQuality = new Quality();
				localQuality.setBreedId(paramBreed.getBreedId());
				localQuality.setQualityId(this.qualityDao.getId());
				localQuality.setQualityName((String) ((Map) localObject4).get("qualityName"));
				localQuality.setStatus(1);
				this.qualityDao.add(localQuality);
			}
		}
		this.breedDao.add(paramBreed);
	}

	public void updateBreed(Breed paramBreed) {
		this.breedDao.update(paramBreed);
	}

	public Breed getBreedById(long paramLong) {
		return this.breedDao.getObject(paramLong);
	}

	public void refurbish(List<Long> paramList) {
		for (int i = 0; i < paramList.size(); i++) {
			Long localLong = (Long) paramList.get(i);
			long l = ((Long) paramList.get(i)).longValue();
			Breed localBreed = this.breedDao.getObject(l);
			QueryConditions localQueryConditions = new QueryConditions();
			localQueryConditions.addCondition("BreedId", "=", Long.valueOf(localBreed.getBreedId()));
			List localList1 = this.commodityPropertyDao.getObjectList(null, null);
			List localList2 = this.commodityParameterDao.getObjectList(localQueryConditions, null);
			if ((localList2 != null) && (localList2.size() > 0)) {
				Iterator localObject1 = localList2.iterator();
				while (((Iterator) localObject1).hasNext()) {
					CommodityParameter localObject2 = (CommodityParameter) ((Iterator) localObject1).next();
					((CommodityParameter) localObject2).setParameterStatus(3);
					this.commodityParameterDao.update((CommodityParameter) localObject2);
				}
			}
			Object localObject4;
			Object localObject5;
			if ((localList1 != null) && (localList1.size() > 0)) {
				Iterator localObject1 = localList1.iterator();
				while (((Iterator) localObject1).hasNext()) {
					CommodityProperty localObject2 = (CommodityProperty) ((Iterator) localObject1).next();
					QueryConditions localObject3 = new QueryConditions();
					((QueryConditions) localObject3).addCondition("BreedId", "=", Long.valueOf(localBreed.getBreedId()));
					((QueryConditions) localObject3).addCondition("PropertyKey", "=", ((CommodityProperty) localObject2).getKey());
					localObject4 = this.entityBreedDao.getEntityParametersTableList((QueryConditions) localObject3, null);
					if ((localObject4 != null) && (((List) localObject4).size() > 0)) {
						for (int j = 0; j < ((List) localObject4).size(); j++) {
							localObject5 = (Map) ((List) localObject4).get(j);
							CommodityParameter localCommodityParameter = new CommodityParameter();
							localCommodityParameter.setBreedId(localBreed.getBreedId());
							localCommodityParameter.setCommodityPropertyId(((CommodityProperty) localObject2).getPropertyId());
							localCommodityParameter.setParameterDescription(" ");
							localCommodityParameter.setParameterId(this.commodityParameterDao.getId());
							localCommodityParameter.setParameterName((String) ((Map) localObject5).get("name"));
							localCommodityParameter.setParameterStatus(1);
							this.commodityParameterDao.add(localCommodityParameter);
						}
					}
				}
			}
			Object localObject1 = new QueryConditions();
			((QueryConditions) localObject1).addCondition("BreedId", "=", Long.valueOf(localBreed.getBreedId()));
			Object localObject2 = this.qualityDao.getObjectList((QueryConditions) localObject1, null);
			if ((localObject2 != null) && (((List) localObject2).size() > 0)) {
				Iterator localObject3 = ((List) localObject2).iterator();
				while (((Iterator) localObject3).hasNext()) {
					localObject4 = (Quality) ((Iterator) localObject3).next();
					((Quality) localObject4).setStatus(3);
					this.qualityDao.update((Quality) localObject4);
				}
			}
			Object localObject3 = this.entityBreedDao.getEntityQualityTableList((QueryConditions) localObject1, null);
			if ((localObject3 != null) && (((List) localObject3).size() > 0)) {
				localObject4 = ((List) localObject3).iterator();
				while (((Iterator) localObject4).hasNext()) {
					Map localMap = (Map) ((Iterator) localObject4).next();
					localObject5 = new Quality();
					((Quality) localObject5).setBreedId(localBreed.getBreedId());
					((Quality) localObject5).setQualityId(this.qualityDao.getId());
					((Quality) localObject5).setQualityName((String) localMap.get("qualityName"));
					((Quality) localObject5).setStatus(1);
					this.qualityDao.add((Quality) localObject5);
				}
			}
		}
	}
}
