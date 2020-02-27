import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthDetailsComponent } from '../auth-details/auth-details.component';
import { BaseComponent } from './../../lib/classes/BaseComponent';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
@Component({
  selector: 'app-approve-do',
  templateUrl: './approve-do.component.html',
  styleUrls: ['./approve-do.component.css']
})
export class ApproveDoComponent extends BaseComponent implements OnInit {

  @ViewChild(AuthDetailsComponent) authDetailsComponent: AuthDetailsComponent;
  authId: string;
  authStatus: string;
  showButton: boolean;
  userName: string;
  constructor(private route: ActivatedRoute) {
    super();

  }
  ngAfterViewInit() {
    this.getChildProperty();
  }
  getChildProperty() {
    this.showButton = false;
    this.authId = this.authDetailsComponent.authoriseDO.doAuthRequestIdEncr;
    this.authStatus = this.authDetailsComponent.authoriseDO.status;
    if (this.authId != null && this.authStatus != 'COMPLETED')
      this.showButton = true;
    if (this.authId != null && this.authStatus === 'REJECTED')
      this.showButton = false;
    this._httpRequestService.getData('/do/app/api/secure/updateApproverViewedStatus?authIdEncr=' + this.authDetailsComponent.authoriseDO.doAuthRequestIdEncr, false)
      .subscribe((result) => {
        this.userName = result.status;
      });
  }

  ngOnInit() {

  }

  close() {
    this._dialogService.confirm('All your changes will be lost. Do you want to proceed?')
      .subscribe(retval => {
        if (retval != 1) {
          return;
        }
        else {
          this.notfnService.clearMessage();
          const navextras: NavigationExtras = {
            queryParams: { 'bolNo': this.authDetailsComponent.authoriseDO.bol.bolNumber }
          };
          if (this.route.snapshot.queryParams.parent != undefined)
            this._router.navigate([this.route.snapshot.queryParams.parent], navextras);
          else
            window.history.back();
          //  this.authDetailsComponent.close();
        }
      });
  }
  upoloadDO(status, $event) {
    this.authDetailsComponent.upoloadDO(status, $event, this.userName);
  }
  onRejectReturnCancel(status) {
    this.authDetailsComponent.onRejectReturnCancel(status);
  }

}
