package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.AssistantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssistantService
{
    @Autowired
    public AssistantRepository assistantRepository;

    @Transactional
    public void beep(){
        System.out.println("beep");
    }
}
