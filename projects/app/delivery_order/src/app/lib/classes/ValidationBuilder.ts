import { Validators } from '@angular/forms';

export class ValidationBuilder {
   public static getRequired() {
        return Validators.required;
    }

    public static getEmail() {
        return Validators.email;
    }

    public static getMinLength(length) {
        return Validators.minLength(length);
    }


}