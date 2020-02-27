import { Component, OnInit, Input, Output, EventEmitter, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-dyna-form',
  templateUrl: './dyna-form.component.html',
  styleUrls: ['./dyna-form.component.css']
})
export class DynaFormComponent implements OnInit {

  constructor() { }

  @Input() formConfig;
  @Input() parentForm: FormGroup;
  @Input() formBuilder: FormBuilder;
  @Output() submitEvent = new EventEmitter();
  keyConfig: any[] = [];
  htmlString: string ='';
  rowStart = '<div class="row">\n';
  divend = '</div>\n'; 
  colDiv = '<div class="col-sm-{cols}">\n{col_txt}</div>\n ';
  cntrl: any;
currentRow =0;
 
intializeCtrl(){
  this.cntrl = {};
  this.cntrl['text'] = '<app-text-input [fieldType]="\'text\'" [parentForm]="parentForm" [label]="\'{label}\'" [placeHolder]="\'{ph}\'"  [fieldName]="\'{fn}\'" [fb]="formBuilder" [validations]="{val}"></app-text-input>';
  this.cntrl['email'] = '<app-text-input [fieldType]="\'email\'" [parentForm]="parentForm" [label]="\'{label}\'" [placeHolder]="\'{ph}\'"  [fieldName]="\'{fn}\'" [fb]="formBuilder" [validations]="{val}"></app-text-input>';
  this.cntrl['date'] = '<app-date-input  [parentForm]="parentForm" [label]="\'{label}\'"   [placeHolder]="\'{ph}\'"  [fieldName]="\'{fn}\'"  [validations]="{val}"  [fb]="formBuilder"></app-date-input>';
  this.cntrl['select'] = '<app-select [fieldName]="\'{fn}\'" [label]="\'{label}\'"  [items]="{items}" [parentForm]="parentForm" [fb]="formBuilder"></app-select>';
  this.cntrl['multiselect'] = '<app-select [fieldName]="\'{fn}\'" [label]="\'{label}\'"  [items]="{items}" [parentForm]="parentForm" [fb]="formBuilder" [multiple]="\'true\'"></app-select>';
  this.cntrl['autoSelect'] = '<app-auto-complete [fieldName]="\'{fn}\'" [label]="\'{label}\'"  [items]="{items}" [parentForm]="parentForm" [fb]="formBuilder" [url]=""></app-auto-complete>';
  this.cntrl['textArea'] = '<app-textarea [parentForm]="parentForm" [label]="\'{label}\'" [placeHolder]="\'{ph}\'" [fieldName]="\'{fn}\'" [fb]="formBuilder" [validations]="{val}"></app-textarea>';
  this.cntrl['radio'] = '<app-radio [parentForm]="parentForm" [label]="\'{label}\'" [placeHolder]="\'{ph}\'"  [fieldName]="\'{fn}\'" [fb]="formBuilder" [validations]="{val}"  [options]="{items}"></app-radio>';
  this.cntrl['slider'] = '<app-slide [label]="\'{label}\'" [fieldName]="\'{fn}\'"  [parentForm]="parentForm" [fb]="formBuilder" ></app-slide>';
  this.cntrl['upload'] = '<fileupload [parentForm]="myForm" [label]="\'{label}\'" [placeHolder]="\'{ph}\'" [fieldName]="\'{fn}\'" [fb]="formBuilder"   (dataEmitter)="fileUpload($event)" [dndType]="\'true\'"></fileupload>';
  this.cntrl['dataTable'] = '<app-data-table (clickEvent)="dataTableClick($event) [url]="\'{url}\'"   [tableDefn]="tableDfn" [pageSize]="\'{ps}\'"></app-data-table>';
}
  ngOnInit() {
  //  this.generateHTML();
   this.generateCode();
    // console.log(this.htmlString);


  }

  generateHTML() {
    this.intializeCtrl();
    this.htmlString = this.rowStart;
    const keys = Object.keys(this.formConfig);
    let cntrlStr: string;
    for (const prop of keys) {
        this.keyConfig.push(this.formConfig[prop]);
        cntrlStr = '';
        this.htmlString = this.htmlString + this.ifNewRow(parseInt(this.formConfig[prop].row, 10));
        this.htmlString = this.htmlString + this.colDiv.replace('{cols}', this.formConfig[prop].col);
        cntrlStr = this.cntrl[this.formConfig[prop].config.type];
        cntrlStr = cntrlStr.replace('{label}', this.formConfig[prop].config.label);
        cntrlStr = cntrlStr.replace('{fn}', this.formConfig[prop].control);
        cntrlStr = cntrlStr.replace('{ph}', this.formConfig[prop].config.placeHolder);
        cntrlStr = cntrlStr.replace('{ps}', this.formConfig[prop].pageSize);
        cntrlStr = cntrlStr.replace('{url}', this.formConfig[prop].url);
        cntrlStr = cntrlStr.replace('{val}', JSON.stringify(this.formConfig[prop].config.validation));
        cntrlStr = cntrlStr.replace('{items}', JSON.stringify(this.formConfig[prop].config.items));
        this.htmlString =  this.htmlString.replace('{col_txt}', cntrlStr);
    }
    this.htmlString = this.htmlString + this.divend;
  }

  generateCode() {

    this.intializeCtrl();
    this.htmlString = this.rowStart;
    const keys = Object.keys(this.formConfig);
    let cntrlStr: string;
    let txtStr: string;
    for (const prop of keys) {
        cntrlStr = '';
        this.htmlString = this.htmlString + this.ifNewRow(parseInt(this.formConfig[prop].row, 10));
        this.htmlString = this.htmlString + this.colDiv.replace('{cols}', this.formConfig[prop].col);
        cntrlStr = this.cntrl[this.formConfig[prop].config.type];
        cntrlStr = cntrlStr.replace('{label}', this.formConfig[prop].config.label);
        cntrlStr = cntrlStr.replace('{fn}', this.formConfig[prop].control);
        cntrlStr = cntrlStr.replace('{ph}', this.formConfig[prop].config.placeHolder);
        cntrlStr = cntrlStr.replace('{ps}', this.formConfig[prop].pageSize);
        cntrlStr = cntrlStr.replace('{url}', this.formConfig[prop].url);
        txtStr = 'formConfig[\'' + prop + '\'].config.validation';
        cntrlStr = cntrlStr.replace('{val}', txtStr);
         txtStr = 'formConfig[\'' + prop + '\'].config.items';
        cntrlStr = cntrlStr.replace('{items}', txtStr);
        this.htmlString =  this.htmlString.replace('{col_txt}', cntrlStr);
    }
    this.htmlString = this.htmlString + this.divend;
    // <!-- <app-text-input  [parentForm]="myForm" [label]="'First Name'" 
    // [placeHolder]="'First Name'"
    // [fieldName]="'firstName'" [fieldType]="'text'" [validations]="formconfig['firstName'].config.validation"
    // [fb]="formBuilder"></app-text-input> -->
    console.log(this.htmlString);

  }
  

   
  onSubmit(event) {
    this.submitEvent.emit(event);
  }

  ifNewRow(currRow){
    currRow = parseInt(currRow,10);
    let rowstr ='';
    if (this.currentRow === 0){
       this.currentRow = currRow;
    } else  if (currRow > this.currentRow) {
        this.currentRow = currRow;
        rowstr = '</div><div class="row">';
        console.log(rowstr);
    }
    return rowstr;
  }

  getColClass(fc) {
    // console.log(fc);
    return 'col-sm-6';
  }

}
