import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Constants } from '../lib/classes/Constants';

@Component({
  selector: 'app-test-pop-up',
  template: `
  <app-dialog [title]="title" (clickEvent)="clicked($event)" [ok]="'true'" [cancel]="'true'">
      <div [formGroup]="form">
              <app-text-input  [parentForm]="form" [label]="'First Name'" 
                [placeHolder]="'First Name'"
                [fieldName]="'firstName'" [fieldType]="'text'" [validations]=""
                [fb]="_fb"></app-text-input>
          <app-text-input  [parentForm]="form" [label]="'Age'" 
                [placeHolder]="'Age'"
                [fieldName]="'age'" [fieldType]="'text'" [validations]=""
                [fb]="_fb"></app-text-input>
      </div>
  </app-dialog>
  `,
  styles: []
})
export class TestPopUpComponent implements OnInit {

  
  form: FormGroup;
  firstName: string;
  age: string;
  title: string;

  
  constructor(
    public _fb: FormBuilder,
    public dialogRef: MatDialogRef<TestPopUpComponent>,
    @Inject(MAT_DIALOG_DATA) data) {

    this.firstName = data.firstName;
    this.age = data.age;
    this.title = data.title;
}
  ngOnInit() {
      this.form = this._fb.group({
        firstName: [this.firstName, []],
        age: [this.age, []]
      });

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


  save() {
    this.dialogRef.close(this.form.value);
}

close() {
    this.dialogRef.close();
}

}
