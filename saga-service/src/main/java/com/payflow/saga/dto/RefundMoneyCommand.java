package com.payflow.saga.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundMoneyCommand {

    private String eventId;

    private Long senderWalletId;

    private Long receiverWalletId;


}
