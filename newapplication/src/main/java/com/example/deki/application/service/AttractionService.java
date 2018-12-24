package com.example.deki.application.service;

import com.example.deki.application.commond.Response;
import com.example.deki.application.pojo.Attraction;
import com.example.deki.application.pojo.Ticket;

import java.util.List;

public interface AttractionService {

    Response<List<Ticket>> buyTicket(Attraction attraction);

    Response<Attraction> updatePrice(Attraction attraction);

    Response<Attraction> updateNum(Attraction attraction);

    Response<Attraction> saveAttraction(Attraction attraction);
}
