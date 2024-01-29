package com.xisui.springbootneo4j.repository;

import com.xisui.springbootneo4j.doman.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends Neo4jRepository<Movie, String> {

	List<Movie> findAll();

	Optional<Movie> findById(String title);

	Optional<Movie> findOneByTitle(String title);

	List<Movie> findAllByTitleLikeIgnoreCase(String title);

	List<Movie> findAllByDirectorsName(String name);

	@Query("MATCH (m:Movie {title:{0}}) RETURN m")
    List<Movie> findMovies(String title);

}
