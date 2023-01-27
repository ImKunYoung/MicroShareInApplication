package com.sharein.apartment.repository;

import com.sharein.apartment.domain.Apartment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Apartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>, JpaSpecificationExecutor<Apartment> {}
