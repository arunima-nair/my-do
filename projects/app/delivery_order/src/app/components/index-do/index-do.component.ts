import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import {  FormGroup  } from '@angular/forms';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { TranslateService } from '@ngx-translate/core';
import {  NavigationExtras } from '@angular/router';
import { SecurityInfoService} from 'src/app/lib/service/security-info.service';
@Component({
  selector: 'app-index-do',
  templateUrl: './index-do.component.html',
  styleUrls: ['./index-do.component.css']
})
export class IndexDoComponent extends BaseComponent  implements OnInit {
  myForm: FormGroup;
  selectedValue: string;
  agentType:String;
  constructor(private _cd: ChangeDetectorRef, private _ts: TranslateService,private _securityInfo: SecurityInfoService) {
    super();
    this._pageHeaderService.updateHeader('Amend Bol Details');
}
reset($event){
  this.myForm.reset();
 }

onSubmit($event) {
  console.log($event.value);
  const data = {
    event
    };
    const navextras: NavigationExtras = {
      queryParams: {'amend': true}
    };
    if($event.value==="2"){
     
      this._router.navigate(['/uploadBL'],navextras);
        
      
     }
    else{
     
        this._router.navigate(['/search'],navextras);
    
   }
  }
   
  ngOnInit() {
    this.agentType= this._securityInfo.getAgentType();
    console.log( this.agentType);
    this.myForm  = this.formBuilder.group({
      type: []
  });
  }

}
