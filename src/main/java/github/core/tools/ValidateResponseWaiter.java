package github.core.tools;

import io.restassured.response.ValidatableResponse;

public interface ValidateResponseWaiter {
    ValidatableResponse validatableResponse();
}
