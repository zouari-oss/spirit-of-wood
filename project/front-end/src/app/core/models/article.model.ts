export interface Article {
  id: string;
  name: string;
  description: string;
  image: string;
  price: number;
}

export interface ArticleRequest {
  name: string;
  description: string;
  image: string;
  price: number;
}
