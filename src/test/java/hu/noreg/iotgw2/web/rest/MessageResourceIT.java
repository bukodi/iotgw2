package hu.noreg.iotgw2.web.rest;

import static hu.noreg.iotgw2.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hu.noreg.iotgw2.IntegrationTest;
import hu.noreg.iotgw2.domain.Device;
import hu.noreg.iotgw2.domain.Message;
import hu.noreg.iotgw2.domain.MessageType;
import hu.noreg.iotgw2.repository.MessageRepository;
import hu.noreg.iotgw2.service.criteria.MessageCriteria;
import hu.noreg.iotgw2.service.dto.MessageDTO;
import hu.noreg.iotgw2.service.mapper.MessageMapper;
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
 * Integration tests for the {@link MessageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MessageResourceIT {

    private static final ZonedDateTime DEFAULT_SERVER_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SERVER_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_SERVER_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DEVICE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DEVICE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DEVICE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_RAW_MESSAGE_SHA_256 = "AAAAAAAAAA";
    private static final String UPDATED_RAW_MESSAGE_SHA_256 = "BBBBBBBBBB";

    private static final byte[] DEFAULT_RAW_MESSAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RAW_MESSAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RAW_MESSAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RAW_MESSAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DECRYPTED_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_DECRYPTED_PAYLOAD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEV_TO_SRV = false;
    private static final Boolean UPDATED_DEV_TO_SRV = true;

    private static final String DEFAULT_INDEX_FIELD_VALUE_01 = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_FIELD_VALUE_01 = "BBBBBBBBBB";

    private static final String DEFAULT_INDEX_FIELD_VALUE_02 = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_FIELD_VALUE_02 = "BBBBBBBBBB";

    private static final String DEFAULT_INDEX_FIELD_VALUE_03 = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_FIELD_VALUE_03 = "BBBBBBBBBB";

    private static final String DEFAULT_INDEX_FIELD_VALUE_04 = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_FIELD_VALUE_04 = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESSING_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSING_ERROR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageMockMvc;

    private Message message;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createEntity(EntityManager em) {
        Message message = new Message()
            .serverTime(DEFAULT_SERVER_TIME)
            .deviceTime(DEFAULT_DEVICE_TIME)
            .rawMessageSHA256(DEFAULT_RAW_MESSAGE_SHA_256)
            .rawMessage(DEFAULT_RAW_MESSAGE)
            .rawMessageContentType(DEFAULT_RAW_MESSAGE_CONTENT_TYPE)
            .decryptedPayload(DEFAULT_DECRYPTED_PAYLOAD)
            .devToSrv(DEFAULT_DEV_TO_SRV)
            .indexFieldValue01(DEFAULT_INDEX_FIELD_VALUE_01)
            .indexFieldValue02(DEFAULT_INDEX_FIELD_VALUE_02)
            .indexFieldValue03(DEFAULT_INDEX_FIELD_VALUE_03)
            .indexFieldValue04(DEFAULT_INDEX_FIELD_VALUE_04)
            .processingError(DEFAULT_PROCESSING_ERROR);
        // Add required entity
        MessageType messageType;
        if (TestUtil.findAll(em, MessageType.class).isEmpty()) {
            messageType = MessageTypeResourceIT.createEntity(em);
            em.persist(messageType);
            em.flush();
        } else {
            messageType = TestUtil.findAll(em, MessageType.class).get(0);
        }
        message.setType(messageType);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        message.setDevice(device);
        return message;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createUpdatedEntity(EntityManager em) {
        Message message = new Message()
            .serverTime(UPDATED_SERVER_TIME)
            .deviceTime(UPDATED_DEVICE_TIME)
            .rawMessageSHA256(UPDATED_RAW_MESSAGE_SHA_256)
            .rawMessage(UPDATED_RAW_MESSAGE)
            .rawMessageContentType(UPDATED_RAW_MESSAGE_CONTENT_TYPE)
            .decryptedPayload(UPDATED_DECRYPTED_PAYLOAD)
            .devToSrv(UPDATED_DEV_TO_SRV)
            .indexFieldValue01(UPDATED_INDEX_FIELD_VALUE_01)
            .indexFieldValue02(UPDATED_INDEX_FIELD_VALUE_02)
            .indexFieldValue03(UPDATED_INDEX_FIELD_VALUE_03)
            .indexFieldValue04(UPDATED_INDEX_FIELD_VALUE_04)
            .processingError(UPDATED_PROCESSING_ERROR);
        // Add required entity
        MessageType messageType;
        if (TestUtil.findAll(em, MessageType.class).isEmpty()) {
            messageType = MessageTypeResourceIT.createUpdatedEntity(em);
            em.persist(messageType);
            em.flush();
        } else {
            messageType = TestUtil.findAll(em, MessageType.class).get(0);
        }
        message.setType(messageType);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createUpdatedEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        message.setDevice(device);
        return message;
    }

    @BeforeEach
    public void initTest() {
        message = createEntity(em);
    }

    @Test
    @Transactional
    void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);
        restMessageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getServerTime()).isEqualTo(DEFAULT_SERVER_TIME);
        assertThat(testMessage.getDeviceTime()).isEqualTo(DEFAULT_DEVICE_TIME);
        assertThat(testMessage.getRawMessageSHA256()).isEqualTo(DEFAULT_RAW_MESSAGE_SHA_256);
        assertThat(testMessage.getRawMessage()).isEqualTo(DEFAULT_RAW_MESSAGE);
        assertThat(testMessage.getRawMessageContentType()).isEqualTo(DEFAULT_RAW_MESSAGE_CONTENT_TYPE);
        assertThat(testMessage.getDecryptedPayload()).isEqualTo(DEFAULT_DECRYPTED_PAYLOAD);
        assertThat(testMessage.getDevToSrv()).isEqualTo(DEFAULT_DEV_TO_SRV);
        assertThat(testMessage.getIndexFieldValue01()).isEqualTo(DEFAULT_INDEX_FIELD_VALUE_01);
        assertThat(testMessage.getIndexFieldValue02()).isEqualTo(DEFAULT_INDEX_FIELD_VALUE_02);
        assertThat(testMessage.getIndexFieldValue03()).isEqualTo(DEFAULT_INDEX_FIELD_VALUE_03);
        assertThat(testMessage.getIndexFieldValue04()).isEqualTo(DEFAULT_INDEX_FIELD_VALUE_04);
        assertThat(testMessage.getProcessingError()).isEqualTo(DEFAULT_PROCESSING_ERROR);
    }

    @Test
    @Transactional
    void createMessageWithExistingId() throws Exception {
        // Create the Message with an existing ID
        message.setId(1L);
        MessageDTO messageDTO = messageMapper.toDto(message);

        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkServerTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setServerTime(null);

        // Create the Message, which fails.
        MessageDTO messageDTO = messageMapper.toDto(message);

        restMessageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRawMessageSHA256IsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setRawMessageSHA256(null);

        // Create the Message, which fails.
        MessageDTO messageDTO = messageMapper.toDto(message);

        restMessageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDevToSrvIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setDevToSrv(null);

        // Create the Message, which fails.
        MessageDTO messageDTO = messageMapper.toDto(message);

        restMessageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList
        restMessageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].serverTime").value(hasItem(sameInstant(DEFAULT_SERVER_TIME))))
            .andExpect(jsonPath("$.[*].deviceTime").value(hasItem(sameInstant(DEFAULT_DEVICE_TIME))))
            .andExpect(jsonPath("$.[*].rawMessageSHA256").value(hasItem(DEFAULT_RAW_MESSAGE_SHA_256)))
            .andExpect(jsonPath("$.[*].rawMessageContentType").value(hasItem(DEFAULT_RAW_MESSAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rawMessage").value(hasItem(Base64Utils.encodeToString(DEFAULT_RAW_MESSAGE))))
            .andExpect(jsonPath("$.[*].decryptedPayload").value(hasItem(DEFAULT_DECRYPTED_PAYLOAD.toString())))
            .andExpect(jsonPath("$.[*].devToSrv").value(hasItem(DEFAULT_DEV_TO_SRV.booleanValue())))
            .andExpect(jsonPath("$.[*].indexFieldValue01").value(hasItem(DEFAULT_INDEX_FIELD_VALUE_01)))
            .andExpect(jsonPath("$.[*].indexFieldValue02").value(hasItem(DEFAULT_INDEX_FIELD_VALUE_02)))
            .andExpect(jsonPath("$.[*].indexFieldValue03").value(hasItem(DEFAULT_INDEX_FIELD_VALUE_03)))
            .andExpect(jsonPath("$.[*].indexFieldValue04").value(hasItem(DEFAULT_INDEX_FIELD_VALUE_04)))
            .andExpect(jsonPath("$.[*].processingError").value(hasItem(DEFAULT_PROCESSING_ERROR.toString())));
    }

    @Test
    @Transactional
    void getMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get the message
        restMessageMockMvc
            .perform(get(ENTITY_API_URL_ID, message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(message.getId().intValue()))
            .andExpect(jsonPath("$.serverTime").value(sameInstant(DEFAULT_SERVER_TIME)))
            .andExpect(jsonPath("$.deviceTime").value(sameInstant(DEFAULT_DEVICE_TIME)))
            .andExpect(jsonPath("$.rawMessageSHA256").value(DEFAULT_RAW_MESSAGE_SHA_256))
            .andExpect(jsonPath("$.rawMessageContentType").value(DEFAULT_RAW_MESSAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.rawMessage").value(Base64Utils.encodeToString(DEFAULT_RAW_MESSAGE)))
            .andExpect(jsonPath("$.decryptedPayload").value(DEFAULT_DECRYPTED_PAYLOAD.toString()))
            .andExpect(jsonPath("$.devToSrv").value(DEFAULT_DEV_TO_SRV.booleanValue()))
            .andExpect(jsonPath("$.indexFieldValue01").value(DEFAULT_INDEX_FIELD_VALUE_01))
            .andExpect(jsonPath("$.indexFieldValue02").value(DEFAULT_INDEX_FIELD_VALUE_02))
            .andExpect(jsonPath("$.indexFieldValue03").value(DEFAULT_INDEX_FIELD_VALUE_03))
            .andExpect(jsonPath("$.indexFieldValue04").value(DEFAULT_INDEX_FIELD_VALUE_04))
            .andExpect(jsonPath("$.processingError").value(DEFAULT_PROCESSING_ERROR.toString()));
    }

    @Test
    @Transactional
    void getMessagesByIdFiltering() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        Long id = message.getId();

        defaultMessageShouldBeFound("id.equals=" + id);
        defaultMessageShouldNotBeFound("id.notEquals=" + id);

        defaultMessageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMessageShouldNotBeFound("id.greaterThan=" + id);

        defaultMessageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMessageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMessagesByServerTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where serverTime equals to DEFAULT_SERVER_TIME
        defaultMessageShouldBeFound("serverTime.equals=" + DEFAULT_SERVER_TIME);

        // Get all the messageList where serverTime equals to UPDATED_SERVER_TIME
        defaultMessageShouldNotBeFound("serverTime.equals=" + UPDATED_SERVER_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByServerTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where serverTime not equals to DEFAULT_SERVER_TIME
        defaultMessageShouldNotBeFound("serverTime.notEquals=" + DEFAULT_SERVER_TIME);

        // Get all the messageList where serverTime not equals to UPDATED_SERVER_TIME
        defaultMessageShouldBeFound("serverTime.notEquals=" + UPDATED_SERVER_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByServerTimeIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where serverTime in DEFAULT_SERVER_TIME or UPDATED_SERVER_TIME
        defaultMessageShouldBeFound("serverTime.in=" + DEFAULT_SERVER_TIME + "," + UPDATED_SERVER_TIME);

        // Get all the messageList where serverTime equals to UPDATED_SERVER_TIME
        defaultMessageShouldNotBeFound("serverTime.in=" + UPDATED_SERVER_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByServerTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where serverTime is not null
        defaultMessageShouldBeFound("serverTime.specified=true");

        // Get all the messageList where serverTime is null
        defaultMessageShouldNotBeFound("serverTime.specified=false");
    }

    @Test
    @Transactional
    void getAllMessagesByServerTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where serverTime is greater than or equal to DEFAULT_SERVER_TIME
        defaultMessageShouldBeFound("serverTime.greaterThanOrEqual=" + DEFAULT_SERVER_TIME);

        // Get all the messageList where serverTime is greater than or equal to UPDATED_SERVER_TIME
        defaultMessageShouldNotBeFound("serverTime.greaterThanOrEqual=" + UPDATED_SERVER_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByServerTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where serverTime is less than or equal to DEFAULT_SERVER_TIME
        defaultMessageShouldBeFound("serverTime.lessThanOrEqual=" + DEFAULT_SERVER_TIME);

        // Get all the messageList where serverTime is less than or equal to SMALLER_SERVER_TIME
        defaultMessageShouldNotBeFound("serverTime.lessThanOrEqual=" + SMALLER_SERVER_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByServerTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where serverTime is less than DEFAULT_SERVER_TIME
        defaultMessageShouldNotBeFound("serverTime.lessThan=" + DEFAULT_SERVER_TIME);

        // Get all the messageList where serverTime is less than UPDATED_SERVER_TIME
        defaultMessageShouldBeFound("serverTime.lessThan=" + UPDATED_SERVER_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByServerTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where serverTime is greater than DEFAULT_SERVER_TIME
        defaultMessageShouldNotBeFound("serverTime.greaterThan=" + DEFAULT_SERVER_TIME);

        // Get all the messageList where serverTime is greater than SMALLER_SERVER_TIME
        defaultMessageShouldBeFound("serverTime.greaterThan=" + SMALLER_SERVER_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByDeviceTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where deviceTime equals to DEFAULT_DEVICE_TIME
        defaultMessageShouldBeFound("deviceTime.equals=" + DEFAULT_DEVICE_TIME);

        // Get all the messageList where deviceTime equals to UPDATED_DEVICE_TIME
        defaultMessageShouldNotBeFound("deviceTime.equals=" + UPDATED_DEVICE_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByDeviceTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where deviceTime not equals to DEFAULT_DEVICE_TIME
        defaultMessageShouldNotBeFound("deviceTime.notEquals=" + DEFAULT_DEVICE_TIME);

        // Get all the messageList where deviceTime not equals to UPDATED_DEVICE_TIME
        defaultMessageShouldBeFound("deviceTime.notEquals=" + UPDATED_DEVICE_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByDeviceTimeIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where deviceTime in DEFAULT_DEVICE_TIME or UPDATED_DEVICE_TIME
        defaultMessageShouldBeFound("deviceTime.in=" + DEFAULT_DEVICE_TIME + "," + UPDATED_DEVICE_TIME);

        // Get all the messageList where deviceTime equals to UPDATED_DEVICE_TIME
        defaultMessageShouldNotBeFound("deviceTime.in=" + UPDATED_DEVICE_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByDeviceTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where deviceTime is not null
        defaultMessageShouldBeFound("deviceTime.specified=true");

        // Get all the messageList where deviceTime is null
        defaultMessageShouldNotBeFound("deviceTime.specified=false");
    }

    @Test
    @Transactional
    void getAllMessagesByDeviceTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where deviceTime is greater than or equal to DEFAULT_DEVICE_TIME
        defaultMessageShouldBeFound("deviceTime.greaterThanOrEqual=" + DEFAULT_DEVICE_TIME);

        // Get all the messageList where deviceTime is greater than or equal to UPDATED_DEVICE_TIME
        defaultMessageShouldNotBeFound("deviceTime.greaterThanOrEqual=" + UPDATED_DEVICE_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByDeviceTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where deviceTime is less than or equal to DEFAULT_DEVICE_TIME
        defaultMessageShouldBeFound("deviceTime.lessThanOrEqual=" + DEFAULT_DEVICE_TIME);

        // Get all the messageList where deviceTime is less than or equal to SMALLER_DEVICE_TIME
        defaultMessageShouldNotBeFound("deviceTime.lessThanOrEqual=" + SMALLER_DEVICE_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByDeviceTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where deviceTime is less than DEFAULT_DEVICE_TIME
        defaultMessageShouldNotBeFound("deviceTime.lessThan=" + DEFAULT_DEVICE_TIME);

        // Get all the messageList where deviceTime is less than UPDATED_DEVICE_TIME
        defaultMessageShouldBeFound("deviceTime.lessThan=" + UPDATED_DEVICE_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByDeviceTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where deviceTime is greater than DEFAULT_DEVICE_TIME
        defaultMessageShouldNotBeFound("deviceTime.greaterThan=" + DEFAULT_DEVICE_TIME);

        // Get all the messageList where deviceTime is greater than SMALLER_DEVICE_TIME
        defaultMessageShouldBeFound("deviceTime.greaterThan=" + SMALLER_DEVICE_TIME);
    }

    @Test
    @Transactional
    void getAllMessagesByRawMessageSHA256IsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where rawMessageSHA256 equals to DEFAULT_RAW_MESSAGE_SHA_256
        defaultMessageShouldBeFound("rawMessageSHA256.equals=" + DEFAULT_RAW_MESSAGE_SHA_256);

        // Get all the messageList where rawMessageSHA256 equals to UPDATED_RAW_MESSAGE_SHA_256
        defaultMessageShouldNotBeFound("rawMessageSHA256.equals=" + UPDATED_RAW_MESSAGE_SHA_256);
    }

    @Test
    @Transactional
    void getAllMessagesByRawMessageSHA256IsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where rawMessageSHA256 not equals to DEFAULT_RAW_MESSAGE_SHA_256
        defaultMessageShouldNotBeFound("rawMessageSHA256.notEquals=" + DEFAULT_RAW_MESSAGE_SHA_256);

        // Get all the messageList where rawMessageSHA256 not equals to UPDATED_RAW_MESSAGE_SHA_256
        defaultMessageShouldBeFound("rawMessageSHA256.notEquals=" + UPDATED_RAW_MESSAGE_SHA_256);
    }

    @Test
    @Transactional
    void getAllMessagesByRawMessageSHA256IsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where rawMessageSHA256 in DEFAULT_RAW_MESSAGE_SHA_256 or UPDATED_RAW_MESSAGE_SHA_256
        defaultMessageShouldBeFound("rawMessageSHA256.in=" + DEFAULT_RAW_MESSAGE_SHA_256 + "," + UPDATED_RAW_MESSAGE_SHA_256);

        // Get all the messageList where rawMessageSHA256 equals to UPDATED_RAW_MESSAGE_SHA_256
        defaultMessageShouldNotBeFound("rawMessageSHA256.in=" + UPDATED_RAW_MESSAGE_SHA_256);
    }

    @Test
    @Transactional
    void getAllMessagesByRawMessageSHA256IsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where rawMessageSHA256 is not null
        defaultMessageShouldBeFound("rawMessageSHA256.specified=true");

        // Get all the messageList where rawMessageSHA256 is null
        defaultMessageShouldNotBeFound("rawMessageSHA256.specified=false");
    }

    @Test
    @Transactional
    void getAllMessagesByRawMessageSHA256ContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where rawMessageSHA256 contains DEFAULT_RAW_MESSAGE_SHA_256
        defaultMessageShouldBeFound("rawMessageSHA256.contains=" + DEFAULT_RAW_MESSAGE_SHA_256);

        // Get all the messageList where rawMessageSHA256 contains UPDATED_RAW_MESSAGE_SHA_256
        defaultMessageShouldNotBeFound("rawMessageSHA256.contains=" + UPDATED_RAW_MESSAGE_SHA_256);
    }

    @Test
    @Transactional
    void getAllMessagesByRawMessageSHA256NotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where rawMessageSHA256 does not contain DEFAULT_RAW_MESSAGE_SHA_256
        defaultMessageShouldNotBeFound("rawMessageSHA256.doesNotContain=" + DEFAULT_RAW_MESSAGE_SHA_256);

        // Get all the messageList where rawMessageSHA256 does not contain UPDATED_RAW_MESSAGE_SHA_256
        defaultMessageShouldBeFound("rawMessageSHA256.doesNotContain=" + UPDATED_RAW_MESSAGE_SHA_256);
    }

    @Test
    @Transactional
    void getAllMessagesByDevToSrvIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where devToSrv equals to DEFAULT_DEV_TO_SRV
        defaultMessageShouldBeFound("devToSrv.equals=" + DEFAULT_DEV_TO_SRV);

        // Get all the messageList where devToSrv equals to UPDATED_DEV_TO_SRV
        defaultMessageShouldNotBeFound("devToSrv.equals=" + UPDATED_DEV_TO_SRV);
    }

    @Test
    @Transactional
    void getAllMessagesByDevToSrvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where devToSrv not equals to DEFAULT_DEV_TO_SRV
        defaultMessageShouldNotBeFound("devToSrv.notEquals=" + DEFAULT_DEV_TO_SRV);

        // Get all the messageList where devToSrv not equals to UPDATED_DEV_TO_SRV
        defaultMessageShouldBeFound("devToSrv.notEquals=" + UPDATED_DEV_TO_SRV);
    }

    @Test
    @Transactional
    void getAllMessagesByDevToSrvIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where devToSrv in DEFAULT_DEV_TO_SRV or UPDATED_DEV_TO_SRV
        defaultMessageShouldBeFound("devToSrv.in=" + DEFAULT_DEV_TO_SRV + "," + UPDATED_DEV_TO_SRV);

        // Get all the messageList where devToSrv equals to UPDATED_DEV_TO_SRV
        defaultMessageShouldNotBeFound("devToSrv.in=" + UPDATED_DEV_TO_SRV);
    }

    @Test
    @Transactional
    void getAllMessagesByDevToSrvIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where devToSrv is not null
        defaultMessageShouldBeFound("devToSrv.specified=true");

        // Get all the messageList where devToSrv is null
        defaultMessageShouldNotBeFound("devToSrv.specified=false");
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue01IsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue01 equals to DEFAULT_INDEX_FIELD_VALUE_01
        defaultMessageShouldBeFound("indexFieldValue01.equals=" + DEFAULT_INDEX_FIELD_VALUE_01);

        // Get all the messageList where indexFieldValue01 equals to UPDATED_INDEX_FIELD_VALUE_01
        defaultMessageShouldNotBeFound("indexFieldValue01.equals=" + UPDATED_INDEX_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue01IsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue01 not equals to DEFAULT_INDEX_FIELD_VALUE_01
        defaultMessageShouldNotBeFound("indexFieldValue01.notEquals=" + DEFAULT_INDEX_FIELD_VALUE_01);

        // Get all the messageList where indexFieldValue01 not equals to UPDATED_INDEX_FIELD_VALUE_01
        defaultMessageShouldBeFound("indexFieldValue01.notEquals=" + UPDATED_INDEX_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue01IsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue01 in DEFAULT_INDEX_FIELD_VALUE_01 or UPDATED_INDEX_FIELD_VALUE_01
        defaultMessageShouldBeFound("indexFieldValue01.in=" + DEFAULT_INDEX_FIELD_VALUE_01 + "," + UPDATED_INDEX_FIELD_VALUE_01);

        // Get all the messageList where indexFieldValue01 equals to UPDATED_INDEX_FIELD_VALUE_01
        defaultMessageShouldNotBeFound("indexFieldValue01.in=" + UPDATED_INDEX_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue01IsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue01 is not null
        defaultMessageShouldBeFound("indexFieldValue01.specified=true");

        // Get all the messageList where indexFieldValue01 is null
        defaultMessageShouldNotBeFound("indexFieldValue01.specified=false");
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue01ContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue01 contains DEFAULT_INDEX_FIELD_VALUE_01
        defaultMessageShouldBeFound("indexFieldValue01.contains=" + DEFAULT_INDEX_FIELD_VALUE_01);

        // Get all the messageList where indexFieldValue01 contains UPDATED_INDEX_FIELD_VALUE_01
        defaultMessageShouldNotBeFound("indexFieldValue01.contains=" + UPDATED_INDEX_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue01NotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue01 does not contain DEFAULT_INDEX_FIELD_VALUE_01
        defaultMessageShouldNotBeFound("indexFieldValue01.doesNotContain=" + DEFAULT_INDEX_FIELD_VALUE_01);

        // Get all the messageList where indexFieldValue01 does not contain UPDATED_INDEX_FIELD_VALUE_01
        defaultMessageShouldBeFound("indexFieldValue01.doesNotContain=" + UPDATED_INDEX_FIELD_VALUE_01);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue02IsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue02 equals to DEFAULT_INDEX_FIELD_VALUE_02
        defaultMessageShouldBeFound("indexFieldValue02.equals=" + DEFAULT_INDEX_FIELD_VALUE_02);

        // Get all the messageList where indexFieldValue02 equals to UPDATED_INDEX_FIELD_VALUE_02
        defaultMessageShouldNotBeFound("indexFieldValue02.equals=" + UPDATED_INDEX_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue02IsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue02 not equals to DEFAULT_INDEX_FIELD_VALUE_02
        defaultMessageShouldNotBeFound("indexFieldValue02.notEquals=" + DEFAULT_INDEX_FIELD_VALUE_02);

        // Get all the messageList where indexFieldValue02 not equals to UPDATED_INDEX_FIELD_VALUE_02
        defaultMessageShouldBeFound("indexFieldValue02.notEquals=" + UPDATED_INDEX_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue02IsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue02 in DEFAULT_INDEX_FIELD_VALUE_02 or UPDATED_INDEX_FIELD_VALUE_02
        defaultMessageShouldBeFound("indexFieldValue02.in=" + DEFAULT_INDEX_FIELD_VALUE_02 + "," + UPDATED_INDEX_FIELD_VALUE_02);

        // Get all the messageList where indexFieldValue02 equals to UPDATED_INDEX_FIELD_VALUE_02
        defaultMessageShouldNotBeFound("indexFieldValue02.in=" + UPDATED_INDEX_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue02IsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue02 is not null
        defaultMessageShouldBeFound("indexFieldValue02.specified=true");

        // Get all the messageList where indexFieldValue02 is null
        defaultMessageShouldNotBeFound("indexFieldValue02.specified=false");
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue02ContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue02 contains DEFAULT_INDEX_FIELD_VALUE_02
        defaultMessageShouldBeFound("indexFieldValue02.contains=" + DEFAULT_INDEX_FIELD_VALUE_02);

        // Get all the messageList where indexFieldValue02 contains UPDATED_INDEX_FIELD_VALUE_02
        defaultMessageShouldNotBeFound("indexFieldValue02.contains=" + UPDATED_INDEX_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue02NotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue02 does not contain DEFAULT_INDEX_FIELD_VALUE_02
        defaultMessageShouldNotBeFound("indexFieldValue02.doesNotContain=" + DEFAULT_INDEX_FIELD_VALUE_02);

        // Get all the messageList where indexFieldValue02 does not contain UPDATED_INDEX_FIELD_VALUE_02
        defaultMessageShouldBeFound("indexFieldValue02.doesNotContain=" + UPDATED_INDEX_FIELD_VALUE_02);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue03IsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue03 equals to DEFAULT_INDEX_FIELD_VALUE_03
        defaultMessageShouldBeFound("indexFieldValue03.equals=" + DEFAULT_INDEX_FIELD_VALUE_03);

        // Get all the messageList where indexFieldValue03 equals to UPDATED_INDEX_FIELD_VALUE_03
        defaultMessageShouldNotBeFound("indexFieldValue03.equals=" + UPDATED_INDEX_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue03IsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue03 not equals to DEFAULT_INDEX_FIELD_VALUE_03
        defaultMessageShouldNotBeFound("indexFieldValue03.notEquals=" + DEFAULT_INDEX_FIELD_VALUE_03);

        // Get all the messageList where indexFieldValue03 not equals to UPDATED_INDEX_FIELD_VALUE_03
        defaultMessageShouldBeFound("indexFieldValue03.notEquals=" + UPDATED_INDEX_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue03IsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue03 in DEFAULT_INDEX_FIELD_VALUE_03 or UPDATED_INDEX_FIELD_VALUE_03
        defaultMessageShouldBeFound("indexFieldValue03.in=" + DEFAULT_INDEX_FIELD_VALUE_03 + "," + UPDATED_INDEX_FIELD_VALUE_03);

        // Get all the messageList where indexFieldValue03 equals to UPDATED_INDEX_FIELD_VALUE_03
        defaultMessageShouldNotBeFound("indexFieldValue03.in=" + UPDATED_INDEX_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue03IsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue03 is not null
        defaultMessageShouldBeFound("indexFieldValue03.specified=true");

        // Get all the messageList where indexFieldValue03 is null
        defaultMessageShouldNotBeFound("indexFieldValue03.specified=false");
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue03ContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue03 contains DEFAULT_INDEX_FIELD_VALUE_03
        defaultMessageShouldBeFound("indexFieldValue03.contains=" + DEFAULT_INDEX_FIELD_VALUE_03);

        // Get all the messageList where indexFieldValue03 contains UPDATED_INDEX_FIELD_VALUE_03
        defaultMessageShouldNotBeFound("indexFieldValue03.contains=" + UPDATED_INDEX_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue03NotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue03 does not contain DEFAULT_INDEX_FIELD_VALUE_03
        defaultMessageShouldNotBeFound("indexFieldValue03.doesNotContain=" + DEFAULT_INDEX_FIELD_VALUE_03);

        // Get all the messageList where indexFieldValue03 does not contain UPDATED_INDEX_FIELD_VALUE_03
        defaultMessageShouldBeFound("indexFieldValue03.doesNotContain=" + UPDATED_INDEX_FIELD_VALUE_03);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue04IsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue04 equals to DEFAULT_INDEX_FIELD_VALUE_04
        defaultMessageShouldBeFound("indexFieldValue04.equals=" + DEFAULT_INDEX_FIELD_VALUE_04);

        // Get all the messageList where indexFieldValue04 equals to UPDATED_INDEX_FIELD_VALUE_04
        defaultMessageShouldNotBeFound("indexFieldValue04.equals=" + UPDATED_INDEX_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue04IsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue04 not equals to DEFAULT_INDEX_FIELD_VALUE_04
        defaultMessageShouldNotBeFound("indexFieldValue04.notEquals=" + DEFAULT_INDEX_FIELD_VALUE_04);

        // Get all the messageList where indexFieldValue04 not equals to UPDATED_INDEX_FIELD_VALUE_04
        defaultMessageShouldBeFound("indexFieldValue04.notEquals=" + UPDATED_INDEX_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue04IsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue04 in DEFAULT_INDEX_FIELD_VALUE_04 or UPDATED_INDEX_FIELD_VALUE_04
        defaultMessageShouldBeFound("indexFieldValue04.in=" + DEFAULT_INDEX_FIELD_VALUE_04 + "," + UPDATED_INDEX_FIELD_VALUE_04);

        // Get all the messageList where indexFieldValue04 equals to UPDATED_INDEX_FIELD_VALUE_04
        defaultMessageShouldNotBeFound("indexFieldValue04.in=" + UPDATED_INDEX_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue04IsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue04 is not null
        defaultMessageShouldBeFound("indexFieldValue04.specified=true");

        // Get all the messageList where indexFieldValue04 is null
        defaultMessageShouldNotBeFound("indexFieldValue04.specified=false");
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue04ContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue04 contains DEFAULT_INDEX_FIELD_VALUE_04
        defaultMessageShouldBeFound("indexFieldValue04.contains=" + DEFAULT_INDEX_FIELD_VALUE_04);

        // Get all the messageList where indexFieldValue04 contains UPDATED_INDEX_FIELD_VALUE_04
        defaultMessageShouldNotBeFound("indexFieldValue04.contains=" + UPDATED_INDEX_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllMessagesByIndexFieldValue04NotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where indexFieldValue04 does not contain DEFAULT_INDEX_FIELD_VALUE_04
        defaultMessageShouldNotBeFound("indexFieldValue04.doesNotContain=" + DEFAULT_INDEX_FIELD_VALUE_04);

        // Get all the messageList where indexFieldValue04 does not contain UPDATED_INDEX_FIELD_VALUE_04
        defaultMessageShouldBeFound("indexFieldValue04.doesNotContain=" + UPDATED_INDEX_FIELD_VALUE_04);
    }

    @Test
    @Transactional
    void getAllMessagesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);
        MessageType type = MessageTypeResourceIT.createEntity(em);
        em.persist(type);
        em.flush();
        message.setType(type);
        messageRepository.saveAndFlush(message);
        Long typeId = type.getId();

        // Get all the messageList where type equals to typeId
        defaultMessageShouldBeFound("typeId.equals=" + typeId);

        // Get all the messageList where type equals to (typeId + 1)
        defaultMessageShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    @Test
    @Transactional
    void getAllMessagesByDeviceIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);
        Device device = DeviceResourceIT.createEntity(em);
        em.persist(device);
        em.flush();
        message.setDevice(device);
        messageRepository.saveAndFlush(message);
        Long deviceId = device.getId();

        // Get all the messageList where device equals to deviceId
        defaultMessageShouldBeFound("deviceId.equals=" + deviceId);

        // Get all the messageList where device equals to (deviceId + 1)
        defaultMessageShouldNotBeFound("deviceId.equals=" + (deviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessageShouldBeFound(String filter) throws Exception {
        restMessageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].serverTime").value(hasItem(sameInstant(DEFAULT_SERVER_TIME))))
            .andExpect(jsonPath("$.[*].deviceTime").value(hasItem(sameInstant(DEFAULT_DEVICE_TIME))))
            .andExpect(jsonPath("$.[*].rawMessageSHA256").value(hasItem(DEFAULT_RAW_MESSAGE_SHA_256)))
            .andExpect(jsonPath("$.[*].rawMessageContentType").value(hasItem(DEFAULT_RAW_MESSAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rawMessage").value(hasItem(Base64Utils.encodeToString(DEFAULT_RAW_MESSAGE))))
            .andExpect(jsonPath("$.[*].decryptedPayload").value(hasItem(DEFAULT_DECRYPTED_PAYLOAD.toString())))
            .andExpect(jsonPath("$.[*].devToSrv").value(hasItem(DEFAULT_DEV_TO_SRV.booleanValue())))
            .andExpect(jsonPath("$.[*].indexFieldValue01").value(hasItem(DEFAULT_INDEX_FIELD_VALUE_01)))
            .andExpect(jsonPath("$.[*].indexFieldValue02").value(hasItem(DEFAULT_INDEX_FIELD_VALUE_02)))
            .andExpect(jsonPath("$.[*].indexFieldValue03").value(hasItem(DEFAULT_INDEX_FIELD_VALUE_03)))
            .andExpect(jsonPath("$.[*].indexFieldValue04").value(hasItem(DEFAULT_INDEX_FIELD_VALUE_04)))
            .andExpect(jsonPath("$.[*].processingError").value(hasItem(DEFAULT_PROCESSING_ERROR.toString())));

        // Check, that the count call also returns 1
        restMessageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessageShouldNotBeFound(String filter) throws Exception {
        restMessageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        Message updatedMessage = messageRepository.findById(message.getId()).get();
        // Disconnect from session so that the updates on updatedMessage are not directly saved in db
        em.detach(updatedMessage);
        updatedMessage
            .serverTime(UPDATED_SERVER_TIME)
            .deviceTime(UPDATED_DEVICE_TIME)
            .rawMessageSHA256(UPDATED_RAW_MESSAGE_SHA_256)
            .rawMessage(UPDATED_RAW_MESSAGE)
            .rawMessageContentType(UPDATED_RAW_MESSAGE_CONTENT_TYPE)
            .decryptedPayload(UPDATED_DECRYPTED_PAYLOAD)
            .devToSrv(UPDATED_DEV_TO_SRV)
            .indexFieldValue01(UPDATED_INDEX_FIELD_VALUE_01)
            .indexFieldValue02(UPDATED_INDEX_FIELD_VALUE_02)
            .indexFieldValue03(UPDATED_INDEX_FIELD_VALUE_03)
            .indexFieldValue04(UPDATED_INDEX_FIELD_VALUE_04)
            .processingError(UPDATED_PROCESSING_ERROR);
        MessageDTO messageDTO = messageMapper.toDto(updatedMessage);

        restMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getServerTime()).isEqualTo(UPDATED_SERVER_TIME);
        assertThat(testMessage.getDeviceTime()).isEqualTo(UPDATED_DEVICE_TIME);
        assertThat(testMessage.getRawMessageSHA256()).isEqualTo(UPDATED_RAW_MESSAGE_SHA_256);
        assertThat(testMessage.getRawMessage()).isEqualTo(UPDATED_RAW_MESSAGE);
        assertThat(testMessage.getRawMessageContentType()).isEqualTo(UPDATED_RAW_MESSAGE_CONTENT_TYPE);
        assertThat(testMessage.getDecryptedPayload()).isEqualTo(UPDATED_DECRYPTED_PAYLOAD);
        assertThat(testMessage.getDevToSrv()).isEqualTo(UPDATED_DEV_TO_SRV);
        assertThat(testMessage.getIndexFieldValue01()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_01);
        assertThat(testMessage.getIndexFieldValue02()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_02);
        assertThat(testMessage.getIndexFieldValue03()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_03);
        assertThat(testMessage.getIndexFieldValue04()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_04);
        assertThat(testMessage.getProcessingError()).isEqualTo(UPDATED_PROCESSING_ERROR);
    }

    @Test
    @Transactional
    void putNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(count.incrementAndGet());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(count.incrementAndGet());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(count.incrementAndGet());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMessageWithPatch() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message using partial update
        Message partialUpdatedMessage = new Message();
        partialUpdatedMessage.setId(message.getId());

        partialUpdatedMessage
            .deviceTime(UPDATED_DEVICE_TIME)
            .indexFieldValue01(UPDATED_INDEX_FIELD_VALUE_01)
            .indexFieldValue02(UPDATED_INDEX_FIELD_VALUE_02)
            .indexFieldValue03(UPDATED_INDEX_FIELD_VALUE_03)
            .indexFieldValue04(UPDATED_INDEX_FIELD_VALUE_04)
            .processingError(UPDATED_PROCESSING_ERROR);

        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessage))
            )
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getServerTime()).isEqualTo(DEFAULT_SERVER_TIME);
        assertThat(testMessage.getDeviceTime()).isEqualTo(UPDATED_DEVICE_TIME);
        assertThat(testMessage.getRawMessageSHA256()).isEqualTo(DEFAULT_RAW_MESSAGE_SHA_256);
        assertThat(testMessage.getRawMessage()).isEqualTo(DEFAULT_RAW_MESSAGE);
        assertThat(testMessage.getRawMessageContentType()).isEqualTo(DEFAULT_RAW_MESSAGE_CONTENT_TYPE);
        assertThat(testMessage.getDecryptedPayload()).isEqualTo(DEFAULT_DECRYPTED_PAYLOAD);
        assertThat(testMessage.getDevToSrv()).isEqualTo(DEFAULT_DEV_TO_SRV);
        assertThat(testMessage.getIndexFieldValue01()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_01);
        assertThat(testMessage.getIndexFieldValue02()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_02);
        assertThat(testMessage.getIndexFieldValue03()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_03);
        assertThat(testMessage.getIndexFieldValue04()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_04);
        assertThat(testMessage.getProcessingError()).isEqualTo(UPDATED_PROCESSING_ERROR);
    }

    @Test
    @Transactional
    void fullUpdateMessageWithPatch() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message using partial update
        Message partialUpdatedMessage = new Message();
        partialUpdatedMessage.setId(message.getId());

        partialUpdatedMessage
            .serverTime(UPDATED_SERVER_TIME)
            .deviceTime(UPDATED_DEVICE_TIME)
            .rawMessageSHA256(UPDATED_RAW_MESSAGE_SHA_256)
            .rawMessage(UPDATED_RAW_MESSAGE)
            .rawMessageContentType(UPDATED_RAW_MESSAGE_CONTENT_TYPE)
            .decryptedPayload(UPDATED_DECRYPTED_PAYLOAD)
            .devToSrv(UPDATED_DEV_TO_SRV)
            .indexFieldValue01(UPDATED_INDEX_FIELD_VALUE_01)
            .indexFieldValue02(UPDATED_INDEX_FIELD_VALUE_02)
            .indexFieldValue03(UPDATED_INDEX_FIELD_VALUE_03)
            .indexFieldValue04(UPDATED_INDEX_FIELD_VALUE_04)
            .processingError(UPDATED_PROCESSING_ERROR);

        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessage))
            )
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getServerTime()).isEqualTo(UPDATED_SERVER_TIME);
        assertThat(testMessage.getDeviceTime()).isEqualTo(UPDATED_DEVICE_TIME);
        assertThat(testMessage.getRawMessageSHA256()).isEqualTo(UPDATED_RAW_MESSAGE_SHA_256);
        assertThat(testMessage.getRawMessage()).isEqualTo(UPDATED_RAW_MESSAGE);
        assertThat(testMessage.getRawMessageContentType()).isEqualTo(UPDATED_RAW_MESSAGE_CONTENT_TYPE);
        assertThat(testMessage.getDecryptedPayload()).isEqualTo(UPDATED_DECRYPTED_PAYLOAD);
        assertThat(testMessage.getDevToSrv()).isEqualTo(UPDATED_DEV_TO_SRV);
        assertThat(testMessage.getIndexFieldValue01()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_01);
        assertThat(testMessage.getIndexFieldValue02()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_02);
        assertThat(testMessage.getIndexFieldValue03()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_03);
        assertThat(testMessage.getIndexFieldValue04()).isEqualTo(UPDATED_INDEX_FIELD_VALUE_04);
        assertThat(testMessage.getProcessingError()).isEqualTo(UPDATED_PROCESSING_ERROR);
    }

    @Test
    @Transactional
    void patchNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(count.incrementAndGet());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, messageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(count.incrementAndGet());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(count.incrementAndGet());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Delete the message
        restMessageMockMvc
            .perform(delete(ENTITY_API_URL_ID, message.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
