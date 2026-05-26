package egovframework.com.mip.mva.sp.config.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName DbVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description DB 설정 VO
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
public class DbVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 제조사 */
	private String provider;
	/** 드라이버 */
	private String driverClassName;
	/** Url */
	private String url;
	/** 사용자명 */
	private String username;
	/** 비밀번호 */
	private String password;

}
