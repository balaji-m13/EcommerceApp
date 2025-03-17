import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ICartTotals } from '../shared/model/cart';
import { CartService } from '../cart/cart/cart.service';
import { OrderTotalsComponent } from '../shared/components/order-totals/order-totals.component';
import { AsyncPipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { StepperComponent } from "../shared/components/stepper/stepper.component";
import { CdkStepperModule } from '@angular/cdk/stepper'
import { CheckoutAddressComponent } from './checkout-address/checkout-address.component';
import { CheckoutDeliveryComponent } from './checkout-delivery/checkout-delivery.component';
import { CheckoutReviewComponent } from './checkout-review/checkout-review.component';
import { CheckoutPaymentComponent } from './checkout-payment/checkout-payment.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-checkout',
  imports: [OrderTotalsComponent, AsyncPipe, CommonModule, StepperComponent, CdkStepperModule, ReactiveFormsModule, CheckoutAddressComponent, CheckoutDeliveryComponent, CheckoutReviewComponent, CheckoutPaymentComponent],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.scss'
})
export class CheckoutComponent implements OnInit {
  cartTotals$!: Observable<ICartTotals | null>;
  checkoutForm!: FormGroup;
  constructor(private fb: FormBuilder, private cartService: CartService) {
    this.checkoutForm = this.fb.group({
      addressForm: this.fb.group({
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        street: ['', Validators.required],
        city: ['', Validators.required],
        state: ['', Validators.required],
        zipCode: ['', Validators.required]
      }),
      deliveryForm: this.fb.group({
        deliveryMethod: ['', Validators.required]
      }),
      paymentForm: this.fb.group({
        nameOnCard: ['', Validators.required]
      })
    });
  }

  ngOnInit() {
    this.cartTotals$ = this.cartService.cartTotal$;
  }

  getAddressFormValues() {
    const formValue = this.checkoutForm.get('addressForm')?.value;
    console.log(formValue);
  }

  getDeliveryMethodValue() {
    const cart = this.cartService.getCurrentCart();
    if(cart&&cart.deliveryMethodId) {
      this.checkoutForm.get('deliveryForm')?.get('deliveryMethod')?.patchValue(cart.deliveryMethodId.toString());
      console.log("delivery method");
      console.log(this.checkoutForm.get('deliveryForm')?.get('deliveryMethod')?.value);
    }
  }

}
