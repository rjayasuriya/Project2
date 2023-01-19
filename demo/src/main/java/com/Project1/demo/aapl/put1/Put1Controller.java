package com.Project1.demo.aapl.put1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="aapl")
public class Put1Controller {

    //commented out autowired
    @Autowired
    Put1Service Service;

    //added constructor
    @Autowired
    public Put1Controller(Put1Service Service ) {

        this.Service = Service;
    }

    @PostMapping(path="/put1")
    private void save(@RequestBody Put1 input)
    {
        Service.saveOrUpdate(input);

    }
    @GetMapping(path="/getAllPuts")
    private List<Put1> getOptions(){
        return Service.findAll();
    }
}
