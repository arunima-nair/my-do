import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'info-box',
  templateUrl: './info-box.component.html',
  styleUrls: ['./info-box.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InfoBoxComponent implements OnInit {

  @Input() boxType: number = 1;
  @Input() icon: string;
  @Input() message: string;
  @Input() value: string;
  @Input() color: string = '#F44336';
  constructor() { }

  ngOnInit() {
  }

}
