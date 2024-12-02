package com.hand.infra.constant;

public class TodoTaskConstant {

    public static class ErrorCode {
        public static final String ERROR_CODE_EMPLOYEE_NOT_FOUND = "EMPLOYEE_NOT_FOUND";
        public static final String ERROR_CODE_TASK_NUMBER_INVALID = "TASK_NUMBER_INVALID";
    }
    public static class ErrorMessage {
        public static final String ERROR_MESSAGE_EMPLOYEE_NOT_FOUND = "Employee ID %s not found in database";
        public static final String ERROR_MESSAGE_TASK_NUMBER_INVALID = "Task number must contain only English letters";
    }
}
