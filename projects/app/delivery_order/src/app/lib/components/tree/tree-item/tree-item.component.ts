import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'tree-item',
  templateUrl: './tree-item.component.html',
  styleUrls: ['./tree-item.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TreeItemComponent implements OnInit {

  @Input() panelCount;
  @Input() panelText;
  @Input() expanded = false;
  @Input() showPanelIcon = true;
  @Input() icon: string;
  @Input() panelIcon;
  constructor() { }

  ngOnInit() {
  }

}
