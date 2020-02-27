import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BreadcrumbService {
  private breadCrumbSubject = new BehaviorSubject<any>([]);

  constructor() { }

  updateCrumb(crumb: BreadCrumb) {
    this.breadCrumbSubject.next(crumb);
  }

  getCrumb(): Observable<any> {
    return this.breadCrumbSubject.asObservable();
  }

  clearCrumb(){
    this.breadCrumbSubject.next([]);
  }
}

export interface BreadCrumb {
  name?: string;
  url?: string;
}
