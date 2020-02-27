import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-card',
  template: `

  <mat-card [ngClass]="'custom-card'">
     <div *ngIf="header" class="custom-card-header">
      <div class="custom-card-header-title">
         <span class="custom-card-header-title-txt">{{title}} </span>
      </div>
        <ng-container *ngIf="collapsable">
           <span class="{{expansionClass}}" (click)="toggle()"></span>
        </ng-container>
     </div>
    <mat-card-content [ngClass]="bodyClass">
       <ng-content></ng-content>
    </mat-card-content>
  </mat-card>
  `,
  styles: [`
 .custom-card {
    padding:0px;
    margin-top:10px;
  }

 .custom-card .custom-card-header-title {
    padding-left:1.0rem;
    padding-right:1.0rem;
    padding-top:1.0rem;
    padding-bottom:1.0rem;
    border-bottom:1px solid #eaeded;
    background-color:#fafafa;
    font-size:1.25rem;
    font-weigth:550;
    color:rgb(22, 25, 31);
  }
  .custom-card .custom-card-content {
    padding:2rem;
    }

  .custom-card .custom-card-content-collapse {
    padding:0rem;
    height:0px;
    visibility:hidden;
  }

  .custom-card .expansion-indicator:after{
    border-style: solid;
    border-width: 0 2px 2px 0;
    content: '';
    display: inline-block;
    padding: 3px;
    transform: rotate(45deg);
    vertical-align: middle;
    cursor:pointer;
  }

  span.expansion-indicator {
    position: absolute;
    right: 2em;
    top: 1em;
  }

  .transform-45 {
    
  }
  .transform-225{
    transform: rotate(180deg);
  }

  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CardComponent implements OnInit {

  @Input() header = false;
  @Input() title: String;
  @Input() collapsable = false;

  bodyClass = 'custom-card-content';
  bodyCollapsed = false;
  expansionClass = 'expansion-indicator';
  constructor() { }

  ngOnInit() {
  }

  toggle() {

    if (this.bodyCollapsed === true) {
      this.bodyCollapsed = false;
      this.bodyClass = 'custom-card-content';
      this.expansionClass = 'expansion-indicator';
    }
    else {
      this.bodyCollapsed = true;
      this.bodyClass = 'custom-card-content-collapse';
      this.expansionClass = 'expansion-indicator transform-225';
    }

  }

}
