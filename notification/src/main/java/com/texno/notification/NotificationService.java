package com.texno.notification;

import com.texno.clients.notification.NotificationRequest;

public interface NotificationService {
    void send(NotificationRequest notification);
}
