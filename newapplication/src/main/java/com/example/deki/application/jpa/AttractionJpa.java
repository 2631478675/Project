package com.example.deki.application.jpa;

import com.example.deki.application.pojo.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionJpa extends JpaRepository<Attraction, Long> {
    Attraction findByName(String name);

}
