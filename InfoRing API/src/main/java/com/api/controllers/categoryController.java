package com.api.controllers;

import com.api.payloads.apiResponse;
import com.api.payloads.categoryDto;
import com.api.services.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class categoryController {

    @Autowired
    private categoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<?> createCategory(@Valid @RequestBody categoryDto categoryDto){
        categoryDto category = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<categoryDto> getCategoryById(@PathVariable Integer categoryId){
        categoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryById);
    }

    @GetMapping("/")
    public ResponseEntity<List<categoryDto>> getAllCategory(){
        return ResponseEntity.ok(this.categoryService.getAllCategory());
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<categoryDto> updateCategory(@Valid @RequestBody categoryDto categoryDto, @PathVariable Integer categoryId){
        categoryDto categoryDto1 = this.categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok(categoryDto1);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<apiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new apiResponse("Deleted Successfully", true), HttpStatus.OK);
    }
}
