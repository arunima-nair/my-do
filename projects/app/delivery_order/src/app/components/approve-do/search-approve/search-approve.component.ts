import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FormGroup, FormArray, Validators } from '@angular/forms';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { TranslateService } from '@ngx-translate/core';
import { TableType, TableDefn } from 'src/app/lib/classes/TableDefn';
import { TableComponent } from 'src/app/lib/components/table/table.component';
import { Router } from '@angular/router';
import { ComponentFactoryResolver } from '@angular/core';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { formatDate } from '@angular/common';
import { Validator } from 'src/app/lib/classes/field.interface';
import { NavigationHandlerService } from 'src/app/lib/service/navigation-handler.service';

class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}


@Component({
  selector: 'app-search-approve',
  templateUrl: './search-approve.component.html',
  styleUrls: ['./search-approve.component.css']
})


export class SearchApproveComponent extends BaseComponent implements OnInit {

  myForm: FormGroup;
  agentType: String;
  userType: string;
  user: string
  emailValidation: Validator[];
  maxDate: any = new Date();
  isBackButtonClicked: Boolean = false;
  minDate: any;
  @ViewChild(TableComponent) dataTable: TableComponent;
  items: any[] = [];

  @Input() actionable = true;

  constructor(private _ts: TranslateService, private router: Router, private componentFactoryResolver: ComponentFactoryResolver, private _securityInfo: SecurityInfoService, private route: ActivatedRoute, private navigationHandlerService: NavigationHandlerService) {
    super();
    this._pageHeaderService.updateHeader('Search Approvals');

  }
  statusItems: any[] = [
    { value: 'PENDING_FOR_APPROVAL', label: 'PENDING_FOR_APPROVAL' },
    { value: 'REJECTED', label: 'REJECTED' },
    { value: 'COMPLETED', label: 'COMPLETED' }
  ];
  tableDfn: TableDefn[] = [

    { displayName: 'DO Reference Number', mappingName: 'doRefNo', type: TableType.string, sort: true },
    { displayName: 'BOL Number', mappingName: 'bolNumber', type: TableType.string, sort: false },
    { displayName: 'Request Party Name', mappingName: 'reqPartyName', type: TableType.string, sort: false },
    { displayName: 'BoL Party Name', mappingName: 'bolPartyName', type: TableType.string, sort: false },
    { displayName: 'Created Date', mappingName: 'formatedCreatedDate', type: TableType.string, sort: true },
    { displayName: 'Status', mappingName: 'status', type: TableType.string, sort: false },
    { displayName: 'Is Returned', mappingName: 'isReturned', type: TableType.string, sort: false }


  ];
  reloadDataTable(event) {
    this.dataTable.tableDS = null;
    if (this.navigationHandlerService.url)
      this.dataTable.url = this.navigationHandlerService.url;
    var fromDate = "", toDate = "";
    let reqPartyLabel = null;
    super.validateForm(this.myForm);
    if (this.myForm.valid) {
      if (this.myForm.get('frmDate').value != "")
        fromDate = formatDate(this.myForm.get('frmDate').value, 'dd-MM-yyyy', 'en-US', '+0530');
      if (this.myForm.get('toDate').value != "")
        toDate = formatDate(this.myForm.get('toDate').value, 'dd-MM-yyyy', 'en-US', '+0530');
      if (this.myForm.get('status').value === 'Choose' || this.myForm.get('status').value === '') {
        this.myForm.controls['status'].setValue('PENDING_FOR_APPROVAL');
      }
      if (this.myForm.get('reqParty').value == undefined) {
        this.myForm.get('reqParty').setValue(null);
        reqPartyLabel = null;
      } else if (this.myForm.get('reqParty').value == "") {
        reqPartyLabel = null;
      } else {
        reqPartyLabel = this.myForm.get('reqParty').value.label;
      }

      var bolNo = this.myForm.get('bol').value ? encodeURIComponent(this.myForm.get('bol').value.toUpperCase()) : this.myForm.get('bol').value;
      var doRefNo = this.myForm.get('doRefNo').value ? encodeURIComponent(this.myForm.get('doRefNo').value.toUpperCase()) : this.myForm.get('doRefNo').value;
      this.dataTable.refreshTable(
        'bolNo=' + bolNo
        + '&doRefNo=' + doRefNo
        // + '&status=PENDING_FOR_APPROVAL' 
        + '&status=' + this.myForm.get('status').value
        + '&frmDate=' + fromDate
        + '&toDate=' + toDate
        + '&reqPartyEmail=' + encodeURIComponent(this.myForm.get('reqPartyEmail').value)
        + '&reqParty=' + encodeURIComponent(reqPartyLabel)
        + '&doParty=' + encodeURIComponent(this.myForm.get('doParty').value)
      )
      if (this.userType == 'A') {
        this.dataTable.view = false;
      }
      this.dataTable.loading = false;
    }
  }
  reset($event) {
    this.myForm.reset();
    this.dataTable.resetTable();
    this.myForm.get('status').setValue(this.statusItems[0].value);
  }
  editDO(event) {
    const actionVal = event.action;
    const bolNo = event.val.bolNumber.toUpperCase();
    const doAuthRequestId = event.val.doAuthRequestId;
    const doAuthRequestIdEncr = event.val.doAuthRequestIdEncr;
    if (this.userType == 'I') {
      this.user = "0";
    } else if (this.userType == 'A') {
      this.user = "1";
    }
    const navextras: NavigationExtras = {
      queryParams: { 'bolNo': bolNo, 'actionVal': actionVal, 'user': this.user, "parent": '/searchAppr', "doAuthRequestId": doAuthRequestIdEncr }
    };
    this.navigationValuePass();
    if (this.dataTable.getTableActionEdit() === actionVal) {
      this._router.navigate(['/approveDO'], navextras);
    }
    else if (this.dataTable.getTableActionView() === actionVal) {
      this._router.navigate(['/approveDO'], navextras);
    }
  }
  get tabForms() {
    return this.myForm.get('tableData') as FormArray;
  }

  onChangeDoParty(event) {
    //ToDO
  }

  ngOnInit() {
    this.agentType = this._securityInfo.getAgentType();
    this.userType = this._securityInfo.getUserType();
    let agentCode = this._securityInfo.getAgentCode();
    var emailPattern = "[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}";
    this.myForm = this.formBuilder.group({
      doRefNo: [''],
      bol: [''],
      invoiceNumber: [''],
      reqParty: ['', []],
      reqPartyEmail: ['', [Validators.pattern(emailPattern), Validators.minLength(6)]],
      doParty: ['', []],
      frmDate: ['', []],
      toDate: ['', []],
      status: ['', []]
    });
    this.myForm.get('status').setValue(this.statusItems[0].value);
    this._httpRequestService.getData('/do/app/api/secure/getImporterAgentDetails?q=' + agentCode, true).subscribe((res) => {

      if (res != undefined) {
        if (res.length > 0) {
          let str = res[0].label;

          var strSplit = str.split('-')[1];
          const item = new Item(strSplit, str);
          this.items.push(item);

          let bolPartytxt = str;
          if (bolPartytxt != undefined) {
            let strSplit = bolPartytxt.split('-')[0];
            const item = new Item(strSplit, bolPartytxt);
            this.items.push(item);
          }

        }
      }
    });
    this.setBackNavigationValues();
    this.dataTable.url = "/do/app/api/secure/searchDOApprov";
    this.dataTable.pageSize = 10;
    if (!this.navigationHandlerService.isBackButtonClicked) {
      this.dataTable.refreshTable(
        'bolNo=' + ""
        + '&doRefNo=' + ""
        + '&status=PENDING_FOR_APPROVAL'
        + '&frmDate=' + ""
        + '&toDate=' + ""
        + '&reqPartyEmail=' + encodeURIComponent(this.myForm.get('reqPartyEmail').value)
        + '&reqParty=' + null
        + '&doParty=' + encodeURIComponent(this.myForm.get('doParty').value)
      )
    }
  }

  navigationValuePass() {
    this.navigationHandlerService.storage = this.dataTable.tableDS;
    this.navigationHandlerService.searchParam = this.myForm.value;
    this.navigationHandlerService.tableData = this.dataTable;
    this.navigationHandlerService.pageName = this.router.url;
    this.navigationHandlerService.url = this.dataTable.url;
    this.navigationHandlerService.pageSize = this.dataTable.pageSize;
    this.navigationHandlerService.isBackButtonClicked = true;
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
