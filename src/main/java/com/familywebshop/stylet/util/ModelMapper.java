package com.familywebshop.stylet.util;

import java.util.ArrayList;
import java.util.List;

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

    public <T, D> D mapEntityToDto(T entity, Class<D> dtoClass){
        return modelMapper.map(entity, dtoClass);
    }

    public <T, D> T mapDtoToEntity(D dto, Class<T> entityClass){
        return modelMapper.map(dto, entityClass);
    }

    public <T, D> List<D> mapEntityListToDtoList(List<T> entityList, Class<D> dtoClass) {
        List<D> dtos = new ArrayList<>();

        for (T entity : entityList) {
            dtos.add(mapEntityToDto(entity, dtoClass));
        }

        return dtos;
    }

    public <T, D> List<T> mapDtoListToEntityList(List<D> dtoList, Class<T> entityClass) {
        List<T> entities = new ArrayList<>();

        for (D dto : dtoList) {
            entities.add(mapDtoToEntity(dto, entityClass));
        }

        return entities;
    }
}
