package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.*; // for static metamodels
import hu.noreg.iotgw2.domain.Processor;
import hu.noreg.iotgw2.repository.ProcessorRepository;
import hu.noreg.iotgw2.service.criteria.ProcessorCriteria;
import hu.noreg.iotgw2.service.dto.ProcessorDTO;
import hu.noreg.iotgw2.service.mapper.ProcessorMapper;
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
 * Service for executing complex queries for {@link Processor} entities in the database.
 * The main input is a {@link ProcessorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcessorDTO} or a {@link Page} of {@link ProcessorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessorQueryService extends QueryService<Processor> {

    private final Logger log = LoggerFactory.getLogger(ProcessorQueryService.class);

    private final ProcessorRepository processorRepository;

    private final ProcessorMapper processorMapper;

    public ProcessorQueryService(ProcessorRepository processorRepository, ProcessorMapper processorMapper) {
        this.processorRepository = processorRepository;
        this.processorMapper = processorMapper;
    }

    /**
     * Return a {@link List} of {@link ProcessorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessorDTO> findByCriteria(ProcessorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Processor> specification = createSpecification(criteria);
        return processorMapper.toDto(processorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProcessorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessorDTO> findByCriteria(ProcessorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Processor> specification = createSpecification(criteria);
        return processorRepository.findAll(specification, page).map(processorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Processor> specification = createSpecification(criteria);
        return processorRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Processor> createSpecification(ProcessorCriteria criteria) {
        Specification<Processor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Processor_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Processor_.name));
            }
            if (criteria.getExample() != null) {
                specification = specification.and(buildSpecification(criteria.getExample(), Processor_.example));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Processor_.description));
            }
            if (criteria.getProcessorIterface() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessorIterface(), Processor_.processorIterface));
            }
            if (criteria.getImplType() != null) {
                specification = specification.and(buildSpecification(criteria.getImplType(), Processor_.implType));
            }
            if (criteria.getParam01() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParam01(), Processor_.param01));
            }
            if (criteria.getParam02() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParam02(), Processor_.param02));
            }
            if (criteria.getSignerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSignerName(), Processor_.signerName));
            }
        }
        return specification;
    }
}
