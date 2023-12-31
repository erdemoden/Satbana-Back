package com.woh.satbana.Responses;

import lombok.Data;

@Data
public class ErrorSuccess {
    private String error;
    private String success;
    private String jwt;
}
