import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { map, catchError, retry, tap, finalize } from 'rxjs/operators';
import { ProgressLoaderService } from './progress-loader.service';




@Injectable({
  providedIn: 'root'
})
export class HttpRequestService {

  constructor(private http: HttpClient,
    private progressLoaderService: ProgressLoaderService) { }

  private urlData = '/assets/json/country.json';

  private toggleLoader(showLoader) {
    // console.log(showLoader);
    if (showLoader) {
      this.showLoader();
    } else {
      this.hideLoader();
    }
  }

  public async getDataSync(url, showLoader = false): Promise<any> {
    this.showLoader();
    const data = await this.http.get(url).toPromise();
    // console.log('In Async COmplete');
    this.hideLoader();
    return data;
  }

  public getData(url, showLoader = false): Observable<any> {
    this.toggleLoader(showLoader);
    return this.http.get(url)
      .pipe(
        tap(() => this.toggleLoader(false)),
        catchError(this.errorHandler)
      );
  }

  errorHandler(error: HttpErrorResponse) {
    // console.log(' ERROR handler Toggle Loader');
    // this.hideLoader();
    return throwError(error);
  }

  public postData(url: string, data: any, showLoader = false): Observable<any> {
    this.toggleLoader(showLoader);
    // console.log('IN POST ' + data);
    return this.http.post(url, data)
      .pipe(
        //retry(3),
        tap(() => this.hideLoader()),
        catchError(this.errorHandler),
        finalize(() => this.hideLoader())
      );
  }

  private showLoader(): void {
    this.progressLoaderService.show();
  }
  private hideLoader(): void {
    this.progressLoaderService.hide();
  }
}
