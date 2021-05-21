package hu.noreg.iotgw2.repository;

import hu.noreg.iotgw2.domain.MessageType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MessageType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, Long>, JpaSpecificationExecutor<MessageType> {}
