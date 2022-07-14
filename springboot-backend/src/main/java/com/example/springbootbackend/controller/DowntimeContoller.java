package com.example.springbootbackend.controller;

import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Downtime;
import com.example.springbootbackend.model.DowntimeToFrom;
import com.example.springbootbackend.repository.DowntimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/base-url")
public class DowntimeContoller {

    @Autowired
    private DowntimeRepository downtimeRepository;

    @GetMapping("/get-downtimes")
    public List<Downtime> getAllDowntime() {
        return downtimeRepository.findAll();
    }

    @GetMapping("/get-downtime/{id}")
    public ResponseEntity<Downtime> getDowntimeById(@PathVariable long id) {
        return downtimeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create-downtime")
    public Downtime createDowntime(@RequestParam(name="provider") String providerName,@RequestParam(name="flow_name") String flowName,@RequestBody DowntimeToFrom requestBodyObject){
        Downtime downtime = new Downtime();
        downtime.setProviderName(providerName);
        downtime.setFlowName(flowName);
        downtime.setDownFrom(requestBodyObject.getDownFrom());
        downtime.setDownTo(requestBodyObject.getDownTo());
        return downtimeRepository.save(downtime);
    }

    @PutMapping("/update-downtime/{id}")
    public ResponseEntity<Downtime> updateDowntimeById(@PathVariable long id,@RequestBody Downtime downtime) {
        Downtime updateDowntime = downtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Downtime does not exist with id "+id));
        updateDowntime.setProviderName(downtime.getProviderName());
        updateDowntime.setFlowName(downtime.getFlowName());
        updateDowntime.setDownFrom(downtime.getDownFrom());
        updateDowntime.setDownTo(downtime.getDownTo());
        downtimeRepository.save(updateDowntime);

        return ResponseEntity.ok(updateDowntime);
    }

    @DeleteMapping("/delete-downtime/{id}")
    public ResponseEntity<String> deleteDowntimeById(@PathVariable long id) {
        downtimeRepository.deleteById(id);
        return new ResponseEntity<String>("Downtime deleted successfully!.", HttpStatus.OK);
    }
}
