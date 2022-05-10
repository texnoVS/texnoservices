package com.texno.fraud;

import com.texno.clients.fraud.FraudCheckResponse;

public interface FraudService {
    FraudCheckResponse isFraudulentCustomer(Long customerId);
}
