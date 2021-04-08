import {Component, Inject, Injectable} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA, MatDialog} from '@angular/material';

@Component({
templateUrl:  './login-error.component.html'
})
export class LoginErrorComponent {
    constructor(private dialogRef: MatDialogRef<LoginErrorComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }
    public  closeDialog() {
        this.dialogRef.close();
    }
}
