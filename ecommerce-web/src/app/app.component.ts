import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavBarComponent } from './core/nav-bar/nav-bar.component';
import { ShopComponent } from './shop/shop.component';
import { BehaviorSubject, Subject } from 'rxjs';
import { ICart } from './shared/model/cart';
import { CartService } from './cart/cart/cart.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavBarComponent, ShopComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  pageTitle: string = 'Welcome to online shopping';
  constructor(private cartService: CartService) {}
  ngOnInit():void {
    this.loadBasket();
  }
  loadBasket() {
    const cartId = localStorage.getItem('angular_cart_id')
    if(cartId) {
      this.cartService.getCart(cartId).subscribe(() => {
        console.log("Initialized cart")
      },
      error => {
        console.log("Error");
      })
    }
  }
}
