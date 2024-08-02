package org.takim2.insan_kaynaklari_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
	BAD_REQUEST_ERROR(0001,
			"Girilen parametreler hatalıdır. Lütfen düzeltiniz.",
			HttpStatus.BAD_REQUEST),
	INTERNAL_SERVER_ERROR(0002,
			"Aktivasyon işlemleri yapılamıyor. Server Hatası.",
			HttpStatus.INTERNAL_SERVER_ERROR),
	USERNAME_ALREADY_TAKEN(1001,
			"Bu username daha önce kullanılmış. Yeniden deneyiniz.",
			HttpStatus.BAD_REQUEST),
	EMAIL_OR_PASSWORD_WRONG(1002,
			"Email veya parola yanlış.",
			HttpStatus.BAD_REQUEST),
	PASSWORDS_NOT_MATCHED(1003,
			"Girdiğiniz parolalar uyuşmamaktadır. Lütfen kontrol ediniz.",
			HttpStatus.BAD_REQUEST),
	EMAIL_ALREADY_TAKEN(1004,"Bu email daha önce alınmış. Yeniden deneyiniz.",
			HttpStatus.BAD_REQUEST),
	INVALID_TOKEN(2001,
			"Token geçersizdir.",
			HttpStatus.BAD_REQUEST),
	TOKEN_CREATION_FAILED(2002,
			"Token yaratmada hata meydana geldi.",
			HttpStatus.SERVICE_UNAVAILABLE),
	TOKEN_VERIFY_FAILED(2003,
			"Token verify etmede bir hata meydana geldi.",
			HttpStatus.SERVICE_UNAVAILABLE),
	TOKEN_ARGUMENT_NOTVALID(2004,
			"Token argümanı yanlış formatta.",
			HttpStatus.BAD_REQUEST),
	ACCOUNT_NOT_ACTIVE(3005,
			"Hesabınız aktif değil.",
			HttpStatus.BAD_REQUEST),
	USER_NOT_FOUND(3006,
			"Kullanıcı bulunamadı.",
			HttpStatus.BAD_REQUEST),
	ACTIVATION_CODE_MISMATCH(3007,
			"Aktivasyon kodu hatalı.",
			HttpStatus.BAD_REQUEST),
	ACCOUNT_ALREADY_ACTIVE(3008,
			"Hesabınız zaten aktif durumda.",
			HttpStatus.BAD_REQUEST),
	ACCOUNT_BANNED(3007,
			"Hesabınız banlanmış. Aktivasyon işlemi gerçekleştirilemez.",
			HttpStatus.BAD_REQUEST),
	ACCOUNT_DELETED(3007,
			"Hesabınız silinmiş. Aktivasyon işlemi gerçekleştirilemez",
			HttpStatus.BAD_REQUEST),
	ACCOUNT_ALREADY_DELETED(3008,
			"Hesabınız daha önce silinmiş. Silme işlemi gerçekleştirilemez",
			HttpStatus.BAD_REQUEST);
	private Integer code;
	private String message;
	private HttpStatus httpStatus;

}