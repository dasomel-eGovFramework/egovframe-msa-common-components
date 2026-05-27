package egovframework.com.mip.mva.sp.comm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName TrxInfoVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 거래정보 VO
 *
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * 2025.02. 13.    박성완           표준프레임워크 v4.3 - toString 메서드 추가
 * </pre>
 */
@Getter
@Setter
@ToString
public class TrxInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 거래코드 */
	private String trxcode;
	/** 서비스코드 */
	private String svcCode;
	/** 모드 */
	private String mode;
	/** nonce(presentType=1) */
	private String nonce;
	/** zkpNonce(presentType=2) */
	private String zkpNonce;
	/** VP 검증 결과 여부 */
	private String vpVerifyResult;
	/** VP */
	private String vp;
	/** 거래상태코드(0001: 서비스요청, 0002: profile요청, 0003: VP 검증요청, 0004: VP 검증완료) */
	private String trxStsCode;
	/** profile 송신일시(M310) */
	private String profileSendDt;
	/** 이미지 송신일시(M320) */
	private String imgSendDt;
	/** VP 수신일시(M400) */
	private String vpReceptDt;
	/** 오류 내용 */
	private String errorCn;
	/** 등록일시 */
	private String regDt;
	/** 수정일시 */
	private String udtDt;

}
