import { Component, OnInit, Input, ChangeDetectorRef, Output, EventEmitter, ChangeDetectionStrategy, ElementRef, ViewChild } from '@angular/core';
import { BaseFormControlComponent } from '../base-form-control/base-form-control.component';
import { DialogService } from '../../service/dialog.service';

@Component({
  selector: 'fileupload',
  templateUrl: './fileUpload.component.html',
  styleUrls: ['./fileupload.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FileuploadComponent extends BaseFormControlComponent implements OnInit {


  @Output() dataEmitter = new EventEmitter();
  @Input() dndType: boolean;
  @Input() maxFileSizeMb: number = 0;
  @Input() allowedFileTypes: any[];
  @Input() defaultLoaded: boolean;
  @Input() defaultFileName: string;
  @Input() defaultFileId: string;
  @Input() labelPos = 'T';
  filesAdded: boolean = false;
  @ViewChild('fileInput') fileInput: ElementRef;


  uploadTypeMapping = {
    pdf: 'pdf', xls: 'xls', png: 'png', jpeg: 'jpeg', gif: 'gif',
    txt: 'text', xlsx: 'spreadsheetml', csv: 'ms-excel', zip: 'zip'
  };

  normalType = true;
  fileName = '';
  isLoading = false;
  fileLoaded = false;
  dngclass = 'dnd-area';

  constructor(private _dialogService: DialogService) { super(); }



  uploadFile(fn) {
    if (fn) {
      this.filesAdded = true;
      this.fileName = fn;
      this.fileLoaded = true;
    }
  }


  ngOnInit() {
    if (this.dndType) {
      this.normalType = false;
    }

    if (this.defaultLoaded) {
      this.filesAdded = true;
      this.fileName = this.defaultFileName;
      this.fileLoaded = true;

    }
  }

  processFile(inpFile) {

    const reader = new FileReader();
    this.isLoading = true;
    if (inpFile) {
      const file = inpFile;
      if (((file.size) / 1000000) > this.maxFileSizeMb) {
        this.fileInput.nativeElement.value = '';
        this._dialogService.alert(' File size exceeds maximum permissible size of  ' + this.maxFileSizeMb + 'Mb ');
        this.dngclass = 'dnd-area';
        return;
      } else if (!this.checkValidFileTypes(file)) {
        this.fileInput.nativeElement.value = '';
        this._dialogService.alert(' Not a valid file Type, allowed types are  ' + this.allowedFileTypes);
        this.dngclass = 'dnd-area';
        return;
      }
      this.fileName = file.name;
      reader.readAsDataURL(file);
      // reader.readAsBinaryString(file);

      reader.onload = () => {
        if (this.dndType) {
          this.fileLoaded = true;
        }
        this.dataEmitter.emit(reader.result);
        this.isLoading = false;
        this.filesAdded = true;
        this.dngclass = 'dnd-area';
      };
    }
  }
  onFilesAdded(event) {
    this.processFile(event.target.files[0]);
  }

  dragOver(ev) {
    this.dngclass = 'dnd-area drop-area-hover';
    ev.preventDefault();
  }

  dragLeave(e) {
    this.dngclass = 'dnd-area';
  }

  drag(ev) {
    this.dngclass = 'drop-object';
    ev.dataTransfer.setData('text', ev.target.id);
  }

  drop(ev) {
    ev.preventDefault();
    this.processFile(ev.dataTransfer.files[0]);
    // const reader = new FileReader();
    // reader.readAsDataURL(ev.dataTransfer.files[0]);
    // this.fileName = ev.dataTransfer.files[0].name;
    // reader.onload = () => {
    //   this.fileLoaded = true;
    //   this.dataEmitter.emit(reader.result);
    //   this.isLoading = false;
    //   this.dngclass = 'dnd-area';

    // };
    // ev.target.appendChild(document.getElementById(data));
  }

  checkValidFileTypes(file: File) {
    let validType = false;
    if (!this.allowedFileTypes)
      return true;

    for (let i = 0; i < this.allowedFileTypes.length; i++) {
      if (file.type.indexOf(this.uploadTypeMapping[this.allowedFileTypes[i]]) !== -1) {
        validType = true;
        break;
      }
    }
    return validType;

  }

  public clearUpload(ev?: any) {
    if (ev) {
      ev.preventDefault();
      ev.stopPropagation();
    }
    this.dataEmitter.emit(null);
    this.fileLoaded = false;
    this.defaultLoaded = false;
    this.dngclass = 'dnd-area';
    (<HTMLInputElement>document.getElementById('fileElem')).value = '';

  }

  downLoadFile(ev) {
    ev.preventDefault();
    ev.stopPropagation();
    const data = { download: true, name: this.defaultFileName, id: this.defaultFileId };
    this.dataEmitter.emit(data);
  }
}


export enum UploadType {
  pdf = 'pdf',
  img = 'image',
  png = 'png',
  jpeg = 'jpeg',
  gif = 'gif',
  txt = 'text',
  xlsx = 'spreadsheetml',
  csv = 'ms-excel'
}
