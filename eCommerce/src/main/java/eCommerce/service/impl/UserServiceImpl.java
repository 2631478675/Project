package eCommerce.service.impl;

import eCommerce.common.Response;
import eCommerce.dao.UserMapper;
import eCommerce.pojo.User;
import eCommerce.service.IUserService;
import eCommerce.util.DateUtil;
import eCommerce.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Date;

import static eCommerce.common.Const.Role.ROLE_CUSTOME;

@Service("IUserService")
public class UserServiceImpl implements IUserService{

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response<User> login(User user) {
        if(user != null){
            String password = userMapper.selectPasswordByUsername(user.getUsername());
            logger.info(password);
            if(password.equals(MD5Util.MD5EncodeUtf8(user.getPassword()))){
                User userSelect = userMapper.selectUserByUsername(user.getUsername());
                logger.info(userSelect.toString());
                return Response.createBySuccess("成功登陆",userSelect);
            }
            else {
                return Response.createByErrorMessage("密码错误或者用户名为空");
            }

        }else{
            return Response.createByErrorMessage("用户输入为空");
        }
    }

    @Override
    public Response<String> logon(User user) {

        if(user.getUsername() != null){
            //判断是否用户名重复
            int usernameCount= userMapper.selectCountByUsername(user.getUsername());
            if(usernameCount != 0){
                return Response.createByErrorMessage("用户名已经存在");
            }
            //判断邮箱是否重复
            //todo

            //为用户分配角色并保存到数据库中,0代表普通用户，1代表管理员
            user.setRole(ROLE_CUSTOME);
            //将用户的密码进行MD5加密
            user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            //调用方法，将此用户加载到数据库中
            int result = userMapper.insert(user);
            if(result != 0){
                return Response.createBySuccessMessage("注册成功");
            }
            return Response.createByErrorMessage("注册失败");
        }
        return Response.createByErrorMessage("注册者用户名为空");


    }

    @Override
    public Response<String> selectQuestionByUsername(User user) {
        if(user.getUsername() != null){
            int usernameCount= userMapper.selectCountByUsername(user.getUsername());
            if(usernameCount == 0){
                return Response.createByErrorMessage("用户名不存在");
            }
            //通过用户名查找问题
            String question = userMapper.selectQuestionByUsername(user.getUsername());
            //判断当时用户注册时是否填写问题
            if(question == null){
                return Response.createByErrorMessage("用户注册时未填写问题");
            }
            return Response.createBySuccess(question);
        }
        return Response.createByErrorMessage("用户名为空");
    }

    @Override
    public Response<String> judgeAnswerIsRight(User user) {
        if(user.getUsername() != null && user.getAnswer() != null && user.getQuestion() != null){
            //根据问题和用户名锁定数据库中的答案（注意，不要只使用问题）

            String answer = userMapper.judgeAnswerIsRight(user.getUsername(),user.getQuestion());

            if(answer.equals(user.getAnswer())){
                return Response.createBySuccessMessage("答案验证成功，请进行重置密码");
            }
            return Response.createByErrorMessage("答案错误");

        }
        return Response.createByErrorMessage("用户名或者问题或者答案为空");
    }

    @Override
    public Response<String> resetPassword(User user) {
        if(user.getUsername() != null && user.getAnswer() != null && user.getQuestion() != null && user.getPassword() != null){
            //根据问题和用户名锁定数据库中的答案（注意，不要只使用问题）

            int count = userMapper.resetPassword(user.getUsername(),user.getQuestion(),user.getAnswer(),user.getPassword());

            System.out.println(count);
            if(count != 0){
                return Response.createBySuccessMessage("密码更改成功");
            }
            return Response.createByErrorMessage("密码更改失败");

        }
        return Response.createByErrorMessage("用户名或者问题或者答案或重置密码为空");
    }


}
