import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProgressLoaderService {
  private loaderSubject = new Subject<LoaderState>();
  loaderState = this.loaderSubject.asObservable();

    show() {
      this.loaderSubject.next(<LoaderState>{show: true});
  }
  hide() {
    this.loaderSubject.next(<LoaderState>{show: false});
  }
  constructor() { }
}


export interface LoaderState {
  show: boolean;
}
