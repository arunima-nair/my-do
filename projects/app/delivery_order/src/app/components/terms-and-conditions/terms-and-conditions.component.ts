import { Component, OnInit } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { NavigationExtras, ActivatedRoute } from '@angular/router';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-terms-and-conditions',
  templateUrl: './terms-and-conditions.component.html',
  styleUrls: ['./terms-and-conditions.component.css']
})
export class TermsAndConditionsComponent extends BaseComponent implements OnInit {

  constructor(private _securityInfo: SecurityInfoService, private route: ActivatedRoute, private dialogRef: MatDialogRef<TermsAndConditionsComponent>) {
    super();
  }
  ngOnInit() {
  }

  close(event) {
    this.dialogRef.close();
  }

}
