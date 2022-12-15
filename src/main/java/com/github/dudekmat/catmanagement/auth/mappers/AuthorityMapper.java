package com.github.dudekmat.catmanagement.auth.mappers;

import com.github.dudekmat.catmanagement.auth.dto.AuthorityDetails;
import com.github.dudekmat.catmanagement.auth.model.Authority;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorityMapper {

  AuthorityDetails toAuthorityDetails(Authority authority);

  Authority toAuthority(AuthorityDetails authorityDetails);
}
