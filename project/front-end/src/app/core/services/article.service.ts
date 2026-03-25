import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Article, ArticleRequest } from '../models/article.model';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class ArticleService {
  private readonly baseUrl = 'http://localhost:8080/api/admin/articles';

  constructor(private readonly http: HttpClient, private readonly authService: AuthService) {}

  list(): Promise<Article[]> {
    return firstValueFrom(this.http.get<Article[]>(this.baseUrl, { headers: this.authHeaders() }));
  }

  create(request: ArticleRequest): Promise<Article> {
    return firstValueFrom(
      this.http.post<Article>(this.baseUrl, request, { headers: this.authHeaders() })
    );
  }

  update(id: string, request: ArticleRequest): Promise<Article> {
    return firstValueFrom(
      this.http.put<Article>(`${this.baseUrl}/${id}`, request, { headers: this.authHeaders() })
    );
  }

  delete(id: string): Promise<void> {
    return firstValueFrom(this.http.delete<void>(`${this.baseUrl}/${id}`, { headers: this.authHeaders() }));
  }

  private authHeaders(): HttpHeaders {
    const token = this.authService.token;
    return new HttpHeaders({ Authorization: `Bearer ${token ?? ''}` });
  }
}
