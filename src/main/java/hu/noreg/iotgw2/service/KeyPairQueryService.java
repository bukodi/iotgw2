package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.*; // for static metamodels
import hu.noreg.iotgw2.domain.KeyPair;
import hu.noreg.iotgw2.repository.KeyPairRepository;
import hu.noreg.iotgw2.service.criteria.KeyPairCriteria;
import hu.noreg.iotgw2.service.dto.KeyPairDTO;
import hu.noreg.iotgw2.service.mapper.KeyPairMapper;
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
 * Service for executing complex queries for {@link KeyPair} entities in the database.
 * The main input is a {@link KeyPairCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KeyPairDTO} or a {@link Page} of {@link KeyPairDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KeyPairQueryService extends QueryService<KeyPair> {

    private final Logger log = LoggerFactory.getLogger(KeyPairQueryService.class);

    private final KeyPairRepository keyPairRepository;

    private final KeyPairMapper keyPairMapper;

    public KeyPairQueryService(KeyPairRepository keyPairRepository, KeyPairMapper keyPairMapper) {
        this.keyPairRepository = keyPairRepository;
        this.keyPairMapper = keyPairMapper;
    }

    /**
     * Return a {@link List} of {@link KeyPairDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KeyPairDTO> findByCriteria(KeyPairCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KeyPair> specification = createSpecification(criteria);
        return keyPairMapper.toDto(keyPairRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link KeyPairDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KeyPairDTO> findByCriteria(KeyPairCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KeyPair> specification = createSpecification(criteria);
        return keyPairRepository.findAll(specification, page).map(keyPairMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KeyPairCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KeyPair> specification = createSpecification(criteria);
        return keyPairRepository.count(specification);
    }

    /**
     * Function to convert {@link KeyPairCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KeyPair> createSpecification(KeyPairCriteria criteria) {
        Specification<KeyPair> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KeyPair_.id));
            }
            if (criteria.getKeyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeyId(), KeyPair_.keyId));
            }
            if (criteria.getAlgorithm() != null) {
                specification = specification.and(buildSpecification(criteria.getAlgorithm(), KeyPair_.algorithm));
            }
            if (criteria.getCertSubjectDN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCertSubjectDN(), KeyPair_.certSubjectDN));
            }
            if (criteria.getCertIssuerDN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCertIssuerDN(), KeyPair_.certIssuerDN));
            }
            if (criteria.getCertSerial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCertSerial(), KeyPair_.certSerial));
            }
            if (criteria.getCertNotBefore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCertNotBefore(), KeyPair_.certNotBefore));
            }
            if (criteria.getCertNotAfter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCertNotAfter(), KeyPair_.certNotAfter));
            }
            if (criteria.getCertRevoked() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCertRevoked(), KeyPair_.certRevoked));
            }
        }
        return specification;
    }
}
