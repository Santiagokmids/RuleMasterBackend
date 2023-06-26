package com.perficient.ruleMaster.maper;

import com.perficient.ruleMaster.dto.UserDTO;
import com.perficient.ruleMaster.model.RuleMasterUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    RuleMasterUser fromUserDTO(UserDTO userDTO);

    UserDTO fromUser(RuleMasterUser ruleMasterUser);
}
