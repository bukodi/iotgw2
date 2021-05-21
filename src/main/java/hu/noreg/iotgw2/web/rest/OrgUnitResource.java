package hu.noreg.iotgw2.web.rest;

import hu.noreg.iotgw2.repository.OrgUnitRepository;
import hu.noreg.iotgw2.service.OrgUnitQueryService;
import hu.noreg.iotgw2.service.OrgUnitService;
import hu.noreg.iotgw2.service.criteria.OrgUnitCriteria;
import hu.noreg.iotgw2.service.dto.OrgUnitDTO;
import hu.noreg.iotgw2.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link hu.noreg.iotgw2.domain.OrgUnit}.
 */
@RestController
@RequestMapping("/api")
public class OrgUnitResource {

    private final Logger log = LoggerFactory.getLogger(OrgUnitResource.class);

    private static final String ENTITY_NAME = "orgUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrgUnitService orgUnitService;

    private final OrgUnitRepository orgUnitRepository;

    private final OrgUnitQueryService orgUnitQueryService;

    public OrgUnitResource(OrgUnitService orgUnitService, OrgUnitRepository orgUnitRepository, OrgUnitQueryService orgUnitQueryService) {
        this.orgUnitService = orgUnitService;
        this.orgUnitRepository = orgUnitRepository;
        this.orgUnitQueryService = orgUnitQueryService;
    }

    /**
     * {@code POST  /org-units} : Create a new orgUnit.
     *
     * @param orgUnitDTO the orgUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orgUnitDTO, or with status {@code 400 (Bad Request)} if the orgUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/org-units")
    public ResponseEntity<OrgUnitDTO> createOrgUnit(@Valid @RequestBody OrgUnitDTO orgUnitDTO) throws URISyntaxException {
        log.debug("REST request to save OrgUnit : {}", orgUnitDTO);
        if (orgUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new orgUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrgUnitDTO result = orgUnitService.save(orgUnitDTO);
        return ResponseEntity
            .created(new URI("/api/org-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /org-units/:id} : Updates an existing orgUnit.
     *
     * @param id the id of the orgUnitDTO to save.
     * @param orgUnitDTO the orgUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgUnitDTO,
     * or with status {@code 400 (Bad Request)} if the orgUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orgUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/org-units/{id}")
    public ResponseEntity<OrgUnitDTO> updateOrgUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrgUnitDTO orgUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrgUnit : {}, {}", id, orgUnitDTO);
        if (orgUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrgUnitDTO result = orgUnitService.save(orgUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /org-units/:id} : Partial updates given fields of an existing orgUnit, field will ignore if it is null
     *
     * @param id the id of the orgUnitDTO to save.
     * @param orgUnitDTO the orgUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgUnitDTO,
     * or with status {@code 400 (Bad Request)} if the orgUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orgUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orgUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/org-units/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrgUnitDTO> partialUpdateOrgUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrgUnitDTO orgUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrgUnit partially : {}, {}", id, orgUnitDTO);
        if (orgUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrgUnitDTO> result = orgUnitService.partialUpdate(orgUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /org-units} : get all the orgUnits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orgUnits in body.
     */
    @GetMapping("/org-units")
    public ResponseEntity<List<OrgUnitDTO>> getAllOrgUnits(OrgUnitCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OrgUnits by criteria: {}", criteria);
        Page<OrgUnitDTO> page = orgUnitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /org-units/count} : count all the orgUnits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/org-units/count")
    public ResponseEntity<Long> countOrgUnits(OrgUnitCriteria criteria) {
        log.debug("REST request to count OrgUnits by criteria: {}", criteria);
        return ResponseEntity.ok().body(orgUnitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /org-units/:id} : get the "id" orgUnit.
     *
     * @param id the id of the orgUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orgUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/org-units/{id}")
    public ResponseEntity<OrgUnitDTO> getOrgUnit(@PathVariable Long id) {
        log.debug("REST request to get OrgUnit : {}", id);
        Optional<OrgUnitDTO> orgUnitDTO = orgUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orgUnitDTO);
    }

    /**
     * {@code DELETE  /org-units/:id} : delete the "id" orgUnit.
     *
     * @param id the id of the orgUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/org-units/{id}")
    public ResponseEntity<Void> deleteOrgUnit(@PathVariable Long id) {
        log.debug("REST request to delete OrgUnit : {}", id);
        orgUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
