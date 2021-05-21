package hu.noreg.iotgw2.repository;

import hu.noreg.iotgw2.domain.DeviceType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeviceType entity.
 */
@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long>, JpaSpecificationExecutor<DeviceType> {
    @Query(
        value = "select distinct deviceType from DeviceType deviceType left join fetch deviceType.messageTypes",
        countQuery = "select count(distinct deviceType) from DeviceType deviceType"
    )
    Page<DeviceType> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct deviceType from DeviceType deviceType left join fetch deviceType.messageTypes")
    List<DeviceType> findAllWithEagerRelationships();

    @Query("select deviceType from DeviceType deviceType left join fetch deviceType.messageTypes where deviceType.id =:id")
    Optional<DeviceType> findOneWithEagerRelationships(@Param("id") Long id);
}
