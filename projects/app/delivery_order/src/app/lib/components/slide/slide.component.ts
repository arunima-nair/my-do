import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';

@Component({
  selector: 'app-slide',
  template: `
  <div [formGroup]="parentForm">
    <div><mat-label class="static-label">{{labelTxt}}</mat-label></div>
    <mat-slide-toggle  [formControlName]="fieldName" [checked]="checked" (change)="toggled($event)" [ngClass]="slideClass">    
       {{slideTxt}} </mat-slide-toggle>
    <ng-container *ngFor="let validation of validations;"
    ngProjectAs="mat-error" >
   <mat-error *ngIf="parentForm.get(fieldName).hasError(validation.name)">
       {{validation.message}}</mat-error>
  </ng-container>
  </div>
  `,
  styles: [`
  mat-label.static-label {
    font-size: 12px !important;
    color: rgba(0, 0, 0, 0.46);
    margin-top: -2px;
    display: block;
    margin-bottom: 13px;
}
/deep/ .slideClass-true .mat-slide-toggle-bar {
  background-color: green !important;
}
/deep/ .slideClass-true .mat-ripple-element {
  background-color: black !important;
}

/deep/ .slideClass-true .mat-slide-toggle-thumb {
  background-color: #f3f3f3 !important;
}
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SlideComponent extends BaseFormControlComponent implements OnInit {

  @Input() checked: Boolean = false;
  @Input() formText: string = '';
  @Input() trueTxt = '';
  @Input() falseTxt = '';

  slideTxt: string;
  slideClass: string;
  constructor() { super(); }

  ngOnInit() {

    this.updateFieldTxt();

  }

  updateFieldTxt() {
    if (this.getFieldValue() === true) {
      this.slideTxt = this.trueTxt;
      this.slideClass = 'slideClass-true';
    } else {
      this.slideTxt = this.falseTxt;
      this.slideClass = 'slideClass-false';
    }

    if (this.slideTxt.trim() === '') {
      this.slideTxt = this.formText;
    }
  }

  toggled($event) {

    this.updateFieldTxt();
    super.change($event);
  }

}
