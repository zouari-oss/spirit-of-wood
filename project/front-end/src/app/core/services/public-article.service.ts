import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Article } from '../models/article.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class PublicArticleService {
  private readonly baseUrl = `${environment.apiBaseUrl}/api/articles`;

  constructor(private readonly http: HttpClient) {}

  list(): Promise<Article[]> {
    return firstValueFrom(this.http.get<Article[]>(this.baseUrl));
  }
}
