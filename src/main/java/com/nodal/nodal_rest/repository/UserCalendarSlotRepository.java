package com.nodal.nodal_rest.repository;

import com.nodal.nodal_rest.model.UserCalendarSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCalendarSlotRepository extends JpaRepository<UserCalendarSlot,Integer> {
}
