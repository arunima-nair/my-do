import { InvoiceType } from './invoice-type';
import { PaymentLog } from './paymentlog.model';
import { CollectionDo } from './collection-do.model';

export class BolInvoice {
    disablecheck: boolean = false;
    invoiceValue: string;
    invoiceNumber: string;
    invoiceDate: string;
    path: string;
    invoiceValidityDate: string;
    paidStatus: string;
    invoiceType: InvoiceType;
    paymentLogs: PaymentLog;
    currency: string;
    base64Invoice: string;
    invoiceTypeLabel: string;
    collections: CollectionDo;
    fileName: string;
    invoiceTypeTxt: string;
    encrInvoiceNumber: string;
    status: string;
    createdBy: string;
    statusTxt: string;
    refNumber: string;
    download: boolean;
    constructor(invoiceValue: string, invoiceNumber: string, invoiceDate: string,
        invoiceValidityDate: string, currency: string, base64Invoice: string, status: string) {

    }

}
