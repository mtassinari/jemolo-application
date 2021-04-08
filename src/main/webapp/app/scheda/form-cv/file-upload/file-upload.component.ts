import { Component, OnInit, ElementRef, HostListener, Input } from '@angular/core';
import { FormGroup, NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

@Component({
  selector: 'jhi-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  providers: [{
      provide: NG_VALUE_ACCESSOR,
      useExisting: FileUploadComponent,
      multi: true
  }]
})
export class FileUploadComponent implements OnInit, ControlValueAccessor {

    @Input()  cv: FormGroup;

    file: File | null = null;

    // the callback function to register on UI change
    // onChange: Function;
    onChange: any = () => { };

    // the callback function to register on element touch
    // onTouch: Function;
    onTouch: any = () => { };

    constructor( private host: ElementRef<HTMLInputElement> ) { }

    ngOnInit() { }

    @HostListener('change', ['$event.target.files']) emitFiles( event: FileList ) {
        const file = event && event.item(0);
        this.onChange(file);
        this.file = file;
        this.cv.patchValue({file: event});
    }

    // This will will write the value to the view if the the value changes
    // occur on the model programmatically
    // this method sets the value programmatically
    writeValue( value: any ) {
        // clear file input
        // this.host.nativeElement.value = '';
        // this.file = null;
    }

    formatBytes(bytes, decimals = 2) {
        if (bytes === 0) { return '0 Bytes'; }

        const k = 1024;
        const dm = decimals < 0 ? 0 : decimals;
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

        const i = Math.floor(Math.log(bytes) / Math.log(k));

        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }

    // When the value in the UI is changed, this method will invoke a callback function
    // upon UI element value changes, this method gets triggered
    registerOnChange( fn: Function ) {
        this.onChange = fn;
    }

    // When the element is touched, this method will get called
    // upon touching the element, this method gets triggered
    registerOnTouched( fn: Function ) {
        this.onTouch = fn;
    }

    // sets the value used by the ngModel of the element
    // set value(val: any) { }
    set value(val: File) {
        // this value is updated by programmatic changes
        if (val !== undefined && this.file !== val) {
            this.file = val;
            this.onChange(val);
            this.onTouch(val);
        }
    }
}
