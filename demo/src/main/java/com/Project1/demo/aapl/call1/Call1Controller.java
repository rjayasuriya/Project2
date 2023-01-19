package com.Project1.demo.aapl.call1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="aapl")
public class Call1Controller {

    //commented out autowired
    @Autowired
    Call1Service call1Service;

    //added constructor
    @Autowired
    public Call1Controller(Call1Service call1Service ) {

        this.call1Service = call1Service;
    }

    @PostMapping(path="/call1")
    private void saveBook(@RequestBody Call1 input)
    {
        call1Service.saveOrUpdate(input);

    }
    @GetMapping(path="/getAllCalls")
    private List<Call1> getCalls(){
        return call1Service.findCalls();
    }

    @GetMapping(path="/custom1")
    private List<Call1> custom1() {return call1Service.customQuery();}




}
