import { Component, Input, Output } from '@angular/core';
import { ICartItem } from '../../shared/model/cart';
import { CurrencyPipe, NgIf } from '@angular/common';
import { CommonModule } from '@angular/common';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-cart-details',
  imports: [CurrencyPipe, CommonModule],
  templateUrl: './cart-details.component.html',
  styleUrl: './cart-details.component.scss'
})
export class CartDetailsComponent {

  @Input() items: ICartItem[] = [];
  @Input() isCart: boolean = true;
  @Output('increment') increment: EventEmitter<ICartItem> = new EventEmitter<ICartItem>();

  @Output() decrement: EventEmitter<ICartItem>= new EventEmitter<ICartItem>();
  @Output() remove: EventEmitter<ICartItem>= new EventEmitter<ICartItem>();


  public constructor() {
    console.log("ITEMS")
    console.log(this.items)
  }

  incrementItemQuantity(item: ICartItem) {
    this.increment.emit(item);

  }

  decrementItemQuantity(item: ICartItem) {
    this.decrement.emit(item);
  }

  removeCartItem(item: ICartItem) {
    this.remove.emit(item);
  }

}
