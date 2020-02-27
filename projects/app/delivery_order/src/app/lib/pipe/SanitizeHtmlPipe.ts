import { Pipe, PipeTransform, SecurityContext } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'sanitizeHtml'
})
export class SanitizeHtmlPipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) { }
  transform(value: any): any {
    console.log(' Sanitizing ');
    console.log(value);
    return this.sanitizer.bypassSecurityTrustHtml(value);
  }
}
