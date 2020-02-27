import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Constants } from 'src/app/lib/classes/Constants';
import { BTN_ClICK } from '../button/button.component';

@Component({
  selector: 'app-dialog',
  template: `
<div [class]="css">
  <p class="heading">{{title}}</p>
  <span class="close-icon" (click)="cancelClicked()">
    <mat-icon class="white-background no-border" >close</mat-icon>
  </span>
</div>
<div class="dialog-content" [ngClass]="getScrollable()"  [style.max-height.px]="getMaxHeight()" >
  <ng-content></ng-content>
</div>
<ng-container *ngIf="actionButtons">
  <div  class="dialog-bottom button-holder">
    <app-button *ngIf="ok" [buttonType]="'button'"  [buttonName]="getokTxt()" (clickEvent)="okClicked()" [icon]="'send'"></app-button>
    <app-button *ngIf="cancel" [buttonType]="'button'"  [buttonName]="getCancelTxt()" (clickEvent)="cancelClicked()" [icon]="'clear'"></app-button>
  </div>
</ng-container>
  `,
  styleUrls: ['./dialog.component.css']

})
export class DialogComponent implements OnInit {

  @Input() title: string;
  @Input() dialogType: number;
  @Output() clickEvent = new EventEmitter();
  @Input() ok: boolean;
  @Input() cancel: boolean;
  @Input() okTxt: string;
  @Input() cancelTxt: string;
  @Input() scrollable = false;
  @Input() maxHeight;
  @Input() actionButtons = true;
  css: string;
  constructor() { }

  getScrollable() {
    if (this.scrollable)
      return 'scrollable';
  }

  getMaxHeight() {
    if (this.getScrollable())
      return this.maxHeight;
  }

  getokTxt() {
    if (!this.okTxt)
      return 'Ok';
    return this.okTxt;
  }

  getCancelTxt() {
    if (!this.cancelTxt)
      return 'Cancel';
    return this.cancelTxt;
  }

  ngOnInit() {
    if (!this.ok)
      this.ok = true;

    console.log(this.dialogType);
    if (this.dialogType === Constants.ALERT_BOX) {
      this.css = 'dialog-header alert';
    } else {
      this.css = 'dialog-header classic';
    }

  }

  okClicked() {
    this.click(BTN_ClICK.OK);
  }

  cancelClicked() {
    this.click(BTN_ClICK.CANCEL);
  }

  click(val) {
    this.clickEvent.emit(val);
  }

}


