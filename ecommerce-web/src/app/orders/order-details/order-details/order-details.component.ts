import { Component, OnInit } from '@angular/core';
import { IOrder } from '../../../shared/model/order';
import { OrderService } from '../../order.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule, CurrencyPipe, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-order-details',
  imports: [CommonModule, NgIf, NgFor, CurrencyPipe, RouterLink],
  templateUrl: './order-details.component.html',
  styleUrl: './order-details.component.scss'
})
export class OrderDetailsComponent implements OnInit {
  order?: IOrder;
  constructor(private orderService: OrderService, private route: ActivatedRoute) {
    console.log("Inside details");
  }
  ngOnInit(): void {
    console.log("Inside details");
    const orderId = this.route.snapshot.paramMap.get('orderId');
    console.log("Order id");
    console.log(orderId);
    orderId && this.orderService.getOrderDetailed(+orderId).subscribe({
      next: order => {
        this.order = order;
        console.log(order);
      }
    })
  }

}
