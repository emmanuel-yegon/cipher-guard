package com.emmanuel_yegon.cipherguard.utility;

public interface AuthenticatorTask {

    public void afterValidationSuccess();

    public void onValidationFailed();

}
