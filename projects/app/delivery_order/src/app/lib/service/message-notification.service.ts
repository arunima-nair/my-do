import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { NotificationMessage } from '../classes/NotificationMessage';

@Injectable({
  providedIn: 'root'
})
export class MessageNotificationService {

 private notfnSubject = new BehaviorSubject<any>([]);
 
  constructor() {
    console.log(' IN NOTFN ');
  }
 updateMessage(notfn: any) {
   this.notfnSubject.next(notfn);
   
 }

  getMessage(): Observable<any> {
    return this.notfnSubject.asObservable();
 }  
 
  clearMessage() {
    this.notfnSubject.next([]);
  }
}
