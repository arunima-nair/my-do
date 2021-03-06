import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { AutoCompleteComponent } from 'src/app/lib/components/auto-complete/auto-complete.component';
import { Bol } from 'src/app/model/bol.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthoriseDO } from 'src/app/model/authorise-do.model';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { DatePipe } from '@angular/common';
import { RequestInvoiceComponent } from '../request-invoice/request-invoice.component';
import { DialogOptions } from 'src/app/lib/service/dialog.service';
import { NotificationMessage, MessageStatus } from 'src/app/lib/classes/NotificationMessage';
import { Validator } from 'src/app/lib/classes/field.interface';
import { NormalTableComponent } from 'src/app/lib/components/table/normal-table.component';
import { TableDefn, TableType, TableAction } from 'src/app/lib/classes/TableDefn';
import { FileuploadComponent } from 'src/app/lib/components/fileupload/fileupload.component';
import { TelNumberComponent } from 'src/app/lib/components/tel-number/tel-number.component';
import { InvoiceStatus } from 'src/app/common/enum/status';
import { TermsAndConditionsComponent } from '../terms-and-conditions/terms-and-conditions.component';

class Item {
  value: string;
  label: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}

@Component({
  selector: 'app-request-do',
  templateUrl: './request-do.component.html',
  styleUrls: ['./request-do.component.css']
})
export class RequestDoComponent extends BaseComponent implements OnInit {

  @ViewChild('autocompletebl') autocompletebl: AutoCompleteComponent;
  @ViewChild('autocompletedo') autocompletedo: AutoCompleteComponent;
  @ViewChild('invoiceTable') invoiceTable: NormalTableComponent;
  @ViewChild('fileUploadAuthLetter') fileUploadAuthLetter: FileuploadComponent;
  @ViewChild('fileUploadEID') fileUploadEID: FileuploadComponent;
  @ViewChild('fileUploadBLCopy') fileUploadBLCopy: FileuploadComponent;
  @ViewChild('fileUploadOtherDoc') fileUploadOtherDoc: FileuploadComponent;
  @ViewChild('BlPartyNumber') BlPartyNumber: TelNumberComponent;
  @ViewChild('bolContactNo') bolContactNumber: TelNumberComponent;
  @ViewChild('doContactNumber') doContactNumber: TelNumberComponent;


  items: any[] = [];
  bol: Bol;
  fetcheditems: Item[] = new Array<Item>();
  checked = false;
  myForm: FormGroup;
  formBuilder: FormBuilder;
  authoriseDO: AuthoriseDO;
  doAuthRequestId: number;
  doRefNo: string;
  reqPartyName: string;
  identicalPartyName: string;
  reqEmail: string;
  bolEmail: string;
  bolPartyName: string;
  status: string;
  authDoc: any[];
  agentType: String;
  agentCode: String;
  payOptionItems: any[] = new Array();
  hideAmend: Boolean;
  showPay: Boolean;
  returnPay: Boolean = false;
  pendingPay: Boolean = false;
  statusPendingPayImporter: Boolean = true;
  statusPendingPayImporterPendingPay: Boolean = true;
  showPayTab: Boolean = false;
  paid: Boolean;
  paystring: string;
  payingAmt: number = 0;
  payingInvoice: any[] = new Array();
  reqPartyNameValidation: Validator[];
  reqContactPersonValidation: Validator[];
  reqEmailValidation: Validator[];
  reqContactNumberValidation: Validator[];
  bolContactNumberValidation: Validator[];
  bolContactPersonValidation: Validator[];
  bolEmailValidation: Validator[];
  bolPartyNameValidation: Validator[];
  doContactNumberValidation: Validator[];
  doContactPersonValidation: Validator[];
  doEmailValidation: Validator[];
  doPartyNameValidation: Validator[];
  eidValidation: Validator[];
  authLetterValidation: Validator[];
  blCopyValidation: Validator[];
  paymentOptionValidation: Validator[];
  tncValidation: Validator[];
  bankTrAdviceValidation: Validator[];
  payAmountValidations: Validator[];
  loadAUTHFlag: Boolean;
  loadBLFlag: Boolean;
  loadEIDFlag: Boolean;
  loadOTHFlag: Boolean;
  comments: string;
  remarks: string;
  bolPartytxt: string;
  doPartytxt: string;
  version: number;
  isInvoiceSelected: Boolean = false;
  reqPartyBusinessId: string;
  bolPartyBusinessId: string;
  autoFillOptions: any[];
  saveType: string;
  showDownload: Boolean = false;

  emiratedIdMaxFileSizeMb: string;
  blCopyMaxFileSizeMb: string;
  authLetterMaxFileSizeMb: string;
  othDocMaxFileSizeMb: string;
  payUploadProofMaxFileSizeMb: string;
  uploadDOMaxFileSizeMb: string;
  partialPay: string;
  totalAmt: number = 0;
  paidAmt: number = 0;
  paidStatus: boolean = false;
  payProofShow: boolean = false;
  isDisabled: boolean = false;
  otherInvoicePaid: boolean = true;
  tableItems: any[] = [];
  public bolInvoiceStatus;
  tableDfn: TableDefn[] = [
    { displayName: 'Invoice No', mappingName: 'invoiceNumber', type: TableType.string, sort: true },
    { displayName: 'Invoice Type', mappingName: 'invoiceTypeTxt', type: TableType.string, sort: true },
    { displayName: 'Invoice Value', mappingName: 'invoiceValue', type: TableType.string, sort: true },
    { displayName: 'Invoice currency', mappingName: 'currency', type: TableType.date, sort: true },
    { displayName: 'Payment Status', mappingName: 'statusTxt', type: TableType.string },
    { displayName: 'Paid By', mappingName: 'createdBy', type: TableType.string },

  ];
  emailValidations = [
    {
      name: 'required',
      message: 'Email is required!',
      validator: Validators.required
    },
    {
      name: 'email',
      validator: Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}'),
      message: 'Enter valid email'
    },
    {
      name: 'isEmailExist',
      validator: null,
      message: 'Email already exist'
    }
  ];

  ngOnInit() {
    this.agentType = this._securityInfo.getAgentType();
    this.agentCode = this._securityInfo.getAgentCode();
    this.autoFillOptions = [{ label: 'Same as Requesting Party Details', value: 1 }, { label: 'Same as BOL Party Details', value: 2 }];
    // var emailPattern = "[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}";
    this.myForm = this._fb.group({
      doAuthRequestId: [''],
      doAuthRequestIdEncr: [''],
      reqPartyName: ['', []],
      reqContactPerson: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
      reqEmail: ['', [Validators.required, Validators.email]],
      reqContactNumber: ['', [Validators.required]],
      bolContactNumber: ['', [Validators.required]],
      bolContactPerson: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
      bolEmail: ['', [Validators.required, Validators.email, Validators.minLength(6)]],
      bolPartyName: ['', Validators.required],
      doContactNumber: ['', [Validators.required]],
      doContactPerson: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
      doEmail: ['', [Validators.required, Validators.email, Validators.minLength(6)]],
      doPartyName: ['', [Validators.required]],
      tnc: ['', [Validators.required]],
      authLetter: ['', [Validators.required]],
      othDoc: [''],
      bolLetter: ['', Validators.required],
      emiratesIdCopy: ['', Validators.required],
      encrEmiratesIdCopyPath: [''],
      encrBolLetterPath: [''],
      encrAuthLetterPath: [''],
      encrOthDocPath: [''],
      isAmend: [false],
      isPay: [false],
      choosePay: [false],
      payOption: ['-1'],
      payAmount: [''],
      bankTrAdvice: [''],
      bolNumber: [''],
      isCredit: [false],
      isPayProof: [false],
      show: [true],
      hideAmend: [true],
      showPay: [true],
      returnPay: [false],
      doRefNo: ['', []],
      uploadProof: [''],
      paid: [false],
      status: [''],
      loadAUTHFlag: [false],
      loadBLFlag: [false],
      loadEIDFlag: [false],
      loadOTHFlag: [false],
      comments: [''],
      remarks: [''],
      bolPartytxt: [''],
      doPartytxt: [''],
      version: [0],
      emiratedIdMaxFileSizeMb: ['0.5'],
      blCopyMaxFileSizeMb: ['0.5'],
      authLetterMaxFileSizeMb: ['0.5'],
      othDocMaxFileSizeMb: ['0.5'],
      payUploadProofMaxFileSizeMb: ['0.5'],
      uploadDOMaxFileSizeMb: ['0.5'],
      payInvoice: [''],
      payingAmt: [0],
      payingInvoice: [],
      partialPay: [''],
      totalAmt: [0],
      paidAmt: [0],
      paidStatus: [false],
      payProofShow: [false],
      isDisabled: [false],
      sameAsReq: [],
      sameAsBol: [],
      pendingPay: [false],
      initiatorId: [''],
      initiatorCode: [''],
      initiatorType: [''],
      type: [''],
      refNumber: [''],
      doPartyCode: [''],
      doPartyType: [''],
      blPartyCode: [''],
      blPartyType: ['']
    });

    this.reqContactPersonValidation = [{ name: 'minlength', validator: Validators.minLength(4), message: 'Min Length 4' }, { name: 'maxlength', validator: Validators.maxLength(50), message: 'Max Length 50' },
    { name: 'required', validator: Validators.required, message: 'Requesting Party Representative Person Required' }];

    this.reqEmailValidation = [{ name: 'maxlength', validator: Validators.maxLength(50), message: 'Max Length 50' },
    { name: 'required', validator: Validators.required, message: 'Requesting Party Email Required' },
    { name: 'email', validator: Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}'), message: 'Enter valid email format' }];

    this.reqContactNumberValidation = [{ name: 'minlength', validator: Validators.minLength(12), message: 'Min Length 12' },
    { name: 'required', validator: Validators.required, message: 'Requesting Party Representative Phone Number Required' },
    { name: 'maxlength', validator: Validators.maxLength(14), message: 'Max Length 14' }];

    this.bolContactNumberValidation = [{ name: 'minlength', validator: Validators.minLength(12), message: 'Min Length 12' },
    { name: 'required', validator: Validators.required, message: 'B/L Representative Phone Number Required' },
    { name: 'maxlength', validator: Validators.maxLength(14), message: 'Max Length 14' }];

    this.bolContactPersonValidation = [{ name: 'minlength', validator: Validators.minLength(4), message: 'Min Length 4' }, { name: 'maxlength', validator: Validators.maxLength(50), message: 'Max Length 50' },
    { name: 'required', validator: Validators.required, message: 'B/L Representative Person Required' }];

    this.bolEmailValidation = [{ name: 'maxlength', validator: Validators.maxLength(50), message: 'Max Length 50' },
    { name: 'required', validator: Validators.required, message: 'B/L Party Email Required' },
    { name: 'email', validator: Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}'), message: 'Enter valid email format' }];

    this.eidValidation = [{ name: 'required', validator: Validators.required, message: 'Emirates Id copy is Required' }];

    this.blCopyValidation = [{ name: 'required', validator: Validators.required, message: 'B/L copy is Required' }];

    this.paymentOptionValidation = [{ name: 'required', validator: Validators.required, message: 'Payment Option Required' }];

    this.tncValidation = [{ name: 'required', validator: Validators.required, message: 'Please agree to Terms and Conditions *' }];
    this.doContactPersonValidation = [{ name: 'required', validator: Validators.required, message: 'Contact Person required' }, { name: 'minlength', validator: Validators.required, message: 'Min Length 4' }, { name: 'maxlength', validator: Validators.maxLength(50), message: 'Max Length 50' }];

    this.doContactNumberValidation = [{ name: 'required', validator: Validators.required, message: 'Contact Number required' }, { name: 'minlength', validator: Validators.minLength(12), message: 'Min Length 12' },
    { name: 'maxlength', validator: Validators.maxLength(14), message: 'Max Length 14' }];

    this.doEmailValidation = [{ name: 'required', validator: Validators.required, message: 'DO Email Required.' }, { name: 'email', validator: Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}'), message: 'Enter valid email format' }];

  }
  constructor(private _fb: FormBuilder, private route: ActivatedRoute, private _securityInfo: SecurityInfoService, private cdr: ChangeDetectorRef, private datePipe: DatePipe) {
    super();
    this._pageHeaderService.updateHeader('Search BOL/Invoice Request');
    this._httpRequestService.getData('/do/app/api/secure/getDocFileSize', false).subscribe((sizeres) => {
      if (sizeres != undefined) {
        if (sizeres.length > 0) {
          this.emiratedIdMaxFileSizeMb = sizeres[0].value;
          this.blCopyMaxFileSizeMb = sizeres[1].value;
          this.authLetterMaxFileSizeMb = sizeres[2].value;
          this.othDocMaxFileSizeMb = sizeres[3].value;
          this.payUploadProofMaxFileSizeMb = sizeres[4].value;
          this.uploadDOMaxFileSizeMb = sizeres[4].value;
        }
      }
    });
    this._httpRequestService.getData('/do/app/api/secure/getImporterAgentDetailsWithEmailReqParty?q=' + this._securityInfo.getAgentCode(), true).subscribe((res) => {
      this.reqPartyName = res.value;
      this.reqPartyBusinessId = res.businessSubId;
      if (this.reqPartyBusinessId == 'C') {
        this.autoFillOptions = [{ label: 'Same as BOL Party Details', value: 2 }];
      }
      this.identicalPartyName = res;
      if (this.reqEmail == undefined) {
        this.reqEmail = res.emailId;
        this.myForm.controls['reqEmail'].setValue(this.reqEmail);
      }
    });

    this._httpRequestService.getData('/do/app/api/secure/getBLdetailsNew?bolNo=' + this.route.snapshot.queryParams.bolEncr, true).subscribe((res) => {
      this.showPay = true;
      this.saveType = this.route.snapshot.queryParams.amend ? 'AMEND' : 'SAVE';
      this.bol = res.dataItems[0];
      this.bol.status = res.dataItems[0].status;
      this.bol.bolNumber = res.dataItems[0].bolNumber.toUpperCase();
      this.bol.bolDetails = res.dataItems[0].bolDetails[0];
      if (this.bol.bolType === 'EBL')
        this.authLetterValidation = [{ name: 'required', validator: Validators.required, message: 'Authorization Letter is Required' }];

      if (res.dataItems[0].bolInvoices === undefined && res.dataItems[0].doAuthRequests == undefined) {
        this._dialogService.alert("No Invoice found for BOL:  " + this.bol.bolNumber);
        this._router.navigate(['/search']);
      }
      this.bol.bolInvoice = res.dataItems[0].bolInvoices;
      let paidflag = true;
      if (this.bol.bolInvoice != undefined) {
        for (let entry of this.bol.bolInvoice) {
          if (entry.download)
            this.showDownload = true;

          if (this.bol.bolInvoice.length == 1) {
            if (entry.status == 'PAYMENT_PENDING') {
              this.pendingPay = true;
            }
          }
          var amt = Number(entry.invoiceValue);
          this.totalAmt = this.totalAmt + amt;
          entry.invoiceTypeTxt = entry.invoiceType.invoiceTypeName;
          if (entry.createdBy === "SYSTEM") {
            entry.createdBy = "";
          }
          if (entry.paidStatus === "PAID") {
            entry.disablecheck = false;
          }
          else {
            this.returnPay = true;
            paidflag = false;
            entry.disablecheck = true;
          }
          if (entry.status == 'PAYMENT_PENDING_WITH_IMPORTER') {
            entry.disablecheck = false;
          }
          entry.statusTxt = InvoiceStatus[entry.status];
          console.log(entry);
          this.myForm.get("paidStatus").setValue(paidflag);
          this.tableItems.push(entry);
        }
        for (let bolInvoice of this.bol.bolInvoice) {
          if (bolInvoice.status === null || bolInvoice.status === undefined || bolInvoice.status === 'PAYMENT_NOT_INITIATED' || bolInvoice.status === '' || bolInvoice.status === 'PAYMENT_FAILED' || bolInvoice.status === 'PAYMENT_PENDING') {
            this.showPayTab = true;
            break;
          }
        }
      }
      this.bol.bolType = res.dataItems[0].bolType;
      if (this.bol.bolDetails.importerCode === undefined)
        this.bol.bolDetails.importerCode = "";

      if (this.bol.status === "NEW") {
        let bolImpCode = this.bol.bolDetails.importerCode;
        if (bolImpCode != undefined && bolImpCode != null && bolImpCode != "") {
          this._httpRequestService.getData('/do/app/api/secure/getImporterAgentDetailsWithEmail?q=' + this.bol.bolDetails.importerCode, true).subscribe((res) => {
            if (res != undefined) {
              if (res.length > 0) {
                // let str = res[0].label;
                // var strSplit = str.split('-')[1];
                const item = new Item(res[0].label, res[0].value);
                this.items.push(item);
                this.bolPartytxt = res[0].value;
                this.myForm.controls['bolPartyName'].setValue(this.bolPartytxt);
                if (this.bolEmail == undefined) {
                  this.bolEmail = res[0].emailId;
                  this.bolPartyBusinessId = res[0].businessSubId;
                  this.myForm.controls['bolEmail'].setValue(this.bolEmail);
                }
                let strbl = this.bol.doAuthRequests.bolPartyName;
                if (strbl === undefined) {
                  if (this.bolPartytxt != undefined) {
                    this.items.push(item);
                    this.autocompletebl.refreshItems(this.items, item);
                    //  this.autocompletedo.refreshItems(this.items, item);
                  }
                }
              }
            }
          });
        }
      }
      this._httpRequestService.getData('/do/app/api/secure/getPaymentOption?agentCode=' + this.bol.bolDetails.shippingAgentCode, true).subscribe((res) => {
        res.forEach(element => {
          this.payOptionItems.push(element);
        });

      });
      this._httpRequestService.getData('/do/app/api/secure/getSAInitiatorPartialPayment?q=' + this.bol.bolDetails.shippingAgentCode, true).subscribe((res) => {
        if (res.length > 0)
          this.partialPay = res[0].paymentAllowed;
        this.myForm.get('partialPay').setValue(this.partialPay);
      });

      if (res.dataItems[0].doAuthRequests != undefined) {

        if (res.dataItems[0].doAuthRequests.length > 1) {
          var filterDoAUthRequest = res.dataItems[0].doAuthRequests.filter(doAuthReq => doAuthReq.doRefNo == this.route.snapshot.queryParams.doRefNo);
          this.bol.doAuthRequests = filterDoAUthRequest[0];
        } else {
          this.bol.doAuthRequests = res.dataItems[0].doAuthRequests[0];
        }


        /** */
        const blPartyType = this.bol.doAuthRequests.blPartyType;
        //const reqPartyBusinessId = res.dataItems[0].doAuthRequests[0].reqPartyBusinessId;

        if (blPartyType) {
          if (blPartyType == 'C' && this.reqPartyBusinessId == 'C') {
            this.autoFillOptions = [];
          }
          else if (blPartyType == 'C' && this.reqPartyBusinessId != 'C') {
            this.autoFillOptions = [{ label: 'Same as Requesting Party Details', value: 1 }];
          }
          else if (blPartyType != 'C' && this.reqPartyBusinessId == 'C') {
            this.autoFillOptions = [{ label: 'Same as BOL Party Details', value: 2 }];
          }
          else {
            this.autoFillOptions = [{ label: 'Same as Requesting Party Details', value: 1 }, { label: 'Same as BOL Party Details', value: 2 }];
          }

        }
        /** */

        this.doAuthRequestId = this.bol.doAuthRequests.doAuthRequestId;
        this.doRefNo = this.bol.doAuthRequests.doRefNo;
        if (res.dataItems[0].doAuthRequests.initiatorCode != undefined)
          this.myForm.get('initiatorCode').setValue(res.dataItems[0].doAuthRequests.initiatorCode);
        if (res.dataItems[0].doAuthRequests.initiatorId != undefined)
          this.myForm.get('initiatorId').setValue(res.dataItems[0].doAuthRequests.initiatorId);
        if (res.dataItems[0].doAuthRequests.initiatorType != undefined)
          this.myForm.get('initiatorType').setValue(res.dataItems[0].doAuthRequests.initiatorType);
        if (res.dataItems[0].doAuthRequests.doPartyCode != undefined)
          this.myForm.get('doPartyCode').setValue(res.dataItems[0].doAuthRequests.doPartyCode);
        if (res.dataItems[0].doAuthRequests.doPartyType != undefined)
          this.myForm.get('doPartyType').setValue(res.dataItems[0].doAuthRequests.doPartyType);
        if (res.dataItems[0].doAuthRequests.bolPartyType != undefined)
          this.myForm.get('bolPartyType').setValue(res.dataItems[0].doAuthRequests.bolPartyType);
        if (res.dataItems[0].doAuthRequests.bolPartyCode != undefined)
          this.myForm.get('bolPartyCode').setValue(res.dataItems[0].doAuthRequests.bolPartyCode);
        if (res.dataItems[0].doAuthRequests.doAuthDocs != undefined)
          this.authDoc = res.dataItems[0].doAuthRequests.doAuthDocs;
        if (this.bol.bolInvoice) {
          this.paidAmt = 0;
          for (let bolInvoice of this.bol.bolInvoice) {
            if (bolInvoice.collections[0]) {
              this.paidAmt = this.paidAmt + Number(bolInvoice.collections[0].amount);
              if (bolInvoice.collections[0].status != "SUCCESS") {
                this.showPay = true;
                return;
              } else {
                this.bol.doAuthRequests.payMethod = bolInvoice.collections[0].status;
                this.showPay = false;
              }
            }
          }
        }



        if (this.bol.doAuthRequests.approvalLog != undefined) {
          this.comments = this.bol.doAuthRequests.approvalLog[0].comments;
          if (this.bol.doAuthRequests.approvalLog[0].returnRemark != undefined)
            this.remarks = this.bol.doAuthRequests.approvalLog[0].returnRemark.remarks;
          else if (this.bol.doAuthRequests.approvalLog[0].rejectionRemark != undefined)
            this.remarks = this.bol.doAuthRequests.approvalLog[0].rejectionRemark.remarks;
        }
        if (this.bol.doAuthRequests.bolContactPerson != null) {
          this.bol.doAuthRequests.bolContactPerson = this.bol.doAuthRequests.bolContactPerson;
          this.myForm.get('bolContactPerson').setValue(this.bol.doAuthRequests.bolContactPerson);
        }
        if (this.bol.doAuthRequests.bolPartyName != null) {
          this.bol.doAuthRequests.bolPartyName = this.bol.doAuthRequests.bolPartyName;
        }
        if (this.bol.doAuthRequests.bolEmail != null) {
          this.bol.doAuthRequests.bolEmail = this.bol.doAuthRequests.bolEmail;
          this.myForm.get('bolEmail').setValue(this.bol.doAuthRequests.bolEmail);
        }
        if (this.bol.doAuthRequests.bolContactNumber != null) {
          this.bol.doAuthRequests.bolContactNumber = this.bol.doAuthRequests.bolContactNumber;
          this.myForm.get('bolContactNumber').setValue(this.bol.doAuthRequests.bolContactNumber);
        }
        if (this.bol.doAuthRequests.doContactPerson != null) {
          this.bol.doAuthRequests.doContactPerson = this.bol.doAuthRequests.doContactPerson;
          this.myForm.get('doContactPerson').setValue(this.bol.doAuthRequests.doContactPerson);
        }
        if (this.bol.doAuthRequests.doEmail != null) {
          this.bol.doAuthRequests.doEmail = this.bol.doAuthRequests.doEmail;
          this.myForm.get('doEmail').setValue(this.bol.doAuthRequests.doEmail);
        }
        if (this.bol.doAuthRequests.doContactNumber != null) {
          this.bol.doAuthRequests.doContactNumber = this.bol.doAuthRequests.doContactNumber;
          this.myForm.get('doContactNumber').setValue(this.bol.doAuthRequests.doContactNumber);
        }
        if (this.bol.doAuthRequests.doPartyName != null) {
          this.bol.doAuthRequests.doPartyName = this.bol.doAuthRequests.doPartyName;
        }
        if (this.bol.doAuthRequests.reqContactPerson != null) {
          this.bol.doAuthRequests.reqContactPerson = this.bol.doAuthRequests.reqContactPerson;
          this.myForm.get('reqContactPerson').setValue(this.bol.doAuthRequests.reqContactPerson);
        }
        if (this.bol.doAuthRequests.reqEmail != null) {
          this.bol.doAuthRequests.reqEmail = this.bol.doAuthRequests.reqEmail;
          this.reqEmail = this.bol.doAuthRequests.reqEmail;
          this.myForm.get('reqEmail').setValue(this.bol.doAuthRequests.reqEmail);
        }
        if (this.bol.doAuthRequests.reqContactNumber != null) {
          this.bol.doAuthRequests.reqContactNumber = this.bol.doAuthRequests.reqContactNumber;
          this.myForm.get('reqContactNumber').setValue(this.bol.doAuthRequests.reqContactNumber);
        }
        if (this.bol.doAuthRequests.reqPartyName != null) {
          this.bol.doAuthRequests.reqPartyName = this.bol.doAuthRequests.reqPartyName;
          this.myForm.get('reqPartyName').setValue(this.bol.doAuthRequests.reqPartyName);
        }
        this.myForm.get('doPartyCode').setValue(this.bol.doAuthRequests.doPartyCode);
        this.myForm.get('doPartyType').setValue(this.bol.doAuthRequests.doPartyType);
        this.myForm.get('blPartyCode').setValue(this.bol.doAuthRequests.blPartyCode);
        this.myForm.get('blPartyType').setValue(this.bol.doAuthRequests.blPartyType);

        if (this.authDoc != null) {
          for (let i of this.authDoc) {

            if (i.documentBean.documentValue === 'AUTHORIZATION_LETTER') {
              if (i.documentPath === "null") {

              } else {
                this.bol.doAuthRequests.authLetter = i.encrDocumentPath;
                this.bol.doAuthRequests.authDocId = i.encrDoAuthDocsId;
                this.bol.doAuthRequests.encrDoAuthDocsId = i.encrDoAuthDocsId;
                this.myForm.controls['encrAuthLetterPath'].setValue(i.encrDocumentPath);
                this.loadAUTHFlag = true;
              }
            }
            else if (i.documentBean.documentValue === "BL_COPY") {
              this.bol.doAuthRequests.bolLetter = i.encrDocumentPath;
              this.bol.doAuthRequests.blDocId = i.encrDoAuthDocsId;
              this.bol.doAuthRequests.encBlDocId = i.encrDoAuthDocsId;
              this.myForm.controls['encrBolLetterPath'].setValue(i.encrDocumentPath);
              this.loadBLFlag = true;
            }
            else if (i.documentBean.documentValue === 'EMIRATES_ID') {
              this.bol.doAuthRequests.emiratesIdCopy = i.encrDocumentPath;
              this.bol.doAuthRequests.emDocId = i.encrDoAuthDocsId;
              this.bol.doAuthRequests.encEmDocId = i.encrDoAuthDocsId;
              this.myForm.controls['encrEmiratesIdCopyPath'].setValue(i.encrDocumentPath);
              this.loadEIDFlag = true;
            }
            else if (i.documentBean.documentValue === 'OTHER_DOCUMENT') {
              if (i.documentPath === "null") {

              } else {
                this.bol.doAuthRequests.othDoc = i.encrDocumentPath;
                this.bol.doAuthRequests.othDocId = i.encrDoAuthDocsId;
                this.bol.doAuthRequests.encOthDocId = i.encrDoAuthDocsId;
                this.myForm.controls['encrOthDocPath'].setValue(i.encrDocumentPath);
                this.loadOTHFlag = true;
              }

            }
          }
        }
        this.bol.doAuthRequests.status = this.bol.doAuthRequests.status;
        if (this.bol.doAuthRequests.status === 'COMPLETED' || this.bol.doAuthRequests.status === 'CANCELLED' || this.bol.doAuthRequests.status === 'REJECTED')
          this.hideAmend = false;

      } else {
        this.bol.doAuthRequests = new AuthoriseDO;
        this.myForm.get('paidAmt').setValue(0);
      }

      if (this.otherInvoicePaid) {
        if (this.bol.bolInvoice != undefined) {

          for (let bolInv of this.bol.bolInvoice) {
            if (this.payingInvoice.includes(bolInv.invoiceNumber)) {

            } else {
              if (this.bol.bolInvoice.length == 1) {
                if (bolInv.status == 'PAYMENT_PENDING') {
                  this.pendingPay = true;
                  this.authorizeDo();
                  break;
                }
              }
              const payLogArray = this.bol.doAuthRequests.paymentLogs;
              if (payLogArray != undefined) {
                for (let payLog of payLogArray) {
                  if (payLog.status != 'SUCCESS') {
                    if ((bolInv.status != 'PAYMENT_PENDING_WITH_IMPORTER')) {
                      if (bolInv.status === 'PAYMENT_SUCCESS') {
                        this.otherInvoicePaid = false;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    });
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
    let strbl = this.bol.doAuthRequests.bolPartyName;
    if (strbl != undefined) {
      var strSplit = strbl.split('-')[0];
      const item = new Item(strSplit, strbl);
      this.items.push(item);
      this.autocompletebl.refreshItems(this.items, item);
    }
    let strdo = this.bol.doAuthRequests.doPartyName;
    if (strdo != undefined) {
      var strSplit = strdo.split('-')[0];
      const item = new Item(strSplit, strdo);
      this.items.push(item);
      this.autocompletedo.refreshItems(this.items, item);
    }
    if (this.bol.bolInvoice != undefined) {
      for (let entry of this.bol.bolInvoice) {
        if (entry.paidStatus != 'PAID' && entry.status != 'PAYMENT_PENDING_WITH_IMPORTER' && entry.status != 'PAYMENT_PENDING') {
          this.invoiceTable.disablecheck = true;
          this.invoiceTable.clickEvent.emit(this.onChangeInvoiceCheckBox(0, entry.invoiceNumber, entry.encrInvoiceNumber));
        } else {
          this.invoiceTable.disablecheck = false;
        }
      }
    }
  }


  doValidator() {

  }

  openTC(): any {
    const dialogOptions: DialogOptions = { disableClose: false, maxHeight: '90vh' };
    this._dialogService.openDialog(TermsAndConditionsComponent, { title: 'Terms And Conditions' }, dialogOptions).subscribe(value => {
    });
  }

  changed() {
    this.checked = !this.checked;
  }

  close() {
    this._dialogService.confirm('All your changes will be lost. Do you want to proceed?')
      .subscribe(retval => {
        if (retval != 1) {
          return;
        }
        else {
          this.notfnService.clearMessage();
          const navextras: NavigationExtras = {
            queryParams: { 'bolNo': this.bol.bolNumber }
          };
          if (this.route.snapshot.queryParams.parent != undefined)
            this._router.navigate([this.route.snapshot.queryParams.parent], navextras);
          else
            window.history.back();
        }
      });
  }

  authLetterUpload($event, id) {
    if ($event === null) {
      this.myForm.controls['authLetter'].setValue("");
      this.myForm.controls['encrAuthLetterPath'].setValue("");
      if (this.bol.bolType === "EBL") {
        this.myForm.controls['authLetter'].setErrors({ 'required': true });
        this.fileUploadAuthLetter.filesAdded = false;
      }
    } else {
      if (typeof $event != 'object') {
        this.myForm.patchValue({
          authLetter: $event
        });
        this.myForm.controls['encrAuthLetterPath'].setValue("");
      } else if ($event.download || id)
        this.viewFile(id);
      this.chageDetectorRef.markForCheck();
    }
  }

  eidUpload($event, id) {
    if ($event === null) {
      this.myForm.controls['emiratesIdCopy'].setValue("amend");
      this.myForm.controls['encrEmiratesIdCopyPath'].setValue("");
      this.myForm.controls['emiratesIdCopy'].setErrors({ 'required': true });
      this.fileUploadEID.filesAdded = false;
    } else {
      if (typeof $event != 'object') {
        this.myForm.patchValue({
          emiratesIdCopy: $event
        });
        this.myForm.controls['encrEmiratesIdCopyPath'].setValue("");
      } else if ($event.download || id)
        this.viewFile(id);
    }
    this.chageDetectorRef.markForCheck();
  }

  blCopyUpload($event, id) {
    if ($event === null) {
      this.myForm.controls['bolLetter'].setValue("amend");
      this.myForm.controls['encrBolLetterPath'].setValue("");
      this.myForm.controls['bolLetter'].setErrors({ 'required': true });
      this.fileUploadBLCopy.filesAdded = false;

    } else {
      if (typeof $event != 'object') {
        this.myForm.patchValue({
          bolLetter: $event
        });
        this.myForm.controls['encrBolLetterPath'].setValue("");
      } else if ($event.download || id)
        this.viewFile(id);
      this.chageDetectorRef.markForCheck();

    }
  }

  othDocUpload($event, id) {
    if ($event === null) {
      this.myForm.controls['othDoc'].setValue("");
      this.myForm.controls['encrOthDocPath'].setValue("");
    } else {
      if (typeof $event != 'object') {
        this.myForm.patchValue({
          othDoc: $event
        });
        this.myForm.controls['encrOthDocPath'].setValue("");
      } else if ($event.download || id)
        this.viewFile(id);
      this.chageDetectorRef.markForCheck();
    }
  }

  uploadProof($event) {
    this.payProofShow = true;
    if ($event === null) {
      this.myForm.controls['uploadProof'].setValue("amend");
    } else {
      if ($event.download) {
        this.viewFile($event.id);
      }
      this.myForm.patchValue({
        uploadProof: $event
      });
      this.chageDetectorRef.markForCheck();

    }
  }
  onClear(event) {
    this._dialogService.confirm('All your changes will be lost. Do you want to reset the page?')
      .subscribe(response => {
        if (response != 1) {
          return;
        }
        else {
          this.myForm.reset();
          const notfnMessage: NotificationMessage = {
            text: 'Authorized DO details cleared',
            type: MessageStatus.info
          };
          this.notfnService.updateMessage(notfnMessage);
        }
      });
  }


  onProceed(saveOrAmend) {
    if (this.myForm.controls['tnc'].value === false) {
      this.myForm.controls['tnc'].setValue("");
    }
    if (this.bol.bolInvoice != undefined && this.showPayTab) {
      if (this.myForm.controls['payOption'].value === "-1")
        this.myForm.controls['payOption'].setErrors({ 'required': true });
    }

    if (this.payingInvoice.length === 0) {
      this.myForm.controls['payOption'].setValidators([]);
    }
    if (this.bol.doAuthRequests != undefined)
      this.myForm.controls['status'].setValue(this.bol.doAuthRequests.status);
    this.myForm.controls['reqPartyName'].setValue(this.reqPartyName);
    this.myForm.controls['doAuthRequestId'].setValue(this.doAuthRequestId);
    this.myForm.controls['doAuthRequestIdEncr'].setValue(this.bol.doAuthRequests.doAuthRequestIdEncr);
    this.myForm.controls['doRefNo'].setValue(this.doRefNo);

    if (this.myForm.controls['encrAuthLetterPath'].value)
      this.myForm.controls['authLetter'].setValue('Amend');
    if (this.myForm.controls['encrEmiratesIdCopyPath'].value)
      this.myForm.controls['emiratesIdCopy'].setValue('Amend');
    if (this.myForm.controls['encrBolLetterPath'].value)
      this.myForm.controls['bolLetter'].setValue('Amend');
    if (this.myForm.controls['encrOthDocPath'].value)
      this.myForm.controls['othDoc'].setValue('Amend');


    if (this.bol.bolType === "EBL") {
      if (this.myForm.controls['authLetter'].value.length === 0 && this.bol.doAuthRequests.authDocId === undefined) {
        this.myForm.controls['authLetter'].setErrors({ 'required': true });
        this.fileUploadAuthLetter.filesAdded = false;
        //this._dialogService.alert('Upload Authorization Letter and continue.');
        // return;
      } else if (this.myForm.controls['authLetter'].value === "amend") {
        this.myForm.controls['authLetter'].setErrors({ 'required': true });
        this.fileUploadAuthLetter.filesAdded = false;
        // this._dialogService.alert('Upload Authorization Letter and continue.');
        //  return;
      } else if (this.bol.doAuthRequests.authDocId != undefined) {
        this.myForm.controls['authLetter'].setErrors(null);
      }
    }
    else {
      this.myForm.controls['authLetter'].setErrors(null);
      if (this.myForm.controls['bolLetter'].value === "amend") {
        this.myForm.controls['bolLetter'].setErrors({ 'required': true });
        this.fileUploadBLCopy.filesAdded = false;
      }
      if (this.myForm.controls['emiratesIdCopy'].value === "amend") {
        this.myForm.controls['emiratesIdCopy'].setErrors({ 'required': true });
        this.fileUploadEID.filesAdded = false;
      }
    }
    // if (this.reqPartyBusinessId == 'C' || this.bolPartyBusinessId == 'C') {
    //   if (this.reqPartyBusinessId == 'C') {
    //     this.myForm.controls['bolContactNumber'].setErrors(null);
    //     this.myForm.controls['bolContactPerson'].setErrors(null);
    //     this.myForm.controls['bolEmail'].setErrors(null);
    //     this.myForm.controls['bolPartyName'].setErrors(null);

    //     this.myForm.controls['doContactNumber'].setErrors(null);
    //     this.myForm.controls['doContactPerson'].setErrors(null);
    //     this.myForm.controls['doEmail'].setErrors(null);
    //     this.myForm.controls['doPartyName'].setErrors(null);
    //   }
    //   else {
    //     this.myForm.controls['doContactNumber'].setErrors(null);
    //     this.myForm.controls['doContactPerson'].setErrors(null);
    //     this.myForm.controls['doEmail'].setErrors(null);
    //     this.myForm.controls['doPartyName'].setErrors(null);
    //   }
    // }

    super.validateForm(this.myForm);

    if (this.myForm.valid) {
      var payopt = this.myForm.get('payOption').value;
      if (payopt != -1) {
        if (payopt === "2") {
          if (this.myForm.controls['uploadProof'].value === "") {
            this._dialogService.alert('Upload payment proof and continue.');
            return false;
          } else {
            this.myForm.controls['isCredit'].setValue(false);
            this.myForm.controls['isPay'].setValue(false);
            this.myForm.controls['isPayProof'].setValue(true);
          }
        }
        else {
          if (payopt === "0") {
            if (this.myForm.get('choosePay').value === true) {
              if (this.payingAmt <= 0) {
                this.myForm.controls['choosePay'].setValue(false);
              }
            }
            if (this.myForm.get('payInvoice').value == false && this.myForm.get('choosePay').value === true) {

              this._dialogService.alert('Select Invoice for Payment and continue.');
              return false;
            } else {
              this.myForm.controls['isPay'].setValue(true);
              this.myForm.controls['isCredit'].setValue(false);
              this.myForm.controls['isPayProof'].setValue(false);
            }
          }
          else if (payopt === "1") {
            this.myForm.controls['isCredit'].setValue(true);
            this.myForm.controls['isPay'].setValue(false);
            this.myForm.controls['isPayProof'].setValue(false);
          }
        }

      } else {
        if (this.showPayTab) {
          this._dialogService.alert("Select Payment option and Continue.");
          return;
        }
      }
      if (saveOrAmend == 'AMEND') {
        this.myForm.get('isAmend').setValue(true);
        if (payopt == 2) {
          this._dialogService.alert('DO will be released upon payment realization.')
            .subscribe(isAllow => {
              if (isAllow != 1) {
                return;
              } else {
                this.authorizeDo();
              }
            });
        }
        else {
          this.authorizeDo();
        }
      }
      else if (this.partialPay === 'N') {
        var amt = this.totalAmt - this.paidAmt;
        if (this.payingInvoice.length == 0 && (amt > 0)) {
          if (this.showPayTab)
            this._dialogService.alert("Select atleast one Invoice and continue.");
          else {
            var payingAmt = this.totalAmt - (this.payingAmt + this.paidAmt);
            if (payingAmt > 0) {
              this._dialogService.confirm('Your Total Payment Amount is ' + this.totalAmt + '/- .Your DO request will be processed only once payment is done. Do you want to continue.?')
                .subscribe(retval => {
                  if (retval != 1) {
                    return;
                  } else {
                    if (payopt == 2) {
                      this._dialogService.alert('DO will be released upon payment realization.')
                        .subscribe(isAllow => {
                          if (isAllow != 1) {
                            return;
                          } else {
                            this.authorizeDo();
                          }
                        });
                      //  this.authorizeDo();
                    }
                    else {
                      this.authorizeDo();
                    }
                  }
                });
            }
          }
        } else {
          if (payopt == 2) {
            this._dialogService.alert('DO will be released upon payment realization.')
              .subscribe(isAllow => {
                if (isAllow != 1) {
                  return;
                } else {
                  this.authorizeDo();
                }
              });
            //  this.authorizeDo();
          }
          else {
            this.authorizeDo();
          }
        }
      }
      else {
        if (this.payingInvoice.length == 0) {
          this._dialogService.alert("Select atleast one Invoice and continue.")
        } else {
          this.authorizeDo();
        }
      }
    }
    else {
      const notfnMessage: NotificationMessage = {
        text: 'Please enter all mandatory details',
        type: MessageStatus.error
      };
      this.notfnService.updateMessage(notfnMessage);
      super.scrollToTop();
    }
  }

  authorizeDo() {
    /**AMEND DO */
    if (this.bol.doAuthRequests.doAuthRequestIdEncr != undefined) {
      this._httpRequestService.postData('/do/app/api/secure/getApproverViewedStatus?authIdEncr=' + this.bol.doAuthRequests.doAuthRequestIdEncr, false).subscribe((res) => {
        if (res != undefined && res != null && res.approvedViewed != undefined) {
          if (this.bol.doAuthRequests.status === 'PENDING_FOR_APPROVAL' && res.approvedViewed === 'Y') {
            this.datePipe.transform(new Date(res.viewedDate), "dd-MM-yyyy");
            this._dialogService.alert('Application is under review and logged by ' + res.viewedBy + ' at ' + this.datePipe.transform(new Date(res.viewedDate), "dd-MM-yyyy"));
          }
          else {
            this.authoriseDOonSubmit();
          }
        }
        else {
          this.authoriseDOonSubmit();
        }
      });
    }
    else {
      this.authoriseDOonSubmit();
    }
  }

  authoriseDOonSubmit() {
    this.myForm.get('payingInvoice').setValue(this.payingInvoice);
    this.myForm.get('payingAmt').setValue(this.payingAmt);
    if (this.bol.doAuthRequests.version != undefined) {
      this.version = this.bol.doAuthRequests.version;
      this.myForm.controls['version'].setValue(this.version);
    }
    if (this.myForm.get("bolPartyName").value != undefined) {
      this.myForm.controls['bolPartyName'].setValue(this.myForm.get("bolPartyName").value.label);
    }
    if (this.myForm.get("doPartyName").value != undefined) {
      this.myForm.controls['doPartyName'].setValue(this.myForm.get("doPartyName").value.label);
    }

    this._httpRequestService.postData('/do/app/api/secure/authorizeDOrequestNew?bol=' + this.bol.bolNumber, this.myForm.value, true).subscribe((data) => {
      if (data.message != null && this.myForm.get('isPay').value == true && this.myForm.get('choosePay').value === true) {
        const dataVal: any = {
          serviceOwnerID: data.serviceOwnerId, serviceID: data.rosoomServiceId, serviceChannel: data.rosoomChannel, licenseKey: data.rosoomLicenseKey, customerReferenceNumber: data.custRefNo,
          serviceDescription: data.serviceDesc, responseURL: data.responseUrl, serviceCost: data.totalAmount, soTransactionID: data.soTransactionId, documentationCharges: data.documentCharges, signature: data.base64signatureString,
          popup: data.popup, invoiceNo: data.invoiceNo, buEncryptionMode: data.buEncryptionMode
        };
        super.postToExternalSite(dataVal, data.gatewayUrl);
      } else {
        let confirmStatus = true;
        if (data.status === 'success') {
          confirmStatus = true;
        } else {
          confirmStatus = false;
        }
        const navextras: NavigationExtras = {
          queryParams: { 'data': data.message, 'success': confirmStatus }
        };
        this.notfnService.clearMessage();
        this._router.navigate(['/confirm'], navextras);
      }
    });
    this.myForm.get('isAmend').setValue(false);
  }

  viewFile(id) {
    window.open("/do/app/api/file/downloadauthDocs?id=" + id);
  }

  onChangeBlParty(event) {
    this.myForm.controls['bolEmail'].setValue('');
    const bolAgentCode = this.myForm.get('bolPartyName').value.value;
    if (event.value != undefined)
      this.bolPartytxt = event.value;
    if (bolAgentCode) {
      this._httpRequestService.getData('/do/app/api/secure/getAdminDetailsByAgentCode?agentCode=' + bolAgentCode, false).subscribe((result) => {
        this.myForm.controls['bolEmail'].setValue(result.value);
        this.bolPartyBusinessId = result.businessSubId;
        this.myForm.get('blPartyCode').setValue(result.label);
        this.myForm.get('blPartyType').setValue(result.businessSubId);
        if (this.bolPartyBusinessId == 'C' && this.reqPartyBusinessId == 'C') {
          this.autoFillOptions = [];
        }
        else if (this.bolPartyBusinessId == 'C' && this.reqPartyBusinessId != 'C') {
          this.autoFillOptions = [{ label: 'Same as Requesting Party Details', value: 1 }];
        }
        else if (this.bolPartyBusinessId != 'C' && this.reqPartyBusinessId == 'C') {
          this.autoFillOptions = [{ label: 'Same as BOL Party Details', value: 2 }];
        }
        else {
          this.autoFillOptions = [{ label: 'Same as Requesting Party Details', value: 1 }, { label: 'Same as BOL Party Details', value: 2 }];
        }
      });
    }
  }

  setValues

  onChangeDoParty(event) {
    if (event.action == 2) {
      this.myForm.controls['doEmail'].setValue('');
      const doAgentCode = this.myForm.get('doPartyName').value.value;
      if (doAgentCode) {
        this._httpRequestService.getData('/do/app/api/secure/getDOImporterAgentDetails?q=' + doAgentCode, false).subscribe((result) => {
          this.myForm.controls['doEmail'].setValue(result[0].emailId);
          this.myForm.get('doPartyCode').setValue(result[0].value);
          this.myForm.get('doPartyType').setValue(result[0].businessSubId);
        });
      }
    } else {
      this.myForm.controls['doEmail'].setValidators(null);
      this.myForm.controls['doEmail'].setValue('');
      this.myForm.controls['doContactPerson'].setValue('');
    }
  }
  onChangePay($event) {

    if ($event.event[0] != undefined) {
      if ($event.event[0].value != undefined) {
        this.isDisabled = false;
        if ($event.event[0].value == 2)
          this.payProofShow = true;
        else {
          this.payProofShow = false;
          if ($event.event[0].value == 0)
            this.isDisabled = true;
        }
      }
    }
    // else {
    //   this.payProofShow = false;
    //   this.isDisabled = false;
    // }
  }
  viewInvoice(invEncr) {
    var bol = invEncr;
    window.open("/do/app/api/file/getFile?bol=" + (this.bol.bolNumber + "&invNo=" + bol.encrInvoiceNumber));

  }
  tableAction($event) {
    if ($event != undefined) {
      if ($event.action === 4) {
        this.myForm.controls['payInvoice'].setValue(true);
        this.onChangeInvoiceCheckBox($event.dataRow.invoiceValue, $event.dataRow.invoiceNumber, $event.dataRow.encrInvoiceNumber);
      } else if ($event.action === 5) {
        this.myForm.controls['payInvoice'].setValue(false);
        this.onChangeInvoiceCheckBox($event.dataRow.invoiceValue, $event.dataRow.invoiceNumber, $event.dataRow.encrInvoiceNumber);
      } else if ($event.action === TableAction.download) {
        window.open("/do/app/api/file/getFile?bol=" + (this.bol.bolNumber + "&invNo=" + $event.dataRow.encrInvoiceNumber));
      }
    }
    //this.isInvoiceSelected = !this.isInvoiceSelected;
  }
  onChangeInvoiceCheckBox(invoiceValue, invoiceNumber, invoiceNumberEncr): any {
    if (this.myForm.controls['payInvoice'].value) {
      this.payingAmt = this.payingAmt + invoiceValue;

      this._httpRequestService.getData('/do/app/api/secure/getInvoiceExpiryDate?invoiceNumber=' + encodeURIComponent(invoiceNumber) + '&bol=' + encodeURIComponent(this.bol.bolNumber), false).subscribe((result) => {
        var invoiceDate = result;
        var CurrentDate1 = new Date();
        var CurrentDate = null;
        CurrentDate = CurrentDate1.toDateString();
        CurrentDate = new Date(CurrentDate);
        invoiceDate = new Date(invoiceDate);
        invoiceDate = invoiceDate.toDateString();
        invoiceDate = new Date(invoiceDate);
        var invExpired = false;
        if (invoiceDate != null) {
          // invoiceDate = new Date(invoiceDate);
          if (result != null) {
            if (invoiceDate < CurrentDate) {

              this._dialogService.confirm('Invoice ' + invoiceNumber + ' Expired. Do you want to continue.', "", "OK", "Request For Invoice")
                .subscribe(retval => {
                  if (retval == 1) {
                    return;
                  }
                  else {
                    this.myForm.get('payInvoice').setValue(false);
                    this.payingAmt = this.payingAmt - invoiceValue;
                    let arr = this.payingInvoice;
                    this.payingInvoice.splice(arr.indexOf(invoiceValue))
                    this._dialogService.confirm('Do you want to Request new Invoice.?')
                      .subscribe(retval => {
                        if (retval != 1) {
                          this.myForm.get('payInvoice').setValue(false);
                          this.payingAmt = 0;
                          let arr = this.payingInvoice;
                          this.payingInvoice.splice(arr.indexOf(invoiceValue));
                          return;
                        }
                        else {
                          let invoiceList = new Array;
                          const item = new Item(invoiceNumber, invoiceNumber);
                          invoiceList.push(item)
                          const data = {
                            invoiceNo: invoiceList,
                            shippingAgentCode: this.bol.bolDetails.shippingAgentCode,
                            purpose: 'INVOICE_EXPIRY'
                          };
                          const dialogOptions: DialogOptions = { disableClose: false };

                          this._dialogService.openDialog(RequestInvoiceComponent, data, dialogOptions).subscribe((s) => {
                            if (s.shippingAgentName.value != undefined)
                              s.shippingAgentName = s.shippingAgentName.value;
                            var url = '/do/app/api/secure/requestBLInvoice?id=' + s.selectedInvoice + '&shippingagentCode=' + s.shippingAgentName + '&type=INVOICE_REQUEST';

                            this._httpRequestService.postData(url, null, true).subscribe((result) => {
                              let confirmStatus = true;
                              if (result.status === 'success') {
                                confirmStatus = true;
                              } else {
                                confirmStatus = false;;
                              }
                              const navextras: NavigationExtras = {
                                queryParams: { 'data': result.message, 'success': confirmStatus }
                              };

                              this._router.navigate(['/confirm'], navextras);
                            }, () => {
                              this.notfnService.clearMessage();
                              this._dialogService.alert('Unable to Notify');

                            });
                          });
                        }
                      });

                  }

                });
              invExpired = true;

            }
          }
        }
        this.payingInvoice.push(invoiceNumberEncr + "+" + invExpired);
      });
    } else {
      this.payingAmt = this.payingAmt - invoiceValue;
      let arr = this.payingInvoice;
      this.payingInvoice.splice(arr.indexOf(invoiceValue))

    }
  }

  doSomething(event) {
    //To Do
  }

  onChangesameAsReq(event) {
    if (event === 1) {
      var reqParty = this.reqPartyName;
      this.bolPartyBusinessId = this.reqPartyBusinessId;
      var splitBOLParty = this.reqPartyName.split('-');
      this.myForm.get('blPartyCode').setValue(splitBOLParty[0]);
      this.myForm.get('blPartyType').setValue(this.reqPartyBusinessId);

      if (this.bolPartyBusinessId == 'C' && this.reqPartyBusinessId == 'C') {
        this.autoFillOptions = [];
      }
      else if (this.bolPartyBusinessId == 'C' && this.reqPartyBusinessId != 'C') {
        this.autoFillOptions = [{ label: 'Same as Requesting Party Details', value: 1 }];
      }
      else if (this.bolPartyBusinessId != 'C' && this.reqPartyBusinessId == 'C') {
        this.autoFillOptions = [{ label: 'Same as BOL Party Details', value: 2 }];
      }
      else {
        this.autoFillOptions = [{ label: 'Same as Requesting Party Details', value: 1 }, { label: 'Same as BOL Party Details', value: 2 }];
      }

      if (reqParty != undefined) {
        var strSplit = reqParty.split('-')[0];
        const item = new Item(strSplit, reqParty);
        this.items.push(item);
        this.autocompletebl.refreshItems(this.items, item);
      }
      this.myForm.controls['bolEmail'].setValue(this.myForm.controls['reqEmail'].value != undefined ? this.myForm.controls['reqEmail'].value : null);
      // this.myForm.controls['bolContactNumber'].setValue(this.myForm.controls['reqContactNumber'].value != undefined ? this.myForm.controls['reqContactNumber'].value : null);
      this.BlPartyNumber.setTelNumber(this.myForm.controls['reqContactNumber'].value);
      this.myForm.controls['bolContactPerson'].setValue(this.myForm.controls['reqContactPerson'].value != undefined ? this.myForm.controls['reqContactPerson'].value : null);
    }
    else {
      this.myForm.controls['bolEmail'].setValue(null);
      this.myForm.controls['bolContactNumber'].setValue(null);
      this.myForm.controls['bolPartyName'].setValue(null);
      this.myForm.controls['bolContactPerson'].setValue(null);
    }
  }


  onChangedosameAsReq(event) {
    if (event === 1) {
      var doParty = this.reqPartyName;
      if (doParty != undefined) {

        var strSplit = doParty.split('-')[0];
        const item = new Item(strSplit, doParty);
        this.items.push(item);
        this.autocompletedo.refreshItems(this.items, item);
      }
      this.myForm.controls['doEmail'].setValue(this.myForm.controls['reqEmail'].value != undefined ? this.myForm.controls['reqEmail'].value : null);
      //this.myForm.controls['doContactNumber'].setValue(this.myForm.controls['reqContactNumber'].value != undefined ? this.myForm.controls['reqContactNumber'].value : null);
      this.doContactNumber.setTelNumber(this.myForm.controls['reqContactNumber'].value);
      this.myForm.controls['doContactPerson'].setValue(this.myForm.controls['reqContactPerson'].value != undefined ? this.myForm.controls['reqContactPerson'].value : null);
      this.myForm.get('doPartyCode').setValue(strSplit);
      this.myForm.get('doPartyType').setValue(this.reqPartyBusinessId);

    }
    else {
      this.myForm.controls['doEmail'].setValue(null);
      this.myForm.controls['doContactNumber'].setValue(null);
      this.myForm.controls['doPartyName'].setValue(null);
      this.myForm.controls['doContactPerson'].setValue(null);
      var doPartyName = this.myForm.controls['bolPartyName'].value.label;

      if (doPartyName != undefined) {
        var strSplitbol = doPartyName.split('-')[0];
        const item = new Item(strSplitbol, doPartyName);
        this.items.push(item);
        this.autocompletedo.refreshItems(this.items, item);
      }
      this.myForm.controls['doEmail'].setValue(this.myForm.controls['bolEmail'].value != undefined ? this.myForm.controls['bolEmail'].value : null);
      //this.myForm.controls['doContactNumber'].setValue(this.myForm.controls['bolContactNumber'].value != undefined ? this.myForm.controls['bolContactNumber'].value : null);
      this.doContactNumber.setTelNumber(this.myForm.controls['bolContactNumber'].value);
      this.myForm.controls['doContactPerson'].setValue(this.myForm.controls['bolContactPerson'].value != undefined ? this.myForm.controls['bolContactPerson'].value : null);
      this.myForm.get('doPartyCode').setValue(strSplitbol);
      this.myForm.get('doPartyType').setValue(this.bolPartyBusinessId);

    }
  }

  onChangesameAsBol(event) {
    if (event.event.checked) {
      var doParty = this.bolPartyName;
      if (doParty != undefined) {
        var strSplit = doParty.split('-')[0];
        const item = new Item(strSplit, doParty);
        this.items.push(item);
        this.autocompletedo.refreshItems(this.items, item);
      }
      this.myForm.controls['doEmail'].setValue(this.myForm.controls['bolEmail'].value != undefined ? this.myForm.controls['bolEmail'].value : null);
      //this.myForm.controls['doContactNumber'].setValue(this.myForm.controls['bolContactNumber'].value != undefined ? this.myForm.controls['bolContactNumber'].value : null);
      this.doContactNumber.setTelNumber(this.myForm.controls['bolContactNumber'].value);
      this.myForm.controls['doContactPerson'].setValue(this.myForm.controls['bolContactPerson'].value != undefined ? this.myForm.controls['bolContactPerson'].value : null);

    }
    else {
      this.myForm.controls['doEmail'].setValue(null);
      this.myForm.controls['doContactNumber'].setValue(null);
      this.myForm.controls['doPartyName'].setValue(null);
      this.myForm.controls['doContactPerson'].setValue(null);
    }
  }
  onChangeTC($event) {
    console.log($event);
    if (!$event.event.checked) {
      this.myForm.controls['tnc'].setErrors({ 'required': true });
    }
  }
}
