import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';

@Component({
  selector: 'static-field',
  templateUrl: './static-field.component.html',
  styleUrls: ['./static-field.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StaticFieldComponent extends BaseFormControlComponent implements OnInit {

  constructor() { super(); }
  @Input() value: string;
  @Input() small: boolean;
  @Input() large: boolean;
  @Input() hyperLink: boolean;
  @Input() hyperLinkId: string;
  @Output() dataEmitter = new EventEmitter();

  ngOnInit() {
    if (!this.small && !this.large)
      this.small = true;

    if (this.hyperLink) {
      this.small = false;
      this.large = false;
    }

    if (!this.value) {
      this.value = ' ';
    }

  }


  updateLabelValue(lv) {
    this.value = lv;
  }

  clicked(event) {
    console.log('clicked');
    const obj = { id: this.hyperLinkId };
    this.dataEmitter.emit(obj);
  }

}
