// Import Libraries
import { ActivatedRoute, Params } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

// Import Services
import { UrlService } from '../../services/url.service';

// Import Models

import { Url } from '../../domain/balgruuf_db/url';

// START - USED SERVICES
/*
 *	urlService.create
 *		PARAMS: 
 *		
 *
 *	urlService.get
 *		PARAMS: 
 *		
 *
 *	urlService.update
 *		PARAMS: 
 *		
 *
 */
// END - USED SERVICES

// START - REQUIRED RESOURCES
/*
 * urlService  
 */
// END - REQUIRED RESOURCES

/**
 * Edit component for urlEdit
 */
@Component({
    selector: 'url-edit',
    templateUrl : './url-edit.component.html',
    styleUrls: ['./url-edit.component.css']
})
export class UrlEditComponent implements OnInit {

    item: Url;
    model: Url;
    
    constructor(
        private urlService: UrlService,
        private route: ActivatedRoute,
        private location: Location) {
        // Init item
        this.item = new Url();
    }

    ngOnInit(): void {
            this.route.params.subscribe(param => {
                let id: string = param['id'];
                if (id !== 'new') {
                    // Get item from server 
                    this.urlService.get(id).subscribe(item => this.item = item);
                }
            });
    }

    /**
     * Save Item
     */
    save (formValid:boolean, item: Url): void{
        if (formValid) {
            if(item._id){
                this.urlService.update(item).subscribe(data => this.goBack());
            } else {
                this.urlService.create(item).subscribe(data => this.goBack());
            }  
        }
    }

    /**
     * Go Back
     */
    goBack(): void {
        this.location.back();
    }
    

}