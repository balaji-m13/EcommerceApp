import { CdkStepperModule } from '@angular/cdk/stepper';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CheckoutService } from '../checkout.service';
import { IDeliveryMethod } from '../../shared/model/delivery';
import { CartService } from '../../cart/cart/cart.service';

@Component({
  selector: 'app-checkout-delivery',
  imports: [CommonModule, ReactiveFormsModule, CdkStepperModule, NgIf, NgFor],
  templateUrl: './checkout-delivery.component.html',
  styleUrl: './checkout-delivery.component.scss'
})
export class CheckoutDeliveryComponent implements OnInit{
  @Input() checkoutForm?: FormGroup;
  deliveryMethods: IDeliveryMethod[] = [];
  constructor(private checkoutService: CheckoutService, private cartService: CartService) {}
  ngOnInit(): void {
      this.checkoutService.getDeliveryMethods().subscribe((dm:  IDeliveryMethod[]) => {
        this.deliveryMethods = dm;
      },
      error=>{
        console.log(error);
      })
  }
  setShippingPrice(deliveryMethod: IDeliveryMethod) {
    this.cartService.setShippingPrice(deliveryMethod);
  }
}
