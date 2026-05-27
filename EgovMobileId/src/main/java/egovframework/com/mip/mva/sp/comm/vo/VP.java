package egovframework.com.mip.mva.sp.comm.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName VP.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description VP 정보 VO
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
public class VP implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 제공유형 */
	private Integer presentType;
	/** 암호화유형 */
	private Integer encryptType;
	/** 키유형 */
	private Integer keyType;
	/** 인증유형 */
	private List<String> authType;
	/** DID */
	private String did;
	/** Nonce */
	private String nonce;
	/** ZKP Nonce */
	private String zkpNonce;
	/** 유형 */
	private String type;
	/** 암호화된 검증 요청 데이터 */
	private String data;

}
