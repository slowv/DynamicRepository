package com.vsoftlight.dynamicrepository.service;

import com.vsoftlight.dynamicrepository.entity.Hero;
import com.vsoftlight.dynamicrepository.repository.MasterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@Slf4j
@ShellComponent
@AllArgsConstructor
public class HeroService {
    private final MasterRepository<Hero, Long> repository;

    @ShellMethod(value = "Find all hero")
    public void test() {
        final List<Hero> heroes = repository.findAll(Hero.class);
        log.info("HERO: {}", heroes);
    }
}
