import { Injectable } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NavigationHandlerService {

  private history = [];
  public storage: any;
  public searchParam: any;
  public tableData: any;
  public pageName: any;
  public url: string;
  public pageSize: number;
  public isBackButtonClicked: Boolean = false;
  public isShowButton: Boolean = false;

  constructor(private router: Router) {

  }

  public loadRouting(): void {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(({ urlAfterRedirects }: NavigationEnd) => {
        this.history = [...this.history, urlAfterRedirects];
      });
  }

  public getHistory(): string[] {
    return this.history;
  }

  public getPreviousUrl(): string {
    return this.history[this.history.length - 2] || '/home';
  }
}
