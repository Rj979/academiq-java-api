package com.academiq.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class AppUser {
    
    // Static builder method
    public static AppUserBuilder builder() {
        return new AppUserBuilder();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "is_active")
    private boolean isActive = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    public AppUser() {
    }

    public AppUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    
    // Builder class
    public static class AppUserBuilder {
        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private boolean isActive = true;
        private Set<String> roles = new HashSet<>();
        
        public AppUserBuilder username(String username) {
            this.username = username;
            return this;
        }
        
        public AppUserBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public AppUserBuilder password(String password) {
            this.password = password;
            return this;
        }
        
        public AppUserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public AppUserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public AppUserBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }
        
        public AppUserBuilder role(String role) {
            this.roles.add(role);
            return this;
        }
        
        public AppUserBuilder roles(Set<String> roles) {
            this.roles.addAll(roles);
            return this;
        }
        
        public AppUser build() {
            AppUser user = new AppUser();
            user.setUsername(this.username);
            user.setEmail(this.email);
            user.setPassword(this.password);
            user.setFirstName(this.firstName);
            user.setLastName(this.lastName);
            user.setActive(this.isActive);
            user.setRoles(this.roles);
            return user;
        }
    }
}