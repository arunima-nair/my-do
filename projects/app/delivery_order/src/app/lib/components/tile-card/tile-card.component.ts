import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';

@Component({
  selector: 'app-tile-card',
  templateUrl: './tile-card.component.html',
  styleUrls: ['./tile-card.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TileCardComponent extends BaseFormControlComponent implements OnInit {

  @Input() icon: string;
  @Input() description: string;
  @Input() link: string;
  @Input() subLinks: SubLinks[];
  @Input() cardDimensions: CardDimensions;
  constructor() { super(); }

  ngOnInit() {
  }


}
export interface SubLinks {
  name?: string;
  link?: string;

}

export interface CardDimensions {
  widthpx?: string;
  heightpx?: string;

}