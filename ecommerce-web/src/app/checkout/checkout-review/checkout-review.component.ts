import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { ICart } from '../../shared/model/cart';
import { CartService } from '../../cart/cart/cart.service';
import { CartDetailsComponent } from '../../core/cart-details/cart-details.component';
import { AsyncPipe, CommonModule, NgIf } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { CdkStepper } from '@angular/cdk/stepper';

@Component({
  selector: 'app-checkout-review',
  imports: [CartDetailsComponent, AsyncPipe, CommonModule],
  templateUrl: './checkout-review.component.html',
  styleUrl: './checkout-review.component.scss'
})
export class CheckoutReviewComponent {

  @Input() insideStepper?:CdkStepper;
  cart$:Observable<ICart | null>;

  constructor(private cartService: CartService, private toastrService: ToastrService) {
    this.cart$ = this.cartService.cart$;
  }

  createPaymentIntent() {
    this.cartService.createPaymentIntent()
      .subscribe({
        next: () => {
          this.toastrService.success("Payment intent created successfully");
          this.insideStepper?.next();
        },
        error: (error: string | undefined) => {
          this.toastrService.error( JSON.stringify(error));
        }
      })
  }
}
