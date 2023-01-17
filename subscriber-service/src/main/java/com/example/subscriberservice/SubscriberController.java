package com.example.subscriberservice;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
public class SubscriberController {
    @Autowired
    SubscriberService subscriberService;

    @GetMapping("/subscribers")
    List<Subscriber> allSubscribers() {
        return (List<Subscriber>) subscriberService.getAllSubscribers();
    }
    @GetMapping("/subscribers/{subscriber_id}")
    @ResponseBody
    Subscriber findSubscriber(@PathVariable("subscriber_id") Long subscriberId) {
        return subscriberService.getSubscriber(subscriberId);
    }

    @DeleteMapping("/subscribers/{id}")
    void deleteSubscriber(@PathVariable Long id) {
        subscriberService.deleteSubscriberById(id);
    }

    @PostMapping("/subscribers/add-subscriber")
    @ResponseBody Subscriber newSubscriber(@RequestBody Subscriber newSubscriber) {
        return subscriberService.saveSubscriber(newSubscriber);
    }

    @PutMapping("/subscribers/{id}")
    public Subscriber updateSubscriber(@PathVariable(value = "id") Long subscriberId, @RequestBody Subscriber subscriberDetails)  {
        Subscriber subscriber = subscriberService.getSubscriber(subscriberId);
        if(subscriberDetails.getAddress()!=null)
            subscriber.setAddress(subscriberDetails.getAddress());
        if(subscriberDetails.getBirth()!=null)
            subscriber.setBirth(subscriberDetails.getBirth());
        if(subscriberDetails.getEmail()!=null)
            subscriber.setEmail(subscriberDetails.getEmail());
        if(subscriberDetails.getName()!=null)
            subscriber.setName(subscriberDetails.getName());
        if(subscriberDetails.getSurname()!=null)
            subscriber.setSurname(subscriberDetails.getSurname());


        final Subscriber updatedSubscriber = subscriberService.saveSubscriber(subscriber);
        return subscriberService.updateSubscriber(updatedSubscriber);
    }
}
