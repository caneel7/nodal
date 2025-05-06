package com.nodal.nodal_rest.repository;

import com.nodal.nodal_rest.model.UserCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCalendarRepository extends JpaRepository<UserCalendar,Integer> {

    List<UserCalendar> getAllByUserId(Integer id);
    Optional<UserCalendar> findOneByIdAndUserId(Integer id, Integer userId);
}
