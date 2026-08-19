# Kiến trúc thư mục Frontend (Angular 17 — Standalone)

Tài liệu này giải thích cách tổ chức mã nguồn theo 3 phân hệ: `core/`, `shared/`, `features/`.
Mục tiêu: khi tạo file mới, bạn chỉ cần trả lời vài câu hỏi bên dưới để biết ngay nên đặt vào đâu, không cần phân vân.

---

## Câu hỏi cốt lõi

> **"Cái này biết gì về nghiệp vụ (business), và nó được dùng ở đâu?"**

Trả lời được câu này, bạn sẽ tự suy ra được nên đặt file vào `core/`, `shared/`, hay `features/`.

---

## 1. `core/` — Hạ tầng chạy ngầm, chỉ có 1 bản duy nhất

### Bản chất
Những thứ **chạy nền, tự động**, không ai "gọi tay" mỗi lần dùng. Chạy 1 lần khi app khởi động và tồn tại xuyên suốt vòng đời ứng dụng.

### Đặc điểm nhận diện
- Chỉ có **đúng 1 instance** trong toàn app (Singleton — `providedIn: 'root'`)
- **Không hiển thị UI** (guard, interceptor, service quản lý state toàn cục)
- Không import thủ công vào từng feature — nó tự chạy nền

### Câu hỏi test nhanh
> "Nếu tôi xoá 1 feature bất kỳ đi, cái này còn cần tồn tại không?"
> → Nếu **có** (vẫn cần cho các feature khác, cho toàn app) → `core/`

### Cấu trúc
```
core/
├── guards/          # Chặn/cho phép truy cập route (vd: authGuard, roleGuard)
├── interceptors/     # Tự động can thiệp mọi HTTP request/response (vd: gắn JWT token)
└── services/         # Service singleton toàn cục (vd: AuthService, TokenService)
```

### Ví dụ thực tế
| Tên file | Vì sao thuộc `core/` |
|---|---|
| `auth.service.ts` | Quản lý trạng thái đăng nhập — chỉ 1 người dùng đang login tại 1 thời điểm, toàn app cùng dùng chung |
| `auth.guard.ts` | Chặn route, chạy tự động mỗi lần chuyển trang, không phân biệt feature nào |
| `api.interceptor.ts` | Tự động gắn token vào mọi request, không ai gọi nó thủ công |

---

## 2. `shared/` — Đồ nội thất chung, không biết gì về nghiệp vụ

### Bản chất
Các component/pipe/directive **thuần hiển thị**, giống một cái nút bấm, một khung loading — **không biết mình đang ở trang nào, phục vụ ai**.

### Đặc điểm nhận diện
- Nhận dữ liệu qua `@Input()`, trả kết quả qua `@Output()` — hoàn toàn "câm" (dumb component)
- Không tự gọi API, không tự biết logic nghiệp vụ
- Đem dùng cho 1 project **hoàn toàn khác** vẫn chạy được y nguyên, không cần sửa

### Câu hỏi test nhanh
> "Nếu tôi bóc component này ra dùng cho project khác (vd: project bán hàng online), nó có chạy được ngay không cần sửa gì?"
> → Nếu **có** → `shared/`

### Cấu trúc
```
shared/
├── components/       # UI thuần, tái sử dụng (vd: LoadingSpinner, ConfirmDialog, Header, Footer)
├── directives/       # Custom directive dùng chung
├── pipes/            # Custom pipe dùng chung (vd: FormatDate, SafeHtml)
└── models/
```

### Ví dụ thực tế
| Tên file | Vì sao thuộc `shared/` |
|---|---|
| `loading-spinner.component.ts` | Chỉ hiện icon xoay, không biết đang loading cái gì |
| `confirm-dialog.component.ts` | Nhận `title`, `message` qua Input, trả `confirmed: boolean` qua Output — không biết context |
| `format-date.pipe.ts` | Định dạng ngày tháng, không liên quan sinh viên/giáo viên |

### ⚠️ Bẫy hay gặp
Một component tên `StudentCard` (hiển thị thông tin sinh viên) — dù được nhiều nơi dùng, nhưng nó **biết về khái niệm "Student"** (biết field `mssv`, `gpa`...) → **KHÔNG** phải `shared/`. Phải để trong `features/student/components/`, vì nó gắn chặt với nghiệp vụ sinh viên.

---

## 3. `features/[tên-feature]/` — Lãnh thổ riêng của 1 nghiệp vụ

### Bản chất
Mọi thứ **biết và phục vụ riêng cho 1 domain nghiệp vụ cụ thể** (sinh viên, giáo viên, đăng nhập...) — kể cả khi nó không có route riêng.

### Đặc điểm nhận diện
- Có nhắc đến khái niệm nghiệp vụ cụ thể (Student, Score, ClassRoom...)
- Chỉ dùng trong đúng 1 feature đó, feature khác không đụng tới

### Cấu trúc con trong mỗi feature (Type-based)
```
features/student/
├── components/       # Component con, KHÔNG có route riêng, nhúng vào page
├── pages/            # Component CÓ route riêng (gắn trực tiếp với URL)
├── services/         # Service/API riêng cho feature này
├── models/           # Interface dữ liệu riêng cho feature này
└── student.routes.ts # Khai báo route nội bộ của feature
```

### Ví dụ thực tế
| Tên file | Vì sao & đặt ở đâu |
|---|---|
| `student-dashboard.component.ts` | Có route `/student` → `features/student/pages/` |
| `score-table.component.ts` | Bảng điểm, chỉ dùng trong dashboard, không có route riêng → `features/student/components/` |
| `student.service.ts` | Gọi API lấy danh sách sinh viên, biết khái niệm Student → `features/student/services/` |
| `student.model.ts` | Định nghĩa `interface Student { mssv, name, gpa... }` → `features/student/models/` |

---

## Bảng quyết định nhanh

| Câu hỏi | Trả lời | → Đặt vào |
|---|---|---|
| Chạy nền tự động, chỉ 1 instance, không có UI? | Có | `core/` |
| Có UI nhưng hoàn toàn không biết nghiệp vụ, dùng lại được ở project khác? | Có | `shared/` |
| Có nhắc đến khái niệm nghiệp vụ cụ thể (Student, Score...)? | Có | `features/[tên]/` |
| Trong `features/`, có route riêng không? | Có → `pages/` <br> Không → `components/` |
| Service có lưu state/logic toàn cục (đăng nhập, token, notification)? | Có → `core/services/` <br> Không, chỉ phục vụ 1 feature → `features/[tên]/services/` |
| Model/Interface dùng chung nhiều feature hay chỉ 1 feature? | Dùng chung → `shared/models/` <br> Riêng 1 feature → `features/[tên]/models/` |

---

## Luyện tập — Tự trả lời trước khi xem đáp án

1. `NotificationToastComponent` — hiện thông báo "Lưu thành công" ở góc màn hình, dùng ở cả trang sinh viên lẫn giáo viên
2. `TeacherService` — gọi API `/api/teachers`
3. `RoleGuard` — chặn sinh viên không cho vào trang giáo viên
4. `ScoreInputFormComponent` — form nhập điểm, chỉ giáo viên dùng, không có route riêng

<details>
<summary>Xem đáp án</summary>

1. `shared/components/` — thuần UI, không biết context, tái sử dụng nhiều nơi
2. `features/teacher/services/` — biết khái niệm Teacher, chỉ phục vụ feature này
3. `core/guards/` — chạy nền, singleton, chặn route toàn cục
4. `features/teacher/components/` — biết nghiệp vụ (Score), chỉ dùng trong feature teacher, không có route riêng

</details>

---

## Lưu ý về naming convention

Project này dùng **Angular CLI 17**, giữ nguyên quy ước đặt tên mặc định của CLI:
- `ten-file.component.ts`, `ten-file.service.ts`, `ten-file.guard.ts`, `ten-file.interceptor.ts`, `ten-file.pipe.ts` (dấu chấm truyền thống)

*(Không áp dụng quy ước mới của Angular 20 — bỏ suffix, dùng dấu gạch ngang — vì CLI 17 không tự sinh theo chuẩn đó.)*