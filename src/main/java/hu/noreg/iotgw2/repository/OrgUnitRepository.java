package hu.noreg.iotgw2.repository;

import hu.noreg.iotgw2.domain.OrgUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrgUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgUnitRepository extends JpaRepository<OrgUnit, Long>, JpaSpecificationExecutor<OrgUnit> {}
