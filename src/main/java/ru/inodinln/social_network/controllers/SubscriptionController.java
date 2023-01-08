package ru.inodinln.social_network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.subscriptionsDTO.SubscriptionCreatingDTO;
import ru.inodinln.social_network.dto.subscriptionsDTO.SubscriptionViewDTO;
import ru.inodinln.social_network.facades.SubscriptionFacade;

import java.util.List;

@RestController
@RequestMapping("/API/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionFacade subscriptionFacade;

    public SubscriptionController(SubscriptionFacade subscriptionFacade) {
        this.subscriptionFacade = subscriptionFacade;
    }

    ////////////////////////////Business methods section///////////////////////////////////////
    //Get all subscriptions by current user:
    @GetMapping("/SubscriptionsByUser/{userId}")
    public ResponseEntity<List<SubscriptionViewDTO>> getSubscriptionsByUser
    (@PathVariable("userId") Long userId,
     @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
     @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(subscriptionFacade.getSubscriptionsByUser(userId, page, itemsPerPage), HttpStatus.OK);
    }

    //Get all subscriptions to current user:
    @GetMapping("/SubscriptionsToUser/{userId}")
    public ResponseEntity<List<SubscriptionViewDTO>> getSubscriptionsToUser
    (@PathVariable("userId") Long userId,
     @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
     @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(subscriptionFacade.getSubscriptionsToUser(userId, page, itemsPerPage), HttpStatus.OK);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<SubscriptionViewDTO>> getAll
    (@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
     @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(subscriptionFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{subscrId}")
    public ResponseEntity<SubscriptionViewDTO> getById(@PathVariable("subscrId") Long subscrId) {
        return new ResponseEntity<>(subscriptionFacade.getById(subscrId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubscriptionViewDTO> create(@RequestBody SubscriptionCreatingDTO subscrDTO) {
        return new ResponseEntity<>(subscriptionFacade.create(subscrDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{subscrId}")
    public ResponseEntity<Void> delete(@PathVariable("subscrId") Long subscrId) {
        subscriptionFacade.delete(subscrId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
