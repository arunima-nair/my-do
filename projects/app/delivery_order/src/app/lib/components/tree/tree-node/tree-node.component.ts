import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'tree-node',
  templateUrl: './tree-node.component.html',
  styleUrls: ['./tree-node.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TreeNodeComponent implements OnInit {

  @Input() treeElements: any;
  @Input() marginLeft = 0;

  marginleftpx = 0;
  constructor() { }

  ngOnInit() {
    if (this.marginLeft === 0)
      this.marginleftpx = 10;

  }
  hasChildren(s: TreeNode) {
    if (s.children && s.children.length > 0)
      return true;
    return false;
  }

  isFirstIndex(i) {
    if (i === 1) {
      return true;

    }
    return false;
  }

}

export interface TreeNode {
  name: string;
  link?: string;
  icon?: string;
  children?: TreeNode[];
}
