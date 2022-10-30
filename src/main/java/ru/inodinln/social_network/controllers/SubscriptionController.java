package ru.inodinln.social_network.controllers;

import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.SubscriptionCreationDTO;
import ru.inodinln.social_network.dto.SubscriptionViewDTO;
import ru.inodinln.social_network.facades.SubscriptionFacade;
import ru.inodinln.social_network.services.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/API/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionFacade subscriptionFacade;

    private final SubscriptionService subscriptionService;

    public SubscriptionController (SubscriptionService subscriptionService, SubscriptionFacade subscriptionFacade) {
        this.subscriptionService = subscriptionService;
        this.subscriptionFacade = subscriptionFacade;
    }

    //Get all subscriptions by current user:
    @GetMapping("/SubscriptionsByUser/{userId}")
    public List<SubscriptionViewDTO> getSubscriptionsByUser(@PathVariable("userId") Long userId){
        return subscriptionFacade.getSubscriptionsByUser(userId);
    }

    //Get all subscriptions to current user:
    @GetMapping("/SubscriptionsToUser/{userId}")
    public List<SubscriptionViewDTO> getSubscriptionsToUser(@PathVariable("userId") Long userId){
        return subscriptionFacade.getSubscriptionsToUser(userId);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public List<SubscriptionViewDTO> findAll(){ //responseEntity
        return subscriptionFacade.findAll();
    }

    @GetMapping("/{subscrId}")
    public SubscriptionViewDTO findById(@PathVariable("subscrId") Long subscrId) {
        return subscriptionFacade.findById(subscrId);
    }

    @PostMapping
    public void create(@RequestBody SubscriptionCreationDTO subscrDTO) {
        subscriptionFacade.save(subscrDTO);
    }

    @DeleteMapping("/{subscrId}")
    public void delete(@PathVariable("subscrId") Long subscrId) {
        subscriptionService.delete(subscrId);
    }

}
