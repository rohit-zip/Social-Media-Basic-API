package com.api.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class categoryDto {
    private Integer categoryId;

    @NotBlank
    @Size(min = 4, max = 20, message = "Size must be greater than 4 and less than 20")
    private String categoryTitle;

    @NotBlank
    @Size(min = 4, max = 40, message = "Size must be greater than 10 and less than 40")
    private String categoryDescription;
}
