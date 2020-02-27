import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchDoComponent } from './components/search-bl/search-bl.component';
import { AmendBlComponent } from './components/amend-bl/amend-bl.component';
import { UploadBlComponent } from './components/upload-bl/upload-bl.component';
import { DoSearchComponent } from './components/do-search/do-search.component';
import { ApproveDoComponent } from './components/approve-do/approve-do.component';
import { ConfirmPageComponent } from './components/confirm-page/confirm-page.component';
import { MasterGuard } from './lib/guards/master-guard.service';
import { AuthoriseDOComponent } from './components/authorise-do/authorise-do.component';
import { LoginResolverService } from './lib/service/login-resolver.service';
import { ErrorComponent } from './components/error/error.component';
import { PaymentDoComponent } from './components/payment-do/payment-do.component';
import { IndexDoComponent } from './components/index-do/index-do.component';
import { ViewBlComponent } from './components/view-bl/view-bl.component';
import { SearchApproveComponent } from './components/approve-do/search-approve/search-approve.component';
import { TrackDoComponent } from './components/track-do/track-do.component';
import { ConsignereportDoComponent } from './components/consignereport-do/consignereport-do.component';
import { AlertNotificationComponent } from './components/alert-notification/alert-notification.component';
import { SearchpaymentComponent } from './components/searchpayment/searchpayment.component';
import { ViewDoComponent } from './components/view-do/view-do.component';
import { MonitorFileUploadsComponent } from './components/monitor-file-uploads/monitor-file-uploads.component';
import { HomeNewComponent } from './components/home-new/home-new.component';
import { ReturnDoComponent } from './components/return-do/return-do.component';
import { MonitorFileUploadsInternalComponent } from './components/monitor-file-uploads-internal/monitor-file-uploads-internal.component';
import { SearchBolComponent } from './components/search-bol/search-bol.component';
import { DoSearchInternalComponent } from './components/do-search-internal/do-search-internal.component';
import { TrackDoInternalComponent } from './components/track-do-internal/track-do-internal.component';
import { ConsigneReportInternalComponent } from './components/consigne-report-internal/consigne-report-internal.component';
import { RequestDoComponent } from './components/request-do/request-do.component';
import { ReqSearchInternalComponent } from './components/req-search-internal/req-search-internal.component';

const routes: Routes = [
  {
    path: '', canActivateChild: [MasterGuard], children: [
      { path: 'home', component: HomeNewComponent, data: [{ key: 'xyz' }] },
      { path: 'confirm', component: ConfirmPageComponent, data: [{ key: 'Confirm' }] },
      { path: 'authDO', component: AuthoriseDOComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Authorize Delivery Order' }] },
      { path: 'search', component: SearchDoComponent, resolve: { items: LoginResolverService }, data: [{ key: ' BOL Details' }] },
      { path: 'searchBOL', component: SearchBolComponent, resolve: { items: LoginResolverService }, data: [{ key: ' BOL Details' }] },
      { path: 'searchdo', component: DoSearchComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Search Delivery Order' }] },
      { path: 'amendBL', component: AmendBlComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Amend BOL Details' }] },
      // { path: 'login', component: AboutComponent, resolve: {items: LoginResolverService} ,  data: [{key: '1100'}] },
      { path: 'error', component: ErrorComponent, data: [{ key: '1100' }] },
      { path: 'uploadBL', component: UploadBlComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Upload BOL Details' }] },
      { path: 'approveDO', component: ApproveDoComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Approve Delivery Order' }] },
      { path: 'paymentDO', component: PaymentDoComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Online Payment Delivery Order' }] },
      { path: 'homeDO', component: IndexDoComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Amend BOL Details' }] },
      { path: 'viewBL', component: ViewBlComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Track Bill of Ladding' }] },
      { path: 'searchAppr', component: SearchApproveComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Approve Delivery Order' }] },
      { path: 'trackDO', component: TrackDoComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Track Delivery Order' }] },
      { path: 'consigneReport', component: ConsignereportDoComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Report' }] },
      { path: 'alertnotify', component: AlertNotificationComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Alert and Notification' }] },
      { path: 'searchPayment', component: SearchpaymentComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Track Delivery Order' }] },
      { path: 'amendDO', component: AuthoriseDOComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Amend Delivery Order' }] },
      { path: 'viewDO', component: ViewDoComponent, resolve: { items: LoginResolverService }, data: [{ key: 'View Delivery Order' }] },
      { path: 'monitor', component: MonitorFileUploadsComponent, resolve: { items: LoginResolverService }, data: [{ key: ' Monitor File Uploads' }] },
      { path: 'returndo', component: ReturnDoComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Return DO' }] },
      { path: 'requestDO', component: RequestDoComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Authorize Delivery Order' }] },
      /***********************************************FOR INTERNAL USERS*************************************************************************************** */
      { path: 'searchBOLInternal', component: SearchBolComponent, resolve: { items: LoginResolverService }, data: [{ key: ' BOL Details' }] },
      { path: 'MonitorInternal', component: MonitorFileUploadsInternalComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Monitor File Uploads' }] },
      { path: 'searchdoInternal', component: DoSearchInternalComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Search Delivery Order' }] },
      { path: 'trackDOInternal', component: TrackDoInternalComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Search Delivery Order' }] },
      { path: 'consigneReportInternal', component: ConsigneReportInternalComponent, resolve: { items: LoginResolverService }, data: [{ key: 'Report' }] },
      { path: 'reqsearchInternal', component: ReqSearchInternalComponent, resolve: { items: LoginResolverService }, data: [{ key: '  Bol/Invoice Request Details' }] },

    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
