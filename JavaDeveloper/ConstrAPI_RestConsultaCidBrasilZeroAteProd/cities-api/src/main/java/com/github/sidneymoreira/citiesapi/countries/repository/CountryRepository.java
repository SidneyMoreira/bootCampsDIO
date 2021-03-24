package com.github.sidneymoreira.citiesapi.countries.repository;

import com.github.sidneymoreira.citiesapi.countries.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
