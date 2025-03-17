import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, from } from 'rxjs';
import { KeycloakService } from 'keycloak-angular';
import { mergeMap } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private keycloakService: KeycloakService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Convert the promise-based getToken() to an observable with 'from()'
    return from(this.keycloakService.getToken()).pipe(
      mergeMap(token => {
        if (token) {
          // Clone the original request and add the Authorization header
          const authRequest = request.clone({
            setHeaders: {
              Authorization: `Bearer ${token}`
            }
          });
          return next.handle(authRequest);
        }
        // If no token, pass the request unmodified (or reject, up to you)
        return next.handle(request);
      })
    );
  }
}