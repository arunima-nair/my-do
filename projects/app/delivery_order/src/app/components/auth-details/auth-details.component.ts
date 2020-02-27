import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { BaseComponent } from '../../lib/classes/BaseComponent';
import { AuthoriseDO } from '../../model/authorise-do.model';
import { Bol } from '../../model/bol.model';
import { BolDetail } from '../../model/bol-detail.model';
import { BolInvoice } from '../../model/bol-invoice.model';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { UploadDoComponent } from 'src/app/components/upload-do/upload-do.component';
import { RejectDoComponent } from 'src/app/components/reject-do/reject-do.component';
import { DialogOptions } from 'src/app/lib/service/dialog.service';
import { RequestInvoiceComponent } from '../request-invoice/request-invoice.component';
import { TableDefn, TableType } from 'src/app/lib/classes/TableDefn';
import { TableComponent } from 'src/app/lib/components/table/table.component';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { formatDate, DatePipe } from '@angular/common';
import { TrackDoModelComponent } from '../track-do/track-do-model/track-do-model.component';
import { NormalTableComponent } from 'src/app/lib/components/table/normal-table.component';
import { utils } from 'protractor';

class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}
@Component({
  selector: 'app-auth-details',
  templateUrl: './auth-details.component.html',
  styleUrls: ['./auth-details.component.css']
})
export class AuthDetailsComponent extends BaseComponent implements OnInit {

  myForm: FormGroup;
  formBuilder: FormBuilder;
  authoriseDO: AuthoriseDO;
  dataItems: any[];
  data: any;
  actionVal: string;
  user: string;
  url: string;
  dostring: string;
  authDoc: any[];
  payStatus: string;
  payProof: string;
  paidBy: string;
  totalAmt: number = 0;
  paidAmt: number = 0;
  paidInvoice: any[] = new Array();
  paylogInvoice: BolInvoice[] = [];
  payingAmt: number = 0;
  payingInvoice: any[] = new Array();
  paid: Boolean;
  agentType: String;
  showDialog: boolean;
  tableItem: BolInvoice[] = [];
  authoriseDOList: AuthoriseDO[] = [];
  userType: string;
  selectedList: any[] = new Array();
  isShowTable: boolean;
  payUploadProofMaxFileSizeMb: string;
  uploadDOMaxFileSizeMb: string;

  approverViewedBy: string;
  userName: String;

  @Output() backEvent = new EventEmitter();

  @ViewChild(TableComponent) dataTable: TableComponent;
  @ViewChild('invoiceTable') invoiceTable: NormalTableComponent;


  tableItems: any[] = [];
  approvalLogItems: any[] = [];

  tableDfn: TableDefn[] = [
    { displayName: 'Invoice No', mappingName: 'invoiceNumber', type: TableType.string, sort: true },
    { displayName: 'Invoice Type', mappingName: 'invoiceTypeName', type: TableType.string, sort: true },
    { displayName: 'Invoice Value', mappingName: 'invoiceValue', type: TableType.string, sort: true },
    { displayName: 'Invoice Currency', mappingName: 'currency', type: TableType.string, sort: true },
    { displayName: 'Payment Status', mappingName: 'status', type: TableType.string },
    { displayName: 'Refer No', mappingName: 'refNumber', type: TableType.string },
    { displayName: 'Payment Type', mappingName: 'collectionType', type: TableType.string },
    { displayName: 'Paid By', mappingName: 'createdBy', type: TableType.string }
  ];

  approvalLogDfn: TableDefn[] = [
    { displayName: 'DATE', mappingName: 'createdDate', type: TableType.string, sort: true },
    { displayName: 'ACTION', mappingName: 'status', type: TableType.string, sort: true },
    { displayName: 'USER NAME', mappingName: 'createdBy', type: TableType.string },
    { displayName: 'REMARKS', mappingName: 'remarks' },
    { displayName: 'COMMENTS', mappingName: 'comments' }
  ];
  approvalLogDfnImporter: TableDefn[] = [
    { displayName: 'DATE', mappingName: 'createdDate', type: TableType.string, sort: true },
    { displayName: 'ACTION', mappingName: 'status', type: TableType.string, sort: true },
    { displayName: 'USER NAME', mappingName: 'createdBy', type: TableType.string },
    { displayName: 'COMMENTS', mappingName: 'comments' }
  ];

  DO_HistoryLog_Defn: TableDefn[] = [
    { displayName: 'Created Date', mappingName: 'tempCreatedDate' },
    { displayName: 'Created By', mappingName: 'createdBy' },
    { displayName: 'Modified Date', mappingName: 'tempmodifiedDate' },
    { displayName: 'Modified By', mappingName: 'modifiedBy' }
  ];
  constructor(private _fb: FormBuilder, private route: ActivatedRoute, private _securityInfo: SecurityInfoService, private datePipe: DatePipe) {
    super();
    this._pageHeaderService.updateHeader('Delivery Order Details');
    this.agentType = this._securityInfo.getAgentType();
    this.userType = this._securityInfo.getUserType();

    this._httpRequestService.getData('/do/app/api/secure/getDocFileSize', false).subscribe((sizeres) => {
      if (sizeres != undefined) {
        if (sizeres.length > 0) {

          this.payUploadProofMaxFileSizeMb = sizeres[4].value;
          this.uploadDOMaxFileSizeMb = sizeres[5].value;

        }
      }
    });
    this._httpRequestService.getData('/do/app/api/secure/getDODetailByAuthRequestId?authRequestId=' + this.route.snapshot.queryParams.doAuthRequestId
      , true).subscribe((res) => {

        this.authoriseDO = new AuthoriseDO;
        this.authoriseDO.bol = new Bol;
        this.authoriseDOList.push(res.dataItems[0]);
        this.authoriseDO.bol.bolDetails = new BolDetail;

        this.authoriseDO.bol.bolDetails = new BolDetail;
        if (res.dataItems[0] != undefined)
          this.authoriseDO = res.dataItems[0];

        this.authoriseDO.bol = res.dataItems[0].bol;
        this.authoriseDO.bol.status = res.dataItems[0].bol.status;
        this.authoriseDO.bol.bolNumber = res.dataItems[0].bol.bolNumber;
        this.authoriseDO.bol.bolDetails = res.dataItems[0].bol.bolDetails[0];
        this.authoriseDO.bol.bolInvoice = res.dataItems[0].bol.bolInvoices;
        this.authoriseDO.approvalLog = res.dataItems[0].approvalLog;
        this.authoriseDO.doAuthRequestIdEncr = res.dataItems[0].doAuthRequestIdEncr;
        this.isShowTable = this.route.snapshot.routeConfig.path && this.route.snapshot.routeConfig.path === 'paymentDO' ? true : false;
        // this.authoriseDO.approvalLog = this.sortData(this.authoriseDO.approvalLog);
        if (this.route.snapshot.routeConfig.path === 'approveDO') {
          if (this.userType === 'A') {


            console.log(this.userName);
            this._httpRequestService.postData('/do/app/api/secure/getApproverViewedStatus?authIdEncr=' + this.authoriseDO.doAuthRequestIdEncr, false).subscribe((res) => {
              if (res != undefined && res != null) {
                console.log(res.viewedBy);
                this.userName = res.userName;
                if (res.viewedBy != undefined) {
                  if (this.userName === res.viewedBy) {

                  } else {
                    console.log("----------" + res.viewedBy);
                    var msg = 'Application is under review and logged by ' + res.viewedBy + ' at ' + this.datePipe.transform(new Date(res.viewedDate), "dd-MM-yyyy");
                    this._dialogService.alert(msg)
                      .subscribe(isAllow => {
                        if (isAllow != 1) {
                          const navextras: NavigationExtras = {
                            queryParams: { 'bolNo': this.authoriseDO.bol.bolNumber.toUpperCase() }
                          };
                          if (this.route.snapshot.queryParams.parent != undefined)
                            this._router.navigate([this.route.snapshot.queryParams.parent], navextras);
                          else
                            window.history.back();
                        } else {
                          const navextras: NavigationExtras = {
                            queryParams: { 'bolNo': this.authoriseDO.bol.bolNumber.toUpperCase() }
                          };
                          if (this.route.snapshot.queryParams.parent != undefined)
                            this._router.navigate([this.route.snapshot.queryParams.parent], navextras);
                          else
                            window.history.back();
                        }
                      });

                  }
                }
              }
            });


          }
        }
        if (this.authoriseDO.approvalLog != undefined) {
          for (let log of this.authoriseDO.approvalLog as any) {
            //let date = new Date(log.createdDate);
            // let formatedDate = formatDate(date, 'dd-MM-yyyy', 'en-US', '+0530');
            var ddMMyyyy = this.datePipe.transform(log.createdDate, "dd-MM-yyyy HH:mm");
            log.createdDate = ddMMyyyy;
            if (log.returnRemark != null)
              log.remarks = log.returnRemark.remarks;
            else if (log.rejectionRemark != null)
              log.remarks = log.rejectionRemark.remarks;
            this.approvalLogItems.push(log);
          }
        }
        // for (let entry of this.authoriseDO.bol.bolInvoice as any) {
        //   var amt = Number(entry.invoiceValue);
        //   this.totalAmt = this.totalAmt + amt;

        // }

        if (res.dataItems[0].doAuthDocs != undefined) {
          this.authoriseDO.doAuthDocs = res.dataItems[0].doAuthDocs;
          this.authDoc = res.dataItems[0].doAuthDocs;

          if (this.authDoc != null) {
            if (this.authDoc.length != undefined)
              for (let i of this.authDoc) {
                if (i.documentBean.documentValue == 'AUTHORIZATION_LETTER') {
                  this.authoriseDO.encrDoAuthDocsId = i.encrDoAuthDocsId;
                  this.authoriseDO.encrAuthLetterPath = i.encrDocumentPath;
                }
                else if (i.documentBean.documentValue === 'EMIRATES_ID') {
                  this.authoriseDO.encEmDocId = i.encrDoAuthDocsId;
                  this.authoriseDO.encrEmiratesIdCopyPath = i.encrDocumentPath;
                }
                else if (i.documentBean.documentValue === 'BL_COPY') {
                  this.authoriseDO.encBlDocId = i.encrDoAuthDocsId;
                  this.authoriseDO.encrBolLetterPath = i.encrDocumentPath;
                }
                else if (i.documentBean.documentValue === 'OTHER_DOCUMENT') {
                  this.authoriseDO.encOthDocId = i.encrDoAuthDocsId;
                  this.authoriseDO.encrOthDocPath = i.encrDocumentPath;
                }
              }
          }
        }
        if (this.authoriseDO.bolPartyName === undefined)
          this.authoriseDO.bolPartyName = "";
        if (this.authoriseDO.reqPartyName === undefined)
          this.authoriseDO.reqPartyName = "";
        if (this.authoriseDO.doPartyName === undefined)
          this.authoriseDO.doPartyName = "";
        if (this.authoriseDO.doContactNumber === undefined)
          this.authoriseDO.doContactNumber = "";
        if (this.authoriseDO.doContactPerson === undefined)
          this.authoriseDO.doContactPerson = "";
        if (this.authoriseDO.doEmail === undefined)
          this.authoriseDO.doEmail = "";
        this.user = this.route.snapshot.queryParams.user;
        this.actionVal = this.route.snapshot.queryParams.actionVal;

        if (this.authoriseDO.bol.bolInvoice) {
          if (this.authoriseDO.bol.bolInvoice[0].collections === undefined) {
            this.payStatus = "PENDING"
          }
          else {
            for (let i of this.authoriseDO.bol.bolInvoice[0].collections as any) {
              this.paidAmt = this.paidAmt + Number(i.amount);
              this.payStatus = i.collectionType;
              this.paidInvoice.push(i.invoiceNumber);

              // if (this.payStatus === 'UPLOAD_PAYMENT_PROOF')
              //   if (res.dataItems[0].collections[0].paymentOfProofs != undefined) {
              //     this.payProof = res.dataItems[0].collections[0].paymentOfProofs[0].paymentOfProofId;
              //   }
            }
          }
        }
        else {
          this.tableItems = [];
          this.invoiceTable.refreshTable(this.tableItems);
        }

        /*SHOWING INVOICES ONLY IN PAYMENT LOG*/
        if (res.dataItems[0].paymentLogs != undefined) {
          this.paidBy = res.dataItems[0].paymentLogs[0].paidBy;
          if (this.route.snapshot.routeConfig.path === 'paymentDO') {
            if (this.authoriseDO.bol.bolInvoice) {
              for (let pay of res.dataItems[0].paymentLogs) {
                for (let bolInv of this.authoriseDO.bol.bolInvoice) {
                  if (pay.invoiceNumber === bolInv.invoiceNumber) {
                    if (bolInv.status === 'PAYMENT_PENDING_WITH_IMPORTER') {
                      this.payingAmt = this.payingAmt + Number(pay.amount);
                      this.paylogInvoice.push(bolInv);
                      bolInv['invoiceTypeName'] = bolInv.invoiceType.invoiceTypeName;
                      if (bolInv.collections[0] && this.authoriseDO.status != 'REJECTED') {
                        bolInv['status'] = parseInt(bolInv.invoiceValue) == 0 ? '' : (bolInv.collections != null && bolInv.collections != undefined) ? (bolInv.collections[0].status == 'SUCCESS' ? 'PAID' : 'NOT PAID') : 'NOT PAID';
                        bolInv['collectionType'] = (bolInv.collections != null && bolInv.collections != undefined) ? (bolInv.collections[0].collectionType) : "-";
                        bolInv.refNumber = bolInv.collections[0].refNumber;
                        bolInv['createdBy'] = bolInv.collections[0].createdBy
                        bolInv.disablecheck = false;
                      }
                      else if (this.authoriseDO.status == 'REJECTED') {
                        /*   bolInv['status'] = '-';
                           bolInv['collectionType'] = '-';
                           bolInv['createdBy'] = '-';
                           bolInv.disablecheck = true;
                           bolInv.refNumber = bolInv.collections[0].refNumber;*/
                        bolInv['status'] = parseInt(bolInv.invoiceValue) == 0 ? '' : (bolInv.collections != null && bolInv.collections != undefined) ? (bolInv.collections[0].status == 'SUCCESS' ? 'PAID' : 'NOT PAID') : 'NOT PAID';
                        bolInv['collectionType'] = (bolInv.collections != null && bolInv.collections != undefined) ? (bolInv.collections[0].collectionType) : "-";
                        bolInv.refNumber = bolInv.collections[0].refNumber;
                        bolInv['createdBy'] = bolInv.collections[0].createdBy
                        bolInv.disablecheck = true;
                      }
                      else {
                        bolInv['status'] = 'NOT PAID';
                        bolInv['collectionType'] = '-';
                        bolInv['createdBy'] = '-';
                        bolInv.disablecheck = true;
                        bolInv.refNumber = bolInv.collections[0].refNumber;
                      }
                      this.tableItems.push(bolInv);
                    }
                  }
                }
              }
              if (this.invoiceTable != undefined)
                this.invoiceTable.refreshTable(this.tableItems);
            }
          }
          else {
            if (this.authoriseDO.bol.bolInvoice)
              for (let bolInv of this.authoriseDO.bol.bolInvoice) {
                this.paylogInvoice.push(bolInv);
                bolInv['invoiceTypeName'] = (bolInv.invoiceType != null) ? bolInv.invoiceType.invoiceTypeName : "-";
                if (bolInv.collections[0] && this.authoriseDO.status != 'REJECTED') {
                  bolInv['status'] = parseInt(bolInv.invoiceValue) == 0 ? '' : (bolInv.collections != null && bolInv.collections != undefined) ? (bolInv.collections[0].status == 'SUCCESS' ? 'PAID' : 'NOT PAID') : 'NOT PAID';
                  bolInv['collectionType'] = (bolInv.collections != null && bolInv.collections != undefined) ? (bolInv.collections[0].collectionType) : "-";
                  bolInv.refNumber = bolInv.collections[0].refNumber;
                  bolInv['createdBy'] = bolInv.collections[0].createdBy
                }
                else if (this.authoriseDO.status == 'REJECTED') {
                  /*  bolInv['status'] = '-';
                    bolInv['collectionType'] = '-';
                    bolInv['createdBy'] = '-';
                    bolInv.disablecheck = true;
                    bolInv.refNumber = bolInv.collections[0].refNumber;*/
                  bolInv['status'] = parseInt(bolInv.invoiceValue) == 0 ? '' : (bolInv.collections != null && bolInv.collections != undefined) ? (bolInv.collections[0].status == 'SUCCESS' ? 'PAID' : 'NOT PAID') : 'NOT PAID';
                  bolInv['collectionType'] = (bolInv.collections != null && bolInv.collections != undefined) ? (bolInv.collections[0].collectionType) : "-";
                  bolInv.refNumber = bolInv.collections[0].refNumber;
                  bolInv['createdBy'] = bolInv.collections[0].createdBy
                  bolInv.disablecheck = true;
                }
                else {
                  bolInv['status'] = 'NOT PAID';
                  bolInv['collectionType'] = '-';
                  bolInv['createdBy'] = '-';
                  bolInv.refNumber = bolInv.collections[0].refNumber;
                }
                this.tableItems.push(bolInv);
              }
            if (this.invoiceTable != undefined)
              this.invoiceTable.refreshTable(this.tableItems);
          }
          if (this.route.snapshot.routeConfig.path != 'paymentDO')
            this.payingAmt = this.payingAmt - this.paidAmt;

          if (this.totalAmt - (this.paidAmt + this.payingAmt) != 0) {
            this.paid = true;
            this.myForm.controls['paid'].setValue(true);
          } else
            this.myForm.controls['paid'].setValue(false);
        }



      });




  }

  ngOnInit() {

    this.myForm = this._fb.group({
      doAuthRequestId: ['', []],
      reqPartyName: ['', []],
      reqContactPerson: ['', []],
      reqEmail: ['', []],
      reqContactNumber: ['', []],
      bolContactNumber: ['', []],
      bolContactPerson: ['', []],
      bolPartyName: ['', []],
      bolEmail: ['', []],
      doContactNumber: ['', []],
      doContactPerson: ['', []],
      doEmail: ['', []],
      doPartyName: ['', []],
      tnc: [''],
      authLetter: ['', []],
      othDoc: ['', []],
      bolLetter: [''],
      emiratesIdCopy: [''],
      isPay: [false],
      pay: [false],
      payOption: [''],
      payAmount: [''],
      bankTrAdvice: [''],
      bolNumber: ['', []],
      isCredit: [''],
      uploadDo: [''],
      user: [''],
      actionVal: [''],
      payStatus: [''],
      paid: [false],
      payProof: [''],
      paidBy: [''],
      totalAmt: [0],
      paidAmt: [0],
      payInvoice: [''],
      paylogInvoice: [],
      payingInvoice: [],
      payingAmt: [0],
      oblCopy: [''],
      payUploadProofMaxFileSizeMb: ['0.5'],
      uploadDOMaxFileSizeMb: ['0.5']

    });

  }

  onSubmit(status, uploaddoc) {
    super.validateForm(this.myForm);
    if (this.myForm.valid) {

      if (status === 'COMPLETED') {
        if (uploaddoc === null) {
          uploaddoc = "null";
          this._dialogService.confirm('DO YOU WANT TO CONTINUE WITHOUT UPLOADING DO.')
            .subscribe(retval => {
              if (retval != 1) {
                this.url = null;
                return;
              }
              else {
                this.url = '/do/app/api/secure/approveDO?status=' + status + '&id=' + this.authoriseDO.doAuthRequestIdEncr, uploaddoc;
                if (this.url != null)
                  this._httpRequestService.postData(this.url, uploaddoc, true).subscribe((result) => {
                    let confirmStatus = true;
                    if (result.status === 'success') {
                      confirmStatus = true;
                    } else {
                      confirmStatus = false;
                    }
                    const navextras: NavigationExtras = {
                      queryParams: { 'data': result.message, 'success': confirmStatus }
                    };
                    this._router.navigate(['/confirm'], navextras);
                  }, () => {
                    this.notfnService.clearMessage();
                    this._dialogService.alert('Unable to save data');

                  });
              }
            });
        } else {
          this.url = '/do/app/api/secure/approveDO?status=' + status + '&id=' + this.authoriseDO.doAuthRequestIdEncr, uploaddoc;

        }

      }
      if (status === 'RETURNED' || status === 'REJECTED') {
        uploaddoc = "null";
        this.url = '/do/app/api/secure/approveDO?status=' + status + '&id=' + this.authoriseDO.doAuthRequestIdEncr, uploaddoc;

      }

      if (this.url != null)
        this._httpRequestService.postData(this.url, uploaddoc, true).subscribe((result) => {
          let confirmStatus = true;
          if (result.status === 'success') {
            confirmStatus = true;
          } else {
            confirmStatus = false;
          }
          const navextras: NavigationExtras = {
            queryParams: { 'data': result.message, 'success': confirmStatus }
          };
          this._router.navigate(['/confirm'], navextras);
        }, () => {
          this.notfnService.clearMessage();
          this._dialogService.alert('Unable to save data');

        });
    }
    // this.click.emit($event);
  }
  onRejectReturnCancel(status) {
    super.validateForm(this.myForm);
    if (this.myForm.valid) {
      var msg = null;
      if (status === "REJECTED") {
        msg = "REJECT DO!!";
        this._dialogService.confirm('DO YOU WANT TO ' + msg)
          .subscribe(retval => {
            if (retval != 1) {
              this.url = null;
              return;
            } else {
              this._dialogService.openDialog(RejectDoComponent, status, status).subscribe((s) => {
                this.url = '/do/app/api/secure/rejectReturnDO?status=' + status + '&id=' + this.authoriseDO.doAuthRequestIdEncr + '&remarkId=' + s.selectItem + '&comments=' + s.otherComments;
                this._httpRequestService.postData(this.url, this.tableItem, true).subscribe((result) => {
                  let confirmStatus = true;
                  if (result.status === 'success') {
                    confirmStatus = true;
                  } else {
                    confirmStatus = false;
                  }
                  const navextras: NavigationExtras = {
                    queryParams: { 'data': result.message, 'success': confirmStatus }
                  };
                  this._router.navigate(['/confirm'], navextras);
                }, () => {
                  this.notfnService.clearMessage();
                  this._dialogService.alert('Unable to save data');

                });
              });
            }

          });
      }
      if (status === "RETURNED") {
        msg = "RETURN DO FOR MORE INFORMATION!!";
        this._dialogService.confirm('DO YOU WANT TO ' + msg)
          .subscribe(retval => {
            if (retval != 1) {
              this.url = null;
              return;
            } else {
              const navextras: NavigationExtras = {
                queryParams: {
                  'id': this.authoriseDO.doAuthRequestIdEncr, 'bol': this.authoriseDO.bol.encBolNumber
                }
              };
              this._router.navigate(['/returndo'], navextras);
            }

          });
      }
      if (status === "CANCELLED") {
        msg = "CANCEL DO!!";
        this._httpRequestService.postData('/do/app/api/secure/getApproverViewedStatus?authIdEncr=' + this.authoriseDO.doAuthRequestIdEncr, false).subscribe((res) => {
          if (res != undefined && res != null) {
            if (this.authoriseDO.status === 'PENDING_FOR_APPROVAL' && res.approvedViewed === 'Y') {
              this.datePipe.transform(new Date(res.viewedDate), "dd-MM-yyyy");
              this._dialogService.alert('Your application is under review. ');
            } else {
              this._dialogService.confirm('DO YOU WANT TO ' + msg)
                .subscribe(retval => {
                  if (retval != 1) {
                    this.url = null;
                    return;
                  } else {
                    var uploaddoc = "null";
                    this.url = '/do/app/api/secure/approveDO?status=' + status + '&id=' + this.authoriseDO.doAuthRequestIdEncr, uploaddoc;
                    this._httpRequestService.postData(this.url, uploaddoc, true).subscribe((result) => {
                      let confirmStatus = true;
                      if (result.status === 'success') {
                        confirmStatus = true;
                      } else {
                        confirmStatus = false;
                      }
                      const navextras: NavigationExtras = {
                        queryParams: { 'data': result.message, 'success': confirmStatus }
                      };
                      this._router.navigate(['/confirm'], navextras);
                    }, () => {
                      this.notfnService.clearMessage();
                      this._dialogService.alert('Unable to save data');

                    });
                  }
                });
            }
          }
        });
      }
    }
  }


  close() {
    this.notfnService.clearMessage();
    const navextras: NavigationExtras = {
      queryParams: { 'bolNo': this.authoriseDO.bol.bolNumber }
    };
    if (this.route.snapshot.queryParams.parent != undefined) {
      this._router.navigate([this.route.snapshot.queryParams.parent], navextras);
    }

    else {
      window.history.back();
    }
    // this.backEvent.emit();
  }
  pay(payOpt, proofupload) {
    var param = null;
    var url = "";
    if (this.payingAmt <= 0) {
      this.myForm.controls['pay'].setValue(false);

    }
    if (payOpt != 2) {
      if (this.tableItems != undefined && this.tableItems.length > 0) {
        if (this.selectedList != undefined && this.selectedList.length > 0) {
          param = this.selectedList;
          //doreq encr id
          url = '/do/app/api/secure/paymentDONew?doAuthReqIdStr=' + this.authoriseDO.doAuthRequestIdEncr;
        } else {
          this._dialogService.alert('Select invoice and continue.');
          return;
        }
      }
    } else {
      param = proofupload;
      url = '/do/app/api/secure/paymentDOProof?bolNo=' + this.authoriseDO.bol.bolNumber;
    }
    this._httpRequestService.postData(url, param, true).subscribe((data) => {
      if (data.message != null && payOpt == 0) {
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
      this.myForm.reset();
    });
    /*else {
     this._dialogService.alert('Paying amount should be greater than Zero.');
     return false;
   }*/
  }
  upoloadDO(status, $event, userName) {
    this._httpRequestService.postData('/do/app/api/secure/getApproverViewedStatus?authIdEncr=' + this.authoriseDO.doAuthRequestIdEncr, false).subscribe((res) => {
      if (res != undefined && res != null) {
        if (userName === res.viewedBy) {
          if (this.authoriseDO.status === "DO_INITIATED") {
            this._dialogService.alert('Payment pending.')
              .subscribe(retval => console.log(' Dialog Data ', retval));
          } else {
            const data = {
              isOBL: this.authoriseDO.bol.bolType,
              status: 'PENDING_FOR_APPROVAL',
              fileSize: this.uploadDOMaxFileSizeMb
            };
            const dialogOptions: DialogOptions = { disableClose: false };
            this._dialogService.openDialog(UploadDoComponent, data, dialogOptions).subscribe((s) => {
              if (s != undefined) {
                this.myForm.patchValue({
                  uploadDo: s
                });
                this.onSubmit(status, s);
                this.dostring = s;
              }
            });

          }
        } else {

          this._dialogService.alert('Application is under review and logged by ' + res.viewedBy + ' at ' + this.datePipe.transform(new Date(res.viewedDate), "dd-MM-yyyy"));
          return;
        }
      }
    });
  }
  viewFile(data) {
    const id: any = data;
    //  window.open("/do/app/api/file/downloadauthDocs?id=" + id);
    super.postToExternalSite([], "/do/app/api/file/downloadauthDocs?id=" + id);

  }
  viewDOFile(id) {
    // window.open("/do/app/api/file/downloadDo?id=" + id);
    super.postToExternalSite([], "/do/app/api/file/downloadDo?id=" + id);
  }
  viewInvoice(event, bol) {
    const id: any = event.dataRow.encrInvoiceNumber;
    // window.open("/do/app/api/file/getFile?bol=" + bol + "&invNo=" + id);
    super.postToExternalSite([], "/do/app/api/file/getFile?bol=" + bol + "&invNo=" + id);
  }
  viewPayProof(event) {
    const id: any = event.id;
    // window.open("/do/app/api/file/getPayProof?id=" + id);
    super.postToExternalSite([], "/do/app/api/file/getPayProof?id=" + id);
  }
  onChangeInvoiceCheckBox(invoiceValue, invoiceNumber, invoiceNumberEncr) {
    if (this.myForm.controls['payInvoice'].value) {
      this.payingAmt = this.payingAmt + invoiceValue;

      this._httpRequestService.getData('/do/app/api/secure/getInvoiceExpiryDate?invoiceNumber=' + encodeURIComponent(invoiceNumber) + '&bol=' + encodeURIComponent(this.authoriseDO.bol.bolNumber), true).subscribe((result) => {
        var invoiceDate = result;
        var CurrentDate1 = new Date();
        var CurrentDate = null;
        CurrentDate = CurrentDate1.toDateString();
        CurrentDate = new Date(CurrentDate);
        invoiceDate = new Date(invoiceDate);
        invoiceDate = invoiceDate.toDateString();
        invoiceDate = new Date(invoiceDate);
        var invExpired = false;
        if (result != null) {
          if (invoiceDate < CurrentDate) {
            this._dialogService.confirm('Invoice ' + invoiceNumber + ' is expired. Do you want to continue.')
              .subscribe(retval => {
                if (retval == 1) {
                  return;
                }
                else {
                  this.myForm.get('payInvoice').setValue(false);

                  this._dialogService.confirm('Do you want to Request new Invoice.?')
                    .subscribe(retval => {
                      if (retval != 1) {
                        this.payingAmt = this.payingAmt - invoiceValue;
                        return;
                      }
                      else {
                        this.payingAmt = this.payingAmt - invoiceValue;
                        let invoiceList = new Array;
                        const item = new Item(invoiceNumber, invoiceNumber);
                        invoiceList.push(item)
                        const data = {
                          invoiceNo: invoiceList,
                          shippingAgentCode: this.authoriseDO.bol.bolDetails.shippingAgentCode
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
                              confirmStatus = false;
                            }
                            const navextras: NavigationExtras = {
                              queryParams: { 'data': result.message, 'success': confirmStatus }
                            };
                            this.notfnService.clearMessage();
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
        this.payingInvoice.push(invoiceNumberEncr + "+" + invExpired);
      });
    } else {
      this.payingAmt = this.payingAmt - invoiceValue;
      let arr = this.payingInvoice;
      this.payingInvoice.splice(arr.indexOf(invoiceValue))
    }

  }


  //selectedInvoice
  tableAction(event) {
    if (event.action == 4) {
      this.selectedList.push(event.dataRow.encrInvoiceNumber)
    }
    else if (event.action == 5) {
      this.selectedList.splice(event.dataRow.encrInvoiceNumber, 1)
    }
    else if (event.action == 3) {
      const dialogOptions: DialogOptions = { disableClose: false };
      event['bolNo'] = this.authoriseDO.bol.bolNumber;
      let data = event;

      this._dialogService
        .openDialog(TrackDoModelComponent, data, dialogOptions)
        .subscribe(retval => {
        });
    }
  }
  approvalLogAction(event) {
    //To Do
  }

  printReciept(event) {
    if (event.dataRow.collections != undefined) {
      if (event.dataRow.collections[0].status === "SUCCESS") {
        if (event.dataRow.collections[0].collectionType === "ONLINE_PAYMENT") {
          window.open("/do/app/api/file/recieptInvoiceNo?invoiceNo=" + event.dataRow.collections[0].encrInvoiceNumber + '&agentCode=' + this._securityInfo.getAgentCode() + '&agentType=' + this.agentType);
        }
        else if (event.dataRow.collections[0].collectionType === "CREDIT") {
          this._dialogService.alert('No Invoice Available for CREDIT Payment');
        }
        else if (event.dataRow.collections[0].collectionType = 'UPLOAD_PAYMENT_PROOF') {
          window.open("/do/app/api/file/DowloadRecipt?collectionId=" + event.dataRow.collections[0].encrCollectionId);
        }
      }
    }
    else {
      this._dialogService.alert('Payment pending.');
    }
  }

  // sortData(data) {
  //   return data.sort((a, b) => {
  //     return <any>new Date(b.createdDate) - <any>new Date(a.createdDate);
  //   });
  // }
}
