package com.jxust.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class HellowObject implements Serializable {
    private Integer id;
    private String message;
}
