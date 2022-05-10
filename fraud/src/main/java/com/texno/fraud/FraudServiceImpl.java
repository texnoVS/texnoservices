package com.texno.fraud;

import com.texno.clients.fraud.FraudCheckResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FraudServiceImpl implements FraudService {
    private final FraudRepository fraudRepository;

    @Override
    public FraudCheckResponse isFraudulentCustomer(Long customerId) {
        log.info("Fraud check customer with id {}", customerId);

        FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder()
                .customerId(customerId)
                .isFraudster(Boolean.FALSE)
                .createAt(LocalDateTime.now())
                .build();

        fraudRepository.save(fraudCheckHistory);

        log.info("Check customer with id {} for fraudster is {}", customerId, Boolean.FALSE);

        return new FraudCheckResponse(Boolean.FALSE);
    }
}
