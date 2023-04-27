package com.api.services;

import com.api.payloads.categoryDto;

import java.util.List;

public interface categoryService {
    categoryDto createCategory(categoryDto categoryDto);
    categoryDto getCategoryById(Integer categoryId);
    List<categoryDto> getAllCategory();
    categoryDto updateCategory(Integer categoryId, categoryDto categoryDto);
    void deleteCategory(Integer categoryId);
}
