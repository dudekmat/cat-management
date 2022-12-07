package com.banzo.catmanagement.auth.mappers;

import com.banzo.catmanagement.auth.dto.RoleDetails;
import com.banzo.catmanagement.auth.model.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {

  RoleDetails toRoleDetails(Role role);

  Role toRole(RoleDetails roleDetails);
}
