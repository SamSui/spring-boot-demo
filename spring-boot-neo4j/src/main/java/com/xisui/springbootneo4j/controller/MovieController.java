package com.xisui.springbootneo4j.controller;

import com.xisui.springbootneo4j.doman.Actor;
import com.xisui.springbootneo4j.doman.Movie;
import com.xisui.springbootneo4j.doman.User;
import com.xisui.springbootneo4j.repository.MovieRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/movie")
@Slf4j
public class MovieController {

    @Resource
    private MovieRepository movieRepository;
    @RequestMapping("/create")
    public String createMovie() {
        Movie immortal = new Movie("仙逆", "王琳的成长史");
        User primaryDirector = new User(1990, "Tory");
        User slaveDirector = new User(2020, "Tome");
        User user2 = new User(1990, "Lin Wang");
        User user3 = new User(1990, "Muwan Li");
        immortal.getDirectors().addAll(Arrays.asList(primaryDirector, slaveDirector));

        Actor primaryActor = new Actor(user2);
        Actor slaveActor = new Actor(user3);
        Actor primaryActor1 = new Actor(primaryDirector);
        Actor slaveActor1 = new Actor(slaveDirector);
        immortal.setActors(Arrays.asList(primaryActor, slaveActor, primaryActor1, slaveActor1));

        movieRepository.save(immortal);

        return "create movie";
    }
    @GetMapping("/delete")
    public String deleteMovie() {
        movieRepository.deleteAll();
        return "delete movie";
    }

    @RequestMapping("/find")
    public String findMovie(String title) {
        movieRepository.findAll().forEach(System.out::println);

        Optional<Movie> movie = movieRepository.findOneByTitle(title);		//通过接口获取节点
        if(movie.isPresent()){
            List<User> directors = movie.get().getDirectors ();					//获取movie的directors关系数组
            User person = new User(2020, "Tory3");			//创建人物节点
            directors.add(person);		//将person节点通过directors关系指向movie
            movieRepository.save(movie.get());
        }

        return "find movie";
    }
}
