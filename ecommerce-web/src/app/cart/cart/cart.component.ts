import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ICart, ICartItem, ICartTotals } from '../../shared/model/cart';
import { CartService } from './cart.service';
import { CartDetailsComponent } from "../../core/cart-details/cart-details.component";
import { AsyncPipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CartDetailsComponent,AsyncPipe,CommonModule,RouterLink],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent implements OnInit {
  cart$!: Observable<ICart |null>;
  cartTotals$!: Observable<ICartTotals | null>;

  constructor(public cartService: CartService) {

  }

  ngOnInit(): void {
       this.cart$ = this.cartService.cart$;
       this.cartTotals$ = this.cartService.cartTotal$;
       console.log("ITEMS LIST")
       this.cartTotals$.subscribe({
        next:(data) => {
          console.log(data)
        }
       })
  }
   
    incrementItemQuantity(item: ICartItem) {
      this.cartService.incrementItemQuantity(item);
    }
    decrementItemQuantity(item: ICartItem) {
      this.cartService.decrementItemQuantity(item);
    }
    removeCartItem(item: ICartItem) {
      this.cartService.removeItemFromCart(item);
    }
}
