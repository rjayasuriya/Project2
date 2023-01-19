package com.Project1.demo.amd.call1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AMDCall1Service {

    //commented out autowired
    //@Autowired
    AMDCall1Repository Repository;

    // added the constructor

    @Autowired
    public AMDCall1Service(AMDCall1Repository Repository) {
        this.Repository = Repository;
    }

    public void saveOrUpdate(AMDCall1 input)
    {
        Repository.save(input);
    }

    public List<AMDCall1>  findCalls(){
        List<AMDCall1> out = Repository.findAll();
        return out;
    }
    public void deleteAll(){
        Repository.deleteAll();
    }
}
