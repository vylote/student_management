import { Component, EventEmitter, Input, Output } from '@angular/core';

/**
 * Component vỏ dùng chung cho mọi Drawer/Modal trong hệ thống.
 * Không biết trước bên trong có gì — dùng ng-content (Content Projection, Day 13)
 * để nơi gọi tự quyết định nội dung, component này chỉ lo phần khung:
 * overlay, đóng khi click ra ngoài, nút ✕.
 */
@Component({
  selector: 'app-modal-shell',
  standalone: true,
  template: `
    <div class="drawer-overlay" (click)="close.emit()">
      <aside class="drawer" [class.assign-modal]="compact" (click)="$event.stopPropagation()">
        <button class="drawer__close" (click)="close.emit()" aria-label="Đóng">✕</button>
        <ng-content></ng-content>
      </aside>
    </div>
  `,
})
export class ModalShellComponent {
  /** true = modal nhỏ (form thêm/sửa), false = drawer rộng (xem chi tiết) */
  @Input() compact = false;
  @Output() close = new EventEmitter<void>();
}