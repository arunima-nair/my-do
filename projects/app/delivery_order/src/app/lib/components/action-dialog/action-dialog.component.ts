import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-action-dialog',
  templateUrl: './action-dialog.component.html',
  styleUrls: ['./action-dialog.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ActionDialogComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
