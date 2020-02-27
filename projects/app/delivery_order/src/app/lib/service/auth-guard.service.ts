import { Injectable } from '@angular/core';

import { Router, CanActivate } from '@angular/router'
import { SecurityInfoService } from './security-info.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(public router: Router) { }
  
  canActivate(): boolean {
    // this.router.navigate(['']);
    
    return true;
  }
}
