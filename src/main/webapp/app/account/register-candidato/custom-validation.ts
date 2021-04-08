import {
    FormGroup,
    FormControl,
    FormGroupDirective,
    NgForm,
    ValidatorFn,
    AbstractControl,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material';

/**
 * Custom validator functions for reactive form validation
 */
export class CustomValidators {
    /**
     * Validates that child controls in the form group are equal
     */
    static childrenEqual: ValidatorFn = (formGroup: FormGroup) => {
        const [firstControlName, ...otherControlNames] = Object.keys(
            formGroup.controls || {}
        );
        const isValid = otherControlNames.every(
            controlName =>
                formGroup.get(controlName).value ===
                formGroup.get(firstControlName).value
        );
        return isValid ? null : { childrenNotEqual: true };
    };

    static ageRangeValidator(min: number, max: number): ValidatorFn {
        return (control: AbstractControl): { [key: string]: boolean } | null => {
           if (control.value !== undefined && (isNaN(control.value) || control.value < min || control.value > max)) {
               return { 'ageRange': true };
            }
            return null;
        };
     }

    static lastCharCheck(): ValidatorFn {
      return (control: FormControl): {[key: string]: boolean} | null => {
          // let validi, i, s, set1, set2, setpari, setdisp;
          let cf = control.value;
          // console.log('control: ', cf);
          // if( cf == '' )  return '';
          cf = cf.toUpperCase();
          const validi = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
          for ( let i = 0; i < 16; i++ ) {
              if ( validi.indexOf( cf.charAt(i) ) === -1 ) {
                  return { 'charCheck': true };
              }
          }
          const set1 = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
          const set2 = 'ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ';
          const setpari = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
          const setdisp = 'BAKPLCQDREVOSFTGUHMINJWZYX';
          let s = 0;
          for ( let i = 1; i <= 13; i += 2 ) {
              s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
          }
          for ( let i = 0; i <= 14; i += 2 ) {
              s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
          }
          if ( s % 26 !== cf.charCodeAt(15) - 'A'.charCodeAt(0) ) {
              return { 'charCheck': true };
          }
          return null;
       };
   }
}

/**
 * Custom ErrorStateMatcher which returns true (error exists) when the parent form group is invalid and the control has been touched
 */
export class ConfirmValidParentMatcher implements ErrorStateMatcher {
    isErrorState(
        control: FormControl | null,
        form: FormGroupDirective | NgForm | null
    ): boolean {
        const isSubmitted = form && form.submitted;
        // return control.parent.invalid && control.touched;
        return !!(control.dirty && control.parent.invalid && (control.parent.dirty || control.parent.touched || isSubmitted));
    }
}

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

/**
 * Collection of reusable RegExps
 */
export const regExps: { [key: string]: RegExp } = {
    password: /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{7,15}$/,
};

/**
 * Collection of reusable error messages
 */
export const errorMessages: { [key: string]: string } = {
    fullName: 'Full name must be between 1 and 128 characters',
    email: 'Email must be a valid email address (username@domain)',
    confirmEmail: 'Email addresses must match',
    password:
        'Password must be between 7 and 15 characters, and contain at least one number and special character',
    confirmPassword: 'Passwords must match',
};
