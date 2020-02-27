import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthDetailsComponent } from '../auth-details/auth-details.component';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { UploadDoComponent } from '../upload-do/upload-do.component';
import { DialogOptions } from 'src/app/lib/service/dialog.service';
import { RequestNewDOComponent } from '../request-new-do/request-new-do.component';

@Component({
  selector: 'app-track-do',
  templateUrl: './track-do.component.html',
  styleUrls: ['./track-do.component.css']
})
export class TrackDoComponent extends BaseComponent implements OnInit {
  ngOnInit() {

    this.agentType = this._securityInfo.getAgentType();
    this.userType = this._securityInfo.getUserType();
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
  authEncId: any;
  paidInvoice: any[] = new Array();
  url: string;

  constructor(private _fb: FormBuilder, private _securityInfo: SecurityInfoService, private route: ActivatedRoute) {
    super();
  }
  ngAfterViewInit() {
    this.getChildProperty();
  }
  getChildProperty() {

    //this.authId = this.authDetailsComponent.authoriseDO.doAuthRequestIdEncr;
    this.authEncId = this.authDetailsComponent.authoriseDO.doAuthRequestIdEncr;
    this.authStatus = this.authDetailsComponent.authoriseDO.status;
    this.payStatus = this.authDetailsComponent.payStatus;
    this.paidBy = this.authDetailsComponent.paidBy;

  }
  onRejectReturnCancel(status) {
    this.authDetailsComponent.onRejectReturnCancel(status);
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
  amendDo() {

    this._httpRequestService.postData('/do/app/api/secure/getApproverViewedStatus?authIdEncr=' + this.authDetailsComponent.authoriseDO.doAuthRequestIdEncr, false).subscribe((res) => {
      console.log(res);


      if (res != undefined && res != null) {
        if (this.authDetailsComponent.authoriseDO.status === 'PENDING_FOR_APPROVAL' && res.approvedViewed === 'Y') {
          this._dialogService.alert('Your Application is under review. ');
        } else {
          const navextras: NavigationExtras = {
            queryParams: { 'bolNo': this.route.snapshot.queryParams.bolNo, 'invoiceNo': this.authDetailsComponent.authoriseDO.bol.bolInvoice[0].invoiceNumber, "shippingLine": this.authDetailsComponent.authoriseDO.bol.bolDetails.shippingAgentName, "amend": true, "bolEncr": this.route.snapshot.queryParams.bolEncr, "doRefNo": this.authDetailsComponent.authoriseDO.doRefNo }
          };

          this._router.navigate(['/requestDO'], navextras);
        }
      }
    });
  }

  doReUpload(event) {
    this._dialogService.confirm('Existing DO will be overwritten by new DO. Do you want to proceed?')
      .subscribe(isOK => {
        if (isOK != 1) {
          return;
        }
        else {
          this.doUpload(event);
        }
      });
  }


  reqNewDo(event) {
    const dialogOptions: DialogOptions = { disableClose: false };
    const data = {
      shippingAgentName: this.authDetailsComponent.authoriseDO.bol.bolDetails.shippingAgentName,
      shippingAgentCode: this.authDetailsComponent.authoriseDO.bol.bolDetails.shippingAgentCode
    };

    this._dialogService.openDialog(RequestNewDOComponent, data, dialogOptions).subscribe((result) => {
      this.url = '/do/app/api/secure/requestNewDO?id=' + this.authDetailsComponent.authoriseDO.bol.encBolNumber + '&shippingagentCode=' + result.shippingAgentCode + '&type=BOL_REQUEST' + '&reqEmail=' + result.reqEmail;
      this._httpRequestService.postData(this.url, null, true).subscribe((result) => {
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
      }, (error) => {
        this.notfnService.clearMessage();
        this._dialogService.alert('Unable to Notify');

      });
    });
  }



  doUpload($event) {
    const dialogOptions: DialogOptions = { disableClose: false };
    const data = {
      isOBL: this.authDetailsComponent.authoriseDO.bol.bolType,
      status: 'PENDING_DO'
    };
    this._dialogService.openDialog(UploadDoComponent, data, dialogOptions).subscribe((result) => {
      // console.log(result);
      this.myForm.patchValue({
        uploadDo: result
      });
      if (result.uploadDoc != undefined) {
        console.log(result);
        if (result.uploadDoc != null) {
          this._httpRequestService.postData('/do/app/api/secure/uploadDO?id=' + this.authDetailsComponent.authoriseDO.doAuthRequestIdEncr, result, true).subscribe((result) => {
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
          }, (error) => {
            this.notfnService.clearMessage();
            this._dialogService.alert('Unable to save data');

          });
        } else {
          this._dialogService.alert('Upload DO and proceed.');

        }
      }
      else {
        this._dialogService.alert('Upload DO and proceed.');

      }
    });
  }
  printReciept() {
    if (this.authDetailsComponent.authoriseDO.collections != undefined) {
      console.log(this.authDetailsComponent.authoriseDO.collections[0].transactionId);
      if (this.authDetailsComponent.authoriseDO.collections[0].transactionId != undefined)
        window.open("/do/app/api/file/recieptTransId?transactionId=" + this.authDetailsComponent.authoriseDO.collections[0].encrTransactionId + '&agentCode=' + this._securityInfo.getAgentCode() + '&agentType=' + this.agentType);
    }
  }

}
