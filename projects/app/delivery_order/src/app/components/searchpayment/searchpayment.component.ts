import { Component, OnInit, ViewChild, Input, ComponentFactoryResolver } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { FormGroup, FormArray } from '@angular/forms';
import { TableComponent } from 'src/app/lib/components/table/table.component';
import { TranslateService } from '@ngx-translate/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { TableDefn, TableType } from 'src/app/lib/classes/TableDefn';
import { formatDate } from '@angular/common';
import { NavigationHandlerService } from 'src/app/lib/service/navigation-handler.service';

@Component({
  selector: 'app-searchpayment',
  templateUrl: './searchpayment.component.html',
  styleUrls: ['./searchpayment.component.css']
})
export class SearchpaymentComponent extends BaseComponent implements OnInit {

  myForm: FormGroup;
  agentType: String;
  user: string
  @ViewChild(TableComponent) dataTable: TableComponent;

  @Input() actionable = true;

  constructor(private _ts: TranslateService, private router: Router, private componentFactoryResolver: ComponentFactoryResolver, private _securityInfo: SecurityInfoService, private route: ActivatedRoute, private navigationHandlerService: NavigationHandlerService) {
    super();
    this._pageHeaderService.updateHeader('Search Pending Payment');
  }
  tableDfn: TableDefn[] = [

    { displayName: 'DO Ref No', mappingName: 'doRefNo', type: TableType.string, sort: true },
    { displayName: 'BoL No', mappingName: 'bolNumber', type: TableType.string, sort: false },

    { displayName: 'Req Party Name', mappingName: 'reqPartyName', type: TableType.string, sort: false },
    { displayName: 'BoL Party Name', mappingName: 'bolPartyName', type: TableType.string, sort: false },


    { displayName: 'Status', mappingName: 'status', type: TableType.string, sort: false }

  ];
  reloadDataTable(event) {
    //console.log(this.myForm.get('doRefNo').value);
    //  console.log(this.myForm.get('bol').value);
    this.dataTable.tableDS = null;
    if (this.navigationHandlerService.url)
      this.dataTable.url = this.navigationHandlerService.url;
    this.dataTable.refreshTable('bolNo=' + encodeURIComponent(this.myForm.get('bol').value.toUpperCase()) + '&doRefNo=' + encodeURIComponent(this.myForm.get('doRefNo').value.toUpperCase()));
    if (this._securityInfo.getUserType() == 'A') {
      this.dataTable.view = false;
    }
  }

  editDO(event) {
    console.log(event);
    const actionVal = event.action;
    const bolNo = event.val.bolNumber;
    const status = event.val.status;
    const bolStatus = event.val.bolStatus;
    const encBolNo = event.val.bolNumberEncr;
    const doAuthRequestId = event.val.doAuthRequestId;
    const doAuthRequestIdEncr = event.val.doAuthRequestIdEncr;

    if (this._securityInfo.getUserType() == 'I') {
      this.user = "0";
    } else if (this._securityInfo.getUserType() == 'A') {
      this.user = "1";
    }
    const navextras: NavigationExtras = {
      queryParams: { 'bolNo': bolNo, 'bolEncr': encBolNo, 'actionVal': actionVal, 'user': this.user, "doAuthRequestId": doAuthRequestIdEncr, "parent": '/searchPayment' }
    };
    this.navigationValuePass();
    if (this.dataTable.getTableActionEdit() === actionVal) {
      if (this._securityInfo.getUserType() == 'A') {
        if (status === 'PENDING_FOR_APPROVAL') {
          this._router.navigate(['/approveDO'], navextras);
        } else {
          this._router.navigate(['/trackDO'], navextras);
        }

      }
      else {

        if (status === 'PAYMENT_PENDING_WITH_IMPORTER' || status === 'DO_INITIATED' || status === 'PARTIAL_PAYMENT') {
          this._router.navigate(['/paymentDO'], navextras);
        }
        else {
          this._router.navigate(['/trackDO'], navextras);
        }
      }
    }

  }
  reset($event) {
    this.myForm.reset();
    this.initFn();
    this.dataTable.resetTable();
  }

  get tabForms() {
    return this.myForm.get('tableData') as FormArray;
  }
  ngOnInit() {
    this.agentType = this._securityInfo.getAgentType();
    this.initFn();
    this.setBackNavigationValues();
  }

  initFn() {
    this.myForm = this.formBuilder.group({
      doRefNo: [''],
      bol: [''],

    });
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

}
