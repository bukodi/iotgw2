package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.*; // for static metamodels
import hu.noreg.iotgw2.domain.OrgUnit;
import hu.noreg.iotgw2.repository.OrgUnitRepository;
import hu.noreg.iotgw2.service.criteria.OrgUnitCriteria;
import hu.noreg.iotgw2.service.dto.OrgUnitDTO;
import hu.noreg.iotgw2.service.mapper.OrgUnitMapper;
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
 * Service for executing complex queries for {@link OrgUnit} entities in the database.
 * The main input is a {@link OrgUnitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrgUnitDTO} or a {@link Page} of {@link OrgUnitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrgUnitQueryService extends QueryService<OrgUnit> {

    private final Logger log = LoggerFactory.getLogger(OrgUnitQueryService.class);

    private final OrgUnitRepository orgUnitRepository;

    private final OrgUnitMapper orgUnitMapper;

    public OrgUnitQueryService(OrgUnitRepository orgUnitRepository, OrgUnitMapper orgUnitMapper) {
        this.orgUnitRepository = orgUnitRepository;
        this.orgUnitMapper = orgUnitMapper;
    }

    /**
     * Return a {@link List} of {@link OrgUnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrgUnitDTO> findByCriteria(OrgUnitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrgUnit> specification = createSpecification(criteria);
        return orgUnitMapper.toDto(orgUnitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrgUnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgUnitDTO> findByCriteria(OrgUnitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrgUnit> specification = createSpecification(criteria);
        return orgUnitRepository.findAll(specification, page).map(orgUnitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrgUnitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrgUnit> specification = createSpecification(criteria);
        return orgUnitRepository.count(specification);
    }

    /**
     * Function to convert {@link OrgUnitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrgUnit> createSpecification(OrgUnitCriteria criteria) {
        Specification<OrgUnit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrgUnit_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OrgUnit_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), OrgUnit_.description));
            }
            if (criteria.getReadAuthPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReadAuthPattern(), OrgUnit_.readAuthPattern));
            }
            if (criteria.getWriteAuthPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWriteAuthPattern(), OrgUnit_.writeAuthPattern));
            }
            if (criteria.getSubUnitsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSubUnitsId(), root -> root.join(OrgUnit_.subUnits, JoinType.LEFT).get(OrgUnit_.id))
                    );
            }
            if (criteria.getParentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getParentId(), root -> root.join(OrgUnit_.parent, JoinType.LEFT).get(OrgUnit_.id))
                    );
            }
        }
        return specification;
    }
}
