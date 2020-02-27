import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class LangTranslateService {

  loaded = false;
  count = 0;
  constructor(private _translateService: TranslateService) {
    _translateService.setDefaultLang('en');
    _translateService.use('en');
    this._translateService.get('dummy').subscribe(() => {
      this.loaded = true;
      console.log(' LOADED TOTAL ');
    });
  }

  getLocaleValue(key) {
    if (key)
      return this._translateService.instant(key);
    else
      return key;
  }



  public loadLangPack(): Promise<any> {

    return this._translateService.get('dummy').toPromise();

  }

}
