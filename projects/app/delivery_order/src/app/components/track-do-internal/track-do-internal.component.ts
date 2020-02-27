import { Component, OnInit, ViewChild } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { AuthDetailsComponent } from '../auth-details/auth-details.component';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { ActivatedRoute, NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-track-do-internal',
  templateUrl: './track-do-internal.component.html',
  styleUrls: ['./track-do-internal.component.css']
})
export class TrackDoInternalComponent extends BaseComponent implements OnInit {

  ngOnInit() {


    this.myForm = this._fb.group({
      uploadDo: [''],
      pendingPayment: [''],
      paidInvoice: [],
      isPartialPay: [false]
    });
  }

  @ViewChild(AuthDetailsComponent) authDetailsComponent: AuthDetailsComponent;
  myForm: FormGroup;
  formBuilder: FormBuilder;
  authId: number;
  authStatus: string;
  agentType: string;
  agentCode: string;
  userType: string;
  payStatus: string;
  paidBy: string;
  pendingPayment: number;
  paidInvoice: any[] = new Array();
  url: string;

  constructor(private _fb: FormBuilder, private _securityInfo: SecurityInfoService, private route: ActivatedRoute) {
    super();
  }
  ngAfterViewInit() {
    this.getChildProperty();
  }
  getChildProperty() {

    this.authId = this.authDetailsComponent.authoriseDO.doAuthRequestId;
    this.authStatus = this.authDetailsComponent.authoriseDO.status;
    this.payStatus = this.authDetailsComponent.payStatus;
    this.paidBy = this.authDetailsComponent.paidBy;

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
            queryParams: { 'bolNo': this.authDetailsComponent.authoriseDO.bol.bolNumber.toUpperCase() }
          };
          if (this.route.snapshot.queryParams.parent != undefined)
            this._router.navigate([this.route.snapshot.queryParams.parent], navextras);
          else
            window.history.back();
          //   this.authDetailsComponent.close();
        }
      });
  }
  viewDOFile(id) {
    this.authDetailsComponent.viewDOFile(id);
  }




  printReciept() {
    if (this.authDetailsComponent.authoriseDO.collections != undefined) {
      console.log(this.authDetailsComponent.authoriseDO.collections[0].transactionId);
      if (this.authDetailsComponent.authoriseDO.collections[0].transactionId != undefined)
        window.open("/do/app/api/file/recieptTransId?transactionId=" + this.authDetailsComponent.authoriseDO.collections[0].encrTransactionId + '&agentCode=' + this._securityInfo.getAgentCode() + '&agentType=' + this.agentType);
    }
  }

}
