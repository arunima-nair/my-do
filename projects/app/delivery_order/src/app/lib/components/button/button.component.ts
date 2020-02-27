import { Component, OnInit, Input, Output, EventEmitter, AfterViewInit, AfterContentInit, ChangeDetectionStrategy } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FieldConfig } from 'src/app/lib/classes/field.interface';
import { Subscription } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-button',
  template: `
  <ng-container *ngIf="!iconButton">
    <button [type]="buttonType" mat-raised-button [color]="color" [ngClass]="btnClass"
        [attr.mat-icon-button]="iconButton"
    (click)="clicked()" [disabled]="buttonClicked" [disabled]="!parentForm.valid">
    <mat-icon aria-label="" [ngClass]="send" 
    *ngIf="!buttonClicked">{{icon}}</mat-icon>
    <span class="spinner-border spinner-border-sm"  *ngIf="buttonClicked" ></span>
    <span *ngIf="!iconButton"> {{btnTxt}} </span>
    </button>
  </ng-container>
  <ng-container *ngIf="iconButton">
  <button mat-fab>
    <mat-icon>{{icon}}</mat-icon>
   </button>
</ng-container>
  `
  , styles: [`
  .icon-send {
    transform: rotate(320deg);
    font-size: 20px;
  }
 `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonComponent implements OnInit, AfterContentInit, AfterViewInit {
  @Input() parentForm: FormGroup;
  @Input() field: FieldConfig;
  @Input() buttonName: string;
  @Input() buttonType: string;
  @Input() color: string;
  @Input() icon: string;
  @Input() iconButton = false;
  @Output() clickEvent = new EventEmitter();
  @Input() clickCount: number;
  @Input() btnCategory: BTN_CATEGORY.PRIMARY;
  buttonClicked = false;
  subscription: Subscription;
  send: string;
  btnClass: string;

  btnTxt: string;

  clicked() {
    if (this.clickCount > 0) {
      this.buttonClicked = true;
    }
    this.clickEvent.emit(this.buttonClicked);
  }



  ngAfterContentInit() {
    this.btnTxt = this.getLocaleValue(this.buttonName);
    if (this.btnCategory === BTN_CATEGORY.PRIMARY || !this.btnCategory)
      this.btnClass = 'btn-class-primary';
    else
      this.btnClass = 'btn-class-secondary';
  }

  ngAfterViewInit() {
    this.btnTxt = this.getLocaleValue(this.buttonName);
    if (this.btnCategory === BTN_CATEGORY.PRIMARY || !this.btnCategory)
      return 'btn-class-primary';
    else
      return 'btn-class-secondary';
  }



  constructor(private _translateService: TranslateService) {
    //  console.log(' Constructor ');
  }

  ngOnInit() {
    // console.log(' in INIT ');
    if (!this.buttonClicked) {
      this.buttonClicked = false;
    }
    if (!this.color) {
      this.color = 'primary';
    }
    if (!this.buttonType) {
      this.buttonType = 'button';
    }

    if (this.icon === 'send') {
      this.send = 'icon-send';
    }

    if (!this.icon) {
      this.icon = 'send';
      this.send = 'icon-send';
    }
  }

  getLocaleValue(label) {
    if (label)
      return this._translateService.instant(label);
    else
      return label;
  }

}

export enum BTN_ClICK {
  OK = 1,
  CANCEL = 2
}

export enum BTN_CATEGORY {
  PRIMARY = 1,
  SECONDARY = 2
}
