package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.AssistantRepository;
import ca.mcgill.ecse321.scrs.model.Assistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class AssistantService
{
    @Autowired
    public AssistantRepository assistantRepository;

    @Transactional
    public Assistant createAssistant(String email, String name, String password, String phone) {
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
    public Assistant updateAssistantInfo(Assistant updates)
    {
        Assistant assistant = assistantRepository.findByScrsUserId(updates.getScrsUserId());
        if (updates.getEmail() != null) assistant.setEmail(updates.getEmail());
        if (updates.getName() != null) assistant.setName(updates.getName());
        if (updates.getPassword() != null) assistant.setPassword(updates.getPassword());
        if (updates.getPhone() != null) assistant.setPhone(updates.getPhone());
        assistantRepository.save(assistant);
        return assistant;
    }
}
