import { Component, OnInit, ViewChild } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { Validators, FormGroup } from '@angular/forms';
import { TableDefn, TableType } from 'src/app/lib/classes/TableDefn';
import { TableComponent } from 'src/app/lib/components/table/table.component';
import { formatDate } from '@angular/common';
import { Validator } from 'src/app/lib/classes/field.interface';
class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}
@Component({
  selector: 'app-req-search-internal',
  templateUrl: './req-search-internal.component.html',
  styleUrls: ['./req-search-internal.component.css']
})
export class ReqSearchInternalComponent extends BaseComponent implements OnInit {
  myForm: FormGroup;
  items: any[] = [];
  @ViewChild(TableComponent) dataTable: TableComponent;
  shippingAgentValidation: Validator[];
  importerValidation: Validator[];
  tableDfn: TableDefn[] = [
    { displayName: 'BOL No', mappingName: 'bolNo', type: TableType.number, sort: true },
    { displayName: 'Invoice No', mappingName: 'bolInvoiceNo', type: TableType.string, sort: false },
    { displayName: 'Shipping Agent Code', mappingName: 'shippingCode', type: TableType.string, sort: false },
    { displayName: 'Initiator Code', mappingName: 'initiatorCode', type: TableType.string, sort: false },
    { displayName: 'Created Date', mappingName: 'createdDateStr', type: TableType.string, sort: true },
  ];
  requestTypeItems: any[] = new Array();
  constructor() {
    super();
  }
  ngAfterViewInit() {
    this.getChildProperty();
  }
  getChildProperty() {
    const item = new Item('BOL REQUEST', 'BOL REQUEST');

    this.requestTypeItems.push(item);
    const item2 = new Item('INVOICE REQUEST', 'INVOICE REQUEST');
    this.requestTypeItems.push(item2);
    //  this.requestTypeItems.push('2', 'INVOICE REQUEST');

  }
  ngOnInit() {
    this.myForm = this.formBuilder.group({
      shippingAgentName: [''],
      bol: [''],
      invoiceNumber: [''],
      status: [''],
      importerName: [''],
      toDate: [''],
      frmDate: [''],
      reqType: ['BOL REQUEST']

    });

  }
  reloadDataTable($event) {
    var fromDate = "", toDate = "";
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
    this.dataTable.refreshTable(
      'bolNo=' + this.myForm.get('bol').value.toUpperCase()
      + '&invoiceNo=' + this.myForm.get('invoiceNumber').value.toUpperCase()
      + '&frmDate=' + fromDate
      + '&toDate=' + toDate
      + '&shippingLine=' + shippingName
      + '&reqType=' + this.myForm.get('reqType').value
      + '&impCode=' + importerName
    );

  }
}
