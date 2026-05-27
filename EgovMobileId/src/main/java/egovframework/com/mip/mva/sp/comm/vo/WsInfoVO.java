package egovframework.com.mip.mva.sp.comm.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName WsInfoVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 웹소켓 정보 VO
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
public class WsInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 연결 URL */
	private String connUrl;
	/** 연결 Timeout 시간 */
	private Integer timeout;
	/** 거래코드 */
	private String trxcode;
	/** 서비스코드 */
	private String svcCode;
	/** 메세지 */
	private String result;
	/** 상태 */
	private String status;

}
