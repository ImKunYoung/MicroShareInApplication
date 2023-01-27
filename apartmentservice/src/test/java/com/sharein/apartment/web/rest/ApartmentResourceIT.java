package com.sharein.apartment.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sharein.apartment.IntegrationTest;
import com.sharein.apartment.domain.Apartment;
import com.sharein.apartment.repository.ApartmentRepository;
import com.sharein.apartment.service.criteria.ApartmentCriteria;
import com.sharein.apartment.service.dto.ApartmentDTO;
import com.sharein.apartment.service.mapper.ApartmentMapper;
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
 * Integration tests for the {@link ApartmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApartmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/apartments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ApartmentMapper apartmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApartmentMockMvc;

    private Apartment apartment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apartment createEntity(EntityManager em) {
        Apartment apartment = new Apartment().name(DEFAULT_NAME).address(DEFAULT_ADDRESS);
        return apartment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apartment createUpdatedEntity(EntityManager em) {
        Apartment apartment = new Apartment().name(UPDATED_NAME).address(UPDATED_ADDRESS);
        return apartment;
    }

    @BeforeEach
    public void initTest() {
        apartment = createEntity(em);
    }

    @Test
    @Transactional
    void createApartment() throws Exception {
        int databaseSizeBeforeCreate = apartmentRepository.findAll().size();
        // Create the Apartment
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(apartment);
        restApartmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apartmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeCreate + 1);
        Apartment testApartment = apartmentList.get(apartmentList.size() - 1);
        assertThat(testApartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApartment.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void createApartmentWithExistingId() throws Exception {
        // Create the Apartment with an existing ID
        apartment.setId(1L);
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(apartment);

        int databaseSizeBeforeCreate = apartmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApartmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apartmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApartments() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList
        restApartmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    @Transactional
    void getApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get the apartment
        restApartmentMockMvc
            .perform(get(ENTITY_API_URL_ID, apartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apartment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    void getApartmentsByIdFiltering() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        Long id = apartment.getId();

        defaultApartmentShouldBeFound("id.equals=" + id);
        defaultApartmentShouldNotBeFound("id.notEquals=" + id);

        defaultApartmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApartmentShouldNotBeFound("id.greaterThan=" + id);

        defaultApartmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApartmentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApartmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where name equals to DEFAULT_NAME
        defaultApartmentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the apartmentList where name equals to UPDATED_NAME
        defaultApartmentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllApartmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultApartmentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the apartmentList where name equals to UPDATED_NAME
        defaultApartmentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllApartmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where name is not null
        defaultApartmentShouldBeFound("name.specified=true");

        // Get all the apartmentList where name is null
        defaultApartmentShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllApartmentsByNameContainsSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where name contains DEFAULT_NAME
        defaultApartmentShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the apartmentList where name contains UPDATED_NAME
        defaultApartmentShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllApartmentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where name does not contain DEFAULT_NAME
        defaultApartmentShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the apartmentList where name does not contain UPDATED_NAME
        defaultApartmentShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllApartmentsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where address equals to DEFAULT_ADDRESS
        defaultApartmentShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the apartmentList where address equals to UPDATED_ADDRESS
        defaultApartmentShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllApartmentsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultApartmentShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the apartmentList where address equals to UPDATED_ADDRESS
        defaultApartmentShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllApartmentsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where address is not null
        defaultApartmentShouldBeFound("address.specified=true");

        // Get all the apartmentList where address is null
        defaultApartmentShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllApartmentsByAddressContainsSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where address contains DEFAULT_ADDRESS
        defaultApartmentShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the apartmentList where address contains UPDATED_ADDRESS
        defaultApartmentShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllApartmentsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where address does not contain DEFAULT_ADDRESS
        defaultApartmentShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the apartmentList where address does not contain UPDATED_ADDRESS
        defaultApartmentShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApartmentShouldBeFound(String filter) throws Exception {
        restApartmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));

        // Check, that the count call also returns 1
        restApartmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApartmentShouldNotBeFound(String filter) throws Exception {
        restApartmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApartmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApartment() throws Exception {
        // Get the apartment
        restApartmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();

        // Update the apartment
        Apartment updatedApartment = apartmentRepository.findById(apartment.getId()).get();
        // Disconnect from session so that the updates on updatedApartment are not directly saved in db
        em.detach(updatedApartment);
        updatedApartment.name(UPDATED_NAME).address(UPDATED_ADDRESS);
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(updatedApartment);

        restApartmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apartmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apartmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
        Apartment testApartment = apartmentList.get(apartmentList.size() - 1);
        assertThat(testApartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApartment.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void putNonExistingApartment() throws Exception {
        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();
        apartment.setId(count.incrementAndGet());

        // Create the Apartment
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(apartment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApartmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apartmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apartmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApartment() throws Exception {
        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();
        apartment.setId(count.incrementAndGet());

        // Create the Apartment
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(apartment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApartmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apartmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApartment() throws Exception {
        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();
        apartment.setId(count.incrementAndGet());

        // Create the Apartment
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(apartment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApartmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apartmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApartmentWithPatch() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();

        // Update the apartment using partial update
        Apartment partialUpdatedApartment = new Apartment();
        partialUpdatedApartment.setId(apartment.getId());

        partialUpdatedApartment.address(UPDATED_ADDRESS);

        restApartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApartment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApartment))
            )
            .andExpect(status().isOk());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
        Apartment testApartment = apartmentList.get(apartmentList.size() - 1);
        assertThat(testApartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApartment.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void fullUpdateApartmentWithPatch() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();

        // Update the apartment using partial update
        Apartment partialUpdatedApartment = new Apartment();
        partialUpdatedApartment.setId(apartment.getId());

        partialUpdatedApartment.name(UPDATED_NAME).address(UPDATED_ADDRESS);

        restApartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApartment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApartment))
            )
            .andExpect(status().isOk());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
        Apartment testApartment = apartmentList.get(apartmentList.size() - 1);
        assertThat(testApartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApartment.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void patchNonExistingApartment() throws Exception {
        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();
        apartment.setId(count.incrementAndGet());

        // Create the Apartment
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(apartment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apartmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apartmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApartment() throws Exception {
        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();
        apartment.setId(count.incrementAndGet());

        // Create the Apartment
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(apartment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apartmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApartment() throws Exception {
        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();
        apartment.setId(count.incrementAndGet());

        // Create the Apartment
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(apartment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApartmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(apartmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        int databaseSizeBeforeDelete = apartmentRepository.findAll().size();

        // Delete the apartment
        restApartmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, apartment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
