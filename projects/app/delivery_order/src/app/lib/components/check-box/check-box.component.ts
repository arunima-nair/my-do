import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';

@Component({
  selector: 'app-check-box',
  template: `
    <div [formGroup]="parentForm">
      <label class="radio-label-padding">{{labelTxt}}</label>
      <mat-checkbox [formControlName]="fieldName" (change)="change($event)" ></mat-checkbox>
      <ng-container *ngFor="let validation of validations;"
          ngProjectAs="mat-error">
            <mat-error *ngIf="parentForm.get(fieldName).hasError(validation.name)">
                {{validation.message}}</mat-error>
        </ng-container>
    </div>
  `,
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CheckBoxComponent extends BaseFormControlComponent implements OnInit {

  constructor() { super(); }
  ngOnInit() {
    // this.parentForm = this.fb.group({
    //   chkboxes: this.buildChxBoxes()
    // });
  }

  // get chkboxes() {
  //   console.log('calling checkboxes');
  //   return this.parentForm.get('chkboxes');
  // }

  // buildChxBoxes() {
  //   const arr = this.fieldConfig.options.map(item => {
  //     return this.fb.control(item.selected);
  //   });
  //   return this.fb.array(arr);
  // }

}
