package com.Project1.demo.amd.put1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="amd")
public class AMDPut1Controller {

    //commented out autowired
    @Autowired
    AMDPut1Service Service;

    //added constructor
    @Autowired
    public AMDPut1Controller(AMDPut1Service Service ) {

        this.Service = Service;
    }

    @PostMapping(path="/put1")
    private void saveBook(@RequestBody AMDPut1 input)
    {
        Service.saveOrUpdate(input);

    }
    @GetMapping(path="/getAllPuts")
    private List<AMDPut1> getCalls(){
        return Service.findPuts();
    }
}
