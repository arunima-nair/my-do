import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { Constants } from 'src/app/lib/classes/Constants';

class Item {
  value?: string;
  label?: string;

  constructor(value: string, label: string) {
    this.label = label;
    this.value = value
  }
}

@Component({
  selector: 'app-request-new-do',
  templateUrl: './request-new-do.component.html',
  styleUrls: ['./request-new-do.component.css']
})


export class RequestNewDOComponent extends BaseComponent implements OnInit {
  myform: FormGroup;
  shippingAgentName: any;
  shippingCode: string;
  reqEmail: string;

  event: any;
  constructor(private dialogRef: MatDialogRef<RequestNewDOComponent>, @Inject(MAT_DIALOG_DATA) data) {
    super();
    this.event = data;
  }

  ngOnInit() {
    var emailPattern = "[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}";
    this.myform = this.formBuilder.group({
      shippingAgentName: [this.event.shippingAgentName, Validators.required],
      shippingAgentCode: [this.event.shippingAgentCode],
      reqEmail: ['', [Validators.required, Validators.pattern(emailPattern)]]
    });
  }

  clickFn(event) {
    if (parseInt(event) === Constants.OK_CLICK) {
      if (this.myform.valid) {
        this._httpRequestService.getData('/do/app/api/secure/getAgentEmailByAgentCode?agentCode=' + this.event.shippingAgentCode, true).subscribe((result) => {
          if (result.length > 0) {
            this.save();
          }
          else
            this._dialogService.alert("Agent Email not found.");
        });
      }
    }
    else {
      this.close();
    }
  }

  save() {
    this.dialogRef.close(this.myform.value);
  }

  close() {
    this.dialogRef.close();
  }
}
