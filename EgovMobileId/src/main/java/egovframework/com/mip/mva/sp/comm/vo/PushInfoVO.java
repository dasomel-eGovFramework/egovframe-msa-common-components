package egovframework.com.mip.mva.sp.comm.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.vo
 * @FileName PushInfoVO.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description 푸시 요청 VO
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
public class PushInfoVO {

	/** MS-CODE(조폐공사에 등록하여 할당 받음) */
	private String mscode;
	/** 푸시유형 */
	private String pushType;
	/** 성명 */
	private String name;
	/** 전화번호 */
	private String telno;
	/** Base64로 인코딩된 M200메세지 */
	private String data;

	/** 푸시키 */
	private Integer key;
	/** 결과 */
	private Boolean result;
	/** 결과메세지 */
	private String resultMsg;
	/** 오류코드 */
	private Integer errcode;
	/** 오류메세지 */
	private String errmsg;

}
