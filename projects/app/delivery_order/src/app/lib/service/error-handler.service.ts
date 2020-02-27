import { Injectable , ErrorHandler, Injector} from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService implements ErrorHandler {


  handleError(error: Error | HttpErrorResponse): void {

    if (error instanceof HttpErrorResponse) {
      throwError(`Server Error : ${error.message}`);
    } else {
      console.log(' Error  ' + error.stack);
      const router = this._injector.get(Router);
      // window.location.href = '/error';
      // router.navigate(['/error'], { queryParams: {error: error} });
    }
  }

  constructor(private _injector: Injector) { }
 
}
