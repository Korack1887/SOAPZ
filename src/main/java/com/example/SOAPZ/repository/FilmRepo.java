package com.example.SOAPZ.repository;

import com.example.SOAPZ.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepo extends JpaRepository<Film, Long> {
}
