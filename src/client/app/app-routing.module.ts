// DEPENDENCIES
import { NgModule } from '@angular/core';
import { CanActivate, RouterModule, Routes } from '@angular/router';

/* START MY VIEWS IMPORT */
// Do not edit this comment content, it will be overwritten in next Skaffolder generation
import { HomeComponent} from './pages/home/home.component';
import { ConfigEditComponent} from './pages/config-edit/config-edit.component';
import { ConfigListComponent} from './pages/config-list/config-list.component';
import { UrlEditComponent} from './pages/url-edit/url-edit.component';
import { UrlListComponent} from './pages/url-list/url-list.component';
import { UserEditComponent} from './pages/user-edit/user-edit.component';
import { UserListComponent} from './pages/user-list/user-list.component';

/* END MY VIEWS IMPORT */

// SECURITY
import { LoginComponent } from './pages/login/login.component';
import { ManageUserEditComponent } from './security/manage-user/edit-user/manage-user-edit.component';
import { ManageUserListComponent } from './security/manage-user/list-user/manage-user-list.component';
import { ProfileComponent } from './security/profile/profile.component';
import { AuthGuard } from "./security/auth.guard";

/**
 * WEB APP ROUTES
 */
const routes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full'  },

    /* START MY VIEWS */

    { path: 'home',  component: HomeComponent , canActivate: [AuthGuard] },
    { path: 'configs/:id',  component: ConfigEditComponent , canActivate: [AuthGuard] },
    { path: 'configs',  component: ConfigListComponent , canActivate: [AuthGuard] },
    { path: 'urls/:id',  component: UrlEditComponent , canActivate: [AuthGuard] },
    { path: 'urls',  component: UrlListComponent , canActivate: [AuthGuard] },
    { path: 'users/:id',  component: UserEditComponent , canActivate: [AuthGuard] },
    { path: 'users',  component: UserListComponent , canActivate: [AuthGuard] },

 /* END MY VIEWS */
    
    { path: 'home', component: HomeComponent},
    // SECURITY
    { path: 'manage-users',  component: ManageUserListComponent, canActivate: [AuthGuard], data:['ADMIN']},
    { path: 'manage-users/:id',  component: ManageUserEditComponent, canActivate: [AuthGuard], data:['ADMIN']},
    { path: 'profile',  component: ProfileComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent}
];

/**
 * ROUTING MODULE
 */
@NgModule({
    imports: [ RouterModule.forRoot(routes) ],
    exports: [ RouterModule ]
})

export class AppRoutingModule {}