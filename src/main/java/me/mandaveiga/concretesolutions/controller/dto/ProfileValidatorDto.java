package me.mandaveiga.concretesolutions.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileValidatorDto {

    private final String id;
    private final String authorization;
    private final String token;
}
