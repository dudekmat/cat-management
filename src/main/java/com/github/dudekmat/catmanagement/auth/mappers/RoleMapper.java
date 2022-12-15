package com.github.dudekmat.catmanagement.auth.mappers;

import com.github.dudekmat.catmanagement.auth.dto.RoleDetails;
import com.github.dudekmat.catmanagement.auth.model.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {

  RoleDetails toRoleDetails(Role role);

  Role toRole(RoleDetails roleDetails);
}
