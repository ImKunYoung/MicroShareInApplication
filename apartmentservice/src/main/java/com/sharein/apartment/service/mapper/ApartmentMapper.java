package com.sharein.apartment.service.mapper;

import com.sharein.apartment.domain.Apartment;
import com.sharein.apartment.service.dto.ApartmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Apartment} and its DTO {@link ApartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApartmentMapper extends EntityMapper<ApartmentDTO, Apartment> {}
