package egovframework.com.mip.mva.sp.config.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName PushVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 푸시 설정 VO
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
public class PushVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 푸시서버주소 */
	private String pushServer;
	/** 민간개방 푸시서버주소 */
	private String opnPushServer;
	/** 푸시 연계시스템 코드(MS-CODE) */
	private String pushMsCode;
	/** 푸시유형 */
	private String pushType;

}
