package com.banzo.catmanagement.auth.mappers;

import com.banzo.catmanagement.auth.dto.AuthorityDetails;
import com.banzo.catmanagement.auth.model.Authority;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorityMapper {

  AuthorityDetails toAuthorityDetails(Authority authority);

  Authority toAuthority(AuthorityDetails authorityDetails);
}
