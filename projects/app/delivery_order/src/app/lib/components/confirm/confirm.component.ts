import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ConfirmComponent implements OnInit {

  @Input() header_txt: string;
  @Input() success: boolean;
  @Input() error: boolean;

  constructor() { }


  ngOnInit() {
  }

  getHeaderStyle() {
    if (this.success)
      return 'confirm-header-text color-green';
    if (this.error)
      return 'confirm-header-text color-red';
  }

  getIcon() {
    if (this.success)
      return 'check_circle';
    if (this.error)
      return 'highlight_off';
  }

  getStyle() {
    if (this.success)
      return 'success-icon color-green';
    if (this.error)
      return 'success-icon color-red';
  }

}
