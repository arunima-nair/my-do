import { Component, OnInit, ViewChild, Input, ComponentFactoryResolver } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { FormGroup, FormArray, Validators } from '@angular/forms';
import { TableComponent } from 'src/app/lib/components/table/table.component';
import { AuthDetailsComponent } from '../auth-details/auth-details.component';
import { LangTranslateService } from 'src/app/lib/service/lang-translate.service';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';
import { NavigationHandlerService } from 'src/app/lib/service/navigation-handler.service';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { TableDefn, TableType } from 'src/app/lib/classes/TableDefn';
import { DOApproverStatusMapping, DOInitiatorStatusMapping } from 'src/app/common/enum/status';
import { AutoCompleteComponent } from 'src/app/lib/components/auto-complete/auto-complete.component';
class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}
@Component({
  selector: 'app-do-search-internal',
  templateUrl: './do-search-internal.component.html',
  styleUrls: ['./do-search-internal.component.css']
})
export class DoSearchInternalComponent extends BaseComponent implements OnInit {

  myForm: FormGroup;
  agentType: String;
  userType: any;
  user: string
  shippingAgent: any;
  importerName: any;
  shippingAgentList: any;
  importerNameList: any;

  @ViewChild(TableComponent) dataTable: TableComponent;
  @ViewChild(AuthDetailsComponent) authDetailsComponent: AuthDetailsComponent;
  @ViewChild('autocompletesa') autocompletesa: AutoCompleteComponent;
  @ViewChild('autocompleteimp') autocompleteimp: AutoCompleteComponent;

  @Input() actionable = true;
  items: any[] = [];
  constructor(private _ts: LangTranslateService, private router: Router, private componentFactoryResolver: ComponentFactoryResolver, private navigationHandlerService: NavigationHandlerService, private _securityInfo: SecurityInfoService, private route: ActivatedRoute) {
    super();
    this._translatorService = _ts;
    this._pageHeaderService.updateHeader('Search Delivery Order');
  }
  statusItems: any[] = new Array;
  tableDfn: TableDefn[] = [
    { displayName: 'DO Reference Number', mappingName: 'doRefNo', type: TableType.string, sort: true },
    { displayName: 'BOL Number', mappingName: 'bolNumber', type: TableType.string, sort: false },
    { displayName: 'Request Party Name', mappingName: 'reqPartyName', type: TableType.string, sort: false },
    { displayName: 'BoL Party Name', mappingName: 'bolPartyName', type: TableType.string, sort: false },
    { displayName: 'Status', mappingName: 'status', type: TableType.string, sort: true },
    { displayName: 'Action', mappingName: 'action', type: TableType.button, sort: false }
  ];

  reloadDataTable(event) {
    this.dataTable.tableDS = null;
    var shippingName = this.myForm.get('shippingAgentName').value ? this.myForm.get('shippingAgentName').value.value : this.myForm.get('shippingAgentName').value;
    var importerName = this.myForm.get('importerName').value ? this.myForm.get('importerName').value.value : this.myForm.get('importerName').value;
    if (this.navigationHandlerService.url)
      this.dataTable.url = this.navigationHandlerService.url;
    this.dataTable.refreshTable('bolNo=' + this.myForm.get('bol').value + '&doRefNo=' + this.myForm.get('doRefNo').value + '&status=' + this.myForm.get('status').value + '&importerName=' + importerName + '&shippingAgent=' + shippingName);
    if (this.userType == 'A') {
      this.dataTable.view = false;
    }
    this.dataTable.loading = false;
  }
  reset($event) {
    this.myForm.reset();
    this.dataTable.loading = false;
    this.dataTable.tableDS = null;
    this.autocompleteimp.refreshItems(this.importerNameList);
    this.autocompletesa.refreshItems(this.shippingAgentList);
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

    /*  if (this.userType == 'I') {
       this.user = "0";
       //  this.dataTable.view = true;
     } else if (this.userType == 'A') {
       this.user = "1";
       // this.dataTable.view = false;
     } */
    const navextras: NavigationExtras = {
      queryParams: { 'bolNo': encBolNo, 'actionVal': actionVal, 'user': this.user, "parent": '/searchdoInternal', "bolEncr": event.val.bolNumberEncr, "doAuthRequestId": doAuthRequestIdEncr }
    };
    //if (this.dataTable.getTableActionEdit() === actionVal) {
    this.navigationValuePass();
    this._router.navigate(['/trackDOInternal'], navextras);
    // }

  }
  get tabForms() {
    return this.myForm.get('tableData') as FormArray;
  }
  ngOnInit() {
    this.statusItems = DOApproverStatusMapping;
    this.myForm = this.formBuilder.group({
      doRefNo: [''],
      bol: [''],
      invoiceNumber: [''],
      status: [''],
      shippingAgentName: [''],
      importerName: ['']
    });

    if (this.navigationHandlerService.storage)
      this.dataTable.tableDS = this.navigationHandlerService.storage;
    if (this.navigationHandlerService.searchParam)
      this.myForm.patchValue(this.navigationHandlerService.searchParam);
    if (this.navigationHandlerService.tableData) {
      this.dataTable.pageSize = this.navigationHandlerService.pageSize;
      this.reloadDataTable(event);
    }

    //this.dataTable = this.navigationHandlerService.tableData;


  }

  navigationValuePass() {
    this.navigationHandlerService.storage = this.dataTable.tableDS;
    this.navigationHandlerService.searchParam = this.myForm.value;
    this.navigationHandlerService.tableData = this.dataTable;
    this.navigationHandlerService.pageName = this.router.url;
    this.navigationHandlerService.url = this.dataTable.url;
    this.navigationHandlerService.pageSize = this.dataTable.pageSize;
  }
  onChangeSA($event) {
    if ($event.value != undefined) {
      console.log($event.value.value);
      this._httpRequestService.getData('/do/app/api/secure/getShippingAgentSearchAttribute?q=' + $event.value.value, false).subscribe((res) => {
        // this.searchParam = res;
      });
    }
  }

}
