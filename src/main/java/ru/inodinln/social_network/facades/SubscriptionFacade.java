package ru.inodinln.social_network.facades;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.SubscriptionCreationDTO;
import ru.inodinln.social_network.dto.SubscriptionViewDTO;
import ru.inodinln.social_network.entities.Subscription;
import ru.inodinln.social_network.services.SubscriptionService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionFacade {

    private final SubscriptionService subscriptionService;

    public SubscriptionFacade(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    //Get all subscriptions by current user:
    public List<SubscriptionViewDTO> getSubscriptionsByUser(Long userId) {
        return packListDTO(subscriptionService.getSubscriptionsByUser(userId));
    }

    //Get all subscriptions to current user:
    public List<SubscriptionViewDTO> getSubscriptionsToUser(Long userId) {
        return packListDTO(subscriptionService.getSubscriptionsToUser(userId));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<SubscriptionViewDTO> findAll(){
        return packListDTO(subscriptionService.findAll());
    }

    public SubscriptionViewDTO findById(Long subscrId){
        return packDTO(subscriptionService.findById(subscrId));
    }

    public void save(SubscriptionCreationDTO subscrDTO){
        subscriptionService.save(subscrDTO.getFromId(), subscrDTO.getToId());
    }

    ////////////////////////////Service methods section///////////////////////////////////////

    public List<SubscriptionViewDTO> packListDTO(List<Subscription> listOfSubscr){
        List<SubscriptionViewDTO> listOfDTO = new ArrayList<>(listOfSubscr.size());
        for (Subscription subscr : listOfSubscr) {

            SubscriptionViewDTO dto = new SubscriptionViewDTO();
            BeanUtils.copyProperties(subscr, dto);
            dto.setFromId(subscr.getFrom().getId());
            listOfDTO.add(dto);
        }
        return listOfDTO;
    }

    public SubscriptionViewDTO packDTO(Subscription subscr){
        SubscriptionViewDTO dto = new SubscriptionViewDTO();
        BeanUtils.copyProperties(subscr, dto);
        dto.setFromId(subscr.getFrom().getId());
        return dto;
    }

}
