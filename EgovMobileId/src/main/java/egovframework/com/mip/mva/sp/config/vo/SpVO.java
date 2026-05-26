package egovframework.com.mip.mva.sp.config.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName SpVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description SP 설정 VO
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
public class SpVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** SP 서버주소 */
	private String serverDomain;
	/** SP BI 이미지 URL */
	private String biImageUrl;
	/** SP BI Data(Base64 인코딩) */
	private String biImageBase64;
	/** CI 제공 여부 */
	private Boolean isCi;
	/** 전화번호 제공 여부 */
	private Boolean isTelno;
	/** 필수 Privacy 확인 여부 */
	private Boolean checkRequiredPrivacy;
	/** 만료일자 확인 여부 */
	private Boolean checkVcExpirationDate;

}
