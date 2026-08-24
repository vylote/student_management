export interface StudentSearchRequest {
  name?: string;
  code?: string;
  cohort?: string;
  classroom?: string;
  hasAccount?: boolean;
  page: number;
  size: number;
}