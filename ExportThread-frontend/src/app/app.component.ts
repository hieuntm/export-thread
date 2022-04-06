import { Component, OnInit } from '@angular/core';
import { ProductService } from './service/product-service';
import { FileResponse } from './dto/upload-file-response';
import { ProductType } from './dto/product-type';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title = 'ExportThread-frontend';

  responseImport: FileResponse = {} as FileResponse;

  constructor(private productService: ProductService) { }

  dropdownList: ProductType[] = [];
  selectedItems: ProductType[] = [];
  dropdownSettings = {};

  ngOnInit() {
    this.getProductType();
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'code',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true,
    };
  }

  onItemSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }

  getProductType(){
    let tempTypes: any = [];
    this.productService.getProductTypes().subscribe((res: any) => {
      res.forEach((element: any) => {
        tempTypes.push({name: element, code: element});
      });
      this.dropdownList = tempTypes;
    }), (error: any) => {
      console.log(error);
    }
  }

  handleClick(event: any){
    var types = this.selectedItems.map(function(el) {
      return el.code;
    });
    this.productService.getProductFile(types).subscribe((res: any) => {
      this.responseImport = res.body as FileResponse;
      if(this.responseImport.type === 'OK'){
        const byteCharacters = atob(this.responseImport.fileBase64);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        const file = new Blob([byteArray], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8'});
        const fileURL = URL.createObjectURL(file);
        const a = document.createElement('a');
        a.href = fileURL;
        a.target = '_blank';
        a.download = 'File_Result.zip'
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(fileURL);
      }
    }), (error: any) => {
      console.log(error);
    }
  }
}
