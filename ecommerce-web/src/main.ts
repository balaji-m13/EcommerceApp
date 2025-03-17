import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { provideHttpClient } from '@angular/common/http';
import { APP_INITIALIZER, importProvidersFrom} from '@angular/core';
import { PaginationModule } from 'ngx-bootstrap/pagination'
import {KeycloakService} from 'keycloak-angular'
import { provideToastr, ToastrModule } from 'ngx-toastr';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './app/auth/auth.interceptor';

function initializeKeycloack(keycloak: KeycloakService) {
  return () => {
    if (typeof window === 'undefined') {
      console.log('Skipping Keycloak initialization (not in browser)');
      return Promise.resolve(false);
    }

    return keycloak.init({
      config:{
        url: 'http://localhost:9003',
        realm: 'oauth2-demo-realm',
        clientId: 'angular-ecommerce'
      },
      initOptions: {
        onLoad: 'check-sso',
        pkceMethod: 'S256',
        silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-verify-sso.html'
      },
      loadUserProfileAtStartUp: false
    });
  };
}

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(PaginationModule.forRoot()),
    KeycloakService, {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloack,
      multi: true,
      deps: [KeycloakService]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    provideAnimations(),
    provideRouter(routes),
    provideHttpClient(),
    provideToastr()
  ]
})
  .catch((err) => console.error(err));
