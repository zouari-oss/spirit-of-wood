import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Article } from '../models/article.model';

@Injectable({ providedIn: 'root' })
export class PublicArticleService {
  private readonly baseUrl = 'http://localhost:8080/api/articles';

  constructor(private readonly http: HttpClient) {}

  list(query: string): Promise<Article[]> {
    const q = query.trim();
    const url = q ? `${this.baseUrl}?q=${encodeURIComponent(q)}` : this.baseUrl;
    return firstValueFrom(this.http.get<Article[]>(url));
  }
}
