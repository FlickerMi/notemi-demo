package cn.notemi.demo.repository;

import cn.notemi.demo.model.po.LoginCredential;
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
public interface LoginCredentialRepository extends JpaRepository<LoginCredential, Long> {

    List<LoginCredential> findAllByAccountAndTypeIn(String account, List<String> types);
}
