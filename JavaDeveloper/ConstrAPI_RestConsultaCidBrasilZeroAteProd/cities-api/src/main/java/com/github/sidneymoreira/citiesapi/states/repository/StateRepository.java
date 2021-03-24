package com.github.sidneymoreira.citiesapi.states.repository;

import com.github.sidneymoreira.citiesapi.states.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}