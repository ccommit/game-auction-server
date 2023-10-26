package com.ccommit.gameauctionserver.service;


import com.ccommit.gameauctionserver.dto.User;
import com.ccommit.gameauctionserver.dto.user.RequestUserInfo;
import com.ccommit.gameauctionserver.dto.user.UserType;
import com.ccommit.gameauctionserver.exception.CustomException;
import com.ccommit.gameauctionserver.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


//테스트 클래스가 Mockito를 사용한다는것을 명시하는 어노테이션
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    // 테스트시에만 객체 대신에 Mock객체를 주입하는 어노테이션. 런타임동안 Mock객체가 주입된다.
    @Mock
    private UserMapper userMapper;

    // Mock객체가 주입된 클래스를 사용하게될 클래스. 실제 객체 대신 Mock객체를 주입하여 사용한다.
    @InjectMocks
    private UserService userService;

    User user;
    RequestUserInfo requestUserInfo;

    // 테스트 실행전에 가장 먼저 실행되는 어노테이션.
    @BeforeEach
    public void createUser()
    {
        user = new User();
        user.setId(1);
        user.setUserId("TestUserId");
        user.setPassword("TestUserPw");
        user.setNickname("NickName");
        user.setPhoneNumber("010-0000-1111");
        user.setUserLevel(100);
        user.setGold(10000);
        user.setUserType(UserType.USER);

        requestUserInfo = new RequestUserInfo();
        requestUserInfo.setId(user.getId());
        requestUserInfo.setUserId(user.getUserId());
        requestUserInfo.setNickname(user.getNickname());
        requestUserInfo.setPhoneNumber(user.getPhoneNumber());
        requestUserInfo.setUserLevel(user.getUserLevel());
        requestUserInfo.setGold(user.getGold());
        requestUserInfo.setUserType(user.getUserType());
    }

    @Test
    @DisplayName("회원가입시 중복된 아이디가 없을시에 FALSE를 반환")
    public void ExistTestSuccess()
    {
        when(userMapper.isExistId(user.getUserId())).thenReturn(false);

        assertEquals(userService.isExistId(user.getUserId()),false);
    }

    @Test
    @DisplayName("회원가입시 중복된 아이디가 있을경우 TRUE반환, 예외발생")
    public void ExistTestFail()
    {
        when(userMapper.isExistId(user.getUserId())).thenReturn(true);

        assertThrows(CustomException.class, () -> userService.isExistId(user.getUserId()));
    }

    @Test
    @DisplayName("중복된 아이디 검색 후 회원가입")
    public void signUpTestSuccess()
    {
        //반환형이 void형일 경우 doNothing를 사용한다.
        doNothing().when(userMapper).createUser(any(User.class));

        userService.createUser(user);

        verify(userMapper).createUser(any(User.class));
    }


    @Test
    @DisplayName("로그인시 아이디와 패스워드가 일치한 정보가 있을시에 TRUE 반환")
    public void compareUserIDandPWSuccess(){
        when(userMapper.getID(user.getUserId(),user.getPassword())).thenReturn(user.getId());

        assertEquals(userService.compareUserInfo(user.getUserId(), user.getPassword()),true);
    }

    @Test
    @DisplayName("로그인시 아이디와 패스워드가 일치한 정보가 없을시에 예외가 발생한다")
    public void compareUserIDandPWFail(){
        when(userMapper.getID(user.getUserId(),user.getPassword())).thenReturn(null);

        assertThrows(CustomException.class, () -> userService.compareUserInfo(user.getUserId(),user.getPassword()));
    }

    @Test
    @DisplayName("로그인에 성공한 경우 PW를 제외한 정보를 가지고있는 RequestUserInfo DTO클래스를 반환한다")
    public void loginUser(){
        when(userMapper.readUserInfo(user.getUserId())).thenReturn(requestUserInfo);

        assertEquals(userService.findUserInfoByID(user.getUserId()),requestUserInfo);
    }

    @Test
    @DisplayName("회원정보를 변경에 성공하는 경우 변경된 회원 정보를 반환한다")
    public void updateUserInfo(){
        RequestUserInfo updateRequestUserInfo = new RequestUserInfo();
        updateRequestUserInfo.setId(user.getId());
        updateRequestUserInfo.setNickname("NewNickName");
        updateRequestUserInfo.setPhoneNumber("010-1111-2222");

        userMapper.updateUser(updateRequestUserInfo);
        when(userMapper.readUserInfo(user.getUserId())).thenReturn(updateRequestUserInfo);

        RequestUserInfo resultUserInfo = userService.findUserInfoByID(user.getUserId());

        assertEquals(resultUserInfo.getNickname(),"NewNickName");
        assertEquals(resultUserInfo.getPhoneNumber(),"010-1111-2222");

    }
}
