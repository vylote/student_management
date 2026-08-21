export type Role = 'ROLE_PRINCIPAL' | 'ROLE_TEACHER' | 'ROLE_STUDENT';

export interface Account {
  id: number;
  username: string;
  role: Role;
}