package chatbot.api.mappers;

import chatbot.api.user.domain.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {

    Optional<UserInfoDto> getUser(Long id);


    Optional<UserInfoDto> getUserByProviderId(String providerId);


    UserInfoDto getUserByUserId(Long userId);


    UserInfoDto getUserByEmail(String email);


    void save(UserInfoDto user);


    void update(UserInfoDto user);

}
