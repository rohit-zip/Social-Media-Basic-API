package com.api.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class postDto {
    private int postId;
    private String title;
    private String content;
    private String image;
    private Date addedDate;
    private Date updatedDate;
    private categoryDto category;
    private userDto user;
    private Set<commentDto> comments = new HashSet<>();
}
