import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RequestBlComponent } from '../request-bl/request-bl.component';
import { Constants } from 'src/app/lib/classes/Constants';
import { Validator } from './../../lib/classes/field.interface';
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
  selector: 'app-request-invoice',
  templateUrl: './request-invoice.component.html',
  styleUrls: ['./request-invoice.component.css']
})
export class RequestInvoiceComponent extends BaseComponent implements OnInit {
  myform: FormGroup;
  invoiceNumber: any[] = [];
  shippingCode: string;
  items: any[] = [];
  selectedInvoice: string;
  purpose: string;
  reqEmail: string;
  @ViewChild('autocompleteagent') autocompleteagent: AutoCompleteComponent;
  constructor(private dialogRef: MatDialogRef<RequestBlComponent>, @Inject(MAT_DIALOG_DATA) data) {
    super();
    this._pageHeaderService.updateHeader('Request Invoice');
    data.invoiceNo.forEach(element => {
      const item = new Item(element.label, element.value);
      this.invoiceNumber.push(item);
    });
    this.shippingCode = data.shippingAgentCode;
    this.purpose = data.purpose;
    this.getAgentEmail(this.shippingCode.split('-')[0]).subscribe((result) => {
      if (result.length > 0) {
        this.reqEmail = result[0].value.trim();
        //   var lastCharSemicolon = this.reqEmail.substr(this.reqEmail.length - 1);
        //   lastCharSemicolon === ';' ? this.reqEmail = this.reqEmail : this.reqEmail = this.reqEmail + ';';
        this.myform.get('reqEmail').setValue(this.reqEmail);
      }
    });
  }
  clicked(event) {
    if (this.selectedInvoice == undefined) {

    } else
      if (this.selectedInvoice.length == 0)
        this.selectedInvoice = this.myform.controls['invNo'].value;
    super.validateForm(this.myform);

    if (parseInt(event) === Constants.OK_CLICK) {
      if (this.selectedInvoice === undefined || this.selectedInvoice.length == 0) {
        this._dialogService.alert('Please select invoice and continue.');
        return;
      }

      if (this.myform.valid) {
        let str = this.myform.controls['shippingAgentName'].value;
        if (str.label != undefined)
          str = str.label;
        var strSplit = str.split('-')[0];
        this.getAgentEmail(strSplit).subscribe((result) => {
          if (result.length > 0) {
            this.dialogRef.close(this.myform.value);
            this.save();
          }
          else
            this._dialogService.alert("Agent Email not found.");
        });

      }
    }
    else {
      this.close();
    }
  }

  getAgentEmail(agentCode) {
    return this._httpRequestService.getData('/do/app/api/secure/getAgentEmailByAgentCode?agentCode=' + agentCode, true);
  }
  save() {
    this.dialogRef.close(this.myform.value);
  }

  close() {
    this.dialogRef.close();
  }
  ngOnInit() {
    var emailPattern = "[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}{;}";
    this.myform = this.formBuilder.group({
      invNo: [this.invoiceNumber, [Validators.required]],
      shippingAgentName: [this.shippingCode, [Validators.required]],
      reqEmail: [this.reqEmail, [Validators.required/*, Validators.pattern(emailPattern)*/]],
      selectedInvoice: ['', []]
    });
  }
  ngAfterViewInit() {
    let strbl = this.shippingCode;
    var strSplit = strbl.split('-')[0];
    const item = new Item(strSplit, strbl);
    this.items.push(item);
    console.log(this.items);
    this.autocompleteagent.refreshItems(this.items, item);
  }
  onChangeInv(event) {
    console.log(event)
    this.selectedInvoice = event.event;
    this.myform.controls['selectedInvoice'].setValue(this.selectedInvoice);
  }
}

