export interface PageResponse<T> {
  currentPage: number;
  pageSize: number;
  totalPages: number;
  totalElements: number;
  isLast: boolean;
  data: T[];
}