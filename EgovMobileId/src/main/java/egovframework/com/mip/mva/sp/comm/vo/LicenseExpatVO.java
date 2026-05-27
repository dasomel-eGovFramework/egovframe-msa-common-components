package egovframework.com.mip.mva.sp.comm.vo;

import com.raonsecure.omnione.core.data.iw.Unprotected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 모바일 재외국민 신원확인증에 담긴 정보들의 VO
 * @author 표준프레임워크 박성완
 * @since 2025.02.13
 * @version 4.3
 * <pre>
 *
 * ==================================================
 * DATE            AUTHOR          NOTE
 * ==================================================
 * 2025.02.13,     박성완           표준프레임워크 v4.3 - 최초생성
 *
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
public class LicenseExpatVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;              // 이름
	private String ihidnum;           // 주민등록번호
	private String address;           // 주소
	private String birth;             // 생년월일
	private String dlphotoimage;      // 사진이미지

	private String expatno;           // 재외국민번호
	private String expatnosn;         // 재외국민번호 일련번호
	private String rephonno;          // 대표 보훈번호
	private String title;             // 타이틀
	private String usrname;           // 영문 성
	private String givennames;        // 영문 이름
	private String passporttype;      // 종류
	private String countrycode;       // 발행국
	private String passportno;        // 여권번호
	private String nationality;       // 국적
	private String issude;            // 발급일
	private String expirdate;         // 만료일
	private String sex;               // 성별
	private String bearersigna;       // 여권소지인의서명
	private String authority;         // 발행관청
	private String emblnm;            // 관할공관
	private String ci;                // 연계정보

	/**
	 * Privacy 목록과 License VO를 매핑
	 *
	 * @MethodName mapFromList
	 * @param privacy Privacy목록
	 * @return LicenseExpatVO
	 */
	public LicenseExpatVO mapFromList(List<Unprotected> privacy) {
		LicenseExpatVO vo = new LicenseExpatVO();

		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < privacy.size(); i++) {
			map.put(
				privacy.get(i).getType(),
				privacy.get(i).getValue().equals("") ? null : privacy.get(i).getValue()
			);
		}

		vo.setName(map.get("name"));
		vo.setIhidnum(map.get("ihidnum"));
		vo.setDlphotoimage(map.get("dlphotoimage"));
		vo.setAddress(map.get("address"));
		vo.setBirth(map.get("birth"));
		vo.setExpatno(map.get("expatno"));
		vo.setExpatnosn(map.get("expatnosn"));
		vo.setRephonno(map.get("rephonno"));
		vo.setTitle(map.get("title"));
		vo.setUsrname(map.get("usrname"));
		vo.setGivennames(map.get("givennames"));
		vo.setPassporttype(map.get("passporttype"));
		vo.setCountrycode(map.get("countrycode"));
		vo.setPassportno(map.get("passportno"));
		vo.setNationality(map.get("nationality"));
		vo.setIssude(map.get("issude"));
		vo.setExpirdate(map.get("expirdate"));
		vo.setSex(map.get("sex"));
		vo.setBearersigna(map.get("bearersigna"));
		vo.setAuthority(map.get("authority"));
		vo.setEmblnm(map.get("emblnm"));
		vo.setCi(map.get("ci"));

		return vo;
	}

}
