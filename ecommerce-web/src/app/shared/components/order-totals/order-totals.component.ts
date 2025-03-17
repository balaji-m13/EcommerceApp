import { Component, Input } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-order-totals',
  imports: [CurrencyPipe, CommonModule],
  templateUrl: './order-totals.component.html',
  styleUrl: './order-totals.component.scss'
})
export class OrderTotalsComponent {
  @Input() shippingPrice: number = 0;
  @Input() subTotal: number = 0;
  @Input() total: number = 0;
}
