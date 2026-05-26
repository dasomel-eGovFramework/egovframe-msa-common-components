package egovframework.com.mip.mva.sp.config.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName DidWalletFileVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description DID 파일 설정 VO
 *
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * </pre>
 */
@Getter
@Setter
@ToString
public class DidWalletFileVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Wallet 파일 경로 */
	private String keymanagerPath;
	/** Wallet 파일 비밀번호 */
	private String keymanagerPassword;
	/** 서명 키아이디 */
	private String signKeyId;
	/** 암호화 키아이디 */
	private String encryptKeyId;
	/** DID Document 파일 경로 */
	private String didFilePath;

}
