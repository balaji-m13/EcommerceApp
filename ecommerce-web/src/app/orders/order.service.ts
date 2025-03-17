import { Injectable } from "@angular/core";
import { environment } from "../../environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { IOrder } from "../shared/model/order";
import { KeycloakService } from "keycloak-angular";
import { from, switchMap, tap } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class OrderService {
    baseUrl = environment.apiUrl;
    constructor(private http: HttpClient, private keycloakService: KeycloakService) {}
    getOrdersForUser() {

        return from(this.keycloakService.getToken()).pipe(
            // 2) Switch from the token Observable to the actual HTTP POST:
            switchMap((token: string) => {
              // Build the headers with your Bearer token
              const headers = new HttpHeaders({
                Authorization: `Bearer ${token}`
              });
        
              // 3) Now return the POST call with the new headers
              return this.http.get<IOrder[]>(this.baseUrl + 'orders', { headers });
            }),
            // 4) Optionally inspect the response or do additional logic
            tap(newOrder => {
              console.log('Order created:', newOrder);
            })
          );
    }
    getOrderDetailed(orderId: number) {
        console.log("ORDER FOR ORDER ID")
        console.log(orderId);
        return from(this.keycloakService.getToken()).pipe(
            // 2) Switch from the token Observable to the actual HTTP POST:
            switchMap((token: string) => {
              // Build the headers with your Bearer token
              const headers = new HttpHeaders({
                Authorization: `Bearer ${token}`
              });
        
              // 3) Now return the POST call with the new headers
              return this.http.get<IOrder>(this.baseUrl + 'orders/' + orderId, {headers});
            }),
            // 4) Optionally inspect the response or do additional logic
            tap(newOrder => {
              console.log('Order created:', newOrder);
            })
          );
    }
}