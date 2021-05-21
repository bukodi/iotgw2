package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.*; // for static metamodels
import hu.noreg.iotgw2.domain.Device;
import hu.noreg.iotgw2.repository.DeviceRepository;
import hu.noreg.iotgw2.service.criteria.DeviceCriteria;
import hu.noreg.iotgw2.service.dto.DeviceDTO;
import hu.noreg.iotgw2.service.mapper.DeviceMapper;
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
 * Service for executing complex queries for {@link Device} entities in the database.
 * The main input is a {@link DeviceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeviceDTO} or a {@link Page} of {@link DeviceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeviceQueryService extends QueryService<Device> {

    private final Logger log = LoggerFactory.getLogger(DeviceQueryService.class);

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    public DeviceQueryService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    /**
     * Return a {@link List} of {@link DeviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeviceDTO> findByCriteria(DeviceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Device> specification = createSpecification(criteria);
        return deviceMapper.toDto(deviceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceDTO> findByCriteria(DeviceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Device> specification = createSpecification(criteria);
        return deviceRepository.findAll(specification, page).map(deviceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeviceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Device> specification = createSpecification(criteria);
        return deviceRepository.count(specification);
    }

    /**
     * Function to convert {@link DeviceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Device> createSpecification(DeviceCriteria criteria) {
        Specification<Device> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Device_.id));
            }
            if (criteria.getVisualId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVisualId(), Device_.visualId));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Device_.description));
            }
            if (criteria.getEnrollmentCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnrollmentCode(), Device_.enrollmentCode));
            }
            if (criteria.getEnrollmentTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnrollmentTime(), Device_.enrollmentTime));
            }
            if (criteria.getStateFieldValue01() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateFieldValue01(), Device_.stateFieldValue01));
            }
            if (criteria.getStateFieldValue02() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateFieldValue02(), Device_.stateFieldValue02));
            }
            if (criteria.getStateFieldValue03() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateFieldValue03(), Device_.stateFieldValue03));
            }
            if (criteria.getStateFieldValue04() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateFieldValue04(), Device_.stateFieldValue04));
            }
            if (criteria.getReadAuthPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReadAuthPattern(), Device_.readAuthPattern));
            }
            if (criteria.getWriteAuthPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWriteAuthPattern(), Device_.writeAuthPattern));
            }
            if (criteria.getDeviceSignKeyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDeviceSignKeyId(),
                            root -> root.join(Device_.deviceSignKey, JoinType.LEFT).get(KeyPair_.id)
                        )
                    );
            }
            if (criteria.getDeviceEncKeyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDeviceEncKeyId(),
                            root -> root.join(Device_.deviceEncKey, JoinType.LEFT).get(KeyPair_.id)
                        )
                    );
            }
            if (criteria.getServerSignKeyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getServerSignKeyId(),
                            root -> root.join(Device_.serverSignKey, JoinType.LEFT).get(KeyPair_.id)
                        )
                    );
            }
            if (criteria.getServerEncKeyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getServerEncKeyId(),
                            root -> root.join(Device_.serverEncKey, JoinType.LEFT).get(KeyPair_.id)
                        )
                    );
            }
            if (criteria.getNextServerSignKeyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNextServerSignKeyId(),
                            root -> root.join(Device_.nextServerSignKey, JoinType.LEFT).get(KeyPair_.id)
                        )
                    );
            }
            if (criteria.getNextServerEncKeyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNextServerEncKeyId(),
                            root -> root.join(Device_.nextServerEncKey, JoinType.LEFT).get(KeyPair_.id)
                        )
                    );
            }
            if (criteria.getTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTypeId(), root -> root.join(Device_.type, JoinType.LEFT).get(DeviceType_.id))
                    );
            }
            if (criteria.getOrgUnitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOrgUnitId(), root -> root.join(Device_.orgUnit, JoinType.LEFT).get(OrgUnit_.id))
                    );
            }
        }
        return specification;
    }
}
