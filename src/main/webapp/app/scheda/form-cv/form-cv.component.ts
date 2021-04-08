import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import * as FileSaver from 'file-saver';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';
import { CustomValidators, ConfirmValidParentMatcher, MyErrorStateMatcher } from 'app/account/register-candidato/custom-validation';
import { FileValidator } from 'app/scheda/form-cv/file-input.validator';

@Component({
  selector: 'jhi-form-cv',
  templateUrl: './form-cv.component.html',
  styleUrls: ['./form-cv.component.scss']
})
export class FormCvComponent implements OnInit {

    @Input()  cv: FormGroup;
    file: File;
    constructor(protected curriculumService: CurriculumService, private cd: ChangeDetectorRef) { }
    formatBytes(bytes, decimals = 2) {
        if (bytes === 0) { return '0 Bytes'; }

        const k = 1024;
        const dm = decimals < 0 ? 0 : decimals;
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

        const i = Math.floor(Math.log(bytes) / Math.log(k));

        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }

    downloadCv() {
        this.curriculumService.download(this.cv.get('id').value).subscribe(file => {
            FileSaver.saveAs(file, this.cv.get('cv').value);
        });
    }

    handleFileInput(files: FileList) {
        /*const validators = Validators.compose([FileValidator.validate, FileValidator.validateSize, FileValidator.validateType]);
        this.cv.controls['file'].setValidators(validators);*/
        this.file = files[0];
        this.cv.patchValue({file: files});
    }

    onFileChange(event) {
        const reader = new FileReader();
        if (event.target.files && event.target.files.length) {
          const [file] = event.target.files;
          reader.readAsDataURL(file);
           reader.onload = () => {
           this.cv.patchValue({
              file: reader.result
           });
            // need to run CD since file load runs outside of zone
            this.cd.markForCheck();
          };
        }
    }

    ngOnInit() {
    }
}
