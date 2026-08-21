export interface JwtPayload {
  sub: string;
  role: string;
  id: number;
  iss: string;
  exp: number;
  iat: number;
  jti: string;
}