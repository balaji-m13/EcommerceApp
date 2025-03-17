import { Route } from "@angular/router";
import { OrderComponent } from "./order/order.component";
import { AuthGuard } from "../auth/auth.guard";
import { OrderDetailsComponent } from "./order-details/order-details/order-details.component";

export const ORDER_ROUTES: Route[] = [
    {path: '', component: OrderComponent, canActivate: [AuthGuard]},
    {path: ':orderId', component: OrderDetailsComponent, canActivate: [AuthGuard]}
]