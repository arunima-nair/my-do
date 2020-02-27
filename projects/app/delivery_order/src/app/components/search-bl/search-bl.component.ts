import { Component, OnInit, Input, ViewChild, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormArray, Validators } from '@angular/forms';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { TableType, TableDefn } from 'src/app/lib/classes/TableDefn';
import { TableComponent } from 'src/app/lib/components/table/table.component';
import { AmendBlComponent } from 'src/app/components/amend-bl/amend-bl.component';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { RequestBlComponent } from '../request-bl/request-bl.component';
import { RequestInvoiceComponent } from '../request-invoice/request-invoice.component';
import { DialogOptions } from 'src/app/lib/service/dialog.service';
import { AutoCompleteComponent } from 'src/app/lib/components/auto-complete/auto-complete.component';
import { Validator } from 'src/app/lib/classes/field.interface';
import { NotificationMessage, MessageStatus } from 'src/app/lib/classes/NotificationMessage';
import { InvoiceType } from 'src/app/model/invoice-type';
import { NavigationHandlerService } from 'src/app/lib/service/navigation-handler.service';
import { formatDate } from '@angular/common';
declare var dtframeResizer: any;
class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}
@Component({
  selector: 'app-search-do',
  templateUrl: './search-bl.component.html',
  styleUrls: ['./search-bl.component.css']
})
export class SearchDoComponent extends BaseComponent implements OnInit {
  myForm: FormGroup;
  flag: boolean;
  agentType: string;
  agentCode: string;
  userType: string;
  shippingAgent: string;
  shippingAgentList: any;
  importerNameList: any;
  importerName: string;
  url: string;
  searchFlag: boolean = true;
  isEdit: Boolean = false;
  isView: Boolean = false;
  isRequest: Boolean = false;
  agentList: any[];

  maxDate: any = new Date();
  minDate: any;
  @ViewChild(TableComponent) dataTable: TableComponent;
  @ViewChild(AmendBlComponent) amendComponent: AmendBlComponent;
  @ViewChild('autocompletesa') autocompletesa: AutoCompleteComponent;
  @ViewChild('autocompleteimp') autocompleteimp: AutoCompleteComponent;
  @Input() actionable = true;
  saValidation: Validator[];

  currentAdIndex = -1;
  items: any[] = [];
  searchParam: any[] = [];
  statusItems: any[] = []
  isShowMsg: Boolean;


  shippingAgentCode = '';

  tableDfn: TableDefn[] = [
    { displayName: 'BOL No', mappingName: 'bolNumber', type: TableType.number, sort: true },
    { displayName: 'Bill of Lading Type', mappingName: 'bolType', type: TableType.string, sort: false },
    { displayName: 'Shipping Agent Name', mappingName: 'shippingAgentName', type: TableType.string, sort: false },
    // { displayName: 'Invoice Number', mappingName: 'invoiceNumber', type: TableType.string, sort: false },
    //  { displayName: 'Invoice Value', mappingName: 'invoiceValue', type: TableType.string, sort: false },
    // { displayName: 'Invoice Date', mappingName: 'invoiceDate', type: TableType.date, sort: false },
    { displayName: 'Vessel Name', mappingName: 'vesselName', type: TableType.string, sort: false },
    //{ displayName: 'Vessel ETA/ATA', mappingName: 'vesselEtaAta', type: TableType.string, sort: true },
    { displayName: 'Voyage Number', mappingName: 'voyageNumber', type: TableType.string, sort: false },
    // { displayName: 'Importer Code', mappingName: 'importerCode', type: TableType.string, sort: true },   
    { displayName: 'Status', mappingName: 'status', type: TableType.string, sort: false }
  ];

  reloadDataTable(event) {
    this.notfnService.clearMessage();
    var fromDate = "", toDate = "";
    if (this.navigationHandlerService.url)
      this.dataTable.url = this.navigationHandlerService.url;
    console.log(this.myForm.get('shippingAgentName').value)
    if (this.myForm.valid) {
      var msg = "Provide  ";
      this.dataTable.request = false;
      if (this.userType === 'I') {
        this.dataTable.view = true;
        this.dataTable.request = true;
        if (this.myForm.get('shippingAgentName').value != null) {
          console.log(this.searchParam);
          if (this.searchParam.length > 0) {
            var firstEntry = true;
            for (let entry of this.searchParam) {
              console.log("for each", entry.value)
              //   var strSplit = entry.value.split('-')[0];
              if (entry.value === "NORULE") {
                this.searchFlag = true;
              } else if (entry.value === "NO_SHIPPING_AGENT") {
                this.searchFlag = false;

                msg = entry.label;
              } else {
                if (entry.value.indexOf('_') > -1) {
                  console.log(entry.value.split('_')[0]);
                  if (this.myForm.get(entry.value.split('_')[0]).value != "") {
                    if (this.myForm.get(entry.value.split('_')[1]).value != "")
                      this.searchFlag = true;
                    else {
                      this.searchFlag = false;
                      msg = msg + " or " + entry.label;
                      break;

                    }
                  }

                  else {
                    this.searchFlag = false;
                    var s = entry.label;
                    var regex = "_";
                    s.replace(regex, ' and ');

                    msg = msg + s.replace(regex, ' and ');
                    break;

                  }
                } else {
                  if (!!this.myForm.get(entry.value).value) {
                    // console.log("NOT NULL-------------------"+this.myForm.get(entry.value).value);
                    this.searchFlag = true;
                    break;
                  } else {
                    // console.log("NULL-------------------"+entry.label);
                    this.searchFlag = false;
                    if (entry.label === 'BOLNO') {
                      entry.label = 'BOL NO'
                    }
                    if (entry.label === 'INVOICENO') {
                      entry.label = 'INVOICE NO'
                    }
                    if (firstEntry) {
                      msg = msg + entry.label;
                      firstEntry = false;
                    } else {
                      msg = msg + " or " + entry.label;
                    }
                    continue;

                  }
                }
              }
            }

          }
        }

      } else {
        this.dataTable.view = false;
        this.dataTable.request = false;
        this.dataTable.edit = true;
        this.searchFlag = true;

      }

      if (this.searchFlag) {
        this.dataTable.tableDS = new Array;
        //Set empty string to search 
        if (this.myForm.get('bol').value == null)
          this.myForm.get('bol').setValue('');
        else
          this.myForm.get('bol').setValue(this.myForm.get('bol').value.toUpperCase());
        if (this.myForm.get('invoiceNumber').value == null)
          this.myForm.get('invoiceNumber').setValue('');
        if (this.myForm.get('status').value == null)
          this.myForm.get('status').setValue('');
        if (this.myForm.get('importerName').value == null)
          this.myForm.get('importerName').setValue('');
        var shippingName = this.myForm.get('shippingAgentName').value ? encodeURIComponent(this.myForm.get('shippingAgentName').value.value) : this.myForm.get('shippingAgentName').value;
        var importerName = this.myForm.get('importerName').value ? encodeURIComponent(this.myForm.get('importerName').value.value) : this.myForm.get('importerName').value;

        if (this.myForm.get('frmDate') != null) {
          if (this.myForm.get('frmDate').value != null && this.myForm.get('frmDate').value != "")
            fromDate = formatDate(this.myForm.get('frmDate').value, 'dd-MM-yyyy', 'en-US', '+0530');
        }
        if (this.myForm.get('toDate') != null) {
          if (this.myForm.get('toDate').value != null && this.myForm.get('toDate').value != "")
            toDate = formatDate(this.myForm.get('toDate').value, 'dd-MM-yyyy', 'en-US', '+0530');
        }
        if (this.myForm.get('frmDate').value != null && this.myForm.get('toDate').value != null) {
          if (this.myForm.get('frmDate').value._d > this.myForm.get('toDate').value._d) {
            this._dialogService.alert("From Date should be less than To Date");
          }

        }

        this.dataTable.refreshTable('bolNo=' + encodeURIComponent(this.myForm.get('bol').value.toUpperCase())
          + '&invoiceNo=' + encodeURIComponent(this.myForm.get('invoiceNumber').value.toUpperCase())
          + '&shippingLine=' + shippingName
          + '&status=' + this.myForm.get('status').value
          + '&impCode=' + importerName
          + '&frmDate=' + fromDate
          + '&toDate=' + toDate);

      } else {
        this._dialogService.alert(msg + ".");
      }

    } else {
      const notfnMessage: NotificationMessage = {
        text: 'Shipping Agent Mandatory.',
        type: MessageStatus.error
      };
      this.notfnService.updateMessage(notfnMessage);
      super.scrollToTop();
    }
  }
  countEvent(event) {
    console.log(event);
    console.log(this.agentType);
    if (this.userType === 'I') {
      if (event.totalCount == 0 || event.totalCount === null) {
        console.log(this.dataTable.loadedData.length);
        // if(this.dataTable.loadedData.length>0)
        if (this.myForm.get('bol').value.length > 0) {
          var confirmMsg
          if (this.isShowMsg)
            confirmMsg = 'Manifest will be available 3 days prior vessel arrival. Do you Still  want to request BOL?';
          else
            confirmMsg = 'Do you Still  want to request BOL?';

          this._dialogService.confirm(confirmMsg)
            .subscribe(retval => {
              console.log(' Dialog Data ', retval);
              if (retval != 1) {

                return;
              }
              else {
                const data = {
                  bolNo: this.myForm.get('bol').value.toUpperCase(),
                };
                const dialogOptions: DialogOptions = { disableClose: false };

                this._dialogService.openDialog(RequestBlComponent, data, dialogOptions).subscribe((s) => {
                  console.log(s);
                  if (s.shippingAgentName.value != undefined) {
                    s.shippingAgentName = s.shippingAgentName.value;
                    console.log(s.shippingAgentName.value);
                  }
                  this.url = '/do/app/api/secure/requestBLInvoice?id=' + s.bol + '&shippingagentCode=' + s.shippingAgentName + '&type=BOL_REQUEST';
                  this._httpRequestService.postData(this.url, null, true).subscribe((result) => {
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
                  }, (error) => {
                    this.notfnService.clearMessage();
                    // this._dialogService.alert('Unable to Request');
                    const notfnMessage: NotificationMessage = {
                      text: error.error.message,
                      type: MessageStatus.error
                    };
                    this.notfnService.updateMessage(notfnMessage);
                    super.scrollToTop();


                  });
                });
              }
            });

        }
      }
      else if (this.dataTable.loadedData[0].status != 'NEW' && this.dataTable.loadedData[0].status != 'REJECTED' && this.dataTable.loadedData[0].status != 'PARTIAL_PAYMENT') {
        confirmMsg = 'DO has already been Initiated. For  status enquiry, please check Track Delivery Order.';
        this._dialogService.alert(confirmMsg);
        this.dataTable.tableDS = [];
      }
    }
    dtframeResizer.postHeightChanges();
  }

  editBol(event) {
    const actionVal = event.action;
    const val = event.val;

    const bolNo = event.val.bolNumber;
    const bolNumberEncr = event.val.bolNumberEncr;
    const doAuthRequestId = event.val.doAuthRequestId;
    const doAuthRequestIdEncr = event.val.doAuthRequestIdEncr;

    const navextras: NavigationExtras = {
      queryParams: { 'invoiceNo': event.val.invoiceNumber, "shippingLine": event.val.shippingLine, "amend": this.route.snapshot.queryParams.amend, "parent": '/search', "bolEncr": bolNumberEncr, "doAuthRequestId": doAuthRequestIdEncr }
    };
    if (this.userType === 'I') {
      if (this.dataTable.getTableActionView() === actionVal) {
        console.log(actionVal + '---------' + this.dataTable.getTableActionView());
        if (val.status === 'NEW' || val.status === 'CANCELLED') {
          this._httpRequestService.postData('/do/app/api/secure/getInvoicesUploaded?bol=' + event.val.bolNumberEncr, null, true).subscribe((resInvoice) => {
            console.log(resInvoice);
            if (resInvoice != null) {
              // console.log(resInvoice);
              if (resInvoice.length > 0) {
                if (resInvoice[0].label === 'NO_INVOICE') {
                  this._dialogService.alert("No Invoice Found.");
                } else {
                  this._httpRequestService.getData('/do/app/api/secure/getShippingAgentDetails?q=' + event.val.shippingAgentCode, true).subscribe((res) => {
                    console.log(res);
                    if (res.length > 0) {
                      this.shippingAgentCode = res[0].label;
                      //  this.myForm.get('shippingAgentName').setValue(this.shippingAgent);
                    }
                    const data = {
                      invoiceNo: resInvoice,
                      shippingAgentCode: this.shippingAgentCode,
                      purpose: 'INVOICE_NOT_UPLOADED'
                    };
                    const dialogOptions: DialogOptions = { disableClose: false };

                    this._dialogService.openDialog(RequestInvoiceComponent, data, dialogOptions).subscribe((s) => {
                      console.log(s);
                      if (s.shippingAgentName.value != undefined)
                        s.shippingAgentName = s.shippingAgentName.value;
                      if (s.selectedInvoice != undefined) {
                        if (s.selectedInvoice.length == 0)
                          if (s.invNo != undefined)
                            s.selectedInvoice = s.invNo;
                      }
                      this.url = '/do/app/api/secure/requestBLInvoice?id=' + s.selectedInvoice + '&shippingagentCode=' + s.shippingAgentName + '&type=INVOICE_REQUEST' + '&reqEmail=' + s.reqEmail;
                      this._httpRequestService.postData(this.url, null, true).subscribe((result) => {
                        let confirmStatus = true;
                        if (result.status === 'success') {
                          confirmStatus = true;
                        } else {
                          confirmStatus = false;;
                        }
                        const navextras: NavigationExtras = {
                          queryParams: { 'data': result.message, 'success': confirmStatus }
                        };
                        this.notfnService.clearMessage();
                        this._router.navigate(['/confirm'], navextras);
                      }, (error) => {
                        this.notfnService.clearMessage();
                        this.notfnService.clearMessage();
                        const notfnMessage: NotificationMessage = {
                          text: error.error.message,
                          type: MessageStatus.error
                        };
                        this.notfnService.updateMessage(notfnMessage);
                        super.scrollToTop();

                      });
                    });
                  });

                }
              } else {
                this._dialogService.alert("Invoice Uploaded.");
              }
            }
          });
        } else {
          this._dialogService.alert("REQUEST INVOICE DENIED!! BOL STATUS IS " + val.status);
        }
      }

    }
    this.navigationValuePass();
    if ((this.dataTable.getTableActionEdit() === actionVal) || (this.dataTable.getTableActionRequest() === actionVal)) {
      if (val.status === 'NEW' || val.status === 'CANCELLED' || val.status === 'RETURNED' || val.status === 'PARTIAL_PAYMENT' || val.status == 'DO_INITIATED' || val.status == 'REJECTED') {
        if (this.userType === 'I') {
          if ((this.dataTable.getTableActionEdit() === actionVal) || (this.dataTable.getTableActionRequest() === actionVal))
            //this._router.navigate(['/authDO'], navextras);
            this._router.navigate(['/requestDO'], navextras);

        }
        else if (this.userType === 'A' && val.status === 'NEW') {
          if (this.dataTable.getTableActionEdit() === actionVal)
            this._router.navigate(['/amendBL'], navextras);

        }
        else if (this.userType === 'A') {
          if (this.dataTable.getTableActionEdit() === actionVal)
            this._router.navigate(['/viewBL'], navextras);

        }
      }
      else if (val.status === 'INITIATED' || val.status === 'COMPLETED' || val.status === 'REJECTED' || val.status === 'PARTIAL_PAYMENT') {
        if (this.userType === 'I') {
          if (this.dataTable.getTableActionView() === actionVal)
            this._router.navigate(['/approveDO'], navextras);
          if ((this.dataTable.getTableActionEdit() === actionVal) || (this.dataTable.getTableActionRequest() === actionVal))
            this._router.navigate(['/viewDO'], navextras);
        }
        else if (this.userType === 'A') {
          if (this.dataTable.getTableActionView() === actionVal)
            this._router.navigate(['/viewBL'], navextras);
          if (this.dataTable.getTableActionEdit() === actionVal)
            this._router.navigate(['/viewBL'], navextras);
        }
      }
      else if (val.status === 'RETURNED') {
        if (this.userType === 'I') {
          if ((this.dataTable.getTableActionEdit() === actionVal) || (this.dataTable.getTableActionRequest() === actionVal))
            //this._router.navigate(['/authDO'], navextras);
            this._router.navigate(['/requestDO'], navextras);
        }
        else if (this.userType === 'A') {
          if (this.dataTable.getTableActionEdit() === actionVal)
            this._router.navigate(['/viewBL'], navextras);

        }
      }
      else if (val.status === 'DELETED') {

        if (this.dataTable.getTableActionEdit() === actionVal)
          this._router.navigate(['/viewBL'], navextras);
      }


    }
  }
  reset($event) {
    this.myForm.reset();
    this.dataTable.tableDS = new Array;
    this.dataTable.resetTable();
    this.dataTable.loading = false;
    this.isShowMsg = false;
    if (this.userType === 'A')
      this.myForm.get('shippingAgentName').setValue(this.shippingAgentList);
    else {
      this.myForm.get('shippingAgentName').setValue('');
      this.autocompletesa.refreshItems(this.shippingAgentList);
    }
    this.autocompleteimp.refreshItems(this.importerNameList);

  }
  close($event) {
    this._dialogService.confirm('All your changes will be lost. Do you want to proceed?')
      .subscribe(retval => {
        console.log(' Dialog Data ', retval);
        if (retval != 1) {
          return;
        }
        else {
          this._router.navigate(['/homeDO']);
        }
      });
  }

  get tabForms() {

    return this.myForm.get('tableData') as FormArray;
  }


  constructor(private route: ActivatedRoute, private _securityInfo: SecurityInfoService, private cdr: ChangeDetectorRef, private router: Router, private navigationHandlerService: NavigationHandlerService) {
    super();
    this._pageHeaderService.updateHeader('Search Bill of Lading');
    console.log(this._securityInfo);
  }


  ngOnInit() {
    // console.log(this.route.snapshot.queryParams.bolNo);
    this.agentType = this._securityInfo.getAgentType();
    this.agentCode = this._securityInfo.getAgentCode();
    this.userType = this._securityInfo.getUserType();
    if (this.userType == 'A') {
      this.isEdit = true;
      this.statusItems = [
        { value: 'Choose', label: 'Choose' },
        { value: 'NEW', label: 'NEW' },
        { value: 'REJECTED', label: 'REJECTED' },
        { value: 'COMPLETED', label: 'COMPLETED' },
        { value: 'RETURNED', label: 'RETURNED' },
        { value: 'INITIATED', label: 'INITIATED' },
        { value: 'CANCELLED', label: 'CANCELLED' },
        { value: 'PARTIAL_PAYMENT', label: 'PARTIAL PAYMENT' },
        { value: 'DELETED', label: 'DELETED' }

      ];
    }
    else {
      this.isView = true;
      this.isRequest = true;
      this.statusItems = [
        { value: 'Choose', label: 'Choose' },
        { value: 'NEW', label: 'NEW' },
        { value: 'REJECTED', label: 'REJECTED' },
        { value: 'PARTIAL_PAYMENT', label: 'PARTIAL PAYMENT' }
      ];
    }
    this.myForm = this.formBuilder.group({
      shippingAgentName: ['', [Validators.required]],
      bol: [''],
      invoiceNumber: [''],
      status: [''],
      importerName: [''],
      toDate: [''],
      frmDate: ['']

    });
    this.saValidation = [{ name: 'required', validator: Validators.required, message: 'Shipping Agent is Required' }];


    console.log(this._securityInfo);
    if (this.userType === 'A') {
      this._httpRequestService.getData('/do/app/api/secure/getAgentDetailByUserDTO', true).subscribe((res) => {
        this.shippingAgentList = res[0].label;
        this.shippingAgent = res[0].label;
        this.myForm.get('shippingAgentName').setValue(this.shippingAgent);
      });
    } else
      this.shippingAgent = "";
    if (this.userType === 'I') {
      this._httpRequestService.getData('/do/app/api/secure/getAgentDetailByUserDTO', true).subscribe((res) => {
        console.log(res[0].label);
        this.shippingAgentList = res[0].label;
        this.importerName = res[0].label;
        let strbl = this.importerName;
        var strSplit = strbl.split('-')[0];
        const item = new Item(strSplit, strbl);
        this.items.push(item);
        console.log(this.items);
        this.autocompleteimp.refreshItems(this.items, item);
      });
    } else
      this.importerName = "";
    if (this.route.snapshot.queryParams.amend)
      this.flag = this.route.snapshot.queryParams.amend;
    else
      this.flag = false;

    this.setBackNavigationValues();
  }
  onChangeSA($event) {
    //console.log($event);
    if ($event.value != undefined) {
      console.log($event.value.label);
      this._httpRequestService.getData('/do/app/api/secure/getShippingAgentSearchAttribute?q=' + $event.value.value, false).subscribe((res) => {
        console.log("0000000 ", res);
        this.searchParam = res;
        console.log(this.searchParam);
      });
    }

    this._httpRequestService.getData('/do/app/api/secure/getShippingAgentDetails?q=' + $event.value.value, true).subscribe((res) => {
      if (res.length > 0) {
        this.agentList = res[0].shippingAgentList;
        this.isShowMsg = this.agentList.includes($event.value.value);
      }
    })
  }

  navigationValuePass() {
    this.navigationHandlerService.storage = this.dataTable.tableDS;
    this.navigationHandlerService.searchParam = this.myForm.value;
    this.navigationHandlerService.tableData = this.dataTable;
    this.navigationHandlerService.pageName = this.router.url;
    this.navigationHandlerService.url = this.dataTable.url;
    this.navigationHandlerService.pageSize = this.dataTable.pageSize;
    this.navigationHandlerService.isShowButton = this.isShowMsg;
  }

  setBackNavigationValues() {
    if (this.navigationHandlerService.storage)
      this.dataTable.tableDS = this.navigationHandlerService.storage;
    if (this.navigationHandlerService.searchParam)
      this.myForm.patchValue(this.navigationHandlerService.searchParam);
    if (this.navigationHandlerService.tableData) {
      this.dataTable.pageSize = this.navigationHandlerService.pageSize;
      this.reloadDataTable(event);

    }
    if (this.navigationHandlerService.url)
      this.dataTable.url = this.navigationHandlerService.url;
    this.isShowMsg = this.navigationHandlerService.isShowButton;
  }
  onChange() {
    this.minDate = this.myForm.get("fromDate").value.toDate();
  }

}
