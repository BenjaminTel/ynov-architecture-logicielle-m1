package com.ubik.formation.userservice.entity;

import com.ubik.formation.userservice.enumeration.MemberShipType;
import jakarta.persistence.*;

@Entity
@Table(name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private MemberShipType membershipType;
    private boolean isLocked;
    private Integer nombreMaxEmprunt;

    public User() {
    }

    public User(Long id, String name, String email, MemberShipType membershipType, boolean isLocked, Integer nombreMaxEmprunt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.membershipType = membershipType;
        this.isLocked = isLocked;
        this.nombreMaxEmprunt = nombreMaxEmprunt;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MemberShipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MemberShipType membershipType) {
        this.membershipType = membershipType;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Integer getNombreMaxEmprunt() {
        return nombreMaxEmprunt;
    }

    public void setNombreMaxEmprunt(Integer nombreMaxEmprunt) {
        this.nombreMaxEmprunt = nombreMaxEmprunt;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private MemberShipType membershipType;
        private boolean isLocked;
        private Integer nombreMaxEmprunt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder membershipType(MemberShipType membershipType) {
            this.membershipType = membershipType;
            return this;
        }

        public Builder isLocked(boolean isLocked) {
            this.isLocked = isLocked;
            return this;
        }

        public Builder nombreMaxEmprunt(Integer nombreMaxEmprunt) {
            this.nombreMaxEmprunt = nombreMaxEmprunt;
            return this;
        }

        public User build() {
            return new User(id, name, email, membershipType, isLocked, nombreMaxEmprunt);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
