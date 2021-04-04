package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.controller.Helper;
import ca.mcgill.ecse321.scrs.dao.AssistantRepository;
import ca.mcgill.ecse321.scrs.model.Assistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.checkAccountInfoValidity;
import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class AssistantService
{
    @Autowired
    AssistantRepository assistantRepository;

    @Transactional
    public Assistant createAssistant(String email, String name, String password, String phone)
    {
        checkAccountInfoValidity(email, name, password, phone);
        Assistant assistant = new Assistant();
        assistant.setEmail(email);
        assistant.setName(name);
        assistant.setPassword(password);
        assistant.setPhone(phone);
        assistantRepository.save(assistant);
        return assistant;
    }

    @Transactional
    public Assistant getAssistantByID(int id)
    {
        return assistantRepository.findByScrsUserId(id);
    }

    @Transactional
    public List<Assistant> getAllAssistants()
    {
        return toList(assistantRepository.findAll());
    }

    @Transactional
    public Assistant getAssistantByEmail(String email)
    {
        return assistantRepository.findByEmail(email);
    }

    @Transactional
    public Assistant getAssistantByName(String name)
    {
        return assistantRepository.findByName(name);
    }

    @Transactional
    public Assistant getAssistantByPhone(String phone)
    {
        return assistantRepository.findByPhone(phone);
    }

    @Transactional
    public Assistant updateAssistantInfo(Assistant assistant)
    {
        checkAccountInfoValidity(assistant);
        if (assistant.getPassword() == null || assistant.getPassword().trim().length() == 0)
        {
            assistant.setPassword(getAssistantByID(assistant.getScrsUserId()).getPassword());
        }
        else
        {
            assistant.setPassword(Helper.hash(assistant.getPassword()));
        }
        assistantRepository.save(assistant);
        return assistant;
    }

    @Transactional
    public Assistant deleteAssistant(Assistant assistant)
    {
        assistantRepository.delete(assistant);
        return assistant;
    }
}
