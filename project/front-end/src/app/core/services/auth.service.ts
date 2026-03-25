import { Inject, Injectable, PLATFORM_ID, computed, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { firstValueFrom } from 'rxjs';
import { AdminLoginRequest, AdminLoginResponse } from '../models/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly baseUrl = 'http://localhost:8080/api/admin';
  private readonly tokenSignal = signal<string | null>(null);
  private readonly isBrowser: boolean;

  readonly isAuthenticated = computed(() => !!this.tokenSignal());

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router,
    @Inject(PLATFORM_ID) platformId: object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);

    if (this.isBrowser) {
      this.tokenSignal.set(localStorage.getItem('admin_token'));
    }
  }

  get token(): string | null {
    return this.tokenSignal();
  }

  async login(request: AdminLoginRequest): Promise<void> {
    const response = await firstValueFrom(
      this.http.post<AdminLoginResponse>(`${this.baseUrl}/login`, request)
    );

    if (this.isBrowser) {
      localStorage.setItem('admin_token', response.token);
    }

    this.tokenSignal.set(response.token);
  }

  logout(): void {
    if (this.isBrowser) {
      localStorage.removeItem('admin_token');
    }

    this.tokenSignal.set(null);
    void this.router.navigate(['/admin/login']);
  }
}
