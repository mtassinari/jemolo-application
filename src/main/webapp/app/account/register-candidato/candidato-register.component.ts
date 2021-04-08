import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm, FormGroupDirective, FormControl, FormBuilder, Validators, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CandidatoRegister } from './candidato-register.service';
import { JhiLanguageService } from 'ng-jhipster';
import { LoginCandidatoModalService } from 'app/core';
import { EMAIL_ALREADY_USED_TYPE, CF_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { ErrorStateMatcher } from '@angular/material/core';
import { CustomValidators, ConfirmValidParentMatcher, MyErrorStateMatcher } from 'app/account/register-candidato/custom-validation';

export class MyErrorStateMatcher2 implements ErrorStateMatcher {
    isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
      const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
      const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);

      return (invalidCtrl || invalidParent);
    }
  }

@Component({
  selector: 'jhi-candidato-register',
  templateUrl: './candidato2-register.component.html'
})
export class CandidatoRegisterComponent implements OnInit, AfterViewInit {
    doNotMatch: string;
    error: string;
    errorLoginExists: string;
    errorEmailExists: string;
    errorCFExists: string;
    errorCFNotValid: string;
    success: boolean;
    modalRef: NgbModalRef;
    hide: Boolean = true;
    matcher = new MyErrorStateMatcher();
    confirmValidParentMatcher = new ConfirmValidParentMatcher();

    registerForm: FormGroup;
    createForm() {
    this.registerForm = this.fb.group({
        nome: ['', [Validators.required]],
        cognome: ['', [Validators.required]],
        cfGroup: this.fb.group({
            codiceFiscale: ['', [CustomValidators.lastCharCheck(), Validators.required, Validators.pattern('^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$')]],
            confirmCodiceFiscale: ['']
        }, { validator: CustomValidators.childrenEqual}),
        emailGroup: this.fb.group({
            email: ['', [Validators.required, Validators.pattern('^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$')]],
            confirmEmail: ['']
        }, { validator: CustomValidators.childrenEqual}),
        passwordGroup: this.fb.group({
            /*password: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{10,}$')]],*/
            password: ['', [Validators.required, Validators.pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{10,}$')]],
            confirmPassword: ['']
        }, { validator: CustomValidators.childrenEqual})
    });
    }
    public hasErrorPassword = (errorName: string) => {
        return this.registerForm['controls'].passwordGroup['controls'].password.hasError(errorName);
    }
    public hasErrorEmail = (errorName: string) => {
        return this.registerForm['controls'].emailGroup['controls'].email.hasError(errorName);
    }

    public hasErrorCF = (errorName: string) => {
        /*console.log('errorName: ', errorName);
	    console.log('value: ', this.registerForm['controls'].cfGroup['controls'].codiceFiscale.hasError(errorName));*/
        return this.registerForm['controls'].cfGroup['controls'].codiceFiscale.hasError(errorName);
    }

    checkPasswords(group: FormGroup) { // here we have the 'passwords' group
        const pass = group.get('password').value;
        const confirmPass = group.get('confirmPassword').value;

        return pass === confirmPass ? null : { notSame: true };
    }

    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginCandidatoModalService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        protected candidatoService: CandidatoRegister,
        private fb: FormBuilder
    ) { this.createForm(); }

    ngOnInit() {
          this.success = false;
    }

    ngAfterViewInit() {
        // this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#field_nome'), 'focus', []);
        setTimeout(() => {
            const elem = this.renderer.selectRootElement('#field_nome');
            this.renderer.listen(elem, 'focus', () => { /*console.log('focus');*/ });
            this.renderer.listen(elem, 'blur', () => { /*console.log('blur');*/ });
            elem.focus();
        }, 1000);
    }

    register() {
        this.doNotMatch = null;
        this.error = null;
        this.errorLoginExists = null;
        this.errorCFExists = null;
        this.errorEmailExists = null;
        this.errorCFNotValid = null;
        let registerAccount = {};
        const nome = this.registerForm.get(['nome']).value;
        const cognome = this.registerForm.get(['cognome']).value;
        const codiceFiscale = this.registerForm.get('cfGroup.codiceFiscale').value;
        const login = this.registerForm.get('emailGroup.email').value;
        const email = this.registerForm.get('emailGroup.email').value;
        const password = this.registerForm.get('passwordGroup.password').value;
        if (password !== this.registerForm.get('passwordGroup.confirmPassword').value) {
           this.doNotMatch = 'ERROR';
        }
        if (!this.validaCodiceFiscale(codiceFiscale)) {
           this.errorCFNotValid = 'ERROR';
        } else {
         registerAccount = { ...registerAccount, nome, cognome, codiceFiscale, login, email, password};
         /*this.doNotMatch = null;
         this.error = null;
         this.errorCFExists = null;
         this.errorEmailExists = null;*/
         this.languageService.getCurrent().then(langKey => {
           registerAccount = { ...registerAccount, langKey };
           this.candidatoService.save(registerAccount).subscribe(
           () => {
             this.success = true;
           },
           response => this.processError(response)
           );
         });
        }
    }

    openLogin() {
      this.modalRef = this.loginModalService.open();
    }

    private processError(response: HttpErrorResponse) {
      this.success = null;
      /*console.log('Status : ', response.status);
      console.log('Error type : ', response.error.type);*/
      if (response.status === 400 && response.error.type === CF_ALREADY_USED_TYPE) {
        this.errorCFExists = 'ERROR';
      } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
        this.errorEmailExists = 'ERROR';
      } else if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
        this.errorLoginExists = 'ERROR';
      } else {
        this.error = 'ERROR';
      }
    }

     validaCodiceFiscale(cf) {
          // let validi, i, s, set1, set2, setpari, setdisp;
          /*if( cf == '' )  return '';*/
          cf = cf.toUpperCase();
          if ( cf.length !== 16 ) {
              return false;
          }
          const validi = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
          for ( let i = 0; i < 16; i++ ) {
              if ( validi.indexOf( cf.charAt(i) ) === -1 ) {
                  return false;
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
              return false;
          }
          return true;
     }
}
