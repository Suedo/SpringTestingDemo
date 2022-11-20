package com.example.testingdemo.Types;

import java.util.List;

public class Common {
    public record Errors(String field, String validationFailureCause) {}

    public record ErrorResponse(int errorCount, String operation, List<Errors> errorList) {}

}
