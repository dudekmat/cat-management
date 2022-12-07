package com.banzo.catmanagement.auth.mappers;

import com.banzo.catmanagement.auth.dto.UserDetails;
import com.banzo.catmanagement.auth.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

  UserDetails toUserDetails(User user);

  User toUser(UserDetails userDetails);
}
