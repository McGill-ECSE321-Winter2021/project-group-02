package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.model.Appointment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/notification", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController
{
    @GetMapping("/getall/{id}")
    public ResponseEntity<ArrayList<Appointment>> getAll(@PathVariable("id") String id)
    {
        int ID = Integer.valueOf(id);
        return new ResponseEntity<>(new ArrayList<Appointment>(), HttpStatus.OK);
    }
}
