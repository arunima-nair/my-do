import { Component, OnInit, Input, ViewChild, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormArray } from '@angular/forms';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { TranslateService } from '@ngx-translate/core';
import { TableType, TableDefn } from 'src/app/lib/classes/TableDefn';
import { TableComponent } from 'src/app/lib/components/table/table.component';
import { Router } from '@angular/router';
import { ComponentFactoryResolver } from '@angular/core';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { NavigationHandlerService } from 'src/app/lib/service/navigation-handler.service';
import { AuthDetailsComponent } from '../auth-details/auth-details.component';
import { DOApproverStatusMapping, DOInitiatorStatusMapping, DOStatus } from 'src/app/common/enum/status';
import { LangTranslateService } from 'src/app/lib/service/lang-translate.service';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-do-search',
  templateUrl: './do-search.component.html',
  styleUrls: ['./do-search.component.css']
})
export class DoSearchComponent extends BaseComponent implements OnInit {
  myForm: FormGroup;
  agentType: String;
  userType: any;
  user: string
  maxDate: any = new Date();
  minDate: any;
  @ViewChild(TableComponent) dataTable: TableComponent;
  @ViewChild(AuthDetailsComponent) authDetailsComponent: AuthDetailsComponent;

  @Input() actionable = true;

  constructor(private _ts: LangTranslateService, private router: Router, private componentFactoryResolver: ComponentFactoryResolver, private navigationHandlerService: NavigationHandlerService, private _securityInfo: SecurityInfoService, private route: ActivatedRoute, private cdr: ChangeDetectorRef) {
    super();
    this._translatorService = _ts;
    this._pageHeaderService.updateHeader('Search Delivery Order');
  }
  statusItems: any[] = new Array;
  tableDfn: TableDefn[] = [

    // { displayName: 'BoL Party Email', mappingName: 'bolEmail', type: TableType.string, sort: true },
    { displayName: 'DO Reference Number', mappingName: 'doRefNo', type: TableType.string, sort: true },
    { displayName: 'BOL Number', mappingName: 'bolNumber', type: TableType.string, sort: false },
    // { displayName: 'BoL Contact Person', mappingName: 'bolContactPerson', type: TableType.string, sort: true },
    // { displayName: 'BoL Contact Number', mappingName: 'bolContactNumber', type: TableType.string, sort: true },

    { displayName: 'Request Party Name', mappingName: 'reqPartyName', type: TableType.string, sort: false },
    { displayName: 'BoL Party Name', mappingName: 'bolPartyName', type: TableType.string, sort: false },
    // { displayName: 'Request Party Email', mappingName: 'reqEmail', type: TableType.string, sort: true },
    //{ displayName: 'Request Contact Person', mappingName: 'reqContactPerson', type: TableType.string, sort: true },
    // { displayName: 'Request Contact Number', mappingName: 'reqContactNumber', type: TableType.string, sort: true },


    { displayName: 'Status', mappingName: 'status', type: TableType.string, sort: false },
    { displayName: 'Requested Date', mappingName: 'convertedDate', type: TableType.string, sort: true },
    { displayName: 'Action', mappingName: 'action', type: TableType.button, sort: false }

  ];
  reloadDataTable(event) {
    this.dataTable.tableDS = null;
    if (this.navigationHandlerService.url)
      this.dataTable.url = this.navigationHandlerService.url;
    var fromDate = "", toDate = "";
    if (this.myForm.get('frmDate') != null) {
      if (this.myForm.get('frmDate').value != null && this.myForm.get('frmDate').value != "")
        fromDate = formatDate(this.myForm.get('frmDate').value, 'dd-MM-yyyy', 'en-US', '+0530');
    }
    if (this.myForm.get('toDate') != null) {
      if (this.myForm.get('toDate').value != null && this.myForm.get('toDate').value != "")
        toDate = formatDate(this.myForm.get('toDate').value, 'dd-MM-yyyy', 'en-US', '+0530');
    }
    var bolNumber = this.myForm.get('bol').value ? encodeURIComponent(this.myForm.get('bol').value.toUpperCase()) : "";
    var doreferenceNo = this.myForm.get('doRefNo').value ? encodeURIComponent(this.myForm.get('doRefNo').value.toUpperCase()) : "";

    this.dataTable.refreshTable('bolNo=' + bolNumber
      + '&doRefNo=' + doreferenceNo
      + '&frmDate=' + fromDate
      + '&toDate=' + toDate
      + '&status=' + this.myForm.get('status').value);

    this.dataTable.view = false;

    if (this.userType == 'A') {
      this.dataTable.view = false;
    }
    this.dataTable.loading = false;
  }

  reset($event) {
    this.myForm.reset();
    this.dataTable.loading = false;
    this.dataTable.resetTable();

  }
  editDO(event) {
    console.log(event);
    const actionVal = event.action;
    const bolNo = event.val.bolNumber;
    const encBolNo = event.val.bolNumberEncr;
    const status = event.val.status;
    const bolStatus = event.val.bolStatus;
    const doAuthRequestId = event.val.doAuthRequestId;
    const doAuthRequestIdEncr = event.val.doAuthRequestIdEncr;

    if (this.userType == 'I') {
      this.user = "0";
      //  this.dataTable.view = true;
    } else if (this.userType == 'A') {
      this.user = "1";
      // this.dataTable.view = false;
    }
    const navextras: NavigationExtras = {
      queryParams: { 'bolNo': encBolNo, 'actionVal': actionVal, 'user': this.user, "parent": '/searchdo', "bolEncr": event.val.bolNumberEncr, "doAuthRequestId": doAuthRequestIdEncr, "doAuthRequestIdEncr": doAuthRequestIdEncr }
    };
    if (this.userType == 'A') {
      if (status === 'PENDING_FOR_APPROVAL') {
        this.navigationValuePass();
        this._router.navigate(['/approveDO'], navextras);
      } else {
        this.navigationValuePass();
        this._router.navigate(['/trackDO'], navextras);
      }

    }
    else {

      if (status === 'PAYMENT_PENDING_WITH_IMPORTER' || status === 'PAYMENT_FAILED') {
        this.navigationValuePass();
        this._router.navigate(['/paymentDO'], navextras);
      } else {
        this.navigationValuePass();
        this._router.navigate(['/trackDO'], navextras);
      }
    }

  }
  get tabForms() {
    return this.myForm.get('tableData') as FormArray;
  }
  ngOnInit() {
    this.agentType = this._securityInfo.getAgentType();
    this.userType = this._securityInfo.getUserType();
    if (this.userType == 'A') {
      this.statusItems = DOApproverStatusMapping;
    } else {
      this.statusItems = DOInitiatorStatusMapping;
    }

    this.myForm = this.formBuilder.group({
      doRefNo: [''],
      bol: [''],
      invoiceNumber: [''],
      status: [''],
      toDate: [''],
      frmDate: ['']
    });
    this.setBackNavigationValues();
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
