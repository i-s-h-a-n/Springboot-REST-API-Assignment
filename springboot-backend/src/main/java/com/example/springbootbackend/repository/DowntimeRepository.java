package com.example.springbootbackend.repository;

import com.example.springbootbackend.model.Downtime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DowntimeRepository extends JpaRepository<Downtime,Long> {

    //all crud database operations

}
