package com.hand.infra.constant;

/**
 * Workflow constant
 *
 * @author hapid.fadli@hand-global.com
 */
public class WorkFlowConstant {
    private WorkFlowConstant() {}

    public static class DocStatus {
        private DocStatus() {}

        /**
         * Draft status
         */
        public static final String DRAFT = "DRAFT";

        /**
         * Processing status
         */
        public static final String PROCESSING = "PROCESSING";

        /**
         * Completed/Approved status
         */
        public static final String COMPLETED = "APPROVED";

        /**
         * Rejected status
         */
        public static final String REJECTED = "REJECTED";
    }


    public static class MsgError {
        public static final String ERROR_WORKFLOW_START = "47840.error.workflow.start";
        public static final String ERROR_WORKFLOW_WITHDRAW = "47840.error.workflow.withdraw";
        public static final String ERROR_WORKFLOW_HISTORY = "47840.error.workflow.history";
        public static final String ERROR_DOCUMENT_NOT_FOUND = "47840.error.document.not.found";
    }
}