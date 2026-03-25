import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Article, ArticleRequest } from '../../core/models/article.model';
import { ArticleService } from '../../core/services/article.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-admin-dashboard',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent {
  readonly articles = signal<Article[]>([]);
  readonly loading = signal(true);
  readonly saving = signal(false);
  readonly editingId = signal<string | null>(null);
  readonly errorMessage = signal<string | null>(null);
  readonly form;

  constructor(
    private readonly formBuilder: FormBuilder,
    private readonly articleService: ArticleService,
    private readonly authService: AuthService
  ) {
    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      image: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]]
    });
    void this.refreshArticles();
  }

  async refreshArticles(): Promise<void> {
    this.loading.set(true);
    this.errorMessage.set(null);

    try {
      const items = await this.articleService.list();
      this.articles.set(items);
    } catch (error) {
      this.handleHttpError(error, 'Failed to load articles');
    } finally {
      this.loading.set(false);
    }
  }

  edit(article: Article): void {
    this.editingId.set(article.id);
    this.form.patchValue({
      name: article.name,
      description: article.description,
      image: article.image,
      price: article.price
    });
  }

  cancelEdit(): void {
    this.editingId.set(null);
    this.form.reset({ name: '', description: '', image: '', price: 0 });
  }

  async save(): Promise<void> {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    this.errorMessage.set(null);

    const payload: ArticleRequest = {
      name: this.form.controls.name.value?.trim() ?? '',
      description: this.form.controls.description.value?.trim() ?? '',
      image: this.form.controls.image.value?.trim() ?? '',
      price: Number(this.form.controls.price.value ?? 0)
    };

    try {
      const editingId = this.editingId();
      if (editingId) {
        await this.articleService.update(editingId, payload);
      } else {
        await this.articleService.create(payload);
      }

      await this.refreshArticles();
      this.cancelEdit();
    } catch (error) {
      this.handleHttpError(error, 'Failed to save article');
    } finally {
      this.saving.set(false);
    }
  }

  async remove(id: string): Promise<void> {
    this.errorMessage.set(null);

    try {
      await this.articleService.delete(id);
      await this.refreshArticles();
    } catch (error) {
      this.handleHttpError(error, 'Failed to delete article');
    }
  }

  logout(): void {
    this.authService.logout();
  }

  private handleHttpError(error: unknown, fallback: string): void {
    const message =
      error instanceof HttpErrorResponse
        ? error.error?.message ?? fallback
        : fallback;

    this.errorMessage.set(message);

    if (error instanceof HttpErrorResponse && error.status === 401) {
      this.authService.logout();
    }
  }
}
