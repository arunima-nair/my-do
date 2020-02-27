import { Component, OnInit } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { FormGroup, Validators } from '@angular/forms';
import { formatDate } from '@angular/common';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { DOStatus, BOLStatusMapping } from 'src/app/common/enum/status';

class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}

@Component({
  selector: 'app-consignereport-do',
  templateUrl: './consignereport-do.component.html',
  styleUrls: ['./consignereport-do.component.css']
})
export class ConsignereportDoComponent extends BaseComponent implements OnInit {
  myForm: FormGroup;
  shippingAgentCode: string;
  importerCode: string;
  minDate: any;
  //DO status from Enum
  public doStatus;
  constructor(private _securityInfo: SecurityInfoService) {
    super();
    this._pageHeaderService.updateHeader('Report');
    this.doStatus = BOLStatusMapping;

  }
  generateReport($event) {
    super.validateForm(this.myForm);
    if (this.myForm.valid) {
      if (this.myForm.get('fromDate').value <= this.myForm.get('toDate').value) {
        //window.open("/do/app/api/file/consigneeReport?fromDate=" + formatDate(this.myForm.get('fromDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530') + "&toDate=" + formatDate(this.myForm.get('toDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530') + "&agentCode=" + this._securityInfo.getAgentCode() + "&agentType=" + this._securityInfo.getAgentType() + "&status=" + this.myForm.get('status').value + "&userType=" + this._securityInfo.getUserType());
        if (this._securityInfo.getAgentType() == 'A') {
          this.shippingAgentCode = this._securityInfo.getAgentCode();
          this.importerCode = '';
          window.open("/do/app/api/file/consigneeReportInternal?fromDate=" + formatDate(this.myForm.get('fromDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530') + "&toDate=" + formatDate(this.myForm.get('toDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530') + "&agentCode=" + this._securityInfo.getAgentCode() + "&agentType=" + this._securityInfo.getAgentType() + "&status=" + this.myForm.get('status').value + "&userType=" + this._securityInfo.getUserType() + "&shippingAgent=" + this.shippingAgentCode + "&importerName=" + this.importerCode);
        }
        else {
          this.shippingAgentCode = '';
          this.importerCode = this._securityInfo.getAgentCode();
          window.open("/do/app/api/file/consigneeReportInternal?fromDate=" + formatDate(this.myForm.get('fromDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530') + "&toDate=" + formatDate(this.myForm.get('toDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530') + "&agentCode=" + this._securityInfo.getAgentCode() + "&agentType=" + this._securityInfo.getAgentType() + "&status=" + this.myForm.get('status').value + "&userType=" + this._securityInfo.getUserType() + "&shippingAgent=" + this.shippingAgentCode + "&importerName=" + this.importerCode);
        }
      }
      else {
        this._dialogService.alert("To Date should be greater than from date");
      }
      // window.open("/do/app/api/file/consigneeReport?fromDate=" + formatDate(this.myForm.get('fromDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530') + "&toDate=" + formatDate(this.myForm.get('toDate').value._d, 'dd-MM-yyyy', 'en-US', '+0530'));
    }
  }
  reset() {
    this.myForm.reset();
  }
  ngOnInit() {
    this.myForm = this.formBuilder.group({
      fromDate: ['', [Validators.required]],
      toDate: ['', [Validators.required]],
      status: [DOStatus.CHOOSE]
    });
  }
  onChange() {
    // console.log(this.myForm.get("fromDate").value);
    this.minDate = this.myForm.get("fromDate").value.toDate();
  }
}
