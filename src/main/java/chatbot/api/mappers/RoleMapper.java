package chatbot.api.mappers;

import chatbot.api.role.domain.RoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper {

    // 허브유저 레코드 저장
    void save(RoleDto hubUser);


    // heqSeq 와 useSeq 데이터를 인자로 받음
    // -> 추가하려는 사용자가 이미 해당 허브를 사용하고 있는지 조회
    RoleDto getRoleInfo(@Param("hubSeq") Long hubSeq, @Param("userSeq") Long userSeq);


    // hubSeq + adminSeq를 가지고 hubUser 삭제
    void deleteRole(RoleDto hubUser);
}
