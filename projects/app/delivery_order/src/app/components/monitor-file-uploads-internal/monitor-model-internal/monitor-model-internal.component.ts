import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';

@Component({
  selector: 'app-monitor-model-internal',
  templateUrl: './monitor-model-internal.component.html',
  styleUrls: ['./monitor-model-internal.component.css']
})
export class MonitorModelInternalComponent extends BaseComponent implements OnInit {
  event: any;
  constructor(private dialogRef: MatDialogRef<MonitorModelInternalComponent>, @Inject(MAT_DIALOG_DATA) data) {
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
    window.open('/do/app/api/file/fileLogReport?fileLogId=' + this.event[1].val.fileLogIdEncr
      + '&shippingAgentName=' + this.event[0].shippingAgentCode
      + '&referenceNumber=' + this.event[1].val.referenceNumber
      + '&uploadType=' + this.event[1].val.uploadType
      + '&fromDate=' + this.event[1].val.convertedDate);
    this.dialogRef.close();
  }

  close(event) {
    this.dialogRef.close();
  }

}
