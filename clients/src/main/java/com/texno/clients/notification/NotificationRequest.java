package com.texno.clients.notification;

public record NotificationRequest(String message,
                                  String sender,
                                  String toCustomerEmail,
                                  Long customerId) {
}
