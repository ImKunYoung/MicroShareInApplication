package com.sharein.client.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sharein.client.domain.Client} entity. This class is used
 * in {@link com.sharein.client.web.rest.ClientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter nickname;

    private StringFilter email;

    private StringFilter password;

    private StringFilter phone;

    private LongFilter apartmentid;

    private StringFilter apartmentname;

    private Boolean distinct;

    public ClientCriteria() {}

    public ClientCriteria(ClientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.nickname = other.nickname == null ? null : other.nickname.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.apartmentid = other.apartmentid == null ? null : other.apartmentid.copy();
        this.apartmentname = other.apartmentname == null ? null : other.apartmentname.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClientCriteria copy() {
        return new ClientCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getNickname() {
        return nickname;
    }

    public StringFilter nickname() {
        if (nickname == null) {
            nickname = new StringFilter();
        }
        return nickname;
    }

    public void setNickname(StringFilter nickname) {
        this.nickname = nickname;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPassword() {
        return password;
    }

    public StringFilter password() {
        if (password == null) {
            password = new StringFilter();
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public LongFilter getApartmentid() {
        return apartmentid;
    }

    public LongFilter apartmentid() {
        if (apartmentid == null) {
            apartmentid = new LongFilter();
        }
        return apartmentid;
    }

    public void setApartmentid(LongFilter apartmentid) {
        this.apartmentid = apartmentid;
    }

    public StringFilter getApartmentname() {
        return apartmentname;
    }

    public StringFilter apartmentname() {
        if (apartmentname == null) {
            apartmentname = new StringFilter();
        }
        return apartmentname;
    }

    public void setApartmentname(StringFilter apartmentname) {
        this.apartmentname = apartmentname;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClientCriteria that = (ClientCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(nickname, that.nickname) &&
            Objects.equals(email, that.email) &&
            Objects.equals(password, that.password) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(apartmentid, that.apartmentid) &&
            Objects.equals(apartmentname, that.apartmentname) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nickname, email, password, phone, apartmentid, apartmentname, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (nickname != null ? "nickname=" + nickname + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (password != null ? "password=" + password + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (apartmentid != null ? "apartmentid=" + apartmentid + ", " : "") +
            (apartmentname != null ? "apartmentname=" + apartmentname + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
