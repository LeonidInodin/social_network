package ru.inodinln.social_network.facades;

import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.subscriptionsDTO.SubscriptionCreatingDTO;
import ru.inodinln.social_network.dto.subscriptionsDTO.SubscriptionViewDTO;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.SubscriptionService;
import ru.inodinln.social_network.utils.MapperService;

import java.util.List;

@Service
public class SubscriptionFacade {

    private final SubscriptionService subscriptionService;

    public SubscriptionFacade(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    //Get all subscriptions by current user:
    public List<SubscriptionViewDTO> getSubscriptionsByUser(Long userId, Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfSubscriptionViewDTO
                (subscriptionService.getSubscriptionsByUser(userId, page, itemsPerPage));
    }

    //Get all subscriptions to current user:
    public List<SubscriptionViewDTO> getSubscriptionsToUser(Long userId, Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfSubscriptionViewDTO
                (subscriptionService.getSubscriptionsToUser(userId, page, itemsPerPage));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<SubscriptionViewDTO> getAll(Integer page, Integer itemsPerPage){
        return MapperService.mapperForCollectionOfSubscriptionViewDTO(subscriptionService.getAll(page, itemsPerPage));
    }

    public SubscriptionViewDTO getById(Long subscrId){
        return MapperService.mapperForSingleSubscriptionViewDTO(subscriptionService.getById(subscrId));
    }

    public SubscriptionViewDTO create(SubscriptionCreatingDTO subscrDTO){
        ValidationService.subscriptionCreatingDtoValidation(subscrDTO);
        return MapperService.mapperForSingleSubscriptionViewDTO
                (subscriptionService.create(subscrDTO.getSubscriberId(), subscrDTO.getTargetId()));
    }

    public void delete(Long subscriptionId){
        subscriptionService.delete(subscriptionId);
    }

}
