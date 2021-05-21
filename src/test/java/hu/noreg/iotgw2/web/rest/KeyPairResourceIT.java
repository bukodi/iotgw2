package hu.noreg.iotgw2.web.rest;

import static hu.noreg.iotgw2.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hu.noreg.iotgw2.IntegrationTest;
import hu.noreg.iotgw2.domain.KeyPair;
import hu.noreg.iotgw2.domain.enumeration.PkAlgorithm;
import hu.noreg.iotgw2.repository.KeyPairRepository;
import hu.noreg.iotgw2.service.criteria.KeyPairCriteria;
import hu.noreg.iotgw2.service.dto.KeyPairDTO;
import hu.noreg.iotgw2.service.mapper.KeyPairMapper;
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
 * Integration tests for the {@link KeyPairResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KeyPairResourceIT {

    private static final String DEFAULT_KEY_ID = "AAAAAAAAAA";
    private static final String UPDATED_KEY_ID = "BBBBBBBBBB";

    private static final PkAlgorithm DEFAULT_ALGORITHM = PkAlgorithm.RSA1024;
    private static final PkAlgorithm UPDATED_ALGORITHM = PkAlgorithm.RSA2048;

    private static final byte[] DEFAULT_PUBLIC_KEY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PUBLIC_KEY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PUBLIC_KEY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PUBLIC_KEY_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PRIVATE_KEY_DESCRIPTOR = "AAAAAAAAAA";
    private static final String UPDATED_PRIVATE_KEY_DESCRIPTOR = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTESTATION_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTESTATION_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTESTATION_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTESTATION_DATA_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ASSOCIATED_CERT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ASSOCIATED_CERT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ASSOCIATED_CERT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ASSOCIATED_CERT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CERTIFICATE_REQUEST = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERTIFICATE_REQUEST = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CERTIFICATE_REQUEST_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERTIFICATE_REQUEST_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CERT_SUBJECT_DN = "AAAAAAAAAA";
    private static final String UPDATED_CERT_SUBJECT_DN = "BBBBBBBBBB";

    private static final String DEFAULT_CERT_ISSUER_DN = "AAAAAAAAAA";
    private static final String UPDATED_CERT_ISSUER_DN = "BBBBBBBBBB";

    private static final String DEFAULT_CERT_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_CERT_SERIAL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CERT_NOT_BEFORE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CERT_NOT_BEFORE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CERT_NOT_BEFORE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_CERT_NOT_AFTER = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CERT_NOT_AFTER = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CERT_NOT_AFTER = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_CERT_REVOKED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CERT_REVOKED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CERT_REVOKED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/key-pairs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KeyPairRepository keyPairRepository;

    @Autowired
    private KeyPairMapper keyPairMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeyPairMockMvc;

    private KeyPair keyPair;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyPair createEntity(EntityManager em) {
        KeyPair keyPair = new KeyPair()
            .keyId(DEFAULT_KEY_ID)
            .algorithm(DEFAULT_ALGORITHM)
            .publicKey(DEFAULT_PUBLIC_KEY)
            .publicKeyContentType(DEFAULT_PUBLIC_KEY_CONTENT_TYPE)
            .privateKeyDescriptor(DEFAULT_PRIVATE_KEY_DESCRIPTOR)
            .attestationData(DEFAULT_ATTESTATION_DATA)
            .attestationDataContentType(DEFAULT_ATTESTATION_DATA_CONTENT_TYPE)
            .associatedCert(DEFAULT_ASSOCIATED_CERT)
            .associatedCertContentType(DEFAULT_ASSOCIATED_CERT_CONTENT_TYPE)
            .certificateRequest(DEFAULT_CERTIFICATE_REQUEST)
            .certificateRequestContentType(DEFAULT_CERTIFICATE_REQUEST_CONTENT_TYPE)
            .certSubjectDN(DEFAULT_CERT_SUBJECT_DN)
            .certIssuerDN(DEFAULT_CERT_ISSUER_DN)
            .certSerial(DEFAULT_CERT_SERIAL)
            .certNotBefore(DEFAULT_CERT_NOT_BEFORE)
            .certNotAfter(DEFAULT_CERT_NOT_AFTER)
            .certRevoked(DEFAULT_CERT_REVOKED);
        return keyPair;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyPair createUpdatedEntity(EntityManager em) {
        KeyPair keyPair = new KeyPair()
            .keyId(UPDATED_KEY_ID)
            .algorithm(UPDATED_ALGORITHM)
            .publicKey(UPDATED_PUBLIC_KEY)
            .publicKeyContentType(UPDATED_PUBLIC_KEY_CONTENT_TYPE)
            .privateKeyDescriptor(UPDATED_PRIVATE_KEY_DESCRIPTOR)
            .attestationData(UPDATED_ATTESTATION_DATA)
            .attestationDataContentType(UPDATED_ATTESTATION_DATA_CONTENT_TYPE)
            .associatedCert(UPDATED_ASSOCIATED_CERT)
            .associatedCertContentType(UPDATED_ASSOCIATED_CERT_CONTENT_TYPE)
            .certificateRequest(UPDATED_CERTIFICATE_REQUEST)
            .certificateRequestContentType(UPDATED_CERTIFICATE_REQUEST_CONTENT_TYPE)
            .certSubjectDN(UPDATED_CERT_SUBJECT_DN)
            .certIssuerDN(UPDATED_CERT_ISSUER_DN)
            .certSerial(UPDATED_CERT_SERIAL)
            .certNotBefore(UPDATED_CERT_NOT_BEFORE)
            .certNotAfter(UPDATED_CERT_NOT_AFTER)
            .certRevoked(UPDATED_CERT_REVOKED);
        return keyPair;
    }

    @BeforeEach
    public void initTest() {
        keyPair = createEntity(em);
    }

    @Test
    @Transactional
    void createKeyPair() throws Exception {
        int databaseSizeBeforeCreate = keyPairRepository.findAll().size();
        // Create the KeyPair
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);
        restKeyPairMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(keyPairDTO)))
            .andExpect(status().isCreated());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeCreate + 1);
        KeyPair testKeyPair = keyPairList.get(keyPairList.size() - 1);
        assertThat(testKeyPair.getKeyId()).isEqualTo(DEFAULT_KEY_ID);
        assertThat(testKeyPair.getAlgorithm()).isEqualTo(DEFAULT_ALGORITHM);
        assertThat(testKeyPair.getPublicKey()).isEqualTo(DEFAULT_PUBLIC_KEY);
        assertThat(testKeyPair.getPublicKeyContentType()).isEqualTo(DEFAULT_PUBLIC_KEY_CONTENT_TYPE);
        assertThat(testKeyPair.getPrivateKeyDescriptor()).isEqualTo(DEFAULT_PRIVATE_KEY_DESCRIPTOR);
        assertThat(testKeyPair.getAttestationData()).isEqualTo(DEFAULT_ATTESTATION_DATA);
        assertThat(testKeyPair.getAttestationDataContentType()).isEqualTo(DEFAULT_ATTESTATION_DATA_CONTENT_TYPE);
        assertThat(testKeyPair.getAssociatedCert()).isEqualTo(DEFAULT_ASSOCIATED_CERT);
        assertThat(testKeyPair.getAssociatedCertContentType()).isEqualTo(DEFAULT_ASSOCIATED_CERT_CONTENT_TYPE);
        assertThat(testKeyPair.getCertificateRequest()).isEqualTo(DEFAULT_CERTIFICATE_REQUEST);
        assertThat(testKeyPair.getCertificateRequestContentType()).isEqualTo(DEFAULT_CERTIFICATE_REQUEST_CONTENT_TYPE);
        assertThat(testKeyPair.getCertSubjectDN()).isEqualTo(DEFAULT_CERT_SUBJECT_DN);
        assertThat(testKeyPair.getCertIssuerDN()).isEqualTo(DEFAULT_CERT_ISSUER_DN);
        assertThat(testKeyPair.getCertSerial()).isEqualTo(DEFAULT_CERT_SERIAL);
        assertThat(testKeyPair.getCertNotBefore()).isEqualTo(DEFAULT_CERT_NOT_BEFORE);
        assertThat(testKeyPair.getCertNotAfter()).isEqualTo(DEFAULT_CERT_NOT_AFTER);
        assertThat(testKeyPair.getCertRevoked()).isEqualTo(DEFAULT_CERT_REVOKED);
    }

    @Test
    @Transactional
    void createKeyPairWithExistingId() throws Exception {
        // Create the KeyPair with an existing ID
        keyPair.setId(1L);
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);

        int databaseSizeBeforeCreate = keyPairRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyPairMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(keyPairDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKeyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyPairRepository.findAll().size();
        // set the field null
        keyPair.setKeyId(null);

        // Create the KeyPair, which fails.
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);

        restKeyPairMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(keyPairDTO)))
            .andExpect(status().isBadRequest());

        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAlgorithmIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyPairRepository.findAll().size();
        // set the field null
        keyPair.setAlgorithm(null);

        // Create the KeyPair, which fails.
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);

        restKeyPairMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(keyPairDTO)))
            .andExpect(status().isBadRequest());

        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKeyPairs() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList
        restKeyPairMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyPair.getId().intValue())))
            .andExpect(jsonPath("$.[*].keyId").value(hasItem(DEFAULT_KEY_ID)))
            .andExpect(jsonPath("$.[*].algorithm").value(hasItem(DEFAULT_ALGORITHM.toString())))
            .andExpect(jsonPath("$.[*].publicKeyContentType").value(hasItem(DEFAULT_PUBLIC_KEY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].publicKey").value(hasItem(Base64Utils.encodeToString(DEFAULT_PUBLIC_KEY))))
            .andExpect(jsonPath("$.[*].privateKeyDescriptor").value(hasItem(DEFAULT_PRIVATE_KEY_DESCRIPTOR.toString())))
            .andExpect(jsonPath("$.[*].attestationDataContentType").value(hasItem(DEFAULT_ATTESTATION_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attestationData").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTESTATION_DATA))))
            .andExpect(jsonPath("$.[*].associatedCertContentType").value(hasItem(DEFAULT_ASSOCIATED_CERT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].associatedCert").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSOCIATED_CERT))))
            .andExpect(jsonPath("$.[*].certificateRequestContentType").value(hasItem(DEFAULT_CERTIFICATE_REQUEST_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].certificateRequest").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICATE_REQUEST))))
            .andExpect(jsonPath("$.[*].certSubjectDN").value(hasItem(DEFAULT_CERT_SUBJECT_DN)))
            .andExpect(jsonPath("$.[*].certIssuerDN").value(hasItem(DEFAULT_CERT_ISSUER_DN)))
            .andExpect(jsonPath("$.[*].certSerial").value(hasItem(DEFAULT_CERT_SERIAL)))
            .andExpect(jsonPath("$.[*].certNotBefore").value(hasItem(sameInstant(DEFAULT_CERT_NOT_BEFORE))))
            .andExpect(jsonPath("$.[*].certNotAfter").value(hasItem(sameInstant(DEFAULT_CERT_NOT_AFTER))))
            .andExpect(jsonPath("$.[*].certRevoked").value(hasItem(sameInstant(DEFAULT_CERT_REVOKED))));
    }

    @Test
    @Transactional
    void getKeyPair() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get the keyPair
        restKeyPairMockMvc
            .perform(get(ENTITY_API_URL_ID, keyPair.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keyPair.getId().intValue()))
            .andExpect(jsonPath("$.keyId").value(DEFAULT_KEY_ID))
            .andExpect(jsonPath("$.algorithm").value(DEFAULT_ALGORITHM.toString()))
            .andExpect(jsonPath("$.publicKeyContentType").value(DEFAULT_PUBLIC_KEY_CONTENT_TYPE))
            .andExpect(jsonPath("$.publicKey").value(Base64Utils.encodeToString(DEFAULT_PUBLIC_KEY)))
            .andExpect(jsonPath("$.privateKeyDescriptor").value(DEFAULT_PRIVATE_KEY_DESCRIPTOR.toString()))
            .andExpect(jsonPath("$.attestationDataContentType").value(DEFAULT_ATTESTATION_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.attestationData").value(Base64Utils.encodeToString(DEFAULT_ATTESTATION_DATA)))
            .andExpect(jsonPath("$.associatedCertContentType").value(DEFAULT_ASSOCIATED_CERT_CONTENT_TYPE))
            .andExpect(jsonPath("$.associatedCert").value(Base64Utils.encodeToString(DEFAULT_ASSOCIATED_CERT)))
            .andExpect(jsonPath("$.certificateRequestContentType").value(DEFAULT_CERTIFICATE_REQUEST_CONTENT_TYPE))
            .andExpect(jsonPath("$.certificateRequest").value(Base64Utils.encodeToString(DEFAULT_CERTIFICATE_REQUEST)))
            .andExpect(jsonPath("$.certSubjectDN").value(DEFAULT_CERT_SUBJECT_DN))
            .andExpect(jsonPath("$.certIssuerDN").value(DEFAULT_CERT_ISSUER_DN))
            .andExpect(jsonPath("$.certSerial").value(DEFAULT_CERT_SERIAL))
            .andExpect(jsonPath("$.certNotBefore").value(sameInstant(DEFAULT_CERT_NOT_BEFORE)))
            .andExpect(jsonPath("$.certNotAfter").value(sameInstant(DEFAULT_CERT_NOT_AFTER)))
            .andExpect(jsonPath("$.certRevoked").value(sameInstant(DEFAULT_CERT_REVOKED)));
    }

    @Test
    @Transactional
    void getKeyPairsByIdFiltering() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        Long id = keyPair.getId();

        defaultKeyPairShouldBeFound("id.equals=" + id);
        defaultKeyPairShouldNotBeFound("id.notEquals=" + id);

        defaultKeyPairShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKeyPairShouldNotBeFound("id.greaterThan=" + id);

        defaultKeyPairShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKeyPairShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKeyPairsByKeyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where keyId equals to DEFAULT_KEY_ID
        defaultKeyPairShouldBeFound("keyId.equals=" + DEFAULT_KEY_ID);

        // Get all the keyPairList where keyId equals to UPDATED_KEY_ID
        defaultKeyPairShouldNotBeFound("keyId.equals=" + UPDATED_KEY_ID);
    }

    @Test
    @Transactional
    void getAllKeyPairsByKeyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where keyId not equals to DEFAULT_KEY_ID
        defaultKeyPairShouldNotBeFound("keyId.notEquals=" + DEFAULT_KEY_ID);

        // Get all the keyPairList where keyId not equals to UPDATED_KEY_ID
        defaultKeyPairShouldBeFound("keyId.notEquals=" + UPDATED_KEY_ID);
    }

    @Test
    @Transactional
    void getAllKeyPairsByKeyIdIsInShouldWork() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where keyId in DEFAULT_KEY_ID or UPDATED_KEY_ID
        defaultKeyPairShouldBeFound("keyId.in=" + DEFAULT_KEY_ID + "," + UPDATED_KEY_ID);

        // Get all the keyPairList where keyId equals to UPDATED_KEY_ID
        defaultKeyPairShouldNotBeFound("keyId.in=" + UPDATED_KEY_ID);
    }

    @Test
    @Transactional
    void getAllKeyPairsByKeyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where keyId is not null
        defaultKeyPairShouldBeFound("keyId.specified=true");

        // Get all the keyPairList where keyId is null
        defaultKeyPairShouldNotBeFound("keyId.specified=false");
    }

    @Test
    @Transactional
    void getAllKeyPairsByKeyIdContainsSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where keyId contains DEFAULT_KEY_ID
        defaultKeyPairShouldBeFound("keyId.contains=" + DEFAULT_KEY_ID);

        // Get all the keyPairList where keyId contains UPDATED_KEY_ID
        defaultKeyPairShouldNotBeFound("keyId.contains=" + UPDATED_KEY_ID);
    }

    @Test
    @Transactional
    void getAllKeyPairsByKeyIdNotContainsSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where keyId does not contain DEFAULT_KEY_ID
        defaultKeyPairShouldNotBeFound("keyId.doesNotContain=" + DEFAULT_KEY_ID);

        // Get all the keyPairList where keyId does not contain UPDATED_KEY_ID
        defaultKeyPairShouldBeFound("keyId.doesNotContain=" + UPDATED_KEY_ID);
    }

    @Test
    @Transactional
    void getAllKeyPairsByAlgorithmIsEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where algorithm equals to DEFAULT_ALGORITHM
        defaultKeyPairShouldBeFound("algorithm.equals=" + DEFAULT_ALGORITHM);

        // Get all the keyPairList where algorithm equals to UPDATED_ALGORITHM
        defaultKeyPairShouldNotBeFound("algorithm.equals=" + UPDATED_ALGORITHM);
    }

    @Test
    @Transactional
    void getAllKeyPairsByAlgorithmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where algorithm not equals to DEFAULT_ALGORITHM
        defaultKeyPairShouldNotBeFound("algorithm.notEquals=" + DEFAULT_ALGORITHM);

        // Get all the keyPairList where algorithm not equals to UPDATED_ALGORITHM
        defaultKeyPairShouldBeFound("algorithm.notEquals=" + UPDATED_ALGORITHM);
    }

    @Test
    @Transactional
    void getAllKeyPairsByAlgorithmIsInShouldWork() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where algorithm in DEFAULT_ALGORITHM or UPDATED_ALGORITHM
        defaultKeyPairShouldBeFound("algorithm.in=" + DEFAULT_ALGORITHM + "," + UPDATED_ALGORITHM);

        // Get all the keyPairList where algorithm equals to UPDATED_ALGORITHM
        defaultKeyPairShouldNotBeFound("algorithm.in=" + UPDATED_ALGORITHM);
    }

    @Test
    @Transactional
    void getAllKeyPairsByAlgorithmIsNullOrNotNull() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where algorithm is not null
        defaultKeyPairShouldBeFound("algorithm.specified=true");

        // Get all the keyPairList where algorithm is null
        defaultKeyPairShouldNotBeFound("algorithm.specified=false");
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSubjectDNIsEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSubjectDN equals to DEFAULT_CERT_SUBJECT_DN
        defaultKeyPairShouldBeFound("certSubjectDN.equals=" + DEFAULT_CERT_SUBJECT_DN);

        // Get all the keyPairList where certSubjectDN equals to UPDATED_CERT_SUBJECT_DN
        defaultKeyPairShouldNotBeFound("certSubjectDN.equals=" + UPDATED_CERT_SUBJECT_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSubjectDNIsNotEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSubjectDN not equals to DEFAULT_CERT_SUBJECT_DN
        defaultKeyPairShouldNotBeFound("certSubjectDN.notEquals=" + DEFAULT_CERT_SUBJECT_DN);

        // Get all the keyPairList where certSubjectDN not equals to UPDATED_CERT_SUBJECT_DN
        defaultKeyPairShouldBeFound("certSubjectDN.notEquals=" + UPDATED_CERT_SUBJECT_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSubjectDNIsInShouldWork() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSubjectDN in DEFAULT_CERT_SUBJECT_DN or UPDATED_CERT_SUBJECT_DN
        defaultKeyPairShouldBeFound("certSubjectDN.in=" + DEFAULT_CERT_SUBJECT_DN + "," + UPDATED_CERT_SUBJECT_DN);

        // Get all the keyPairList where certSubjectDN equals to UPDATED_CERT_SUBJECT_DN
        defaultKeyPairShouldNotBeFound("certSubjectDN.in=" + UPDATED_CERT_SUBJECT_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSubjectDNIsNullOrNotNull() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSubjectDN is not null
        defaultKeyPairShouldBeFound("certSubjectDN.specified=true");

        // Get all the keyPairList where certSubjectDN is null
        defaultKeyPairShouldNotBeFound("certSubjectDN.specified=false");
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSubjectDNContainsSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSubjectDN contains DEFAULT_CERT_SUBJECT_DN
        defaultKeyPairShouldBeFound("certSubjectDN.contains=" + DEFAULT_CERT_SUBJECT_DN);

        // Get all the keyPairList where certSubjectDN contains UPDATED_CERT_SUBJECT_DN
        defaultKeyPairShouldNotBeFound("certSubjectDN.contains=" + UPDATED_CERT_SUBJECT_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSubjectDNNotContainsSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSubjectDN does not contain DEFAULT_CERT_SUBJECT_DN
        defaultKeyPairShouldNotBeFound("certSubjectDN.doesNotContain=" + DEFAULT_CERT_SUBJECT_DN);

        // Get all the keyPairList where certSubjectDN does not contain UPDATED_CERT_SUBJECT_DN
        defaultKeyPairShouldBeFound("certSubjectDN.doesNotContain=" + UPDATED_CERT_SUBJECT_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertIssuerDNIsEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certIssuerDN equals to DEFAULT_CERT_ISSUER_DN
        defaultKeyPairShouldBeFound("certIssuerDN.equals=" + DEFAULT_CERT_ISSUER_DN);

        // Get all the keyPairList where certIssuerDN equals to UPDATED_CERT_ISSUER_DN
        defaultKeyPairShouldNotBeFound("certIssuerDN.equals=" + UPDATED_CERT_ISSUER_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertIssuerDNIsNotEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certIssuerDN not equals to DEFAULT_CERT_ISSUER_DN
        defaultKeyPairShouldNotBeFound("certIssuerDN.notEquals=" + DEFAULT_CERT_ISSUER_DN);

        // Get all the keyPairList where certIssuerDN not equals to UPDATED_CERT_ISSUER_DN
        defaultKeyPairShouldBeFound("certIssuerDN.notEquals=" + UPDATED_CERT_ISSUER_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertIssuerDNIsInShouldWork() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certIssuerDN in DEFAULT_CERT_ISSUER_DN or UPDATED_CERT_ISSUER_DN
        defaultKeyPairShouldBeFound("certIssuerDN.in=" + DEFAULT_CERT_ISSUER_DN + "," + UPDATED_CERT_ISSUER_DN);

        // Get all the keyPairList where certIssuerDN equals to UPDATED_CERT_ISSUER_DN
        defaultKeyPairShouldNotBeFound("certIssuerDN.in=" + UPDATED_CERT_ISSUER_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertIssuerDNIsNullOrNotNull() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certIssuerDN is not null
        defaultKeyPairShouldBeFound("certIssuerDN.specified=true");

        // Get all the keyPairList where certIssuerDN is null
        defaultKeyPairShouldNotBeFound("certIssuerDN.specified=false");
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertIssuerDNContainsSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certIssuerDN contains DEFAULT_CERT_ISSUER_DN
        defaultKeyPairShouldBeFound("certIssuerDN.contains=" + DEFAULT_CERT_ISSUER_DN);

        // Get all the keyPairList where certIssuerDN contains UPDATED_CERT_ISSUER_DN
        defaultKeyPairShouldNotBeFound("certIssuerDN.contains=" + UPDATED_CERT_ISSUER_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertIssuerDNNotContainsSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certIssuerDN does not contain DEFAULT_CERT_ISSUER_DN
        defaultKeyPairShouldNotBeFound("certIssuerDN.doesNotContain=" + DEFAULT_CERT_ISSUER_DN);

        // Get all the keyPairList where certIssuerDN does not contain UPDATED_CERT_ISSUER_DN
        defaultKeyPairShouldBeFound("certIssuerDN.doesNotContain=" + UPDATED_CERT_ISSUER_DN);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSerial equals to DEFAULT_CERT_SERIAL
        defaultKeyPairShouldBeFound("certSerial.equals=" + DEFAULT_CERT_SERIAL);

        // Get all the keyPairList where certSerial equals to UPDATED_CERT_SERIAL
        defaultKeyPairShouldNotBeFound("certSerial.equals=" + UPDATED_CERT_SERIAL);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSerialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSerial not equals to DEFAULT_CERT_SERIAL
        defaultKeyPairShouldNotBeFound("certSerial.notEquals=" + DEFAULT_CERT_SERIAL);

        // Get all the keyPairList where certSerial not equals to UPDATED_CERT_SERIAL
        defaultKeyPairShouldBeFound("certSerial.notEquals=" + UPDATED_CERT_SERIAL);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSerialIsInShouldWork() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSerial in DEFAULT_CERT_SERIAL or UPDATED_CERT_SERIAL
        defaultKeyPairShouldBeFound("certSerial.in=" + DEFAULT_CERT_SERIAL + "," + UPDATED_CERT_SERIAL);

        // Get all the keyPairList where certSerial equals to UPDATED_CERT_SERIAL
        defaultKeyPairShouldNotBeFound("certSerial.in=" + UPDATED_CERT_SERIAL);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSerial is not null
        defaultKeyPairShouldBeFound("certSerial.specified=true");

        // Get all the keyPairList where certSerial is null
        defaultKeyPairShouldNotBeFound("certSerial.specified=false");
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSerialContainsSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSerial contains DEFAULT_CERT_SERIAL
        defaultKeyPairShouldBeFound("certSerial.contains=" + DEFAULT_CERT_SERIAL);

        // Get all the keyPairList where certSerial contains UPDATED_CERT_SERIAL
        defaultKeyPairShouldNotBeFound("certSerial.contains=" + UPDATED_CERT_SERIAL);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertSerialNotContainsSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certSerial does not contain DEFAULT_CERT_SERIAL
        defaultKeyPairShouldNotBeFound("certSerial.doesNotContain=" + DEFAULT_CERT_SERIAL);

        // Get all the keyPairList where certSerial does not contain UPDATED_CERT_SERIAL
        defaultKeyPairShouldBeFound("certSerial.doesNotContain=" + UPDATED_CERT_SERIAL);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotBeforeIsEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotBefore equals to DEFAULT_CERT_NOT_BEFORE
        defaultKeyPairShouldBeFound("certNotBefore.equals=" + DEFAULT_CERT_NOT_BEFORE);

        // Get all the keyPairList where certNotBefore equals to UPDATED_CERT_NOT_BEFORE
        defaultKeyPairShouldNotBeFound("certNotBefore.equals=" + UPDATED_CERT_NOT_BEFORE);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotBeforeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotBefore not equals to DEFAULT_CERT_NOT_BEFORE
        defaultKeyPairShouldNotBeFound("certNotBefore.notEquals=" + DEFAULT_CERT_NOT_BEFORE);

        // Get all the keyPairList where certNotBefore not equals to UPDATED_CERT_NOT_BEFORE
        defaultKeyPairShouldBeFound("certNotBefore.notEquals=" + UPDATED_CERT_NOT_BEFORE);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotBeforeIsInShouldWork() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotBefore in DEFAULT_CERT_NOT_BEFORE or UPDATED_CERT_NOT_BEFORE
        defaultKeyPairShouldBeFound("certNotBefore.in=" + DEFAULT_CERT_NOT_BEFORE + "," + UPDATED_CERT_NOT_BEFORE);

        // Get all the keyPairList where certNotBefore equals to UPDATED_CERT_NOT_BEFORE
        defaultKeyPairShouldNotBeFound("certNotBefore.in=" + UPDATED_CERT_NOT_BEFORE);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotBeforeIsNullOrNotNull() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotBefore is not null
        defaultKeyPairShouldBeFound("certNotBefore.specified=true");

        // Get all the keyPairList where certNotBefore is null
        defaultKeyPairShouldNotBeFound("certNotBefore.specified=false");
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotBeforeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotBefore is greater than or equal to DEFAULT_CERT_NOT_BEFORE
        defaultKeyPairShouldBeFound("certNotBefore.greaterThanOrEqual=" + DEFAULT_CERT_NOT_BEFORE);

        // Get all the keyPairList where certNotBefore is greater than or equal to UPDATED_CERT_NOT_BEFORE
        defaultKeyPairShouldNotBeFound("certNotBefore.greaterThanOrEqual=" + UPDATED_CERT_NOT_BEFORE);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotBeforeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotBefore is less than or equal to DEFAULT_CERT_NOT_BEFORE
        defaultKeyPairShouldBeFound("certNotBefore.lessThanOrEqual=" + DEFAULT_CERT_NOT_BEFORE);

        // Get all the keyPairList where certNotBefore is less than or equal to SMALLER_CERT_NOT_BEFORE
        defaultKeyPairShouldNotBeFound("certNotBefore.lessThanOrEqual=" + SMALLER_CERT_NOT_BEFORE);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotBeforeIsLessThanSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotBefore is less than DEFAULT_CERT_NOT_BEFORE
        defaultKeyPairShouldNotBeFound("certNotBefore.lessThan=" + DEFAULT_CERT_NOT_BEFORE);

        // Get all the keyPairList where certNotBefore is less than UPDATED_CERT_NOT_BEFORE
        defaultKeyPairShouldBeFound("certNotBefore.lessThan=" + UPDATED_CERT_NOT_BEFORE);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotBeforeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotBefore is greater than DEFAULT_CERT_NOT_BEFORE
        defaultKeyPairShouldNotBeFound("certNotBefore.greaterThan=" + DEFAULT_CERT_NOT_BEFORE);

        // Get all the keyPairList where certNotBefore is greater than SMALLER_CERT_NOT_BEFORE
        defaultKeyPairShouldBeFound("certNotBefore.greaterThan=" + SMALLER_CERT_NOT_BEFORE);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotAfterIsEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotAfter equals to DEFAULT_CERT_NOT_AFTER
        defaultKeyPairShouldBeFound("certNotAfter.equals=" + DEFAULT_CERT_NOT_AFTER);

        // Get all the keyPairList where certNotAfter equals to UPDATED_CERT_NOT_AFTER
        defaultKeyPairShouldNotBeFound("certNotAfter.equals=" + UPDATED_CERT_NOT_AFTER);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotAfterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotAfter not equals to DEFAULT_CERT_NOT_AFTER
        defaultKeyPairShouldNotBeFound("certNotAfter.notEquals=" + DEFAULT_CERT_NOT_AFTER);

        // Get all the keyPairList where certNotAfter not equals to UPDATED_CERT_NOT_AFTER
        defaultKeyPairShouldBeFound("certNotAfter.notEquals=" + UPDATED_CERT_NOT_AFTER);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotAfterIsInShouldWork() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotAfter in DEFAULT_CERT_NOT_AFTER or UPDATED_CERT_NOT_AFTER
        defaultKeyPairShouldBeFound("certNotAfter.in=" + DEFAULT_CERT_NOT_AFTER + "," + UPDATED_CERT_NOT_AFTER);

        // Get all the keyPairList where certNotAfter equals to UPDATED_CERT_NOT_AFTER
        defaultKeyPairShouldNotBeFound("certNotAfter.in=" + UPDATED_CERT_NOT_AFTER);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotAfterIsNullOrNotNull() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotAfter is not null
        defaultKeyPairShouldBeFound("certNotAfter.specified=true");

        // Get all the keyPairList where certNotAfter is null
        defaultKeyPairShouldNotBeFound("certNotAfter.specified=false");
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotAfterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotAfter is greater than or equal to DEFAULT_CERT_NOT_AFTER
        defaultKeyPairShouldBeFound("certNotAfter.greaterThanOrEqual=" + DEFAULT_CERT_NOT_AFTER);

        // Get all the keyPairList where certNotAfter is greater than or equal to UPDATED_CERT_NOT_AFTER
        defaultKeyPairShouldNotBeFound("certNotAfter.greaterThanOrEqual=" + UPDATED_CERT_NOT_AFTER);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotAfterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotAfter is less than or equal to DEFAULT_CERT_NOT_AFTER
        defaultKeyPairShouldBeFound("certNotAfter.lessThanOrEqual=" + DEFAULT_CERT_NOT_AFTER);

        // Get all the keyPairList where certNotAfter is less than or equal to SMALLER_CERT_NOT_AFTER
        defaultKeyPairShouldNotBeFound("certNotAfter.lessThanOrEqual=" + SMALLER_CERT_NOT_AFTER);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotAfterIsLessThanSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotAfter is less than DEFAULT_CERT_NOT_AFTER
        defaultKeyPairShouldNotBeFound("certNotAfter.lessThan=" + DEFAULT_CERT_NOT_AFTER);

        // Get all the keyPairList where certNotAfter is less than UPDATED_CERT_NOT_AFTER
        defaultKeyPairShouldBeFound("certNotAfter.lessThan=" + UPDATED_CERT_NOT_AFTER);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertNotAfterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certNotAfter is greater than DEFAULT_CERT_NOT_AFTER
        defaultKeyPairShouldNotBeFound("certNotAfter.greaterThan=" + DEFAULT_CERT_NOT_AFTER);

        // Get all the keyPairList where certNotAfter is greater than SMALLER_CERT_NOT_AFTER
        defaultKeyPairShouldBeFound("certNotAfter.greaterThan=" + SMALLER_CERT_NOT_AFTER);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertRevokedIsEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certRevoked equals to DEFAULT_CERT_REVOKED
        defaultKeyPairShouldBeFound("certRevoked.equals=" + DEFAULT_CERT_REVOKED);

        // Get all the keyPairList where certRevoked equals to UPDATED_CERT_REVOKED
        defaultKeyPairShouldNotBeFound("certRevoked.equals=" + UPDATED_CERT_REVOKED);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertRevokedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certRevoked not equals to DEFAULT_CERT_REVOKED
        defaultKeyPairShouldNotBeFound("certRevoked.notEquals=" + DEFAULT_CERT_REVOKED);

        // Get all the keyPairList where certRevoked not equals to UPDATED_CERT_REVOKED
        defaultKeyPairShouldBeFound("certRevoked.notEquals=" + UPDATED_CERT_REVOKED);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertRevokedIsInShouldWork() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certRevoked in DEFAULT_CERT_REVOKED or UPDATED_CERT_REVOKED
        defaultKeyPairShouldBeFound("certRevoked.in=" + DEFAULT_CERT_REVOKED + "," + UPDATED_CERT_REVOKED);

        // Get all the keyPairList where certRevoked equals to UPDATED_CERT_REVOKED
        defaultKeyPairShouldNotBeFound("certRevoked.in=" + UPDATED_CERT_REVOKED);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertRevokedIsNullOrNotNull() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certRevoked is not null
        defaultKeyPairShouldBeFound("certRevoked.specified=true");

        // Get all the keyPairList where certRevoked is null
        defaultKeyPairShouldNotBeFound("certRevoked.specified=false");
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertRevokedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certRevoked is greater than or equal to DEFAULT_CERT_REVOKED
        defaultKeyPairShouldBeFound("certRevoked.greaterThanOrEqual=" + DEFAULT_CERT_REVOKED);

        // Get all the keyPairList where certRevoked is greater than or equal to UPDATED_CERT_REVOKED
        defaultKeyPairShouldNotBeFound("certRevoked.greaterThanOrEqual=" + UPDATED_CERT_REVOKED);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertRevokedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certRevoked is less than or equal to DEFAULT_CERT_REVOKED
        defaultKeyPairShouldBeFound("certRevoked.lessThanOrEqual=" + DEFAULT_CERT_REVOKED);

        // Get all the keyPairList where certRevoked is less than or equal to SMALLER_CERT_REVOKED
        defaultKeyPairShouldNotBeFound("certRevoked.lessThanOrEqual=" + SMALLER_CERT_REVOKED);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertRevokedIsLessThanSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certRevoked is less than DEFAULT_CERT_REVOKED
        defaultKeyPairShouldNotBeFound("certRevoked.lessThan=" + DEFAULT_CERT_REVOKED);

        // Get all the keyPairList where certRevoked is less than UPDATED_CERT_REVOKED
        defaultKeyPairShouldBeFound("certRevoked.lessThan=" + UPDATED_CERT_REVOKED);
    }

    @Test
    @Transactional
    void getAllKeyPairsByCertRevokedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        // Get all the keyPairList where certRevoked is greater than DEFAULT_CERT_REVOKED
        defaultKeyPairShouldNotBeFound("certRevoked.greaterThan=" + DEFAULT_CERT_REVOKED);

        // Get all the keyPairList where certRevoked is greater than SMALLER_CERT_REVOKED
        defaultKeyPairShouldBeFound("certRevoked.greaterThan=" + SMALLER_CERT_REVOKED);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKeyPairShouldBeFound(String filter) throws Exception {
        restKeyPairMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyPair.getId().intValue())))
            .andExpect(jsonPath("$.[*].keyId").value(hasItem(DEFAULT_KEY_ID)))
            .andExpect(jsonPath("$.[*].algorithm").value(hasItem(DEFAULT_ALGORITHM.toString())))
            .andExpect(jsonPath("$.[*].publicKeyContentType").value(hasItem(DEFAULT_PUBLIC_KEY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].publicKey").value(hasItem(Base64Utils.encodeToString(DEFAULT_PUBLIC_KEY))))
            .andExpect(jsonPath("$.[*].privateKeyDescriptor").value(hasItem(DEFAULT_PRIVATE_KEY_DESCRIPTOR.toString())))
            .andExpect(jsonPath("$.[*].attestationDataContentType").value(hasItem(DEFAULT_ATTESTATION_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attestationData").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTESTATION_DATA))))
            .andExpect(jsonPath("$.[*].associatedCertContentType").value(hasItem(DEFAULT_ASSOCIATED_CERT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].associatedCert").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSOCIATED_CERT))))
            .andExpect(jsonPath("$.[*].certificateRequestContentType").value(hasItem(DEFAULT_CERTIFICATE_REQUEST_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].certificateRequest").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICATE_REQUEST))))
            .andExpect(jsonPath("$.[*].certSubjectDN").value(hasItem(DEFAULT_CERT_SUBJECT_DN)))
            .andExpect(jsonPath("$.[*].certIssuerDN").value(hasItem(DEFAULT_CERT_ISSUER_DN)))
            .andExpect(jsonPath("$.[*].certSerial").value(hasItem(DEFAULT_CERT_SERIAL)))
            .andExpect(jsonPath("$.[*].certNotBefore").value(hasItem(sameInstant(DEFAULT_CERT_NOT_BEFORE))))
            .andExpect(jsonPath("$.[*].certNotAfter").value(hasItem(sameInstant(DEFAULT_CERT_NOT_AFTER))))
            .andExpect(jsonPath("$.[*].certRevoked").value(hasItem(sameInstant(DEFAULT_CERT_REVOKED))));

        // Check, that the count call also returns 1
        restKeyPairMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKeyPairShouldNotBeFound(String filter) throws Exception {
        restKeyPairMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKeyPairMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKeyPair() throws Exception {
        // Get the keyPair
        restKeyPairMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKeyPair() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        int databaseSizeBeforeUpdate = keyPairRepository.findAll().size();

        // Update the keyPair
        KeyPair updatedKeyPair = keyPairRepository.findById(keyPair.getId()).get();
        // Disconnect from session so that the updates on updatedKeyPair are not directly saved in db
        em.detach(updatedKeyPair);
        updatedKeyPair
            .keyId(UPDATED_KEY_ID)
            .algorithm(UPDATED_ALGORITHM)
            .publicKey(UPDATED_PUBLIC_KEY)
            .publicKeyContentType(UPDATED_PUBLIC_KEY_CONTENT_TYPE)
            .privateKeyDescriptor(UPDATED_PRIVATE_KEY_DESCRIPTOR)
            .attestationData(UPDATED_ATTESTATION_DATA)
            .attestationDataContentType(UPDATED_ATTESTATION_DATA_CONTENT_TYPE)
            .associatedCert(UPDATED_ASSOCIATED_CERT)
            .associatedCertContentType(UPDATED_ASSOCIATED_CERT_CONTENT_TYPE)
            .certificateRequest(UPDATED_CERTIFICATE_REQUEST)
            .certificateRequestContentType(UPDATED_CERTIFICATE_REQUEST_CONTENT_TYPE)
            .certSubjectDN(UPDATED_CERT_SUBJECT_DN)
            .certIssuerDN(UPDATED_CERT_ISSUER_DN)
            .certSerial(UPDATED_CERT_SERIAL)
            .certNotBefore(UPDATED_CERT_NOT_BEFORE)
            .certNotAfter(UPDATED_CERT_NOT_AFTER)
            .certRevoked(UPDATED_CERT_REVOKED);
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(updatedKeyPair);

        restKeyPairMockMvc
            .perform(
                put(ENTITY_API_URL_ID, keyPairDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(keyPairDTO))
            )
            .andExpect(status().isOk());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeUpdate);
        KeyPair testKeyPair = keyPairList.get(keyPairList.size() - 1);
        assertThat(testKeyPair.getKeyId()).isEqualTo(UPDATED_KEY_ID);
        assertThat(testKeyPair.getAlgorithm()).isEqualTo(UPDATED_ALGORITHM);
        assertThat(testKeyPair.getPublicKey()).isEqualTo(UPDATED_PUBLIC_KEY);
        assertThat(testKeyPair.getPublicKeyContentType()).isEqualTo(UPDATED_PUBLIC_KEY_CONTENT_TYPE);
        assertThat(testKeyPair.getPrivateKeyDescriptor()).isEqualTo(UPDATED_PRIVATE_KEY_DESCRIPTOR);
        assertThat(testKeyPair.getAttestationData()).isEqualTo(UPDATED_ATTESTATION_DATA);
        assertThat(testKeyPair.getAttestationDataContentType()).isEqualTo(UPDATED_ATTESTATION_DATA_CONTENT_TYPE);
        assertThat(testKeyPair.getAssociatedCert()).isEqualTo(UPDATED_ASSOCIATED_CERT);
        assertThat(testKeyPair.getAssociatedCertContentType()).isEqualTo(UPDATED_ASSOCIATED_CERT_CONTENT_TYPE);
        assertThat(testKeyPair.getCertificateRequest()).isEqualTo(UPDATED_CERTIFICATE_REQUEST);
        assertThat(testKeyPair.getCertificateRequestContentType()).isEqualTo(UPDATED_CERTIFICATE_REQUEST_CONTENT_TYPE);
        assertThat(testKeyPair.getCertSubjectDN()).isEqualTo(UPDATED_CERT_SUBJECT_DN);
        assertThat(testKeyPair.getCertIssuerDN()).isEqualTo(UPDATED_CERT_ISSUER_DN);
        assertThat(testKeyPair.getCertSerial()).isEqualTo(UPDATED_CERT_SERIAL);
        assertThat(testKeyPair.getCertNotBefore()).isEqualTo(UPDATED_CERT_NOT_BEFORE);
        assertThat(testKeyPair.getCertNotAfter()).isEqualTo(UPDATED_CERT_NOT_AFTER);
        assertThat(testKeyPair.getCertRevoked()).isEqualTo(UPDATED_CERT_REVOKED);
    }

    @Test
    @Transactional
    void putNonExistingKeyPair() throws Exception {
        int databaseSizeBeforeUpdate = keyPairRepository.findAll().size();
        keyPair.setId(count.incrementAndGet());

        // Create the KeyPair
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeyPairMockMvc
            .perform(
                put(ENTITY_API_URL_ID, keyPairDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(keyPairDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKeyPair() throws Exception {
        int databaseSizeBeforeUpdate = keyPairRepository.findAll().size();
        keyPair.setId(count.incrementAndGet());

        // Create the KeyPair
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeyPairMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(keyPairDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKeyPair() throws Exception {
        int databaseSizeBeforeUpdate = keyPairRepository.findAll().size();
        keyPair.setId(count.incrementAndGet());

        // Create the KeyPair
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeyPairMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(keyPairDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKeyPairWithPatch() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        int databaseSizeBeforeUpdate = keyPairRepository.findAll().size();

        // Update the keyPair using partial update
        KeyPair partialUpdatedKeyPair = new KeyPair();
        partialUpdatedKeyPair.setId(keyPair.getId());

        partialUpdatedKeyPair
            .publicKey(UPDATED_PUBLIC_KEY)
            .publicKeyContentType(UPDATED_PUBLIC_KEY_CONTENT_TYPE)
            .privateKeyDescriptor(UPDATED_PRIVATE_KEY_DESCRIPTOR)
            .associatedCert(UPDATED_ASSOCIATED_CERT)
            .associatedCertContentType(UPDATED_ASSOCIATED_CERT_CONTENT_TYPE)
            .certificateRequest(UPDATED_CERTIFICATE_REQUEST)
            .certificateRequestContentType(UPDATED_CERTIFICATE_REQUEST_CONTENT_TYPE)
            .certIssuerDN(UPDATED_CERT_ISSUER_DN)
            .certSerial(UPDATED_CERT_SERIAL)
            .certNotAfter(UPDATED_CERT_NOT_AFTER)
            .certRevoked(UPDATED_CERT_REVOKED);

        restKeyPairMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKeyPair.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKeyPair))
            )
            .andExpect(status().isOk());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeUpdate);
        KeyPair testKeyPair = keyPairList.get(keyPairList.size() - 1);
        assertThat(testKeyPair.getKeyId()).isEqualTo(DEFAULT_KEY_ID);
        assertThat(testKeyPair.getAlgorithm()).isEqualTo(DEFAULT_ALGORITHM);
        assertThat(testKeyPair.getPublicKey()).isEqualTo(UPDATED_PUBLIC_KEY);
        assertThat(testKeyPair.getPublicKeyContentType()).isEqualTo(UPDATED_PUBLIC_KEY_CONTENT_TYPE);
        assertThat(testKeyPair.getPrivateKeyDescriptor()).isEqualTo(UPDATED_PRIVATE_KEY_DESCRIPTOR);
        assertThat(testKeyPair.getAttestationData()).isEqualTo(DEFAULT_ATTESTATION_DATA);
        assertThat(testKeyPair.getAttestationDataContentType()).isEqualTo(DEFAULT_ATTESTATION_DATA_CONTENT_TYPE);
        assertThat(testKeyPair.getAssociatedCert()).isEqualTo(UPDATED_ASSOCIATED_CERT);
        assertThat(testKeyPair.getAssociatedCertContentType()).isEqualTo(UPDATED_ASSOCIATED_CERT_CONTENT_TYPE);
        assertThat(testKeyPair.getCertificateRequest()).isEqualTo(UPDATED_CERTIFICATE_REQUEST);
        assertThat(testKeyPair.getCertificateRequestContentType()).isEqualTo(UPDATED_CERTIFICATE_REQUEST_CONTENT_TYPE);
        assertThat(testKeyPair.getCertSubjectDN()).isEqualTo(DEFAULT_CERT_SUBJECT_DN);
        assertThat(testKeyPair.getCertIssuerDN()).isEqualTo(UPDATED_CERT_ISSUER_DN);
        assertThat(testKeyPair.getCertSerial()).isEqualTo(UPDATED_CERT_SERIAL);
        assertThat(testKeyPair.getCertNotBefore()).isEqualTo(DEFAULT_CERT_NOT_BEFORE);
        assertThat(testKeyPair.getCertNotAfter()).isEqualTo(UPDATED_CERT_NOT_AFTER);
        assertThat(testKeyPair.getCertRevoked()).isEqualTo(UPDATED_CERT_REVOKED);
    }

    @Test
    @Transactional
    void fullUpdateKeyPairWithPatch() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        int databaseSizeBeforeUpdate = keyPairRepository.findAll().size();

        // Update the keyPair using partial update
        KeyPair partialUpdatedKeyPair = new KeyPair();
        partialUpdatedKeyPair.setId(keyPair.getId());

        partialUpdatedKeyPair
            .keyId(UPDATED_KEY_ID)
            .algorithm(UPDATED_ALGORITHM)
            .publicKey(UPDATED_PUBLIC_KEY)
            .publicKeyContentType(UPDATED_PUBLIC_KEY_CONTENT_TYPE)
            .privateKeyDescriptor(UPDATED_PRIVATE_KEY_DESCRIPTOR)
            .attestationData(UPDATED_ATTESTATION_DATA)
            .attestationDataContentType(UPDATED_ATTESTATION_DATA_CONTENT_TYPE)
            .associatedCert(UPDATED_ASSOCIATED_CERT)
            .associatedCertContentType(UPDATED_ASSOCIATED_CERT_CONTENT_TYPE)
            .certificateRequest(UPDATED_CERTIFICATE_REQUEST)
            .certificateRequestContentType(UPDATED_CERTIFICATE_REQUEST_CONTENT_TYPE)
            .certSubjectDN(UPDATED_CERT_SUBJECT_DN)
            .certIssuerDN(UPDATED_CERT_ISSUER_DN)
            .certSerial(UPDATED_CERT_SERIAL)
            .certNotBefore(UPDATED_CERT_NOT_BEFORE)
            .certNotAfter(UPDATED_CERT_NOT_AFTER)
            .certRevoked(UPDATED_CERT_REVOKED);

        restKeyPairMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKeyPair.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKeyPair))
            )
            .andExpect(status().isOk());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeUpdate);
        KeyPair testKeyPair = keyPairList.get(keyPairList.size() - 1);
        assertThat(testKeyPair.getKeyId()).isEqualTo(UPDATED_KEY_ID);
        assertThat(testKeyPair.getAlgorithm()).isEqualTo(UPDATED_ALGORITHM);
        assertThat(testKeyPair.getPublicKey()).isEqualTo(UPDATED_PUBLIC_KEY);
        assertThat(testKeyPair.getPublicKeyContentType()).isEqualTo(UPDATED_PUBLIC_KEY_CONTENT_TYPE);
        assertThat(testKeyPair.getPrivateKeyDescriptor()).isEqualTo(UPDATED_PRIVATE_KEY_DESCRIPTOR);
        assertThat(testKeyPair.getAttestationData()).isEqualTo(UPDATED_ATTESTATION_DATA);
        assertThat(testKeyPair.getAttestationDataContentType()).isEqualTo(UPDATED_ATTESTATION_DATA_CONTENT_TYPE);
        assertThat(testKeyPair.getAssociatedCert()).isEqualTo(UPDATED_ASSOCIATED_CERT);
        assertThat(testKeyPair.getAssociatedCertContentType()).isEqualTo(UPDATED_ASSOCIATED_CERT_CONTENT_TYPE);
        assertThat(testKeyPair.getCertificateRequest()).isEqualTo(UPDATED_CERTIFICATE_REQUEST);
        assertThat(testKeyPair.getCertificateRequestContentType()).isEqualTo(UPDATED_CERTIFICATE_REQUEST_CONTENT_TYPE);
        assertThat(testKeyPair.getCertSubjectDN()).isEqualTo(UPDATED_CERT_SUBJECT_DN);
        assertThat(testKeyPair.getCertIssuerDN()).isEqualTo(UPDATED_CERT_ISSUER_DN);
        assertThat(testKeyPair.getCertSerial()).isEqualTo(UPDATED_CERT_SERIAL);
        assertThat(testKeyPair.getCertNotBefore()).isEqualTo(UPDATED_CERT_NOT_BEFORE);
        assertThat(testKeyPair.getCertNotAfter()).isEqualTo(UPDATED_CERT_NOT_AFTER);
        assertThat(testKeyPair.getCertRevoked()).isEqualTo(UPDATED_CERT_REVOKED);
    }

    @Test
    @Transactional
    void patchNonExistingKeyPair() throws Exception {
        int databaseSizeBeforeUpdate = keyPairRepository.findAll().size();
        keyPair.setId(count.incrementAndGet());

        // Create the KeyPair
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeyPairMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, keyPairDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(keyPairDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKeyPair() throws Exception {
        int databaseSizeBeforeUpdate = keyPairRepository.findAll().size();
        keyPair.setId(count.incrementAndGet());

        // Create the KeyPair
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeyPairMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(keyPairDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKeyPair() throws Exception {
        int databaseSizeBeforeUpdate = keyPairRepository.findAll().size();
        keyPair.setId(count.incrementAndGet());

        // Create the KeyPair
        KeyPairDTO keyPairDTO = keyPairMapper.toDto(keyPair);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeyPairMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(keyPairDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KeyPair in the database
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKeyPair() throws Exception {
        // Initialize the database
        keyPairRepository.saveAndFlush(keyPair);

        int databaseSizeBeforeDelete = keyPairRepository.findAll().size();

        // Delete the keyPair
        restKeyPairMockMvc
            .perform(delete(ENTITY_API_URL_ID, keyPair.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KeyPair> keyPairList = keyPairRepository.findAll();
        assertThat(keyPairList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
