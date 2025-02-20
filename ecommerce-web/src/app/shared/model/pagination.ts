export interface Pagination<T> {
    pageIndex: number;
    pageSize: number;
    totalCount: number;
    productList: T;
}