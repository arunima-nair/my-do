import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { SecurityInfoService } from './security-info.service';

@Injectable({
  providedIn: 'root'
})
export class LoginResolverService implements Resolve<any> {
  // resolve(route: import("@angular/router").ActivatedRouteSnapshot, state: import("@angular/router").RouterStateSnapshot) {
  //   throw new Error("Method not implemented.");
  // }

  async resolve() {
    console.log('***************************STARTED********************* ');
    const loginSuccessFul:any =  await this._securityInfo.login('/do/app/api/public/getToken');
    if (!loginSuccessFul) {
        throw new Error('Unable to Login');
    }
    console.log('***************************REsolved********************* ');
  }

  constructor(private _securityInfo: SecurityInfoService) { }
}
