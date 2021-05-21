package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.*; // for static metamodels
import hu.noreg.iotgw2.domain.MessageType;
import hu.noreg.iotgw2.repository.MessageTypeRepository;
import hu.noreg.iotgw2.service.criteria.MessageTypeCriteria;
import hu.noreg.iotgw2.service.dto.MessageTypeDTO;
import hu.noreg.iotgw2.service.mapper.MessageTypeMapper;
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
 * Service for executing complex queries for {@link MessageType} entities in the database.
 * The main input is a {@link MessageTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MessageTypeDTO} or a {@link Page} of {@link MessageTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageTypeQueryService extends QueryService<MessageType> {

    private final Logger log = LoggerFactory.getLogger(MessageTypeQueryService.class);

    private final MessageTypeRepository messageTypeRepository;

    private final MessageTypeMapper messageTypeMapper;

    public MessageTypeQueryService(MessageTypeRepository messageTypeRepository, MessageTypeMapper messageTypeMapper) {
        this.messageTypeRepository = messageTypeRepository;
        this.messageTypeMapper = messageTypeMapper;
    }

    /**
     * Return a {@link List} of {@link MessageTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MessageTypeDTO> findByCriteria(MessageTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MessageType> specification = createSpecification(criteria);
        return messageTypeMapper.toDto(messageTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MessageTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageTypeDTO> findByCriteria(MessageTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MessageType> specification = createSpecification(criteria);
        return messageTypeRepository.findAll(specification, page).map(messageTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MessageType> specification = createSpecification(criteria);
        return messageTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link MessageTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MessageType> createSpecification(MessageTypeCriteria criteria) {
        Specification<MessageType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MessageType_.id));
            }
            if (criteria.getTypeCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTypeCode(), MessageType_.typeCode));
            }
            if (criteria.getDevToSrv() != null) {
                specification = specification.and(buildSpecification(criteria.getDevToSrv(), MessageType_.devToSrv));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MessageType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MessageType_.description));
            }
            if (criteria.getTimeout() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeout(), MessageType_.timeout));
            }
            if (criteria.getRetentionTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRetentionTime(), MessageType_.retentionTime));
            }
            if (criteria.getIndexFieldName01() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndexFieldName01(), MessageType_.indexFieldName01));
            }
            if (criteria.getIndexFieldName02() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndexFieldName02(), MessageType_.indexFieldName02));
            }
            if (criteria.getIndexFieldName03() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndexFieldName03(), MessageType_.indexFieldName03));
            }
            if (criteria.getIndexFieldName04() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndexFieldName04(), MessageType_.indexFieldName04));
            }
            if (criteria.getReadAuthPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReadAuthPattern(), MessageType_.readAuthPattern));
            }
            if (criteria.getWriteAuthPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWriteAuthPattern(), MessageType_.writeAuthPattern));
            }
            if (criteria.getMessageProcessorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMessageProcessorId(),
                            root -> root.join(MessageType_.messageProcessor, JoinType.LEFT).get(Processor_.id)
                        )
                    );
            }
            if (criteria.getTimeoutProcessorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTimeoutProcessorId(),
                            root -> root.join(MessageType_.timeoutProcessor, JoinType.LEFT).get(Processor_.id)
                        )
                    );
            }
            if (criteria.getDeviceTypesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDeviceTypesId(),
                            root -> root.join(MessageType_.deviceTypes, JoinType.LEFT).get(DeviceType_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
