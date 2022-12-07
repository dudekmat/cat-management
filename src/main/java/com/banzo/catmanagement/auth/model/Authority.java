package com.banzo.catmanagement.auth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_generator")
  @SequenceGenerator(name = "authority_generator", sequenceName = "authority_id_seq", allocationSize = 1)
  private Long id;

  private String permission;

  @ManyToMany(mappedBy = "authorities")
  private Set<Role> roles;
}
