import { Component, OnInit, ChangeDetectorRef, ViewChild } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { BaseComponent } from 'src/app/lib/classes/BaseComponent';
import { NotificationMessage, MessageStatus } from 'src/app/lib/classes/NotificationMessage';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { Validator } from '../../lib/classes/field.interface';
import { FileuploadComponent } from 'src/app/lib/components/fileupload/fileupload.component';
import { LangTranslateService } from 'src/app/lib/service/lang-translate.service';
import { SecurityInfoService } from 'src/app/lib/service/security-info.service';
@Component({
  selector: 'app-upload-bl',
  templateUrl: './upload-bl.component.html',
  styleUrls: ['./upload-bl.component.css']
})
export class UploadBlComponent extends BaseComponent implements OnInit {
  myForm: FormGroup;
  flag: boolean;
  clickCount = 0;
  file: any[];
  uploadDocValidation: Validator[];
  clickValue: number;
  allowedFile: any[];
  fileSize: any = 1;
  fileName: string;
  filesizes: number;
  fileDelimtter: string;
  agentType: String;
  agentCode: String;
  userType: string;
  @ViewChild(FileuploadComponent) fileUploadComponent: FileuploadComponent;

  constructor(private _cd: ChangeDetectorRef, private _ts: LangTranslateService, private route: ActivatedRoute, private _securityInfo: SecurityInfoService) {
    super();
    this._translatorService = _ts;
    this.chageDetectorRef = _cd;
    this._pageHeaderService.updateHeader('Upload BOL Details');
    this.agentType = this._securityInfo.getAgentType();
    this.agentCode = this._securityInfo.getAgentCode();
    this.userType = this._securityInfo.getUserType();
  }
  fileUpload($event) {
    //let file = $event;
    /* if (file != null) {
       let x = file.split(";base64");
       file = x[0];
       //let filetype = file.split("data:");
       this.fileUploadComponent.fileLoaded = true;
       // this.checkValidFileTypes(filetype[1]);
       if (!this.fileUploadComponent.checkValidFileTypes(file)) {
         if (this.clickValue === 1) {
           this._dialogService.alert('File should be CSV format!!');
           this.myForm.reset();
           this.fileUploadComponent.dataEmitter.emit(null);
           this.fileUploadComponent.fileLoaded = false;
           this.fileUploadComponent.dngclass = 'dnd-area';
         } else {
           this._dialogService.alert('Invoice File should be ZIP format!!');
           this.myForm.reset();
           this.fileUploadComponent.dataEmitter.emit(null);
           this.fileUploadComponent.fileLoaded = false;
           this.fileUploadComponent.dngclass = 'dnd-area';
       }
         this.fileUploadComponent.dngclass = 'dnd-area';
         return;
       }
     }*/
    //console.log($event)
    //console.log($event);
    console.log(this.fileUploadComponent.fileName)
    this.fileName = this.fileUploadComponent.fileName;
    this.myForm.patchValue({
      uploadDoc: $event,
      fileName: this.fileUploadComponent.fileName
    });
    this.chageDetectorRef.markForCheck();
  }
  reset($event?: any) {
    super.resetForm(this.myForm);
    this.setDefault();
    this.fileUploadComponent.clearUpload('');

  }
  close($event) {
    this._dialogService.confirm('All your changes will be lost. Do you want to proceed?')
      .subscribe(retval => {
        console.log(' Dialog Data ', retval);
        if (retval != 1) {
          return;
        }
        else {
          this._router.navigate(['/homeDO']);
        }
      });
  }
  onSubmit($event) {
    console.log($event);
    console.log('Valid?', this.myForm.valid); // true or false
    super.validateForm(this.myForm);
    const notfnMessage: NotificationMessage = {
      text: '',
      type: MessageStatus.info
    };
    this.notfnService.updateMessage(notfnMessage);
    this.clickCount = this.clickCount + 1;

    console.log('Value', this.myForm.value);

    var url = "";
    console.log(this.clickValue);
    if (this.myForm.valid) {
      if (this.clickValue === 1) {
        url = "/do/app/api/secure/saveBLdetailsUploadFile?fileName=" + this.fileName;
        console.log(this.myForm.value);
        super.postData(url, JSON.stringify(this.myForm.value))
          .subscribe((data) => {
            let confirmStatus = true;
            let monitorPage = false;
            if (data.status === 'success') {
              confirmStatus = true;
              monitorPage = true;

            } else {
              confirmStatus = false;
              monitorPage = false;
            }
            const navextras: NavigationExtras = {
              queryParams: { 'data': data.message, 'success': confirmStatus, 'monitor': monitorPage }
            };
            this.notfnService.clearMessage();
            this._router.navigate(['/confirm'], navextras);
          }, (error) => {
            this.fileUploadComponent.dngclass = 'dnd-area';
            this.notfnService.clearMessage();
            this.setDefault();
            //  this.fileUploadComponent.clearUpload('');
            // this._dialogService.alert('Unable to save data');
            const notfnMessage: NotificationMessage = {
              text: error.error.message,
              type: MessageStatus.error
            };
          });
      } else {
        url = "/do/app/api/secure/saveInvoicedetails?fileName=" + this.fileName;
        super.postData(url, this.myForm.value)
          .subscribe((data) => {
            let confirmStatus = true;
            if (data.status === 'success') {
              confirmStatus = true;
            } else {
              confirmStatus = false;;
            }
            const navextras: NavigationExtras = {
              queryParams: { 'data': data.message, 'success': confirmStatus }
            };
            this.notfnService.clearMessage();
            this._router.navigate(['/confirm'], navextras);
          }, (error) => {
            //   this.setDefault();
            // this.fileUploadComponent.clearUpload('');
            this.notfnService.clearMessage();
            /* const navextras: NavigationExtras = {
               queryParams: { 'data': error.error.message, 'success': false }
             };*/
            //this._router.navigate(['/confirm'], navextras);
            const notfnMessage: NotificationMessage = {
              text: error.error.message,
              type: MessageStatus.error
            };
            this.notfnService.updateMessage(notfnMessage);
            super.scrollToTop();

          });
      }
      console.log(url);
    } else {
      const notfnMessage: NotificationMessage = {
        text: 'Upload Documents Required',
        type: MessageStatus.error
      };
      this.notfnService.updateMessage(notfnMessage);
      super.scrollToTop();
    }
  }
  /* checkValidFileTypes(type) {
     let validType = false;
     console.log(type);
     console.log(this.fileUploadComponent.allowedFileTypes[0]);
     if (type === this.fileUploadComponent.allowedFileTypes[0]) {
       validType = true;
       console.log("type***********");
     }
     return validType;
 
   }*/
  ngOnInit() {
    this.setDefault();
    // this.allowedFile = "xlsx";
    this.allowedFile = ['xlsx', 'xls', 'txt']

    this._httpRequestService.getData('/do/app/api/getFileSize', false).subscribe((res) => {
      this.filesizes = res;
    });
  }
  setDefault() {
    this.clickValue = 1;
    //this.fileUploadComponent.clearUpload('');
    // this.fileUploadComponent.allowedFileTypes = ['xlsx'];
    this.fileUploadComponent.allowedFileTypes = ['xlsx', 'xls', 'pdf', 'txt']
    if (this.route.snapshot.queryParams.amend)
      this.flag = this.route.snapshot.queryParams.amend;
    else
      this.flag = false;
    this.myForm = this.formBuilder.group({
      //  fileName: [''],
      type: this.clickValue,
      uploadDoc: ['', Validators.required],


    });
    this.uploadDocValidation = [
      { name: 'required', validator: Validators.required, message: 'Upload Required' }];
  }

  radioChange($event) {
    console.log($event);
    this.fileUploadComponent.clearUpload();
    this.clickValue = $event;

    if (this.clickValue === 1) {
      this.allowedFile = new Array;
      this.allowedFile.push("xlsx", "xls", "txt")
      this.fileSize = this.filesizes['bolFileSize'];
    } else if (this.clickValue === 2) {
      // this.allowedFile = "pdf";
      this.allowedFile = new Array;
      this.allowedFile.push('pdf');
      this.allowedFile.push('zip')
      this.fileSize = this.filesizes['invoiceFileSize'];
      this._httpRequestService.getData('/do/app/api/getFileDelimtter?agent=' + this.agentCode, false).subscribe((response) => {
        this.fileDelimtter = response['fileDelimtter'];
      });
    }
    // console.log( allowedFile);
    this.fileUploadComponent.allowedFileTypes = this.allowedFile;
  }
}
