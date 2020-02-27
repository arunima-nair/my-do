import { Injectable } from '@angular/core';
import { Router, CanActivate, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';

import { Observable } from 'rxjs';
import { BreadcrumbService } from '../service/breadcrumb.service';

@Injectable({
  providedIn: 'root'
})
export class MasterGuard implements CanActivateChild {
  
  constructor(public router: Router,
              private _breadCrumbService: BreadcrumbService) { }



  canActivateChild(childRoute: ActivatedRouteSnapshot, 
      state: RouterStateSnapshot): Observable<boolean | UrlTree> | 
      Promise<boolean | UrlTree> | boolean | UrlTree {
        console.log(' can activate child ');
        
        this._breadCrumbService.updateCrumb({name: childRoute.data[0].key, url: ''});
        return true;
      }

}
