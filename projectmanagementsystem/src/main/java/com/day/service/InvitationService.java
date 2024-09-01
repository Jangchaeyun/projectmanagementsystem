package com.day.service;

import com.day.modal.Invitation;

public interface InvitationService {
    public void sendInvitation(String email, Long projectId) throws Exception;
    public Invitation acceptInvitation(String token, Long userId) throws Exception;
    public String getTokenByUserEmail(String userEmail) throws Exception;
    void deleteToken(String token) throws Exception;
}
