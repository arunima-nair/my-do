import { Component, OnInit, Inject } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { FormGroup, Validators } from '@angular/forms';
import { Constants } from 'src/app/lib/classes/Constants';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-request-bl',
  templateUrl: './request-bl.component.html',
  styleUrls: ['./request-bl.component.css']
})
export class RequestBlComponent extends BaseComponent implements OnInit {
  myform: FormGroup;
  bolNo: string;
  constructor(private dialogRef: MatDialogRef<RequestBlComponent>, @Inject(MAT_DIALOG_DATA) data) {
    super();
    this._pageHeaderService.updateHeader('Request BOL');
    console.log(data);
    this.bolNo = data.bolNo;
  }
  clicked(event) {

    console.log(event);
    console.log(this.myform.value);
    if (parseInt(event) === Constants.OK_CLICK) {

      let str = this.myform.controls['shippingAgentName'].value;
      console.log(str);
      if (str.label != undefined)
        str = str.label;

      var strSplit = str.split('-')[0].toUpperCase();
      this._httpRequestService.getData('/do/app/api/secure/getAgentEmailByAgentCode?agentCode=' + strSplit, true).subscribe((result) => {
        console.log(result);
        if (result.length > 0) {
          this.dialogRef.close(this.myform.value);
          this.save();
        }
        else
          this._dialogService.alert("Agent Email not found.");
        //  this.myForm.controls['bolEmail'].setValue(result[0].label);
        // this.save();
      });

    } else {
      this.close();
    }

  }
  save() {
    this.dialogRef.close(this.myform.value);
  }

  close() {
    this.dialogRef.close();
  }
  ngOnInit() {
    this.myform = this.formBuilder.group({
      bol: [this.bolNo, [Validators.required]],
      shippingAgentName: ['', []]

    });
  }

}
