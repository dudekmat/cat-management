package com.github.dudekmat.catmanagement.auth.mappers;

import com.github.dudekmat.catmanagement.auth.dto.UserDetails;
import com.github.dudekmat.catmanagement.auth.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

  UserDetails toUserDetails(User user);

  User toUser(UserDetails userDetails);
}
