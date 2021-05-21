package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.*; // for static metamodels
import hu.noreg.iotgw2.domain.DeviceType;
import hu.noreg.iotgw2.repository.DeviceTypeRepository;
import hu.noreg.iotgw2.service.criteria.DeviceTypeCriteria;
import hu.noreg.iotgw2.service.dto.DeviceTypeDTO;
import hu.noreg.iotgw2.service.mapper.DeviceTypeMapper;
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
 * Service for executing complex queries for {@link DeviceType} entities in the database.
 * The main input is a {@link DeviceTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeviceTypeDTO} or a {@link Page} of {@link DeviceTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeviceTypeQueryService extends QueryService<DeviceType> {

    private final Logger log = LoggerFactory.getLogger(DeviceTypeQueryService.class);

    private final DeviceTypeRepository deviceTypeRepository;

    private final DeviceTypeMapper deviceTypeMapper;

    public DeviceTypeQueryService(DeviceTypeRepository deviceTypeRepository, DeviceTypeMapper deviceTypeMapper) {
        this.deviceTypeRepository = deviceTypeRepository;
        this.deviceTypeMapper = deviceTypeMapper;
    }

    /**
     * Return a {@link List} of {@link DeviceTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeviceTypeDTO> findByCriteria(DeviceTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeviceType> specification = createSpecification(criteria);
        return deviceTypeMapper.toDto(deviceTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeviceTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceTypeDTO> findByCriteria(DeviceTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeviceType> specification = createSpecification(criteria);
        return deviceTypeRepository.findAll(specification, page).map(deviceTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeviceTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeviceType> specification = createSpecification(criteria);
        return deviceTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link DeviceTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DeviceType> createSpecification(DeviceTypeCriteria criteria) {
        Specification<DeviceType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DeviceType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DeviceType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), DeviceType_.description));
            }
            if (criteria.getStateFieldName01() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateFieldName01(), DeviceType_.stateFieldName01));
            }
            if (criteria.getStateFieldName02() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateFieldName02(), DeviceType_.stateFieldName02));
            }
            if (criteria.getStateFieldName03() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateFieldName03(), DeviceType_.stateFieldName03));
            }
            if (criteria.getStateFieldName04() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateFieldName04(), DeviceType_.stateFieldName04));
            }
            if (criteria.getReadAuthPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReadAuthPattern(), DeviceType_.readAuthPattern));
            }
            if (criteria.getWriteAuthPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWriteAuthPattern(), DeviceType_.writeAuthPattern));
            }
            if (criteria.getEnrollProcessorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEnrollProcessorId(),
                            root -> root.join(DeviceType_.enrollProcessor, JoinType.LEFT).get(Processor_.id)
                        )
                    );
            }
            if (criteria.getMessageTypesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMessageTypesId(),
                            root -> root.join(DeviceType_.messageTypes, JoinType.LEFT).get(MessageType_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
