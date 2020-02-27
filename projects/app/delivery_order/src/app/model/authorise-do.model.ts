import { Bol } from './bol.model';
import { Authdoc } from './authdoc.model';
import { CollectionDo } from './collection-do.model';
import { ApprovalLog } from './approval-log';
import { PaymentLog } from './paymentlog.model';
export class AuthoriseDO {
  bolPartyName?: string;

  reqPartyName?: string;

  bol?: Bol;

  doAuthDocs?: Authdoc;

  authLetter?: string;
  encrAuthLetterPath?: string;

  authDocId?: number;

  encrDoAuthDocsId?: string;

  blDocId?: number;

  encBlDocId?: string;

  emDocId?: number;

  encEmDocId?: string;

  othDocId?: number;

  encOthDocId?: string;

  bolLetter?: string;
  encrBolLetterPath?: string;

  emiratesIdCopy?: string;
  encrEmiratesIdCopyPath?: string;

  uploadDo?: string;

  othDoc?: string;
  encrOthDocPath?: string;

  doAuthRequestId?: number;

  doAuthReqIdStr?: string;

  bolContactNumber?: string;

  bolContactPerson?: string;

  bolEmail?: string;

  doContactNumber?: string;

  doContactPerson?: string;

  doEmail?: string;

  doPartyName?: string;

  reqContactNumber?: string;

  reqContactPerson?: string;

  reqEmail?: string;

  dostatus?: string;

  isPay?: boolean;

  choosePay: boolean;

  isCredit?: boolean;

  status?: string;

  doRefNo?: string;

  payMethod?: string;

  collections?: CollectionDo[];

  paymentLogs?: PaymentLog[];

  paid?: boolean;

  approvalLog?: ApprovalLog;

  version?: number;

  doAuthRequestIdEncr?: string;

  createdDate?: string;

  createdBy?: string;

  modifiedDate?: string;

  modifiedBy?: string;

  tempCreatedDate?: string;

  tempModifiedDate?: string;

  approvedViewed?: string;

  viewedBy?: string;

  viewedDate?: string;

  initiatorId?: number;

  initiatorCode?: string;

  initiatorType?: string;

  doPartyCode: string;

  doPartyType: string;

  blPartyCode: string;

  blPartyType: string;
}



