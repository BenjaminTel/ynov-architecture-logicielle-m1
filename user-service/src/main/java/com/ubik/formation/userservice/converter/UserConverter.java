package com.ubik.formation.userservice.converter;

import com.ubik.formation.userservice.dto.UserDto;
import com.ubik.formation.userservice.entity.User;
import com.ubik.formation.userservice.enumeration.MemberShipType;

import java.util.Objects;

public class UserConverter {
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setMembershipType(user.getMembershipType().toString());
        userDto.setNombreMaxEmprunt(user.getNombreMaxEmprunt());
        userDto.setLocked(user.isLocked());
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .membershipType(MemberShipType.fromString(userDto.getMembershipType()))
                .isLocked(userDto.isLocked())
                .nombreMaxEmprunt(userDto.getMembershipType().equals("PREMIUM") ? 7 : 5)
                .build();
    }
}

