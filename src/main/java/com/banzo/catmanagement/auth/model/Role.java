package com.banzo.catmanagement.auth.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;
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
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
  @SequenceGenerator(name = "role_generator", sequenceName = "role_id_seq", allocationSize = 1)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users;

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "role_authority",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
  private Set<Authority> authorities;
}
