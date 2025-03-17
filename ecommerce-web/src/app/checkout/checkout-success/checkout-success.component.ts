import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { IOrder } from '../../shared/model/order';

@Component({
  selector: 'app-checkout-success',
  imports: [CommonModule, RouterLink],
  templateUrl: './checkout-success.component.html',
  styleUrl: './checkout-success.component.scss'
})
export class CheckoutSuccessComponent {
  order!: IOrder;
  constructor(private router: Router) {
    const navigation =  this.router.getCurrentNavigation();
    const state = navigation && navigation.extras && navigation.extras.state;
    if(state) {
      this.order = state as IOrder;
    }
  }
}