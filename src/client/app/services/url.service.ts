// DEPENDENCIES
import { Observable } from 'rxjs/Rx';
import { Http, RequestOptions, Headers, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

// SECURITY
import { AuthenticationService } from '../security/authentication.service';

// CONFIG
import { config } from "../../config/properties";

// MODEL
import { UrlBaseService } from "./base/url.base.service";
import { Url } from '../domain/balgruuf_db/url';

/**
 * YOU CAN OVERRIDE HERE urlBaseService
 */

@Injectable()
export class UrlService extends UrlBaseService {
    
    // CONSTRUCTOR
    constructor(http: Http, authenticationService: AuthenticationService) {
            super(http, authenticationService);
    }
}