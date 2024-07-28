package com.example.greenstoreproject.service;

import com.example.greenstoreproject.entity.ComboProduct;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendDailyScheduleEmail(Customers customer, Schedule schedule) {
        String subject = "Daily Use Schedule " + schedule.getDate();
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Welcome ").append(customer.getFirstName()).append(" ").append(customer.getLastName()).append(",\n\n")
                .append("Here is your treatment plan for today:\n")
                .append("Date: ").append(schedule.getDate()).append("\n")
                .append("Products:\n");

//        for (ComboProduct comboProduct : schedule.getComboProducts()) {
//            bodyBuilder.append("Product: ").append(comboProduct.getProduct().getProductName())
//                    .append(", Quantity: ").append(comboProduct.getQuantity()).append("\n");
//        }

        bodyBuilder.append("\nHave a nice day!\nSupport team.");

        sendEmail(customer.getEmail(), subject, bodyBuilder.toString());
    }
}
