package com.Project1.demo.aapl.put1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Put1Service {

    //commented out autowired
    //@Autowired
    Put1Repository Repository;

    // added the constructor

    @Autowired
    public Put1Service(Put1Repository Repository) {
        this.Repository = Repository;
    }

    public void saveOrUpdate(Put1 input)
    {
        Repository.save(input);
    }

    public List<Put1>  findAll(){
        List<Put1> out = Repository.findAll();
        return out;
    }
    public void deleteAll(){
        Repository.deleteAll();
    }
}
