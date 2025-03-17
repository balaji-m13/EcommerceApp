import { Injectable } from '@angular/core';
import { ReplaySubject } from 'rxjs';
import { UserProfile } from '../shared/model/userProfile';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _authenticated = false;
  private userProfileSource = new ReplaySubject<UserProfile>(1);
  public userProfile$ = this.userProfileSource.asObservable();
  private _loggedIn = false;
  private profile: UserProfile | undefined;
  constructor(private keycloakService: KeycloakService) {

   }

    
   public login(redirectUrlSegment?: string): Promise<void>  {
      let redirectUrl = window.location.origin + '/' + (redirectUrlSegment !== undefined ? redirectUrlSegment : '');
      console.log('Starting Keycloak login. Redirect Uri: ' + redirectUrl);
      return this.keycloakService.login({redirectUri: redirectUrl}).then(()=> {
        this.loadUserProfile();
      })
   }
   private loadUserProfile() {
    if(this.keycloakService.isLoggedIn())
     {
        this.keycloakService.loadUserProfile().then(profile => 
          {
            console.log(profile);
            this._authenticated = true;
            this.profile = profile;
            this.userProfileSource.next(profile);
          },
          (error) => 
          {
            this._authenticated = false;
            this.profile = undefined;

          }
          );

     };
  }

  public logout() {
    const protocol = window.location.protocol;
    const port = window.location.port;
    let redirectUrl = "http://localhost:4200/home";
    window.location.href =   "http://localhost:9003/realms/" +
    "oauth2-demo-realm/protocol/openid-connect/logout?redirect_uri=" +redirectUrl;
    this.keycloakService.logout(redirectUrl).then(() => 
    {
        console.log("logged out");
        this.keycloakService.clearToken();
    },
    (error) => 
    {
      console.log(error);
    } );
    this._authenticated = false;
    this.profile = undefined;
   
  }

  private textHelperMethod(text: string | null | undefined) : string 
  {
    return !!text ? text : '';
  }
  public getUserName() : string 
  {
    return this.textHelperMethod(this.profile?.username);
  }
  public getEmail() : string{
    return this.textHelperMethod(this.profile?.email);
  }
  public getFullName(): string 
  {
    let firstName = this.textHelperMethod(this.profile?.firstName);
    return (!!firstName ? firstName + ' ' : '') + this.textHelperMethod(this.profile?.lastName);
  }
  get authenticated(): boolean 
  {
    return this._authenticated;
  }
   private async isLoggedIn() {
     this._loggedIn = await this.keycloakService.isLoggedIn();
     console.log("LoggedIn?");
     console.log(this._loggedIn);

   }
   get loggedIn() {
    return this._loggedIn;
   }
}
