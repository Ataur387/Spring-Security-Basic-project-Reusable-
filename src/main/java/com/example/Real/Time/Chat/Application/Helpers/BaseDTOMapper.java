package com.example.Real.Time.Chat.Application.Helpers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BaseDTOMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Generic method to map DTO to Entity
    public <D, E> E convertToEntity(D dto, Class<E> entityClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, entityClass);
    }

    // Generic method to map Entity to DTO
    public <E, D> D convertToDTO(E entity, Class<D> dtoClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, dtoClass);
    }
}
