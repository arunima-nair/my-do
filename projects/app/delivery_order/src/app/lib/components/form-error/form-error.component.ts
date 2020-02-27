import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';

@Component({
  selector: 'form-error',
  template: `
  <ng-container *ngFor="let validation of validations;"
             ngProjectAs="mat-error" >
            <mat-error *ngIf="parentForm.get(fieldName).hasError(validation.name)">
                {{validation.message}}</mat-error>
      </ng-container>
  `,
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormErrorComponent extends BaseFormControlComponent implements OnInit {


  constructor() { super(); }

  ngOnInit() {
  }

}
