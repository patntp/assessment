package com.kbtg.bootcamp.posttest.userticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
    List<UserTicket> findAllByUserId(String userId);
}
