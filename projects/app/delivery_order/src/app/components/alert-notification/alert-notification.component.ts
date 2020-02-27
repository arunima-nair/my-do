import { Component, OnInit } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { FormGroup, Validators } from '@angular/forms';
import { NavigationExtras } from '@angular/router';
import { NotificationMessage, MessageStatus } from 'src/app/lib/classes/NotificationMessage';

@Component({
  selector: 'app-alert-notification',
  templateUrl: './alert-notification.component.html',
  styleUrls: ['./alert-notification.component.css']
})
export class AlertNotificationComponent extends BaseComponent implements OnInit {
  myForm: FormGroup;
  constructor() {
    super();
    this._pageHeaderService.updateHeader('Alert and Notification');
  }
  reset() {
    this.myForm.reset();
  }
  submitAlert() {
    super.validateForm(this.myForm);
    if (this.myForm.valid) {

      this._httpRequestService.postData('/do/app/api/secure/alertnotify?bol=' + this.myForm.get('bol').value.toUpperCase() + '&email=' + this.myForm.get('email').value, null, true).subscribe((result) => {
        let confirmStatus = true;
        if (result.status === 'success') {
          confirmStatus = true;
        } else {
          confirmStatus = false;;
        }
        const navextras: NavigationExtras = {
          queryParams: { 'data': result.message, 'success': confirmStatus }
        };

        this.notfnService.clearMessage();
        this._router.navigate(['/confirm'], navextras);

      });
    } else {
      const notfnMessage: NotificationMessage = {
        text: 'Please enter all mandatory details',
        type: MessageStatus.info
      };
      this.notfnService.updateMessage(notfnMessage);
      super.scrollToTop();
    }
  }
  ngOnInit() {
    this.myForm = this.formBuilder.group({
      bol: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

}
