import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AccountService } from 'app/core';

@Component({
  selector: 'jhi-risultato-ricerca',
  templateUrl: './risultato-ricerca.component.html',
  styleUrls: ['./risultato-ricerca.component.scss']
})
export class RisultatoRicercaComponent implements OnInit {

    currentAccount: any;

  constructor(private location: Location,
          protected accountService: AccountService) { }

  ngOnInit() {
      this.accountService.identity().then(account => {
          this.currentAccount = account;
        });
      console.log(this.location.getState());
  }

}
