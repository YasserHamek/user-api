package com.test.userapi.services;

import com.test.userapi.dto.mapper.GenericMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public abstract class GenericService<T extends JpaRepository, S extends GenericMapper, U, V, E> {
    protected final T repository;
    protected final S mapper;

    protected GenericService(T repository, S mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Save entity in data base
     * @param dto representing the entity to be saved
     */
    public void saveEntity(V dto) {
        try{
            repository.save(mapper.dtoToEntity(dto));
        } catch(Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "A problem occured when trying to save the " + dto.getClass().getSimpleName() + " in DataBase, please contact the admin.",
                    exception
            );
        }
    }

    /**
     * Searching entities using filter, the search will be made using query by {@link Example} of {@link JpaRepository}
     * @param filter representing entities filtering
     * @return List of entities
     */
    public List<V> findAllByExample(E filter){
        try{
            ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnorePaths("id");
            return mapper.entitiesToDtos(repository.findAll(Example.of(mapper.filterToUser(filter), matcher)));
        } catch(Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "A problem occured when trying search by filter : " +  filter.getClass().getSimpleName() + " in DataBase, please contact the admin.",
                    exception
            );
        }
    }


}
