package com.sharein.apartment.service.impl;

import com.sharein.apartment.domain.Apartment;
import com.sharein.apartment.repository.ApartmentRepository;
import com.sharein.apartment.service.ApartmentService;
import com.sharein.apartment.service.dto.ApartmentDTO;
import com.sharein.apartment.service.mapper.ApartmentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Apartment}.
 */
@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService {

    private final Logger log = LoggerFactory.getLogger(ApartmentServiceImpl.class);

    private final ApartmentRepository apartmentRepository;

    private final ApartmentMapper apartmentMapper;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ApartmentMapper apartmentMapper) {
        this.apartmentRepository = apartmentRepository;
        this.apartmentMapper = apartmentMapper;
    }

    @Override
    public ApartmentDTO save(ApartmentDTO apartmentDTO) {
        log.debug("Request to save Apartment : {}", apartmentDTO);
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        apartment = apartmentRepository.save(apartment);
        return apartmentMapper.toDto(apartment);
    }

    @Override
    public ApartmentDTO update(ApartmentDTO apartmentDTO) {
        log.debug("Request to update Apartment : {}", apartmentDTO);
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        apartment = apartmentRepository.save(apartment);
        return apartmentMapper.toDto(apartment);
    }

    @Override
    public Optional<ApartmentDTO> partialUpdate(ApartmentDTO apartmentDTO) {
        log.debug("Request to partially update Apartment : {}", apartmentDTO);

        return apartmentRepository
            .findById(apartmentDTO.getId())
            .map(existingApartment -> {
                apartmentMapper.partialUpdate(existingApartment, apartmentDTO);

                return existingApartment;
            })
            .map(apartmentRepository::save)
            .map(apartmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApartmentDTO> findAll() {
        log.debug("Request to get all Apartments");
        return apartmentRepository.findAll().stream().map(apartmentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApartmentDTO> findOne(Long id) {
        log.debug("Request to get Apartment : {}", id);
        return apartmentRepository.findById(id).map(apartmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Apartment : {}", id);
        apartmentRepository.deleteById(id);
    }
}
