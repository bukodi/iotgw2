package hu.noreg.iotgw2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hu.noreg.iotgw2.IntegrationTest;
import hu.noreg.iotgw2.domain.DeviceType;
import hu.noreg.iotgw2.domain.MessageType;
import hu.noreg.iotgw2.domain.Processor;
import hu.noreg.iotgw2.repository.DeviceTypeRepository;
import hu.noreg.iotgw2.service.DeviceTypeService;
import hu.noreg.iotgw2.service.criteria.DeviceTypeCriteria;
import hu.noreg.iotgw2.service.dto.DeviceTypeDTO;
import hu.noreg.iotgw2.service.mapper.DeviceTypeMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeviceTypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DeviceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_FIELD_NAME_01 = "AAAAAAAAAA";
    private static final String UPDATED_STATE_FIELD_NAME_01 = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_FIELD_NAME_02 = "AAAAAAAAAA";
    private static final String UPDATED_STATE_FIELD_NAME_02 = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_FIELD_NAME_03 = "AAAAAAAAAA";
    private static final String UPDATED_STATE_FIELD_NAME_03 = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_FIELD_NAME_04 = "AAAAAAAAAA";
    private static final String UPDATED_STATE_FIELD_NAME_04 = "BBBBBBBBBB";

    private static final String DEFAULT_READ_AUTH_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_READ_AUTH_PATTERN = "BBBBBBBBBB";

    private static final String DEFAULT_WRITE_AUTH_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_WRITE_AUTH_PATTERN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/device-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Mock
    private DeviceTypeRepository deviceTypeRepositoryMock;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Mock
    private DeviceTypeService deviceTypeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceTypeMockMvc;

    private DeviceType deviceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceType createEntity(EntityManager em) {
        DeviceType deviceType = new DeviceType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .stateFieldName01(DEFAULT_STATE_FIELD_NAME_01)
            .stateFieldName02(DEFAULT_STATE_FIELD_NAME_02)
            .stateFieldName03(DEFAULT_STATE_FIELD_NAME_03)
            .stateFieldName04(DEFAULT_STATE_FIELD_NAME_04)
            .readAuthPattern(DEFAULT_READ_AUTH_PATTERN)
            .writeAuthPattern(DEFAULT_WRITE_AUTH_PATTERN);
        return deviceType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceType createUpdatedEntity(EntityManager em) {
        DeviceType deviceType = new DeviceType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .stateFieldName01(UPDATED_STATE_FIELD_NAME_01)
            .stateFieldName02(UPDATED_STATE_FIELD_NAME_02)
            .stateFieldName03(UPDATED_STATE_FIELD_NAME_03)
            .stateFieldName04(UPDATED_STATE_FIELD_NAME_04)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);
        return deviceType;
    }

    @BeforeEach
    public void initTest() {
        deviceType = createEntity(em);
    }

    @Test
    @Transactional
    void createDeviceType() throws Exception {
        int databaseSizeBeforeCreate = deviceTypeRepository.findAll().size();
        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);
        restDeviceTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceType testDeviceType = deviceTypeList.get(deviceTypeList.size() - 1);
        assertThat(testDeviceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeviceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeviceType.getStateFieldName01()).isEqualTo(DEFAULT_STATE_FIELD_NAME_01);
        assertThat(testDeviceType.getStateFieldName02()).isEqualTo(DEFAULT_STATE_FIELD_NAME_02);
        assertThat(testDeviceType.getStateFieldName03()).isEqualTo(DEFAULT_STATE_FIELD_NAME_03);
        assertThat(testDeviceType.getStateFieldName04()).isEqualTo(DEFAULT_STATE_FIELD_NAME_04);
        assertThat(testDeviceType.getReadAuthPattern()).isEqualTo(DEFAULT_READ_AUTH_PATTERN);
        assertThat(testDeviceType.getWriteAuthPattern()).isEqualTo(DEFAULT_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void createDeviceTypeWithExistingId() throws Exception {
        // Create the DeviceType with an existing ID
        deviceType.setId(1L);
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        int databaseSizeBeforeCreate = deviceTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceTypeRepository.findAll().size();
        // set the field null
        deviceType.setName(null);

        // Create the DeviceType, which fails.
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        restDeviceTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDeviceTypes() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList
        restDeviceTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].stateFieldName01").value(hasItem(DEFAULT_STATE_FIELD_NAME_01)))
            .andExpect(jsonPath("$.[*].stateFieldName02").value(hasItem(DEFAULT_STATE_FIELD_NAME_02)))
            .andExpect(jsonPath("$.[*].stateFieldName03").value(hasItem(DEFAULT_STATE_FIELD_NAME_03)))
            .andExpect(jsonPath("$.[*].stateFieldName04").value(hasItem(DEFAULT_STATE_FIELD_NAME_04)))
            .andExpect(jsonPath("$.[*].readAuthPattern").value(hasItem(DEFAULT_READ_AUTH_PATTERN)))
            .andExpect(jsonPath("$.[*].writeAuthPattern").value(hasItem(DEFAULT_WRITE_AUTH_PATTERN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDeviceTypesWithEagerRelationshipsIsEnabled() throws Exception {
        when(deviceTypeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeviceTypeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(deviceTypeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDeviceTypesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(deviceTypeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeviceTypeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(deviceTypeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDeviceType() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get the deviceType
        restDeviceTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, deviceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.stateFieldName01").value(DEFAULT_STATE_FIELD_NAME_01))
            .andExpect(jsonPath("$.stateFieldName02").value(DEFAULT_STATE_FIELD_NAME_02))
            .andExpect(jsonPath("$.stateFieldName03").value(DEFAULT_STATE_FIELD_NAME_03))
            .andExpect(jsonPath("$.stateFieldName04").value(DEFAULT_STATE_FIELD_NAME_04))
            .andExpect(jsonPath("$.readAuthPattern").value(DEFAULT_READ_AUTH_PATTERN))
            .andExpect(jsonPath("$.writeAuthPattern").value(DEFAULT_WRITE_AUTH_PATTERN));
    }

    @Test
    @Transactional
    void getDeviceTypesByIdFiltering() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        Long id = deviceType.getId();

        defaultDeviceTypeShouldBeFound("id.equals=" + id);
        defaultDeviceTypeShouldNotBeFound("id.notEquals=" + id);

        defaultDeviceTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeviceTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultDeviceTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeviceTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name equals to DEFAULT_NAME
        defaultDeviceTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the deviceTypeList where name equals to UPDATED_NAME
        defaultDeviceTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name not equals to DEFAULT_NAME
        defaultDeviceTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the deviceTypeList where name not equals to UPDATED_NAME
        defaultDeviceTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDeviceTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the deviceTypeList where name equals to UPDATED_NAME
        defaultDeviceTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name is not null
        defaultDeviceTypeShouldBeFound("name.specified=true");

        // Get all the deviceTypeList where name is null
        defaultDeviceTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDeviceTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name contains DEFAULT_NAME
        defaultDeviceTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the deviceTypeList where name contains UPDATED_NAME
        defaultDeviceTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name does not contain DEFAULT_NAME
        defaultDeviceTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the deviceTypeList where name does not contain UPDATED_NAME
        defaultDeviceTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where description equals to DEFAULT_DESCRIPTION
        defaultDeviceTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the deviceTypeList where description equals to UPDATED_DESCRIPTION
        defaultDeviceTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultDeviceTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the deviceTypeList where description not equals to UPDATED_DESCRIPTION
        defaultDeviceTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDeviceTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the deviceTypeList where description equals to UPDATED_DESCRIPTION
        defaultDeviceTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where description is not null
        defaultDeviceTypeShouldBeFound("description.specified=true");

        // Get all the deviceTypeList where description is null
        defaultDeviceTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllDeviceTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where description contains DEFAULT_DESCRIPTION
        defaultDeviceTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the deviceTypeList where description contains UPDATED_DESCRIPTION
        defaultDeviceTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultDeviceTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the deviceTypeList where description does not contain UPDATED_DESCRIPTION
        defaultDeviceTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName01IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName01 equals to DEFAULT_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldBeFound("stateFieldName01.equals=" + DEFAULT_STATE_FIELD_NAME_01);

        // Get all the deviceTypeList where stateFieldName01 equals to UPDATED_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldNotBeFound("stateFieldName01.equals=" + UPDATED_STATE_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName01IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName01 not equals to DEFAULT_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldNotBeFound("stateFieldName01.notEquals=" + DEFAULT_STATE_FIELD_NAME_01);

        // Get all the deviceTypeList where stateFieldName01 not equals to UPDATED_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldBeFound("stateFieldName01.notEquals=" + UPDATED_STATE_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName01IsInShouldWork() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName01 in DEFAULT_STATE_FIELD_NAME_01 or UPDATED_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldBeFound("stateFieldName01.in=" + DEFAULT_STATE_FIELD_NAME_01 + "," + UPDATED_STATE_FIELD_NAME_01);

        // Get all the deviceTypeList where stateFieldName01 equals to UPDATED_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldNotBeFound("stateFieldName01.in=" + UPDATED_STATE_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName01IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName01 is not null
        defaultDeviceTypeShouldBeFound("stateFieldName01.specified=true");

        // Get all the deviceTypeList where stateFieldName01 is null
        defaultDeviceTypeShouldNotBeFound("stateFieldName01.specified=false");
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName01ContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName01 contains DEFAULT_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldBeFound("stateFieldName01.contains=" + DEFAULT_STATE_FIELD_NAME_01);

        // Get all the deviceTypeList where stateFieldName01 contains UPDATED_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldNotBeFound("stateFieldName01.contains=" + UPDATED_STATE_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName01NotContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName01 does not contain DEFAULT_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldNotBeFound("stateFieldName01.doesNotContain=" + DEFAULT_STATE_FIELD_NAME_01);

        // Get all the deviceTypeList where stateFieldName01 does not contain UPDATED_STATE_FIELD_NAME_01
        defaultDeviceTypeShouldBeFound("stateFieldName01.doesNotContain=" + UPDATED_STATE_FIELD_NAME_01);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName02IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName02 equals to DEFAULT_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldBeFound("stateFieldName02.equals=" + DEFAULT_STATE_FIELD_NAME_02);

        // Get all the deviceTypeList where stateFieldName02 equals to UPDATED_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldNotBeFound("stateFieldName02.equals=" + UPDATED_STATE_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName02IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName02 not equals to DEFAULT_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldNotBeFound("stateFieldName02.notEquals=" + DEFAULT_STATE_FIELD_NAME_02);

        // Get all the deviceTypeList where stateFieldName02 not equals to UPDATED_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldBeFound("stateFieldName02.notEquals=" + UPDATED_STATE_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName02IsInShouldWork() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName02 in DEFAULT_STATE_FIELD_NAME_02 or UPDATED_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldBeFound("stateFieldName02.in=" + DEFAULT_STATE_FIELD_NAME_02 + "," + UPDATED_STATE_FIELD_NAME_02);

        // Get all the deviceTypeList where stateFieldName02 equals to UPDATED_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldNotBeFound("stateFieldName02.in=" + UPDATED_STATE_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName02IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName02 is not null
        defaultDeviceTypeShouldBeFound("stateFieldName02.specified=true");

        // Get all the deviceTypeList where stateFieldName02 is null
        defaultDeviceTypeShouldNotBeFound("stateFieldName02.specified=false");
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName02ContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName02 contains DEFAULT_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldBeFound("stateFieldName02.contains=" + DEFAULT_STATE_FIELD_NAME_02);

        // Get all the deviceTypeList where stateFieldName02 contains UPDATED_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldNotBeFound("stateFieldName02.contains=" + UPDATED_STATE_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName02NotContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName02 does not contain DEFAULT_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldNotBeFound("stateFieldName02.doesNotContain=" + DEFAULT_STATE_FIELD_NAME_02);

        // Get all the deviceTypeList where stateFieldName02 does not contain UPDATED_STATE_FIELD_NAME_02
        defaultDeviceTypeShouldBeFound("stateFieldName02.doesNotContain=" + UPDATED_STATE_FIELD_NAME_02);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName03IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName03 equals to DEFAULT_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldBeFound("stateFieldName03.equals=" + DEFAULT_STATE_FIELD_NAME_03);

        // Get all the deviceTypeList where stateFieldName03 equals to UPDATED_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldNotBeFound("stateFieldName03.equals=" + UPDATED_STATE_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName03IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName03 not equals to DEFAULT_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldNotBeFound("stateFieldName03.notEquals=" + DEFAULT_STATE_FIELD_NAME_03);

        // Get all the deviceTypeList where stateFieldName03 not equals to UPDATED_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldBeFound("stateFieldName03.notEquals=" + UPDATED_STATE_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName03IsInShouldWork() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName03 in DEFAULT_STATE_FIELD_NAME_03 or UPDATED_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldBeFound("stateFieldName03.in=" + DEFAULT_STATE_FIELD_NAME_03 + "," + UPDATED_STATE_FIELD_NAME_03);

        // Get all the deviceTypeList where stateFieldName03 equals to UPDATED_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldNotBeFound("stateFieldName03.in=" + UPDATED_STATE_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName03IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName03 is not null
        defaultDeviceTypeShouldBeFound("stateFieldName03.specified=true");

        // Get all the deviceTypeList where stateFieldName03 is null
        defaultDeviceTypeShouldNotBeFound("stateFieldName03.specified=false");
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName03ContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName03 contains DEFAULT_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldBeFound("stateFieldName03.contains=" + DEFAULT_STATE_FIELD_NAME_03);

        // Get all the deviceTypeList where stateFieldName03 contains UPDATED_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldNotBeFound("stateFieldName03.contains=" + UPDATED_STATE_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName03NotContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName03 does not contain DEFAULT_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldNotBeFound("stateFieldName03.doesNotContain=" + DEFAULT_STATE_FIELD_NAME_03);

        // Get all the deviceTypeList where stateFieldName03 does not contain UPDATED_STATE_FIELD_NAME_03
        defaultDeviceTypeShouldBeFound("stateFieldName03.doesNotContain=" + UPDATED_STATE_FIELD_NAME_03);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName04IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName04 equals to DEFAULT_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldBeFound("stateFieldName04.equals=" + DEFAULT_STATE_FIELD_NAME_04);

        // Get all the deviceTypeList where stateFieldName04 equals to UPDATED_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldNotBeFound("stateFieldName04.equals=" + UPDATED_STATE_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName04IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName04 not equals to DEFAULT_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldNotBeFound("stateFieldName04.notEquals=" + DEFAULT_STATE_FIELD_NAME_04);

        // Get all the deviceTypeList where stateFieldName04 not equals to UPDATED_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldBeFound("stateFieldName04.notEquals=" + UPDATED_STATE_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName04IsInShouldWork() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName04 in DEFAULT_STATE_FIELD_NAME_04 or UPDATED_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldBeFound("stateFieldName04.in=" + DEFAULT_STATE_FIELD_NAME_04 + "," + UPDATED_STATE_FIELD_NAME_04);

        // Get all the deviceTypeList where stateFieldName04 equals to UPDATED_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldNotBeFound("stateFieldName04.in=" + UPDATED_STATE_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName04IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName04 is not null
        defaultDeviceTypeShouldBeFound("stateFieldName04.specified=true");

        // Get all the deviceTypeList where stateFieldName04 is null
        defaultDeviceTypeShouldNotBeFound("stateFieldName04.specified=false");
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName04ContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName04 contains DEFAULT_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldBeFound("stateFieldName04.contains=" + DEFAULT_STATE_FIELD_NAME_04);

        // Get all the deviceTypeList where stateFieldName04 contains UPDATED_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldNotBeFound("stateFieldName04.contains=" + UPDATED_STATE_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByStateFieldName04NotContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where stateFieldName04 does not contain DEFAULT_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldNotBeFound("stateFieldName04.doesNotContain=" + DEFAULT_STATE_FIELD_NAME_04);

        // Get all the deviceTypeList where stateFieldName04 does not contain UPDATED_STATE_FIELD_NAME_04
        defaultDeviceTypeShouldBeFound("stateFieldName04.doesNotContain=" + UPDATED_STATE_FIELD_NAME_04);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByReadAuthPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where readAuthPattern equals to DEFAULT_READ_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("readAuthPattern.equals=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the deviceTypeList where readAuthPattern equals to UPDATED_READ_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("readAuthPattern.equals=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByReadAuthPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where readAuthPattern not equals to DEFAULT_READ_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("readAuthPattern.notEquals=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the deviceTypeList where readAuthPattern not equals to UPDATED_READ_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("readAuthPattern.notEquals=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByReadAuthPatternIsInShouldWork() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where readAuthPattern in DEFAULT_READ_AUTH_PATTERN or UPDATED_READ_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("readAuthPattern.in=" + DEFAULT_READ_AUTH_PATTERN + "," + UPDATED_READ_AUTH_PATTERN);

        // Get all the deviceTypeList where readAuthPattern equals to UPDATED_READ_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("readAuthPattern.in=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByReadAuthPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where readAuthPattern is not null
        defaultDeviceTypeShouldBeFound("readAuthPattern.specified=true");

        // Get all the deviceTypeList where readAuthPattern is null
        defaultDeviceTypeShouldNotBeFound("readAuthPattern.specified=false");
    }

    @Test
    @Transactional
    void getAllDeviceTypesByReadAuthPatternContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where readAuthPattern contains DEFAULT_READ_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("readAuthPattern.contains=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the deviceTypeList where readAuthPattern contains UPDATED_READ_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("readAuthPattern.contains=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByReadAuthPatternNotContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where readAuthPattern does not contain DEFAULT_READ_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("readAuthPattern.doesNotContain=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the deviceTypeList where readAuthPattern does not contain UPDATED_READ_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("readAuthPattern.doesNotContain=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByWriteAuthPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where writeAuthPattern equals to DEFAULT_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("writeAuthPattern.equals=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the deviceTypeList where writeAuthPattern equals to UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("writeAuthPattern.equals=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByWriteAuthPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where writeAuthPattern not equals to DEFAULT_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("writeAuthPattern.notEquals=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the deviceTypeList where writeAuthPattern not equals to UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("writeAuthPattern.notEquals=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByWriteAuthPatternIsInShouldWork() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where writeAuthPattern in DEFAULT_WRITE_AUTH_PATTERN or UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("writeAuthPattern.in=" + DEFAULT_WRITE_AUTH_PATTERN + "," + UPDATED_WRITE_AUTH_PATTERN);

        // Get all the deviceTypeList where writeAuthPattern equals to UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("writeAuthPattern.in=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByWriteAuthPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where writeAuthPattern is not null
        defaultDeviceTypeShouldBeFound("writeAuthPattern.specified=true");

        // Get all the deviceTypeList where writeAuthPattern is null
        defaultDeviceTypeShouldNotBeFound("writeAuthPattern.specified=false");
    }

    @Test
    @Transactional
    void getAllDeviceTypesByWriteAuthPatternContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where writeAuthPattern contains DEFAULT_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("writeAuthPattern.contains=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the deviceTypeList where writeAuthPattern contains UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("writeAuthPattern.contains=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByWriteAuthPatternNotContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where writeAuthPattern does not contain DEFAULT_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldNotBeFound("writeAuthPattern.doesNotContain=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the deviceTypeList where writeAuthPattern does not contain UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceTypeShouldBeFound("writeAuthPattern.doesNotContain=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDeviceTypesByEnrollProcessorIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);
        Processor enrollProcessor = ProcessorResourceIT.createEntity(em);
        em.persist(enrollProcessor);
        em.flush();
        deviceType.setEnrollProcessor(enrollProcessor);
        deviceTypeRepository.saveAndFlush(deviceType);
        Long enrollProcessorId = enrollProcessor.getId();

        // Get all the deviceTypeList where enrollProcessor equals to enrollProcessorId
        defaultDeviceTypeShouldBeFound("enrollProcessorId.equals=" + enrollProcessorId);

        // Get all the deviceTypeList where enrollProcessor equals to (enrollProcessorId + 1)
        defaultDeviceTypeShouldNotBeFound("enrollProcessorId.equals=" + (enrollProcessorId + 1));
    }

    @Test
    @Transactional
    void getAllDeviceTypesByMessageTypesIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);
        MessageType messageTypes = MessageTypeResourceIT.createEntity(em);
        em.persist(messageTypes);
        em.flush();
        deviceType.addMessageTypes(messageTypes);
        deviceTypeRepository.saveAndFlush(deviceType);
        Long messageTypesId = messageTypes.getId();

        // Get all the deviceTypeList where messageTypes equals to messageTypesId
        defaultDeviceTypeShouldBeFound("messageTypesId.equals=" + messageTypesId);

        // Get all the deviceTypeList where messageTypes equals to (messageTypesId + 1)
        defaultDeviceTypeShouldNotBeFound("messageTypesId.equals=" + (messageTypesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeviceTypeShouldBeFound(String filter) throws Exception {
        restDeviceTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].stateFieldName01").value(hasItem(DEFAULT_STATE_FIELD_NAME_01)))
            .andExpect(jsonPath("$.[*].stateFieldName02").value(hasItem(DEFAULT_STATE_FIELD_NAME_02)))
            .andExpect(jsonPath("$.[*].stateFieldName03").value(hasItem(DEFAULT_STATE_FIELD_NAME_03)))
            .andExpect(jsonPath("$.[*].stateFieldName04").value(hasItem(DEFAULT_STATE_FIELD_NAME_04)))
            .andExpect(jsonPath("$.[*].readAuthPattern").value(hasItem(DEFAULT_READ_AUTH_PATTERN)))
            .andExpect(jsonPath("$.[*].writeAuthPattern").value(hasItem(DEFAULT_WRITE_AUTH_PATTERN)));

        // Check, that the count call also returns 1
        restDeviceTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeviceTypeShouldNotBeFound(String filter) throws Exception {
        restDeviceTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeviceTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDeviceType() throws Exception {
        // Get the deviceType
        restDeviceTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeviceType() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();

        // Update the deviceType
        DeviceType updatedDeviceType = deviceTypeRepository.findById(deviceType.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceType are not directly saved in db
        em.detach(updatedDeviceType);
        updatedDeviceType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .stateFieldName01(UPDATED_STATE_FIELD_NAME_01)
            .stateFieldName02(UPDATED_STATE_FIELD_NAME_02)
            .stateFieldName03(UPDATED_STATE_FIELD_NAME_03)
            .stateFieldName04(UPDATED_STATE_FIELD_NAME_04)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(updatedDeviceType);

        restDeviceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
        DeviceType testDeviceType = deviceTypeList.get(deviceTypeList.size() - 1);
        assertThat(testDeviceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeviceType.getStateFieldName01()).isEqualTo(UPDATED_STATE_FIELD_NAME_01);
        assertThat(testDeviceType.getStateFieldName02()).isEqualTo(UPDATED_STATE_FIELD_NAME_02);
        assertThat(testDeviceType.getStateFieldName03()).isEqualTo(UPDATED_STATE_FIELD_NAME_03);
        assertThat(testDeviceType.getStateFieldName04()).isEqualTo(UPDATED_STATE_FIELD_NAME_04);
        assertThat(testDeviceType.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testDeviceType.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void putNonExistingDeviceType() throws Exception {
        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();
        deviceType.setId(count.incrementAndGet());

        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeviceType() throws Exception {
        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();
        deviceType.setId(count.incrementAndGet());

        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeviceType() throws Exception {
        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();
        deviceType.setId(count.incrementAndGet());

        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeviceTypeWithPatch() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();

        // Update the deviceType using partial update
        DeviceType partialUpdatedDeviceType = new DeviceType();
        partialUpdatedDeviceType.setId(deviceType.getId());

        partialUpdatedDeviceType
            .stateFieldName01(UPDATED_STATE_FIELD_NAME_01)
            .stateFieldName03(UPDATED_STATE_FIELD_NAME_03)
            .stateFieldName04(UPDATED_STATE_FIELD_NAME_04)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);

        restDeviceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceType))
            )
            .andExpect(status().isOk());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
        DeviceType testDeviceType = deviceTypeList.get(deviceTypeList.size() - 1);
        assertThat(testDeviceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeviceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeviceType.getStateFieldName01()).isEqualTo(UPDATED_STATE_FIELD_NAME_01);
        assertThat(testDeviceType.getStateFieldName02()).isEqualTo(DEFAULT_STATE_FIELD_NAME_02);
        assertThat(testDeviceType.getStateFieldName03()).isEqualTo(UPDATED_STATE_FIELD_NAME_03);
        assertThat(testDeviceType.getStateFieldName04()).isEqualTo(UPDATED_STATE_FIELD_NAME_04);
        assertThat(testDeviceType.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testDeviceType.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void fullUpdateDeviceTypeWithPatch() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();

        // Update the deviceType using partial update
        DeviceType partialUpdatedDeviceType = new DeviceType();
        partialUpdatedDeviceType.setId(deviceType.getId());

        partialUpdatedDeviceType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .stateFieldName01(UPDATED_STATE_FIELD_NAME_01)
            .stateFieldName02(UPDATED_STATE_FIELD_NAME_02)
            .stateFieldName03(UPDATED_STATE_FIELD_NAME_03)
            .stateFieldName04(UPDATED_STATE_FIELD_NAME_04)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);

        restDeviceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceType))
            )
            .andExpect(status().isOk());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
        DeviceType testDeviceType = deviceTypeList.get(deviceTypeList.size() - 1);
        assertThat(testDeviceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeviceType.getStateFieldName01()).isEqualTo(UPDATED_STATE_FIELD_NAME_01);
        assertThat(testDeviceType.getStateFieldName02()).isEqualTo(UPDATED_STATE_FIELD_NAME_02);
        assertThat(testDeviceType.getStateFieldName03()).isEqualTo(UPDATED_STATE_FIELD_NAME_03);
        assertThat(testDeviceType.getStateFieldName04()).isEqualTo(UPDATED_STATE_FIELD_NAME_04);
        assertThat(testDeviceType.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testDeviceType.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void patchNonExistingDeviceType() throws Exception {
        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();
        deviceType.setId(count.incrementAndGet());

        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deviceTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeviceType() throws Exception {
        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();
        deviceType.setId(count.incrementAndGet());

        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeviceType() throws Exception {
        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();
        deviceType.setId(count.incrementAndGet());

        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeviceType() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        int databaseSizeBeforeDelete = deviceTypeRepository.findAll().size();

        // Delete the deviceType
        restDeviceTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, deviceType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
