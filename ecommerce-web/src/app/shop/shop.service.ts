import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pagination } from '../shared/model/pagination';
import { IBrand, ICategory, IProduct } from '../shared/model/product';
import { environment } from '../../environment';
import { inject, signal } from '@angular/core';
import { ShopParams } from '../shared/model/shopparams';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShopService {

  shopParams: ShopParams = new ShopParams();
  pagination?: Pagination<IProduct[]>;

  constructor(private httpClient: HttpClient) {}

  getProducts() {
    let params = new HttpParams();
    if(this.shopParams.brandId > 0) params = params.append('brandId', this.shopParams.brandId);
    if(this.shopParams.categoryId > 0) params = params.append('categoryId', this.shopParams.categoryId);
    params = params.append('sort', this.shopParams.sort);
    params = params.append('pageIndex', this.shopParams.pageIndex.toString());
    params = params.append('pageSize', this.shopParams.pageSize.toString());
    console.log("PARAMS:")
    console.log(params)
    return this.httpClient.get<Pagination<IProduct[]>>(environment.apiUrl + 'shop/products', {params}).pipe(
      map(response => {
        this.pagination = response;
        console.log(response);
        return response;
      })
    )
  }

  getBrands() {
    return this.httpClient.get<IBrand[]>(environment.apiUrl + 'shop/brands');
  }

  getCategories() {
    return this.httpClient.get<ICategory[]>(environment.apiUrl + 'shop/categories');
  }

  setShopParams(params: ShopParams) {
    this.shopParams = params;
  }

  getShopParams() {
    return this.shopParams;
  }

}
