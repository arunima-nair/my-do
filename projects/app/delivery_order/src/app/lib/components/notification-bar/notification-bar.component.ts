import { Component, OnInit, OnDestroy, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { MessageNotificationService } from 'src/app/lib/service/message-notification.service';
import { Subscription } from 'rxjs';
import { NotificationMessage, MessageStatus } from 'src/app/lib/classes/NotificationMessage';

@Component({
  selector: 'notification-bar',
  template: `
  <div [class]="cssClass">
     <div class="alert-icon">
        <strong><mat-icon>{{icon}}</mat-icon></strong>
      </div>
      <div class="alert-message">
          <ul>
            <ng-container *ngFor="let m of messages">
                  <li>{{m.text}}</li>
            </ng-container>
          </ul>
     </div>
     <div class="close" >
       <strong><mat-icon (click)="close()">close</mat-icon></strong>
    </div>
    </div>
  `,
  styles: [`
  .notfn-bar {
    width:100%;
    display: table;
    padding:0px;
    transition: all 2s ease;
    box-shadow:0 -1px 1px rgba(0,0,0,.06), 0 2px 4px rgba(0,0,0,.06), 0 1px 2px rgba(0,0,0,.12);
   }
 
   .notfn-bar .alert-icon,  .notfn-bar .alert-message, .notfn-bar .close {
     display : table-cell;
     vertical-align :middle;
   }
   .notfn-bar .alert-icon {
     width:60px;
     padding:20px;
     background-color:rgba(97, 117, 80, 0.14);
   }

  .notfn-bar .mat-icon {
     font-size:20px;
   }
   .notfn-bar .close {
     width: 60px;  
     padding:20px;
   }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NotificationBarComponent implements OnInit, OnDestroy {

  message: NotificationMessage;
  cssClass = 'no-display';
  icon: string;
  messages: NotificationMessage[];
  subscription: Subscription;
  constructor(private _notfnService: MessageNotificationService, private _cdref: ChangeDetectorRef) {
    this.subscription = this._notfnService.getMessage()
      .subscribe((msgs: any) => {
        this.messages = [];
        if (msgs.length && msgs.length > 0) {

          this.message = msgs[0];
          this.messages = msgs.slice();
        } else {
          this.messages.push(msgs);
          this.message = msgs;
        }


        if (this.message.type === MessageStatus.success) {
          this.cssClass = 'alert alert-success notfn-bar';
          this.icon = 'done';
        } else if (this.message.type === MessageStatus.info) {
          this.cssClass = 'alert alert-info notfn-bar';
          this.icon = 'info';
        } else if (this.message.type === MessageStatus.error) {
          this.cssClass = 'alert alert-danger notfn-bar';
          this.icon = 'warning';
        } else if (this.message.type === MessageStatus.warning) {
          this.cssClass = 'alert alert-warning notfn-bar';
          this.icon = 'priority_high';
        } else {
          this.cssClass = 'no-display';
        }
        this._cdref.markForCheck();
      });
  }

  ngOnInit() {
  }

  close() {
    this.cssClass = 'no-display';

  }

  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.subscription.unsubscribe();
  }

}
