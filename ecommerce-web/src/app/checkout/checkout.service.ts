import { Injectable } from '@angular/core';
import { environment } from '../../environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IDeliveryMethod } from '../shared/model/delivery';
import { map, switchMap, tap } from 'rxjs/operators';
import { IOrder, IOrderToCreate } from '../shared/model/order';
import { from } from 'rxjs';
import { KeycloakService } from "keycloak-angular";


@Injectable({
  providedIn: 'root'
})
export class CheckoutService {
  baseUrl = environment.apiUrl;
  constructor(private httpClient: HttpClient, private keycloakService: KeycloakService) {}

  getDeliveryMethods() {
    return this.httpClient.get<IDeliveryMethod[]> (
      this.baseUrl + 'deliverymethods'
    ).pipe(
      map(dm => {
        console.log(dm);
        return dm.sort((a, b) => b.price - a.price);
      })
    );
  }

  createOrder(order: IOrderToCreate) {
    console.log(order);
    return from(this.keycloakService.getToken()).pipe(
      // 2) Switch from the token Observable to the actual HTTP POST:
      switchMap((token: string) => {
        // Build the headers with your Bearer token
        const headers = new HttpHeaders({
          Authorization: `Bearer ${token}`
        });
  
        // 3) Now return the POST call with the new headers
        return this.httpClient.post<IOrder>(this.baseUrl + 'orders', order, { headers });
      }),
      // 4) Optionally inspect the response or do additional logic
      tap(newOrder => {
        console.log('Order created:', newOrder);
      })
    );
  }

}
