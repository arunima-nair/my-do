import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoggingService {

  projectLogLevel = LogLevel.DEBUG;
  constructor() { }

  log(message: string, logLevel: LogLevel = LogLevel.DEBUG) {
     if (logLevel >= this.projectLogLevel) {
       console.log(message);
     }
  }
}

export enum LogLevel {
  DEBUG = 1,
  INFO = 2,
  ERROR = 3
}
