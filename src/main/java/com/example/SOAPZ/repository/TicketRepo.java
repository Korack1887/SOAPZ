package com.example.SOAPZ.repository;

import com.example.SOAPZ.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket, Long> {
}
