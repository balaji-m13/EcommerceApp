import { CdkStepperModule } from '@angular/cdk/stepper';
import { CommonModule, NgIf } from '@angular/common';
import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TextInputComponent } from '../../shared/components/text-input/text-input.component';
import { CartService } from '../../cart/cart/cart.service';
import { CheckoutService } from '../checkout.service';
import { ToastrService } from 'ngx-toastr';
import { ICart } from '../../shared/model/cart';
import { firstValueFrom } from 'rxjs';
import { IAddress, IOrderToCreate } from '../../shared/model/order';
import { IDeliveryMethod } from '../../shared/model/delivery';
import {loadStripe, Stripe, StripeCardCvcElement, StripeCardExpiryElement, StripeCardNumberElement} from '@stripe/stripe-js';
import { NavigationExtras, Route } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout-payment',
  imports: [CommonModule, ReactiveFormsModule, CdkStepperModule, NgIf, TextInputComponent],
  templateUrl: './checkout-payment.component.html',
  styleUrl: './checkout-payment.component.scss'
})
export class CheckoutPaymentComponent implements OnInit {
  @Input() checkoutForm?: FormGroup
  @ViewChild("cardNumber") cardNumberElement?: ElementRef;
  @ViewChild("cardExpiry") cardExpiryElement?: ElementRef;
  @ViewChild("cardCvc") cardCvcElement?: ElementRef;
  stripe: Stripe | null = null;
  cardNumber?: StripeCardNumberElement;
  cardExpiry?: StripeCardExpiryElement;
  cardCvc?: StripeCardCvcElement;
  cardNumberErrors: any;
  cardExpiryErrors: any;
  cardCvcErrors: any;
  cardNumberComplete = false;
  cardExpiryComplete = false;
  cardCvcComplete = false;

  constructor(private cartService: CartService, private checkoutService: CheckoutService, private toastrService: ToastrService, private router: Router) {

  }

  async ngOnInit() {
    await loadStripe('pk_test_51QzJ1J03ZJeA9IYOGNqOFVL5e609cbDcyRh2qf8N2TLx6fzNUMms9w5e9EF1A4rMgntO6g7TZWt5wfkr8GZ1hEjp00rkIXIP60').then(stripe => {
      this.stripe = stripe;
      const elements = stripe?.elements();
      if(elements) {
        this.cardNumber = elements.create('cardNumber');
        this.cardNumber.mount(this.cardNumberElement?.nativeElement);
        this.cardNumber.on('change', event=> {
          this.cardNumberComplete = event.complete;
          if(event.error) this.cardNumberErrors = event.error.message;
          else this.cardNumberErrors='';
        });

        this.cardExpiry = elements.create('cardExpiry');
        this.cardExpiry.mount(this.cardExpiryElement?.nativeElement);
        this.cardExpiry.on('change', event=> {
          this.cardExpiryComplete = event.complete;
          if(event.error) this.cardExpiryErrors = event.error.message;
          else this.cardExpiryErrors = '';
          console.log("Expiry error")
          console.log(event.error?.message)
        });


        this.cardCvc = elements.create('cardCvc');
        this.cardCvc.mount(this.cardCvcElement?.nativeElement);
        this.cardCvc.on('change', event=> {
          this.cardCvcComplete = event.complete;
          if(event.error) this.cardCvcErrors = event.error.message;
          else this.cardCvcErrors='';
        });

      }
    })
    
  }

  async submitOrder() {
    console.log("Inside Submit order")
    const cart = await this.cartService.getCurrentCart();
    try {
      const createOrder = await this.createOrder(cart);
      const paymentResult = await this.getPaymentConfirmation(cart);
      if(paymentResult!.paymentIntent) {
        this.cartService.deleteCart(cart!);
        const navigationExtras: NavigationExtras={state:createOrder};
        this.router.navigate(['checkout/success'], navigationExtras)
        .then(success => console.log("Navigation success:", success))
        .catch(err => console.error("Navigation error:", err));
        this.toastrService.success("Payment was successful")
      }
      else {
        this.toastrService.error(paymentResult.error.message);
      }

    } catch (error: any) {
      console.log(error);
      this.toastrService.error(error.message);
    }

  }
  getPaymentConfirmation(cart: ICart | null) {
    if(!cart) throw new Error('Cart is null');
    const result = this.stripe?.confirmCardPayment(cart.clientSecret!, {
      payment_method: {
        card: this.cardNumber!,
        billing_details: {
          name: this.checkoutForm?.get('paymentForm')?.get('nameOnCard')?.value
        }
      }
    });
    if(!result) {
      throw new Error("Problem with reaching out to stripe for confirmation");
    }
    return result;
  }
  createOrder(cart: ICart | null) {
    if(!cart) {
      throw new Error('Cart is null');
    }
    const orderToCreate = this.getOrderToCreate(cart);
    console.log("ordertocreate object preparation:");
    console.log(orderToCreate);
    return firstValueFrom(this.checkoutService.createOrder(orderToCreate));
  }
  getOrderToCreate(cart: ICart): IOrderToCreate {
    const deliveryMethod = this.checkoutForm?.get('deliveryForm')?.get('deliveryMethod')?.value as IDeliveryMethod;
    const shipToAddress = this.checkoutForm?.get('addressForm')?.value as IAddress;
    console.log("Address and delivery methods:");
    console.log(shipToAddress);
    console.log(deliveryMethod);
    if(!deliveryMethod || !shipToAddress) {
      throw new Error("There is an issue with card address or delivery method selection");
    }
    return {
      basketId: cart.id,
      shipToAddress: shipToAddress,
      deliveryMethodId: deliveryMethod.deliveryMethodId
    };
  }
  get paymentFormComplete() {
    return this.checkoutForm?.get('paymentForm')
    && this.cardNumberComplete
    && this.cardExpiryComplete
    && this.cardCvcComplete;
  }

}
