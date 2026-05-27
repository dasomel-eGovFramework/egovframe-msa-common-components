package egovframework.com.mip.mva.sp.comm.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName T540VO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 푸시 시작용 VO
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
public class T540VO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Command */
	private String cmd;
	/** 모드 */
	private String mode;
	/** 서비스코드 */
	private String svcCode;
	/** 앱 */
	private String appCode;
	/** 성명 */
	private String name;
	/** 전화번호 */
	private String telno;

	/** Base64로 인코딩된 M200 메세지 */
	private String m200Base64;

}
