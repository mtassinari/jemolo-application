import { Directive, HostListener } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

@Directive({
    // selector: 'input[type=file]',
    selector: '[jhiFile]',
    /*host : {
        '(change)' : 'onChange($event.target.files)',
        '(blur)': 'onTouched()'
    },*/
    providers: [
        { provide: NG_VALUE_ACCESSOR, useExisting: FileValueAccessorDirective, multi: true }
    ]
})
export class FileValueAccessorDirective implements ControlValueAccessor {
    value: any;
    // onChange = (_) => {};
    // onTouched = () => {};

    @HostListener('change') onChange = _ => { console.log('event: '); };
    @HostListener('blur') onTouched = () => {};

    writeValue(value) {}
    registerOnChange(fn: any) { this.onChange = fn; }
    registerOnTouched(fn: any) { this.onTouched = fn; }
}
