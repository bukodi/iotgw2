package hu.noreg.iotgw2.web.rest;

import hu.noreg.iotgw2.repository.MessageTypeRepository;
import hu.noreg.iotgw2.service.MessageTypeQueryService;
import hu.noreg.iotgw2.service.MessageTypeService;
import hu.noreg.iotgw2.service.criteria.MessageTypeCriteria;
import hu.noreg.iotgw2.service.dto.MessageTypeDTO;
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
 * REST controller for managing {@link hu.noreg.iotgw2.domain.MessageType}.
 */
@RestController
@RequestMapping("/api")
public class MessageTypeResource {

    private final Logger log = LoggerFactory.getLogger(MessageTypeResource.class);

    private static final String ENTITY_NAME = "messageType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageTypeService messageTypeService;

    private final MessageTypeRepository messageTypeRepository;

    private final MessageTypeQueryService messageTypeQueryService;

    public MessageTypeResource(
        MessageTypeService messageTypeService,
        MessageTypeRepository messageTypeRepository,
        MessageTypeQueryService messageTypeQueryService
    ) {
        this.messageTypeService = messageTypeService;
        this.messageTypeRepository = messageTypeRepository;
        this.messageTypeQueryService = messageTypeQueryService;
    }

    /**
     * {@code POST  /message-types} : Create a new messageType.
     *
     * @param messageTypeDTO the messageTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageTypeDTO, or with status {@code 400 (Bad Request)} if the messageType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-types")
    public ResponseEntity<MessageTypeDTO> createMessageType(@Valid @RequestBody MessageTypeDTO messageTypeDTO) throws URISyntaxException {
        log.debug("REST request to save MessageType : {}", messageTypeDTO);
        if (messageTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new messageType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageTypeDTO result = messageTypeService.save(messageTypeDTO);
        return ResponseEntity
            .created(new URI("/api/message-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-types/:id} : Updates an existing messageType.
     *
     * @param id the id of the messageTypeDTO to save.
     * @param messageTypeDTO the messageTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageTypeDTO,
     * or with status {@code 400 (Bad Request)} if the messageTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-types/{id}")
    public ResponseEntity<MessageTypeDTO> updateMessageType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MessageTypeDTO messageTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MessageType : {}, {}", id, messageTypeDTO);
        if (messageTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MessageTypeDTO result = messageTypeService.save(messageTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /message-types/:id} : Partial updates given fields of an existing messageType, field will ignore if it is null
     *
     * @param id the id of the messageTypeDTO to save.
     * @param messageTypeDTO the messageTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageTypeDTO,
     * or with status {@code 400 (Bad Request)} if the messageTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the messageTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the messageTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/message-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MessageTypeDTO> partialUpdateMessageType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MessageTypeDTO messageTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MessageType partially : {}, {}", id, messageTypeDTO);
        if (messageTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MessageTypeDTO> result = messageTypeService.partialUpdate(messageTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /message-types} : get all the messageTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageTypes in body.
     */
    @GetMapping("/message-types")
    public ResponseEntity<List<MessageTypeDTO>> getAllMessageTypes(MessageTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MessageTypes by criteria: {}", criteria);
        Page<MessageTypeDTO> page = messageTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /message-types/count} : count all the messageTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/message-types/count")
    public ResponseEntity<Long> countMessageTypes(MessageTypeCriteria criteria) {
        log.debug("REST request to count MessageTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(messageTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /message-types/:id} : get the "id" messageType.
     *
     * @param id the id of the messageTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-types/{id}")
    public ResponseEntity<MessageTypeDTO> getMessageType(@PathVariable Long id) {
        log.debug("REST request to get MessageType : {}", id);
        Optional<MessageTypeDTO> messageTypeDTO = messageTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageTypeDTO);
    }

    /**
     * {@code DELETE  /message-types/:id} : delete the "id" messageType.
     *
     * @param id the id of the messageTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-types/{id}")
    public ResponseEntity<Void> deleteMessageType(@PathVariable Long id) {
        log.debug("REST request to delete MessageType : {}", id);
        messageTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
