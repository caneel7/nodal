package com.nodal.nodal_rest.repository;

import com.nodal.nodal_rest.model.UserCalender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCalendarRepository extends JpaRepository<UserCalender,Integer> {

    List<UserCalender> getAllByUserId(Integer id);
}
