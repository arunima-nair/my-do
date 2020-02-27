import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ButtonStateService {

  private buttonSubject = new BehaviorSubject<any>([]);

  constructor() {
    console.log(' IN NOTFN ');
  }
  
 updateState(notfn: any) {
   this.buttonSubject.next(notfn);
 }

  getMessage(): Observable<any> {
    return this.buttonSubject.asObservable();
 }  
 
  clearMessage() {
    this.buttonSubject.next([]);
  }
}
