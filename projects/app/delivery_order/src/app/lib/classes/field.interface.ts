import { FormBuilder } from '@angular/forms';

export interface Validator {
    name: string;
    validator: any;
    message: string;
}

export interface FieldConfig {
    label?: string;
    name?: string;
    inputType?: string;
    placeholder?: string;
    options?: any[];
    collections?: string;
    value?: any;
    validations?: Validator[];
}
