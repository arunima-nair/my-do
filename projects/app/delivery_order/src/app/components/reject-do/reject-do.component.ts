import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { BaseComponent } from '../../lib/classes/BaseComponent';
import { SelectComponent } from 'src/app/lib/components/select/select.component';
import { MatDialogRef } from '@angular/material';
import { FormGroup, Validators } from '@angular/forms';
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
  selector: 'app-reject-do',
  templateUrl: './reject-do.component.html',
  styleUrls: ['./reject-do.component.css']
})
export class RejectDoComponent extends BaseComponent implements OnInit {
  @ViewChild('#reject')
  private _cdRef: ChangeDetectorRef
  rejectSelect: SelectComponent = new SelectComponent(this._cdRef);
  items: Item[] = new Array<Item>();
  form: FormGroup;
  constructor(private dialogRef: MatDialogRef<RejectDoComponent>) {
    super();
    this._httpRequestService.getData('/do/app/api/secure/getRejectionRemarks', true).subscribe((res) => {
      console.log(res);
      res.forEach(element => {
        const item = new Item(element.rejectionRemarksId, element.remarks);
        this.items.push(item);
      });
      // this.rejectSelect.r

    });
  }
  clicked(event) {
    if (parseInt(event) === Constants.OK_CLICK) {
      super.validateForm(this.form);
      if (this.form.valid) {
        this.save();
      }
    } else {
      this.close();
    }
  }

  save() {
    this.dialogRef.close(this.form.value);
  }

  close() {
    this.dialogRef.close();
  }
  ngOnInit() {
    this.form = this.formBuilder.group({
      selectItem: ['', Validators.required],
      otherComments: ['', Validators.required]

    });
  }

}
