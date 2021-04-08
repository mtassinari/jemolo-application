import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AccountService } from 'app/core';
import { PasswordService } from './password.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-password',
  templateUrl: './password.component.html'
})
export class PasswordComponent implements OnInit {
  doNotMatch: string;
  error: string;
  success: string;
  account: any;
  passwordForm = this.fb.group({
    currentPassword: ['', [Validators.required]],
    /*newPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]]*/
    // newPassword: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{10,}$')]],
    newPassword: ['', [Validators.required, Validators.pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{10,}$')]],
    // confirmPassword: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{10,}$')]],
    confirmPassword: ['', [Validators.required, Validators.pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{10,}$')]],
  });

  constructor(private passwordService: PasswordService, private accountService: AccountService, private fb: FormBuilder) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.account = account;
    });
  }

  changePassword() {
    const newPassword = this.passwordForm.get(['newPassword']).value;
    if (newPassword !== this.passwordForm.get(['confirmPassword']).value) {
      this.error = null;
      this.success = null;
      this.doNotMatch = 'ERROR';
    } else {
      this.doNotMatch = null;
      this.passwordService.save(newPassword, this.passwordForm.get(['currentPassword']).value).subscribe(
        () => {
          this.error = null;
          this.success = 'OK';
        },
        /*() => {
          this.success = null;
          this.error = 'ERROR';
        }*/
        (response: HttpErrorResponse) => {
          console.log('Status : ', response.status);
          console.log('Error type : ', response.error.type);
          if (response.status === 400 && response.error.type === 'https://www.jemoloapp/problem/invalid-password') {
            this.error = 'ERROR';
          }
        }
      );
    }
  }
}
