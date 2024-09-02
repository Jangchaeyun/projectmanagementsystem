package com.day.service;

import com.day.modal.PlanType;
import com.day.modal.Subscription;
import com.day.modal.User;

public interface SubscriptionService {
    Subscription createSubscription(User user);
    Subscription getUsersSubscription(Long userId);
    Subscription upgradeSubscription(Long userId, PlanType planType);
    boolean isValid(Subscription subscription);
}
