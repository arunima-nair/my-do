import { Component, OnInit, Input, ChangeDetectionStrategy, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';
import { ChangeDetectionService } from '../../service/change-detection.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-text-input',
  template: `
    <mat-form-field appearance="fill" [ngClass]="formElementBackGround"
            [formGroup]="parentForm">
        <mat-label>{{labelTxt}}</mat-label>
        <mat-icon  *ngIf="showIcon" matPrefix>{{prefixIcon}}</mat-icon>
        <input matInput [placeholder]="placeHolder" class="my-form-field"
          [formControlName]="fieldName" matTooltip="{{toolTip}}"
          [matTooltipPosition]="'below'"  [type]="fieldType" numbersOnly apply="{{numbersOnly}}" autocomplete="off" 
          (change)="change($event)"  (keydown)="onKeydown($event)" #f> 
          <button mat-button *ngIf="f.value" matSuffix mat-icon-button aria-label="Clear" [ngClass]="'clear-button'"
          (click)="clear(f,$event)" tabindex=-1>
          <mat-icon [ngClass]="'label-close-icon'" data-attr="close">close</mat-icon>
  </button>
 
          <ng-container *ngFor="let validation of validations"
             ngProjectAs="mat-error" >
            <mat-error *ngIf="parentForm.get(fieldName).hasError(validation.name)">
                {{validation.message}}</mat-error>
          </ng-container>
    </mat-form-field>`,
  styles: [`
  
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TextInputComponent extends BaseFormControlComponent implements OnInit, OnDestroy {



  constructor(
    private _cD: ChangeDetectorRef

  ) {
    super();
    this._cDref = _cD;
  }

  @Input() numbersOnly: boolean;


  getNumbersOnly() {
    return this.numbersOnly;
  }
  ngOnInit() {
    if (this.numbersOnly) {
      this.numbersOnly = false;
    }
    // super.setValidations();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
