import { JhiAlertService } from 'ng-jhipster';
import { HttpInterceptor, HttpRequest, HttpResponse, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable()
export class NotificationInterceptor implements HttpInterceptor {
  constructor(private alertService: JhiAlertService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap(
        (event: HttpEvent<any>) => {
          if (event instanceof HttpResponse) {
            const arr = event.headers.keys();
            let alert = null;
            let alertParams = null;
            arr.forEach(entry => {
              if (entry.toLowerCase().endsWith('app-alert')) {
                alert = event.headers.get(entry);
                console.log('app-alert: ', alert);
              } else if (entry.toLowerCase().endsWith('app-params')) {
                alertParams = event.headers.get(entry);
                console.log('app-params: ', alertParams);
              }
            });
            if (alert) {
              if (typeof alert === 'string') {
                const success = this.alertService.success(alert, { param: alertParams }, null);
             // const success2 = this.alertService.addAlert({type: 'success', msg: alertParams.message, timeout: 1000}, []);
             // const success2 = this.alertService.addAlert({type: 'success', msg: alert, params: { param: alertParams }, timeout: 10000}, []);
             // const success2 = this.alertService.addAlert({type: 'success', msg: alert, params: { param: alertParams }, timeout: -1}, []);
             // console.log('success: ', success);
              }
            }
          }
        },
        (err: any) => {}
      )
    );
  }
}
