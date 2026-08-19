export type Role = 'ROLE_PRINCIPAL' | 'ROLE_TEACHER' | 'ROLE_STUDENT';

export interface User {
  id: number;
  username: string;
  fullName: string;
  role: Role;
  avatarUrl?: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  user: User;
}
