import { Component, OnInit, Input, ChangeDetectionStrategy, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';
import { ChangeDetectionService } from '../../service/change-detection.service';

@Component({
  selector: 'app-textarea',
  template: `
  <mat-form-field appearance="fill" [formGroup]="parentForm">
    <mat-label>{{labelTxt}}</mat-label>
    <mat-icon *ngIf="showIcon" [ngClass]="'txtareaicon'" matPrefix>{{prefixIcon}}</mat-icon>
        <textarea matInput  [placeholder]="placeHolder"
        [formControlName]="fieldName" (change)="change($event)" #f></textarea>
        <button mat-button *ngIf="f.value" matSuffix mat-icon-button aria-label="Clear" 
        (click)="clear(f,$event)" tabindex=-1>
        <mat-icon [ngClass]="'label-close-icon'" data-attr="close">close</mat-icon>
</button>
          <ng-container *ngFor="let validation of validations;" ngProjectAs="mat-error" >
          <mat-error *ngIf="parentForm.get(fieldName).hasError(validation.name)">
              {{validation.message}}
          </mat-error>
      </ng-container>
  </mat-form-field>
  `,
  styles: [``],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TextareaComponent extends BaseFormControlComponent implements OnInit {


  @Input() numbersOnly: boolean;
  constructor(private _cD: ChangeDetectorRef,
    private _cdService: ChangeDetectionService) {
    super();
    this._cDref = _cD;
  }

  ngOnInit() {
  }


}
