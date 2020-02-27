import { Component, OnInit, Inject, ChangeDetectionStrategy } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Constants } from 'src/app/lib/classes/Constants';


@Component({
  selector: 'app-alert',
  template: `
  <app-dialog [title]="title" [ok]="ok" [cancel]="cancel" [okTxt]="okTxt" [cancelTxt]="cancelTxt" (clickEvent)="close($event)" 
    [dialogType]="getDialogType()">  {{message}}</app-dialog>
  
  `,
  styles: [`
  
  `],
  changeDetection: ChangeDetectionStrategy.OnPush

})
export class AlertComponent implements OnInit {

  cancel: Boolean = false;
  ok: Boolean = false;
  message: string;
  title: string;
  okTxt: string;
  cancelTxt: string;


  constructor(private _fb: FormBuilder,
    private _dialogRef: MatDialogRef<AlertComponent>,
    @Inject(MAT_DIALOG_DATA) data) {
    console.log(data);
    this.ok = data.ok;
    this.cancel = data.cancel;
    this.message = data.message;
    this.title = data.title;
    this.okTxt = data.okTxt;
    this.cancelTxt = data.cancelTxt;
  }

  ngOnInit() {

  }

  close(val) {
    this._dialogRef.close(val);
  }

  getDialogType() {
    if (this.ok && this.cancel) {
      return Constants.INFO_BOX;
    } else {
      return Constants.ALERT_BOX;
    }
  }

}
