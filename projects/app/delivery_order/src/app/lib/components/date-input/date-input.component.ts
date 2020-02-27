import { Component, OnInit, Input, ChangeDetectionStrategy, ChangeDetectorRef, Output, EventEmitter } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';

@Component({
  selector: 'app-date-input',
  template: `
  <mat-form-field appearance="fill" [formGroup]="parentForm">
    <mat-label>{{labelTxt}}</mat-label>
    <input matInput [matDatepicker]="picker" [formControlName]="fieldName" [min]="minDate" [max]="maxDate"
    [placeholder]="placeHolder" autocomplete="off"  (dateChange)="onBlur($event)" (focus)="picker.open()">
      <mat-icon matPrefix (click)="picker.open()" style="cursor:pointer; color:#777;">today</mat-icon>
     <mat-datepicker [panelClass]="'datePicker'" #picker></mat-datepicker>
        <ng-container *ngFor="let validation of validations;"
          ngProjectAs="mat-error">
            <mat-error *ngIf="parentForm.get(fieldName).hasError(validation.name)">
                {{validation.message}}</mat-error>
        </ng-container>
  </mat-form-field>
  `,
  styles: [`
 `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DateInputComponent extends BaseFormControlComponent implements OnInit {
  @Input() minDate: string;
  @Input() maxDate: string;
  @Output() clickEvent = new EventEmitter();
  onBlur($event) {
    // console.log(this.parentForm.get(this.fieldName).value);
    this.clickEvent.emit(this.parentForm.get(this.fieldName).value);
  }
  constructor(private _cdRef: ChangeDetectorRef) {
    super();
    this._cDref = _cdRef;
  }

  ngOnInit() {

  }

}
