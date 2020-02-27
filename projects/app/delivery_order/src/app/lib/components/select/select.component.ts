import { Component, OnInit, Input, Output, ChangeDetectionStrategy, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';
import { ChangeDetectionService } from '../../service/change-detection.service';

@Component({
  selector: 'app-select',
  template: `
  <mat-form-field appearance="fill" [floatLabel]="'always'" [formGroup]="parentForm">
  <mat-label>{{labelTxt}}</mat-label>
  <mat-icon *ngIf="showIcon"  matPrefix>{{prefixIcon}}</mat-icon>
  <mat-select [placeholder]="placeHolder" [formControlName]="fieldName" 
    (valueChange)="change($event)"
  [multiple]="multiple">
    <mat-option *ngFor="let item of items" [value]="item.value" (click)="emitItems(item)">
      {{item.label}}
    </mat-option>
    </mat-select>
    <ng-container *ngFor="let validation of validations;" ngProjectAs="mat-error" >
        <mat-error *ngIf="parentForm.get(fieldName).hasError(validation.name)">
            {{validation.message}}
        </mat-error>
    </ng-container>
  </mat-form-field>
  `,
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelectComponent extends BaseFormControlComponent implements OnInit {

  @Input() items: any[];
  @Input() multiple: Boolean = false;
  @Output() selectedItems: any;

  constructor(private _cdRef: ChangeDetectorRef) {
    super();
    this._cDref = _cdRef;

  }

  ngOnInit() {

  }


  emitItems(item) {
    const actveItems = [];
    const selectedValues = super.getFieldValue();

    for (let i = 0; i < this.items.length; i++) {
      for (let j = 0; j < selectedValues.length; j++) {
        if (selectedValues[j] === this.items[i].value) {
          actveItems.push(this.items[i]);
        }
      }
    }

    super.change(actveItems);
  }

  public refreshItems(newItems: any[], values: any[]) {
    this.items = newItems;
    super.getFormField().patchValue(values);
    super.markForChangeDetection(this._cdRef);
  }

}
