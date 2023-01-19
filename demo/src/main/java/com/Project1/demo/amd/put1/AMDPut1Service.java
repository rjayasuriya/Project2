package com.Project1.demo.amd.put1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AMDPut1Service {

    //commented out autowired
    //@Autowired
    AMDPut1Repository Repository;

    // added the constructor

    @Autowired
    public AMDPut1Service(AMDPut1Repository Repository) {
        this.Repository = Repository;
    }

    public void saveOrUpdate(AMDPut1 input)
    {
        Repository.save(input);
    }

    public List<AMDPut1>  findPuts(){
        List<AMDPut1> out = Repository.findAll();
        return out;
    }
    public void deleteAll(){
        Repository.deleteAll();
    }
}
