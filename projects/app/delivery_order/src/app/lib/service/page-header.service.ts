import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PageHeaderService {

  private header = new BehaviorSubject<any>([]);
 
  constructor() {
  }
 updateHeader(head: any) {
   this.header.next(head);
   
 }

  getHeader(): Observable<any> {
    return this.header.asObservable();
 }  
 
  clearHeader() {
    this.header.next([]);
  }
}
