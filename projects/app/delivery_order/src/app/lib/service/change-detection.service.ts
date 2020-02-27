import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChangeDetectionService {

  private chgSub = new BehaviorSubject<any>([]);

  constructor() {

  }
  updateMessage(msg: any) {
    this.chgSub.next(msg);

  }

  getMessage(): Observable<any> {
    return this.chgSub.asObservable();
  }

  clearMessage() {
    this.chgSub.next([]);
  }
}
