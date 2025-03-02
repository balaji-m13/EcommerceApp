import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { importProvidersFrom} from '@angular/core';
import { PaginationModule } from 'ngx-bootstrap/pagination'
import {KeycloakService} from 'keycloak-angular'
import { APP_INITIALIZER} from '@angular/core';


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

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes), provideClientHydration(withEventReplay()), provideHttpClient(),importProvidersFrom(PaginationModule.forRoot()), 
    KeycloakService, {
    provide: APP_INITIALIZER,
    useFactory: initializeKeycloack,
    multi: true,
    deps: [KeycloakService]
  }]
};
