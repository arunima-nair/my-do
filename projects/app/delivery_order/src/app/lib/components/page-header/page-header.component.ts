import { Component, OnInit, Input, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { PageHeaderService } from '../../service/page-header.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'page-header',
  templateUrl: './page-header.component.html',
  styleUrls: ['./page-header.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PageHeaderComponent implements OnInit {

  headerName: string;
  subscription: Subscription;

  constructor(private _pageHeaderService: PageHeaderService, private _cdRef: ChangeDetectorRef) {
    this.subscription = this._pageHeaderService.getHeader()
      .subscribe((msg: any) => {
        this.headerName = msg;
        this._cdRef.markForCheck();
      });


  }

  ngOnInit() {
  }

  getHeaderName() {
    console.log('header ' + this.headerName);
    return this.headerName;
  }

}
