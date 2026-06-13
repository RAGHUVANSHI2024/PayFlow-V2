package com.payflow.wallet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateWalletRequest {

    private Long userId;

}
