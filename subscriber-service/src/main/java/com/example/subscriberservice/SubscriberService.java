package com.example.subscriberservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberService {
    @Autowired
    SubscriberRepository subscriberRepository;

    PasswordEncoder passwordEncoder;

    public SubscriberService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Subscriber saveSubscriber(Subscriber s) {
        String encodedPassword = this.passwordEncoder.encode(s.getPassword());
        s.setPassword(encodedPassword);
        return subscriberRepository.save(s);
    }

    public Subscriber updateSubscriber(Subscriber s) {
        return subscriberRepository.save(s);
    }

    public void deleteSubscriber(Subscriber s) {
        subscriberRepository.delete(s);

    }

    public void deleteSubscriberById(Long id) {
        subscriberRepository.deleteById(id);

    }

    public Subscriber getSubscriber(Long id) {
        return subscriberRepository.findById(id).get();
    }

    public List<Subscriber> getAllSubscribers() {
        return (List<Subscriber>) subscriberRepository.findAll();
    }

}
