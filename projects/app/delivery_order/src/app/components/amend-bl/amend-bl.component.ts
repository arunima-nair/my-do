import { Location } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { BolDetail } from '../../model/bol-detail.model';
import { BolInvoice } from '../../model/bol-invoice.model';
import { Bol } from '../../model/bol.model';
import { FileuploadComponent } from 'src/app/lib/components/fileupload/fileupload.component';
import { TableDefn, TableType, TableAction } from 'src/app/lib/classes/TableDefn';
import { InvoiceType } from 'src/app/model/invoice-type';
import { Currency, currencyMapping } from 'src/app/common/enum/currency';
import { formatDate } from '@angular/common';
import { Observable } from 'rxjs';
import { NormalTableComponent } from 'src/app/lib/components/table/normal-table.component';
import { Validator } from '../../lib/classes/field.interface';

class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}

@Component({
  selector: 'app-amend-bl',
  templateUrl: './amend-bl.component.html',
  styleUrls: ['./amend-bl.component.css']
})


export class AmendBlComponent extends BaseComponent implements OnInit {
  @ViewChild(FileuploadComponent) fileUploadComponent: FileuploadComponent;
  @ViewChild(NormalTableComponent) invoiceTable: NormalTableComponent;
  form: FormGroup;
  invForm: FormGroup;
  bol: Bol;
  reqBol: Bol;
  boltype: string;
  invoiceIndex: any;
  tableItems: BolInvoice[] = [];
  pathFlag: boolean;
  items: FormArray;
  invoiceTyp: InvoiceType = new InvoiceType;
  invoiceList: any;
  invoiceIndexEdit: any;
  invoiceNoFlag: Boolean = false;
  invItems: Item[] = new Array<Item>();
  bolId: number;
  tableInvItems: BolInvoice[] = [];
  fileName: string;
  isNewInvoice: boolean = false;
  amountValidation: Validator[];
  bolTypeItems: any[] = new Array();
  version: number;
  isDateModified: Boolean = false;

  //Currency from Enum
  public currencyTypes;


  showInvPanel: boolean = false;
  minDate = new Date();
  maxDate = new Date();
  tableDfn: TableDefn[] = [
    { displayName: 'Invoice Number', mappingName: 'invoiceNumber', type: TableType.number, sort: true },
    { displayName: 'Invoice Value', mappingName: 'invoiceValue', type: TableType.string, sort: false },
    { displayName: 'Invoice Validity Date', mappingName: 'ModifiedInvoiceValidityDate', type: TableType.date, sort: false },
    { displayName: 'Invoice Date', mappingName: 'ModifiedInvoiceDate', type: TableType.date, sort: false },
    { displayName: 'Currency', mappingName: 'currency', type: TableType.string, sort: false }
  ];
  constructor(private _ts: TranslateService, public http: HttpClient,
    private _fb: FormBuilder, private route: ActivatedRoute, private _location: Location) {
    super();
    this._pageHeaderService.updateHeader('Amend BOL Details');
    this.bol = new Bol;
    this.reqBol = new Bol;
    /**VERSIONING SAVE */
    this._httpRequestService.postData('/do/app/api/secure/saveBolVersion?bolNo=' + this.route.snapshot.queryParams.bolEncr, false).subscribe((result) => {
      this.version = result.version;
      this.initLoadData();
      this.currencyTypes = currencyMapping;
    });


  }

  initLoadData() {
    this._httpRequestService.getData('/do/app/api/secure/getBLdetailsNew?bolNo=' + this.route.snapshot.queryParams.bolEncr, true).subscribe((res) => {
      this.bol = res
      this.bol.bolDetails = new BolDetail;
      this.showInvPanel = true;
      // this.bol.bolInvoice = new BolInvoice;

      if (res.dataItems[0].bolDetails[0] != undefined || res.dataItems[0].bolDetails[0] != null)
        this.bol.bolDetails = res.dataItems[0].bolDetails[0];
      if (res.dataItems[0].bolInvoices != undefined || res.dataItems[0].bolInvoices != null)
        this.bol.bolInvoice = res.dataItems[0].bolInvoices;
      if (res.dataItems[0].bolNumber != undefined || res.dataItems[0].bolNumber != null)
        this.bol.bolNumber = res.dataItems[0].bolNumber;
      if (res.dataItems[0].bolId != undefined || res.dataItems[0].bolId != null)
        this.bolId = res.dataItems[0].bolId;
      if (res.dataItems[0].status != undefined || res.dataItems[0].status != null)
        this.bol.status = res.dataItems[0].status;
      if (res.dataItems[0].bolType != undefined || res.dataItems[0].bolType != null) {
        this.bol.bolType = res.dataItems[0].bolType;
        this.boltype = res.dataItems[0].bolType;
      }
      if (res.dataItems[0].encBolNumber != undefined || res.dataItems[0].encBolNumber != null)
        this.bol.encBolNumber = res.dataItems[0].encBolNumber;

      if (this.bol.bolInvoice == undefined || this.bol.bolInvoice == null) {
        this.bol.bolInvoice = [];
        this.tableItems = this.bol.bolInvoice;
      }
      else {
        this.tableItems = this.bol.bolInvoice;
      }


      if (this.bol.bolDetails != undefined || this.bol.bolDetails != null) {
        this.bol.bolDetails.shippingAgentName = this.bol.bolDetails.shippingAgentName != undefined ? this.bol.bolDetails.shippingAgentName : "";
        this.bol.bolDetails.shippingAgentCode = this.bol.bolDetails.shippingAgentCode != undefined ? this.bol.bolDetails.shippingAgentCode : "";
        this.bol.bolDetails.vesselName = this.bol.bolDetails.vesselName != undefined ? this.bol.bolDetails.vesselName : "";
        this.bol.bolDetails.voyageNumber = this.bol.bolDetails.voyageNumber != undefined ? this.bol.bolDetails.voyageNumber : "";
        this.bol.bolDetails.importerCode = this.bol.bolDetails.importerCode != undefined ? this.bol.bolDetails.importerCode : "";
        this.bol.bolDetails.consigneeName = this.bol.bolDetails.consigneeName != undefined ? this.bol.bolDetails.consigneeName : "";
        this.bol.bolDetails.containerCount = this.bol.bolDetails.containerCount != undefined ? this.bol.bolDetails.containerCount : "";
        this.bol.bolDetails.vesselEta = this.bol.bolDetails.vesselEta != undefined ? this.bol.bolDetails.vesselEta : "";
        this.bol.bolDetails.vesselAta = this.bol.bolDetails.vesselAta != undefined ? this.bol.bolDetails.vesselAta : "";
      }

      this.formatInvoiceDates(this.tableItems);
      this.form.get('bolId').setValue(this.bolId);
      this.form.get('bolNumber').setValue(this.bol.bolNumber);
      this.form.get('bolType').setValue(this.bol.bolType);
      this.form.get('shippingAgentName').setValue(this.bol.bolDetails.shippingAgentName);
      this.form.get('shippingAgentCode').setValue(this.bol.bolDetails.shippingAgentCode);
      this.form.get('vesselName').setValue(this.bol.bolDetails.vesselName);
      this.form.get('voyageNumber').setValue(this.bol.bolDetails.voyageNumber);
      this.form.get('importerCode').setValue(this.bol.bolDetails.importerCode);
      this.form.get('consigneeName').setValue(this.bol.bolDetails.consigneeName);
      this.form.get('containerCount').setValue(this.bol.bolDetails.containerCount);
      this.form.get('vesselEta').setValue(this.bol.bolDetails.vesselEta);
      this.form.get('vesselAta').setValue(this.bol.bolDetails.vesselAta);
      this.addInvoice();
      this.onClear(event);
    });
  }

  ngOnInit() {
    // this.fileUploadComponent.allowedFileTypes = ['pdf'];
    this.bolTypeItems.push({ label: 'OBL', value: 'OBL' }, { label: 'EBL', value: 'EBL' });
    this._httpRequestService.getData('/do/app/api/secure/getInvoiceType', false).subscribe((res) => {
      res.forEach(element => {
        const item = new Item(element.invoiceTypeId, element.invoiceTypeName);
        this.invItems.push(item);
      });
    });


    this.form = this.formBuilder.group({
      bolId: [''],
      bolNumber: [, [Validators.required]],
      bolType: [, [Validators.required]],
      shippingAgentName: [, [Validators.required]],
      bolInvoices: this.formBuilder.group({
        invoiceNumber: ['', [Validators.required]],
        invoiceDate: [, [Validators.required]],
        invoiceValue: [, [Validators.required]],
        path: [],
        currency: [Currency.AED, [Validators.required]],
        invoiceValidityDate: [],
        invoiceType: [, [Validators.required]],
        uploadDoc: [''],
        encrInvoiceNumber: [],
        fileName: [],
        base64Invoice: []
      }),
      vesselName: [],
      vesselEtaAta: [],
      vesselEta: [],
      vesselAta: [],
      shippingAgentCode: [, [Validators.required]],
      voyageNumber: [],
      importerCode: [, []],
      pathFlag: [false],
      consigneeName: [],
      containerCount: []

    });
    this.amountValidation = [{ name: 'invoiceAmount', validator: Validators.pattern(/^[.\d]+$/), message: 'Enter valid amount' }];

  }


  addInvoice() {
    this.form.get('bolInvoices').enable();
    this.isNewInvoice = true;
    this.invoiceNoFlag = false;
    this.showInvPanel = true;
    this.form.get('bolInvoices').reset();
    this.clearFileUpload(event);
    this.pathFlag = false;
  }


  tableAction(data) {
    if (data.action == 1 || data.action == 3) {
      this.isNewInvoice = false;
      this.form.get('bolInvoices').enable();
      if (data.action == 3) {
        this.form.get('bolInvoices').disable();
      }
      if ((data.dataRow.status == 'PAYMENT_PENDING' || data.dataRow.status == 'PAYMENT_PENDING_WITH_IMPORTER' || data.dataRow.status == 'PAYMENT_SUCCESS') && data.action == 1) {
        this._dialogService.alert("You Can't Edit this invoice!!");
      }
      else {
        this.form.get('bolInvoices').reset();
        if (data.dataRow.path == null || data.dataRow.path == undefined || data.dataRow.path == '') {
          this.clearFileUpload(event);
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

        this.populateDates(data.dataRow);

        // this.invoiceList.at(this.invoiceIndex).patchValue(data.dataRow.invoiceNumber);
        this.form.get('bolInvoices').patchValue(data.dataRow);
        if (data.dataRow.invoiceType.invoiceTypeId == undefined)
          this.form.get('bolInvoices').get('invoiceType').setValue(data.dataRow.invoiceTypeId);
        else
          this.form.get('bolInvoices').get('invoiceType').setValue(data.dataRow.invoiceType.invoiceTypeId);
      }
    }
    else if (data.action == 2) {
      if (data.dataRow.status == 'PAYMENT_PENDING' || data.dataRow.status == 'PAYMENT_PENDING_WITH_IMPORTER' || data.dataRow.status == 'PAYMENT_SUCCESS') {
        this._dialogService.alert("Payment already completed for the invoice. It cannot be deleted.");
      }
      else {
        this._dialogService.confirm('Are you sure to delete this invoice?')
          .subscribe(response => {
            if (response != 1) {
              return;
            }
            else {
              this.removeInvoice(data);
              this.showInvPanel = false;
            }
          });
      }

    }

  }

  removeInvoice = (data): any => {
    const index = this.tableItems.findIndex(item => item.invoiceNumber === data.dataRow.invoiceNumber);
    this.tableItems.splice(index, 1);
    this.invoiceTable.refreshTable(this.tableItems);
  }

  showUpdatedItem(newItem) {
    let updateItem = this.tableItems.find(this.findIndexToUpdate, newItem.invoiceNumber);
    let index = this.tableItems.indexOf(updateItem);
    let invType = this.tableItems[index].invoiceType;

    var updatedItem = Object.keys(this.tableItems[index]).reduce((all, key) => {
      this.tableItems[index][key] = newItem[key] || this.tableItems[index][key];
      return this.tableItems[index];
    }, {});

    this.tableItems[index].base64Invoice = newItem.path;
    this.tableItems[index].path = newItem.path;
    this.tableItems[index].fileName = newItem.uploadDoc;

    this.tableItems[index].invoiceType = invType;
    this.tableItems[index].invoiceType.invoiceTypeId = this.form.get('bolInvoices').get('invoiceType').value;


    this.tableItems[index].invoiceNumber = this.form.get('bolInvoices').get('invoiceNumber').value;

    if (this.form.get('bolInvoices').get('invoiceDate').value != undefined && this.form.get('bolInvoices').get('invoiceDate').value != null) {
      this.tableItems[index].invoiceDate = formatDate(this.form.get('bolInvoices').get('invoiceDate').value, 'yyyy-MM-dd', 'en-US', '+0530');
      this.tableItems[index]['ModifiedInvoiceDate'] = formatDate(this.form.get('bolInvoices').get('invoiceDate').value, 'dd/MM/yyyy', 'en-US', '+0530');
    }
    else
      this.tableItems[index]['ModifiedInvoiceDate'] = "";
    if (this.form.get('bolInvoices').get('invoiceValidityDate').value != undefined && this.form.get('bolInvoices').get('invoiceValidityDate').value != null) {
      this.tableItems[index].invoiceValidityDate = formatDate(this.form.get('bolInvoices').get('invoiceValidityDate').value, 'yyyy-MM-dd', 'en-US', '+0530');
      this.tableItems[index]['ModifiedInvoiceValidityDate'] = formatDate(this.form.get('bolInvoices').get('invoiceValidityDate').value, 'dd/MM/yyyy', 'en-US', '+0530');
    }
    else
      this.tableItems[index]['ModifiedInvoiceValidityDate'] = "";

    // this.tableItems[index].invoiceType. = "";
    this.invoiceTable.refreshTable(this.tableItems);
  }

  findIndexToUpdate(newItem) {
    return newItem.invoiceNumber === this;
  }

  addTable(data) {
    super.validateForm(this.form);
    if (this.form.get('bolInvoices').valid) {

      this.showInvPanel = false;

      var invoiceDate = "";
      var invoiceValidityDate = "";

      if (this.form.get('bolInvoices').get('invoiceDate').value != undefined && this.form.get('bolInvoices').get('invoiceDate').value != null)
        invoiceDate = formatDate(this.form.get('bolInvoices').get('invoiceDate').value, 'dd/MM/yyyy', 'en-US', '+0530');

      else
        invoiceDate = "";
      if (this.form.get('bolInvoices').get('invoiceValidityDate').value != undefined && this.form.get('bolInvoices').get('invoiceValidityDate').value != null)
        invoiceValidityDate = formatDate(this.form.get('bolInvoices').get('invoiceValidityDate').value, 'dd/MM/yyyy', 'en-US', '+0530');
      else
        invoiceValidityDate = "";



      if (this.tableItems.some((item) => item.invoiceNumber == this.form.get('bolInvoices').get('invoiceNumber').value.toUpperCase())) {
        if (this.isNewInvoice) {
          this._dialogService.alert('This Invoice is available in current BOL. Please Click edit button And Update Invoice');
        } else {
          this.showUpdatedItem(this.form.get('bolInvoices').value);
          this.invoiceTable.refreshTable(this.tableItems);
        }
      }
      else {
        //this.invoiceTyp.invoiceTypeId = this.form.get('bolInvoices').get('invoiceType').value;
        let invoiceTyp = new InvoiceType();
        invoiceTyp.invoiceTypeId = this.form.get('bolInvoices').get('invoiceType').value;
        let invoice = {
          invoiceValue: this.form.get('bolInvoices').get('invoiceValue').value,
          invoiceNumber: this.form.get('bolInvoices').get('invoiceNumber').value.toUpperCase(),
          invoiceDate: this.form.get('bolInvoices').get('invoiceDate').value,
          invoiceType: invoiceTyp,
          invoiceValidityDate: this.form.get('bolInvoices').get('invoiceValidityDate').value,
          ModifiedInvoiceDate: invoiceDate,
          ModifiedInvoiceValidityDate: invoiceValidityDate,
          currency: this.form.get('bolInvoices').get('currency').value,
          paymentLogs: null,
          path: this.form.get('bolInvoices').get('path').value ? this.form.get('bolInvoices').get('path').value : "",
          base64Invoice: this.form.get('bolInvoices').get('path').value ? this.form.get('bolInvoices').get('path').value : "",
          paidStatus: null,
          invoiceTypeLabel: this.form.get('bolInvoices').get('invoiceType').value,
          collections: null,
          fileName: this.fileName,
          invoiceTypeTxt: null,
          encrInvoiceNumber: this.form.get('bolInvoices').get('encrInvoiceNumber').value,
          status: null,
          disablecheck: true,
          createdBy: null,
          statusTxt: null,
          refNumber: null,
          download: false

        }
        this.tableItems.push(invoice);
        this.invoiceTable.refreshTable(this.tableItems);
      }
      this.clearFileUpload(event);
      this.form.get('bolInvoices').reset();
      this.showInvPanel = false;
      this.pathFlag = false;
    }
  }


  onClear(event) {
    this.form.get('bolInvoices').reset();
    this.clearFileUpload(event);
    this.pathFlag = false;
    this.showInvPanel = false;
  }


  amendBL(event) {
    var tableInvItems = this.tableItems;
    this.isDateModified = true;
    this.formatInvoiceDates(tableInvItems);

    this.reqBol.bolId = this.form.get('bolId').value;
    this.reqBol.bolNumber = this.form.get('bolNumber').value;
    this.reqBol.bolType = this.form.get('bolType').value;
    this.reqBol.bolDetails = new BolDetail;
    this.reqBol.bolDetails.shippingAgentName = this.form.get('shippingAgentName').value;
    this.reqBol.bolDetails.shippingAgentCode = this.form.get('shippingAgentCode').value;
    this.reqBol.bolDetails.vesselName = this.form.get('vesselName').value;
    this.reqBol.bolDetails.voyageNumber = this.form.get('voyageNumber').value;
    this.reqBol.bolDetails.importerCode = this.form.get('importerCode').value;
    this.reqBol.bolDetails.consigneeName = this.form.get('consigneeName').value;
    this.reqBol.bolDetails.containerCount = this.form.get('containerCount').value;

    if (this.form.get('vesselEta').value != undefined && this.form.get('vesselEta').value != null && this.form.get('vesselEta').value != "")
      this.reqBol.bolDetails.vesselEta = formatDate(this.form.get('vesselEta').value, 'dd/MM/yyyy', 'en-US', '+0530');
    else
      this.reqBol.bolDetails.vesselEta = "";
    if (this.form.get('vesselAta').value != undefined && this.form.get('vesselAta').value != null && this.form.get('vesselAta').value != "")
      this.reqBol.bolDetails.vesselAta = formatDate(this.form.get('vesselAta').value, 'dd/MM/yyyy', 'en-US', '+0530');
    else
      this.reqBol.bolDetails.vesselAta = "";


    // let selectedInvoiceType = this.invItems.filter(x => x.value === this.form.get('bolInvoices').get('invoiceType').value);
    // for (let invoice of this.tableItems) {
    //   //this.invItems.filter(x => x.value === invoice.invoiceType.invoiceTypeId);
    //   this.updateItem(invoice);
    // }


    if (this.reqBol.bolDetails.consigneeName == undefined) {
      this.reqBol.bolDetails.consigneeName = "";
    }
    if (this.reqBol.bolDetails.containerCount == undefined) {
      this.reqBol.bolDetails.containerCount = "";
    }

    let modifiedDates = tableInvItems.map(function (item) {
      var invFormatedDateArray: any;
      var invoiceValidityDate: any;
      var invFormatedDate1: any;
      var invFormatedDate2: any;
      if (item.invoiceDate != undefined && item.invoiceDate != null && item.invoiceDate != "")
        if (item.invoiceDate != undefined && item.invoiceDate.includes("/")) {
          invFormatedDateArray = item.invoiceDate.split('/');
          invFormatedDate1 = new Date(invFormatedDateArray[2], invFormatedDateArray[1] - 1, invFormatedDateArray[0]);
          item.invoiceDate = formatDate(invFormatedDate1, 'dd-MM-yyyy', 'en-US', '+0530');
        }
      if (item.invoiceValidityDate != undefined && item.invoiceValidityDate != null && item.invoiceValidityDate != "")
        if (item.invoiceValidityDate != undefined && item.invoiceValidityDate.includes("/")) {
          invoiceValidityDate = item.invoiceValidityDate.split('/');
          invFormatedDate2 = new Date(invoiceValidityDate[2], invoiceValidityDate[1] - 1, invoiceValidityDate[0]);
          item.invoiceValidityDate = formatDate(invFormatedDate2, 'dd-MM-yyyy', 'en-US', '+0530');
        }
    });


    if (this.reqBol.bolDetails.vesselEta != undefined && this.reqBol.bolDetails.vesselEta.includes("/")) {
      var vessalETA: any;
      vessalETA = this.reqBol.bolDetails.vesselEta.split('/');
      var vessalETASplit = new Date(vessalETA[2], vessalETA[1] - 1, vessalETA[0]);
      this.reqBol.bolDetails.vesselEta = formatDate(vessalETASplit, 'dd-MM-yyyy', 'en-US', '+0530');
    }
    if (this.reqBol.bolDetails.vesselAta != undefined && this.reqBol.bolDetails.vesselAta.includes("/")) {
      var vessalATA: any;
      vessalATA = this.reqBol.bolDetails.vesselAta.split('/');
      var vessalATASplit = new Date(vessalATA[2], vessalATA[1] - 1, vessalATA[0]);
      this.reqBol.bolDetails.vesselAta = formatDate(vessalATASplit, 'dd-MM-yyyy', 'en-US', '+0530');
    }
    this._httpRequestService.getData('/do/app/api/secure/getBolVersion?bolNo=' + this.bol.encBolNumber, false).subscribe((bolversion) => {
      // console.log(bolversion);
      if (bolversion == this.version) {
        this._httpRequestService.postData('/do/app/api/secure/saveAmendBLdetails?bolNumber=' + this.bol.encBolNumber
          + '&bolType=' + this.reqBol.bolType
          + '&shippingAgentName=' + this.reqBol.bolDetails.shippingAgentName
          + '&shippingAgentCode=' + this.reqBol.bolDetails.shippingAgentCode
          + '&vesselName=' + this.reqBol.bolDetails.vesselName
          + '&voyageNumber=' + this.reqBol.bolDetails.voyageNumber
          + '&importerCode=' + this.reqBol.bolDetails.importerCode
          + '&containerCount=' + this.reqBol.bolDetails.containerCount
          + '&consigneeName=' + this.reqBol.bolDetails.consigneeName
          + '&vesselEta=' + this.reqBol.bolDetails.vesselEta
          + '&vesselAta=' + this.reqBol.bolDetails.vesselAta,
          tableInvItems,
          true).subscribe((result) => {
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
          }, () => {
            this.notfnService.clearMessage();
            this._dialogService.alert('Unable to save data');

          });
      } else {
        this._dialogService.alert('Amend Bol denied. Bol already under processing by another user.');
      }
    });

  }

  populateDates(data) {
    /*if (data.invoiceDate && Object.prototype.toString.call(data.invoiceDate) != '[object Date]') {
      let invFormatedDateArray = data.invoiceDate.split('-');
      let invFormatedDate = new Date(invFormatedDateArray[2], invFormatedDateArray[1] - 1, invFormatedDateArray[0])
      data.invoiceDate = invFormatedDate;
    }
    if (data.invoiceValidityDate && Object.prototype.toString.call(data.invoiceValidityDate) != '[object Date]') {
      let invValidFormatedDateArray = data.invoiceValidityDate.split('-');
      let invValidFormatedDate = new Date(invValidFormatedDateArray[2], invValidFormatedDateArray[1] - 1, invValidFormatedDateArray[0]);
      data.invoiceValidityDate = invValidFormatedDate;
    }*/

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
      if (!this.isDateModified)
        this.commonDateFormat(invoice);
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

  commonDateFormat(data) {
    if (data.invoiceDate && Object.prototype.toString.call(data.invoiceDate) != '[object Date]') {
      let invFormatedDateArray = data.invoiceDate.split('-');
      let invFormatedDate = new Date(invFormatedDateArray[2], invFormatedDateArray[1] - 1, invFormatedDateArray[0])
      data.invoiceDate = invFormatedDate;
    }
    if (data.invoiceValidityDate && Object.prototype.toString.call(data.invoiceValidityDate) != '[object Date]') {
      let invValidFormatedDateArray = data.invoiceValidityDate.split('-');
      let invValidFormatedDate = new Date(invValidFormatedDateArray[2], invValidFormatedDateArray[1] - 1, invValidFormatedDateArray[0]);
      data.invoiceValidityDate = invValidFormatedDate;
    }
    return data;
  }


  close() {
    this._dialogService.confirm('All your changes will be lost. Do you want to proceed?')
      .subscribe(retval => {
        if (retval != 1) {
          return;
        }
        else {
          const navextras: NavigationExtras = {
            queryParams: { 'bolNo': this.route.snapshot.queryParams.bolEncr, 'amend': this.route.snapshot.queryParams.amend }
          };
          if (this.route.snapshot.queryParams.parent != undefined)
            this._router.navigate([this.route.snapshot.queryParams.parent], navextras);
          else
            window.history.back();
        }
      });
  }




  getFiles(id) {
    //var s = window.open("/do/app/api/file/getFile?bol=" + this.form.get('bolNumber').value + "&invNo=" + id);
    super.postToExternalSite([], "/do/app/api/file/getFile?bol=" + this.form.get('bolNumber').value + "&invNo=" + id);

  }

  fileUpload(event) {
    if (event != null) {
      if (event.download) {
        this.getFiles(event.id);
      }
      else {
        if (event.startsWith('data:application/pdf')) {
          this.form.get('bolInvoices').patchValue({
            // uploadDoc: $event
            path: event,
            uploadDoc: this.fileUploadComponent.fileName
          });
        }
      }
    }
    //this.chageDetectorRef.markForCheck();
    else {
      this.form.get('bolInvoices').patchValue({
        path: null,
        uploadDoc: null
      });
    }
  }

  checkValidFileTypes(type) {
    let validType = false;
    if (type[1] === this.fileUploadComponent.allowedFileTypes[0]) {
      validType = true;
    }
    return validType;

  }

  onSubmit(event) {
    var url = "";
    url = "/do/app/api/secure/saveInvoicedetails";
    super.postData(url, JSON.stringify(this.form.get("uploadDoc").value))
      .subscribe((data) => {
      }, (error) => {
        this._dialogService.alert('Unable to save data');
      });

  }

  reset($event) {
    this._dialogService.confirm('All your changes will be lost. Do you want to reset the page?')
      .subscribe(response => {
        if (response != 1) {
          return;
        }
        else {
          this.bol = new Bol;
          this.form.reset();
          this.initLoadData();
        }
      });
  }

  checkInvoiceNo(): any {
    var invNo = this.form.get('bolInvoices').get('invoiceNumber').value;
    this._httpRequestService.postData('/do/app/api/secure/getInvoiceDetails?invNo=' + invNo, null, true).subscribe((resBol) => {
      if (resBol.message != undefined) {
        if (resBol.message != this.bol.bolNumber) {
          this._dialogService.alert("BOL Invoice No: " + invNo + " is associated with another BOL. ");
          this.form.get('bolInvoices').get('invoiceNumber').setValue('');
          return true;
        }
        else {
          return false;
        }
      }
    });

  }

  checkInvType(enevt) {
    //TO DO
  }

  checkInvCurreny(enevt) {
    //TO DO
  }

  clearFileUpload(event) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
    }

    if (this.fileUploadComponent != undefined && this.fileUploadComponent != null) {
      this.fileUploadComponent.dataEmitter.emit(null);
      this.fileUploadComponent.fileLoaded = false;
      this.fileUploadComponent.defaultLoaded = false;
      this.fileUploadComponent.dngclass = 'dnd-area';
    }
  }


  standardDate(date): any {
    let invFormatedDateArray = date.split('/');
    let invFormatedDate = new Date(invFormatedDateArray[2], invFormatedDateArray[1] - 1, invFormatedDateArray[0])
    return invFormatedDate;
  }
  deleteBL($event) {
    this._dialogService.confirm('Do you want to delete Bol?')
      .subscribe(response => {
        if (response != 1) {
          return;
        }
        else {
          this._httpRequestService.postData('/do/app/api/secure/deleteBol?bolNo=' + this.route.snapshot.queryParams.bolEncr, true).subscribe((res) => {
            if (res) {
              const navextras: NavigationExtras = {
                queryParams: { 'data': 'Bol deleted successfully.', 'success': true }
              }
              this.notfnService.clearMessage();
              this._router.navigate(['/confirm'], navextras);
            }
            else {
              const navextras: NavigationExtras = {
                queryParams: { 'data': 'Bol deletion failed.', 'success': false }
              };
              this.notfnService.clearMessage();
              this._router.navigate(['/confirm'], navextras);
            }

          });
        }
      });
  }
}