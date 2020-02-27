import { Component, OnInit } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { TranslateService } from '@ngx-translate/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Bol } from '../../model/bol.model';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BolDetail } from '../../model/bol-detail.model';
import { BolInvoice } from '../../model/bol-invoice.model';
import { Location, formatDate } from '@angular/common';
import { TableDefn, TableType } from 'src/app/lib/classes/TableDefn';
import { InvoiceType } from 'src/app/model/invoice-type';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
@Component({
  selector: 'app-view-bl',
  templateUrl: './view-bl.component.html',
  styleUrls: ['./view-bl.component.css']
})
export class ViewBlComponent extends BaseComponent implements OnInit {
  form: FormGroup;

  bol: Bol;
  tableItems: BolInvoice[] = [];
  pathFlag: boolean;
  items: FormArray;
  showInvPanel: boolean = false;
  invoiceNoFlag: Boolean = false;
  invoiceIndex: any;
  selectedRow: any;
  showNoFile: boolean = false;
  agentType: String;
  agentCode: String;
  userType: string;

  tableDfn: TableDefn[] = [
    { displayName: 'Invoice Number', mappingName: 'invoiceNumber', type: TableType.number, sort: true },
    { displayName: 'Invoice Value', mappingName: 'invoiceValue', type: TableType.string, sort: false },
    { displayName: 'Invoice Validity Date', mappingName: 'invoiceValidityDate', type: TableType.date, sort: false },
    { displayName: 'Invoice Date', mappingName: 'invoiceDate', type: TableType.date, sort: false },
    { displayName: 'Currency', mappingName: 'currency', type: TableType.string, sort: false }
  ];

  constructor(private _ts: TranslateService, public http: HttpClient,
    private _fb: FormBuilder, private route: ActivatedRoute, private _location: Location, private _securityInfo: SecurityInfoService) {

    super();

    this._pageHeaderService.updateHeader('View BOL Details');

    this.bol = new Bol;

  }

  ngOnInit() {
    this.agentType = this._securityInfo.getAgentType();
    this.agentCode = this._securityInfo.getAgentCode();
    this.userType = this._securityInfo.getUserType();

    this.form = this.formBuilder.group({

      bolNumber: [, []],
      bolType: [, []],
      shippingAgentName: [, []],
      vesselName: [, []],
      invoiceNumber: [, []],
      vesselEtaAta: [, []],
      shippingAgentCode: [, []],
      invoiceDate: [, []],
      invoiceValue: [, []],
      path: [, []],
      uploadDoc: [, Validators.required],
      voyageNumber: [, []],
      importerCode: [, []],
    });

    if (this.userType == 'IN') {
      this._httpRequestService.postData('/do/app/api/secure/getBLdetailsForInternal?bolNo=' + this.route.snapshot.queryParams.bolEncr, null, true).subscribe((res) => {
        console.log(res);
        this.bol.bolDetails = new BolDetail;
        if (res.dataItems.length > 0) {
          this.bol = res.dataItems[0];
          // this.bol.status = res.dataItems[0].status;
          // this.bol.bolNumber = res.dataItems[0].bolNumber;
          this.bol.bolDetails = res.dataItems[0].bolDetails[0];
          this.bol.bolInvoice = res.dataItems[0].bolInvoices;
          this.selectedRow = BolInvoice;
          this.selectedRow.invoiceType = InvoiceType;
          if (this.bol.bolInvoice) {
            this.formatInvoiceDates(this.bol.bolInvoice);
          }
        }
      });
    }
    else {
      this._httpRequestService.postData('/do/app/api/secure/getBLdetailsNew?bolNo=' + this.route.snapshot.queryParams.bolEncr, null, true).subscribe((res) => {
        console.log(res);
        this.bol.bolDetails = new BolDetail;
        //    this.bol.bolInvoice = new BolInvoice[];
        if (res.dataItems.length > 0) {
          this.bol.status = res.dataItems[0].status;
          this.bol.bolNumber = res.dataItems[0].bolNumber;
          this.bol.bolDetails = res.dataItems[0].bolDetails[0];
          this.bol.bolInvoice = res.dataItems[0].bolInvoices;
          this.selectedRow = BolInvoice;
          this.selectedRow.invoiceType = InvoiceType;
          if (this.bol.bolInvoice) {
            this.formatInvoiceDates(this.bol.bolInvoice);
          }
        }
      });
    }
  }
  clicked(event) {
    console.log(event);
    console.log(this.form.value);

  }

  populateDates(data) {
    // For Date populated issue
    if (data.ModifiedInvoiceDate != undefined && data.ModifiedInvoiceDate != null && data.ModifiedInvoiceDate != "")
      if (data.invoiceDate) {
        let invFormatedDateArray = data.ModifiedInvoiceDate.split('/');
        let invFormatedDate = new Date(invFormatedDateArray[2], invFormatedDateArray[1] - 1, invFormatedDateArray[0])
        data.invoiceDate = invFormatedDate;
      }
    if (data.ModifiedInvoiceValidityDate != undefined && data.ModifiedInvoiceValidityDate != null && data.ModifiedInvoiceValidityDate != "")
      if (data.invoiceValidityDate) {
        let invValidFormatedDateArray = data.ModifiedInvoiceValidityDate.split('/');
        let invValidFormatedDate = new Date(invValidFormatedDateArray[2], invValidFormatedDateArray[1] - 1, invValidFormatedDateArray[0]);
        data.invoiceValidityDate = invValidFormatedDate;
      }
  }

  formatInvoiceDates(tableInvItems) {
    var invoiceDate = "";
    var invoiceValidityDate = "";
    for (let invoice of tableInvItems) {
      if (invoice.invoiceDate != undefined && invoice.invoiceDate != null) {
        try {
          invoiceDate = formatDate(invoice.invoiceDate, 'dd/MM/yyyy', 'en-US', '+0530');
          invoice['ModifiedInvoiceDate'] = invoiceDate;
          invoice['invoiceDate'] = invoiceDate;
        }
        catch{
          invoice['ModifiedInvoiceDate'] = invoice.invoiceDate;
          invoice['invoiceDate'] = invoice.invoiceDate;
        }
      }
      else
        invoiceDate = "";
      if (invoice.invoiceValidityDate != undefined && invoice.invoiceValidityDate != null) {
        try {
          invoiceValidityDate = formatDate(invoice.invoiceValidityDate, 'dd/MM/yyyy', 'en-US', '+0530');
          invoice['ModifiedInvoiceValidityDate'] = invoiceValidityDate;
          invoice['invoiceValidityDate'] = invoiceValidityDate;
        }
        catch{
          invoice['ModifiedInvoiceValidityDate'] = invoice.invoiceValidityDate;
          invoice['invoiceValidityDate'] = invoice.invoiceValidityDate;
        }
      }
      else
        invoiceValidityDate = "";
    }
  }

  getFiles(invNo) {
    super.postToExternalSite([], "/do/app/api/file/getFile?bol=" + this.bol.bolNumber + "&invNo=" + invNo);
  }

  dowloadInvoice(row) {
    if (row != null)
      if (row.path) {
        if (this.userType != 'IN') {
          this.getFiles(row.encrInvoiceNumber);
        } else {
          this._dialogService.alert("Not authorized to access the Invoice.");
        }
      }
      else {
        this._dialogService.alert("No Invoice Uploaded for this Invoice.");
      }
  }
  tableAction(data) {
    this.selectedRow = data.dataRow;
    if (data.dataRow.path == null || data.dataRow.path == undefined || data.dataRow.path == '') {
      //this.clearFileUpload(event);
    }

    this.showInvPanel = true;

    if (data.dataRow.path != undefined && data.dataRow.path != null && data.dataRow.path != '') {
      this.pathFlag = true;
    }
    else {
      this.pathFlag = false;
    }
    this.invoiceNoFlag = true;
    this.invoiceIndex = data.index;

    //this.populateDates(data.dataRow);

    // this.form.get('bolInvoices').patchValue(data.dataRow);
    // if (data.dataRow.invoiceType.invoiceTypeId == undefined)
    //   this.form.get('bolInvoices').get('invoiceType').setValue(data.dataRow.invoiceTypeId);
    // else
    //   this.form.get('bolInvoices').get('invoiceType').setValue(data.dataRow.invoiceType.invoiceTypeId);

  }

  close() {
    this._dialogService.confirm('All your changes will be lost. Do you want to proceed?')
      .subscribe(retval => {
        console.log(' Dialog Data ', retval);
        if (retval != 1) {
          return;
        }
        else {
          const navextras: NavigationExtras = {
            queryParams: { 'bolNo': this.bol.bolNumber }
          };
          if (this.userType == 'IN') {
            this._router.navigate(['/searchBOLInternal'], navextras);
          } else {
            this._router.navigate(['/search'], navextras);
          }
        }
      });

  }
  viewInvoice() {
    window.open("/do/app/api/file/getFile?bol=" + this.bol.bolNumber + "&invNo=" + this.bol.bolInvoice[0].invoiceNumber);
  }
}