import { Component, OnInit, ViewChild } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { FormGroup, Validators } from '@angular/forms';
import { Validator } from 'src/app/lib/classes/field.interface';
import { formatDate } from '@angular/common';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { DOStatus, BOLStatusMapping } from 'src/app/common/enum/status';
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
  selector: 'app-consigne-report-internal',
  templateUrl: './consigne-report-internal.component.html',
  styleUrls: ['./consigne-report-internal.component.css']
})
export class ConsigneReportInternalComponent extends BaseComponent implements OnInit {
  myForm: FormGroup;
  //DO status from Enum
  public doStatus;
  searchParam: any[] = [];
  saValidation: Validator[];
  impValidation: Validator[];
  saItems: any[] = [];
  impItems: any[] = [];
  agentType: any;
  agentCode: String;
  userType: any;
  shippingAgent: String;
  importerName: string;
  importerCode: string;
  shippingAgentName: string;
  shippingAgentCode = '';
  minDate: any;

  @ViewChild('autocompletesa') autocompletesa: AutoCompleteComponent;
  @ViewChild('autocompleteimp') autocompleteimp: AutoCompleteComponent;


  constructor(private _securityInfo: SecurityInfoService) {
    super();
    this._pageHeaderService.updateHeader('Report');
    this.doStatus = BOLStatusMapping;
  }
  generateReport($event) {
    super.validateForm(this.myForm);
    if ((this.myForm.get('shippingAgentName').valid || this.myForm.get('importerName').valid) && this.myForm.get('fromDate').valid && this.myForm.get('toDate').valid) {
      var shippingName = this.myForm.get('shippingAgentName').value ? this.myForm.get('shippingAgentName').value.value : this.myForm.get('shippingAgentName').value;
      var importerName = this.myForm.get('importerName').value ? this.myForm.get('importerName').value.value : this.myForm.get('importerName').value;
      if (this.myForm.get('fromDate').value <= this.myForm.get('toDate').value) {
        window.open("/do/app/api/file/consigneeReportInternal?fromDate=" + formatDate(this.myForm.get('fromDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530') + "&toDate=" + formatDate(this.myForm.get('toDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530') + "&agentCode=" + this._securityInfo.getAgentCode() + "&agentType=" + this._securityInfo.getAgentType() + "&status=" + this.myForm.get('status').value + "&userType=" + this._securityInfo.getUserType() + "&shippingAgent=" + shippingName + "&importerName=" + importerName);
      }
      else {
        this._dialogService.alert("To Date should be greater than from date");
      }
    }
  }
  reset() {
    this.myForm.reset();
  }


  ngOnInit() {
    this.myForm = this.formBuilder.group({
      fromDate: ['', [Validators.required]],
      toDate: ['', [Validators.required]],
      status: [DOStatus.CHOOSE],
      shippingAgentName: [''],
      importerName: ['']
    });

    this.saValidation = [{ name: 'required', validator: Validators.required, message: 'Shipping Agent is Required' }];
    this.impValidation = [{ name: 'required', validator: Validators.required, message: 'Importer is Required' }];

    this.agentType = this._securityInfo.getAgentType();
    this.agentCode = this._securityInfo.getAgentCode();
    this.userType = this._securityInfo.getUserType();
  }
  onChange() {
    // console.log(this.myForm.get("fromDate").value);
    this.minDate = this.myForm.get("fromDate").value.toDate();
  }
}
