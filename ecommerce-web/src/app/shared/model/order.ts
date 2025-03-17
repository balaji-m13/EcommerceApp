export interface IAddress {
    firstName: string;
    lastName: string;
    street: string;
    city: string;
    state: string;
    zipCode: string;
}

export interface IOrderToCreate {
    basketId: string;
    deliveryMethodId: number;
    shipToAddress: IAddress;
}

export interface IOrderReturned {
    orderId: number;
    orderDate: string;
    total: number;
    status: string;
}

export interface IOrder {
    orderId: number;
    buyerEmail: string;
    orderDate: string;
    shippingAddress: IAddress;
    deliveryMethod: string;
    shippingPrice: number;
    orderItems: IOrderItem[];
    subTotal: number;
    total: number;
    status: string
}

export interface IOrderItem {
    productId: number;
    productName: string;
    imageUrl: string;
    price: number;
    quantity: number;
}