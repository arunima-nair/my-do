import { Component, OnInit, Input, EventEmitter, Output, ChangeDetectionStrategy, AfterContentInit, AfterViewInit, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormControl, AbstractControl } from '@angular/forms';
import { FieldConfig } from 'src/app/lib/classes/field.interface';
import { TranslateService } from '@ngx-translate/core';
import { AppInjector } from '../../classes/AppInjector';
import { LangTranslateService } from '../../service/lang-translate.service';
import { Subscription } from 'rxjs';
import { ChangeDetectionService } from '../../service/change-detection.service';

@Component({
  selector: 'app-base-form-control',
  template: `
     `,
  styles: []
})
export class BaseFormControlComponent implements OnInit, AfterContentInit, AfterViewInit, OnDestroy {


  @Input() parentForm: FormGroup;
  @Input() fieldConfig: FieldConfig;
  @Input() fb: FormBuilder;
  @Input() label;
  @Input() placeHolder;
  @Input() fieldName;
  @Input() fieldType;
  @Input() toolTip: string;
  @Input() validations: any[];
  @Input() prefixIcon = 'description';
  @Input() showIcon: boolean = true;
  @Input() backgroundColor: ElementBackGround;
  @Output() outputEvent = new EventEmitter<FormEmitter>();
  @Input() helpTxt: string;
  // private _translateService: TranslateService;
  private _translateService: LangTranslateService;
  _cDref: ChangeDetectorRef;

  labelTxt = '';
  formElementBackGround = '';
  subscription: Subscription;
  constructor() {
    const injector = AppInjector.getInjector();
    this._translateService = injector.get(LangTranslateService);
    this.subscription = injector.get(ChangeDetectionService).getMessage()
      .subscribe((msg: any) => {
        if (this._cDref)
          this._cDref.markForCheck();
      });
  }

  ngOnInit() {

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngAfterContentInit() {
    this.labelTxt = this.getLocaleValue(this.label);
    this.formElementBackGround = this.getFormElementBackGround();
    this.setValidations();
  }

  ngAfterViewInit() {
    // console.log('After View Init');
    this.labelTxt = this.getLocaleValue(this.label);
    this.formElementBackGround = this.getFormElementBackGround();
  }

  isEmptyObject(obj) {
    return (obj && (Object.keys(obj).length === 0));
  }

  logObject(obj) {
    console.log(JSON.stringify(obj));
  }

  getFormElementBackGround() {
    if (this.backgroundColor === ElementBackGround.white)
      return 'form-element-background-white .mat-form-field-flex';
  }

  addToForm(name: string, control: FormControl) {
    this.parentForm.addControl(name, control);
  }

  emitAction(action: FormActions, event, data?: any) {
    const obj: FormEmitter = { action: action, event: event, value: data };
    this.outputEvent.emit(obj);
  }

  markForChangeDetection(cdref: ChangeDetectorRef) {
    if (this._cDref)
      this._cDref.markForCheck();
  }
  bindValidations(validations: any) {
    if (validations.length > 0) {
      const validList = [];
      validations.forEach(valid => {
        validList.push(valid.validator);
      });
      return Validators.compose(validList);
    }
    return null;
  }


  change(event, data?: any) {
    this.emitAction(FormActions.change, event, data);
  }

  clear(f, event) {
    // f.value = '';
    this.parentForm.get(this.fieldName).patchValue('');
    this.emitAction(FormActions.click, event);
  }

  onKeydown(event) {
    if (event.key === "Enter") {
      this.emitAction(FormActions.enter, event);
    }
  }

  getLocaleValue(key) {
    return this._translateService.getLocaleValue(key);
  }

  getFieldValue(): any {
    return this.getFormField().value;
  }

  setFieldValue(val) {
    this.getFormField().patchValue(val);
  }

  getFormField(): AbstractControl {
    return this.parentForm.get(this.fieldName);
  }

  updateValidations(newValidations: any) {
    if (newValidations && newValidations.length > 0) {
      let validList = [];
      newValidations.forEach(validation => {
        validList.push(validation.validator);
      });
      this.parentForm.get(this.fieldName).setErrors(null);
      this.parentForm.get(this.fieldName).setValidators(validList);
    }
  }
  setValidations() {
    if (this.validations && this.validations.length > 0) {
      let validList = [];
      this.validations.forEach(validation => {
        validList.push(validation.validator);
      });
      this.parentForm.get(this.fieldName).setValidators(validList);
    }
  }



  getSpecificedField(fieldName): AbstractControl {
    return this.parentForm.get(fieldName);
  }

  delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }


  isNumberPressed(event) {
    return (event.keyCode > 47 && event.keyCode < 58) || (event.keyCode > 95 && event.keyCode < 106)
  }
  isFowardSlash(event) {
    return (event.keyCode === KEYS.FWD_SLASH)
  }

  isHelperKeys(event) {
    return (event.keyCode === KEYS.TAB
      || event.keyCode === KEYS.BACKSPACE
      || event.keyCode === KEYS.DELETE
      || event.keyCode === KEYS.LEFT_ARROW
      || event.keyCode === KEYS.RIGHT_ARROW
      || event.keyCode === KEYS.END
      || event.keyCode === KEYS.SHIFT
      || event.keyCode === KEYS.HOME);
  }

  isTabKey(event) {
    return (event.keyCode === KEYS.TAB);
  }

  isClearKeys(event) {
    return (event.keyCode === KEYS.DELETE || event.keyCode === KEYS.BACKSPACE)
  }

  getFieldValueToken(token, pos) {
    if (this.getFieldValue().split(token).length > pos)
      return this.getFieldValue().split(token)[pos];
    return '';
  }

  getTxtTokenValue(txt, token, pos) {
    if (txt.split(token).length > pos)
      return txt.split(token)[pos];
    return '';
  }

  isTxtHighLighted(event) {
    return event.currentTarget.selectionStart < event.currentTarget.selectionEnd
  }


  switchToNext(event) {
    event.currentTarget.nextElementSibling.focus();
  }

  stopDefaultBehaviour(event) {
    event.preventDefault();
  }

  updateLabelTxt(label) {
    this.labelTxt = label;
  }



}

export enum FormActions {
  click = 1,
  change = 2,
  enter = 3
}

export class FormEmitter {
  action?: FormActions;
  event?: any;
  value?: any;
}

export enum ElementBackGround {
  default = 0,
  white = 1
}

export enum KEYS {
  TAB = 9,
  BACKSPACE = 8,
  DELETE = 46,
  LEFT_ARROW = 37,
  RIGHT_ARROW = 39,
  END = 35,
  HOME = 36,
  SHIFT = 16,
  FWD_SLASH = 191
}

