package com.sharein.client.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sharein.client.domain.Client} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientDTO implements Serializable {

    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String password;

    private String phone;

    private Long apartmentid;

    private String apartmentname;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getApartmentid() {
        return apartmentid;
    }

    public void setApartmentid(Long apartmentid) {
        this.apartmentid = apartmentid;
    }

    public String getApartmentname() {
        return apartmentname;
    }

    public void setApartmentname(String apartmentname) {
        this.apartmentname = apartmentname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nickname='" + getNickname() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", phone='" + getPhone() + "'" +
            ", apartmentid=" + getApartmentid() +
            ", apartmentname='" + getApartmentname() + "'" +
            "}";
    }
}
