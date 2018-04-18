// Import Libraries
import { Observable } from 'rxjs/Rx';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { ModalRemoveComponent } from '../../components/modal-remove.component';


// Import Services
import { UrlService } from '../../services/url.service';

// Import Models
import { Url } from '../../domain/balgruuf_db/url';

// START - USED SERVICES
/*
 *	urlService.delete
 *		PARAMS: 
 *		
 *
 *	urlService.list
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

@Component({
    selector: "url-list",
    templateUrl: './url-list.component.html',
    styleUrls: ['./url-list.component.css']
})
export class UrlListComponent implements OnInit {
    
    // Attributes
    list: Url[];
    search: any = {};
    idSelected: string;
    
    // Constructor
    constructor(
        private urlService: UrlService, 
        public dialog: MatDialog) {}

    // Functions
    ngOnInit(): void {
        this.urlService.list().subscribe(list => this.list = list);
    }

    openModal(id: string): void {
        let dialogRef = this.dialog.open(ModalRemoveComponent, {
            width: '250px',
            data: () => {
                // Execute on confirm
                this.urlService.remove(id).subscribe(() => {
                    dialogRef.close();
                });
                this.list = this.list.filter(item => item._id != id);
            }
        });
    }

}