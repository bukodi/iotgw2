package hu.noreg.iotgw2.web.rest;

import hu.noreg.iotgw2.repository.KeyPairRepository;
import hu.noreg.iotgw2.service.KeyPairQueryService;
import hu.noreg.iotgw2.service.KeyPairService;
import hu.noreg.iotgw2.service.criteria.KeyPairCriteria;
import hu.noreg.iotgw2.service.dto.KeyPairDTO;
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
 * REST controller for managing {@link hu.noreg.iotgw2.domain.KeyPair}.
 */
@RestController
@RequestMapping("/api")
public class KeyPairResource {

    private final Logger log = LoggerFactory.getLogger(KeyPairResource.class);

    private static final String ENTITY_NAME = "keyPair";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeyPairService keyPairService;

    private final KeyPairRepository keyPairRepository;

    private final KeyPairQueryService keyPairQueryService;

    public KeyPairResource(KeyPairService keyPairService, KeyPairRepository keyPairRepository, KeyPairQueryService keyPairQueryService) {
        this.keyPairService = keyPairService;
        this.keyPairRepository = keyPairRepository;
        this.keyPairQueryService = keyPairQueryService;
    }

    /**
     * {@code POST  /key-pairs} : Create a new keyPair.
     *
     * @param keyPairDTO the keyPairDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keyPairDTO, or with status {@code 400 (Bad Request)} if the keyPair has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/key-pairs")
    public ResponseEntity<KeyPairDTO> createKeyPair(@Valid @RequestBody KeyPairDTO keyPairDTO) throws URISyntaxException {
        log.debug("REST request to save KeyPair : {}", keyPairDTO);
        if (keyPairDTO.getId() != null) {
            throw new BadRequestAlertException("A new keyPair cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyPairDTO result = keyPairService.save(keyPairDTO);
        return ResponseEntity
            .created(new URI("/api/key-pairs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /key-pairs/:id} : Updates an existing keyPair.
     *
     * @param id the id of the keyPairDTO to save.
     * @param keyPairDTO the keyPairDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keyPairDTO,
     * or with status {@code 400 (Bad Request)} if the keyPairDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keyPairDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/key-pairs/{id}")
    public ResponseEntity<KeyPairDTO> updateKeyPair(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KeyPairDTO keyPairDTO
    ) throws URISyntaxException {
        log.debug("REST request to update KeyPair : {}, {}", id, keyPairDTO);
        if (keyPairDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, keyPairDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!keyPairRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KeyPairDTO result = keyPairService.save(keyPairDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keyPairDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /key-pairs/:id} : Partial updates given fields of an existing keyPair, field will ignore if it is null
     *
     * @param id the id of the keyPairDTO to save.
     * @param keyPairDTO the keyPairDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keyPairDTO,
     * or with status {@code 400 (Bad Request)} if the keyPairDTO is not valid,
     * or with status {@code 404 (Not Found)} if the keyPairDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the keyPairDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/key-pairs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<KeyPairDTO> partialUpdateKeyPair(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KeyPairDTO keyPairDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update KeyPair partially : {}, {}", id, keyPairDTO);
        if (keyPairDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, keyPairDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!keyPairRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KeyPairDTO> result = keyPairService.partialUpdate(keyPairDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keyPairDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /key-pairs} : get all the keyPairs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keyPairs in body.
     */
    @GetMapping("/key-pairs")
    public ResponseEntity<List<KeyPairDTO>> getAllKeyPairs(KeyPairCriteria criteria, Pageable pageable) {
        log.debug("REST request to get KeyPairs by criteria: {}", criteria);
        Page<KeyPairDTO> page = keyPairQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /key-pairs/count} : count all the keyPairs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/key-pairs/count")
    public ResponseEntity<Long> countKeyPairs(KeyPairCriteria criteria) {
        log.debug("REST request to count KeyPairs by criteria: {}", criteria);
        return ResponseEntity.ok().body(keyPairQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /key-pairs/:id} : get the "id" keyPair.
     *
     * @param id the id of the keyPairDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyPairDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/key-pairs/{id}")
    public ResponseEntity<KeyPairDTO> getKeyPair(@PathVariable Long id) {
        log.debug("REST request to get KeyPair : {}", id);
        Optional<KeyPairDTO> keyPairDTO = keyPairService.findOne(id);
        return ResponseUtil.wrapOrNotFound(keyPairDTO);
    }

    /**
     * {@code DELETE  /key-pairs/:id} : delete the "id" keyPair.
     *
     * @param id the id of the keyPairDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/key-pairs/{id}")
    public ResponseEntity<Void> deleteKeyPair(@PathVariable Long id) {
        log.debug("REST request to delete KeyPair : {}", id);
        keyPairService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
