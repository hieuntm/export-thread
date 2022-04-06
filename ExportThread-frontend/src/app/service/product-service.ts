import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HeaderUtil } from '../utils/HeaderUtil';
import { Constants } from '../constant/constant';
import { Observable } from 'rxjs';

@Injectable()
export class ProductService {

  opt = HeaderUtil.createHeader();

  constructor(private http: HttpClient) { }

  public getProductFile(object: any): Observable<any>{
    const data = {
      productTypes: object,
    };
    return this.http.post(Constants.URL_SERVICE + '/v1/product/file', data, {headers: this.opt, observe: 'response'})
  }

  public getProductTypes(): Observable<any>{
    return this.http.get(Constants.URL_SERVICE + '/v1/product/type', {headers: this.opt})
  }

}
