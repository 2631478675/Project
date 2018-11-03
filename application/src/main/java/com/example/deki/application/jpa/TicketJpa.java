package com.example.deki.application.jpa;

import com.example.deki.application.pojo.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpa extends JpaRepository<Ticket,Long>{
}
