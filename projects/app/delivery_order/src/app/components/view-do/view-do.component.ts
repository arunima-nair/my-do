import { Component, OnInit, ViewChild } from '@angular/core';
import { BaseComponent } from './../../lib/classes/BaseComponent';
import { AuthDetailsComponent } from '../auth-details/auth-details.component';

@Component({
  selector: 'app-view-do',
  templateUrl: './view-do.component.html',
  styleUrls: ['./view-do.component.css']
})
export class ViewDoComponent extends  BaseComponent implements OnInit {
  @ViewChild(AuthDetailsComponent) authDetailsComponent:AuthDetailsComponent;
  constructor() { 
    super();
  }

  ngOnInit() {
  }
  close($event){
    this._dialogService.confirm('All your changes will be lost. Do you want to proceed?')
    .subscribe(retval => {
      console.log(' Dialog Data ', retval);
      if (retval != 1) {
         return;
      }
      else {
    this.authDetailsComponent.close();
      }
    });
  }
}
