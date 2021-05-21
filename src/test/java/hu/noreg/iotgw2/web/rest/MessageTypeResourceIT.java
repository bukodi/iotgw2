package hu.noreg.iotgw2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hu.noreg.iotgw2.IntegrationTest;
import hu.noreg.iotgw2.domain.DeviceType;
import hu.noreg.iotgw2.domain.MessageType;
import hu.noreg.iotgw2.domain.Processor;
import hu.noreg.iotgw2.repository.MessageTypeRepository;
import hu.noreg.iotgw2.service.criteria.MessageTypeCriteria;
import hu.noreg.iotgw2.service.dto.MessageTypeDTO;
import hu.noreg.iotgw2.service.mapper.MessageTypeMapper;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MessageTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MessageTypeResourceIT {

    private static final Integer DEFAULT_TYPE_CODE = 1;
    private static final Integer UPDATED_TYPE_CODE = 2;
    private static final Integer SMALLER_TYPE_CODE = 1 - 1;

    private static final Boolean DEFAULT_DEV_TO_SRV = false;
    private static final Boolean UPDATED_DEV_TO_SRV = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Duration DEFAULT_TIMEOUT = Duration.ofHours(6);
    private static final Duration UPDATED_TIMEOUT = Duration.ofHours(12);
    private static final Duration SMALLER_TIMEOUT = Duration.ofHours(5);

    private static final Duration DEFAULT_RETENTION_TIME = Duration.ofHours(6);
    private static final Duration UPDATED_RETENTION_TIME = Duration.ofHours(12);
    private static final Duration SMALLER_RETENTION_TIME = Duration.ofHours(5);

    private static final String DEFAULT_INDEX_FIELD_NAME_01 = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_FIELD_NAME_01 = "BBBBBBBBBB";

    private static final String DEFAULT_INDEX_FIELD_NAME_02 = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_FIELD_NAME_02 = "BBBBBBBBBB";

    private static final String DEFAULT_INDEX_FIELD_NAME_03 = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_FIELD_NAME_03 = "BBBBBBBBBB";

    private static final String DEFAULT_INDEX_FIELD_NAME_04 = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_FIELD_NAME_04 = "BBBBBBBBBB";

    private static final String DEFAULT_READ_AUTH_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_READ_AUTH_PATTERN = "BBBBBBBBBB";

    private static final String DEFAULT_WRITE_AUTH_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_WRITE_AUTH_PATTERN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/message-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MessageTypeRepository messageTypeRepository;

    @Autowired
    private MessageTypeMapper messageTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageTypeMockMvc;

    private MessageType messageType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageType createEntity(EntityManager em) {
        MessageType messageType = new MessageType()
            .typeCode(DEFAULT_TYPE_CODE)
            .devToSrv(DEFAULT_DEV_TO_SRV)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .timeout(DEFAULT_TIMEOUT)
            .retentionTime(DEFAULT_RETENTION_TIME)
            .indexFieldName01(DEFAULT_INDEX_FIELD_NAME_01)
            .indexFieldName02(DEFAULT_INDEX_FIELD_NAME_02)
            .indexFieldName03(DEFAULT_INDEX_FIELD_NAME_03)
            .indexFieldName04(DEFAULT_INDEX_FIELD_NAME_04)
            .readAuthPattern(DEFAULT_READ_AUTH_PATTERN)
            .writeAuthPattern(DEFAULT_WRITE_AUTH_PATTERN);
        return messageType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageType createUpdatedEntity(EntityManager em) {
        MessageType messageType = new MessageType()
            .typeCode(UPDATED_TYPE_CODE)
            .devToSrv(UPDATED_DEV_TO_SRV)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .timeout(UPDATED_TIMEOUT)
            .retentionTime(UPDATED_RETENTION_TIME)
            .indexFieldName01(UPDATED_INDEX_FIELD_NAME_01)
            .indexFieldName02(UPDATED_INDEX_FIELD_NAME_02)
            .indexFieldName03(UPDATED_INDEX_FIELD_NAME_03)
            .indexFieldName04(UPDATED_INDEX_FIELD_NAME_04)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);
        return messageType;
    }

    @BeforeEach
    public void initTest() {
        messageType = createEntity(em);
    }

    @Test
    @Transactional
    void createMessageType() throws Exception {
        int databaseSizeBeforeCreate = messageTypeRepository.findAll().size();
        // Create the MessageType
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);
        restMessageTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MessageType testMessageType = messageTypeList.get(messageTypeList.size() - 1);
        assertThat(testMessageType.getTypeCode()).isEqualTo(DEFAULT_TYPE_CODE);
        assertThat(testMessageType.getDevToSrv()).isEqualTo(DEFAULT_DEV_TO_SRV);
        assertThat(testMessageType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMessageType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMessageType.getTimeout()).isEqualTo(DEFAULT_TIMEOUT);
        assertThat(testMessageType.getRetentionTime()).isEqualTo(DEFAULT_RETENTION_TIME);
        assertThat(testMessageType.getIndexFieldName01()).isEqualTo(DEFAULT_INDEX_FIELD_NAME_01);
        assertThat(testMessageType.getIndexFieldName02()).isEqualTo(DEFAULT_INDEX_FIELD_NAME_02);
        assertThat(testMessageType.getIndexFieldName03()).isEqualTo(DEFAULT_INDEX_FIELD_NAME_03);
        assertThat(testMessageType.getIndexFieldName04()).isEqualTo(DEFAULT_INDEX_FIELD_NAME_04);
        assertThat(testMessageType.getReadAuthPattern()).isEqualTo(DEFAULT_READ_AUTH_PATTERN);
        assertThat(testMessageType.getWriteAuthPattern()).isEqualTo(DEFAULT_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void createMessageTypeWithExistingId() throws Exception {
        // Create the MessageType with an existing ID
        messageType.setId(1L);
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        int databaseSizeBeforeCreate = messageTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTypeRepository.findAll().size();
        // set the field null
        messageType.setTypeCode(null);

        // Create the MessageType, which fails.
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        restMessageTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDevToSrvIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTypeRepository.findAll().size();
        // set the field null
        messageType.setDevToSrv(null);

        // Create the MessageType, which fails.
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        restMessageTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTypeRepository.findAll().size();
        // set the field null
        messageType.setName(null);

        // Create the MessageType, which fails.
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        restMessageTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMessageTypes() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList
        restMessageTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeCode").value(hasItem(DEFAULT_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].devToSrv").value(hasItem(DEFAULT_DEV_TO_SRV.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeout").value(hasItem(DEFAULT_TIMEOUT.toString())))
            .andExpect(jsonPath("$.[*].retentionTime").value(hasItem(DEFAULT_RETENTION_TIME.toString())))
            .andExpect(jsonPath("$.[*].indexFieldName01").value(hasItem(DEFAULT_INDEX_FIELD_NAME_01)))
            .andExpect(jsonPath("$.[*].indexFieldName02").value(hasItem(DEFAULT_INDEX_FIELD_NAME_02)))
            .andExpect(jsonPath("$.[*].indexFieldName03").value(hasItem(DEFAULT_INDEX_FIELD_NAME_03)))
            .andExpect(jsonPath("$.[*].indexFieldName04").value(hasItem(DEFAULT_INDEX_FIELD_NAME_04)))
            .andExpect(jsonPath("$.[*].readAuthPattern").value(hasItem(DEFAULT_READ_AUTH_PATTERN)))
            .andExpect(jsonPath("$.[*].writeAuthPattern").value(hasItem(DEFAULT_WRITE_AUTH_PATTERN)));
    }

    @Test
    @Transactional
    void getMessageType() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get the messageType
        restMessageTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, messageType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageType.getId().intValue()))
            .andExpect(jsonPath("$.typeCode").value(DEFAULT_TYPE_CODE))
            .andExpect(jsonPath("$.devToSrv").value(DEFAULT_DEV_TO_SRV.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.timeout").value(DEFAULT_TIMEOUT.toString()))
            .andExpect(jsonPath("$.retentionTime").value(DEFAULT_RETENTION_TIME.toString()))
            .andExpect(jsonPath("$.indexFieldName01").value(DEFAULT_INDEX_FIELD_NAME_01))
            .andExpect(jsonPath("$.indexFieldName02").value(DEFAULT_INDEX_FIELD_NAME_02))
            .andExpect(jsonPath("$.indexFieldName03").value(DEFAULT_INDEX_FIELD_NAME_03))
            .andExpect(jsonPath("$.indexFieldName04").value(DEFAULT_INDEX_FIELD_NAME_04))
            .andExpect(jsonPath("$.readAuthPattern").value(DEFAULT_READ_AUTH_PATTERN))
            .andExpect(jsonPath("$.writeAuthPattern").value(DEFAULT_WRITE_AUTH_PATTERN));
    }

    @Test
    @Transactional
    void getMessageTypesByIdFiltering() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        Long id = messageType.getId();

        defaultMessageTypeShouldBeFound("id.equals=" + id);
        defaultMessageTypeShouldNotBeFound("id.notEquals=" + id);

        defaultMessageTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMessageTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultMessageTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMessageTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where typeCode equals to DEFAULT_TYPE_CODE
        defaultMessageTypeShouldBeFound("typeCode.equals=" + DEFAULT_TYPE_CODE);

        // Get all the messageTypeList where typeCode equals to UPDATED_TYPE_CODE
        defaultMessageTypeShouldNotBeFound("typeCode.equals=" + UPDATED_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTypeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where typeCode not equals to DEFAULT_TYPE_CODE
        defaultMessageTypeShouldNotBeFound("typeCode.notEquals=" + DEFAULT_TYPE_CODE);

        // Get all the messageTypeList where typeCode not equals to UPDATED_TYPE_CODE
        defaultMessageTypeShouldBeFound("typeCode.notEquals=" + UPDATED_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where typeCode in DEFAULT_TYPE_CODE or UPDATED_TYPE_CODE
        defaultMessageTypeShouldBeFound("typeCode.in=" + DEFAULT_TYPE_CODE + "," + UPDATED_TYPE_CODE);

        // Get all the messageTypeList where typeCode equals to UPDATED_TYPE_CODE
        defaultMessageTypeShouldNotBeFound("typeCode.in=" + UPDATED_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where typeCode is not null
        defaultMessageTypeShouldBeFound("typeCode.specified=true");

        // Get all the messageTypeList where typeCode is null
        defaultMessageTypeShouldNotBeFound("typeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByTypeCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where typeCode is greater than or equal to DEFAULT_TYPE_CODE
        defaultMessageTypeShouldBeFound("typeCode.greaterThanOrEqual=" + DEFAULT_TYPE_CODE);

        // Get all the messageTypeList where typeCode is greater than or equal to UPDATED_TYPE_CODE
        defaultMessageTypeShouldNotBeFound("typeCode.greaterThanOrEqual=" + UPDATED_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTypeCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where typeCode is less than or equal to DEFAULT_TYPE_CODE
        defaultMessageTypeShouldBeFound("typeCode.lessThanOrEqual=" + DEFAULT_TYPE_CODE);

        // Get all the messageTypeList where typeCode is less than or equal to SMALLER_TYPE_CODE
        defaultMessageTypeShouldNotBeFound("typeCode.lessThanOrEqual=" + SMALLER_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTypeCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where typeCode is less than DEFAULT_TYPE_CODE
        defaultMessageTypeShouldNotBeFound("typeCode.lessThan=" + DEFAULT_TYPE_CODE);

        // Get all the messageTypeList where typeCode is less than UPDATED_TYPE_CODE
        defaultMessageTypeShouldBeFound("typeCode.lessThan=" + UPDATED_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTypeCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where typeCode is greater than DEFAULT_TYPE_CODE
        defaultMessageTypeShouldNotBeFound("typeCode.greaterThan=" + DEFAULT_TYPE_CODE);

        // Get all the messageTypeList where typeCode is greater than SMALLER_TYPE_CODE
        defaultMessageTypeShouldBeFound("typeCode.greaterThan=" + SMALLER_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllMessageTypesByDevToSrvIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where devToSrv equals to DEFAULT_DEV_TO_SRV
        defaultMessageTypeShouldBeFound("devToSrv.equals=" + DEFAULT_DEV_TO_SRV);

        // Get all the messageTypeList where devToSrv equals to UPDATED_DEV_TO_SRV
        defaultMessageTypeShouldNotBeFound("devToSrv.equals=" + UPDATED_DEV_TO_SRV);
    }

    @Test
    @Transactional
    void getAllMessageTypesByDevToSrvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where devToSrv not equals to DEFAULT_DEV_TO_SRV
        defaultMessageTypeShouldNotBeFound("devToSrv.notEquals=" + DEFAULT_DEV_TO_SRV);

        // Get all the messageTypeList where devToSrv not equals to UPDATED_DEV_TO_SRV
        defaultMessageTypeShouldBeFound("devToSrv.notEquals=" + UPDATED_DEV_TO_SRV);
    }

    @Test
    @Transactional
    void getAllMessageTypesByDevToSrvIsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where devToSrv in DEFAULT_DEV_TO_SRV or UPDATED_DEV_TO_SRV
        defaultMessageTypeShouldBeFound("devToSrv.in=" + DEFAULT_DEV_TO_SRV + "," + UPDATED_DEV_TO_SRV);

        // Get all the messageTypeList where devToSrv equals to UPDATED_DEV_TO_SRV
        defaultMessageTypeShouldNotBeFound("devToSrv.in=" + UPDATED_DEV_TO_SRV);
    }

    @Test
    @Transactional
    void getAllMessageTypesByDevToSrvIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where devToSrv is not null
        defaultMessageTypeShouldBeFound("devToSrv.specified=true");

        // Get all the messageTypeList where devToSrv is null
        defaultMessageTypeShouldNotBeFound("devToSrv.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where name equals to DEFAULT_NAME
        defaultMessageTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the messageTypeList where name equals to UPDATED_NAME
        defaultMessageTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where name not equals to DEFAULT_NAME
        defaultMessageTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the messageTypeList where name not equals to UPDATED_NAME
        defaultMessageTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMessageTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the messageTypeList where name equals to UPDATED_NAME
        defaultMessageTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where name is not null
        defaultMessageTypeShouldBeFound("name.specified=true");

        // Get all the messageTypeList where name is null
        defaultMessageTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where name contains DEFAULT_NAME
        defaultMessageTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the messageTypeList where name contains UPDATED_NAME
        defaultMessageTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where name does not contain DEFAULT_NAME
        defaultMessageTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the messageTypeList where name does not contain UPDATED_NAME
        defaultMessageTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where description equals to DEFAULT_DESCRIPTION
        defaultMessageTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the messageTypeList where description equals to UPDATED_DESCRIPTION
        defaultMessageTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMessageTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultMessageTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the messageTypeList where description not equals to UPDATED_DESCRIPTION
        defaultMessageTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMessageTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMessageTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the messageTypeList where description equals to UPDATED_DESCRIPTION
        defaultMessageTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMessageTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where description is not null
        defaultMessageTypeShouldBeFound("description.specified=true");

        // Get all the messageTypeList where description is null
        defaultMessageTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where description contains DEFAULT_DESCRIPTION
        defaultMessageTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the messageTypeList where description contains UPDATED_DESCRIPTION
        defaultMessageTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMessageTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultMessageTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the messageTypeList where description does not contain UPDATED_DESCRIPTION
        defaultMessageTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTimeoutIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where timeout equals to DEFAULT_TIMEOUT
        defaultMessageTypeShouldBeFound("timeout.equals=" + DEFAULT_TIMEOUT);

        // Get all the messageTypeList where timeout equals to UPDATED_TIMEOUT
        defaultMessageTypeShouldNotBeFound("timeout.equals=" + UPDATED_TIMEOUT);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTimeoutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where timeout not equals to DEFAULT_TIMEOUT
        defaultMessageTypeShouldNotBeFound("timeout.notEquals=" + DEFAULT_TIMEOUT);

        // Get all the messageTypeList where timeout not equals to UPDATED_TIMEOUT
        defaultMessageTypeShouldBeFound("timeout.notEquals=" + UPDATED_TIMEOUT);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTimeoutIsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where timeout in DEFAULT_TIMEOUT or UPDATED_TIMEOUT
        defaultMessageTypeShouldBeFound("timeout.in=" + DEFAULT_TIMEOUT + "," + UPDATED_TIMEOUT);

        // Get all the messageTypeList where timeout equals to UPDATED_TIMEOUT
        defaultMessageTypeShouldNotBeFound("timeout.in=" + UPDATED_TIMEOUT);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTimeoutIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where timeout is not null
        defaultMessageTypeShouldBeFound("timeout.specified=true");

        // Get all the messageTypeList where timeout is null
        defaultMessageTypeShouldNotBeFound("timeout.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByTimeoutIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where timeout is greater than or equal to DEFAULT_TIMEOUT
        defaultMessageTypeShouldBeFound("timeout.greaterThanOrEqual=" + DEFAULT_TIMEOUT);

        // Get all the messageTypeList where timeout is greater than or equal to UPDATED_TIMEOUT
        defaultMessageTypeShouldNotBeFound("timeout.greaterThanOrEqual=" + UPDATED_TIMEOUT);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTimeoutIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where timeout is less than or equal to DEFAULT_TIMEOUT
        defaultMessageTypeShouldBeFound("timeout.lessThanOrEqual=" + DEFAULT_TIMEOUT);

        // Get all the messageTypeList where timeout is less than or equal to SMALLER_TIMEOUT
        defaultMessageTypeShouldNotBeFound("timeout.lessThanOrEqual=" + SMALLER_TIMEOUT);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTimeoutIsLessThanSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where timeout is less than DEFAULT_TIMEOUT
        defaultMessageTypeShouldNotBeFound("timeout.lessThan=" + DEFAULT_TIMEOUT);

        // Get all the messageTypeList where timeout is less than UPDATED_TIMEOUT
        defaultMessageTypeShouldBeFound("timeout.lessThan=" + UPDATED_TIMEOUT);
    }

    @Test
    @Transactional
    void getAllMessageTypesByTimeoutIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where timeout is greater than DEFAULT_TIMEOUT
        defaultMessageTypeShouldNotBeFound("timeout.greaterThan=" + DEFAULT_TIMEOUT);

        // Get all the messageTypeList where timeout is greater than SMALLER_TIMEOUT
        defaultMessageTypeShouldBeFound("timeout.greaterThan=" + SMALLER_TIMEOUT);
    }

    @Test
    @Transactional
    void getAllMessageTypesByRetentionTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where retentionTime equals to DEFAULT_RETENTION_TIME
        defaultMessageTypeShouldBeFound("retentionTime.equals=" + DEFAULT_RETENTION_TIME);

        // Get all the messageTypeList where retentionTime equals to UPDATED_RETENTION_TIME
        defaultMessageTypeShouldNotBeFound("retentionTime.equals=" + UPDATED_RETENTION_TIME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByRetentionTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where retentionTime not equals to DEFAULT_RETENTION_TIME
        defaultMessageTypeShouldNotBeFound("retentionTime.notEquals=" + DEFAULT_RETENTION_TIME);

        // Get all the messageTypeList where retentionTime not equals to UPDATED_RETENTION_TIME
        defaultMessageTypeShouldBeFound("retentionTime.notEquals=" + UPDATED_RETENTION_TIME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByRetentionTimeIsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where retentionTime in DEFAULT_RETENTION_TIME or UPDATED_RETENTION_TIME
        defaultMessageTypeShouldBeFound("retentionTime.in=" + DEFAULT_RETENTION_TIME + "," + UPDATED_RETENTION_TIME);

        // Get all the messageTypeList where retentionTime equals to UPDATED_RETENTION_TIME
        defaultMessageTypeShouldNotBeFound("retentionTime.in=" + UPDATED_RETENTION_TIME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByRetentionTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where retentionTime is not null
        defaultMessageTypeShouldBeFound("retentionTime.specified=true");

        // Get all the messageTypeList where retentionTime is null
        defaultMessageTypeShouldNotBeFound("retentionTime.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByRetentionTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where retentionTime is greater than or equal to DEFAULT_RETENTION_TIME
        defaultMessageTypeShouldBeFound("retentionTime.greaterThanOrEqual=" + DEFAULT_RETENTION_TIME);

        // Get all the messageTypeList where retentionTime is greater than or equal to UPDATED_RETENTION_TIME
        defaultMessageTypeShouldNotBeFound("retentionTime.greaterThanOrEqual=" + UPDATED_RETENTION_TIME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByRetentionTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where retentionTime is less than or equal to DEFAULT_RETENTION_TIME
        defaultMessageTypeShouldBeFound("retentionTime.lessThanOrEqual=" + DEFAULT_RETENTION_TIME);

        // Get all the messageTypeList where retentionTime is less than or equal to SMALLER_RETENTION_TIME
        defaultMessageTypeShouldNotBeFound("retentionTime.lessThanOrEqual=" + SMALLER_RETENTION_TIME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByRetentionTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where retentionTime is less than DEFAULT_RETENTION_TIME
        defaultMessageTypeShouldNotBeFound("retentionTime.lessThan=" + DEFAULT_RETENTION_TIME);

        // Get all the messageTypeList where retentionTime is less than UPDATED_RETENTION_TIME
        defaultMessageTypeShouldBeFound("retentionTime.lessThan=" + UPDATED_RETENTION_TIME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByRetentionTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where retentionTime is greater than DEFAULT_RETENTION_TIME
        defaultMessageTypeShouldNotBeFound("retentionTime.greaterThan=" + DEFAULT_RETENTION_TIME);

        // Get all the messageTypeList where retentionTime is greater than SMALLER_RETENTION_TIME
        defaultMessageTypeShouldBeFound("retentionTime.greaterThan=" + SMALLER_RETENTION_TIME);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName01IsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName01 equals to DEFAULT_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldBeFound("indexFieldName01.equals=" + DEFAULT_INDEX_FIELD_NAME_01);

        // Get all the messageTypeList where indexFieldName01 equals to UPDATED_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldNotBeFound("indexFieldName01.equals=" + UPDATED_INDEX_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName01IsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName01 not equals to DEFAULT_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldNotBeFound("indexFieldName01.notEquals=" + DEFAULT_INDEX_FIELD_NAME_01);

        // Get all the messageTypeList where indexFieldName01 not equals to UPDATED_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldBeFound("indexFieldName01.notEquals=" + UPDATED_INDEX_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName01IsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName01 in DEFAULT_INDEX_FIELD_NAME_01 or UPDATED_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldBeFound("indexFieldName01.in=" + DEFAULT_INDEX_FIELD_NAME_01 + "," + UPDATED_INDEX_FIELD_NAME_01);

        // Get all the messageTypeList where indexFieldName01 equals to UPDATED_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldNotBeFound("indexFieldName01.in=" + UPDATED_INDEX_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName01IsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName01 is not null
        defaultMessageTypeShouldBeFound("indexFieldName01.specified=true");

        // Get all the messageTypeList where indexFieldName01 is null
        defaultMessageTypeShouldNotBeFound("indexFieldName01.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName01ContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName01 contains DEFAULT_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldBeFound("indexFieldName01.contains=" + DEFAULT_INDEX_FIELD_NAME_01);

        // Get all the messageTypeList where indexFieldName01 contains UPDATED_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldNotBeFound("indexFieldName01.contains=" + UPDATED_INDEX_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName01NotContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName01 does not contain DEFAULT_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldNotBeFound("indexFieldName01.doesNotContain=" + DEFAULT_INDEX_FIELD_NAME_01);

        // Get all the messageTypeList where indexFieldName01 does not contain UPDATED_INDEX_FIELD_NAME_01
        defaultMessageTypeShouldBeFound("indexFieldName01.doesNotContain=" + UPDATED_INDEX_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName02IsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName02 equals to DEFAULT_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldBeFound("indexFieldName02.equals=" + DEFAULT_INDEX_FIELD_NAME_02);

        // Get all the messageTypeList where indexFieldName02 equals to UPDATED_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldNotBeFound("indexFieldName02.equals=" + UPDATED_INDEX_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName02IsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName02 not equals to DEFAULT_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldNotBeFound("indexFieldName02.notEquals=" + DEFAULT_INDEX_FIELD_NAME_02);

        // Get all the messageTypeList where indexFieldName02 not equals to UPDATED_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldBeFound("indexFieldName02.notEquals=" + UPDATED_INDEX_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName02IsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName02 in DEFAULT_INDEX_FIELD_NAME_02 or UPDATED_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldBeFound("indexFieldName02.in=" + DEFAULT_INDEX_FIELD_NAME_02 + "," + UPDATED_INDEX_FIELD_NAME_02);

        // Get all the messageTypeList where indexFieldName02 equals to UPDATED_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldNotBeFound("indexFieldName02.in=" + UPDATED_INDEX_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName02IsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName02 is not null
        defaultMessageTypeShouldBeFound("indexFieldName02.specified=true");

        // Get all the messageTypeList where indexFieldName02 is null
        defaultMessageTypeShouldNotBeFound("indexFieldName02.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName02ContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName02 contains DEFAULT_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldBeFound("indexFieldName02.contains=" + DEFAULT_INDEX_FIELD_NAME_02);

        // Get all the messageTypeList where indexFieldName02 contains UPDATED_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldNotBeFound("indexFieldName02.contains=" + UPDATED_INDEX_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName02NotContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName02 does not contain DEFAULT_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldNotBeFound("indexFieldName02.doesNotContain=" + DEFAULT_INDEX_FIELD_NAME_02);

        // Get all the messageTypeList where indexFieldName02 does not contain UPDATED_INDEX_FIELD_NAME_02
        defaultMessageTypeShouldBeFound("indexFieldName02.doesNotContain=" + UPDATED_INDEX_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName03IsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName03 equals to DEFAULT_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldBeFound("indexFieldName03.equals=" + DEFAULT_INDEX_FIELD_NAME_03);

        // Get all the messageTypeList where indexFieldName03 equals to UPDATED_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldNotBeFound("indexFieldName03.equals=" + UPDATED_INDEX_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName03IsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName03 not equals to DEFAULT_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldNotBeFound("indexFieldName03.notEquals=" + DEFAULT_INDEX_FIELD_NAME_03);

        // Get all the messageTypeList where indexFieldName03 not equals to UPDATED_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldBeFound("indexFieldName03.notEquals=" + UPDATED_INDEX_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName03IsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName03 in DEFAULT_INDEX_FIELD_NAME_03 or UPDATED_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldBeFound("indexFieldName03.in=" + DEFAULT_INDEX_FIELD_NAME_03 + "," + UPDATED_INDEX_FIELD_NAME_03);

        // Get all the messageTypeList where indexFieldName03 equals to UPDATED_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldNotBeFound("indexFieldName03.in=" + UPDATED_INDEX_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName03IsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName03 is not null
        defaultMessageTypeShouldBeFound("indexFieldName03.specified=true");

        // Get all the messageTypeList where indexFieldName03 is null
        defaultMessageTypeShouldNotBeFound("indexFieldName03.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName03ContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName03 contains DEFAULT_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldBeFound("indexFieldName03.contains=" + DEFAULT_INDEX_FIELD_NAME_03);

        // Get all the messageTypeList where indexFieldName03 contains UPDATED_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldNotBeFound("indexFieldName03.contains=" + UPDATED_INDEX_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName03NotContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName03 does not contain DEFAULT_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldNotBeFound("indexFieldName03.doesNotContain=" + DEFAULT_INDEX_FIELD_NAME_03);

        // Get all the messageTypeList where indexFieldName03 does not contain UPDATED_INDEX_FIELD_NAME_03
        defaultMessageTypeShouldBeFound("indexFieldName03.doesNotContain=" + UPDATED_INDEX_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName04IsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName04 equals to DEFAULT_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldBeFound("indexFieldName04.equals=" + DEFAULT_INDEX_FIELD_NAME_04);

        // Get all the messageTypeList where indexFieldName04 equals to UPDATED_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldNotBeFound("indexFieldName04.equals=" + UPDATED_INDEX_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName04IsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName04 not equals to DEFAULT_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldNotBeFound("indexFieldName04.notEquals=" + DEFAULT_INDEX_FIELD_NAME_04);

        // Get all the messageTypeList where indexFieldName04 not equals to UPDATED_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldBeFound("indexFieldName04.notEquals=" + UPDATED_INDEX_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName04IsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName04 in DEFAULT_INDEX_FIELD_NAME_04 or UPDATED_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldBeFound("indexFieldName04.in=" + DEFAULT_INDEX_FIELD_NAME_04 + "," + UPDATED_INDEX_FIELD_NAME_04);

        // Get all the messageTypeList where indexFieldName04 equals to UPDATED_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldNotBeFound("indexFieldName04.in=" + UPDATED_INDEX_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName04IsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName04 is not null
        defaultMessageTypeShouldBeFound("indexFieldName04.specified=true");

        // Get all the messageTypeList where indexFieldName04 is null
        defaultMessageTypeShouldNotBeFound("indexFieldName04.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName04ContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName04 contains DEFAULT_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldBeFound("indexFieldName04.contains=" + DEFAULT_INDEX_FIELD_NAME_04);

        // Get all the messageTypeList where indexFieldName04 contains UPDATED_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldNotBeFound("indexFieldName04.contains=" + UPDATED_INDEX_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllMessageTypesByIndexFieldName04NotContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where indexFieldName04 does not contain DEFAULT_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldNotBeFound("indexFieldName04.doesNotContain=" + DEFAULT_INDEX_FIELD_NAME_04);

        // Get all the messageTypeList where indexFieldName04 does not contain UPDATED_INDEX_FIELD_NAME_04
        defaultMessageTypeShouldBeFound("indexFieldName04.doesNotContain=" + UPDATED_INDEX_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllMessageTypesByReadAuthPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where readAuthPattern equals to DEFAULT_READ_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("readAuthPattern.equals=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the messageTypeList where readAuthPattern equals to UPDATED_READ_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("readAuthPattern.equals=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByReadAuthPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where readAuthPattern not equals to DEFAULT_READ_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("readAuthPattern.notEquals=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the messageTypeList where readAuthPattern not equals to UPDATED_READ_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("readAuthPattern.notEquals=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByReadAuthPatternIsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where readAuthPattern in DEFAULT_READ_AUTH_PATTERN or UPDATED_READ_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("readAuthPattern.in=" + DEFAULT_READ_AUTH_PATTERN + "," + UPDATED_READ_AUTH_PATTERN);

        // Get all the messageTypeList where readAuthPattern equals to UPDATED_READ_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("readAuthPattern.in=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByReadAuthPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where readAuthPattern is not null
        defaultMessageTypeShouldBeFound("readAuthPattern.specified=true");

        // Get all the messageTypeList where readAuthPattern is null
        defaultMessageTypeShouldNotBeFound("readAuthPattern.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByReadAuthPatternContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where readAuthPattern contains DEFAULT_READ_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("readAuthPattern.contains=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the messageTypeList where readAuthPattern contains UPDATED_READ_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("readAuthPattern.contains=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByReadAuthPatternNotContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where readAuthPattern does not contain DEFAULT_READ_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("readAuthPattern.doesNotContain=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the messageTypeList where readAuthPattern does not contain UPDATED_READ_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("readAuthPattern.doesNotContain=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByWriteAuthPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where writeAuthPattern equals to DEFAULT_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("writeAuthPattern.equals=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the messageTypeList where writeAuthPattern equals to UPDATED_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("writeAuthPattern.equals=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByWriteAuthPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where writeAuthPattern not equals to DEFAULT_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("writeAuthPattern.notEquals=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the messageTypeList where writeAuthPattern not equals to UPDATED_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("writeAuthPattern.notEquals=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByWriteAuthPatternIsInShouldWork() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where writeAuthPattern in DEFAULT_WRITE_AUTH_PATTERN or UPDATED_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("writeAuthPattern.in=" + DEFAULT_WRITE_AUTH_PATTERN + "," + UPDATED_WRITE_AUTH_PATTERN);

        // Get all the messageTypeList where writeAuthPattern equals to UPDATED_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("writeAuthPattern.in=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByWriteAuthPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where writeAuthPattern is not null
        defaultMessageTypeShouldBeFound("writeAuthPattern.specified=true");

        // Get all the messageTypeList where writeAuthPattern is null
        defaultMessageTypeShouldNotBeFound("writeAuthPattern.specified=false");
    }

    @Test
    @Transactional
    void getAllMessageTypesByWriteAuthPatternContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where writeAuthPattern contains DEFAULT_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("writeAuthPattern.contains=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the messageTypeList where writeAuthPattern contains UPDATED_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("writeAuthPattern.contains=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByWriteAuthPatternNotContainsSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList where writeAuthPattern does not contain DEFAULT_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldNotBeFound("writeAuthPattern.doesNotContain=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the messageTypeList where writeAuthPattern does not contain UPDATED_WRITE_AUTH_PATTERN
        defaultMessageTypeShouldBeFound("writeAuthPattern.doesNotContain=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllMessageTypesByMessageProcessorIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);
        Processor messageProcessor = ProcessorResourceIT.createEntity(em);
        em.persist(messageProcessor);
        em.flush();
        messageType.setMessageProcessor(messageProcessor);
        messageTypeRepository.saveAndFlush(messageType);
        Long messageProcessorId = messageProcessor.getId();

        // Get all the messageTypeList where messageProcessor equals to messageProcessorId
        defaultMessageTypeShouldBeFound("messageProcessorId.equals=" + messageProcessorId);

        // Get all the messageTypeList where messageProcessor equals to (messageProcessorId + 1)
        defaultMessageTypeShouldNotBeFound("messageProcessorId.equals=" + (messageProcessorId + 1));
    }

    @Test
    @Transactional
    void getAllMessageTypesByTimeoutProcessorIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);
        Processor timeoutProcessor = ProcessorResourceIT.createEntity(em);
        em.persist(timeoutProcessor);
        em.flush();
        messageType.setTimeoutProcessor(timeoutProcessor);
        messageTypeRepository.saveAndFlush(messageType);
        Long timeoutProcessorId = timeoutProcessor.getId();

        // Get all the messageTypeList where timeoutProcessor equals to timeoutProcessorId
        defaultMessageTypeShouldBeFound("timeoutProcessorId.equals=" + timeoutProcessorId);

        // Get all the messageTypeList where timeoutProcessor equals to (timeoutProcessorId + 1)
        defaultMessageTypeShouldNotBeFound("timeoutProcessorId.equals=" + (timeoutProcessorId + 1));
    }

    @Test
    @Transactional
    void getAllMessageTypesByDeviceTypesIsEqualToSomething() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);
        DeviceType deviceTypes = DeviceTypeResourceIT.createEntity(em);
        em.persist(deviceTypes);
        em.flush();
        messageType.addDeviceTypes(deviceTypes);
        messageTypeRepository.saveAndFlush(messageType);
        Long deviceTypesId = deviceTypes.getId();

        // Get all the messageTypeList where deviceTypes equals to deviceTypesId
        defaultMessageTypeShouldBeFound("deviceTypesId.equals=" + deviceTypesId);

        // Get all the messageTypeList where deviceTypes equals to (deviceTypesId + 1)
        defaultMessageTypeShouldNotBeFound("deviceTypesId.equals=" + (deviceTypesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessageTypeShouldBeFound(String filter) throws Exception {
        restMessageTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeCode").value(hasItem(DEFAULT_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].devToSrv").value(hasItem(DEFAULT_DEV_TO_SRV.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeout").value(hasItem(DEFAULT_TIMEOUT.toString())))
            .andExpect(jsonPath("$.[*].retentionTime").value(hasItem(DEFAULT_RETENTION_TIME.toString())))
            .andExpect(jsonPath("$.[*].indexFieldName01").value(hasItem(DEFAULT_INDEX_FIELD_NAME_01)))
            .andExpect(jsonPath("$.[*].indexFieldName02").value(hasItem(DEFAULT_INDEX_FIELD_NAME_02)))
            .andExpect(jsonPath("$.[*].indexFieldName03").value(hasItem(DEFAULT_INDEX_FIELD_NAME_03)))
            .andExpect(jsonPath("$.[*].indexFieldName04").value(hasItem(DEFAULT_INDEX_FIELD_NAME_04)))
            .andExpect(jsonPath("$.[*].readAuthPattern").value(hasItem(DEFAULT_READ_AUTH_PATTERN)))
            .andExpect(jsonPath("$.[*].writeAuthPattern").value(hasItem(DEFAULT_WRITE_AUTH_PATTERN)));

        // Check, that the count call also returns 1
        restMessageTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessageTypeShouldNotBeFound(String filter) throws Exception {
        restMessageTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessageTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMessageType() throws Exception {
        // Get the messageType
        restMessageTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMessageType() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();

        // Update the messageType
        MessageType updatedMessageType = messageTypeRepository.findById(messageType.getId()).get();
        // Disconnect from session so that the updates on updatedMessageType are not directly saved in db
        em.detach(updatedMessageType);
        updatedMessageType
            .typeCode(UPDATED_TYPE_CODE)
            .devToSrv(UPDATED_DEV_TO_SRV)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .timeout(UPDATED_TIMEOUT)
            .retentionTime(UPDATED_RETENTION_TIME)
            .indexFieldName01(UPDATED_INDEX_FIELD_NAME_01)
            .indexFieldName02(UPDATED_INDEX_FIELD_NAME_02)
            .indexFieldName03(UPDATED_INDEX_FIELD_NAME_03)
            .indexFieldName04(UPDATED_INDEX_FIELD_NAME_04)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(updatedMessageType);

        restMessageTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
        MessageType testMessageType = messageTypeList.get(messageTypeList.size() - 1);
        assertThat(testMessageType.getTypeCode()).isEqualTo(UPDATED_TYPE_CODE);
        assertThat(testMessageType.getDevToSrv()).isEqualTo(UPDATED_DEV_TO_SRV);
        assertThat(testMessageType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMessageType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMessageType.getTimeout()).isEqualTo(UPDATED_TIMEOUT);
        assertThat(testMessageType.getRetentionTime()).isEqualTo(UPDATED_RETENTION_TIME);
        assertThat(testMessageType.getIndexFieldName01()).isEqualTo(UPDATED_INDEX_FIELD_NAME_01);
        assertThat(testMessageType.getIndexFieldName02()).isEqualTo(UPDATED_INDEX_FIELD_NAME_02);
        assertThat(testMessageType.getIndexFieldName03()).isEqualTo(UPDATED_INDEX_FIELD_NAME_03);
        assertThat(testMessageType.getIndexFieldName04()).isEqualTo(UPDATED_INDEX_FIELD_NAME_04);
        assertThat(testMessageType.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testMessageType.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void putNonExistingMessageType() throws Exception {
        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();
        messageType.setId(count.incrementAndGet());

        // Create the MessageType
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMessageType() throws Exception {
        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();
        messageType.setId(count.incrementAndGet());

        // Create the MessageType
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMessageType() throws Exception {
        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();
        messageType.setId(count.incrementAndGet());

        // Create the MessageType
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMessageTypeWithPatch() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();

        // Update the messageType using partial update
        MessageType partialUpdatedMessageType = new MessageType();
        partialUpdatedMessageType.setId(messageType.getId());

        partialUpdatedMessageType
            .retentionTime(UPDATED_RETENTION_TIME)
            .indexFieldName01(UPDATED_INDEX_FIELD_NAME_01)
            .indexFieldName04(UPDATED_INDEX_FIELD_NAME_04)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);

        restMessageTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessageType))
            )
            .andExpect(status().isOk());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
        MessageType testMessageType = messageTypeList.get(messageTypeList.size() - 1);
        assertThat(testMessageType.getTypeCode()).isEqualTo(DEFAULT_TYPE_CODE);
        assertThat(testMessageType.getDevToSrv()).isEqualTo(DEFAULT_DEV_TO_SRV);
        assertThat(testMessageType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMessageType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMessageType.getTimeout()).isEqualTo(DEFAULT_TIMEOUT);
        assertThat(testMessageType.getRetentionTime()).isEqualTo(UPDATED_RETENTION_TIME);
        assertThat(testMessageType.getIndexFieldName01()).isEqualTo(UPDATED_INDEX_FIELD_NAME_01);
        assertThat(testMessageType.getIndexFieldName02()).isEqualTo(DEFAULT_INDEX_FIELD_NAME_02);
        assertThat(testMessageType.getIndexFieldName03()).isEqualTo(DEFAULT_INDEX_FIELD_NAME_03);
        assertThat(testMessageType.getIndexFieldName04()).isEqualTo(UPDATED_INDEX_FIELD_NAME_04);
        assertThat(testMessageType.getReadAuthPattern()).isEqualTo(DEFAULT_READ_AUTH_PATTERN);
        assertThat(testMessageType.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void fullUpdateMessageTypeWithPatch() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();

        // Update the messageType using partial update
        MessageType partialUpdatedMessageType = new MessageType();
        partialUpdatedMessageType.setId(messageType.getId());

        partialUpdatedMessageType
            .typeCode(UPDATED_TYPE_CODE)
            .devToSrv(UPDATED_DEV_TO_SRV)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .timeout(UPDATED_TIMEOUT)
            .retentionTime(UPDATED_RETENTION_TIME)
            .indexFieldName01(UPDATED_INDEX_FIELD_NAME_01)
            .indexFieldName02(UPDATED_INDEX_FIELD_NAME_02)
            .indexFieldName03(UPDATED_INDEX_FIELD_NAME_03)
            .indexFieldName04(UPDATED_INDEX_FIELD_NAME_04)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);

        restMessageTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessageType))
            )
            .andExpect(status().isOk());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
        MessageType testMessageType = messageTypeList.get(messageTypeList.size() - 1);
        assertThat(testMessageType.getTypeCode()).isEqualTo(UPDATED_TYPE_CODE);
        assertThat(testMessageType.getDevToSrv()).isEqualTo(UPDATED_DEV_TO_SRV);
        assertThat(testMessageType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMessageType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMessageType.getTimeout()).isEqualTo(UPDATED_TIMEOUT);
        assertThat(testMessageType.getRetentionTime()).isEqualTo(UPDATED_RETENTION_TIME);
        assertThat(testMessageType.getIndexFieldName01()).isEqualTo(UPDATED_INDEX_FIELD_NAME_01);
        assertThat(testMessageType.getIndexFieldName02()).isEqualTo(UPDATED_INDEX_FIELD_NAME_02);
        assertThat(testMessageType.getIndexFieldName03()).isEqualTo(UPDATED_INDEX_FIELD_NAME_03);
        assertThat(testMessageType.getIndexFieldName04()).isEqualTo(UPDATED_INDEX_FIELD_NAME_04);
        assertThat(testMessageType.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testMessageType.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void patchNonExistingMessageType() throws Exception {
        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();
        messageType.setId(count.incrementAndGet());

        // Create the MessageType
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, messageTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMessageType() throws Exception {
        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();
        messageType.setId(count.incrementAndGet());

        // Create the MessageType
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMessageType() throws Exception {
        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();
        messageType.setId(count.incrementAndGet());

        // Create the MessageType
        MessageTypeDTO messageTypeDTO = messageTypeMapper.toDto(messageType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(messageTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMessageType() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        int databaseSizeBeforeDelete = messageTypeRepository.findAll().size();

        // Delete the messageType
        restMessageTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, messageType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
