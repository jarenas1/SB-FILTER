package com.filtro.FILTRO_SPRINGBOOT.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Size(min = 4, max = 12)
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Hide the passwords in the responses
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String email;

    @Transient //avoid the creation of this column in the DB
    private boolean admin;

    private boolean enabled;

    //------------------------RELATION-------------------------------------------------

    @JsonIgnoreProperties({"users"}) //Avoid infity cicles in the responses
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}  //Unique columns
    )
    private List<RoleEntity> roles = new ArrayList<>();

    //------------------------RELATION-------------------------------------------------

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "userEntity")
    List<LoadEntity> loads = new ArrayList<>();
    //---------------------------GETTERS AND SETTERS-------------------------------------------------

    @PrePersist //SET enable in true
    public void prePersist(){
        this.enabled = true;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<LoadEntity> getLoads() {
        return loads;
    }

    public void setLoads(List<LoadEntity> loads) {
        this.loads = loads;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public @NotBlank @Size(min = 4, max = 12) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 4, max = 12) String username) {
        this.username = username;
    }
}
