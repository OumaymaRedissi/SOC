package com.example.subscriberservice;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Subscriber {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name,surname,address;

    private Date birth;

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", name=" + name +", surname=" + surname + ", address=" + address + ", password=" + password +", dateOfBirth=" + birth ;
    }


    public void setPassword(String encodedPassword) {
        this.password=encodedPassword;
    }


    public String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }



}
