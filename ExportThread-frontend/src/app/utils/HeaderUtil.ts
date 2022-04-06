import { HttpClient, HttpHeaders } from '@angular/common/http';

export class HeaderUtil {
  public static createHeader(){
    let opt = new HttpHeaders();
    opt = opt.set('Content-Type', 'application/json');
    opt = opt.set("Access-Control-Allow-Origin", "*");
    opt = opt.set("Access-Control-Allow-Methods", "DELETE, POST, GET, OPTIONS");
    opt = opt.set("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
    return opt;
  }
}
