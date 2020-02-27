import { Component, OnInit, Output, EventEmitter, ChangeDetectorRef, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { Constants } from 'src/app/lib/classes/Constants';
@Component({
  selector: 'app-upload-do',
  templateUrl: './upload-do.component.html',
  styleUrls: ['./upload-do.component.css']
})
export class UploadDoComponent extends BaseComponent implements OnInit {
  form: FormGroup;
  uploadDo: string;
  checkOBL: Boolean = true;
  bolType: string;
  fileSize: string;
  @Output() clickEvent = new EventEmitter();
  constructor(private dialogRef: MatDialogRef<UploadDoComponent>, @Inject(MAT_DIALOG_DATA) data, private _cd: ChangeDetectorRef) {
    super();
    this.chageDetectorRef = _cd;
    console.log(data);
    if (data.status === 'PENDING_DO') {
      this.checkOBL = false;
    }
    this.bolType = data.isOBL;
    this.fileSize = data.fileSize;
  }
  clicked(event) {

    console.log(event);
    console.log(this.form.value);

    if (parseInt(event) === Constants.OK_CLICK) {
      if (this.form.valid) {
        if (this.bolType === "OBL") {
          if (this.checkOBL === true) {
            if (this.form.controls.oblCopy.value === "") {
              this._dialogService.alert("Select either OBL physical copy received or Convert to EBL");
            }

            else {
              this.save();
            }

          } else {
            //this._dialogService.alert("Select if OBL physical copy received.");
            this.save();
          }
        }
        else {
          this.save();
        }
      }
    } else {
      this.close();

    }
  }
  Upload($event) {
    this.form.patchValue({
      uploadDoc: $event,
      // oblCopy:this.form.get("oblCopy").value
    });
    this.chageDetectorRef.markForCheck();
    this.clickEvent.emit($event);
  }

  save() {
    if (this.form.controls.uploadDoc.value != null)
      this.dialogRef.close(this.form.value);
    else
      this._dialogService.alert("Upload DO and continue.");
  }

  close() {
    this.dialogRef.close();
  }
  ngOnInit() {

    this.form = this.formBuilder.group({
      oblCopy: [''],
      uploadDoc: [],
      checkOBL: true,
      bolType: [],
      fileSize: ['0.5']

    });

  }
}
