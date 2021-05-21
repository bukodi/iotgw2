package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.*; // for static metamodels
import hu.noreg.iotgw2.domain.Message;
import hu.noreg.iotgw2.repository.MessageRepository;
import hu.noreg.iotgw2.service.criteria.MessageCriteria;
import hu.noreg.iotgw2.service.dto.MessageDTO;
import hu.noreg.iotgw2.service.mapper.MessageMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Message} entities in the database.
 * The main input is a {@link MessageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MessageDTO} or a {@link Page} of {@link MessageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageQueryService extends QueryService<Message> {

    private final Logger log = LoggerFactory.getLogger(MessageQueryService.class);

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    public MessageQueryService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    /**
     * Return a {@link List} of {@link MessageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MessageDTO> findByCriteria(MessageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Message> specification = createSpecification(criteria);
        return messageMapper.toDto(messageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MessageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageDTO> findByCriteria(MessageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Message> specification = createSpecification(criteria);
        return messageRepository.findAll(specification, page).map(messageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Message> specification = createSpecification(criteria);
        return messageRepository.count(specification);
    }

    /**
     * Function to convert {@link MessageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Message> createSpecification(MessageCriteria criteria) {
        Specification<Message> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Message_.id));
            }
            if (criteria.getServerTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getServerTime(), Message_.serverTime));
            }
            if (criteria.getDeviceTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeviceTime(), Message_.deviceTime));
            }
            if (criteria.getRawMessageSHA256() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRawMessageSHA256(), Message_.rawMessageSHA256));
            }
            if (criteria.getDevToSrv() != null) {
                specification = specification.and(buildSpecification(criteria.getDevToSrv(), Message_.devToSrv));
            }
            if (criteria.getIndexFieldValue01() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndexFieldValue01(), Message_.indexFieldValue01));
            }
            if (criteria.getIndexFieldValue02() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndexFieldValue02(), Message_.indexFieldValue02));
            }
            if (criteria.getIndexFieldValue03() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndexFieldValue03(), Message_.indexFieldValue03));
            }
            if (criteria.getIndexFieldValue04() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndexFieldValue04(), Message_.indexFieldValue04));
            }
            if (criteria.getTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTypeId(), root -> root.join(Message_.type, JoinType.LEFT).get(MessageType_.id))
                    );
            }
            if (criteria.getDeviceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDeviceId(), root -> root.join(Message_.device, JoinType.LEFT).get(Device_.id))
                    );
            }
        }
        return specification;
    }
}
