import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CheckoutSuccessComponent } from './checkout/checkout-success/checkout-success.component';

export const routes: Routes = [
   
    { path: '', redirectTo: 'welcome', pathMatch: 'full' },
     { path: 'welcome', component:HomeComponent },
    {
        path: 'shop',
        loadChildren: () => import('./shop/shop.routes').then(r => r.SHOP_ROUTES)
      },
      /*  { 
        path: 'cart', 
        loadComponent: () => import('./cart/cart.component').then(c => c.CartComponent)
      },   */
      {
        path: 'cart', loadChildren: () => import('./cart/cart/cart.routes').then(r=>r.CART_ROUTES)
      },
      {
        path: 'checkout',
        loadChildren: () => import('./checkout/checkout.routes').then(r => r.CHECKOUT_ROUTES)
      },
      {
        path: 'orders',
        loadChildren: () => import('./orders/routes').then(r => r.ORDER_ROUTES)
      },
      { path: 'checkout/success', component: CheckoutSuccessComponent }
     // { path: '**', redirectTo: 'welcome', pathMatch: 'full' }
   
  ];

