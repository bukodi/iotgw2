package hu.noreg.iotgw2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hu.noreg.iotgw2.IntegrationTest;
import hu.noreg.iotgw2.domain.OrgUnit;
import hu.noreg.iotgw2.domain.OrgUnit;
import hu.noreg.iotgw2.repository.OrgUnitRepository;
import hu.noreg.iotgw2.service.criteria.OrgUnitCriteria;
import hu.noreg.iotgw2.service.dto.OrgUnitDTO;
import hu.noreg.iotgw2.service.mapper.OrgUnitMapper;
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
 * Integration tests for the {@link OrgUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrgUnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_READ_AUTH_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_READ_AUTH_PATTERN = "BBBBBBBBBB";

    private static final String DEFAULT_WRITE_AUTH_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_WRITE_AUTH_PATTERN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/org-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrgUnitRepository orgUnitRepository;

    @Autowired
    private OrgUnitMapper orgUnitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrgUnitMockMvc;

    private OrgUnit orgUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgUnit createEntity(EntityManager em) {
        OrgUnit orgUnit = new OrgUnit()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .readAuthPattern(DEFAULT_READ_AUTH_PATTERN)
            .writeAuthPattern(DEFAULT_WRITE_AUTH_PATTERN);
        return orgUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgUnit createUpdatedEntity(EntityManager em) {
        OrgUnit orgUnit = new OrgUnit()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);
        return orgUnit;
    }

    @BeforeEach
    public void initTest() {
        orgUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createOrgUnit() throws Exception {
        int databaseSizeBeforeCreate = orgUnitRepository.findAll().size();
        // Create the OrgUnit
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(orgUnit);
        restOrgUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeCreate + 1);
        OrgUnit testOrgUnit = orgUnitList.get(orgUnitList.size() - 1);
        assertThat(testOrgUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrgUnit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrgUnit.getReadAuthPattern()).isEqualTo(DEFAULT_READ_AUTH_PATTERN);
        assertThat(testOrgUnit.getWriteAuthPattern()).isEqualTo(DEFAULT_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void createOrgUnitWithExistingId() throws Exception {
        // Create the OrgUnit with an existing ID
        orgUnit.setId(1L);
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(orgUnit);

        int databaseSizeBeforeCreate = orgUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = orgUnitRepository.findAll().size();
        // set the field null
        orgUnit.setName(null);

        // Create the OrgUnit, which fails.
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(orgUnit);

        restOrgUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgUnitDTO)))
            .andExpect(status().isBadRequest());

        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrgUnits() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList
        restOrgUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].readAuthPattern").value(hasItem(DEFAULT_READ_AUTH_PATTERN)))
            .andExpect(jsonPath("$.[*].writeAuthPattern").value(hasItem(DEFAULT_WRITE_AUTH_PATTERN)));
    }

    @Test
    @Transactional
    void getOrgUnit() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get the orgUnit
        restOrgUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, orgUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orgUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.readAuthPattern").value(DEFAULT_READ_AUTH_PATTERN))
            .andExpect(jsonPath("$.writeAuthPattern").value(DEFAULT_WRITE_AUTH_PATTERN));
    }

    @Test
    @Transactional
    void getOrgUnitsByIdFiltering() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        Long id = orgUnit.getId();

        defaultOrgUnitShouldBeFound("id.equals=" + id);
        defaultOrgUnitShouldNotBeFound("id.notEquals=" + id);

        defaultOrgUnitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrgUnitShouldNotBeFound("id.greaterThan=" + id);

        defaultOrgUnitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrgUnitShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where name equals to DEFAULT_NAME
        defaultOrgUnitShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the orgUnitList where name equals to UPDATED_NAME
        defaultOrgUnitShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where name not equals to DEFAULT_NAME
        defaultOrgUnitShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the orgUnitList where name not equals to UPDATED_NAME
        defaultOrgUnitShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrgUnitShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the orgUnitList where name equals to UPDATED_NAME
        defaultOrgUnitShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where name is not null
        defaultOrgUnitShouldBeFound("name.specified=true");

        // Get all the orgUnitList where name is null
        defaultOrgUnitShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgUnitsByNameContainsSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where name contains DEFAULT_NAME
        defaultOrgUnitShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the orgUnitList where name contains UPDATED_NAME
        defaultOrgUnitShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where name does not contain DEFAULT_NAME
        defaultOrgUnitShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the orgUnitList where name does not contain UPDATED_NAME
        defaultOrgUnitShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where description equals to DEFAULT_DESCRIPTION
        defaultOrgUnitShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the orgUnitList where description equals to UPDATED_DESCRIPTION
        defaultOrgUnitShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where description not equals to DEFAULT_DESCRIPTION
        defaultOrgUnitShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the orgUnitList where description not equals to UPDATED_DESCRIPTION
        defaultOrgUnitShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOrgUnitShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the orgUnitList where description equals to UPDATED_DESCRIPTION
        defaultOrgUnitShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where description is not null
        defaultOrgUnitShouldBeFound("description.specified=true");

        // Get all the orgUnitList where description is null
        defaultOrgUnitShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgUnitsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where description contains DEFAULT_DESCRIPTION
        defaultOrgUnitShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the orgUnitList where description contains UPDATED_DESCRIPTION
        defaultOrgUnitShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where description does not contain DEFAULT_DESCRIPTION
        defaultOrgUnitShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the orgUnitList where description does not contain UPDATED_DESCRIPTION
        defaultOrgUnitShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByReadAuthPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where readAuthPattern equals to DEFAULT_READ_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("readAuthPattern.equals=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the orgUnitList where readAuthPattern equals to UPDATED_READ_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("readAuthPattern.equals=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByReadAuthPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where readAuthPattern not equals to DEFAULT_READ_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("readAuthPattern.notEquals=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the orgUnitList where readAuthPattern not equals to UPDATED_READ_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("readAuthPattern.notEquals=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByReadAuthPatternIsInShouldWork() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where readAuthPattern in DEFAULT_READ_AUTH_PATTERN or UPDATED_READ_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("readAuthPattern.in=" + DEFAULT_READ_AUTH_PATTERN + "," + UPDATED_READ_AUTH_PATTERN);

        // Get all the orgUnitList where readAuthPattern equals to UPDATED_READ_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("readAuthPattern.in=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByReadAuthPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where readAuthPattern is not null
        defaultOrgUnitShouldBeFound("readAuthPattern.specified=true");

        // Get all the orgUnitList where readAuthPattern is null
        defaultOrgUnitShouldNotBeFound("readAuthPattern.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgUnitsByReadAuthPatternContainsSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where readAuthPattern contains DEFAULT_READ_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("readAuthPattern.contains=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the orgUnitList where readAuthPattern contains UPDATED_READ_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("readAuthPattern.contains=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByReadAuthPatternNotContainsSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where readAuthPattern does not contain DEFAULT_READ_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("readAuthPattern.doesNotContain=" + DEFAULT_READ_AUTH_PATTERN);

        // Get all the orgUnitList where readAuthPattern does not contain UPDATED_READ_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("readAuthPattern.doesNotContain=" + UPDATED_READ_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByWriteAuthPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where writeAuthPattern equals to DEFAULT_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("writeAuthPattern.equals=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the orgUnitList where writeAuthPattern equals to UPDATED_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("writeAuthPattern.equals=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByWriteAuthPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where writeAuthPattern not equals to DEFAULT_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("writeAuthPattern.notEquals=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the orgUnitList where writeAuthPattern not equals to UPDATED_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("writeAuthPattern.notEquals=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByWriteAuthPatternIsInShouldWork() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where writeAuthPattern in DEFAULT_WRITE_AUTH_PATTERN or UPDATED_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("writeAuthPattern.in=" + DEFAULT_WRITE_AUTH_PATTERN + "," + UPDATED_WRITE_AUTH_PATTERN);

        // Get all the orgUnitList where writeAuthPattern equals to UPDATED_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("writeAuthPattern.in=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByWriteAuthPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where writeAuthPattern is not null
        defaultOrgUnitShouldBeFound("writeAuthPattern.specified=true");

        // Get all the orgUnitList where writeAuthPattern is null
        defaultOrgUnitShouldNotBeFound("writeAuthPattern.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgUnitsByWriteAuthPatternContainsSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where writeAuthPattern contains DEFAULT_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("writeAuthPattern.contains=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the orgUnitList where writeAuthPattern contains UPDATED_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("writeAuthPattern.contains=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsByWriteAuthPatternNotContainsSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        // Get all the orgUnitList where writeAuthPattern does not contain DEFAULT_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldNotBeFound("writeAuthPattern.doesNotContain=" + DEFAULT_WRITE_AUTH_PATTERN);

        // Get all the orgUnitList where writeAuthPattern does not contain UPDATED_WRITE_AUTH_PATTERN
        defaultOrgUnitShouldBeFound("writeAuthPattern.doesNotContain=" + UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void getAllOrgUnitsBySubUnitsIsEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);
        OrgUnit subUnits = OrgUnitResourceIT.createEntity(em);
        em.persist(subUnits);
        em.flush();
        orgUnit.addSubUnits(subUnits);
        orgUnitRepository.saveAndFlush(orgUnit);
        Long subUnitsId = subUnits.getId();

        // Get all the orgUnitList where subUnits equals to subUnitsId
        defaultOrgUnitShouldBeFound("subUnitsId.equals=" + subUnitsId);

        // Get all the orgUnitList where subUnits equals to (subUnitsId + 1)
        defaultOrgUnitShouldNotBeFound("subUnitsId.equals=" + (subUnitsId + 1));
    }

    @Test
    @Transactional
    void getAllOrgUnitsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);
        OrgUnit parent = OrgUnitResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        orgUnit.setParent(parent);
        orgUnitRepository.saveAndFlush(orgUnit);
        Long parentId = parent.getId();

        // Get all the orgUnitList where parent equals to parentId
        defaultOrgUnitShouldBeFound("parentId.equals=" + parentId);

        // Get all the orgUnitList where parent equals to (parentId + 1)
        defaultOrgUnitShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrgUnitShouldBeFound(String filter) throws Exception {
        restOrgUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].readAuthPattern").value(hasItem(DEFAULT_READ_AUTH_PATTERN)))
            .andExpect(jsonPath("$.[*].writeAuthPattern").value(hasItem(DEFAULT_WRITE_AUTH_PATTERN)));

        // Check, that the count call also returns 1
        restOrgUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrgUnitShouldNotBeFound(String filter) throws Exception {
        restOrgUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrgUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrgUnit() throws Exception {
        // Get the orgUnit
        restOrgUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrgUnit() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        int databaseSizeBeforeUpdate = orgUnitRepository.findAll().size();

        // Update the orgUnit
        OrgUnit updatedOrgUnit = orgUnitRepository.findById(orgUnit.getId()).get();
        // Disconnect from session so that the updates on updatedOrgUnit are not directly saved in db
        em.detach(updatedOrgUnit);
        updatedOrgUnit
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(updatedOrgUnit);

        restOrgUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeUpdate);
        OrgUnit testOrgUnit = orgUnitList.get(orgUnitList.size() - 1);
        assertThat(testOrgUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrgUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrgUnit.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testOrgUnit.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void putNonExistingOrgUnit() throws Exception {
        int databaseSizeBeforeUpdate = orgUnitRepository.findAll().size();
        orgUnit.setId(count.incrementAndGet());

        // Create the OrgUnit
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(orgUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrgUnit() throws Exception {
        int databaseSizeBeforeUpdate = orgUnitRepository.findAll().size();
        orgUnit.setId(count.incrementAndGet());

        // Create the OrgUnit
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(orgUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrgUnit() throws Exception {
        int databaseSizeBeforeUpdate = orgUnitRepository.findAll().size();
        orgUnit.setId(count.incrementAndGet());

        // Create the OrgUnit
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(orgUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgUnitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgUnitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrgUnitWithPatch() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        int databaseSizeBeforeUpdate = orgUnitRepository.findAll().size();

        // Update the orgUnit using partial update
        OrgUnit partialUpdatedOrgUnit = new OrgUnit();
        partialUpdatedOrgUnit.setId(orgUnit.getId());

        partialUpdatedOrgUnit
            .description(UPDATED_DESCRIPTION)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);

        restOrgUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgUnit))
            )
            .andExpect(status().isOk());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeUpdate);
        OrgUnit testOrgUnit = orgUnitList.get(orgUnitList.size() - 1);
        assertThat(testOrgUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrgUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrgUnit.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testOrgUnit.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void fullUpdateOrgUnitWithPatch() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        int databaseSizeBeforeUpdate = orgUnitRepository.findAll().size();

        // Update the orgUnit using partial update
        OrgUnit partialUpdatedOrgUnit = new OrgUnit();
        partialUpdatedOrgUnit.setId(orgUnit.getId());

        partialUpdatedOrgUnit
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .readAuthPattern(UPDATED_READ_AUTH_PATTERN)
            .writeAuthPattern(UPDATED_WRITE_AUTH_PATTERN);

        restOrgUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgUnit))
            )
            .andExpect(status().isOk());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeUpdate);
        OrgUnit testOrgUnit = orgUnitList.get(orgUnitList.size() - 1);
        assertThat(testOrgUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrgUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrgUnit.getReadAuthPattern()).isEqualTo(UPDATED_READ_AUTH_PATTERN);
        assertThat(testOrgUnit.getWriteAuthPattern()).isEqualTo(UPDATED_WRITE_AUTH_PATTERN);
    }

    @Test
    @Transactional
    void patchNonExistingOrgUnit() throws Exception {
        int databaseSizeBeforeUpdate = orgUnitRepository.findAll().size();
        orgUnit.setId(count.incrementAndGet());

        // Create the OrgUnit
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(orgUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orgUnitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrgUnit() throws Exception {
        int databaseSizeBeforeUpdate = orgUnitRepository.findAll().size();
        orgUnit.setId(count.incrementAndGet());

        // Create the OrgUnit
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(orgUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrgUnit() throws Exception {
        int databaseSizeBeforeUpdate = orgUnitRepository.findAll().size();
        orgUnit.setId(count.incrementAndGet());

        // Create the OrgUnit
        OrgUnitDTO orgUnitDTO = orgUnitMapper.toDto(orgUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgUnitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orgUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrgUnit in the database
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrgUnit() throws Exception {
        // Initialize the database
        orgUnitRepository.saveAndFlush(orgUnit);

        int databaseSizeBeforeDelete = orgUnitRepository.findAll().size();

        // Delete the orgUnit
        restOrgUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, orgUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrgUnit> orgUnitList = orgUnitRepository.findAll();
        assertThat(orgUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
