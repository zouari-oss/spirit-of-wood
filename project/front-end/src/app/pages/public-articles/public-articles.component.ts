import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { Article } from '../../core/models/article.model';
import { PublicArticleService } from '../../core/services/public-article.service';

@Component({
  selector: 'app-public-articles',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './public-articles.component.html',
  styleUrl: './public-articles.component.scss'
})
export class PublicArticlesComponent {
  readonly searchControl = new FormControl('', { nonNullable: true });
  readonly allArticles = signal<Article[]>([]);
  readonly searchTerm = signal('');
  readonly articles = computed(() => {
    const query = this.searchTerm().trim().toLowerCase();
    const items = this.allArticles();

    if (!query) {
      return items;
    }

    return items.filter(
      (article) =>
        article.name.toLowerCase().includes(query) || article.description.toLowerCase().includes(query)
    );
  });
  readonly loading = signal(true);
  readonly errorMessage = signal<string | null>(null);
  readonly hasResults = computed(() => this.articles().length > 0);
  readonly currentYear = new Date().getFullYear();

  constructor(private readonly publicArticleService: PublicArticleService) {
    this.searchControl.valueChanges
      .pipe(debounceTime(250), distinctUntilChanged())
      .subscribe((value) => {
        this.searchTerm.set(value);
      });

    void this.loadArticles();
  }

  async loadArticles(): Promise<void> {
    this.loading.set(true);
    this.errorMessage.set(null);

    try {
      const data = await this.publicArticleService.list();
      this.allArticles.set(data);
    } catch (error) {
      const message =
        error instanceof HttpErrorResponse
          ? error.error?.message ?? 'Failed to load articles'
          : 'Failed to load articles';
      this.errorMessage.set(message);
    } finally {
      this.loading.set(false);
    }
  }

  trackById(_: number, article: Article): string {
    return article.id;
  }
}
