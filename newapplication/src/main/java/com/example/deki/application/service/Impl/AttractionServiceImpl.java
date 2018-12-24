package com.example.deki.application.service.Impl;

import com.example.deki.application.commond.Response;
import com.example.deki.application.jpa.AttractionJpa;
import com.example.deki.application.jpa.TicketJpa;
import com.example.deki.application.pojo.Attraction;
import com.example.deki.application.pojo.Ticket;
import com.example.deki.application.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService{

    @Autowired
    private AttractionJpa attractionJpa;

    @Autowired
    private TicketJpa ticketJpa;

    @Override
    public Response<List<Ticket>> buyTicket(Attraction attraction) {
        //查询
        Attraction attractionNew  = attractionJpa.findByName(attraction.getName());
        //一共需要买的票数
        attraction.setSumOfPerson(attraction.getAdultNum()+attraction.getChildNum()+attraction.getStudentNum());

        if(attractionNew.getRemainingTicket() < attraction.getSumOfPerson()){
            return Response.createByErrorMessage("已经没那么多票");

        }
        List<Ticket> tickets = attraction.getTicket();
        Integer price = attractionNew.getPrice()*attraction.getSumOfPerson();
        for(int i =0 ;i <attraction.getSumOfPerson(); i++){
            Ticket ticket = new Ticket();
            ticket.setPrice(attractionNew.getPrice());
            ticket.setTotal(price);
            ticket.setTimeTicket(new Date());
            tickets.add(ticket);
        }
        ticketJpa.saveAll(tickets);
        //更新票数
        attractionNew.setRemainingTicket(attractionNew.getRemainingTicket()-attraction.getSumOfPerson());
        //返回票价

        return Response.createBySuccess("购票成功,价钱为"+price ,tickets);
    }

    @Override
    public Response<Attraction> updatePrice(Attraction attraction) {
        //查询
        Attraction attractionNew  = attractionJpa.findByName(attraction.getName());
        attractionNew.setPrice(attraction.getPrice());
        attractionJpa.save(attractionNew);
        return Response.createBySuccess("修改票价成功",attractionNew);
    }

    @Override
    public Response<Attraction> updateNum(Attraction attraction) {
        //查询
        Attraction attractionNew  = attractionJpa.findByName(attraction.getName());
        attractionNew.setRemainingTicket(attraction.getRemainingTicket());
        attractionJpa.save(attractionNew);
        return Response.createBySuccess("修改余票票数成功",attractionNew);
    }

    @Override
    public Response<Attraction> saveAttraction(Attraction attraction) {
        attractionJpa.save(attraction);
        return Response.createBySuccess("增加景区成功",attraction);}
}
