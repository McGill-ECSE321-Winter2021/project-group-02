package ca.mcgill.ecse321.scrs.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CookieController
{

    @GetMapping("/")
    public String readCookie(@CookieValue(value = "id", defaultValue = "-1") String id){
        return id;
    }
}
