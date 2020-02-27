import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injector, APP_INITIALIZER } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AboutComponent } from './components/about/about.component';
import { ContactComponent } from './components/contact/contact.component';
import { HomeComponent } from './components/home/home.component';
import { HomeNewComponent } from './components/home-new/home-new.component';
import { NavComponent } from './lib/components/nav/nav.component';
import { MaterialModule } from './lib/classes/material.module';
import { TestPopUpComponent } from './test-pop-up/test-pop-up.component';
import { AppInjector } from './lib/classes/AppInjector';
import { DateFormatPipe } from './lib/pipe/DateFormatPipe';
import { ConfirmPageComponent } from './components/confirm-page/confirm-page.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { CommonLibModule } from './lib/classes/common-lib/common-lib.module';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TestDynaComponent } from './components/test-dyna/test-dyna.component';
import { DynaFormComponent } from './lib/components/dyna-form/dyna-form.component';
import { HttpRequestInterceptor } from './lib/service/http-interceptor.service';
import { AuthoriseDOComponent } from './components/authorise-do/authorise-do.component';
import { SearchDoComponent } from './components/search-bl/search-bl.component';
import { LoginResolverService } from './lib/service/login-resolver.service';
import { AmendBlComponent } from './components/amend-bl/amend-bl.component';
import { UploadBlComponent } from './components/upload-bl/upload-bl.component';
import { DoSearchComponent } from './components/do-search/do-search.component';
import { ApproveDoComponent } from './components/approve-do/approve-do.component';
import { ErrorComponent } from './components/error/error.component';
import { PaymentDoComponent } from './components/payment-do/payment-do.component';
import { IndexDoComponent } from './components/index-do/index-do.component';
import { UploadDoComponent } from './components/upload-do/upload-do.component';
import { ErrorComponent1 } from './component/error/error.component';
import { ErrorComponentComponent } from './component/error-component/error-component.component';
import { RejectDoComponent } from './components/reject-do/reject-do.component';
import { ReturnDoComponent } from './components/return-do/return-do.component';
import { AuthDetailsComponent } from './components/auth-details/auth-details.component';
import { ViewBlComponent } from './components/view-bl/view-bl.component';

import { PageHeaderService } from './lib/service/page-header.service';
import { SearchApproveComponent } from './components/approve-do/search-approve/search-approve.component';
import { TrackDoComponent } from './components/track-do/track-do.component';
import { ConsignereportDoComponent } from './components/consignereport-do/consignereport-do.component';
import { AlertNotificationComponent } from './components/alert-notification/alert-notification.component';
import { RequestBlComponent } from './components/request-bl/request-bl.component';
import { SearchpaymentComponent } from './components/searchpayment/searchpayment.component';
import { RequestInvoiceComponent } from './components/request-invoice/request-invoice.component';
import { PaymentProofUploadComponent } from './components/payment-proof-upload/payment-proof-upload.component';
import { ViewDoComponent } from './components/view-do/view-do.component';
import { TileCardComponent } from './lib/components/tile-card/tile-card.component';
import { CloseComponent } from './lib/components/close/close.component';
import { MonitorFileUploadsComponent } from './components/monitor-file-uploads/monitor-file-uploads.component';
import { MonitorModelComponent } from './components/monitor-file-uploads/monitor-model/monitor-model.component';
import { TrackDoModelComponent } from './components/track-do/track-do-model/track-do-model.component';
import { DatePipe } from '@angular/common';
import { RequestNewDOComponent } from './components/request-new-do/request-new-do.component';
import { LangTranslateService } from './lib/service/lang-translate.service';
import { MonitorFileUploadsInternalComponent } from './components/monitor-file-uploads-internal/monitor-file-uploads-internal.component';
import { MonitorModelInternalComponent } from './components/monitor-file-uploads-internal/monitor-model-internal/monitor-model-internal.component';
import { SearchBolComponent } from './components/search-bol/search-bol.component';
import { DoSearchInternalComponent } from './components/do-search-internal/do-search-internal.component';
import { TrackDoInternalComponent } from './components/track-do-internal/track-do-internal.component';
import { ConsigneReportInternalComponent } from './components/consigne-report-internal/consigne-report-internal.component';
import { RequestDoComponent } from './components/request-do/request-do.component';
import { ReqSearchInternalComponent } from './components/req-search-internal/req-search-internal.component';
import { TermsAndConditionsComponent } from './components/terms-and-conditions/terms-and-conditions.component';


@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    AboutComponent,
    ContactComponent,
    HomeComponent,
    HomeNewComponent,
    TestPopUpComponent,
    ConfirmPageComponent,
    DateFormatPipe,
    TestDynaComponent,
    DynaFormComponent,
    AuthoriseDOComponent,
    SearchDoComponent,
    ErrorComponent,
    AmendBlComponent,
    UploadBlComponent,
    DoSearchComponent,
    ApproveDoComponent,
    PaymentDoComponent,
    IndexDoComponent,
    UploadDoComponent,
    ErrorComponent1,
    ErrorComponentComponent,
    RejectDoComponent,
    ReturnDoComponent,
    AuthDetailsComponent,
    ViewBlComponent,
    SearchApproveComponent,
    TrackDoComponent,
    ConsignereportDoComponent,
    AlertNotificationComponent,
    RequestBlComponent,
    SearchpaymentComponent,
    RequestInvoiceComponent,
    PaymentProofUploadComponent,
    ViewDoComponent,
    TileCardComponent,
    CloseComponent,
    MonitorFileUploadsComponent,
    MonitorModelComponent,
    TrackDoModelComponent,
    RequestNewDOComponent,
    MonitorFileUploadsInternalComponent,
    MonitorModelInternalComponent,
    SearchBolComponent,
    DoSearchInternalComponent,
    TrackDoInternalComponent,
    ConsigneReportInternalComponent,
    RequestDoComponent,
    ReqSearchInternalComponent,
    TermsAndConditionsComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    CommonLibModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    DragDropModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: HttpRequestInterceptor,
    multi: true
  },
    /* {provide: RouteReuseStrategy,
      useClass: CustomRouteReuseStrategy
    },*/
    DatePipe,
    LoginResolverService, PageHeaderService, LangTranslateService, {
    provide: APP_INITIALIZER,
    useFactory: loadLangPack,
    deps: [LangTranslateService], // dependancy
    multi: true
  }
  ],
  bootstrap: [AppComponent],
  entryComponents: [TestPopUpComponent, AmendBlComponent, UploadDoComponent,
    RejectDoComponent, ReturnDoComponent, ViewBlComponent, RequestBlComponent, TermsAndConditionsComponent,
    RequestInvoiceComponent, PaymentProofUploadComponent, MonitorModelComponent, MonitorModelInternalComponent, TrackDoModelComponent, RequestNewDOComponent],

})
export class AppModule {
  constructor(_injector: Injector) {
    // Store module's injector in the AppInjector class
    AppInjector.setInjector(_injector);
  }
}

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}

export function loadLangPack(langTransService: LangTranslateService) {
  return () => langTransService.loadLangPack();
}

