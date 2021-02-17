package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entity.User;
import ru.geekbrains.persist.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserRepr> findAllProductsByUserId(long id) {
        return userRepo.findAllProductsByUserId(id)
                .stream()
                .map(UserRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRepr> findAll() {
        return userRepo.findAll()
                .stream()
                .map(UserRepr::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<UserRepr> findById(Long id) {
        return userRepo.findById(id)
                .map(UserRepr::new);
    }

    @Transactional
    @Override
    public Optional<UserRepr> findById(String id) {
        long idd = Long.parseLong(id);
        return userRepo.findById(idd)
                .map(UserRepr::new);
    }

    @Transactional
    @Override
    public List<UserRepr> findByName(String userName) {
        return userRepo.findByUserName(userName)
                .stream()
                .map(UserRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRepr> findWithFilter(String userName) {
        return userRepo.findUserByUserNameLike(userName)
                .stream()
                .map(UserRepr::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(UserRepr user) {
        userRepo.save(new User(user));
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByName(String userName) {
        userRepo.deleteByUserName(userName);
    }

    @Transactional
    @Override
    public void delete(String id) {
        long idd = Long.parseLong(id);
        userRepo.deleteById(idd);
    }
}
