package com.api.implementation;

import com.api.entities.Category;
import com.api.entities.User;
import com.api.exceptions.resourceNotFoundException;
import com.api.payloads.categoryDto;
import com.api.repositories.categoryRepository;
import com.api.services.categoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class categoryServiceImplementation implements categoryService {

    @Autowired
    private categoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public categoryDto createCategory(categoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category savedCategory = this.categoryRepository.save(category);
        return this.categoryToDto(savedCategory);
    }

    @Override
    public categoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new resourceNotFoundException("Category", "Category Id", categoryId));
        return this.categoryToDto(category);
    }

    @Override
    public List<categoryDto> getAllCategory() {
        List<Category> allCategory = this.categoryRepository.findAll();
        List<categoryDto> categoryDtoList = allCategory.stream().map(this::categoryToDto).collect(Collectors.toList());
        return categoryDtoList;
    }

    @Override
    public categoryDto updateCategory(Integer categoryId, categoryDto categoryDto) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new resourceNotFoundException("Category", "Category Id", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepository.save(category);
        return this.categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        this.categoryRepository.deleteById(categoryId);
    }

    public Category dtoToCategory(categoryDto categoryDto){
        Category category = this.modelMapper.map(categoryDto, Category.class);
        return category;
    }

    public categoryDto categoryToDto(Category category){
        categoryDto categoryDto = this.modelMapper.map(category, categoryDto.class);
        return categoryDto;
    }

}
