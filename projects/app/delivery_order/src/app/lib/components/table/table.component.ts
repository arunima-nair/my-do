import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef, AfterContentInit, DoCheck, AfterViewInit, AfterViewChecked, ChangeDetectionStrategy } from '@angular/core';
import { TableDefn, TableType, TableAction, TableComponentResp } from '../../classes/TableDefn';
import { HttpRequestService } from '../../service/http-request.service';
import { DialogService } from '../../service/dialog.service';
import { PageEvent, MatPaginator } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-data-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TableComponent implements OnInit, AfterViewInit {
  @Input() displayedColumns: string[];
  @Input() columnDefns: string[];
  @Input() tableDS: any[];
  @Input() url: string;
  @Input() tableDefn: TableDefn[];
  @Input() pageSize: number;
  @Input() local: boolean;
  @Input() pagination: boolean;
  @Input() loadByDefault = false;
  @Input() checked = false;
  @Input() actionable = false;
  @Input() edit = false;
  @Input() view = false;
  @Input() download = false;
  @Input() request = false;

  @Input() viewTip: string;
  @Input() editTip: string;
  @Input() deleteTip: string;
  @Input() downloadTip: string;
  @Input() requestTip: string;
  @Input() dataGrid = true;
  @Input() delete = false;

  @Input() noRecordsMessage = 'No Data Found';
  @Input() isRowClickable = false;
  @Output() clickEvent = new EventEmitter();
  @Output() countEvent = new EventEmitter();
  @ViewChild('paginator') paginator: MatPaginator;

  @ViewChild('tableTh') thHeight: ElementRef;

  show_del_button = false;
  checkedItems = [];
  dataSource: any;
  loading = false;
  currentPage: number;
  loadedData: any[];
  firstPage: boolean;
  lastPage: boolean;
  prevClass: string;
  nextClass: string;
  pageStart: number;
  pageEnd: number;
  total: number;
  currentPageLength: number;
  error = false;
  error_txt = '';
  sort_column = 0;
  prevTarget = null;
  totalPages = 0;
  currentSortOrder: string;
  queryParams: string = '';
  pageEvent: PageEvent;

  tableId = 0;
  offsetWidth = 0;
  cellWidth = 0;
  cellWidthAction = 100;
  topHeight = 0;
  viewTxt = 'view';
  downloadTxt = 'download';
  editTxt = 'edit';
  requestTxt = '';

  // @ViewChild(MatSort) sort: MatSort;

  constructor(
    private _httpService: HttpRequestService,
    private _dialogService: DialogService,
    private _transService: TranslateService,
    private _cDref: ChangeDetectorRef
  ) {
    this.tableId = Math.floor((Math.random() * 20000) + 1);

  }


  ngAfterViewInit() {

    if (document.getElementById("" + this.tableId + "") != null && this.offsetWidth === 0) {
      let margin = 20;


      let columnSize = this.tableDefn.length;
      if (this.delete || this.view || this.edit || this.download || this.request) {
        columnSize = columnSize + 1;
      }

      let definedWidth = 0;
      for (var i = 0; i < this.tableDefn.length; i++) {
        if (!this.tableDefn[i].width)
          this.tableDefn[i].width = 0;
        else
          definedWidth = definedWidth + this.tableDefn[i].width;
      }
      this.offsetWidth = document.getElementById("" + this.tableId + "").offsetWidth - margin - definedWidth;
      this.cellWidth = Math.floor(this.offsetWidth / columnSize);



    }


    if (this.thHeight) {
      this.topHeight = (this.thHeight.nativeElement.offsetHeight - 30) / 2;
    }


    if (this.viewTip)
      this.viewTxt = this._transService.instant(this.viewTip);

    if (this.editTip)
      this.editTxt = this._transService.instant(this.editTip);

    if (this.editTip)
      this.editTxt = this._transService.instant(this.editTip);

    if (this.downloadTip)
      this.downloadTxt = this._transService.instant(this.downloadTip);
    if (this.requestTip)
      this.requestTxt = this._transService.instant(this.requestTip);

    this._cDref.markForCheck();
  }


  ngOnInit() {
    if (!this.pageSize) {
      this.pageSize = 10;
    }
    this.currentPage = 1;
    this.firstPage = true;
    if (this.local) {
      this.local = true;
    }

    // this.pagination = true;
    if (this.loadByDefault) {
      this.fetchData();
    }

  }


  isEmpty(obj) {
    for (const key in obj) {
      if (obj.hasOwnProperty(key))
        return false;
    }
    return true;
  }

  getView() { return TableAction.view; }
  getDelete() { return TableAction.delete; }
  getEdit() { return TableAction.edit; }
  getDownload() { return TableAction.download; }
  getRequest() { return TableAction.request; }

  action(tableAction: TableAction, row, i) {
    const data: TableComponentResp = { action: tableAction, dataRow: row, index: i };
    this.clickEvent.emit(data);

  }

  isClickableRow() {
    if (this.isRowClickable)
      return 'row-click-enabled';
  }

  isButtonType(obj) {
    return obj.type == TableType.button;
  }

  checkAction(obj, action) {
    for (let i = 0; i < obj.length; i++) {
      if (obj[i] == action)
        return true;
    }

    return false;
  }

  isViewButton(obj) {
    return this.checkAction(obj, 'view');
  }

  isEditButton(obj) {
    return this.checkAction(obj, 'edit');
  }

  isDeleteButton(obj) {
    return this.checkAction(obj, 'delete');
  }



  rowCLick(dataRow, event, dataIndex) {
    // const docBody = event.currentTarget.getRootNode();
    // if (docBody.body.clientWidth < 720) {
    //   if (this.view && this.edit) {
    //     const dialogOptions: DialogOptions = { disableClose: false };
    //     this._dialogService.openDialog(ActionDialogComponent, null, dialogOptions)
    //       .subscribe((data) => {
    //         console.log(data);
    //       });
    //   } else {
    //     this.tableClick(this.getTableAction(), event);
    //   }
    //   console.log(' mob Screen ');
    // }
    // console.log(event);
    const data: TableComponentResp = { action: TableAction.rowClick, dataRow: dataRow, index: dataIndex };
    // console.log(data);
    this.clickEvent.emit(data);
  }

  validRow(i) {
    const str = 'this.tableDS[' + i + ']';
    try {
      return !this.isEmpty(eval(str));
    } catch (error) {
      return false;
    }
  }
  getColumnData(i, j) {
    const str = 'this.tableDS[' + i + '].' + this.tableDefn[j].mappingName;
    try {
      return eval(str);
    } catch (error) {
      return '';
    }

  }

  getColumnWidth(i, j) {
    const str = 'this.tableDS[' + i + '].width';
    try {
      return eval(str);
    } catch (error) {
      return null;
    }

  }

  getWidth(w) {
    if (w.width) {
      return w.width;
    }
    return this.cellWidth;

  }




  getTableAction() {
    if (this.edit)
      return TableAction.edit;
    if (this.view)
      return TableAction.view;
    if (this.request)
      return TableAction.request;
  }
  getTableActionDelete() {
    return TableAction.delete;
  }

  getTableActionEdit() {
    return TableAction.edit;
  }

  getTableActionDownload() {
    return TableAction.download;
  }

  getTableActionView() {
    return TableAction.view;
  }
  getTableActionRequest() {
    return TableAction.request;
  }
  checkClick(row, event) {
    if (event.currentTarget.checked) {
      this.checkedItems.push(row);
    } else {
      this.checkedItems = this.checkedItems.filter(item => {
        return item.id !== row.id;
      });
    }
    if (this.checkedItems.length > 0) {
      this.show_del_button = true;
    } else {
      this.show_del_button = false;
    }
  }

  checkAll(event) {
  }





  goTo(event) {
    if (event.pageIndex === this.currentPage)
      this.currentPage = this.currentPage + 1;
    else
      this.currentPage = this.currentPage - 1;

    if (this.local) {
      this.loadDisplayData(this.currentPage);
      this.checkPageStates();
    } else {
      this.fetchData();
    }

  }

  loopArray(n: number): any[] {
    return Array(n);
  }

  tableClick(value, action) {
    const obj = { val: value, action: action };
    this.clickEvent.emit(obj);
  }

  sort(columnId, event) {

    if (!this.tableDS || this.tableDS.length === 0)
      return;

    this.sort_column = columnId;
    if (this.prevTarget !== null && this.prevTarget !== event.currentTarget) {
      this.prevTarget.classList.remove('table-sort-icon-clicked');
      this.prevTarget.classList.add('table-sort-icon');
    }
    this.prevTarget = event.currentTarget;
    if (event.currentTarget.classList.contains('table-sort-icon')) {
      event.currentTarget.classList.remove('table-sort-icon');
      event.currentTarget.classList.add('table-sort-icon-clicked');
    }
    if (event.currentTarget.textContent.trim() === 'arrow_drop_down') {
      this.currentSortOrder = 'DSC';
    } else {
      this.currentSortOrder = 'ASC';
    }

    this.currentPage = 1;
    if (this.local) {
      this.loadDisplayData(this.currentPage);
      this.checkPageStates();
      this.sortData();
    } else {
      this.fetchData('sort');
    }
  }

  sortData() {
    this.loadedData.sort((a, b) => {
      const obj1 = a[this.tableDefn[this.sort_column].mappingName];
      const obj2 = b[this.tableDefn[this.sort_column].mappingName];
      if (this.tableDefn[this.sort_column].type === TableType.number) {
        if (this.currentSortOrder === 'ASC') {
          return parseInt(obj2, 10) - parseInt(obj1, 10);
        } else {
          return parseInt(obj1, 10) - parseInt(obj2, 10);
        }
      } else if (this.tableDefn[this.sort_column].type === TableType.string) {
        if (obj1 < obj2) {
          if (this.currentSortOrder === 'ASC') {
            return -1;
          } else {
            return 1;
          }
        } else {
          if (this.currentSortOrder === 'ASC') {
            return 1;
          } else {
            return -1;
          }
        }
      } else if (this.tableDefn[this.sort_column].type === TableType.date) {
        if (new Date(obj1) > new Date(obj2)) {
          if (this.currentSortOrder === 'ASC') {
            return -1;
          } else {
            return 1;
          }
        } else {
          if (this.currentSortOrder === 'ASC') {
            return 1;
          } else {
            return -1;
          }
        }
      }
    });
  }

  page(incr: number) {
    if (incr < 0 && this.firstPage) {
      return;
    }
    if (this.lastPage && incr > 0) {
      return;
    }
    this.currentPage = this.currentPage + incr;
    if (this.local) {
      this.loadDisplayData(this.currentPage);
      this.checkPageStates();
    } else {
      this.fetchData();
    }
  }

  loadAllData() {
    this.tableDS = [];
    this.pageSize = this.total;
    for (let i = 0; i < this.total; i++) {
      this.tableDS.push(this.loadedData[i]);
    }
  }
  padDummy() {
    const currLength = this.tableDS.length;
    for (let i = currLength; i < this.pageSize; i++) {
      this.tableDS.push({});
    }

  }

  loadDisplayData(pageNumber: number) {
    this.tableDS = [];
    if (!this.local) {
      for (let i = 0; i < this.loadedData.length && i < this.pageSize; i++) {
        this.tableDS.push(this.loadedData[i]);
      }
    } else {
      for (
        let i = (pageNumber - 1) * this.pageSize;
        i < pageNumber * this.pageSize && i < this.total;
        i++
      ) {
        this.tableDS.push(this.loadedData[i]);
      }
    }
    // if (this.tableDS.length < this.pageSize) {
    //   console.log('Less than Page size');
    //   this.padDummy();
    // }
    // currentPageLength= this.tableDS.length;
  }

  checkPageStates() {
    if (this.currentPage === 1) {
      this.firstPage = true;
      this.prevClass = 'no-active';
    } else if (this.currentPage > 1) {
      this.firstPage = false;
      this.prevClass = 'active';
    }

    this.totalPages = this.total / this.pageSize;
    if (this.total % this.pageSize !== 0) {
      this.totalPages = this.totalPages + 1;
    }

    if (this.currentPage + 1 > this.totalPages) {
      this.lastPage = true;
      this.nextClass = 'no-active';
    } else {
      this.lastPage = false;
      this.nextClass = 'active';
    }

    this.updatePageStartAndEnd();
    this.totalPages = Math.floor(this.totalPages);
  }

  updatePageStartAndEnd() {
    if (this.loadedData && this.loadedData.length === 0) {
      return;
    }
    this.pageStart = (this.currentPage - 1) * this.pageSize + 1;
    if (this.loadedData.length < this.pageStart + this.pageSize) {
      this.pageEnd = this.loadedData.length;
    } else {
      this.pageEnd = this.pageStart + this.pageSize - 1;
    }
  }

  refreshTable(queryParams: string = '') {
    this.queryParams = queryParams;
    if (this.prevTarget !== null) {
      this.prevTarget.classList.remove('table-sort-icon-clicked');
      this.prevTarget.classList.add('table-sort-icon');
    }
    if (this.paginator)
      this.paginator.pageIndex = 0;

    this.currentPage = 1;
    this.fetchData();
  }
  /**Reset Table */
  resetTable() {
    this.tableDS = new Array();
    this._cDref.markForCheck();
  }

  fetchData(operation?) {
    this.loading = true;
    this._httpService
      .getData(
        this.url +
        '?pgSize=' +
        this.pageSize +
        '&pgNo=' +
        this.currentPage +
        '&sort_order=' +
        this.currentSortOrder +
        '&sort_col=' +
        this.sort_column +
        '&' +
        this.queryParams, true
      )
      .subscribe(
        data => {
          this.loading = false;
          this.loadedData = data.elements;
          this.total = data.total;
          if (operation === 'sort') {
            this.paginator.pageIndex = 0;
            this.currentPage = 1;
          }
          this.loadDisplayData(this.currentPage);
          const dataCount = { pageCount: this.pageSize, totalCount: data.total };
          this.countEvent.emit(dataCount);

          this.checkPageStates();
          if (this.total > 0) {
            this.updatePageStartAndEnd();
          }
          // if (this.total < this.pageSize) {
          //   this.pageSize = this.total;
          // }
          this._cDref.markForCheck();
        },
        error => {
          this.loading = false;
          this._dialogService.alert();
        }
      );
  }
}
