import { Injectable } from '@angular/core';
import { HttpRequestService } from './http-request.service';
import { LoginInfo } from '../classes/LoginInfo';


@Injectable({
  providedIn: 'root'
})
export class SecurityInfoService {

  private loginInfo: LoginInfo;
  private _remoteUrl: string;
  constructor(private _httpService: HttpRequestService) {
    this.loginInfo = new LoginInfo();
  }

  public isLoggedIn(): boolean {
    return this.loginInfo.access_token != null;
  }

  public getToken(): string {
    return this.loginInfo.access_token;
  }

  public logOut() {
    this.loginInfo = {};
  }

  public getAgentType(): string {
    return this.loginInfo.agent_type;
  }

  public getAgentCode(): string {
    return this.loginInfo.agent_code;
  }

  public getUserType(): string {
    return this.loginInfo.user_type;
  }
  private loginNotExpired() {
    if (!this.loginInfo.expires_in_seconds) {
      return true;
    }
    let timeDiff: number;
    timeDiff = (new Date().getTime() - this.loginInfo.created_Time.getTime()) / 1000;

    return (timeDiff < this.loginInfo.expires_in_seconds);

  }
  private refreshToken() {
    const data: any = this._httpService.getData(this._remoteUrl, true)
      .subscribe((data) => {
        this.loginInfo = {
          access_token: data.token, refresh_token: data.refreshToken,
          expires_in_seconds: data.refreshIntervalSeconds, created_Time: new Date(), agent_type: data.agentType, agent_code: data.agentCode, user_type: data.userType
        };

        this.updateRefreshTime();
      });
  }
  public async login(url) {
    this._remoteUrl = url;
    if (this.loginInfo.access_token && this.loginNotExpired()) {
      console.log(' Already Logged in');
      return true;
    }
    console.log(' Logging in ');
    const data: any = await this._httpService.getDataSync(url, true);
    this.loginInfo.access_token = data.token;
    this.loginInfo = {
      access_token: data.token, refresh_token: data.refreshToken,
      expires_in_seconds: data.refreshIntervalSeconds, created_Time: new Date(), agent_type: data.agentType, agent_code: data.agentCode, user_type: data.userType
    };
    this.updateRefreshTime()
    if (this.loginInfo.access_token)
      return true;
    else
      return false;
  }
  private updateRefreshTime() {
    setTimeout(() => {
      this.refreshToken();
    }, 1800000);
  }
}
