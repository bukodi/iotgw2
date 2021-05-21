package hu.noreg.iotgw2.service;

import hu.noreg.iotgw2.domain.OrgUnit;
import hu.noreg.iotgw2.repository.OrgUnitRepository;
import hu.noreg.iotgw2.service.dto.OrgUnitDTO;
import hu.noreg.iotgw2.service.mapper.OrgUnitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrgUnit}.
 */
@Service
@Transactional
public class OrgUnitService {

    private final Logger log = LoggerFactory.getLogger(OrgUnitService.class);

    private final OrgUnitRepository orgUnitRepository;

    private final OrgUnitMapper orgUnitMapper;

    public OrgUnitService(OrgUnitRepository orgUnitRepository, OrgUnitMapper orgUnitMapper) {
        this.orgUnitRepository = orgUnitRepository;
        this.orgUnitMapper = orgUnitMapper;
    }

    /**
     * Save a orgUnit.
     *
     * @param orgUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgUnitDTO save(OrgUnitDTO orgUnitDTO) {
        log.debug("Request to save OrgUnit : {}", orgUnitDTO);
        OrgUnit orgUnit = orgUnitMapper.toEntity(orgUnitDTO);
        orgUnit = orgUnitRepository.save(orgUnit);
        return orgUnitMapper.toDto(orgUnit);
    }

    /**
     * Partially update a orgUnit.
     *
     * @param orgUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrgUnitDTO> partialUpdate(OrgUnitDTO orgUnitDTO) {
        log.debug("Request to partially update OrgUnit : {}", orgUnitDTO);

        return orgUnitRepository
            .findById(orgUnitDTO.getId())
            .map(
                existingOrgUnit -> {
                    orgUnitMapper.partialUpdate(existingOrgUnit, orgUnitDTO);
                    return existingOrgUnit;
                }
            )
            .map(orgUnitRepository::save)
            .map(orgUnitMapper::toDto);
    }

    /**
     * Get all the orgUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrgUnits");
        return orgUnitRepository.findAll(pageable).map(orgUnitMapper::toDto);
    }

    /**
     * Get one orgUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrgUnitDTO> findOne(Long id) {
        log.debug("Request to get OrgUnit : {}", id);
        return orgUnitRepository.findById(id).map(orgUnitMapper::toDto);
    }

    /**
     * Delete the orgUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrgUnit : {}", id);
        orgUnitRepository.deleteById(id);
    }
}
