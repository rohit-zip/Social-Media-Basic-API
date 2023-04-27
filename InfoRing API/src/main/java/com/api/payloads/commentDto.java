package com.api.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class commentDto {
    private Integer id;
    private String content;
    private Date commentDate;
}
