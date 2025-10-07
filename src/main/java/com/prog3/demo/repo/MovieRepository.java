package com.prog3.demo.repo;

import com.prog3.demo.model.MovieEntity;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface MovieRepository extends ReactiveNeo4jRepository<MovieEntity,
        String> {
    Mono<MovieEntity> findOneByTitle(String title);

}