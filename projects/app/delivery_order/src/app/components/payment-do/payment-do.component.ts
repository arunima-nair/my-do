import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { BaseComponent } from '../../lib/classes/BaseComponent';
import { AuthDetailsComponent } from '../auth-details/auth-details.component';
import { PaymentProofUploadComponent } from '../payment-proof-upload/payment-proof-upload.component';
import { NavigationExtras, ActivatedRoute } from '@angular/router';
import { TermsAndConditionsComponent } from '../terms-and-conditions/terms-and-conditions.component';
import { DialogOptions } from 'src/app/lib/service/dialog.service';
class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}

@Component({
  selector: 'app-payment-do',
  templateUrl: './payment-do.component.html',
  styleUrls: ['./payment-do.component.css']
})
export class PaymentDoComponent extends BaseComponent implements OnInit {

  @ViewChild(AuthDetailsComponent) authDetailsComponent: AuthDetailsComponent;
  myForm: FormGroup;
  formBuilder: FormBuilder;
  authId: string;
  authStatus: string;
  bolInvoiceValue: string;
  payOptionItems: any[] = new Array();
  uploadProofDoc: string;
  pendingPayment: number;
  paidInvoice: any[] = new Array();
  payProofShow: boolean = false;
  payUploadProofMaxFileSizeMb: string;
  constructor(private _fb: FormBuilder, private route: ActivatedRoute) {
    super();
    this._pageHeaderService.updateHeader('Pending Payment');

  }
  ngAfterViewInit() {
    this.getChildProperty();
  }
  getChildProperty() {

    this.bolInvoiceValue = this.authDetailsComponent.authoriseDO.bol.bolInvoice[0].invoiceValue;
    this.authId = this.authDetailsComponent.authoriseDO.doAuthRequestIdEncr;
    this.authStatus = this.authDetailsComponent.authoriseDO.status;
    console.log(this.authDetailsComponent.payingAmt);
    this.payUploadProofMaxFileSizeMb = this.authDetailsComponent.payUploadProofMaxFileSizeMb;
    if (this.authStatus === 'PAYMENT_FAILED') {

      this._httpRequestService.getData('/do/app/api/secure/getPaymentOption?agentCode=' + this.authDetailsComponent.authoriseDO.bol.bolDetails.shippingAgentCode, true).subscribe((res) => {
        console.log(res);
        res.forEach(element => {
          console.log(element);
          this.payOptionItems.push(element);
        });

      });
    } else {
      const item = new Item('0', 'ONLINE PAYMENT');
      this.payOptionItems.push(item);
    }
    this.pendingPayment = this.authDetailsComponent.totalAmt - this.authDetailsComponent.paidAmt;
    console.log(this.authDetailsComponent.paidInvoice);
    this.paidInvoice = this.authDetailsComponent.paidInvoice;

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
            queryParams: { 'bolNo': this.authDetailsComponent.authoriseDO.bol.bolNumber }
          };
          if (this.route.snapshot.queryParams.parent != undefined)
            this._router.navigate([this.route.snapshot.queryParams.parent], navextras);
          else
            window.history.back();
          // this.authDetailsComponent.close();
        }
      });
  }
  pay() {
    console.log(this.myForm.get('payOption').value);
    var payOpt = this.myForm.get('payOption').value;
    if (payOpt != -1) {
      if (payOpt == 2) {
        this.uploadProofPayment();
      }
      else
        this.authDetailsComponent.pay(payOpt, null);

    } else {
      this._dialogService.alert('Select Payment option and continue.')
    }

  }

  openTC(): any {
    const dialogOptions: DialogOptions = { disableClose: false, maxHeight: '90vh' };
    this._dialogService.openDialog(TermsAndConditionsComponent, { title: 'Terms And Conditions' }, dialogOptions).subscribe(value => {
      console.log(value);
    });
  }
  uploadProofPayment() {

    this._httpRequestService.postData('/do/app/api/secure/paymentDOProof?bolNo=' + this.authDetailsComponent.authoriseDO.bol.bolNumber, this.myForm.controls['uploadProofDoc'].value, true).subscribe((data) => {
      console.log(data);

      if (data.message != null) {

        let confirmStatus = true;
        if (data.status === 'success') {
          confirmStatus = true;
        } else {
          confirmStatus = false;;
        }
        const navextras: NavigationExtras = {
          queryParams: { 'data': data.message, 'success': confirmStatus }
        };
        this.notfnService.clearMessage();
        this._router.navigate(['/confirm'], navextras);

      }
      this.myForm.reset();
    });
  }

  onChangePay($event) {
    if ($event.event[0] != undefined) {
      if ($event.event[0].value != undefined) {

        if ($event.event[0].value == 2) {
          // this._dialogService.alert('DO will be released upon payment realization.Please ensure that payments are only made to the Bank Account Details provided in the invoice.')
          //   .subscribe(isAllow => {
          //     if (isAllow != 1) {
          //       return;
          //     } else {
          //       this.payProofShow = true;
          //     }
          //   });
          this.payProofShow = true;
        }

        else {
          this.payProofShow = false;


        }
      }
    }
  }
  /* uploadProof() {
     this._dialogService.openDialog(PaymentProofUploadComponent, null, null).subscribe((s) => {
       console.log(s);
       this.myForm.patchValue({
         uploadProof: s.uploadDoc
       });
       console.log(s);
       if (s != undefined) {
         if (s.uploadDoc === null)
           this._dialogService.alert("Please Upload Payment Proof and continue!!");
         else
           this.uploadProofDoc = s.uploadDoc;
       }
       else
         this._dialogService.alert("Please Upload Payment Proof and continue!!");
     });
   }*/
  uploadProof($event) {
    this.payProofShow = true;
    if ($event === null) {
      this.myForm.controls['uploadProofDoc'].setValue("");
    } else {
      if ($event.download) {
        this.authDetailsComponent.viewFile($event.id);
      }
      this.myForm.patchValue({
        uploadProofDoc: $event
      });
      this.chageDetectorRef.markForCheck();

    }
  }
  reInitiatePay() {
    console.log(this.authDetailsComponent.payingInvoice);
    if (this.myForm.get('payOption').value != undefined)
      if (this.myForm.get('payOption').value != 0) {
        this.pay();
      } else
        this._httpRequestService.postData('/do/app/api/secure/saveReinitiatePaymentApp?bolNo=' + this.authDetailsComponent.authoriseDO.bol.bolNumber.toUpperCase() + '&payingAmt=' + this.authDetailsComponent.payingAmt, null, true).subscribe((data) => {
          console.log('request success' + data.message);
          if (data) {
            // window.open(result.message, "_blank");
            const dataVal: any = {
              serviceOwnerID: data.serviceOwnerId, serviceID: data.rosoomServiceId, serviceChannel: data.rosoomChannel, licenseKey: data.rosoomLicenseKey, customerReferenceNumber: data.custRefNo,
              serviceDescription: data.serviceDesc, responseURL: data.responseUrl, serviceCost: data.totalAmount, soTransactionID: data.soTransactionId, documentationCharges: data.documentCharges, signature: data.base64signatureString,
              popup: data.popup, invoiceNo: data.invoiceNo, buEncryptionMode: data.buEncryptionMode
            };
            super.postToExternalSite(dataVal, data.gatewayUrl);
          } else {
            const navextras: NavigationExtras = {
              queryParams: { 'data': data.message }
            };
            this.notfnService.clearMessage();
            this._router.navigate(['/confirm'], navextras);
          }
        });
  }
  ngOnInit() {
    this.myForm = this._fb.group({
      payOption: ['0'],
      uploadProofDoc: [''],
      pendingPayment: [''],
      paidInvoice: [],
      payProofShow: [false],
    });
  }
}
