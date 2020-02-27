import { Component, OnInit, Input, ChangeDetectionStrategy, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';
import { ChangeDetectionService } from '../../service/change-detection.service';

@Component({
  selector: 'tel-number',
  templateUrl: './tel-number.component.html',
  styleUrls: ['./tel-number.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TelNumberComponent extends BaseFormControlComponent implements OnInit {


  @Input() extn = false;
  constructor(private _cdRef: ChangeDetectorRef) {
    super();
    this._cDref = _cdRef;
  }

  countryCode = '';
  areaCode = '';
  telNumber = '';
  extValue = '';


  ngOnInit() {
    this.updateFields();
    if (this.getFieldValue()) {
      this.countryCode = this.getFieldValueToken('-', 0);
      this.areaCode = this.getFieldValueToken('-', 1);;
      this.telNumber = this.getFieldValueToken('-', 2);
      if (this.extn)
        this.extValue = this.getFieldValueToken('-', 3);
    }

  }


  clearData() {
    this.setFieldValue('');
    this.countryCode = '';
    this.areaCode = '';
    this.telNumber = '';
    super.markForChangeDetection(this._cdRef);
  }

  trackById(index, item) {
    console.log(' TRACKING ' + index + " item " + item);
  }

  getFieldValueLength() {
    return this.getFieldValue().replace('-', '').trim().length;
  }

  isValidInput(event, elePos, len) {
    var txt = this.getFieldValue().split('-')[elePos];


    if (this.isHelperKeys(event))
      return true;

    if (this.isTxtHighLighted(event))
      return true;

    if (!this.isNumberPressed(event)) {
      event.preventDefault();
      return false;
    }


    if (txt.trim().length >= len) {
      event.preventDefault();
      return false;
    }

    return true;

  }

  isEmptyTelephone() {
    var txt = this.getFieldValue().split('-');

    var isEmpty = true;
    for (let i = 0; i < txt.length; i++) {
      if (txt[i] && txt[i].trim().length > 0) {
        isEmpty = false;
        break;
      }
    }

    return isEmpty;
  }


  ccInput(event) {
    if (!this.isValidInput(event, 0, 3))
      return;
  }

  ccUp(event) {
    this.countryCode = event.currentTarget.value;
    this.setFieldValue(this.countryCode + '-' + this.areaCode + '-' + this.telNumber);
    if (this.countryCode.length >= 3 && !(this.isTabKey(event) ||
      this.isTxtHighLighted(event))) {
      event.currentTarget.nextElementSibling.nextElementSibling.focus();
    }

    if (this.isEmptyTelephone()) {
      this.setFieldValue('');
    }

  }

  acInput(event) {
    if (!this.isValidInput(event, 1, 4))
      return;
  }

  acUp(event) {
    this.areaCode = event.currentTarget.value;
    this.setFieldValue(this.countryCode + '-' + this.areaCode + '-' + this.telNumber);
    if (this.areaCode.length >= 2 && !(this.isTabKey(event) ||
      this.isTxtHighLighted(event))) {
      event.currentTarget.nextElementSibling.nextElementSibling.focus();
    }

    if (this.isEmptyTelephone()) {
      this.setFieldValue('');
    }

  }

  numInput(event) {
    if (!this.isValidInput(event, 2, 10))
      return;


  }

  updateTelNumber() {
    var telnum = this.countryCode + '-' + this.areaCode + '-' + this.telNumber;
    if (this.extn === true)
      telnum = '-' + this.extValue;
    return telnum;
  }

  numInputUp(event) {
    this.telNumber = event.currentTarget.value;
    this.setFieldValue(this.countryCode + '-' + this.areaCode + '-' + this.telNumber);
    if (this.isEmptyTelephone()) {
      this.setFieldValue('');
    }
  }


  extInput(event) {
    if (!this.isValidInput(event, 3, 4))
      return;


  }


  extUp(event) {
    this.extValue = event.currentTarget.value;
    this.setFieldValue(this.countryCode + '-' + this.areaCode + '-' + this.telNumber + '-' + this.extValue);
    if (this.isEmptyTelephone()) {
      this.setFieldValue('');
    }
  }

  updateFields() {
    if (this.getFieldValue()) {
      this.countryCode = this.getFieldValueToken('-', 0);
      this.areaCode = this.getFieldValueToken('-', 1);;
      this.telNumber = this.getFieldValueToken('-', 2);
      if (this.extn)
        this.extValue = this.getFieldValueToken('-', 3);
    }
  }

  setTelNumber(telNumber) {
    // if (this.getFieldTokenLength('-') > 0) {
    if (telNumber.split('-').length > 0) {
      this.setFieldValue(telNumber);
      this.updateFields();
    } else {
      console.error(" ERROR  " + telNumber + " NOt in the correct Format cc-ac-telNumber");
    }

  }

}
