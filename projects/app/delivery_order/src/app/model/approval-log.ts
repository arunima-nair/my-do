import { ReturnRemarks } from './return-remarks';

import { RejectionRemarks } from './rejection-remarks';

export class ApprovalLog {
    status: string;
    comments: string;
    returnRemark: ReturnRemarks;
    rejectionRemark: RejectionRemarks;
    createdBy: string;
    createdDate: Date;
    remarks: string;
}
