import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';

@Component({
  selector: 'app-monitor-model',
  templateUrl: './monitor-model.component.html',
  styleUrls: ['./monitor-model.component.css']
})
export class MonitorModelComponent extends BaseComponent implements OnInit {
  event: any;
  constructor(private dialogRef: MatDialogRef<MonitorModelComponent>, @Inject(MAT_DIALOG_DATA) data) {
    super();
    this.event = data;
  }

  ngOnInit() {
  }

  downloadRecipt(type) {
    window.open('/do/app/api/file/fileDowload/' + this.event[1].val.fileLogIdEncr);
    this.dialogRef.close();
  }

  downloadFile(type) {
    var shippingAgent = "";
    if (this.event[0].shippingAgentCode.value != null) {
      shippingAgent = this.event[0].shippingAgentCode.value;
    }
    if (this.event[0].shippingAgentCode != null) {
      shippingAgent = this.event[0].shippingAgentCode;
    }
    if (this.event[1].val.convertedDate != null) {
      var date = this.event[1].val.convertedDate.split(" ")[0];
    }
    window.open('/do/app/api/file/fileLogReport?fileLogId=' + this.event[1].val.fileLogIdEncr
      + '&shippingAgentName=' + shippingAgent
      + '&referenceNumber=' + this.event[1].val.referenceNumber
      + '&uploadType=' + this.event[1].val.uploadType
      + '&fromDate=' + date);
    this.dialogRef.close();
  }

  close(event) {
    this.dialogRef.close();
  }

}
