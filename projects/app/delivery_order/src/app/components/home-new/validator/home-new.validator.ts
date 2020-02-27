import { AbstractControl } from '@angular/forms';

export function ValidateName(control: AbstractControl) {
    if (control.value === 'DubaiTrade') {
        return null;
    }
    return {validname: true};
}
