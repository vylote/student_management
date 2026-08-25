import { Component, Input } from '@angular/core';

/**
 * Component nhỏ chỉ nhận 1 @Input, tự quyết định hiển thị gì (Day 7).
 * Dùng lại được ở mọi nơi cần hiển thị trạng thái điểm: Trang Sinh viên,
 * Trang Nhập điểm của Giảng viên, Trang Sinh viên tự xem điểm.
 */
@Component({
  selector: 'app-pass-badge',
  standalone: true,
  template: `
    @if (passed === null) {
      <span class="badge badge-count">Chưa có điểm</span>
    } @else if (passed) {
      <span class="badge badge-pass">Đỗ</span>
    } @else {
      <span class="badge badge-fail">Trượt</span>
    }
  `,
})
export class PassBadgeComponent {
  @Input() passed: boolean | null = null;
}