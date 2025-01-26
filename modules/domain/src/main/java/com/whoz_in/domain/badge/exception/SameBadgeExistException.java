package com.whoz_in.domain.badge.exception;

import com.whoz_in.domain.shared.BusinessException;

public class SameBadgeExistException extends BusinessException {
  public static final SameBadgeExistException EXCEPTION = new SameBadgeExistException();
  public SameBadgeExistException() {
    super("5005", "동일한 뱃지가 존재하여 뱃지 생성이 불가합니다.");
  }
}
