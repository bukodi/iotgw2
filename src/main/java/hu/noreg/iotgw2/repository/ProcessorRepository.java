package hu.noreg.iotgw2.repository;

import hu.noreg.iotgw2.domain.Processor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Processor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessorRepository extends JpaRepository<Processor, Long>, JpaSpecificationExecutor<Processor> {}
