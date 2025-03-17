import { Component, OnInit } from '@angular/core';
import { IOrder } from '../../shared/model/order';
import { OrderService } from '../order.service';
import { CommonModule, CurrencyPipe, DatePipe, NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-order',
  imports: [CommonModule, NgFor, RouterLink, CurrencyPipe, DatePipe],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent implements OnInit {
  orders: IOrder[] = [];
  constructor(private orderService: OrderService) {}

  getOrders() {
    this.orderService.getOrdersForUser().subscribe({
      next: orders => {
        this.orders = orders;
        console.log(orders);
      }
    })
  }
  ngOnInit(): void {
    this.getOrders();
  }

}
