import { FormGroup, ValidatorFn, AbstractControl, FormArray } from '@angular/forms';

export function atLeastOneCheckboxCheckedValidator3(minRequired = 1): ValidatorFn {
  return function validate(formGroup: FormGroup) {
    let checked = 0;

    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.controls[key];
      const control2: FormGroup = formGroup.controls[key] as FormGroup;
      const formArray: FormArray = control2.controls['sottoambitos'] as FormArray;
      Object.keys(formArray.controls).forEach(key2 => {
          const formGroup2: FormGroup = formArray.controls[key2] as FormGroup;
          if (formGroup2.controls.selected.value === true) {
            checked++;
          }
      });
    });
    if (checked < minRequired) {
      return {
        requireCheckboxToBeChecked: true,
      };
    }

    return null;
  };
}
export function atLeastOneCheckboxCheckedValidator2(minRequired = 1): ValidatorFn {
    return function validate(formGroup: FormGroup) {
      let checked = 0;

      Object.keys(formGroup.controls).forEach(key => {
        const control = formGroup.controls[key];
        const control2: FormGroup = formGroup.controls[key] as FormGroup;
        // console.log('control2.controls: ', control2.controls.descrizione.value);
        if (control2.controls.selected.value === true) {
          checked++;
        }
      });
      if (checked < minRequired) {
        return {
          requireCheckboxToBeChecked: true,
        };
      }

      return null;
    };
}
export function atLeastOneCheckboxCheckedValidator(minRequired = 1): ValidatorFn {
    return function validate(formGroup: FormGroup) {
      let checked = 0;
      Object.keys(formGroup.controls).forEach(key => {
        const control = formGroup.controls[key];
        if (control.value === true) {
          checked++;
        }
      });
      if (checked < minRequired) {
        return {
          requireCheckboxToBeChecked: true,
        };
      }
      return null;
    };
  }
