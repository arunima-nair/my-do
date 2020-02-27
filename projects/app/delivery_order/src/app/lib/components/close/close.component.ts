import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-close',
  template: `
       <mat-icon [ngClass]="'label-close-icon'" data-attr="close">close</mat-icon>
  `,
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CloseComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
