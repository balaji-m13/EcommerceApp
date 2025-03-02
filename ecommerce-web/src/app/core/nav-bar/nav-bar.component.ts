import { Component } from '@angular/core';
import { CartService } from '../../cart/cart/cart.service';
import { Observable } from 'rxjs';
import { AsyncPipe, CommonModule, NgIf } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { ICart } from '../../shared/model/cart';
import { KeycloakProfile } from 'keycloak-js';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-nav-bar',
  imports: [CommonModule, RouterModule, RouterLink, RouterLinkActive, NgIf, AsyncPipe],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent {
  cart$: Observable<ICart | null>;
  public loggedIn = false;
  public userProfile: KeycloakProfile | null = null;

  constructor(public cartService: CartService, private keycloak: KeycloakService) {
    this.cart$ = this.cartService.cart$;
  }

  ngOnInit() {
    this.isLogged();
  }

  async isLogged() {
    this.loggedIn = await this.keycloak.isLoggedIn();
    if(this.loggedIn) {
      this.userProfile = await this.keycloak.loadUserProfile();
      console.log(this.userProfile);
    }
  }

  public login() {
    this.keycloak.login();
  }

  public logout() {
    this.keycloak.logout();
  }

  get userName() {
    return this.userProfile?.username;
  }

  get emailId() {
    return this.userProfile?.email;
  }

}
