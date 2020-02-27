import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';
import { MatRadioChange } from '@angular/material';
@Component({
  selector: 'app-radio',
  template: `
  <div [formGroup]="parentForm">
  <label *ngIf="isColon" class="radio-label-padding">{{label}}:</label>
     <mat-radio-group [formControlName]="fieldName">
         <mat-radio-button *ngFor="let item of options" [value]="item.value" (change)="radioChange($event)">
           {{item.label}}</mat-radio-button>
     </mat-radio-group>
 </div>
  `,
  styles: []
})
export class RadioComponent extends BaseFormControlComponent implements OnInit {

  @Input() options: any[];
  @Input() isColon: true;
  @Output() dataEmitter = new EventEmitter(); constructor() { super(); }

  ngOnInit() {

  }
  radioChange(event: MatRadioChange) {
    console.log(event.value);
    this.dataEmitter.emit(event.value)
  }

}
