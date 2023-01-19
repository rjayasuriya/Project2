package com.Project1.demo.aapl.call1;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Call1Repository extends JpaRepository<Call1, Integer> {
    // below was added

    @Query(nativeQuery = true,value="SELECT * FROM call1")
    List<Call1> findAllOfCalls();
}
