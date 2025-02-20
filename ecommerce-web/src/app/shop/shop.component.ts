import { Component, OnInit } from '@angular/core';
import { ShopService } from './shop.service';
import { response } from 'express';
import { CommonModule } from '@angular/common';
import { IBrand, ICategory, IProduct } from '../shared/model/product';
import { ProductItemComponent } from './product-item/product-item.component';
import { ShopParams } from '../shared/model/shopparams';
import { threadId } from 'worker_threads';
import { Pagination } from '../shared/model/pagination';
import { PaginationComponent } from 'ngx-bootstrap/pagination';


@Component({
  selector: 'app-shop',
  imports: [CommonModule, ProductItemComponent, PaginationComponent],
  templateUrl: './shop.component.html',
  styleUrl: './shop.component.scss'
})
export class ShopComponent implements OnInit {
  products: IProduct[] = []
  categories: ICategory[] = []
  brands: IBrand[] = []
  totalCount = 18
  shopParams: ShopParams;
  sortOptions = [
    {name: 'Aplabetical', value: 'title'},
    {name: 'Price: low to high',  value: 'priceAsc'},
    {name: 'Price: high to low', value: 'priceDesc'}
  ]
  constructor(private shopService: ShopService) {
    this.shopParams = this.shopService.getShopParams()
  }
  ngOnInit(): void {
    this.getProducts()
    this.getBrands()
    this.getCategories()
  }

  getProducts() {
    this.shopService.getProducts().subscribe({
      next: response => {
        if(response != null) {
          this.products = response.productList
          this.shopParams.pageIndex = response.pageIndex;
          this.shopParams.pageSize = response.pageSize;
          this.totalCount = response.totalCount;
          console.log("TotalCount") 
          console.log(this.totalCount)
        }
      },
      error: err => console.log(err)
    }) 
  }

  getBrands() {
    this.shopService.getBrands().subscribe({
      next: response => this.brands = [{brandId: 0, brandName: "All"}, ...response],
      error: err => console.log(err)
    })
  }

  getCategories() {
    this.shopService.getCategories().subscribe({
      next: response => {
        this.categories = [{categoryId: 0, categoryName: "All"}, ...response]
      },
      error: err => console.log(err)
    })
  }

  onBrandSelected(brandId: number) {
    const params = this.shopService.getShopParams();
    params.brandId = brandId;
    this.shopService.setShopParams(params);
    this.shopParams = params;
    this.getProducts() 
  }

  onCategorySelected(categoryId: number) {
    const params = this.shopService.getShopParams();
    params.categoryId = categoryId;
    this.shopService.setShopParams(params);
    this.shopParams = params;
    this.getProducts() 
  }

  onSortSelected(event: any) {
    const params = this.shopService.getShopParams()
    params.sort = event.target.value
    this.shopService.setShopParams(params);
    this.shopParams = params;
    this.getProducts();
  }

  onPageChange(event: any) {
    const params = this.shopService.getShopParams()
    if(params.pageIndex != event.page) {
      params.pageIndex = event.page;
      this.shopService.setShopParams(params);
      this.shopParams = params;
      this.getProducts();
    }
  }

}
