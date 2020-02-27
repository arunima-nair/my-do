import { LoggingService } from '../service/logging.service';
import { Component, ChangeDetectorRef } from '@angular/core';
import { AppInjector } from './AppInjector';
import { HttpRequestService } from '../service/http-request.service';
import { DialogService } from '../service/dialog.service';
import { MessageNotificationService } from '../service/message-notification.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { BTN_ClICK } from '../components/button/button.component';
import { PageHeaderService } from '../service/page-header.service';
import { LangTranslateService } from '../service/lang-translate.service';
import { ChangeDetectionService } from '../service/change-detection.service';


@Component({
    template: '',
    styles: ['']
})
export class BaseComponent {
    loggingSerivce: LoggingService;
    _httpRequestService: HttpRequestService;
    _dialogService: DialogService;
    _translatorService: LangTranslateService;
    notfnService: MessageNotificationService;
    chageDetectorRef: ChangeDetectorRef;
    _pageHeaderService: PageHeaderService;
    _router: Router;
    formBuilder: FormBuilder;
    _cdService: ChangeDetectionService;

    constructor() {
        const injector = AppInjector.getInjector();
        this.loggingSerivce = injector.get(LoggingService);
        this._httpRequestService = injector.get(HttpRequestService);
        this._dialogService = injector.get(DialogService);
        this.notfnService = injector.get(MessageNotificationService);
        this._router = injector.get(Router);
        this.formBuilder = injector.get(FormBuilder);
        this._translatorService = injector.get(LangTranslateService);
        this._pageHeaderService = injector.get(PageHeaderService);
        this._cdService = injector.get(ChangeDetectionService);
    }
    logMessage(log) {
        this.loggingSerivce.log(log);
    }

    postData(url, data): Observable<any> {
        return this._httpRequestService.postData(url, data, true);
    }
    scrollToTop() {
        window.scrollTo(0, 0);

    }

    validateForm(fg: FormGroup): void {
        Object.keys(fg.controls).forEach((key: string) => {
            const abstractControl = fg.get(key);
            if (abstractControl instanceof FormGroup) {
                this.validateForm(abstractControl);
            } else {
                if (abstractControl) {
                    abstractControl.markAsDirty();
                    abstractControl.markAsTouched();
                    this._cdService.clearMessage();
                    this._cdService.updateMessage('Reset Change');
                }
            }
        });
    }

    resetForm(fg: FormGroup) {
        this._dialogService.confirm('Are you sure you want to reset the Form ?')
            .subscribe((message) => {
                if (BTN_ClICK.OK === message) {
                    fg.reset();
                }
            });
    }

    postToExternalSite(dataToPost: any, url: any): void {
        const form = window.document.createElement('form');
        form.setAttribute('method', 'post');
        form.setAttribute('action', url);
        const dataArr = Object.keys(dataToPost);
        for (let i = 0; i < dataArr.length; i++) {
            form.appendChild(this.createHiddenElement(dataArr[i], dataToPost[dataArr[i]]));
        }
        window.document.body.appendChild(form);
        form.submit();
    }

    createHiddenElement(name: string, value: string): HTMLInputElement {
        const hiddenField = document.createElement('input');
        hiddenField.setAttribute('name', name);
        hiddenField.setAttribute('value', value);
        hiddenField.setAttribute('type', 'hidden');
        return hiddenField;
    }
}
