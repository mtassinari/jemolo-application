import {Directive} from '@angular/core';
import {NG_VALIDATORS, Validator, FormControl} from '@angular/forms';

export class FileValidator implements Validator {

    static validate(c: FormControl): {[key: string]: any} {
        return c.value == null || c.value.length === 0 ? { 'required' : true} : null;
    }

    static validateSize(c: FormControl): {[key: string]: any} {
        return c.value != null && c.value.length > 0 && c.value[0].size > 3145728 ? { 'size' : true} : null;
    }

    static validateType(c: FormControl): {[key: string]: any} {
        return c.value != null && c.value.length > 0 && c.value[0].type !== 'application/pdf' ? { 'type' : true} : null;
    }

    validate(c: FormControl): {[key: string]: any} {
        return FileValidator.validate(c);
    }

    validateSize(c: FormControl): {[key: string]: any} {
        return FileValidator.validate(c);
    }

    validateType(c: FormControl): {[key: string]: any} {
        return FileValidator.validate(c);
    }
}
