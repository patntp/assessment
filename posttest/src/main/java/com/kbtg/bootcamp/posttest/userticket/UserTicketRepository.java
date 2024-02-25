package com.kbtg.bootcamp.posttest.userticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
}
