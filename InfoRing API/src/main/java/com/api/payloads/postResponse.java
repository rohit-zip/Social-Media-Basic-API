package com.api.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class postResponse {
    private List<postDto> content;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalPages;
    private Long totalElements;
    private boolean lastPage;
}
