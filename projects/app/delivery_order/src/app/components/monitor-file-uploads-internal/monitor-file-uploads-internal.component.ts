import { Component, OnInit, ViewChild } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder, FormControlName } from '@angular/forms';
import { AutoCompleteComponent } from 'src/app/lib/components/auto-complete/auto-complete.component';
import { Validator } from 'src/app/lib/classes/field.interface';
import { TableType, TableDefn } from 'src/app/lib/classes/TableDefn';
import { TableComponent } from 'src/app/lib/components/table/table.component';
import { NotificationMessage, MessageStatus } from 'src/app/lib/classes/NotificationMessage';
import { formatDate } from '@angular/common';
import { isUndefined } from 'util';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MonitorModelInternalComponent } from './monitor-model-internal/monitor-model-internal.component';
import { DialogOptions } from 'src/app/lib/service/dialog.service';
import { FileLog } from 'src/app/model/file-log.model';

class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}

@Component({
  selector: 'app-monitor-file-uploads-internal',
  templateUrl: './monitor-file-uploads-internal.component.html',
  styleUrls: ['./monitor-file-uploads-internal.component.css']
})

export class MonitorFileUploadsInternalComponent extends BaseComponent implements OnInit {
  monitorForm: FormGroup;
  formBuilder: FormBuilder;
  saValidation: Validator[];
  agentType: any;
  agentCode: String;
  userType: any;
  shippingAgent: String;
  importerName: string;
  url: string;
  flag: boolean;
  items: any[] = [];
  searchParam: any[] = [];
  searchFlag: boolean = true;
  shippingAgentCode = '';
  fromDate: any;
  toDate: any;
  tableItems: any[];
  maxDate: any = new Date();
  minDate: any;
  statusItems: any[] = [
    { value: 'Choose', label: 'Choose' },
    { value: 'NEW', label: 'NEW' },
    { value: 'REJECTED', label: 'REJECTED' },
    { value: 'COMPLETED', label: 'COMPLETED' },
    { value: 'RETURNED', label: 'RETURNED' },
    { value: 'INITIATED', label: 'INITIATED' },
    { value: 'CANCELLED', label: 'CANCELLED' }
  ];


  tableDfn: TableDefn[] = [
    { displayName: 'Reference No', mappingName: 'referenceNumber', type: TableType.string, sort: true },
    { displayName: 'Upload Type', mappingName: 'uploadType', type: TableType.string, sort: false },
    { displayName: 'Created Date', mappingName: 'convertedDate', type: TableType.string, sort: true },
    { displayName: 'Total Records', mappingName: 'totalCount', type: TableType.number, sort: false },
    { displayName: 'Processed Records', mappingName: 'passedCount', type: TableType.number, sort: false },
    { displayName: 'Failed Records', mappingName: 'failedCount', type: TableType.number, sort: false }
  ];


  @ViewChild(TableComponent) dataTable: TableComponent;
  @ViewChild('autocompletesa') autocompletesa: AutoCompleteComponent;
  @ViewChild('autocompleteimp') autocompleteimp: AutoCompleteComponent;


  constructor(private route: ActivatedRoute, private _securityInfo: SecurityInfoService, public dialog: MatDialog) {
    super();
    this._pageHeaderService.updateHeader('Monitor File Uploads');
  }


  ngOnInit() {
    this.monitorForm = this.formBuilder.group({
      referenceNumber: [''],
      bolNo: [''],
      invoiceNumber: [''],
      uploadType: [''],
      shippingAgentName: ['', [Validators.required]],
      status: [''],
      fromDate: [''],
      toDate: ['']
    });

    this.saValidation = [{ name: 'required', validator: Validators.required, message: 'Shipping Agent is Required' }];
    this.agentType = this._securityInfo.getAgentType();
    this.agentCode = this._securityInfo.getAgentCode();
    this.userType = this._securityInfo.getUserType();

    this._httpRequestService.getData('/do/app/api/secure/getAgentDetailByUserDTO', false).subscribe((res) => {
      this.importerName = res[0].label;
      this.shippingAgentCode = res[0].value;
      let strbl = this.importerName;
      var strSplit = strbl.split('-')[0];
      const item = new Item(strSplit, strbl);
      this.items.push(item);
      this.autocompleteimp.refreshItems(this.items, item);
    });

    if (this.route.snapshot.queryParams.amend)
      this.flag = this.route.snapshot.queryParams.amend;
    else
      this.flag = false;
  }

  onChangeSA($event) {
    if ($event.value != undefined) {
      this._httpRequestService.getData('/do/app/api/secure/getShippingAgentSearchAttribute?q=' + $event.value.value, false).subscribe((res) => {
        this.searchParam = res;
      });
    }
  }

  // openDialog(): void {
  //   const dialogRef = this.dialog.open(ModalComponent, {
  //     width: '250px',
  //     data: {name: this.name, animal: this.animal}
  //   });

  //   dialogRef.afterClosed().subscribe(result => {
  //     this.animal = result;
  //   });
  // }

  reset($event) {
    this.monitorForm.reset();
    this.dataTable.tableDS = new Array;
    this.dataTable.loading = false;
    this.monitorForm.get('shippingAgentName').setValue(this.shippingAgent);
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



  reloadDataTable(event) {
    var status = "";
    var bolNo = "";
    var invoiceNumber = "";
    var referenceNumber = "";
    var isProccessAPI = false;

    super.validateForm(this.monitorForm);
    if (this.monitorForm.valid) {


      this.shippingAgentCode = this.monitorForm.get('shippingAgentName').value.value;
      if (this.monitorForm.get('fromDate').value != "" && this.monitorForm.get('fromDate').value != undefined)
        this.fromDate = formatDate(this.monitorForm.get('fromDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530');
      else
        this.fromDate = "";
      if (this.monitorForm.get('toDate').value != "" && this.monitorForm.get('toDate').value != undefined)
        this.toDate = formatDate(this.monitorForm.get('toDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530');
      else
        this.toDate = "";

      if (this.monitorForm.get('status').value == "" || this.monitorForm.get('status').value == undefined)
        status = "";
      else
        status = this.monitorForm.get('status').value;
      if (this.monitorForm.get('bolNo').value == "" || this.monitorForm.get('bolNo').value == undefined)
        bolNo = "";
      else
        bolNo = this.monitorForm.get('bolNo').value.toUpperCase();
      if (this.monitorForm.get('invoiceNumber').value == "" || this.monitorForm.get('invoiceNumber').value == undefined)
        invoiceNumber = "";
      else
        invoiceNumber = this.monitorForm.get('invoiceNumber').value.toUpperCase();
      if (this.monitorForm.get('referenceNumber').value == "" || this.monitorForm.get('referenceNumber').value == undefined)
        referenceNumber = "";
      else
        referenceNumber = this.monitorForm.get('referenceNumber').value.toUpperCase();

      if (this.fromDate && this.toDate) {
        if (this.monitorForm.get('fromDate').value._d > this.monitorForm.get('toDate').value._d) {
          isProccessAPI = false;
          this._dialogService.alert("From Date should be less than To Date");
        }
        else {
          isProccessAPI = true;
        }
      }
      else if (this.fromDate || this.toDate) {
        isProccessAPI = false;
        this._dialogService.alert("Both From and To Date Needed for Search");
      }
      else {
        isProccessAPI = true;
      }

      if (isProccessAPI) {
        this.dataTable.refreshTable('&shippingAgentName=' + this.shippingAgentCode
          + '&status=' + status
          + '&bolNo=' + bolNo
          + '&invoiceNumber=' + invoiceNumber
          + '&referenceNumber=' + referenceNumber
          + '&fromDate=' + this.fromDate
          + '&toDate=' + this.toDate);
      }

      this.shippingAgentCode = this.shippingAgentCode.split(' ')[0];
      console.log(this.shippingAgentCode);
    }
  }

  editFL(event) {
    const actionVal = event.action;
    const dialogOptions: DialogOptions = { disableClose: false, autoFocus: true };
    // const dialogConfig = new MatDialogConfig();
    // dialogConfig.disableClose = true;
    // dialogConfig.autoFocus = true;
    if (this.monitorForm.get('fromDate').value != null)
      var fromDate = this.monitorForm.get('fromDate').value._d;
    if (this.monitorForm.get('toDate').value != null)
      var toDate = this.monitorForm.get('toDate').value._d;
    fromDate = (fromDate != undefined) ? formatDate(fromDate, 'dd-MM-yyyy', 'en-US', '+0530') : "";
    toDate = (toDate != undefined) ? formatDate(toDate, 'dd-MM-yyyy', 'en-US', '+0530') : "";
    if (this.monitorForm.get('shippingAgentName').value == "" || this.monitorForm.get('shippingAgentName').value == undefined)
      this.shippingAgentCode = "";
    else
      this.shippingAgentCode = this.monitorForm.get('shippingAgentName').value;
    let data = [{
      fromDate: fromDate,
      toDate: toDate,
      shippingAgentCode: this.shippingAgentCode,
    }]
    data.push(event);

    this._dialogService
      .openDialog(MonitorModelInternalComponent, data, dialogOptions)
      .subscribe(retval => {
        this.shippingAgentCode = this.shippingAgentCode.split('-')[0];
      });
    //this.shippingAgentCode = this.shippingAgentCode.split('-')[0];
  }
  onChange() {
    this.minDate = this.monitorForm.get("fromDate").value.toDate();
  }
}
