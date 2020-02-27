import { BolInvoice } from './bol-invoice.model';
import { BolDetail } from './bol-detail.model';
import { AuthoriseDO } from './authorise-do.model';

export class Bol {
    bolDetails: BolDetail;
    bolInvoice: BolInvoice[];
    doAuthRequests: AuthoriseDO;
    bolNumber: string;
    bolType: string;
    status: string;
    path: string;
    bolId: number;
    bolIdStr: string;
    encBolNumber: string;
    createdDate: string;
    version: number;

}
