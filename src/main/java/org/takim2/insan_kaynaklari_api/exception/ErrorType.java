package org.takim2.insan_kaynaklari_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

	USERNAME_ALREADY_TAKEN(5000,"username alınmış", HttpStatus.CONFLICT),
	EMAIL_EXIST(5001,"email alınmış", HttpStatus.CONFLICT),
	USERNAME_IS_NULL(5002,"username null", HttpStatus.NOT_FOUND),
	EMAIL_IS_NULL(5003,"email null", HttpStatus.NOT_FOUND),
	USERNAME_FORMAT_NOT_CORRECT(5004,"username formatı uygun değil",HttpStatus.BAD_REQUEST),
	EMAIL_FORMAT_NOT_CORRECT(5005,"email fortmat not correct", HttpStatus.BAD_REQUEST),
	PASSWORD_MUST_BE_MIN_8_CHARACTER(5006,"password must be min 8 character", HttpStatus.BAD_REQUEST),
	BAD_REQUEST_ERROR(1001,"Girilen parametreler hatalıdır. Lütfen düzeltiniz.",HttpStatus.BAD_REQUEST),
	USERNAME_OR_PASSWORD_WRONG(1001,"kullanıcı adı ya da parola hatalı",HttpStatus.BAD_REQUEST),
	PASSWORD_MISMATCH(1001,"password uıyuşmuyor",HttpStatus.BAD_REQUEST),
	UNAUTHORIZED_REQUEST(5005,"unauthorized request", HttpStatus.UNAUTHORIZED),
	INVALID_TOKEN(5006,"invalid token", HttpStatus.UNAUTHORIZED),
	TOKEN_CREATION_FAILED(5007,"token creation failed", HttpStatus.SERVICE_UNAVAILABLE),
	TOKEN_VERIFY_FAILED(5007,"token verify failed", HttpStatus.SERVICE_UNAVAILABLE),
	TOKEN_FORMAT_NOT_ACCEPTABLE(5007,"token format not acceptable", HttpStatus.BAD_REQUEST),
	ACTIVATION_CODE_WRONG(5008,"Aktivasyon Kodu geçersiz",HttpStatus.BAD_REQUEST),
	ACCOUNT_IS_NOT_ACTIVE(5009,"account is not active", HttpStatus.BAD_REQUEST),
	ACCOUNT_IS_BANNED(5009,"account is banned", HttpStatus.FORBIDDEN),
	ACCOUNT_IS_DELETED(5009,"account is deleted", HttpStatus.NOT_FOUND),
	ACCOUNT_IS_ALREADY_DELETED(5009,"account is akready deleted", HttpStatus.CONFLICT),
	ACCOUNT_ALREADY_ACTIVATED(5009,"account already activated", HttpStatus.CONFLICT),
	USER_NOT_FOUND(5009,"user not found", HttpStatus.NOT_FOUND),
	COMPANY_NOT_FOUND(5009,"company not found", HttpStatus.NOT_FOUND),
	USER_SERVICE_UNAVAILABLE(5010,"user service unavailable", HttpStatus.SERVICE_UNAVAILABLE),
	USER_SERVICE_CAN_NOT_SAVE_USER_PROFILE(5011,"kayıt sırasında sorun oluştu",HttpStatus.SERVICE_UNAVAILABLE),

	INTERNAL_SERVER_ERROR(0002,
			"Aktivasyon işlemleri yapılamıyor. Server Hatası.",
			HttpStatus.INTERNAL_SERVER_ERROR),

	EMAIL_OR_PASSWORD_WRONG(1002,
			"Email veya parola yanlış.",
			HttpStatus.BAD_REQUEST),
	PASSWORDS_NOT_MATCHED(1003,
			"Girdiğiniz parolalar uyuşmamaktadır. Lütfen kontrol ediniz.",
			HttpStatus.BAD_REQUEST),
	EMAIL_ALREADY_TAKEN(1004,"Bu email daha önce alınmış. Yeniden deneyiniz.",
			HttpStatus.BAD_REQUEST),

	TOKEN_ARGUMENT_NOTVALID(2004,
			"Token argümanı yanlış formatta.",
			HttpStatus.BAD_REQUEST),
	ACCOUNT_NOT_ACTIVE(3005,
			"Hesabınız aktif değil.",
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
			HttpStatus.BAD_REQUEST),
	USER_STATUS_NOT_ACTIVE(5555,"user not active",HttpStatus.BAD_REQUEST);

	private Integer code;
	private String message;
	private HttpStatus httpStatus;
}






