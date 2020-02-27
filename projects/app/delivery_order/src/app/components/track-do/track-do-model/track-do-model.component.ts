import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';

@Component({
  selector: 'app-track-do-model',
  templateUrl: './track-do-model.component.html',
  styleUrls: ['./track-do-model.component.css']
})
export class TrackDoModelComponent extends BaseComponent implements OnInit {

  event: any;
  showBtn: boolean;
  userType: string;
  constructor(private _securityInfo: SecurityInfoService, private dialogRef: MatDialogRef<TrackDoModelComponent>, @Inject(MAT_DIALOG_DATA) data) {
    super();
    this.event = data;
    console.log(data);
  }

  ngOnInit() {
    this.userType = this._securityInfo.getUserType();
    this.showBtn = this.event.dataRow.collections[0].collectionType === 'UPLOAD_PAYMENT_PROOF' ? true : false;

  }

  printReciept() {
    if (this.event.dataRow.collections != undefined && this.event.dataRow.collections.length != 0) {
      if (this.event.dataRow.collections[0].status === "SUCCESS") {
        if (this.event.dataRow.collections[0].collectionType === "ONLINE_PAYMENT") {
          window.open("/do/app/api/file/recieptInvoiceNo?invoiceNo=" + this.event.dataRow.encrInvoiceNumber + '&agentCode=' + this._securityInfo.getAgentCode() + '&agentType=' + this._securityInfo.getAgentType());
        }
        else if (this.event.dataRow.collections[0].collectionType === "CREDIT PAYMENT") {
          this._dialogService.alert('No Invoice Available for CREDIT Payment');
        }
        else if (this.event.dataRow.collections[0].collectionType === 'UPLOAD_PAYMENT_PROOF') {
          window.open("/do/app/api/file/DowloadRecipt?collectionId=" + this.event.dataRow.collections[0].encrCollectionId);
        }
      }
    }
    else {
      this._dialogService.alert('Payment Not yet done');
    }
  }

  viewInvoice() {
    if (this.userType != "IN") {
      const id: any = this.event.dataRow.encrInvoiceNumber;
      window.open("/do/app/api/file/getFile?bol=" + this.event.bolNo + "&invNo=" + id);
      //  window.open("/do/app/api/file/getFile?bol=" + (this.bol.bolNumber + "&invNo=" + id));
    } else {
      this._dialogService.alert("Not authorized to access the Invoice.");
    }
  }

  paymentProof() {
    window.open("/do/app/api/file/DowloadRecipt?collectionId=" + this.event.dataRow.collections[0].encrCollectionId);
  }

  close(event) {
    this.dialogRef.close();
  }

}
