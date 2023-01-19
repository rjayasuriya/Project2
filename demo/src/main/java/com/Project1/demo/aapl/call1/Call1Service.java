package com.Project1.demo.aapl.call1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class Call1Service {

    //commented out autowired
    //@Autowired
    Call1Repository call1Repository;

    // added the constructor

    @Autowired
    public Call1Service(Call1Repository studentRepository) {
        this.call1Repository = studentRepository;
    }

    public void saveOrUpdate(Call1 input)
    {
        call1Repository.save(input);
    }

    public List<Call1>  findCalls(){
        List<Call1> out = call1Repository.findAll();
        return out;
    }
    public void deleteAll(){
        call1Repository.deleteAll();
    }

    @Transactional
    public List<Call1> customQuery(){
        return call1Repository.findAllOfCalls();
    }


}
