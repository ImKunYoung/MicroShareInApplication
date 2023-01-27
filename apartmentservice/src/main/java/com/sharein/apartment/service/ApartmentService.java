package com.sharein.apartment.service;

import com.sharein.apartment.service.dto.ApartmentDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sharein.apartment.domain.Apartment}.
 */
public interface ApartmentService {
    /**
     * Save a apartment.
     *
     * @param apartmentDTO the entity to save.
     * @return the persisted entity.
     */
    ApartmentDTO save(ApartmentDTO apartmentDTO);

    /**
     * Updates a apartment.
     *
     * @param apartmentDTO the entity to update.
     * @return the persisted entity.
     */
    ApartmentDTO update(ApartmentDTO apartmentDTO);

    /**
     * Partially updates a apartment.
     *
     * @param apartmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApartmentDTO> partialUpdate(ApartmentDTO apartmentDTO);

    /**
     * Get all the apartments.
     *
     * @return the list of entities.
     */
    List<ApartmentDTO> findAll();

    /**
     * Get the "id" apartment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApartmentDTO> findOne(Long id);

    /**
     * Delete the "id" apartment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
