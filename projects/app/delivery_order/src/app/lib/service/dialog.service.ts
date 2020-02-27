import { Injectable, TemplateRef } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { Observable } from 'rxjs';
import { AlertComponent } from '../components/alert/alert.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  private _dialogConfig: MatDialogConfig;

  constructor(private _mdialog: MatDialog) {
    this._dialogConfig = new MatDialogConfig();
    this._dialogConfig.disableClose = true;
    this._dialogConfig.autoFocus = true;
    this._dialogConfig.minWidth = '400px';
  }

  updateConfigProperties(dialogOptions) {
    if (!dialogOptions) return;
    Object.keys(dialogOptions).forEach((key: string) => {
      if (dialogOptions[key] !== null) {
        this._dialogConfig[key] = dialogOptions[key];
      }
    });


  }

  openDialog(component: any, dialogData: any, dialogOptions?: DialogOptions): Observable<any> {
    this._dialogConfig.data = dialogData;
    this.updateConfigProperties(dialogOptions);
    const mdDialog = this._mdialog.open(component, this._dialogConfig);
    return mdDialog.afterClosed();
  }

  alert(message = 'Unable to Fetch Data', title = 'Alert', okTxt = 'ok'): Observable<any> {
    const data = {
      id: 1,
      title: title,
      ok: true,
      cancel: false,
      okTxt: okTxt,
      message: message
    };
    this._dialogConfig.data = data;
    const mdDialog = this._mdialog.open(AlertComponent, this._dialogConfig);
    return mdDialog.afterClosed();
  }

  confirm(message = 'Please Confirm', title = 'confirm', okTxt = 'ok', cancelTxt = 'Cancel'): Observable<any> {
    const data = {
      id: 1,
      title: 'Confirm',
      ok: true,
      cancel: true,
      message: message,
      okTxt: okTxt,
      cancelTxt: cancelTxt
    };
    this._dialogConfig.data = data;
    const mdDialog = this._mdialog.open(AlertComponent, this._dialogConfig);
    return mdDialog.afterClosed();
  }

}
export class DialogOptions {
  disableClose?: boolean = true;
  autoFocus?: boolean = true;
  minWidth?: string = '400px';
  maxHeight?: string
}
