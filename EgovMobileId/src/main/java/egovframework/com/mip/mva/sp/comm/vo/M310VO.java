package egovframework.com.mip.mva.sp.comm.vo;

import egovframework.com.mip.mva.sp.config.ConfigBean;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName M310VO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description Profile 요청 메시지 VO
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
public class M310VO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 유형 */
	private String type = ConfigBean.TYPE;
	/** 버전 */
	private String version = ConfigBean.VERSION;
	/** Command */
	private String cmd = ConfigBean.M310;
	/** Request */
	private String request;
	/** 거래코드 */
	private String trxcode;
	/** Base64로 인코딩된 Profile */
	private String profile;

}
