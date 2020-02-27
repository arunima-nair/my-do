import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectorRef, ChangeDetectionStrategy, AfterViewChecked, AfterViewInit } from '@angular/core';
import { TableDefn, TableAction, TableComponentResp } from '../../classes/TableDefn';

@Component({
  selector: 'app-normal-table',
  template: `
  
  <div class="table-wrapper" id="{{tableId}}">
    <table class="dt-table"  [ngClass]="scrollableClass">
    
            <thead [ngClass]="scrollableHeadClass">
                    <tr class="table-warning">
                    <ng-container *ngIf="checkBoxEnabled">
                      <th class="check-box-col" [style.width.px]="40"></th>
                    </ng-container>
                      <th *ngFor="let tb of tableDefn; let i=index" [style.width.px]="cellWidth">
                        <span> {{tableDefn[i].displayName}} </span>
                      </th>
                    <ng-container *ngIf="view || edit || delete || download">
                        <th class="action-button-col" [style.width.px]="cellWidth"></th>
                    </ng-container>
                  </tr>
            </thead>
            <tbody [ngClass]="scrollableBodyClass">
                <tr *ngFor="let tb of loopArray(tableDS.length); let i=index;" 
                   [ngClass]="clickRow">
                
                    <ng-container *ngIf="checkBoxEnabled">
                        <td [style.width.px]="40">
                          <mat-checkbox *ngIf="tableDS[i].disablecheck" (change)="checkedItem($event,tableDS[i],i)" ></mat-checkbox>
                          <div *ngIf="!tableDS[i].disablecheck" >
                          <i class="material-icons" style="padding-left: 6px;">
                  check_box
                </i>&nbsp;&nbsp;
                      </div>
                          </td>
                    </ng-container>
                    <td *ngFor="let dfn of tableDefn; let j=index" [style.width.px]="cellWidth" >
                      {{tableDS[i][tableDefn[j].mappingName]}}
                    </td>
                    <ng-container *ngIf="view || edit || delete || download">
                          <td [style.width.px]="cellWidth">
                            <ng-container *ngIf="delete">
                              <span>
                                  <mat-icon matTooltip="Delete" [matTooltipPosition]="'above'" class="no-border" 
                                  (click)="action(getDelete(),tableDS[i])"
                                  [color]="'warn'">delete</mat-icon>
                              </span>
                            </ng-container>
                            <ng-container *ngIf="edit">
                              <span>
                                <mat-icon class="no-border" matTooltip="Edit" [matTooltipPosition]="'above'"
                                (click)="action(getEdit(),tableDS[i],i)">create</mat-icon>
                              </span>
                            </ng-container>
                            <ng-container *ngIf="view">
                                <span>
                                    <mat-icon class="no-border" matTooltip="View" [matTooltipPosition]="'above'"
                                    (click)="action(getView(),tableDS[i],i)">view_headline</mat-icon>
                                </span>
                            </ng-container>
                            <ng-container *ngIf="download">
                            <span>
                                <mat-icon class="no-border" matTooltip="Download" [matTooltipPosition]="'above'"
                                (click)="action(getDownload(),tableDS[i],i)">save_alt</mat-icon>
                            </span>
                        </ng-container>
                        </td>
                   </ng-container>
                </tr>
            </tbody>
            
            <ng-container *ngIf="!tableDS || tableDS.length ===0">
                  <tfoot>
                      <tr>
                        <td class="table-footer" colspan="100%">
                              <div class="no-records-found"> {{noRecordsMessage}} </div>
                        </td>
                      </tr>
                  </tfoot>
            </ng-container>
    </table>
  </div>
  `,
  styleUrls: ['./table.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NormalTableComponent implements OnInit, AfterViewInit {

  @Input() displayedColumns: string[];
  @Input() columnDefns: string[];
  @Input() tableDS: any[] = [];
  @Input() url: string;
  @Input() tableDefn: TableDefn[];
  @Input() view = false;
  @Input() delete = false;
  @Input() edit = false;
  @Input() download = false;
  @Output() clickEvent = new EventEmitter();
  @Input() checkBoxEnabled = false;
  @Input() isScrollableTable = false;
  @Input() isRowClickable = false;
  @Input() noRecordsMessage = 'No Data Found';
  @Input() disablecheck = false;

  tableId = 0;
  offsetWidth = 0;
  cellWidth = 0;
  checkBoxColWidth = 40;
  scrollableClass = '';
  scrollableHeadClass = '';
  scrollableBodyClass = '';
  clickRow = '';
  constructor(private _changeDetector: ChangeDetectorRef) {
    this.tableId = Math.floor((Math.random() * 20000) + 1)
  }

  ngOnInit() {
    if (this.isScrollableTable) {
      this.scrollableClass = 'table-scroll';
      this.scrollableHeadClass = 'thead-scroll';
      this.scrollableBodyClass = 'tbody-scroll'
    }

    if (this.isRowClickable)
      this.clickRow = 'row-click-enabled';

  }

  ngAfterViewInit() {
    if (document.getElementById("" + this.tableId + "") != null && this.offsetWidth === 0) {
      let margin = 20;

      if (this.checkBoxEnabled) {
        margin = margin + this.checkBoxColWidth;
      }
      let columnSize = this.tableDefn.length;
      if (this.delete || this.view || this.edit || this.download) {
        columnSize = columnSize + 1;
      }


      this.offsetWidth = document.getElementById("" + this.tableId + "").offsetWidth - margin;
      this.cellWidth = Math.floor(this.offsetWidth / columnSize);
      this._changeDetector.markForCheck();
    }

  }

  isClickableRow() {
    if (this.isRowClickable)
      return 'row-click-enabled';
  }

  rowClick(dataRow, dataIndex) {
    const data: TableComponentResp = { action: TableAction.rowClick, dataRow: dataRow, index: dataIndex };
    this.clickEvent.emit(data);
  }

  getView() {
    return TableAction.view;
  }

  getDownload() {
    return TableAction.download;
  }
  getDelete() {
    return TableAction.delete;
  }
  getEdit() {
    return TableAction.edit;
  }

  getId() {
    if (this.tableId == 0)
      this.tableId = Math.floor((Math.random() * 20000) + 1);

    return this.tableId;
  }



  checkedItem($event, row, i) {
    if ($event.checked)
      this.action(TableAction.rowCheck, row, i);
    else
      this.action(TableAction.rowUnCheck, row, i);
  }
  action(tableAction: TableAction, row, i) {
    const data: TableComponentResp = { action: tableAction, dataRow: row, index: i };
    this.clickEvent.emit(data);

  }


  refreshTable(tableDS) {
    this.tableDS = tableDS;
    this._changeDetector.markForCheck();
  }

  addItems(tablElem) {
    this.tableDS.push(tablElem);
    this._changeDetector.markForCheck();

  }



  removeItem(tableElem) {
    this.tableDS.splice(tableElem, 1);
    this._changeDetector.markForCheck();

  }
  loopArray(n: number): any[] {
    return Array(n);
  }

}


