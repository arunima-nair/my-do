import { Component, OnInit, OnDestroy, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ProgressLoaderService, LoaderState } from '../../service/progress-loader.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-progress-loader',
  template: `
  <div class="loader-wrapper">
    <ng-container *ngIf="show">
            <div class="spinner-div" >
                <mat-progress-bar mode="indeterminate"></mat-progress-bar>
            </div>
            <div class="loader-overlay">
            </div>
      </ng-container>
   </div>
  `,
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProgressLoaderComponent implements OnInit, OnDestroy {

  show = false;
  private subscription: Subscription;

  constructor(private progressLoader: ProgressLoaderService,
    private _cdRef: ChangeDetectorRef) { }

  ngOnInit() {
    this.subscription = this.progressLoader.loaderState
      .subscribe((state: LoaderState) => {
        this.show = state.show;
        this._cdRef.markForCheck();
      });

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();


  }

}
