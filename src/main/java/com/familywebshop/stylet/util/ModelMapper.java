package com.familywebshop.stylet.util;

public class ModelMapper {
    private static ModelMapper INSTANCE = null;

    private final org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();

    private ModelMapper()
    {
    }

    public static synchronized ModelMapper getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new ModelMapper();

        return INSTANCE;
    }

    public org.modelmapper.ModelMapper getModelMapper() {
        return modelMapper;
    }
}
