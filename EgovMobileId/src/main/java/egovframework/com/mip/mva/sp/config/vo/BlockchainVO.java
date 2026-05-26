package egovframework.com.mip.mva.sp.config.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName BlockchainVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 블록체인 설정 VO
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
public class BlockchainVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 블록체인 계정 */
	private String account;
	/** 블록체인 서버 도메인 */
	private String serverDomain;
	/** Connect Timeout */
	private Integer connectTimeout;
	/** Read Timeout */
	private Integer readTimeout;
	/** 캐시 사용여부 */
	private Boolean useCache;
	/** SDK 상세로그여부 */
	private Boolean sdkDetailLog;

}
