export enum DOStatus {
    CHOOSE = 'Choose',
    DO_INITIATED = 'DO Initiate',
    CANCELLED = 'Canceled',
    COMPLETED = 'Completed',
    PAYMENT_FAILED = 'Payment Failed',
    PARTIAL_PAYMENT = 'Partial Payment',
    PAYMENT_PENDING = 'Payment Pending',
    PAYMENT_PENDING_WITH_IMPORTER = 'Payment Pending with Importer',
    // PENDING_DO = 'Pending DO',
    PENDING_FOR_APPROVAL = 'Pending for Approval',
    REJECTED = 'Rejected',
    RETURNED = 'Returned',
    NEW = 'New',
    INITIATED = 'Initiated',
    DELETED = 'DELETED'

}
export enum InvoiceStatus {
    CHOOSE = 'Choose',
    DELETED = "DELETED",
    PAYMENT_PENDING = "PAYMENT PENDING",
    PAYMENT_PENDING_WITH_IMPORTER = "PAYMENT PENDING WITH IMPORTER",
    PAYMENT_FAILED = "PAYMENT FAILED",
    PAYMENT_SUCCESS = "PAYMENT SUCCESS",
    PAYMENT_NOT_INITIATED = "PAYMENT NOT INITIATED"
}

//For Approver
export const DOApproverStatusMapping = [
    { value: 'CHOOSE', label: DOStatus.CHOOSE },
    { value: 'INITIATED', label: DOStatus.INITIATED },
    { value: 'DO_INITIATED', label: DOStatus.DO_INITIATED },
    { value: 'CANCELLED', label: DOStatus.CANCELLED },
    { value: 'COMPLETED', label: DOStatus.COMPLETED },
    { value: 'PAYMENT_FAILED', label: DOStatus.PAYMENT_FAILED },
    { value: 'PARTIAL_PAYMENT', label: DOStatus.PARTIAL_PAYMENT },
    { value: 'PAYMENT_PENDING', label: DOStatus.PAYMENT_PENDING },
    { value: 'PAYMENT_PENDING_WITH_IMPORTER', label: DOStatus.PAYMENT_PENDING_WITH_IMPORTER },
    //   { value: 'PENDING_DO', label: DOStatus.PENDING_DO },
    { value: 'PENDING_FOR_APPROVAL', label: DOStatus.PENDING_FOR_APPROVAL },
    { value: 'REJECTED', label: DOStatus.REJECTED },
    { value: 'RETURNED', label: DOStatus.RETURNED }
];


//For initiator
export const DOInitiatorStatusMapping = [
    { value: 'CHOOSE', label: DOStatus.CHOOSE },
    { value: 'INITIATED', label: DOStatus.INITIATED },
    { value: 'DO_INITIATED', label: DOStatus.DO_INITIATED },
    { value: 'CANCELLED', label: DOStatus.CANCELLED },
    { value: 'COMPLETED', label: DOStatus.COMPLETED },
    { value: 'PAYMENT_FAILED', label: DOStatus.PAYMENT_FAILED },
    { value: 'PARTIAL_PAYMENT', label: DOStatus.PARTIAL_PAYMENT },
    { value: 'PAYMENT_PENDING_WITH_IMPORTER', label: DOStatus.PAYMENT_PENDING_WITH_IMPORTER },
    { value: 'PENDING_FOR_APPROVAL', label: DOStatus.PENDING_FOR_APPROVAL },
    { value: 'REJECTED', label: DOStatus.REJECTED },
    { value: 'RETURNED', label: DOStatus.RETURNED }
];


//For BOL Status
export const BOLStatusMapping = [
    { value: 'CHOOSE', label: DOStatus.CHOOSE },
    { value: 'NEW', label: DOStatus.NEW },
    { value: 'INITIATED', label: DOStatus.INITIATED },
    { value: 'DO_INITIATED', label: DOStatus.DO_INITIATED },
    { value: 'COMPLETED', label: DOStatus.COMPLETED },
    { value: 'PARTIAL_PAYMENT', label: DOStatus.PARTIAL_PAYMENT },
    { value: 'REJECTED', label: DOStatus.REJECTED },
    { value: 'RETURNED', label: DOStatus.RETURNED },
    { value: 'DELETED', label: DOStatus.DELETED }
]