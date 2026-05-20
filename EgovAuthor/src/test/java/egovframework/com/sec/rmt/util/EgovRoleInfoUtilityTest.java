package egovframework.com.sec.rmt.util;

import egovframework.com.sec.rmt.entity.RoleInfo;
import egovframework.com.sec.rmt.service.RoleInfoVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EgovRoleInfoUtility 단위 테스트.
 * Spring 컨텍스트 없이 엔티티↔VO 변환 로직을 검증한다.
 */
@ExtendWith(MockitoExtension.class)
class EgovRoleInfoUtilityTest {

    @Test
    @DisplayName("roleEntityToVO: 엔티티의 모든 필드가 VO에 올바르게 복사된다")
    void roleEntityToVO_copiesAllFields() {
        RoleInfo entity = new RoleInfo();
        entity.setRoleCode("web-0001");
        entity.setRoleNm("관리자 역할");
        entity.setRolePttrn("/admin/**");
        entity.setRoleDc("관리자 전용 접근 역할");
        entity.setRoleTy("web");
        entity.setRoleSort("1");
        entity.setRoleCreatDe("2024-01-01 00:00:00");

        RoleInfoVO vo = EgovRoleInfoUtility.roleEntityToVO(entity);

        assertThat(vo).isNotNull();
        assertThat(vo.getRoleCode()).isEqualTo("web-0001");
        assertThat(vo.getRoleNm()).isEqualTo("관리자 역할");
        assertThat(vo.getRolePttrn()).isEqualTo("/admin/**");
        assertThat(vo.getRoleDc()).isEqualTo("관리자 전용 접근 역할");
        assertThat(vo.getRoleTy()).isEqualTo("web");
        assertThat(vo.getRoleSort()).isEqualTo("1");
        assertThat(vo.getRoleCreatDe()).isEqualTo("2024-01-01 00:00:00");
    }

    @Test
    @DisplayName("roleVOToEntity: VO의 모든 필드가 엔티티에 올바르게 복사된다")
    void roleVOToEntity_copiesAllFields() {
        RoleInfoVO vo = new RoleInfoVO();
        vo.setRoleCode("mtd-0001");
        vo.setRoleNm("메서드 역할");
        vo.setRolePttrn("egovframework.com.service.*");
        vo.setRoleDc("서비스 레이어 역할");
        vo.setRoleTy("method");
        vo.setRoleSort("2");
        vo.setRoleCreatDe("2024-06-15 12:00:00");

        RoleInfo entity = EgovRoleInfoUtility.roleVOToEntity(vo);

        assertThat(entity).isNotNull();
        assertThat(entity.getRoleCode()).isEqualTo("mtd-0001");
        assertThat(entity.getRoleNm()).isEqualTo("메서드 역할");
        assertThat(entity.getRolePttrn()).isEqualTo("egovframework.com.service.*");
        assertThat(entity.getRoleDc()).isEqualTo("서비스 레이어 역할");
        assertThat(entity.getRoleTy()).isEqualTo("method");
        assertThat(entity.getRoleSort()).isEqualTo("2");
        assertThat(entity.getRoleCreatDe()).isEqualTo("2024-06-15 12:00:00");
    }

    @Test
    @DisplayName("roleEntityToVO: null 필드도 그대로 VO에 반영된다")
    void roleEntityToVO_nullFieldsPreserved() {
        RoleInfo entity = new RoleInfo();
        entity.setRoleCode("web-0002");

        RoleInfoVO vo = EgovRoleInfoUtility.roleEntityToVO(entity);

        assertThat(vo).isNotNull();
        assertThat(vo.getRoleCode()).isEqualTo("web-0002");
        assertThat(vo.getRoleDc()).isNull();
        assertThat(vo.getRoleCreatDe()).isNull();
    }

    @Test
    @DisplayName("roundTrip: entity→VO→entity 변환 후 roleCode가 유지된다")
    void roundTrip_roleCodePreserved() {
        RoleInfo original = new RoleInfo();
        original.setRoleCode("pct-0001");
        original.setRoleNm("포인트컷 역할");
        original.setRoleTy("pointcut");

        RoleInfoVO vo = EgovRoleInfoUtility.roleEntityToVO(original);
        RoleInfo restored = EgovRoleInfoUtility.roleVOToEntity(vo);

        assertThat(restored.getRoleCode()).isEqualTo(original.getRoleCode());
        assertThat(restored.getRoleNm()).isEqualTo(original.getRoleNm());
        assertThat(restored.getRoleTy()).isEqualTo(original.getRoleTy());
    }
}
