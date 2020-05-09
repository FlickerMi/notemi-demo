package cn.notemi.demo.repository;

import cn.notemi.demo.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Title：UserRepository
 * Description：
 *
 * @author Flicker
 * @create 2020-05-04 21:23
 **/
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByNickname(String nickname);
}
