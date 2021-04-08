import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError, NavigationStart } from '@angular/router';
import { Location } from '@angular/common';
import { JhiLanguageHelper } from 'app/core';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
  route: string;
  constructor(private location: Location, private jhiLanguageHelper: JhiLanguageHelper, private router: Router) {}

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'jemoloApplicationApp';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.route = this.location.path();
      }
      if (event instanceof NavigationEnd) {
        this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
        if (this.route.startsWith('/jemolouser') && event.urlAfterRedirects === '/404') {
           this.router.navigate(['/jemolouser/404']);
        }
        if (this.route.startsWith('/jemoloiscritto') && event.urlAfterRedirects === '/404') {
           this.router.navigate(['/jemoloiscritto/404']);
        }
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        if (this.route.startsWith('/jemoloiscritto')) {
           this.router.navigate(['/jemoloiscritto/404']);
        } else
        if (this.route.startsWith('/jemolouser')) {
           this.router.navigate(['/jemolouser/404']);
        } else {
           // this.router.navigate(['/404']);
           this.router.navigate(['/public/404']);
        }
      }
    });
  }
}
