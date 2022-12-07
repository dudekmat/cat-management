package com.banzo.catmanagement.auth.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  private String password;

  @Builder.Default private Integer failedLoginAttempts = 0;

  @Builder.Default
  @Column(nullable = false)
  private Boolean enabled = true;

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Set<Role> roles;

  @Transient
  private Set<Authority> authorities;

  public Set<Authority> getAuthorities() {
    return this.roles.stream()
        .map(Role::getAuthorities)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());
  }
}
