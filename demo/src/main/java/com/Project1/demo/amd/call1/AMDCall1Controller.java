package com.Project1.demo.amd.call1;

import com.Project1.demo.amd.put1.AMDPut1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="amd")
public class AMDCall1Controller {

    //commented out autowired
    @Autowired
    AMDCall1Service Service;

    //added constructor
    @Autowired
    public AMDCall1Controller(AMDCall1Service Service ) {

        this.Service = Service;
    }

    @PostMapping(path="/call1")
    private void save(@RequestBody AMDCall1 input)
    {
        Service.saveOrUpdate(input);

    }
    @GetMapping(path="/getAllCalls")
    private List<AMDCall1> getCalls(){
        return Service.findCalls();
    }
}
