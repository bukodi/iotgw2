package hu.noreg.iotgw2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hu.noreg.iotgw2.IntegrationTest;
import hu.noreg.iotgw2.domain.Processor;
import hu.noreg.iotgw2.domain.enumeration.ImplemntationType;
import hu.noreg.iotgw2.domain.enumeration.ProcessorInterface;
import hu.noreg.iotgw2.repository.ProcessorRepository;
import hu.noreg.iotgw2.service.criteria.ProcessorCriteria;
import hu.noreg.iotgw2.service.dto.ProcessorDTO;
import hu.noreg.iotgw2.service.mapper.ProcessorMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProcessorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcessorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXAMPLE = false;
    private static final Boolean UPDATED_EXAMPLE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ProcessorInterface DEFAULT_PROCESSOR_ITERFACE = ProcessorInterface.BeforeSendToDevice;
    private static final ProcessorInterface UPDATED_PROCESSOR_ITERFACE = ProcessorInterface.AfterRecivedFromDevice;

    private static final ImplemntationType DEFAULT_IMPL_TYPE = ImplemntationType.JavaCall;
    private static final ImplemntationType UPDATED_IMPL_TYPE = ImplemntationType.GroovyScript;

    private static final String DEFAULT_PARAM_01 = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_01 = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM_02 = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_02 = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SIGNER_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SIGNATURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SIGNATURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SIGNATURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SIGNATURE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/processors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcessorRepository processorRepository;

    @Autowired
    private ProcessorMapper processorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessorMockMvc;

    private Processor processor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processor createEntity(EntityManager em) {
        Processor processor = new Processor()
            .name(DEFAULT_NAME)
            .example(DEFAULT_EXAMPLE)
            .description(DEFAULT_DESCRIPTION)
            .processorIterface(DEFAULT_PROCESSOR_ITERFACE)
            .implType(DEFAULT_IMPL_TYPE)
            .param01(DEFAULT_PARAM_01)
            .param02(DEFAULT_PARAM_02)
            .source(DEFAULT_SOURCE)
            .signerName(DEFAULT_SIGNER_NAME)
            .signature(DEFAULT_SIGNATURE)
            .signatureContentType(DEFAULT_SIGNATURE_CONTENT_TYPE);
        return processor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processor createUpdatedEntity(EntityManager em) {
        Processor processor = new Processor()
            .name(UPDATED_NAME)
            .example(UPDATED_EXAMPLE)
            .description(UPDATED_DESCRIPTION)
            .processorIterface(UPDATED_PROCESSOR_ITERFACE)
            .implType(UPDATED_IMPL_TYPE)
            .param01(UPDATED_PARAM_01)
            .param02(UPDATED_PARAM_02)
            .source(UPDATED_SOURCE)
            .signerName(UPDATED_SIGNER_NAME)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE);
        return processor;
    }

    @BeforeEach
    public void initTest() {
        processor = createEntity(em);
    }

    @Test
    @Transactional
    void createProcessor() throws Exception {
        int databaseSizeBeforeCreate = processorRepository.findAll().size();
        // Create the Processor
        ProcessorDTO processorDTO = processorMapper.toDto(processor);
        restProcessorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processorDTO)))
            .andExpect(status().isCreated());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeCreate + 1);
        Processor testProcessor = processorList.get(processorList.size() - 1);
        assertThat(testProcessor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProcessor.getExample()).isEqualTo(DEFAULT_EXAMPLE);
        assertThat(testProcessor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProcessor.getProcessorIterface()).isEqualTo(DEFAULT_PROCESSOR_ITERFACE);
        assertThat(testProcessor.getImplType()).isEqualTo(DEFAULT_IMPL_TYPE);
        assertThat(testProcessor.getParam01()).isEqualTo(DEFAULT_PARAM_01);
        assertThat(testProcessor.getParam02()).isEqualTo(DEFAULT_PARAM_02);
        assertThat(testProcessor.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testProcessor.getSignerName()).isEqualTo(DEFAULT_SIGNER_NAME);
        assertThat(testProcessor.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testProcessor.getSignatureContentType()).isEqualTo(DEFAULT_SIGNATURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createProcessorWithExistingId() throws Exception {
        // Create the Processor with an existing ID
        processor.setId(1L);
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        int databaseSizeBeforeCreate = processorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = processorRepository.findAll().size();
        // set the field null
        processor.setName(null);

        // Create the Processor, which fails.
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        restProcessorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processorDTO)))
            .andExpect(status().isBadRequest());

        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProcessorIterfaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = processorRepository.findAll().size();
        // set the field null
        processor.setProcessorIterface(null);

        // Create the Processor, which fails.
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        restProcessorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processorDTO)))
            .andExpect(status().isBadRequest());

        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImplTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = processorRepository.findAll().size();
        // set the field null
        processor.setImplType(null);

        // Create the Processor, which fails.
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        restProcessorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processorDTO)))
            .andExpect(status().isBadRequest());

        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProcessors() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList
        restProcessorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].example").value(hasItem(DEFAULT_EXAMPLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].processorIterface").value(hasItem(DEFAULT_PROCESSOR_ITERFACE.toString())))
            .andExpect(jsonPath("$.[*].implType").value(hasItem(DEFAULT_IMPL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].param01").value(hasItem(DEFAULT_PARAM_01)))
            .andExpect(jsonPath("$.[*].param02").value(hasItem(DEFAULT_PARAM_02)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].signerName").value(hasItem(DEFAULT_SIGNER_NAME)))
            .andExpect(jsonPath("$.[*].signatureContentType").value(hasItem(DEFAULT_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE))));
    }

    @Test
    @Transactional
    void getProcessor() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get the processor
        restProcessorMockMvc
            .perform(get(ENTITY_API_URL_ID, processor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.example").value(DEFAULT_EXAMPLE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.processorIterface").value(DEFAULT_PROCESSOR_ITERFACE.toString()))
            .andExpect(jsonPath("$.implType").value(DEFAULT_IMPL_TYPE.toString()))
            .andExpect(jsonPath("$.param01").value(DEFAULT_PARAM_01))
            .andExpect(jsonPath("$.param02").value(DEFAULT_PARAM_02))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.signerName").value(DEFAULT_SIGNER_NAME))
            .andExpect(jsonPath("$.signatureContentType").value(DEFAULT_SIGNATURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signature").value(Base64Utils.encodeToString(DEFAULT_SIGNATURE)));
    }

    @Test
    @Transactional
    void getProcessorsByIdFiltering() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        Long id = processor.getId();

        defaultProcessorShouldBeFound("id.equals=" + id);
        defaultProcessorShouldNotBeFound("id.notEquals=" + id);

        defaultProcessorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessorShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProcessorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where name equals to DEFAULT_NAME
        defaultProcessorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the processorList where name equals to UPDATED_NAME
        defaultProcessorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProcessorsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where name not equals to DEFAULT_NAME
        defaultProcessorShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the processorList where name not equals to UPDATED_NAME
        defaultProcessorShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProcessorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProcessorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the processorList where name equals to UPDATED_NAME
        defaultProcessorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProcessorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where name is not null
        defaultProcessorShouldBeFound("name.specified=true");

        // Get all the processorList where name is null
        defaultProcessorShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessorsByNameContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where name contains DEFAULT_NAME
        defaultProcessorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the processorList where name contains UPDATED_NAME
        defaultProcessorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProcessorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where name does not contain DEFAULT_NAME
        defaultProcessorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the processorList where name does not contain UPDATED_NAME
        defaultProcessorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProcessorsByExampleIsEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where example equals to DEFAULT_EXAMPLE
        defaultProcessorShouldBeFound("example.equals=" + DEFAULT_EXAMPLE);

        // Get all the processorList where example equals to UPDATED_EXAMPLE
        defaultProcessorShouldNotBeFound("example.equals=" + UPDATED_EXAMPLE);
    }

    @Test
    @Transactional
    void getAllProcessorsByExampleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where example not equals to DEFAULT_EXAMPLE
        defaultProcessorShouldNotBeFound("example.notEquals=" + DEFAULT_EXAMPLE);

        // Get all the processorList where example not equals to UPDATED_EXAMPLE
        defaultProcessorShouldBeFound("example.notEquals=" + UPDATED_EXAMPLE);
    }

    @Test
    @Transactional
    void getAllProcessorsByExampleIsInShouldWork() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where example in DEFAULT_EXAMPLE or UPDATED_EXAMPLE
        defaultProcessorShouldBeFound("example.in=" + DEFAULT_EXAMPLE + "," + UPDATED_EXAMPLE);

        // Get all the processorList where example equals to UPDATED_EXAMPLE
        defaultProcessorShouldNotBeFound("example.in=" + UPDATED_EXAMPLE);
    }

    @Test
    @Transactional
    void getAllProcessorsByExampleIsNullOrNotNull() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where example is not null
        defaultProcessorShouldBeFound("example.specified=true");

        // Get all the processorList where example is null
        defaultProcessorShouldNotBeFound("example.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessorsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where description equals to DEFAULT_DESCRIPTION
        defaultProcessorShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the processorList where description equals to UPDATED_DESCRIPTION
        defaultProcessorShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProcessorsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where description not equals to DEFAULT_DESCRIPTION
        defaultProcessorShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the processorList where description not equals to UPDATED_DESCRIPTION
        defaultProcessorShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProcessorsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProcessorShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the processorList where description equals to UPDATED_DESCRIPTION
        defaultProcessorShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProcessorsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where description is not null
        defaultProcessorShouldBeFound("description.specified=true");

        // Get all the processorList where description is null
        defaultProcessorShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessorsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where description contains DEFAULT_DESCRIPTION
        defaultProcessorShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the processorList where description contains UPDATED_DESCRIPTION
        defaultProcessorShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProcessorsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where description does not contain DEFAULT_DESCRIPTION
        defaultProcessorShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the processorList where description does not contain UPDATED_DESCRIPTION
        defaultProcessorShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProcessorsByProcessorIterfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where processorIterface equals to DEFAULT_PROCESSOR_ITERFACE
        defaultProcessorShouldBeFound("processorIterface.equals=" + DEFAULT_PROCESSOR_ITERFACE);

        // Get all the processorList where processorIterface equals to UPDATED_PROCESSOR_ITERFACE
        defaultProcessorShouldNotBeFound("processorIterface.equals=" + UPDATED_PROCESSOR_ITERFACE);
    }

    @Test
    @Transactional
    void getAllProcessorsByProcessorIterfaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where processorIterface not equals to DEFAULT_PROCESSOR_ITERFACE
        defaultProcessorShouldNotBeFound("processorIterface.notEquals=" + DEFAULT_PROCESSOR_ITERFACE);

        // Get all the processorList where processorIterface not equals to UPDATED_PROCESSOR_ITERFACE
        defaultProcessorShouldBeFound("processorIterface.notEquals=" + UPDATED_PROCESSOR_ITERFACE);
    }

    @Test
    @Transactional
    void getAllProcessorsByProcessorIterfaceIsInShouldWork() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where processorIterface in DEFAULT_PROCESSOR_ITERFACE or UPDATED_PROCESSOR_ITERFACE
        defaultProcessorShouldBeFound("processorIterface.in=" + DEFAULT_PROCESSOR_ITERFACE + "," + UPDATED_PROCESSOR_ITERFACE);

        // Get all the processorList where processorIterface equals to UPDATED_PROCESSOR_ITERFACE
        defaultProcessorShouldNotBeFound("processorIterface.in=" + UPDATED_PROCESSOR_ITERFACE);
    }

    @Test
    @Transactional
    void getAllProcessorsByProcessorIterfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where processorIterface is not null
        defaultProcessorShouldBeFound("processorIterface.specified=true");

        // Get all the processorList where processorIterface is null
        defaultProcessorShouldNotBeFound("processorIterface.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessorsByImplTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where implType equals to DEFAULT_IMPL_TYPE
        defaultProcessorShouldBeFound("implType.equals=" + DEFAULT_IMPL_TYPE);

        // Get all the processorList where implType equals to UPDATED_IMPL_TYPE
        defaultProcessorShouldNotBeFound("implType.equals=" + UPDATED_IMPL_TYPE);
    }

    @Test
    @Transactional
    void getAllProcessorsByImplTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where implType not equals to DEFAULT_IMPL_TYPE
        defaultProcessorShouldNotBeFound("implType.notEquals=" + DEFAULT_IMPL_TYPE);

        // Get all the processorList where implType not equals to UPDATED_IMPL_TYPE
        defaultProcessorShouldBeFound("implType.notEquals=" + UPDATED_IMPL_TYPE);
    }

    @Test
    @Transactional
    void getAllProcessorsByImplTypeIsInShouldWork() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where implType in DEFAULT_IMPL_TYPE or UPDATED_IMPL_TYPE
        defaultProcessorShouldBeFound("implType.in=" + DEFAULT_IMPL_TYPE + "," + UPDATED_IMPL_TYPE);

        // Get all the processorList where implType equals to UPDATED_IMPL_TYPE
        defaultProcessorShouldNotBeFound("implType.in=" + UPDATED_IMPL_TYPE);
    }

    @Test
    @Transactional
    void getAllProcessorsByImplTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where implType is not null
        defaultProcessorShouldBeFound("implType.specified=true");

        // Get all the processorList where implType is null
        defaultProcessorShouldNotBeFound("implType.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessorsByParam01IsEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param01 equals to DEFAULT_PARAM_01
        defaultProcessorShouldBeFound("param01.equals=" + DEFAULT_PARAM_01);

        // Get all the processorList where param01 equals to UPDATED_PARAM_01
        defaultProcessorShouldNotBeFound("param01.equals=" + UPDATED_PARAM_01);
    }

    @Test
    @Transactional
    void getAllProcessorsByParam01IsNotEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param01 not equals to DEFAULT_PARAM_01
        defaultProcessorShouldNotBeFound("param01.notEquals=" + DEFAULT_PARAM_01);

        // Get all the processorList where param01 not equals to UPDATED_PARAM_01
        defaultProcessorShouldBeFound("param01.notEquals=" + UPDATED_PARAM_01);
    }

    @Test
    @Transactional
    void getAllProcessorsByParam01IsInShouldWork() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param01 in DEFAULT_PARAM_01 or UPDATED_PARAM_01
        defaultProcessorShouldBeFound("param01.in=" + DEFAULT_PARAM_01 + "," + UPDATED_PARAM_01);

        // Get all the processorList where param01 equals to UPDATED_PARAM_01
        defaultProcessorShouldNotBeFound("param01.in=" + UPDATED_PARAM_01);
    }

    @Test
    @Transactional
    void getAllProcessorsByParam01IsNullOrNotNull() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param01 is not null
        defaultProcessorShouldBeFound("param01.specified=true");

        // Get all the processorList where param01 is null
        defaultProcessorShouldNotBeFound("param01.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessorsByParam01ContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param01 contains DEFAULT_PARAM_01
        defaultProcessorShouldBeFound("param01.contains=" + DEFAULT_PARAM_01);

        // Get all the processorList where param01 contains UPDATED_PARAM_01
        defaultProcessorShouldNotBeFound("param01.contains=" + UPDATED_PARAM_01);
    }

    @Test
    @Transactional
    void getAllProcessorsByParam01NotContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param01 does not contain DEFAULT_PARAM_01
        defaultProcessorShouldNotBeFound("param01.doesNotContain=" + DEFAULT_PARAM_01);

        // Get all the processorList where param01 does not contain UPDATED_PARAM_01
        defaultProcessorShouldBeFound("param01.doesNotContain=" + UPDATED_PARAM_01);
    }

    @Test
    @Transactional
    void getAllProcessorsByParam02IsEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param02 equals to DEFAULT_PARAM_02
        defaultProcessorShouldBeFound("param02.equals=" + DEFAULT_PARAM_02);

        // Get all the processorList where param02 equals to UPDATED_PARAM_02
        defaultProcessorShouldNotBeFound("param02.equals=" + UPDATED_PARAM_02);
    }

    @Test
    @Transactional
    void getAllProcessorsByParam02IsNotEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param02 not equals to DEFAULT_PARAM_02
        defaultProcessorShouldNotBeFound("param02.notEquals=" + DEFAULT_PARAM_02);

        // Get all the processorList where param02 not equals to UPDATED_PARAM_02
        defaultProcessorShouldBeFound("param02.notEquals=" + UPDATED_PARAM_02);
    }

    @Test
    @Transactional
    void getAllProcessorsByParam02IsInShouldWork() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param02 in DEFAULT_PARAM_02 or UPDATED_PARAM_02
        defaultProcessorShouldBeFound("param02.in=" + DEFAULT_PARAM_02 + "," + UPDATED_PARAM_02);

        // Get all the processorList where param02 equals to UPDATED_PARAM_02
        defaultProcessorShouldNotBeFound("param02.in=" + UPDATED_PARAM_02);
    }

    @Test
    @Transactional
    void getAllProcessorsByParam02IsNullOrNotNull() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param02 is not null
        defaultProcessorShouldBeFound("param02.specified=true");

        // Get all the processorList where param02 is null
        defaultProcessorShouldNotBeFound("param02.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessorsByParam02ContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param02 contains DEFAULT_PARAM_02
        defaultProcessorShouldBeFound("param02.contains=" + DEFAULT_PARAM_02);

        // Get all the processorList where param02 contains UPDATED_PARAM_02
        defaultProcessorShouldNotBeFound("param02.contains=" + UPDATED_PARAM_02);
    }

    @Test
    @Transactional
    void getAllProcessorsByParam02NotContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where param02 does not contain DEFAULT_PARAM_02
        defaultProcessorShouldNotBeFound("param02.doesNotContain=" + DEFAULT_PARAM_02);

        // Get all the processorList where param02 does not contain UPDATED_PARAM_02
        defaultProcessorShouldBeFound("param02.doesNotContain=" + UPDATED_PARAM_02);
    }

    @Test
    @Transactional
    void getAllProcessorsBySignerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where signerName equals to DEFAULT_SIGNER_NAME
        defaultProcessorShouldBeFound("signerName.equals=" + DEFAULT_SIGNER_NAME);

        // Get all the processorList where signerName equals to UPDATED_SIGNER_NAME
        defaultProcessorShouldNotBeFound("signerName.equals=" + UPDATED_SIGNER_NAME);
    }

    @Test
    @Transactional
    void getAllProcessorsBySignerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where signerName not equals to DEFAULT_SIGNER_NAME
        defaultProcessorShouldNotBeFound("signerName.notEquals=" + DEFAULT_SIGNER_NAME);

        // Get all the processorList where signerName not equals to UPDATED_SIGNER_NAME
        defaultProcessorShouldBeFound("signerName.notEquals=" + UPDATED_SIGNER_NAME);
    }

    @Test
    @Transactional
    void getAllProcessorsBySignerNameIsInShouldWork() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where signerName in DEFAULT_SIGNER_NAME or UPDATED_SIGNER_NAME
        defaultProcessorShouldBeFound("signerName.in=" + DEFAULT_SIGNER_NAME + "," + UPDATED_SIGNER_NAME);

        // Get all the processorList where signerName equals to UPDATED_SIGNER_NAME
        defaultProcessorShouldNotBeFound("signerName.in=" + UPDATED_SIGNER_NAME);
    }

    @Test
    @Transactional
    void getAllProcessorsBySignerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where signerName is not null
        defaultProcessorShouldBeFound("signerName.specified=true");

        // Get all the processorList where signerName is null
        defaultProcessorShouldNotBeFound("signerName.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessorsBySignerNameContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where signerName contains DEFAULT_SIGNER_NAME
        defaultProcessorShouldBeFound("signerName.contains=" + DEFAULT_SIGNER_NAME);

        // Get all the processorList where signerName contains UPDATED_SIGNER_NAME
        defaultProcessorShouldNotBeFound("signerName.contains=" + UPDATED_SIGNER_NAME);
    }

    @Test
    @Transactional
    void getAllProcessorsBySignerNameNotContainsSomething() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        // Get all the processorList where signerName does not contain DEFAULT_SIGNER_NAME
        defaultProcessorShouldNotBeFound("signerName.doesNotContain=" + DEFAULT_SIGNER_NAME);

        // Get all the processorList where signerName does not contain UPDATED_SIGNER_NAME
        defaultProcessorShouldBeFound("signerName.doesNotContain=" + UPDATED_SIGNER_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessorShouldBeFound(String filter) throws Exception {
        restProcessorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].example").value(hasItem(DEFAULT_EXAMPLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].processorIterface").value(hasItem(DEFAULT_PROCESSOR_ITERFACE.toString())))
            .andExpect(jsonPath("$.[*].implType").value(hasItem(DEFAULT_IMPL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].param01").value(hasItem(DEFAULT_PARAM_01)))
            .andExpect(jsonPath("$.[*].param02").value(hasItem(DEFAULT_PARAM_02)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].signerName").value(hasItem(DEFAULT_SIGNER_NAME)))
            .andExpect(jsonPath("$.[*].signatureContentType").value(hasItem(DEFAULT_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE))));

        // Check, that the count call also returns 1
        restProcessorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessorShouldNotBeFound(String filter) throws Exception {
        restProcessorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProcessor() throws Exception {
        // Get the processor
        restProcessorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProcessor() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        int databaseSizeBeforeUpdate = processorRepository.findAll().size();

        // Update the processor
        Processor updatedProcessor = processorRepository.findById(processor.getId()).get();
        // Disconnect from session so that the updates on updatedProcessor are not directly saved in db
        em.detach(updatedProcessor);
        updatedProcessor
            .name(UPDATED_NAME)
            .example(UPDATED_EXAMPLE)
            .description(UPDATED_DESCRIPTION)
            .processorIterface(UPDATED_PROCESSOR_ITERFACE)
            .implType(UPDATED_IMPL_TYPE)
            .param01(UPDATED_PARAM_01)
            .param02(UPDATED_PARAM_02)
            .source(UPDATED_SOURCE)
            .signerName(UPDATED_SIGNER_NAME)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE);
        ProcessorDTO processorDTO = processorMapper.toDto(updatedProcessor);

        restProcessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeUpdate);
        Processor testProcessor = processorList.get(processorList.size() - 1);
        assertThat(testProcessor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcessor.getExample()).isEqualTo(UPDATED_EXAMPLE);
        assertThat(testProcessor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProcessor.getProcessorIterface()).isEqualTo(UPDATED_PROCESSOR_ITERFACE);
        assertThat(testProcessor.getImplType()).isEqualTo(UPDATED_IMPL_TYPE);
        assertThat(testProcessor.getParam01()).isEqualTo(UPDATED_PARAM_01);
        assertThat(testProcessor.getParam02()).isEqualTo(UPDATED_PARAM_02);
        assertThat(testProcessor.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testProcessor.getSignerName()).isEqualTo(UPDATED_SIGNER_NAME);
        assertThat(testProcessor.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testProcessor.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProcessor() throws Exception {
        int databaseSizeBeforeUpdate = processorRepository.findAll().size();
        processor.setId(count.incrementAndGet());

        // Create the Processor
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcessor() throws Exception {
        int databaseSizeBeforeUpdate = processorRepository.findAll().size();
        processor.setId(count.incrementAndGet());

        // Create the Processor
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcessor() throws Exception {
        int databaseSizeBeforeUpdate = processorRepository.findAll().size();
        processor.setId(count.incrementAndGet());

        // Create the Processor
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessorWithPatch() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        int databaseSizeBeforeUpdate = processorRepository.findAll().size();

        // Update the processor using partial update
        Processor partialUpdatedProcessor = new Processor();
        partialUpdatedProcessor.setId(processor.getId());

        partialUpdatedProcessor
            .name(UPDATED_NAME)
            .example(UPDATED_EXAMPLE)
            .description(UPDATED_DESCRIPTION)
            .implType(UPDATED_IMPL_TYPE)
            .source(UPDATED_SOURCE);

        restProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessor))
            )
            .andExpect(status().isOk());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeUpdate);
        Processor testProcessor = processorList.get(processorList.size() - 1);
        assertThat(testProcessor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcessor.getExample()).isEqualTo(UPDATED_EXAMPLE);
        assertThat(testProcessor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProcessor.getProcessorIterface()).isEqualTo(DEFAULT_PROCESSOR_ITERFACE);
        assertThat(testProcessor.getImplType()).isEqualTo(UPDATED_IMPL_TYPE);
        assertThat(testProcessor.getParam01()).isEqualTo(DEFAULT_PARAM_01);
        assertThat(testProcessor.getParam02()).isEqualTo(DEFAULT_PARAM_02);
        assertThat(testProcessor.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testProcessor.getSignerName()).isEqualTo(DEFAULT_SIGNER_NAME);
        assertThat(testProcessor.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testProcessor.getSignatureContentType()).isEqualTo(DEFAULT_SIGNATURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateProcessorWithPatch() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        int databaseSizeBeforeUpdate = processorRepository.findAll().size();

        // Update the processor using partial update
        Processor partialUpdatedProcessor = new Processor();
        partialUpdatedProcessor.setId(processor.getId());

        partialUpdatedProcessor
            .name(UPDATED_NAME)
            .example(UPDATED_EXAMPLE)
            .description(UPDATED_DESCRIPTION)
            .processorIterface(UPDATED_PROCESSOR_ITERFACE)
            .implType(UPDATED_IMPL_TYPE)
            .param01(UPDATED_PARAM_01)
            .param02(UPDATED_PARAM_02)
            .source(UPDATED_SOURCE)
            .signerName(UPDATED_SIGNER_NAME)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE);

        restProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessor))
            )
            .andExpect(status().isOk());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeUpdate);
        Processor testProcessor = processorList.get(processorList.size() - 1);
        assertThat(testProcessor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcessor.getExample()).isEqualTo(UPDATED_EXAMPLE);
        assertThat(testProcessor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProcessor.getProcessorIterface()).isEqualTo(UPDATED_PROCESSOR_ITERFACE);
        assertThat(testProcessor.getImplType()).isEqualTo(UPDATED_IMPL_TYPE);
        assertThat(testProcessor.getParam01()).isEqualTo(UPDATED_PARAM_01);
        assertThat(testProcessor.getParam02()).isEqualTo(UPDATED_PARAM_02);
        assertThat(testProcessor.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testProcessor.getSignerName()).isEqualTo(UPDATED_SIGNER_NAME);
        assertThat(testProcessor.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testProcessor.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProcessor() throws Exception {
        int databaseSizeBeforeUpdate = processorRepository.findAll().size();
        processor.setId(count.incrementAndGet());

        // Create the Processor
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, processorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcessor() throws Exception {
        int databaseSizeBeforeUpdate = processorRepository.findAll().size();
        processor.setId(count.incrementAndGet());

        // Create the Processor
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcessor() throws Exception {
        int databaseSizeBeforeUpdate = processorRepository.findAll().size();
        processor.setId(count.incrementAndGet());

        // Create the Processor
        ProcessorDTO processorDTO = processorMapper.toDto(processor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(processorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Processor in the database
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcessor() throws Exception {
        // Initialize the database
        processorRepository.saveAndFlush(processor);

        int databaseSizeBeforeDelete = processorRepository.findAll().size();

        // Delete the processor
        restProcessorMockMvc
            .perform(delete(ENTITY_API_URL_ID, processor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Processor> processorList = processorRepository.findAll();
        assertThat(processorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
