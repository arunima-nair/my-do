import { Component, OnInit, ViewChild } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { TableDefn, TableType } from 'src/app/lib/classes/TableDefn';
import { TableComponent } from 'src/app/lib/components/table/table.component';

@Component({
  selector: 'app-test-dyna',
  templateUrl: './test-dyna.component.html',
  styleUrls: ['./test-dyna.component.css']
})
export class TestDynaComponent extends BaseComponent implements OnInit {

  myControl = new FormControl();
  parentForm: FormGroup;
  @ViewChild(TableComponent) dataTable:  TableComponent;

  

  tableDfn: TableDefn[] = [
    { displayName: 'id', mappingName: 'id', type: TableType.number, sort: true },
    { displayName: 'Name', mappingName: 'name', type: TableType.string, sort: true },
    { displayName: 'Email', mappingName: 'email', type: TableType.string  },
    { displayName: 'Dept', mappingName: 'dept.deptName', type: TableType.string  },
    { displayName: 'Date of Birth', mappingName: 'dateOfBirth', type: TableType.string, sort: true }
  ];

  items: any[] = [
    {value: '0', label: 'Red'},
    {value: '1', label: 'Green'},
    {value: '2', label: 'Blue'}
  ];
autoItems: any[] = [
  {value: '0', label: 'india'}
];
 
radioItems: any[] = [{label:'Male',value:1},{label:'Female',value:2}];
  formConfig = {
    'firstName':
    {
      control: 'firstName', col: '6', row: '1',
      config: {
        value: '', type: 'text', col: 1, label: 'First Name', placeHolder: 'first Name',
        validation: [{ name: 'minlength', validator: Validators.minLength(6), message: 'Min Length 6' },
        { name: 'required', validator: Validators.required, message: 'First Name Required' }]
      }
    },
    'email': {
      control: 'email', col: '6', row: '1',
      config: {
        value: '', type: 'email', col: 1, label: 'email', placeHolder: 'Email',
        validation: [{ name: 'required', validator: Validators.required, message: 'Email is Required' }]
      }
    },
    'date': {
      control: 'date', col: '6', row: '2',
      config: {
        value: '', type: 'date', col: 1, label: 'Date', placeHolder: 'Date',
        validation: [{ name: 'required', validator: Validators.required, message: 'Date is required' }]
      }
    },
    'selectItem': {
      control: 'selectItem', col: '6', row: '2',
      config: {
        value: '2', type: 'select', col: 1, label: 'Select', placeHolder: 'select Item', items: this.items,
        validation: [{ name: 'required', validator: Validators.required, message: 'Date is required' }]
      }
    },
    'autoSelectItem': {
      control: 'autoSelectItem', col: '6', row: '3', url: '/api/url',
      config: {
        value: '0', type: 'autoSelect', col: 1, label: 'Auto Complete',
        placeHolder: 'Auto Complete', items: this.autoItems,
        validation: [{
          name: 'required',
          validator: Validators.required, message: 'Auto Select is required'
        }]
      }
    },
    'multiSelect': {
      control: 'multiSelect', col: '6', row: '3',
      config: {
        value: ['1', '2', '0'], type: 'multiselect', col: 1,
        label: 'Select', placeHolder: 'select Item', items: this.items,
        validation: [{ name: 'required', validator: Validators.required, message: 'Date is required' }]
      }
    },
    'address': {
      control: 'address', col: '6', row: '4',
      config: {
        value: '', type: 'textArea', col: 1,
        label: 'Address', placeHolder: 'Address',
        validation: [{ name: 'required', validator: Validators.required, message: 'Date is required' }]
      }
    },
    'gender': {
      control: 'gender', col: '6', row: '5',
      config: {
        value: 1, type: 'radio', col: 1,
        label: 'Gender', placeHolder: 'Gender', items: this.radioItems,
        validation: [{ name: 'required', validator: Validators.required, message: 'Date is required' }]
      }
    },
    'yn': {
      control: 'yn', col: '6', row: '5',
      config: {
        value: 1, type: 'slider', col: 1,
        label: 'Yes/No', placeHolder: 'yn',
        validation: [{ name: 'required', validator: Validators.required, message: 'Date is required' }]
      }
    },
    'passport': {
      control: 'passport', col: '6', row: '6',
      config: {
        value: '', type: 'upload', label: 'Passport', placeHolder: 'Passport',
        validation: [{ name: 'required', validator: Validators.required, message: 'Passport  is required' }]
      }
    },
    'datatable': { url: '/api/dd', col: '12', row: '7', pageSize: 10, config: { type: 'dataTable' } }
  };

  constructor() { super(); }

  reloadDataTable(event) {
    this.dataTable.refreshTable('firName=22&lastName=dd');
  }
  
  ngOnInit() {
    // console.log(this.formconfig);
    // console.log(this.formconfig[0].control);
    // console.log(this.formconfig[0].config);


    const keys = Object.keys(this.formConfig);
    let fc: FormControl;
    this.parentForm  = this.formBuilder.group({});
    let validators = [];
    for (const prop of keys) {
      validators = [];
      //  console.log( prop);
      //  console.log(this.formconfig[prop].control);
      //  console.log(this.formconfig[prop].config);
      if (prop === 'datatable')
        continue;

        for (let i = 0; i < this.formConfig[prop].config.validation.length; i++) {
              validators.push(this.formConfig[prop].config.validation[i].validator);
        }
        fc = new FormControl(this.formConfig[prop].config.value, validators);
        this.parentForm.addControl(this.formConfig[prop].control, fc);
    }

  }

  submittedData($event) {
    console.log(this.parentForm.valid);
    console.log(this.parentForm.value);
  }

}
