package com.familywebshop.stylet.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String s) {
        super(s);
    }

    public CategoryNotFoundException(Long id) {
        super("Category with ID " + id  + " not found!");
    }
}
