package com.texno.customer;

import com.texno.clients.fraud.FraudCheckResponse;
import com.texno.clients.fraud.FraudClient;
import com.texno.clients.notification.NotificationRequest;
import com.texno.customer.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final String MESSAGE_TEMPLATE = "Hi %s, welcome to texnocode";
    private static final String SENDER = "Texno";

    private final FraudClient fraudClient;
    private final CustomerRepository customerRepository;
    private final KafkaProducer kafkaProducer;

    @Value("${kafka.topics.notification}")
    private String notificationTopicName;

    @Override
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // todo: if email valid
        // todo: check if email not taken

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Customer is fraudster");
        }

        kafkaProducer.send(notificationTopicName,
                           new NotificationRequest(getMessage(customer.getFirstName()), SENDER, customer.getEmail(), customer.getId()));
    }

    private String getMessage(String name) {
        return String.format(MESSAGE_TEMPLATE, name);
    }
}
