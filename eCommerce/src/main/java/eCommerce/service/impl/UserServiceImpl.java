package eCommerce.service.impl;

import eCommerce.common.Response;
import eCommerce.common.Token;
import eCommerce.dao.UserMapper;
import eCommerce.pojo.User;
import eCommerce.service.IUserService;
import eCommerce.util.DateUtil;
import eCommerce.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Date;
import java.util.UUID;

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

                String token = UUID.randomUUID().toString();

                Token.setKey(Token.TOKEN_PREFIX+user.getUsername(),token);

                 logger.info(token);
                return Response.createBySuccess("请重置密码",token);
            }
            return Response.createByErrorMessage("答案错误");

        }
        return Response.createByErrorMessage("用户名或者问题或者答案为空");
    }

    @Override
    public Response<String> resetPassword(User user,String token) {
        if(user.getUsername() != null && user.getPassword() != null){
            logger.info(token);
            if(StringUtils.isBlank(token)){
                return Response.createByErrorMessage("token为空，需要传递token");
            }else {
                //从缓存中取出token
                String tokenCache = Token.getKey(Token.TOKEN_PREFIX+user.getUsername());
                if(StringUtils.isBlank(tokenCache)){
                    return Response.createByErrorMessage("token过期或者无效");
                }
                if(!StringUtils.equals(tokenCache,token)){
                    return Response.createByErrorMessage("token错误，请重新获取");
                }
                int count = userMapper.resetPassword(MD5Util.MD5EncodeUtf8(user.getPassword()),user.getUsername());

                System.out.println(count);
                if (count > 0) {
                    return Response.createBySuccessMessage("密码更改成功");
                }
                return Response.createByErrorMessage("密码更改失败");
            }
        }
        return Response.createByErrorMessage("用户名或重置密码为空");
    }

    @Override
    public Response<String> resetPasswordAfterLogin(String password, String newPassword ,String oldPassword) {
        //防止越权，一定要拿到用户的旧密码
        //password:前端传来的旧密码
        //newPassword：前端传来的新密码
        //oldPassword：session中的密码
        //查看旧密码是否是对的(session中的旧密码和前端传来的密码比较),user中只有密码
        if (!StringUtils.equals(oldPassword, MD5Util.MD5EncodeUtf8(password))) {
            return Response.createByErrorMessage("旧密码错误");
        }
        //更新密码
        int result = userMapper.resetPasswordByOldPassword(MD5Util.MD5EncodeUtf8(newPassword), oldPassword);
        if (result > 0) {
            return Response.createBySuccessMessage("更新密码成功");
        }
        return Response.createByErrorMessage("更新密码失败");
    }

    @Override
    public Response<User> updateInfo(User user) {
        //注意id和username是不能更改的,如果user中为空的属性，默认不进行修改，不修改密码
        int resultCount = userMapper.updateByPrimaryKeySelective(user);
        User updateUser = userMapper.selectByPrimaryKey(user.getId());
        if(resultCount >0 ){
            return Response.createBySuccess("修改个人信息成功",updateUser);
        }
        return Response.createByErrorMessage("修改个人信息失败");
    }

}
