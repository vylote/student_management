import { User } from '../models/user.model';

// Mock tài khoản đăng nhập - chưa gọi API, dùng để giả lập xác thực
export const MOCK_USERS: (User & { password: string })[] = [
  {
    id: 1,
    username: 'hieutruong',
    password: '123456',
    fullName: 'Nguyễn Văn Hiệu',
    role: 'ROLE_PRINCIPAL',
    avatarUrl: 'https://i.pravatar.cc/150?img=12',
  },
  {
    id: 2,
    username: 'giangvien',
    password: '123456',
    fullName: 'Trần Thị Mai',
    role: 'ROLE_TEACHER',
    avatarUrl: 'https://i.pravatar.cc/150?img=32',
  },
  {
    id: 3,
    username: 'sinhvien',
    password: '123456',
    fullName: 'Lê Văn An',
    role: 'ROLE_STUDENT',
    avatarUrl: 'https://i.pravatar.cc/150?img=5',
  },
];
