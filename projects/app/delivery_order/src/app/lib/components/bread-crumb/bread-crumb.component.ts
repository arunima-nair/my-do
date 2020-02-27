import { Component, OnInit } from '@angular/core';
import { BreadcrumbService, BreadCrumb } from '../../service/breadcrumb.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-bread-crumb',
  template: `
    <mat-card ngClass="bread-crumb-card">
        <ul class="breadcrumb">
            <li>Home  </li>
            <li> > </li>
            <li class="active-crumb">{{getLocaleValue(crumb.name)}}</li>
        </ul>
    </mat-card>
  `,
  styles: [`
 
  .breadcrumb { 
    list-style: none; 
    overflow: hidden; 
    font-size: 14px;
    background-color: inherit;
    color: #656565;
  }
 
  .breadcrumb a {
    color: inherit;
  }
  .breadcrumb li { 
    float: left; 
    padding-right:20px;
    
  }
 
  .bread-crumb-card {
    background-color: #FFF;
    padding:1px;
}
 
li.active-crumb {
  color: #e0504b;
  font-weight: bold;
}
  
  
  `]
})
export class BreadCrumbComponent implements OnInit {

  crumb: BreadCrumb;
  constructor(private _breadCrumbService: BreadcrumbService,
    private _transalateService: TranslateService) {
    console.log(this._breadCrumbService);
    _breadCrumbService.getCrumb()
      .subscribe((crumb: BreadCrumb) => {
        this.crumb = crumb;

      });
  }

  ngOnInit() {
  }

  getLocaleValue(key) {
    if (key) {
      return this._transalateService.instant(key);
    } return key;
  }

}