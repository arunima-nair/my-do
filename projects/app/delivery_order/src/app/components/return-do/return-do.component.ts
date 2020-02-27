import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { BaseComponent } from '../../lib/classes/BaseComponent';
import { SelectComponent } from 'src/app/lib/components/select/select.component';
import { FormGroup, Validators } from '@angular/forms';
import { Constants } from 'src/app/lib/classes/Constants';
import { TableDefn, TableType, TableAction } from 'src/app/lib/classes/TableDefn';
import { BolInvoice } from './../../model/bol-invoice.model';
import { NavigationExtras, ActivatedRoute } from '@angular/router';
import { InvoiceType } from './../../model/invoice-type';
import { FileuploadComponent } from 'src/app/lib/components/fileupload/fileupload.component';
import { Currency, currencyMapping } from 'src/app/common/enum/currency';
import { formatDate } from '@angular/common';
import { DateInputComponent } from 'src/app/lib/components/date-input/date-input.component';
import { NormalTableComponent } from 'src/app/lib/components/table/normal-table.component';
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
  selector: 'app-return-do',
  templateUrl: './return-do.component.html',
  styleUrls: ['./return-do.component.css']
})
export class ReturnDoComponent extends BaseComponent implements OnInit {
  @ViewChild('#return')
  @ViewChild(NormalTableComponent) returnInvoiceTable: NormalTableComponent;
  private _cdRef: ChangeDetectorRef;
  returnSelect: SelectComponent = new SelectComponent(this._cdRef);
  items: Item[] = new Array<Item>();
  invItems: Item[] = new Array<Item>();
  form: FormGroup;
  tableItems: BolInvoice[] = [];
  tableInvItems: BolInvoice[] = [];
  invoiceTyp: InvoiceType = new InvoiceType;
  showInvPanel: boolean = false;
  fileName: string;
  isEdit: Boolean = false;
  uploadedID: string;
  isUploaded: Boolean = false;
  amountValidation: Validator[];

  @ViewChild(FileuploadComponent) fileUploadComponent: FileuploadComponent;
  @ViewChild(DateInputComponent) dateInputComponent: DateInputComponent;
  public currencyTypes;
  minDate = new Date()
  tableDfn: TableDefn[] = [
    { displayName: 'Invoice Number', mappingName: 'invoiceNumber', type: TableType.number, sort: true },
    { displayName: 'Invoice Value', mappingName: 'invoiceValue', type: TableType.string, sort: false },
    { displayName: 'Invoice Validity Date', mappingName: 'invoiceValidityDate', type: TableType.string, sort: false },
    { displayName: 'Invoice Date', mappingName: 'invoiceDate', type: TableType.string, sort: false },
    { displayName: 'Invoice Type', mappingName: 'invoiceTypeTxt', type: TableType.string, sort: false },
    { displayName: 'Currency', mappingName: 'currency', type: TableType.string, sort: false },
    // { displayName: 'Invoice Copy', mappingName: 'path', type: TableType.string, sort: false }
  ];
  constructor(private route: ActivatedRoute) {
    super();
    this._httpRequestService.getData('/do/app/api/secure/getReturnRemarks', false).subscribe((res) => {
      console.log(res);
      res.forEach(element => {
        const item = new Item(element.returnRemarksId, element.remarks);
        this.items.push(item);
      });
    });
    this.currencyTypes = currencyMapping;
  }

  clicked(event) {

    console.log(event);
    console.log(this.form.value);
    if (parseInt(event) === Constants.OK_CLICK) {
      this.save();
    } else {
      this.close();
    }

  }
  save() {
    // this.dialogRef.close(this.form.value);
  }

  close() {
    //  this.dialogRef.close();
  }
  addInvoice() {

    if (this.invItems.length == 0)
      this._httpRequestService.getData('/do/app/api/secure/getInvoiceType', true).subscribe((res) => {
        console.log(res);
        res.forEach(element => {
          const item = new Item(element.invoiceTypeId, element.invoiceTypeName);
          this.invItems.push(item);
        });
      });
    this.form.get('invoiceValue').reset();
    this.form.get('invoiceNumber').reset();
    this.form.get('invoiceDate').reset();
    this.form.get('invoiceValidityDate').reset();
    this.form.get('currency').reset();
    this.form.get('path').reset();
    this.form.get('invoiceType').reset();

    this.showInvPanel = true;
  }
  tableAction(data) {
    console.log(data);
    console.log(this.tableItems.length);
    if (data.action === TableAction.delete) {
      this.deleteTableRow(data.index);
    }
    else if (data.action === TableAction.edit) {
      this.showInvPanel = true;
      this.form.patchValue(data.dataRow);
      //console.log(data.dataRow.invoiceTypeLabel);
      this.form.get('invoiceType').setValue(data.dataRow.invoiceTypeLabel);
      if (data.dataRow.invoiceValidityDate != undefined && data.dataRow.invoiceValidityDate != null && data.dataRow.invoiceValidityDate != "") {
        var parts = data.dataRow.invoiceValidityDate.split('-');
        let invoiceValidityDateFormat = new Date(parts[2], parts[1] - 1, parts[0])
        this.form.get('invoiceValidityDate').setValue(invoiceValidityDateFormat);
      }
      if (data.dataRow.invoiceDate != undefined && data.dataRow.invoiceDate != null && data.dataRow.invoiceDate != "") {
        var parts = data.dataRow.invoiceDate.split('-');
        let invoiceDateFormat = new Date(parts[2], parts[1] - 1, parts[0])
        this.form.get('invoiceDate').setValue(invoiceDateFormat);
      }
      this.uploadedID = data.dataRow.base64Invoice;
      this.isEdit = true;
      if (this.uploadedID != null)
        this.isUploaded = true;
      else
        this.isUploaded = false;
    } else if (data.action === TableAction.download) {
      this.isUploaded = false;
      this.isEdit = false;
      if (data.dataRow.base64Invoice) {
        this.showPdf(data.dataRow.base64Invoice, data.dataRow.fileName);
      } else {
        this._dialogService.alert('Invoice not uploaded.')
      }
    }
    console.log(this.tableItems.length);
  }

  deleteTableRow(i) {
    this.tableItems.splice(i, 1);
  }
  addTable() {
    super.validateForm(this.form);
    if (this.form.valid) {
      console.log(this.isEdit)
      this._httpRequestService.getData('/do/app/api/secure/checkInvoice?invoiceNumber=' + this.form.get('invoiceNumber').value.toUpperCase() + '&bol=' + this.route.snapshot.queryParams.bol, false).subscribe((res) => {
        console.log(res);
        if (!res) {
          this._dialogService.alert('Invoice Number ' + this.form.get('invoiceNumber').value + ' already associated with corresponding Bol.');
          return;

        }
        else {
          let existItem = this.tableItems.filter(item => item.invoiceNumber === this.form.get('invoiceNumber').value);
          if (existItem.length > 0) {
            if (!this.isEdit) {
              this._dialogService.alert("Invoice Number : " + this.form.get('invoiceNumber').value + " already added.")
              return;
            } else {
              const index = this.tableItems.findIndex(item => item.invoiceNumber === this.form.get('invoiceNumber').value);
              this.tableItems.splice(index, 1);
              this.showInvPanel = false;

              // this.invoiceTyp.invoiceTypeId = this.form.get('invoiceType').value;
              let invDate = this.form.get('invoiceDate').value ? formatDate(this.form.get('invoiceDate').value, 'dd-MM-yyyy', 'en-US', '+0530') : this.form.get('invoiceDate').value;
              let invValidityDate = this.form.get('invoiceValidityDate').value ? formatDate(this.form.get('invoiceValidityDate').value, 'dd-MM-yyyy', 'en-US', '+0530') : this.form.get('invoiceValidityDate').value;

              this.tableItems.push({
                invoiceValue: this.form.get('invoiceValue').value,
                invoiceNumber: this.form.get('invoiceNumber').value.toUpperCase(),
                invoiceDate: invDate,
                invoiceType: this.invoiceTyp,
                invoiceValidityDate: invValidityDate,
                currency: this.form.get('currency').value,
                base64Invoice: this.form.get('path').value,
                paymentLogs: null,
                path: null,
                paidStatus: null,
                invoiceTypeLabel: this.form.get('invoiceType').value,
                collections: null,
                fileName: this.fileName,
                invoiceTypeTxt: this.invoiceTyp.invoiceTypeName,
                encrInvoiceNumber: null,
                status: null,
                disablecheck: false,
                createdBy: null,
                statusTxt: null,
                refNumber: null,
                 download: false

              });
              this.returnInvoiceTable.refreshTable(this.tableItems);
            }
          } else {
            this.showInvPanel = false;
            //   console.log(this.form.get('path').value);

            //  this.tableItems=new Array;
            let invDate = this.form.get('invoiceDate').value ? formatDate(this.form.get('invoiceDate').value, 'dd-MM-yyyy', 'en-US', '+0530') : this.form.get('invoiceDate').value;
            let invValidityDate = this.form.get('invoiceValidityDate').value ? formatDate(this.form.get('invoiceValidityDate').value, 'dd-MM-yyyy', 'en-US', '+0530') : this.form.get('invoiceValidityDate').value;
            this.tableItems.push({
              invoiceValue: this.form.get('invoiceValue').value,
              invoiceNumber: this.form.get('invoiceNumber').value.toUpperCase(),
              invoiceDate: invDate,
              invoiceType: this.invoiceTyp,
              invoiceValidityDate: invValidityDate,
              currency: this.form.get('currency').value,
              base64Invoice: this.form.get('path').value,
              paymentLogs: null,
              path: null,
              paidStatus: null,
              invoiceTypeLabel: this.form.get('invoiceType').value,
              collections: null,
              fileName: this.fileName,
              invoiceTypeTxt: this.invoiceTyp.invoiceTypeName,
              encrInvoiceNumber: null,
              status: null,
              disablecheck: false,
              createdBy: null,
              statusTxt: null,
              refNumber: null,
              download: false
            });
            this.returnInvoiceTable.refreshTable(this.tableItems);
            // this.tableInvItems.push(this.tableItems as BolInvoice)
            /*  this.tableItems.push(this.addTableData(this.form.get('invoiceNumber').value,
                                     this.form.get('invoiceValue').value,
                                     this.form.get('invoiceType').value,
                                     this.form.get('invoiceValidityDate').value,
                                     this.form.get('invoiceDate').value,
                                     this.form.get('currency').value,
                                    ));*/


          }
        }
      });
    }
    this.tableItems.forEach(function (value) {
      console.log(value);
    });
    this.form.get("tableItems").setValue(this.tableItems);
    //  this.tableItems.control.set
  }
  addTableData(invoiceNumber: String, invoiceValue, invoiceType, invoiceValidityDate, invoiceDate, currency): FormGroup {

    return this.formBuilder.group({
      invoiceNumber: [invoiceNumber],
      invoiceValue: [invoiceValue],
      invoiceType: [invoiceType],
      invoiceValidityDate: [invoiceValidityDate],
      invoiceDate: [invoiceDate],
      currency: [currency],
    });

    throw new Error("Method not implemented.");
  }
  checkInvType(event) {
    console.log(event)
    console.log(this.invItems[event.event - 1].label + "--------" + event.event);
    this.invoiceTyp.invoiceTypeId = this.invItems[event.event - 1].value;
    this.invoiceTyp.invoiceTypeName = this.invItems[event.event - 1].label;
    console.log(this.invoiceTyp.invoiceTypeName)
  }
  onClear(event) {
    this.showInvPanel = false;
  }
  reset(event) {
    this._dialogService.confirm('All your changes will be lost. Do you want to proceed?')
      .subscribe(retval => {
        console.log(' Dialog Data ', retval);
        if (retval != 1) {
          return;
        }
        else {
          this.onClear(event);
          this.form.reset();
          this.tableItems = [];
        }
      });
  }

  onSubmit($event) {
    super.validateForm(this.form);

    //this.url = '/do/app/api/secure/rejectReturnDO?status=' + status + '&id=' + this.authoriseDO.doAuthRequestId + '&remarkId=' + s.selectItem, s.otherComments;
    if (this.form.controls.selectItem.valid && this.form.controls.otherComments.valid) {
      this.tableInvItems = this.tableItems;

      this._httpRequestService.postData('/do/app/api/secure/rejectReturnDO?status=' + 'RETURNED' + '&id=' + this.route.snapshot.queryParams.id + '&remarkId=' + this.form.get("selectItem").value + '&comments=' + this.form.get("otherComments").value, this.tableItems, true).subscribe((result) => {
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
      if (!this.form.controls.selectItem.valid)
        this.form.controls['selectItem'].setErrors({ 'required': true });
      if (!this.form.controls.otherComments.valid)
        this.form.controls['otherComments'].setErrors({ 'required': true });
    }
  }


  fileUpload($event) {
    console.log(this.fileUploadComponent.fileName)
    this.fileName = this.fileUploadComponent.fileName;
    console.log($event);
    if ($event != null)
      if ($event.download) {
        this.showPdf($event.id, this.fileName);
      }
    this.form.patchValue({
      path: $event,
      fileName: this.fileUploadComponent.fileName
    });

    this.chageDetectorRef.markForCheck();

  }
  ngOnInit() {
    // super.validateForm(this.form);
    this.form = this.formBuilder.group({
      selectItem: ['', Validators.required],
      otherComments: ['', Validators.required],
      invoiceNumber: [, [Validators.required]],
      invoiceDate: [, []],
      invoiceValue: [, [Validators.required]],
      path: [, []],
      showInvPanel: [false],
      currency: [Currency.AED, [Validators.required]],
      invoiceValidityDate: [, []],
      invoiceType: [, [Validators.required]],
      uploadDoc: [''],
      tableItems: [],
      isEdit: [false],
      uploadedID: [''],
      isUploaded: [false],

    });
    this.amountValidation = [{ name: 'invoiceAmount', validator: Validators.pattern(/^[.\d]+$/), message: 'Enter valid amount' }];

  }
  showPdf(base64, filename) {
    const linkSource = base64;
    const downloadLink = document.createElement("a");
    const fileName = this.form.get('invoiceNumber').value;

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }
  getMinDate() {
    return new Date();
  }
}
