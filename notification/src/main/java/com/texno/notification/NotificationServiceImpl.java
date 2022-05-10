package com.texno.notification;

import com.texno.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void send(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .message(notificationRequest.message())
                .sentAt(LocalDateTime.now())
                .sender(notificationRequest.sender())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .customerId(notificationRequest.customerId())
                .build();

        log.info("New notification... {}", notification);

        notificationRepository.save(notification);
    }
}
