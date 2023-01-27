package com.sharein.apartment.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sharein.apartment.domain.Apartment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApartmentDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApartmentDTO)) {
            return false;
        }

        ApartmentDTO apartmentDTO = (ApartmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, apartmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApartmentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
