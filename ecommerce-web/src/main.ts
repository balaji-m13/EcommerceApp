import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { provideHttpClient } from '@angular/common/http';
import { importProvidersFrom} from '@angular/core';
import { PaginationModule } from 'ngx-bootstrap/pagination'

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(PaginationModule.forRoot()),
    provideAnimations(),
    provideRouter(routes),
    provideHttpClient()
  ]
})
  .catch((err) => console.error(err));
