import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { Cart, ICart, ICartTotals } from "../../shared/model/cart";
import { environment } from "../../../environment";
import { HttpClient } from "@angular/common/http";
import { map } from "rxjs";
import { response } from "express";
import { IProduct } from "../../shared/model/product";
import { ICartItem } from "../../shared/model/cart";
import { ProductItemComponent } from "../../shop/product-item/product-item.component";


@Injectable({
    providedIn: 'root'
})

export class CartService {
    private cartSource =  new BehaviorSubject<ICart | null>(null);
    cart$ = this.cartSource.asObservable();
    private cartTotalsSource = new BehaviorSubject<ICartTotals | null>(null);
    cartTotal$ = this.cartTotalsSource.asObservable();
    basketUrl = environment.apiBasketUrl;

    constructor(private httpClient: HttpClient) {}

    createCart(): Cart {
        const cart = new Cart();
        localStorage.setItem('angular_cart_id', cart.id);
        return cart;
    }

    getCurrentCart() {
        return this.cartSource.value;
    }

    calculateTotals() {
        const cart = this.cartSource.value;
        if(!cart) return;
        const shipping = cart.shippingPrice;
        const subTotal = cart.items.reduce((a, b) => (b.unitPrice * b.quantity) + a, 0);
        const total = subTotal + shipping;
        this.cartTotalsSource.next({shipping: shipping, total, subTotal});
    }

    getCart(id: string) {
        return this.httpClient.get<ICart>(this.basketUrl + "/" + id)
        .pipe(
            map((cart: Cart) => {
            this.cartSource.next(cart);
            this.calculateTotals()
        }))
        ;
    }

    setCart(cart: ICart) {
        return this.httpClient.post<ICart>(this.basketUrl, cart)
        .subscribe((response: ICart) => {
            this.cartSource.next(response);
            this.calculateTotals();
        },
        error => {
            console.log(error);
        }
        )
    }

    addItemToCart(product: IProduct, quantity = 1){
        console.log("ADDING CONTINUES")
        const itemToAdd= this.mapProductToCartItem(product);
        console.log(itemToAdd)
        const cart = this.getCurrentCart() ?? this.createCart();
        cart.items = this.addOrUpdateItem(cart.items, itemToAdd, quantity);
        console.log("CARRRTTT")
        console.log(cart)
        this.setCart(cart);
        
      }
    addOrUpdateItem(items: ICartItem[], item: ICartItem, quantity: number): ICartItem[] {
        const itemFound = items.find(i => i.productId == item.productId);
        if(itemFound) {
            itemFound.quantity += quantity;
        } else {
            item.quantity = quantity;
            items.push(item);
        }
        return items;
    }

    mapProductToCartItem(product: IProduct) : ICartItem{
        console.log("UNIT PRICE")
        console.log(product.unitPrice)
        return {
            productId: product.productId,
            title: product.name,
            unitPrice: product.unitPrice,
            quantity: 1,
            imageUrl: product.imageUrl,
            brandName: product.brandName,
            categoryName: product.categoryName
        }
    }

    incrementItemQuantity(item: ICartItem) {
        const cart = this.getCurrentCart();
        if(cart) {
            const foundItemIndex = cart.items.findIndex(i => i.productId == item.productId);
            cart.items[foundItemIndex].quantity++;
            this.setCart(cart);
        }
    }

    decrementItemQuantity(item: ICartItem) {
        const cart = this.getCurrentCart();
        if(cart) {
            const foundItemIndex = cart.items.findIndex(i => i.productId == item.productId);
            if(cart.items[foundItemIndex].quantity > 1) {
                cart.items[foundItemIndex].quantity--;
                this.setCart(cart);
            }
            else
                this.removeItemFromCart(cart.items[foundItemIndex]);
        }
    }

    removeItemFromCart(item: ICartItem) {
        const cart = this.getCurrentCart();
        if(cart && cart.items.some(i=> i.productId == item.productId)) {
            cart.items = cart.items.filter(i => i.productId != item.productId);
            if(cart.items.length > 0) {
                this.setCart(cart);
            }
            else {
                this.deleteCart(cart);
            }
        } 
    }

    deleteCart(cart: ICart) {
        return this.httpClient.delete(this.basketUrl + '/' + cart.id, {responseType: 'text'}).subscribe({
            next: () =>{
                this.cartSource.next(null);
                this.cartTotalsSource.next(null);
                localStorage.removeItem('angular_cart_id');
            }
        })
    }
}