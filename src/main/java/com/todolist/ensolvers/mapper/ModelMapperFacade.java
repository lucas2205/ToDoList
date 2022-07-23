package com.todolist.ensolvers.mapper;


import org.modelmapper.ModelMapper;

public interface ModelMapperFacade {

    public static  <S, T> T map(S source, Class<T> destinationType) {
        return new ModelMapper().map(source, destinationType);
    }

}