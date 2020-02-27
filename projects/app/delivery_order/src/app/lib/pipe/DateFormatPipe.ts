import { PipeTransform, Pipe } from '@angular/core';


@Pipe({ name: 'dateFormatter' })
export class DateFormatPipe implements PipeTransform {
    transform(text: string, dateFormat: string = 'DD/MM/YYYY'): string {
        console.log(text);
        // const dateParts: any[] = text.split('/');
        // console.log(' IN The Date formatter');
        // const dateObject = new Date(parseInt(dateParts [2], 10), 
        //             parseInt(dateParts [1], 10) - 1, parseInt(dateParts [0], 10));
                    return '';

    }
}