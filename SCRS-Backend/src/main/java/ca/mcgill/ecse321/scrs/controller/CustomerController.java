package ca.mcgill.ecse321.scrs.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController
{
}