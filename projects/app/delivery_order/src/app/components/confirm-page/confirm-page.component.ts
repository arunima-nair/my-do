import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';

@Component({
  selector: 'app-confirm-page',
  templateUrl: './confirm-page.component.html',
  styleUrls: ['./confirm-page.component.css']
})
export class ConfirmPageComponent implements OnInit {
  myForm: FormGroup;
  queryData: any;
  transId: string;
  encrTransactionId: string;
  success: boolean = true;
  monitor: boolean = false;
  agentType: String;
  agentCode: String;
  userType: string;

  constructor(private _fb: FormBuilder, private _route: ActivatedRoute, private _securityInfo: SecurityInfoService) {
  }

  ngOnInit() {
    this.agentType = this._securityInfo.getAgentType();
    this.agentCode = this._securityInfo.getAgentCode();
    this.userType = this._securityInfo.getUserType();
    this.myForm = this._fb.group({
    });

    this.transId = this._route.snapshot.queryParams.transactionId;
    this.monitor = this._route.snapshot.queryParams.monitor;

    if (this.transId != undefined)
      this.transId === "null" ? this.transId = null : this.transId;
    this._route.queryParams.subscribe(params => {
      this.queryData = params['data'];
      if (params['success'] != "true") {
        this.success = false;
      }
      else {
        this.success = true;
      }
    });
  }
  printReciept() {
    window.open("/do/app/api/file/recieptTransId?transactionId=" + this.transId);
  }
}
