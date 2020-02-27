import { Component, OnInit, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material';
import { UploadDoComponent } from '../upload-do/upload-do.component';
import { Constants } from 'src/app/lib/classes/Constants';

@Component({
  selector: 'app-payment-proof-upload',
  templateUrl: './payment-proof-upload.component.html',
  styleUrls: ['./payment-proof-upload.component.css']
})
export class PaymentProofUploadComponent extends BaseComponent  implements OnInit{
  form: FormGroup;
  uploadproof:string;
  @Output() clickEvent = new EventEmitter();
  constructor( private dialogRef: MatDialogRef<UploadDoComponent>, private _cd: ChangeDetectorRef) {
    super();
     this.chageDetectorRef = _cd;
   }
  clicked(event) {
   
    console.log(event);
    console.log(this.form.value);
    if (parseInt(event) === Constants.OK_CLICK) {
     this.save();
    } else {
      this.close();
    }
  
}
Upload($event) {
  this.form.patchValue({
    uploadDoc: $event
 });
  this.chageDetectorRef.markForCheck();
  this.clickEvent.emit($event);
}

save() {
 this.dialogRef.close(this.form.value);
}

close() {
  this.dialogRef.close();
}
  ngOnInit() {
  
    this.form  = this.formBuilder.group({
       uploadDoc: []
  });

}
}

