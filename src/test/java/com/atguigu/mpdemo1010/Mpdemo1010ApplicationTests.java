package com.atguigu.mpdemo1010;

import com.atguigu.mpdemo1010.entity.User;
import com.atguigu.mpdemo1010.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class Mpdemo1010ApplicationTests {

    @Autowired
    UserMapper userMapper;

    //查询user表中所有数据
    @Test
   public void FindAll() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    //添加操作
    @Test
    public void addUser() {
       User user = new User();
       user.setName("岳不1");
       user.setAge(70);
       user.setEmail("lilei@qq.com");

        int insert = userMapper.insert(user);
        System.out.println("insert:"+insert);
    }

    //修改操作
    @Test
    public void updateUser(){
        User user = new User();
        user.setId(1314455831047094273L);
        user.setAge(120);
        int row = userMapper.updateById(user);
        System.out.println(row);
    }

    //测试乐观锁
    @Test
    public void testOptimisticLocker(){
        //根据id查询数据
        User user = userMapper.selectById(1314480586999468034L);
        //进行修改
        user.setAge(200);
        userMapper.updateById(user);
    }
    //多个id的批量查询
    @Test
    public void testSelectDemo1(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
        System.out.println(users);
    }


    @Test
    public void testSelectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Jone");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //分页查询
    @Test
    public void testPage(){
        //创建page对象
        //传入两个参数    当前页和每一页的记录数
        Page<User>  page = new Page<>(1,3);
        //调用mp分页查询的方法
        //mp底层会把数据封装到page对象里
        userMapper.selectPage(page,null);

        //通过page对象 获取分页数据
        System.out.println(page.getCurrent()); //获取当前页
        System.out.println(page.getRecords()); //当前页数据list集合
        System.out.println(page.getSize());  //每页显示记录数
        System.out.println(page.getTotal()); //总记录数
        System.out.println(page.getPages()); //总页数

        System.out.println(page.hasNext()); //是否有下一页
        System.out.println(page.hasPrevious()); //是否有上一页
    }

    //删除操作   物理删除
    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(1315543311787827202L);
        System.out.println(result);
    }

    //批量删除   物理删除
    @Test
    public void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(2,3));
        System.out.println(result);
    }

    //mp实现复杂查询操作
    @Test
    public void testSelectQuery() {
     //创建一个 QueryWrapper对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //通过QueryWrapper设置条件
        //ge、gt、le、lt、isNull、isNotNull
        //查询age>=30的记录
        //第一个参数是要查询的字段的名称，第二个参数是设置的值
        //wrapper.ge("age",30);

        //eq,ne
       // wrapper.eq("name","lilei");
      //  wrapper.ne("name","lilei");

        //between
        //查询年龄70-200
       // wrapper.between("age",70,200);

        //like
       // wrapper.like("name","岳");

        //orderByDesc  降序
        //wrapper.orderByDesc("id");

        //last 最后可以拼接语句
        //wrapper.last("limit 1");

        //指定要查询的列
        wrapper.select("name");
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);









    }






}
