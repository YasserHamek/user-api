package com.test.userapi.dto.mapper;


import java.util.ArrayList;
import java.util.List;

public interface GenericMapper<U, V, E> {
    V entityToDto(U entity);

    U dtoToEntity(V dto);

    default List<V> entitiesToDtos(List<U> entities) {
        List<V> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(entityToDto(entity)));
        return dtos;
    }

    default List<U> dtosToEntities(List<V>  dtos) {
        List<U> entities = new ArrayList<>();
        dtos.forEach(dto -> entities.add(dtoToEntity(dto)));
        return entities;
    }

   U filterToUser(E userFilterDto);
}
