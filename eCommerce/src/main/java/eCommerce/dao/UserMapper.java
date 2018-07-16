package eCommerce.dao;

import eCommerce.pojo.User;
import eCommerce.pojo.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);


    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    String selectPasswordByUsername(String username);

    User selectUserByUsername(String username);

    int selectCountByUsername(String username);

    String selectQuestionByUsername(String username);

    String judgeAnswerIsRight(@Param("username")String username,@Param("question") String question);

    int resetPassword(@Param("password") String password ,@Param("username")String username);

    int resetPasswordByOldPassword(@Param("newPassword") String newPassword , @Param("oldPassword")String oldPassword );
}