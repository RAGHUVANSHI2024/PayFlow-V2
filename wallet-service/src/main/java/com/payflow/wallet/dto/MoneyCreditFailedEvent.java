package com.payflow.wallet.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoneyCreditFailedEvent {

    private String eventId;

    private Long senderWalletId;

    private Long receiverWalletId;

    private String reason;
}