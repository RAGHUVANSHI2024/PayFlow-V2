package com.payflow.wallet.Exception;

public class WalletIdNotFoundException extends RuntimeException{

    public WalletIdNotFoundException(String message){
        super(message);
    }
}
