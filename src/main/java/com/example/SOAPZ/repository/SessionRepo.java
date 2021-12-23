package com.example.SOAPZ.repository;

import com.example.SOAPZ.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<Session, Long> {
}
