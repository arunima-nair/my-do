import { Component } from '@angular/core';
import { transition, trigger, query,  style,  animate, group, animateChild, state } from '@angular/animations';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    trigger('fadeInOut',
    [
      state('in', style({
        opacity: 1
      })),
      transition('* => *', [
        style({
          opacity: 0
        }),
        animate(1000)])
     ])
  ]
})


export class AppComponent {
  title = 'test-app';
}
