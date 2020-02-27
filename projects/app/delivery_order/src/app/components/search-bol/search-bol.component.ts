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
  selector: 'app-search-bol',
  templateUrl: './search-bol.component.html',
  styleUrls: ['./search-bol.component.css']
})
export class SearchBolComponent extends BaseComponent implements OnInit {
  myForm: FormGroup;
  flag: boolean;
  agentType: String;
  agentCode: String;
  userType: string;
  shippingAgent: String;
  shippingAgentList: any;
  importerNameList: any;
  importerName: string;
  url: string;
  searchFlag: boolean = true;
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
    if (this.navigationHandlerService.url)
      this.dataTable.url = this.navigationHandlerService.url;
    console.log(this.myForm.get('shippingAgentName').value)
    if (this.myForm.get('shippingAgentName').valid || this.myForm.get('importerName').valid) {
      var msg = "Provide  ";
      var fromDate = "", toDate = "";
      console.log(this.searchFlag);
      if (this.searchFlag) {
        console.log(this.myForm.get('shippingAgentName').value.value);
        this.dataTable.tableDS = new Array;
        //Set empty string to search 
        if (this.myForm.get('bol').value == null)
          this.myForm.get('bol').setValue('');
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
        this.dataTable.refreshTable('bolNo=' + this.myForm.get('bol').value.toUpperCase() + '&invoiceNo=' + this.myForm.get('invoiceNumber').value.toUpperCase() + '&shippingLine='
          + shippingName + '&status='
          + this.myForm.get('status').value
          + '&impCode=' + importerName
          + '&frmDate=' + fromDate
          + '&toDate=' + toDate);


      } else {
        this._dialogService.alert(msg + ".");
      }

    } else {
      const notfnMessage: NotificationMessage = {
        text: 'Shipping Agent or Importer Name Mandatory.',
        type: MessageStatus.info
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
          this._dialogService.confirm('DO YOU WANT TO REQUEST BOL?')
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
                    this._dialogService.alert('Unable to Request');


                  });
                });
              }
            });

        }
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
    const navextras: NavigationExtras = {
      queryParams: { 'invoiceNo': event.val.invoiceNumber, "shippingLine": event.val.shippingLine, "amend": this.route.snapshot.queryParams.amend, "parent": '/searchBOLInternal', "bolEncr": bolNumberEncr, "doAuthRequestId": doAuthRequestId }
    };
    this.navigationValuePass();
    if (this.dataTable.getTableActionView() === actionVal) {
      this._router.navigate(['/viewBL'], navextras);
    }
  }
  reset($event) {
    this.myForm.reset();
    this.dataTable.tableDS = new Array;
    //this.dataTable.refreshTable();
    this.autocompleteimp.refreshItems(this.importerNameList);
    this.autocompletesa.refreshItems(this.shippingAgentList);
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


  constructor(private route: ActivatedRoute, private _securityInfo: SecurityInfoService, private cdr: ChangeDetectorRef, private navigationHandlerService: NavigationHandlerService, private router: Router) {
    super();
    this._pageHeaderService.updateHeader('Search Bill of Lading');
    console.log(this._securityInfo);
  }


  ngOnInit() {
    // console.log(this.route.snapshot.queryParams.bolNo);
    this.agentType = this._securityInfo.getAgentType();
    this.agentCode = this._securityInfo.getAgentCode();
    this.userType = this._securityInfo.getUserType();
    this.statusItems = [
      { value: 'Choose', label: 'Choose' },
      { value: 'NEW', label: 'NEW' },
      { value: 'REJECTED', label: 'REJECTED' },
      { value: 'COMPLETED', label: 'COMPLETED' },
      { value: 'RETURNED', label: 'RETURNED' },
      { value: 'INITIATED', label: 'INITIATED' },
      { value: 'CANCELLED', label: 'CANCELLED' },
      { value: 'PARTIAL_PAYMENT', label: 'PARTIAL PAYMENT' },

    ];

    this.myForm = this.formBuilder.group({
      shippingAgentName: ['', [Validators.required]],
      bol: [''],
      invoiceNumber: [''],
      status: [''],
      importerName: ['', [Validators.required]],
      toDate: [''],
      frmDate: ['']

    });
    this.saValidation = [{ name: 'required', validator: Validators.required, message: 'Shipping Agent is Required' }];
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
  }
  navigationValuePass() {
    this.navigationHandlerService.storage = this.dataTable.tableDS;
    this.navigationHandlerService.searchParam = this.myForm.value;
    this.navigationHandlerService.tableData = this.dataTable;
    this.navigationHandlerService.pageName = this.router.url;
    this.navigationHandlerService.url = this.dataTable.url;
    this.navigationHandlerService.pageSize = this.dataTable.pageSize;
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
  }
  onChange() {
    this.minDate = this.myForm.get("frmDate").value.toDate();
  }
}
