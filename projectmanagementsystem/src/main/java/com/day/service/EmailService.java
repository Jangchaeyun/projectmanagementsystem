package com.day.service;

public interface EmailService {
    void sendEmailToken(String userEmail, String link) throws Exception;
}
