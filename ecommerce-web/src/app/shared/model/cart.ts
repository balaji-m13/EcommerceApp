import {createId} from '@paralleldrive/cuid2'

export interface ICart {
    id: string;
    items: ICartItem[];
    shippingPrice: number;
}


export interface ICartItem {
    productId: number;
    title: string;
    unitPrice: number;
    quantity: number;
    imageUrl: string;
    brandName: string;
    categoryName: string;
}

export class Cart implements ICart {
    id = createId();
    items: ICartItem[] = [];
    shippingPrice: number = 0;
}

export interface ICartTotals {
    subTotal: number;
    shipping: number;
    total: number;
}