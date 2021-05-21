package hu.noreg.iotgw2.repository;

import hu.noreg.iotgw2.domain.KeyPair;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the KeyPair entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyPairRepository extends JpaRepository<KeyPair, Long>, JpaSpecificationExecutor<KeyPair> {}
