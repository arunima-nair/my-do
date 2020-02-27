import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'collapse-panel',
  templateUrl: './collapse-panel.component.html',
  styleUrls: ['./collapse-panel.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CollapsePanelComponent implements OnInit {

  @Input() panelCount;
  @Input() panelText;
  @Input() expanded = false;
  @Input() showPanelIcon = true;
  @Input() icon: string;
  @Input() panelIcon = false;
  constructor() { }

  ngOnInit() {
  }



}
