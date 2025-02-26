package com.sky.dto;

import lombok.Data;

@Data
public class EditPasswordDTO {

    private Long empId;

    private String oldPassword;

    private String newPassword;
}
