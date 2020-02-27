import { Injectable } from '@angular/core';
import {  HttpInterceptor, HttpHandler, HttpRequest, HttpHeaders} from '@angular/common/http';
import { SecurityInfoService } from './security-info.service';
import { TranslateService } from '@ngx-translate/core';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

   constructor(private securityInfoService: SecurityInfoService,
               private _ts: TranslateService) {

   }
    intercept(request: HttpRequest<any>, next: HttpHandler) {
        const headerToken = this.securityInfoService.getToken();
        //const headerToken = 'djfdjhfsjhfjsh';
      
        // console.log(this.translatorService);
        const headers = new HttpHeaders( {
            'Authorization': 'Bearer ' + headerToken,
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        });
            request = request.clone({
            headers:  headers,
            setParams : {
              locale: this._ts.currentLang
            }
          });

        return next.handle(request);
    }
}
