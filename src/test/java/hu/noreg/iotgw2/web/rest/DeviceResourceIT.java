package hu.noreg.iotgw2.web.rest;

import static hu.noreg.iotgw2.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hu.noreg.iotgw2.IntegrationTest;
import hu.noreg.iotgw2.domain.Device;
import hu.noreg.iotgw2.domain.DeviceType;
import hu.noreg.iotgw2.domain.KeyPair;
import hu.noreg.iotgw2.domain.OrgUnit;
import hu.noreg.iotgw2.repository.DeviceRepository;
import hu.noreg.iotgw2.service.criteria.DeviceCriteria;
import hu.noreg.iotgw2.service.dto.DeviceDTO;
import hu.noreg.iotgw2.service.mapper.DeviceMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link DeviceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeviceResourceIT {

    private static final String DEFAULT_VISUAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_VISUAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ENROLLMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ENROLLMENT_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ENROLLMENT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENROLLMENT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ENROLLMENT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_STATE_FIELD_VALUE_01 = "AAAAAAAAAA";
    private static final String UPDATED_STATE_FIELD_VALUE_01 = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_FIELD_VALUE_02 = "AAAAAAAAAA";
    private static final String UPDATED_STATE_FIELD_VALUE_02 = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_FIELD_VALUE_03 = "AAAAAAAAAA";
    private static final String UPDATED_STATE_FIELD_VALUE_03 = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_FIELD_VALUE_04 = "AAAAAAAAAA";
    private static final String UPDATED_STATE_FIELD_VALUE_04 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_LAST_ERROR = "BBBBBBBBBB";

    private static final String DEFAULT_READ_AUTH_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_READ_AUTH_PATTERN = "BBBBBBBBBB";

    private static final String DEFAULT_WRITE_AUTH_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_WRITE_AUTH_PATTERN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/devices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceMockMvc;

    private Device device;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createEntity(EntityManager em) {
        Device device = new Device()
            .visualId(DEFAULT_VISUAL_ID)
            .description(DEFAULT_DESCRIPTION)
            .enrollmentCode(DEFAULT_ENROLLMENT_CODE)
            .enrollmentTime(DEFAULT_ENROLLMENT_TIME)
            .stateFieldValue01(DEFAULT_STATE_FIELD_VALUE_01)
            .stateFieldValue02(DEFAULT_STATE_FIELD_VALUE_02)
            .stateFieldValue03(DEFAULT_STATE_FIELD_VALUE_03)
            .stateFieldValue04(DEFAULT_STATE_FIELD_VALUE_04)
            .lastError(DEFAULT_LAST_ERROR)
            .readAuthPattern(DEFAULT_READ_AUTH_PATTERN)
            .writeAuthPattern(DEFAULT_WRITE_AUTH_PATTERN);
        // Add required entity
        DeviceType deviceType;
        if (TestUtil.findAll(em, DeviceType.class).isEmpty()) {
            deviceType = DeviceTypeResourceIT.createEntity(em);
            em.persist(deviceType);
            em.flush();
        } else {
            deviceType = TestUtil.findAll(em, DeviceType.class).get(0);
        }
        device.setType(deviceType);
        // Add required entity
        OrgUnit orgUnit;
        if (TestUtil.findAll(em, OrgUnit.class).isEmpty()) {
            orgUnit = OrgUnitResourceIT.createEntity(em);
            em.persist(orgUnit);
            em.flush();
        } else {
            orgUnit = TestUtil.findAll(em, OrgUnit.class).get(0);
        }
        device.setOrgUnit(orgUnit);
        return device;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createUpdatedEntity(EntityManager em) {
        Device device = new Device()
            .visualId(UPDATED_VISUAL_ID)
            .description(UPDATED_DESCRIPTION)
            .enrollmentCode(UPDATED_ENROLLMENT_CODE)
            .enrollmentTime(UPDATED_ENROLLMENT_TIME)
            .stateFieldValue01(UPDATED_STATE_FIELD_VALUE_01)
            .stateFieldValue02(UPDATED_STATE_FIELD_VALUE_02)
            .stateFieldValue03(UPDATED_STATE_FIELD_VALUE_03)
            .stateFieldValue04(UPDATED_STATE_FIELD_VALUE_04)
            .lastError(UPDATED_LAST_ERROR)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);
        // Add required entity
        DeviceType deviceType;
        if (TestUtil.findAll(em, DeviceType.class).isEmpty()) {
            deviceType = DeviceTypeResourceIT.createUpdatedEntity(em);
            em.persist(deviceType);
            em.flush();
        } else {
            deviceType = TestUtil.findAll(em, DeviceType.class).get(0);
        }
        device.setType(deviceType);
        // Add required entity
        OrgUnit orgUnit;
        if (TestUtil.findAll(em, OrgUnit.class).isEmpty()) {
            orgUnit = OrgUnitResourceIT.createUpdatedEntity(em);
            em.persist(orgUnit);
            em.flush();
        } else {
            orgUnit = TestUtil.findAll(em, OrgUnit.class).get(0);
        }
        device.setOrgUnit(orgUnit);
        return device;
    }

    @BeforeEach
    public void initTest() {
        device = createEntity(em);
    }

    @Test
    @Transactional
    void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();
        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);
        restDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getVisualId()).isEqualTo(DEFAULT_VISUAL_ID);
        assertThat(testDevice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDevice.getEnrollmentCode()).isEqualTo(DEFAULT_ENROLLMENT_CODE);
        assertThat(testDevice.getEnrollmentTime()).isEqualTo(DEFAULT_ENROLLMENT_TIME);
        assertThat(testDevice.getStateFieldValue01()).isEqualTo(DEFAULT_STATE_FIELD_VALUE_01);
        assertThat(testDevice.getStateFieldValue02()).isEqualTo(DEFAULT_STATE_FIELD_VALUE_02);
        assertThat(testDevice.getStateFieldValue03()).isEqualTo(DEFAULT_STATE_FIELD_VALUE_03);
        assertThat(testDevice.getStateFieldValue04()).isEqualTo(DEFAULT_STATE_FIELD_VALUE_04);
        assertThat(testDevice.getLastError()).isEqualTo(DEFAULT_LAST_ERROR);
        assertThat(testDevice.getReadAuthPattern()).isEqualTo(DEFAULT_READ_AUTH_PATTERN);
        assertThat(testDevice.getWriteAuthPattern()).isEqualTo(DEFAULT_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void createDeviceWithExistingId() throws Exception {
        // Create the Device with an existing ID
        device.setId(1L);
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVisualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceRepository.findAll().size();
        // set the field null
        device.setVisualId(null);

        // Create the Device, which fails.
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        restDeviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList
        restDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].visualId").value(hasItem(DEFAULT_VISUAL_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].enrollmentCode").value(hasItem(DEFAULT_ENROLLMENT_CODE)))
            .andExpect(jsonPath("$.[*].enrollmentTime").value(hasItem(sameInstant(DEFAULT_ENROLLMENT_TIME))))
            .andExpect(jsonPath("$.[*].stateFieldValue01").value(hasItem(DEFAULT_STATE_FIELD_VALUE_01)))
            .andExpect(jsonPath("$.[*].stateFieldValue02").value(hasItem(DEFAULT_STATE_FIELD_VALUE_02)))
            .andExpect(jsonPath("$.[*].stateFieldValue03").value(hasItem(DEFAULT_STATE_FIELD_VALUE_03)))
            .andExpect(jsonPath("$.[*].stateFieldValue04").value(hasItem(DEFAULT_STATE_FIELD_VALUE_04)))
            .andExpect(jsonPath("$.[*].lastError").value(hasItem(DEFAULT_LAST_ERROR.toString())))
            .andExpect(jsonPath("$.[*].readAuthPattern").value(hasItem(DEFAULT_READ_AUTH_PATTERN)))
            .andExpect(jsonPath("$.[*].writeAuthPattern").value(hasItem(DEFAULT_WRITE_AUTH_PATTERN)));
    }

    @Test
    @Transactional
    void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc
            .perform(get(ENTITY_API_URL_ID, device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(device.getId().intValue()))
            .andExpect(jsonPath("$.visualId").value(DEFAULT_VISUAL_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.enrollmentCode").value(DEFAULT_ENROLLMENT_CODE))
            .andExpect(jsonPath("$.enrollmentTime").value(sameInstant(DEFAULT_ENROLLMENT_TIME)))
            .andExpect(jsonPath("$.stateFieldValue01").value(DEFAULT_STATE_FIELD_VALUE_01))
            .andExpect(jsonPath("$.stateFieldValue02").value(DEFAULT_STATE_FIELD_VALUE_02))
            .andExpect(jsonPath("$.stateFieldValue03").value(DEFAULT_STATE_FIELD_VALUE_03))
            .andExpect(jsonPath("$.stateFieldValue04").value(DEFAULT_STATE_FIELD_VALUE_04))
            .andExpect(jsonPath("$.lastError").value(DEFAULT_LAST_ERROR.toString()))
            .andExpect(jsonPath("$.readAuthPattern").value(DEFAULT_READ_AUTH_PATTERN))
            .andExpect(jsonPath("$.writeAuthPattern").value(DEFAULT_WRITE_AUTH_PATTERN));
    }

    @Test
    @Transactional
    void getDevicesByIdFiltering() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        Long id = device.getId();

        defaultDeviceShouldBeFound("id.equals=" + id);
        defaultDeviceShouldNotBeFound("id.notEquals=" + id);

        defaultDeviceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeviceShouldNotBeFound("id.greaterThan=" + id);

        defaultDeviceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeviceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDevicesByVisualIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where visualId equals to DEFAULT_VISUAL_ID
        defaultDeviceShouldBeFound("visualId.equals=" + DEFAULT_VISUAL_ID);

        // Get all the deviceList where visualId equals to UPDATED_VISUAL_ID
        defaultDeviceShouldNotBeFound("visualId.equals=" + UPDATED_VISUAL_ID);
    }

    @Test
    @Transactional
    void getAllDevicesByVisualIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where visualId not equals to DEFAULT_VISUAL_ID
        defaultDeviceShouldNotBeFound("visualId.notEquals=" + DEFAULT_VISUAL_ID);

        // Get all the deviceList where visualId not equals to UPDATED_VISUAL_ID
        defaultDeviceShouldBeFound("visualId.notEquals=" + UPDATED_VISUAL_ID);
    }

    @Test
    @Transactional
    void getAllDevicesByVisualIdIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where visualId in DEFAULT_VISUAL_ID or UPDATED_VISUAL_ID
        defaultDeviceShouldBeFound("visualId.in=" + DEFAULT_VISUAL_ID + "," + UPDATED_VISUAL_ID);

        // Get all the deviceList where visualId equals to UPDATED_VISUAL_ID
        defaultDeviceShouldNotBeFound("visualId.in=" + UPDATED_VISUAL_ID);
    }

    @Test
    @Transactional
    void getAllDevicesByVisualIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where visualId is not null
        defaultDeviceShouldBeFound("visualId.specified=true");

        // Get all the deviceList where visualId is null
        defaultDeviceShouldNotBeFound("visualId.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByVisualIdContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where visualId contains DEFAULT_VISUAL_ID
        defaultDeviceShouldBeFound("visualId.contains=" + DEFAULT_VISUAL_ID);

        // Get all the deviceList where visualId contains UPDATED_VISUAL_ID
        defaultDeviceShouldNotBeFound("visualId.contains=" + UPDATED_VISUAL_ID);
    }

    @Test
    @Transactional
    void getAllDevicesByVisualIdNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where visualId does not contain DEFAULT_VISUAL_ID
        defaultDeviceShouldNotBeFound("visualId.doesNotContain=" + DEFAULT_VISUAL_ID);

        // Get all the deviceList where visualId does not contain UPDATED_VISUAL_ID
        defaultDeviceShouldBeFound("visualId.doesNotContain=" + UPDATED_VISUAL_ID);
    }

    @Test
    @Transactional
    void getAllDevicesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where description equals to DEFAULT_DESCRIPTION
        defaultDeviceShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the deviceList where description equals to UPDATED_DESCRIPTION
        defaultDeviceShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDevicesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where description not equals to DEFAULT_DESCRIPTION
        defaultDeviceShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the deviceList where description not equals to UPDATED_DESCRIPTION
        defaultDeviceShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDevicesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDeviceShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the deviceList where description equals to UPDATED_DESCRIPTION
        defaultDeviceShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDevicesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where description is not null
        defaultDeviceShouldBeFound("description.specified=true");

        // Get all the deviceList where description is null
        defaultDeviceShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where description contains DEFAULT_DESCRIPTION
        defaultDeviceShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the deviceList where description contains UPDATED_DESCRIPTION
        defaultDeviceShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDevicesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where description does not contain DEFAULT_DESCRIPTION
        defaultDeviceShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the deviceList where description does not contain UPDATED_DESCRIPTION
        defaultDeviceShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentCode equals to DEFAULT_ENROLLMENT_CODE
        defaultDeviceShouldBeFound("enrollmentCode.equals=" + DEFAULT_ENROLLMENT_CODE);

        // Get all the deviceList where enrollmentCode equals to UPDATED_ENROLLMENT_CODE
        defaultDeviceShouldNotBeFound("enrollmentCode.equals=" + UPDATED_ENROLLMENT_CODE);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentCode not equals to DEFAULT_ENROLLMENT_CODE
        defaultDeviceShouldNotBeFound("enrollmentCode.notEquals=" + DEFAULT_ENROLLMENT_CODE);

        // Get all the deviceList where enrollmentCode not equals to UPDATED_ENROLLMENT_CODE
        defaultDeviceShouldBeFound("enrollmentCode.notEquals=" + UPDATED_ENROLLMENT_CODE);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentCodeIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentCode in DEFAULT_ENROLLMENT_CODE or UPDATED_ENROLLMENT_CODE
        defaultDeviceShouldBeFound("enrollmentCode.in=" + DEFAULT_ENROLLMENT_CODE + "," + UPDATED_ENROLLMENT_CODE);

        // Get all the deviceList where enrollmentCode equals to UPDATED_ENROLLMENT_CODE
        defaultDeviceShouldNotBeFound("enrollmentCode.in=" + UPDATED_ENROLLMENT_CODE);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentCode is not null
        defaultDeviceShouldBeFound("enrollmentCode.specified=true");

        // Get all the deviceList where enrollmentCode is null
        defaultDeviceShouldNotBeFound("enrollmentCode.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentCodeContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentCode contains DEFAULT_ENROLLMENT_CODE
        defaultDeviceShouldBeFound("enrollmentCode.contains=" + DEFAULT_ENROLLMENT_CODE);

        // Get all the deviceList where enrollmentCode contains UPDATED_ENROLLMENT_CODE
        defaultDeviceShouldNotBeFound("enrollmentCode.contains=" + UPDATED_ENROLLMENT_CODE);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentCodeNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentCode does not contain DEFAULT_ENROLLMENT_CODE
        defaultDeviceShouldNotBeFound("enrollmentCode.doesNotContain=" + DEFAULT_ENROLLMENT_CODE);

        // Get all the deviceList where enrollmentCode does not contain UPDATED_ENROLLMENT_CODE
        defaultDeviceShouldBeFound("enrollmentCode.doesNotContain=" + UPDATED_ENROLLMENT_CODE);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentTime equals to DEFAULT_ENROLLMENT_TIME
        defaultDeviceShouldBeFound("enrollmentTime.equals=" + DEFAULT_ENROLLMENT_TIME);

        // Get all the deviceList where enrollmentTime equals to UPDATED_ENROLLMENT_TIME
        defaultDeviceShouldNotBeFound("enrollmentTime.equals=" + UPDATED_ENROLLMENT_TIME);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentTime not equals to DEFAULT_ENROLLMENT_TIME
        defaultDeviceShouldNotBeFound("enrollmentTime.notEquals=" + DEFAULT_ENROLLMENT_TIME);

        // Get all the deviceList where enrollmentTime not equals to UPDATED_ENROLLMENT_TIME
        defaultDeviceShouldBeFound("enrollmentTime.notEquals=" + UPDATED_ENROLLMENT_TIME);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentTimeIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentTime in DEFAULT_ENROLLMENT_TIME or UPDATED_ENROLLMENT_TIME
        defaultDeviceShouldBeFound("enrollmentTime.in=" + DEFAULT_ENROLLMENT_TIME + "," + UPDATED_ENROLLMENT_TIME);

        // Get all the deviceList where enrollmentTime equals to UPDATED_ENROLLMENT_TIME
        defaultDeviceShouldNotBeFound("enrollmentTime.in=" + UPDATED_ENROLLMENT_TIME);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentTime is not null
        defaultDeviceShouldBeFound("enrollmentTime.specified=true");

        // Get all the deviceList where enrollmentTime is null
        defaultDeviceShouldNotBeFound("enrollmentTime.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentTime is greater than or equal to DEFAULT_ENROLLMENT_TIME
        defaultDeviceShouldBeFound("enrollmentTime.greaterThanOrEqual=" + DEFAULT_ENROLLMENT_TIME);

        // Get all the deviceList where enrollmentTime is greater than or equal to UPDATED_ENROLLMENT_TIME
        defaultDeviceShouldNotBeFound("enrollmentTime.greaterThanOrEqual=" + UPDATED_ENROLLMENT_TIME);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentTime is less than or equal to DEFAULT_ENROLLMENT_TIME
        defaultDeviceShouldBeFound("enrollmentTime.lessThanOrEqual=" + DEFAULT_ENROLLMENT_TIME);

        // Get all the deviceList where enrollmentTime is less than or equal to SMALLER_ENROLLMENT_TIME
        defaultDeviceShouldNotBeFound("enrollmentTime.lessThanOrEqual=" + SMALLER_ENROLLMENT_TIME);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentTime is less than DEFAULT_ENROLLMENT_TIME
        defaultDeviceShouldNotBeFound("enrollmentTime.lessThan=" + DEFAULT_ENROLLMENT_TIME);

        // Get all the deviceList where enrollmentTime is less than UPDATED_ENROLLMENT_TIME
        defaultDeviceShouldBeFound("enrollmentTime.lessThan=" + UPDATED_ENROLLMENT_TIME);
    }

    @Test
    @Transactional
    void getAllDevicesByEnrollmentTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where enrollmentTime is greater than DEFAULT_ENROLLMENT_TIME
        defaultDeviceShouldNotBeFound("enrollmentTime.greaterThan=" + DEFAULT_ENROLLMENT_TIME);

        // Get all the deviceList where enrollmentTime is greater than SMALLER_ENROLLMENT_TIME
        defaultDeviceShouldBeFound("enrollmentTime.greaterThan=" + SMALLER_ENROLLMENT_TIME);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue01IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue01 equals to DEFAULT_STATE_FIELD_VALUE_01
        defaultDeviceShouldBeFound("stateFieldValue01.equals=" + DEFAULT_STATE_FIELD_VALUE_01);

        // Get all the deviceList where stateFieldValue01 equals to UPDATED_STATE_FIELD_VALUE_01
        defaultDeviceShouldNotBeFound("stateFieldValue01.equals=" + UPDATED_STATE_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue01IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue01 not equals to DEFAULT_STATE_FIELD_VALUE_01
        defaultDeviceShouldNotBeFound("stateFieldValue01.notEquals=" + DEFAULT_STATE_FIELD_VALUE_01);

        // Get all the deviceList where stateFieldValue01 not equals to UPDATED_STATE_FIELD_VALUE_01
        defaultDeviceShouldBeFound("stateFieldValue01.notEquals=" + UPDATED_STATE_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue01IsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue01 in DEFAULT_STATE_FIELD_VALUE_01 or UPDATED_STATE_FIELD_VALUE_01
        defaultDeviceShouldBeFound("stateFieldValue01.in=" + DEFAULT_STATE_FIELD_VALUE_01 + "," + UPDATED_STATE_FIELD_VALUE_01);

        // Get all the deviceList where stateFieldValue01 equals to UPDATED_STATE_FIELD_VALUE_01
        defaultDeviceShouldNotBeFound("stateFieldValue01.in=" + UPDATED_STATE_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue01IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue01 is not null
        defaultDeviceShouldBeFound("stateFieldValue01.specified=true");

        // Get all the deviceList where stateFieldValue01 is null
        defaultDeviceShouldNotBeFound("stateFieldValue01.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue01ContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue01 contains DEFAULT_STATE_FIELD_VALUE_01
        defaultDeviceShouldBeFound("stateFieldValue01.contains=" + DEFAULT_STATE_FIELD_VALUE_01);

        // Get all the deviceList where stateFieldValue01 contains UPDATED_STATE_FIELD_VALUE_01
        defaultDeviceShouldNotBeFound("stateFieldValue01.contains=" + UPDATED_STATE_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue01NotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue01 does not contain DEFAULT_STATE_FIELD_VALUE_01
        defaultDeviceShouldNotBeFound("stateFieldValue01.doesNotContain=" + DEFAULT_STATE_FIELD_VALUE_01);

        // Get all the deviceList where stateFieldValue01 does not contain UPDATED_STATE_FIELD_VALUE_01
        defaultDeviceShouldBeFound("stateFieldValue01.doesNotContain=" + UPDATED_STATE_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue02IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue02 equals to DEFAULT_STATE_FIELD_VALUE_02
        defaultDeviceShouldBeFound("stateFieldValue02.equals=" + DEFAULT_STATE_FIELD_VALUE_02);

        // Get all the deviceList where stateFieldValue02 equals to UPDATED_STATE_FIELD_VALUE_02
        defaultDeviceShouldNotBeFound("stateFieldValue02.equals=" + UPDATED_STATE_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue02IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue02 not equals to DEFAULT_STATE_FIELD_VALUE_02
        defaultDeviceShouldNotBeFound("stateFieldValue02.notEquals=" + DEFAULT_STATE_FIELD_VALUE_02);

        // Get all the deviceList where stateFieldValue02 not equals to UPDATED_STATE_FIELD_VALUE_02
        defaultDeviceShouldBeFound("stateFieldValue02.notEquals=" + UPDATED_STATE_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue02IsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue02 in DEFAULT_STATE_FIELD_VALUE_02 or UPDATED_STATE_FIELD_VALUE_02
        defaultDeviceShouldBeFound("stateFieldValue02.in=" + DEFAULT_STATE_FIELD_VALUE_02 + "," + UPDATED_STATE_FIELD_VALUE_02);

        // Get all the deviceList where stateFieldValue02 equals to UPDATED_STATE_FIELD_VALUE_02
        defaultDeviceShouldNotBeFound("stateFieldValue02.in=" + UPDATED_STATE_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue02IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue02 is not null
        defaultDeviceShouldBeFound("stateFieldValue02.specified=true");

        // Get all the deviceList where stateFieldValue02 is null
        defaultDeviceShouldNotBeFound("stateFieldValue02.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue02ContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue02 contains DEFAULT_STATE_FIELD_VALUE_02
        defaultDeviceShouldBeFound("stateFieldValue02.contains=" + DEFAULT_STATE_FIELD_VALUE_02);

        // Get all the deviceList where stateFieldValue02 contains UPDATED_STATE_FIELD_VALUE_02
        defaultDeviceShouldNotBeFound("stateFieldValue02.contains=" + UPDATED_STATE_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue02NotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue02 does not contain DEFAULT_STATE_FIELD_VALUE_02
        defaultDeviceShouldNotBeFound("stateFieldValue02.doesNotContain=" + DEFAULT_STATE_FIELD_VALUE_02);

        // Get all the deviceList where stateFieldValue02 does not contain UPDATED_STATE_FIELD_VALUE_02
        defaultDeviceShouldBeFound("stateFieldValue02.doesNotContain=" + UPDATED_STATE_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue03IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue03 equals to DEFAULT_STATE_FIELD_VALUE_03
        defaultDeviceShouldBeFound("stateFieldValue03.equals=" + DEFAULT_STATE_FIELD_VALUE_03);

        // Get all the deviceList where stateFieldValue03 equals to UPDATED_STATE_FIELD_VALUE_03
        defaultDeviceShouldNotBeFound("stateFieldValue03.equals=" + UPDATED_STATE_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue03IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue03 not equals to DEFAULT_STATE_FIELD_VALUE_03
        defaultDeviceShouldNotBeFound("stateFieldValue03.notEquals=" + DEFAULT_STATE_FIELD_VALUE_03);

        // Get all the deviceList where stateFieldValue03 not equals to UPDATED_STATE_FIELD_VALUE_03
        defaultDeviceShouldBeFound("stateFieldValue03.notEquals=" + UPDATED_STATE_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue03IsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue03 in DEFAULT_STATE_FIELD_VALUE_03 or UPDATED_STATE_FIELD_VALUE_03
        defaultDeviceShouldBeFound("stateFieldValue03.in=" + DEFAULT_STATE_FIELD_VALUE_03 + "," + UPDATED_STATE_FIELD_VALUE_03);

        // Get all the deviceList where stateFieldValue03 equals to UPDATED_STATE_FIELD_VALUE_03
        defaultDeviceShouldNotBeFound("stateFieldValue03.in=" + UPDATED_STATE_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue03IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue03 is not null
        defaultDeviceShouldBeFound("stateFieldValue03.specified=true");

        // Get all the deviceList where stateFieldValue03 is null
        defaultDeviceShouldNotBeFound("stateFieldValue03.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue03ContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue03 contains DEFAULT_STATE_FIELD_VALUE_03
        defaultDeviceShouldBeFound("stateFieldValue03.contains=" + DEFAULT_STATE_FIELD_VALUE_03);

        // Get all the deviceList where stateFieldValue03 contains UPDATED_STATE_FIELD_VALUE_03
        defaultDeviceShouldNotBeFound("stateFieldValue03.contains=" + UPDATED_STATE_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue03NotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue03 does not contain DEFAULT_STATE_FIELD_VALUE_03
        defaultDeviceShouldNotBeFound("stateFieldValue03.doesNotContain=" + DEFAULT_STATE_FIELD_VALUE_03);

        // Get all the deviceList where stateFieldValue03 does not contain UPDATED_STATE_FIELD_VALUE_03
        defaultDeviceShouldBeFound("stateFieldValue03.doesNotContain=" + UPDATED_STATE_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue04IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue04 equals to DEFAULT_STATE_FIELD_VALUE_04
        defaultDeviceShouldBeFound("stateFieldValue04.equals=" + DEFAULT_STATE_FIELD_VALUE_04);

        // Get all the deviceList where stateFieldValue04 equals to UPDATED_STATE_FIELD_VALUE_04
        defaultDeviceShouldNotBeFound("stateFieldValue04.equals=" + UPDATED_STATE_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue04IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue04 not equals to DEFAULT_STATE_FIELD_VALUE_04
        defaultDeviceShouldNotBeFound("stateFieldValue04.notEquals=" + DEFAULT_STATE_FIELD_VALUE_04);

        // Get all the deviceList where stateFieldValue04 not equals to UPDATED_STATE_FIELD_VALUE_04
        defaultDeviceShouldBeFound("stateFieldValue04.notEquals=" + UPDATED_STATE_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue04IsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue04 in DEFAULT_STATE_FIELD_VALUE_04 or UPDATED_STATE_FIELD_VALUE_04
        defaultDeviceShouldBeFound("stateFieldValue04.in=" + DEFAULT_STATE_FIELD_VALUE_04 + "," + UPDATED_STATE_FIELD_VALUE_04);

        // Get all the deviceList where stateFieldValue04 equals to UPDATED_STATE_FIELD_VALUE_04
        defaultDeviceShouldNotBeFound("stateFieldValue04.in=" + UPDATED_STATE_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue04IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue04 is not null
        defaultDeviceShouldBeFound("stateFieldValue04.specified=true");

        // Get all the deviceList where stateFieldValue04 is null
        defaultDeviceShouldNotBeFound("stateFieldValue04.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue04ContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue04 contains DEFAULT_STATE_FIELD_VALUE_04
        defaultDeviceShouldBeFound("stateFieldValue04.contains=" + DEFAULT_STATE_FIELD_VALUE_04);

        // Get all the deviceList where stateFieldValue04 contains UPDATED_STATE_FIELD_VALUE_04
        defaultDeviceShouldNotBeFound("stateFieldValue04.contains=" + UPDATED_STATE_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllDevicesByStateFieldValue04NotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where stateFieldValue04 does not contain DEFAULT_STATE_FIELD_VALUE_04
        defaultDeviceShouldNotBeFound("stateFieldValue04.doesNotContain=" + DEFAULT_STATE_FIELD_VALUE_04);

        // Get all the deviceList where stateFieldValue04 does not contain UPDATED_STATE_FIELD_VALUE_04
        defaultDeviceShouldBeFound("stateFieldValue04.doesNotContain=" + UPDATED_STATE_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllDevicesByReadAuthPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where readAuthPattern equals to DEFAULT_READ_AUTH_PATTERN
        defaultDeviceShouldBeFound("readAuthPattern.equals=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the deviceList where readAuthPattern equals to UPDATED_READ_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("readAuthPattern.equals=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByReadAuthPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where readAuthPattern not equals to DEFAULT_READ_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("readAuthPattern.notEquals=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the deviceList where readAuthPattern not equals to UPDATED_READ_AUTH_PATTERN
        defaultDeviceShouldBeFound("readAuthPattern.notEquals=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByReadAuthPatternIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where readAuthPattern in DEFAULT_READ_AUTH_PATTERN or UPDATED_READ_AUTH_PATTERN
        defaultDeviceShouldBeFound("readAuthPattern.in=" + DEFAULT_READ_AUTH_PATTERN + "," + UPDATED_READ_AUTH_PATTERN);

        // Get all the deviceList where readAuthPattern equals to UPDATED_READ_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("readAuthPattern.in=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByReadAuthPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where readAuthPattern is not null
        defaultDeviceShouldBeFound("readAuthPattern.specified=true");

        // Get all the deviceList where readAuthPattern is null
        defaultDeviceShouldNotBeFound("readAuthPattern.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByReadAuthPatternContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where readAuthPattern contains DEFAULT_READ_AUTH_PATTERN
        defaultDeviceShouldBeFound("readAuthPattern.contains=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the deviceList where readAuthPattern contains UPDATED_READ_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("readAuthPattern.contains=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByReadAuthPatternNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where readAuthPattern does not contain DEFAULT_READ_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("readAuthPattern.doesNotContain=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the deviceList where readAuthPattern does not contain UPDATED_READ_AUTH_PATTERN
        defaultDeviceShouldBeFound("readAuthPattern.doesNotContain=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByWriteAuthPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where writeAuthPattern equals to DEFAULT_WRITE_AUTH_PATTERN
        defaultDeviceShouldBeFound("writeAuthPattern.equals=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the deviceList where writeAuthPattern equals to UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("writeAuthPattern.equals=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByWriteAuthPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where writeAuthPattern not equals to DEFAULT_WRITE_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("writeAuthPattern.notEquals=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the deviceList where writeAuthPattern not equals to UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceShouldBeFound("writeAuthPattern.notEquals=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByWriteAuthPatternIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where writeAuthPattern in DEFAULT_WRITE_AUTH_PATTERN or UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceShouldBeFound("writeAuthPattern.in=" + DEFAULT_WRITE_AUTH_PATTERN + "," + UPDATED_WRITE_AUTH_PATTERN);

        // Get all the deviceList where writeAuthPattern equals to UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("writeAuthPattern.in=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByWriteAuthPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where writeAuthPattern is not null
        defaultDeviceShouldBeFound("writeAuthPattern.specified=true");

        // Get all the deviceList where writeAuthPattern is null
        defaultDeviceShouldNotBeFound("writeAuthPattern.specified=false");
    }

    @Test
    @Transactional
    void getAllDevicesByWriteAuthPatternContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where writeAuthPattern contains DEFAULT_WRITE_AUTH_PATTERN
        defaultDeviceShouldBeFound("writeAuthPattern.contains=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the deviceList where writeAuthPattern contains UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("writeAuthPattern.contains=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByWriteAuthPatternNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where writeAuthPattern does not contain DEFAULT_WRITE_AUTH_PATTERN
        defaultDeviceShouldNotBeFound("writeAuthPattern.doesNotContain=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the deviceList where writeAuthPattern does not contain UPDATED_WRITE_AUTH_PATTERN
        defaultDeviceShouldBeFound("writeAuthPattern.doesNotContain=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllDevicesByDeviceSignKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        KeyPair deviceSignKey = KeyPairResourceIT.createEntity(em);
        em.persist(deviceSignKey);
        em.flush();
        device.setDeviceSignKey(deviceSignKey);
        deviceRepository.saveAndFlush(device);
        Long deviceSignKeyId = deviceSignKey.getId();

        // Get all the deviceList where deviceSignKey equals to deviceSignKeyId
        defaultDeviceShouldBeFound("deviceSignKeyId.equals=" + deviceSignKeyId);

        // Get all the deviceList where deviceSignKey equals to (deviceSignKeyId + 1)
        defaultDeviceShouldNotBeFound("deviceSignKeyId.equals=" + (deviceSignKeyId + 1));
    }

    @Test
    @Transactional
    void getAllDevicesByDeviceEncKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        KeyPair deviceEncKey = KeyPairResourceIT.createEntity(em);
        em.persist(deviceEncKey);
        em.flush();
        device.setDeviceEncKey(deviceEncKey);
        deviceRepository.saveAndFlush(device);
        Long deviceEncKeyId = deviceEncKey.getId();

        // Get all the deviceList where deviceEncKey equals to deviceEncKeyId
        defaultDeviceShouldBeFound("deviceEncKeyId.equals=" + deviceEncKeyId);

        // Get all the deviceList where deviceEncKey equals to (deviceEncKeyId + 1)
        defaultDeviceShouldNotBeFound("deviceEncKeyId.equals=" + (deviceEncKeyId + 1));
    }

    @Test
    @Transactional
    void getAllDevicesByServerSignKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        KeyPair serverSignKey = KeyPairResourceIT.createEntity(em);
        em.persist(serverSignKey);
        em.flush();
        device.setServerSignKey(serverSignKey);
        deviceRepository.saveAndFlush(device);
        Long serverSignKeyId = serverSignKey.getId();

        // Get all the deviceList where serverSignKey equals to serverSignKeyId
        defaultDeviceShouldBeFound("serverSignKeyId.equals=" + serverSignKeyId);

        // Get all the deviceList where serverSignKey equals to (serverSignKeyId + 1)
        defaultDeviceShouldNotBeFound("serverSignKeyId.equals=" + (serverSignKeyId + 1));
    }

    @Test
    @Transactional
    void getAllDevicesByServerEncKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        KeyPair serverEncKey = KeyPairResourceIT.createEntity(em);
        em.persist(serverEncKey);
        em.flush();
        device.setServerEncKey(serverEncKey);
        deviceRepository.saveAndFlush(device);
        Long serverEncKeyId = serverEncKey.getId();

        // Get all the deviceList where serverEncKey equals to serverEncKeyId
        defaultDeviceShouldBeFound("serverEncKeyId.equals=" + serverEncKeyId);

        // Get all the deviceList where serverEncKey equals to (serverEncKeyId + 1)
        defaultDeviceShouldNotBeFound("serverEncKeyId.equals=" + (serverEncKeyId + 1));
    }

    @Test
    @Transactional
    void getAllDevicesByNextServerSignKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        KeyPair nextServerSignKey = KeyPairResourceIT.createEntity(em);
        em.persist(nextServerSignKey);
        em.flush();
        device.setNextServerSignKey(nextServerSignKey);
        deviceRepository.saveAndFlush(device);
        Long nextServerSignKeyId = nextServerSignKey.getId();

        // Get all the deviceList where nextServerSignKey equals to nextServerSignKeyId
        defaultDeviceShouldBeFound("nextServerSignKeyId.equals=" + nextServerSignKeyId);

        // Get all the deviceList where nextServerSignKey equals to (nextServerSignKeyId + 1)
        defaultDeviceShouldNotBeFound("nextServerSignKeyId.equals=" + (nextServerSignKeyId + 1));
    }

    @Test
    @Transactional
    void getAllDevicesByNextServerEncKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        KeyPair nextServerEncKey = KeyPairResourceIT.createEntity(em);
        em.persist(nextServerEncKey);
        em.flush();
        device.setNextServerEncKey(nextServerEncKey);
        deviceRepository.saveAndFlush(device);
        Long nextServerEncKeyId = nextServerEncKey.getId();

        // Get all the deviceList where nextServerEncKey equals to nextServerEncKeyId
        defaultDeviceShouldBeFound("nextServerEncKeyId.equals=" + nextServerEncKeyId);

        // Get all the deviceList where nextServerEncKey equals to (nextServerEncKeyId + 1)
        defaultDeviceShouldNotBeFound("nextServerEncKeyId.equals=" + (nextServerEncKeyId + 1));
    }

    @Test
    @Transactional
    void getAllDevicesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        DeviceType type = DeviceTypeResourceIT.createEntity(em);
        em.persist(type);
        em.flush();
        device.setType(type);
        deviceRepository.saveAndFlush(device);
        Long typeId = type.getId();

        // Get all the deviceList where type equals to typeId
        defaultDeviceShouldBeFound("typeId.equals=" + typeId);

        // Get all the deviceList where type equals to (typeId + 1)
        defaultDeviceShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    @Test
    @Transactional
    void getAllDevicesByOrgUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        OrgUnit orgUnit = OrgUnitResourceIT.createEntity(em);
        em.persist(orgUnit);
        em.flush();
        device.setOrgUnit(orgUnit);
        deviceRepository.saveAndFlush(device);
        Long orgUnitId = orgUnit.getId();

        // Get all the deviceList where orgUnit equals to orgUnitId
        defaultDeviceShouldBeFound("orgUnitId.equals=" + orgUnitId);

        // Get all the deviceList where orgUnit equals to (orgUnitId + 1)
        defaultDeviceShouldNotBeFound("orgUnitId.equals=" + (orgUnitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeviceShouldBeFound(String filter) throws Exception {
        restDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].visualId").value(hasItem(DEFAULT_VISUAL_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].enrollmentCode").value(hasItem(DEFAULT_ENROLLMENT_CODE)))
            .andExpect(jsonPath("$.[*].enrollmentTime").value(hasItem(sameInstant(DEFAULT_ENROLLMENT_TIME))))
            .andExpect(jsonPath("$.[*].stateFieldValue01").value(hasItem(DEFAULT_STATE_FIELD_VALUE_01)))
            .andExpect(jsonPath("$.[*].stateFieldValue02").value(hasItem(DEFAULT_STATE_FIELD_VALUE_02)))
            .andExpect(jsonPath("$.[*].stateFieldValue03").value(hasItem(DEFAULT_STATE_FIELD_VALUE_03)))
            .andExpect(jsonPath("$.[*].stateFieldValue04").value(hasItem(DEFAULT_STATE_FIELD_VALUE_04)))
            .andExpect(jsonPath("$.[*].lastError").value(hasItem(DEFAULT_LAST_ERROR.toString())))
            .andExpect(jsonPath("$.[*].readAuthPattern").value(hasItem(DEFAULT_READ_AUTH_PATTERN)))
            .andExpect(jsonPath("$.[*].writeAuthPattern").value(hasItem(DEFAULT_WRITE_AUTH_PATTERN)));

        // Check, that the count call also returns 1
        restDeviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeviceShouldNotBeFound(String filter) throws Exception {
        restDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        Device updatedDevice = deviceRepository.findById(device.getId()).get();
        // Disconnect from session so that the updates on updatedDevice are not directly saved in db
        em.detach(updatedDevice);
        updatedDevice
            .visualId(UPDATED_VISUAL_ID)
            .description(UPDATED_DESCRIPTION)
            .enrollmentCode(UPDATED_ENROLLMENT_CODE)
            .enrollmentTime(UPDATED_ENROLLMENT_TIME)
            .stateFieldValue01(UPDATED_STATE_FIELD_VALUE_01)
            .stateFieldValue02(UPDATED_STATE_FIELD_VALUE_02)
            .stateFieldValue03(UPDATED_STATE_FIELD_VALUE_03)
            .stateFieldValue04(UPDATED_STATE_FIELD_VALUE_04)
            .lastError(UPDATED_LAST_ERROR)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);
        DeviceDTO deviceDTO = deviceMapper.toDto(updatedDevice);

        restDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getVisualId()).isEqualTo(UPDATED_VISUAL_ID);
        assertThat(testDevice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDevice.getEnrollmentCode()).isEqualTo(UPDATED_ENROLLMENT_CODE);
        assertThat(testDevice.getEnrollmentTime()).isEqualTo(UPDATED_ENROLLMENT_TIME);
        assertThat(testDevice.getStateFieldValue01()).isEqualTo(UPDATED_STATE_FIELD_VALUE_01);
        assertThat(testDevice.getStateFieldValue02()).isEqualTo(UPDATED_STATE_FIELD_VALUE_02);
        assertThat(testDevice.getStateFieldValue03()).isEqualTo(UPDATED_STATE_FIELD_VALUE_03);
        assertThat(testDevice.getStateFieldValue04()).isEqualTo(UPDATED_STATE_FIELD_VALUE_04);
        assertThat(testDevice.getLastError()).isEqualTo(UPDATED_LAST_ERROR);
        assertThat(testDevice.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testDevice.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void putNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeviceWithPatch() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device using partial update
        Device partialUpdatedDevice = new Device();
        partialUpdatedDevice.setId(device.getId());

        partialUpdatedDevice
            .enrollmentCode(UPDATED_ENROLLMENT_CODE)
            .stateFieldValue02(UPDATED_STATE_FIELD_VALUE_02)
            .stateFieldValue03(UPDATED_STATE_FIELD_VALUE_03)
            .stateFieldValue04(UPDATED_STATE_FIELD_VALUE_04)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);

        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevice))
            )
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getVisualId()).isEqualTo(DEFAULT_VISUAL_ID);
        assertThat(testDevice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDevice.getEnrollmentCode()).isEqualTo(UPDATED_ENROLLMENT_CODE);
        assertThat(testDevice.getEnrollmentTime()).isEqualTo(DEFAULT_ENROLLMENT_TIME);
        assertThat(testDevice.getStateFieldValue01()).isEqualTo(DEFAULT_STATE_FIELD_VALUE_01);
        assertThat(testDevice.getStateFieldValue02()).isEqualTo(UPDATED_STATE_FIELD_VALUE_02);
        assertThat(testDevice.getStateFieldValue03()).isEqualTo(UPDATED_STATE_FIELD_VALUE_03);
        assertThat(testDevice.getStateFieldValue04()).isEqualTo(UPDATED_STATE_FIELD_VALUE_04);
        assertThat(testDevice.getLastError()).isEqualTo(DEFAULT_LAST_ERROR);
        assertThat(testDevice.getReadAuthPattern()).isEqualTo(DEFAULT_READ_AUTH_PATTERN);
        assertThat(testDevice.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void fullUpdateDeviceWithPatch() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device using partial update
        Device partialUpdatedDevice = new Device();
        partialUpdatedDevice.setId(device.getId());

        partialUpdatedDevice
            .visualId(UPDATED_VISUAL_ID)
            .description(UPDATED_DESCRIPTION)
            .enrollmentCode(UPDATED_ENROLLMENT_CODE)
            .enrollmentTime(UPDATED_ENROLLMENT_TIME)
            .stateFieldValue01(UPDATED_STATE_FIELD_VALUE_01)
            .stateFieldValue02(UPDATED_STATE_FIELD_VALUE_02)
            .stateFieldValue03(UPDATED_STATE_FIELD_VALUE_03)
            .stateFieldValue04(UPDATED_STATE_FIELD_VALUE_04)
            .lastError(UPDATED_LAST_ERROR)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);

        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevice))
            )
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getVisualId()).isEqualTo(UPDATED_VISUAL_ID);
        assertThat(testDevice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDevice.getEnrollmentCode()).isEqualTo(UPDATED_ENROLLMENT_CODE);
        assertThat(testDevice.getEnrollmentTime()).isEqualTo(UPDATED_ENROLLMENT_TIME);
        assertThat(testDevice.getStateFieldValue01()).isEqualTo(UPDATED_STATE_FIELD_VALUE_01);
        assertThat(testDevice.getStateFieldValue02()).isEqualTo(UPDATED_STATE_FIELD_VALUE_02);
        assertThat(testDevice.getStateFieldValue03()).isEqualTo(UPDATED_STATE_FIELD_VALUE_03);
        assertThat(testDevice.getStateFieldValue04()).isEqualTo(UPDATED_STATE_FIELD_VALUE_04);
        assertThat(testDevice.getLastError()).isEqualTo(UPDATED_LAST_ERROR);
        assertThat(testDevice.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testDevice.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void patchNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deviceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Delete the device
        restDeviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, device.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
