package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.BreedDao;
import gnnt.MEBS.zcjs.model.Breed;
import gnnt.MEBS.zcjs.model.WarehouseRegStock;
import gnnt.MEBS.zcjs.util.QualityRatio;
import gnnt.MEBS.zcjs.util.QualityUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_reGetService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ReGetService
{
  @Autowired
  @Qualifier("z_breedDao")
  private BreedDao breedDao;
  
  public WarehouseRegStock reGetWarehouseRegStock(WarehouseRegStock paramWarehouseRegStock)
  {
    Breed localBreed = this.breedDao.getObject(paramWarehouseRegStock.getBreedId());
    double d1 = QualityRatio.ratioBack(paramWarehouseRegStock.getFrozenWeight(), localBreed.getDeliveryRatio());
    d1 = QualityUnit.unitBack(d1, localBreed.getUnitVolume());
    paramWarehouseRegStock.setFrozenWeight(d1);
    double d2 = QualityRatio.ratioBack(paramWarehouseRegStock.getInitWeight(), localBreed.getDeliveryRatio());
    d2 = QualityUnit.unitBack(d2, localBreed.getUnitVolume());
    paramWarehouseRegStock.setInitWeight(d2);
    double d3 = QualityRatio.ratioBack(paramWarehouseRegStock.getWeight(), localBreed.getDeliveryRatio());
    d3 = QualityUnit.unitBack(d3, localBreed.getUnitVolume());
    paramWarehouseRegStock.setWeight(d3);
    return paramWarehouseRegStock;
  }
  
  public WarehouseRegStock reGetWarehouseRegStockObverse(WarehouseRegStock paramWarehouseRegStock)
  {
    Breed localBreed = this.breedDao.getObject(paramWarehouseRegStock.getBreedId());
    double d1 = QualityRatio.ratioObverse(paramWarehouseRegStock.getFrozenWeight(), localBreed.getDeliveryRatio());
    d1 = QualityUnit.uintObverse(d1, localBreed.getUnitVolume());
    paramWarehouseRegStock.setFrozenWeight(d1);
    double d2 = QualityRatio.ratioObverse(paramWarehouseRegStock.getInitWeight(), localBreed.getDeliveryRatio());
    d2 = QualityUnit.uintObverse(d2, localBreed.getUnitVolume());
    paramWarehouseRegStock.setInitWeight(d2);
    double d3 = QualityRatio.ratioObverse(paramWarehouseRegStock.getWeight(), localBreed.getDeliveryRatio());
    d3 = QualityUnit.uintObverse(d3, localBreed.getUnitVolume());
    paramWarehouseRegStock.setWeight(d3);
    return paramWarehouseRegStock;
  }
}
