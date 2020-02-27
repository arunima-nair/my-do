import { Component, OnInit, ChangeDetectorRef, ViewChild } from '@angular/core';
import {  FormGroup, FormBuilder, Validators, FormArray  } from '@angular/forms';
import { FieldConfig, Validator } from 'src/app/lib/classes/field.interface';
import { TestPopUpComponent } from 'src/app/test-pop-up/test-pop-up.component';
import { NotificationMessage, MessageStatus } from 'src/app/lib/classes/NotificationMessage';
import { ValidateName } from './validator/home-new.validator';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { TableType, TableDefn, TableAction } from 'src/app/lib/classes/TableDefn';
import { TableComponent } from 'src/app/lib/components/table/table.component';
import { AutoCompleteComponent } from 'src/app/lib/components/auto-complete/auto-complete.component';
import { NavigationExtras } from '@angular/router';


@Component({
  selector: 'app-home-new',
  templateUrl: './home-new.component.html',
  styleUrls: ['./home-new.component.css']
})
export class HomeNewComponent extends BaseComponent  implements OnInit {
  myForm: FormGroup;
 
  firstNameValidations: Validator[];
  uploadValidations: Validator[];
  autoVals: Validator[];
  checkBoxVals: Validator[];
  checkBoxConfig: FieldConfig;
  test: string;
  clickCount = 0;
  @ViewChild(TableComponent) dataTable:  TableComponent;
  @ViewChild('ac1') autoComplete: AutoCompleteComponent;

  items: any[] = [
    {value: '0', label: 'Red'},
    {value: '1', label: 'Green'},
    {value: '2', label: 'Blue'}
  ];

  items2: any[] = [
    {value: '0', label: 'One'},
    {value: '1', label: 'Two'},
    {value: '2', label: 'Three'}
  ];

  selectItems: any[] = [
    {value: '0', label: 'egg'},
    {value: '1', label: 'Peas'},
    {value: '2', label: 'Beans'}
  ];

  tableItems: any[] =[];

  
  tableDfn: TableDefn[] = [
    { displayName: 'id', mappingName: 'id', type: TableType.number, sort: true },
    { displayName: 'Name', mappingName: 'name', type: TableType.string, sort: true },
    { displayName: 'Email', mappingName: 'email', type: TableType.string  },
    { displayName: 'Dept', mappingName: 'dept.deptName', type: TableType.string  },
    { displayName: 'Date of Birth', mappingName: 'dateOfBirth', type: TableType.string, sort: true }
  ];

  reloadDataTable(event) {
    this.dataTable.refreshTable('firName=22&lastName=dd');
  }
  constructor(private _cd: ChangeDetectorRef) {
             super();
             this._pageHeaderService.updateHeader('Amend BOL');
     }

 
   
   fileUpload($event) {
      this.myForm.patchValue({
        uploadDoc: $event
     });
      this.chageDetectorRef.markForCheck();
   }

   openAlert() {

    this._dialogService.alert()
    .subscribe( retval =>  console.log(' Dialog Data ', retval));
   }

   openConfirm() {
    this._dialogService.confirm()
    .subscribe( retval => console.log(' Dialog Data ', retval));
   }

   addRow() {
    const data = {
      firstName: 'Carren',
      age: 12, 
      title: 'Hello',
    };
     this._dialogService.openDialog(TestPopUpComponent, data)
     .subscribe((retval) => {
       console.log(retval);
       this.tabForms.push(this.addTableData(retval.firstName,retval.age));
      });
   }

   addTableRow() {
    this.tableItems.push( {id : '6', name :'ccdd', email: 'a@y.com', dateOfBirth: '12/12/2018'});
   }
   tableAction(data) {
     console.log(data);
     if (data.action === TableAction.delete) {
       this.deleteTableRow(data.index);
     }

   }

   deleteTableRow(i) {
     this.tableItems.splice(i, 1);
   }
 
   deleteRow(i) {
      this.tabForms.removeAt(i);
   }

   editRowr(i) {
       console.log(this.tabForms.controls[i].value);
       const data = {
        firstName: this.tabForms.controls[i].value.firstName,
        age: this.tabForms.controls[i].value.age, 
        title: 'Hello',
      };
      this._dialogService.openDialog(TestPopUpComponent, data)
      .subscribe((retval) => {
        console.log(retval);
        this.tabForms.controls[i].setValue(retval);
       });
   }

   addTableData(nameStr, age): FormGroup {
     return this.formBuilder.group({
                        firstName : [nameStr],
                        age: [age]
                      });
   }

  
 
   get tabForms() { 
     return this.myForm.get('tableData') as FormArray; 
   }

  ngOnInit() {
    this.firstNameValidations = [{name: 'minlength', validator: Validators.minLength(6), message: 'Min Length 6'},
                                         {name: 'required', validator: Validators.required, message: 'First Name Required'},
                                         {name: 'validname', validator: ValidateName, message: 'Name has to be DubaiTrade'}];
    this.uploadValidations = [{name: 'required', validator: Validators.required, message: 'Document is Required'}];
    this.autoVals = [{name: 'required', validator: Validators.required, message: 'Select one item'}];
    this.checkBoxVals = [{name: 'required', validator: Validators.required, message: 'Select one item'}]
    this.myForm  = this.formBuilder.group({
                            firstName : ['', [ Validators.required, Validators.minLength(6), ValidateName ]],
                            email: ['y@y.com', [ Validators.required, Validators.email]],
                            selectItem: ['2'],
                            dateInput: ['', [ Validators.required]],
                            gender: [1],
                            address: ['',[Validators.required]],
                            yn : [''],
                            tnc : [ true,[Validators.required]],
                            uploadDoc: [null, Validators.required],
                            multiSelectItem : [ ['1', '2', '0'] ],
                            autoSelectItem2: ['', [Validators.required]],
                            autoSelectItem: ['', [Validators.required]],
                            tableData: this.formBuilder.array( [])
                    });
           
            this.checkBoxConfig = {label: 'Lang', name: 'lang',
                                   options : [{id: 1, value: 'Eng', selected: false},
                                              {id: 2, value: 'Arabic', selected: true}]};
  }


  dataTableClick(event) {
    console.log(event);
  }
  getOutput(event) {
     console.log(event);
     console.log(this.myForm.value);
     console.log(this.myForm.get('autoSelectItem').value);
  }
 
  intializeAutoSelect() {
    this.items = [];
    this.items.push({value: '2', label: 'Red'});
    // this.autoComplete.refreshItems(this.items);
     this.autoComplete.refreshItems(this.items, '2');
    // this.myForm.get('autoSelectItem').patchValue('2');
  }

  reset() { 
      super.resetForm(this.myForm);
  }
  
  onSubmit(event) {
    console.log(event);
    console.log('Valid?', this.myForm.valid); // true or false
    console.log('Value', this.myForm.value);
    const notfnMessage: NotificationMessage = {
        text: 'Hello',
        type : MessageStatus.warning
    };
    let notfnMessages: NotificationMessage[] = [];
    notfnMessages.push(notfnMessage);
    notfnMessages.push(notfnMessage);
    this.notfnService.updateMessage(notfnMessage);
    this.clickCount = this.clickCount + 1;


    super.validateForm(this.myForm);
    // this.myForm.get('firstName').setErrors({'incorrect': true});
    for(let i = 0 ; i <  this.tableItems.length ; i++ ) {
      this.tabForms.push(this.addTableData(this.tableItems[i].id, this.tableItems[i].name));
    }
    console.log('Value', this.myForm.value);
    super.scrollToTop();
       const navextras: NavigationExtras = {
        queryParams: {'data': JSON.stringify('') }
      };
      this.notfnService.clearMessage();
      this._router.navigate(['/confirm'], navextras);
    // super.postData('/api/submit', JSON.stringify(this.myForm.value))
    // .subscribe((data) => {
    //   const navextras: NavigationExtras = {
    //     queryParams: {'data': JSON.stringify(data) }
    //   };
    //   this.notfnService.clearMessage();
    //   this._router.navigate(['/confirm'], navextras);
    // }, (error) => {
    //   this.notfnService.clearMessage();
    //   this._dialogService.alert('Unable to save data');
    // });
   }

}

