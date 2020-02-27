import { NgModule } from '@angular/core';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import {
  MatButtonModule, MatIconModule, MatCardModule,
  MatFormFieldModule, MatInputModule, MatListModule, MatDatepickerModule,
  MatNativeDateModule, MatSelectModule, MatOptionModule, MatCheckboxModule,
  MatRadioModule, MatAutocompleteModule, MatSlideToggleModule, MatMenuModule,
  MatProgressSpinnerModule, MatSliderModule, MatTableModule, MatDialogModule, MatPaginatorModule,
  MatProgressBarModule, MatSidenavModule, MatTooltipModule, MatExpansionModule, MatBadgeModule, MatTreeModule
} from '@angular/material';
import { MatToolbarModule } from '@angular/material/toolbar';
import { LayoutModule } from '@angular/cdk/layout';


@NgModule({
  declarations: [],
  imports: [
    MatButtonModule, MatIconModule, MatCardModule, MatExpansionModule, MatBadgeModule,
    MatFormFieldModule, MatInputModule, MatListModule, MatSidenavModule,
    MatDatepickerModule, MatNativeDateModule, MatMomentDateModule, LayoutModule, MatMenuModule,
    MatSelectModule, MatOptionModule, MatCheckboxModule, MatRadioModule, MatToolbarModule,
    MatAutocompleteModule, MatSlideToggleModule, MatSliderModule, MatTableModule, MatDialogModule,
    MatProgressSpinnerModule, MatPaginatorModule, MatProgressBarModule, MatTooltipModule, MatTreeModule

  ],
  exports: [
    MatButtonModule, MatSidenavModule, LayoutModule, MatBadgeModule,
    MatIconModule, MatCardModule, MatFormFieldModule, MatInputModule, MatExpansionModule,
    MatListModule, MatDatepickerModule, MatNativeDateModule, MatMomentDateModule, MatMenuModule,
    MatSelectModule, MatOptionModule, MatCheckboxModule, MatRadioModule, MatToolbarModule,
    MatAutocompleteModule, MatSlideToggleModule, MatSliderModule, MatTableModule, MatDialogModule,
    MatProgressSpinnerModule, MatPaginatorModule, MatProgressBarModule, MatTooltipModule, MatTreeModule

  ]
})
export class MaterialModule { }
